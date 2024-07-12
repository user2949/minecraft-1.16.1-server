package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.DoubleUnaryOperator;

@FunctionalInterface
public interface Float2DoubleFunction extends Function<Float, Double>, DoubleUnaryOperator {
	@Deprecated
	default double applyAsDouble(double operand) {
		return this.get(SafeMath.safeDoubleToFloat(operand));
	}

	default double put(float key, double value) {
		throw new UnsupportedOperationException();
	}

	double get(float float1);

	default double remove(float key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Double put(Float key, Double value) {
		float k = key;
		boolean containsKey = this.containsKey(k);
		double v = this.put(k, value.doubleValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Double get(Object key) {
		if (key == null) {
			return null;
		} else {
			float k = (Float)key;
			double v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Double remove(Object key) {
		if (key == null) {
			return null;
		} else {
			float k = (Float)key;
			return this.containsKey(k) ? this.remove(k) : null;
		}
	}

	default boolean containsKey(float key) {
		return true;
	}

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return key == null ? false : this.containsKey(((Float)key).floatValue());
	}

	default void defaultReturnValue(double rv) {
		throw new UnsupportedOperationException();
	}

	default double defaultReturnValue() {
		return 0.0;
	}
}
