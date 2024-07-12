package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.ints.Int2BooleanMap.Entry;
import it.unimi.dsi.fastutil.ints.Int2BooleanMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Int2BooleanSortedMap extends Int2BooleanMap, SortedMap<Integer, Boolean> {
	Int2BooleanSortedMap subMap(int integer1, int integer2);

	Int2BooleanSortedMap headMap(int integer);

	Int2BooleanSortedMap tailMap(int integer);

	int firstIntKey();

	int lastIntKey();

	@Deprecated
	default Int2BooleanSortedMap subMap(Integer from, Integer to) {
		return this.subMap(from.intValue(), to.intValue());
	}

	@Deprecated
	default Int2BooleanSortedMap headMap(Integer to) {
		return this.headMap(to.intValue());
	}

	@Deprecated
	default Int2BooleanSortedMap tailMap(Integer from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Integer, Boolean>> entrySet() {
		return this.int2BooleanEntrySet();
	}

	ObjectSortedSet<Entry> int2BooleanEntrySet();

	IntSortedSet keySet();

	@Override
	BooleanCollection values();

	IntComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
