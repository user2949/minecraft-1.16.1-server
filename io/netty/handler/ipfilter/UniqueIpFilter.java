package io.netty.handler.ipfilter;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.internal.ConcurrentSet;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Set;

@Sharable
public class UniqueIpFilter extends AbstractRemoteAddressFilter<InetSocketAddress> {
	private final Set<InetAddress> connected = new ConcurrentSet<InetAddress>();

	protected boolean accept(ChannelHandlerContext ctx, InetSocketAddress remoteAddress) throws Exception {
		final InetAddress remoteIp = remoteAddress.getAddress();
		if (this.connected.contains(remoteIp)) {
			return false;
		} else {
			this.connected.add(remoteIp);
			ctx.channel().closeFuture().addListener(new ChannelFutureListener() {
				public void operationComplete(ChannelFuture future) throws Exception {
					UniqueIpFilter.this.connected.remove(remoteIp);
				}
			});
			return true;
		}
	}
}