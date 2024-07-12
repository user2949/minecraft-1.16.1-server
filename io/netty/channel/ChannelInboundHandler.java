package io.netty.channel;

public interface ChannelInboundHandler extends ChannelHandler {
	void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception;

	void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception;

	void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception;

	void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception;

	void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception;

	void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception;

	void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object object) throws Exception;

	void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception;

	@Override
	void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception;
}
