package io.netty.channel.pool;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ReadOnlyIterator;
import java.io.Closeable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;

public abstract class AbstractChannelPoolMap<K, P extends ChannelPool> implements ChannelPoolMap<K, P>, Iterable<Entry<K, P>>, Closeable {
	private final ConcurrentMap<K, P> map = PlatformDependent.newConcurrentHashMap();

	@Override
	public final P get(K key) {
		P pool = (P)this.map.get(ObjectUtil.checkNotNull(key, "key"));
		if (pool == null) {
			pool = this.newPool(key);
			P old = (P)this.map.putIfAbsent(key, pool);
			if (old != null) {
				pool.close();
				pool = old;
			}
		}

		return pool;
	}

	public final boolean remove(K key) {
		P pool = (P)this.map.remove(ObjectUtil.checkNotNull(key, "key"));
		if (pool != null) {
			pool.close();
			return true;
		} else {
			return false;
		}
	}

	public final Iterator<Entry<K, P>> iterator() {
		return new ReadOnlyIterator<>(this.map.entrySet().iterator());
	}

	public final int size() {
		return this.map.size();
	}

	public final boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public final boolean contains(K key) {
		return this.map.containsKey(ObjectUtil.checkNotNull(key, "key"));
	}

	protected abstract P newPool(K object);

	public final void close() {
		for (K key : this.map.keySet()) {
			this.remove(key);
		}
	}
}
