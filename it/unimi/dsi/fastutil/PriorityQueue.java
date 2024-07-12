package it.unimi.dsi.fastutil;

import java.util.Comparator;

public interface PriorityQueue<K> {
	void enqueue(K object);

	K dequeue();

	default boolean isEmpty() {
		return this.size() == 0;
	}

	int size();

	void clear();

	K first();

	default K last() {
		throw new UnsupportedOperationException();
	}

	default void changed() {
		throw new UnsupportedOperationException();
	}

	Comparator<? super K> comparator();
}
