package it.unimi.dsi.fastutil.doubles;

import java.util.Collection;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;

public interface DoubleCollection extends Collection<Double>, DoubleIterable {
	@Override
	DoubleIterator iterator();

	boolean add(double double1);

	boolean contains(double double1);

	boolean rem(double double1);

	@Deprecated
	default boolean add(Double key) {
		return this.add(key.doubleValue());
	}

	@Deprecated
	default boolean contains(Object key) {
		return key == null ? false : this.contains(((Double)key).doubleValue());
	}

	@Deprecated
	default boolean remove(Object key) {
		return key == null ? false : this.rem((Double)key);
	}

	double[] toDoubleArray();

	@Deprecated
	double[] toDoubleArray(double[] arr);

	double[] toArray(double[] arr);

	boolean addAll(DoubleCollection doubleCollection);

	boolean containsAll(DoubleCollection doubleCollection);

	boolean removeAll(DoubleCollection doubleCollection);

	@Deprecated
	default boolean removeIf(Predicate<? super Double> filter) {
		return this.removeIf((DoublePredicate)(key -> filter.test(key)));
	}

	default boolean removeIf(DoublePredicate filter) {
		boolean removed = false;
		DoubleIterator each = this.iterator();

		while (each.hasNext()) {
			if (filter.test(each.nextDouble())) {
				each.remove();
				removed = true;
			}
		}

		return removed;
	}

	boolean retainAll(DoubleCollection doubleCollection);
}
