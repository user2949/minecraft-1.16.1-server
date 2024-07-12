package it.unimi.dsi.fastutil.chars;

import java.util.Set;

public interface CharSet extends CharCollection, Set<Character> {
	@Override
	CharIterator iterator();

	boolean remove(char character);

	@Deprecated
	@Override
	default boolean remove(Object o) {
		return CharCollection.super.remove(o);
	}

	@Deprecated
	@Override
	default boolean add(Character o) {
		return CharCollection.super.add(o);
	}

	@Deprecated
	@Override
	default boolean contains(Object o) {
		return CharCollection.super.contains(o);
	}

	@Deprecated
	@Override
	default boolean rem(char k) {
		return this.remove(k);
	}
}
