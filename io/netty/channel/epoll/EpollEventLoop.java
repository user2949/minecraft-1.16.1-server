package io.netty.channel.epoll;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.SelectStrategy;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.channel.epoll.AbstractEpollChannel.AbstractEpollUnsafe;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.IovArray;
import io.netty.util.IntSupplier;
import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

final class EpollEventLoop extends SingleThreadEventLoop {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(EpollEventLoop.class);
	private static final AtomicIntegerFieldUpdater<EpollEventLoop> WAKEN_UP_UPDATER = AtomicIntegerFieldUpdater.newUpdater(EpollEventLoop.class, "wakenUp");
	private final FileDescriptor epollFd;
	private final FileDescriptor eventFd;
	private final FileDescriptor timerFd;
	private final IntObjectMap<AbstractEpollChannel> channels = new IntObjectHashMap<>(4096);
	private final boolean allowGrowing;
	private final EpollEventArray events;
	private final IovArray iovArray = new IovArray();
	private final SelectStrategy selectStrategy;
	private final IntSupplier selectNowSupplier = new IntSupplier() {
		@Override
		public int get() throws Exception {
			return EpollEventLoop.this.epollWaitNow();
		}
	};
	private final Callable<Integer> pendingTasksCallable = new Callable<Integer>() {
		public Integer call() throws Exception {
			return EpollEventLoop.super.pendingTasks();
		}
	};
	private volatile int wakenUp;
	private volatile int ioRatio = 50;
	static final long MAX_SCHEDULED_DAYS = TimeUnit.SECONDS.toDays(999999999L);

	EpollEventLoop(EventLoopGroup parent, Executor executor, int maxEvents, SelectStrategy strategy, RejectedExecutionHandler rejectedExecutionHandler) {
		super(parent, executor, false, DEFAULT_MAX_PENDING_TASKS, rejectedExecutionHandler);
		this.selectStrategy = ObjectUtil.checkNotNull(strategy, "strategy");
		if (maxEvents == 0) {
			this.allowGrowing = true;
			this.events = new EpollEventArray(4096);
		} else {
			this.allowGrowing = false;
			this.events = new EpollEventArray(maxEvents);
		}

		boolean success = false;
		FileDescriptor epollFd = null;
		FileDescriptor eventFd = null;
		FileDescriptor timerFd = null;

		try {
			this.epollFd = epollFd = Native.newEpollCreate();
			this.eventFd = eventFd = Native.newEventFd();

			try {
				Native.epollCtlAdd(epollFd.intValue(), eventFd.intValue(), Native.EPOLLIN);
			} catch (IOException var26) {
				throw new IllegalStateException("Unable to add eventFd filedescriptor to epoll", var26);
			}

			this.timerFd = timerFd = Native.newTimerFd();

			try {
				Native.epollCtlAdd(epollFd.intValue(), timerFd.intValue(), Native.EPOLLIN | Native.EPOLLET);
			} catch (IOException var25) {
				throw new IllegalStateException("Unable to add timerFd filedescriptor to epoll", var25);
			}

			success = true;
		} finally {
			if (!success) {
				if (epollFd != null) {
					try {
						epollFd.close();
					} catch (Exception var24) {
					}
				}

				if (eventFd != null) {
					try {
						eventFd.close();
					} catch (Exception var23) {
					}
				}

				if (timerFd != null) {
					try {
						timerFd.close();
					} catch (Exception var22) {
					}
				}
			}
		}
	}

	IovArray cleanArray() {
		this.iovArray.clear();
		return this.iovArray;
	}

	@Override
	protected void wakeup(boolean inEventLoop) {
		if (!inEventLoop && WAKEN_UP_UPDATER.compareAndSet(this, 0, 1)) {
			Native.eventFdWrite(this.eventFd.intValue(), 1L);
		}
	}

	void add(AbstractEpollChannel ch) throws IOException {
		assert this.inEventLoop();

		int fd = ch.socket.intValue();
		Native.epollCtlAdd(this.epollFd.intValue(), fd, ch.flags);
		this.channels.put(fd, ch);
	}

	void modify(AbstractEpollChannel ch) throws IOException {
		assert this.inEventLoop();

		Native.epollCtlMod(this.epollFd.intValue(), ch.socket.intValue(), ch.flags);
	}

	void remove(AbstractEpollChannel ch) throws IOException {
		assert this.inEventLoop();

		if (ch.isOpen()) {
			int fd = ch.socket.intValue();
			if (this.channels.remove(fd) != null) {
				Native.epollCtlDel(this.epollFd.intValue(), ch.fd().intValue());
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

	private int epollWait(boolean oldWakeup) throws IOException {
		if (oldWakeup && this.hasTasks()) {
			return this.epollWaitNow();
		} else {
			long totalDelay = this.delayNanos(System.nanoTime());
			int delaySeconds = (int)Math.min(totalDelay / 1000000000L, 2147483647L);
			return Native.epollWait(this.epollFd, this.events, this.timerFd, delaySeconds, (int)Math.min(totalDelay - (long)delaySeconds * 1000000000L, 2147483647L));
		}
	}

	private int epollWaitNow() throws IOException {
		return Native.epollWait(this.epollFd, this.events, this.timerFd, 0, 0);
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
						strategy = this.epollWait(WAKEN_UP_UPDATER.getAndSet(this, 0) == 1);
						if (this.wakenUp == 1) {
							Native.eventFdWrite(this.eventFd.intValue(), 1L);
						}
					default:
						int ioRatio = this.ioRatio;
						if (ioRatio == 100) {
							try {
								if (strategy > 0) {
									this.processReady(this.events, strategy);
								}
							} finally {
								this.runAllTasks();
							}
						} else {
							long ioStartTime = System.nanoTime();

							try {
								if (strategy > 0) {
									this.processReady(this.events, strategy);
								}
							} finally {
								long ioTime = System.nanoTime() - ioStartTime;
								this.runAllTasks(ioTime * (long)(100 - ioRatio) / (long)ioRatio);
							}
						}

						if (this.allowGrowing && strategy == this.events.length()) {
							this.events.increase();
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

	private static void handleLoopException(Throwable t) {
		logger.warn("Unexpected exception in the selector loop.", t);

		try {
			Thread.sleep(1000L);
		} catch (InterruptedException var2) {
		}
	}

	private void closeAll() {
		try {
			this.epollWaitNow();
		} catch (IOException var4) {
		}

		Collection<AbstractEpollChannel> array = new ArrayList(this.channels.size());

		for (AbstractEpollChannel channel : this.channels.values()) {
			array.add(channel);
		}

		for (AbstractEpollChannel ch : array) {
			ch.unsafe().close(ch.unsafe().voidPromise());
		}
	}

	private void processReady(EpollEventArray events, int ready) {
		for (int i = 0; i < ready; i++) {
			int fd = events.fd(i);
			if (fd == this.eventFd.intValue()) {
				Native.eventFdRead(fd);
			} else if (fd == this.timerFd.intValue()) {
				Native.timerFdRead(fd);
			} else {
				long ev = (long)events.events(i);
				AbstractEpollChannel ch = this.channels.get(fd);
				if (ch != null) {
					AbstractEpollUnsafe unsafe = (AbstractEpollUnsafe)ch.unsafe();
					if ((ev & (long)(Native.EPOLLERR | Native.EPOLLOUT)) != 0L) {
						unsafe.epollOutReady();
					}

					if ((ev & (long)(Native.EPOLLERR | Native.EPOLLIN)) != 0L) {
						unsafe.epollInReady();
					}

					if ((ev & (long)Native.EPOLLRDHUP) != 0L) {
						unsafe.epollRdHupReady();
					}
				} else {
					try {
						Native.epollCtlDel(this.epollFd.intValue(), fd);
					} catch (IOException var9) {
					}
				}
			}
		}
	}

	@Override
	protected void cleanup() {
		try {
			try {
				this.epollFd.close();
			} catch (IOException var9) {
				logger.warn("Failed to close the epoll fd.", (Throwable)var9);
			}

			try {
				this.eventFd.close();
			} catch (IOException var8) {
				logger.warn("Failed to close the event fd.", (Throwable)var8);
			}

			try {
				this.timerFd.close();
			} catch (IOException var7) {
				logger.warn("Failed to close the timer fd.", (Throwable)var7);
			}
		} finally {
			this.iovArray.release();
			this.events.free();
		}
	}

	@Override
	protected void validateScheduled(long amount, TimeUnit unit) {
		long days = unit.toDays(amount);
		if (days > MAX_SCHEDULED_DAYS) {
			throw new IllegalArgumentException("days: " + days + " (expected: < " + MAX_SCHEDULED_DAYS + ')');
		}
	}

	static {
		Epoll.ensureAvailability();
	}
}
