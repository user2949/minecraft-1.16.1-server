package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.util.concurrent.ForwardingListenableFuture.SimpleForwardingListenableFuture;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Beta
@GwtIncompatible
public abstract class AbstractCheckedFuture<V, X extends Exception> extends SimpleForwardingListenableFuture<V> implements CheckedFuture<V, X> {
	protected AbstractCheckedFuture(ListenableFuture<V> delegate) {
		super(delegate);
	}

	protected abstract X mapException(Exception exception);

	@CanIgnoreReturnValue
	@Override
	public V checkedGet() throws X {
		try {
			return this.get();
		} catch (InterruptedException var2) {
			Thread.currentThread().interrupt();
			throw this.mapException(var2);
		} catch (CancellationException var3) {
			throw this.mapException(var3);
		} catch (ExecutionException var4) {
			throw this.mapException(var4);
		}
	}

	@CanIgnoreReturnValue
	@Override
	public V checkedGet(long timeout, TimeUnit unit) throws TimeoutException, X {
		try {
			return this.get(timeout, unit);
		} catch (InterruptedException var5) {
			Thread.currentThread().interrupt();
			throw this.mapException(var5);
		} catch (CancellationException var6) {
			throw this.mapException(var6);
		} catch (ExecutionException var7) {
			throw this.mapException(var7);
		}
	}
}
