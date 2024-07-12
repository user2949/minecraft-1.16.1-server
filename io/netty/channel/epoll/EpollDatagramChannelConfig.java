package io.netty.channel.epoll;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.socket.DatagramChannelConfig;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Map;

public final class EpollDatagramChannelConfig extends EpollChannelConfig implements DatagramChannelConfig {
	private static final RecvByteBufAllocator DEFAULT_RCVBUF_ALLOCATOR = new FixedRecvByteBufAllocator(2048);
	private final EpollDatagramChannel datagramChannel;
	private boolean activeOnOpen;

	EpollDatagramChannelConfig(EpollDatagramChannel channel) {
		super(channel);
		this.datagramChannel = channel;
		this.setRecvByteBufAllocator(DEFAULT_RCVBUF_ALLOCATOR);
	}

	@Override
	public Map<ChannelOption<?>, Object> getOptions() {
		return this.getOptions(
			super.getOptions(),
			new ChannelOption[]{
				ChannelOption.SO_BROADCAST,
				ChannelOption.SO_RCVBUF,
				ChannelOption.SO_SNDBUF,
				ChannelOption.SO_REUSEADDR,
				ChannelOption.IP_MULTICAST_LOOP_DISABLED,
				ChannelOption.IP_MULTICAST_ADDR,
				ChannelOption.IP_MULTICAST_IF,
				ChannelOption.IP_MULTICAST_TTL,
				ChannelOption.IP_TOS,
				ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION,
				EpollChannelOption.SO_REUSEPORT,
				EpollChannelOption.IP_TRANSPARENT,
				EpollChannelOption.IP_RECVORIGDSTADDR
			}
		);
	}

	@Override
	public <T> T getOption(ChannelOption<T> option) {
		if (option == ChannelOption.SO_BROADCAST) {
			return (T)this.isBroadcast();
		} else if (option == ChannelOption.SO_RCVBUF) {
			return (T)this.getReceiveBufferSize();
		} else if (option == ChannelOption.SO_SNDBUF) {
			return (T)this.getSendBufferSize();
		} else if (option == ChannelOption.SO_REUSEADDR) {
			return (T)this.isReuseAddress();
		} else if (option == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
			return (T)this.isLoopbackModeDisabled();
		} else if (option == ChannelOption.IP_MULTICAST_ADDR) {
			return (T)this.getInterface();
		} else if (option == ChannelOption.IP_MULTICAST_IF) {
			return (T)this.getNetworkInterface();
		} else if (option == ChannelOption.IP_MULTICAST_TTL) {
			return (T)this.getTimeToLive();
		} else if (option == ChannelOption.IP_TOS) {
			return (T)this.getTrafficClass();
		} else if (option == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
			return (T)this.activeOnOpen;
		} else if (option == EpollChannelOption.SO_REUSEPORT) {
			return (T)this.isReusePort();
		} else if (option == EpollChannelOption.IP_TRANSPARENT) {
			return (T)this.isIpTransparent();
		} else {
			return (T)(option == EpollChannelOption.IP_RECVORIGDSTADDR ? this.isIpRecvOrigDestAddr() : super.getOption(option));
		}
	}

	@Override
	public <T> boolean setOption(ChannelOption<T> option, T value) {
		this.validate(option, value);
		if (option == ChannelOption.SO_BROADCAST) {
			this.setBroadcast((Boolean)value);
		} else if (option == ChannelOption.SO_RCVBUF) {
			this.setReceiveBufferSize((Integer)value);
		} else if (option == ChannelOption.SO_SNDBUF) {
			this.setSendBufferSize((Integer)value);
		} else if (option == ChannelOption.SO_REUSEADDR) {
			this.setReuseAddress((Boolean)value);
		} else if (option == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
			this.setLoopbackModeDisabled((Boolean)value);
		} else if (option == ChannelOption.IP_MULTICAST_ADDR) {
			this.setInterface((InetAddress)value);
		} else if (option == ChannelOption.IP_MULTICAST_IF) {
			this.setNetworkInterface((NetworkInterface)value);
		} else if (option == ChannelOption.IP_MULTICAST_TTL) {
			this.setTimeToLive((Integer)value);
		} else if (option == ChannelOption.IP_TOS) {
			this.setTrafficClass((Integer)value);
		} else if (option == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
			this.setActiveOnOpen((Boolean)value);
		} else if (option == EpollChannelOption.SO_REUSEPORT) {
			this.setReusePort((Boolean)value);
		} else if (option == EpollChannelOption.IP_TRANSPARENT) {
			this.setIpTransparent((Boolean)value);
		} else {
			if (option != EpollChannelOption.IP_RECVORIGDSTADDR) {
				return super.setOption(option, value);
			}

			this.setIpRecvOrigDestAddr((Boolean)value);
		}

		return true;
	}

	private void setActiveOnOpen(boolean activeOnOpen) {
		if (this.channel.isRegistered()) {
			throw new IllegalStateException("Can only changed before channel was registered");
		} else {
			this.activeOnOpen = activeOnOpen;
		}
	}

	boolean getActiveOnOpen() {
		return this.activeOnOpen;
	}

	public EpollDatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
		super.setMessageSizeEstimator(estimator);
		return this;
	}

	@Deprecated
	public EpollDatagramChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
		super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
		return this;
	}

	@Deprecated
	public EpollDatagramChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
		super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
		return this;
	}

	public EpollDatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
		super.setWriteBufferWaterMark(writeBufferWaterMark);
		return this;
	}

	public EpollDatagramChannelConfig setAutoClose(boolean autoClose) {
		super.setAutoClose(autoClose);
		return this;
	}

	public EpollDatagramChannelConfig setAutoRead(boolean autoRead) {
		super.setAutoRead(autoRead);
		return this;
	}

	public EpollDatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
		super.setRecvByteBufAllocator(allocator);
		return this;
	}

	public EpollDatagramChannelConfig setWriteSpinCount(int writeSpinCount) {
		super.setWriteSpinCount(writeSpinCount);
		return this;
	}

	public EpollDatagramChannelConfig setAllocator(ByteBufAllocator allocator) {
		super.setAllocator(allocator);
		return this;
	}

	public EpollDatagramChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
		super.setConnectTimeoutMillis(connectTimeoutMillis);
		return this;
	}

	@Deprecated
	public EpollDatagramChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
		super.setMaxMessagesPerRead(maxMessagesPerRead);
		return this;
	}

	@Override
	public int getSendBufferSize() {
		try {
			return this.datagramChannel.socket.getSendBufferSize();
		} catch (IOException var2) {
			throw new ChannelException(var2);
		}
	}

	public EpollDatagramChannelConfig setSendBufferSize(int sendBufferSize) {
		try {
			this.datagramChannel.socket.setSendBufferSize(sendBufferSize);
			return this;
		} catch (IOException var3) {
			throw new ChannelException(var3);
		}
	}

	@Override
	public int getReceiveBufferSize() {
		try {
			return this.datagramChannel.socket.getReceiveBufferSize();
		} catch (IOException var2) {
			throw new ChannelException(var2);
		}
	}

	public EpollDatagramChannelConfig setReceiveBufferSize(int receiveBufferSize) {
		try {
			this.datagramChannel.socket.setReceiveBufferSize(receiveBufferSize);
			return this;
		} catch (IOException var3) {
			throw new ChannelException(var3);
		}
	}

	@Override
	public int getTrafficClass() {
		try {
			return this.datagramChannel.socket.getTrafficClass();
		} catch (IOException var2) {
			throw new ChannelException(var2);
		}
	}

	public EpollDatagramChannelConfig setTrafficClass(int trafficClass) {
		try {
			this.datagramChannel.socket.setTrafficClass(trafficClass);
			return this;
		} catch (IOException var3) {
			throw new ChannelException(var3);
		}
	}

	@Override
	public boolean isReuseAddress() {
		try {
			return this.datagramChannel.socket.isReuseAddress();
		} catch (IOException var2) {
			throw new ChannelException(var2);
		}
	}

	public EpollDatagramChannelConfig setReuseAddress(boolean reuseAddress) {
		try {
			this.datagramChannel.socket.setReuseAddress(reuseAddress);
			return this;
		} catch (IOException var3) {
			throw new ChannelException(var3);
		}
	}

	@Override
	public boolean isBroadcast() {
		try {
			return this.datagramChannel.socket.isBroadcast();
		} catch (IOException var2) {
			throw new ChannelException(var2);
		}
	}

	public EpollDatagramChannelConfig setBroadcast(boolean broadcast) {
		try {
			this.datagramChannel.socket.setBroadcast(broadcast);
			return this;
		} catch (IOException var3) {
			throw new ChannelException(var3);
		}
	}

	@Override
	public boolean isLoopbackModeDisabled() {
		return false;
	}

	@Override
	public DatagramChannelConfig setLoopbackModeDisabled(boolean loopbackModeDisabled) {
		throw new UnsupportedOperationException("Multicast not supported");
	}

	@Override
	public int getTimeToLive() {
		return -1;
	}

	public EpollDatagramChannelConfig setTimeToLive(int ttl) {
		throw new UnsupportedOperationException("Multicast not supported");
	}

	@Override
	public InetAddress getInterface() {
		return null;
	}

	public EpollDatagramChannelConfig setInterface(InetAddress interfaceAddress) {
		throw new UnsupportedOperationException("Multicast not supported");
	}

	@Override
	public NetworkInterface getNetworkInterface() {
		return null;
	}

	public EpollDatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
		throw new UnsupportedOperationException("Multicast not supported");
	}

	public EpollDatagramChannelConfig setEpollMode(EpollMode mode) {
		super.setEpollMode(mode);
		return this;
	}

	public boolean isReusePort() {
		try {
			return this.datagramChannel.socket.isReusePort();
		} catch (IOException var2) {
			throw new ChannelException(var2);
		}
	}

	public EpollDatagramChannelConfig setReusePort(boolean reusePort) {
		try {
			this.datagramChannel.socket.setReusePort(reusePort);
			return this;
		} catch (IOException var3) {
			throw new ChannelException(var3);
		}
	}

	public boolean isIpTransparent() {
		try {
			return this.datagramChannel.socket.isIpTransparent();
		} catch (IOException var2) {
			throw new ChannelException(var2);
		}
	}

	public EpollDatagramChannelConfig setIpTransparent(boolean ipTransparent) {
		try {
			this.datagramChannel.socket.setIpTransparent(ipTransparent);
			return this;
		} catch (IOException var3) {
			throw new ChannelException(var3);
		}
	}

	public boolean isIpRecvOrigDestAddr() {
		try {
			return this.datagramChannel.socket.isIpRecvOrigDestAddr();
		} catch (IOException var2) {
			throw new ChannelException(var2);
		}
	}

	public EpollDatagramChannelConfig setIpRecvOrigDestAddr(boolean ipTransparent) {
		try {
			this.datagramChannel.socket.setIpRecvOrigDestAddr(ipTransparent);
			return this;
		} catch (IOException var3) {
			throw new ChannelException(var3);
		}
	}
}
