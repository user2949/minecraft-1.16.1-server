package it.unimi.dsi.fastutil.doubles;

import java.util.Comparator;

@FunctionalInterface
public interface DoubleComparator extends Comparator<Double> {
	int compare(double double1, double double2);

	@Deprecated
	default int compare(Double ok1, Double ok2) {
		return this.compare(ok1.doubleValue(), ok2.doubleValue());
	}
}
