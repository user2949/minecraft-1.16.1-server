package io.netty.channel.kqueue;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.socket.ServerSocketChannelConfig;
import io.netty.util.NetUtil;
import java.io.IOException;
import java.util.Map;

public class KQueueServerChannelConfig extends KQueueChannelConfig implements ServerSocketChannelConfig {
	protected final AbstractKQueueChannel channel;
	private volatile int backlog = NetUtil.SOMAXCONN;

	KQueueServerChannelConfig(AbstractKQueueChannel channel) {
		super(channel);
		this.channel = channel;
	}

	@Override
	public Map<ChannelOption<?>, Object> getOptions() {
		return this.getOptions(super.getOptions(), new ChannelOption[]{ChannelOption.SO_RCVBUF, ChannelOption.SO_REUSEADDR, ChannelOption.SO_BACKLOG});
	}

	@Override
	public <T> T getOption(ChannelOption<T> option) {
		if (option == ChannelOption.SO_RCVBUF) {
			return (T)this.getReceiveBufferSize();
		} else if (option == ChannelOption.SO_REUSEADDR) {
			return (T)this.isReuseAddress();
		} else {
			return (T)(option == ChannelOption.SO_BACKLOG ? this.getBacklog() : super.getOption(option));
		}
	}

	@Override
	public <T> boolean setOption(ChannelOption<T> option, T value) {
		this.validate(option, value);
		if (option == ChannelOption.SO_RCVBUF) {
			this.setReceiveBufferSize((Integer)value);
		} else if (option == ChannelOption.SO_REUSEADDR) {
			this.setReuseAddress((Boolean)value);
		} else {
			if (option != ChannelOption.SO_BACKLOG) {
				return super.setOption(option, value);
			}

			this.setBacklog((Integer)value);
		}

		return true;
	}

	@Override
	public boolean isReuseAddress() {
		try {
			return this.channel.socket.isReuseAddress();
		} catch (IOException var2) {
			throw new ChannelException(var2);
		}
	}

	public KQueueServerChannelConfig setReuseAddress(boolean reuseAddress) {
		try {
			this.channel.socket.setReuseAddress(reuseAddress);
			return this;
		} catch (IOException var3) {
			throw new ChannelException(var3);
		}
	}

	@Override
	public int getReceiveBufferSize() {
		try {
			return this.channel.socket.getReceiveBufferSize();
		} catch (IOException var2) {
			throw new ChannelException(var2);
		}
	}

	public KQueueServerChannelConfig setReceiveBufferSize(int receiveBufferSize) {
		try {
			this.channel.socket.setReceiveBufferSize(receiveBufferSize);
			return this;
		} catch (IOException var3) {
			throw new ChannelException(var3);
		}
	}

	@Override
	public int getBacklog() {
		return this.backlog;
	}

	public KQueueServerChannelConfig setBacklog(int backlog) {
		if (backlog < 0) {
			throw new IllegalArgumentException("backlog: " + backlog);
		} else {
			this.backlog = backlog;
			return this;
		}
	}

	public KQueueServerChannelConfig setRcvAllocTransportProvidesGuess(boolean transportProvidesGuess) {
		super.setRcvAllocTransportProvidesGuess(transportProvidesGuess);
		return this;
	}

	public KQueueServerChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
		return this;
	}

	public KQueueServerChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
		super.setConnectTimeoutMillis(connectTimeoutMillis);
		return this;
	}

	@Deprecated
	public KQueueServerChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
		super.setMaxMessagesPerRead(maxMessagesPerRead);
		return this;
	}

	public KQueueServerChannelConfig setWriteSpinCount(int writeSpinCount) {
		super.setWriteSpinCount(writeSpinCount);
		return this;
	}

	public KQueueServerChannelConfig setAllocator(ByteBufAllocator allocator) {
		super.setAllocator(allocator);
		return this;
	}

	public KQueueServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
		super.setRecvByteBufAllocator(allocator);
		return this;
	}

	public KQueueServerChannelConfig setAutoRead(boolean autoRead) {
		super.setAutoRead(autoRead);
		return this;
	}

	@Deprecated
	public KQueueServerChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
		super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
		return this;
	}

	@Deprecated
	public KQueueServerChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
		super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
		return this;
	}

	public KQueueServerChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
		super.setWriteBufferWaterMark(writeBufferWaterMark);
		return this;
	}

	public KQueueServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
		super.setMessageSizeEstimator(estimator);
		return this;
	}
}
