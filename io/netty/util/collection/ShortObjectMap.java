package io.netty.util.collection;

import java.util.Map;

public interface ShortObjectMap<V> extends Map<Short, V> {
	V get(short short1);

	V put(short short1, V object);

	V remove(short short1);

	Iterable<ShortObjectMap.PrimitiveEntry<V>> entries();

	boolean containsKey(short short1);

	public interface PrimitiveEntry<V> {
		short key();

		V value();

		void setValue(V object);
	}
}
