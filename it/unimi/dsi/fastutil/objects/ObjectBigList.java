package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.Size64;

public interface ObjectBigList<K> extends BigList<K>, ObjectCollection<K>, Size64, Comparable<BigList<? extends K>> {
	ObjectBigListIterator<K> iterator();

	ObjectBigListIterator<K> listIterator();

	ObjectBigListIterator<K> listIterator(long long1);

	ObjectBigList<K> subList(long long1, long long2);

	void getElements(long long1, Object[][] arr, long long3, long long4);

	void removeElements(long long1, long long2);

	void addElements(long long1, K[][] arr);

	void addElements(long long1, K[][] arr, long long3, long long4);
}
