package io.netty.channel;

import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.TypeParameterMatcher;

public abstract class SimpleChannelInboundHandler<I> extends ChannelInboundHandlerAdapter {
	private final TypeParameterMatcher matcher;
	private final boolean autoRelease;

	protected SimpleChannelInboundHandler() {
		this(true);
	}

	protected SimpleChannelInboundHandler(boolean autoRelease) {
		this.matcher = TypeParameterMatcher.find(this, SimpleChannelInboundHandler.class, "I");
		this.autoRelease = autoRelease;
	}

	protected SimpleChannelInboundHandler(Class<? extends I> inboundMessageType) {
		this(inboundMessageType, true);
	}

	protected SimpleChannelInboundHandler(Class<? extends I> inboundMessageType, boolean autoRelease) {
		this.matcher = TypeParameterMatcher.get(inboundMessageType);
		this.autoRelease = autoRelease;
	}

	public boolean acceptInboundMessage(Object msg) throws Exception {
		return this.matcher.match(msg);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		boolean release = true;

		try {
			if (this.acceptInboundMessage(msg)) {
				this.channelRead0(ctx, (I)msg);
			} else {
				release = false;
				ctx.fireChannelRead(msg);
			}
		} finally {
			if (this.autoRelease && release) {
				ReferenceCountUtil.release(msg);
			}
		}
	}

	protected abstract void channelRead0(ChannelHandlerContext channelHandlerContext, I object) throws Exception;
}