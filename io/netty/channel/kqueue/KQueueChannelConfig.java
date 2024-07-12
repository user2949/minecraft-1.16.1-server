package io.netty.channel.kqueue;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.RecvByteBufAllocator.ExtendedHandle;
import io.netty.channel.unix.Limits;
import java.util.Map;

public class KQueueChannelConfig extends DefaultChannelConfig {
	final AbstractKQueueChannel channel;
	private volatile boolean transportProvidesGuess;
	private volatile long maxBytesPerGatheringWrite = Limits.SSIZE_MAX;

	KQueueChannelConfig(AbstractKQueueChannel channel) {
		super(channel);
		this.channel = channel;
	}

	@Override
	public Map<ChannelOption<?>, Object> getOptions() {
		return this.getOptions(super.getOptions(), new ChannelOption[]{KQueueChannelOption.RCV_ALLOC_TRANSPORT_PROVIDES_GUESS});
	}

	@Override
	public <T> T getOption(ChannelOption<T> option) {
		return (T)(option == KQueueChannelOption.RCV_ALLOC_TRANSPORT_PROVIDES_GUESS ? this.getRcvAllocTransportProvidesGuess() : super.getOption(option));
	}

	@Override
	public <T> boolean setOption(ChannelOption<T> option, T value) {
		this.validate(option, value);
		if (option == KQueueChannelOption.RCV_ALLOC_TRANSPORT_PROVIDES_GUESS) {
			this.setRcvAllocTransportProvidesGuess((Boolean)value);
			return true;
		} else {
			return super.setOption(option, value);
		}
	}

	public KQueueChannelConfig setRcvAllocTransportProvidesGuess(boolean transportProvidesGuess) {
		this.transportProvidesGuess = transportProvidesGuess;
		return this;
	}

	public boolean getRcvAllocTransportProvidesGuess() {
		return this.transportProvidesGuess;
	}

	public KQueueChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
		super.setConnectTimeoutMillis(connectTimeoutMillis);
		return this;
	}

	@Deprecated
	public KQueueChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
		super.setMaxMessagesPerRead(maxMessagesPerRead);
		return this;
	}

	public KQueueChannelConfig setWriteSpinCount(int writeSpinCount) {
		super.setWriteSpinCount(writeSpinCount);
		return this;
	}

	public KQueueChannelConfig setAllocator(ByteBufAllocator allocator) {
		super.setAllocator(allocator);
		return this;
	}

	public KQueueChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
		if (!(allocator.newHandle() instanceof ExtendedHandle)) {
			throw new IllegalArgumentException("allocator.newHandle() must return an object of type: " + ExtendedHandle.class);
		} else {
			super.setRecvByteBufAllocator(allocator);
			return this;
		}
	}

	public KQueueChannelConfig setAutoRead(boolean autoRead) {
		super.setAutoRead(autoRead);
		return this;
	}

	@Deprecated
	public KQueueChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
		super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
		return this;
	}

	@Deprecated
	public KQueueChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
		super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
		return this;
	}

	public KQueueChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
		super.setWriteBufferWaterMark(writeBufferWaterMark);
		return this;
	}

	public KQueueChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
		super.setMessageSizeEstimator(estimator);
		return this;
	}

	@Override
	protected final void autoReadCleared() {
		this.channel.clearReadFilter();
	}

	final void setMaxBytesPerGatheringWrite(long maxBytesPerGatheringWrite) {
		this.maxBytesPerGatheringWrite = Math.min(Limits.SSIZE_MAX, maxBytesPerGatheringWrite);
	}

	final long getMaxBytesPerGatheringWrite() {
		return this.maxBytesPerGatheringWrite;
	}
}
