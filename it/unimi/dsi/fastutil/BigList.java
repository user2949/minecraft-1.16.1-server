package it.unimi.dsi.fastutil;

import java.util.Collection;

public interface BigList<K> extends Collection<K>, Size64 {
	K get(long long1);

	K remove(long long1);

	K set(long long1, K object);

	void add(long long1, K object);

	void size(long long1);

	boolean addAll(long long1, Collection<? extends K> collection);

	long indexOf(Object object);

	long lastIndexOf(Object object);

	BigListIterator<K> listIterator();

	BigListIterator<K> listIterator(long long1);

	BigList<K> subList(long long1, long long2);

	@Deprecated
	@Override
	default int size() {
		return Size64.super.size();
	}
}
