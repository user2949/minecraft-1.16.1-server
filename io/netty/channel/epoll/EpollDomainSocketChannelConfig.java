package io.netty.channel.epoll;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.unix.DomainSocketChannelConfig;
import io.netty.channel.unix.DomainSocketReadMode;
import java.util.Map;

public final class EpollDomainSocketChannelConfig extends EpollChannelConfig implements DomainSocketChannelConfig {
	private volatile DomainSocketReadMode mode = DomainSocketReadMode.BYTES;

	EpollDomainSocketChannelConfig(AbstractEpollChannel channel) {
		super(channel);
	}

	@Override
	public Map<ChannelOption<?>, Object> getOptions() {
		return this.getOptions(super.getOptions(), new ChannelOption[]{EpollChannelOption.DOMAIN_SOCKET_READ_MODE});
	}

	@Override
	public <T> T getOption(ChannelOption<T> option) {
		return (T)(option == EpollChannelOption.DOMAIN_SOCKET_READ_MODE ? this.getReadMode() : super.getOption(option));
	}

	@Override
	public <T> boolean setOption(ChannelOption<T> option, T value) {
		this.validate(option, value);
		if (option == EpollChannelOption.DOMAIN_SOCKET_READ_MODE) {
			this.setReadMode((DomainSocketReadMode)value);
			return true;
		} else {
			return super.setOption(option, value);
		}
	}

	@Deprecated
	public EpollDomainSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
		super.setMaxMessagesPerRead(maxMessagesPerRead);
		return this;
	}

	public EpollDomainSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
		super.setConnectTimeoutMillis(connectTimeoutMillis);
		return this;
	}

	public EpollDomainSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
		super.setWriteSpinCount(writeSpinCount);
		return this;
	}

	public EpollDomainSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
		super.setRecvByteBufAllocator(allocator);
		return this;
	}

	public EpollDomainSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
		super.setAllocator(allocator);
		return this;
	}

	public EpollDomainSocketChannelConfig setAutoClose(boolean autoClose) {
		super.setAutoClose(autoClose);
		return this;
	}

	public EpollDomainSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
		super.setMessageSizeEstimator(estimator);
		return this;
	}

	@Deprecated
	public EpollDomainSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
		super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
		return this;
	}

	@Deprecated
	public EpollDomainSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
		super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
		return this;
	}

	public EpollDomainSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
		super.setWriteBufferWaterMark(writeBufferWaterMark);
		return this;
	}

	public EpollDomainSocketChannelConfig setAutoRead(boolean autoRead) {
		super.setAutoRead(autoRead);
		return this;
	}

	public EpollDomainSocketChannelConfig setEpollMode(EpollMode mode) {
		super.setEpollMode(mode);
		return this;
	}

	public EpollDomainSocketChannelConfig setReadMode(DomainSocketReadMode mode) {
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
