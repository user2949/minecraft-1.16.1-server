package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap.Entry;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap.FastEntrySet;
import java.util.SortedMap;

public interface Short2BooleanSortedMap extends Short2BooleanMap, SortedMap<Short, Boolean> {
	Short2BooleanSortedMap subMap(short short1, short short2);

	Short2BooleanSortedMap headMap(short short1);

	Short2BooleanSortedMap tailMap(short short1);

	short firstShortKey();

	short lastShortKey();

	@Deprecated
	default Short2BooleanSortedMap subMap(Short from, Short to) {
		return this.subMap(from.shortValue(), to.shortValue());
	}

	@Deprecated
	default Short2BooleanSortedMap headMap(Short to) {
		return this.headMap(to.shortValue());
	}

	@Deprecated
	default Short2BooleanSortedMap tailMap(Short from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Short, Boolean>> entrySet() {
		return this.short2BooleanEntrySet();
	}

	ObjectSortedSet<Entry> short2BooleanEntrySet();

	ShortSortedSet keySet();

	@Override
	BooleanCollection values();

	ShortComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
