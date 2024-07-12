package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.errorprone.annotations.CompatibleWith;
import javax.annotation.Nullable;

@Beta
public interface ValueGraph<N, V> extends Graph<N> {
	V edgeValue(@CompatibleWith("N") Object object1, @CompatibleWith("N") Object object2);

	V edgeValueOrDefault(@CompatibleWith("N") Object object1, @CompatibleWith("N") Object object2, @Nullable V object3);

	@Override
	boolean equals(@Nullable Object object);

	@Override
	int hashCode();
}
