package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry;
import it.unimi.dsi.fastutil.floats.Float2ByteMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Float2ByteSortedMap extends Float2ByteMap, SortedMap<Float, Byte> {
	Float2ByteSortedMap subMap(float float1, float float2);

	Float2ByteSortedMap headMap(float float1);

	Float2ByteSortedMap tailMap(float float1);

	float firstFloatKey();

	float lastFloatKey();

	@Deprecated
	default Float2ByteSortedMap subMap(Float from, Float to) {
		return this.subMap(from.floatValue(), to.floatValue());
	}

	@Deprecated
	default Float2ByteSortedMap headMap(Float to) {
		return this.headMap(to.floatValue());
	}

	@Deprecated
	default Float2ByteSortedMap tailMap(Float from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Float, Byte>> entrySet() {
		return this.float2ByteEntrySet();
	}

	ObjectSortedSet<Entry> float2ByteEntrySet();

	FloatSortedSet keySet();

	@Override
	ByteCollection values();

	FloatComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
