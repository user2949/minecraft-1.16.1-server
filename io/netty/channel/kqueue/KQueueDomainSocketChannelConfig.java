package io.netty.channel.kqueue;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.unix.DomainSocketChannelConfig;
import io.netty.channel.unix.DomainSocketReadMode;
import io.netty.channel.unix.UnixChannelOption;
import java.util.Map;

public final class KQueueDomainSocketChannelConfig extends KQueueChannelConfig implements DomainSocketChannelConfig {
	private volatile DomainSocketReadMode mode = DomainSocketReadMode.BYTES;

	KQueueDomainSocketChannelConfig(AbstractKQueueChannel channel) {
		super(channel);
	}

	@Override
	public Map<ChannelOption<?>, Object> getOptions() {
		return this.getOptions(super.getOptions(), new ChannelOption[]{UnixChannelOption.DOMAIN_SOCKET_READ_MODE});
	}

	@Override
	public <T> T getOption(ChannelOption<T> option) {
		return (T)(option == UnixChannelOption.DOMAIN_SOCKET_READ_MODE ? this.getReadMode() : super.getOption(option));
	}

	@Override
	public <T> boolean setOption(ChannelOption<T> option, T value) {
		this.validate(option, value);
		if (option == UnixChannelOption.DOMAIN_SOCKET_READ_MODE) {
			this.setReadMode((DomainSocketReadMode)value);
			return true;
		} else {
			return super.setOption(option, value);
		}
	}

	public KQueueDomainSocketChannelConfig setRcvAllocTransportProvidesGuess(boolean transportProvidesGuess) {
		super.setRcvAllocTransportProvidesGuess(transportProvidesGuess);
		return this;
	}

	@Deprecated
	public KQueueDomainSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
		super.setMaxMessagesPerRead(maxMessagesPerRead);
		return this;
	}

	public KQueueDomainSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
		super.setConnectTimeoutMillis(connectTimeoutMillis);
		return this;
	}

	public KQueueDomainSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
		super.setWriteSpinCount(writeSpinCount);
		return this;
	}

	public KQueueDomainSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
		super.setRecvByteBufAllocator(allocator);
		return this;
	}

	public KQueueDomainSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
		super.setAllocator(allocator);
		return this;
	}

	public KQueueDomainSocketChannelConfig setAutoClose(boolean autoClose) {
		super.setAutoClose(autoClose);
		return this;
	}

	public KQueueDomainSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
		super.setMessageSizeEstimator(estimator);
		return this;
	}

	@Deprecated
	public KQueueDomainSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
		super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
		return this;
	}

	@Deprecated
	public KQueueDomainSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
		super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
		return this;
	}

	public KQueueDomainSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
		super.setWriteBufferWaterMark(writeBufferWaterMark);
		return this;
	}

	public KQueueDomainSocketChannelConfig setAutoRead(boolean autoRead) {
		super.setAutoRead(autoRead);
		return this;
	}

	public KQueueDomainSocketChannelConfig setReadMode(DomainSocketReadMode mode) {
		if (mode == null) {
			throw new NullPointerException("mode");
		} else {
			this.mode = mode;
			return this;
		}
	}

	@Override
	public DomainSocketReadMode getReadMode() {
		return this.mode;
	}
}
