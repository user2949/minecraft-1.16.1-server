package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.SafeMath;
import java.util.Collection;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;

public interface FloatCollection extends Collection<Float>, FloatIterable {
	@Override
	FloatIterator iterator();

	boolean add(float float1);

	boolean contains(float float1);

	boolean rem(float float1);

	@Deprecated
	default boolean add(Float key) {
		return this.add(key.floatValue());
	}

	@Deprecated
	default boolean contains(Object key) {
		return key == null ? false : this.contains(((Float)key).floatValue());
	}

	@Deprecated
	default boolean remove(Object key) {
		return key == null ? false : this.rem((Float)key);
	}

	float[] toFloatArray();

	@Deprecated
	float[] toFloatArray(float[] arr);

	float[] toArray(float[] arr);

	boolean addAll(FloatCollection floatCollection);

	boolean containsAll(FloatCollection floatCollection);

	boolean removeAll(FloatCollection floatCollection);

	@Deprecated
	default boolean removeIf(Predicate<? super Float> filter) {
		return this.removeIf((DoublePredicate)(key -> filter.test(SafeMath.safeDoubleToFloat(key))));
	}

	default boolean removeIf(DoublePredicate filter) {
		boolean removed = false;
		FloatIterator each = this.iterator();

		while (each.hasNext()) {
			if (filter.test((double)each.nextFloat())) {
				each.remove();
				removed = true;
			}
		}

		return removed;
	}

	boolean retainAll(FloatCollection floatCollection);
}
