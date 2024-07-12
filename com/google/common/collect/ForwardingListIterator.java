package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ListIterator;

@GwtCompatible
public abstract class ForwardingListIterator<E> extends ForwardingIterator<E> implements ListIterator<E> {
	protected ForwardingListIterator() {
	}

	protected abstract ListIterator<E> delegate();

	public void add(E element) {
		this.delegate().add(element);
	}

	public boolean hasPrevious() {
		return this.delegate().hasPrevious();
	}

	public int nextIndex() {
		return this.delegate().nextIndex();
	}

	@CanIgnoreReturnValue
	public E previous() {
		return (E)this.delegate().previous();
	}

	public int previousIndex() {
		return this.delegate().previousIndex();
	}

	public void set(E element) {
		this.delegate().set(element);
	}
}
