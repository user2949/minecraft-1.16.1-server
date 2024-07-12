package it.unimi.dsi.fastutil.ints;

import java.util.Collection;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public interface IntCollection extends Collection<Integer>, IntIterable {
	@Override
	IntIterator iterator();

	boolean add(int integer);

	boolean contains(int integer);

	boolean rem(int integer);

	@Deprecated
	default boolean add(Integer key) {
		return this.add(key.intValue());
	}

	@Deprecated
	default boolean contains(Object key) {
		return key == null ? false : this.contains(((Integer)key).intValue());
	}

	@Deprecated
	default boolean remove(Object key) {
		return key == null ? false : this.rem((Integer)key);
	}

	int[] toIntArray();

	@Deprecated
	int[] toIntArray(int[] arr);

	int[] toArray(int[] arr);

	boolean addAll(IntCollection intCollection);

	boolean containsAll(IntCollection intCollection);

	boolean removeAll(IntCollection intCollection);

	@Deprecated
	default boolean removeIf(Predicate<? super Integer> filter) {
		return this.removeIf((IntPredicate)(key -> filter.test(key)));
	}

	default boolean removeIf(IntPredicate filter) {
		boolean removed = false;
		IntIterator each = this.iterator();

		while (each.hasNext()) {
			if (filter.test(each.nextInt())) {
				each.remove();
				removed = true;
			}
		}

		return removed;
	}

	boolean retainAll(IntCollection intCollection);
}
