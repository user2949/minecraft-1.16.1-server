package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Beta
@CanIgnoreReturnValue
@GwtIncompatible
public interface ListeningScheduledExecutorService extends ScheduledExecutorService, ListeningExecutorService {
	ListenableScheduledFuture<?> schedule(Runnable runnable, long long2, TimeUnit timeUnit);

	<V> ListenableScheduledFuture<V> schedule(Callable<V> callable, long long2, TimeUnit timeUnit);

	ListenableScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long long2, long long3, TimeUnit timeUnit);

	ListenableScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long long2, long long3, TimeUnit timeUnit);
}
