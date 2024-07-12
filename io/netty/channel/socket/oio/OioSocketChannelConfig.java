package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.socket.SocketChannelConfig;

public interface OioSocketChannelConfig extends SocketChannelConfig {
	OioSocketChannelConfig setSoTimeout(int integer);

	int getSoTimeout();

	OioSocketChannelConfig setTcpNoDelay(boolean boolean1);

	OioSocketChannelConfig setSoLinger(int integer);

	OioSocketChannelConfig setSendBufferSize(int integer);

	OioSocketChannelConfig setReceiveBufferSize(int integer);

	OioSocketChannelConfig setKeepAlive(boolean boolean1);

	OioSocketChannelConfig setTrafficClass(int integer);

	OioSocketChannelConfig setReuseAddress(boolean boolean1);

	OioSocketChannelConfig setPerformancePreferences(int integer1, int integer2, int integer3);

	OioSocketChannelConfig setAllowHalfClosure(boolean boolean1);

	OioSocketChannelConfig setConnectTimeoutMillis(int integer);

	@Deprecated
	OioSocketChannelConfig setMaxMessagesPerRead(int integer);

	OioSocketChannelConfig setWriteSpinCount(int integer);

	OioSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

	OioSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

	OioSocketChannelConfig setAutoRead(boolean boolean1);

	OioSocketChannelConfig setAutoClose(boolean boolean1);

	OioSocketChannelConfig setWriteBufferHighWaterMark(int integer);

	OioSocketChannelConfig setWriteBufferLowWaterMark(int integer);

	OioSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

	OioSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);
}
