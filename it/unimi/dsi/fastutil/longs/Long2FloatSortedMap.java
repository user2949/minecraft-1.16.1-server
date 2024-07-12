package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.longs.Long2FloatMap.Entry;
import it.unimi.dsi.fastutil.longs.Long2FloatMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Long2FloatSortedMap extends Long2FloatMap, SortedMap<Long, Float> {
	Long2FloatSortedMap subMap(long long1, long long2);

	Long2FloatSortedMap headMap(long long1);

	Long2FloatSortedMap tailMap(long long1);

	long firstLongKey();

	long lastLongKey();

	@Deprecated
	default Long2FloatSortedMap subMap(Long from, Long to) {
		return this.subMap(from.longValue(), to.longValue());
	}

	@Deprecated
	default Long2FloatSortedMap headMap(Long to) {
		return this.headMap(to.longValue());
	}

	@Deprecated
	default Long2FloatSortedMap tailMap(Long from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Long, Float>> entrySet() {
		return this.long2FloatEntrySet();
	}

	ObjectSortedSet<Entry> long2FloatEntrySet();

	LongSortedSet keySet();

	@Override
	FloatCollection values();

	LongComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
