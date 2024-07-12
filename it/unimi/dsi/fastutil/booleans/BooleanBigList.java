package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.Size64;

public interface BooleanBigList extends BigList<Boolean>, BooleanCollection, Size64, Comparable<BigList<? extends Boolean>> {
	BooleanBigListIterator iterator();

	BooleanBigListIterator listIterator();

	BooleanBigListIterator listIterator(long long1);

	BooleanBigList subList(long long1, long long2);

	void getElements(long long1, boolean[][] arr, long long3, long long4);

	void removeElements(long long1, long long2);

	void addElements(long long1, boolean[][] arr);

	void addElements(long long1, boolean[][] arr, long long3, long long4);

	void add(long long1, boolean boolean2);

	boolean addAll(long long1, BooleanCollection booleanCollection);

	boolean addAll(long long1, BooleanBigList booleanBigList);

	boolean addAll(BooleanBigList booleanBigList);

	boolean getBoolean(long long1);

	boolean removeBoolean(long long1);

	boolean set(long long1, boolean boolean2);

	long indexOf(boolean boolean1);

	long lastIndexOf(boolean boolean1);

	@Deprecated
	void add(long long1, Boolean boolean2);

	@Deprecated
	Boolean get(long long1);

	@Deprecated
	@Override
	long indexOf(Object object);

	@Deprecated
	@Override
	long lastIndexOf(Object object);

	@Deprecated
	Boolean remove(long long1);

	@Deprecated
	Boolean set(long long1, Boolean boolean2);
}
