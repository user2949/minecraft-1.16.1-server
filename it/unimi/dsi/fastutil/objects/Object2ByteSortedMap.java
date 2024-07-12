package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2ByteMap.FastEntrySet;
import java.util.Comparator;
import java.util.SortedMap;

public interface Object2ByteSortedMap<K> extends Object2ByteMap<K>, SortedMap<K, Byte> {
	Object2ByteSortedMap<K> subMap(K object1, K object2);

	Object2ByteSortedMap<K> headMap(K object);

	Object2ByteSortedMap<K> tailMap(K object);

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<K, Byte>> entrySet() {
		return this.object2ByteEntrySet();
	}

	ObjectSortedSet<Entry<K>> object2ByteEntrySet();

	ObjectSortedSet<K> keySet();

	@Override
	ByteCollection values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K> extends ObjectSortedSet<Entry<K>>, FastEntrySet<K> {
		ObjectBidirectionalIterator<Entry<K>> fastIterator();

		ObjectBidirectionalIterator<Entry<K>> fastIterator(Entry<K> entry);
	}
}
