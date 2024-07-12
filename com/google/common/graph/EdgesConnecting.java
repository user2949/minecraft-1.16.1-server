package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Map;
import javax.annotation.Nullable;

final class EdgesConnecting<E> extends AbstractSet<E> {
	private final Map<?, E> nodeToOutEdge;
	private final Object targetNode;

	EdgesConnecting(Map<?, E> nodeToEdgeMap, Object targetNode) {
		this.nodeToOutEdge = Preconditions.checkNotNull(nodeToEdgeMap);
		this.targetNode = Preconditions.checkNotNull(targetNode);
	}

	public UnmodifiableIterator<E> iterator() {
		E connectingEdge = this.getConnectingEdge();
		return connectingEdge == null ? ImmutableSet.<E>of().iterator() : Iterators.singletonIterator(connectingEdge);
	}

	public int size() {
		return this.getConnectingEdge() == null ? 0 : 1;
	}

	public boolean contains(@Nullable Object edge) {
		E connectingEdge = this.getConnectingEdge();
		return connectingEdge != null && connectingEdge.equals(edge);
	}

	@Nullable
	private E getConnectingEdge() {
		return (E)this.nodeToOutEdge.get(this.targetNode);
	}
}