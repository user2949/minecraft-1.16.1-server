package it.unimi.dsi.fastutil.objects;

import java.util.List;

public interface ObjectList<K> extends List<K>, Comparable<List<? extends K>>, ObjectCollection<K> {
	ObjectListIterator<K> iterator();

	ObjectListIterator<K> listIterator();

	ObjectListIterator<K> listIterator(int integer);

	ObjectList<K> subList(int integer1, int integer2);

	void size(int integer);

	void getElements(int integer1, Object[] arr, int integer3, int integer4);

	void removeElements(int integer1, int integer2);

	void addElements(int integer, K[] arr);

	void addElements(int integer1, K[] arr, int integer3, int integer4);
}
