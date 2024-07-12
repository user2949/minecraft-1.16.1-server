package io.netty.channel.udt;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

@Deprecated
public interface UdtServerChannelConfig extends UdtChannelConfig {
	int getBacklog();

	UdtServerChannelConfig setBacklog(int integer);

	UdtServerChannelConfig setConnectTimeoutMillis(int integer);

	@Deprecated
	UdtServerChannelConfig setMaxMessagesPerRead(int integer);

	UdtServerChannelConfig setWriteSpinCount(int integer);

	UdtServerChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

	UdtServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

	UdtServerChannelConfig setAutoRead(boolean boolean1);

	UdtServerChannelConfig setAutoClose(boolean boolean1);

	UdtServerChannelConfig setProtocolReceiveBufferSize(int integer);

	UdtServerChannelConfig setProtocolSendBufferSize(int integer);

	UdtServerChannelConfig setReceiveBufferSize(int integer);

	UdtServerChannelConfig setReuseAddress(boolean boolean1);

	UdtServerChannelConfig setSendBufferSize(int integer);

	UdtServerChannelConfig setSoLinger(int integer);

	UdtServerChannelConfig setSystemReceiveBufferSize(int integer);

	UdtServerChannelConfig setSystemSendBufferSize(int integer);

	UdtServerChannelConfig setWriteBufferHighWaterMark(int integer);

	UdtServerChannelConfig setWriteBufferLowWaterMark(int integer);

	UdtServerChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

	UdtServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);
}
