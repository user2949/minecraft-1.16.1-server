package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.Spliterator;

@GwtCompatible(
	serializable = true,
	emulated = true
)
final class SingletonImmutableList<E> extends ImmutableList<E> {
	final transient E element;

	SingletonImmutableList(E element) {
		this.element = Preconditions.checkNotNull(element);
	}

	public E get(int index) {
		Preconditions.checkElementIndex(index, 1);
		return this.element;
	}

	@Override
	public UnmodifiableIterator<E> iterator() {
		return Iterators.singletonIterator(this.element);
	}

	@Override
	public Spliterator<E> spliterator() {
		return Collections.singleton(this.element).spliterator();
	}

	public int size() {
		return 1;
	}

	@Override
	public ImmutableList<E> subList(int fromIndex, int toIndex) {
		Preconditions.checkPositionIndexes(fromIndex, toIndex, 1);
		return (ImmutableList<E>)(fromIndex == toIndex ? ImmutableList.of() : this);
	}

	public String toString() {
		return '[' + this.element.toString() + ']';
	}

	@Override
	boolean isPartialView() {
		return false;
	}
}
