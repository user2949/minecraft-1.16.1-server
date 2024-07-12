package io.netty.channel;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public interface ChannelFuture extends Future<Void> {
	Channel channel();

	ChannelFuture addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

	ChannelFuture addListeners(GenericFutureListener<? extends Future<? super Void>>... arr);

	ChannelFuture removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

	ChannelFuture removeListeners(GenericFutureListener<? extends Future<? super Void>>... arr);

	ChannelFuture sync() throws InterruptedException;

	ChannelFuture syncUninterruptibly();

	ChannelFuture await() throws InterruptedException;

	ChannelFuture awaitUninterruptibly();

	boolean isVoid();
}
