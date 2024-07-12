package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.socket.DatagramChannelConfig;
import java.net.InetAddress;
import java.net.NetworkInterface;

public interface OioDatagramChannelConfig extends DatagramChannelConfig {
	OioDatagramChannelConfig setSoTimeout(int integer);

	int getSoTimeout();

	OioDatagramChannelConfig setSendBufferSize(int integer);

	OioDatagramChannelConfig setReceiveBufferSize(int integer);

	OioDatagramChannelConfig setTrafficClass(int integer);

	OioDatagramChannelConfig setReuseAddress(boolean boolean1);

	OioDatagramChannelConfig setBroadcast(boolean boolean1);

	OioDatagramChannelConfig setLoopbackModeDisabled(boolean boolean1);

	OioDatagramChannelConfig setTimeToLive(int integer);

	OioDatagramChannelConfig setInterface(InetAddress inetAddress);

	OioDatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface);

	OioDatagramChannelConfig setMaxMessagesPerRead(int integer);

	OioDatagramChannelConfig setWriteSpinCount(int integer);

	OioDatagramChannelConfig setConnectTimeoutMillis(int integer);

	OioDatagramChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

	OioDatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

	OioDatagramChannelConfig setAutoRead(boolean boolean1);

	OioDatagramChannelConfig setAutoClose(boolean boolean1);

	OioDatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

	OioDatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

	OioDatagramChannelConfig setWriteBufferHighWaterMark(int integer);

	OioDatagramChannelConfig setWriteBufferLowWaterMark(int integer);
}
