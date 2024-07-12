package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.Multisets.UnmodifiableMultiset;
import java.util.Comparator;
import java.util.NavigableSet;

@GwtCompatible(
	emulated = true
)
final class UnmodifiableSortedMultiset<E> extends UnmodifiableMultiset<E> implements SortedMultiset<E> {
	private transient UnmodifiableSortedMultiset<E> descendingMultiset;
	private static final long serialVersionUID = 0L;

	UnmodifiableSortedMultiset(SortedMultiset<E> delegate) {
		super(delegate);
	}

	protected SortedMultiset<E> delegate() {
		return (SortedMultiset<E>)super.delegate();
	}

	@Override
	public Comparator<? super E> comparator() {
		return this.delegate().comparator();
	}

	NavigableSet<E> createElementSet() {
		return Sets.unmodifiableNavigableSet(this.delegate().elementSet());
	}

	@Override
	public NavigableSet<E> elementSet() {
		return (NavigableSet<E>)super.elementSet();
	}

	@Override
	public SortedMultiset<E> descendingMultiset() {
		UnmodifiableSortedMultiset<E> result = this.descendingMultiset;
		if (result == null) {
			result = new UnmodifiableSortedMultiset<>(this.delegate().descendingMultiset());
			result.descendingMultiset = this;
			return this.descendingMultiset = result;
		} else {
			return result;
		}
	}

	@Override
	public Entry<E> firstEntry() {
		return this.delegate().firstEntry();
	}

	@Override
	public Entry<E> lastEntry() {
		return this.delegate().lastEntry();
	}

	@Override
	public Entry<E> pollFirstEntry() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Entry<E> pollLastEntry() {
		throw new UnsupportedOperationException();
	}

	@Override
	public SortedMultiset<E> headMultiset(E upperBound, BoundType boundType) {
		return Multisets.unmodifiableSortedMultiset(this.delegate().headMultiset(upperBound, boundType));
	}

	@Override
	public SortedMultiset<E> subMultiset(E lowerBound, BoundType lowerBoundType, E upperBound, BoundType upperBoundType) {
		return Multisets.unmodifiableSortedMultiset(this.delegate().subMultiset(lowerBound, lowerBoundType, upperBound, upperBoundType));
	}

	@Override
	public SortedMultiset<E> tailMultiset(E lowerBound, BoundType boundType) {
		return Multisets.unmodifiableSortedMultiset(this.delegate().tailMultiset(lowerBound, boundType));
	}
}
