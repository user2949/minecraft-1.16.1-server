package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import java.util.function.ToIntFunction;

@FunctionalInterface
public interface Object2IntFunction<K> extends Function<K, Integer>, ToIntFunction<K> {
	default int applyAsInt(K operand) {
		return this.getInt(operand);
	}

	default int put(K key, int value) {
		throw new UnsupportedOperationException();
	}

	int getInt(Object object);

	default int removeInt(Object key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Integer put(K key, Integer value) {
		boolean containsKey = this.containsKey(key);
		int v = this.put(key, value.intValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Integer get(Object key) {
		int v = this.getInt(key);
		return v == this.defaultReturnValue() && !this.containsKey(key) ? null : v;
	}

	@Deprecated
	default Integer remove(Object key) {
		return this.containsKey(key) ? this.removeInt(key) : null;
	}

	default void defaultReturnValue(int rv) {
		throw new UnsupportedOperationException();
	}

	default int defaultReturnValue() {
		return 0;
	}
}
