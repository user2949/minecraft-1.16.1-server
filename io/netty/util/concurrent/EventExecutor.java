package io.netty.util.concurrent;

public interface EventExecutor extends EventExecutorGroup {
	@Override
	EventExecutor next();

	EventExecutorGroup parent();

	boolean inEventLoop();

	boolean inEventLoop(Thread thread);

	<V> Promise<V> newPromise();

	<V> ProgressivePromise<V> newProgressivePromise();

	<V> Future<V> newSucceededFuture(V object);

	<V> Future<V> newFailedFuture(Throwable throwable);
}
