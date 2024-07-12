package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap.FastEntrySet;
import java.util.Comparator;
import java.util.SortedMap;

public interface Reference2ReferenceSortedMap<K, V> extends Reference2ReferenceMap<K, V>, SortedMap<K, V> {
	Reference2ReferenceSortedMap<K, V> subMap(K object1, K object2);

	Reference2ReferenceSortedMap<K, V> headMap(K object);

	Reference2ReferenceSortedMap<K, V> tailMap(K object);

	default ObjectSortedSet<java.util.Map.Entry<K, V>> entrySet() {
		return this.reference2ReferenceEntrySet();
	}

	ObjectSortedSet<Entry<K, V>> reference2ReferenceEntrySet();

	ReferenceSortedSet<K> keySet();

	@Override
	ReferenceCollection<V> values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K, V> extends ObjectSortedSet<Entry<K, V>>, FastEntrySet<K, V> {
		ObjectBidirectionalIterator<Entry<K, V>> fastIterator();

		ObjectBidirectionalIterator<Entry<K, V>> fastIterator(Entry<K, V> entry);
	}
}
