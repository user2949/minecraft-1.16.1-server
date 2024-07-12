package io.netty.handler.codec.spdy;

public interface SpdyHeadersFrame extends SpdyStreamFrame {
	boolean isInvalid();

	SpdyHeadersFrame setInvalid();

	boolean isTruncated();

	SpdyHeadersFrame setTruncated();

	SpdyHeaders headers();

	SpdyHeadersFrame setStreamId(int integer);

	SpdyHeadersFrame setLast(boolean boolean1);
}
