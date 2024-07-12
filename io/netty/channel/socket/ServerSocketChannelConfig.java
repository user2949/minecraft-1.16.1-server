package io.netty.channel.socket;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

public interface ServerSocketChannelConfig extends ChannelConfig {
	int getBacklog();

	ServerSocketChannelConfig setBacklog(int integer);

	boolean isReuseAddress();

	ServerSocketChannelConfig setReuseAddress(boolean boolean1);

	int getReceiveBufferSize();

	ServerSocketChannelConfig setReceiveBufferSize(int integer);

	ServerSocketChannelConfig setPerformancePreferences(int integer1, int integer2, int integer3);

	ServerSocketChannelConfig setConnectTimeoutMillis(int integer);

	@Deprecated
	ServerSocketChannelConfig setMaxMessagesPerRead(int integer);

	ServerSocketChannelConfig setWriteSpinCount(int integer);

	ServerSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

	ServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

	ServerSocketChannelConfig setAutoRead(boolean boolean1);

	ServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

	ServerSocketChannelConfig setWriteBufferHighWaterMark(int integer);

	ServerSocketChannelConfig setWriteBufferLowWaterMark(int integer);

	ServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);
}
