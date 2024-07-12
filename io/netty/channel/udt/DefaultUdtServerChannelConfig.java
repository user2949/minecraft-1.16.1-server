package io.netty.channel.udt;

import com.barchart.udt.nio.ChannelUDT;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import java.io.IOException;
import java.util.Map;

@Deprecated
public class DefaultUdtServerChannelConfig extends DefaultUdtChannelConfig implements UdtServerChannelConfig {
	private volatile int backlog = 64;

	public DefaultUdtServerChannelConfig(UdtChannel channel, ChannelUDT channelUDT, boolean apply) throws IOException {
		super(channel, channelUDT, apply);
		if (apply) {
			this.apply(channelUDT);
		}
	}

	@Override
	protected void apply(ChannelUDT channelUDT) throws IOException {
	}

	@Override
	public int getBacklog() {
		return this.backlog;
	}

	@Override
	public <T> T getOption(ChannelOption<T> option) {
		return (T)(option == ChannelOption.SO_BACKLOG ? this.getBacklog() : super.getOption(option));
	}

	@Override
	public Map<ChannelOption<?>, Object> getOptions() {
		return this.getOptions(super.getOptions(), new ChannelOption[]{ChannelOption.SO_BACKLOG});
	}

	@Override
	public UdtServerChannelConfig setBacklog(int backlog) {
		this.backlog = backlog;
		return this;
	}

	@Override
	public <T> boolean setOption(ChannelOption<T> option, T value) {
		this.validate(option, value);
		if (option == ChannelOption.SO_BACKLOG) {
			this.setBacklog((Integer)value);
			return true;
		} else {
			return super.setOption(option, value);
		}
	}

	@Override
	public UdtServerChannelConfig setProtocolReceiveBufferSize(int protocolReceiveBufferSize) {
		super.setProtocolReceiveBufferSize(protocolReceiveBufferSize);
		return this;
	}

	@Override
	public UdtServerChannelConfig setProtocolSendBufferSize(int protocolSendBufferSize) {
		super.setProtocolSendBufferSize(protocolSendBufferSize);
		return this;
	}

	@Override
	public UdtServerChannelConfig setReceiveBufferSize(int receiveBufferSize) {
		super.setReceiveBufferSize(receiveBufferSize);
		return this;
	}

	@Override
	public UdtServerChannelConfig setReuseAddress(boolean reuseAddress) {
		super.setReuseAddress(reuseAddress);
		return this;
	}

	@Override
	public UdtServerChannelConfig setSendBufferSize(int sendBufferSize) {
		super.setSendBufferSize(sendBufferSize);
		return this;
	}

	@Override
	public UdtServerChannelConfig setSoLinger(int soLinger) {
		super.setSoLinger(soLinger);
		return this;
	}

	@Override
	public UdtServerChannelConfig setSystemReceiveBufferSize(int systemSendBufferSize) {
		super.setSystemReceiveBufferSize(systemSendBufferSize);
		return this;
	}

	@Override
	public UdtServerChannelConfig setSystemSendBufferSize(int systemReceiveBufferSize) {
		super.setSystemSendBufferSize(systemReceiveBufferSize);
		return this;
	}

	@Override
	public UdtServerChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
		super.setConnectTimeoutMillis(connectTimeoutMillis);
		return this;
	}

	@Deprecated
	@Override
	public UdtServerChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
		super.setMaxMessagesPerRead(maxMessagesPerRead);
		return this;
	}

	@Override
	public UdtServerChannelConfig setWriteSpinCount(int writeSpinCount) {
		super.setWriteSpinCount(writeSpinCount);
		return this;
	}

	@Override
	public UdtServerChannelConfig setAllocator(ByteBufAllocator allocator) {
		super.setAllocator(allocator);
		return this;
	}

	@Override
	public UdtServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
		super.setRecvByteBufAllocator(allocator);
		return this;
	}

	@Override
	public UdtServerChannelConfig setAutoRead(boolean autoRead) {
		super.setAutoRead(autoRead);
		return this;
	}

	@Override
	public UdtServerChannelConfig setAutoClose(boolean autoClose) {
		super.setAutoClose(autoClose);
		return this;
	}

	@Override
	public UdtServerChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
		super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
		return this;
	}

	@Override
	public UdtServerChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
		super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
		return this;
	}

	@Override
	public UdtServerChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
		super.setWriteBufferWaterMark(writeBufferWaterMark);
		return this;
	}

	@Override
	public UdtServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
		super.setMessageSizeEstimator(estimator);
		return this;
	}
}
