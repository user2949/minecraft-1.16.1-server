package it.unimi.dsi.fastutil.floats;

import java.util.Set;

public interface FloatSet extends FloatCollection, Set<Float> {
	@Override
	FloatIterator iterator();

	boolean remove(float float1);

	@Deprecated
	@Override
	default boolean remove(Object o) {
		return FloatCollection.super.remove(o);
	}

	@Deprecated
	@Override
	default boolean add(Float o) {
		return FloatCollection.super.add(o);
	}

	@Deprecated
	@Override
	default boolean contains(Object o) {
		return FloatCollection.super.contains(o);
	}

	@Deprecated
	@Override
	default boolean rem(float k) {
		return this.remove(k);
	}
}
