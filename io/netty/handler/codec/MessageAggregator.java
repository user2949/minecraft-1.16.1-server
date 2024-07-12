package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.util.ReferenceCountUtil;
import java.util.List;

public abstract class MessageAggregator<I, S, C extends ByteBufHolder, O extends ByteBufHolder> extends MessageToMessageDecoder<I> {
	private static final int DEFAULT_MAX_COMPOSITEBUFFER_COMPONENTS = 1024;
	private final int maxContentLength;
	private O currentMessage;
	private boolean handlingOversizedMessage;
	private int maxCumulationBufferComponents = 1024;
	private ChannelHandlerContext ctx;
	private ChannelFutureListener continueResponseWriteListener;

	protected MessageAggregator(int maxContentLength) {
		validateMaxContentLength(maxContentLength);
		this.maxContentLength = maxContentLength;
	}

	protected MessageAggregator(int maxContentLength, Class<? extends I> inboundMessageType) {
		super(inboundMessageType);
		validateMaxContentLength(maxContentLength);
		this.maxContentLength = maxContentLength;
	}

	private static void validateMaxContentLength(int maxContentLength) {
		if (maxContentLength < 0) {
			throw new IllegalArgumentException("maxContentLength: " + maxContentLength + " (expected: >= 0)");
		}
	}

	@Override
	public boolean acceptInboundMessage(Object msg) throws Exception {
		return !super.acceptInboundMessage(msg) ? false : (this.isContentMessage((I)msg) || this.isStartMessage((I)msg)) && !this.isAggregated((I)msg);
	}

	protected abstract boolean isStartMessage(I object) throws Exception;

	protected abstract boolean isContentMessage(I object) throws Exception;

	protected abstract boolean isLastContentMessage(C byteBufHolder) throws Exception;

	protected abstract boolean isAggregated(I object) throws Exception;

	public final int maxContentLength() {
		return this.maxContentLength;
	}

	public final int maxCumulationBufferComponents() {
		return this.maxCumulationBufferComponents;
	}

	public final void setMaxCumulationBufferComponents(int maxCumulationBufferComponents) {
		if (maxCumulationBufferComponents < 2) {
			throw new IllegalArgumentException("maxCumulationBufferComponents: " + maxCumulationBufferComponents + " (expected: >= 2)");
		} else if (this.ctx == null) {
			this.maxCumulationBufferComponents = maxCumulationBufferComponents;
		} else {
			throw new IllegalStateException("decoder properties cannot be changed once the decoder is added to a pipeline.");
		}
	}

	@Deprecated
	public final boolean isHandlingOversizedMessage() {
		return this.handlingOversizedMessage;
	}

	protected final ChannelHandlerContext ctx() {
		if (this.ctx == null) {
			throw new IllegalStateException("not added to a pipeline yet");
		} else {
			return this.ctx;
		}
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, I msg, List<Object> out) throws Exception {
		if (this.isStartMessage(msg)) {
			this.handlingOversizedMessage = false;
			if (this.currentMessage != null) {
				this.currentMessage.release();
				this.currentMessage = null;
				throw new MessageAggregationException();
			}

			Object continueResponse = this.newContinueResponse((S)msg, this.maxContentLength, ctx.pipeline());
			if (continueResponse != null) {
				ChannelFutureListener listener = this.continueResponseWriteListener;
				if (listener == null) {
					this.continueResponseWriteListener = listener = new ChannelFutureListener() {
						public void operationComplete(ChannelFuture future) throws Exception {
							if (!future.isSuccess()) {
								ctx.fireExceptionCaught(future.cause());
							}
						}
					};
				}

				boolean closeAfterWrite = this.closeAfterContinueResponse(continueResponse);
				this.handlingOversizedMessage = this.ignoreContentAfterContinueResponse(continueResponse);
				ChannelFuture future = ctx.writeAndFlush(continueResponse).addListener(listener);
				if (closeAfterWrite) {
					future.addListener(ChannelFutureListener.CLOSE);
					return;
				}

				if (this.handlingOversizedMessage) {
					return;
				}
			} else if (this.isContentLengthInvalid((S)msg, this.maxContentLength)) {
				this.invokeHandleOversizedMessage(ctx, (S)msg);
				return;
			}

			if (msg instanceof DecoderResultProvider && !((DecoderResultProvider)msg).decoderResult().isSuccess()) {
				O aggregated;
				if (msg instanceof ByteBufHolder) {
					aggregated = this.beginAggregation((S)msg, ((ByteBufHolder)msg).content().retain());
				} else {
					aggregated = this.beginAggregation((S)msg, Unpooled.EMPTY_BUFFER);
				}

				this.finishAggregation(aggregated);
				out.add(aggregated);
				return;
			}

			CompositeByteBuf content = ctx.alloc().compositeBuffer(this.maxCumulationBufferComponents);
			if (msg instanceof ByteBufHolder) {
				appendPartialContent(content, ((ByteBufHolder)msg).content());
			}

			this.currentMessage = this.beginAggregation((S)msg, content);
		} else {
			if (!this.isContentMessage(msg)) {
				throw new MessageAggregationException();
			}

			if (this.currentMessage == null) {
				return;
			}

			CompositeByteBuf content = (CompositeByteBuf)this.currentMessage.content();
			C m = (C)((ByteBufHolder)msg);
			if (content.readableBytes() > this.maxContentLength - m.content().readableBytes()) {
				S s = (S)this.currentMessage;
				this.invokeHandleOversizedMessage(ctx, s);
				return;
			}

			appendPartialContent(content, m.content());
			this.aggregate(this.currentMessage, m);
			boolean last;
			if (m instanceof DecoderResultProvider) {
				DecoderResult decoderResult = ((DecoderResultProvider)m).decoderResult();
				if (!decoderResult.isSuccess()) {
					if (this.currentMessage instanceof DecoderResultProvider) {
						((DecoderResultProvider)this.currentMessage).setDecoderResult(DecoderResult.failure(decoderResult.cause()));
					}

					last = true;
				} else {
					last = this.isLastContentMessage(m);
				}
			} else {
				last = this.isLastContentMessage(m);
			}

			if (last) {
				this.finishAggregation(this.currentMessage);
				out.add(this.currentMessage);
				this.currentMessage = null;
			}
		}
	}

	private static void appendPartialContent(CompositeByteBuf content, ByteBuf partialContent) {
		if (partialContent.isReadable()) {
			content.addComponent(true, partialContent.retain());
		}
	}

	protected abstract boolean isContentLengthInvalid(S object, int integer) throws Exception;

	protected abstract Object newContinueResponse(S object, int integer, ChannelPipeline channelPipeline) throws Exception;

	protected abstract boolean closeAfterContinueResponse(Object object) throws Exception;

	protected abstract boolean ignoreContentAfterContinueResponse(Object object) throws Exception;

	protected abstract O beginAggregation(S object, ByteBuf byteBuf) throws Exception;

	protected void aggregate(O aggregated, C content) throws Exception {
	}

	protected void finishAggregation(O aggregated) throws Exception {
	}

	private void invokeHandleOversizedMessage(ChannelHandlerContext ctx, S oversized) throws Exception {
		this.handlingOversizedMessage = true;
		this.currentMessage = null;

		try {
			this.handleOversizedMessage(ctx, oversized);
		} finally {
			ReferenceCountUtil.release(oversized);
		}
	}

	protected void handleOversizedMessage(ChannelHandlerContext ctx, S oversized) throws Exception {
		ctx.fireExceptionCaught(new TooLongFrameException("content length exceeded " + this.maxContentLength() + " bytes."));
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		if (this.currentMessage != null && !ctx.channel().config().isAutoRead()) {
			ctx.read();
		}

		ctx.fireChannelReadComplete();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		try {
			super.channelInactive(ctx);
		} finally {
			this.releaseCurrentMessage();
		}
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		this.ctx = ctx;
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		try {
			super.handlerRemoved(ctx);
		} finally {
			this.releaseCurrentMessage();
		}
	}

	private void releaseCurrentMessage() {
		if (this.currentMessage != null) {
			this.currentMessage.release();
			this.currentMessage = null;
			this.handlingOversizedMessage = false;
		}
	}
}
