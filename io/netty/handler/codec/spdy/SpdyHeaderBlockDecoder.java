package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

abstract class SpdyHeaderBlockDecoder {
	static SpdyHeaderBlockDecoder newInstance(SpdyVersion spdyVersion, int maxHeaderSize) {
		return new SpdyHeaderBlockZlibDecoder(spdyVersion, maxHeaderSize);
	}

	abstract void decode(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, SpdyHeadersFrame spdyHeadersFrame) throws Exception;

	abstract void endHeaderBlock(SpdyHeadersFrame spdyHeadersFrame) throws Exception;

	abstract void end();
}
