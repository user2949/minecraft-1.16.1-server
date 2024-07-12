package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;
import java.util.function.IntToLongFunction;

@FunctionalInterface
public interface Int2LongFunction extends Function<Integer, Long>, IntToLongFunction {
	default long applyAsLong(int operand) {
		return this.get(operand);
	}

	default long put(int key, long value) {
		throw new UnsupportedOperationException();
	}

	long get(int integer);

	default long remove(int key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Long put(Integer key, Long value) {
		int k = key;
		boolean containsKey = this.containsKey(k);
		long v = this.put(k, value.longValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Long get(Object key) {
		if (key == null) {
			return null;
		} else {
			int k = (Integer)key;
			long v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Long remove(Object key) {
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

	default void defaultReturnValue(long rv) {
		throw new UnsupportedOperationException();
	}

	default long defaultReturnValue() {
		return 0L;
	}
}
