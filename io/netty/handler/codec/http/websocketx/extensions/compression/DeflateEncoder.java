package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
import java.util.List;

abstract class DeflateEncoder extends WebSocketExtensionEncoder {
	private final int compressionLevel;
	private final int windowSize;
	private final boolean noContext;
	private EmbeddedChannel encoder;

	public DeflateEncoder(int compressionLevel, int windowSize, boolean noContext) {
		this.compressionLevel = compressionLevel;
		this.windowSize = windowSize;
		this.noContext = noContext;
	}

	protected abstract int rsv(WebSocketFrame webSocketFrame);

	protected abstract boolean removeFrameTail(WebSocketFrame webSocketFrame);

	protected void encode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
		if (this.encoder == null) {
			this.encoder = new EmbeddedChannel(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.NONE, this.compressionLevel, this.windowSize, 8));
		}

		this.encoder.writeOutbound(msg.content().retain());
		CompositeByteBuf fullCompressedContent = ctx.alloc().compositeBuffer();

		while (true) {
			ByteBuf partCompressedContent = this.encoder.readOutbound();
			if (partCompressedContent == null) {
				if (fullCompressedContent.numComponents() <= 0) {
					fullCompressedContent.release();
					throw new CodecException("cannot read compressed buffer");
				}

				if (msg.isFinalFragment() && this.noContext) {
					this.cleanup();
				}

				if (this.removeFrameTail(msg)) {
					int realLength = fullCompressedContent.readableBytes() - PerMessageDeflateDecoder.FRAME_TAIL.length;
					partCompressedContent = fullCompressedContent.slice(0, realLength);
				} else {
					partCompressedContent = fullCompressedContent;
				}

				WebSocketFrame outMsg;
				if (msg instanceof TextWebSocketFrame) {
					outMsg = new TextWebSocketFrame(msg.isFinalFragment(), this.rsv(msg), partCompressedContent);
				} else if (msg instanceof BinaryWebSocketFrame) {
					outMsg = new BinaryWebSocketFrame(msg.isFinalFragment(), this.rsv(msg), partCompressedContent);
				} else {
					if (!(msg instanceof ContinuationWebSocketFrame)) {
						throw new CodecException("unexpected frame type: " + msg.getClass().getName());
					}

					outMsg = new ContinuationWebSocketFrame(msg.isFinalFragment(), this.rsv(msg), partCompressedContent);
				}

				out.add(outMsg);
				return;
			}

			if (!partCompressedContent.isReadable()) {
				partCompressedContent.release();
			} else {
				fullCompressedContent.addComponent(true, partCompressedContent);
			}
		}
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		this.cleanup();
		super.handlerRemoved(ctx);
	}

	private void cleanup() {
		if (this.encoder != null) {
			if (this.encoder.finish()) {
				while (true) {
					ByteBuf buf = this.encoder.readOutbound();
					if (buf == null) {
						break;
					}

					buf.release();
				}
			}

			this.encoder = null;
		}
	}
}
