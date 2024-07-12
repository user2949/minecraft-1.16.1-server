package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Int2ObjectSortedMap<V> extends Int2ObjectMap<V>, SortedMap<Integer, V> {
	Int2ObjectSortedMap<V> subMap(int integer1, int integer2);

	Int2ObjectSortedMap<V> headMap(int integer);

	Int2ObjectSortedMap<V> tailMap(int integer);

	int firstIntKey();

	int lastIntKey();

	@Deprecated
	default Int2ObjectSortedMap<V> subMap(Integer from, Integer to) {
		return this.subMap(from.intValue(), to.intValue());
	}

	@Deprecated
	default Int2ObjectSortedMap<V> headMap(Integer to) {
		return this.headMap(to.intValue());
	}

	@Deprecated
	default Int2ObjectSortedMap<V> tailMap(Integer from) {
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
		return this.int2ObjectEntrySet();
	}

	ObjectSortedSet<Entry<V>> int2ObjectEntrySet();

	IntSortedSet keySet();

	@Override
	ObjectCollection<V> values();

	IntComparator comparator();

	public interface FastSortedEntrySet<V> extends ObjectSortedSet<Entry<V>>, FastEntrySet<V> {
		ObjectBidirectionalIterator<Entry<V>> fastIterator();

		ObjectBidirectionalIterator<Entry<V>> fastIterator(Entry<V> entry);
	}
}
