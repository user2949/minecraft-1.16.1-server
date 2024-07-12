package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

public interface Http2DataFrame extends Http2StreamFrame, ByteBufHolder {
	int padding();

	@Override
	ByteBuf content();

	int initialFlowControlledBytes();

	boolean isEndStream();

	Http2DataFrame copy();

	Http2DataFrame duplicate();

	Http2DataFrame retainedDuplicate();

	Http2DataFrame replace(ByteBuf byteBuf);

	Http2DataFrame retain();

	Http2DataFrame retain(int integer);

	Http2DataFrame touch();

	Http2DataFrame touch(Object object);
}
