package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.ConcurrentMap;

@GwtCompatible
public abstract class ForwardingConcurrentMap<K, V> extends ForwardingMap<K, V> implements ConcurrentMap<K, V> {
	protected ForwardingConcurrentMap() {
	}

	protected abstract ConcurrentMap<K, V> delegate();

	@CanIgnoreReturnValue
	public V putIfAbsent(K key, V value) {
		return (V)this.delegate().putIfAbsent(key, value);
	}

	@CanIgnoreReturnValue
	public boolean remove(Object key, Object value) {
		return this.delegate().remove(key, value);
	}

	@CanIgnoreReturnValue
	public V replace(K key, V value) {
		return (V)this.delegate().replace(key, value);
	}

	@CanIgnoreReturnValue
	public boolean replace(K key, V oldValue, V newValue) {
		return this.delegate().replace(key, oldValue, newValue);
	}
}
