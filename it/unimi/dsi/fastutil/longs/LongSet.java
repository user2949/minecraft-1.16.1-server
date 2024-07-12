package it.unimi.dsi.fastutil.longs;

import java.util.Set;

public interface LongSet extends LongCollection, Set<Long> {
	@Override
	LongIterator iterator();

	boolean remove(long long1);

	@Deprecated
	@Override
	default boolean remove(Object o) {
		return LongCollection.super.remove(o);
	}

	@Deprecated
	@Override
	default boolean add(Long o) {
		return LongCollection.super.add(o);
	}

	@Deprecated
	@Override
	default boolean contains(Object o) {
		return LongCollection.super.contains(o);
	}

	@Deprecated
	@Override
	default boolean rem(long k) {
		return this.remove(k);
	}
}
