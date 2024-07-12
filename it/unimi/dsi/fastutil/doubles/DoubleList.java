package it.unimi.dsi.fastutil.doubles;

import java.util.List;

public interface DoubleList extends List<Double>, Comparable<List<? extends Double>>, DoubleCollection {
	DoubleListIterator iterator();

	DoubleListIterator listIterator();

	DoubleListIterator listIterator(int integer);

	DoubleList subList(int integer1, int integer2);

	void size(int integer);

	void getElements(int integer1, double[] arr, int integer3, int integer4);

	void removeElements(int integer1, int integer2);

	void addElements(int integer, double[] arr);

	void addElements(int integer1, double[] arr, int integer3, int integer4);

	@Override
	boolean add(double double1);

	void add(int integer, double double2);

	@Deprecated
	default void add(int index, Double key) {
		this.add(index, key.doubleValue());
	}

	boolean addAll(int integer, DoubleCollection doubleCollection);

	boolean addAll(int integer, DoubleList doubleList);

	boolean addAll(DoubleList doubleList);

	double set(int integer, double double2);

	double getDouble(int integer);

	int indexOf(double double1);

	int lastIndexOf(double double1);

	@Deprecated
	@Override
	default boolean contains(Object key) {
		return DoubleCollection.super.contains(key);
	}

	@Deprecated
	default Double get(int index) {
		return this.getDouble(index);
	}

	@Deprecated
	default int indexOf(Object o) {
		return this.indexOf(((Double)o).doubleValue());
	}

	@Deprecated
	default int lastIndexOf(Object o) {
		return this.lastIndexOf(((Double)o).doubleValue());
	}

	@Deprecated
	@Override
	default boolean add(Double k) {
		return this.add(k.doubleValue());
	}

	double removeDouble(int integer);

	@Deprecated
	@Override
	default boolean remove(Object key) {
		return DoubleCollection.super.remove(key);
	}

	@Deprecated
	default Double remove(int index) {
		return this.removeDouble(index);
	}

	@Deprecated
	default Double set(int index, Double k) {
		return this.set(index, k.doubleValue());
	}
}
