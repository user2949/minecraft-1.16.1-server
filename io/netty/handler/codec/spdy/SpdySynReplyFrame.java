package io.netty.handler.codec.spdy;

public interface SpdySynReplyFrame extends SpdyHeadersFrame {
	SpdySynReplyFrame setStreamId(int integer);

	SpdySynReplyFrame setLast(boolean boolean1);

	SpdySynReplyFrame setInvalid();
}
