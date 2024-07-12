package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Deque;
import java.util.Iterator;

@GwtIncompatible
public abstract class ForwardingDeque<E> extends ForwardingQueue<E> implements Deque<E> {
	protected ForwardingDeque() {
	}

	protected abstract Deque<E> delegate();

	public void addFirst(E e) {
		this.delegate().addFirst(e);
	}

	public void addLast(E e) {
		this.delegate().addLast(e);
	}

	public Iterator<E> descendingIterator() {
		return this.delegate().descendingIterator();
	}

	public E getFirst() {
		return (E)this.delegate().getFirst();
	}

	public E getLast() {
		return (E)this.delegate().getLast();
	}

	@CanIgnoreReturnValue
	public boolean offerFirst(E e) {
		return this.delegate().offerFirst(e);
	}

	@CanIgnoreReturnValue
	public boolean offerLast(E e) {
		return this.delegate().offerLast(e);
	}

	public E peekFirst() {
		return (E)this.delegate().peekFirst();
	}

	public E peekLast() {
		return (E)this.delegate().peekLast();
	}

	@CanIgnoreReturnValue
	public E pollFirst() {
		return (E)this.delegate().pollFirst();
	}

	@CanIgnoreReturnValue
	public E pollLast() {
		return (E)this.delegate().pollLast();
	}

	@CanIgnoreReturnValue
	public E pop() {
		return (E)this.delegate().pop();
	}

	public void push(E e) {
		this.delegate().push(e);
	}

	@CanIgnoreReturnValue
	public E removeFirst() {
		return (E)this.delegate().removeFirst();
	}

	@CanIgnoreReturnValue
	public E removeLast() {
		return (E)this.delegate().removeLast();
	}

	@CanIgnoreReturnValue
	public boolean removeFirstOccurrence(Object o) {
		return this.delegate().removeFirstOccurrence(o);
	}

	@CanIgnoreReturnValue
	public boolean removeLastOccurrence(Object o) {
		return this.delegate().removeLastOccurrence(o);
	}
}
