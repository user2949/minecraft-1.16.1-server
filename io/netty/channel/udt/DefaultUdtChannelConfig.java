package io.netty.channel.udt;

import com.barchart.udt.OptionUDT;
import com.barchart.udt.SocketUDT;
import com.barchart.udt.nio.ChannelUDT;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import java.io.IOException;
import java.util.Map;

@Deprecated
public class DefaultUdtChannelConfig extends DefaultChannelConfig implements UdtChannelConfig {
	private static final int K = 1024;
	private static final int M = 1048576;
	private volatile int protocolReceiveBufferSize = 10485760;
	private volatile int protocolSendBufferSize = 10485760;
	private volatile int systemReceiveBufferSize = 1048576;
	private volatile int systemSendBufferSize = 1048576;
	private volatile int allocatorReceiveBufferSize = 131072;
	private volatile int allocatorSendBufferSize = 131072;
	private volatile int soLinger;
	private volatile boolean reuseAddress = true;

	public DefaultUdtChannelConfig(UdtChannel channel, ChannelUDT channelUDT, boolean apply) throws IOException {
		super(channel);
		if (apply) {
			this.apply(channelUDT);
		}
	}

	protected void apply(ChannelUDT channelUDT) throws IOException {
		SocketUDT socketUDT = channelUDT.socketUDT();
		socketUDT.setReuseAddress(this.isReuseAddress());
		socketUDT.setSendBufferSize(this.getSendBufferSize());
		if (this.getSoLinger() <= 0) {
			socketUDT.setSoLinger(false, 0);
		} else {
			socketUDT.setSoLinger(true, this.getSoLinger());
		}

		socketUDT.setOption(OptionUDT.Protocol_Receive_Buffer_Size, this.getProtocolReceiveBufferSize());
		socketUDT.setOption(OptionUDT.Protocol_Send_Buffer_Size, this.getProtocolSendBufferSize());
		socketUDT.setOption(OptionUDT.System_Receive_Buffer_Size, this.getSystemReceiveBufferSize());
		socketUDT.setOption(OptionUDT.System_Send_Buffer_Size, this.getSystemSendBufferSize());
	}

	@Override
	public int getProtocolReceiveBufferSize() {
		return this.protocolReceiveBufferSize;
	}

	@Override
	public <T> T getOption(ChannelOption<T> option) {
		if (option == UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE) {
			return (T)this.getProtocolReceiveBufferSize();
		} else if (option == UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE) {
			return (T)this.getProtocolSendBufferSize();
		} else if (option == UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE) {
			return (T)this.getSystemReceiveBufferSize();
		} else if (option == UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE) {
			return (T)this.getSystemSendBufferSize();
		} else if (option == ChannelOption.SO_RCVBUF) {
			return (T)this.getReceiveBufferSize();
		} else if (option == ChannelOption.SO_SNDBUF) {
			return (T)this.getSendBufferSize();
		} else if (option == ChannelOption.SO_REUSEADDR) {
			return (T)this.isReuseAddress();
		} else {
			return (T)(option == ChannelOption.SO_LINGER ? this.getSoLinger() : super.getOption(option));
		}
	}

	@Override
	public Map<ChannelOption<?>, Object> getOptions() {
		return this.getOptions(
			super.getOptions(),
			new ChannelOption[]{
				UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE,
				UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE,
				UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE,
				UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE,
				ChannelOption.SO_RCVBUF,
				ChannelOption.SO_SNDBUF,
				ChannelOption.SO_REUSEADDR,
				ChannelOption.SO_LINGER
			}
		);
	}

	@Override
	public int getReceiveBufferSize() {
		return this.allocatorReceiveBufferSize;
	}

	@Override
	public int getSendBufferSize() {
		return this.allocatorSendBufferSize;
	}

	@Override
	public int getSoLinger() {
		return this.soLinger;
	}

	@Override
	public boolean isReuseAddress() {
		return this.reuseAddress;
	}

	@Override
	public UdtChannelConfig setProtocolReceiveBufferSize(int protocolReceiveBufferSize) {
		this.protocolReceiveBufferSize = protocolReceiveBufferSize;
		return this;
	}

	@Override
	public <T> boolean setOption(ChannelOption<T> option, T value) {
		this.validate(option, value);
		if (option == UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE) {
			this.setProtocolReceiveBufferSize((Integer)value);
		} else if (option == UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE) {
			this.setProtocolSendBufferSize((Integer)value);
		} else if (option == UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE) {
			this.setSystemReceiveBufferSize((Integer)value);
		} else if (option == UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE) {
			this.setSystemSendBufferSize((Integer)value);
		} else if (option == ChannelOption.SO_RCVBUF) {
			this.setReceiveBufferSize((Integer)value);
		} else if (option == ChannelOption.SO_SNDBUF) {
			this.setSendBufferSize((Integer)value);
		} else if (option == ChannelOption.SO_REUSEADDR) {
			this.setReuseAddress((Boolean)value);
		} else {
			if (option != ChannelOption.SO_LINGER) {
				return super.setOption(option, value);
			}

			this.setSoLinger((Integer)value);
		}

		return true;
	}

	@Override
	public UdtChannelConfig setReceiveBufferSize(int receiveBufferSize) {
		this.allocatorReceiveBufferSize = receiveBufferSize;
		return this;
	}

	@Override
	public UdtChannelConfig setReuseAddress(boolean reuseAddress) {
		this.reuseAddress = reuseAddress;
		return this;
	}

	@Override
	public UdtChannelConfig setSendBufferSize(int sendBufferSize) {
		this.allocatorSendBufferSize = sendBufferSize;
		return this;
	}

	@Override
	public UdtChannelConfig setSoLinger(int soLinger) {
		this.soLinger = soLinger;
		return this;
	}

	@Override
	public int getSystemReceiveBufferSize() {
		return this.systemReceiveBufferSize;
	}

	@Override
	public UdtChannelConfig setSystemSendBufferSize(int systemReceiveBufferSize) {
		this.systemReceiveBufferSize = systemReceiveBufferSize;
		return this;
	}

	@Override
	public int getProtocolSendBufferSize() {
		return this.protocolSendBufferSize;
	}

	@Override
	public UdtChannelConfig setProtocolSendBufferSize(int protocolSendBufferSize) {
		this.protocolSendBufferSize = protocolSendBufferSize;
		return this;
	}

	@Override
	public UdtChannelConfig setSystemReceiveBufferSize(int systemSendBufferSize) {
		this.systemSendBufferSize = systemSendBufferSize;
		return this;
	}

	@Override
	public int getSystemSendBufferSize() {
		return this.systemSendBufferSize;
	}

	@Override
	public UdtChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
		super.setConnectTimeoutMillis(connectTimeoutMillis);
		return this;
	}

	@Deprecated
	@Override
	public UdtChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
		super.setMaxMessagesPerRead(maxMessagesPerRead);
		return this;
	}

	@Override
	public UdtChannelConfig setWriteSpinCount(int writeSpinCount) {
		super.setWriteSpinCount(writeSpinCount);
		return this;
	}

	@Override
	public UdtChannelConfig setAllocator(ByteBufAllocator allocator) {
		super.setAllocator(allocator);
		return this;
	}

	@Override
	public UdtChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
		super.setRecvByteBufAllocator(allocator);
		return this;
	}

	@Override
	public UdtChannelConfig setAutoRead(boolean autoRead) {
		super.setAutoRead(autoRead);
		return this;
	}

	@Override
	public UdtChannelConfig setAutoClose(boolean autoClose) {
		super.setAutoClose(autoClose);
		return this;
	}

	@Override
	public UdtChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
		super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
		return this;
	}

	@Override
	public UdtChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
		super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
		return this;
	}

	@Override
	public UdtChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
		super.setWriteBufferWaterMark(writeBufferWaterMark);
		return this;
	}

	@Override
	public UdtChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
		super.setMessageSizeEstimator(estimator);
		return this;
	}
}
