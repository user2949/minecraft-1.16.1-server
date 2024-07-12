package io.netty.channel.kqueue;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.SelectStrategy;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.channel.kqueue.AbstractKQueueChannel.AbstractKQueueUnsafe;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.IovArray;
import io.netty.util.IntSupplier;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

final class KQueueEventLoop extends SingleThreadEventLoop {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(KQueueEventLoop.class);
	private static final AtomicIntegerFieldUpdater<KQueueEventLoop> WAKEN_UP_UPDATER = AtomicIntegerFieldUpdater.newUpdater(KQueueEventLoop.class, "wakenUp");
	private static final int KQUEUE_WAKE_UP_IDENT = 0;
	private final NativeLongArray jniChannelPointers;
	private final boolean allowGrowing;
	private final FileDescriptor kqueueFd;
	private final KQueueEventArray changeList;
	private final KQueueEventArray eventList;
	private final SelectStrategy selectStrategy;
	private final IovArray iovArray = new IovArray();
	private final IntSupplier selectNowSupplier = new IntSupplier() {
		@Override
		public int get() throws Exception {
			return KQueueEventLoop.this.kqueueWaitNow();
		}
	};
	private final Callable<Integer> pendingTasksCallable = new Callable<Integer>() {
		public Integer call() throws Exception {
			return KQueueEventLoop.super.pendingTasks();
		}
	};
	private volatile int wakenUp;
	private volatile int ioRatio = 50;
	static final long MAX_SCHEDULED_DAYS = 1095L;

	KQueueEventLoop(EventLoopGroup parent, Executor executor, int maxEvents, SelectStrategy strategy, RejectedExecutionHandler rejectedExecutionHandler) {
		super(parent, executor, false, DEFAULT_MAX_PENDING_TASKS, rejectedExecutionHandler);
		this.selectStrategy = ObjectUtil.checkNotNull(strategy, "strategy");
		this.kqueueFd = Native.newKQueue();
		if (maxEvents == 0) {
			this.allowGrowing = true;
			maxEvents = 4096;
		} else {
			this.allowGrowing = false;
		}

		this.changeList = new KQueueEventArray(maxEvents);
		this.eventList = new KQueueEventArray(maxEvents);
		this.jniChannelPointers = new NativeLongArray(4096);
		int result = Native.keventAddUserEvent(this.kqueueFd.intValue(), 0);
		if (result < 0) {
			this.cleanup();
			throw new IllegalStateException("kevent failed to add user event with errno: " + -result);
		}
	}

	void evSet(AbstractKQueueChannel ch, short filter, short flags, int fflags) {
		this.changeList.evSet(ch, filter, flags, fflags);
	}

	void remove(AbstractKQueueChannel ch) throws IOException {
		assert this.inEventLoop();

		if (ch.jniSelfPtr != 0L) {
			this.jniChannelPointers.add(ch.jniSelfPtr);
			ch.jniSelfPtr = 0L;
		}
	}

	IovArray cleanArray() {
		this.iovArray.clear();
		return this.iovArray;
	}

	@Override
	protected void wakeup(boolean inEventLoop) {
		if (!inEventLoop && WAKEN_UP_UPDATER.compareAndSet(this, 0, 1)) {
			this.wakeup();
		}
	}

	private void wakeup() {
		Native.keventTriggerUserEvent(this.kqueueFd.intValue(), 0);
	}

	private int kqueueWait(boolean oldWakeup) throws IOException {
		if (oldWakeup && this.hasTasks()) {
			return this.kqueueWaitNow();
		} else {
			long totalDelay = this.delayNanos(System.nanoTime());
			int delaySeconds = (int)Math.min(totalDelay / 1000000000L, 2147483647L);
			return this.kqueueWait(delaySeconds, (int)Math.min(totalDelay - (long)delaySeconds * 1000000000L, 2147483647L));
		}
	}

	private int kqueueWaitNow() throws IOException {
		return this.kqueueWait(0, 0);
	}

	private int kqueueWait(int timeoutSec, int timeoutNs) throws IOException {
		this.deleteJniChannelPointers();
		int numEvents = Native.keventWait(this.kqueueFd.intValue(), this.changeList, this.eventList, timeoutSec, timeoutNs);
		this.changeList.clear();
		return numEvents;
	}

	private void deleteJniChannelPointers() {
		if (!this.jniChannelPointers.isEmpty()) {
			KQueueEventArray.deleteGlobalRefs(this.jniChannelPointers.memoryAddress(), this.jniChannelPointers.memoryAddressEnd());
			this.jniChannelPointers.clear();
		}
	}

	private void processReady(int ready) {
		for (int i = 0; i < ready; i++) {
			short filter = this.eventList.filter(i);
			short flags = this.eventList.flags(i);
			if (filter != Native.EVFILT_USER && (flags & Native.EV_ERROR) == 0) {
				AbstractKQueueChannel channel = this.eventList.channel(i);
				if (channel == null) {
					logger.warn("events[{}]=[{}, {}] had no channel!", i, this.eventList.fd(i), filter);
				} else {
					AbstractKQueueUnsafe unsafe = (AbstractKQueueUnsafe)channel.unsafe();
					if (filter == Native.EVFILT_WRITE) {
						unsafe.writeReady();
					} else if (filter == Native.EVFILT_READ) {
						unsafe.readReady(this.eventList.data(i));
					} else if (filter == Native.EVFILT_SOCK && (this.eventList.fflags(i) & Native.NOTE_RDHUP) != 0) {
						unsafe.readEOF();
					}

					if ((flags & Native.EV_EOF) != 0) {
						unsafe.readEOF();
					}
				}
			} else {
				assert filter != Native.EVFILT_USER || filter == Native.EVFILT_USER && this.eventList.fd(i) == 0;
			}
		}
	}

	@Override
	protected void run() {
		while (true) {
			try {
				int strategy = this.selectStrategy.calculateStrategy(this.selectNowSupplier, this.hasTasks());
				switch (strategy) {
					case -2:
						continue;
					case -1:
						strategy = this.kqueueWait(WAKEN_UP_UPDATER.getAndSet(this, 0) == 1);
						if (this.wakenUp == 1) {
							this.wakeup();
						}
					default:
						int ioRatio = this.ioRatio;
						if (ioRatio == 100) {
							try {
								if (strategy > 0) {
									this.processReady(strategy);
								}
							} finally {
								this.runAllTasks();
							}
						} else {
							long ioStartTime = System.nanoTime();

							try {
								if (strategy > 0) {
									this.processReady(strategy);
								}
							} finally {
								long ioTime = System.nanoTime() - ioStartTime;
								this.runAllTasks(ioTime * (long)(100 - ioRatio) / (long)ioRatio);
							}
						}

						if (this.allowGrowing && strategy == this.eventList.capacity()) {
							this.eventList.realloc(false);
						}
				}
			} catch (Throwable var21) {
				handleLoopException(var21);
			}

			try {
				if (this.isShuttingDown()) {
					this.closeAll();
					if (this.confirmShutdown()) {
						return;
					}
				}
			} catch (Throwable var18) {
				handleLoopException(var18);
			}
		}
	}

	@Override
	protected Queue<Runnable> newTaskQueue(int maxPendingTasks) {
		return maxPendingTasks == Integer.MAX_VALUE ? PlatformDependent.newMpscQueue() : PlatformDependent.newMpscQueue(maxPendingTasks);
	}

	@Override
	public int pendingTasks() {
		return this.inEventLoop() ? super.pendingTasks() : this.<Integer>submit(this.pendingTasksCallable).syncUninterruptibly().getNow();
	}

	public int getIoRatio() {
		return this.ioRatio;
	}

	public void setIoRatio(int ioRatio) {
		if (ioRatio > 0 && ioRatio <= 100) {
			this.ioRatio = ioRatio;
		} else {
			throw new IllegalArgumentException("ioRatio: " + ioRatio + " (expected: 0 < ioRatio <= 100)");
		}
	}

	@Override
	protected void cleanup() {
		try {
			this.kqueueFd.close();
		} catch (IOException var5) {
			logger.warn("Failed to close the kqueue fd.", (Throwable)var5);
		} finally {
			this.deleteJniChannelPointers();
			this.jniChannelPointers.free();
			this.changeList.free();
			this.eventList.free();
		}
	}

	private void closeAll() {
		try {
			this.kqueueWaitNow();
		} catch (IOException var2) {
		}
	}

	private static void handleLoopException(Throwable t) {
		logger.warn("Unexpected exception in the selector loop.", t);

		try {
			Thread.sleep(1000L);
		} catch (InterruptedException var2) {
		}
	}

	@Override
	protected void validateScheduled(long amount, TimeUnit unit) {
		long days = unit.toDays(amount);
		if (days > 1095L) {
			throw new IllegalArgumentException("days: " + days + " (expected: < " + 1095L + ')');
		}
	}

	static {
		KQueue.ensureAvailability();
	}
}
