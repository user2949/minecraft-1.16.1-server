package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry;
import it.unimi.dsi.fastutil.longs.Long2ByteMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Long2ByteSortedMap extends Long2ByteMap, SortedMap<Long, Byte> {
	Long2ByteSortedMap subMap(long long1, long long2);

	Long2ByteSortedMap headMap(long long1);

	Long2ByteSortedMap tailMap(long long1);

	long firstLongKey();

	long lastLongKey();

	@Deprecated
	default Long2ByteSortedMap subMap(Long from, Long to) {
		return this.subMap(from.longValue(), to.longValue());
	}

	@Deprecated
	default Long2ByteSortedMap headMap(Long to) {
		return this.headMap(to.longValue());
	}

	@Deprecated
	default Long2ByteSortedMap tailMap(Long from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Long, Byte>> entrySet() {
		return this.long2ByteEntrySet();
	}

	ObjectSortedSet<Entry> long2ByteEntrySet();

	LongSortedSet keySet();

	@Override
	ByteCollection values();

	LongComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
