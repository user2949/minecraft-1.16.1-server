package io.netty.util;

import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;

public class HashedWheelTimer implements Timer {
	static final InternalLogger logger = InternalLoggerFactory.getInstance(HashedWheelTimer.class);
	private static final AtomicInteger INSTANCE_COUNTER = new AtomicInteger();
	private static final AtomicBoolean WARNED_TOO_MANY_INSTANCES = new AtomicBoolean();
	private static final int INSTANCE_COUNT_LIMIT = 64;
	private static final ResourceLeakDetector<HashedWheelTimer> leakDetector = ResourceLeakDetectorFactory.instance()
		.newResourceLeakDetector(HashedWheelTimer.class, 1);
	private static final AtomicIntegerFieldUpdater<HashedWheelTimer> WORKER_STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(
		HashedWheelTimer.class, "workerState"
	);
	private final ResourceLeakTracker<HashedWheelTimer> leak;
	private final HashedWheelTimer.Worker worker = new HashedWheelTimer.Worker();
	private final Thread workerThread;
	public static final int WORKER_STATE_INIT = 0;
	public static final int WORKER_STATE_STARTED = 1;
	public static final int WORKER_STATE_SHUTDOWN = 2;
	private volatile int workerState;
	private final long tickDuration;
	private final HashedWheelTimer.HashedWheelBucket[] wheel;
	private final int mask;
	private final CountDownLatch startTimeInitialized = new CountDownLatch(1);
	private final Queue<HashedWheelTimer.HashedWheelTimeout> timeouts = PlatformDependent.newMpscQueue();
	private final Queue<HashedWheelTimer.HashedWheelTimeout> cancelledTimeouts = PlatformDependent.newMpscQueue();
	private final AtomicLong pendingTimeouts = new AtomicLong(0L);
	private final long maxPendingTimeouts;
	private volatile long startTime;

	public HashedWheelTimer() {
		this(Executors.defaultThreadFactory());
	}

	public HashedWheelTimer(long tickDuration, TimeUnit unit) {
		this(Executors.defaultThreadFactory(), tickDuration, unit);
	}

	public HashedWheelTimer(long tickDuration, TimeUnit unit, int ticksPerWheel) {
		this(Executors.defaultThreadFactory(), tickDuration, unit, ticksPerWheel);
	}

	public HashedWheelTimer(ThreadFactory threadFactory) {
		this(threadFactory, 100L, TimeUnit.MILLISECONDS);
	}

	public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit) {
		this(threadFactory, tickDuration, unit, 512);
	}

	public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel) {
		this(threadFactory, tickDuration, unit, ticksPerWheel, true);
	}

	public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel, boolean leakDetection) {
		this(threadFactory, tickDuration, unit, ticksPerWheel, leakDetection, -1L);
	}

	public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel, boolean leakDetection, long maxPendingTimeouts) {
		if (threadFactory == null) {
			throw new NullPointerException("threadFactory");
		} else if (unit == null) {
			throw new NullPointerException("unit");
		} else if (tickDuration <= 0L) {
			throw new IllegalArgumentException("tickDuration must be greater than 0: " + tickDuration);
		} else if (ticksPerWheel <= 0) {
			throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + ticksPerWheel);
		} else {
			this.wheel = createWheel(ticksPerWheel);
			this.mask = this.wheel.length - 1;
			this.tickDuration = unit.toNanos(tickDuration);
			if (this.tickDuration >= Long.MAX_VALUE / (long)this.wheel.length) {
				throw new IllegalArgumentException(
					String.format("tickDuration: %d (expected: 0 < tickDuration in nanos < %d", tickDuration, Long.MAX_VALUE / (long)this.wheel.length)
				);
			} else {
				this.workerThread = threadFactory.newThread(this.worker);
				this.leak = !leakDetection && this.workerThread.isDaemon() ? null : leakDetector.track(this);
				this.maxPendingTimeouts = maxPendingTimeouts;
				if (INSTANCE_COUNTER.incrementAndGet() > 64 && WARNED_TOO_MANY_INSTANCES.compareAndSet(false, true)) {
					reportTooManyInstances();
				}
			}
		}
	}

	protected void finalize() throws Throwable {
		try {
			super.finalize();
		} finally {
			if (WORKER_STATE_UPDATER.getAndSet(this, 2) != 2) {
				INSTANCE_COUNTER.decrementAndGet();
			}
		}
	}

	private static HashedWheelTimer.HashedWheelBucket[] createWheel(int ticksPerWheel) {
		if (ticksPerWheel <= 0) {
			throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + ticksPerWheel);
		} else if (ticksPerWheel > 1073741824) {
			throw new IllegalArgumentException("ticksPerWheel may not be greater than 2^30: " + ticksPerWheel);
		} else {
			ticksPerWheel = normalizeTicksPerWheel(ticksPerWheel);
			HashedWheelTimer.HashedWheelBucket[] wheel = new HashedWheelTimer.HashedWheelBucket[ticksPerWheel];

			for (int i = 0; i < wheel.length; i++) {
				wheel[i] = new HashedWheelTimer.HashedWheelBucket();
			}

			return wheel;
		}
	}

	private static int normalizeTicksPerWheel(int ticksPerWheel) {
		int normalizedTicksPerWheel = 1;

		while (normalizedTicksPerWheel < ticksPerWheel) {
			normalizedTicksPerWheel <<= 1;
		}

		return normalizedTicksPerWheel;
	}

	public void start() {
		switch (WORKER_STATE_UPDATER.get(this)) {
			case 0:
				if (WORKER_STATE_UPDATER.compareAndSet(this, 0, 1)) {
					this.workerThread.start();
				}
			case 1:
				break;
			case 2:
				throw new IllegalStateException("cannot be started once stopped");
			default:
				throw new Error("Invalid WorkerState");
		}

		while (this.startTime == 0L) {
			try {
				this.startTimeInitialized.await();
			} catch (InterruptedException var2) {
			}
		}
	}

	@Override
	public Set<Timeout> stop() {
		if (Thread.currentThread() == this.workerThread) {
			throw new IllegalStateException(HashedWheelTimer.class.getSimpleName() + ".stop() cannot be called from " + TimerTask.class.getSimpleName());
		} else if (!WORKER_STATE_UPDATER.compareAndSet(this, 1, 2)) {
			if (WORKER_STATE_UPDATER.getAndSet(this, 2) != 2) {
				INSTANCE_COUNTER.decrementAndGet();
				if (this.leak != null) {
					boolean closed = this.leak.close(this);

					assert closed;
				}
			}

			return Collections.emptySet();
		} else {
			try {
				boolean interrupted = false;

				while (this.workerThread.isAlive()) {
					this.workerThread.interrupt();

					try {
						this.workerThread.join(100L);
					} catch (InterruptedException var7) {
						interrupted = true;
					}
				}

				if (interrupted) {
					Thread.currentThread().interrupt();
				}
			} finally {
				INSTANCE_COUNTER.decrementAndGet();
				if (this.leak != null) {
					boolean closed = this.leak.close(this);

					assert closed;
				}
			}

			return this.worker.unprocessedTimeouts();
		}
	}

	@Override
	public Timeout newTimeout(TimerTask task, long delay, TimeUnit unit) {
		if (task == null) {
			throw new NullPointerException("task");
		} else if (unit == null) {
			throw new NullPointerException("unit");
		} else {
			long pendingTimeoutsCount = this.pendingTimeouts.incrementAndGet();
			if (this.maxPendingTimeouts > 0L && pendingTimeoutsCount > this.maxPendingTimeouts) {
				this.pendingTimeouts.decrementAndGet();
				throw new RejectedExecutionException(
					"Number of pending timeouts (" + pendingTimeoutsCount + ") is greater than or equal to maximum allowed pending timeouts (" + this.maxPendingTimeouts + ")"
				);
			} else {
				this.start();
				long deadline = System.nanoTime() + unit.toNanos(delay) - this.startTime;
				if (delay > 0L && deadline < 0L) {
					deadline = Long.MAX_VALUE;
				}

				HashedWheelTimer.HashedWheelTimeout timeout = new HashedWheelTimer.HashedWheelTimeout(this, task, deadline);
				this.timeouts.add(timeout);
				return timeout;
			}
		}
	}

	public long pendingTimeouts() {
		return this.pendingTimeouts.get();
	}

	private static void reportTooManyInstances() {
		String resourceType = StringUtil.simpleClassName(HashedWheelTimer.class);
		logger.error(
			"You are creating too many "
				+ resourceType
				+ " instances. "
				+ resourceType
				+ " is a shared resource that must be reused across the JVM,so that only a few instances are created."
		);
	}

	private static final class HashedWheelBucket {
		private HashedWheelTimer.HashedWheelTimeout head;
		private HashedWheelTimer.HashedWheelTimeout tail;

		private HashedWheelBucket() {
		}

		public void addTimeout(HashedWheelTimer.HashedWheelTimeout timeout) {
			assert timeout.bucket == null;

			timeout.bucket = this;
			if (this.head == null) {
				this.head = this.tail = timeout;
			} else {
				this.tail.next = timeout;
				timeout.prev = this.tail;
				this.tail = timeout;
			}
		}

		public void expireTimeouts(long deadline) {
			HashedWheelTimer.HashedWheelTimeout timeout = this.head;

			while (timeout != null) {
				HashedWheelTimer.HashedWheelTimeout next = timeout.next;
				if (timeout.remainingRounds <= 0L) {
					next = this.remove(timeout);
					if (timeout.deadline > deadline) {
						throw new IllegalStateException(String.format("timeout.deadline (%d) > deadline (%d)", timeout.deadline, deadline));
					}

					timeout.expire();
				} else if (timeout.isCancelled()) {
					next = this.remove(timeout);
				} else {
					timeout.remainingRounds--;
				}

				timeout = next;
			}
		}

		public HashedWheelTimer.HashedWheelTimeout remove(HashedWheelTimer.HashedWheelTimeout timeout) {
			HashedWheelTimer.HashedWheelTimeout next = timeout.next;
			if (timeout.prev != null) {
				timeout.prev.next = next;
			}

			if (timeout.next != null) {
				timeout.next.prev = timeout.prev;
			}

			if (timeout == this.head) {
				if (timeout == this.tail) {
					this.tail = null;
					this.head = null;
				} else {
					this.head = next;
				}
			} else if (timeout == this.tail) {
				this.tail = timeout.prev;
			}

			timeout.prev = null;
			timeout.next = null;
			timeout.bucket = null;
			timeout.timer.pendingTimeouts.decrementAndGet();
			return next;
		}

		public void clearTimeouts(Set<Timeout> set) {
			while (true) {
				HashedWheelTimer.HashedWheelTimeout timeout = this.pollTimeout();
				if (timeout == null) {
					return;
				}

				if (!timeout.isExpired() && !timeout.isCancelled()) {
					set.add(timeout);
				}
			}
		}

		private HashedWheelTimer.HashedWheelTimeout pollTimeout() {
			HashedWheelTimer.HashedWheelTimeout head = this.head;
			if (head == null) {
				return null;
			} else {
				HashedWheelTimer.HashedWheelTimeout next = head.next;
				if (next == null) {
					this.tail = this.head = null;
				} else {
					this.head = next;
					next.prev = null;
				}

				head.next = null;
				head.prev = null;
				head.bucket = null;
				return head;
			}
		}
	}

	private static final class HashedWheelTimeout implements Timeout {
		private static final int ST_INIT = 0;
		private static final int ST_CANCELLED = 1;
		private static final int ST_EXPIRED = 2;
		private static final AtomicIntegerFieldUpdater<HashedWheelTimer.HashedWheelTimeout> STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(
			HashedWheelTimer.HashedWheelTimeout.class, "state"
		);
		private final HashedWheelTimer timer;
		private final TimerTask task;
		private final long deadline;
		private volatile int state = 0;
		long remainingRounds;
		HashedWheelTimer.HashedWheelTimeout next;
		HashedWheelTimer.HashedWheelTimeout prev;
		HashedWheelTimer.HashedWheelBucket bucket;

		HashedWheelTimeout(HashedWheelTimer timer, TimerTask task, long deadline) {
			this.timer = timer;
			this.task = task;
			this.deadline = deadline;
		}

		@Override
		public Timer timer() {
			return this.timer;
		}

		@Override
		public TimerTask task() {
			return this.task;
		}

		@Override
		public boolean cancel() {
			if (!this.compareAndSetState(0, 1)) {
				return false;
			} else {
				this.timer.cancelledTimeouts.add(this);
				return true;
			}
		}

		void remove() {
			HashedWheelTimer.HashedWheelBucket bucket = this.bucket;
			if (bucket != null) {
				bucket.remove(this);
			} else {
				this.timer.pendingTimeouts.decrementAndGet();
			}
		}

		public boolean compareAndSetState(int expected, int state) {
			return STATE_UPDATER.compareAndSet(this, expected, state);
		}

		public int state() {
			return this.state;
		}

		@Override
		public boolean isCancelled() {
			return this.state() == 1;
		}

		@Override
		public boolean isExpired() {
			return this.state() == 2;
		}

		public void expire() {
			if (this.compareAndSetState(0, 2)) {
				try {
					this.task.run(this);
				} catch (Throwable var2) {
					if (HashedWheelTimer.logger.isWarnEnabled()) {
						HashedWheelTimer.logger.warn("An exception was thrown by " + TimerTask.class.getSimpleName() + '.', var2);
					}
				}
			}
		}

		public String toString() {
			long currentTime = System.nanoTime();
			long remaining = this.deadline - currentTime + this.timer.startTime;
			StringBuilder buf = new StringBuilder(192).append(StringUtil.simpleClassName(this)).append('(').append("deadline: ");
			if (remaining > 0L) {
				buf.append(remaining).append(" ns later");
			} else if (remaining < 0L) {
				buf.append(-remaining).append(" ns ago");
			} else {
				buf.append("now");
			}

			if (this.isCancelled()) {
				buf.append(", cancelled");
			}

			return buf.append(", task: ").append(this.task()).append(')').toString();
		}
	}

	private final class Worker implements Runnable {
		private final Set<Timeout> unprocessedTimeouts = new HashSet();
		private long tick;

		private Worker() {
		}

		public void run() {
			HashedWheelTimer.this.startTime = System.nanoTime();
			if (HashedWheelTimer.this.startTime == 0L) {
				HashedWheelTimer.this.startTime = 1L;
			}

			HashedWheelTimer.this.startTimeInitialized.countDown();

			do {
				long deadline = this.waitForNextTick();
				if (deadline > 0L) {
					int idx = (int)(this.tick & (long)HashedWheelTimer.this.mask);
					this.processCancelledTasks();
					HashedWheelTimer.HashedWheelBucket bucket = HashedWheelTimer.this.wheel[idx];
					this.transferTimeoutsToBuckets();
					bucket.expireTimeouts(deadline);
					this.tick++;
				}
			} while (HashedWheelTimer.WORKER_STATE_UPDATER.get(HashedWheelTimer.this) == 1);

			for (HashedWheelTimer.HashedWheelBucket bucket : HashedWheelTimer.this.wheel) {
				bucket.clearTimeouts(this.unprocessedTimeouts);
			}

			while (true) {
				HashedWheelTimer.HashedWheelTimeout timeout = (HashedWheelTimer.HashedWheelTimeout)HashedWheelTimer.this.timeouts.poll();
				if (timeout == null) {
					this.processCancelledTasks();
					return;
				}

				if (!timeout.isCancelled()) {
					this.unprocessedTimeouts.add(timeout);
				}
			}
		}

		private void transferTimeoutsToBuckets() {
			for (int i = 0; i < 100000; i++) {
				HashedWheelTimer.HashedWheelTimeout timeout = (HashedWheelTimer.HashedWheelTimeout)HashedWheelTimer.this.timeouts.poll();
				if (timeout == null) {
					break;
				}

				if (timeout.state() != 1) {
					long calculated = timeout.deadline / HashedWheelTimer.this.tickDuration;
					timeout.remainingRounds = (calculated - this.tick) / (long)HashedWheelTimer.this.wheel.length;
					long ticks = Math.max(calculated, this.tick);
					int stopIndex = (int)(ticks & (long)HashedWheelTimer.this.mask);
					HashedWheelTimer.HashedWheelBucket bucket = HashedWheelTimer.this.wheel[stopIndex];
					bucket.addTimeout(timeout);
				}
			}
		}

		private void processCancelledTasks() {
			while (true) {
				HashedWheelTimer.HashedWheelTimeout timeout = (HashedWheelTimer.HashedWheelTimeout)HashedWheelTimer.this.cancelledTimeouts.poll();
				if (timeout == null) {
					return;
				}

				try {
					timeout.remove();
				} catch (Throwable var3) {
					if (HashedWheelTimer.logger.isWarnEnabled()) {
						HashedWheelTimer.logger.warn("An exception was thrown while process a cancellation task", var3);
					}
				}
			}
		}

		private long waitForNextTick() {
			long deadline = HashedWheelTimer.this.tickDuration * (this.tick + 1L);

			while (true) {
				long currentTime = System.nanoTime() - HashedWheelTimer.this.startTime;
				long sleepTimeMs = (deadline - currentTime + 999999L) / 1000000L;
				if (sleepTimeMs <= 0L) {
					if (currentTime == Long.MIN_VALUE) {
						return -9223372036854775807L;
					}

					return currentTime;
				}

				if (PlatformDependent.isWindows()) {
					sleepTimeMs = sleepTimeMs / 10L * 10L;
				}

				try {
					Thread.sleep(sleepTimeMs);
				} catch (InterruptedException var8) {
					if (HashedWheelTimer.WORKER_STATE_UPDATER.get(HashedWheelTimer.this) == 2) {
						return Long.MIN_VALUE;
					}
				}
			}
		}

		public Set<Timeout> unprocessedTimeouts() {
			return Collections.unmodifiableSet(this.unprocessedTimeouts);
		}
	}
}
