package com.google.common.graph;

import com.google.common.graph.GraphConstants.Presence;

final class ConfigurableMutableGraph<N> extends ForwardingGraph<N> implements MutableGraph<N> {
	private final MutableValueGraph<N, Presence> backingValueGraph;

	ConfigurableMutableGraph(AbstractGraphBuilder<? super N> builder) {
		this.backingValueGraph = new ConfigurableMutableValueGraph<>(builder);
	}

	@Override
	protected Graph<N> delegate() {
		return this.backingValueGraph;
	}

	@Override
	public boolean addNode(N node) {
		return this.backingValueGraph.addNode(node);
	}

	@Override
	public boolean putEdge(N nodeU, N nodeV) {
		return this.backingValueGraph.putEdgeValue(nodeU, nodeV, Presence.EDGE_EXISTS) == null;
	}

	@Override
	public boolean removeNode(Object node) {
		return this.backingValueGraph.removeNode(node);
	}

	@Override
	public boolean removeEdge(Object nodeU, Object nodeV) {
		return this.backingValueGraph.removeEdge(nodeU, nodeV) != null;
	}
}
