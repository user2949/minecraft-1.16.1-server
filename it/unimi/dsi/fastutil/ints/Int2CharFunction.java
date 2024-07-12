package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;
import java.util.function.IntUnaryOperator;

@FunctionalInterface
public interface Int2CharFunction extends Function<Integer, Character>, IntUnaryOperator {
	default int applyAsInt(int operand) {
		return this.get(operand);
	}

	default char put(int key, char value) {
		throw new UnsupportedOperationException();
	}

	char get(int integer);

	default char remove(int key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Character put(Integer key, Character value) {
		int k = key;
		boolean containsKey = this.containsKey(k);
		char v = this.put(k, value.charValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Character get(Object key) {
		if (key == null) {
			return null;
		} else {
			int k = (Integer)key;
			char v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Character remove(Object key) {
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

	default void defaultReturnValue(char rv) {
		throw new UnsupportedOperationException();
	}

	default char defaultReturnValue() {
		return '\u0000';
	}
}
