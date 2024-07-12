package io.netty.handler.codec.http;

public interface HttpResponse extends HttpMessage {
	@Deprecated
	HttpResponseStatus getStatus();

	HttpResponseStatus status();

	HttpResponse setStatus(HttpResponseStatus httpResponseStatus);

	HttpResponse setProtocolVersion(HttpVersion httpVersion);
}
