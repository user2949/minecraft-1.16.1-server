package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.Size64;

public interface IntBigList extends BigList<Integer>, IntCollection, Size64, Comparable<BigList<? extends Integer>> {
	IntBigListIterator iterator();

	IntBigListIterator listIterator();

	IntBigListIterator listIterator(long long1);

	IntBigList subList(long long1, long long2);

	void getElements(long long1, int[][] arr, long long3, long long4);

	void removeElements(long long1, long long2);

	void addElements(long long1, int[][] arr);

	void addElements(long long1, int[][] arr, long long3, long long4);

	void add(long long1, int integer);

	boolean addAll(long long1, IntCollection intCollection);

	boolean addAll(long long1, IntBigList intBigList);

	boolean addAll(IntBigList intBigList);

	int getInt(long long1);

	int removeInt(long long1);

	int set(long long1, int integer);

	long indexOf(int integer);

	long lastIndexOf(int integer);

	@Deprecated
	void add(long long1, Integer integer);

	@Deprecated
	Integer get(long long1);

	@Deprecated
	@Override
	long indexOf(Object object);

	@Deprecated
	@Override
	long lastIndexOf(Object object);

	@Deprecated
	Integer remove(long long1);

	@Deprecated
	Integer set(long long1, Integer integer);
}
