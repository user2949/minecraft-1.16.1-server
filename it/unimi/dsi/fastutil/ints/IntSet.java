package it.unimi.dsi.fastutil.ints;

import java.util.Set;

public interface IntSet extends IntCollection, Set<Integer> {
	@Override
	IntIterator iterator();

	boolean remove(int integer);

	@Deprecated
	@Override
	default boolean remove(Object o) {
		return IntCollection.super.remove(o);
	}

	@Deprecated
	@Override
	default boolean add(Integer o) {
		return IntCollection.super.add(o);
	}

	@Deprecated
	@Override
	default boolean contains(Object o) {
		return IntCollection.super.contains(o);
	}

	@Deprecated
	@Override
	default boolean rem(int k) {
		return this.remove(k);
	}
}
