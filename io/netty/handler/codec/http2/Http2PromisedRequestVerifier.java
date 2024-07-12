package io.netty.handler.codec.http2;

import io.netty.channel.ChannelHandlerContext;

public interface Http2PromisedRequestVerifier {
	Http2PromisedRequestVerifier ALWAYS_VERIFY = new Http2PromisedRequestVerifier() {
		@Override
		public boolean isAuthoritative(ChannelHandlerContext ctx, Http2Headers headers) {
			return true;
		}

		@Override
		public boolean isCacheable(Http2Headers headers) {
			return true;
		}

		@Override
		public boolean isSafe(Http2Headers headers) {
			return true;
		}
	};

	boolean isAuthoritative(ChannelHandlerContext channelHandlerContext, Http2Headers http2Headers);

	boolean isCacheable(Http2Headers http2Headers);

	boolean isSafe(Http2Headers http2Headers);
}
