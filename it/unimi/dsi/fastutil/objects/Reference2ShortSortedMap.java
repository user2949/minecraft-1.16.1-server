package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Reference2ShortMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2ShortMap.FastEntrySet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Comparator;
import java.util.SortedMap;

public interface Reference2ShortSortedMap<K> extends Reference2ShortMap<K>, SortedMap<K, Short> {
	Reference2ShortSortedMap<K> subMap(K object1, K object2);

	Reference2ShortSortedMap<K> headMap(K object);

	Reference2ShortSortedMap<K> tailMap(K object);

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<K, Short>> entrySet() {
		return this.reference2ShortEntrySet();
	}

	ObjectSortedSet<Entry<K>> reference2ShortEntrySet();

	ReferenceSortedSet<K> keySet();

	@Override
	ShortCollection values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K> extends ObjectSortedSet<Entry<K>>, FastEntrySet<K> {
		ObjectBidirectionalIterator<Entry<K>> fastIterator();

		ObjectBidirectionalIterator<Entry<K>> fastIterator(Entry<K> entry);
	}
}
