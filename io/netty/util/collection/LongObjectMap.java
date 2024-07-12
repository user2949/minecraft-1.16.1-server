package io.netty.util.collection;

import java.util.Map;

public interface LongObjectMap<V> extends Map<Long, V> {
	V get(long long1);

	V put(long long1, V object);

	V remove(long long1);

	Iterable<LongObjectMap.PrimitiveEntry<V>> entries();

	boolean containsKey(long long1);

	public interface PrimitiveEntry<V> {
		long key();

		V value();

		void setValue(V object);
	}
}
