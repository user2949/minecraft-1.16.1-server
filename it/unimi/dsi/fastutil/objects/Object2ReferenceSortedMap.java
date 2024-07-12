package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Object2ReferenceMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2ReferenceMap.FastEntrySet;
import java.util.Comparator;
import java.util.SortedMap;

public interface Object2ReferenceSortedMap<K, V> extends Object2ReferenceMap<K, V>, SortedMap<K, V> {
	Object2ReferenceSortedMap<K, V> subMap(K object1, K object2);

	Object2ReferenceSortedMap<K, V> headMap(K object);

	Object2ReferenceSortedMap<K, V> tailMap(K object);

	default ObjectSortedSet<java.util.Map.Entry<K, V>> entrySet() {
		return this.object2ReferenceEntrySet();
	}

	ObjectSortedSet<Entry<K, V>> object2ReferenceEntrySet();

	ObjectSortedSet<K> keySet();

	@Override
	ReferenceCollection<V> values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K, V> extends ObjectSortedSet<Entry<K, V>>, FastEntrySet<K, V> {
		ObjectBidirectionalIterator<Entry<K, V>> fastIterator();

		ObjectBidirectionalIterator<Entry<K, V>> fastIterator(Entry<K, V> entry);
	}
}
