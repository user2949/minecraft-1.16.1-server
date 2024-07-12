package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import java.util.function.DoubleUnaryOperator;

@FunctionalInterface
public interface Double2FloatFunction extends Function<Double, Float>, DoubleUnaryOperator {
	default double applyAsDouble(double operand) {
		return (double)this.get(operand);
	}

	default float put(double key, float value) {
		throw new UnsupportedOperationException();
	}

	float get(double double1);

	default float remove(double key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Float put(Double key, Float value) {
		double k = key;
		boolean containsKey = this.containsKey(k);
		float v = this.put(k, value.floatValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Float get(Object key) {
		if (key == null) {
			return null;
		} else {
			double k = (Double)key;
			float v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Float remove(Object key) {
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

	default void defaultReturnValue(float rv) {
		throw new UnsupportedOperationException();
	}

	default float defaultReturnValue() {
		return 0.0F;
	}
}
