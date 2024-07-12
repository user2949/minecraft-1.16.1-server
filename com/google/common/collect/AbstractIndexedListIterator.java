package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.NoSuchElementException;

@GwtCompatible
abstract class AbstractIndexedListIterator<E> extends UnmodifiableListIterator<E> {
	private final int size;
	private int position;

	protected abstract E get(int integer);

	protected AbstractIndexedListIterator(int size) {
		this(size, 0);
	}

	protected AbstractIndexedListIterator(int size, int position) {
		Preconditions.checkPositionIndex(position, size);
		this.size = size;
		this.position = position;
	}

	public final boolean hasNext() {
		return this.position < this.size;
	}

	public final E next() {
		if (!this.hasNext()) {
			throw new NoSuchElementException();
		} else {
			return this.get(this.position++);
		}
	}

	public final int nextIndex() {
		return this.position;
	}

	public final boolean hasPrevious() {
		return this.position > 0;
	}

	public final E previous() {
		if (!this.hasPrevious()) {
			throw new NoSuchElementException();
		} else {
			return this.get(--this.position);
		}
	}

	public final int previousIndex() {
		return this.position - 1;
	}
}
