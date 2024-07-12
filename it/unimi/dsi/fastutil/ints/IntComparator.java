package it.unimi.dsi.fastutil.ints;

import java.util.Comparator;

@FunctionalInterface
public interface IntComparator extends Comparator<Integer> {
	int compare(int integer1, int integer2);

	@Deprecated
	default int compare(Integer ok1, Integer ok2) {
		return this.compare(ok1.intValue(), ok2.intValue());
	}
}
