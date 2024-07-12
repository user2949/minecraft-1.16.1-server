package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Maps.EntrySet;
import com.google.common.collect.Maps.IteratorBasedAbstractMap;
import com.google.common.collect.Maps.KeySet;
import com.google.common.collect.Maps.Values;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@Beta
@GwtIncompatible
public final class TreeRangeMap<K extends Comparable, V> implements RangeMap<K, V> {
	private final NavigableMap<Cut<K>, TreeRangeMap.RangeMapEntry<K, V>> entriesByLowerBound = Maps.<Cut<K>, TreeRangeMap.RangeMapEntry<K, V>>newTreeMap();
	private static final RangeMap EMPTY_SUB_RANGE_MAP = new RangeMap() {
		@Nullable
		@Override
		public Object get(Comparable key) {
			return null;
		}

		@Nullable
		@Override
		public Entry<Range, Object> getEntry(Comparable key) {
			return null;
		}

		@Override
		public Range span() {
			throw new NoSuchElementException();
		}

		@Override
		public void put(Range range, Object value) {
			Preconditions.checkNotNull(range);
			throw new IllegalArgumentException("Cannot insert range " + range + " into an empty subRangeMap");
		}

		@Override
		public void putAll(RangeMap rangeMap) {
			if (!rangeMap.asMapOfRanges().isEmpty()) {
				throw new IllegalArgumentException("Cannot putAll(nonEmptyRangeMap) into an empty subRangeMap");
			}
		}

		@Override
		public void clear() {
		}

		@Override
		public void remove(Range range) {
			Preconditions.checkNotNull(range);
		}

		@Override
		public Map<Range, Object> asMapOfRanges() {
			return Collections.emptyMap();
		}

		@Override
		public Map<Range, Object> asDescendingMapOfRanges() {
			return Collections.emptyMap();
		}

		@Override
		public RangeMap subRangeMap(Range range) {
			Preconditions.checkNotNull(range);
			return this;
		}
	};

	public static <K extends Comparable, V> TreeRangeMap<K, V> create() {
		return new TreeRangeMap<>();
	}

	private TreeRangeMap() {
	}

	@Nullable
	@Override
	public V get(K key) {
		Entry<Range<K>, V> entry = this.getEntry(key);
		return (V)(entry == null ? null : entry.getValue());
	}

	@Nullable
	@Override
	public Entry<Range<K>, V> getEntry(K key) {
		Entry<Cut<K>, TreeRangeMap.RangeMapEntry<K, V>> mapEntry = this.entriesByLowerBound.floorEntry(Cut.belowValue(key));
		return mapEntry != null && ((TreeRangeMap.RangeMapEntry)mapEntry.getValue()).contains(key) ? (Entry)mapEntry.getValue() : null;
	}

	@Override
	public void put(Range<K> range, V value) {
		if (!range.isEmpty()) {
			Preconditions.checkNotNull(value);
			this.remove(range);
			this.entriesByLowerBound.put(range.lowerBound, new TreeRangeMap.RangeMapEntry<>(range, value));
		}
	}

	@Override
	public void putAll(RangeMap<K, V> rangeMap) {
		for (Entry<Range<K>, V> entry : rangeMap.asMapOfRanges().entrySet()) {
			this.put((Range<K>)entry.getKey(), (V)entry.getValue());
		}
	}

	@Override
	public void clear() {
		this.entriesByLowerBound.clear();
	}

	@Override
	public Range<K> span() {
		Entry<Cut<K>, TreeRangeMap.RangeMapEntry<K, V>> firstEntry = this.entriesByLowerBound.firstEntry();
		Entry<Cut<K>, TreeRangeMap.RangeMapEntry<K, V>> lastEntry = this.entriesByLowerBound.lastEntry();
		if (firstEntry == null) {
			throw new NoSuchElementException();
		} else {
			return Range.create(
				((TreeRangeMap.RangeMapEntry)firstEntry.getValue()).getKey().lowerBound, ((TreeRangeMap.RangeMapEntry)lastEntry.getValue()).getKey().upperBound
			);
		}
	}

	private void putRangeMapEntry(Cut<K> lowerBound, Cut<K> upperBound, V value) {
		this.entriesByLowerBound.put(lowerBound, new TreeRangeMap.RangeMapEntry<>(lowerBound, upperBound, value));
	}

	@Override
	public void remove(Range<K> rangeToRemove) {
		if (!rangeToRemove.isEmpty()) {
			Entry<Cut<K>, TreeRangeMap.RangeMapEntry<K, V>> mapEntryBelowToTruncate = this.entriesByLowerBound.lowerEntry(rangeToRemove.lowerBound);
			if (mapEntryBelowToTruncate != null) {
				TreeRangeMap.RangeMapEntry<K, V> rangeMapEntry = (TreeRangeMap.RangeMapEntry<K, V>)mapEntryBelowToTruncate.getValue();
				if (rangeMapEntry.getUpperBound().compareTo(rangeToRemove.lowerBound) > 0) {
					if (rangeMapEntry.getUpperBound().compareTo(rangeToRemove.upperBound) > 0) {
						this.putRangeMapEntry(
							rangeToRemove.upperBound, rangeMapEntry.getUpperBound(), (V)((TreeRangeMap.RangeMapEntry)mapEntryBelowToTruncate.getValue()).getValue()
						);
					}

					this.putRangeMapEntry(
						rangeMapEntry.getLowerBound(), rangeToRemove.lowerBound, (V)((TreeRangeMap.RangeMapEntry)mapEntryBelowToTruncate.getValue()).getValue()
					);
				}
			}

			Entry<Cut<K>, TreeRangeMap.RangeMapEntry<K, V>> mapEntryAboveToTruncate = this.entriesByLowerBound.lowerEntry(rangeToRemove.upperBound);
			if (mapEntryAboveToTruncate != null) {
				TreeRangeMap.RangeMapEntry<K, V> rangeMapEntry = (TreeRangeMap.RangeMapEntry<K, V>)mapEntryAboveToTruncate.getValue();
				if (rangeMapEntry.getUpperBound().compareTo(rangeToRemove.upperBound) > 0) {
					this.putRangeMapEntry(
						rangeToRemove.upperBound, rangeMapEntry.getUpperBound(), (V)((TreeRangeMap.RangeMapEntry)mapEntryAboveToTruncate.getValue()).getValue()
					);
				}
			}

			this.entriesByLowerBound.subMap(rangeToRemove.lowerBound, rangeToRemove.upperBound).clear();
		}
	}

	@Override
	public Map<Range<K>, V> asMapOfRanges() {
		return new TreeRangeMap.AsMapOfRanges(this.entriesByLowerBound.values());
	}

	@Override
	public Map<Range<K>, V> asDescendingMapOfRanges() {
		return new TreeRangeMap.AsMapOfRanges(this.entriesByLowerBound.descendingMap().values());
	}

	@Override
	public RangeMap<K, V> subRangeMap(Range<K> subRange) {
		return (RangeMap<K, V>)(subRange.equals(Range.all()) ? this : new TreeRangeMap.SubRangeMap(subRange));
	}

	private RangeMap<K, V> emptySubRangeMap() {
		return EMPTY_SUB_RANGE_MAP;
	}

	@Override
	public boolean equals(@Nullable Object o) {
		if (o instanceof RangeMap) {
			RangeMap<?, ?> rangeMap = (RangeMap<?, ?>)o;
			return this.asMapOfRanges().equals(rangeMap.asMapOfRanges());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.asMapOfRanges().hashCode();
	}

	@Override
	public String toString() {
		return this.entriesByLowerBound.values().toString();
	}

	private final class AsMapOfRanges extends IteratorBasedAbstractMap<Range<K>, V> {
		final Iterable<Entry<Range<K>, V>> entryIterable;

		AsMapOfRanges(Iterable<TreeRangeMap.RangeMapEntry<K, V>> entryIterable) {
			this.entryIterable = entryIterable;
		}

		public boolean containsKey(@Nullable Object key) {
			return this.get(key) != null;
		}

		public V get(@Nullable Object key) {
			if (key instanceof Range) {
				Range<?> range = (Range<?>)key;
				TreeRangeMap.RangeMapEntry<K, V> rangeMapEntry = (TreeRangeMap.RangeMapEntry<K, V>)TreeRangeMap.this.entriesByLowerBound.get(range.lowerBound);
				if (rangeMapEntry != null && rangeMapEntry.getKey().equals(range)) {
					return rangeMapEntry.getValue();
				}
			}

			return null;
		}

		@Override
		public int size() {
			return TreeRangeMap.this.entriesByLowerBound.size();
		}

		@Override
		Iterator<Entry<Range<K>, V>> entryIterator() {
			return this.entryIterable.iterator();
		}
	}

	private static final class RangeMapEntry<K extends Comparable, V> extends AbstractMapEntry<Range<K>, V> {
		private final Range<K> range;
		private final V value;

		RangeMapEntry(Cut<K> lowerBound, Cut<K> upperBound, V value) {
			this(Range.create(lowerBound, upperBound), value);
		}

		RangeMapEntry(Range<K> range, V value) {
			this.range = range;
			this.value = value;
		}

		public Range<K> getKey() {
			return this.range;
		}

		@Override
		public V getValue() {
			return this.value;
		}

		public boolean contains(K value) {
			return this.range.contains(value);
		}

		Cut<K> getLowerBound() {
			return this.range.lowerBound;
		}

		Cut<K> getUpperBound() {
			return this.range.upperBound;
		}
	}

	private class SubRangeMap implements RangeMap<K, V> {
		private final Range<K> subRange;

		SubRangeMap(Range<K> subRange) {
			this.subRange = subRange;
		}

		@Nullable
		@Override
		public V get(K key) {
			return this.subRange.contains(key) ? TreeRangeMap.this.get(key) : null;
		}

		@Nullable
		@Override
		public Entry<Range<K>, V> getEntry(K key) {
			if (this.subRange.contains(key)) {
				Entry<Range<K>, V> entry = TreeRangeMap.this.getEntry(key);
				if (entry != null) {
					return Maps.immutableEntry(((Range)entry.getKey()).intersection(this.subRange), (V)entry.getValue());
				}
			}

			return null;
		}

		@Override
		public Range<K> span() {
			Entry<Cut<K>, TreeRangeMap.RangeMapEntry<K, V>> lowerEntry = TreeRangeMap.this.entriesByLowerBound.floorEntry(this.subRange.lowerBound);
			Cut<K> lowerBound;
			if (lowerEntry != null && ((TreeRangeMap.RangeMapEntry)lowerEntry.getValue()).getUpperBound().compareTo(this.subRange.lowerBound) > 0) {
				lowerBound = this.subRange.lowerBound;
			} else {
				lowerBound = (Cut<K>)TreeRangeMap.this.entriesByLowerBound.ceilingKey(this.subRange.lowerBound);
				if (lowerBound == null || lowerBound.compareTo(this.subRange.upperBound) >= 0) {
					throw new NoSuchElementException();
				}
			}

			Entry<Cut<K>, TreeRangeMap.RangeMapEntry<K, V>> upperEntry = TreeRangeMap.this.entriesByLowerBound.lowerEntry(this.subRange.upperBound);
			if (upperEntry == null) {
				throw new NoSuchElementException();
			} else {
				Cut<K> upperBound;
				if (((TreeRangeMap.RangeMapEntry)upperEntry.getValue()).getUpperBound().compareTo(this.subRange.upperBound) >= 0) {
					upperBound = this.subRange.upperBound;
				} else {
					upperBound = ((TreeRangeMap.RangeMapEntry)upperEntry.getValue()).getUpperBound();
				}

				return Range.create(lowerBound, upperBound);
			}
		}

		@Override
		public void put(Range<K> range, V value) {
			Preconditions.checkArgument(this.subRange.encloses(range), "Cannot put range %s into a subRangeMap(%s)", range, this.subRange);
			TreeRangeMap.this.put(range, value);
		}

		@Override
		public void putAll(RangeMap<K, V> rangeMap) {
			if (!rangeMap.asMapOfRanges().isEmpty()) {
				Range<K> span = rangeMap.span();
				Preconditions.checkArgument(this.subRange.encloses(span), "Cannot putAll rangeMap with span %s into a subRangeMap(%s)", span, this.subRange);
				TreeRangeMap.this.putAll(rangeMap);
			}
		}

		@Override
		public void clear() {
			TreeRangeMap.this.remove(this.subRange);
		}

		@Override
		public void remove(Range<K> range) {
			if (range.isConnected(this.subRange)) {
				TreeRangeMap.this.remove(range.intersection(this.subRange));
			}
		}

		@Override
		public RangeMap<K, V> subRangeMap(Range<K> range) {
			return !range.isConnected(this.subRange) ? TreeRangeMap.this.emptySubRangeMap() : TreeRangeMap.this.subRangeMap(range.intersection(this.subRange));
		}

		@Override
		public Map<Range<K>, V> asMapOfRanges() {
			return new TreeRangeMap.SubRangeMap.SubRangeMapAsMap();
		}

		@Override
		public Map<Range<K>, V> asDescendingMapOfRanges() {
			return new TreeRangeMap<K, V>.SubRangeMap.SubRangeMapAsMap() {
				@Override
				Iterator<Entry<Range<K>, V>> entryIterator() {
					if (SubRangeMap.this.subRange.isEmpty()) {
						return Iterators.emptyIterator();
					} else {
						final Iterator<TreeRangeMap.RangeMapEntry<K, V>> backingItr = TreeRangeMap.this.entriesByLowerBound
							.headMap(SubRangeMap.this.subRange.upperBound, false)
							.descendingMap()
							.values()
							.iterator();
						return new AbstractIterator<Entry<Range<K>, V>>() {
							protected Entry<Range<K>, V> computeNext() {
								if (backingItr.hasNext()) {
									TreeRangeMap.RangeMapEntry<K, V> entry = (TreeRangeMap.RangeMapEntry<K, V>)backingItr.next();
									return entry.getUpperBound().compareTo(SubRangeMap.this.subRange.lowerBound) <= 0
										? this.endOfData()
										: Maps.immutableEntry(entry.getKey().intersection(SubRangeMap.this.subRange), entry.getValue());
								} else {
									return this.endOfData();
								}
							}
						};
					}
				}
			};
		}

		@Override
		public boolean equals(@Nullable Object o) {
			if (o instanceof RangeMap) {
				RangeMap<?, ?> rangeMap = (RangeMap<?, ?>)o;
				return this.asMapOfRanges().equals(rangeMap.asMapOfRanges());
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			return this.asMapOfRanges().hashCode();
		}

		@Override
		public String toString() {
			return this.asMapOfRanges().toString();
		}

		class SubRangeMapAsMap extends AbstractMap<Range<K>, V> {
			public boolean containsKey(Object key) {
				return this.get(key) != null;
			}

			public V get(Object key) {
				try {
					if (key instanceof Range) {
						Range<K> r = (Range<K>)key;
						if (!SubRangeMap.this.subRange.encloses(r) || r.isEmpty()) {
							return null;
						}

						TreeRangeMap.RangeMapEntry<K, V> candidate = null;
						if (r.lowerBound.compareTo(SubRangeMap.this.subRange.lowerBound) == 0) {
							Entry<Cut<K>, TreeRangeMap.RangeMapEntry<K, V>> entry = TreeRangeMap.this.entriesByLowerBound.floorEntry(r.lowerBound);
							if (entry != null) {
								candidate = (TreeRangeMap.RangeMapEntry<K, V>)entry.getValue();
							}
						} else {
							candidate = (TreeRangeMap.RangeMapEntry<K, V>)TreeRangeMap.this.entriesByLowerBound.get(r.lowerBound);
						}

						if (candidate != null
							&& candidate.getKey().isConnected(SubRangeMap.this.subRange)
							&& candidate.getKey().intersection(SubRangeMap.this.subRange).equals(r)) {
							return candidate.getValue();
						}
					}

					return null;
				} catch (ClassCastException var5) {
					return null;
				}
			}

			public V remove(Object key) {
				V value = (V)this.get(key);
				if (value != null) {
					Range<K> range = (Range<K>)key;
					TreeRangeMap.this.remove(range);
					return value;
				} else {
					return null;
				}
			}

			public void clear() {
				SubRangeMap.this.clear();
			}

			private boolean removeEntryIf(Predicate<? super Entry<Range<K>, V>> predicate) {
				List<Range<K>> toRemove = Lists.<Range<K>>newArrayList();

				for (Entry<Range<K>, V> entry : this.entrySet()) {
					if (predicate.apply(entry)) {
						toRemove.add(entry.getKey());
					}
				}

				for (Range<K> range : toRemove) {
					TreeRangeMap.this.remove(range);
				}

				return !toRemove.isEmpty();
			}

			public Set<Range<K>> keySet() {
				return new KeySet<Range<K>, V>(this) {
					@Override
					public boolean remove(@Nullable Object o) {
						return SubRangeMapAsMap.this.remove(o) != null;
					}

					@Override
					public boolean retainAll(Collection<?> c) {
						return SubRangeMapAsMap.this.removeEntryIf(Predicates.compose(Predicates.not(Predicates.in((Collection<? extends Range<K>>)c)), Maps.keyFunction()));
					}
				};
			}

			public Set<Entry<Range<K>, V>> entrySet() {
				return new EntrySet<Range<K>, V>() {
					@Override
					Map<Range<K>, V> map() {
						return SubRangeMapAsMap.this;
					}

					public Iterator<Entry<Range<K>, V>> iterator() {
						return SubRangeMapAsMap.this.entryIterator();
					}

					@Override
					public boolean retainAll(Collection<?> c) {
						return SubRangeMapAsMap.this.removeEntryIf(Predicates.not(Predicates.in((Collection<? extends Entry<Range<K>, V>>)c)));
					}

					@Override
					public int size() {
						return Iterators.size(this.iterator());
					}

					@Override
					public boolean isEmpty() {
						return !this.iterator().hasNext();
					}
				};
			}

			Iterator<Entry<Range<K>, V>> entryIterator() {
				if (SubRangeMap.this.subRange.isEmpty()) {
					return Iterators.emptyIterator();
				} else {
					Cut<K> cutToStart = MoreObjects.firstNonNull(
						(Cut<K>)TreeRangeMap.this.entriesByLowerBound.floorKey(SubRangeMap.this.subRange.lowerBound), SubRangeMap.this.subRange.lowerBound
					);
					final Iterator<TreeRangeMap.RangeMapEntry<K, V>> backingItr = TreeRangeMap.this.entriesByLowerBound.tailMap(cutToStart, true).values().iterator();
					return new AbstractIterator<Entry<Range<K>, V>>() {
						protected Entry<Range<K>, V> computeNext() {
							while (backingItr.hasNext()) {
								TreeRangeMap.RangeMapEntry<K, V> entry = (TreeRangeMap.RangeMapEntry<K, V>)backingItr.next();
								if (entry.getLowerBound().compareTo(SubRangeMap.this.subRange.upperBound) >= 0) {
									return this.endOfData();
								}

								if (entry.getUpperBound().compareTo(SubRangeMap.this.subRange.lowerBound) > 0) {
									return Maps.immutableEntry(entry.getKey().intersection(SubRangeMap.this.subRange), entry.getValue());
								}
							}

							return this.endOfData();
						}
					};
				}
			}

			public Collection<V> values() {
				return new Values<Range<K>, V>(this) {
					@Override
					public boolean removeAll(Collection<?> c) {
						return SubRangeMapAsMap.this.removeEntryIf(Predicates.compose(Predicates.in((Collection<? extends V>)c), Maps.valueFunction()));
					}

					@Override
					public boolean retainAll(Collection<?> c) {
						return SubRangeMapAsMap.this.removeEntryIf(Predicates.compose(Predicates.not(Predicates.in((Collection<? extends V>)c)), Maps.valueFunction()));
					}
				};
			}
		}
	}
}
