package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import java.util.function.LongToIntFunction;

@FunctionalInterface
public interface Long2CharFunction extends Function<Long, Character>, LongToIntFunction {
	default int applyAsInt(long operand) {
		return this.get(operand);
	}

	default char put(long key, char value) {
		throw new UnsupportedOperationException();
	}

	char get(long long1);

	default char remove(long key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Character put(Long key, Character value) {
		long k = key;
		boolean containsKey = this.containsKey(k);
		char v = this.put(k, value.charValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Character get(Object key) {
		if (key == null) {
			return null;
		} else {
			long k = (Long)key;
			char v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Character remove(Object key) {
		if (key == null) {
			return null;
		} else {
			long k = (Long)key;
			return this.containsKey(k) ? this.remove(k) : null;
		}
	}

	default boolean containsKey(long key) {
		return true;
	}

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return key == null ? false : this.containsKey(((Long)key).longValue());
	}

	default void defaultReturnValue(char rv) {
		throw new UnsupportedOperationException();
	}

	default char defaultReturnValue() {
		return '\u0000';
	}
}
