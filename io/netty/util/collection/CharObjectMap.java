package io.netty.util.collection;

import java.util.Map;

public interface CharObjectMap<V> extends Map<Character, V> {
	V get(char character);

	V put(char character, V object);

	V remove(char character);

	Iterable<CharObjectMap.PrimitiveEntry<V>> entries();

	boolean containsKey(char character);

	public interface PrimitiveEntry<V> {
		char key();

		V value();

		void setValue(V object);
	}
}
