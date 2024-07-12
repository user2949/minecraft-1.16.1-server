package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import java.util.function.DoubleToIntFunction;

@FunctionalInterface
public interface Double2IntFunction extends Function<Double, Integer>, DoubleToIntFunction {
	default int applyAsInt(double operand) {
		return this.get(operand);
	}

	default int put(double key, int value) {
		throw new UnsupportedOperationException();
	}

	int get(double double1);

	default int remove(double key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Integer put(Double key, Integer value) {
		double k = key;
		boolean containsKey = this.containsKey(k);
		int v = this.put(k, value.intValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Integer get(Object key) {
		if (key == null) {
			return null;
		} else {
			double k = (Double)key;
			int v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Integer remove(Object key) {
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

	default void defaultReturnValue(int rv) {
		throw new UnsupportedOperationException();
	}

	default int defaultReturnValue() {
		return 0;
	}
}
