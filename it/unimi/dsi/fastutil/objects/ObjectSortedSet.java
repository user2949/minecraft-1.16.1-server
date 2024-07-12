package it.unimi.dsi.fastutil.objects;

import java.util.SortedSet;

public interface ObjectSortedSet<K> extends ObjectSet<K>, SortedSet<K>, ObjectBidirectionalIterable<K> {
	ObjectBidirectionalIterator<K> iterator(K object);

	@Override
	ObjectBidirectionalIterator<K> iterator();

	ObjectSortedSet<K> subSet(K object1, K object2);

	ObjectSortedSet<K> headSet(K object);

	ObjectSortedSet<K> tailSet(K object);
}
