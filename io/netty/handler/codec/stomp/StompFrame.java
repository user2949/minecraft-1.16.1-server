package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;

public interface StompFrame extends StompHeadersSubframe, LastStompContentSubframe {
	StompFrame copy();

	StompFrame duplicate();

	StompFrame retainedDuplicate();

	StompFrame replace(ByteBuf byteBuf);

	StompFrame retain();

	StompFrame retain(int integer);

	StompFrame touch();

	StompFrame touch(Object object);
}
