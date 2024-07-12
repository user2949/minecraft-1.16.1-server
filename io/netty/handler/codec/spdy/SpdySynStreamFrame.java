package io.netty.handler.codec.spdy;

public interface SpdySynStreamFrame extends SpdyHeadersFrame {
	int associatedStreamId();

	SpdySynStreamFrame setAssociatedStreamId(int integer);

	byte priority();

	SpdySynStreamFrame setPriority(byte byte1);

	boolean isUnidirectional();

	SpdySynStreamFrame setUnidirectional(boolean boolean1);

	SpdySynStreamFrame setStreamId(int integer);

	SpdySynStreamFrame setLast(boolean boolean1);

	SpdySynStreamFrame setInvalid();
}
