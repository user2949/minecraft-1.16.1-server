package io.netty.channel.socket;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

public interface SocketChannelConfig extends ChannelConfig {
	boolean isTcpNoDelay();

	SocketChannelConfig setTcpNoDelay(boolean boolean1);

	int getSoLinger();

	SocketChannelConfig setSoLinger(int integer);

	int getSendBufferSize();

	SocketChannelConfig setSendBufferSize(int integer);

	int getReceiveBufferSize();

	SocketChannelConfig setReceiveBufferSize(int integer);

	boolean isKeepAlive();

	SocketChannelConfig setKeepAlive(boolean boolean1);

	int getTrafficClass();

	SocketChannelConfig setTrafficClass(int integer);

	boolean isReuseAddress();

	SocketChannelConfig setReuseAddress(boolean boolean1);

	SocketChannelConfig setPerformancePreferences(int integer1, int integer2, int integer3);

	boolean isAllowHalfClosure();

	SocketChannelConfig setAllowHalfClosure(boolean boolean1);

	SocketChannelConfig setConnectTimeoutMillis(int integer);

	@Deprecated
	SocketChannelConfig setMaxMessagesPerRead(int integer);

	SocketChannelConfig setWriteSpinCount(int integer);

	SocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

	SocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

	SocketChannelConfig setAutoRead(boolean boolean1);

	SocketChannelConfig setAutoClose(boolean boolean1);

	SocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

	SocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);
}
