package io.netty.channel.udt;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

@Deprecated
public interface UdtChannelConfig extends ChannelConfig {
	int getProtocolReceiveBufferSize();

	int getProtocolSendBufferSize();

	int getReceiveBufferSize();

	int getSendBufferSize();

	int getSoLinger();

	int getSystemReceiveBufferSize();

	int getSystemSendBufferSize();

	boolean isReuseAddress();

	UdtChannelConfig setConnectTimeoutMillis(int integer);

	@Deprecated
	UdtChannelConfig setMaxMessagesPerRead(int integer);

	UdtChannelConfig setWriteSpinCount(int integer);

	UdtChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

	UdtChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

	UdtChannelConfig setAutoRead(boolean boolean1);

	UdtChannelConfig setAutoClose(boolean boolean1);

	UdtChannelConfig setWriteBufferHighWaterMark(int integer);

	UdtChannelConfig setWriteBufferLowWaterMark(int integer);

	UdtChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

	UdtChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

	UdtChannelConfig setProtocolReceiveBufferSize(int integer);

	UdtChannelConfig setProtocolSendBufferSize(int integer);

	UdtChannelConfig setReceiveBufferSize(int integer);

	UdtChannelConfig setReuseAddress(boolean boolean1);

	UdtChannelConfig setSendBufferSize(int integer);

	UdtChannelConfig setSoLinger(int integer);

	UdtChannelConfig setSystemReceiveBufferSize(int integer);

	UdtChannelConfig setSystemSendBufferSize(int integer);
}
