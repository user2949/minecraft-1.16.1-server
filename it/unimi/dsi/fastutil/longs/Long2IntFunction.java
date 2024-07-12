package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import java.util.function.LongToIntFunction;

@FunctionalInterface
public interface Long2IntFunction extends Function<Long, Integer>, LongToIntFunction {
	default int applyAsInt(long operand) {
		return this.get(operand);
	}

	default int put(long key, int value) {
		throw new UnsupportedOperationException();
	}

	int get(long long1);

	default int remove(long key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Integer put(Long key, Integer value) {
		long k = key;
		boolean containsKey = this.containsKey(k);
		int v = this.put(k, value.intValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Integer get(Object key) {
		if (key == null) {
			return null;
		} else {
			long k = (Long)key;
			int v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Integer remove(Object key) {
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

	default void defaultReturnValue(int rv) {
		throw new UnsupportedOperationException();
	}

	default int defaultReturnValue() {
		return 0;
	}
}
