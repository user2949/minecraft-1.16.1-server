package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap.FastEntrySet;
import java.util.Comparator;
import java.util.SortedMap;

public interface Object2DoubleSortedMap<K> extends Object2DoubleMap<K>, SortedMap<K, Double> {
	Object2DoubleSortedMap<K> subMap(K object1, K object2);

	Object2DoubleSortedMap<K> headMap(K object);

	Object2DoubleSortedMap<K> tailMap(K object);

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<K, Double>> entrySet() {
		return this.object2DoubleEntrySet();
	}

	ObjectSortedSet<Entry<K>> object2DoubleEntrySet();

	ObjectSortedSet<K> keySet();

	@Override
	DoubleCollection values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K> extends ObjectSortedSet<Entry<K>>, FastEntrySet<K> {
		ObjectBidirectionalIterator<Entry<K>> fastIterator();

		ObjectBidirectionalIterator<Entry<K>> fastIterator(Entry<K> entry);
	}
}
