package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntToDoubleFunction;

@FunctionalInterface
public interface Char2FloatFunction extends Function<Character, Float>, IntToDoubleFunction {
	@Deprecated
	default double applyAsDouble(int operand) {
		return (double)this.get(SafeMath.safeIntToChar(operand));
	}

	default float put(char key, float value) {
		throw new UnsupportedOperationException();
	}

	float get(char character);

	default float remove(char key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Float put(Character key, Float value) {
		char k = key;
		boolean containsKey = this.containsKey(k);
		float v = this.put(k, value.floatValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Float get(Object key) {
		if (key == null) {
			return null;
		} else {
			char k = (Character)key;
			float v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Float remove(Object key) {
		if (key == null) {
			return null;
		} else {
			char k = (Character)key;
			return this.containsKey(k) ? this.remove(k) : null;
		}
	}

	default boolean containsKey(char key) {
		return true;
	}

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return key == null ? false : this.containsKey(((Character)key).charValue());
	}

	default void defaultReturnValue(float rv) {
		throw new UnsupportedOperationException();
	}

	default float defaultReturnValue() {
		return 0.0F;
	}
}
