package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;

@FunctionalInterface
public interface Object2ObjectFunction<K, V> extends Function<K, V> {
	@Override
	default V put(K key, V value) {
		throw new UnsupportedOperationException();
	}

	@Override
	V get(Object object);

	@Override
	default V remove(Object key) {
		throw new UnsupportedOperationException();
	}

	default void defaultReturnValue(V rv) {
		throw new UnsupportedOperationException();
	}

	default V defaultReturnValue() {
		return null;
	}
}
