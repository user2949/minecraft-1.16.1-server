package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2Stream.State;

public interface Http2FrameStream {
	int id();

	State state();
}
