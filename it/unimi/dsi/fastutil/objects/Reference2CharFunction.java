package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import java.util.function.ToIntFunction;

@FunctionalInterface
public interface Reference2CharFunction<K> extends Function<K, Character>, ToIntFunction<K> {
	default int applyAsInt(K operand) {
		return this.getChar(operand);
	}

	default char put(K key, char value) {
		throw new UnsupportedOperationException();
	}

	char getChar(Object object);

	default char removeChar(Object key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Character put(K key, Character value) {
		boolean containsKey = this.containsKey(key);
		char v = this.put(key, value.charValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Character get(Object key) {
		char v = this.getChar(key);
		return v == this.defaultReturnValue() && !this.containsKey(key) ? null : v;
	}

	@Deprecated
	default Character remove(Object key) {
		return this.containsKey(key) ? this.removeChar(key) : null;
	}

	default void defaultReturnValue(char rv) {
		throw new UnsupportedOperationException();
	}

	default char defaultReturnValue() {
		return '\u0000';
	}
}
