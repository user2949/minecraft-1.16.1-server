package io.netty.handler.codec.http2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpScheme;
import io.netty.handler.codec.http2.HttpConversionUtil.ExtensionHeaderNames;

public class InboundHttpToHttp2Adapter extends ChannelInboundHandlerAdapter {
	private final Http2Connection connection;
	private final Http2FrameListener listener;

	public InboundHttpToHttp2Adapter(Http2Connection connection, Http2FrameListener listener) {
		this.connection = connection;
		this.listener = listener;
	}

	private static int getStreamId(Http2Connection connection, HttpHeaders httpHeaders) {
		return httpHeaders.getInt(ExtensionHeaderNames.STREAM_ID.text(), connection.remote().incrementAndGetNextStreamId());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof FullHttpMessage) {
			handle(ctx, this.connection, this.listener, (FullHttpMessage)msg);
		} else {
			super.channelRead(ctx, msg);
		}
	}

	static void handle(ChannelHandlerContext ctx, Http2Connection connection, Http2FrameListener listener, FullHttpMessage message) throws Http2Exception {
		try {
			int streamId = getStreamId(connection, message.headers());
			Http2Stream stream = connection.stream(streamId);
			if (stream == null) {
				stream = connection.remote().createStream(streamId, false);
			}

			message.headers().set(ExtensionHeaderNames.SCHEME.text(), HttpScheme.HTTP.name());
			Http2Headers messageHeaders = HttpConversionUtil.toHttp2Headers(message, true);
			boolean hasContent = message.content().isReadable();
			boolean hasTrailers = !message.trailingHeaders().isEmpty();
			listener.onHeadersRead(ctx, streamId, messageHeaders, 0, !hasContent && !hasTrailers);
			if (hasContent) {
				listener.onDataRead(ctx, streamId, message.content(), 0, !hasTrailers);
			}

			if (hasTrailers) {
				Http2Headers headers = HttpConversionUtil.toHttp2Headers(message.trailingHeaders(), true);
				listener.onHeadersRead(ctx, streamId, headers, 0, true);
			}

			stream.closeRemoteSide();
		} finally {
			message.release();
		}
	}
}
