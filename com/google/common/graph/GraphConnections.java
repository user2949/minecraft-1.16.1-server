package com.google.common.graph;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Set;
import javax.annotation.Nullable;

interface GraphConnections<N, V> {
	Set<N> adjacentNodes();

	Set<N> predecessors();

	Set<N> successors();

	@Nullable
	V value(Object object);

	void removePredecessor(Object object);

	@CanIgnoreReturnValue
	V removeSuccessor(Object object);

	void addPredecessor(N object1, V object2);

	@CanIgnoreReturnValue
	V addSuccessor(N object1, V object2);
}
