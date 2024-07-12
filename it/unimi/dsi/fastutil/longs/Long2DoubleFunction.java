package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import java.util.function.LongToDoubleFunction;

@FunctionalInterface
public interface Long2DoubleFunction extends Function<Long, Double>, LongToDoubleFunction {
	default double applyAsDouble(long operand) {
		return this.get(operand);
	}

	default double put(long key, double value) {
		throw new UnsupportedOperationException();
	}

	double get(long long1);

	default double remove(long key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Double put(Long key, Double value) {
		long k = key;
		boolean containsKey = this.containsKey(k);
		double v = this.put(k, value.doubleValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Double get(Object key) {
		if (key == null) {
			return null;
		} else {
			long k = (Long)key;
			double v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Double remove(Object key) {
		if (key == null) {
			return null;
		} else {
			long k = (Long)key;
			return this.containsKey(k) ? this.remove(k) : null;
		}
	}

	default boolean containsKey(long key) {
		return true;
	}

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return key == null ? false : this.containsKey(((Long)key).longValue());
	}

	default void defaultReturnValue(double rv) {
		throw new UnsupportedOperationException();
	}

	default double defaultReturnValue() {
		return 0.0;
	}
}
