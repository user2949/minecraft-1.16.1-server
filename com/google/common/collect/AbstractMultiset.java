package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.collect.Multiset.Entry;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractMultiset<E> extends AbstractCollection<E> implements Multiset<E> {
	private transient Set<E> elementSet;
	private transient Set<Entry<E>> entrySet;

	@Override
	public int size() {
		return Multisets.sizeImpl(this);
	}

	public boolean isEmpty() {
		return this.entrySet().isEmpty();
	}

	@Override
	public boolean contains(@Nullable Object element) {
		return this.count(element) > 0;
	}

	@Override
	public Iterator<E> iterator() {
		return Multisets.iteratorImpl(this);
	}

	@Override
	public int count(@Nullable Object element) {
		for (Entry<E> entry : this.entrySet()) {
			if (Objects.equal(entry.getElement(), element)) {
				return entry.getCount();
			}
		}

		return 0;
	}

	@CanIgnoreReturnValue
	@Override
	public boolean add(@Nullable E element) {
		this.add(element, 1);
		return true;
	}

	@CanIgnoreReturnValue
	@Override
	public int add(@Nullable E element, int occurrences) {
		throw new UnsupportedOperationException();
	}

	@CanIgnoreReturnValue
	@Override
	public boolean remove(@Nullable Object element) {
		return this.remove(element, 1) > 0;
	}

	@CanIgnoreReturnValue
	@Override
	public int remove(@Nullable Object element, int occurrences) {
		throw new UnsupportedOperationException();
	}

	@CanIgnoreReturnValue
	@Override
	public int setCount(@Nullable E element, int count) {
		return Multisets.setCountImpl(this, element, count);
	}

	@CanIgnoreReturnValue
	@Override
	public boolean setCount(@Nullable E element, int oldCount, int newCount) {
		return Multisets.setCountImpl(this, element, oldCount, newCount);
	}

	@CanIgnoreReturnValue
	public boolean addAll(Collection<? extends E> elementsToAdd) {
		return Multisets.addAllImpl(this, elementsToAdd);
	}

	@CanIgnoreReturnValue
	@Override
	public boolean removeAll(Collection<?> elementsToRemove) {
		return Multisets.removeAllImpl(this, elementsToRemove);
	}

	@CanIgnoreReturnValue
	@Override
	public boolean retainAll(Collection<?> elementsToRetain) {
		return Multisets.retainAllImpl(this, elementsToRetain);
	}

	public void clear() {
		Iterators.clear(this.entryIterator());
	}

	@Override
	public Set<E> elementSet() {
		Set<E> result = this.elementSet;
		if (result == null) {
			this.elementSet = result = this.createElementSet();
		}

		return result;
	}

	Set<E> createElementSet() {
		return new AbstractMultiset.ElementSet();
	}

	abstract Iterator<Entry<E>> entryIterator();

	abstract int distinctElements();

	@Override
	public Set<Entry<E>> entrySet() {
		Set<Entry<E>> result = this.entrySet;
		if (result == null) {
			this.entrySet = result = this.createEntrySet();
		}

		return result;
	}

	Set<Entry<E>> createEntrySet() {
		return new AbstractMultiset.EntrySet();
	}

	@Override
	public boolean equals(@Nullable Object object) {
		return Multisets.equalsImpl(this, object);
	}

	@Override
	public int hashCode() {
		return this.entrySet().hashCode();
	}

	@Override
	public String toString() {
		return this.entrySet().toString();
	}

	class ElementSet extends Multisets.ElementSet<E> {
		@Override
		Multiset<E> multiset() {
			return AbstractMultiset.this;
		}
	}

	class EntrySet extends Multisets.EntrySet<E> {
		@Override
		Multiset<E> multiset() {
			return AbstractMultiset.this;
		}

		public Iterator<Entry<E>> iterator() {
			return AbstractMultiset.this.entryIterator();
		}

		public int size() {
			return AbstractMultiset.this.distinctElements();
		}
	}
}
