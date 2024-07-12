package io.netty.util.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class AbstractFuture<V> implements Future<V> {
	public V get() throws InterruptedException, ExecutionException {
		this.await();
		Throwable cause = this.cause();
		if (cause == null) {
			return this.getNow();
		} else if (cause instanceof CancellationException) {
			throw (CancellationException)cause;
		} else {
			throw new ExecutionException(cause);
		}
	}

	public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		if (this.await(timeout, unit)) {
			Throwable cause = this.cause();
			if (cause == null) {
				return this.getNow();
			} else if (cause instanceof CancellationException) {
				throw (CancellationException)cause;
			} else {
				throw new ExecutionException(cause);
			}
		} else {
			throw new TimeoutException();
		}
	}
}
