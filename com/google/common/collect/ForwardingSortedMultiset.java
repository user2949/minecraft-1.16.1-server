package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.SortedMultisets.NavigableElementSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;

@Beta
@GwtCompatible(
	emulated = true
)
public abstract class ForwardingSortedMultiset<E> extends ForwardingMultiset<E> implements SortedMultiset<E> {
	protected ForwardingSortedMultiset() {
	}

	protected abstract SortedMultiset<E> delegate();

	@Override
	public NavigableSet<E> elementSet() {
		return this.delegate().elementSet();
	}

	@Override
	public Comparator<? super E> comparator() {
		return this.delegate().comparator();
	}

	@Override
	public SortedMultiset<E> descendingMultiset() {
		return this.delegate().descendingMultiset();
	}

	@Override
	public Entry<E> firstEntry() {
		return this.delegate().firstEntry();
	}

	protected Entry<E> standardFirstEntry() {
		Iterator<Entry<E>> entryIterator = this.entrySet().iterator();
		if (!entryIterator.hasNext()) {
			return null;
		} else {
			Entry<E> entry = (Entry<E>)entryIterator.next();
			return Multisets.immutableEntry(entry.getElement(), entry.getCount());
		}
	}

	@Override
	public Entry<E> lastEntry() {
		return this.delegate().lastEntry();
	}

	protected Entry<E> standardLastEntry() {
		Iterator<Entry<E>> entryIterator = this.descendingMultiset().entrySet().iterator();
		if (!entryIterator.hasNext()) {
			return null;
		} else {
			Entry<E> entry = (Entry<E>)entryIterator.next();
			return Multisets.immutableEntry(entry.getElement(), entry.getCount());
		}
	}

	@Override
	public Entry<E> pollFirstEntry() {
		return this.delegate().pollFirstEntry();
	}

	protected Entry<E> standardPollFirstEntry() {
		Iterator<Entry<E>> entryIterator = this.entrySet().iterator();
		if (!entryIterator.hasNext()) {
			return null;
		} else {
			Entry<E> entry = (Entry<E>)entryIterator.next();
			entry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
			entryIterator.remove();
			return entry;
		}
	}

	@Override
	public Entry<E> pollLastEntry() {
		return this.delegate().pollLastEntry();
	}

	protected Entry<E> standardPollLastEntry() {
		Iterator<Entry<E>> entryIterator = this.descendingMultiset().entrySet().iterator();
		if (!entryIterator.hasNext()) {
			return null;
		} else {
			Entry<E> entry = (Entry<E>)entryIterator.next();
			entry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
			entryIterator.remove();
			return entry;
		}
	}

	@Override
	public SortedMultiset<E> headMultiset(E upperBound, BoundType boundType) {
		return this.delegate().headMultiset(upperBound, boundType);
	}

	@Override
	public SortedMultiset<E> subMultiset(E lowerBound, BoundType lowerBoundType, E upperBound, BoundType upperBoundType) {
		return this.delegate().subMultiset(lowerBound, lowerBoundType, upperBound, upperBoundType);
	}

	protected SortedMultiset<E> standardSubMultiset(E lowerBound, BoundType lowerBoundType, E upperBound, BoundType upperBoundType) {
		return this.tailMultiset(lowerBound, lowerBoundType).headMultiset(upperBound, upperBoundType);
	}

	@Override
	public SortedMultiset<E> tailMultiset(E lowerBound, BoundType boundType) {
		return this.delegate().tailMultiset(lowerBound, boundType);
	}

	protected abstract class StandardDescendingMultiset extends DescendingMultiset<E> {
		public StandardDescendingMultiset() {
		}

		@Override
		SortedMultiset<E> forwardMultiset() {
			return ForwardingSortedMultiset.this;
		}
	}

	protected class StandardElementSet extends NavigableElementSet<E> {
		public StandardElementSet() {
			super(ForwardingSortedMultiset.this);
		}
	}
}
