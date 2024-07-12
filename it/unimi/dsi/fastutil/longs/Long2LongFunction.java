package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import java.util.function.LongUnaryOperator;

@FunctionalInterface
public interface Long2LongFunction extends Function<Long, Long>, LongUnaryOperator {
	default long applyAsLong(long operand) {
		return this.get(operand);
	}

	default long put(long key, long value) {
		throw new UnsupportedOperationException();
	}

	long get(long long1);

	default long remove(long key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Long put(Long key, Long value) {
		long k = key;
		boolean containsKey = this.containsKey(k);
		long v = this.put(k, value.longValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Long get(Object key) {
		if (key == null) {
			return null;
		} else {
			long k = (Long)key;
			long v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Long remove(Object key) {
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

	default void defaultReturnValue(long rv) {
		throw new UnsupportedOperationException();
	}

	default long defaultReturnValue() {
		return 0L;
	}
}
