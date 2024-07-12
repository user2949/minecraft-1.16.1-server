package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.DefaultChannelPipeline;
import io.netty.channel.DefaultMaxMessagesRecvByteBufAllocator;
import io.netty.channel.EventLoop;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.VoidChannelPromise;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.Channel.Unsafe;
import io.netty.channel.DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle;
import io.netty.channel.RecvByteBufAllocator.ExtendedHandle;
import io.netty.channel.RecvByteBufAllocator.Handle;
import io.netty.handler.codec.http2.Http2FrameCodec.DefaultHttp2FrameStream;
import io.netty.util.DefaultAttributeMap;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.ThrowableUtil;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;

public class Http2MultiplexCodec extends Http2FrameCodec {
	private static final ChannelFutureListener CHILD_CHANNEL_REGISTRATION_LISTENER = new ChannelFutureListener() {
		public void operationComplete(ChannelFuture future) throws Exception {
			Http2MultiplexCodec.registerDone(future);
		}
	};
	private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
	private static final ClosedChannelException CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), Http2MultiplexCodec.DefaultHttp2StreamChannel.Http2ChannelUnsafe.class, "write(...)"
	);
	private static final int MIN_HTTP2_FRAME_SIZE = 9;
	private final ChannelHandler inboundStreamHandler;
	private int initialOutboundStreamWindow = 65535;
	private boolean parentReadInProgress;
	private int idCount;
	private Http2MultiplexCodec.DefaultHttp2StreamChannel head;
	private Http2MultiplexCodec.DefaultHttp2StreamChannel tail;
	volatile ChannelHandlerContext ctx;

	Http2MultiplexCodec(Http2ConnectionEncoder encoder, Http2ConnectionDecoder decoder, Http2Settings initialSettings, ChannelHandler inboundStreamHandler) {
		super(encoder, decoder, initialSettings);
		this.inboundStreamHandler = inboundStreamHandler;
	}

	private static void registerDone(ChannelFuture future) {
		if (!future.isSuccess()) {
			Channel childChannel = future.channel();
			if (childChannel.isRegistered()) {
				childChannel.close();
			} else {
				childChannel.unsafe().closeForcibly();
			}
		}
	}

	@Override
	public final void handlerAdded0(ChannelHandlerContext ctx) throws Exception {
		if (ctx.executor() != ctx.channel().eventLoop()) {
			throw new IllegalStateException("EventExecutor must be EventLoop of Channel");
		} else {
			this.ctx = ctx;
		}
	}

	@Override
	public final void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved0(ctx);
		Http2MultiplexCodec.DefaultHttp2StreamChannel ch = this.head;

		while (ch != null) {
			Http2MultiplexCodec.DefaultHttp2StreamChannel curr = ch;
			ch = ch.next;
			curr.next = null;
		}

		this.head = this.tail = null;
	}

	Http2MultiplexCodec.Http2MultiplexCodecStream newStream() {
		return new Http2MultiplexCodec.Http2MultiplexCodecStream();
	}

	@Override
	final void onHttp2Frame(ChannelHandlerContext ctx, Http2Frame frame) {
		if (frame instanceof Http2StreamFrame) {
			Http2StreamFrame streamFrame = (Http2StreamFrame)frame;
			this.onHttp2StreamFrame(((Http2MultiplexCodec.Http2MultiplexCodecStream)streamFrame.stream()).channel, streamFrame);
		} else if (frame instanceof Http2GoAwayFrame) {
			this.onHttp2GoAwayFrame(ctx, (Http2GoAwayFrame)frame);
			ctx.fireChannelRead(frame);
		} else if (frame instanceof Http2SettingsFrame) {
			Http2Settings settings = ((Http2SettingsFrame)frame).settings();
			if (settings.initialWindowSize() != null) {
				this.initialOutboundStreamWindow = settings.initialWindowSize();
			}

			ctx.fireChannelRead(frame);
		} else {
			ctx.fireChannelRead(frame);
		}
	}

	@Override
	final void onHttp2StreamStateChanged(ChannelHandlerContext ctx, Http2FrameStream stream) {
		Http2MultiplexCodec.Http2MultiplexCodecStream s = (Http2MultiplexCodec.Http2MultiplexCodecStream)stream;
		switch (stream.state()) {
			case HALF_CLOSED_REMOTE:
			case OPEN:
				if (s.channel == null) {
					ChannelFuture future = ctx.channel().eventLoop().register(new Http2MultiplexCodec.DefaultHttp2StreamChannel(s, false));
					if (future.isDone()) {
						registerDone(future);
					} else {
						future.addListener(CHILD_CHANNEL_REGISTRATION_LISTENER);
					}
				}
				break;
			case CLOSED:
				Http2MultiplexCodec.DefaultHttp2StreamChannel channel = s.channel;
				if (channel != null) {
					channel.streamClosed();
				}
		}
	}

	@Override
	final void onHttp2StreamWritabilityChanged(ChannelHandlerContext ctx, Http2FrameStream stream, boolean writable) {
		((Http2MultiplexCodec.Http2MultiplexCodecStream)stream).channel.writabilityChanged(writable);
	}

	final Http2StreamChannel newOutboundStream() {
		return new Http2MultiplexCodec.DefaultHttp2StreamChannel(this.newStream(), true);
	}

	@Override
	final void onHttp2FrameStreamException(ChannelHandlerContext ctx, Http2FrameStreamException cause) {
		Http2FrameStream stream = cause.stream();
		Http2MultiplexCodec.DefaultHttp2StreamChannel childChannel = ((Http2MultiplexCodec.Http2MultiplexCodecStream)stream).channel;

		try {
			childChannel.pipeline().fireExceptionCaught(cause.getCause());
		} finally {
			childChannel.unsafe().closeForcibly();
		}
	}

	private void onHttp2StreamFrame(Http2MultiplexCodec.DefaultHttp2StreamChannel childChannel, Http2StreamFrame frame) {
		switch (childChannel.fireChildRead(frame)) {
			case READ_PROCESSED_BUT_STOP_READING:
				childChannel.fireChildReadComplete();
				break;
			case READ_PROCESSED_OK_TO_PROCESS_MORE:
				this.addChildChannelToReadPendingQueue(childChannel);
			case READ_IGNORED_CHANNEL_INACTIVE:
			case READ_QUEUED:
				break;
			default:
				throw new Error();
		}
	}

	final void addChildChannelToReadPendingQueue(Http2MultiplexCodec.DefaultHttp2StreamChannel childChannel) {
		if (!childChannel.fireChannelReadPending) {
			assert childChannel.next == null;

			if (this.tail == null) {
				assert this.head == null;

				this.tail = this.head = childChannel;
			} else {
				this.tail.next = childChannel;
				this.tail = childChannel;
			}

			childChannel.fireChannelReadPending = true;
		}
	}

	private void onHttp2GoAwayFrame(ChannelHandlerContext ctx, Http2GoAwayFrame goAwayFrame) {
		try {
			this.forEachActiveStream(new Http2FrameStreamVisitor() {
				@Override
				public boolean visit(Http2FrameStream stream) {
					int streamId = stream.id();
					Http2MultiplexCodec.DefaultHttp2StreamChannel childChannel = ((Http2MultiplexCodec.Http2MultiplexCodecStream)stream).channel;
					if (streamId > goAwayFrame.lastStreamId() && Http2MultiplexCodec.this.connection().local().isValidStreamId(streamId)) {
						childChannel.pipeline().fireUserEventTriggered(goAwayFrame.retainedDuplicate());
					}

					return true;
				}
			});
		} catch (Http2Exception var4) {
			ctx.fireExceptionCaught(var4);
			ctx.close();
		}
	}

	@Override
	public final void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		this.parentReadInProgress = false;
		this.onChannelReadComplete(ctx);
		this.channelReadComplete0(ctx);
	}

	@Override
	public final void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		this.parentReadInProgress = true;
		super.channelRead(ctx, msg);
	}

	final void onChannelReadComplete(ChannelHandlerContext ctx) {
		try {
			for (Http2MultiplexCodec.DefaultHttp2StreamChannel current = this.head; current != null; current = current.next) {
				if (current.fireChannelReadPending) {
					current.fireChannelReadPending = false;
					current.fireChildReadComplete();
				}

				current.next = null;
			}
		} finally {
			this.tail = this.head = null;
			this.flush0(ctx);
		}
	}

	void flush0(ChannelHandlerContext ctx) {
		this.flush(ctx);
	}

	boolean onBytesConsumed(ChannelHandlerContext ctx, Http2FrameStream stream, int bytes) throws Http2Exception {
		return this.consumeBytes(stream.id(), bytes);
	}

	private boolean initialWritability(DefaultHttp2FrameStream stream) {
		return !Http2CodecUtil.isStreamIdValid(stream.id()) || this.isWritable(stream);
	}

	private final class DefaultHttp2StreamChannel extends DefaultAttributeMap implements Http2StreamChannel {
		private final Http2MultiplexCodec.DefaultHttp2StreamChannel.Http2StreamChannelConfig config = new Http2MultiplexCodec.DefaultHttp2StreamChannel.Http2StreamChannelConfig(
			this
		);
		private final Http2MultiplexCodec.DefaultHttp2StreamChannel.Http2ChannelUnsafe unsafe = new Http2MultiplexCodec.DefaultHttp2StreamChannel.Http2ChannelUnsafe();
		private final ChannelId channelId;
		private final ChannelPipeline pipeline;
		private final DefaultHttp2FrameStream stream;
		private final ChannelPromise closePromise;
		private final boolean outbound;
		private volatile boolean registered;
		private volatile boolean writable;
		private boolean outboundClosed;
		private boolean closePending;
		private boolean readInProgress;
		private Queue<Object> inboundBuffer;
		private boolean firstFrameWritten;
		private boolean streamClosedWithoutError;
		private boolean inFireChannelReadComplete;
		boolean fireChannelReadPending;
		Http2MultiplexCodec.DefaultHttp2StreamChannel next;

		DefaultHttp2StreamChannel(DefaultHttp2FrameStream stream, boolean outbound) {
			this.stream = stream;
			this.outbound = outbound;
			this.writable = Http2MultiplexCodec.this.initialWritability(stream);
			((Http2MultiplexCodec.Http2MultiplexCodecStream)stream).channel = this;
			this.pipeline = new DefaultChannelPipeline(this) {
				@Override
				protected void incrementPendingOutboundBytes(long size) {
				}

				@Override
				protected void decrementPendingOutboundBytes(long size) {
				}
			};
			this.closePromise = this.pipeline.newPromise();
			this.channelId = new Http2StreamChannelId(this.parent().id(), ++Http2MultiplexCodec.this.idCount);
		}

		@Override
		public Http2FrameStream stream() {
			return this.stream;
		}

		void streamClosed() {
			this.streamClosedWithoutError = true;
			if (this.readInProgress) {
				this.unsafe().closeForcibly();
			} else {
				this.closePending = true;
			}
		}

		@Override
		public ChannelMetadata metadata() {
			return Http2MultiplexCodec.METADATA;
		}

		@Override
		public ChannelConfig config() {
			return this.config;
		}

		@Override
		public boolean isOpen() {
			return !this.closePromise.isDone();
		}

		@Override
		public boolean isActive() {
			return this.isOpen();
		}

		@Override
		public boolean isWritable() {
			return this.writable;
		}

		@Override
		public ChannelId id() {
			return this.channelId;
		}

		@Override
		public EventLoop eventLoop() {
			return this.parent().eventLoop();
		}

		@Override
		public Channel parent() {
			return Http2MultiplexCodec.this.ctx.channel();
		}

		@Override
		public boolean isRegistered() {
			return this.registered;
		}

		@Override
		public SocketAddress localAddress() {
			return this.parent().localAddress();
		}

		@Override
		public SocketAddress remoteAddress() {
			return this.parent().remoteAddress();
		}

		@Override
		public ChannelFuture closeFuture() {
			return this.closePromise;
		}

		@Override
		public long bytesBeforeUnwritable() {
			return (long)this.config().getWriteBufferHighWaterMark();
		}

		@Override
		public long bytesBeforeWritable() {
			return 0L;
		}

		@Override
		public Unsafe unsafe() {
			return this.unsafe;
		}

		@Override
		public ChannelPipeline pipeline() {
			return this.pipeline;
		}

		@Override
		public ByteBufAllocator alloc() {
			return this.config().getAllocator();
		}

		@Override
		public Channel read() {
			this.pipeline().read();
			return this;
		}

		@Override
		public Channel flush() {
			this.pipeline().flush();
			return this;
		}

		@Override
		public ChannelFuture bind(SocketAddress localAddress) {
			return this.pipeline().bind(localAddress);
		}

		@Override
		public ChannelFuture connect(SocketAddress remoteAddress) {
			return this.pipeline().connect(remoteAddress);
		}

		@Override
		public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
			return this.pipeline().connect(remoteAddress, localAddress);
		}

		@Override
		public ChannelFuture disconnect() {
			return this.pipeline().disconnect();
		}

		@Override
		public ChannelFuture close() {
			return this.pipeline().close();
		}

		@Override
		public ChannelFuture deregister() {
			return this.pipeline().deregister();
		}

		@Override
		public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
			return this.pipeline().bind(localAddress, promise);
		}

		@Override
		public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
			return this.pipeline().connect(remoteAddress, promise);
		}

		@Override
		public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
			return this.pipeline().connect(remoteAddress, localAddress, promise);
		}

		@Override
		public ChannelFuture disconnect(ChannelPromise promise) {
			return this.pipeline().disconnect(promise);
		}

		@Override
		public ChannelFuture close(ChannelPromise promise) {
			return this.pipeline().close(promise);
		}

		@Override
		public ChannelFuture deregister(ChannelPromise promise) {
			return this.pipeline().deregister(promise);
		}

		@Override
		public ChannelFuture write(Object msg) {
			return this.pipeline().write(msg);
		}

		@Override
		public ChannelFuture write(Object msg, ChannelPromise promise) {
			return this.pipeline().write(msg, promise);
		}

		@Override
		public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
			return this.pipeline().writeAndFlush(msg, promise);
		}

		@Override
		public ChannelFuture writeAndFlush(Object msg) {
			return this.pipeline().writeAndFlush(msg);
		}

		@Override
		public ChannelPromise newPromise() {
			return this.pipeline().newPromise();
		}

		@Override
		public ChannelProgressivePromise newProgressivePromise() {
			return this.pipeline().newProgressivePromise();
		}

		@Override
		public ChannelFuture newSucceededFuture() {
			return this.pipeline().newSucceededFuture();
		}

		@Override
		public ChannelFuture newFailedFuture(Throwable cause) {
			return this.pipeline().newFailedFuture(cause);
		}

		@Override
		public ChannelPromise voidPromise() {
			return this.pipeline().voidPromise();
		}

		public int hashCode() {
			return this.id().hashCode();
		}

		public boolean equals(Object o) {
			return this == o;
		}

		public int compareTo(Channel o) {
			return this == o ? 0 : this.id().compareTo(o.id());
		}

		public String toString() {
			return this.parent().toString() + "(H2 - " + this.stream + ')';
		}

		void writabilityChanged(boolean writable) {
			assert this.eventLoop().inEventLoop();

			if (writable != this.writable && this.isActive()) {
				this.writable = writable;
				this.pipeline().fireChannelWritabilityChanged();
			}
		}

		Http2MultiplexCodec.ReadState fireChildRead(Http2Frame frame) {
			assert this.eventLoop().inEventLoop();

			if (!this.isActive()) {
				ReferenceCountUtil.release(frame);
				return Http2MultiplexCodec.ReadState.READ_IGNORED_CHANNEL_INACTIVE;
			} else if (!this.readInProgress || this.inboundBuffer != null && !this.inboundBuffer.isEmpty()) {
				if (this.inboundBuffer == null) {
					this.inboundBuffer = new ArrayDeque(4);
				}

				this.inboundBuffer.add(frame);
				return Http2MultiplexCodec.ReadState.READ_QUEUED;
			} else {
				ExtendedHandle allocHandle = this.unsafe.recvBufAllocHandle();
				this.unsafe.doRead0(frame, allocHandle);
				return allocHandle.continueReading()
					? Http2MultiplexCodec.ReadState.READ_PROCESSED_OK_TO_PROCESS_MORE
					: Http2MultiplexCodec.ReadState.READ_PROCESSED_BUT_STOP_READING;
			}
		}

		void fireChildReadComplete() {
			assert this.eventLoop().inEventLoop();

			try {
				if (this.readInProgress) {
					this.inFireChannelReadComplete = true;
					this.readInProgress = false;
					this.unsafe().recvBufAllocHandle().readComplete();
					this.pipeline().fireChannelReadComplete();
				}
			} finally {
				this.inFireChannelReadComplete = false;
			}
		}

		private final class Http2ChannelUnsafe implements Unsafe {
			private final VoidChannelPromise unsafeVoidPromise = new VoidChannelPromise(DefaultHttp2StreamChannel.this, false);
			private ExtendedHandle recvHandle;
			private boolean writeDoneAndNoFlush;
			private boolean closeInitiated;

			private Http2ChannelUnsafe() {
			}

			@Override
			public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
				if (promise.setUncancellable()) {
					promise.setFailure(new UnsupportedOperationException());
				}
			}

			public ExtendedHandle recvBufAllocHandle() {
				if (this.recvHandle == null) {
					this.recvHandle = (ExtendedHandle)DefaultHttp2StreamChannel.this.config().<RecvByteBufAllocator>getRecvByteBufAllocator().newHandle();
				}

				return this.recvHandle;
			}

			@Override
			public SocketAddress localAddress() {
				return DefaultHttp2StreamChannel.this.parent().unsafe().localAddress();
			}

			@Override
			public SocketAddress remoteAddress() {
				return DefaultHttp2StreamChannel.this.parent().unsafe().remoteAddress();
			}

			@Override
			public void register(EventLoop eventLoop, ChannelPromise promise) {
				if (promise.setUncancellable()) {
					if (DefaultHttp2StreamChannel.this.registered) {
						throw new UnsupportedOperationException("Re-register is not supported");
					} else {
						DefaultHttp2StreamChannel.this.registered = true;
						if (!DefaultHttp2StreamChannel.this.outbound) {
							DefaultHttp2StreamChannel.this.pipeline().addLast(Http2MultiplexCodec.this.inboundStreamHandler);
						}

						promise.setSuccess();
						DefaultHttp2StreamChannel.this.pipeline().fireChannelRegistered();
						if (DefaultHttp2StreamChannel.this.isActive()) {
							DefaultHttp2StreamChannel.this.pipeline().fireChannelActive();
						}
					}
				}
			}

			@Override
			public void bind(SocketAddress localAddress, ChannelPromise promise) {
				if (promise.setUncancellable()) {
					promise.setFailure(new UnsupportedOperationException());
				}
			}

			@Override
			public void disconnect(ChannelPromise promise) {
				this.close(promise);
			}

			@Override
			public void close(ChannelPromise promise) {
				if (promise.setUncancellable()) {
					if (this.closeInitiated) {
						if (DefaultHttp2StreamChannel.this.closePromise.isDone()) {
							promise.setSuccess();
						} else if (!(promise instanceof VoidChannelPromise)) {
							DefaultHttp2StreamChannel.this.closePromise.addListener(new ChannelFutureListener() {
								public void operationComplete(ChannelFuture future) throws Exception {
									promise.setSuccess();
								}
							});
						}
					} else {
						this.closeInitiated = true;
						DefaultHttp2StreamChannel.this.closePending = false;
						DefaultHttp2StreamChannel.this.fireChannelReadPending = false;
						if (DefaultHttp2StreamChannel.this.parent().isActive()
							&& !DefaultHttp2StreamChannel.this.streamClosedWithoutError
							&& Http2CodecUtil.isStreamIdValid(DefaultHttp2StreamChannel.this.stream().id())) {
							Http2StreamFrame resetFrame = new DefaultHttp2ResetFrame(Http2Error.CANCEL).stream(DefaultHttp2StreamChannel.this.stream());
							this.write(resetFrame, DefaultHttp2StreamChannel.this.unsafe().voidPromise());
							this.flush();
						}

						if (DefaultHttp2StreamChannel.this.inboundBuffer != null) {
							while (true) {
								Object msg = DefaultHttp2StreamChannel.this.inboundBuffer.poll();
								if (msg == null) {
									break;
								}

								ReferenceCountUtil.release(msg);
							}
						}

						DefaultHttp2StreamChannel.this.outboundClosed = true;
						DefaultHttp2StreamChannel.this.closePromise.setSuccess();
						promise.setSuccess();
						DefaultHttp2StreamChannel.this.pipeline().fireChannelInactive();
						if (DefaultHttp2StreamChannel.this.isRegistered()) {
							this.deregister(DefaultHttp2StreamChannel.this.unsafe().voidPromise());
						}
					}
				}
			}

			@Override
			public void closeForcibly() {
				this.close(DefaultHttp2StreamChannel.this.unsafe().voidPromise());
			}

			@Override
			public void deregister(ChannelPromise promise) {
				if (promise.setUncancellable()) {
					if (DefaultHttp2StreamChannel.this.registered) {
						DefaultHttp2StreamChannel.this.registered = true;
						promise.setSuccess();
						DefaultHttp2StreamChannel.this.pipeline().fireChannelUnregistered();
					} else {
						promise.setFailure(new IllegalStateException("Not registered"));
					}
				}
			}

			@Override
			public void beginRead() {
				if (!DefaultHttp2StreamChannel.this.readInProgress && DefaultHttp2StreamChannel.this.isActive()) {
					DefaultHttp2StreamChannel.this.readInProgress = true;
					Handle allocHandle = DefaultHttp2StreamChannel.this.unsafe().recvBufAllocHandle();
					allocHandle.reset(DefaultHttp2StreamChannel.this.config());
					if (DefaultHttp2StreamChannel.this.inboundBuffer != null && !DefaultHttp2StreamChannel.this.inboundBuffer.isEmpty()) {
						boolean continueReading;
						do {
							Object m = DefaultHttp2StreamChannel.this.inboundBuffer.poll();
							if (m == null) {
								continueReading = false;
								break;
							}

							this.doRead0((Http2Frame)m, allocHandle);
						} while (continueReading = allocHandle.continueReading());

						if (continueReading && Http2MultiplexCodec.this.parentReadInProgress) {
							Http2MultiplexCodec.this.addChildChannelToReadPendingQueue(DefaultHttp2StreamChannel.this);
						} else {
							DefaultHttp2StreamChannel.this.readInProgress = false;
							allocHandle.readComplete();
							DefaultHttp2StreamChannel.this.pipeline().fireChannelReadComplete();
							this.flush();
							if (DefaultHttp2StreamChannel.this.closePending) {
								DefaultHttp2StreamChannel.this.unsafe.closeForcibly();
							}
						}
					} else {
						if (DefaultHttp2StreamChannel.this.closePending) {
							DefaultHttp2StreamChannel.this.unsafe.closeForcibly();
						}
					}
				}
			}

			void doRead0(Http2Frame frame, Handle allocHandle) {
				int numBytesToBeConsumed = 0;
				if (frame instanceof Http2DataFrame) {
					numBytesToBeConsumed = ((Http2DataFrame)frame).initialFlowControlledBytes();
					allocHandle.lastBytesRead(numBytesToBeConsumed);
				} else {
					allocHandle.lastBytesRead(9);
				}

				allocHandle.incMessagesRead(1);
				DefaultHttp2StreamChannel.this.pipeline().fireChannelRead(frame);
				if (numBytesToBeConsumed != 0) {
					try {
						this.writeDoneAndNoFlush = this.writeDoneAndNoFlush
							| Http2MultiplexCodec.this.onBytesConsumed(Http2MultiplexCodec.this.ctx, DefaultHttp2StreamChannel.this.stream, numBytesToBeConsumed);
					} catch (Http2Exception var5) {
						DefaultHttp2StreamChannel.this.pipeline().fireExceptionCaught(var5);
					}
				}
			}

			@Override
			public void write(Object msg, ChannelPromise promise) {
				if (!promise.setUncancellable()) {
					ReferenceCountUtil.release(msg);
				} else if (DefaultHttp2StreamChannel.this.isActive()
					&& (!DefaultHttp2StreamChannel.this.outboundClosed || !(msg instanceof Http2HeadersFrame) && !(msg instanceof Http2DataFrame))) {
					try {
						if (!(msg instanceof Http2StreamFrame)) {
							String msgStr = msg.toString();
							ReferenceCountUtil.release(msg);
							promise.setFailure(new IllegalArgumentException("Message must be an " + StringUtil.simpleClassName(Http2StreamFrame.class) + ": " + msgStr));
							return;
						}

						Http2StreamFrame frame = this.validateStreamFrame((Http2StreamFrame)msg).stream(DefaultHttp2StreamChannel.this.stream());
						if (DefaultHttp2StreamChannel.this.firstFrameWritten || Http2CodecUtil.isStreamIdValid(DefaultHttp2StreamChannel.this.stream().id())) {
							ChannelFuture future = this.write0(msg);
							if (future.isDone()) {
								this.writeComplete(future, promise);
							} else {
								future.addListener(new ChannelFutureListener() {
									public void operationComplete(ChannelFuture future) throws Exception {
										Http2ChannelUnsafe.this.writeComplete(future, promise);
									}
								});
							}

							return;
						}

						if (!(frame instanceof Http2HeadersFrame)) {
							ReferenceCountUtil.release(frame);
							promise.setFailure(new IllegalArgumentException("The first frame must be a headers frame. Was: " + frame.name()));
							return;
						}

						DefaultHttp2StreamChannel.this.firstFrameWritten = true;
						ChannelFuture future = this.write0(frame);
						if (future.isDone()) {
							this.firstWriteComplete(future, promise);
						} else {
							future.addListener(new ChannelFutureListener() {
								public void operationComplete(ChannelFuture future) throws Exception {
									Http2ChannelUnsafe.this.firstWriteComplete(future, promise);
								}
							});
						}
					} catch (Throwable var8) {
						promise.tryFailure(var8);
						return;
					} finally {
						this.writeDoneAndNoFlush = true;
					}
				} else {
					ReferenceCountUtil.release(msg);
					promise.setFailure(Http2MultiplexCodec.CLOSED_CHANNEL_EXCEPTION);
				}
			}

			private void firstWriteComplete(ChannelFuture future, ChannelPromise promise) {
				Throwable cause = future.cause();
				if (cause == null) {
					DefaultHttp2StreamChannel.this.writabilityChanged(Http2MultiplexCodec.this.isWritable(DefaultHttp2StreamChannel.this.stream));
					promise.setSuccess();
				} else {
					promise.setFailure(this.wrapStreamClosedError(cause));
					this.closeForcibly();
				}
			}

			private void writeComplete(ChannelFuture future, ChannelPromise promise) {
				Throwable cause = future.cause();
				if (cause == null) {
					promise.setSuccess();
				} else {
					Throwable error = this.wrapStreamClosedError(cause);
					promise.setFailure(error);
					if (error instanceof ClosedChannelException) {
						if (DefaultHttp2StreamChannel.this.config.isAutoClose()) {
							this.closeForcibly();
						} else {
							DefaultHttp2StreamChannel.this.outboundClosed = true;
						}
					}
				}
			}

			private Throwable wrapStreamClosedError(Throwable cause) {
				return cause instanceof Http2Exception && ((Http2Exception)cause).error() == Http2Error.STREAM_CLOSED
					? new ClosedChannelException().initCause(cause)
					: cause;
			}

			private Http2StreamFrame validateStreamFrame(Http2StreamFrame frame) {
				if (frame.stream() != null && frame.stream() != DefaultHttp2StreamChannel.this.stream) {
					String msgString = frame.toString();
					ReferenceCountUtil.release(frame);
					throw new IllegalArgumentException("Stream " + frame.stream() + " must not be set on the frame: " + msgString);
				} else {
					return frame;
				}
			}

			private ChannelFuture write0(Object msg) {
				ChannelPromise promise = Http2MultiplexCodec.this.ctx.newPromise();
				Http2MultiplexCodec.this.write(Http2MultiplexCodec.this.ctx, msg, promise);
				return promise;
			}

			@Override
			public void flush() {
				if (this.writeDoneAndNoFlush) {
					try {
						if (!DefaultHttp2StreamChannel.this.inFireChannelReadComplete) {
							Http2MultiplexCodec.this.flush0(Http2MultiplexCodec.this.ctx);
						}
					} finally {
						this.writeDoneAndNoFlush = false;
					}
				}
			}

			@Override
			public ChannelPromise voidPromise() {
				return this.unsafeVoidPromise;
			}

			@Override
			public ChannelOutboundBuffer outboundBuffer() {
				return null;
			}
		}

		private final class Http2StreamChannelConfig extends DefaultChannelConfig {
			Http2StreamChannelConfig(Channel channel) {
				super(channel);
				this.setRecvByteBufAllocator(new Http2MultiplexCodec.Http2StreamChannelRecvByteBufAllocator());
			}

			@Override
			public int getWriteBufferHighWaterMark() {
				return Math.min(DefaultHttp2StreamChannel.this.parent().config().getWriteBufferHighWaterMark(), Http2MultiplexCodec.this.initialOutboundStreamWindow);
			}

			@Override
			public int getWriteBufferLowWaterMark() {
				return Math.min(DefaultHttp2StreamChannel.this.parent().config().getWriteBufferLowWaterMark(), Http2MultiplexCodec.this.initialOutboundStreamWindow);
			}

			@Override
			public MessageSizeEstimator getMessageSizeEstimator() {
				return Http2MultiplexCodec.FlowControlledFrameSizeEstimator.INSTANCE;
			}

			@Override
			public WriteBufferWaterMark getWriteBufferWaterMark() {
				int mark = this.getWriteBufferHighWaterMark();
				return new WriteBufferWaterMark(mark, mark);
			}

			@Override
			public ChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
				throw new UnsupportedOperationException();
			}

			@Deprecated
			@Override
			public ChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
				throw new UnsupportedOperationException();
			}

			@Deprecated
			@Override
			public ChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
				throw new UnsupportedOperationException();
			}

			@Override
			public ChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
				throw new UnsupportedOperationException();
			}

			@Override
			public ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
				if (!(allocator.newHandle() instanceof ExtendedHandle)) {
					throw new IllegalArgumentException("allocator.newHandle() must return an object of type: " + ExtendedHandle.class);
				} else {
					super.setRecvByteBufAllocator(allocator);
					return this;
				}
			}
		}
	}

	private static final class FlowControlledFrameSizeEstimator implements MessageSizeEstimator {
		static final Http2MultiplexCodec.FlowControlledFrameSizeEstimator INSTANCE = new Http2MultiplexCodec.FlowControlledFrameSizeEstimator();
		static final io.netty.channel.MessageSizeEstimator.Handle HANDLE_INSTANCE = new io.netty.channel.MessageSizeEstimator.Handle() {
			@Override
			public int size(Object msg) {
				return msg instanceof Http2DataFrame ? (int)Math.min(2147483647L, (long)((Http2DataFrame)msg).initialFlowControlledBytes() + 9L) : 9;
			}
		};

		@Override
		public io.netty.channel.MessageSizeEstimator.Handle newHandle() {
			return HANDLE_INSTANCE;
		}
	}

	static class Http2MultiplexCodecStream extends DefaultHttp2FrameStream {
		Http2MultiplexCodec.DefaultHttp2StreamChannel channel;
	}

	private static final class Http2StreamChannelRecvByteBufAllocator extends DefaultMaxMessagesRecvByteBufAllocator {
		private Http2StreamChannelRecvByteBufAllocator() {
		}

		public MaxMessageHandle newHandle() {
			return new MaxMessageHandle() {
				@Override
				public int guess() {
					return 1024;
				}
			};
		}
	}

	private static enum ReadState {
		READ_QUEUED,
		READ_IGNORED_CHANNEL_INACTIVE,
		READ_PROCESSED_BUT_STOP_READING,
		READ_PROCESSED_OK_TO_PROCESS_MORE;
	}
}
