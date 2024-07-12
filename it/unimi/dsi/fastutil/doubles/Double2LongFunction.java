package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import java.util.function.DoubleToLongFunction;

@FunctionalInterface
public interface Double2LongFunction extends Function<Double, Long>, DoubleToLongFunction {
	default long applyAsLong(double operand) {
		return this.get(operand);
	}

	default long put(double key, long value) {
		throw new UnsupportedOperationException();
	}

	long get(double double1);

	default long remove(double key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Long put(Double key, Long value) {
		double k = key;
		boolean containsKey = this.containsKey(k);
		long v = this.put(k, value.longValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Long get(Object key) {
		if (key == null) {
			return null;
		} else {
			double k = (Double)key;
			long v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Long remove(Object key) {
		if (key == null) {
			return null;
		} else {
			double k = (Double)key;
			return this.containsKey(k) ? this.remove(k) : null;
		}
	}

	default boolean containsKey(double key) {
		return true;
	}

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return key == null ? false : this.containsKey(((Double)key).doubleValue());
	}

	default void defaultReturnValue(long rv) {
		throw new UnsupportedOperationException();
	}

	default long defaultReturnValue() {
		return 0L;
	}
}
