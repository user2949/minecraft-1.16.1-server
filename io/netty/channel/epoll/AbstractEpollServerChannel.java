package io.netty.channel.epoll;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.AbstractEpollChannel.AbstractEpollUnsafe;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public abstract class AbstractEpollServerChannel extends AbstractEpollChannel implements ServerChannel {
	private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);

	protected AbstractEpollServerChannel(int fd) {
		this(new LinuxSocket(fd), false);
	}

	AbstractEpollServerChannel(LinuxSocket fd) {
		this(fd, isSoErrorZero(fd));
	}

	AbstractEpollServerChannel(LinuxSocket fd, boolean active) {
		super(null, fd, Native.EPOLLIN, active);
	}

	@Override
	public ChannelMetadata metadata() {
		return METADATA;
	}

	@Override
	protected boolean isCompatible(EventLoop loop) {
		return loop instanceof EpollEventLoop;
	}

	protected InetSocketAddress remoteAddress0() {
		return null;
	}

	@Override
	protected AbstractEpollUnsafe newUnsafe() {
		return new AbstractEpollServerChannel.EpollServerSocketUnsafe();
	}

	@Override
	protected void doWrite(ChannelOutboundBuffer in) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Object filterOutboundMessage(Object msg) throws Exception {
		throw new UnsupportedOperationException();
	}

	abstract Channel newChildChannel(int integer1, byte[] arr, int integer3, int integer4) throws Exception;

	@Override
	protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
		throw new UnsupportedOperationException();
	}

	final class EpollServerSocketUnsafe extends AbstractEpollUnsafe {
		private final byte[] acceptedAddress = new byte[26];

		EpollServerSocketUnsafe() {
			super(AbstractEpollServerChannel.this);
		}

		@Override
		public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
			channelPromise.setFailure(new UnsupportedOperationException());
		}

		@Override
		void epollInReady() {
			assert AbstractEpollServerChannel.this.eventLoop().inEventLoop();

			ChannelConfig config = AbstractEpollServerChannel.this.config();
			if (AbstractEpollServerChannel.this.shouldBreakEpollInReady(config)) {
				this.clearEpollIn0();
			} else {
				EpollRecvByteAllocatorHandle allocHandle = this.recvBufAllocHandle();
				allocHandle.edgeTriggered(AbstractEpollServerChannel.this.isFlagSet(Native.EPOLLET));
				ChannelPipeline pipeline = AbstractEpollServerChannel.this.pipeline();
				allocHandle.reset(config);
				allocHandle.attemptedBytesRead(1);
				this.epollInBefore();
				Throwable exception = null;

				try {
					try {
						do {
							allocHandle.lastBytesRead(AbstractEpollServerChannel.this.socket.accept(this.acceptedAddress));
							if (allocHandle.lastBytesRead() == -1) {
								break;
							}

							allocHandle.incMessagesRead(1);
							this.readPending = false;
							pipeline.fireChannelRead(AbstractEpollServerChannel.this.newChildChannel(allocHandle.lastBytesRead(), this.acceptedAddress, 1, this.acceptedAddress[0]));
						} while (allocHandle.continueReading());
					} catch (Throwable var9) {
						exception = var9;
					}

					allocHandle.readComplete();
					pipeline.fireChannelReadComplete();
					if (exception != null) {
						pipeline.fireExceptionCaught(exception);
					}
				} finally {
					this.epollInFinally(config);
				}
			}
		}
	}
}
