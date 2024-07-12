package io.netty.channel.sctp;

import com.sun.nio.sctp.Association;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Set;

public interface SctpChannel extends Channel {
	SctpServerChannel parent();

	Association association();

	InetSocketAddress localAddress();

	Set<InetSocketAddress> allLocalAddresses();

	SctpChannelConfig config();

	InetSocketAddress remoteAddress();

	Set<InetSocketAddress> allRemoteAddresses();

	ChannelFuture bindAddress(InetAddress inetAddress);

	ChannelFuture bindAddress(InetAddress inetAddress, ChannelPromise channelPromise);

	ChannelFuture unbindAddress(InetAddress inetAddress);

	ChannelFuture unbindAddress(InetAddress inetAddress, ChannelPromise channelPromise);
}
