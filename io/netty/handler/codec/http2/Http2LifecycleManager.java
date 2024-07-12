package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public interface Http2LifecycleManager {
	void closeStreamLocal(Http2Stream http2Stream, ChannelFuture channelFuture);

	void closeStreamRemote(Http2Stream http2Stream, ChannelFuture channelFuture);

	void closeStream(Http2Stream http2Stream, ChannelFuture channelFuture);

	ChannelFuture resetStream(ChannelHandlerContext channelHandlerContext, int integer, long long3, ChannelPromise channelPromise);

	ChannelFuture goAway(ChannelHandlerContext channelHandlerContext, int integer, long long3, ByteBuf byteBuf, ChannelPromise channelPromise);

	void onError(ChannelHandlerContext channelHandlerContext, boolean boolean2, Throwable throwable);
}
