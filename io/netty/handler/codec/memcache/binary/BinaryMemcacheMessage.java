package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.MemcacheMessage;

public interface BinaryMemcacheMessage extends MemcacheMessage {
	byte magic();

	BinaryMemcacheMessage setMagic(byte byte1);

	byte opcode();

	BinaryMemcacheMessage setOpcode(byte byte1);

	short keyLength();

	byte extrasLength();

	byte dataType();

	BinaryMemcacheMessage setDataType(byte byte1);

	int totalBodyLength();

	BinaryMemcacheMessage setTotalBodyLength(int integer);

	int opaque();

	BinaryMemcacheMessage setOpaque(int integer);

	long cas();

	BinaryMemcacheMessage setCas(long long1);

	ByteBuf key();

	BinaryMemcacheMessage setKey(ByteBuf byteBuf);

	ByteBuf extras();

	BinaryMemcacheMessage setExtras(ByteBuf byteBuf);

	BinaryMemcacheMessage retain();

	BinaryMemcacheMessage retain(int integer);

	BinaryMemcacheMessage touch();

	BinaryMemcacheMessage touch(Object object);
}
