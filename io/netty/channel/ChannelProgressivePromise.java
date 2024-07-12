package io.netty.channel;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ProgressivePromise;

public interface ChannelProgressivePromise extends ProgressivePromise<Void>, ChannelProgressiveFuture, ChannelPromise {
	ChannelProgressivePromise addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

	ChannelProgressivePromise addListeners(GenericFutureListener<? extends Future<? super Void>>... arr);

	ChannelProgressivePromise removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

	ChannelProgressivePromise removeListeners(GenericFutureListener<? extends Future<? super Void>>... arr);

	ChannelProgressivePromise sync() throws InterruptedException;

	ChannelProgressivePromise syncUninterruptibly();

	ChannelProgressivePromise await() throws InterruptedException;

	ChannelProgressivePromise awaitUninterruptibly();

	ChannelProgressivePromise setSuccess(Void void1);

	ChannelProgressivePromise setSuccess();

	ChannelProgressivePromise setFailure(Throwable throwable);

	ChannelProgressivePromise setProgress(long long1, long long2);

	ChannelProgressivePromise unvoid();
}
