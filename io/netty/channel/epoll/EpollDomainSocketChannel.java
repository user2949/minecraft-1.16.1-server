package io.netty.channel.epoll;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.epoll.AbstractEpollChannel.AbstractEpollUnsafe;
import io.netty.channel.epoll.AbstractEpollStreamChannel.EpollStreamUnsafe;
import io.netty.channel.unix.DomainSocketAddress;
import io.netty.channel.unix.DomainSocketChannel;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.PeerCredentials;
import java.io.IOException;
import java.net.SocketAddress;

public final class EpollDomainSocketChannel extends AbstractEpollStreamChannel implements DomainSocketChannel {
	private final EpollDomainSocketChannelConfig config = new EpollDomainSocketChannelConfig(this);
	private volatile DomainSocketAddress local;
	private volatile DomainSocketAddress remote;

	public EpollDomainSocketChannel() {
		super(LinuxSocket.newSocketDomain(), false);
	}

	EpollDomainSocketChannel(Channel parent, FileDescriptor fd) {
		super(parent, new LinuxSocket(fd.intValue()));
	}

	public EpollDomainSocketChannel(int fd) {
		super(fd);
	}

	public EpollDomainSocketChannel(Channel parent, LinuxSocket fd) {
		super(parent, fd);
	}

	public EpollDomainSocketChannel(int fd, boolean active) {
		super(new LinuxSocket(fd), active);
	}

	@Override
	protected AbstractEpollUnsafe newUnsafe() {
		return new EpollDomainSocketChannel.EpollDomainUnsafe();
	}

	protected DomainSocketAddress localAddress0() {
		return this.local;
	}

	protected DomainSocketAddress remoteAddress0() {
		return this.remote;
	}

	@Override
	protected void doBind(SocketAddress localAddress) throws Exception {
		this.socket.bind(localAddress);
		this.local = (DomainSocketAddress)localAddress;
	}

	public EpollDomainSocketChannelConfig config() {
		return this.config;
	}

	@Override
	protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
		if (super.doConnect(remoteAddress, localAddress)) {
			this.local = (DomainSocketAddress)localAddress;
			this.remote = (DomainSocketAddress)remoteAddress;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public DomainSocketAddress remoteAddress() {
		return (DomainSocketAddress)super.remoteAddress();
	}

	@Override
	public DomainSocketAddress localAddress() {
		return (DomainSocketAddress)super.localAddress();
	}

	@Override
	protected int doWriteSingle(ChannelOutboundBuffer in) throws Exception {
		Object msg = in.current();
		if (msg instanceof FileDescriptor && this.socket.sendFd(((FileDescriptor)msg).intValue()) > 0) {
			in.remove();
			return 1;
		} else {
			return super.doWriteSingle(in);
		}
	}

	@Override
	protected Object filterOutboundMessage(Object msg) {
		return msg instanceof FileDescriptor ? msg : super.filterOutboundMessage(msg);
	}

	public PeerCredentials peerCredentials() throws IOException {
		return this.socket.getPeerCredentials();
	}

	private final class EpollDomainUnsafe extends EpollStreamUnsafe {
		private EpollDomainUnsafe() {
			super(EpollDomainSocketChannel.this);
		}

		@Override
		void epollInReady() {
			switch (EpollDomainSocketChannel.this.config().getReadMode()) {
				case BYTES:
					super.epollInReady();
					break;
				case FILE_DESCRIPTORS:
					this.epollInReadFd();
					break;
				default:
					throw new Error();
			}
		}

		private void epollInReadFd() {
			if (EpollDomainSocketChannel.this.socket.isInputShutdown()) {
				this.clearEpollIn0();
			} else {
				ChannelConfig config = EpollDomainSocketChannel.this.config();
				EpollRecvByteAllocatorHandle allocHandle = this.recvBufAllocHandle();
				allocHandle.edgeTriggered(EpollDomainSocketChannel.this.isFlagSet(Native.EPOLLET));
				ChannelPipeline pipeline = EpollDomainSocketChannel.this.pipeline();
				allocHandle.reset(config);
				this.epollInBefore();

				try {
					while (true) {
						allocHandle.lastBytesRead(EpollDomainSocketChannel.this.socket.recvFd());
						switch (allocHandle.lastBytesRead()) {
							case -1:
								this.close(this.voidPromise());
								return;
							default:
								allocHandle.incMessagesRead(1);
								this.readPending = false;
								pipeline.fireChannelRead(new FileDescriptor(allocHandle.lastBytesRead()));
								if (allocHandle.continueReading()) {
									break;
								}
							case 0:
								allocHandle.readComplete();
								pipeline.fireChannelReadComplete();
								return;
						}
					}
				} catch (Throwable var8) {
					allocHandle.readComplete();
					pipeline.fireChannelReadComplete();
					pipeline.fireExceptionCaught(var8);
				} finally {
					this.epollInFinally(config);
				}
			}
		}
	}
}
