package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.Reference2LongMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2LongMap.FastEntrySet;
import java.util.Comparator;
import java.util.SortedMap;

public interface Reference2LongSortedMap<K> extends Reference2LongMap<K>, SortedMap<K, Long> {
	Reference2LongSortedMap<K> subMap(K object1, K object2);

	Reference2LongSortedMap<K> headMap(K object);

	Reference2LongSortedMap<K> tailMap(K object);

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<K, Long>> entrySet() {
		return this.reference2LongEntrySet();
	}

	ObjectSortedSet<Entry<K>> reference2LongEntrySet();

	ReferenceSortedSet<K> keySet();

	@Override
	LongCollection values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K> extends ObjectSortedSet<Entry<K>>, FastEntrySet<K> {
		ObjectBidirectionalIterator<Entry<K>> fastIterator();

		ObjectBidirectionalIterator<Entry<K>> fastIterator(Entry<K> entry);
	}
}
