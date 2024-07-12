package it.unimi.dsi.fastutil.booleans;

import java.util.Comparator;

@FunctionalInterface
public interface BooleanComparator extends Comparator<Boolean> {
	int compare(boolean boolean1, boolean boolean2);

	@Deprecated
	default int compare(Boolean ok1, Boolean ok2) {
		return this.compare(ok1.booleanValue(), ok2.booleanValue());
	}
}
