package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerUpgradeHandler;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

public final class CleartextHttp2ServerUpgradeHandler extends ChannelHandlerAdapter {
	private static final ByteBuf CONNECTION_PREFACE = Unpooled.unreleasableBuffer(Http2CodecUtil.connectionPrefaceBuf());
	private final HttpServerCodec httpServerCodec;
	private final HttpServerUpgradeHandler httpServerUpgradeHandler;
	private final ChannelHandler http2ServerHandler;

	public CleartextHttp2ServerUpgradeHandler(
		HttpServerCodec httpServerCodec, HttpServerUpgradeHandler httpServerUpgradeHandler, ChannelHandler http2ServerHandler
	) {
		this.httpServerCodec = ObjectUtil.checkNotNull(httpServerCodec, "httpServerCodec");
		this.httpServerUpgradeHandler = ObjectUtil.checkNotNull(httpServerUpgradeHandler, "httpServerUpgradeHandler");
		this.http2ServerHandler = ObjectUtil.checkNotNull(http2ServerHandler, "http2ServerHandler");
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		ctx.pipeline()
			.addBefore(ctx.name(), null, new CleartextHttp2ServerUpgradeHandler.PriorKnowledgeHandler())
			.addBefore(ctx.name(), null, this.httpServerCodec)
			.replace(this, null, this.httpServerUpgradeHandler);
	}

	private final class PriorKnowledgeHandler extends ByteToMessageDecoder {
		private PriorKnowledgeHandler() {
		}

		@Override
		protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
			int prefaceLength = CleartextHttp2ServerUpgradeHandler.CONNECTION_PREFACE.readableBytes();
			int bytesRead = Math.min(in.readableBytes(), prefaceLength);
			if (!ByteBufUtil.equals(
				CleartextHttp2ServerUpgradeHandler.CONNECTION_PREFACE, CleartextHttp2ServerUpgradeHandler.CONNECTION_PREFACE.readerIndex(), in, in.readerIndex(), bytesRead
			)) {
				ctx.pipeline().remove(this);
			} else if (bytesRead == prefaceLength) {
				ctx.pipeline().remove(CleartextHttp2ServerUpgradeHandler.this.httpServerCodec).remove(CleartextHttp2ServerUpgradeHandler.this.httpServerUpgradeHandler);
				ctx.pipeline().addAfter(ctx.name(), null, CleartextHttp2ServerUpgradeHandler.this.http2ServerHandler);
				ctx.pipeline().remove(this);
				ctx.fireUserEventTriggered(CleartextHttp2ServerUpgradeHandler.PriorKnowledgeUpgradeEvent.INSTANCE);
			}
		}
	}

	public static final class PriorKnowledgeUpgradeEvent {
		private static final CleartextHttp2ServerUpgradeHandler.PriorKnowledgeUpgradeEvent INSTANCE = new CleartextHttp2ServerUpgradeHandler.PriorKnowledgeUpgradeEvent(
			
		);

		private PriorKnowledgeUpgradeEvent() {
		}
	}
}
