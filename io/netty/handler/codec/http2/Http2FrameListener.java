package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface Http2FrameListener {
	int onDataRead(ChannelHandlerContext channelHandlerContext, int integer2, ByteBuf byteBuf, int integer4, boolean boolean5) throws Http2Exception;

	void onHeadersRead(ChannelHandlerContext channelHandlerContext, int integer2, Http2Headers http2Headers, int integer4, boolean boolean5) throws Http2Exception;

	void onHeadersRead(
		ChannelHandlerContext channelHandlerContext,
		int integer2,
		Http2Headers http2Headers,
		int integer4,
		short short5,
		boolean boolean6,
		int integer7,
		boolean boolean8
	) throws Http2Exception;

	void onPriorityRead(ChannelHandlerContext channelHandlerContext, int integer2, int integer3, short short4, boolean boolean5) throws Http2Exception;

	void onRstStreamRead(ChannelHandlerContext channelHandlerContext, int integer, long long3) throws Http2Exception;

	void onSettingsAckRead(ChannelHandlerContext channelHandlerContext) throws Http2Exception;

	void onSettingsRead(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings) throws Http2Exception;

	void onPingRead(ChannelHandlerContext channelHandlerContext, long long2) throws Http2Exception;

	void onPingAckRead(ChannelHandlerContext channelHandlerContext, long long2) throws Http2Exception;

	void onPushPromiseRead(ChannelHandlerContext channelHandlerContext, int integer2, int integer3, Http2Headers http2Headers, int integer5) throws Http2Exception;

	void onGoAwayRead(ChannelHandlerContext channelHandlerContext, int integer, long long3, ByteBuf byteBuf) throws Http2Exception;

	void onWindowUpdateRead(ChannelHandlerContext channelHandlerContext, int integer2, int integer3) throws Http2Exception;

	void onUnknownFrame(ChannelHandlerContext channelHandlerContext, byte byte2, int integer, Http2Flags http2Flags, ByteBuf byteBuf) throws Http2Exception;
}
