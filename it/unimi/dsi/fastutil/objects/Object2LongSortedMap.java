package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.Object2LongMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2LongMap.FastEntrySet;
import java.util.Comparator;
import java.util.SortedMap;

public interface Object2LongSortedMap<K> extends Object2LongMap<K>, SortedMap<K, Long> {
	Object2LongSortedMap<K> subMap(K object1, K object2);

	Object2LongSortedMap<K> headMap(K object);

	Object2LongSortedMap<K> tailMap(K object);

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<K, Long>> entrySet() {
		return this.object2LongEntrySet();
	}

	ObjectSortedSet<Entry<K>> object2LongEntrySet();

	ObjectSortedSet<K> keySet();

	@Override
	LongCollection values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K> extends ObjectSortedSet<Entry<K>>, FastEntrySet<K> {
		ObjectBidirectionalIterator<Entry<K>> fastIterator();

		ObjectBidirectionalIterator<Entry<K>> fastIterator(Entry<K> entry);
	}
}
