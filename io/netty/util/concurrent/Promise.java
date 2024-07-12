package io.netty.util.concurrent;

public interface Promise<V> extends Future<V> {
	Promise<V> setSuccess(V object);

	boolean trySuccess(V object);

	Promise<V> setFailure(Throwable throwable);

	boolean tryFailure(Throwable throwable);

	boolean setUncancellable();

	Promise<V> addListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

	Promise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... arr);

	Promise<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

	Promise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... arr);

	Promise<V> await() throws InterruptedException;

	Promise<V> awaitUninterruptibly();

	Promise<V> sync() throws InterruptedException;

	Promise<V> syncUninterruptibly();
}
