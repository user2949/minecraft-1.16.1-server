package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.ints.Int2FloatMap.Entry;
import it.unimi.dsi.fastutil.ints.Int2FloatMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Int2FloatSortedMap extends Int2FloatMap, SortedMap<Integer, Float> {
	Int2FloatSortedMap subMap(int integer1, int integer2);

	Int2FloatSortedMap headMap(int integer);

	Int2FloatSortedMap tailMap(int integer);

	int firstIntKey();

	int lastIntKey();

	@Deprecated
	default Int2FloatSortedMap subMap(Integer from, Integer to) {
		return this.subMap(from.intValue(), to.intValue());
	}

	@Deprecated
	default Int2FloatSortedMap headMap(Integer to) {
		return this.headMap(to.intValue());
	}

	@Deprecated
	default Int2FloatSortedMap tailMap(Integer from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Integer, Float>> entrySet() {
		return this.int2FloatEntrySet();
	}

	ObjectSortedSet<Entry> int2FloatEntrySet();

	IntSortedSet keySet();

	@Override
	FloatCollection values();

	IntComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
