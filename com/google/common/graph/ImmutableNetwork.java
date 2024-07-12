package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;

@Beta
public final class ImmutableNetwork<N, E> extends ConfigurableNetwork<N, E> {
	private ImmutableNetwork(Network<N, E> network) {
		super(NetworkBuilder.from(network), getNodeConnections(network), getEdgeToReferenceNode(network));
	}

	public static <N, E> ImmutableNetwork<N, E> copyOf(Network<N, E> network) {
		return network instanceof ImmutableNetwork ? (ImmutableNetwork)network : new ImmutableNetwork<>(network);
	}

	@Deprecated
	public static <N, E> ImmutableNetwork<N, E> copyOf(ImmutableNetwork<N, E> network) {
		return Preconditions.checkNotNull(network);
	}

	public ImmutableGraph<N> asGraph() {
		final Graph<N> asGraph = super.asGraph();
		return new ImmutableGraph<N>() {
			@Override
			protected Graph<N> delegate() {
				return asGraph;
			}
		};
	}

	private static <N, E> Map<N, NetworkConnections<N, E>> getNodeConnections(Network<N, E> network) {
		Builder<N, NetworkConnections<N, E>> nodeConnections = ImmutableMap.builder();

		for (N node : network.nodes()) {
			nodeConnections.put(node, connectionsOf(network, node));
		}

		return nodeConnections.build();
	}

	private static <N, E> Map<E, N> getEdgeToReferenceNode(Network<N, E> network) {
		Builder<E, N> edgeToReferenceNode = ImmutableMap.builder();

		for (E edge : network.edges()) {
			edgeToReferenceNode.put(edge, network.incidentNodes(edge).nodeU());
		}

		return edgeToReferenceNode.build();
	}

	private static <N, E> NetworkConnections<N, E> connectionsOf(Network<N, E> network, N node) {
		if (network.isDirected()) {
			Map<E, N> inEdgeMap = Maps.asMap(network.inEdges(node), sourceNodeFn(network));
			Map<E, N> outEdgeMap = Maps.asMap(network.outEdges(node), targetNodeFn(network));
			int selfLoopCount = network.edgesConnecting(node, node).size();
			return (NetworkConnections<N, E>)(network.allowsParallelEdges()
				? DirectedMultiNetworkConnections.ofImmutable(inEdgeMap, outEdgeMap, selfLoopCount)
				: DirectedNetworkConnections.ofImmutable(inEdgeMap, outEdgeMap, selfLoopCount));
		} else {
			Map<E, N> incidentEdgeMap = Maps.asMap(network.incidentEdges(node), adjacentNodeFn(network, node));
			return (NetworkConnections<N, E>)(network.allowsParallelEdges()
				? UndirectedMultiNetworkConnections.ofImmutable(incidentEdgeMap)
				: UndirectedNetworkConnections.ofImmutable(incidentEdgeMap));
		}
	}

	private static <N, E> Function<E, N> sourceNodeFn(Network<N, E> network) {
		return new Function<E, N>() {
			@Override
			public N apply(E edge) {
				return network.incidentNodes(edge).source();
			}
		};
	}

	private static <N, E> Function<E, N> targetNodeFn(Network<N, E> network) {
		return new Function<E, N>() {
			@Override
			public N apply(E edge) {
				return network.incidentNodes(edge).target();
			}
		};
	}

	private static <N, E> Function<E, N> adjacentNodeFn(Network<N, E> network, N node) {
		return new Function<E, N>() {
			@Override
			public N apply(E edge) {
				return network.incidentNodes(edge).adjacentNode(node);
			}
		};
	}
}
