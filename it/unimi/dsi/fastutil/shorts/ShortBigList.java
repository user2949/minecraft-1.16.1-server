package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.Size64;

public interface ShortBigList extends BigList<Short>, ShortCollection, Size64, Comparable<BigList<? extends Short>> {
	ShortBigListIterator iterator();

	ShortBigListIterator listIterator();

	ShortBigListIterator listIterator(long long1);

	ShortBigList subList(long long1, long long2);

	void getElements(long long1, short[][] arr, long long3, long long4);

	void removeElements(long long1, long long2);

	void addElements(long long1, short[][] arr);

	void addElements(long long1, short[][] arr, long long3, long long4);

	void add(long long1, short short2);

	boolean addAll(long long1, ShortCollection shortCollection);

	boolean addAll(long long1, ShortBigList shortBigList);

	boolean addAll(ShortBigList shortBigList);

	short getShort(long long1);

	short removeShort(long long1);

	short set(long long1, short short2);

	long indexOf(short short1);

	long lastIndexOf(short short1);

	@Deprecated
	void add(long long1, Short short2);

	@Deprecated
	Short get(long long1);

	@Deprecated
	@Override
	long indexOf(Object object);

	@Deprecated
	@Override
	long lastIndexOf(Object object);

	@Deprecated
	Short remove(long long1);

	@Deprecated
	Short set(long long1, Short short2);
}
