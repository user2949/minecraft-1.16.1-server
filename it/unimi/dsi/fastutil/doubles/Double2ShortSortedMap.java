package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2ShortMap.Entry;
import it.unimi.dsi.fastutil.doubles.Double2ShortMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.SortedMap;

public interface Double2ShortSortedMap extends Double2ShortMap, SortedMap<Double, Short> {
	Double2ShortSortedMap subMap(double double1, double double2);

	Double2ShortSortedMap headMap(double double1);

	Double2ShortSortedMap tailMap(double double1);

	double firstDoubleKey();

	double lastDoubleKey();

	@Deprecated
	default Double2ShortSortedMap subMap(Double from, Double to) {
		return this.subMap(from.doubleValue(), to.doubleValue());
	}

	@Deprecated
	default Double2ShortSortedMap headMap(Double to) {
		return this.headMap(to.doubleValue());
	}

	@Deprecated
	default Double2ShortSortedMap tailMap(Double from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Double, Short>> entrySet() {
		return this.double2ShortEntrySet();
	}

	ObjectSortedSet<Entry> double2ShortEntrySet();

	DoubleSortedSet keySet();

	@Override
	ShortCollection values();

	DoubleComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
