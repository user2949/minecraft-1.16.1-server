package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import java.util.function.LongFunction;

@FunctionalInterface
public interface Long2ReferenceFunction<V> extends Function<Long, V>, LongFunction<V> {
	default V apply(long operand) {
		return this.get(operand);
	}

	default V put(long key, V value) {
		throw new UnsupportedOperationException();
	}

	V get(long long1);

	default V remove(long key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default V put(Long key, V value) {
		long k = key;
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
			long k = (Long)key;
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

	default void defaultReturnValue(V rv) {
		throw new UnsupportedOperationException();
	}

	default V defaultReturnValue() {
		return null;
	}
}
