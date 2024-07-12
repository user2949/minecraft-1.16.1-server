package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2IntMap.Entry;
import it.unimi.dsi.fastutil.doubles.Double2IntMap.FastEntrySet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Double2IntSortedMap extends Double2IntMap, SortedMap<Double, Integer> {
	Double2IntSortedMap subMap(double double1, double double2);

	Double2IntSortedMap headMap(double double1);

	Double2IntSortedMap tailMap(double double1);

	double firstDoubleKey();

	double lastDoubleKey();

	@Deprecated
	default Double2IntSortedMap subMap(Double from, Double to) {
		return this.subMap(from.doubleValue(), to.doubleValue());
	}

	@Deprecated
	default Double2IntSortedMap headMap(Double to) {
		return this.headMap(to.doubleValue());
	}

	@Deprecated
	default Double2IntSortedMap tailMap(Double from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Double, Integer>> entrySet() {
		return this.double2IntEntrySet();
	}

	ObjectSortedSet<Entry> double2IntEntrySet();

	DoubleSortedSet keySet();

	@Override
	IntCollection values();

	DoubleComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
