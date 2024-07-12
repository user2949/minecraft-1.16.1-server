package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2ReferenceMap.Entry;
import it.unimi.dsi.fastutil.doubles.Double2ReferenceMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.SortedMap;

public interface Double2ReferenceSortedMap<V> extends Double2ReferenceMap<V>, SortedMap<Double, V> {
	Double2ReferenceSortedMap<V> subMap(double double1, double double2);

	Double2ReferenceSortedMap<V> headMap(double double1);

	Double2ReferenceSortedMap<V> tailMap(double double1);

	double firstDoubleKey();

	double lastDoubleKey();

	@Deprecated
	default Double2ReferenceSortedMap<V> subMap(Double from, Double to) {
		return this.subMap(from.doubleValue(), to.doubleValue());
	}

	@Deprecated
	default Double2ReferenceSortedMap<V> headMap(Double to) {
		return this.headMap(to.doubleValue());
	}

	@Deprecated
	default Double2ReferenceSortedMap<V> tailMap(Double from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Double, V>> entrySet() {
		return this.double2ReferenceEntrySet();
	}

	ObjectSortedSet<Entry<V>> double2ReferenceEntrySet();

	DoubleSortedSet keySet();

	@Override
	ReferenceCollection<V> values();

	DoubleComparator comparator();

	public interface FastSortedEntrySet<V> extends ObjectSortedSet<Entry<V>>, FastEntrySet<V> {
		ObjectBidirectionalIterator<Entry<V>> fastIterator();

		ObjectBidirectionalIterator<Entry<V>> fastIterator(Entry<V> entry);
	}
}
