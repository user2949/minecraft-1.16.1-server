package it.unimi.dsi.fastutil.chars;

import java.util.List;

public interface CharList extends List<Character>, Comparable<List<? extends Character>>, CharCollection {
	CharListIterator iterator();

	CharListIterator listIterator();

	CharListIterator listIterator(int integer);

	CharList subList(int integer1, int integer2);

	void size(int integer);

	void getElements(int integer1, char[] arr, int integer3, int integer4);

	void removeElements(int integer1, int integer2);

	void addElements(int integer, char[] arr);

	void addElements(int integer1, char[] arr, int integer3, int integer4);

	@Override
	boolean add(char character);

	void add(int integer, char character);

	@Deprecated
	default void add(int index, Character key) {
		this.add(index, key.charValue());
	}

	boolean addAll(int integer, CharCollection charCollection);

	boolean addAll(int integer, CharList charList);

	boolean addAll(CharList charList);

	char set(int integer, char character);

	char getChar(int integer);

	int indexOf(char character);

	int lastIndexOf(char character);

	@Deprecated
	@Override
	default boolean contains(Object key) {
		return CharCollection.super.contains(key);
	}

	@Deprecated
	default Character get(int index) {
		return this.getChar(index);
	}

	@Deprecated
	default int indexOf(Object o) {
		return this.indexOf(((Character)o).charValue());
	}

	@Deprecated
	default int lastIndexOf(Object o) {
		return this.lastIndexOf(((Character)o).charValue());
	}

	@Deprecated
	@Override
	default boolean add(Character k) {
		return this.add(k.charValue());
	}

	char removeChar(int integer);

	@Deprecated
	@Override
	default boolean remove(Object key) {
		return CharCollection.super.remove(key);
	}

	@Deprecated
	default Character remove(int index) {
		return this.removeChar(index);
	}

	@Deprecated
	default Character set(int index, Character k) {
		return this.set(index, k.charValue());
	}
}
