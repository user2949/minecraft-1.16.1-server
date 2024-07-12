package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.errorprone.annotations.CompatibleWith;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
public interface Network<N, E> {
	Set<N> nodes();

	Set<E> edges();

	Graph<N> asGraph();

	boolean isDirected();

	boolean allowsParallelEdges();

	boolean allowsSelfLoops();

	ElementOrder<N> nodeOrder();

	ElementOrder<E> edgeOrder();

	Set<N> adjacentNodes(@CompatibleWith("N") Object object);

	Set<N> predecessors(@CompatibleWith("N") Object object);

	Set<N> successors(@CompatibleWith("N") Object object);

	Set<E> incidentEdges(@CompatibleWith("N") Object object);

	Set<E> inEdges(@CompatibleWith("N") Object object);

	Set<E> outEdges(@CompatibleWith("N") Object object);

	int degree(@CompatibleWith("N") Object object);

	int inDegree(@CompatibleWith("N") Object object);

	int outDegree(@CompatibleWith("N") Object object);

	EndpointPair<N> incidentNodes(@CompatibleWith("E") Object object);

	Set<E> adjacentEdges(@CompatibleWith("E") Object object);

	Set<E> edgesConnecting(@CompatibleWith("N") Object object1, @CompatibleWith("N") Object object2);

	boolean equals(@Nullable Object object);

	int hashCode();
}
