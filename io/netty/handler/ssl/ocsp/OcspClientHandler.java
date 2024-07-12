package io.netty.handler.ssl.ocsp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.ssl.ReferenceCountedOpenSslEngine;
import io.netty.handler.ssl.SslHandshakeCompletionEvent;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.ThrowableUtil;
import javax.net.ssl.SSLHandshakeException;

public abstract class OcspClientHandler extends ChannelInboundHandlerAdapter {
	private static final SSLHandshakeException OCSP_VERIFICATION_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new SSLHandshakeException("Bad OCSP response"), OcspClientHandler.class, "verify(...)"
	);
	private final ReferenceCountedOpenSslEngine engine;

	protected OcspClientHandler(ReferenceCountedOpenSslEngine engine) {
		this.engine = ObjectUtil.checkNotNull(engine, "engine");
	}

	protected abstract boolean verify(ChannelHandlerContext channelHandlerContext, ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine) throws Exception;

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof SslHandshakeCompletionEvent) {
			ctx.pipeline().remove(this);
			SslHandshakeCompletionEvent event = (SslHandshakeCompletionEvent)evt;
			if (event.isSuccess() && !this.verify(ctx, this.engine)) {
				throw OCSP_VERIFICATION_EXCEPTION;
			}
		}

		ctx.fireUserEventTriggered(evt);
	}
}
