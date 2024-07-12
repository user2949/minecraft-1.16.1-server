package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.DoubleToLongFunction;

@FunctionalInterface
public interface Float2LongFunction extends Function<Float, Long>, DoubleToLongFunction {
	@Deprecated
	default long applyAsLong(double operand) {
		return this.get(SafeMath.safeDoubleToFloat(operand));
	}

	default long put(float key, long value) {
		throw new UnsupportedOperationException();
	}

	long get(float float1);

	default long remove(float key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Long put(Float key, Long value) {
		float k = key;
		boolean containsKey = this.containsKey(k);
		long v = this.put(k, value.longValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Long get(Object key) {
		if (key == null) {
			return null;
		} else {
			float k = (Float)key;
			long v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Long remove(Object key) {
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

	default void defaultReturnValue(long rv) {
		throw new UnsupportedOperationException();
	}

	default long defaultReturnValue() {
		return 0L;
	}
}
