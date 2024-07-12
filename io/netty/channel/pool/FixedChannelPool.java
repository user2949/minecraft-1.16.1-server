package io.netty.channel.pool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.ThrowableUtil;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FixedChannelPool extends SimpleChannelPool {
	private static final IllegalStateException FULL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new IllegalStateException("Too many outstanding acquire operations"), FixedChannelPool.class, "acquire0(...)"
	);
	private static final TimeoutException TIMEOUT_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new TimeoutException("Acquire operation took longer then configured maximum time"), FixedChannelPool.class, "<init>(...)"
	);
	static final IllegalStateException POOL_CLOSED_ON_RELEASE_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new IllegalStateException("FixedChannelPool was closed"), FixedChannelPool.class, "release(...)"
	);
	static final IllegalStateException POOL_CLOSED_ON_ACQUIRE_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new IllegalStateException("FixedChannelPool was closed"), FixedChannelPool.class, "acquire0(...)"
	);
	private final EventExecutor executor;
	private final long acquireTimeoutNanos;
	private final Runnable timeoutTask;
	private final Queue<FixedChannelPool.AcquireTask> pendingAcquireQueue = new ArrayDeque();
	private final int maxConnections;
	private final int maxPendingAcquires;
	private int acquiredChannelCount;
	private int pendingAcquireCount;
	private boolean closed;

	public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, int maxConnections) {
		this(bootstrap, handler, maxConnections, Integer.MAX_VALUE);
	}

	public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, int maxConnections, int maxPendingAcquires) {
		this(bootstrap, handler, ChannelHealthChecker.ACTIVE, null, -1L, maxConnections, maxPendingAcquires);
	}

	public FixedChannelPool(
		Bootstrap bootstrap,
		ChannelPoolHandler handler,
		ChannelHealthChecker healthCheck,
		FixedChannelPool.AcquireTimeoutAction action,
		long acquireTimeoutMillis,
		int maxConnections,
		int maxPendingAcquires
	) {
		this(bootstrap, handler, healthCheck, action, acquireTimeoutMillis, maxConnections, maxPendingAcquires, true);
	}

	public FixedChannelPool(
		Bootstrap bootstrap,
		ChannelPoolHandler handler,
		ChannelHealthChecker healthCheck,
		FixedChannelPool.AcquireTimeoutAction action,
		long acquireTimeoutMillis,
		int maxConnections,
		int maxPendingAcquires,
		boolean releaseHealthCheck
	) {
		this(bootstrap, handler, healthCheck, action, acquireTimeoutMillis, maxConnections, maxPendingAcquires, releaseHealthCheck, true);
	}

	public FixedChannelPool(
		Bootstrap bootstrap,
		ChannelPoolHandler handler,
		ChannelHealthChecker healthCheck,
		FixedChannelPool.AcquireTimeoutAction action,
		long acquireTimeoutMillis,
		int maxConnections,
		int maxPendingAcquires,
		boolean releaseHealthCheck,
		boolean lastRecentUsed
	) {
		super(bootstrap, handler, healthCheck, releaseHealthCheck, lastRecentUsed);
		if (maxConnections < 1) {
			throw new IllegalArgumentException("maxConnections: " + maxConnections + " (expected: >= 1)");
		} else if (maxPendingAcquires < 1) {
			throw new IllegalArgumentException("maxPendingAcquires: " + maxPendingAcquires + " (expected: >= 1)");
		} else {
			if (action == null && acquireTimeoutMillis == -1L) {
				this.timeoutTask = null;
				this.acquireTimeoutNanos = -1L;
			} else {
				if (action == null && acquireTimeoutMillis != -1L) {
					throw new NullPointerException("action");
				}

				if (action != null && acquireTimeoutMillis < 0L) {
					throw new IllegalArgumentException("acquireTimeoutMillis: " + acquireTimeoutMillis + " (expected: >= 0)");
				}

				this.acquireTimeoutNanos = TimeUnit.MILLISECONDS.toNanos(acquireTimeoutMillis);
				switch (action) {
					case FAIL:
						this.timeoutTask = new FixedChannelPool.TimeoutTask() {
							@Override
							public void onTimeout(FixedChannelPool.AcquireTask task) {
								task.promise.setFailure(FixedChannelPool.TIMEOUT_EXCEPTION);
							}
						};
						break;
					case NEW:
						this.timeoutTask = new FixedChannelPool.TimeoutTask() {
							@Override
							public void onTimeout(FixedChannelPool.AcquireTask task) {
								task.acquired();
								FixedChannelPool.super.acquire(task.promise);
							}
						};
						break;
					default:
						throw new Error();
				}
			}

			this.executor = bootstrap.config().group().next();
			this.maxConnections = maxConnections;
			this.maxPendingAcquires = maxPendingAcquires;
		}
	}

	@Override
	public Future<Channel> acquire(Promise<Channel> promise) {
		try {
			if (this.executor.inEventLoop()) {
				this.acquire0(promise);
			} else {
				this.executor.execute(new Runnable() {
					public void run() {
						FixedChannelPool.this.acquire0(promise);
					}
				});
			}
		} catch (Throwable var3) {
			promise.setFailure(var3);
		}

		return promise;
	}

	private void acquire0(Promise<Channel> promise) {
		assert this.executor.inEventLoop();

		if (this.closed) {
			promise.setFailure(POOL_CLOSED_ON_ACQUIRE_EXCEPTION);
		} else {
			if (this.acquiredChannelCount < this.maxConnections) {
				assert this.acquiredChannelCount >= 0;

				Promise<Channel> p = this.executor.newPromise();
				FixedChannelPool.AcquireListener l = new FixedChannelPool.AcquireListener(promise);
				l.acquired();
				p.addListener(l);
				super.acquire(p);
			} else {
				if (this.pendingAcquireCount >= this.maxPendingAcquires) {
					promise.setFailure(FULL_EXCEPTION);
				} else {
					FixedChannelPool.AcquireTask task = new FixedChannelPool.AcquireTask(promise);
					if (this.pendingAcquireQueue.offer(task)) {
						this.pendingAcquireCount++;
						if (this.timeoutTask != null) {
							task.timeoutFuture = this.executor.schedule(this.timeoutTask, this.acquireTimeoutNanos, TimeUnit.NANOSECONDS);
						}
					} else {
						promise.setFailure(FULL_EXCEPTION);
					}
				}

				assert this.pendingAcquireCount > 0;
			}
		}
	}

	@Override
	public Future<Void> release(Channel channel, Promise<Void> promise) {
		ObjectUtil.checkNotNull(promise, "promise");
		Promise<Void> p = this.executor.newPromise();
		super.release(channel, p.addListener(new FutureListener<Void>() {
			@Override
			public void operationComplete(Future<Void> future) throws Exception {
				assert FixedChannelPool.this.executor.inEventLoop();

				if (FixedChannelPool.this.closed) {
					channel.close();
					promise.setFailure(FixedChannelPool.POOL_CLOSED_ON_RELEASE_EXCEPTION);
				} else {
					if (future.isSuccess()) {
						FixedChannelPool.this.decrementAndRunTaskQueue();
						promise.setSuccess(null);
					} else {
						Throwable cause = future.cause();
						if (!(cause instanceof IllegalArgumentException)) {
							FixedChannelPool.this.decrementAndRunTaskQueue();
						}

						promise.setFailure(future.cause());
					}
				}
			}
		}));
		return promise;
	}

	private void decrementAndRunTaskQueue() {
		this.acquiredChannelCount--;

		assert this.acquiredChannelCount >= 0;

		this.runTaskQueue();
	}

	private void runTaskQueue() {
		while (this.acquiredChannelCount < this.maxConnections) {
			FixedChannelPool.AcquireTask task = (FixedChannelPool.AcquireTask)this.pendingAcquireQueue.poll();
			if (task != null) {
				ScheduledFuture<?> timeoutFuture = task.timeoutFuture;
				if (timeoutFuture != null) {
					timeoutFuture.cancel(false);
				}

				this.pendingAcquireCount--;
				task.acquired();
				super.acquire(task.promise);
				continue;
			}
			break;
		}

		assert this.pendingAcquireCount >= 0;

		assert this.acquiredChannelCount >= 0;
	}

	@Override
	public void close() {
		this.executor.execute(new Runnable() {
			public void run() {
				if (!FixedChannelPool.this.closed) {
					FixedChannelPool.this.closed = true;

					while (true) {
						FixedChannelPool.AcquireTask task = (FixedChannelPool.AcquireTask)FixedChannelPool.this.pendingAcquireQueue.poll();
						if (task == null) {
							FixedChannelPool.this.acquiredChannelCount = 0;
							FixedChannelPool.this.pendingAcquireCount = 0;
							FixedChannelPool.super.close();
							break;
						}

						ScheduledFuture<?> f = task.timeoutFuture;
						if (f != null) {
							f.cancel(false);
						}

						task.promise.setFailure(new ClosedChannelException());
					}
				}
			}
		});
	}

	private class AcquireListener implements FutureListener<Channel> {
		private final Promise<Channel> originalPromise;
		protected boolean acquired;

		AcquireListener(Promise<Channel> originalPromise) {
			this.originalPromise = originalPromise;
		}

		@Override
		public void operationComplete(Future<Channel> future) throws Exception {
			assert FixedChannelPool.this.executor.inEventLoop();

			if (FixedChannelPool.this.closed) {
				if (future.isSuccess()) {
					future.getNow().close();
				}

				this.originalPromise.setFailure(FixedChannelPool.POOL_CLOSED_ON_ACQUIRE_EXCEPTION);
			} else {
				if (future.isSuccess()) {
					this.originalPromise.setSuccess(future.getNow());
				} else {
					if (this.acquired) {
						FixedChannelPool.this.decrementAndRunTaskQueue();
					} else {
						FixedChannelPool.this.runTaskQueue();
					}

					this.originalPromise.setFailure(future.cause());
				}
			}
		}

		public void acquired() {
			if (!this.acquired) {
				FixedChannelPool.this.acquiredChannelCount++;
				this.acquired = true;
			}
		}
	}

	private final class AcquireTask extends FixedChannelPool.AcquireListener {
		final Promise<Channel> promise;
		final long expireNanoTime = System.nanoTime() + FixedChannelPool.this.acquireTimeoutNanos;
		ScheduledFuture<?> timeoutFuture;

		public AcquireTask(Promise<Channel> promise) {
			super(promise);
			this.promise = FixedChannelPool.this.executor.<Channel>newPromise().addListener(this);
		}
	}

	public static enum AcquireTimeoutAction {
		NEW,
		FAIL;
	}

	private abstract class TimeoutTask implements Runnable {
		private TimeoutTask() {
		}

		public final void run() {
			assert FixedChannelPool.this.executor.inEventLoop();

			long nanoTime = System.nanoTime();

			while (true) {
				FixedChannelPool.AcquireTask task = (FixedChannelPool.AcquireTask)FixedChannelPool.this.pendingAcquireQueue.peek();
				if (task == null || nanoTime - task.expireNanoTime < 0L) {
					return;
				}

				FixedChannelPool.this.pendingAcquireQueue.remove();
				--FixedChannelPool.this.pendingAcquireCount;
				this.onTimeout(task);
			}
		}

		public abstract void onTimeout(FixedChannelPool.AcquireTask acquireTask);
	}
}
