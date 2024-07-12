package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMap.FastEntrySet;
import java.util.Comparator;
import java.util.SortedMap;

public interface Reference2DoubleSortedMap<K> extends Reference2DoubleMap<K>, SortedMap<K, Double> {
	Reference2DoubleSortedMap<K> subMap(K object1, K object2);

	Reference2DoubleSortedMap<K> headMap(K object);

	Reference2DoubleSortedMap<K> tailMap(K object);

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<K, Double>> entrySet() {
		return this.reference2DoubleEntrySet();
	}

	ObjectSortedSet<Entry<K>> reference2DoubleEntrySet();

	ReferenceSortedSet<K> keySet();

	@Override
	DoubleCollection values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K> extends ObjectSortedSet<Entry<K>>, FastEntrySet<K> {
		ObjectBidirectionalIterator<Entry<K>> fastIterator();

		ObjectBidirectionalIterator<Entry<K>> fastIterator(Entry<K> entry);
	}
}
