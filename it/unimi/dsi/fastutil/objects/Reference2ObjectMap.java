package it.unimi.dsi.fastutil.objects;

import java.util.Map;
import java.util.function.Consumer;

public interface Reference2ObjectMap<K, V> extends Reference2ObjectFunction<K, V>, Map<K, V> {
	@Override
	int size();

	@Override
	default void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	void defaultReturnValue(V object);

	@Override
	V defaultReturnValue();

	ObjectSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet();

	default ObjectSet<java.util.Map.Entry<K, V>> entrySet() {
		return this.reference2ObjectEntrySet();
	}

	@Override
	default V put(K key, V value) {
		return Reference2ObjectFunction.super.put(key, value);
	}

	@Override
	default V remove(Object key) {
		return Reference2ObjectFunction.super.remove(key);
	}

	ReferenceSet<K> keySet();

	ObjectCollection<V> values();

	@Override
	boolean containsKey(Object object);

	public interface Entry<K, V> extends java.util.Map.Entry<K, V> {
	}

	public interface FastEntrySet<K, V> extends ObjectSet<Reference2ObjectMap.Entry<K, V>> {
		ObjectIterator<Reference2ObjectMap.Entry<K, V>> fastIterator();

		default void fastForEach(Consumer<? super Reference2ObjectMap.Entry<K, V>> consumer) {
			this.forEach(consumer);
		}
	}
}
