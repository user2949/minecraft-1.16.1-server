package io.netty.channel.kqueue;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultAddressedEnvelope;
import io.netty.channel.kqueue.AbstractKQueueChannel.AbstractKQueueUnsafe;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.unix.DatagramSocketAddress;
import io.netty.channel.unix.IovArray;
import io.netty.channel.unix.UnixChannelUtil;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

public final class KQueueDatagramChannel extends AbstractKQueueChannel implements DatagramChannel {
	private static final ChannelMetadata METADATA = new ChannelMetadata(true);
	private static final String EXPECTED_TYPES = " (expected: "
		+ StringUtil.simpleClassName(DatagramPacket.class)
		+ ", "
		+ StringUtil.simpleClassName(AddressedEnvelope.class)
		+ '<'
		+ StringUtil.simpleClassName(ByteBuf.class)
		+ ", "
		+ StringUtil.simpleClassName(InetSocketAddress.class)
		+ ">, "
		+ StringUtil.simpleClassName(ByteBuf.class)
		+ ')';
	private volatile boolean connected;
	private final KQueueDatagramChannelConfig config = new KQueueDatagramChannelConfig(this);

	public KQueueDatagramChannel() {
		super(null, BsdSocket.newSocketDgram(), false);
	}

	public KQueueDatagramChannel(int fd) {
		this(new BsdSocket(fd), true);
	}

	KQueueDatagramChannel(BsdSocket socket, boolean active) {
		super(null, socket, active);
	}

	@Override
	public InetSocketAddress remoteAddress() {
		return (InetSocketAddress)super.remoteAddress();
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
	public boolean isActive() {
		return this.socket.isOpen() && (this.config.getActiveOnOpen() && this.isRegistered() || this.active);
	}

	@Override
	public boolean isConnected() {
		return this.connected;
	}

	@Override
	public ChannelFuture joinGroup(InetAddress multicastAddress) {
		return this.joinGroup(multicastAddress, this.newPromise());
	}

	@Override
	public ChannelFuture joinGroup(InetAddress multicastAddress, ChannelPromise promise) {
		try {
			return this.joinGroup(multicastAddress, NetworkInterface.getByInetAddress(this.localAddress().getAddress()), null, promise);
		} catch (SocketException var4) {
			promise.setFailure(var4);
			return promise;
		}
	}

	@Override
	public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
		return this.joinGroup(multicastAddress, networkInterface, this.newPromise());
	}

	@Override
	public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
		return this.joinGroup(multicastAddress.getAddress(), networkInterface, null, promise);
	}

	@Override
	public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
		return this.joinGroup(multicastAddress, networkInterface, source, this.newPromise());
	}

	@Override
	public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
		if (multicastAddress == null) {
			throw new NullPointerException("multicastAddress");
		} else if (networkInterface == null) {
			throw new NullPointerException("networkInterface");
		} else {
			promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
			return promise;
		}
	}

	@Override
	public ChannelFuture leaveGroup(InetAddress multicastAddress) {
		return this.leaveGroup(multicastAddress, this.newPromise());
	}

	@Override
	public ChannelFuture leaveGroup(InetAddress multicastAddress, ChannelPromise promise) {
		try {
			return this.leaveGroup(multicastAddress, NetworkInterface.getByInetAddress(this.localAddress().getAddress()), null, promise);
		} catch (SocketException var4) {
			promise.setFailure(var4);
			return promise;
		}
	}

	@Override
	public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
		return this.leaveGroup(multicastAddress, networkInterface, this.newPromise());
	}

	@Override
	public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
		return this.leaveGroup(multicastAddress.getAddress(), networkInterface, null, promise);
	}

	@Override
	public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
		return this.leaveGroup(multicastAddress, networkInterface, source, this.newPromise());
	}

	@Override
	public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
		if (multicastAddress == null) {
			throw new NullPointerException("multicastAddress");
		} else if (networkInterface == null) {
			throw new NullPointerException("networkInterface");
		} else {
			promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
			return promise;
		}
	}

	@Override
	public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock) {
		return this.block(multicastAddress, networkInterface, sourceToBlock, this.newPromise());
	}

	@Override
	public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock, ChannelPromise promise) {
		if (multicastAddress == null) {
			throw new NullPointerException("multicastAddress");
		} else if (sourceToBlock == null) {
			throw new NullPointerException("sourceToBlock");
		} else if (networkInterface == null) {
			throw new NullPointerException("networkInterface");
		} else {
			promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
			return promise;
		}
	}

	@Override
	public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock) {
		return this.block(multicastAddress, sourceToBlock, this.newPromise());
	}

	@Override
	public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock, ChannelPromise promise) {
		try {
			return this.block(multicastAddress, NetworkInterface.getByInetAddress(this.localAddress().getAddress()), sourceToBlock, promise);
		} catch (Throwable var5) {
			promise.setFailure(var5);
			return promise;
		}
	}

	@Override
	protected AbstractKQueueUnsafe newUnsafe() {
		return new KQueueDatagramChannel.KQueueDatagramChannelUnsafe();
	}

	@Override
	protected void doBind(SocketAddress localAddress) throws Exception {
		super.doBind(localAddress);
		this.active = true;
	}

	@Override
	protected void doWrite(ChannelOutboundBuffer in) throws Exception {
		while (true) {
			Object msg = in.current();
			if (msg == null) {
				this.writeFilter(false);
			} else {
				try {
					boolean done = false;

					for (int i = this.config().getWriteSpinCount(); i > 0; i--) {
						if (this.doWriteMessage(msg)) {
							done = true;
							break;
						}
					}

					if (done) {
						in.remove();
						continue;
					}

					this.writeFilter(true);
				} catch (IOException var5) {
					in.remove(var5);
					continue;
				}
			}

			return;
		}
	}

	private boolean doWriteMessage(Object msg) throws Exception {
		ByteBuf data;
		InetSocketAddress remoteAddress;
		if (msg instanceof AddressedEnvelope) {
			AddressedEnvelope<ByteBuf, InetSocketAddress> envelope = (AddressedEnvelope<ByteBuf, InetSocketAddress>)msg;
			data = envelope.content();
			remoteAddress = (InetSocketAddress)envelope.recipient();
		} else {
			data = (ByteBuf)msg;
			remoteAddress = null;
		}

		int dataLen = data.readableBytes();
		if (dataLen == 0) {
			return true;
		} else {
			long writtenBytes;
			if (data.hasMemoryAddress()) {
				long memoryAddress = data.memoryAddress();
				if (remoteAddress == null) {
					writtenBytes = (long)this.socket.writeAddress(memoryAddress, data.readerIndex(), data.writerIndex());
				} else {
					writtenBytes = (long)this.socket.sendToAddress(memoryAddress, data.readerIndex(), data.writerIndex(), remoteAddress.getAddress(), remoteAddress.getPort());
				}
			} else if (data.nioBufferCount() > 1) {
				IovArray array = ((KQueueEventLoop)this.eventLoop()).cleanArray();
				array.add(data);
				int cnt = array.count();

				assert cnt != 0;

				if (remoteAddress == null) {
					writtenBytes = this.socket.writevAddresses(array.memoryAddress(0), cnt);
				} else {
					writtenBytes = (long)this.socket.sendToAddresses(array.memoryAddress(0), cnt, remoteAddress.getAddress(), remoteAddress.getPort());
				}
			} else {
				ByteBuffer nioData = data.internalNioBuffer(data.readerIndex(), data.readableBytes());
				if (remoteAddress == null) {
					writtenBytes = (long)this.socket.write(nioData, nioData.position(), nioData.limit());
				} else {
					writtenBytes = (long)this.socket.sendTo(nioData, nioData.position(), nioData.limit(), remoteAddress.getAddress(), remoteAddress.getPort());
				}
			}

			return writtenBytes > 0L;
		}
	}

	@Override
	protected Object filterOutboundMessage(Object msg) {
		if (msg instanceof DatagramPacket) {
			DatagramPacket packet = (DatagramPacket)msg;
			ByteBuf content = packet.content();
			return UnixChannelUtil.isBufferCopyNeededForWrite(content) ? new DatagramPacket(this.newDirectBuffer(packet, content), packet.recipient()) : msg;
		} else if (msg instanceof ByteBuf) {
			ByteBuf buf = (ByteBuf)msg;
			return UnixChannelUtil.isBufferCopyNeededForWrite(buf) ? this.newDirectBuffer(buf) : buf;
		} else {
			if (msg instanceof AddressedEnvelope) {
				AddressedEnvelope<Object, SocketAddress> e = (AddressedEnvelope<Object, SocketAddress>)msg;
				if (e.content() instanceof ByteBuf && (e.recipient() == null || e.recipient() instanceof InetSocketAddress)) {
					ByteBuf content = (ByteBuf)e.content();
					return UnixChannelUtil.isBufferCopyNeededForWrite(content)
						? new DefaultAddressedEnvelope<>(this.newDirectBuffer(e, content), (InetSocketAddress)e.recipient())
						: e;
				}
			}

			throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
		}
	}

	public KQueueDatagramChannelConfig config() {
		return this.config;
	}

	@Override
	protected void doDisconnect() throws Exception {
		this.socket.disconnect();
		this.connected = this.active = false;
	}

	@Override
	protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
		if (super.doConnect(remoteAddress, localAddress)) {
			this.connected = true;
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void doClose() throws Exception {
		super.doClose();
		this.connected = false;
	}

	final class KQueueDatagramChannelUnsafe extends AbstractKQueueUnsafe {
		KQueueDatagramChannelUnsafe() {
			super(KQueueDatagramChannel.this);
		}

		@Override
		void readReady(KQueueRecvByteAllocatorHandle allocHandle) {
			assert KQueueDatagramChannel.this.eventLoop().inEventLoop();

			DatagramChannelConfig config = KQueueDatagramChannel.this.config();
			if (KQueueDatagramChannel.this.shouldBreakReadReady(config)) {
				this.clearReadFilter0();
			} else {
				ChannelPipeline pipeline = KQueueDatagramChannel.this.pipeline();
				ByteBufAllocator allocator = config.getAllocator();
				allocHandle.reset(config);
				this.readReadyBefore();
				Throwable exception = null;

				try {
					ByteBuf data = null;

					try {
						do {
							data = allocHandle.allocate(allocator);
							allocHandle.attemptedBytesRead(data.writableBytes());
							DatagramSocketAddress remoteAddress;
							if (data.hasMemoryAddress()) {
								remoteAddress = KQueueDatagramChannel.this.socket.recvFromAddress(data.memoryAddress(), data.writerIndex(), data.capacity());
							} else {
								ByteBuffer nioData = data.internalNioBuffer(data.writerIndex(), data.writableBytes());
								remoteAddress = KQueueDatagramChannel.this.socket.recvFrom(nioData, nioData.position(), nioData.limit());
							}

							if (remoteAddress == null) {
								allocHandle.lastBytesRead(-1);
								data.release();
								data = null;
								break;
							}

							allocHandle.incMessagesRead(1);
							allocHandle.lastBytesRead(remoteAddress.receivedAmount());
							data.writerIndex(data.writerIndex() + allocHandle.lastBytesRead());
							this.readPending = false;
							pipeline.fireChannelRead(new DatagramPacket(data, (InetSocketAddress)this.localAddress(), remoteAddress));
							data = null;
						} while (allocHandle.continueReading());
					} catch (Throwable var12) {
						if (data != null) {
							data.release();
						}

						exception = var12;
					}

					allocHandle.readComplete();
					pipeline.fireChannelReadComplete();
					if (exception != null) {
						pipeline.fireExceptionCaught(exception);
					}
				} finally {
					this.readReadyFinally(config);
				}
			}
		}
	}
}
