package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.FullMemcacheMessage;

public interface FullBinaryMemcacheResponse extends BinaryMemcacheResponse, FullMemcacheMessage {
	FullBinaryMemcacheResponse copy();

	FullBinaryMemcacheResponse duplicate();

	FullBinaryMemcacheResponse retainedDuplicate();

	FullBinaryMemcacheResponse replace(ByteBuf byteBuf);

	FullBinaryMemcacheResponse retain(int integer);

	FullBinaryMemcacheResponse retain();

	FullBinaryMemcacheResponse touch();

	FullBinaryMemcacheResponse touch(Object object);
}
