package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap.FastEntrySet;
import java.util.Comparator;
import java.util.SortedMap;

public interface Reference2ObjectSortedMap<K, V> extends Reference2ObjectMap<K, V>, SortedMap<K, V> {
	Reference2ObjectSortedMap<K, V> subMap(K object1, K object2);

	Reference2ObjectSortedMap<K, V> headMap(K object);

	Reference2ObjectSortedMap<K, V> tailMap(K object);

	default ObjectSortedSet<java.util.Map.Entry<K, V>> entrySet() {
		return this.reference2ObjectEntrySet();
	}

	ObjectSortedSet<Entry<K, V>> reference2ObjectEntrySet();

	ReferenceSortedSet<K> keySet();

	@Override
	ObjectCollection<V> values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K, V> extends ObjectSortedSet<Entry<K, V>>, FastEntrySet<K, V> {
		ObjectBidirectionalIterator<Entry<K, V>> fastIterator();

		ObjectBidirectionalIterator<Entry<K, V>> fastIterator(Entry<K, V> entry);
	}
}
