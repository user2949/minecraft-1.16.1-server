package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry;
import it.unimi.dsi.fastutil.doubles.Double2BooleanMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Double2BooleanSortedMap extends Double2BooleanMap, SortedMap<Double, Boolean> {
	Double2BooleanSortedMap subMap(double double1, double double2);

	Double2BooleanSortedMap headMap(double double1);

	Double2BooleanSortedMap tailMap(double double1);

	double firstDoubleKey();

	double lastDoubleKey();

	@Deprecated
	default Double2BooleanSortedMap subMap(Double from, Double to) {
		return this.subMap(from.doubleValue(), to.doubleValue());
	}

	@Deprecated
	default Double2BooleanSortedMap headMap(Double to) {
		return this.headMap(to.doubleValue());
	}

	@Deprecated
	default Double2BooleanSortedMap tailMap(Double from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Double, Boolean>> entrySet() {
		return this.double2BooleanEntrySet();
	}

	ObjectSortedSet<Entry> double2BooleanEntrySet();

	DoubleSortedSet keySet();

	@Override
	BooleanCollection values();

	DoubleComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
