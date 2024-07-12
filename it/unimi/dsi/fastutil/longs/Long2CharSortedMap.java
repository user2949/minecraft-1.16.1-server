package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.longs.Long2CharMap.Entry;
import it.unimi.dsi.fastutil.longs.Long2CharMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Long2CharSortedMap extends Long2CharMap, SortedMap<Long, Character> {
	Long2CharSortedMap subMap(long long1, long long2);

	Long2CharSortedMap headMap(long long1);

	Long2CharSortedMap tailMap(long long1);

	long firstLongKey();

	long lastLongKey();

	@Deprecated
	default Long2CharSortedMap subMap(Long from, Long to) {
		return this.subMap(from.longValue(), to.longValue());
	}

	@Deprecated
	default Long2CharSortedMap headMap(Long to) {
		return this.headMap(to.longValue());
	}

	@Deprecated
	default Long2CharSortedMap tailMap(Long from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Long, Character>> entrySet() {
		return this.long2CharEntrySet();
	}

	ObjectSortedSet<Entry> long2CharEntrySet();

	LongSortedSet keySet();

	@Override
	CharCollection values();

	LongComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
