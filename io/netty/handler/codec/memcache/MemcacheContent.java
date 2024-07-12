package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

public interface MemcacheContent extends MemcacheObject, ByteBufHolder {
	MemcacheContent copy();

	MemcacheContent duplicate();

	MemcacheContent retainedDuplicate();

	MemcacheContent replace(ByteBuf byteBuf);

	MemcacheContent retain();

	MemcacheContent retain(int integer);

	MemcacheContent touch();

	MemcacheContent touch(Object object);
}
