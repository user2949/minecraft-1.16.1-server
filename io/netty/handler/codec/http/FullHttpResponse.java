package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;

public interface FullHttpResponse extends HttpResponse, FullHttpMessage {
	FullHttpResponse copy();

	FullHttpResponse duplicate();

	FullHttpResponse retainedDuplicate();

	FullHttpResponse replace(ByteBuf byteBuf);

	FullHttpResponse retain(int integer);

	FullHttpResponse retain();

	FullHttpResponse touch();

	FullHttpResponse touch(Object object);

	FullHttpResponse setProtocolVersion(HttpVersion httpVersion);

	FullHttpResponse setStatus(HttpResponseStatus httpResponseStatus);
}
