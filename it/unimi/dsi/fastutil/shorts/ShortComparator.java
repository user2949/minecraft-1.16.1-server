package it.unimi.dsi.fastutil.shorts;

import java.util.Comparator;

@FunctionalInterface
public interface ShortComparator extends Comparator<Short> {
	int compare(short short1, short short2);

	@Deprecated
	default int compare(Short ok1, Short ok2) {
		return this.compare(ok1.shortValue(), ok2.shortValue());
	}
}
