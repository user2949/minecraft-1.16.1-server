package io.netty.buffer;

public interface ByteBufAllocator {
	ByteBufAllocator DEFAULT = ByteBufUtil.DEFAULT_ALLOCATOR;

	ByteBuf buffer();

	ByteBuf buffer(int integer);

	ByteBuf buffer(int integer1, int integer2);

	ByteBuf ioBuffer();

	ByteBuf ioBuffer(int integer);

	ByteBuf ioBuffer(int integer1, int integer2);

	ByteBuf heapBuffer();

	ByteBuf heapBuffer(int integer);

	ByteBuf heapBuffer(int integer1, int integer2);

	ByteBuf directBuffer();

	ByteBuf directBuffer(int integer);

	ByteBuf directBuffer(int integer1, int integer2);

	CompositeByteBuf compositeBuffer();

	CompositeByteBuf compositeBuffer(int integer);

	CompositeByteBuf compositeHeapBuffer();

	CompositeByteBuf compositeHeapBuffer(int integer);

	CompositeByteBuf compositeDirectBuffer();

	CompositeByteBuf compositeDirectBuffer(int integer);

	boolean isDirectBufferPooled();

	int calculateNewCapacity(int integer1, int integer2);
}
