package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@Beta
@GwtIncompatible
public interface RangeMap<K extends Comparable, V> {
	@Nullable
	V get(K comparable);

	@Nullable
	Entry<Range<K>, V> getEntry(K comparable);

	Range<K> span();

	void put(Range<K> range, V object);

	void putAll(RangeMap<K, V> rangeMap);

	void clear();

	void remove(Range<K> range);

	Map<Range<K>, V> asMapOfRanges();

	Map<Range<K>, V> asDescendingMapOfRanges();

	RangeMap<K, V> subRangeMap(Range<K> range);

	boolean equals(@Nullable Object object);

	int hashCode();

	String toString();
}
