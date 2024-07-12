package io.netty.channel.sctp;

import com.sun.nio.sctp.SctpStandardSocketOptions.InitMaxStreams;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

public interface SctpChannelConfig extends ChannelConfig {
	boolean isSctpNoDelay();

	SctpChannelConfig setSctpNoDelay(boolean boolean1);

	int getSendBufferSize();

	SctpChannelConfig setSendBufferSize(int integer);

	int getReceiveBufferSize();

	SctpChannelConfig setReceiveBufferSize(int integer);

	InitMaxStreams getInitMaxStreams();

	SctpChannelConfig setInitMaxStreams(InitMaxStreams initMaxStreams);

	SctpChannelConfig setConnectTimeoutMillis(int integer);

	@Deprecated
	SctpChannelConfig setMaxMessagesPerRead(int integer);

	SctpChannelConfig setWriteSpinCount(int integer);

	SctpChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

	SctpChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

	SctpChannelConfig setAutoRead(boolean boolean1);

	SctpChannelConfig setAutoClose(boolean boolean1);

	SctpChannelConfig setWriteBufferHighWaterMark(int integer);

	SctpChannelConfig setWriteBufferLowWaterMark(int integer);

	SctpChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

	SctpChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);
}
