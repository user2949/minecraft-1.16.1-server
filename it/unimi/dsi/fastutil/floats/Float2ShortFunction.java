package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.DoubleToIntFunction;

@FunctionalInterface
public interface Float2ShortFunction extends Function<Float, Short>, DoubleToIntFunction {
	@Deprecated
	default int applyAsInt(double operand) {
		return this.get(SafeMath.safeDoubleToFloat(operand));
	}

	default short put(float key, short value) {
		throw new UnsupportedOperationException();
	}

	short get(float float1);

	default short remove(float key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Short put(Float key, Short value) {
		float k = key;
		boolean containsKey = this.containsKey(k);
		short v = this.put(k, value.shortValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Short get(Object key) {
		if (key == null) {
			return null;
		} else {
			float k = (Float)key;
			short v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Short remove(Object key) {
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

	default void defaultReturnValue(short rv) {
		throw new UnsupportedOperationException();
	}

	default short defaultReturnValue() {
		return 0;
	}
}
