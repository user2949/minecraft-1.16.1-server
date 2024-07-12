package io.netty.channel.epoll;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.RecvByteBufAllocator.ExtendedHandle;
import io.netty.channel.unix.Limits;
import java.io.IOException;
import java.util.Map;

public class EpollChannelConfig extends DefaultChannelConfig {
	final AbstractEpollChannel channel;
	private volatile long maxBytesPerGatheringWrite = Limits.SSIZE_MAX;

	EpollChannelConfig(AbstractEpollChannel channel) {
		super(channel);
		this.channel = channel;
	}

	@Override
	public Map<ChannelOption<?>, Object> getOptions() {
		return this.getOptions(super.getOptions(), new ChannelOption[]{EpollChannelOption.EPOLL_MODE});
	}

	@Override
	public <T> T getOption(ChannelOption<T> option) {
		return (T)(option == EpollChannelOption.EPOLL_MODE ? this.getEpollMode() : super.getOption(option));
	}

	@Override
	public <T> boolean setOption(ChannelOption<T> option, T value) {
		this.validate(option, value);
		if (option == EpollChannelOption.EPOLL_MODE) {
			this.setEpollMode((EpollMode)value);
			return true;
		} else {
			return super.setOption(option, value);
		}
	}

	public EpollChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
		super.setConnectTimeoutMillis(connectTimeoutMillis);
		return this;
	}

	@Deprecated
	public EpollChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
		super.setMaxMessagesPerRead(maxMessagesPerRead);
		return this;
	}

	public EpollChannelConfig setWriteSpinCount(int writeSpinCount) {
		super.setWriteSpinCount(writeSpinCount);
		return this;
	}

	public EpollChannelConfig setAllocator(ByteBufAllocator allocator) {
		super.setAllocator(allocator);
		return this;
	}

	public EpollChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
		if (!(allocator.newHandle() instanceof ExtendedHandle)) {
			throw new IllegalArgumentException("allocator.newHandle() must return an object of type: " + ExtendedHandle.class);
		} else {
			super.setRecvByteBufAllocator(allocator);
			return this;
		}
	}

	public EpollChannelConfig setAutoRead(boolean autoRead) {
		super.setAutoRead(autoRead);
		return this;
	}

	@Deprecated
	public EpollChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
		super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
		return this;
	}

	@Deprecated
	public EpollChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
		super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
		return this;
	}

	public EpollChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
		super.setWriteBufferWaterMark(writeBufferWaterMark);
		return this;
	}

	public EpollChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
		super.setMessageSizeEstimator(estimator);
		return this;
	}

	public EpollMode getEpollMode() {
		return this.channel.isFlagSet(Native.EPOLLET) ? EpollMode.EDGE_TRIGGERED : EpollMode.LEVEL_TRIGGERED;
	}

	public EpollChannelConfig setEpollMode(EpollMode mode) {
		if (mode == null) {
			throw new NullPointerException("mode");
		} else {
			try {
				switch (mode) {
					case EDGE_TRIGGERED:
						this.checkChannelNotRegistered();
						this.channel.setFlag(Native.EPOLLET);
						break;
					case LEVEL_TRIGGERED:
						this.checkChannelNotRegistered();
						this.channel.clearFlag(Native.EPOLLET);
						break;
					default:
						throw new Error();
				}

				return this;
			} catch (IOException var3) {
				throw new ChannelException(var3);
			}
		}
	}

	private void checkChannelNotRegistered() {
		if (this.channel.isRegistered()) {
			throw new IllegalStateException("EpollMode can only be changed before channel is registered");
		}
	}

	@Override
	protected final void autoReadCleared() {
		this.channel.clearEpollIn();
	}

	final void setMaxBytesPerGatheringWrite(long maxBytesPerGatheringWrite) {
		this.maxBytesPerGatheringWrite = maxBytesPerGatheringWrite;
	}

	final long getMaxBytesPerGatheringWrite() {
		return this.maxBytesPerGatheringWrite;
	}
}
