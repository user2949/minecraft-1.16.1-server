package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import java.util.function.ToLongFunction;

@FunctionalInterface
public interface Reference2LongFunction<K> extends Function<K, Long>, ToLongFunction<K> {
	default long applyAsLong(K operand) {
		return this.getLong(operand);
	}

	default long put(K key, long value) {
		throw new UnsupportedOperationException();
	}

	long getLong(Object object);

	default long removeLong(Object key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Long put(K key, Long value) {
		boolean containsKey = this.containsKey(key);
		long v = this.put(key, value.longValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Long get(Object key) {
		long v = this.getLong(key);
		return v == this.defaultReturnValue() && !this.containsKey(key) ? null : v;
	}

	@Deprecated
	default Long remove(Object key) {
		return this.containsKey(key) ? this.removeLong(key) : null;
	}

	default void defaultReturnValue(long rv) {
		throw new UnsupportedOperationException();
	}

	default long defaultReturnValue() {
		return 0L;
	}
}
