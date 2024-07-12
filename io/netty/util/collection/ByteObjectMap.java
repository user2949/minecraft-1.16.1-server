package io.netty.util.collection;

import java.util.Map;

public interface ByteObjectMap<V> extends Map<Byte, V> {
	V get(byte byte1);

	V put(byte byte1, V object);

	V remove(byte byte1);

	Iterable<ByteObjectMap.PrimitiveEntry<V>> entries();

	boolean containsKey(byte byte1);

	public interface PrimitiveEntry<V> {
		byte key();

		V value();

		void setValue(V object);
	}
}
