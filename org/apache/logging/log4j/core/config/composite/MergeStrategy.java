package org.apache.logging.log4j.core.config.composite;

import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;

public interface MergeStrategy {
	void mergeRootProperties(Node node, AbstractConfiguration abstractConfiguration);

	void mergConfigurations(Node node1, Node node2, PluginManager pluginManager);
}
