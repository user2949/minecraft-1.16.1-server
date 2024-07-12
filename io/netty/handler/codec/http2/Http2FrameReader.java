package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.io.Closeable;

public interface Http2FrameReader extends Closeable {
	void readFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception;

	Http2FrameReader.Configuration configuration();

	void close();

	public interface Configuration {
		Http2HeadersDecoder.Configuration headersConfiguration();

		Http2FrameSizePolicy frameSizePolicy();
	}
}
