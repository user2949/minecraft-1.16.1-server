package io.netty.handler.codec.http;

public interface HttpRequest extends HttpMessage {
	@Deprecated
	HttpMethod getMethod();

	HttpMethod method();

	HttpRequest setMethod(HttpMethod httpMethod);

	@Deprecated
	String getUri();

	String uri();

	HttpRequest setUri(String string);

	HttpRequest setProtocolVersion(HttpVersion httpVersion);
}
