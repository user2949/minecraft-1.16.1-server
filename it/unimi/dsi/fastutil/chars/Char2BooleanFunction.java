package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntPredicate;

@FunctionalInterface
public interface Char2BooleanFunction extends Function<Character, Boolean>, IntPredicate {
	@Deprecated
	default boolean test(int operand) {
		return this.get(SafeMath.safeIntToChar(operand));
	}

	default boolean put(char key, boolean value) {
		throw new UnsupportedOperationException();
	}

	boolean get(char character);

	default boolean remove(char key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Boolean put(Character key, Boolean value) {
		char k = key;
		boolean containsKey = this.containsKey(k);
		boolean v = this.put(k, value.booleanValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Boolean get(Object key) {
		if (key == null) {
			return null;
		} else {
			char k = (Character)key;
			boolean v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Boolean remove(Object key) {
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

	default void defaultReturnValue(boolean rv) {
		throw new UnsupportedOperationException();
	}

	default boolean defaultReturnValue() {
		return false;
	}
}
