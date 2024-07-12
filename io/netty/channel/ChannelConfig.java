package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import java.util.Map;

public interface ChannelConfig {
	Map<ChannelOption<?>, Object> getOptions();

	boolean setOptions(Map<ChannelOption<?>, ?> map);

	<T> T getOption(ChannelOption<T> channelOption);

	<T> boolean setOption(ChannelOption<T> channelOption, T object);

	int getConnectTimeoutMillis();

	ChannelConfig setConnectTimeoutMillis(int integer);

	@Deprecated
	int getMaxMessagesPerRead();

	@Deprecated
	ChannelConfig setMaxMessagesPerRead(int integer);

	int getWriteSpinCount();

	ChannelConfig setWriteSpinCount(int integer);

	ByteBufAllocator getAllocator();

	ChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

	<T extends RecvByteBufAllocator> T getRecvByteBufAllocator();

	ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

	boolean isAutoRead();

	ChannelConfig setAutoRead(boolean boolean1);

	@Deprecated
	boolean isAutoClose();

	@Deprecated
	ChannelConfig setAutoClose(boolean boolean1);

	int getWriteBufferHighWaterMark();

	ChannelConfig setWriteBufferHighWaterMark(int integer);

	int getWriteBufferLowWaterMark();

	ChannelConfig setWriteBufferLowWaterMark(int integer);

	MessageSizeEstimator getMessageSizeEstimator();

	ChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

	WriteBufferWaterMark getWriteBufferWaterMark();

	ChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);
}
