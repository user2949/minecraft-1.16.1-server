package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ForwardingSetMultimap<K, V> extends ForwardingMultimap<K, V> implements SetMultimap<K, V> {
	protected abstract SetMultimap<K, V> delegate();

	@Override
	public Set<Entry<K, V>> entries() {
		return this.delegate().entries();
	}

	@Override
	public Set<V> get(@Nullable K key) {
		return this.delegate().get(key);
	}

	@CanIgnoreReturnValue
	@Override
	public Set<V> removeAll(@Nullable Object key) {
		return this.delegate().removeAll(key);
	}

	@CanIgnoreReturnValue
	@Override
	public Set<V> replaceValues(K key, Iterable<? extends V> values) {
		return this.delegate().replaceValues(key, values);
	}
}
