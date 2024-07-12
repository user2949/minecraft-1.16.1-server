package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2FloatMap.Entry;
import it.unimi.dsi.fastutil.doubles.Double2FloatMap.FastEntrySet;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Double2FloatSortedMap extends Double2FloatMap, SortedMap<Double, Float> {
	Double2FloatSortedMap subMap(double double1, double double2);

	Double2FloatSortedMap headMap(double double1);

	Double2FloatSortedMap tailMap(double double1);

	double firstDoubleKey();

	double lastDoubleKey();

	@Deprecated
	default Double2FloatSortedMap subMap(Double from, Double to) {
		return this.subMap(from.doubleValue(), to.doubleValue());
	}

	@Deprecated
	default Double2FloatSortedMap headMap(Double to) {
		return this.headMap(to.doubleValue());
	}

	@Deprecated
	default Double2FloatSortedMap tailMap(Double from) {
		return this.tailMap(from.doubleValue());
	}

	@Deprecated
	default Double firstKey() {
		return this.firstDoubleKey();
	}

	@Deprecated
	default Double lastKey() {
		return this.lastDoubleKey();
	}

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<Double, Float>> entrySet() {
		return this.double2FloatEntrySet();
	}

	ObjectSortedSet<Entry> double2FloatEntrySet();

	DoubleSortedSet keySet();

	@Override
	FloatCollection values();

	DoubleComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
