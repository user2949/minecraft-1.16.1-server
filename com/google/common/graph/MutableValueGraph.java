package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CompatibleWith;

@Beta
public interface MutableValueGraph<N, V> extends ValueGraph<N, V> {
	@CanIgnoreReturnValue
	boolean addNode(N object);

	@CanIgnoreReturnValue
	V putEdgeValue(N object1, N object2, V object3);

	@CanIgnoreReturnValue
	boolean removeNode(@CompatibleWith("N") Object object);

	@CanIgnoreReturnValue
	V removeEdge(@CompatibleWith("N") Object object1, @CompatibleWith("N") Object object2);
}
