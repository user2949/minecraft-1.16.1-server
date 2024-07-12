package io.netty.util.concurrent;

import java.util.concurrent.TimeUnit;

public abstract class CompleteFuture<V> extends AbstractFuture<V> {
	private final EventExecutor executor;

	protected CompleteFuture(EventExecutor executor) {
		this.executor = executor;
	}

	protected EventExecutor executor() {
		return this.executor;
	}

	@Override
	public Future<V> addListener(GenericFutureListener<? extends Future<? super V>> listener) {
		if (listener == null) {
			throw new NullPointerException("listener");
		} else {
			DefaultPromise.notifyListener(this.executor(), this, listener);
			return this;
		}
	}

	@Override
	public Future<V> addListeners(GenericFutureListener<? extends Future<? super V>>... listeners) {
		if (listeners == null) {
			throw new NullPointerException("listeners");
		} else {
			for (GenericFutureListener<? extends Future<? super V>> l : listeners) {
				if (l == null) {
					break;
				}

				DefaultPromise.notifyListener(this.executor(), this, l);
			}

			return this;
		}
	}

	@Override
	public Future<V> removeListener(GenericFutureListener<? extends Future<? super V>> listener) {
		return this;
	}

	@Override
	public Future<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... listeners) {
		return this;
	}

	@Override
	public Future<V> await() throws InterruptedException {
		if (Thread.interrupted()) {
			throw new InterruptedException();
		} else {
			return this;
		}
	}

	@Override
	public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
		if (Thread.interrupted()) {
			throw new InterruptedException();
		} else {
			return true;
		}
	}

	@Override
	public Future<V> sync() throws InterruptedException {
		return this;
	}

	@Override
	public Future<V> syncUninterruptibly() {
		return this;
	}

	@Override
	public boolean await(long timeoutMillis) throws InterruptedException {
		if (Thread.interrupted()) {
			throw new InterruptedException();
		} else {
			return true;
		}
	}

	@Override
	public Future<V> awaitUninterruptibly() {
		return this;
	}

	@Override
	public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
		return true;
	}

	@Override
	public boolean awaitUninterruptibly(long timeoutMillis) {
		return true;
	}

	public boolean isDone() {
		return true;
	}

	@Override
	public boolean isCancellable() {
		return false;
	}

	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}
}
