package io.netty.util.concurrent;

import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class DefaultPromise<V> extends AbstractFuture<V> implements Promise<V> {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultPromise.class);
	private static final InternalLogger rejectedExecutionLogger = InternalLoggerFactory.getInstance(DefaultPromise.class.getName() + ".rejectedExecution");
	private static final int MAX_LISTENER_STACK_DEPTH = Math.min(8, SystemPropertyUtil.getInt("io.netty.defaultPromise.maxListenerStackDepth", 8));
	private static final AtomicReferenceFieldUpdater<DefaultPromise, Object> RESULT_UPDATER = AtomicReferenceFieldUpdater.newUpdater(
		DefaultPromise.class, Object.class, "result"
	);
	private static final Object SUCCESS = new Object();
	private static final Object UNCANCELLABLE = new Object();
	private static final DefaultPromise.CauseHolder CANCELLATION_CAUSE_HOLDER = new DefaultPromise.CauseHolder(
		ThrowableUtil.unknownStackTrace(new CancellationException(), DefaultPromise.class, "cancel(...)")
	);
	private volatile Object result;
	private final EventExecutor executor;
	private Object listeners;
	private short waiters;
	private boolean notifyingListeners;

	public DefaultPromise(EventExecutor executor) {
		this.executor = ObjectUtil.checkNotNull(executor, "executor");
	}

	protected DefaultPromise() {
		this.executor = null;
	}

	@Override
	public Promise<V> setSuccess(V result) {
		if (this.setSuccess0(result)) {
			this.notifyListeners();
			return this;
		} else {
			throw new IllegalStateException("complete already: " + this);
		}
	}

	@Override
	public boolean trySuccess(V result) {
		if (this.setSuccess0(result)) {
			this.notifyListeners();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Promise<V> setFailure(Throwable cause) {
		if (this.setFailure0(cause)) {
			this.notifyListeners();
			return this;
		} else {
			throw new IllegalStateException("complete already: " + this, cause);
		}
	}

	@Override
	public boolean tryFailure(Throwable cause) {
		if (this.setFailure0(cause)) {
			this.notifyListeners();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean setUncancellable() {
		if (RESULT_UPDATER.compareAndSet(this, null, UNCANCELLABLE)) {
			return true;
		} else {
			Object result = this.result;
			return !isDone0(result) || !isCancelled0(result);
		}
	}

	@Override
	public boolean isSuccess() {
		Object result = this.result;
		return result != null && result != UNCANCELLABLE && !(result instanceof DefaultPromise.CauseHolder);
	}

	@Override
	public boolean isCancellable() {
		return this.result == null;
	}

	@Override
	public Throwable cause() {
		Object result = this.result;
		return result instanceof DefaultPromise.CauseHolder ? ((DefaultPromise.CauseHolder)result).cause : null;
	}

	@Override
	public Promise<V> addListener(GenericFutureListener<? extends Future<? super V>> listener) {
		ObjectUtil.checkNotNull(listener, "listener");
		synchronized (this) {
			this.addListener0(listener);
		}

		if (this.isDone()) {
			this.notifyListeners();
		}

		return this;
	}

	@Override
	public Promise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... listeners) {
		ObjectUtil.checkNotNull(listeners, "listeners");
		synchronized (this) {
			for (GenericFutureListener<? extends Future<? super V>> listener : listeners) {
				if (listener == null) {
					break;
				}

				this.addListener0(listener);
			}
		}

		if (this.isDone()) {
			this.notifyListeners();
		}

		return this;
	}

	@Override
	public Promise<V> removeListener(GenericFutureListener<? extends Future<? super V>> listener) {
		ObjectUtil.checkNotNull(listener, "listener");
		synchronized (this) {
			this.removeListener0(listener);
			return this;
		}
	}

	@Override
	public Promise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... listeners) {
		ObjectUtil.checkNotNull(listeners, "listeners");
		synchronized (this) {
			for (GenericFutureListener<? extends Future<? super V>> listener : listeners) {
				if (listener == null) {
					break;
				}

				this.removeListener0(listener);
			}

			return this;
		}
	}

	@Override
	public Promise<V> await() throws InterruptedException {
		if (this.isDone()) {
			return this;
		} else if (Thread.interrupted()) {
			throw new InterruptedException(this.toString());
		} else {
			this.checkDeadLock();
			synchronized (this) {
				while (!this.isDone()) {
					this.incWaiters();

					try {
						this.wait();
					} finally {
						this.decWaiters();
					}
				}

				return this;
			}
		}
	}

	@Override
	public Promise<V> awaitUninterruptibly() {
		if (this.isDone()) {
			return this;
		} else {
			this.checkDeadLock();
			boolean interrupted = false;
			synchronized (this) {
				while (!this.isDone()) {
					this.incWaiters();

					try {
						this.wait();
					} catch (InterruptedException var9) {
						interrupted = true;
					} finally {
						this.decWaiters();
					}
				}
			}

			if (interrupted) {
				Thread.currentThread().interrupt();
			}

			return this;
		}
	}

	@Override
	public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
		return this.await0(unit.toNanos(timeout), true);
	}

	@Override
	public boolean await(long timeoutMillis) throws InterruptedException {
		return this.await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), true);
	}

	@Override
	public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
		try {
			return this.await0(unit.toNanos(timeout), false);
		} catch (InterruptedException var5) {
			throw new InternalError();
		}
	}

	@Override
	public boolean awaitUninterruptibly(long timeoutMillis) {
		try {
			return this.await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), false);
		} catch (InterruptedException var4) {
			throw new InternalError();
		}
	}

	@Override
	public V getNow() {
		Object result = this.result;
		return (V)(!(result instanceof DefaultPromise.CauseHolder) && result != SUCCESS ? result : null);
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		if (RESULT_UPDATER.compareAndSet(this, null, CANCELLATION_CAUSE_HOLDER)) {
			this.checkNotifyWaiters();
			this.notifyListeners();
			return true;
		} else {
			return false;
		}
	}

	public boolean isCancelled() {
		return isCancelled0(this.result);
	}

	public boolean isDone() {
		return isDone0(this.result);
	}

	@Override
	public Promise<V> sync() throws InterruptedException {
		this.await();
		this.rethrowIfFailed();
		return this;
	}

	@Override
	public Promise<V> syncUninterruptibly() {
		this.awaitUninterruptibly();
		this.rethrowIfFailed();
		return this;
	}

	public String toString() {
		return this.toStringBuilder().toString();
	}

	protected StringBuilder toStringBuilder() {
		StringBuilder buf = new StringBuilder(64).append(StringUtil.simpleClassName(this)).append('@').append(Integer.toHexString(this.hashCode()));
		Object result = this.result;
		if (result == SUCCESS) {
			buf.append("(success)");
		} else if (result == UNCANCELLABLE) {
			buf.append("(uncancellable)");
		} else if (result instanceof DefaultPromise.CauseHolder) {
			buf.append("(failure: ").append(((DefaultPromise.CauseHolder)result).cause).append(')');
		} else if (result != null) {
			buf.append("(success: ").append(result).append(')');
		} else {
			buf.append("(incomplete)");
		}

		return buf;
	}

	protected EventExecutor executor() {
		return this.executor;
	}

	protected void checkDeadLock() {
		EventExecutor e = this.executor();
		if (e != null && e.inEventLoop()) {
			throw new BlockingOperationException(this.toString());
		}
	}

	protected static void notifyListener(EventExecutor eventExecutor, Future<?> future, GenericFutureListener<?> listener) {
		ObjectUtil.checkNotNull(eventExecutor, "eventExecutor");
		ObjectUtil.checkNotNull(future, "future");
		ObjectUtil.checkNotNull(listener, "listener");
		notifyListenerWithStackOverFlowProtection(eventExecutor, future, listener);
	}

	private void notifyListeners() {
		EventExecutor executor = this.executor();
		if (executor.inEventLoop()) {
			InternalThreadLocalMap threadLocals = InternalThreadLocalMap.get();
			int stackDepth = threadLocals.futureListenerStackDepth();
			if (stackDepth < MAX_LISTENER_STACK_DEPTH) {
				threadLocals.setFutureListenerStackDepth(stackDepth + 1);

				try {
					this.notifyListenersNow();
				} finally {
					threadLocals.setFutureListenerStackDepth(stackDepth);
				}

				return;
			}
		}

		safeExecute(executor, new Runnable() {
			public void run() {
				DefaultPromise.this.notifyListenersNow();
			}
		});
	}

	private static void notifyListenerWithStackOverFlowProtection(EventExecutor executor, Future<?> future, GenericFutureListener<?> listener) {
		if (executor.inEventLoop()) {
			InternalThreadLocalMap threadLocals = InternalThreadLocalMap.get();
			int stackDepth = threadLocals.futureListenerStackDepth();
			if (stackDepth < MAX_LISTENER_STACK_DEPTH) {
				threadLocals.setFutureListenerStackDepth(stackDepth + 1);

				try {
					notifyListener0(future, listener);
				} finally {
					threadLocals.setFutureListenerStackDepth(stackDepth);
				}

				return;
			}
		}

		safeExecute(executor, new Runnable() {
			public void run() {
				DefaultPromise.notifyListener0(future, listener);
			}
		});
	}

	private void notifyListenersNow() {
		Object listeners;
		synchronized (this) {
			if (this.notifyingListeners || this.listeners == null) {
				return;
			}

			this.notifyingListeners = true;
			listeners = this.listeners;
			this.listeners = null;
		}

		while (true) {
			if (listeners instanceof DefaultFutureListeners) {
				this.notifyListeners0((DefaultFutureListeners)listeners);
			} else {
				notifyListener0(this, (GenericFutureListener)listeners);
			}

			synchronized (this) {
				if (this.listeners == null) {
					this.notifyingListeners = false;
					return;
				}

				listeners = this.listeners;
				this.listeners = null;
			}
		}
	}

	private void notifyListeners0(DefaultFutureListeners listeners) {
		GenericFutureListener<?>[] a = listeners.listeners();
		int size = listeners.size();

		for (int i = 0; i < size; i++) {
			notifyListener0(this, a[i]);
		}
	}

	private static void notifyListener0(Future future, GenericFutureListener l) {
		try {
			l.operationComplete(future);
		} catch (Throwable var3) {
			logger.warn("An exception was thrown by " + l.getClass().getName() + ".operationComplete()", var3);
		}
	}

	private void addListener0(GenericFutureListener<? extends Future<? super V>> listener) {
		if (this.listeners == null) {
			this.listeners = listener;
		} else if (this.listeners instanceof DefaultFutureListeners) {
			((DefaultFutureListeners)this.listeners).add(listener);
		} else {
			this.listeners = new DefaultFutureListeners((GenericFutureListener<? extends Future<?>>)this.listeners, listener);
		}
	}

	private void removeListener0(GenericFutureListener<? extends Future<? super V>> listener) {
		if (this.listeners instanceof DefaultFutureListeners) {
			((DefaultFutureListeners)this.listeners).remove(listener);
		} else if (this.listeners == listener) {
			this.listeners = null;
		}
	}

	private boolean setSuccess0(V result) {
		return this.setValue0(result == null ? SUCCESS : result);
	}

	private boolean setFailure0(Throwable cause) {
		return this.setValue0(new DefaultPromise.CauseHolder(ObjectUtil.checkNotNull(cause, "cause")));
	}

	private boolean setValue0(Object objResult) {
		if (!RESULT_UPDATER.compareAndSet(this, null, objResult) && !RESULT_UPDATER.compareAndSet(this, UNCANCELLABLE, objResult)) {
			return false;
		} else {
			this.checkNotifyWaiters();
			return true;
		}
	}

	private synchronized void checkNotifyWaiters() {
		if (this.waiters > 0) {
			this.notifyAll();
		}
	}

	private void incWaiters() {
		if (this.waiters == 32767) {
			throw new IllegalStateException("too many waiters: " + this);
		} else {
			this.waiters++;
		}
	}

	private void decWaiters() {
		this.waiters--;
	}

	private void rethrowIfFailed() {
		Throwable cause = this.cause();
		if (cause != null) {
			PlatformDependent.throwException(cause);
		}
	}

	private boolean await0(long timeoutNanos, boolean interruptable) throws InterruptedException {
		if (this.isDone()) {
			return true;
		} else if (timeoutNanos <= 0L) {
			return this.isDone();
		} else if (interruptable && Thread.interrupted()) {
			throw new InterruptedException(this.toString());
		} else {
			this.checkDeadLock();
			long startTime = System.nanoTime();
			long waitTime = timeoutNanos;
			boolean interrupted = false;

			while (true) {
				try {
					synchronized (this) {
						if (this.isDone()) {
							return true;
						}

						this.incWaiters();

						try {
							this.wait(waitTime / 1000000L, (int)(waitTime % 1000000L));
						} catch (InterruptedException var22) {
							if (interruptable) {
								throw var22;
							}

							interrupted = true;
						} finally {
							this.decWaiters();
						}
					}

					if (!this.isDone()) {
						waitTime = timeoutNanos - (System.nanoTime() - startTime);
						if (waitTime > 0L) {
							continue;
						}

						return this.isDone();
					}

					return true;
				} finally {
					if (interrupted) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}
	}

	void notifyProgressiveListeners(long progress, long total) {
		Object listeners = this.progressiveListeners();
		if (listeners != null) {
			final ProgressiveFuture<V> self = (ProgressiveFuture<V>)this;
			EventExecutor executor = this.executor();
			if (executor.inEventLoop()) {
				if (listeners instanceof GenericProgressiveFutureListener[]) {
					notifyProgressiveListeners0(self, (GenericProgressiveFutureListener<?>[])listeners, progress, total);
				} else {
					notifyProgressiveListener0(self, (GenericProgressiveFutureListener)listeners, progress, total);
				}
			} else if (listeners instanceof GenericProgressiveFutureListener[]) {
				final GenericProgressiveFutureListener<?>[] array = (GenericProgressiveFutureListener<?>[])listeners;
				safeExecute(executor, new Runnable() {
					public void run() {
						DefaultPromise.notifyProgressiveListeners0(self, array, progress, total);
					}
				});
			} else {
				final GenericProgressiveFutureListener<ProgressiveFuture<V>> l = (GenericProgressiveFutureListener<ProgressiveFuture<V>>)listeners;
				safeExecute(executor, new Runnable() {
					public void run() {
						DefaultPromise.notifyProgressiveListener0(self, l, progress, total);
					}
				});
			}
		}
	}

	private synchronized Object progressiveListeners() {
		Object listeners = this.listeners;
		if (listeners == null) {
			return null;
		} else if (listeners instanceof DefaultFutureListeners) {
			DefaultFutureListeners dfl = (DefaultFutureListeners)listeners;
			int progressiveSize = dfl.progressiveSize();
			switch (progressiveSize) {
				case 0:
					return null;
				case 1:
					for (GenericFutureListener<?> l : dfl.listeners()) {
						if (l instanceof GenericProgressiveFutureListener) {
							return l;
						}
					}

					return null;
				default:
					GenericFutureListener<?>[] array = dfl.listeners();
					GenericProgressiveFutureListener<?>[] copy = new GenericProgressiveFutureListener[progressiveSize];
					int i = 0;

					for (int j = 0; j < progressiveSize; i++) {
						GenericFutureListener<?> lx = array[i];
						if (lx instanceof GenericProgressiveFutureListener) {
							copy[j++] = (GenericProgressiveFutureListener<?>)lx;
						}
					}

					return copy;
			}
		} else {
			return listeners instanceof GenericProgressiveFutureListener ? listeners : null;
		}
	}

	private static void notifyProgressiveListeners0(ProgressiveFuture<?> future, GenericProgressiveFutureListener<?>[] listeners, long progress, long total) {
		for (GenericProgressiveFutureListener<?> l : listeners) {
			if (l == null) {
				break;
			}

			notifyProgressiveListener0(future, l, progress, total);
		}
	}

	private static void notifyProgressiveListener0(ProgressiveFuture future, GenericProgressiveFutureListener l, long progress, long total) {
		try {
			l.operationProgressed(future, progress, total);
		} catch (Throwable var7) {
			logger.warn("An exception was thrown by " + l.getClass().getName() + ".operationProgressed()", var7);
		}
	}

	private static boolean isCancelled0(Object result) {
		return result instanceof DefaultPromise.CauseHolder && ((DefaultPromise.CauseHolder)result).cause instanceof CancellationException;
	}

	private static boolean isDone0(Object result) {
		return result != null && result != UNCANCELLABLE;
	}

	private static void safeExecute(EventExecutor executor, Runnable task) {
		try {
			executor.execute(task);
		} catch (Throwable var3) {
			rejectedExecutionLogger.error("Failed to submit a listener notification task. Event loop shut down?", var3);
		}
	}

	private static final class CauseHolder {
		final Throwable cause;

		CauseHolder(Throwable cause) {
			this.cause = cause;
		}
	}
}
