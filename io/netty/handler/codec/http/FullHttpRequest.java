package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;

public interface FullHttpRequest extends HttpRequest, FullHttpMessage {
	FullHttpRequest copy();

	FullHttpRequest duplicate();

	FullHttpRequest retainedDuplicate();

	FullHttpRequest replace(ByteBuf byteBuf);

	FullHttpRequest retain(int integer);

	FullHttpRequest retain();

	FullHttpRequest touch();

	FullHttpRequest touch(Object object);

	FullHttpRequest setProtocolVersion(HttpVersion httpVersion);

	FullHttpRequest setMethod(HttpMethod httpMethod);

	FullHttpRequest setUri(String string);
}
