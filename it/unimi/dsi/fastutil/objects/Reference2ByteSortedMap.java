package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.objects.Reference2ByteMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2ByteMap.FastEntrySet;
import java.util.Comparator;
import java.util.SortedMap;

public interface Reference2ByteSortedMap<K> extends Reference2ByteMap<K>, SortedMap<K, Byte> {
	Reference2ByteSortedMap<K> subMap(K object1, K object2);

	Reference2ByteSortedMap<K> headMap(K object);

	Reference2ByteSortedMap<K> tailMap(K object);

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<K, Byte>> entrySet() {
		return this.reference2ByteEntrySet();
	}

	ObjectSortedSet<Entry<K>> reference2ByteEntrySet();

	ReferenceSortedSet<K> keySet();

	@Override
	ByteCollection values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K> extends ObjectSortedSet<Entry<K>>, FastEntrySet<K> {
		ObjectBidirectionalIterator<Entry<K>> fastIterator();

		ObjectBidirectionalIterator<Entry<K>> fastIterator(Entry<K> entry);
	}
}
