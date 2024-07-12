package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;
import java.util.function.IntFunction;

@FunctionalInterface
public interface Int2ReferenceFunction<V> extends Function<Integer, V>, IntFunction<V> {
	default V apply(int operand) {
		return this.get(operand);
	}

	default V put(int key, V value) {
		throw new UnsupportedOperationException();
	}

	V get(int integer);

	default V remove(int key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default V put(Integer key, V value) {
		int k = key;
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
			int k = (Integer)key;
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

	default void defaultReturnValue(V rv) {
		throw new UnsupportedOperationException();
	}

	default V defaultReturnValue() {
		return null;
	}
}
