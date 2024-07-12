package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import java.io.Closeable;

public interface Http2FrameWriter extends Http2DataWriter, Closeable {
	ChannelFuture writeHeaders(
		ChannelHandlerContext channelHandlerContext, int integer2, Http2Headers http2Headers, int integer4, boolean boolean5, ChannelPromise channelPromise
	);

	ChannelFuture writeHeaders(
		ChannelHandlerContext channelHandlerContext,
		int integer2,
		Http2Headers http2Headers,
		int integer4,
		short short5,
		boolean boolean6,
		int integer7,
		boolean boolean8,
		ChannelPromise channelPromise
	);

	ChannelFuture writePriority(
		ChannelHandlerContext channelHandlerContext, int integer2, int integer3, short short4, boolean boolean5, ChannelPromise channelPromise
	);

	ChannelFuture writeRstStream(ChannelHandlerContext channelHandlerContext, int integer, long long3, ChannelPromise channelPromise);

	ChannelFuture writeSettings(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings, ChannelPromise channelPromise);

	ChannelFuture writeSettingsAck(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise);

	ChannelFuture writePing(ChannelHandlerContext channelHandlerContext, boolean boolean2, long long3, ChannelPromise channelPromise);

	ChannelFuture writePushPromise(
		ChannelHandlerContext channelHandlerContext, int integer2, int integer3, Http2Headers http2Headers, int integer5, ChannelPromise channelPromise
	);

	ChannelFuture writeGoAway(ChannelHandlerContext channelHandlerContext, int integer, long long3, ByteBuf byteBuf, ChannelPromise channelPromise);

	ChannelFuture writeWindowUpdate(ChannelHandlerContext channelHandlerContext, int integer2, int integer3, ChannelPromise channelPromise);

	ChannelFuture writeFrame(
		ChannelHandlerContext channelHandlerContext, byte byte2, int integer, Http2Flags http2Flags, ByteBuf byteBuf, ChannelPromise channelPromise
	);

	Http2FrameWriter.Configuration configuration();

	void close();

	public interface Configuration {
		Http2HeadersEncoder.Configuration headersConfiguration();

		Http2FrameSizePolicy frameSizePolicy();
	}
}
