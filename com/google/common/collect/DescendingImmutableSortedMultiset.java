package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Multiset.Entry;
import javax.annotation.Nullable;

@GwtIncompatible
final class DescendingImmutableSortedMultiset<E> extends ImmutableSortedMultiset<E> {
	private final transient ImmutableSortedMultiset<E> forward;

	DescendingImmutableSortedMultiset(ImmutableSortedMultiset<E> forward) {
		this.forward = forward;
	}

	@Override
	public int count(@Nullable Object element) {
		return this.forward.count(element);
	}

	@Override
	public Entry<E> firstEntry() {
		return this.forward.lastEntry();
	}

	@Override
	public Entry<E> lastEntry() {
		return this.forward.firstEntry();
	}

	@Override
	public int size() {
		return this.forward.size();
	}

	@Override
	public ImmutableSortedSet<E> elementSet() {
		return this.forward.elementSet().descendingSet();
	}

	@Override
	Entry<E> getEntry(int index) {
		return (Entry<E>)this.forward.entrySet().asList().reverse().get(index);
	}

	@Override
	public ImmutableSortedMultiset<E> descendingMultiset() {
		return this.forward;
	}

	@Override
	public ImmutableSortedMultiset<E> headMultiset(E upperBound, BoundType boundType) {
		return this.forward.tailMultiset(upperBound, boundType).descendingMultiset();
	}

	@Override
	public ImmutableSortedMultiset<E> tailMultiset(E lowerBound, BoundType boundType) {
		return this.forward.headMultiset(lowerBound, boundType).descendingMultiset();
	}

	@Override
	boolean isPartialView() {
		return this.forward.isPartialView();
	}
}
