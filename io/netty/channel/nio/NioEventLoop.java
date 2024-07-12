package io.netty.channel.nio;

import io.netty.channel.ChannelException;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopException;
import io.netty.channel.SelectStrategy;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.channel.nio.AbstractNioChannel.NioUnsafe;
import io.netty.util.IntSupplier;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ReflectionUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class NioEventLoop extends SingleThreadEventLoop {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioEventLoop.class);
	private static final int CLEANUP_INTERVAL = 256;
	private static final boolean DISABLE_KEYSET_OPTIMIZATION = SystemPropertyUtil.getBoolean("io.netty.noKeySetOptimization", false);
	private static final int MIN_PREMATURE_SELECTOR_RETURNS = 3;
	private static final int SELECTOR_AUTO_REBUILD_THRESHOLD;
	private final IntSupplier selectNowSupplier = new IntSupplier() {
		@Override
		public int get() throws Exception {
			return NioEventLoop.this.selectNow();
		}
	};
	private final Callable<Integer> pendingTasksCallable = new Callable<Integer>() {
		public Integer call() throws Exception {
			return NioEventLoop.super.pendingTasks();
		}
	};
	static final long MAX_SCHEDULED_DAYS = 1095L;
	private Selector selector;
	private Selector unwrappedSelector;
	private SelectedSelectionKeySet selectedKeys;
	private final SelectorProvider provider;
	private final AtomicBoolean wakenUp = new AtomicBoolean();
	private final SelectStrategy selectStrategy;
	private volatile int ioRatio = 50;
	private int cancelledKeys;
	private boolean needsToSelectAgain;

	NioEventLoop(
		NioEventLoopGroup parent, Executor executor, SelectorProvider selectorProvider, SelectStrategy strategy, RejectedExecutionHandler rejectedExecutionHandler
	) {
		super(parent, executor, false, DEFAULT_MAX_PENDING_TASKS, rejectedExecutionHandler);
		if (selectorProvider == null) {
			throw new NullPointerException("selectorProvider");
		} else if (strategy == null) {
			throw new NullPointerException("selectStrategy");
		} else {
			this.provider = selectorProvider;
			NioEventLoop.SelectorTuple selectorTuple = this.openSelector();
			this.selector = selectorTuple.selector;
			this.unwrappedSelector = selectorTuple.unwrappedSelector;
			this.selectStrategy = strategy;
		}
	}

	private NioEventLoop.SelectorTuple openSelector() {
		final Selector unwrappedSelector;
		try {
			unwrappedSelector = this.provider.openSelector();
		} catch (IOException var7) {
			throw new ChannelException("failed to open a new selector", var7);
		}

		if (DISABLE_KEYSET_OPTIMIZATION) {
			return new NioEventLoop.SelectorTuple(unwrappedSelector);
		} else {
			final SelectedSelectionKeySet selectedKeySet = new SelectedSelectionKeySet();
			Object maybeSelectorImplClass = AccessController.doPrivileged(new PrivilegedAction<Object>() {
				public Object run() {
					try {
						return Class.forName("sun.nio.ch.SelectorImpl", false, PlatformDependent.getSystemClassLoader());
					} catch (Throwable var2) {
						return var2;
					}
				}
			});
			if (maybeSelectorImplClass instanceof Class && ((Class)maybeSelectorImplClass).isAssignableFrom(unwrappedSelector.getClass())) {
				final Class<?> selectorImplClass = (Class<?>)maybeSelectorImplClass;
				Object maybeException = AccessController.doPrivileged(new PrivilegedAction<Object>() {
					public Object run() {
						try {
							Field selectedKeysField = selectorImplClass.getDeclaredField("selectedKeys");
							Field publicSelectedKeysField = selectorImplClass.getDeclaredField("publicSelectedKeys");
							Throwable cause = ReflectionUtil.trySetAccessible(selectedKeysField, true);
							if (cause != null) {
								return cause;
							} else {
								cause = ReflectionUtil.trySetAccessible(publicSelectedKeysField, true);
								if (cause != null) {
									return cause;
								} else {
									selectedKeysField.set(unwrappedSelector, selectedKeySet);
									publicSelectedKeysField.set(unwrappedSelector, selectedKeySet);
									return null;
								}
							}
						} catch (NoSuchFieldException var4) {
							return var4;
						} catch (IllegalAccessException var5) {
							return var5;
						}
					}
				});
				if (maybeException instanceof Exception) {
					this.selectedKeys = null;
					Exception e = (Exception)maybeException;
					logger.trace("failed to instrument a special java.util.Set into: {}", unwrappedSelector, e);
					return new NioEventLoop.SelectorTuple(unwrappedSelector);
				} else {
					this.selectedKeys = selectedKeySet;
					logger.trace("instrumented a special java.util.Set into: {}", unwrappedSelector);
					return new NioEventLoop.SelectorTuple(unwrappedSelector, new SelectedSelectionKeySetSelector(unwrappedSelector, selectedKeySet));
				}
			} else {
				if (maybeSelectorImplClass instanceof Throwable) {
					Throwable t = (Throwable)maybeSelectorImplClass;
					logger.trace("failed to instrument a special java.util.Set into: {}", unwrappedSelector, t);
				}

				return new NioEventLoop.SelectorTuple(unwrappedSelector);
			}
		}
	}

	public SelectorProvider selectorProvider() {
		return this.provider;
	}

	@Override
	protected Queue<Runnable> newTaskQueue(int maxPendingTasks) {
		return maxPendingTasks == Integer.MAX_VALUE ? PlatformDependent.newMpscQueue() : PlatformDependent.newMpscQueue(maxPendingTasks);
	}

	@Override
	public int pendingTasks() {
		return this.inEventLoop() ? super.pendingTasks() : this.<Integer>submit(this.pendingTasksCallable).syncUninterruptibly().getNow();
	}

	public void register(SelectableChannel ch, int interestOps, NioTask<?> task) {
		if (ch == null) {
			throw new NullPointerException("ch");
		} else if (interestOps == 0) {
			throw new IllegalArgumentException("interestOps must be non-zero.");
		} else if ((interestOps & ~ch.validOps()) != 0) {
			throw new IllegalArgumentException("invalid interestOps: " + interestOps + "(validOps: " + ch.validOps() + ')');
		} else if (task == null) {
			throw new NullPointerException("task");
		} else if (this.isShutdown()) {
			throw new IllegalStateException("event loop shut down");
		} else {
			try {
				ch.register(this.selector, interestOps, task);
			} catch (Exception var5) {
				throw new EventLoopException("failed to register a channel", var5);
			}
		}
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

	public void rebuildSelector() {
		if (!this.inEventLoop()) {
			this.execute(new Runnable() {
				public void run() {
					NioEventLoop.this.rebuildSelector0();
				}
			});
		} else {
			this.rebuildSelector0();
		}
	}

	private void rebuildSelector0() {
		Selector oldSelector = this.selector;
		if (oldSelector != null) {
			NioEventLoop.SelectorTuple newSelectorTuple;
			try {
				newSelectorTuple = this.openSelector();
			} catch (Exception var9) {
				logger.warn("Failed to create a new Selector.", (Throwable)var9);
				return;
			}

			int nChannels = 0;

			for (SelectionKey key : oldSelector.keys()) {
				Object a = key.attachment();

				try {
					if (key.isValid() && key.channel().keyFor(newSelectorTuple.unwrappedSelector) == null) {
						int interestOps = key.interestOps();
						key.cancel();
						SelectionKey newKey = key.channel().register(newSelectorTuple.unwrappedSelector, interestOps, a);
						if (a instanceof AbstractNioChannel) {
							((AbstractNioChannel)a).selectionKey = newKey;
						}

						nChannels++;
					}
				} catch (Exception var11) {
					logger.warn("Failed to re-register a Channel to the new Selector.", (Throwable)var11);
					if (a instanceof AbstractNioChannel) {
						AbstractNioChannel ch = (AbstractNioChannel)a;
						ch.unsafe().close(ch.unsafe().voidPromise());
					} else {
						NioTask<SelectableChannel> task = (NioTask<SelectableChannel>)a;
						invokeChannelUnregistered(task, key, var11);
					}
				}
			}

			this.selector = newSelectorTuple.selector;
			this.unwrappedSelector = newSelectorTuple.unwrappedSelector;

			try {
				oldSelector.close();
			} catch (Throwable var10) {
				if (logger.isWarnEnabled()) {
					logger.warn("Failed to close the old Selector.", var10);
				}
			}

			logger.info("Migrated " + nChannels + " channel(s) to the new Selector.");
		}
	}

	@Override
	protected void run() {
		while (true) {
			try {
				switch (this.selectStrategy.calculateStrategy(this.selectNowSupplier, this.hasTasks())) {
					case -2:
						continue;
					case -1:
						this.select(this.wakenUp.getAndSet(false));
						if (this.wakenUp.get()) {
							this.selector.wakeup();
						}
					default:
						this.cancelledKeys = 0;
						this.needsToSelectAgain = false;
						int ioRatio = this.ioRatio;
						if (ioRatio == 100) {
							try {
								this.processSelectedKeys();
							} finally {
								this.runAllTasks();
							}
						} else {
							long ioStartTime = System.nanoTime();

							try {
								this.processSelectedKeys();
							} finally {
								long ioTime = System.nanoTime() - ioStartTime;
								this.runAllTasks(ioTime * (long)(100 - ioRatio) / (long)ioRatio);
							}
						}
				}
			} catch (Throwable var20) {
				handleLoopException(var20);
			}

			try {
				if (this.isShuttingDown()) {
					this.closeAll();
					if (this.confirmShutdown()) {
						return;
					}
				}
			} catch (Throwable var17) {
				handleLoopException(var17);
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

	private void processSelectedKeys() {
		if (this.selectedKeys != null) {
			this.processSelectedKeysOptimized();
		} else {
			this.processSelectedKeysPlain(this.selector.selectedKeys());
		}
	}

	@Override
	protected void cleanup() {
		try {
			this.selector.close();
		} catch (IOException var2) {
			logger.warn("Failed to close a selector.", (Throwable)var2);
		}
	}

	void cancel(SelectionKey key) {
		key.cancel();
		this.cancelledKeys++;
		if (this.cancelledKeys >= 256) {
			this.cancelledKeys = 0;
			this.needsToSelectAgain = true;
		}
	}

	@Override
	protected Runnable pollTask() {
		Runnable task = super.pollTask();
		if (this.needsToSelectAgain) {
			this.selectAgain();
		}

		return task;
	}

	private void processSelectedKeysPlain(Set<SelectionKey> selectedKeys) {
		if (!selectedKeys.isEmpty()) {
			Iterator<SelectionKey> i = selectedKeys.iterator();

			while (true) {
				SelectionKey k = (SelectionKey)i.next();
				Object a = k.attachment();
				i.remove();
				if (a instanceof AbstractNioChannel) {
					this.processSelectedKey(k, (AbstractNioChannel)a);
				} else {
					NioTask<SelectableChannel> task = (NioTask<SelectableChannel>)a;
					processSelectedKey(k, task);
				}

				if (!i.hasNext()) {
					break;
				}

				if (this.needsToSelectAgain) {
					this.selectAgain();
					selectedKeys = this.selector.selectedKeys();
					if (selectedKeys.isEmpty()) {
						break;
					}

					i = selectedKeys.iterator();
				}
			}
		}
	}

	private void processSelectedKeysOptimized() {
		for (int i = 0; i < this.selectedKeys.size; i++) {
			SelectionKey k = this.selectedKeys.keys[i];
			this.selectedKeys.keys[i] = null;
			Object a = k.attachment();
			if (a instanceof AbstractNioChannel) {
				this.processSelectedKey(k, (AbstractNioChannel)a);
			} else {
				NioTask<SelectableChannel> task = (NioTask<SelectableChannel>)a;
				processSelectedKey(k, task);
			}

			if (this.needsToSelectAgain) {
				this.selectedKeys.reset(i + 1);
				this.selectAgain();
				i = -1;
			}
		}
	}

	private void processSelectedKey(SelectionKey k, AbstractNioChannel ch) {
		NioUnsafe unsafe = ch.unsafe();
		if (!k.isValid()) {
			EventLoop eventLoop;
			try {
				eventLoop = ch.eventLoop();
			} catch (Throwable var6) {
				return;
			}

			if (eventLoop == this && eventLoop != null) {
				unsafe.close(unsafe.voidPromise());
			}
		} else {
			try {
				int readyOps = k.readyOps();
				if ((readyOps & 8) != 0) {
					int ops = k.interestOps();
					ops &= -9;
					k.interestOps(ops);
					unsafe.finishConnect();
				}

				if ((readyOps & 4) != 0) {
					ch.unsafe().forceFlush();
				}

				if ((readyOps & 17) != 0 || readyOps == 0) {
					unsafe.read();
				}
			} catch (CancelledKeyException var7) {
				unsafe.close(unsafe.voidPromise());
			}
		}
	}

	private static void processSelectedKey(SelectionKey k, NioTask<SelectableChannel> task) {
		int state = 0;

		try {
			task.channelReady(k.channel(), k);
			state = 1;
		} catch (Exception var7) {
			k.cancel();
			invokeChannelUnregistered(task, k, var7);
			state = 2;
		} finally {
			switch (state) {
				case 0:
					k.cancel();
					invokeChannelUnregistered(task, k, null);
					break;
				case 1:
					if (!k.isValid()) {
						invokeChannelUnregistered(task, k, null);
					}
			}
		}
	}

	private void closeAll() {
		this.selectAgain();
		Set<SelectionKey> keys = this.selector.keys();
		Collection<AbstractNioChannel> channels = new ArrayList(keys.size());

		for (SelectionKey k : keys) {
			Object a = k.attachment();
			if (a instanceof AbstractNioChannel) {
				channels.add((AbstractNioChannel)a);
			} else {
				k.cancel();
				NioTask<SelectableChannel> task = (NioTask<SelectableChannel>)a;
				invokeChannelUnregistered(task, k, null);
			}
		}

		for (AbstractNioChannel ch : channels) {
			ch.unsafe().close(ch.unsafe().voidPromise());
		}
	}

	private static void invokeChannelUnregistered(NioTask<SelectableChannel> task, SelectionKey k, Throwable cause) {
		try {
			task.channelUnregistered(k.channel(), cause);
		} catch (Exception var4) {
			logger.warn("Unexpected exception while running NioTask.channelUnregistered()", (Throwable)var4);
		}
	}

	@Override
	protected void wakeup(boolean inEventLoop) {
		if (!inEventLoop && this.wakenUp.compareAndSet(false, true)) {
			this.selector.wakeup();
		}
	}

	Selector unwrappedSelector() {
		return this.unwrappedSelector;
	}

	int selectNow() throws IOException {
		int var1;
		try {
			var1 = this.selector.selectNow();
		} finally {
			if (this.wakenUp.get()) {
				this.selector.wakeup();
			}
		}

		return var1;
	}

	private void select(boolean oldWakenUp) throws IOException {
		Selector selector = this.selector;

		try {
			int selectCnt = 0;
			long currentTimeNanos = System.nanoTime();
			long selectDeadLineNanos = currentTimeNanos + this.delayNanos(currentTimeNanos);

			while (true) {
				long timeoutMillis = (selectDeadLineNanos - currentTimeNanos + 500000L) / 1000000L;
				if (timeoutMillis <= 0L) {
					if (selectCnt == 0) {
						selector.selectNow();
						selectCnt = 1;
					}
					break;
				}

				if (this.hasTasks() && this.wakenUp.compareAndSet(false, true)) {
					selector.selectNow();
					selectCnt = 1;
					break;
				}

				int selectedKeys = selector.select(timeoutMillis);
				selectCnt++;
				if (selectedKeys != 0 || oldWakenUp || this.wakenUp.get() || this.hasTasks() || this.hasScheduledTasks()) {
					break;
				}

				if (Thread.interrupted()) {
					if (logger.isDebugEnabled()) {
						logger.debug(
							"Selector.select() returned prematurely because Thread.currentThread().interrupt() was called. Use NioEventLoop.shutdownGracefully() to shutdown the NioEventLoop."
						);
					}

					selectCnt = 1;
					break;
				}

				long time = System.nanoTime();
				if (time - TimeUnit.MILLISECONDS.toNanos(timeoutMillis) >= currentTimeNanos) {
					selectCnt = 1;
				} else if (SELECTOR_AUTO_REBUILD_THRESHOLD > 0 && selectCnt >= SELECTOR_AUTO_REBUILD_THRESHOLD) {
					logger.warn("Selector.select() returned prematurely {} times in a row; rebuilding Selector {}.", selectCnt, selector);
					this.rebuildSelector();
					selector = this.selector;
					selector.selectNow();
					selectCnt = 1;
					break;
				}

				currentTimeNanos = time;
			}

			if (selectCnt > 3 && logger.isDebugEnabled()) {
				logger.debug("Selector.select() returned prematurely {} times in a row for Selector {}.", selectCnt - 1, selector);
			}
		} catch (CancelledKeyException var13) {
			if (logger.isDebugEnabled()) {
				logger.debug(CancelledKeyException.class.getSimpleName() + " raised by a Selector {} - JDK bug?", selector, var13);
			}
		}
	}

	private void selectAgain() {
		this.needsToSelectAgain = false;

		try {
			this.selector.selectNow();
		} catch (Throwable var2) {
			logger.warn("Failed to update SelectionKeys.", var2);
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
		String key = "sun.nio.ch.bugLevel";
		String buglevel = SystemPropertyUtil.get("sun.nio.ch.bugLevel");
		if (buglevel == null) {
			try {
				AccessController.doPrivileged(new PrivilegedAction<Void>() {
					public Void run() {
						System.setProperty("sun.nio.ch.bugLevel", "");
						return null;
					}
				});
			} catch (SecurityException var3) {
				logger.debug("Unable to get/set System Property: sun.nio.ch.bugLevel", (Throwable)var3);
			}
		}

		int selectorAutoRebuildThreshold = SystemPropertyUtil.getInt("io.netty.selectorAutoRebuildThreshold", 512);
		if (selectorAutoRebuildThreshold < 3) {
			selectorAutoRebuildThreshold = 0;
		}

		SELECTOR_AUTO_REBUILD_THRESHOLD = selectorAutoRebuildThreshold;
		if (logger.isDebugEnabled()) {
			logger.debug("-Dio.netty.noKeySetOptimization: {}", DISABLE_KEYSET_OPTIMIZATION);
			logger.debug("-Dio.netty.selectorAutoRebuildThreshold: {}", SELECTOR_AUTO_REBUILD_THRESHOLD);
		}
	}

	private static final class SelectorTuple {
		final Selector unwrappedSelector;
		final Selector selector;

		SelectorTuple(Selector unwrappedSelector) {
			this.unwrappedSelector = unwrappedSelector;
			this.selector = unwrappedSelector;
		}

		SelectorTuple(Selector unwrappedSelector, Selector selector) {
			this.unwrappedSelector = unwrappedSelector;
			this.selector = selector;
		}
	}
}
