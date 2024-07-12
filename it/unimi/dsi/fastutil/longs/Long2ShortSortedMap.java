package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.Long2ShortMap.Entry;
import it.unimi.dsi.fastutil.longs.Long2ShortMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.SortedMap;

public interface Long2ShortSortedMap extends Long2ShortMap, SortedMap<Long, Short> {
	Long2ShortSortedMap subMap(long long1, long long2);

	Long2ShortSortedMap headMap(long long1);

	Long2ShortSortedMap tailMap(long long1);

	long firstLongKey();

	long lastLongKey();

	@Deprecated
	default Long2ShortSortedMap subMap(Long from, Long to) {
		return this.subMap(from.longValue(), to.longValue());
	}

	@Deprecated
	default Long2ShortSortedMap headMap(Long to) {
		return this.headMap(to.longValue());
	}

	@Deprecated
	default Long2ShortSortedMap tailMap(Long from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Long, Short>> entrySet() {
		return this.long2ShortEntrySet();
	}

	ObjectSortedSet<Entry> long2ShortEntrySet();

	LongSortedSet keySet();

	@Override
	ShortCollection values();

	LongComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
