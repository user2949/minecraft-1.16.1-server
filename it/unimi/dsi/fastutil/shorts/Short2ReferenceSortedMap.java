package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import it.unimi.dsi.fastutil.shorts.Short2ReferenceMap.Entry;
import it.unimi.dsi.fastutil.shorts.Short2ReferenceMap.FastEntrySet;
import java.util.SortedMap;

public interface Short2ReferenceSortedMap<V> extends Short2ReferenceMap<V>, SortedMap<Short, V> {
	Short2ReferenceSortedMap<V> subMap(short short1, short short2);

	Short2ReferenceSortedMap<V> headMap(short short1);

	Short2ReferenceSortedMap<V> tailMap(short short1);

	short firstShortKey();

	short lastShortKey();

	@Deprecated
	default Short2ReferenceSortedMap<V> subMap(Short from, Short to) {
		return this.subMap(from.shortValue(), to.shortValue());
	}

	@Deprecated
	default Short2ReferenceSortedMap<V> headMap(Short to) {
		return this.headMap(to.shortValue());
	}

	@Deprecated
	default Short2ReferenceSortedMap<V> tailMap(Short from) {
		return this.tailMap(from.shortValue());
	}

	@Deprecated
	default Short firstKey() {
		return this.firstShortKey();
	}

	@Deprecated
	default Short lastKey() {
		return this.lastShortKey();
	}

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<Short, V>> entrySet() {
		return this.short2ReferenceEntrySet();
	}

	ObjectSortedSet<Entry<V>> short2ReferenceEntrySet();

	ShortSortedSet keySet();

	@Override
	ReferenceCollection<V> values();

	ShortComparator comparator();

	public interface FastSortedEntrySet<V> extends ObjectSortedSet<Entry<V>>, FastEntrySet<V> {
		ObjectBidirectionalIterator<Entry<V>> fastIterator();

		ObjectBidirectionalIterator<Entry<V>> fastIterator(Entry<V> entry);
	}
}
