package io.netty.channel.sctp;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ServerChannel;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Set;

public interface SctpServerChannel extends ServerChannel {
	SctpServerChannelConfig config();

	InetSocketAddress localAddress();

	Set<InetSocketAddress> allLocalAddresses();

	ChannelFuture bindAddress(InetAddress inetAddress);

	ChannelFuture bindAddress(InetAddress inetAddress, ChannelPromise channelPromise);

	ChannelFuture unbindAddress(InetAddress inetAddress);

	ChannelFuture unbindAddress(InetAddress inetAddress, ChannelPromise channelPromise);
}
