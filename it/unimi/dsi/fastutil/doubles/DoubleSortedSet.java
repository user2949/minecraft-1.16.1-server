package it.unimi.dsi.fastutil.doubles;

import java.util.SortedSet;

public interface DoubleSortedSet extends DoubleSet, SortedSet<Double>, DoubleBidirectionalIterable {
	DoubleBidirectionalIterator iterator(double double1);

	@Override
	DoubleBidirectionalIterator iterator();

	DoubleSortedSet subSet(double double1, double double2);

	DoubleSortedSet headSet(double double1);

	DoubleSortedSet tailSet(double double1);

	DoubleComparator comparator();

	double firstDouble();

	double lastDouble();

	@Deprecated
	default DoubleSortedSet subSet(Double from, Double to) {
		return this.subSet(from.doubleValue(), to.doubleValue());
	}

	@Deprecated
	default DoubleSortedSet headSet(Double to) {
		return this.headSet(to.doubleValue());
	}

	@Deprecated
	default DoubleSortedSet tailSet(Double from) {
		return this.tailSet(from.doubleValue());
	}

	@Deprecated
	default Double first() {
		return this.firstDouble();
	}

	@Deprecated
	default Double last() {
		return this.lastDouble();
	}
}
