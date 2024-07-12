package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public interface Http2ConnectionEncoder extends Http2FrameWriter {
	void lifecycleManager(Http2LifecycleManager http2LifecycleManager);

	Http2Connection connection();

	Http2RemoteFlowController flowController();

	Http2FrameWriter frameWriter();

	Http2Settings pollSentSettings();

	void remoteSettings(Http2Settings http2Settings) throws Http2Exception;

	@Override
	ChannelFuture writeFrame(
		ChannelHandlerContext channelHandlerContext, byte byte2, int integer, Http2Flags http2Flags, ByteBuf byteBuf, ChannelPromise channelPromise
	);
}
