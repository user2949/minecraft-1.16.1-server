package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.Serialization.FieldSetter;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

@GwtIncompatible
public final class ConcurrentHashMultiset<E> extends AbstractMultiset<E> implements Serializable {
	private final transient ConcurrentMap<E, AtomicInteger> countMap;
	private static final long serialVersionUID = 1L;

	public static <E> ConcurrentHashMultiset<E> create() {
		return new ConcurrentHashMultiset<>(new ConcurrentHashMap());
	}

	public static <E> ConcurrentHashMultiset<E> create(Iterable<? extends E> elements) {
		ConcurrentHashMultiset<E> multiset = create();
		Iterables.addAll(multiset, elements);
		return multiset;
	}

	@Beta
	public static <E> ConcurrentHashMultiset<E> create(ConcurrentMap<E, AtomicInteger> countMap) {
		return new ConcurrentHashMultiset<>(countMap);
	}

	@VisibleForTesting
	ConcurrentHashMultiset(ConcurrentMap<E, AtomicInteger> countMap) {
		Preconditions.checkArgument(countMap.isEmpty(), "the backing map (%s) must be empty", countMap);
		this.countMap = countMap;
	}

	@Override
	public int count(@Nullable Object element) {
		AtomicInteger existingCounter = Maps.safeGet(this.countMap, element);
		return existingCounter == null ? 0 : existingCounter.get();
	}

	@Override
	public int size() {
		long sum = 0L;

		for (AtomicInteger value : this.countMap.values()) {
			sum += (long)value.get();
		}

		return Ints.saturatedCast(sum);
	}

	public Object[] toArray() {
		return this.snapshot().toArray();
	}

	public <T> T[] toArray(T[] array) {
		return (T[])this.snapshot().toArray(array);
	}

	private List<E> snapshot() {
		List<E> list = Lists.<E>newArrayListWithExpectedSize(this.size());

		for (Entry<E> entry : this.entrySet()) {
			E element = entry.getElement();

			for (int i = entry.getCount(); i > 0; i--) {
				list.add(element);
			}
		}

		return list;
	}

	@CanIgnoreReturnValue
	@Override
	public int add(E element, int occurrences) {
		Preconditions.checkNotNull(element);
		if (occurrences == 0) {
			return this.count(element);
		} else {
			CollectPreconditions.checkPositive(occurrences, "occurences");

			while (true) {
				AtomicInteger existingCounter = Maps.safeGet(this.countMap, element);
				if (existingCounter == null) {
					existingCounter = (AtomicInteger)this.countMap.putIfAbsent(element, new AtomicInteger(occurrences));
					if (existingCounter == null) {
						return 0;
					}
				}

				while (true) {
					int oldValue = existingCounter.get();
					if (oldValue == 0) {
						AtomicInteger newCounter = new AtomicInteger(occurrences);
						if (this.countMap.putIfAbsent(element, newCounter) == null || this.countMap.replace(element, existingCounter, newCounter)) {
							return 0;
						}
						break;
					}

					try {
						int newValue = IntMath.checkedAdd(oldValue, occurrences);
						if (existingCounter.compareAndSet(oldValue, newValue)) {
							return oldValue;
						}
					} catch (ArithmeticException var6) {
						throw new IllegalArgumentException("Overflow adding " + occurrences + " occurrences to a count of " + oldValue);
					}
				}
			}
		}
	}

	@CanIgnoreReturnValue
	@Override
	public int remove(@Nullable Object element, int occurrences) {
		if (occurrences == 0) {
			return this.count(element);
		} else {
			CollectPreconditions.checkPositive(occurrences, "occurences");
			AtomicInteger existingCounter = Maps.safeGet(this.countMap, element);
			if (existingCounter == null) {
				return 0;
			} else {
				int oldValue;
				int newValue;
				do {
					oldValue = existingCounter.get();
					if (oldValue == 0) {
						return 0;
					}

					newValue = Math.max(0, oldValue - occurrences);
				} while (!existingCounter.compareAndSet(oldValue, newValue));

				if (newValue == 0) {
					this.countMap.remove(element, existingCounter);
				}

				return oldValue;
			}
		}
	}

	@CanIgnoreReturnValue
	public boolean removeExactly(@Nullable Object element, int occurrences) {
		if (occurrences == 0) {
			return true;
		} else {
			CollectPreconditions.checkPositive(occurrences, "occurences");
			AtomicInteger existingCounter = Maps.safeGet(this.countMap, element);
			if (existingCounter == null) {
				return false;
			} else {
				int oldValue;
				int newValue;
				do {
					oldValue = existingCounter.get();
					if (oldValue < occurrences) {
						return false;
					}

					newValue = oldValue - occurrences;
				} while (!existingCounter.compareAndSet(oldValue, newValue));

				if (newValue == 0) {
					this.countMap.remove(element, existingCounter);
				}

				return true;
			}
		}
	}

	@CanIgnoreReturnValue
	@Override
	public int setCount(E element, int count) {
		Preconditions.checkNotNull(element);
		CollectPreconditions.checkNonnegative(count, "count");

		label40:
		while (true) {
			AtomicInteger existingCounter = Maps.safeGet(this.countMap, element);
			if (existingCounter == null) {
				if (count == 0) {
					return 0;
				}

				existingCounter = (AtomicInteger)this.countMap.putIfAbsent(element, new AtomicInteger(count));
				if (existingCounter == null) {
					return 0;
				}
			}

			int oldValue;
			do {
				oldValue = existingCounter.get();
				if (oldValue == 0) {
					if (count == 0) {
						return 0;
					}

					AtomicInteger newCounter = new AtomicInteger(count);
					if (this.countMap.putIfAbsent(element, newCounter) == null || this.countMap.replace(element, existingCounter, newCounter)) {
						return 0;
					}
					continue label40;
				}
			} while (!existingCounter.compareAndSet(oldValue, count));

			if (count == 0) {
				this.countMap.remove(element, existingCounter);
			}

			return oldValue;
		}
	}

	@CanIgnoreReturnValue
	@Override
	public boolean setCount(E element, int expectedOldCount, int newCount) {
		Preconditions.checkNotNull(element);
		CollectPreconditions.checkNonnegative(expectedOldCount, "oldCount");
		CollectPreconditions.checkNonnegative(newCount, "newCount");
		AtomicInteger existingCounter = Maps.safeGet(this.countMap, element);
		if (existingCounter == null) {
			if (expectedOldCount != 0) {
				return false;
			} else {
				return newCount == 0 ? true : this.countMap.putIfAbsent(element, new AtomicInteger(newCount)) == null;
			}
		} else {
			int oldValue = existingCounter.get();
			if (oldValue == expectedOldCount) {
				if (oldValue == 0) {
					if (newCount == 0) {
						this.countMap.remove(element, existingCounter);
						return true;
					}

					AtomicInteger newCounter = new AtomicInteger(newCount);
					return this.countMap.putIfAbsent(element, newCounter) == null || this.countMap.replace(element, existingCounter, newCounter);
				}

				if (existingCounter.compareAndSet(oldValue, newCount)) {
					if (newCount == 0) {
						this.countMap.remove(element, existingCounter);
					}

					return true;
				}
			}

			return false;
		}
	}

	@Override
	Set<E> createElementSet() {
		final Set<E> delegate = this.countMap.keySet();
		return new ForwardingSet<E>() {
			@Override
			protected Set<E> delegate() {
				return delegate;
			}

			@Override
			public boolean contains(@Nullable Object object) {
				return object != null && Collections2.safeContains(delegate, object);
			}

			@Override
			public boolean containsAll(Collection<?> collection) {
				return this.standardContainsAll(collection);
			}

			@Override
			public boolean remove(Object object) {
				return object != null && Collections2.safeRemove(delegate, object);
			}

			@Override
			public boolean removeAll(Collection<?> c) {
				return this.standardRemoveAll(c);
			}
		};
	}

	@Override
	public Set<Entry<E>> createEntrySet() {
		return new ConcurrentHashMultiset.EntrySet();
	}

	@Override
	int distinctElements() {
		return this.countMap.size();
	}

	@Override
	public boolean isEmpty() {
		return this.countMap.isEmpty();
	}

	@Override
	Iterator<Entry<E>> entryIterator() {
		final Iterator<Entry<E>> readOnlyIterator = new AbstractIterator<Entry<E>>() {
			private final Iterator<java.util.Map.Entry<E, AtomicInteger>> mapEntries = ConcurrentHashMultiset.this.countMap.entrySet().iterator();

			protected Entry<E> computeNext() {
				while (this.mapEntries.hasNext()) {
					java.util.Map.Entry<E, AtomicInteger> mapEntry = (java.util.Map.Entry<E, AtomicInteger>)this.mapEntries.next();
					int count = ((AtomicInteger)mapEntry.getValue()).get();
					if (count != 0) {
						return Multisets.immutableEntry((E)mapEntry.getKey(), count);
					}
				}

				return this.endOfData();
			}
		};
		return new ForwardingIterator<Entry<E>>() {
			private Entry<E> last;

			@Override
			protected Iterator<Entry<E>> delegate() {
				return readOnlyIterator;
			}

			public Entry<E> next() {
				this.last = (Entry<E>)super.next();
				return this.last;
			}

			@Override
			public void remove() {
				CollectPreconditions.checkRemove(this.last != null);
				ConcurrentHashMultiset.this.setCount(this.last.getElement(), 0);
				this.last = null;
			}
		};
	}

	@Override
	public void clear() {
		this.countMap.clear();
	}

	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		stream.writeObject(this.countMap);
	}

	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		ConcurrentMap<E, Integer> deserializedCountMap = (ConcurrentMap<E, Integer>)stream.readObject();
		ConcurrentHashMultiset.FieldSettersHolder.COUNT_MAP_FIELD_SETTER.set(this, deserializedCountMap);
	}

	private class EntrySet extends AbstractMultiset<E>.EntrySet {
		private EntrySet() {
			super(ConcurrentHashMultiset.this);
		}

		ConcurrentHashMultiset<E> multiset() {
			return ConcurrentHashMultiset.this;
		}

		public Object[] toArray() {
			return this.snapshot().toArray();
		}

		public <T> T[] toArray(T[] array) {
			return (T[])this.snapshot().toArray(array);
		}

		private List<Entry<E>> snapshot() {
			List<Entry<E>> list = Lists.<Entry<E>>newArrayListWithExpectedSize(this.size());
			Iterators.addAll(list, this.iterator());
			return list;
		}
	}

	private static class FieldSettersHolder {
		static final FieldSetter<ConcurrentHashMultiset> COUNT_MAP_FIELD_SETTER = Serialization.getFieldSetter(ConcurrentHashMultiset.class, "countMap");
	}
}
