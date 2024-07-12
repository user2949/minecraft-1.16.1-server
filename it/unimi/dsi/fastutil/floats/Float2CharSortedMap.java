package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.floats.Float2CharMap.Entry;
import it.unimi.dsi.fastutil.floats.Float2CharMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Float2CharSortedMap extends Float2CharMap, SortedMap<Float, Character> {
	Float2CharSortedMap subMap(float float1, float float2);

	Float2CharSortedMap headMap(float float1);

	Float2CharSortedMap tailMap(float float1);

	float firstFloatKey();

	float lastFloatKey();

	@Deprecated
	default Float2CharSortedMap subMap(Float from, Float to) {
		return this.subMap(from.floatValue(), to.floatValue());
	}

	@Deprecated
	default Float2CharSortedMap headMap(Float to) {
		return this.headMap(to.floatValue());
	}

	@Deprecated
	default Float2CharSortedMap tailMap(Float from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Float, Character>> entrySet() {
		return this.float2CharEntrySet();
	}

	ObjectSortedSet<Entry> float2CharEntrySet();

	FloatSortedSet keySet();

	@Override
	CharCollection values();

	FloatComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
