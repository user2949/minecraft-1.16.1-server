package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.SortedMultisets.NavigableElementSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import javax.annotation.Nullable;

@GwtCompatible(
	emulated = true
)
abstract class AbstractSortedMultiset<E> extends AbstractMultiset<E> implements SortedMultiset<E> {
	@GwtTransient
	final Comparator<? super E> comparator;
	private transient SortedMultiset<E> descendingMultiset;

	AbstractSortedMultiset() {
		this(Ordering.natural());
	}

	AbstractSortedMultiset(Comparator<? super E> comparator) {
		this.comparator = Preconditions.checkNotNull(comparator);
	}

	@Override
	public NavigableSet<E> elementSet() {
		return (NavigableSet<E>)super.elementSet();
	}

	NavigableSet<E> createElementSet() {
		return new NavigableElementSet<>(this);
	}

	@Override
	public Comparator<? super E> comparator() {
		return this.comparator;
	}

	@Override
	public Entry<E> firstEntry() {
		Iterator<Entry<E>> entryIterator = this.entryIterator();
		return entryIterator.hasNext() ? (Entry)entryIterator.next() : null;
	}

	@Override
	public Entry<E> lastEntry() {
		Iterator<Entry<E>> entryIterator = this.descendingEntryIterator();
		return entryIterator.hasNext() ? (Entry)entryIterator.next() : null;
	}

	@Override
	public Entry<E> pollFirstEntry() {
		Iterator<Entry<E>> entryIterator = this.entryIterator();
		if (entryIterator.hasNext()) {
			Entry<E> result = (Entry<E>)entryIterator.next();
			result = Multisets.immutableEntry(result.getElement(), result.getCount());
			entryIterator.remove();
			return result;
		} else {
			return null;
		}
	}

	@Override
	public Entry<E> pollLastEntry() {
		Iterator<Entry<E>> entryIterator = this.descendingEntryIterator();
		if (entryIterator.hasNext()) {
			Entry<E> result = (Entry<E>)entryIterator.next();
			result = Multisets.immutableEntry(result.getElement(), result.getCount());
			entryIterator.remove();
			return result;
		} else {
			return null;
		}
	}

	@Override
	public SortedMultiset<E> subMultiset(@Nullable E fromElement, BoundType fromBoundType, @Nullable E toElement, BoundType toBoundType) {
		Preconditions.checkNotNull(fromBoundType);
		Preconditions.checkNotNull(toBoundType);
		return this.tailMultiset(fromElement, fromBoundType).headMultiset(toElement, toBoundType);
	}

	abstract Iterator<Entry<E>> descendingEntryIterator();

	Iterator<E> descendingIterator() {
		return Multisets.iteratorImpl(this.descendingMultiset());
	}

	@Override
	public SortedMultiset<E> descendingMultiset() {
		SortedMultiset<E> result = this.descendingMultiset;
		return result == null ? (this.descendingMultiset = this.createDescendingMultiset()) : result;
	}

	SortedMultiset<E> createDescendingMultiset() {
		class 1DescendingMultisetImpl extends DescendingMultiset<E> {
			@Override
			SortedMultiset<E> forwardMultiset() {
				return AbstractSortedMultiset.this;
			}

			@Override
			Iterator<Entry<E>> entryIterator() {
				return AbstractSortedMultiset.this.descendingEntryIterator();
			}

			@Override
			public Iterator<E> iterator() {
				return AbstractSortedMultiset.this.descendingIterator();
			}
		}

		return new 1DescendingMultisetImpl();
	}
}
