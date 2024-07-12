package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.math.IntMath;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

abstract class AbstractDirectedNetworkConnections<N, E> implements NetworkConnections<N, E> {
	protected final Map<E, N> inEdgeMap;
	protected final Map<E, N> outEdgeMap;
	private int selfLoopCount;

	protected AbstractDirectedNetworkConnections(Map<E, N> inEdgeMap, Map<E, N> outEdgeMap, int selfLoopCount) {
		this.inEdgeMap = Preconditions.checkNotNull(inEdgeMap);
		this.outEdgeMap = Preconditions.checkNotNull(outEdgeMap);
		this.selfLoopCount = Graphs.checkNonNegative(selfLoopCount);
		Preconditions.checkState(selfLoopCount <= inEdgeMap.size() && selfLoopCount <= outEdgeMap.size());
	}

	@Override
	public Set<N> adjacentNodes() {
		return Sets.union(this.predecessors(), this.successors());
	}

	@Override
	public Set<E> incidentEdges() {
		return new AbstractSet<E>() {
			public UnmodifiableIterator<E> iterator() {
				Iterable<E> incidentEdges = (Iterable<E>)(AbstractDirectedNetworkConnections.this.selfLoopCount == 0
					? Iterables.concat(AbstractDirectedNetworkConnections.this.inEdgeMap.keySet(), AbstractDirectedNetworkConnections.this.outEdgeMap.keySet())
					: Sets.<E>union(AbstractDirectedNetworkConnections.this.inEdgeMap.keySet(), AbstractDirectedNetworkConnections.this.outEdgeMap.keySet()));
				return Iterators.unmodifiableIterator(incidentEdges.iterator());
			}

			public int size() {
				return IntMath.saturatedAdd(
					AbstractDirectedNetworkConnections.this.inEdgeMap.size(),
					AbstractDirectedNetworkConnections.this.outEdgeMap.size() - AbstractDirectedNetworkConnections.this.selfLoopCount
				);
			}

			public boolean contains(@Nullable Object obj) {
				return AbstractDirectedNetworkConnections.this.inEdgeMap.containsKey(obj) || AbstractDirectedNetworkConnections.this.outEdgeMap.containsKey(obj);
			}
		};
	}

	@Override
	public Set<E> inEdges() {
		return Collections.unmodifiableSet(this.inEdgeMap.keySet());
	}

	@Override
	public Set<E> outEdges() {
		return Collections.unmodifiableSet(this.outEdgeMap.keySet());
	}

	@Override
	public N oppositeNode(Object edge) {
		return Preconditions.checkNotNull((N)this.outEdgeMap.get(edge));
	}

	@Override
	public N removeInEdge(Object edge, boolean isSelfLoop) {
		if (isSelfLoop) {
			Graphs.checkNonNegative(--this.selfLoopCount);
		}

		N previousNode = (N)this.inEdgeMap.remove(edge);
		return Preconditions.checkNotNull(previousNode);
	}

	@Override
	public N removeOutEdge(Object edge) {
		N previousNode = (N)this.outEdgeMap.remove(edge);
		return Preconditions.checkNotNull(previousNode);
	}

	@Override
	public void addInEdge(E edge, N node, boolean isSelfLoop) {
		if (isSelfLoop) {
			Graphs.checkPositive(++this.selfLoopCount);
		}

		N previousNode = (N)this.inEdgeMap.put(edge, node);
		Preconditions.checkState(previousNode == null);
	}

	@Override
	public void addOutEdge(E edge, N node) {
		N previousNode = (N)this.outEdgeMap.put(edge, node);
		Preconditions.checkState(previousNode == null);
	}
}
