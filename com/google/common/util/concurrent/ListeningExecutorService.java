package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@CanIgnoreReturnValue
@GwtIncompatible
public interface ListeningExecutorService extends ExecutorService {
	<T> ListenableFuture<T> submit(Callable<T> callable);

	ListenableFuture<?> submit(Runnable runnable);

	<T> ListenableFuture<T> submit(Runnable runnable, T object);

	<T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) throws InterruptedException;

	<T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long long2, TimeUnit timeUnit) throws InterruptedException;
}
