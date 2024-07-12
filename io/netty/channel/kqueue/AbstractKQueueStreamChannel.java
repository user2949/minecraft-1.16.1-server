package io.netty.channel.kqueue;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.EventLoop;
import io.netty.channel.FileRegion;
import io.netty.channel.AbstractChannel.AbstractUnsafe;
import io.netty.channel.kqueue.AbstractKQueueChannel.AbstractKQueueUnsafe;
import io.netty.channel.socket.DuplexChannel;
import io.netty.channel.unix.IovArray;
import io.netty.channel.unix.SocketWritableByteChannel;
import io.netty.channel.unix.UnixChannelUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.Executor;

public abstract class AbstractKQueueStreamChannel extends AbstractKQueueChannel implements DuplexChannel {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractKQueueStreamChannel.class);
	private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
	private static final String EXPECTED_TYPES = " (expected: "
		+ StringUtil.simpleClassName(ByteBuf.class)
		+ ", "
		+ StringUtil.simpleClassName(DefaultFileRegion.class)
		+ ')';
	private WritableByteChannel byteChannel;
	private final Runnable flushTask = new Runnable() {
		public void run() {
			((AbstractKQueueUnsafe)AbstractKQueueStreamChannel.this.unsafe()).flush0();
		}
	};

	AbstractKQueueStreamChannel(Channel parent, BsdSocket fd, boolean active) {
		super(parent, fd, active);
	}

	AbstractKQueueStreamChannel(Channel parent, BsdSocket fd, SocketAddress remote) {
		super(parent, fd, remote);
	}

	AbstractKQueueStreamChannel(BsdSocket fd) {
		this(null, fd, isSoErrorZero(fd));
	}

	@Override
	protected AbstractKQueueUnsafe newUnsafe() {
		return new AbstractKQueueStreamChannel.KQueueStreamUnsafe();
	}

	@Override
	public ChannelMetadata metadata() {
		return METADATA;
	}

	private int writeBytes(ChannelOutboundBuffer in, ByteBuf buf) throws Exception {
		int readableBytes = buf.readableBytes();
		if (readableBytes == 0) {
			in.remove();
			return 0;
		} else if (!buf.hasMemoryAddress() && buf.nioBufferCount() != 1) {
			ByteBuffer[] nioBuffers = buf.nioBuffers();
			return this.writeBytesMultiple(in, nioBuffers, nioBuffers.length, (long)readableBytes, this.config().getMaxBytesPerGatheringWrite());
		} else {
			return this.doWriteBytes(in, buf);
		}
	}

	private void adjustMaxBytesPerGatheringWrite(long attempted, long written, long oldMaxBytesPerGatheringWrite) {
		if (attempted == written) {
			if (attempted << 1 > oldMaxBytesPerGatheringWrite) {
				this.config().setMaxBytesPerGatheringWrite(attempted << 1);
			}
		} else if (attempted > 4096L && written < attempted >>> 1) {
			this.config().setMaxBytesPerGatheringWrite(attempted >>> 1);
		}
	}

	private int writeBytesMultiple(ChannelOutboundBuffer in, IovArray array) throws IOException {
		long expectedWrittenBytes = array.size();

		assert expectedWrittenBytes != 0L;

		int cnt = array.count();

		assert cnt != 0;

		long localWrittenBytes = this.socket.writevAddresses(array.memoryAddress(0), cnt);
		if (localWrittenBytes > 0L) {
			this.adjustMaxBytesPerGatheringWrite(expectedWrittenBytes, localWrittenBytes, array.maxBytes());
			in.removeBytes(localWrittenBytes);
			return 1;
		} else {
			return Integer.MAX_VALUE;
		}
	}

	private int writeBytesMultiple(ChannelOutboundBuffer in, ByteBuffer[] nioBuffers, int nioBufferCnt, long expectedWrittenBytes, long maxBytesPerGatheringWrite) throws IOException {
		assert expectedWrittenBytes != 0L;

		if (expectedWrittenBytes > maxBytesPerGatheringWrite) {
			expectedWrittenBytes = maxBytesPerGatheringWrite;
		}

		long localWrittenBytes = this.socket.writev(nioBuffers, 0, nioBufferCnt, expectedWrittenBytes);
		if (localWrittenBytes > 0L) {
			this.adjustMaxBytesPerGatheringWrite(expectedWrittenBytes, localWrittenBytes, maxBytesPerGatheringWrite);
			in.removeBytes(localWrittenBytes);
			return 1;
		} else {
			return Integer.MAX_VALUE;
		}
	}

	private int writeDefaultFileRegion(ChannelOutboundBuffer in, DefaultFileRegion region) throws Exception {
		long regionCount = region.count();
		if (region.transferred() >= regionCount) {
			in.remove();
			return 0;
		} else {
			long offset = region.transferred();
			long flushedAmount = this.socket.sendFile(region, region.position(), offset, regionCount - offset);
			if (flushedAmount > 0L) {
				in.progress(flushedAmount);
				if (region.transferred() >= regionCount) {
					in.remove();
				}

				return 1;
			} else {
				return Integer.MAX_VALUE;
			}
		}
	}

	private int writeFileRegion(ChannelOutboundBuffer in, FileRegion region) throws Exception {
		if (region.transferred() >= region.count()) {
			in.remove();
			return 0;
		} else {
			if (this.byteChannel == null) {
				this.byteChannel = new AbstractKQueueStreamChannel.KQueueSocketWritableByteChannel();
			}

			long flushedAmount = region.transferTo(this.byteChannel, region.transferred());
			if (flushedAmount > 0L) {
				in.progress(flushedAmount);
				if (region.transferred() >= region.count()) {
					in.remove();
				}

				return 1;
			} else {
				return Integer.MAX_VALUE;
			}
		}
	}

	@Override
	protected void doWrite(ChannelOutboundBuffer in) throws Exception {
		int writeSpinCount = this.config().getWriteSpinCount();

		do {
			int msgCount = in.size();
			if (msgCount > 1 && in.current() instanceof ByteBuf) {
				writeSpinCount -= this.doWriteMultiple(in);
			} else {
				if (msgCount == 0) {
					this.writeFilter(false);
					return;
				}

				writeSpinCount -= this.doWriteSingle(in);
			}
		} while (writeSpinCount > 0);

		if (writeSpinCount == 0) {
			this.writeFilter(false);
			this.eventLoop().execute(this.flushTask);
		} else {
			this.writeFilter(true);
		}
	}

	protected int doWriteSingle(ChannelOutboundBuffer in) throws Exception {
		Object msg = in.current();
		if (msg instanceof ByteBuf) {
			return this.writeBytes(in, (ByteBuf)msg);
		} else if (msg instanceof DefaultFileRegion) {
			return this.writeDefaultFileRegion(in, (DefaultFileRegion)msg);
		} else if (msg instanceof FileRegion) {
			return this.writeFileRegion(in, (FileRegion)msg);
		} else {
			throw new Error();
		}
	}

	private int doWriteMultiple(ChannelOutboundBuffer in) throws Exception {
		long maxBytesPerGatheringWrite = this.config().getMaxBytesPerGatheringWrite();
		if (PlatformDependent.hasUnsafe()) {
			IovArray array = ((KQueueEventLoop)this.eventLoop()).cleanArray();
			array.maxBytes(maxBytesPerGatheringWrite);
			in.forEachFlushedMessage(array);
			if (array.count() >= 1) {
				return this.writeBytesMultiple(in, array);
			}
		} else {
			ByteBuffer[] buffers = in.nioBuffers();
			int cnt = in.nioBufferCount();
			if (cnt >= 1) {
				return this.writeBytesMultiple(in, buffers, cnt, in.nioBufferSize(), maxBytesPerGatheringWrite);
			}
		}

		in.removeBytes(0L);
		return 0;
	}

	@Override
	protected Object filterOutboundMessage(Object msg) {
		if (msg instanceof ByteBuf) {
			ByteBuf buf = (ByteBuf)msg;
			return UnixChannelUtil.isBufferCopyNeededForWrite(buf) ? this.newDirectBuffer(buf) : buf;
		} else if (msg instanceof FileRegion) {
			return msg;
		} else {
			throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
		}
	}

	@Override
	protected final void doShutdownOutput() throws Exception {
		this.socket.shutdown(false, true);
	}

	@Override
	public boolean isOutputShutdown() {
		return this.socket.isOutputShutdown();
	}

	@Override
	public boolean isInputShutdown() {
		return this.socket.isInputShutdown();
	}

	@Override
	public boolean isShutdown() {
		return this.socket.isShutdown();
	}

	@Override
	public ChannelFuture shutdownOutput() {
		return this.shutdownOutput(this.newPromise());
	}

	@Override
	public ChannelFuture shutdownOutput(ChannelPromise promise) {
		EventLoop loop = this.eventLoop();
		if (loop.inEventLoop()) {
			((AbstractUnsafe)this.unsafe()).shutdownOutput(promise);
		} else {
			loop.execute(new Runnable() {
				public void run() {
					((AbstractUnsafe)AbstractKQueueStreamChannel.this.unsafe()).shutdownOutput(promise);
				}
			});
		}

		return promise;
	}

	@Override
	public ChannelFuture shutdownInput() {
		return this.shutdownInput(this.newPromise());
	}

	@Override
	public ChannelFuture shutdownInput(ChannelPromise promise) {
		EventLoop loop = this.eventLoop();
		if (loop.inEventLoop()) {
			this.shutdownInput0(promise);
		} else {
			loop.execute(new Runnable() {
				public void run() {
					AbstractKQueueStreamChannel.this.shutdownInput0(promise);
				}
			});
		}

		return promise;
	}

	private void shutdownInput0(ChannelPromise promise) {
		try {
			this.socket.shutdown(true, false);
		} catch (Throwable var3) {
			promise.setFailure(var3);
			return;
		}

		promise.setSuccess();
	}

	@Override
	public ChannelFuture shutdown() {
		return this.shutdown(this.newPromise());
	}

	@Override
	public ChannelFuture shutdown(ChannelPromise promise) {
		ChannelFuture shutdownOutputFuture = this.shutdownOutput();
		if (shutdownOutputFuture.isDone()) {
			this.shutdownOutputDone(shutdownOutputFuture, promise);
		} else {
			shutdownOutputFuture.addListener(new ChannelFutureListener() {
				public void operationComplete(ChannelFuture shutdownOutputFuture) throws Exception {
					AbstractKQueueStreamChannel.this.shutdownOutputDone(shutdownOutputFuture, promise);
				}
			});
		}

		return promise;
	}

	private void shutdownOutputDone(ChannelFuture shutdownOutputFuture, ChannelPromise promise) {
		ChannelFuture shutdownInputFuture = this.shutdownInput();
		if (shutdownInputFuture.isDone()) {
			shutdownDone(shutdownOutputFuture, shutdownInputFuture, promise);
		} else {
			shutdownInputFuture.addListener(new ChannelFutureListener() {
				public void operationComplete(ChannelFuture shutdownInputFuture) throws Exception {
					AbstractKQueueStreamChannel.shutdownDone(shutdownOutputFuture, shutdownInputFuture, promise);
				}
			});
		}
	}

	private static void shutdownDone(ChannelFuture shutdownOutputFuture, ChannelFuture shutdownInputFuture, ChannelPromise promise) {
		Throwable shutdownOutputCause = shutdownOutputFuture.cause();
		Throwable shutdownInputCause = shutdownInputFuture.cause();
		if (shutdownOutputCause != null) {
			if (shutdownInputCause != null) {
				logger.debug("Exception suppressed because a previous exception occurred.", shutdownInputCause);
			}

			promise.setFailure(shutdownOutputCause);
		} else if (shutdownInputCause != null) {
			promise.setFailure(shutdownInputCause);
		} else {
			promise.setSuccess();
		}
	}

	private final class KQueueSocketWritableByteChannel extends SocketWritableByteChannel {
		KQueueSocketWritableByteChannel() {
			super(AbstractKQueueStreamChannel.this.socket);
		}

		@Override
		protected ByteBufAllocator alloc() {
			return AbstractKQueueStreamChannel.this.alloc();
		}
	}

	class KQueueStreamUnsafe extends AbstractKQueueUnsafe {
		KQueueStreamUnsafe() {
			super(AbstractKQueueStreamChannel.this);
		}

		@Override
		protected Executor prepareToClose() {
			return super.prepareToClose();
		}

		@Override
		void readReady(KQueueRecvByteAllocatorHandle allocHandle) {
			ChannelConfig config = AbstractKQueueStreamChannel.this.config();
			if (AbstractKQueueStreamChannel.this.shouldBreakReadReady(config)) {
				this.clearReadFilter0();
			} else {
				ChannelPipeline pipeline = AbstractKQueueStreamChannel.this.pipeline();
				ByteBufAllocator allocator = config.getAllocator();
				allocHandle.reset(config);
				this.readReadyBefore();
				ByteBuf byteBuf = null;
				boolean close = false;

				try {
					do {
						byteBuf = allocHandle.allocate(allocator);
						allocHandle.lastBytesRead(AbstractKQueueStreamChannel.this.doReadBytes(byteBuf));
						if (allocHandle.lastBytesRead() <= 0) {
							byteBuf.release();
							byteBuf = null;
							close = allocHandle.lastBytesRead() < 0;
							if (close) {
								this.readPending = false;
							}
							break;
						}

						allocHandle.incMessagesRead(1);
						this.readPending = false;
						pipeline.fireChannelRead(byteBuf);
						byteBuf = null;
					} while (!AbstractKQueueStreamChannel.this.shouldBreakReadReady(config) && allocHandle.continueReading());

					allocHandle.readComplete();
					pipeline.fireChannelReadComplete();
					if (close) {
						this.shutdownInput(false);
					}
				} catch (Throwable var11) {
					this.handleReadException(pipeline, byteBuf, var11, close, allocHandle);
				} finally {
					this.readReadyFinally(config);
				}
			}
		}

		private void handleReadException(ChannelPipeline pipeline, ByteBuf byteBuf, Throwable cause, boolean close, KQueueRecvByteAllocatorHandle allocHandle) {
			if (byteBuf != null) {
				if (byteBuf.isReadable()) {
					this.readPending = false;
					pipeline.fireChannelRead(byteBuf);
				} else {
					byteBuf.release();
				}
			}

			if (!this.failConnectPromise(cause)) {
				allocHandle.readComplete();
				pipeline.fireChannelReadComplete();
				pipeline.fireExceptionCaught(cause);
				if (close || cause instanceof IOException) {
					this.shutdownInput(false);
				}
			}
		}
	}
}