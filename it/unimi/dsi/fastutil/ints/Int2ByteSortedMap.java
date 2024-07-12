package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.ints.Int2ByteMap.Entry;
import it.unimi.dsi.fastutil.ints.Int2ByteMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Int2ByteSortedMap extends Int2ByteMap, SortedMap<Integer, Byte> {
	Int2ByteSortedMap subMap(int integer1, int integer2);

	Int2ByteSortedMap headMap(int integer);

	Int2ByteSortedMap tailMap(int integer);

	int firstIntKey();

	int lastIntKey();

	@Deprecated
	default Int2ByteSortedMap subMap(Integer from, Integer to) {
		return this.subMap(from.intValue(), to.intValue());
	}

	@Deprecated
	default Int2ByteSortedMap headMap(Integer to) {
		return this.headMap(to.intValue());
	}

	@Deprecated
	default Int2ByteSortedMap tailMap(Integer from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Integer, Byte>> entrySet() {
		return this.int2ByteEntrySet();
	}

	ObjectSortedSet<Entry> int2ByteEntrySet();

	IntSortedSet keySet();

	@Override
	ByteCollection values();

	IntComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
