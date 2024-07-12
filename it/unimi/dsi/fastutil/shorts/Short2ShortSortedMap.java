package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2ShortMap.Entry;
import it.unimi.dsi.fastutil.shorts.Short2ShortMap.FastEntrySet;
import java.util.SortedMap;

public interface Short2ShortSortedMap extends Short2ShortMap, SortedMap<Short, Short> {
	Short2ShortSortedMap subMap(short short1, short short2);

	Short2ShortSortedMap headMap(short short1);

	Short2ShortSortedMap tailMap(short short1);

	short firstShortKey();

	short lastShortKey();

	@Deprecated
	default Short2ShortSortedMap subMap(Short from, Short to) {
		return this.subMap(from.shortValue(), to.shortValue());
	}

	@Deprecated
	default Short2ShortSortedMap headMap(Short to) {
		return this.headMap(to.shortValue());
	}

	@Deprecated
	default Short2ShortSortedMap tailMap(Short from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Short, Short>> entrySet() {
		return this.short2ShortEntrySet();
	}

	ObjectSortedSet<Entry> short2ShortEntrySet();

	ShortSortedSet keySet();

	@Override
	ShortCollection values();

	ShortComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
