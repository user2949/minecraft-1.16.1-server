package it.unimi.dsi.fastutil.booleans;

import java.util.Set;

public interface BooleanSet extends BooleanCollection, Set<Boolean> {
	@Override
	BooleanIterator iterator();

	boolean remove(boolean boolean1);

	@Deprecated
	@Override
	default boolean remove(Object o) {
		return BooleanCollection.super.remove(o);
	}

	@Deprecated
	@Override
	default boolean add(Boolean o) {
		return BooleanCollection.super.add(o);
	}

	@Deprecated
	@Override
	default boolean contains(Object o) {
		return BooleanCollection.super.contains(o);
	}

	@Deprecated
	@Override
	default boolean rem(boolean k) {
		return this.remove(k);
	}
}
