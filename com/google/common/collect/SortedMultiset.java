package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Multiset.Entry;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;

@GwtCompatible(
	emulated = true
)
public interface SortedMultiset<E> extends SortedMultisetBridge<E>, SortedIterable<E> {
	@Override
	Comparator<? super E> comparator();

	Entry<E> firstEntry();

	Entry<E> lastEntry();

	Entry<E> pollFirstEntry();

	Entry<E> pollLastEntry();

	NavigableSet<E> elementSet();

	@Override
	Set<Entry<E>> entrySet();

	@Override
	Iterator<E> iterator();

	SortedMultiset<E> descendingMultiset();

	SortedMultiset<E> headMultiset(E object, BoundType boundType);

	SortedMultiset<E> subMultiset(E object1, BoundType boundType2, E object3, BoundType boundType4);

	SortedMultiset<E> tailMultiset(E object, BoundType boundType);
}
