package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CompatibleWith;

@Beta
public interface MutableNetwork<N, E> extends Network<N, E> {
	@CanIgnoreReturnValue
	boolean addNode(N object);

	@CanIgnoreReturnValue
	boolean addEdge(N object1, N object2, E object3);

	@CanIgnoreReturnValue
	boolean removeNode(@CompatibleWith("N") Object object);

	@CanIgnoreReturnValue
	boolean removeEdge(@CompatibleWith("E") Object object);
}
