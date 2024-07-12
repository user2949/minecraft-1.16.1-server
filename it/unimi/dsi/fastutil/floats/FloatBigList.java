package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.Size64;

public interface FloatBigList extends BigList<Float>, FloatCollection, Size64, Comparable<BigList<? extends Float>> {
	FloatBigListIterator iterator();

	FloatBigListIterator listIterator();

	FloatBigListIterator listIterator(long long1);

	FloatBigList subList(long long1, long long2);

	void getElements(long long1, float[][] arr, long long3, long long4);

	void removeElements(long long1, long long2);

	void addElements(long long1, float[][] arr);

	void addElements(long long1, float[][] arr, long long3, long long4);

	void add(long long1, float float2);

	boolean addAll(long long1, FloatCollection floatCollection);

	boolean addAll(long long1, FloatBigList floatBigList);

	boolean addAll(FloatBigList floatBigList);

	float getFloat(long long1);

	float removeFloat(long long1);

	float set(long long1, float float2);

	long indexOf(float float1);

	long lastIndexOf(float float1);

	@Deprecated
	void add(long long1, Float float2);

	@Deprecated
	Float get(long long1);

	@Deprecated
	@Override
	long indexOf(Object object);

	@Deprecated
	@Override
	long lastIndexOf(Object object);

	@Deprecated
	Float remove(long long1);

	@Deprecated
	Float set(long long1, Float float2);
}
