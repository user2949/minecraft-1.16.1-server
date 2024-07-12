package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.graph.GraphConstants.Presence;

@Beta
public abstract class ImmutableGraph<N> extends ForwardingGraph<N> {
	ImmutableGraph() {
	}

	public static <N> ImmutableGraph<N> copyOf(Graph<N> graph) {
		return (ImmutableGraph<N>)(graph instanceof ImmutableGraph
			? (ImmutableGraph)graph
			: new ImmutableGraph.ValueBackedImpl<>(GraphBuilder.from(graph), getNodeConnections(graph), (long)graph.edges().size()));
	}

	@Deprecated
	public static <N> ImmutableGraph<N> copyOf(ImmutableGraph<N> graph) {
		return Preconditions.checkNotNull(graph);
	}

	private static <N> ImmutableMap<N, GraphConnections<N, Presence>> getNodeConnections(Graph<N> graph) {
		Builder<N, GraphConnections<N, Presence>> nodeConnections = ImmutableMap.builder();

		for (N node : graph.nodes()) {
			nodeConnections.put(node, connectionsOf(graph, node));
		}

		return nodeConnections.build();
	}

	private static <N> GraphConnections<N, Presence> connectionsOf(Graph<N> graph, N node) {
		Function<Object, Presence> edgeValueFn = Functions.constant(Presence.EDGE_EXISTS);
		return (GraphConnections<N, Presence>)(graph.isDirected()
			? DirectedGraphConnections.ofImmutable(graph.predecessors(node), Maps.asMap(graph.successors(node), edgeValueFn))
			: UndirectedGraphConnections.ofImmutable(Maps.asMap(graph.adjacentNodes(node), edgeValueFn)));
	}

	static class ValueBackedImpl<N, V> extends ImmutableGraph<N> {
		protected final ValueGraph<N, V> backingValueGraph;

		ValueBackedImpl(AbstractGraphBuilder<? super N> builder, ImmutableMap<N, GraphConnections<N, V>> nodeConnections, long edgeCount) {
			this.backingValueGraph = new ConfigurableValueGraph<>(builder, nodeConnections, edgeCount);
		}

		@Override
		protected Graph<N> delegate() {
			return this.backingValueGraph;
		}
	}
}
