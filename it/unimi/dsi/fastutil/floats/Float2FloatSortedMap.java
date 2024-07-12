package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2FloatMap.Entry;
import it.unimi.dsi.fastutil.floats.Float2FloatMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Float2FloatSortedMap extends Float2FloatMap, SortedMap<Float, Float> {
	Float2FloatSortedMap subMap(float float1, float float2);

	Float2FloatSortedMap headMap(float float1);

	Float2FloatSortedMap tailMap(float float1);

	float firstFloatKey();

	float lastFloatKey();

	@Deprecated
	default Float2FloatSortedMap subMap(Float from, Float to) {
		return this.subMap(from.floatValue(), to.floatValue());
	}

	@Deprecated
	default Float2FloatSortedMap headMap(Float to) {
		return this.headMap(to.floatValue());
	}

	@Deprecated
	default Float2FloatSortedMap tailMap(Float from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Float, Float>> entrySet() {
		return this.float2FloatEntrySet();
	}

	ObjectSortedSet<Entry> float2FloatEntrySet();

	FloatSortedSet keySet();

	@Override
	FloatCollection values();

	FloatComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
