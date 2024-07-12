package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

@GwtCompatible
public interface LoadingCache<K, V> extends Cache<K, V>, Function<K, V> {
	V get(K object) throws ExecutionException;

	V getUnchecked(K object);

	ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException;

	@Deprecated
	@Override
	V apply(K object);

	void refresh(K object);

	@Override
	ConcurrentMap<K, V> asMap();
}
