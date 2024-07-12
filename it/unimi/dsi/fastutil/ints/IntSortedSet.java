package it.unimi.dsi.fastutil.ints;

import java.util.SortedSet;

public interface IntSortedSet extends IntSet, SortedSet<Integer>, IntBidirectionalIterable {
	IntBidirectionalIterator iterator(int integer);

	@Override
	IntBidirectionalIterator iterator();

	IntSortedSet subSet(int integer1, int integer2);

	IntSortedSet headSet(int integer);

	IntSortedSet tailSet(int integer);

	IntComparator comparator();

	int firstInt();

	int lastInt();

	@Deprecated
	default IntSortedSet subSet(Integer from, Integer to) {
		return this.subSet(from.intValue(), to.intValue());
	}

	@Deprecated
	default IntSortedSet headSet(Integer to) {
		return this.headSet(to.intValue());
	}

	@Deprecated
	default IntSortedSet tailSet(Integer from) {
		return this.tailSet(from.intValue());
	}

	@Deprecated
	default Integer first() {
		return this.firstInt();
	}

	@Deprecated
	default Integer last() {
		return this.lastInt();
	}
}
