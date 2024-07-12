package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.Multisets.EntrySet;
import com.google.common.collect.SortedMultisets.NavigableElementSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;

@GwtCompatible(
	emulated = true
)
abstract class DescendingMultiset<E> extends ForwardingMultiset<E> implements SortedMultiset<E> {
	private transient Comparator<? super E> comparator;
	private transient NavigableSet<E> elementSet;
	private transient Set<Entry<E>> entrySet;

	abstract SortedMultiset<E> forwardMultiset();

	@Override
	public Comparator<? super E> comparator() {
		Comparator<? super E> result = this.comparator;
		return result == null ? (this.comparator = Ordering.from(this.forwardMultiset().comparator()).reverse()) : result;
	}

	@Override
	public NavigableSet<E> elementSet() {
		NavigableSet<E> result = this.elementSet;
		return result == null ? (this.elementSet = new NavigableElementSet<>(this)) : result;
	}

	@Override
	public Entry<E> pollFirstEntry() {
		return this.forwardMultiset().pollLastEntry();
	}

	@Override
	public Entry<E> pollLastEntry() {
		return this.forwardMultiset().pollFirstEntry();
	}

	@Override
	public SortedMultiset<E> headMultiset(E toElement, BoundType boundType) {
		return this.forwardMultiset().tailMultiset(toElement, boundType).descendingMultiset();
	}

	@Override
	public SortedMultiset<E> subMultiset(E fromElement, BoundType fromBoundType, E toElement, BoundType toBoundType) {
		return this.forwardMultiset().subMultiset(toElement, toBoundType, fromElement, fromBoundType).descendingMultiset();
	}

	@Override
	public SortedMultiset<E> tailMultiset(E fromElement, BoundType boundType) {
		return this.forwardMultiset().headMultiset(fromElement, boundType).descendingMultiset();
	}

	@Override
	protected Multiset<E> delegate() {
		return this.forwardMultiset();
	}

	@Override
	public SortedMultiset<E> descendingMultiset() {
		return this.forwardMultiset();
	}

	@Override
	public Entry<E> firstEntry() {
		return this.forwardMultiset().lastEntry();
	}

	@Override
	public Entry<E> lastEntry() {
		return this.forwardMultiset().firstEntry();
	}

	abstract Iterator<Entry<E>> entryIterator();

	@Override
	public Set<Entry<E>> entrySet() {
		Set<Entry<E>> result = this.entrySet;
		return result == null ? (this.entrySet = this.createEntrySet()) : result;
	}

	Set<Entry<E>> createEntrySet() {
		class 1EntrySetImpl extends EntrySet<E> {
			@Override
			Multiset<E> multiset() {
				return DescendingMultiset.this;
			}

			public Iterator<Entry<E>> iterator() {
				return DescendingMultiset.this.entryIterator();
			}

			public int size() {
				return DescendingMultiset.this.forwardMultiset().entrySet().size();
			}
		}

		return new 1EntrySetImpl();
	}

	@Override
	public Iterator<E> iterator() {
		return Multisets.iteratorImpl(this);
	}

	@Override
	public Object[] toArray() {
		return this.standardToArray();
	}

	@Override
	public <T> T[] toArray(T[] array) {
		return (T[])this.standardToArray(array);
	}

	@Override
	public String toString() {
		return this.entrySet().toString();
	}
}
