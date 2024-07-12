package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;
import java.util.function.IntToDoubleFunction;

@FunctionalInterface
public interface Int2FloatFunction extends Function<Integer, Float>, IntToDoubleFunction {
	default double applyAsDouble(int operand) {
		return (double)this.get(operand);
	}

	default float put(int key, float value) {
		throw new UnsupportedOperationException();
	}

	float get(int integer);

	default float remove(int key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Float put(Integer key, Float value) {
		int k = key;
		boolean containsKey = this.containsKey(k);
		float v = this.put(k, value.floatValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Float get(Object key) {
		if (key == null) {
			return null;
		} else {
			int k = (Integer)key;
			float v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Float remove(Object key) {
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

	default void defaultReturnValue(float rv) {
		throw new UnsupportedOperationException();
	}

	default float defaultReturnValue() {
		return 0.0F;
	}
}
