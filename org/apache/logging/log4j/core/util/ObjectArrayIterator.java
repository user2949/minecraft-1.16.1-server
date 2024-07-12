package org.apache.logging.log4j.core.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ObjectArrayIterator<E> implements Iterator<E> {
	final E[] array;
	final int startIndex;
	final int endIndex;
	int index = 0;

	@SafeVarargs
	public ObjectArrayIterator(E... array) {
		this(array, 0, array.length);
	}

	public ObjectArrayIterator(E[] array, int start) {
		this(array, start, array.length);
	}

	public ObjectArrayIterator(E[] array, int start, int end) {
		if (start < 0) {
			throw new ArrayIndexOutOfBoundsException("Start index must not be less than zero");
		} else if (end > array.length) {
			throw new ArrayIndexOutOfBoundsException("End index must not be greater than the array length");
		} else if (start > array.length) {
			throw new ArrayIndexOutOfBoundsException("Start index must not be greater than the array length");
		} else if (end < start) {
			throw new IllegalArgumentException("End index must not be less than start index");
		} else {
			this.array = array;
			this.startIndex = start;
			this.endIndex = end;
			this.index = start;
		}
	}

	public boolean hasNext() {
		return this.index < this.endIndex;
	}

	public E next() {
		if (!this.hasNext()) {
			throw new NoSuchElementException();
		} else {
			return this.array[this.index++];
		}
	}

	public void remove() {
		throw new UnsupportedOperationException("remove() method is not supported for an ObjectArrayIterator");
	}

	public E[] getArray() {
		return this.array;
	}

	public int getStartIndex() {
		return this.startIndex;
	}

	public int getEndIndex() {
		return this.endIndex;
	}

	public void reset() {
		this.index = this.startIndex;
	}
}
