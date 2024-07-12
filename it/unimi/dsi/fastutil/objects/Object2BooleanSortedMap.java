package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap.FastEntrySet;
import java.util.Comparator;
import java.util.SortedMap;

public interface Object2BooleanSortedMap<K> extends Object2BooleanMap<K>, SortedMap<K, Boolean> {
	Object2BooleanSortedMap<K> subMap(K object1, K object2);

	Object2BooleanSortedMap<K> headMap(K object);

	Object2BooleanSortedMap<K> tailMap(K object);

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<K, Boolean>> entrySet() {
		return this.object2BooleanEntrySet();
	}

	ObjectSortedSet<Entry<K>> object2BooleanEntrySet();

	ObjectSortedSet<K> keySet();

	@Override
	BooleanCollection values();

	Comparator<? super K> comparator();

	public interface FastSortedEntrySet<K> extends ObjectSortedSet<Entry<K>>, FastEntrySet<K> {
		ObjectBidirectionalIterator<Entry<K>> fastIterator();

		ObjectBidirectionalIterator<Entry<K>> fastIterator(Entry<K> entry);
	}
}
