package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.Size64;

public interface CharBigList extends BigList<Character>, CharCollection, Size64, Comparable<BigList<? extends Character>> {
	CharBigListIterator iterator();

	CharBigListIterator listIterator();

	CharBigListIterator listIterator(long long1);

	CharBigList subList(long long1, long long2);

	void getElements(long long1, char[][] arr, long long3, long long4);

	void removeElements(long long1, long long2);

	void addElements(long long1, char[][] arr);

	void addElements(long long1, char[][] arr, long long3, long long4);

	void add(long long1, char character);

	boolean addAll(long long1, CharCollection charCollection);

	boolean addAll(long long1, CharBigList charBigList);

	boolean addAll(CharBigList charBigList);

	char getChar(long long1);

	char removeChar(long long1);

	char set(long long1, char character);

	long indexOf(char character);

	long lastIndexOf(char character);

	@Deprecated
	void add(long long1, Character character);

	@Deprecated
	Character get(long long1);

	@Deprecated
	@Override
	long indexOf(Object object);

	@Deprecated
	@Override
	long lastIndexOf(Object object);

	@Deprecated
	Character remove(long long1);

	@Deprecated
	Character set(long long1, Character character);
}
