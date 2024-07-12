package io.netty.channel.sctp.nio;

import com.sun.nio.sctp.SctpChannel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.sctp.DefaultSctpServerChannelConfig;
import io.netty.channel.sctp.SctpServerChannel;
import io.netty.channel.sctp.SctpServerChannelConfig;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class NioSctpServerChannel extends AbstractNioMessageChannel implements SctpServerChannel {
	private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
	private final SctpServerChannelConfig config = new NioSctpServerChannel.NioSctpServerChannelConfig(this, this.javaChannel());

	private static com.sun.nio.sctp.SctpServerChannel newSocket() {
		try {
			return com.sun.nio.sctp.SctpServerChannel.open();
		} catch (IOException var1) {
			throw new ChannelException("Failed to open a server socket.", var1);
		}
	}

	public NioSctpServerChannel() {
		super(null, newSocket(), 16);
	}

	@Override
	public ChannelMetadata metadata() {
		return METADATA;
	}

	@Override
	public Set<InetSocketAddress> allLocalAddresses() {
		try {
			Set<SocketAddress> allLocalAddresses = this.javaChannel().getAllLocalAddresses();
			Set<InetSocketAddress> addresses = new LinkedHashSet(allLocalAddresses.size());

			for (SocketAddress socketAddress : allLocalAddresses) {
				addresses.add((InetSocketAddress)socketAddress);
			}

			return addresses;
		} catch (Throwable var5) {
			return Collections.emptySet();
		}
	}

	@Override
	public SctpServerChannelConfig config() {
		return this.config;
	}

	@Override
	public boolean isActive() {
		return this.isOpen() && !this.allLocalAddresses().isEmpty();
	}

	public InetSocketAddress remoteAddress() {
		return null;
	}

	@Override
	public InetSocketAddress localAddress() {
		return (InetSocketAddress)super.localAddress();
	}

	protected com.sun.nio.sctp.SctpServerChannel javaChannel() {
		return (com.sun.nio.sctp.SctpServerChannel)super.javaChannel();
	}

	@Override
	protected SocketAddress localAddress0() {
		try {
			Iterator<SocketAddress> i = this.javaChannel().getAllLocalAddresses().iterator();
			if (i.hasNext()) {
				return (SocketAddress)i.next();
			}
		} catch (IOException var2) {
		}

		return null;
	}

	@Override
	protected void doBind(SocketAddress localAddress) throws Exception {
		this.javaChannel().bind(localAddress, this.config.getBacklog());
	}

	@Override
	protected void doClose() throws Exception {
		this.javaChannel().close();
	}

	@Override
	protected int doReadMessages(List<Object> buf) throws Exception {
		SctpChannel ch = this.javaChannel().accept();
		if (ch == null) {
			return 0;
		} else {
			buf.add(new NioSctpChannel(this, ch));
			return 1;
		}
	}

	@Override
	public ChannelFuture bindAddress(InetAddress localAddress) {
		return this.bindAddress(localAddress, this.newPromise());
	}

	@Override
	public ChannelFuture bindAddress(InetAddress localAddress, ChannelPromise promise) {
		if (this.eventLoop().inEventLoop()) {
			try {
				this.javaChannel().bindAddress(localAddress);
				promise.setSuccess();
			} catch (Throwable var4) {
				promise.setFailure(var4);
			}
		} else {
			this.eventLoop().execute(new Runnable() {
				public void run() {
					NioSctpServerChannel.this.bindAddress(localAddress, promise);
				}
			});
		}

		return promise;
	}

	@Override
	public ChannelFuture unbindAddress(InetAddress localAddress) {
		return this.unbindAddress(localAddress, this.newPromise());
	}

	@Override
	public ChannelFuture unbindAddress(InetAddress localAddress, ChannelPromise promise) {
		if (this.eventLoop().inEventLoop()) {
			try {
				this.javaChannel().unbindAddress(localAddress);
				promise.setSuccess();
			} catch (Throwable var4) {
				promise.setFailure(var4);
			}
		} else {
			this.eventLoop().execute(new Runnable() {
				public void run() {
					NioSctpServerChannel.this.unbindAddress(localAddress, promise);
				}
			});
		}

		return promise;
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
	protected Object filterOutboundMessage(Object msg) throws Exception {
		throw new UnsupportedOperationException();
	}

	private final class NioSctpServerChannelConfig extends DefaultSctpServerChannelConfig {
		private NioSctpServerChannelConfig(NioSctpServerChannel channel, com.sun.nio.sctp.SctpServerChannel javaChannel) {
			super(channel, javaChannel);
		}

		@Override
		protected void autoReadCleared() {
			NioSctpServerChannel.this.clearReadPending();
		}
	}
}
