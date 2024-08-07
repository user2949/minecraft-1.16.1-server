package io.netty.channel;

import java.net.SocketAddress;

public class ChannelDuplexHandler extends ChannelInboundHandlerAdapter implements ChannelOutboundHandler {
	@Override
	public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
		ctx.bind(localAddress, promise);
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
		ctx.connect(remoteAddress, localAddress, promise);
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		ctx.disconnect(promise);
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		ctx.close(promise);
	}

	@Override
	public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		ctx.deregister(promise);
	}

	@Override
	public void read(ChannelHandlerContext ctx) throws Exception {
		ctx.read();
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		ctx.write(msg, promise);
	}

	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
}
