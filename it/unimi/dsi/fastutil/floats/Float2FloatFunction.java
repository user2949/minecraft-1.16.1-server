package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.DoubleUnaryOperator;

@FunctionalInterface
public interface Float2FloatFunction extends Function<Float, Float>, DoubleUnaryOperator {
	@Deprecated
	default double applyAsDouble(double operand) {
		return (double)this.get(SafeMath.safeDoubleToFloat(operand));
	}

	default float put(float key, float value) {
		throw new UnsupportedOperationException();
	}

	float get(float float1);

	default float remove(float key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Float put(Float key, Float value) {
		float k = key;
		boolean containsKey = this.containsKey(k);
		float v = this.put(k, value.floatValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Float get(Object key) {
		if (key == null) {
			return null;
		} else {
			float k = (Float)key;
			float v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Float remove(Object key) {
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

	default void defaultReturnValue(float rv) {
		throw new UnsupportedOperationException();
	}

	default float defaultReturnValue() {
		return 0.0F;
	}
}
