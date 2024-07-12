package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.Object2FloatMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2FloatMap.FastEntrySet;
import java.util.Comparator;
import java.util.SortedMap;

public interface Object2FloatSortedMap<K> extends Object2FloatMap<K>, SortedMap<K, Float> {
	Object2FloatSortedMap<K> subMap(K object1, K object2);

	Object2FloatSortedMap<K> headMap(K object);

	Object2FloatSortedMap<K> tailMap(K object);

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<K, Float>> entrySet() {
		return this.object2FloatEntrySet();
	}

	ObjectSortedSet<Entry<K>> object2FloatEntrySet();

	ObjectSortedSet<K> keySet();

	@Override
	FloatCollection values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K> extends ObjectSortedSet<Entry<K>>, FastEntrySet<K> {
		ObjectBidirectionalIterator<Entry<K>> fastIterator();

		ObjectBidirectionalIterator<Entry<K>> fastIterator(Entry<K> entry);
	}
}