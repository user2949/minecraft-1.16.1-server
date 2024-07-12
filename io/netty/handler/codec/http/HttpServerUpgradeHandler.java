package io.netty.handler.codec.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AsciiString;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HttpServerUpgradeHandler extends HttpObjectAggregator {
	private final HttpServerUpgradeHandler.SourceCodec sourceCodec;
	private final HttpServerUpgradeHandler.UpgradeCodecFactory upgradeCodecFactory;
	private boolean handlingUpgrade;

	public HttpServerUpgradeHandler(HttpServerUpgradeHandler.SourceCodec sourceCodec, HttpServerUpgradeHandler.UpgradeCodecFactory upgradeCodecFactory) {
		this(sourceCodec, upgradeCodecFactory, 0);
	}

	public HttpServerUpgradeHandler(
		HttpServerUpgradeHandler.SourceCodec sourceCodec, HttpServerUpgradeHandler.UpgradeCodecFactory upgradeCodecFactory, int maxContentLength
	) {
		super(maxContentLength);
		this.sourceCodec = ObjectUtil.checkNotNull(sourceCodec, "sourceCodec");
		this.upgradeCodecFactory = ObjectUtil.checkNotNull(upgradeCodecFactory, "upgradeCodecFactory");
	}

	protected void decode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
		this.handlingUpgrade = this.handlingUpgrade | isUpgradeRequest(msg);
		if (!this.handlingUpgrade) {
			ReferenceCountUtil.retain(msg);
			out.add(msg);
		} else {
			FullHttpRequest fullRequest;
			if (msg instanceof FullHttpRequest) {
				fullRequest = (FullHttpRequest)msg;
				ReferenceCountUtil.retain(msg);
				out.add(msg);
			} else {
				super.decode(ctx, msg, out);
				if (out.isEmpty()) {
					return;
				}

				assert out.size() == 1;

				this.handlingUpgrade = false;
				fullRequest = (FullHttpRequest)out.get(0);
			}

			if (this.upgrade(ctx, fullRequest)) {
				out.clear();
			}
		}
	}

	private static boolean isUpgradeRequest(HttpObject msg) {
		return msg instanceof HttpRequest && ((HttpRequest)msg).headers().get(HttpHeaderNames.UPGRADE) != null;
	}

	private boolean upgrade(ChannelHandlerContext ctx, FullHttpRequest request) {
		List<CharSequence> requestedProtocols = splitHeader(request.headers().get(HttpHeaderNames.UPGRADE));
		int numRequestedProtocols = requestedProtocols.size();
		HttpServerUpgradeHandler.UpgradeCodec upgradeCodec = null;
		CharSequence upgradeProtocol = null;

		for (int i = 0; i < numRequestedProtocols; i++) {
			CharSequence p = (CharSequence)requestedProtocols.get(i);
			HttpServerUpgradeHandler.UpgradeCodec c = this.upgradeCodecFactory.newUpgradeCodec(p);
			if (c != null) {
				upgradeProtocol = p;
				upgradeCodec = c;
				break;
			}
		}

		if (upgradeCodec == null) {
			return false;
		} else {
			CharSequence connectionHeader = request.headers().get(HttpHeaderNames.CONNECTION);
			if (connectionHeader == null) {
				return false;
			} else {
				Collection<CharSequence> requiredHeaders = upgradeCodec.requiredUpgradeHeaders();
				List<CharSequence> values = splitHeader(connectionHeader);
				if (AsciiString.containsContentEqualsIgnoreCase(values, HttpHeaderNames.UPGRADE) && AsciiString.containsAllContentEqualsIgnoreCase(values, requiredHeaders)
					)
				 {
					for (CharSequence requiredHeader : requiredHeaders) {
						if (!request.headers().contains(requiredHeader)) {
							return false;
						}
					}

					FullHttpResponse upgradeResponse = createUpgradeResponse(upgradeProtocol);
					if (!upgradeCodec.prepareUpgradeResponse(ctx, request, upgradeResponse.headers())) {
						return false;
					} else {
						HttpServerUpgradeHandler.UpgradeEvent event = new HttpServerUpgradeHandler.UpgradeEvent(upgradeProtocol, request);

						try {
							ChannelFuture writeComplete = ctx.writeAndFlush(upgradeResponse);
							this.sourceCodec.upgradeFrom(ctx);
							upgradeCodec.upgradeTo(ctx, request);
							ctx.pipeline().remove(this);
							ctx.fireUserEventTriggered(event.retain());
							writeComplete.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
						} finally {
							event.release();
						}

						return true;
					}
				} else {
					return false;
				}
			}
		}
	}

	private static FullHttpResponse createUpgradeResponse(CharSequence upgradeProtocol) {
		DefaultFullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.SWITCHING_PROTOCOLS, Unpooled.EMPTY_BUFFER, false);
		res.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE);
		res.headers().add(HttpHeaderNames.UPGRADE, upgradeProtocol);
		return res;
	}

	private static List<CharSequence> splitHeader(CharSequence header) {
		StringBuilder builder = new StringBuilder(header.length());
		List<CharSequence> protocols = new ArrayList(4);

		for (int i = 0; i < header.length(); i++) {
			char c = header.charAt(i);
			if (!Character.isWhitespace(c)) {
				if (c == ',') {
					protocols.add(builder.toString());
					builder.setLength(0);
				} else {
					builder.append(c);
				}
			}
		}

		if (builder.length() > 0) {
			protocols.add(builder.toString());
		}

		return protocols;
	}

	public interface SourceCodec {
		void upgradeFrom(ChannelHandlerContext channelHandlerContext);
	}

	public interface UpgradeCodec {
		Collection<CharSequence> requiredUpgradeHeaders();

		boolean prepareUpgradeResponse(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest, HttpHeaders httpHeaders);

		void upgradeTo(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest);
	}

	public interface UpgradeCodecFactory {
		HttpServerUpgradeHandler.UpgradeCodec newUpgradeCodec(CharSequence charSequence);
	}

	public static final class UpgradeEvent implements ReferenceCounted {
		private final CharSequence protocol;
		private final FullHttpRequest upgradeRequest;

		UpgradeEvent(CharSequence protocol, FullHttpRequest upgradeRequest) {
			this.protocol = protocol;
			this.upgradeRequest = upgradeRequest;
		}

		public CharSequence protocol() {
			return this.protocol;
		}

		public FullHttpRequest upgradeRequest() {
			return this.upgradeRequest;
		}

		@Override
		public int refCnt() {
			return this.upgradeRequest.refCnt();
		}

		public HttpServerUpgradeHandler.UpgradeEvent retain() {
			this.upgradeRequest.retain();
			return this;
		}

		public HttpServerUpgradeHandler.UpgradeEvent retain(int increment) {
			this.upgradeRequest.retain(increment);
			return this;
		}

		public HttpServerUpgradeHandler.UpgradeEvent touch() {
			this.upgradeRequest.touch();
			return this;
		}

		public HttpServerUpgradeHandler.UpgradeEvent touch(Object hint) {
			this.upgradeRequest.touch(hint);
			return this;
		}

		@Override
		public boolean release() {
			return this.upgradeRequest.release();
		}

		@Override
		public boolean release(int decrement) {
			return this.upgradeRequest.release(decrement);
		}

		public String toString() {
			return "UpgradeEvent [protocol=" + this.protocol + ", upgradeRequest=" + this.upgradeRequest + ']';
		}
	}
}
