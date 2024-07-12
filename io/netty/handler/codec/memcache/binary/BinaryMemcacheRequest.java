package io.netty.handler.codec.memcache.binary;

public interface BinaryMemcacheRequest extends BinaryMemcacheMessage {
	short reserved();

	BinaryMemcacheRequest setReserved(short short1);

	BinaryMemcacheRequest retain();

	BinaryMemcacheRequest retain(int integer);

	BinaryMemcacheRequest touch();

	BinaryMemcacheRequest touch(Object object);
}
