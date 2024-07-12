package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap.FastEntrySet;
import java.util.Comparator;
import java.util.SortedMap;

public interface Reference2FloatSortedMap<K> extends Reference2FloatMap<K>, SortedMap<K, Float> {
	Reference2FloatSortedMap<K> subMap(K object1, K object2);

	Reference2FloatSortedMap<K> headMap(K object);

	Reference2FloatSortedMap<K> tailMap(K object);

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<K, Float>> entrySet() {
		return this.reference2FloatEntrySet();
	}

	ObjectSortedSet<Entry<K>> reference2FloatEntrySet();

	ReferenceSortedSet<K> keySet();

	@Override
	FloatCollection values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K> extends ObjectSortedSet<Entry<K>>, FastEntrySet<K> {
		ObjectBidirectionalIterator<Entry<K>> fastIterator();

		ObjectBidirectionalIterator<Entry<K>> fastIterator(Entry<K> entry);
	}
}
