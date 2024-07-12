package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.errorprone.annotations.CompatibleWith;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
public interface Graph<N> {
	Set<N> nodes();

	Set<EndpointPair<N>> edges();

	boolean isDirected();

	boolean allowsSelfLoops();

	ElementOrder<N> nodeOrder();

	Set<N> adjacentNodes(@CompatibleWith("N") Object object);

	Set<N> predecessors(@CompatibleWith("N") Object object);

	Set<N> successors(@CompatibleWith("N") Object object);

	int degree(@CompatibleWith("N") Object object);

	int inDegree(@CompatibleWith("N") Object object);

	int outDegree(@CompatibleWith("N") Object object);

	boolean equals(@Nullable Object object);

	int hashCode();
}
