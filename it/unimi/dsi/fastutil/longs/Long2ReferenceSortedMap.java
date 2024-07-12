package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.Long2ReferenceMap.Entry;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.SortedMap;

public interface Long2ReferenceSortedMap<V> extends Long2ReferenceMap<V>, SortedMap<Long, V> {
	Long2ReferenceSortedMap<V> subMap(long long1, long long2);

	Long2ReferenceSortedMap<V> headMap(long long1);

	Long2ReferenceSortedMap<V> tailMap(long long1);

	long firstLongKey();

	long lastLongKey();

	@Deprecated
	default Long2ReferenceSortedMap<V> subMap(Long from, Long to) {
		return this.subMap(from.longValue(), to.longValue());
	}

	@Deprecated
	default Long2ReferenceSortedMap<V> headMap(Long to) {
		return this.headMap(to.longValue());
	}

	@Deprecated
	default Long2ReferenceSortedMap<V> tailMap(Long from) {
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
		return this.long2ReferenceEntrySet();
	}

	ObjectSortedSet<Entry<V>> long2ReferenceEntrySet();

	LongSortedSet keySet();

	@Override
	ReferenceCollection<V> values();

	LongComparator comparator();

	public interface FastSortedEntrySet<V> extends ObjectSortedSet<Entry<V>>, FastEntrySet<V> {
		ObjectBidirectionalIterator<Entry<V>> fastIterator();

		ObjectBidirectionalIterator<Entry<V>> fastIterator(Entry<V> entry);
	}
}
