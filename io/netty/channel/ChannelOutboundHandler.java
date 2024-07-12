package io.netty.channel;

import java.net.SocketAddress;

public interface ChannelOutboundHandler extends ChannelHandler {
	void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception;

	void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress2, SocketAddress socketAddress3, ChannelPromise channelPromise) throws Exception;

	void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception;

	void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception;

	void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception;

	void read(ChannelHandlerContext channelHandlerContext) throws Exception;

	void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception;

	void flush(ChannelHandlerContext channelHandlerContext) throws Exception;
}
