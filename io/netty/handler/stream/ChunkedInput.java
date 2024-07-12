package io.netty.handler.stream;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;

public interface ChunkedInput<B> {
	boolean isEndOfInput() throws Exception;

	void close() throws Exception;

	@Deprecated
	B readChunk(ChannelHandlerContext channelHandlerContext) throws Exception;

	B readChunk(ByteBufAllocator byteBufAllocator) throws Exception;

	long length();

	long progress();
}
