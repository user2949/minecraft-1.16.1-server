package io.netty.handler.codec.http.websocketx;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpScheme;
import io.netty.util.NetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.ThrowableUtil;
import java.net.URI;
import java.nio.channels.ClosedChannelException;
import java.util.Locale;

public abstract class WebSocketClientHandshaker {
	private static final ClosedChannelException CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), WebSocketClientHandshaker.class, "processHandshake(...)"
	);
	private static final String HTTP_SCHEME_PREFIX = HttpScheme.HTTP + "://";
	private static final String HTTPS_SCHEME_PREFIX = HttpScheme.HTTPS + "://";
	private final URI uri;
	private final WebSocketVersion version;
	private volatile boolean handshakeComplete;
	private final String expectedSubprotocol;
	private volatile String actualSubprotocol;
	protected final HttpHeaders customHeaders;
	private final int maxFramePayloadLength;

	protected WebSocketClientHandshaker(URI uri, WebSocketVersion version, String subprotocol, HttpHeaders customHeaders, int maxFramePayloadLength) {
		this.uri = uri;
		this.version = version;
		this.expectedSubprotocol = subprotocol;
		this.customHeaders = customHeaders;
		this.maxFramePayloadLength = maxFramePayloadLength;
	}

	public URI uri() {
		return this.uri;
	}

	public WebSocketVersion version() {
		return this.version;
	}

	public int maxFramePayloadLength() {
		return this.maxFramePayloadLength;
	}

	public boolean isHandshakeComplete() {
		return this.handshakeComplete;
	}

	private void setHandshakeComplete() {
		this.handshakeComplete = true;
	}

	public String expectedSubprotocol() {
		return this.expectedSubprotocol;
	}

	public String actualSubprotocol() {
		return this.actualSubprotocol;
	}

	private void setActualSubprotocol(String actualSubprotocol) {
		this.actualSubprotocol = actualSubprotocol;
	}

	public ChannelFuture handshake(Channel channel) {
		if (channel == null) {
			throw new NullPointerException("channel");
		} else {
			return this.handshake(channel, channel.newPromise());
		}
	}

	public final ChannelFuture handshake(Channel channel, ChannelPromise promise) {
		FullHttpRequest request = this.newHandshakeRequest();
		HttpResponseDecoder decoder = channel.pipeline().get(HttpResponseDecoder.class);
		if (decoder == null) {
			HttpClientCodec codec = channel.pipeline().get(HttpClientCodec.class);
			if (codec == null) {
				promise.setFailure(new IllegalStateException("ChannelPipeline does not contain a HttpResponseDecoder or HttpClientCodec"));
				return promise;
			}
		}

		channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture future) {
				if (future.isSuccess()) {
					ChannelPipeline p = future.channel().pipeline();
					ChannelHandlerContext ctx = p.context(HttpRequestEncoder.class);
					if (ctx == null) {
						ctx = p.context(HttpClientCodec.class);
					}

					if (ctx == null) {
						promise.setFailure(new IllegalStateException("ChannelPipeline does not contain a HttpRequestEncoder or HttpClientCodec"));
						return;
					}

					p.addAfter(ctx.name(), "ws-encoder", WebSocketClientHandshaker.this.newWebSocketEncoder());
					promise.setSuccess();
				} else {
					promise.setFailure(future.cause());
				}
			}
		});
		return promise;
	}

	protected abstract FullHttpRequest newHandshakeRequest();

	public final void finishHandshake(Channel channel, FullHttpResponse response) {
		this.verify(response);
		String receivedProtocol = response.headers().get(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);
		receivedProtocol = receivedProtocol != null ? receivedProtocol.trim() : null;
		String expectedProtocol = this.expectedSubprotocol != null ? this.expectedSubprotocol : "";
		boolean protocolValid = false;
		if (expectedProtocol.isEmpty() && receivedProtocol == null) {
			protocolValid = true;
			this.setActualSubprotocol(this.expectedSubprotocol);
		} else if (!expectedProtocol.isEmpty() && receivedProtocol != null && !receivedProtocol.isEmpty()) {
			for (String protocol : expectedProtocol.split(",")) {
				if (protocol.trim().equals(receivedProtocol)) {
					protocolValid = true;
					this.setActualSubprotocol(receivedProtocol);
					break;
				}
			}
		}

		if (!protocolValid) {
			throw new WebSocketHandshakeException(String.format("Invalid subprotocol. Actual: %s. Expected one of: %s", receivedProtocol, this.expectedSubprotocol));
		} else {
			this.setHandshakeComplete();
			final ChannelPipeline p = channel.pipeline();
			HttpContentDecompressor decompressor = p.get(HttpContentDecompressor.class);
			if (decompressor != null) {
				p.remove(decompressor);
			}

			HttpObjectAggregator aggregator = p.get(HttpObjectAggregator.class);
			if (aggregator != null) {
				p.remove(aggregator);
			}

			final ChannelHandlerContext ctx = p.context(HttpResponseDecoder.class);
			if (ctx == null) {
				ctx = p.context(HttpClientCodec.class);
				if (ctx == null) {
					throw new IllegalStateException("ChannelPipeline does not contain a HttpRequestEncoder or HttpClientCodec");
				}

				final HttpClientCodec codec = (HttpClientCodec)ctx.handler();
				codec.removeOutboundHandler();
				p.addAfter(ctx.name(), "ws-decoder", this.newWebsocketDecoder());
				channel.eventLoop().execute(new Runnable() {
					public void run() {
						p.remove(codec);
					}
				});
			} else {
				if (p.get(HttpRequestEncoder.class) != null) {
					p.remove(HttpRequestEncoder.class);
				}

				p.addAfter(ctx.name(), "ws-decoder", this.newWebsocketDecoder());
				channel.eventLoop().execute(new Runnable() {
					public void run() {
						p.remove(ctx.handler());
					}
				});
			}
		}
	}

	public final ChannelFuture processHandshake(Channel channel, HttpResponse response) {
		return this.processHandshake(channel, response, channel.newPromise());
	}

	public final ChannelFuture processHandshake(Channel channel, HttpResponse response, ChannelPromise promise) {
		if (response instanceof FullHttpResponse) {
			try {
				this.finishHandshake(channel, (FullHttpResponse)response);
				promise.setSuccess();
			} catch (Throwable var9) {
				promise.setFailure(var9);
			}
		} else {
			ChannelPipeline p = channel.pipeline();
			ChannelHandlerContext ctx = p.context(HttpResponseDecoder.class);
			if (ctx == null) {
				ctx = p.context(HttpClientCodec.class);
				if (ctx == null) {
					return promise.setFailure(new IllegalStateException("ChannelPipeline does not contain a HttpResponseDecoder or HttpClientCodec"));
				}
			}

			String aggregatorName = "httpAggregator";
			p.addAfter(ctx.name(), aggregatorName, new HttpObjectAggregator(8192));
			p.addAfter(aggregatorName, "handshaker", new SimpleChannelInboundHandler<FullHttpResponse>() {
				protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse msg) throws Exception {
					ctx.pipeline().remove(this);

					try {
						WebSocketClientHandshaker.this.finishHandshake(channel, msg);
						promise.setSuccess();
					} catch (Throwable var4) {
						promise.setFailure(var4);
					}
				}

				@Override
				public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
					ctx.pipeline().remove(this);
					promise.setFailure(cause);
				}

				@Override
				public void channelInactive(ChannelHandlerContext ctx) throws Exception {
					promise.tryFailure(WebSocketClientHandshaker.CLOSED_CHANNEL_EXCEPTION);
					ctx.fireChannelInactive();
				}
			});

			try {
				ctx.fireChannelRead(ReferenceCountUtil.retain(response));
			} catch (Throwable var8) {
				promise.setFailure(var8);
			}
		}

		return promise;
	}

	protected abstract void verify(FullHttpResponse fullHttpResponse);

	protected abstract WebSocketFrameDecoder newWebsocketDecoder();

	protected abstract WebSocketFrameEncoder newWebSocketEncoder();

	public ChannelFuture close(Channel channel, CloseWebSocketFrame frame) {
		if (channel == null) {
			throw new NullPointerException("channel");
		} else {
			return this.close(channel, frame, channel.newPromise());
		}
	}

	public ChannelFuture close(Channel channel, CloseWebSocketFrame frame, ChannelPromise promise) {
		if (channel == null) {
			throw new NullPointerException("channel");
		} else {
			return channel.writeAndFlush(frame, promise);
		}
	}

	static String rawPath(URI wsURL) {
		String path = wsURL.getRawPath();
		String query = wsURL.getRawQuery();
		if (query != null && !query.isEmpty()) {
			path = path + '?' + query;
		}

		return path != null && !path.isEmpty() ? path : "/";
	}

	static CharSequence websocketHostValue(URI wsURL) {
		int port = wsURL.getPort();
		if (port == -1) {
			return wsURL.getHost();
		} else {
			String host = wsURL.getHost();
			if (port == HttpScheme.HTTP.port()) {
				return !HttpScheme.HTTP.name().contentEquals(wsURL.getScheme()) && !WebSocketScheme.WS.name().contentEquals(wsURL.getScheme())
					? NetUtil.toSocketAddressString(host, port)
					: host;
			} else if (port != HttpScheme.HTTPS.port()) {
				return NetUtil.toSocketAddressString(host, port);
			} else {
				return !HttpScheme.HTTPS.name().contentEquals(wsURL.getScheme()) && !WebSocketScheme.WSS.name().contentEquals(wsURL.getScheme())
					? NetUtil.toSocketAddressString(host, port)
					: host;
			}
		}
	}

	static CharSequence websocketOriginValue(URI wsURL) {
		String scheme = wsURL.getScheme();
		int port = wsURL.getPort();
		String schemePrefix;
		int defaultPort;
		if (!WebSocketScheme.WSS.name().contentEquals(scheme)
			&& !HttpScheme.HTTPS.name().contentEquals(scheme)
			&& (scheme != null || port != WebSocketScheme.WSS.port())) {
			schemePrefix = HTTP_SCHEME_PREFIX;
			defaultPort = WebSocketScheme.WS.port();
		} else {
			schemePrefix = HTTPS_SCHEME_PREFIX;
			defaultPort = WebSocketScheme.WSS.port();
		}

		String host = wsURL.getHost().toLowerCase(Locale.US);
		return port != defaultPort && port != -1 ? schemePrefix + NetUtil.toSocketAddressString(host, port) : schemePrefix + host;
	}
}
