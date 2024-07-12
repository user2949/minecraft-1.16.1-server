package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.PreferHeapByteBufAllocator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.socket.DefaultSocketChannelConfig;
import io.netty.channel.socket.SocketChannel;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class DefaultOioSocketChannelConfig extends DefaultSocketChannelConfig implements OioSocketChannelConfig {
	@Deprecated
	public DefaultOioSocketChannelConfig(SocketChannel channel, Socket javaSocket) {
		super(channel, javaSocket);
		this.setAllocator(new PreferHeapByteBufAllocator(this.getAllocator()));
	}

	DefaultOioSocketChannelConfig(OioSocketChannel channel, Socket javaSocket) {
		super(channel, javaSocket);
		this.setAllocator(new PreferHeapByteBufAllocator(this.getAllocator()));
	}

	@Override
	public Map<ChannelOption<?>, Object> getOptions() {
		return this.getOptions(super.getOptions(), new ChannelOption[]{ChannelOption.SO_TIMEOUT});
	}

	@Override
	public <T> T getOption(ChannelOption<T> option) {
		return (T)(option == ChannelOption.SO_TIMEOUT ? this.getSoTimeout() : super.getOption(option));
	}

	@Override
	public <T> boolean setOption(ChannelOption<T> option, T value) {
		this.validate(option, value);
		if (option == ChannelOption.SO_TIMEOUT) {
			this.setSoTimeout((Integer)value);
			return true;
		} else {
			return super.setOption(option, value);
		}
	}

	@Override
	public OioSocketChannelConfig setSoTimeout(int timeout) {
		try {
			this.javaSocket.setSoTimeout(timeout);
			return this;
		} catch (IOException var3) {
			throw new ChannelException(var3);
		}
	}

	@Override
	public int getSoTimeout() {
		try {
			return this.javaSocket.getSoTimeout();
		} catch (IOException var2) {
			throw new ChannelException(var2);
		}
	}

	@Override
	public OioSocketChannelConfig setTcpNoDelay(boolean tcpNoDelay) {
		super.setTcpNoDelay(tcpNoDelay);
		return this;
	}

	@Override
	public OioSocketChannelConfig setSoLinger(int soLinger) {
		super.setSoLinger(soLinger);
		return this;
	}

	@Override
	public OioSocketChannelConfig setSendBufferSize(int sendBufferSize) {
		super.setSendBufferSize(sendBufferSize);
		return this;
	}

	@Override
	public OioSocketChannelConfig setReceiveBufferSize(int receiveBufferSize) {
		super.setReceiveBufferSize(receiveBufferSize);
		return this;
	}

	@Override
	public OioSocketChannelConfig setKeepAlive(boolean keepAlive) {
		super.setKeepAlive(keepAlive);
		return this;
	}

	@Override
	public OioSocketChannelConfig setTrafficClass(int trafficClass) {
		super.setTrafficClass(trafficClass);
		return this;
	}

	@Override
	public OioSocketChannelConfig setReuseAddress(boolean reuseAddress) {
		super.setReuseAddress(reuseAddress);
		return this;
	}

	@Override
	public OioSocketChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
		super.setPerformancePreferences(connectionTime, latency, bandwidth);
		return this;
	}

	@Override
	public OioSocketChannelConfig setAllowHalfClosure(boolean allowHalfClosure) {
		super.setAllowHalfClosure(allowHalfClosure);
		return this;
	}

	@Override
	public OioSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
		super.setConnectTimeoutMillis(connectTimeoutMillis);
		return this;
	}

	@Deprecated
	@Override
	public OioSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
		super.setMaxMessagesPerRead(maxMessagesPerRead);
		return this;
	}

	@Override
	public OioSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
		super.setWriteSpinCount(writeSpinCount);
		return this;
	}

	@Override
	public OioSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
		super.setAllocator(allocator);
		return this;
	}

	@Override
	public OioSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
		super.setRecvByteBufAllocator(allocator);
		return this;
	}

	@Override
	public OioSocketChannelConfig setAutoRead(boolean autoRead) {
		super.setAutoRead(autoRead);
		return this;
	}

	@Override
	protected void autoReadCleared() {
		if (this.channel instanceof OioSocketChannel) {
			((OioSocketChannel)this.channel).clearReadPending0();
		}
	}

	@Override
	public OioSocketChannelConfig setAutoClose(boolean autoClose) {
		super.setAutoClose(autoClose);
		return this;
	}

	@Override
	public OioSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
		super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
		return this;
	}

	@Override
	public OioSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
		super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
		return this;
	}

	@Override
	public OioSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
		super.setWriteBufferWaterMark(writeBufferWaterMark);
		return this;
	}

	@Override
	public OioSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
		super.setMessageSizeEstimator(estimator);
		return this;
	}
}
