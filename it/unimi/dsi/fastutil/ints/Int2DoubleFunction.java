package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;
import java.util.function.IntToDoubleFunction;

@FunctionalInterface
public interface Int2DoubleFunction extends Function<Integer, Double>, IntToDoubleFunction {
	default double applyAsDouble(int operand) {
		return this.get(operand);
	}

	default double put(int key, double value) {
		throw new UnsupportedOperationException();
	}

	double get(int integer);

	default double remove(int key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Double put(Integer key, Double value) {
		int k = key;
		boolean containsKey = this.containsKey(k);
		double v = this.put(k, value.doubleValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Double get(Object key) {
		if (key == null) {
			return null;
		} else {
			int k = (Integer)key;
			double v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Double remove(Object key) {
		if (key == null) {
			return null;
		} else {
			int k = (Integer)key;
			return this.containsKey(k) ? this.remove(k) : null;
		}
	}

	default boolean containsKey(int key) {
		return true;
	}

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return key == null ? false : this.containsKey(((Integer)key).intValue());
	}

	default void defaultReturnValue(double rv) {
		throw new UnsupportedOperationException();
	}

	default double defaultReturnValue() {
		return 0.0;
	}
}
