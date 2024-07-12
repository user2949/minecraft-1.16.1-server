package io.netty.channel;

import java.net.SocketAddress;

public interface ChannelOutboundInvoker {
	ChannelFuture bind(SocketAddress socketAddress);

	ChannelFuture connect(SocketAddress socketAddress);

	ChannelFuture connect(SocketAddress socketAddress1, SocketAddress socketAddress2);

	ChannelFuture disconnect();

	ChannelFuture close();

	ChannelFuture deregister();

	ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise);

	ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise);

	ChannelFuture connect(SocketAddress socketAddress1, SocketAddress socketAddress2, ChannelPromise channelPromise);

	ChannelFuture disconnect(ChannelPromise channelPromise);

	ChannelFuture close(ChannelPromise channelPromise);

	ChannelFuture deregister(ChannelPromise channelPromise);

	ChannelOutboundInvoker read();

	ChannelFuture write(Object object);

	ChannelFuture write(Object object, ChannelPromise channelPromise);

	ChannelOutboundInvoker flush();

	ChannelFuture writeAndFlush(Object object, ChannelPromise channelPromise);

	ChannelFuture writeAndFlush(Object object);

	ChannelPromise newPromise();

	ChannelProgressivePromise newProgressivePromise();

	ChannelFuture newSucceededFuture();

	ChannelFuture newFailedFuture(Throwable throwable);

	ChannelPromise voidPromise();
}
