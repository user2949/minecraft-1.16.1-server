package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2ShortMap.Entry;
import it.unimi.dsi.fastutil.floats.Float2ShortMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.SortedMap;

public interface Float2ShortSortedMap extends Float2ShortMap, SortedMap<Float, Short> {
	Float2ShortSortedMap subMap(float float1, float float2);

	Float2ShortSortedMap headMap(float float1);

	Float2ShortSortedMap tailMap(float float1);

	float firstFloatKey();

	float lastFloatKey();

	@Deprecated
	default Float2ShortSortedMap subMap(Float from, Float to) {
		return this.subMap(from.floatValue(), to.floatValue());
	}

	@Deprecated
	default Float2ShortSortedMap headMap(Float to) {
		return this.headMap(to.floatValue());
	}

	@Deprecated
	default Float2ShortSortedMap tailMap(Float from) {
		return this.tailMap(from.floatValue());
	}

	@Deprecated
	default Float firstKey() {
		return this.firstFloatKey();
	}

	@Deprecated
	default Float lastKey() {
		return this.lastFloatKey();
	}

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<Float, Short>> entrySet() {
		return this.float2ShortEntrySet();
	}

	ObjectSortedSet<Entry> float2ShortEntrySet();

	FloatSortedSet keySet();

	@Override
	ShortCollection values();

	FloatComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
