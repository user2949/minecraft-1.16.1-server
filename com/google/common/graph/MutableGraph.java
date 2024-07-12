package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CompatibleWith;

@Beta
public interface MutableGraph<N> extends Graph<N> {
	@CanIgnoreReturnValue
	boolean addNode(N object);

	@CanIgnoreReturnValue
	boolean putEdge(N object1, N object2);

	@CanIgnoreReturnValue
	boolean removeNode(@CompatibleWith("N") Object object);

	@CanIgnoreReturnValue
	boolean removeEdge(@CompatibleWith("N") Object object1, @CompatibleWith("N") Object object2);
}
