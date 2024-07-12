package io.netty.channel.socket;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import java.net.InetAddress;
import java.net.NetworkInterface;

public interface DatagramChannelConfig extends ChannelConfig {
	int getSendBufferSize();

	DatagramChannelConfig setSendBufferSize(int integer);

	int getReceiveBufferSize();

	DatagramChannelConfig setReceiveBufferSize(int integer);

	int getTrafficClass();

	DatagramChannelConfig setTrafficClass(int integer);

	boolean isReuseAddress();

	DatagramChannelConfig setReuseAddress(boolean boolean1);

	boolean isBroadcast();

	DatagramChannelConfig setBroadcast(boolean boolean1);

	boolean isLoopbackModeDisabled();

	DatagramChannelConfig setLoopbackModeDisabled(boolean boolean1);

	int getTimeToLive();

	DatagramChannelConfig setTimeToLive(int integer);

	InetAddress getInterface();

	DatagramChannelConfig setInterface(InetAddress inetAddress);

	NetworkInterface getNetworkInterface();

	DatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface);

	@Deprecated
	DatagramChannelConfig setMaxMessagesPerRead(int integer);

	DatagramChannelConfig setWriteSpinCount(int integer);

	DatagramChannelConfig setConnectTimeoutMillis(int integer);

	DatagramChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

	DatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

	DatagramChannelConfig setAutoRead(boolean boolean1);

	DatagramChannelConfig setAutoClose(boolean boolean1);

	DatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

	DatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);
}
