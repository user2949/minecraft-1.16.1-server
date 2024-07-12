package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2LongMap.Entry;
import it.unimi.dsi.fastutil.shorts.Short2LongMap.FastEntrySet;
import java.util.SortedMap;

public interface Short2LongSortedMap extends Short2LongMap, SortedMap<Short, Long> {
	Short2LongSortedMap subMap(short short1, short short2);

	Short2LongSortedMap headMap(short short1);

	Short2LongSortedMap tailMap(short short1);

	short firstShortKey();

	short lastShortKey();

	@Deprecated
	default Short2LongSortedMap subMap(Short from, Short to) {
		return this.subMap(from.shortValue(), to.shortValue());
	}

	@Deprecated
	default Short2LongSortedMap headMap(Short to) {
		return this.headMap(to.shortValue());
	}

	@Deprecated
	default Short2LongSortedMap tailMap(Short from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Short, Long>> entrySet() {
		return this.short2LongEntrySet();
	}

	ObjectSortedSet<Entry> short2LongEntrySet();

	ShortSortedSet keySet();

	@Override
	LongCollection values();

	ShortComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
