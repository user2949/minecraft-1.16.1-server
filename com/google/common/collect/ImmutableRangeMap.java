package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.SortedLists.KeyAbsentBehavior;
import com.google.common.collect.SortedLists.KeyPresentBehavior;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@Beta
@GwtIncompatible
public class ImmutableRangeMap<K extends Comparable<?>, V> implements RangeMap<K, V>, Serializable {
	private static final ImmutableRangeMap<Comparable<?>, Object> EMPTY = new ImmutableRangeMap<>(ImmutableList.of(), ImmutableList.of());
	private final transient ImmutableList<Range<K>> ranges;
	private final transient ImmutableList<V> values;
	private static final long serialVersionUID = 0L;

	public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of() {
		return (ImmutableRangeMap<K, V>)EMPTY;
	}

	public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of(Range<K> range, V value) {
		return new ImmutableRangeMap<>(ImmutableList.of(range), ImmutableList.of(value));
	}

	public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> copyOf(RangeMap<K, ? extends V> rangeMap) {
		if (rangeMap instanceof ImmutableRangeMap) {
			return (ImmutableRangeMap<K, V>)rangeMap;
		} else {
			Map<Range<K>, ? extends V> map = rangeMap.asMapOfRanges();
			ImmutableList.Builder<Range<K>> rangesBuilder = new ImmutableList.Builder<>(map.size());
			ImmutableList.Builder<V> valuesBuilder = new ImmutableList.Builder<>(map.size());

			for (Entry<Range<K>, ? extends V> entry : map.entrySet()) {
				rangesBuilder.add((Range<K>)entry.getKey());
				valuesBuilder.add((V)entry.getValue());
			}

			return new ImmutableRangeMap<>(rangesBuilder.build(), valuesBuilder.build());
		}
	}

	public static <K extends Comparable<?>, V> ImmutableRangeMap.Builder<K, V> builder() {
		return new ImmutableRangeMap.Builder<>();
	}

	ImmutableRangeMap(ImmutableList<Range<K>> ranges, ImmutableList<V> values) {
		this.ranges = ranges;
		this.values = values;
	}

	@Nullable
	@Override
	public V get(K key) {
		int index = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(key), KeyPresentBehavior.ANY_PRESENT, KeyAbsentBehavior.NEXT_LOWER);
		if (index == -1) {
			return null;
		} else {
			Range<K> range = (Range<K>)this.ranges.get(index);
			return (V)(range.contains(key) ? this.values.get(index) : null);
		}
	}

	@Nullable
	@Override
	public Entry<Range<K>, V> getEntry(K key) {
		int index = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(key), KeyPresentBehavior.ANY_PRESENT, KeyAbsentBehavior.NEXT_LOWER);
		if (index == -1) {
			return null;
		} else {
			Range<K> range = (Range<K>)this.ranges.get(index);
			return range.contains(key) ? Maps.immutableEntry(range, (V)this.values.get(index)) : null;
		}
	}

	@Override
	public Range<K> span() {
		if (this.ranges.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			Range<K> firstRange = (Range<K>)this.ranges.get(0);
			Range<K> lastRange = (Range<K>)this.ranges.get(this.ranges.size() - 1);
			return Range.create(firstRange.lowerBound, lastRange.upperBound);
		}
	}

	@Deprecated
	@Override
	public void put(Range<K> range, V value) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	@Override
	public void putAll(RangeMap<K, V> rangeMap) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	@Override
	public void remove(Range<K> range) {
		throw new UnsupportedOperationException();
	}

	public ImmutableMap<Range<K>, V> asMapOfRanges() {
		if (this.ranges.isEmpty()) {
			return ImmutableMap.of();
		} else {
			RegularImmutableSortedSet<Range<K>> rangeSet = new RegularImmutableSortedSet<>(this.ranges, Range.RANGE_LEX_ORDERING);
			return new ImmutableSortedMap<>(rangeSet, this.values);
		}
	}

	public ImmutableMap<Range<K>, V> asDescendingMapOfRanges() {
		if (this.ranges.isEmpty()) {
			return ImmutableMap.of();
		} else {
			RegularImmutableSortedSet<Range<K>> rangeSet = new RegularImmutableSortedSet<>(this.ranges.reverse(), Range.RANGE_LEX_ORDERING.reverse());
			return new ImmutableSortedMap<>(rangeSet, this.values.reverse());
		}
	}

	public ImmutableRangeMap<K, V> subRangeMap(Range<K> range) {
		if (Preconditions.checkNotNull(range).isEmpty()) {
			return of();
		} else if (!this.ranges.isEmpty() && !range.encloses(this.span())) {
			final int lowerIndex = SortedLists.binarySearch(
				this.ranges, Range.upperBoundFn(), range.lowerBound, KeyPresentBehavior.FIRST_AFTER, KeyAbsentBehavior.NEXT_HIGHER
			);
			int upperIndex = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), range.upperBound, KeyPresentBehavior.ANY_PRESENT, KeyAbsentBehavior.NEXT_HIGHER);
			if (lowerIndex >= upperIndex) {
				return of();
			} else {
				final int len = upperIndex - lowerIndex;
				ImmutableList<Range<K>> subRanges = new ImmutableList<Range<K>>() {
					public int size() {
						return len;
					}

					public Range<K> get(int index) {
						Preconditions.checkElementIndex(index, len);
						return index != 0 && index != len - 1
							? (Range)ImmutableRangeMap.this.ranges.get(index + lowerIndex)
							: ((Range)ImmutableRangeMap.this.ranges.get(index + lowerIndex)).intersection(range);
					}

					@Override
					boolean isPartialView() {
						return true;
					}
				};
				final ImmutableRangeMap<K, V> outer = this;
				return new ImmutableRangeMap<K, V>(subRanges, this.values.subList(lowerIndex, upperIndex)) {
					@Override
					public ImmutableRangeMap<K, V> subRangeMap(Range<K> subRange) {
						return range.isConnected(subRange) ? outer.subRangeMap(subRange.intersection(range)) : ImmutableRangeMap.of();
					}
				};
			}
		} else {
			return this;
		}
	}

	@Override
	public int hashCode() {
		return this.asMapOfRanges().hashCode();
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
	public String toString() {
		return this.asMapOfRanges().toString();
	}

	Object writeReplace() {
		return new ImmutableRangeMap.SerializedForm<>(this.asMapOfRanges());
	}

	public static final class Builder<K extends Comparable<?>, V> {
		private final List<Entry<Range<K>, V>> entries = Lists.<Entry<Range<K>, V>>newArrayList();

		@CanIgnoreReturnValue
		public ImmutableRangeMap.Builder<K, V> put(Range<K> range, V value) {
			Preconditions.checkNotNull(range);
			Preconditions.checkNotNull(value);
			Preconditions.checkArgument(!range.isEmpty(), "Range must not be empty, but was %s", range);
			this.entries.add(Maps.immutableEntry(range, value));
			return this;
		}

		@CanIgnoreReturnValue
		public ImmutableRangeMap.Builder<K, V> putAll(RangeMap<K, ? extends V> rangeMap) {
			for (Entry<Range<K>, ? extends V> entry : rangeMap.asMapOfRanges().entrySet()) {
				this.put((Range<K>)entry.getKey(), (V)entry.getValue());
			}

			return this;
		}

		public ImmutableRangeMap<K, V> build() {
			Collections.sort(this.entries, Range.RANGE_LEX_ORDERING.onKeys());
			ImmutableList.Builder<Range<K>> rangesBuilder = new ImmutableList.Builder<>(this.entries.size());
			ImmutableList.Builder<V> valuesBuilder = new ImmutableList.Builder<>(this.entries.size());

			for (int i = 0; i < this.entries.size(); i++) {
				Range<K> range = (Range<K>)((Entry)this.entries.get(i)).getKey();
				if (i > 0) {
					Range<K> prevRange = (Range<K>)((Entry)this.entries.get(i - 1)).getKey();
					if (range.isConnected(prevRange) && !range.intersection(prevRange).isEmpty()) {
						throw new IllegalArgumentException("Overlapping ranges: range " + prevRange + " overlaps with entry " + range);
					}
				}

				rangesBuilder.add(range);
				valuesBuilder.add((V)((Entry)this.entries.get(i)).getValue());
			}

			return new ImmutableRangeMap<>(rangesBuilder.build(), valuesBuilder.build());
		}
	}

	private static class SerializedForm<K extends Comparable<?>, V> implements Serializable {
		private final ImmutableMap<Range<K>, V> mapOfRanges;
		private static final long serialVersionUID = 0L;

		SerializedForm(ImmutableMap<Range<K>, V> mapOfRanges) {
			this.mapOfRanges = mapOfRanges;
		}

		Object readResolve() {
			return this.mapOfRanges.isEmpty() ? ImmutableRangeMap.of() : this.createRangeMap();
		}

		Object createRangeMap() {
			ImmutableRangeMap.Builder<K, V> builder = new ImmutableRangeMap.Builder<>();

			for (Entry<Range<K>, V> entry : this.mapOfRanges.entrySet()) {
				builder.put((Range<K>)entry.getKey(), (V)entry.getValue());
			}

			return builder.build();
		}
	}
}
