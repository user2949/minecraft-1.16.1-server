package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.DoubleToIntFunction;

@FunctionalInterface
public interface Float2IntFunction extends Function<Float, Integer>, DoubleToIntFunction {
	@Deprecated
	default int applyAsInt(double operand) {
		return this.get(SafeMath.safeDoubleToFloat(operand));
	}

	default int put(float key, int value) {
		throw new UnsupportedOperationException();
	}

	int get(float float1);

	default int remove(float key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Integer put(Float key, Integer value) {
		float k = key;
		boolean containsKey = this.containsKey(k);
		int v = this.put(k, value.intValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Integer get(Object key) {
		if (key == null) {
			return null;
		} else {
			float k = (Float)key;
			int v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Integer remove(Object key) {
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

	default void defaultReturnValue(int rv) {
		throw new UnsupportedOperationException();
	}

	default int defaultReturnValue() {
		return 0;
	}
}
