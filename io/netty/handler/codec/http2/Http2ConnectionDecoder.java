package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.io.Closeable;
import java.util.List;

public interface Http2ConnectionDecoder extends Closeable {
	void lifecycleManager(Http2LifecycleManager http2LifecycleManager);

	Http2Connection connection();

	Http2LocalFlowController flowController();

	void frameListener(Http2FrameListener http2FrameListener);

	Http2FrameListener frameListener();

	void decodeFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Http2Exception;

	Http2Settings localSettings();

	boolean prefaceReceived();

	void close();
}
