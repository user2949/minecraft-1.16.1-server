package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.Reference2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2IntMap.FastEntrySet;
import java.util.Comparator;
import java.util.SortedMap;

public interface Reference2IntSortedMap<K> extends Reference2IntMap<K>, SortedMap<K, Integer> {
	Reference2IntSortedMap<K> subMap(K object1, K object2);

	Reference2IntSortedMap<K> headMap(K object);

	Reference2IntSortedMap<K> tailMap(K object);

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<K, Integer>> entrySet() {
		return this.reference2IntEntrySet();
	}

	ObjectSortedSet<Entry<K>> reference2IntEntrySet();

	ReferenceSortedSet<K> keySet();

	@Override
	IntCollection values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K> extends ObjectSortedSet<Entry<K>>, FastEntrySet<K> {
		ObjectBidirectionalIterator<Entry<K>> fastIterator();

		ObjectBidirectionalIterator<Entry<K>> fastIterator(Entry<K> entry);
	}
}
