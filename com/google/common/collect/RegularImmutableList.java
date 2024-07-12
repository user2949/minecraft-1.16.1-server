package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Spliterator;
import java.util.Spliterators;

@GwtCompatible(
	serializable = true,
	emulated = true
)
class RegularImmutableList<E> extends ImmutableList<E> {
	static final ImmutableList<Object> EMPTY = new RegularImmutableList<>(ObjectArrays.EMPTY_ARRAY);
	private final transient Object[] array;

	RegularImmutableList(Object[] array) {
		this.array = array;
	}

	public int size() {
		return this.array.length;
	}

	@Override
	boolean isPartialView() {
		return false;
	}

	@Override
	int copyIntoArray(Object[] dst, int dstOff) {
		System.arraycopy(this.array, 0, dst, dstOff, this.array.length);
		return dstOff + this.array.length;
	}

	public E get(int index) {
		return (E)this.array[index];
	}

	@Override
	public UnmodifiableListIterator<E> listIterator(int index) {
		return Iterators.forArray((E[])this.array, 0, this.array.length, index);
	}

	@Override
	public Spliterator<E> spliterator() {
		return Spliterators.spliterator(this.array, 1296);
	}
}
