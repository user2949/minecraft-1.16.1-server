package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nullable;

@GwtCompatible
public interface Cache<K, V> {
	@Nullable
	V getIfPresent(Object object);

	V get(K object, Callable<? extends V> callable) throws ExecutionException;

	ImmutableMap<K, V> getAllPresent(Iterable<?> iterable);

	void put(K object1, V object2);

	void putAll(Map<? extends K, ? extends V> map);

	void invalidate(Object object);

	void invalidateAll(Iterable<?> iterable);

	void invalidateAll();

	long size();

	CacheStats stats();

	ConcurrentMap<K, V> asMap();

	void cleanUp();
}
