package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AbstractFuture.TrustedFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import javax.annotation.Nullable;

@GwtCompatible
class TrustedListenableFutureTask<V> extends TrustedFuture<V> implements RunnableFuture<V> {
	private TrustedListenableFutureTask<V>.TrustedFutureInterruptibleTask task;

	static <V> TrustedListenableFutureTask<V> create(Callable<V> callable) {
		return new TrustedListenableFutureTask<>(callable);
	}

	static <V> TrustedListenableFutureTask<V> create(Runnable runnable, @Nullable V result) {
		return new TrustedListenableFutureTask<>(Executors.callable(runnable, result));
	}

	TrustedListenableFutureTask(Callable<V> callable) {
		this.task = new TrustedListenableFutureTask.TrustedFutureInterruptibleTask(callable);
	}

	public void run() {
		TrustedListenableFutureTask<V>.TrustedFutureInterruptibleTask localTask = this.task;
		if (localTask != null) {
			localTask.run();
		}
	}

	@Override
	protected void afterDone() {
		super.afterDone();
		if (this.wasInterrupted()) {
			TrustedListenableFutureTask<V>.TrustedFutureInterruptibleTask localTask = this.task;
			if (localTask != null) {
				localTask.interruptTask();
			}
		}

		this.task = null;
	}

	public String toString() {
		return super.toString() + " (delegate = " + this.task + ")";
	}

	private final class TrustedFutureInterruptibleTask extends InterruptibleTask {
		private final Callable<V> callable;

		TrustedFutureInterruptibleTask(Callable<V> callable) {
			this.callable = Preconditions.checkNotNull(callable);
		}

		@Override
		void runInterruptibly() {
			if (!TrustedListenableFutureTask.this.isDone()) {
				try {
					TrustedListenableFutureTask.this.set((V)this.callable.call());
				} catch (Throwable var2) {
					TrustedListenableFutureTask.this.setException(var2);
				}
			}
		}

		@Override
		boolean wasInterrupted() {
			return TrustedListenableFutureTask.this.wasInterrupted();
		}

		public String toString() {
			return this.callable.toString();
		}
	}
}
