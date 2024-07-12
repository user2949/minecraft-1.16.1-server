package it.unimi.dsi.fastutil.objects;

import java.util.Map;
import java.util.function.Consumer;

public interface Reference2ReferenceMap<K, V> extends Reference2ReferenceFunction<K, V>, Map<K, V> {
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

	ObjectSet<Reference2ReferenceMap.Entry<K, V>> reference2ReferenceEntrySet();

	default ObjectSet<java.util.Map.Entry<K, V>> entrySet() {
		return this.reference2ReferenceEntrySet();
	}

	@Override
	default V put(K key, V value) {
		return Reference2ReferenceFunction.super.put(key, value);
	}

	@Override
	default V remove(Object key) {
		return Reference2ReferenceFunction.super.remove(key);
	}

	ReferenceSet<K> keySet();

	ReferenceCollection<V> values();

	@Override
	boolean containsKey(Object object);

	public interface Entry<K, V> extends java.util.Map.Entry<K, V> {
	}

	public interface FastEntrySet<K, V> extends ObjectSet<Reference2ReferenceMap.Entry<K, V>> {
		ObjectIterator<Reference2ReferenceMap.Entry<K, V>> fastIterator();

		default void fastForEach(Consumer<? super Reference2ReferenceMap.Entry<K, V>> consumer) {
			this.forEach(consumer);
		}
	}
}
