package io.netty.channel.kqueue;

import io.netty.channel.Channel;
import io.netty.channel.kqueue.AbstractKQueueChannel.AbstractKQueueUnsafe;
import io.netty.channel.kqueue.AbstractKQueueStreamChannel.KQueueStreamUnsafe;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

public final class KQueueSocketChannel extends AbstractKQueueStreamChannel implements SocketChannel {
	private final KQueueSocketChannelConfig config = new KQueueSocketChannelConfig(this);

	public KQueueSocketChannel() {
		super(null, BsdSocket.newSocketStream(), false);
	}

	public KQueueSocketChannel(int fd) {
		super(new BsdSocket(fd));
	}

	KQueueSocketChannel(Channel parent, BsdSocket fd, InetSocketAddress remoteAddress) {
		super(parent, fd, remoteAddress);
	}

	@Override
	public InetSocketAddress remoteAddress() {
		return (InetSocketAddress)super.remoteAddress();
	}

	@Override
	public InetSocketAddress localAddress() {
		return (InetSocketAddress)super.localAddress();
	}

	public KQueueSocketChannelConfig config() {
		return this.config;
	}

	@Override
	public ServerSocketChannel parent() {
		return (ServerSocketChannel)super.parent();
	}

	@Override
	protected AbstractKQueueUnsafe newUnsafe() {
		return new KQueueSocketChannel.KQueueSocketChannelUnsafe();
	}

	private final class KQueueSocketChannelUnsafe extends KQueueStreamUnsafe {
		private KQueueSocketChannelUnsafe() {
			super(KQueueSocketChannel.this);
		}

		@Override
		protected Executor prepareToClose() {
			try {
				if (KQueueSocketChannel.this.isOpen() && KQueueSocketChannel.this.config().getSoLinger() > 0) {
					((KQueueEventLoop)KQueueSocketChannel.this.eventLoop()).remove(KQueueSocketChannel.this);
					return GlobalEventExecutor.INSTANCE;
				}
			} catch (Throwable var2) {
			}

			return null;
		}
	}
}
