package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import java.util.function.LongPredicate;

@FunctionalInterface
public interface Long2BooleanFunction extends Function<Long, Boolean>, LongPredicate {
	default boolean test(long operand) {
		return this.get(operand);
	}

	default boolean put(long key, boolean value) {
		throw new UnsupportedOperationException();
	}

	boolean get(long long1);

	default boolean remove(long key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Boolean put(Long key, Boolean value) {
		long k = key;
		boolean containsKey = this.containsKey(k);
		boolean v = this.put(k, value.booleanValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Boolean get(Object key) {
		if (key == null) {
			return null;
		} else {
			long k = (Long)key;
			boolean v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Boolean remove(Object key) {
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

	default void defaultReturnValue(boolean rv) {
		throw new UnsupportedOperationException();
	}

	default boolean defaultReturnValue() {
		return false;
	}
}
