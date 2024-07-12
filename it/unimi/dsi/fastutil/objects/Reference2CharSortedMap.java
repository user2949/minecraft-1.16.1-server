package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.objects.Reference2CharMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2CharMap.FastEntrySet;
import java.util.Comparator;
import java.util.SortedMap;

public interface Reference2CharSortedMap<K> extends Reference2CharMap<K>, SortedMap<K, Character> {
	Reference2CharSortedMap<K> subMap(K object1, K object2);

	Reference2CharSortedMap<K> headMap(K object);

	Reference2CharSortedMap<K> tailMap(K object);

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<K, Character>> entrySet() {
		return this.reference2CharEntrySet();
	}

	ObjectSortedSet<Entry<K>> reference2CharEntrySet();

	ReferenceSortedSet<K> keySet();

	@Override
	CharCollection values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K> extends ObjectSortedSet<Entry<K>>, FastEntrySet<K> {
		ObjectBidirectionalIterator<Entry<K>> fastIterator();

		ObjectBidirectionalIterator<Entry<K>> fastIterator(Entry<K> entry);
	}
}
