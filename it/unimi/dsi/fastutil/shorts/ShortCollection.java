package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.SafeMath;
import java.util.Collection;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public interface ShortCollection extends Collection<Short>, ShortIterable {
	@Override
	ShortIterator iterator();

	boolean add(short short1);

	boolean contains(short short1);

	boolean rem(short short1);

	@Deprecated
	default boolean add(Short key) {
		return this.add(key.shortValue());
	}

	@Deprecated
	default boolean contains(Object key) {
		return key == null ? false : this.contains(((Short)key).shortValue());
	}

	@Deprecated
	default boolean remove(Object key) {
		return key == null ? false : this.rem((Short)key);
	}

	short[] toShortArray();

	@Deprecated
	short[] toShortArray(short[] arr);

	short[] toArray(short[] arr);

	boolean addAll(ShortCollection shortCollection);

	boolean containsAll(ShortCollection shortCollection);

	boolean removeAll(ShortCollection shortCollection);

	@Deprecated
	default boolean removeIf(Predicate<? super Short> filter) {
		return this.removeIf((IntPredicate)(key -> filter.test(SafeMath.safeIntToShort(key))));
	}

	default boolean removeIf(IntPredicate filter) {
		boolean removed = false;
		ShortIterator each = this.iterator();

		while (each.hasNext()) {
			if (filter.test(each.nextShort())) {
				each.remove();
				removed = true;
			}
		}

		return removed;
	}

	boolean retainAll(ShortCollection shortCollection);
}
