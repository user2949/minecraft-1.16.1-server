package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Set;

abstract class EndpointPairIterator<N> extends AbstractIterator<EndpointPair<N>> {
	private final Graph<N> graph;
	private final Iterator<N> nodeIterator;
	protected N node = (N)null;
	protected Iterator<N> successorIterator = ImmutableSet.<N>of().iterator();

	static <N> EndpointPairIterator<N> of(Graph<N> graph) {
		return (EndpointPairIterator<N>)(graph.isDirected() ? new EndpointPairIterator.Directed<>(graph) : new EndpointPairIterator.Undirected<>(graph));
	}

	private EndpointPairIterator(Graph<N> graph) {
		this.graph = graph;
		this.nodeIterator = graph.nodes().iterator();
	}

	protected final boolean advance() {
		Preconditions.checkState(!this.successorIterator.hasNext());
		if (!this.nodeIterator.hasNext()) {
			return false;
		} else {
			this.node = (N)this.nodeIterator.next();
			this.successorIterator = this.graph.successors(this.node).iterator();
			return true;
		}
	}

	private static final class Directed<N> extends EndpointPairIterator<N> {
		private Directed(Graph<N> graph) {
			super(graph);
		}

		protected EndpointPair<N> computeNext() {
			while (!this.successorIterator.hasNext()) {
				if (!this.advance()) {
					return this.endOfData();
				}
			}

			return EndpointPair.ordered(this.node, (N)this.successorIterator.next());
		}
	}

	private static final class Undirected<N> extends EndpointPairIterator<N> {
		private Set<N> visitedNodes;

		private Undirected(Graph<N> graph) {
			super(graph);
			this.visitedNodes = Sets.<N>newHashSetWithExpectedSize(graph.nodes().size());
		}

		protected EndpointPair<N> computeNext() {
			while (true) {
				if (this.successorIterator.hasNext()) {
					N otherNode = (N)this.successorIterator.next();
					if (!this.visitedNodes.contains(otherNode)) {
						return EndpointPair.unordered(this.node, otherNode);
					}
				} else {
					this.visitedNodes.add(this.node);
					if (!this.advance()) {
						this.visitedNodes = null;
						return this.endOfData();
					}
				}
			}
		}
	}
}
