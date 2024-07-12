package io.netty.handler.codec.spdy;

public interface SpdyGoAwayFrame extends SpdyFrame {
	int lastGoodStreamId();

	SpdyGoAwayFrame setLastGoodStreamId(int integer);

	SpdySessionStatus status();

	SpdyGoAwayFrame setStatus(SpdySessionStatus spdySessionStatus);
}
