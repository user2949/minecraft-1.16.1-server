package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntMap.FastEntrySet;
import java.util.Comparator;
import java.util.SortedMap;

public interface Object2IntSortedMap<K> extends Object2IntMap<K>, SortedMap<K, Integer> {
	Object2IntSortedMap<K> subMap(K object1, K object2);

	Object2IntSortedMap<K> headMap(K object);

	Object2IntSortedMap<K> tailMap(K object);

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<K, Integer>> entrySet() {
		return this.object2IntEntrySet();
	}

	ObjectSortedSet<Entry<K>> object2IntEntrySet();

	ObjectSortedSet<K> keySet();

	@Override
	IntCollection values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K> extends ObjectSortedSet<Entry<K>>, FastEntrySet<K> {
		ObjectBidirectionalIterator<Entry<K>> fastIterator();

		ObjectBidirectionalIterator<Entry<K>> fastIterator(Entry<K> entry);
	}
}
