package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2ReferenceMap.Entry;
import it.unimi.dsi.fastutil.floats.Float2ReferenceMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.SortedMap;

public interface Float2ReferenceSortedMap<V> extends Float2ReferenceMap<V>, SortedMap<Float, V> {
	Float2ReferenceSortedMap<V> subMap(float float1, float float2);

	Float2ReferenceSortedMap<V> headMap(float float1);

	Float2ReferenceSortedMap<V> tailMap(float float1);

	float firstFloatKey();

	float lastFloatKey();

	@Deprecated
	default Float2ReferenceSortedMap<V> subMap(Float from, Float to) {
		return this.subMap(from.floatValue(), to.floatValue());
	}

	@Deprecated
	default Float2ReferenceSortedMap<V> headMap(Float to) {
		return this.headMap(to.floatValue());
	}

	@Deprecated
	default Float2ReferenceSortedMap<V> tailMap(Float from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Float, V>> entrySet() {
		return this.float2ReferenceEntrySet();
	}

	ObjectSortedSet<Entry<V>> float2ReferenceEntrySet();

	FloatSortedSet keySet();

	@Override
	ReferenceCollection<V> values();

	FloatComparator comparator();

	public interface FastSortedEntrySet<V> extends ObjectSortedSet<Entry<V>>, FastEntrySet<V> {
		ObjectBidirectionalIterator<Entry<V>> fastIterator();

		ObjectBidirectionalIterator<Entry<V>> fastIterator(Entry<V> entry);
	}
}
