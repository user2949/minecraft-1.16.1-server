package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.Size64;

public interface LongBigList extends BigList<Long>, LongCollection, Size64, Comparable<BigList<? extends Long>> {
	LongBigListIterator iterator();

	LongBigListIterator listIterator();

	LongBigListIterator listIterator(long long1);

	LongBigList subList(long long1, long long2);

	void getElements(long long1, long[][] arr, long long3, long long4);

	void removeElements(long long1, long long2);

	void addElements(long long1, long[][] arr);

	void addElements(long long1, long[][] arr, long long3, long long4);

	void add(long long1, long long2);

	boolean addAll(long long1, LongCollection longCollection);

	boolean addAll(long long1, LongBigList longBigList);

	boolean addAll(LongBigList longBigList);

	long getLong(long long1);

	long removeLong(long long1);

	long set(long long1, long long2);

	long indexOf(long long1);

	long lastIndexOf(long long1);

	@Deprecated
	void add(long long1, Long long2);

	@Deprecated
	Long get(long long1);

	@Deprecated
	@Override
	long indexOf(Object object);

	@Deprecated
	@Override
	long lastIndexOf(Object object);

	@Deprecated
	Long remove(long long1);

	@Deprecated
	Long set(long long1, Long long2);
}
