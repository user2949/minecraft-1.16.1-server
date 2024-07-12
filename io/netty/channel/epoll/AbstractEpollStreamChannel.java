package io.netty.channel.epoll;

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
import io.netty.channel.RecvByteBufAllocator.ExtendedHandle;
import io.netty.channel.RecvByteBufAllocator.Handle;
import io.netty.channel.epoll.AbstractEpollChannel.AbstractEpollUnsafe;
import io.netty.channel.socket.DuplexChannel;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.IovArray;
import io.netty.channel.unix.SocketWritableByteChannel;
import io.netty.channel.unix.UnixChannelUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.WritableByteChannel;
import java.util.Queue;
import java.util.concurrent.Executor;

public abstract class AbstractEpollStreamChannel extends AbstractEpollChannel implements DuplexChannel {
	private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
	private static final String EXPECTED_TYPES = " (expected: "
		+ StringUtil.simpleClassName(ByteBuf.class)
		+ ", "
		+ StringUtil.simpleClassName(DefaultFileRegion.class)
		+ ')';
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractEpollStreamChannel.class);
	private static final ClosedChannelException CLEAR_SPLICE_QUEUE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), AbstractEpollStreamChannel.class, "clearSpliceQueue()"
	);
	private static final ClosedChannelException SPLICE_TO_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), AbstractEpollStreamChannel.class, "spliceTo(...)"
	);
	private static final ClosedChannelException FAIL_SPLICE_IF_CLOSED_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), AbstractEpollStreamChannel.class, "failSpliceIfClosed(...)"
	);
	private final Runnable flushTask = new Runnable() {
		public void run() {
			((AbstractEpollUnsafe)AbstractEpollStreamChannel.this.unsafe()).flush0();
		}
	};
	private Queue<AbstractEpollStreamChannel.SpliceInTask> spliceQueue;
	private FileDescriptor pipeIn;
	private FileDescriptor pipeOut;
	private WritableByteChannel byteChannel;

	protected AbstractEpollStreamChannel(Channel parent, int fd) {
		this(parent, new LinuxSocket(fd));
	}

	protected AbstractEpollStreamChannel(int fd) {
		this(new LinuxSocket(fd));
	}

	AbstractEpollStreamChannel(LinuxSocket fd) {
		this(fd, isSoErrorZero(fd));
	}

	AbstractEpollStreamChannel(Channel parent, LinuxSocket fd) {
		super(parent, fd, Native.EPOLLIN, true);
		this.flags = this.flags | Native.EPOLLRDHUP;
	}

	AbstractEpollStreamChannel(Channel parent, LinuxSocket fd, SocketAddress remote) {
		super(parent, fd, Native.EPOLLIN, remote);
		this.flags = this.flags | Native.EPOLLRDHUP;
	}

	protected AbstractEpollStreamChannel(LinuxSocket fd, boolean active) {
		super(null, fd, Native.EPOLLIN, active);
		this.flags = this.flags | Native.EPOLLRDHUP;
	}

	@Override
	protected AbstractEpollUnsafe newUnsafe() {
		return new AbstractEpollStreamChannel.EpollStreamUnsafe();
	}

	@Override
	public ChannelMetadata metadata() {
		return METADATA;
	}

	public final ChannelFuture spliceTo(AbstractEpollStreamChannel ch, int len) {
		return this.spliceTo(ch, len, this.newPromise());
	}

	public final ChannelFuture spliceTo(AbstractEpollStreamChannel ch, int len, ChannelPromise promise) {
		if (ch.eventLoop() != this.eventLoop()) {
			throw new IllegalArgumentException("EventLoops are not the same.");
		} else if (len < 0) {
			throw new IllegalArgumentException("len: " + len + " (expected: >= 0)");
		} else if (ch.config().getEpollMode() == EpollMode.LEVEL_TRIGGERED && this.config().getEpollMode() == EpollMode.LEVEL_TRIGGERED) {
			ObjectUtil.checkNotNull(promise, "promise");
			if (!this.isOpen()) {
				promise.tryFailure(SPLICE_TO_CLOSED_CHANNEL_EXCEPTION);
			} else {
				this.addToSpliceQueue(new AbstractEpollStreamChannel.SpliceInChannelTask(ch, len, promise));
				this.failSpliceIfClosed(promise);
			}

			return promise;
		} else {
			throw new IllegalStateException("spliceTo() supported only when using " + EpollMode.LEVEL_TRIGGERED);
		}
	}

	public final ChannelFuture spliceTo(FileDescriptor ch, int offset, int len) {
		return this.spliceTo(ch, offset, len, this.newPromise());
	}

	public final ChannelFuture spliceTo(FileDescriptor ch, int offset, int len, ChannelPromise promise) {
		if (len < 0) {
			throw new IllegalArgumentException("len: " + len + " (expected: >= 0)");
		} else if (offset < 0) {
			throw new IllegalArgumentException("offset must be >= 0 but was " + offset);
		} else if (this.config().getEpollMode() != EpollMode.LEVEL_TRIGGERED) {
			throw new IllegalStateException("spliceTo() supported only when using " + EpollMode.LEVEL_TRIGGERED);
		} else {
			ObjectUtil.checkNotNull(promise, "promise");
			if (!this.isOpen()) {
				promise.tryFailure(SPLICE_TO_CLOSED_CHANNEL_EXCEPTION);
			} else {
				this.addToSpliceQueue(new AbstractEpollStreamChannel.SpliceFdTask(ch, offset, len, promise));
				this.failSpliceIfClosed(promise);
			}

			return promise;
		}
	}

	private void failSpliceIfClosed(ChannelPromise promise) {
		if (!this.isOpen() && promise.tryFailure(FAIL_SPLICE_IF_CLOSED_CLOSED_CHANNEL_EXCEPTION)) {
			this.eventLoop().execute(new Runnable() {
				public void run() {
					AbstractEpollStreamChannel.this.clearSpliceQueue();
				}
			});
		}
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
				this.byteChannel = new AbstractEpollStreamChannel.EpollSocketWritableByteChannel();
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
					this.clearFlag(Native.EPOLLOUT);
					return;
				}

				writeSpinCount -= this.doWriteSingle(in);
			}
		} while (writeSpinCount > 0);

		if (writeSpinCount == 0) {
			this.clearFlag(Native.EPOLLOUT);
			this.eventLoop().execute(this.flushTask);
		} else {
			this.setFlag(Native.EPOLLOUT);
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
		} else if (msg instanceof AbstractEpollStreamChannel.SpliceOutTask) {
			if (!((AbstractEpollStreamChannel.SpliceOutTask)msg).spliceOut()) {
				return Integer.MAX_VALUE;
			} else {
				in.remove();
				return 1;
			}
		} else {
			throw new Error();
		}
	}

	private int doWriteMultiple(ChannelOutboundBuffer in) throws Exception {
		long maxBytesPerGatheringWrite = this.config().getMaxBytesPerGatheringWrite();
		if (PlatformDependent.hasUnsafe()) {
			IovArray array = ((EpollEventLoop)this.eventLoop()).cleanArray();
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
		} else if (!(msg instanceof FileRegion) && !(msg instanceof AbstractEpollStreamChannel.SpliceOutTask)) {
			throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
		} else {
			return msg;
		}
	}

	@Override
	protected final void doShutdownOutput() throws Exception {
		this.socket.shutdown(false, true);
	}

	private void shutdownInput0(ChannelPromise promise) {
		try {
			this.socket.shutdown(true, false);
			promise.setSuccess();
		} catch (Throwable var3) {
			promise.setFailure(var3);
		}
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
					((AbstractUnsafe)AbstractEpollStreamChannel.this.unsafe()).shutdownOutput(promise);
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
		Executor closeExecutor = ((AbstractEpollStreamChannel.EpollStreamUnsafe)this.unsafe()).prepareToClose();
		if (closeExecutor != null) {
			closeExecutor.execute(new Runnable() {
				public void run() {
					AbstractEpollStreamChannel.this.shutdownInput0(promise);
				}
			});
		} else {
			EventLoop loop = this.eventLoop();
			if (loop.inEventLoop()) {
				this.shutdownInput0(promise);
			} else {
				loop.execute(new Runnable() {
					public void run() {
						AbstractEpollStreamChannel.this.shutdownInput0(promise);
					}
				});
			}
		}

		return promise;
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
					AbstractEpollStreamChannel.this.shutdownOutputDone(shutdownOutputFuture, promise);
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
					AbstractEpollStreamChannel.shutdownDone(shutdownOutputFuture, shutdownInputFuture, promise);
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

	@Override
	protected void doClose() throws Exception {
		try {
			super.doClose();
		} finally {
			safeClosePipe(this.pipeIn);
			safeClosePipe(this.pipeOut);
			this.clearSpliceQueue();
		}
	}

	private void clearSpliceQueue() {
		if (this.spliceQueue != null) {
			while (true) {
				AbstractEpollStreamChannel.SpliceInTask task = (AbstractEpollStreamChannel.SpliceInTask)this.spliceQueue.poll();
				if (task == null) {
					return;
				}

				task.promise.tryFailure(CLEAR_SPLICE_QUEUE_CLOSED_CHANNEL_EXCEPTION);
			}
		}
	}

	private static void safeClosePipe(FileDescriptor fd) {
		if (fd != null) {
			try {
				fd.close();
			} catch (IOException var2) {
				if (logger.isWarnEnabled()) {
					logger.warn("Error while closing a pipe", (Throwable)var2);
				}
			}
		}
	}

	private void addToSpliceQueue(AbstractEpollStreamChannel.SpliceInTask task) {
		EventLoop eventLoop = this.eventLoop();
		if (eventLoop.inEventLoop()) {
			this.addToSpliceQueue0(task);
		} else {
			eventLoop.execute(new Runnable() {
				public void run() {
					AbstractEpollStreamChannel.this.addToSpliceQueue0(task);
				}
			});
		}
	}

	private void addToSpliceQueue0(AbstractEpollStreamChannel.SpliceInTask task) {
		if (this.spliceQueue == null) {
			this.spliceQueue = PlatformDependent.newMpscQueue();
		}

		this.spliceQueue.add(task);
	}

	private final class EpollSocketWritableByteChannel extends SocketWritableByteChannel {
		EpollSocketWritableByteChannel() {
			super(AbstractEpollStreamChannel.this.socket);
		}

		@Override
		protected ByteBufAllocator alloc() {
			return AbstractEpollStreamChannel.this.alloc();
		}
	}

	class EpollStreamUnsafe extends AbstractEpollUnsafe {
		EpollStreamUnsafe() {
			super(AbstractEpollStreamChannel.this);
		}

		@Override
		protected Executor prepareToClose() {
			return super.prepareToClose();
		}

		private void handleReadException(ChannelPipeline pipeline, ByteBuf byteBuf, Throwable cause, boolean close, EpollRecvByteAllocatorHandle allocHandle) {
			if (byteBuf != null) {
				if (byteBuf.isReadable()) {
					this.readPending = false;
					pipeline.fireChannelRead(byteBuf);
				} else {
					byteBuf.release();
				}
			}

			allocHandle.readComplete();
			pipeline.fireChannelReadComplete();
			pipeline.fireExceptionCaught(cause);
			if (close || cause instanceof IOException) {
				this.shutdownInput(false);
			}
		}

		@Override
		EpollRecvByteAllocatorHandle newEpollHandle(ExtendedHandle handle) {
			return new EpollRecvByteAllocatorStreamingHandle(handle);
		}

		@Override
		void epollInReady() {
			ChannelConfig config = AbstractEpollStreamChannel.this.config();
			if (AbstractEpollStreamChannel.this.shouldBreakEpollInReady(config)) {
				this.clearEpollIn0();
			} else {
				EpollRecvByteAllocatorHandle allocHandle = this.recvBufAllocHandle();
				allocHandle.edgeTriggered(AbstractEpollStreamChannel.this.isFlagSet(Native.EPOLLET));
				ChannelPipeline pipeline = AbstractEpollStreamChannel.this.pipeline();
				ByteBufAllocator allocator = config.getAllocator();
				allocHandle.reset(config);
				this.epollInBefore();
				ByteBuf byteBuf = null;
				boolean close = false;

				try {
					do {
						if (AbstractEpollStreamChannel.this.spliceQueue != null) {
							AbstractEpollStreamChannel.SpliceInTask spliceTask = (AbstractEpollStreamChannel.SpliceInTask)AbstractEpollStreamChannel.this.spliceQueue.peek();
							if (spliceTask != null) {
								if (!spliceTask.spliceIn(allocHandle)) {
									break;
								}

								if (AbstractEpollStreamChannel.this.isActive()) {
									AbstractEpollStreamChannel.this.spliceQueue.remove();
								}
								continue;
							}
						}

						byteBuf = allocHandle.allocate(allocator);
						allocHandle.lastBytesRead(AbstractEpollStreamChannel.this.doReadBytes(byteBuf));
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
						if (AbstractEpollStreamChannel.this.shouldBreakEpollInReady(config)) {
							break;
						}
					} while (allocHandle.continueReading());

					allocHandle.readComplete();
					pipeline.fireChannelReadComplete();
					if (close) {
						this.shutdownInput(false);
					}
				} catch (Throwable var11) {
					this.handleReadException(pipeline, byteBuf, var11, close, allocHandle);
				} finally {
					this.epollInFinally(config);
				}
			}
		}
	}

	private final class SpliceFdTask extends AbstractEpollStreamChannel.SpliceInTask {
		private final FileDescriptor fd;
		private final ChannelPromise promise;
		private final int offset;

		SpliceFdTask(FileDescriptor fd, int offset, int len, ChannelPromise promise) {
			super(len, promise);
			this.fd = fd;
			this.promise = promise;
			this.offset = offset;
		}

		@Override
		public boolean spliceIn(Handle handle) {
			assert AbstractEpollStreamChannel.this.eventLoop().inEventLoop();

			if (this.len == 0) {
				this.promise.setSuccess();
				return true;
			} else {
				try {
					FileDescriptor[] pipe = FileDescriptor.pipe();
					FileDescriptor pipeIn = pipe[0];
					FileDescriptor pipeOut = pipe[1];

					try {
						int splicedIn = this.spliceIn(pipeOut, handle);
						if (splicedIn > 0) {
							if (this.len != Integer.MAX_VALUE) {
								this.len -= splicedIn;
							}

							do {
								int splicedOut = Native.splice(pipeIn.intValue(), -1L, this.fd.intValue(), (long)this.offset, (long)splicedIn);
								splicedIn -= splicedOut;
							} while (splicedIn > 0);

							if (this.len == 0) {
								this.promise.setSuccess();
								return true;
							}
						}

						return false;
					} finally {
						AbstractEpollStreamChannel.safeClosePipe(pipeIn);
						AbstractEpollStreamChannel.safeClosePipe(pipeOut);
					}
				} catch (Throwable var11) {
					this.promise.setFailure(var11);
					return true;
				}
			}
		}
	}

	private final class SpliceInChannelTask extends AbstractEpollStreamChannel.SpliceInTask implements ChannelFutureListener {
		private final AbstractEpollStreamChannel ch;

		SpliceInChannelTask(AbstractEpollStreamChannel ch, int len, ChannelPromise promise) {
			super(len, promise);
			this.ch = ch;
		}

		public void operationComplete(ChannelFuture future) throws Exception {
			if (!future.isSuccess()) {
				this.promise.setFailure(future.cause());
			}
		}

		@Override
		public boolean spliceIn(Handle handle) {
			assert this.ch.eventLoop().inEventLoop();

			if (this.len == 0) {
				this.promise.setSuccess();
				return true;
			} else {
				try {
					FileDescriptor pipeOut = this.ch.pipeOut;
					if (pipeOut == null) {
						FileDescriptor[] pipe = FileDescriptor.pipe();
						this.ch.pipeIn = pipe[0];
						pipeOut = this.ch.pipeOut = pipe[1];
					}

					int splicedIn = this.spliceIn(pipeOut, handle);
					if (splicedIn > 0) {
						if (this.len != Integer.MAX_VALUE) {
							this.len -= splicedIn;
						}

						ChannelPromise splicePromise;
						if (this.len == 0) {
							splicePromise = this.promise;
						} else {
							splicePromise = this.ch.newPromise().addListener(this);
						}

						boolean autoRead = AbstractEpollStreamChannel.this.config().isAutoRead();
						this.ch.unsafe().write(AbstractEpollStreamChannel.this.new SpliceOutTask(this.ch, splicedIn, autoRead), splicePromise);
						this.ch.unsafe().flush();
						if (autoRead && !splicePromise.isDone()) {
							AbstractEpollStreamChannel.this.config().setAutoRead(false);
						}
					}

					return this.len == 0;
				} catch (Throwable var6) {
					this.promise.setFailure(var6);
					return true;
				}
			}
		}
	}

	protected abstract class SpliceInTask {
		final ChannelPromise promise;
		int len;

		protected SpliceInTask(int len, ChannelPromise promise) {
			this.promise = promise;
			this.len = len;
		}

		abstract boolean spliceIn(Handle handle);

		protected final int spliceIn(FileDescriptor pipeOut, Handle handle) throws IOException {
			int length = Math.min(handle.guess(), this.len);
			int splicedIn = 0;

			while (true) {
				int localSplicedIn = Native.splice(AbstractEpollStreamChannel.this.socket.intValue(), -1L, pipeOut.intValue(), -1L, (long)length);
				if (localSplicedIn == 0) {
					return splicedIn;
				}

				splicedIn += localSplicedIn;
				length -= localSplicedIn;
			}
		}
	}

	private final class SpliceOutTask {
		private final AbstractEpollStreamChannel ch;
		private final boolean autoRead;
		private int len;

		SpliceOutTask(AbstractEpollStreamChannel ch, int len, boolean autoRead) {
			this.ch = ch;
			this.len = len;
			this.autoRead = autoRead;
		}

		public boolean spliceOut() throws Exception {
			assert this.ch.eventLoop().inEventLoop();

			try {
				int splicedOut = Native.splice(this.ch.pipeIn.intValue(), -1L, this.ch.socket.intValue(), -1L, (long)this.len);
				this.len -= splicedOut;
				if (this.len == 0) {
					if (this.autoRead) {
						AbstractEpollStreamChannel.this.config().setAutoRead(true);
					}

					return true;
				} else {
					return false;
				}
			} catch (IOException var2) {
				if (this.autoRead) {
					AbstractEpollStreamChannel.this.config().setAutoRead(true);
				}

				throw var2;
			}
		}
	}
}
