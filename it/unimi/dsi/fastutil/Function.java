package it.unimi.dsi.fastutil;

@FunctionalInterface
public interface Function<K, V> extends java.util.function.Function<K, V> {
	default V apply(K key) {
		return this.get(key);
	}

	default V put(K key, V value) {
		throw new UnsupportedOperationException();
	}

	V get(Object object);

	default boolean containsKey(Object key) {
		return true;
	}

	default V remove(Object key) {
		throw new UnsupportedOperationException();
	}

	default int size() {
		return -1;
	}

	default void clear() {
		throw new UnsupportedOperationException();
	}
}
