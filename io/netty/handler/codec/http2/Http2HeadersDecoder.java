package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;

public interface Http2HeadersDecoder {
	Http2Headers decodeHeaders(int integer, ByteBuf byteBuf) throws Http2Exception;

	Http2HeadersDecoder.Configuration configuration();

	public interface Configuration {
		void maxHeaderTableSize(long long1) throws Http2Exception;

		long maxHeaderTableSize();

		void maxHeaderListSize(long long1, long long2) throws Http2Exception;

		long maxHeaderListSize();

		long maxHeaderListSizeGoAway();
	}
}
