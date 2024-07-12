package it.unimi.dsi.fastutil.floats;

import java.util.Comparator;

@FunctionalInterface
public interface FloatComparator extends Comparator<Float> {
	int compare(float float1, float float2);

	@Deprecated
	default int compare(Float ok1, Float ok2) {
		return this.compare(ok1.floatValue(), ok2.floatValue());
	}
}
