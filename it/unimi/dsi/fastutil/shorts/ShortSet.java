package it.unimi.dsi.fastutil.shorts;

import java.util.Set;

public interface ShortSet extends ShortCollection, Set<Short> {
	@Override
	ShortIterator iterator();

	boolean remove(short short1);

	@Deprecated
	@Override
	default boolean remove(Object o) {
		return ShortCollection.super.remove(o);
	}

	@Deprecated
	@Override
	default boolean add(Short o) {
		return ShortCollection.super.add(o);
	}

	@Deprecated
	@Override
	default boolean contains(Object o) {
		return ShortCollection.super.contains(o);
	}

	@Deprecated
	@Override
	default boolean rem(short k) {
		return this.remove(k);
	}
}
