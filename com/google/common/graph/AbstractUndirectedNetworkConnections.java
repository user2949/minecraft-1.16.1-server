package com.google.common.graph;

import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

abstract class AbstractUndirectedNetworkConnections<N, E> implements NetworkConnections<N, E> {
	protected final Map<E, N> incidentEdgeMap;

	protected AbstractUndirectedNetworkConnections(Map<E, N> incidentEdgeMap) {
		this.incidentEdgeMap = Preconditions.checkNotNull(incidentEdgeMap);
	}

	@Override
	public Set<N> predecessors() {
		return this.adjacentNodes();
	}

	@Override
	public Set<N> successors() {
		return this.adjacentNodes();
	}

	@Override
	public Set<E> incidentEdges() {
		return Collections.unmodifiableSet(this.incidentEdgeMap.keySet());
	}

	@Override
	public Set<E> inEdges() {
		return this.incidentEdges();
	}

	@Override
	public Set<E> outEdges() {
		return this.incidentEdges();
	}

	@Override
	public N oppositeNode(Object edge) {
		return Preconditions.checkNotNull((N)this.incidentEdgeMap.get(edge));
	}

	@Override
	public N removeInEdge(Object edge, boolean isSelfLoop) {
		return !isSelfLoop ? this.removeOutEdge(edge) : null;
	}

	@Override
	public N removeOutEdge(Object edge) {
		N previousNode = (N)this.incidentEdgeMap.remove(edge);
		return Preconditions.checkNotNull(previousNode);
	}

	@Override
	public void addInEdge(E edge, N node, boolean isSelfLoop) {
		if (!isSelfLoop) {
			this.addOutEdge(edge, node);
		}
	}

	@Override
	public void addOutEdge(E edge, N node) {
		N previousNode = (N)this.incidentEdgeMap.put(edge, node);
		Preconditions.checkState(previousNode == null);
	}
}
