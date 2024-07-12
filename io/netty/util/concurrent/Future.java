package io.netty.util.concurrent;

import java.util.concurrent.TimeUnit;

public interface Future<V> extends java.util.concurrent.Future<V> {
	boolean isSuccess();

	boolean isCancellable();

	Throwable cause();

	Future<V> addListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

	Future<V> addListeners(GenericFutureListener<? extends Future<? super V>>... arr);

	Future<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

	Future<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... arr);

	Future<V> sync() throws InterruptedException;

	Future<V> syncUninterruptibly();

	Future<V> await() throws InterruptedException;

	Future<V> awaitUninterruptibly();

	boolean await(long long1, TimeUnit timeUnit) throws InterruptedException;

	boolean await(long long1) throws InterruptedException;

	boolean awaitUninterruptibly(long long1, TimeUnit timeUnit);

	boolean awaitUninterruptibly(long long1);

	V getNow();

	boolean cancel(boolean boolean1);
}
