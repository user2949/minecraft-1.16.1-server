package it.unimi.dsi.fastutil.longs;

import java.util.Comparator;

@FunctionalInterface
public interface LongComparator extends Comparator<Long> {
	int compare(long long1, long long2);

	@Deprecated
	default int compare(Long ok1, Long ok2) {
		return this.compare(ok1.longValue(), ok2.longValue());
	}
}
