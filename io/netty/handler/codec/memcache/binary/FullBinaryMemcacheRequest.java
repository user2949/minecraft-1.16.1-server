package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.FullMemcacheMessage;

public interface FullBinaryMemcacheRequest extends BinaryMemcacheRequest, FullMemcacheMessage {
	FullBinaryMemcacheRequest copy();

	FullBinaryMemcacheRequest duplicate();

	FullBinaryMemcacheRequest retainedDuplicate();

	FullBinaryMemcacheRequest replace(ByteBuf byteBuf);

	FullBinaryMemcacheRequest retain(int integer);

	FullBinaryMemcacheRequest retain();

	FullBinaryMemcacheRequest touch();

	FullBinaryMemcacheRequest touch(Object object);
}
