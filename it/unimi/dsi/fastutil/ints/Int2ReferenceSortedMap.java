package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.SortedMap;

public interface Int2ReferenceSortedMap<V> extends Int2ReferenceMap<V>, SortedMap<Integer, V> {
	Int2ReferenceSortedMap<V> subMap(int integer1, int integer2);

	Int2ReferenceSortedMap<V> headMap(int integer);

	Int2ReferenceSortedMap<V> tailMap(int integer);

	int firstIntKey();

	int lastIntKey();

	@Deprecated
	default Int2ReferenceSortedMap<V> subMap(Integer from, Integer to) {
		return this.subMap(from.intValue(), to.intValue());
	}

	@Deprecated
	default Int2ReferenceSortedMap<V> headMap(Integer to) {
		return this.headMap(to.intValue());
	}

	@Deprecated
	default Int2ReferenceSortedMap<V> tailMap(Integer from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Integer, V>> entrySet() {
		return this.int2ReferenceEntrySet();
	}

	ObjectSortedSet<Entry<V>> int2ReferenceEntrySet();

	IntSortedSet keySet();

	@Override
	ReferenceCollection<V> values();

	IntComparator comparator();

	public interface FastSortedEntrySet<V> extends ObjectSortedSet<Entry<V>>, FastEntrySet<V> {
		ObjectBidirectionalIterator<Entry<V>> fastIterator();

		ObjectBidirectionalIterator<Entry<V>> fastIterator(Entry<V> entry);
	}
}
