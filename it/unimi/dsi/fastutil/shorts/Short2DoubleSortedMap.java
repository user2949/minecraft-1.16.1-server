package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2DoubleMap.Entry;
import it.unimi.dsi.fastutil.shorts.Short2DoubleMap.FastEntrySet;
import java.util.SortedMap;

public interface Short2DoubleSortedMap extends Short2DoubleMap, SortedMap<Short, Double> {
	Short2DoubleSortedMap subMap(short short1, short short2);

	Short2DoubleSortedMap headMap(short short1);

	Short2DoubleSortedMap tailMap(short short1);

	short firstShortKey();

	short lastShortKey();

	@Deprecated
	default Short2DoubleSortedMap subMap(Short from, Short to) {
		return this.subMap(from.shortValue(), to.shortValue());
	}

	@Deprecated
	default Short2DoubleSortedMap headMap(Short to) {
		return this.headMap(to.shortValue());
	}

	@Deprecated
	default Short2DoubleSortedMap tailMap(Short from) {
		return this.tailMap(from.shortValue());
	}

	@Deprecated
	default Short firstKey() {
		return this.firstShortKey();
	}

	@Deprecated
	default Short lastKey() {
		return this.lastShortKey();
	}

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<Short, Double>> entrySet() {
		return this.short2DoubleEntrySet();
	}

	ObjectSortedSet<Entry> short2DoubleEntrySet();

	ShortSortedSet keySet();

	@Override
	DoubleCollection values();

	ShortComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
