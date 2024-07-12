package it.unimi.dsi.fastutil.doubles;

import java.util.Set;

public interface DoubleSet extends DoubleCollection, Set<Double> {
	@Override
	DoubleIterator iterator();

	boolean remove(double double1);

	@Deprecated
	@Override
	default boolean remove(Object o) {
		return DoubleCollection.super.remove(o);
	}

	@Deprecated
	@Override
	default boolean add(Double o) {
		return DoubleCollection.super.add(o);
	}

	@Deprecated
	@Override
	default boolean contains(Object o) {
		return DoubleCollection.super.contains(o);
	}

	@Deprecated
	@Override
	default boolean rem(double k) {
		return this.remove(k);
	}
}
