package it.unimi.dsi.fastutil.booleans;

import java.util.List;

public interface BooleanList extends List<Boolean>, Comparable<List<? extends Boolean>>, BooleanCollection {
	BooleanListIterator iterator();

	BooleanListIterator listIterator();

	BooleanListIterator listIterator(int integer);

	BooleanList subList(int integer1, int integer2);

	void size(int integer);

	void getElements(int integer1, boolean[] arr, int integer3, int integer4);

	void removeElements(int integer1, int integer2);

	void addElements(int integer, boolean[] arr);

	void addElements(int integer1, boolean[] arr, int integer3, int integer4);

	@Override
	boolean add(boolean boolean1);

	void add(int integer, boolean boolean2);

	@Deprecated
	default void add(int index, Boolean key) {
		this.add(index, key.booleanValue());
	}

	boolean addAll(int integer, BooleanCollection booleanCollection);

	boolean addAll(int integer, BooleanList booleanList);

	boolean addAll(BooleanList booleanList);

	boolean set(int integer, boolean boolean2);

	boolean getBoolean(int integer);

	int indexOf(boolean boolean1);

	int lastIndexOf(boolean boolean1);

	@Deprecated
	@Override
	default boolean contains(Object key) {
		return BooleanCollection.super.contains(key);
	}

	@Deprecated
	default Boolean get(int index) {
		return this.getBoolean(index);
	}

	@Deprecated
	default int indexOf(Object o) {
		return this.indexOf(((Boolean)o).booleanValue());
	}

	@Deprecated
	default int lastIndexOf(Object o) {
		return this.lastIndexOf(((Boolean)o).booleanValue());
	}

	@Deprecated
	@Override
	default boolean add(Boolean k) {
		return this.add(k.booleanValue());
	}

	boolean removeBoolean(int integer);

	@Deprecated
	@Override
	default boolean remove(Object key) {
		return BooleanCollection.super.remove(key);
	}

	@Deprecated
	default Boolean remove(int index) {
		return this.removeBoolean(index);
	}

	@Deprecated
	default Boolean set(int index, Boolean k) {
		return this.set(index, k.booleanValue());
	}
}
