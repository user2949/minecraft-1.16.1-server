package io.netty.channel;

public interface ChannelInboundInvoker {
	ChannelInboundInvoker fireChannelRegistered();

	ChannelInboundInvoker fireChannelUnregistered();

	ChannelInboundInvoker fireChannelActive();

	ChannelInboundInvoker fireChannelInactive();

	ChannelInboundInvoker fireExceptionCaught(Throwable throwable);

	ChannelInboundInvoker fireUserEventTriggered(Object object);

	ChannelInboundInvoker fireChannelRead(Object object);

	ChannelInboundInvoker fireChannelReadComplete();

	ChannelInboundInvoker fireChannelWritabilityChanged();
}
