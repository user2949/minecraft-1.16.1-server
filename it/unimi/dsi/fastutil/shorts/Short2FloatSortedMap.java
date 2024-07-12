package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry;
import it.unimi.dsi.fastutil.shorts.Short2FloatMap.FastEntrySet;
import java.util.SortedMap;

public interface Short2FloatSortedMap extends Short2FloatMap, SortedMap<Short, Float> {
	Short2FloatSortedMap subMap(short short1, short short2);

	Short2FloatSortedMap headMap(short short1);

	Short2FloatSortedMap tailMap(short short1);

	short firstShortKey();

	short lastShortKey();

	@Deprecated
	default Short2FloatSortedMap subMap(Short from, Short to) {
		return this.subMap(from.shortValue(), to.shortValue());
	}

	@Deprecated
	default Short2FloatSortedMap headMap(Short to) {
		return this.headMap(to.shortValue());
	}

	@Deprecated
	default Short2FloatSortedMap tailMap(Short from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Short, Float>> entrySet() {
		return this.short2FloatEntrySet();
	}

	ObjectSortedSet<Entry> short2FloatEntrySet();

	ShortSortedSet keySet();

	@Override
	FloatCollection values();

	ShortComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
