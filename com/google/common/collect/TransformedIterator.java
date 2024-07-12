package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Iterator;

@GwtCompatible
abstract class TransformedIterator<F, T> implements Iterator<T> {
	final Iterator<? extends F> backingIterator;

	TransformedIterator(Iterator<? extends F> backingIterator) {
		this.backingIterator = Preconditions.checkNotNull(backingIterator);
	}

	abstract T transform(F object);

	public final boolean hasNext() {
		return this.backingIterator.hasNext();
	}

	public final T next() {
		return this.transform((F)this.backingIterator.next());
	}

	public final void remove() {
		this.backingIterator.remove();
	}
}
