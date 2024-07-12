package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public interface Http2DataWriter {
	ChannelFuture writeData(
		ChannelHandlerContext channelHandlerContext, int integer2, ByteBuf byteBuf, int integer4, boolean boolean5, ChannelPromise channelPromise
	);
}
