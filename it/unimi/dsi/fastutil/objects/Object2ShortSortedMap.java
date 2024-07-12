package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Object2ShortMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2ShortMap.FastEntrySet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Comparator;
import java.util.SortedMap;

public interface Object2ShortSortedMap<K> extends Object2ShortMap<K>, SortedMap<K, Short> {
	Object2ShortSortedMap<K> subMap(K object1, K object2);

	Object2ShortSortedMap<K> headMap(K object);

	Object2ShortSortedMap<K> tailMap(K object);

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<K, Short>> entrySet() {
		return this.object2ShortEntrySet();
	}

	ObjectSortedSet<Entry<K>> object2ShortEntrySet();

	ObjectSortedSet<K> keySet();

	@Override
	ShortCollection values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K> extends ObjectSortedSet<Entry<K>>, FastEntrySet<K> {
		ObjectBidirectionalIterator<Entry<K>> fastIterator();

		ObjectBidirectionalIterator<Entry<K>> fastIterator(Entry<K> entry);
	}
}
