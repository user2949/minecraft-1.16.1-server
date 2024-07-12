package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.Long2LongMap.Entry;
import it.unimi.dsi.fastutil.longs.Long2LongMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Long2LongSortedMap extends Long2LongMap, SortedMap<Long, Long> {
	Long2LongSortedMap subMap(long long1, long long2);

	Long2LongSortedMap headMap(long long1);

	Long2LongSortedMap tailMap(long long1);

	long firstLongKey();

	long lastLongKey();

	@Deprecated
	default Long2LongSortedMap subMap(Long from, Long to) {
		return this.subMap(from.longValue(), to.longValue());
	}

	@Deprecated
	default Long2LongSortedMap headMap(Long to) {
		return this.headMap(to.longValue());
	}

	@Deprecated
	default Long2LongSortedMap tailMap(Long from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Long, Long>> entrySet() {
		return this.long2LongEntrySet();
	}

	ObjectSortedSet<Entry> long2LongEntrySet();

	LongSortedSet keySet();

	@Override
	LongCollection values();

	LongComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
