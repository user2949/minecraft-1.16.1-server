package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;

public interface Http2LocalFlowController extends Http2FlowController {
	Http2LocalFlowController frameWriter(Http2FrameWriter http2FrameWriter);

	void receiveFlowControlledFrame(Http2Stream http2Stream, ByteBuf byteBuf, int integer, boolean boolean4) throws Http2Exception;

	boolean consumeBytes(Http2Stream http2Stream, int integer) throws Http2Exception;

	int unconsumedBytes(Http2Stream http2Stream);

	int initialWindowSize(Http2Stream http2Stream);
}
