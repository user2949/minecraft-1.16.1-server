package it.unimi.dsi.fastutil.longs;

import java.util.SortedSet;

public interface LongSortedSet extends LongSet, SortedSet<Long>, LongBidirectionalIterable {
	LongBidirectionalIterator iterator(long long1);

	@Override
	LongBidirectionalIterator iterator();

	LongSortedSet subSet(long long1, long long2);

	LongSortedSet headSet(long long1);

	LongSortedSet tailSet(long long1);

	LongComparator comparator();

	long firstLong();

	long lastLong();

	@Deprecated
	default LongSortedSet subSet(Long from, Long to) {
		return this.subSet(from.longValue(), to.longValue());
	}

	@Deprecated
	default LongSortedSet headSet(Long to) {
		return this.headSet(to.longValue());
	}

	@Deprecated
	default LongSortedSet tailSet(Long from) {
		return this.tailSet(from.longValue());
	}

	@Deprecated
	default Long first() {
		return this.firstLong();
	}

	@Deprecated
	default Long last() {
		return this.lastLong();
	}
}
