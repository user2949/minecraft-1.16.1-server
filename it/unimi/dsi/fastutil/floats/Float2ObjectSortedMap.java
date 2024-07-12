package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry;
import it.unimi.dsi.fastutil.floats.Float2ObjectMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Float2ObjectSortedMap<V> extends Float2ObjectMap<V>, SortedMap<Float, V> {
	Float2ObjectSortedMap<V> subMap(float float1, float float2);

	Float2ObjectSortedMap<V> headMap(float float1);

	Float2ObjectSortedMap<V> tailMap(float float1);

	float firstFloatKey();

	float lastFloatKey();

	@Deprecated
	default Float2ObjectSortedMap<V> subMap(Float from, Float to) {
		return this.subMap(from.floatValue(), to.floatValue());
	}

	@Deprecated
	default Float2ObjectSortedMap<V> headMap(Float to) {
		return this.headMap(to.floatValue());
	}

	@Deprecated
	default Float2ObjectSortedMap<V> tailMap(Float from) {
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
		return this.float2ObjectEntrySet();
	}

	ObjectSortedSet<Entry<V>> float2ObjectEntrySet();

	FloatSortedSet keySet();

	@Override
	ObjectCollection<V> values();

	FloatComparator comparator();

	public interface FastSortedEntrySet<V> extends ObjectSortedSet<Entry<V>>, FastEntrySet<V> {
		ObjectBidirectionalIterator<Entry<V>> fastIterator();

		ObjectBidirectionalIterator<Entry<V>> fastIterator(Entry<V> entry);
	}
}
