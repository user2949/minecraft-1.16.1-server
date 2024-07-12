package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Long2ObjectSortedMap<V> extends Long2ObjectMap<V>, SortedMap<Long, V> {
	Long2ObjectSortedMap<V> subMap(long long1, long long2);

	Long2ObjectSortedMap<V> headMap(long long1);

	Long2ObjectSortedMap<V> tailMap(long long1);

	long firstLongKey();

	long lastLongKey();

	@Deprecated
	default Long2ObjectSortedMap<V> subMap(Long from, Long to) {
		return this.subMap(from.longValue(), to.longValue());
	}

	@Deprecated
	default Long2ObjectSortedMap<V> headMap(Long to) {
		return this.headMap(to.longValue());
	}

	@Deprecated
	default Long2ObjectSortedMap<V> tailMap(Long from) {
		return this.tailMap(from.longValue());
	}

	@Deprecated
	default Long firstKey() {
		return this.firstLongKey();
	}

	@Deprecated
	default Long lastKey() {
		return this.lastLongKey();
	}

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<Long, V>> entrySet() {
		return this.long2ObjectEntrySet();
	}

	ObjectSortedSet<Entry<V>> long2ObjectEntrySet();

	LongSortedSet keySet();

	@Override
	ObjectCollection<V> values();

	LongComparator comparator();

	public interface FastSortedEntrySet<V> extends ObjectSortedSet<Entry<V>>, FastEntrySet<V> {
		ObjectBidirectionalIterator<Entry<V>> fastIterator();

		ObjectBidirectionalIterator<Entry<V>> fastIterator(Entry<V> entry);
	}
}
