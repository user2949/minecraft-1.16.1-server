package org.apache.logging.log4j.core.util;

import java.lang.reflect.Array;

public final class CyclicBuffer<T> {
	private final T[] ring;
	private int first = 0;
	private int last = 0;
	private int numElems = 0;
	private final Class<T> clazz;

	public CyclicBuffer(Class<T> clazz, int size) throws IllegalArgumentException {
		if (size < 1) {
			throw new IllegalArgumentException("The maxSize argument (" + size + ") is not a positive integer.");
		} else {
			this.ring = this.makeArray(clazz, size);
			this.clazz = clazz;
		}
	}

	private T[] makeArray(Class<T> cls, int size) {
		return (T[])((Object[])Array.newInstance(cls, size));
	}

	public synchronized void add(T item) {
		this.ring[this.last] = item;
		if (++this.last == this.ring.length) {
			this.last = 0;
		}

		if (this.numElems < this.ring.length) {
			this.numElems++;
		} else if (++this.first == this.ring.length) {
			this.first = 0;
		}
	}

	public synchronized T[] removeAll() {
		T[] array = this.makeArray(this.clazz, this.numElems);
		int index = 0;

		while (this.numElems > 0) {
			this.numElems--;
			array[index++] = this.ring[this.first];
			this.ring[this.first] = null;
			if (++this.first == this.ring.length) {
				this.first = 0;
			}
		}

		return array;
	}

	public boolean isEmpty() {
		return 0 == this.numElems;
	}
}
