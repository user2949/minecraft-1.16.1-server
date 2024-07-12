package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap.Entry;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap.FastEntrySet;
import java.util.SortedMap;

public interface Short2ObjectSortedMap<V> extends Short2ObjectMap<V>, SortedMap<Short, V> {
	Short2ObjectSortedMap<V> subMap(short short1, short short2);

	Short2ObjectSortedMap<V> headMap(short short1);

	Short2ObjectSortedMap<V> tailMap(short short1);

	short firstShortKey();

	short lastShortKey();

	@Deprecated
	default Short2ObjectSortedMap<V> subMap(Short from, Short to) {
		return this.subMap(from.shortValue(), to.shortValue());
	}

	@Deprecated
	default Short2ObjectSortedMap<V> headMap(Short to) {
		return this.headMap(to.shortValue());
	}

	@Deprecated
	default Short2ObjectSortedMap<V> tailMap(Short from) {
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
		return this.short2ObjectEntrySet();
	}

	ObjectSortedSet<Entry<V>> short2ObjectEntrySet();

	ShortSortedSet keySet();

	@Override
	ObjectCollection<V> values();

	ShortComparator comparator();

	public interface FastSortedEntrySet<V> extends ObjectSortedSet<Entry<V>>, FastEntrySet<V> {
		ObjectBidirectionalIterator<Entry<V>> fastIterator();

		ObjectBidirectionalIterator<Entry<V>> fastIterator(Entry<V> entry);
	}
}
