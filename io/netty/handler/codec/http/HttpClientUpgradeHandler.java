package io.netty.handler.codec.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.util.AsciiString;
import io.netty.util.ReferenceCountUtil;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class HttpClientUpgradeHandler extends HttpObjectAggregator implements ChannelOutboundHandler {
	private final HttpClientUpgradeHandler.SourceCodec sourceCodec;
	private final HttpClientUpgradeHandler.UpgradeCodec upgradeCodec;
	private boolean upgradeRequested;

	public HttpClientUpgradeHandler(HttpClientUpgradeHandler.SourceCodec sourceCodec, HttpClientUpgradeHandler.UpgradeCodec upgradeCodec, int maxContentLength) {
		super(maxContentLength);
		if (sourceCodec == null) {
			throw new NullPointerException("sourceCodec");
		} else if (upgradeCodec == null) {
			throw new NullPointerException("upgradeCodec");
		} else {
			this.sourceCodec = sourceCodec;
			this.upgradeCodec = upgradeCodec;
		}
	}

	@Override
	public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
		ctx.bind(localAddress, promise);
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
		ctx.connect(remoteAddress, localAddress, promise);
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		ctx.disconnect(promise);
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		ctx.close(promise);
	}

	@Override
	public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		ctx.deregister(promise);
	}

	@Override
	public void read(ChannelHandlerContext ctx) throws Exception {
		ctx.read();
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		if (!(msg instanceof HttpRequest)) {
			ctx.write(msg, promise);
		} else if (this.upgradeRequested) {
			promise.setFailure(new IllegalStateException("Attempting to write HTTP request with upgrade in progress"));
		} else {
			this.upgradeRequested = true;
			this.setUpgradeRequestHeaders(ctx, (HttpRequest)msg);
			ctx.write(msg, promise);
			ctx.fireUserEventTriggered(HttpClientUpgradeHandler.UpgradeEvent.UPGRADE_ISSUED);
		}
	}

	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	protected void decode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
		FullHttpResponse response = null;

		try {
			if (!this.upgradeRequested) {
				throw new IllegalStateException("Read HTTP response without requesting protocol switch");
			}

			if (msg instanceof HttpResponse) {
				HttpResponse rep = (HttpResponse)msg;
				if (!HttpResponseStatus.SWITCHING_PROTOCOLS.equals(rep.status())) {
					ctx.fireUserEventTriggered(HttpClientUpgradeHandler.UpgradeEvent.UPGRADE_REJECTED);
					removeThisHandler(ctx);
					ctx.fireChannelRead(msg);
					return;
				}
			}

			if (msg instanceof FullHttpResponse) {
				response = (FullHttpResponse)msg;
				response.retain();
				out.add(response);
			} else {
				super.decode(ctx, msg, out);
				if (out.isEmpty()) {
					return;
				}

				assert out.size() == 1;

				response = (FullHttpResponse)out.get(0);
			}

			CharSequence upgradeHeader = response.headers().get(HttpHeaderNames.UPGRADE);
			if (upgradeHeader != null && !AsciiString.contentEqualsIgnoreCase(this.upgradeCodec.protocol(), upgradeHeader)) {
				throw new IllegalStateException("Switching Protocols response with unexpected UPGRADE protocol: " + upgradeHeader);
			}

			this.sourceCodec.prepareUpgradeFrom(ctx);
			this.upgradeCodec.upgradeTo(ctx, response);
			ctx.fireUserEventTriggered(HttpClientUpgradeHandler.UpgradeEvent.UPGRADE_SUCCESSFUL);
			this.sourceCodec.upgradeFrom(ctx);
			response.release();
			out.clear();
			removeThisHandler(ctx);
		} catch (Throwable var6) {
			ReferenceCountUtil.release(response);
			ctx.fireExceptionCaught(var6);
			removeThisHandler(ctx);
		}
	}

	private static void removeThisHandler(ChannelHandlerContext ctx) {
		ctx.pipeline().remove(ctx.name());
	}

	private void setUpgradeRequestHeaders(ChannelHandlerContext ctx, HttpRequest request) {
		request.headers().set(HttpHeaderNames.UPGRADE, this.upgradeCodec.protocol());
		Set<CharSequence> connectionParts = new LinkedHashSet(2);
		connectionParts.addAll(this.upgradeCodec.setUpgradeHeaders(ctx, request));
		StringBuilder builder = new StringBuilder();

		for (CharSequence part : connectionParts) {
			builder.append(part);
			builder.append(',');
		}

		builder.append(HttpHeaderValues.UPGRADE);
		request.headers().add(HttpHeaderNames.CONNECTION, builder.toString());
	}

	public interface SourceCodec {
		void prepareUpgradeFrom(ChannelHandlerContext channelHandlerContext);

		void upgradeFrom(ChannelHandlerContext channelHandlerContext);
	}

	public interface UpgradeCodec {
		CharSequence protocol();

		Collection<CharSequence> setUpgradeHeaders(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest);

		void upgradeTo(ChannelHandlerContext channelHandlerContext, FullHttpResponse fullHttpResponse) throws Exception;
	}

	public static enum UpgradeEvent {
		UPGRADE_ISSUED,
		UPGRADE_SUCCESSFUL,
		UPGRADE_REJECTED;
	}
}
