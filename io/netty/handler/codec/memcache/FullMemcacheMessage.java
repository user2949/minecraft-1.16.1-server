package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBuf;

public interface FullMemcacheMessage extends MemcacheMessage, LastMemcacheContent {
	FullMemcacheMessage copy();

	FullMemcacheMessage duplicate();

	FullMemcacheMessage retainedDuplicate();

	FullMemcacheMessage replace(ByteBuf byteBuf);

	FullMemcacheMessage retain(int integer);

	FullMemcacheMessage retain();

	FullMemcacheMessage touch();

	FullMemcacheMessage touch(Object object);
}
