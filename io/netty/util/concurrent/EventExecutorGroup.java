package io.netty.util.concurrent;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public interface EventExecutorGroup extends ScheduledExecutorService, Iterable<EventExecutor> {
	boolean isShuttingDown();

	Future<?> shutdownGracefully();

	Future<?> shutdownGracefully(long long1, long long2, TimeUnit timeUnit);

	Future<?> terminationFuture();

	@Deprecated
	void shutdown();

	@Deprecated
	List<Runnable> shutdownNow();

	EventExecutor next();

	Iterator<EventExecutor> iterator();

	Future<?> submit(Runnable runnable);

	<T> Future<T> submit(Runnable runnable, T object);

	<T> Future<T> submit(Callable<T> callable);

	ScheduledFuture<?> schedule(Runnable runnable, long long2, TimeUnit timeUnit);

	<V> ScheduledFuture<V> schedule(Callable<V> callable, long long2, TimeUnit timeUnit);

	ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long long2, long long3, TimeUnit timeUnit);

	ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long long2, long long3, TimeUnit timeUnit);
}
