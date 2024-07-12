package io.netty.util.concurrent;

public interface GenericProgressiveFutureListener<F extends ProgressiveFuture<?>> extends GenericFutureListener<F> {
	void operationProgressed(F progressiveFuture, long long2, long long3) throws Exception;
}
