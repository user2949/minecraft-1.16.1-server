package io.netty.handler.codec.spdy;

public interface SpdyStreamFrame extends SpdyFrame {
	int streamId();

	SpdyStreamFrame setStreamId(int integer);

	boolean isLast();

	SpdyStreamFrame setLast(boolean boolean1);
}
