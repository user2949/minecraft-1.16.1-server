package it.unimi.dsi.fastutil.objects;

import java.util.Map;
import java.util.function.Consumer;

public interface Object2ReferenceMap<K, V> extends Object2ReferenceFunction<K, V>, Map<K, V> {
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

	ObjectSet<Object2ReferenceMap.Entry<K, V>> object2ReferenceEntrySet();

	default ObjectSet<java.util.Map.Entry<K, V>> entrySet() {
		return this.object2ReferenceEntrySet();
	}

	@Override
	default V put(K key, V value) {
		return Object2ReferenceFunction.super.put(key, value);
	}

	@Override
	default V remove(Object key) {
		return Object2ReferenceFunction.super.remove(key);
	}

	ObjectSet<K> keySet();

	ReferenceCollection<V> values();

	@Override
	boolean containsKey(Object object);

	public interface Entry<K, V> extends java.util.Map.Entry<K, V> {
	}

	public interface FastEntrySet<K, V> extends ObjectSet<Object2ReferenceMap.Entry<K, V>> {
		ObjectIterator<Object2ReferenceMap.Entry<K, V>> fastIterator();

		default void fastForEach(Consumer<? super Object2ReferenceMap.Entry<K, V>> consumer) {
			this.forEach(consumer);
		}
	}
}
