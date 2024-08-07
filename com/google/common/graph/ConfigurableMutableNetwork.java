package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

final class ConfigurableMutableNetwork<N, E> extends ConfigurableNetwork<N, E> implements MutableNetwork<N, E> {
	ConfigurableMutableNetwork(NetworkBuilder<? super N, ? super E> builder) {
		super(builder);
	}

	@CanIgnoreReturnValue
	@Override
	public boolean addNode(N node) {
		Preconditions.checkNotNull(node, "node");
		if (this.containsNode(node)) {
			return false;
		} else {
			this.addNodeInternal(node);
			return true;
		}
	}

	@CanIgnoreReturnValue
	private NetworkConnections<N, E> addNodeInternal(N node) {
		NetworkConnections<N, E> connections = this.newConnections();
		Preconditions.checkState(this.nodeConnections.put(node, connections) == null);
		return connections;
	}

	@CanIgnoreReturnValue
	@Override
	public boolean addEdge(N nodeU, N nodeV, E edge) {
		Preconditions.checkNotNull(nodeU, "nodeU");
		Preconditions.checkNotNull(nodeV, "nodeV");
		Preconditions.checkNotNull(edge, "edge");
		if (this.containsEdge(edge)) {
			EndpointPair<N> existingIncidentNodes = this.incidentNodes(edge);
			EndpointPair<N> newIncidentNodes = EndpointPair.of(this, nodeU, nodeV);
			Preconditions.checkArgument(
				existingIncidentNodes.equals(newIncidentNodes),
				"Edge %s already exists between the following nodes: %s, so it cannot be reused to connect the following nodes: %s.",
				edge,
				existingIncidentNodes,
				newIncidentNodes
			);
			return false;
		} else {
			NetworkConnections<N, E> connectionsU = this.nodeConnections.get(nodeU);
			if (!this.allowsParallelEdges()) {
				Preconditions.checkArgument(
					connectionsU == null || !connectionsU.successors().contains(nodeV),
					"Nodes %s and %s are already connected by a different edge. To construct a graph that allows parallel edges, call allowsParallelEdges(true) on the Builder.",
					nodeU,
					nodeV
				);
			}

			boolean isSelfLoop = nodeU.equals(nodeV);
			if (!this.allowsSelfLoops()) {
				Preconditions.checkArgument(
					!isSelfLoop,
					"Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.",
					nodeU
				);
			}

			if (connectionsU == null) {
				connectionsU = this.addNodeInternal(nodeU);
			}

			connectionsU.addOutEdge(edge, nodeV);
			NetworkConnections<N, E> connectionsV = this.nodeConnections.get(nodeV);
			if (connectionsV == null) {
				connectionsV = this.addNodeInternal(nodeV);
			}

			connectionsV.addInEdge(edge, nodeU, isSelfLoop);
			this.edgeToReferenceNode.put(edge, nodeU);
			return true;
		}
	}

	@CanIgnoreReturnValue
	@Override
	public boolean removeNode(Object node) {
		Preconditions.checkNotNull(node, "node");
		NetworkConnections<N, E> connections = this.nodeConnections.get(node);
		if (connections == null) {
			return false;
		} else {
			for (E edge : ImmutableList.copyOf(connections.incidentEdges())) {
				this.removeEdge(edge);
			}

			this.nodeConnections.remove(node);
			return true;
		}
	}

	@CanIgnoreReturnValue
	@Override
	public boolean removeEdge(Object edge) {
		Preconditions.checkNotNull(edge, "edge");
		N nodeU = this.edgeToReferenceNode.get(edge);
		if (nodeU == null) {
			return false;
		} else {
			NetworkConnections<N, E> connectionsU = this.nodeConnections.get(nodeU);
			N nodeV = connectionsU.oppositeNode(edge);
			NetworkConnections<N, E> connectionsV = this.nodeConnections.get(nodeV);
			connectionsU.removeOutEdge(edge);
			connectionsV.removeInEdge(edge, this.allowsSelfLoops() && nodeU.equals(nodeV));
			this.edgeToReferenceNode.remove(edge);
			return true;
		}
	}

	private NetworkConnections<N, E> newConnections() {
		return (NetworkConnections<N, E>)(this.isDirected()
			? (this.allowsParallelEdges() ? DirectedMultiNetworkConnections.of() : DirectedNetworkConnections.of())
			: (this.allowsParallelEdges() ? UndirectedMultiNetworkConnections.of() : UndirectedNetworkConnections.of()));
	}
}
