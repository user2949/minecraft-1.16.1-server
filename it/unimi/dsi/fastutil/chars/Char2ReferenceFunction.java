package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntFunction;

@FunctionalInterface
public interface Char2ReferenceFunction<V> extends Function<Character, V>, IntFunction<V> {
	@Deprecated
	default V apply(int operand) {
		return this.get(SafeMath.safeIntToChar(operand));
	}

	default V put(char key, V value) {
		throw new UnsupportedOperationException();
	}

	V get(char character);

	default V remove(char key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default V put(Character key, V value) {
		char k = key;
		boolean containsKey = this.containsKey(k);
		V v = this.put(k, value);
		return containsKey ? v : null;
	}

	@Deprecated
	@Override
	default V get(Object key) {
		if (key == null) {
			return null;
		} else {
			char k = (Character)key;
			V v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	@Override
	default V remove(Object key) {
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

	default void defaultReturnValue(V rv) {
		throw new UnsupportedOperationException();
	}

	default V defaultReturnValue() {
		return null;
	}
}
