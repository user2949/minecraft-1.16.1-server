package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry;
import it.unimi.dsi.fastutil.doubles.Double2DoubleMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Double2DoubleSortedMap extends Double2DoubleMap, SortedMap<Double, Double> {
	Double2DoubleSortedMap subMap(double double1, double double2);

	Double2DoubleSortedMap headMap(double double1);

	Double2DoubleSortedMap tailMap(double double1);

	double firstDoubleKey();

	double lastDoubleKey();

	@Deprecated
	default Double2DoubleSortedMap subMap(Double from, Double to) {
		return this.subMap(from.doubleValue(), to.doubleValue());
	}

	@Deprecated
	default Double2DoubleSortedMap headMap(Double to) {
		return this.headMap(to.doubleValue());
	}

	@Deprecated
	default Double2DoubleSortedMap tailMap(Double from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Double, Double>> entrySet() {
		return this.double2DoubleEntrySet();
	}

	ObjectSortedSet<Entry> double2DoubleEntrySet();

	DoubleSortedSet keySet();

	@Override
	DoubleCollection values();

	DoubleComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
