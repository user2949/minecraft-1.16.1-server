package org.apache.logging.log4j.core.config.composite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;
import org.apache.logging.log4j.core.filter.CompositeFilter;

public class DefaultMergeStrategy implements MergeStrategy {
	private static final String APPENDERS = "appenders";
	private static final String PROPERTIES = "properties";
	private static final String LOGGERS = "loggers";
	private static final String SCRIPTS = "scripts";
	private static final String FILTERS = "filters";
	private static final String STATUS = "status";
	private static final String NAME = "name";
	private static final String REF = "ref";

	@Override
	public void mergeRootProperties(Node rootNode, AbstractConfiguration configuration) {
		for (Entry<String, String> attribute : configuration.getRootNode().getAttributes().entrySet()) {
			boolean isFound = false;

			for (Entry<String, String> targetAttribute : rootNode.getAttributes().entrySet()) {
				if (((String)targetAttribute.getKey()).equalsIgnoreCase((String)attribute.getKey())) {
					if (((String)attribute.getKey()).equalsIgnoreCase("status")) {
						Level targetLevel = Level.getLevel(((String)targetAttribute.getValue()).toUpperCase());
						Level sourceLevel = Level.getLevel(((String)attribute.getValue()).toUpperCase());
						if (targetLevel != null && sourceLevel != null) {
							if (sourceLevel.isLessSpecificThan(targetLevel)) {
								targetAttribute.setValue(attribute.getValue());
							}
						} else if (sourceLevel != null) {
							targetAttribute.setValue(attribute.getValue());
						}
					} else if (((String)attribute.getKey()).equalsIgnoreCase("monitorInterval")) {
						int sourceInterval = Integer.parseInt((String)attribute.getValue());
						int targetInterval = Integer.parseInt((String)targetAttribute.getValue());
						if (targetInterval == 0 || sourceInterval < targetInterval) {
							targetAttribute.setValue(attribute.getValue());
						}
					} else {
						targetAttribute.setValue(attribute.getValue());
					}

					isFound = true;
				}
			}

			if (!isFound) {
				rootNode.getAttributes().put(attribute.getKey(), attribute.getValue());
			}
		}
	}

	@Override
	public void mergConfigurations(Node target, Node source, PluginManager pluginManager) {
		for (Node sourceChildNode : source.getChildren()) {
			boolean isFilter = this.isFilterNode(sourceChildNode);
			boolean isMerged = false;

			for (Node targetChildNode : target.getChildren()) {
				if (isFilter) {
					if (this.isFilterNode(targetChildNode)) {
						this.updateFilterNode(target, targetChildNode, sourceChildNode, pluginManager);
						isMerged = true;
						break;
					}
				} else if (targetChildNode.getName().equalsIgnoreCase(sourceChildNode.getName())) {
					String var10 = targetChildNode.getName().toLowerCase();
					switch (var10) {
						case "properties":
						case "scripts":
						case "appenders":
							for (Node node : sourceChildNode.getChildren()) {
								for (Node targetNode : targetChildNode.getChildren()) {
									if (((String)targetNode.getAttributes().get("name")).equals(node.getAttributes().get("name"))) {
										targetChildNode.getChildren().remove(targetNode);
										break;
									}
								}

								targetChildNode.getChildren().add(node);
							}

							isMerged = true;
							break;
						case "loggers":
							Map<String, Node> targetLoggers = new HashMap();

							for (Node node : targetChildNode.getChildren()) {
								targetLoggers.put(node.getName(), node);
							}

							for (Node node : sourceChildNode.getChildren()) {
								Node targetNodex = this.getLoggerNode(targetChildNode, (String)node.getAttributes().get("name"));
								Node loggerNode = new Node(targetChildNode, node.getName(), node.getType());
								if (targetNodex != null) {
									targetNodex.getAttributes().putAll(node.getAttributes());

									for (Node sourceLoggerChild : node.getChildren()) {
										if (this.isFilterNode(sourceLoggerChild)) {
											boolean foundFilter = false;

											for (Node targetChild : targetNodex.getChildren()) {
												if (this.isFilterNode(targetChild)) {
													this.updateFilterNode(loggerNode, targetChild, sourceLoggerChild, pluginManager);
													foundFilter = true;
													break;
												}
											}

											if (!foundFilter) {
												Node childNode = new Node(loggerNode, sourceLoggerChild.getName(), sourceLoggerChild.getType());
												targetNodex.getChildren().add(childNode);
											}
										} else {
											Node childNode = new Node(loggerNode, sourceLoggerChild.getName(), sourceLoggerChild.getType());
											childNode.getAttributes().putAll(sourceLoggerChild.getAttributes());
											childNode.getChildren().addAll(sourceLoggerChild.getChildren());
											if (childNode.getName().equalsIgnoreCase("AppenderRef")) {
												for (Node targetChildx : targetNodex.getChildren()) {
													if (this.isSameReference(targetChildx, childNode)) {
														targetNodex.getChildren().remove(targetChildx);
														break;
													}
												}
											} else {
												for (Node targetChildxx : targetNodex.getChildren()) {
													if (this.isSameName(targetChildxx, childNode)) {
														targetNodex.getChildren().remove(targetChildxx);
														break;
													}
												}
											}

											targetNodex.getChildren().add(childNode);
										}
									}
								} else {
									loggerNode.getAttributes().putAll(node.getAttributes());
									loggerNode.getChildren().addAll(node.getChildren());
									targetChildNode.getChildren().add(loggerNode);
								}
							}

							isMerged = true;
							break;
						default:
							targetChildNode.getChildren().addAll(sourceChildNode.getChildren());
							isMerged = true;
					}
				}
			}

			if (!isMerged) {
				if (sourceChildNode.getName().equalsIgnoreCase("Properties")) {
					target.getChildren().add(0, sourceChildNode);
				} else {
					target.getChildren().add(sourceChildNode);
				}
			}
		}
	}

	private Node getLoggerNode(Node parentNode, String name) {
		for (Node node : parentNode.getChildren()) {
			String nodeName = (String)node.getAttributes().get("name");
			if (name == null && nodeName == null) {
				return node;
			}

			if (nodeName != null && nodeName.equals(name)) {
				return node;
			}
		}

		return null;
	}

	private void updateFilterNode(Node target, Node targetChildNode, Node sourceChildNode, PluginManager pluginManager) {
		if (CompositeFilter.class.isAssignableFrom(targetChildNode.getType().getPluginClass())) {
			Node node = new Node(targetChildNode, sourceChildNode.getName(), sourceChildNode.getType());
			node.getChildren().addAll(sourceChildNode.getChildren());
			node.getAttributes().putAll(sourceChildNode.getAttributes());
			targetChildNode.getChildren().add(node);
		} else {
			PluginType pluginType = pluginManager.getPluginType("filters");
			Node filtersNode = new Node(targetChildNode, "filters", pluginType);
			Node node = new Node(filtersNode, sourceChildNode.getName(), sourceChildNode.getType());
			node.getAttributes().putAll(sourceChildNode.getAttributes());
			List<Node> children = filtersNode.getChildren();
			children.add(targetChildNode);
			children.add(node);
			List<Node> nodes = target.getChildren();
			nodes.remove(targetChildNode);
			nodes.add(filtersNode);
		}
	}

	private boolean isFilterNode(Node node) {
		return Filter.class.isAssignableFrom(node.getType().getPluginClass());
	}

	private boolean isSameName(Node node1, Node node2) {
		String value = (String)node1.getAttributes().get("name");
		return value != null && value.toLowerCase().equals(((String)node2.getAttributes().get("name")).toLowerCase());
	}

	private boolean isSameReference(Node node1, Node node2) {
		String value = (String)node1.getAttributes().get("ref");
		return value != null && value.toLowerCase().equals(((String)node2.getAttributes().get("ref")).toLowerCase());
	}
}
