package io.netty.handler.proxy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public final class HttpProxyHandler extends ProxyHandler {
	private static final String PROTOCOL = "http";
	private static final String AUTH_BASIC = "basic";
	private final HttpClientCodec codec = new HttpClientCodec();
	private final String username;
	private final String password;
	private final CharSequence authorization;
	private final boolean ignoreDefaultPortsInConnectHostHeader;
	private HttpResponseStatus status;
	private HttpHeaders headers;

	public HttpProxyHandler(SocketAddress proxyAddress) {
		this(proxyAddress, null);
	}

	public HttpProxyHandler(SocketAddress proxyAddress, HttpHeaders headers) {
		this(proxyAddress, headers, false);
	}

	public HttpProxyHandler(SocketAddress proxyAddress, HttpHeaders headers, boolean ignoreDefaultPortsInConnectHostHeader) {
		super(proxyAddress);
		this.username = null;
		this.password = null;
		this.authorization = null;
		this.headers = headers;
		this.ignoreDefaultPortsInConnectHostHeader = ignoreDefaultPortsInConnectHostHeader;
	}

	public HttpProxyHandler(SocketAddress proxyAddress, String username, String password) {
		this(proxyAddress, username, password, null);
	}

	public HttpProxyHandler(SocketAddress proxyAddress, String username, String password, HttpHeaders headers) {
		this(proxyAddress, username, password, headers, false);
	}

	public HttpProxyHandler(SocketAddress proxyAddress, String username, String password, HttpHeaders headers, boolean ignoreDefaultPortsInConnectHostHeader) {
		super(proxyAddress);
		if (username == null) {
			throw new NullPointerException("username");
		} else if (password == null) {
			throw new NullPointerException("password");
		} else {
			this.username = username;
			this.password = password;
			ByteBuf authz = Unpooled.copiedBuffer(username + ':' + password, CharsetUtil.UTF_8);
			ByteBuf authzBase64 = Base64.encode(authz, false);
			this.authorization = new AsciiString("Basic " + authzBase64.toString(CharsetUtil.US_ASCII));
			authz.release();
			authzBase64.release();
			this.headers = headers;
			this.ignoreDefaultPortsInConnectHostHeader = ignoreDefaultPortsInConnectHostHeader;
		}
	}

	@Override
	public String protocol() {
		return "http";
	}

	@Override
	public String authScheme() {
		return this.authorization != null ? "basic" : "none";
	}

	public String username() {
		return this.username;
	}

	public String password() {
		return this.password;
	}

	@Override
	protected void addCodec(ChannelHandlerContext ctx) throws Exception {
		ChannelPipeline p = ctx.pipeline();
		String name = ctx.name();
		p.addBefore(name, null, this.codec);
	}

	@Override
	protected void removeEncoder(ChannelHandlerContext ctx) throws Exception {
		this.codec.removeOutboundHandler();
	}

	@Override
	protected void removeDecoder(ChannelHandlerContext ctx) throws Exception {
		this.codec.removeInboundHandler();
	}

	@Override
	protected Object newInitialMessage(ChannelHandlerContext ctx) throws Exception {
		InetSocketAddress raddr = this.destinationAddress();
		String hostString = HttpUtil.formatHostnameForHttp(raddr);
		int port = raddr.getPort();
		String url = hostString + ":" + port;
		String hostHeader = !this.ignoreDefaultPortsInConnectHostHeader || port != 80 && port != 443 ? url : hostString;
		FullHttpRequest req = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.CONNECT, url, Unpooled.EMPTY_BUFFER, false);
		req.headers().set(HttpHeaderNames.HOST, hostHeader);
		if (this.authorization != null) {
			req.headers().set(HttpHeaderNames.PROXY_AUTHORIZATION, this.authorization);
		}

		if (this.headers != null) {
			req.headers().add(this.headers);
		}

		return req;
	}

	@Override
	protected boolean handleResponse(ChannelHandlerContext ctx, Object response) throws Exception {
		if (response instanceof HttpResponse) {
			if (this.status != null) {
				throw new ProxyConnectException(this.exceptionMessage("too many responses"));
			}

			this.status = ((HttpResponse)response).status();
		}

		boolean finished = response instanceof LastHttpContent;
		if (finished) {
			if (this.status == null) {
				throw new ProxyConnectException(this.exceptionMessage("missing response"));
			}

			if (this.status.code() != 200) {
				throw new ProxyConnectException(this.exceptionMessage("status: " + this.status));
			}
		}

		return finished;
	}
}
