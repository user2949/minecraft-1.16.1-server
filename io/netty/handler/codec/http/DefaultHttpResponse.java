package io.netty.handler.codec.http;

import io.netty.util.internal.ObjectUtil;

public class DefaultHttpResponse extends DefaultHttpMessage implements HttpResponse {
	private HttpResponseStatus status;

	public DefaultHttpResponse(HttpVersion version, HttpResponseStatus status) {
		this(version, status, true, false);
	}

	public DefaultHttpResponse(HttpVersion version, HttpResponseStatus status, boolean validateHeaders) {
		this(version, status, validateHeaders, false);
	}

	public DefaultHttpResponse(HttpVersion version, HttpResponseStatus status, boolean validateHeaders, boolean singleFieldHeaders) {
		super(version, validateHeaders, singleFieldHeaders);
		this.status = ObjectUtil.checkNotNull(status, "status");
	}

	public DefaultHttpResponse(HttpVersion version, HttpResponseStatus status, HttpHeaders headers) {
		super(version, headers);
		this.status = ObjectUtil.checkNotNull(status, "status");
	}

	@Deprecated
	@Override
	public HttpResponseStatus getStatus() {
		return this.status();
	}

	@Override
	public HttpResponseStatus status() {
		return this.status;
	}

	@Override
	public HttpResponse setStatus(HttpResponseStatus status) {
		if (status == null) {
			throw new NullPointerException("status");
		} else {
			this.status = status;
			return this;
		}
	}

	@Override
	public HttpResponse setProtocolVersion(HttpVersion version) {
		super.setProtocolVersion(version);
		return this;
	}

	public String toString() {
		return HttpMessageUtil.appendResponse(new StringBuilder(256), this).toString();
	}
}
