package it.unimi.dsi.fastutil.longs;

import java.util.Collection;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

public interface LongCollection extends Collection<Long>, LongIterable {
	@Override
	LongIterator iterator();

	boolean add(long long1);

	boolean contains(long long1);

	boolean rem(long long1);

	@Deprecated
	default boolean add(Long key) {
		return this.add(key.longValue());
	}

	@Deprecated
	default boolean contains(Object key) {
		return key == null ? false : this.contains(((Long)key).longValue());
	}

	@Deprecated
	default boolean remove(Object key) {
		return key == null ? false : this.rem((Long)key);
	}

	long[] toLongArray();

	@Deprecated
	long[] toLongArray(long[] arr);

	long[] toArray(long[] arr);

	boolean addAll(LongCollection longCollection);

	boolean containsAll(LongCollection longCollection);

	boolean removeAll(LongCollection longCollection);

	@Deprecated
	default boolean removeIf(Predicate<? super Long> filter) {
		return this.removeIf((LongPredicate)(key -> filter.test(key)));
	}

	default boolean removeIf(LongPredicate filter) {
		boolean removed = false;
		LongIterator each = this.iterator();

		while (each.hasNext()) {
			if (filter.test(each.nextLong())) {
				each.remove();
				removed = true;
			}
		}

		return removed;
	}

	boolean retainAll(LongCollection longCollection);
}
