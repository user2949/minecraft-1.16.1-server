package it.unimi.dsi.fastutil.floats;

import java.util.SortedSet;

public interface FloatSortedSet extends FloatSet, SortedSet<Float>, FloatBidirectionalIterable {
	FloatBidirectionalIterator iterator(float float1);

	@Override
	FloatBidirectionalIterator iterator();

	FloatSortedSet subSet(float float1, float float2);

	FloatSortedSet headSet(float float1);

	FloatSortedSet tailSet(float float1);

	FloatComparator comparator();

	float firstFloat();

	float lastFloat();

	@Deprecated
	default FloatSortedSet subSet(Float from, Float to) {
		return this.subSet(from.floatValue(), to.floatValue());
	}

	@Deprecated
	default FloatSortedSet headSet(Float to) {
		return this.headSet(to.floatValue());
	}

	@Deprecated
	default FloatSortedSet tailSet(Float from) {
		return this.tailSet(from.floatValue());
	}

	@Deprecated
	default Float first() {
		return this.firstFloat();
	}

	@Deprecated
	default Float last() {
		return this.lastFloat();
	}
}
