package io.netty.channel;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;

public interface ChannelPromise extends ChannelFuture, Promise<Void> {
	@Override
	Channel channel();

	ChannelPromise setSuccess(Void void1);

	ChannelPromise setSuccess();

	boolean trySuccess();

	ChannelPromise setFailure(Throwable throwable);

	ChannelPromise addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

	ChannelPromise addListeners(GenericFutureListener<? extends Future<? super Void>>... arr);

	ChannelPromise removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

	ChannelPromise removeListeners(GenericFutureListener<? extends Future<? super Void>>... arr);

	ChannelPromise sync() throws InterruptedException;

	ChannelPromise syncUninterruptibly();

	ChannelPromise await() throws InterruptedException;

	ChannelPromise awaitUninterruptibly();

	ChannelPromise unvoid();
}
