package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

public interface SpdyDataFrame extends ByteBufHolder, SpdyStreamFrame {
	SpdyDataFrame setStreamId(int integer);

	SpdyDataFrame setLast(boolean boolean1);

	@Override
	ByteBuf content();

	SpdyDataFrame copy();

	SpdyDataFrame duplicate();

	SpdyDataFrame retainedDuplicate();

	SpdyDataFrame replace(ByteBuf byteBuf);

	SpdyDataFrame retain();

	SpdyDataFrame retain(int integer);

	SpdyDataFrame touch();

	SpdyDataFrame touch(Object object);
}
