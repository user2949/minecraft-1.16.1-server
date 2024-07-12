package io.netty.util.concurrent;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public abstract class SingleThreadEventExecutor extends AbstractScheduledEventExecutor implements OrderedEventExecutor {
	static final int DEFAULT_MAX_PENDING_EXECUTOR_TASKS = Math.max(16, SystemPropertyUtil.getInt("io.netty.eventexecutor.maxPendingTasks", Integer.MAX_VALUE));
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(SingleThreadEventExecutor.class);
	private static final int ST_NOT_STARTED = 1;
	private static final int ST_STARTED = 2;
	private static final int ST_SHUTTING_DOWN = 3;
	private static final int ST_SHUTDOWN = 4;
	private static final int ST_TERMINATED = 5;
	private static final Runnable WAKEUP_TASK = new Runnable() {
		public void run() {
		}
	};
	private static final Runnable NOOP_TASK = new Runnable() {
		public void run() {
		}
	};
	private static final AtomicIntegerFieldUpdater<SingleThreadEventExecutor> STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(
		SingleThreadEventExecutor.class, "state"
	);
	private static final AtomicReferenceFieldUpdater<SingleThreadEventExecutor, ThreadProperties> PROPERTIES_UPDATER = AtomicReferenceFieldUpdater.newUpdater(
		SingleThreadEventExecutor.class, ThreadProperties.class, "threadProperties"
	);
	private final Queue<Runnable> taskQueue;
	private volatile Thread thread;
	private volatile ThreadProperties threadProperties;
	private final Executor executor;
	private volatile boolean interrupted;
	private final Semaphore threadLock = new Semaphore(0);
	private final Set<Runnable> shutdownHooks = new LinkedHashSet();
	private final boolean addTaskWakesUp;
	private final int maxPendingTasks;
	private final RejectedExecutionHandler rejectedExecutionHandler;
	private long lastExecutionTime;
	private volatile int state = 1;
	private volatile long gracefulShutdownQuietPeriod;
	private volatile long gracefulShutdownTimeout;
	private long gracefulShutdownStartTime;
	private final Promise<?> terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
	private static final long SCHEDULE_PURGE_INTERVAL = TimeUnit.SECONDS.toNanos(1L);

	protected SingleThreadEventExecutor(EventExecutorGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp) {
		this(parent, new ThreadPerTaskExecutor(threadFactory), addTaskWakesUp);
	}

	protected SingleThreadEventExecutor(
		EventExecutorGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp, int maxPendingTasks, RejectedExecutionHandler rejectedHandler
	) {
		this(parent, new ThreadPerTaskExecutor(threadFactory), addTaskWakesUp, maxPendingTasks, rejectedHandler);
	}

	protected SingleThreadEventExecutor(EventExecutorGroup parent, Executor executor, boolean addTaskWakesUp) {
		this(parent, executor, addTaskWakesUp, DEFAULT_MAX_PENDING_EXECUTOR_TASKS, RejectedExecutionHandlers.reject());
	}

	protected SingleThreadEventExecutor(
		EventExecutorGroup parent, Executor executor, boolean addTaskWakesUp, int maxPendingTasks, RejectedExecutionHandler rejectedHandler
	) {
		super(parent);
		this.addTaskWakesUp = addTaskWakesUp;
		this.maxPendingTasks = Math.max(16, maxPendingTasks);
		this.executor = ObjectUtil.checkNotNull(executor, "executor");
		this.taskQueue = this.newTaskQueue(this.maxPendingTasks);
		this.rejectedExecutionHandler = ObjectUtil.checkNotNull(rejectedHandler, "rejectedHandler");
	}

	@Deprecated
	protected Queue<Runnable> newTaskQueue() {
		return this.newTaskQueue(this.maxPendingTasks);
	}

	protected Queue<Runnable> newTaskQueue(int maxPendingTasks) {
		return new LinkedBlockingQueue(maxPendingTasks);
	}

	protected void interruptThread() {
		Thread currentThread = this.thread;
		if (currentThread == null) {
			this.interrupted = true;
		} else {
			currentThread.interrupt();
		}
	}

	protected Runnable pollTask() {
		assert this.inEventLoop();

		return pollTaskFrom(this.taskQueue);
	}

	protected static Runnable pollTaskFrom(Queue<Runnable> taskQueue) {
		Runnable task;
		do {
			task = (Runnable)taskQueue.poll();
		} while (task == WAKEUP_TASK);

		return task;
	}

	protected Runnable takeTask() {
		assert this.inEventLoop();

		if (!(this.taskQueue instanceof BlockingQueue)) {
			throw new UnsupportedOperationException();
		} else {
			BlockingQueue<Runnable> taskQueue = (BlockingQueue<Runnable>)this.taskQueue;

			Runnable task;
			do {
				ScheduledFutureTask<?> scheduledTask = this.peekScheduledTask();
				if (scheduledTask == null) {
					Runnable taskx = null;

					try {
						taskx = (Runnable)taskQueue.take();
						if (taskx == WAKEUP_TASK) {
							taskx = null;
						}
					} catch (InterruptedException var7) {
					}

					return taskx;
				}

				long delayNanos = scheduledTask.delayNanos();
				task = null;
				if (delayNanos > 0L) {
					try {
						task = (Runnable)taskQueue.poll(delayNanos, TimeUnit.NANOSECONDS);
					} catch (InterruptedException var8) {
						return null;
					}
				}

				if (task == null) {
					this.fetchFromScheduledTaskQueue();
					task = (Runnable)taskQueue.poll();
				}
			} while (task == null);

			return task;
		}
	}

	private boolean fetchFromScheduledTaskQueue() {
		long nanoTime = AbstractScheduledEventExecutor.nanoTime();

		for (Runnable scheduledTask = this.pollScheduledTask(nanoTime); scheduledTask != null; scheduledTask = this.pollScheduledTask(nanoTime)) {
			if (!this.taskQueue.offer(scheduledTask)) {
				this.scheduledTaskQueue().add((ScheduledFutureTask)scheduledTask);
				return false;
			}
		}

		return true;
	}

	protected Runnable peekTask() {
		assert this.inEventLoop();

		return (Runnable)this.taskQueue.peek();
	}

	protected boolean hasTasks() {
		assert this.inEventLoop();

		return !this.taskQueue.isEmpty();
	}

	public int pendingTasks() {
		return this.taskQueue.size();
	}

	protected void addTask(Runnable task) {
		if (task == null) {
			throw new NullPointerException("task");
		} else {
			if (!this.offerTask(task)) {
				this.reject(task);
			}
		}
	}

	final boolean offerTask(Runnable task) {
		if (this.isShutdown()) {
			reject();
		}

		return this.taskQueue.offer(task);
	}

	protected boolean removeTask(Runnable task) {
		if (task == null) {
			throw new NullPointerException("task");
		} else {
			return this.taskQueue.remove(task);
		}
	}

	protected boolean runAllTasks() {
		assert this.inEventLoop();

		boolean ranAtLeastOne = false;

		boolean fetchedAll;
		do {
			fetchedAll = this.fetchFromScheduledTaskQueue();
			if (this.runAllTasksFrom(this.taskQueue)) {
				ranAtLeastOne = true;
			}
		} while (!fetchedAll);

		if (ranAtLeastOne) {
			this.lastExecutionTime = ScheduledFutureTask.nanoTime();
		}

		this.afterRunningAllTasks();
		return ranAtLeastOne;
	}

	protected final boolean runAllTasksFrom(Queue<Runnable> taskQueue) {
		Runnable task = pollTaskFrom(taskQueue);
		if (task == null) {
			return false;
		} else {
			do {
				safeExecute(task);
				task = pollTaskFrom(taskQueue);
			} while (task != null);

			return true;
		}
	}

	protected boolean runAllTasks(long timeoutNanos) {
		this.fetchFromScheduledTaskQueue();
		Runnable task = this.pollTask();
		if (task == null) {
			this.afterRunningAllTasks();
			return false;
		} else {
			long deadline = ScheduledFutureTask.nanoTime() + timeoutNanos;
			long runTasks = 0L;

			long lastExecutionTime;
			while (true) {
				safeExecute(task);
				if ((++runTasks & 63L) == 0L) {
					lastExecutionTime = ScheduledFutureTask.nanoTime();
					if (lastExecutionTime >= deadline) {
						break;
					}
				}

				task = this.pollTask();
				if (task == null) {
					lastExecutionTime = ScheduledFutureTask.nanoTime();
					break;
				}
			}

			this.afterRunningAllTasks();
			this.lastExecutionTime = lastExecutionTime;
			return true;
		}
	}

	protected void afterRunningAllTasks() {
	}

	protected long delayNanos(long currentTimeNanos) {
		ScheduledFutureTask<?> scheduledTask = this.peekScheduledTask();
		return scheduledTask == null ? SCHEDULE_PURGE_INTERVAL : scheduledTask.delayNanos(currentTimeNanos);
	}

	protected void updateLastExecutionTime() {
		this.lastExecutionTime = ScheduledFutureTask.nanoTime();
	}

	protected abstract void run();

	protected void cleanup() {
	}

	protected void wakeup(boolean inEventLoop) {
		if (!inEventLoop || this.state == 3) {
			this.taskQueue.offer(WAKEUP_TASK);
		}
	}

	@Override
	public boolean inEventLoop(Thread thread) {
		return thread == this.thread;
	}

	public void addShutdownHook(Runnable task) {
		if (this.inEventLoop()) {
			this.shutdownHooks.add(task);
		} else {
			this.execute(new Runnable() {
				public void run() {
					SingleThreadEventExecutor.this.shutdownHooks.add(task);
				}
			});
		}
	}

	public void removeShutdownHook(Runnable task) {
		if (this.inEventLoop()) {
			this.shutdownHooks.remove(task);
		} else {
			this.execute(new Runnable() {
				public void run() {
					SingleThreadEventExecutor.this.shutdownHooks.remove(task);
				}
			});
		}
	}

	private boolean runShutdownHooks() {
		boolean ran = false;

		while (!this.shutdownHooks.isEmpty()) {
			List<Runnable> copy = new ArrayList(this.shutdownHooks);
			this.shutdownHooks.clear();

			for (Runnable task : copy) {
				try {
					task.run();
				} catch (Throwable var9) {
					logger.warn("Shutdown hook raised an exception.", var9);
				} finally {
					ran = true;
				}
			}
		}

		if (ran) {
			this.lastExecutionTime = ScheduledFutureTask.nanoTime();
		}

		return ran;
	}

	@Override
	public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
		if (quietPeriod < 0L) {
			throw new IllegalArgumentException("quietPeriod: " + quietPeriod + " (expected >= 0)");
		} else if (timeout < quietPeriod) {
			throw new IllegalArgumentException("timeout: " + timeout + " (expected >= quietPeriod (" + quietPeriod + "))");
		} else if (unit == null) {
			throw new NullPointerException("unit");
		} else if (this.isShuttingDown()) {
			return this.terminationFuture();
		} else {
			boolean inEventLoop = this.inEventLoop();

			while (!this.isShuttingDown()) {
				boolean wakeup = true;
				int oldState = this.state;
				int newState;
				if (inEventLoop) {
					newState = 3;
				} else {
					switch (oldState) {
						case 1:
						case 2:
							newState = 3;
							break;
						default:
							newState = oldState;
							wakeup = false;
					}
				}

				if (STATE_UPDATER.compareAndSet(this, oldState, newState)) {
					this.gracefulShutdownQuietPeriod = unit.toNanos(quietPeriod);
					this.gracefulShutdownTimeout = unit.toNanos(timeout);
					if (oldState == 1) {
						try {
							this.doStartThread();
						} catch (Throwable var10) {
							STATE_UPDATER.set(this, 5);
							this.terminationFuture.tryFailure(var10);
							if (!(var10 instanceof Exception)) {
								PlatformDependent.throwException(var10);
							}

							return this.terminationFuture;
						}
					}

					if (wakeup) {
						this.wakeup(inEventLoop);
					}

					return this.terminationFuture();
				}
			}

			return this.terminationFuture();
		}
	}

	@Override
	public Future<?> terminationFuture() {
		return this.terminationFuture;
	}

	@Deprecated
	@Override
	public void shutdown() {
		if (!this.isShutdown()) {
			boolean inEventLoop = this.inEventLoop();

			while (!this.isShuttingDown()) {
				boolean wakeup = true;
				int oldState = this.state;
				int newState;
				if (inEventLoop) {
					newState = 4;
				} else {
					switch (oldState) {
						case 1:
						case 2:
						case 3:
							newState = 4;
							break;
						default:
							newState = oldState;
							wakeup = false;
					}
				}

				if (STATE_UPDATER.compareAndSet(this, oldState, newState)) {
					if (oldState == 1) {
						try {
							this.doStartThread();
						} catch (Throwable var5) {
							STATE_UPDATER.set(this, 5);
							this.terminationFuture.tryFailure(var5);
							if (!(var5 instanceof Exception)) {
								PlatformDependent.throwException(var5);
							}

							return;
						}
					}

					if (wakeup) {
						this.wakeup(inEventLoop);
					}

					return;
				}
			}
		}
	}

	@Override
	public boolean isShuttingDown() {
		return this.state >= 3;
	}

	public boolean isShutdown() {
		return this.state >= 4;
	}

	public boolean isTerminated() {
		return this.state == 5;
	}

	protected boolean confirmShutdown() {
		if (!this.isShuttingDown()) {
			return false;
		} else if (!this.inEventLoop()) {
			throw new IllegalStateException("must be invoked from an event loop");
		} else {
			this.cancelScheduledTasks();
			if (this.gracefulShutdownStartTime == 0L) {
				this.gracefulShutdownStartTime = ScheduledFutureTask.nanoTime();
			}

			if (!this.runAllTasks() && !this.runShutdownHooks()) {
				long nanoTime = ScheduledFutureTask.nanoTime();
				if (this.isShutdown() || nanoTime - this.gracefulShutdownStartTime > this.gracefulShutdownTimeout) {
					return true;
				} else if (nanoTime - this.lastExecutionTime <= this.gracefulShutdownQuietPeriod) {
					this.wakeup(true);

					try {
						Thread.sleep(100L);
					} catch (InterruptedException var4) {
					}

					return false;
				} else {
					return true;
				}
			} else if (this.isShutdown()) {
				return true;
			} else if (this.gracefulShutdownQuietPeriod == 0L) {
				return true;
			} else {
				this.wakeup(true);
				return false;
			}
		}
	}

	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		if (unit == null) {
			throw new NullPointerException("unit");
		} else if (this.inEventLoop()) {
			throw new IllegalStateException("cannot await termination of the current thread");
		} else {
			if (this.threadLock.tryAcquire(timeout, unit)) {
				this.threadLock.release();
			}

			return this.isTerminated();
		}
	}

	public void execute(Runnable task) {
		if (task == null) {
			throw new NullPointerException("task");
		} else {
			boolean inEventLoop = this.inEventLoop();
			this.addTask(task);
			if (!inEventLoop) {
				this.startThread();
				if (this.isShutdown() && this.removeTask(task)) {
					reject();
				}
			}

			if (!this.addTaskWakesUp && this.wakesUpForTask(task)) {
				this.wakeup(inEventLoop);
			}
		}
	}

	public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
		this.throwIfInEventLoop("invokeAny");
		return (T)super.invokeAny(tasks);
	}

	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		this.throwIfInEventLoop("invokeAny");
		return (T)super.invokeAny(tasks, timeout, unit);
	}

	public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
		this.throwIfInEventLoop("invokeAll");
		return super.invokeAll(tasks);
	}

	public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
		this.throwIfInEventLoop("invokeAll");
		return super.invokeAll(tasks, timeout, unit);
	}

	private void throwIfInEventLoop(String method) {
		if (this.inEventLoop()) {
			throw new RejectedExecutionException("Calling " + method + " from within the EventLoop is not allowed");
		}
	}

	public final ThreadProperties threadProperties() {
		ThreadProperties threadProperties = this.threadProperties;
		if (threadProperties == null) {
			Thread thread = this.thread;
			if (thread == null) {
				assert !this.inEventLoop();

				this.submit(NOOP_TASK).syncUninterruptibly();
				thread = this.thread;

				assert thread != null;
			}

			threadProperties = new SingleThreadEventExecutor.DefaultThreadProperties(thread);
			if (!PROPERTIES_UPDATER.compareAndSet(this, null, threadProperties)) {
				threadProperties = this.threadProperties;
			}
		}

		return threadProperties;
	}

	protected boolean wakesUpForTask(Runnable task) {
		return true;
	}

	protected static void reject() {
		throw new RejectedExecutionException("event executor terminated");
	}

	protected final void reject(Runnable task) {
		this.rejectedExecutionHandler.rejected(task, this);
	}

	private void startThread() {
		if (this.state == 1 && STATE_UPDATER.compareAndSet(this, 1, 2)) {
			try {
				this.doStartThread();
			} catch (Throwable var2) {
				STATE_UPDATER.set(this, 1);
				PlatformDependent.throwException(var2);
			}
		}
	}

	private void doStartThread() {
		assert this.thread == null;

		this.executor
			.execute(
				new Runnable() {
					public void run() {
						SingleThreadEventExecutor.this.thread = Thread.currentThread();
						if (SingleThreadEventExecutor.this.interrupted) {
							SingleThreadEventExecutor.this.thread.interrupt();
						}
		
						boolean success = false;
						SingleThreadEventExecutor.this.updateLastExecutionTime();
		
						try {
							SingleThreadEventExecutor.this.run();
							success = true;
						} catch (Throwable var112) {
							SingleThreadEventExecutor.logger.warn("Unexpected exception from an event executor: ", var112);
						} finally {
							int oldState;
							do {
								oldState = SingleThreadEventExecutor.this.state;
							} while (oldState < 3 && !SingleThreadEventExecutor.STATE_UPDATER.compareAndSet(SingleThreadEventExecutor.this, oldState, 3));
		
							if (success && SingleThreadEventExecutor.this.gracefulShutdownStartTime == 0L) {
								SingleThreadEventExecutor.logger
									.error(
										"Buggy "
											+ EventExecutor.class.getSimpleName()
											+ " implementation; "
											+ SingleThreadEventExecutor.class.getSimpleName()
											+ ".confirmShutdown() must be called before run() implementation terminates."
									);
							}
		
							try {
								while (!SingleThreadEventExecutor.this.confirmShutdown()) {
								}
							} finally {
								try {
									SingleThreadEventExecutor.this.cleanup();
								} finally {
									SingleThreadEventExecutor.STATE_UPDATER.set(SingleThreadEventExecutor.this, 5);
									SingleThreadEventExecutor.this.threadLock.release();
									if (!SingleThreadEventExecutor.this.taskQueue.isEmpty()) {
										SingleThreadEventExecutor.logger
											.warn("An event executor terminated with non-empty task queue (" + SingleThreadEventExecutor.this.taskQueue.size() + ')');
									}
		
									SingleThreadEventExecutor.this.terminationFuture.setSuccess(null);
								}
							}
						}
					}
				}
			);
	}

	private static final class DefaultThreadProperties implements ThreadProperties {
		private final Thread t;

		DefaultThreadProperties(Thread t) {
			this.t = t;
		}

		@Override
		public State state() {
			return this.t.getState();
		}

		@Override
		public int priority() {
			return this.t.getPriority();
		}

		@Override
		public boolean isInterrupted() {
			return this.t.isInterrupted();
		}

		@Override
		public boolean isDaemon() {
			return this.t.isDaemon();
		}

		@Override
		public String name() {
			return this.t.getName();
		}

		@Override
		public long id() {
			return this.t.getId();
		}

		@Override
		public StackTraceElement[] stackTrace() {
			return this.t.getStackTrace();
		}

		@Override
		public boolean isAlive() {
			return this.t.isAlive();
		}
	}
}
