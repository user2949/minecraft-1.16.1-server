package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntToLongFunction;

@FunctionalInterface
public interface Char2LongFunction extends Function<Character, Long>, IntToLongFunction {
	@Deprecated
	default long applyAsLong(int operand) {
		return this.get(SafeMath.safeIntToChar(operand));
	}

	default long put(char key, long value) {
		throw new UnsupportedOperationException();
	}

	long get(char character);

	default long remove(char key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Long put(Character key, Long value) {
		char k = key;
		boolean containsKey = this.containsKey(k);
		long v = this.put(k, value.longValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Long get(Object key) {
		if (key == null) {
			return null;
		} else {
			char k = (Character)key;
			long v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Long remove(Object key) {
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

	default void defaultReturnValue(long rv) {
		throw new UnsupportedOperationException();
	}

	default long defaultReturnValue() {
		return 0L;
	}
}
