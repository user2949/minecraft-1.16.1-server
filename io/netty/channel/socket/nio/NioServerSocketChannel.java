package io.netty.channel.socket.nio;

import io.netty.channel.ChannelException;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.socket.DefaultServerSocketChannelConfig;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.ServerSocketChannelConfig;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.List;

public class NioServerSocketChannel extends AbstractNioMessageChannel implements ServerSocketChannel {
	private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
	private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioServerSocketChannel.class);
	private final ServerSocketChannelConfig config = new NioServerSocketChannel.NioServerSocketChannelConfig(this, this.javaChannel().socket());

	private static java.nio.channels.ServerSocketChannel newSocket(SelectorProvider provider) {
		try {
			return provider.openServerSocketChannel();
		} catch (IOException var2) {
			throw new ChannelException("Failed to open a server socket.", var2);
		}
	}

	public NioServerSocketChannel() {
		this(newSocket(DEFAULT_SELECTOR_PROVIDER));
	}

	public NioServerSocketChannel(SelectorProvider provider) {
		this(newSocket(provider));
	}

	public NioServerSocketChannel(java.nio.channels.ServerSocketChannel channel) {
		super(null, channel, 16);
	}

	@Override
	public InetSocketAddress localAddress() {
		return (InetSocketAddress)super.localAddress();
	}

	@Override
	public ChannelMetadata metadata() {
		return METADATA;
	}

	@Override
	public ServerSocketChannelConfig config() {
		return this.config;
	}

	@Override
	public boolean isActive() {
		return this.javaChannel().socket().isBound();
	}

	@Override
	public InetSocketAddress remoteAddress() {
		return null;
	}

	protected java.nio.channels.ServerSocketChannel javaChannel() {
		return (java.nio.channels.ServerSocketChannel)super.javaChannel();
	}

	@Override
	protected SocketAddress localAddress0() {
		return SocketUtils.localSocketAddress(this.javaChannel().socket());
	}

	@Override
	protected void doBind(SocketAddress localAddress) throws Exception {
		if (PlatformDependent.javaVersion() >= 7) {
			this.javaChannel().bind(localAddress, this.config.getBacklog());
		} else {
			this.javaChannel().socket().bind(localAddress, this.config.getBacklog());
		}
	}

	@Override
	protected void doClose() throws Exception {
		this.javaChannel().close();
	}

	@Override
	protected int doReadMessages(List<Object> buf) throws Exception {
		SocketChannel ch = SocketUtils.accept(this.javaChannel());

		try {
			if (ch != null) {
				buf.add(new NioSocketChannel(this, ch));
				return 1;
			}
		} catch (Throwable var6) {
			logger.warn("Failed to create a new channel from an accepted socket.", var6);

			try {
				ch.close();
			} catch (Throwable var5) {
				logger.warn("Failed to close a socket.", var5);
			}
		}

		return 0;
	}

	@Override
	protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void doFinishConnect() throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected SocketAddress remoteAddress0() {
		return null;
	}

	@Override
	protected void doDisconnect() throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected final Object filterOutboundMessage(Object msg) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean closeOnReadError(Throwable cause) {
		return super.closeOnReadError(cause);
	}

	private final class NioServerSocketChannelConfig extends DefaultServerSocketChannelConfig {
		private NioServerSocketChannelConfig(NioServerSocketChannel channel, ServerSocket javaSocket) {
			super(channel, javaSocket);
		}

		@Override
		protected void autoReadCleared() {
			NioServerSocketChannel.this.clearReadPending();
		}
	}
}
