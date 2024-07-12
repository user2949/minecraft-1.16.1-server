package io.netty.channel.nio;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ServerChannel;
import io.netty.channel.RecvByteBufAllocator.Handle;
import io.netty.channel.nio.AbstractNioChannel.AbstractNioUnsafe;
import java.io.IOException;
import java.net.PortUnreachableException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNioMessageChannel extends AbstractNioChannel {
	boolean inputShutdown;

	protected AbstractNioMessageChannel(Channel parent, SelectableChannel ch, int readInterestOp) {
		super(parent, ch, readInterestOp);
	}

	protected AbstractNioUnsafe newUnsafe() {
		return new AbstractNioMessageChannel.NioMessageUnsafe();
	}

	@Override
	protected void doBeginRead() throws Exception {
		if (!this.inputShutdown) {
			super.doBeginRead();
		}
	}

	@Override
	protected void doWrite(ChannelOutboundBuffer in) throws Exception {
		SelectionKey key = this.selectionKey();
		int interestOps = key.interestOps();

		while (true) {
			Object msg = in.current();
			if (msg == null) {
				if ((interestOps & 4) != 0) {
					key.interestOps(interestOps & -5);
				}
				break;
			}

			try {
				boolean done = false;

				for (int i = this.config().getWriteSpinCount() - 1; i >= 0; i--) {
					if (this.doWriteMessage(msg, in)) {
						done = true;
						break;
					}
				}

				if (!done) {
					if ((interestOps & 4) == 0) {
						key.interestOps(interestOps | 4);
					}
					break;
				}

				in.remove();
			} catch (Exception var7) {
				if (!this.continueOnWriteError()) {
					throw var7;
				}

				in.remove(var7);
			}
		}
	}

	protected boolean continueOnWriteError() {
		return false;
	}

	protected boolean closeOnReadError(Throwable cause) {
		if (!this.isActive()) {
			return true;
		} else if (cause instanceof PortUnreachableException) {
			return false;
		} else {
			return cause instanceof IOException ? !(this instanceof ServerChannel) : true;
		}
	}

	protected abstract int doReadMessages(List<Object> list) throws Exception;

	protected abstract boolean doWriteMessage(Object object, ChannelOutboundBuffer channelOutboundBuffer) throws Exception;

	private final class NioMessageUnsafe extends AbstractNioUnsafe {
		private final List<Object> readBuf = new ArrayList();

		private NioMessageUnsafe() {
			super(AbstractNioMessageChannel.this);
		}

		@Override
		public void read() {
			assert AbstractNioMessageChannel.this.eventLoop().inEventLoop();

			ChannelConfig config = AbstractNioMessageChannel.this.config();
			ChannelPipeline pipeline = AbstractNioMessageChannel.this.pipeline();
			Handle allocHandle = AbstractNioMessageChannel.this.unsafe().recvBufAllocHandle();
			allocHandle.reset(config);
			boolean closed = false;
			Throwable exception = null;

			try {
				try {
					do {
						int localRead = AbstractNioMessageChannel.this.doReadMessages(this.readBuf);
						if (localRead == 0) {
							break;
						}

						if (localRead < 0) {
							closed = true;
							break;
						}

						allocHandle.incMessagesRead(localRead);
					} while (allocHandle.continueReading());
				} catch (Throwable var11) {
					exception = var11;
				}

				int size = this.readBuf.size();

				for (int i = 0; i < size; i++) {
					AbstractNioMessageChannel.this.readPending = false;
					pipeline.fireChannelRead(this.readBuf.get(i));
				}

				this.readBuf.clear();
				allocHandle.readComplete();
				pipeline.fireChannelReadComplete();
				if (exception != null) {
					closed = AbstractNioMessageChannel.this.closeOnReadError(exception);
					pipeline.fireExceptionCaught(exception);
				}

				if (closed) {
					AbstractNioMessageChannel.this.inputShutdown = true;
					if (AbstractNioMessageChannel.this.isOpen()) {
						this.close(this.voidPromise());
					}
				}
			} finally {
				if (!AbstractNioMessageChannel.this.readPending && !config.isAutoRead()) {
					this.removeReadOp();
				}
			}
		}
	}
}
