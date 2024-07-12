package io.netty.util.concurrent;

public interface ProgressivePromise<V> extends Promise<V>, ProgressiveFuture<V> {
	ProgressivePromise<V> setProgress(long long1, long long2);

	boolean tryProgress(long long1, long long2);

	ProgressivePromise<V> setSuccess(V object);

	ProgressivePromise<V> setFailure(Throwable throwable);

	ProgressivePromise<V> addListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

	ProgressivePromise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... arr);

	ProgressivePromise<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

	ProgressivePromise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... arr);

	ProgressivePromise<V> await() throws InterruptedException;

	ProgressivePromise<V> awaitUninterruptibly();

	ProgressivePromise<V> sync() throws InterruptedException;

	ProgressivePromise<V> syncUninterruptibly();
}
