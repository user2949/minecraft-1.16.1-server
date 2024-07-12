package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multisets.AbstractEntry;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.ObjIntConsumer;
import javax.annotation.Nullable;

@GwtCompatible(
	emulated = true
)
abstract class AbstractMapBasedMultiset<E> extends AbstractMultiset<E> implements Serializable {
	private transient Map<E, Count> backingMap;
	private transient long size;
	@GwtIncompatible
	private static final long serialVersionUID = -2250766705698539974L;

	protected AbstractMapBasedMultiset(Map<E, Count> backingMap) {
		this.backingMap = Preconditions.checkNotNull(backingMap);
		this.size = (long)super.size();
	}

	void setBackingMap(Map<E, Count> backingMap) {
		this.backingMap = backingMap;
	}

	@Override
	public Set<com.google.common.collect.Multiset.Entry<E>> entrySet() {
		return super.entrySet();
	}

	@Override
	Iterator<com.google.common.collect.Multiset.Entry<E>> entryIterator() {
		final Iterator<java.util.Map.Entry<E, Count>> backingEntries = this.backingMap.entrySet().iterator();
		return new Iterator<com.google.common.collect.Multiset.Entry<E>>() {
			java.util.Map.Entry<E, Count> toRemove;

			public boolean hasNext() {
				return backingEntries.hasNext();
			}

			public com.google.common.collect.Multiset.Entry<E> next() {
				final java.util.Map.Entry<E, Count> mapEntry = (java.util.Map.Entry<E, Count>)backingEntries.next();
				this.toRemove = mapEntry;
				return new AbstractEntry<E>() {
					@Override
					public E getElement() {
						return (E)mapEntry.getKey();
					}

					@Override
					public int getCount() {
						Count count = (Count)mapEntry.getValue();
						if (count == null || count.get() == 0) {
							Count frequency = (Count)AbstractMapBasedMultiset.this.backingMap.get(this.getElement());
							if (frequency != null) {
								return frequency.get();
							}
						}

						return count == null ? 0 : count.get();
					}
				};
			}

			public void remove() {
				CollectPreconditions.checkRemove(this.toRemove != null);
				AbstractMapBasedMultiset.this.size = AbstractMapBasedMultiset.this.size - (long)((Count)this.toRemove.getValue()).getAndSet(0);
				backingEntries.remove();
				this.toRemove = null;
			}
		};
	}

	@Override
	public void forEachEntry(ObjIntConsumer<? super E> action) {
		Preconditions.checkNotNull(action);
		this.backingMap.forEach((element, count) -> action.accept(element, count.get()));
	}

	@Override
	public void clear() {
		for (Count frequency : this.backingMap.values()) {
			frequency.set(0);
		}

		this.backingMap.clear();
		this.size = 0L;
	}

	@Override
	int distinctElements() {
		return this.backingMap.size();
	}

	@Override
	public int size() {
		return Ints.saturatedCast(this.size);
	}

	@Override
	public Iterator<E> iterator() {
		return new AbstractMapBasedMultiset.MapBasedMultisetIterator();
	}

	@Override
	public int count(@Nullable Object element) {
		Count frequency = Maps.safeGet(this.backingMap, element);
		return frequency == null ? 0 : frequency.get();
	}

	@CanIgnoreReturnValue
	@Override
	public int add(@Nullable E element, int occurrences) {
		if (occurrences == 0) {
			return this.count(element);
		} else {
			Preconditions.checkArgument(occurrences > 0, "occurrences cannot be negative: %s", occurrences);
			Count frequency = (Count)this.backingMap.get(element);
			int oldCount;
			if (frequency == null) {
				oldCount = 0;
				this.backingMap.put(element, new Count(occurrences));
			} else {
				oldCount = frequency.get();
				long newCount = (long)oldCount + (long)occurrences;
				Preconditions.checkArgument(newCount <= 2147483647L, "too many occurrences: %s", newCount);
				frequency.add(occurrences);
			}

			this.size += (long)occurrences;
			return oldCount;
		}
	}

	@CanIgnoreReturnValue
	@Override
	public int remove(@Nullable Object element, int occurrences) {
		if (occurrences == 0) {
			return this.count(element);
		} else {
			Preconditions.checkArgument(occurrences > 0, "occurrences cannot be negative: %s", occurrences);
			Count frequency = (Count)this.backingMap.get(element);
			if (frequency == null) {
				return 0;
			} else {
				int oldCount = frequency.get();
				int numberRemoved;
				if (oldCount > occurrences) {
					numberRemoved = occurrences;
				} else {
					numberRemoved = oldCount;
					this.backingMap.remove(element);
				}

				frequency.add(-numberRemoved);
				this.size -= (long)numberRemoved;
				return oldCount;
			}
		}
	}

	@CanIgnoreReturnValue
	@Override
	public int setCount(@Nullable E element, int count) {
		CollectPreconditions.checkNonnegative(count, "count");
		int oldCount;
		if (count == 0) {
			Count existingCounter = (Count)this.backingMap.remove(element);
			oldCount = getAndSet(existingCounter, count);
		} else {
			Count existingCounter = (Count)this.backingMap.get(element);
			oldCount = getAndSet(existingCounter, count);
			if (existingCounter == null) {
				this.backingMap.put(element, new Count(count));
			}
		}

		this.size += (long)(count - oldCount);
		return oldCount;
	}

	private static int getAndSet(@Nullable Count i, int count) {
		return i == null ? 0 : i.getAndSet(count);
	}

	@GwtIncompatible
	private void readObjectNoData() throws ObjectStreamException {
		throw new InvalidObjectException("Stream data required");
	}

	private class MapBasedMultisetIterator implements Iterator<E> {
		final Iterator<java.util.Map.Entry<E, Count>> entryIterator = AbstractMapBasedMultiset.this.backingMap.entrySet().iterator();
		java.util.Map.Entry<E, Count> currentEntry;
		int occurrencesLeft;
		boolean canRemove;

		MapBasedMultisetIterator() {
		}

		public boolean hasNext() {
			return this.occurrencesLeft > 0 || this.entryIterator.hasNext();
		}

		public E next() {
			if (this.occurrencesLeft == 0) {
				this.currentEntry = (java.util.Map.Entry<E, Count>)this.entryIterator.next();
				this.occurrencesLeft = ((Count)this.currentEntry.getValue()).get();
			}

			this.occurrencesLeft--;
			this.canRemove = true;
			return (E)this.currentEntry.getKey();
		}

		public void remove() {
			CollectPreconditions.checkRemove(this.canRemove);
			int frequency = ((Count)this.currentEntry.getValue()).get();
			if (frequency <= 0) {
				throw new ConcurrentModificationException();
			} else {
				if (((Count)this.currentEntry.getValue()).addAndGet(-1) == 0) {
					this.entryIterator.remove();
				}

				AbstractMapBasedMultiset.this.size--;
				this.canRemove = false;
			}
		}
	}
}
