package com.google.common.graph;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Set;

interface NetworkConnections<N, E> {
	Set<N> adjacentNodes();

	Set<N> predecessors();

	Set<N> successors();

	Set<E> incidentEdges();

	Set<E> inEdges();

	Set<E> outEdges();

	Set<E> edgesConnecting(Object object);

	N oppositeNode(Object object);

	@CanIgnoreReturnValue
	N removeInEdge(Object object, boolean boolean2);

	@CanIgnoreReturnValue
	N removeOutEdge(Object object);

	void addInEdge(E object1, N object2, boolean boolean3);

	void addOutEdge(E object1, N object2);
}
