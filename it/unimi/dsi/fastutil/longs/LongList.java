package it.unimi.dsi.fastutil.longs;

import java.util.List;

public interface LongList extends List<Long>, Comparable<List<? extends Long>>, LongCollection {
	LongListIterator iterator();

	LongListIterator listIterator();

	LongListIterator listIterator(int integer);

	LongList subList(int integer1, int integer2);

	void size(int integer);

	void getElements(int integer1, long[] arr, int integer3, int integer4);

	void removeElements(int integer1, int integer2);

	void addElements(int integer, long[] arr);

	void addElements(int integer1, long[] arr, int integer3, int integer4);

	@Override
	boolean add(long long1);

	void add(int integer, long long2);

	@Deprecated
	default void add(int index, Long key) {
		this.add(index, key.longValue());
	}

	boolean addAll(int integer, LongCollection longCollection);

	boolean addAll(int integer, LongList longList);

	boolean addAll(LongList longList);

	long set(int integer, long long2);

	long getLong(int integer);

	int indexOf(long long1);

	int lastIndexOf(long long1);

	@Deprecated
	@Override
	default boolean contains(Object key) {
		return LongCollection.super.contains(key);
	}

	@Deprecated
	default Long get(int index) {
		return this.getLong(index);
	}

	@Deprecated
	default int indexOf(Object o) {
		return this.indexOf(((Long)o).longValue());
	}

	@Deprecated
	default int lastIndexOf(Object o) {
		return this.lastIndexOf(((Long)o).longValue());
	}

	@Deprecated
	@Override
	default boolean add(Long k) {
		return this.add(k.longValue());
	}

	long removeLong(int integer);

	@Deprecated
	@Override
	default boolean remove(Object key) {
		return LongCollection.super.remove(key);
	}

	@Deprecated
	default Long remove(int index) {
		return this.removeLong(index);
	}

	@Deprecated
	default Long set(int index, Long k) {
		return this.set(index, k.longValue());
	}
}
