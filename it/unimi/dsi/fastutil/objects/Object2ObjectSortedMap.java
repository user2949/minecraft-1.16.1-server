package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap.FastEntrySet;
import java.util.Comparator;
import java.util.SortedMap;

public interface Object2ObjectSortedMap<K, V> extends Object2ObjectMap<K, V>, SortedMap<K, V> {
	Object2ObjectSortedMap<K, V> subMap(K object1, K object2);

	Object2ObjectSortedMap<K, V> headMap(K object);

	Object2ObjectSortedMap<K, V> tailMap(K object);

	default ObjectSortedSet<java.util.Map.Entry<K, V>> entrySet() {
		return this.object2ObjectEntrySet();
	}

	ObjectSortedSet<Entry<K, V>> object2ObjectEntrySet();

	ObjectSortedSet<K> keySet();

	@Override
	ObjectCollection<V> values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K, V> extends ObjectSortedSet<Entry<K, V>>, FastEntrySet<K, V> {
		ObjectBidirectionalIterator<Entry<K, V>> fastIterator();

		ObjectBidirectionalIterator<Entry<K, V>> fastIterator(Entry<K, V> entry);
	}
}
