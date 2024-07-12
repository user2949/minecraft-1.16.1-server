package io.netty.channel;

import io.netty.channel.AbstractChannel.AbstractUnsafe;
import java.net.SocketAddress;

public abstract class AbstractServerChannel extends AbstractChannel implements ServerChannel {
	private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);

	protected AbstractServerChannel() {
		super(null);
	}

	@Override
	public ChannelMetadata metadata() {
		return METADATA;
	}

	@Override
	public SocketAddress remoteAddress() {
		return null;
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
	protected AbstractUnsafe newUnsafe() {
		return new AbstractServerChannel.DefaultServerUnsafe();
	}

	@Override
	protected void doWrite(ChannelOutboundBuffer in) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected final Object filterOutboundMessage(Object msg) {
		throw new UnsupportedOperationException();
	}

	private final class DefaultServerUnsafe extends AbstractUnsafe {
		private DefaultServerUnsafe() {
			super(AbstractServerChannel.this);
		}

		@Override
		public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
			this.safeSetFailure(promise, new UnsupportedOperationException());
		}
	}
}
