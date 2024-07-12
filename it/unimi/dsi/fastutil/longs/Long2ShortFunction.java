package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import java.util.function.LongToIntFunction;

@FunctionalInterface
public interface Long2ShortFunction extends Function<Long, Short>, LongToIntFunction {
	default int applyAsInt(long operand) {
		return this.get(operand);
	}

	default short put(long key, short value) {
		throw new UnsupportedOperationException();
	}

	short get(long long1);

	default short remove(long key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Short put(Long key, Short value) {
		long k = key;
		boolean containsKey = this.containsKey(k);
		short v = this.put(k, value.shortValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Short get(Object key) {
		if (key == null) {
			return null;
		} else {
			long k = (Long)key;
			short v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Short remove(Object key) {
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

	default void defaultReturnValue(short rv) {
		throw new UnsupportedOperationException();
	}

	default short defaultReturnValue() {
		return 0;
	}
}
