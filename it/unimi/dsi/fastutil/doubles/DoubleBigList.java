package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.Size64;

public interface DoubleBigList extends BigList<Double>, DoubleCollection, Size64, Comparable<BigList<? extends Double>> {
	DoubleBigListIterator iterator();

	DoubleBigListIterator listIterator();

	DoubleBigListIterator listIterator(long long1);

	DoubleBigList subList(long long1, long long2);

	void getElements(long long1, double[][] arr, long long3, long long4);

	void removeElements(long long1, long long2);

	void addElements(long long1, double[][] arr);

	void addElements(long long1, double[][] arr, long long3, long long4);

	void add(long long1, double double2);

	boolean addAll(long long1, DoubleCollection doubleCollection);

	boolean addAll(long long1, DoubleBigList doubleBigList);

	boolean addAll(DoubleBigList doubleBigList);

	double getDouble(long long1);

	double removeDouble(long long1);

	double set(long long1, double double2);

	long indexOf(double double1);

	long lastIndexOf(double double1);

	@Deprecated
	void add(long long1, Double double2);

	@Deprecated
	Double get(long long1);

	@Deprecated
	@Override
	long indexOf(Object object);

	@Deprecated
	@Override
	long lastIndexOf(Object object);

	@Deprecated
	Double remove(long long1);

	@Deprecated
	Double set(long long1, Double double2);
}
