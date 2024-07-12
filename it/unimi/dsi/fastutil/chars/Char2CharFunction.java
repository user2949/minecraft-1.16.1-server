package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntUnaryOperator;

@FunctionalInterface
public interface Char2CharFunction extends Function<Character, Character>, IntUnaryOperator {
	@Deprecated
	default int applyAsInt(int operand) {
		return this.get(SafeMath.safeIntToChar(operand));
	}

	default char put(char key, char value) {
		throw new UnsupportedOperationException();
	}

	char get(char character);

	default char remove(char key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Character put(Character key, Character value) {
		char k = key;
		boolean containsKey = this.containsKey(k);
		char v = this.put(k, value.charValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Character get(Object key) {
		if (key == null) {
			return null;
		} else {
			char k = (Character)key;
			char v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Character remove(Object key) {
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

	default void defaultReturnValue(char rv) {
		throw new UnsupportedOperationException();
	}

	default char defaultReturnValue() {
		return '\u0000';
	}
}
