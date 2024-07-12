package io.netty.util.internal;

import java.util.Queue;

public interface PriorityQueue<T> extends Queue<T> {
	boolean removeTyped(T object);

	boolean containsTyped(T object);

	void priorityChanged(T object);

	void clearIgnoringIndexes();
}
