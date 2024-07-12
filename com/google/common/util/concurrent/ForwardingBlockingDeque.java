package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.ForwardingDeque;
import java.util.Collection;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

@GwtIncompatible
public abstract class ForwardingBlockingDeque<E> extends ForwardingDeque<E> implements BlockingDeque<E> {
	protected ForwardingBlockingDeque() {
	}

	protected abstract BlockingDeque<E> delegate();

	public int remainingCapacity() {
		return this.delegate().remainingCapacity();
	}

	public void putFirst(E e) throws InterruptedException {
		this.delegate().putFirst(e);
	}

	public void putLast(E e) throws InterruptedException {
		this.delegate().putLast(e);
	}

	public boolean offerFirst(E e, long timeout, TimeUnit unit) throws InterruptedException {
		return this.delegate().offerFirst(e, timeout, unit);
	}

	public boolean offerLast(E e, long timeout, TimeUnit unit) throws InterruptedException {
		return this.delegate().offerLast(e, timeout, unit);
	}

	public E takeFirst() throws InterruptedException {
		return (E)this.delegate().takeFirst();
	}

	public E takeLast() throws InterruptedException {
		return (E)this.delegate().takeLast();
	}

	public E pollFirst(long timeout, TimeUnit unit) throws InterruptedException {
		return (E)this.delegate().pollFirst(timeout, unit);
	}

	public E pollLast(long timeout, TimeUnit unit) throws InterruptedException {
		return (E)this.delegate().pollLast(timeout, unit);
	}

	public void put(E e) throws InterruptedException {
		this.delegate().put(e);
	}

	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		return this.delegate().offer(e, timeout, unit);
	}

	public E take() throws InterruptedException {
		return (E)this.delegate().take();
	}

	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		return (E)this.delegate().poll(timeout, unit);
	}

	public int drainTo(Collection<? super E> c) {
		return this.delegate().drainTo(c);
	}

	public int drainTo(Collection<? super E> c, int maxElements) {
		return this.delegate().drainTo(c, maxElements);
	}
}
