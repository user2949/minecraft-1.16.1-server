package it.unimi.dsi.fastutil.shorts;

import java.util.SortedSet;

public interface ShortSortedSet extends ShortSet, SortedSet<Short>, ShortBidirectionalIterable {
	ShortBidirectionalIterator iterator(short short1);

	@Override
	ShortBidirectionalIterator iterator();

	ShortSortedSet subSet(short short1, short short2);

	ShortSortedSet headSet(short short1);

	ShortSortedSet tailSet(short short1);

	ShortComparator comparator();

	short firstShort();

	short lastShort();

	@Deprecated
	default ShortSortedSet subSet(Short from, Short to) {
		return this.subSet(from.shortValue(), to.shortValue());
	}

	@Deprecated
	default ShortSortedSet headSet(Short to) {
		return this.headSet(to.shortValue());
	}

	@Deprecated
	default ShortSortedSet tailSet(Short from) {
		return this.tailSet(from.shortValue());
	}

	@Deprecated
	default Short first() {
		return this.firstShort();
	}

	@Deprecated
	default Short last() {
		return this.lastShort();
	}
}
