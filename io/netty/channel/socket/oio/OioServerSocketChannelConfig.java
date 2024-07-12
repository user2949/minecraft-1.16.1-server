package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.socket.ServerSocketChannelConfig;

public interface OioServerSocketChannelConfig extends ServerSocketChannelConfig {
	OioServerSocketChannelConfig setSoTimeout(int integer);

	int getSoTimeout();

	OioServerSocketChannelConfig setBacklog(int integer);

	OioServerSocketChannelConfig setReuseAddress(boolean boolean1);

	OioServerSocketChannelConfig setReceiveBufferSize(int integer);

	OioServerSocketChannelConfig setPerformancePreferences(int integer1, int integer2, int integer3);

	OioServerSocketChannelConfig setConnectTimeoutMillis(int integer);

	@Deprecated
	OioServerSocketChannelConfig setMaxMessagesPerRead(int integer);

	OioServerSocketChannelConfig setWriteSpinCount(int integer);

	OioServerSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

	OioServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

	OioServerSocketChannelConfig setAutoRead(boolean boolean1);

	OioServerSocketChannelConfig setAutoClose(boolean boolean1);

	OioServerSocketChannelConfig setWriteBufferHighWaterMark(int integer);

	OioServerSocketChannelConfig setWriteBufferLowWaterMark(int integer);

	OioServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

	OioServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);
}
