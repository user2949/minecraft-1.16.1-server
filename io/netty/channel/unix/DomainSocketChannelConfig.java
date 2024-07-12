package io.netty.channel.unix;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

public interface DomainSocketChannelConfig extends ChannelConfig {
	@Deprecated
	DomainSocketChannelConfig setMaxMessagesPerRead(int integer);

	DomainSocketChannelConfig setConnectTimeoutMillis(int integer);

	DomainSocketChannelConfig setWriteSpinCount(int integer);

	DomainSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

	DomainSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

	DomainSocketChannelConfig setAutoRead(boolean boolean1);

	DomainSocketChannelConfig setAutoClose(boolean boolean1);

	@Deprecated
	DomainSocketChannelConfig setWriteBufferHighWaterMark(int integer);

	@Deprecated
	DomainSocketChannelConfig setWriteBufferLowWaterMark(int integer);

	DomainSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

	DomainSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

	DomainSocketChannelConfig setReadMode(DomainSocketReadMode domainSocketReadMode);

	DomainSocketReadMode getReadMode();
}
