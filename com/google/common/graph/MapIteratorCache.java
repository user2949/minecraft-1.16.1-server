package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

class MapIteratorCache<K, V> {
	private final Map<K, V> backingMap;
	@Nullable
	private transient Entry<K, V> entrySetCache;

	MapIteratorCache(Map<K, V> backingMap) {
		this.backingMap = Preconditions.checkNotNull(backingMap);
	}

	@CanIgnoreReturnValue
	public V put(@Nullable K key, @Nullable V value) {
		this.clearCache();
		return (V)this.backingMap.put(key, value);
	}

	@CanIgnoreReturnValue
	public V remove(@Nullable Object key) {
		this.clearCache();
		return (V)this.backingMap.remove(key);
	}

	public void clear() {
		this.clearCache();
		this.backingMap.clear();
	}

	public V get(@Nullable Object key) {
		V value = this.getIfCached(key);
		return value != null ? value : this.getWithoutCaching(key);
	}

	public final V getWithoutCaching(@Nullable Object key) {
		return (V)this.backingMap.get(key);
	}

	public final boolean containsKey(@Nullable Object key) {
		return this.getIfCached(key) != null || this.backingMap.containsKey(key);
	}

	public final Set<K> unmodifiableKeySet() {
		return new AbstractSet<K>() {
			public UnmodifiableIterator<K> iterator() {
				final Iterator<Entry<K, V>> entryIterator = MapIteratorCache.this.backingMap.entrySet().iterator();
				return new UnmodifiableIterator<K>() {
					public boolean hasNext() {
						return entryIterator.hasNext();
					}

					public K next() {
						Entry<K, V> entry = (Entry<K, V>)entryIterator.next();
						MapIteratorCache.this.entrySetCache = entry;
						return (K)entry.getKey();
					}
				};
			}

			public int size() {
				return MapIteratorCache.this.backingMap.size();
			}

			public boolean contains(@Nullable Object key) {
				return MapIteratorCache.this.containsKey(key);
			}
		};
	}

	protected V getIfCached(@Nullable Object key) {
		Entry<K, V> entry = this.entrySetCache;
		return (V)(entry != null && entry.getKey() == key ? entry.getValue() : null);
	}

	protected void clearCache() {
		this.entrySetCache = null;
	}
}
