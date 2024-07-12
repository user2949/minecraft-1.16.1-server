package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2IntMap.Entry;
import it.unimi.dsi.fastutil.ints.Int2IntMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Int2IntSortedMap extends Int2IntMap, SortedMap<Integer, Integer> {
	Int2IntSortedMap subMap(int integer1, int integer2);

	Int2IntSortedMap headMap(int integer);

	Int2IntSortedMap tailMap(int integer);

	int firstIntKey();

	int lastIntKey();

	@Deprecated
	default Int2IntSortedMap subMap(Integer from, Integer to) {
		return this.subMap(from.intValue(), to.intValue());
	}

	@Deprecated
	default Int2IntSortedMap headMap(Integer to) {
		return this.headMap(to.intValue());
	}

	@Deprecated
	default Int2IntSortedMap tailMap(Integer from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Integer, Integer>> entrySet() {
		return this.int2IntEntrySet();
	}

	ObjectSortedSet<Entry> int2IntEntrySet();

	IntSortedSet keySet();

	@Override
	IntCollection values();

	IntComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
