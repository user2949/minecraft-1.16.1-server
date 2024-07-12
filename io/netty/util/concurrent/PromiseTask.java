package io.netty.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

class PromiseTask<V> extends DefaultPromise<V> implements RunnableFuture<V> {
	protected final Callable<V> task;

	static <T> Callable<T> toCallable(Runnable runnable, T result) {
		return new PromiseTask.RunnableAdapter<>(runnable, result);
	}

	PromiseTask(EventExecutor executor, Runnable runnable, V result) {
		this(executor, toCallable(runnable, result));
	}

	PromiseTask(EventExecutor executor, Callable<V> callable) {
		super(executor);
		this.task = callable;
	}

	public final int hashCode() {
		return System.identityHashCode(this);
	}

	public final boolean equals(Object obj) {
		return this == obj;
	}

	public void run() {
		try {
			if (this.setUncancellableInternal()) {
				V result = (V)this.task.call();
				this.setSuccessInternal(result);
			}
		} catch (Throwable var2) {
			this.setFailureInternal(var2);
		}
	}

	@Override
	public final Promise<V> setFailure(Throwable cause) {
		throw new IllegalStateException();
	}

	protected final Promise<V> setFailureInternal(Throwable cause) {
		super.setFailure(cause);
		return this;
	}

	@Override
	public final boolean tryFailure(Throwable cause) {
		return false;
	}

	protected final boolean tryFailureInternal(Throwable cause) {
		return super.tryFailure(cause);
	}

	@Override
	public final Promise<V> setSuccess(V result) {
		throw new IllegalStateException();
	}

	protected final Promise<V> setSuccessInternal(V result) {
		super.setSuccess(result);
		return this;
	}

	@Override
	public final boolean trySuccess(V result) {
		return false;
	}

	protected final boolean trySuccessInternal(V result) {
		return super.trySuccess(result);
	}

	@Override
	public final boolean setUncancellable() {
		throw new IllegalStateException();
	}

	protected final boolean setUncancellableInternal() {
		return super.setUncancellable();
	}

	@Override
	protected StringBuilder toStringBuilder() {
		StringBuilder buf = super.toStringBuilder();
		buf.setCharAt(buf.length() - 1, ',');
		return buf.append(" task: ").append(this.task).append(')');
	}

	private static final class RunnableAdapter<T> implements Callable<T> {
		final Runnable task;
		final T result;

		RunnableAdapter(Runnable task, T result) {
			this.task = task;
			this.result = result;
		}

		public T call() {
			this.task.run();
			return this.result;
		}

		public String toString() {
			return "Callable(task: " + this.task + ", result: " + this.result + ')';
		}
	}
}
