package io.netty.util.internal;

public interface PriorityQueueNode {
	int INDEX_NOT_IN_QUEUE = -1;

	int priorityQueueIndex(DefaultPriorityQueue<?> defaultPriorityQueue);

	void priorityQueueIndex(DefaultPriorityQueue<?> defaultPriorityQueue, int integer);
}
