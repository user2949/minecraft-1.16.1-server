package io.netty.handler.codec.memcache.binary;

public interface BinaryMemcacheResponse extends BinaryMemcacheMessage {
	short status();

	BinaryMemcacheResponse setStatus(short short1);

	BinaryMemcacheResponse retain();

	BinaryMemcacheResponse retain(int integer);

	BinaryMemcacheResponse touch();

	BinaryMemcacheResponse touch(Object object);
}
