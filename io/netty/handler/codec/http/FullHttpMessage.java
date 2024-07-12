package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;

public interface FullHttpMessage extends HttpMessage, LastHttpContent {
	FullHttpMessage copy();

	FullHttpMessage duplicate();

	FullHttpMessage retainedDuplicate();

	FullHttpMessage replace(ByteBuf byteBuf);

	FullHttpMessage retain(int integer);

	FullHttpMessage retain();

	FullHttpMessage touch();

	FullHttpMessage touch(Object object);
}
