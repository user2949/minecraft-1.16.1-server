package io.netty.buffer;

import io.netty.util.ReferenceCounted;

public interface ByteBufHolder extends ReferenceCounted {
	ByteBuf content();

	ByteBufHolder copy();

	ByteBufHolder duplicate();

	ByteBufHolder retainedDuplicate();

	ByteBufHolder replace(ByteBuf byteBuf);

	ByteBufHolder retain();

	ByteBufHolder retain(int integer);

	ByteBufHolder touch();

	ByteBufHolder touch(Object object);
}
