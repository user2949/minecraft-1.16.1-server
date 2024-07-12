package it.unimi.dsi.fastutil.shorts;

import java.util.List;

public interface ShortList extends List<Short>, Comparable<List<? extends Short>>, ShortCollection {
	ShortListIterator iterator();

	ShortListIterator listIterator();

	ShortListIterator listIterator(int integer);

	ShortList subList(int integer1, int integer2);

	void size(int integer);

	void getElements(int integer1, short[] arr, int integer3, int integer4);

	void removeElements(int integer1, int integer2);

	void addElements(int integer, short[] arr);

	void addElements(int integer1, short[] arr, int integer3, int integer4);

	@Override
	boolean add(short short1);

	void add(int integer, short short2);

	@Deprecated
	default void add(int index, Short key) {
		this.add(index, key.shortValue());
	}

	boolean addAll(int integer, ShortCollection shortCollection);

	boolean addAll(int integer, ShortList shortList);

	boolean addAll(ShortList shortList);

	short set(int integer, short short2);

	short getShort(int integer);

	int indexOf(short short1);

	int lastIndexOf(short short1);

	@Deprecated
	@Override
	default boolean contains(Object key) {
		return ShortCollection.super.contains(key);
	}

	@Deprecated
	default Short get(int index) {
		return this.getShort(index);
	}

	@Deprecated
	default int indexOf(Object o) {
		return this.indexOf(((Short)o).shortValue());
	}

	@Deprecated
	default int lastIndexOf(Object o) {
		return this.lastIndexOf(((Short)o).shortValue());
	}

	@Deprecated
	@Override
	default boolean add(Short k) {
		return this.add(k.shortValue());
	}

	short removeShort(int integer);

	@Deprecated
	@Override
	default boolean remove(Object key) {
		return ShortCollection.super.remove(key);
	}

	@Deprecated
	default Short remove(int index) {
		return this.removeShort(index);
	}

	@Deprecated
	default Short set(int index, Short k) {
		return this.set(index, k.shortValue());
	}
}
