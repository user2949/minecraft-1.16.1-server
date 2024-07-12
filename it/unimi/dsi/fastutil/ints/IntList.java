package it.unimi.dsi.fastutil.ints;

import java.util.List;

public interface IntList extends List<Integer>, Comparable<List<? extends Integer>>, IntCollection {
	IntListIterator iterator();

	IntListIterator listIterator();

	IntListIterator listIterator(int integer);

	IntList subList(int integer1, int integer2);

	void size(int integer);

	void getElements(int integer1, int[] arr, int integer3, int integer4);

	void removeElements(int integer1, int integer2);

	void addElements(int integer, int[] arr);

	void addElements(int integer1, int[] arr, int integer3, int integer4);

	@Override
	boolean add(int integer);

	void add(int integer1, int integer2);

	@Deprecated
	default void add(int index, Integer key) {
		this.add(index, key.intValue());
	}

	boolean addAll(int integer, IntCollection intCollection);

	boolean addAll(int integer, IntList intList);

	boolean addAll(IntList intList);

	int set(int integer1, int integer2);

	int getInt(int integer);

	int indexOf(int integer);

	int lastIndexOf(int integer);

	@Deprecated
	@Override
	default boolean contains(Object key) {
		return IntCollection.super.contains(key);
	}

	@Deprecated
	default Integer get(int index) {
		return this.getInt(index);
	}

	@Deprecated
	default int indexOf(Object o) {
		return this.indexOf(((Integer)o).intValue());
	}

	@Deprecated
	default int lastIndexOf(Object o) {
		return this.lastIndexOf(((Integer)o).intValue());
	}

	@Deprecated
	@Override
	default boolean add(Integer k) {
		return this.add(k.intValue());
	}

	int removeInt(int integer);

	@Deprecated
	@Override
	default boolean remove(Object key) {
		return IntCollection.super.remove(key);
	}

	@Deprecated
	default Integer remove(int index) {
		return this.removeInt(index);
	}

	@Deprecated
	default Integer set(int index, Integer k) {
		return this.set(index, k.intValue());
	}
}
