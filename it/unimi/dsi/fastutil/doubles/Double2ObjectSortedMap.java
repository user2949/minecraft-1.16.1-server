package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2ObjectMap.Entry;
import it.unimi.dsi.fastutil.doubles.Double2ObjectMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Double2ObjectSortedMap<V> extends Double2ObjectMap<V>, SortedMap<Double, V> {
	Double2ObjectSortedMap<V> subMap(double double1, double double2);

	Double2ObjectSortedMap<V> headMap(double double1);

	Double2ObjectSortedMap<V> tailMap(double double1);

	double firstDoubleKey();

	double lastDoubleKey();

	@Deprecated
	default Double2ObjectSortedMap<V> subMap(Double from, Double to) {
		return this.subMap(from.doubleValue(), to.doubleValue());
	}

	@Deprecated
	default Double2ObjectSortedMap<V> headMap(Double to) {
		return this.headMap(to.doubleValue());
	}

	@Deprecated
	default Double2ObjectSortedMap<V> tailMap(Double from) {
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
		return this.double2ObjectEntrySet();
	}

	ObjectSortedSet<Entry<V>> double2ObjectEntrySet();

	DoubleSortedSet keySet();

	@Override
	ObjectCollection<V> values();

	DoubleComparator comparator();

	public interface FastSortedEntrySet<V> extends ObjectSortedSet<Entry<V>>, FastEntrySet<V> {
		ObjectBidirectionalIterator<Entry<V>> fastIterator();

		ObjectBidirectionalIterator<Entry<V>> fastIterator(Entry<V> entry);
	}
}
