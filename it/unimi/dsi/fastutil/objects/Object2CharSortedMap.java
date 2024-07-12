package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.objects.Object2CharMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2CharMap.FastEntrySet;
import java.util.Comparator;
import java.util.SortedMap;

public interface Object2CharSortedMap<K> extends Object2CharMap<K>, SortedMap<K, Character> {
	Object2CharSortedMap<K> subMap(K object1, K object2);

	Object2CharSortedMap<K> headMap(K object);

	Object2CharSortedMap<K> tailMap(K object);

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<K, Character>> entrySet() {
		return this.object2CharEntrySet();
	}

	ObjectSortedSet<Entry<K>> object2CharEntrySet();

	ObjectSortedSet<K> keySet();

	@Override
	CharCollection values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K> extends ObjectSortedSet<Entry<K>>, FastEntrySet<K> {
		ObjectBidirectionalIterator<Entry<K>> fastIterator();

		ObjectBidirectionalIterator<Entry<K>> fastIterator(Entry<K> entry);
	}
}
