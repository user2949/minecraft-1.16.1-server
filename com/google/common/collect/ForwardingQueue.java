package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.NoSuchElementException;
import java.util.Queue;

@GwtCompatible
public abstract class ForwardingQueue<E> extends ForwardingCollection<E> implements Queue<E> {
	protected ForwardingQueue() {
	}

	protected abstract Queue<E> delegate();

	@CanIgnoreReturnValue
	public boolean offer(E o) {
		return this.delegate().offer(o);
	}

	@CanIgnoreReturnValue
	public E poll() {
		return (E)this.delegate().poll();
	}

	@CanIgnoreReturnValue
	public E remove() {
		return (E)this.delegate().remove();
	}

	public E peek() {
		return (E)this.delegate().peek();
	}

	public E element() {
		return (E)this.delegate().element();
	}

	protected boolean standardOffer(E e) {
		try {
			return this.add(e);
		} catch (IllegalStateException var3) {
			return false;
		}
	}

	protected E standardPeek() {
		try {
			return this.element();
		} catch (NoSuchElementException var2) {
			return null;
		}
	}

	protected E standardPoll() {
		try {
			return this.remove();
		} catch (NoSuchElementException var2) {
			return null;
		}
	}
}
