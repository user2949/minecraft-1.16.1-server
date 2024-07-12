package io.netty.handler.codec.spdy;

public interface SpdyWindowUpdateFrame extends SpdyFrame {
	int streamId();

	SpdyWindowUpdateFrame setStreamId(int integer);

	int deltaWindowSize();

	SpdyWindowUpdateFrame setDeltaWindowSize(int integer);
}
