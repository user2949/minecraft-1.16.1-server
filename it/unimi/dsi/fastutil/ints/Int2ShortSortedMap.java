package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2ShortMap.Entry;
import it.unimi.dsi.fastutil.ints.Int2ShortMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.SortedMap;

public interface Int2ShortSortedMap extends Int2ShortMap, SortedMap<Integer, Short> {
	Int2ShortSortedMap subMap(int integer1, int integer2);

	Int2ShortSortedMap headMap(int integer);

	Int2ShortSortedMap tailMap(int integer);

	int firstIntKey();

	int lastIntKey();

	@Deprecated
	default Int2ShortSortedMap subMap(Integer from, Integer to) {
		return this.subMap(from.intValue(), to.intValue());
	}

	@Deprecated
	default Int2ShortSortedMap headMap(Integer to) {
		return this.headMap(to.intValue());
	}

	@Deprecated
	default Int2ShortSortedMap tailMap(Integer from) {
		return this.tailMap(from.intValue());
	}

	@Deprecated
	default Integer firstKey() {
		return this.firstIntKey();
	}

	@Deprecated
	default Integer lastKey() {
		return this.lastIntKey();
	}

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<Integer, Short>> entrySet() {
		return this.int2ShortEntrySet();
	}

	ObjectSortedSet<Entry> int2ShortEntrySet();

	IntSortedSet keySet();

	@Override
	ShortCollection values();

	IntComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
