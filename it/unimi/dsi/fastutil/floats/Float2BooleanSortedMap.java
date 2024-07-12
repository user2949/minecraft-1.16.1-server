package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry;
import it.unimi.dsi.fastutil.floats.Float2BooleanMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Float2BooleanSortedMap extends Float2BooleanMap, SortedMap<Float, Boolean> {
	Float2BooleanSortedMap subMap(float float1, float float2);

	Float2BooleanSortedMap headMap(float float1);

	Float2BooleanSortedMap tailMap(float float1);

	float firstFloatKey();

	float lastFloatKey();

	@Deprecated
	default Float2BooleanSortedMap subMap(Float from, Float to) {
		return this.subMap(from.floatValue(), to.floatValue());
	}

	@Deprecated
	default Float2BooleanSortedMap headMap(Float to) {
		return this.headMap(to.floatValue());
	}

	@Deprecated
	default Float2BooleanSortedMap tailMap(Float from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Float, Boolean>> entrySet() {
		return this.float2BooleanEntrySet();
	}

	ObjectSortedSet<Entry> float2BooleanEntrySet();

	FloatSortedSet keySet();

	@Override
	BooleanCollection values();

	FloatComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
