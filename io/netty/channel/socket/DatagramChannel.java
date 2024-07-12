package io.netty.channel.socket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;

public interface DatagramChannel extends Channel {
	DatagramChannelConfig config();

	InetSocketAddress localAddress();

	InetSocketAddress remoteAddress();

	boolean isConnected();

	ChannelFuture joinGroup(InetAddress inetAddress);

	ChannelFuture joinGroup(InetAddress inetAddress, ChannelPromise channelPromise);

	ChannelFuture joinGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface);

	ChannelFuture joinGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface, ChannelPromise channelPromise);

	ChannelFuture joinGroup(InetAddress inetAddress1, NetworkInterface networkInterface, InetAddress inetAddress3);

	ChannelFuture joinGroup(InetAddress inetAddress1, NetworkInterface networkInterface, InetAddress inetAddress3, ChannelPromise channelPromise);

	ChannelFuture leaveGroup(InetAddress inetAddress);

	ChannelFuture leaveGroup(InetAddress inetAddress, ChannelPromise channelPromise);

	ChannelFuture leaveGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface);

	ChannelFuture leaveGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface, ChannelPromise channelPromise);

	ChannelFuture leaveGroup(InetAddress inetAddress1, NetworkInterface networkInterface, InetAddress inetAddress3);

	ChannelFuture leaveGroup(InetAddress inetAddress1, NetworkInterface networkInterface, InetAddress inetAddress3, ChannelPromise channelPromise);

	ChannelFuture block(InetAddress inetAddress1, NetworkInterface networkInterface, InetAddress inetAddress3);

	ChannelFuture block(InetAddress inetAddress1, NetworkInterface networkInterface, InetAddress inetAddress3, ChannelPromise channelPromise);

	ChannelFuture block(InetAddress inetAddress1, InetAddress inetAddress2);

	ChannelFuture block(InetAddress inetAddress1, InetAddress inetAddress2, ChannelPromise channelPromise);
}
