package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.util.internal.StringUtil;
import java.util.List;

public abstract class ByteToMessageDecoder extends ChannelInboundHandlerAdapter {
	public static final ByteToMessageDecoder.Cumulator MERGE_CUMULATOR = new ByteToMessageDecoder.Cumulator() {
		@Override
		public ByteBuf cumulate(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf in) {
			ByteBuf buffer;
			if (cumulation.writerIndex() <= cumulation.maxCapacity() - in.readableBytes() && cumulation.refCnt() <= 1 && !cumulation.isReadOnly()) {
				buffer = cumulation;
			} else {
				buffer = ByteToMessageDecoder.expandCumulation(alloc, cumulation, in.readableBytes());
			}

			buffer.writeBytes(in);
			in.release();
			return buffer;
		}
	};
	public static final ByteToMessageDecoder.Cumulator COMPOSITE_CUMULATOR = new ByteToMessageDecoder.Cumulator() {
		@Override
		public ByteBuf cumulate(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf in) {
			ByteBuf buffer;
			if (cumulation.refCnt() > 1) {
				buffer = ByteToMessageDecoder.expandCumulation(alloc, cumulation, in.readableBytes());
				buffer.writeBytes(in);
				in.release();
			} else {
				CompositeByteBuf composite;
				if (cumulation instanceof CompositeByteBuf) {
					composite = (CompositeByteBuf)cumulation;
				} else {
					composite = alloc.compositeBuffer(Integer.MAX_VALUE);
					composite.addComponent(true, cumulation);
				}

				composite.addComponent(true, in);
				buffer = composite;
			}

			return buffer;
		}
	};
	private static final byte STATE_INIT = 0;
	private static final byte STATE_CALLING_CHILD_DECODE = 1;
	private static final byte STATE_HANDLER_REMOVED_PENDING = 2;
	ByteBuf cumulation;
	private ByteToMessageDecoder.Cumulator cumulator = MERGE_CUMULATOR;
	private boolean singleDecode;
	private boolean decodeWasNull;
	private boolean first;
	private byte decodeState = 0;
	private int discardAfterReads = 16;
	private int numReads;

	protected ByteToMessageDecoder() {
		this.ensureNotSharable();
	}

	public void setSingleDecode(boolean singleDecode) {
		this.singleDecode = singleDecode;
	}

	public boolean isSingleDecode() {
		return this.singleDecode;
	}

	public void setCumulator(ByteToMessageDecoder.Cumulator cumulator) {
		if (cumulator == null) {
			throw new NullPointerException("cumulator");
		} else {
			this.cumulator = cumulator;
		}
	}

	public void setDiscardAfterReads(int discardAfterReads) {
		if (discardAfterReads <= 0) {
			throw new IllegalArgumentException("discardAfterReads must be > 0");
		} else {
			this.discardAfterReads = discardAfterReads;
		}
	}

	protected int actualReadableBytes() {
		return this.internalBuffer().readableBytes();
	}

	protected ByteBuf internalBuffer() {
		return this.cumulation != null ? this.cumulation : Unpooled.EMPTY_BUFFER;
	}

	@Override
	public final void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		if (this.decodeState == 1) {
			this.decodeState = 2;
		} else {
			ByteBuf buf = this.cumulation;
			if (buf != null) {
				this.cumulation = null;
				int readable = buf.readableBytes();
				if (readable > 0) {
					ByteBuf bytes = buf.readBytes(readable);
					buf.release();
					ctx.fireChannelRead(bytes);
				} else {
					buf.release();
				}

				this.numReads = 0;
				ctx.fireChannelReadComplete();
			}

			this.handlerRemoved0(ctx);
		}
	}

	protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof ByteBuf) {
			CodecOutputList out = CodecOutputList.newInstance();

			try {
				ByteBuf data = (ByteBuf)msg;
				this.first = this.cumulation == null;
				if (this.first) {
					this.cumulation = data;
				} else {
					this.cumulation = this.cumulator.cumulate(ctx.alloc(), this.cumulation, data);
				}

				this.callDecode(ctx, this.cumulation, out);
			} catch (DecoderException var10) {
				throw var10;
			} catch (Exception var11) {
				throw new DecoderException(var11);
			} finally {
				if (this.cumulation != null && !this.cumulation.isReadable()) {
					this.numReads = 0;
					this.cumulation.release();
					this.cumulation = null;
				} else if (++this.numReads >= this.discardAfterReads) {
					this.numReads = 0;
					this.discardSomeReadBytes();
				}

				int size = out.size();
				this.decodeWasNull = !out.insertSinceRecycled();
				fireChannelRead(ctx, out, size);
				out.recycle();
			}
		} else {
			ctx.fireChannelRead(msg);
		}
	}

	static void fireChannelRead(ChannelHandlerContext ctx, List<Object> msgs, int numElements) {
		if (msgs instanceof CodecOutputList) {
			fireChannelRead(ctx, (CodecOutputList)msgs, numElements);
		} else {
			for (int i = 0; i < numElements; i++) {
				ctx.fireChannelRead(msgs.get(i));
			}
		}
	}

	static void fireChannelRead(ChannelHandlerContext ctx, CodecOutputList msgs, int numElements) {
		for (int i = 0; i < numElements; i++) {
			ctx.fireChannelRead(msgs.getUnsafe(i));
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		this.numReads = 0;
		this.discardSomeReadBytes();
		if (this.decodeWasNull) {
			this.decodeWasNull = false;
			if (!ctx.channel().config().isAutoRead()) {
				ctx.read();
			}
		}

		ctx.fireChannelReadComplete();
	}

	protected final void discardSomeReadBytes() {
		if (this.cumulation != null && !this.first && this.cumulation.refCnt() == 1) {
			this.cumulation.discardSomeReadBytes();
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		this.channelInputClosed(ctx, true);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof ChannelInputShutdownEvent) {
			this.channelInputClosed(ctx, false);
		}

		super.userEventTriggered(ctx, evt);
	}

	private void channelInputClosed(ChannelHandlerContext ctx, boolean callChannelInactive) throws Exception {
		CodecOutputList out = CodecOutputList.newInstance();

		try {
			this.channelInputClosed(ctx, out);
		} catch (DecoderException var24) {
			throw var24;
		} catch (Exception var25) {
			throw new DecoderException(var25);
		} finally {
			try {
				if (this.cumulation != null) {
					this.cumulation.release();
					this.cumulation = null;
				}

				int size = out.size();
				fireChannelRead(ctx, out, size);
				if (size > 0) {
					ctx.fireChannelReadComplete();
				}

				if (callChannelInactive) {
					ctx.fireChannelInactive();
				}
			} finally {
				out.recycle();
			}
		}
	}

	void channelInputClosed(ChannelHandlerContext ctx, List<Object> out) throws Exception {
		if (this.cumulation != null) {
			this.callDecode(ctx, this.cumulation, out);
			this.decodeLast(ctx, this.cumulation, out);
		} else {
			this.decodeLast(ctx, Unpooled.EMPTY_BUFFER, out);
		}
	}

	protected void callDecode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
		try {
			while (in.isReadable()) {
				int outSize = out.size();
				if (outSize > 0) {
					fireChannelRead(ctx, out, outSize);
					out.clear();
					if (ctx.isRemoved()) {
						break;
					}

					outSize = 0;
				}

				int oldInputLength = in.readableBytes();
				this.decodeRemovalReentryProtection(ctx, in, out);
				if (!ctx.isRemoved()) {
					if (outSize == out.size()) {
						if (oldInputLength != in.readableBytes()) {
							continue;
						}
					} else {
						if (oldInputLength == in.readableBytes()) {
							throw new DecoderException(StringUtil.simpleClassName(this.getClass()) + ".decode() did not read anything but decoded a message.");
						}

						if (!this.isSingleDecode()) {
							continue;
						}
					}
				}
				break;
			}
		} catch (DecoderException var6) {
			throw var6;
		} catch (Exception var7) {
			throw new DecoderException(var7);
		}
	}

	protected abstract void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception;

	final void decodeRemovalReentryProtection(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		this.decodeState = 1;

		try {
			this.decode(ctx, in, out);
		} finally {
			boolean removePending = this.decodeState == 2;
			this.decodeState = 0;
			if (removePending) {
				this.handlerRemoved(ctx);
			}
		}
	}

	protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.isReadable()) {
			this.decodeRemovalReentryProtection(ctx, in, out);
		}
	}

	static ByteBuf expandCumulation(ByteBufAllocator alloc, ByteBuf cumulation, int readable) {
		ByteBuf var4 = alloc.buffer(cumulation.readableBytes() + readable);
		var4.writeBytes(cumulation);
		cumulation.release();
		return var4;
	}

	public interface Cumulator {
		ByteBuf cumulate(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf2, ByteBuf byteBuf3);
	}
}
