package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;

public interface RedisMessagePool {
	SimpleStringRedisMessage getSimpleString(String string);

	SimpleStringRedisMessage getSimpleString(ByteBuf byteBuf);

	ErrorRedisMessage getError(String string);

	ErrorRedisMessage getError(ByteBuf byteBuf);

	IntegerRedisMessage getInteger(long long1);

	IntegerRedisMessage getInteger(ByteBuf byteBuf);

	byte[] getByteBufOfInteger(long long1);
}
