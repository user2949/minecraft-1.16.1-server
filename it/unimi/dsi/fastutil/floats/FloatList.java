package it.unimi.dsi.fastutil.floats;

import java.util.List;

public interface FloatList extends List<Float>, Comparable<List<? extends Float>>, FloatCollection {
	FloatListIterator iterator();

	FloatListIterator listIterator();

	FloatListIterator listIterator(int integer);

	FloatList subList(int integer1, int integer2);

	void size(int integer);

	void getElements(int integer1, float[] arr, int integer3, int integer4);

	void removeElements(int integer1, int integer2);

	void addElements(int integer, float[] arr);

	void addElements(int integer1, float[] arr, int integer3, int integer4);

	@Override
	boolean add(float float1);

	void add(int integer, float float2);

	@Deprecated
	default void add(int index, Float key) {
		this.add(index, key.floatValue());
	}

	boolean addAll(int integer, FloatCollection floatCollection);

	boolean addAll(int integer, FloatList floatList);

	boolean addAll(FloatList floatList);

	float set(int integer, float float2);

	float getFloat(int integer);

	int indexOf(float float1);

	int lastIndexOf(float float1);

	@Deprecated
	@Override
	default boolean contains(Object key) {
		return FloatCollection.super.contains(key);
	}

	@Deprecated
	default Float get(int index) {
		return this.getFloat(index);
	}

	@Deprecated
	default int indexOf(Object o) {
		return this.indexOf(((Float)o).floatValue());
	}

	@Deprecated
	default int lastIndexOf(Object o) {
		return this.lastIndexOf(((Float)o).floatValue());
	}

	@Deprecated
	@Override
	default boolean add(Float k) {
		return this.add(k.floatValue());
	}

	float removeFloat(int integer);

	@Deprecated
	@Override
	default boolean remove(Object key) {
		return FloatCollection.super.remove(key);
	}

	@Deprecated
	default Float remove(int index) {
		return this.removeFloat(index);
	}

	@Deprecated
	default Float set(int index, Float k) {
		return this.set(index, k.floatValue());
	}
}
