package org.apache.logging.log4j.core.appender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginNode;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(
	name = "AppenderSet",
	category = "Core",
	printObject = true,
	deferChildren = true
)
public class AppenderSet {
	private static final StatusLogger LOGGER = StatusLogger.getLogger();
	private final Configuration configuration;
	private final Map<String, Node> nodeMap;

	@PluginBuilderFactory
	public static AppenderSet.Builder newBuilder() {
		return new AppenderSet.Builder();
	}

	private AppenderSet(Configuration configuration, Map<String, Node> appenders) {
		this.configuration = configuration;
		this.nodeMap = appenders;
	}

	public Appender createAppender(String appenderName, String actualName) {
		Node node = (Node)this.nodeMap.get(appenderName);
		if (node == null) {
			LOGGER.error("No node named {} in {}", appenderName, this);
			return null;
		} else {
			node.getAttributes().put("name", actualName);
			if (node.getType().getElementName().equals("appender")) {
				Node appNode = new Node(node);
				this.configuration.createConfiguration(appNode, null);
				if (appNode.getObject() instanceof Appender) {
					Appender app = appNode.getObject();
					app.start();
					return app;
				} else {
					LOGGER.error("Unable to create Appender of type " + node.getName());
					return null;
				}
			} else {
				LOGGER.error("No Appender was configured for name {} " + appenderName);
				return null;
			}
		}
	}

	public static class Builder implements org.apache.logging.log4j.core.util.Builder<AppenderSet> {
		@PluginNode
		private Node node;
		@PluginConfiguration
		@Required
		private Configuration configuration;

		public AppenderSet build() {
			if (this.configuration == null) {
				AppenderSet.LOGGER.error("Configuration is missing from AppenderSet {}", this);
				return null;
			} else if (this.node == null) {
				AppenderSet.LOGGER.error("No node in AppenderSet {}", this);
				return null;
			} else {
				List<Node> children = this.node.getChildren();
				if (children == null) {
					AppenderSet.LOGGER.error("No children node in AppenderSet {}", this);
					return null;
				} else {
					Map<String, Node> map = new HashMap(children.size());

					for (Node childNode : children) {
						String key = (String)childNode.getAttributes().get("name");
						if (key == null) {
							AppenderSet.LOGGER.error("The attribute 'name' is missing from from the node {} in AppenderSet {}", childNode, children);
						} else {
							map.put(key, childNode);
						}
					}

					return new AppenderSet(this.configuration, map);
				}
			}
		}

		public Node getNode() {
			return this.node;
		}

		public Configuration getConfiguration() {
			return this.configuration;
		}

		public AppenderSet.Builder withNode(Node node) {
			this.node = node;
			return this;
		}

		public AppenderSet.Builder withConfiguration(Configuration configuration) {
			this.configuration = configuration;
			return this;
		}

		public String toString() {
			return this.getClass().getName() + " [node=" + this.node + ", configuration=" + this.configuration + "]";
		}
	}
}
