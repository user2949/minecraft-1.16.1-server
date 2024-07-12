package it.unimi.dsi.fastutil.objects;

import java.util.SortedSet;

public interface ReferenceSortedSet<K> extends ReferenceSet<K>, SortedSet<K>, ObjectBidirectionalIterable<K> {
	ObjectBidirectionalIterator<K> iterator(K object);

	@Override
	ObjectBidirectionalIterator<K> iterator();

	ReferenceSortedSet<K> subSet(K object1, K object2);

	ReferenceSortedSet<K> headSet(K object);

	ReferenceSortedSet<K> tailSet(K object);
}
