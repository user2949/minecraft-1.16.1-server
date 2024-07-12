package io.netty.channel.sctp;

import com.sun.nio.sctp.SctpStandardSocketOptions.InitMaxStreams;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

public interface SctpServerChannelConfig extends ChannelConfig {
	int getBacklog();

	SctpServerChannelConfig setBacklog(int integer);

	int getSendBufferSize();

	SctpServerChannelConfig setSendBufferSize(int integer);

	int getReceiveBufferSize();

	SctpServerChannelConfig setReceiveBufferSize(int integer);

	InitMaxStreams getInitMaxStreams();

	SctpServerChannelConfig setInitMaxStreams(InitMaxStreams initMaxStreams);

	@Deprecated
	SctpServerChannelConfig setMaxMessagesPerRead(int integer);

	SctpServerChannelConfig setWriteSpinCount(int integer);

	SctpServerChannelConfig setConnectTimeoutMillis(int integer);

	SctpServerChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

	SctpServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

	SctpServerChannelConfig setAutoRead(boolean boolean1);

	SctpServerChannelConfig setAutoClose(boolean boolean1);

	SctpServerChannelConfig setWriteBufferHighWaterMark(int integer);

	SctpServerChannelConfig setWriteBufferLowWaterMark(int integer);

	SctpServerChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

	SctpServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);
}
