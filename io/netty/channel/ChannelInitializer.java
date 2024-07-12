package io.netty.channel;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.ConcurrentMap;

@Sharable
public abstract class ChannelInitializer<C extends Channel> extends ChannelInboundHandlerAdapter {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChannelInitializer.class);
	private final ConcurrentMap<ChannelHandlerContext, Boolean> initMap = PlatformDependent.newConcurrentHashMap();

	protected abstract void initChannel(C channel) throws Exception;

	@Override
	public final void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		if (this.initChannel(ctx)) {
			ctx.pipeline().fireChannelRegistered();
		} else {
			ctx.fireChannelRegistered();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.warn("Failed to initialize a channel. Closing: " + ctx.channel(), cause);
		ctx.close();
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		if (ctx.channel().isRegistered()) {
			this.initChannel(ctx);
		}
	}

	private boolean initChannel(ChannelHandlerContext ctx) throws Exception {
		if (this.initMap.putIfAbsent(ctx, Boolean.TRUE) == null) {
			try {
				this.initChannel((C)ctx.channel());
			} catch (Throwable var6) {
				this.exceptionCaught(ctx, var6);
			} finally {
				this.remove(ctx);
			}

			return true;
		} else {
			return false;
		}
	}

	private void remove(ChannelHandlerContext ctx) {
		try {
			ChannelPipeline pipeline = ctx.pipeline();
			if (pipeline.context(this) != null) {
				pipeline.remove(this);
			}
		} finally {
			this.initMap.remove(ctx);
		}
	}
}
