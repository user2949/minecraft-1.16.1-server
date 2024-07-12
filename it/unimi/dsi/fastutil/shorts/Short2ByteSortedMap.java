package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2ByteMap.Entry;
import it.unimi.dsi.fastutil.shorts.Short2ByteMap.FastEntrySet;
import java.util.SortedMap;

public interface Short2ByteSortedMap extends Short2ByteMap, SortedMap<Short, Byte> {
	Short2ByteSortedMap subMap(short short1, short short2);

	Short2ByteSortedMap headMap(short short1);

	Short2ByteSortedMap tailMap(short short1);

	short firstShortKey();

	short lastShortKey();

	@Deprecated
	default Short2ByteSortedMap subMap(Short from, Short to) {
		return this.subMap(from.shortValue(), to.shortValue());
	}

	@Deprecated
	default Short2ByteSortedMap headMap(Short to) {
		return this.headMap(to.shortValue());
	}

	@Deprecated
	default Short2ByteSortedMap tailMap(Short from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Short, Byte>> entrySet() {
		return this.short2ByteEntrySet();
	}

	ObjectSortedSet<Entry> short2ByteEntrySet();

	ShortSortedSet keySet();

	@Override
	ByteCollection values();

	ShortComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
