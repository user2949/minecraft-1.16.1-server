package com.google.gson;

public interface ExclusionStrategy {
	boolean shouldSkipField(FieldAttributes fieldAttributes);

	boolean shouldSkipClass(Class<?> class1);
}
