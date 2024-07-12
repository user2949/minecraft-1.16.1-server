package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import java.util.function.DoubleUnaryOperator;

@FunctionalInterface
public interface Double2DoubleFunction extends Function<Double, Double>, DoubleUnaryOperator {
	default double applyAsDouble(double operand) {
		return this.get(operand);
	}

	default double put(double key, double value) {
		throw new UnsupportedOperationException();
	}

	double get(double double1);

	default double remove(double key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Double put(Double key, Double value) {
		double k = key;
		boolean containsKey = this.containsKey(k);
		double v = this.put(k, value.doubleValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Double get(Object key) {
		if (key == null) {
			return null;
		} else {
			double k = (Double)key;
			double v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Double remove(Object key) {
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

	default void defaultReturnValue(double rv) {
		throw new UnsupportedOperationException();
	}

	default double defaultReturnValue() {
		return 0.0;
	}
}
