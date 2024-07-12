package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Iterator;
import java.util.ListIterator;

@GwtCompatible
abstract class TransformedListIterator<F, T> extends TransformedIterator<F, T> implements ListIterator<T> {
	TransformedListIterator(ListIterator<? extends F> backingIterator) {
		super(backingIterator);
	}

	private ListIterator<? extends F> backingIterator() {
		return Iterators.cast((Iterator<T>)this.backingIterator);
	}

	public final boolean hasPrevious() {
		return this.backingIterator().hasPrevious();
	}

	public final T previous() {
		return this.transform((F)this.backingIterator().previous());
	}

	public final int nextIndex() {
		return this.backingIterator().nextIndex();
	}

	public final int previousIndex() {
		return this.backingIterator().previousIndex();
	}

	public void set(T element) {
		throw new UnsupportedOperationException();
	}

	public void add(T element) {
		throw new UnsupportedOperationException();
	}
}
