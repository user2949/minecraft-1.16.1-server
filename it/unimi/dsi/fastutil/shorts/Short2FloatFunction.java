package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntToDoubleFunction;

@FunctionalInterface
public interface Short2FloatFunction extends Function<Short, Float>, IntToDoubleFunction {
	@Deprecated
	default double applyAsDouble(int operand) {
		return (double)this.get(SafeMath.safeIntToShort(operand));
	}

	default float put(short key, float value) {
		throw new UnsupportedOperationException();
	}

	float get(short short1);

	default float remove(short key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Float put(Short key, Float value) {
		short k = key;
		boolean containsKey = this.containsKey(k);
		float v = this.put(k, value.floatValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Float get(Object key) {
		if (key == null) {
			return null;
		} else {
			short k = (Short)key;
			float v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Float remove(Object key) {
		if (key == null) {
			return null;
		} else {
			short k = (Short)key;
			return this.containsKey(k) ? this.remove(k) : null;
		}
	}

	default boolean containsKey(short key) {
		return true;
	}

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return key == null ? false : this.containsKey(((Short)key).shortValue());
	}

	default void defaultReturnValue(float rv) {
		throw new UnsupportedOperationException();
	}

	default float defaultReturnValue() {
		return 0.0F;
	}
}
