package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.objects.Reference2BooleanMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2BooleanMap.FastEntrySet;
import java.util.Comparator;
import java.util.SortedMap;

public interface Reference2BooleanSortedMap<K> extends Reference2BooleanMap<K>, SortedMap<K, Boolean> {
	Reference2BooleanSortedMap<K> subMap(K object1, K object2);

	Reference2BooleanSortedMap<K> headMap(K object);

	Reference2BooleanSortedMap<K> tailMap(K object);

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<K, Boolean>> entrySet() {
		return this.reference2BooleanEntrySet();
	}

	ObjectSortedSet<Entry<K>> reference2BooleanEntrySet();

	ReferenceSortedSet<K> keySet();

	@Override
	BooleanCollection values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K> extends ObjectSortedSet<Entry<K>>, FastEntrySet<K> {
		ObjectBidirectionalIterator<Entry<K>> fastIterator();

		ObjectBidirectionalIterator<Entry<K>> fastIterator(Entry<K> entry);
	}
}
