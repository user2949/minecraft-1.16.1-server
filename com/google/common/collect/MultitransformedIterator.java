package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Iterator;
import java.util.NoSuchElementException;

@GwtCompatible
abstract class MultitransformedIterator<F, T> implements Iterator<T> {
	final Iterator<? extends F> backingIterator;
	private Iterator<? extends T> current = Iterators.emptyIterator();
	private Iterator<? extends T> removeFrom;

	MultitransformedIterator(Iterator<? extends F> backingIterator) {
		this.backingIterator = Preconditions.checkNotNull(backingIterator);
	}

	abstract Iterator<? extends T> transform(F object);

	public boolean hasNext() {
		Preconditions.checkNotNull(this.current);
		if (this.current.hasNext()) {
			return true;
		} else {
			while (this.backingIterator.hasNext()) {
				Preconditions.checkNotNull(this.current = this.transform((F)this.backingIterator.next()));
				if (this.current.hasNext()) {
					return true;
				}
			}

			return false;
		}
	}

	public T next() {
		if (!this.hasNext()) {
			throw new NoSuchElementException();
		} else {
			this.removeFrom = this.current;
			return (T)this.current.next();
		}
	}

	public void remove() {
		CollectPreconditions.checkRemove(this.removeFrom != null);
		this.removeFrom.remove();
		this.removeFrom = null;
	}
}
