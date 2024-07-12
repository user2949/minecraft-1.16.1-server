package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
@GwtIncompatible
public interface RangeSet<C extends Comparable> {
	boolean contains(C comparable);

	Range<C> rangeContaining(C comparable);

	boolean intersects(Range<C> range);

	boolean encloses(Range<C> range);

	boolean enclosesAll(RangeSet<C> rangeSet);

	default boolean enclosesAll(Iterable<Range<C>> other) {
		for (Range<C> range : other) {
			if (!this.encloses(range)) {
				return false;
			}
		}

		return true;
	}

	boolean isEmpty();

	Range<C> span();

	Set<Range<C>> asRanges();

	Set<Range<C>> asDescendingSetOfRanges();

	RangeSet<C> complement();

	RangeSet<C> subRangeSet(Range<C> range);

	void add(Range<C> range);

	void remove(Range<C> range);

	void clear();

	void addAll(RangeSet<C> rangeSet);

	default void addAll(Iterable<Range<C>> ranges) {
		for (Range<C> range : ranges) {
			this.add(range);
		}
	}

	void removeAll(RangeSet<C> rangeSet);

	default void removeAll(Iterable<Range<C>> ranges) {
		for (Range<C> range : ranges) {
			this.remove(range);
		}
	}

	boolean equals(@Nullable Object object);

	int hashCode();

	String toString();
}
