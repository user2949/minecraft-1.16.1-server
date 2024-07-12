package io.netty.handler.codec.spdy;

public interface SpdyRstStreamFrame extends SpdyStreamFrame {
	SpdyStreamStatus status();

	SpdyRstStreamFrame setStatus(SpdyStreamStatus spdyStreamStatus);

	SpdyRstStreamFrame setStreamId(int integer);

	SpdyRstStreamFrame setLast(boolean boolean1);
}
