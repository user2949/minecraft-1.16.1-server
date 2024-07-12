package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.TypeParameterMatcher;

public abstract class MessageToByteEncoder<I> extends ChannelOutboundHandlerAdapter {
	private final TypeParameterMatcher matcher;
	private final boolean preferDirect;

	protected MessageToByteEncoder() {
		this(true);
	}

	protected MessageToByteEncoder(Class<? extends I> outboundMessageType) {
		this(outboundMessageType, true);
	}

	protected MessageToByteEncoder(boolean preferDirect) {
		this.matcher = TypeParameterMatcher.find(this, MessageToByteEncoder.class, "I");
		this.preferDirect = preferDirect;
	}

	protected MessageToByteEncoder(Class<? extends I> outboundMessageType, boolean preferDirect) {
		this.matcher = TypeParameterMatcher.get(outboundMessageType);
		this.preferDirect = preferDirect;
	}

	public boolean acceptOutboundMessage(Object msg) throws Exception {
		return this.matcher.match(msg);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		ByteBuf buf = null;

		try {
			if (this.acceptOutboundMessage(msg)) {
				I cast = (I)msg;
				buf = this.allocateBuffer(ctx, (I)msg, this.preferDirect);

				try {
					this.encode(ctx, cast, buf);
				} finally {
					ReferenceCountUtil.release(msg);
				}

				if (buf.isReadable()) {
					ctx.write(buf, promise);
				} else {
					buf.release();
					ctx.write(Unpooled.EMPTY_BUFFER, promise);
				}

				buf = null;
			} else {
				ctx.write(msg, promise);
			}
		} catch (EncoderException var17) {
			throw var17;
		} catch (Throwable var18) {
			throw new EncoderException(var18);
		} finally {
			if (buf != null) {
				buf.release();
			}
		}
	}

	protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, I msg, boolean preferDirect) throws Exception {
		return preferDirect ? ctx.alloc().ioBuffer() : ctx.alloc().heapBuffer();
	}

	protected abstract void encode(ChannelHandlerContext channelHandlerContext, I object, ByteBuf byteBuf) throws Exception;

	protected boolean isPreferDirect() {
		return this.preferDirect;
	}
}
