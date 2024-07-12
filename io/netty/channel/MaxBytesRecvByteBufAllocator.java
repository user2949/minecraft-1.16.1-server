package io.netty.channel;

import java.util.Map.Entry;

public interface MaxBytesRecvByteBufAllocator extends RecvByteBufAllocator {
	int maxBytesPerRead();

	MaxBytesRecvByteBufAllocator maxBytesPerRead(int integer);

	int maxBytesPerIndividualRead();

	MaxBytesRecvByteBufAllocator maxBytesPerIndividualRead(int integer);

	Entry<Integer, Integer> maxBytesPerReadPair();

	MaxBytesRecvByteBufAllocator maxBytesPerReadPair(int integer1, int integer2);
}
