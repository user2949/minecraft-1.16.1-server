package io.netty.channel.udt.nio;

import com.barchart.udt.TypeUDT;
import com.barchart.udt.nio.SocketChannelUDT;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.udt.DefaultUdtChannelConfig;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.UdtChannelConfig;
import io.netty.channel.udt.UdtMessage;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.List;

@Deprecated
public class NioUdtMessageConnectorChannel extends AbstractNioMessageChannel implements UdtChannel {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioUdtMessageConnectorChannel.class);
	private static final ChannelMetadata METADATA = new ChannelMetadata(false);
	private final UdtChannelConfig config;

	public NioUdtMessageConnectorChannel() {
		this(TypeUDT.DATAGRAM);
	}

	public NioUdtMessageConnectorChannel(Channel parent, SocketChannelUDT channelUDT) {
		super(parent, channelUDT, 1);

		try {
			channelUDT.configureBlocking(false);
			switch (channelUDT.socketUDT().status()) {
				case INIT:
				case OPENED:
					this.config = new DefaultUdtChannelConfig(this, channelUDT, true);
					break;
				default:
					this.config = new DefaultUdtChannelConfig(this, channelUDT, false);
			}
		} catch (Exception var6) {
			try {
				channelUDT.close();
			} catch (Exception var5) {
				if (logger.isWarnEnabled()) {
					logger.warn("Failed to close channel.", (Throwable)var5);
				}
			}

			throw new ChannelException("Failed to configure channel.", var6);
		}
	}

	public NioUdtMessageConnectorChannel(SocketChannelUDT channelUDT) {
		this(null, channelUDT);
	}

	public NioUdtMessageConnectorChannel(TypeUDT type) {
		this(NioUdtProvider.newConnectorChannelUDT(type));
	}

	@Override
	public UdtChannelConfig config() {
		return this.config;
	}

	@Override
	protected void doBind(SocketAddress localAddress) throws Exception {
		privilegedBind(this.javaChannel(), localAddress);
	}

	@Override
	protected void doClose() throws Exception {
		this.javaChannel().close();
	}

	@Override
	protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
		this.doBind((SocketAddress)(localAddress != null ? localAddress : new InetSocketAddress(0)));
		boolean success = false;

		boolean var5;
		try {
			boolean connected = SocketUtils.connect(this.javaChannel(), remoteAddress);
			if (!connected) {
				this.selectionKey().interestOps(this.selectionKey().interestOps() | 8);
			}

			success = true;
			var5 = connected;
		} finally {
			if (!success) {
				this.doClose();
			}
		}

		return var5;
	}

	@Override
	protected void doDisconnect() throws Exception {
		this.doClose();
	}

	@Override
	protected void doFinishConnect() throws Exception {
		if (this.javaChannel().finishConnect()) {
			this.selectionKey().interestOps(this.selectionKey().interestOps() & -9);
		} else {
			throw new Error("Provider error: failed to finish connect. Provider library should be upgraded.");
		}
	}

	@Override
	protected int doReadMessages(List<Object> buf) throws Exception {
		int maximumMessageSize = this.config.getReceiveBufferSize();
		ByteBuf byteBuf = this.config.getAllocator().directBuffer(maximumMessageSize);
		int receivedMessageSize = byteBuf.writeBytes(this.javaChannel(), maximumMessageSize);
		if (receivedMessageSize <= 0) {
			byteBuf.release();
			return 0;
		} else if (receivedMessageSize >= maximumMessageSize) {
			this.javaChannel().close();
			throw new ChannelException("Invalid config : increase receive buffer size to avoid message truncation");
		} else {
			buf.add(new UdtMessage(byteBuf));
			return 1;
		}
	}

	@Override
	protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
		UdtMessage message = (UdtMessage)msg;
		ByteBuf byteBuf = message.content();
		int messageSize = byteBuf.readableBytes();
		if (messageSize == 0) {
			return true;
		} else {
			long writtenBytes;
			if (byteBuf.nioBufferCount() == 1) {
				writtenBytes = (long)this.javaChannel().write(byteBuf.nioBuffer());
			} else {
				writtenBytes = this.javaChannel().write(byteBuf.nioBuffers());
			}

			if (writtenBytes > 0L && writtenBytes != (long)messageSize) {
				throw new Error("Provider error: failed to write message. Provider library should be upgraded.");
			} else {
				return writtenBytes > 0L;
			}
		}
	}

	@Override
	public boolean isActive() {
		SocketChannelUDT channelUDT = this.javaChannel();
		return channelUDT.isOpen() && channelUDT.isConnectFinished();
	}

	protected SocketChannelUDT javaChannel() {
		return (SocketChannelUDT)super.javaChannel();
	}

	@Override
	protected SocketAddress localAddress0() {
		return this.javaChannel().socket().getLocalSocketAddress();
	}

	@Override
	public ChannelMetadata metadata() {
		return METADATA;
	}

	@Override
	protected SocketAddress remoteAddress0() {
		return this.javaChannel().socket().getRemoteSocketAddress();
	}

	@Override
	public InetSocketAddress localAddress() {
		return (InetSocketAddress)super.localAddress();
	}

	@Override
	public InetSocketAddress remoteAddress() {
		return (InetSocketAddress)super.remoteAddress();
	}

	private static void privilegedBind(SocketChannelUDT socketChannel, SocketAddress localAddress) throws IOException {
		try {
			AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
				public Void run() throws IOException {
					socketChannel.bind(localAddress);
					return null;
				}
			});
		} catch (PrivilegedActionException var3) {
			throw (IOException)var3.getCause();
		}
	}
}
