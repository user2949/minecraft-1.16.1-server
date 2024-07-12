package org.apache.logging.log4j.core.config.json;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.ConfiguratonFileWatcher;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.Reconfigurable;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil;
import org.apache.logging.log4j.core.config.status.StatusConfiguration;
import org.apache.logging.log4j.core.util.FileWatcher;
import org.apache.logging.log4j.core.util.Patterns;

public class JsonConfiguration extends AbstractConfiguration implements Reconfigurable {
	private static final String[] VERBOSE_CLASSES = new String[]{ResolverUtil.class.getName()};
	private final List<JsonConfiguration.Status> status = new ArrayList();
	private JsonNode root;

	public JsonConfiguration(LoggerContext loggerContext, ConfigurationSource configSource) {
		super(loggerContext, configSource);
		File configFile = configSource.getFile();

		try {
			InputStream configStream = configSource.getInputStream();
			Throwable statusConfig = null;

			byte[] buffer;
			try {
				buffer = toByteArray(configStream);
			} catch (Throwable var19) {
				statusConfig = var19;
				throw var19;
			} finally {
				if (configStream != null) {
					if (statusConfig != null) {
						try {
							configStream.close();
						} catch (Throwable var18) {
							statusConfig.addSuppressed(var18);
						}
					} else {
						configStream.close();
					}
				}
			}

			InputStream is = new ByteArrayInputStream(buffer);
			this.root = this.getObjectMapper().readTree(is);
			if (this.root.size() == 1) {
				for (JsonNode node : this.root) {
					this.root = node;
				}
			}

			this.processAttributes(this.rootNode, this.root);
			StatusConfiguration statusConfigx = new StatusConfiguration().withVerboseClasses(VERBOSE_CLASSES).withStatus(this.getDefaultStatus());

			for (Entry<String, String> entry : this.rootNode.getAttributes().entrySet()) {
				String key = (String)entry.getKey();
				String value = this.getStrSubstitutor().replace((String)entry.getValue());
				if ("status".equalsIgnoreCase(key)) {
					statusConfigx.withStatus(value);
				} else if ("dest".equalsIgnoreCase(key)) {
					statusConfigx.withDestination(value);
				} else if ("shutdownHook".equalsIgnoreCase(key)) {
					this.isShutdownHookEnabled = !"disable".equalsIgnoreCase(value);
				} else if ("shutdownTimeout".equalsIgnoreCase(key)) {
					this.shutdownTimeoutMillis = Long.parseLong(value);
				} else if ("verbose".equalsIgnoreCase((String)entry.getKey())) {
					statusConfigx.withVerbosity(value);
				} else if ("packages".equalsIgnoreCase(key)) {
					this.pluginPackages.addAll(Arrays.asList(value.split(Patterns.COMMA_SEPARATOR)));
				} else if ("name".equalsIgnoreCase(key)) {
					this.setName(value);
				} else if ("monitorInterval".equalsIgnoreCase(key)) {
					int intervalSeconds = Integer.parseInt(value);
					if (intervalSeconds > 0) {
						this.getWatchManager().setIntervalSeconds(intervalSeconds);
						if (configFile != null) {
							FileWatcher watcher = new ConfiguratonFileWatcher(this, this.listeners);
							this.getWatchManager().watchFile(configFile, watcher);
						}
					}
				} else if ("advertiser".equalsIgnoreCase(key)) {
					this.createAdvertiser(value, configSource, buffer, "application/json");
				}
			}

			statusConfigx.initialize();
			if (this.getName() == null) {
				this.setName(configSource.getLocation());
			}
		} catch (Exception var21) {
			LOGGER.error("Error parsing " + configSource.getLocation(), (Throwable)var21);
		}
	}

	protected ObjectMapper getObjectMapper() {
		return new ObjectMapper().configure(Feature.ALLOW_COMMENTS, true);
	}

	@Override
	public void setup() {
		Iterator<Entry<String, JsonNode>> iter = this.root.fields();
		List<Node> children = this.rootNode.getChildren();

		while (iter.hasNext()) {
			Entry<String, JsonNode> entry = (Entry<String, JsonNode>)iter.next();
			JsonNode n = (JsonNode)entry.getValue();
			if (n.isObject()) {
				LOGGER.debug("Processing node for object {}", entry.getKey());
				children.add(this.constructNode((String)entry.getKey(), this.rootNode, n));
			} else if (n.isArray()) {
				LOGGER.error("Arrays are not supported at the root configuration.");
			}
		}

		LOGGER.debug("Completed parsing configuration");
		if (this.status.size() > 0) {
			for (JsonConfiguration.Status s : this.status) {
				LOGGER.error("Error processing element {}: {}", s.name, s.errorType);
			}
		}
	}

	@Override
	public Configuration reconfigure() {
		try {
			ConfigurationSource source = this.getConfigurationSource().resetInputStream();
			return source == null ? null : new JsonConfiguration(this.getLoggerContext(), source);
		} catch (IOException var2) {
			LOGGER.error("Cannot locate file {}", this.getConfigurationSource(), var2);
			return null;
		}
	}

	private Node constructNode(String name, Node parent, JsonNode jsonNode) {
		PluginType<?> type = this.pluginManager.getPluginType(name);
		Node node = new Node(parent, name, type);
		this.processAttributes(node, jsonNode);
		Iterator<Entry<String, JsonNode>> iter = jsonNode.fields();
		List<Node> children = node.getChildren();

		while (iter.hasNext()) {
			Entry<String, JsonNode> entry = (Entry<String, JsonNode>)iter.next();
			JsonNode n = (JsonNode)entry.getValue();
			if (!n.isArray() && !n.isObject()) {
				LOGGER.debug("Node {} is of type {}", entry.getKey(), n.getNodeType());
			} else {
				if (type == null) {
					this.status.add(new JsonConfiguration.Status(name, n, JsonConfiguration.ErrorType.CLASS_NOT_FOUND));
				}

				if (!n.isArray()) {
					LOGGER.debug("Processing node for object {}", entry.getKey());
					children.add(this.constructNode((String)entry.getKey(), node, n));
				} else {
					LOGGER.debug("Processing node for array {}", entry.getKey());

					for (int i = 0; i < n.size(); i++) {
						String pluginType = this.getType(n.get(i), (String)entry.getKey());
						PluginType<?> entryType = this.pluginManager.getPluginType(pluginType);
						Node item = new Node(node, (String)entry.getKey(), entryType);
						this.processAttributes(item, n.get(i));
						if (pluginType.equals(entry.getKey())) {
							LOGGER.debug("Processing {}[{}]", entry.getKey(), i);
						} else {
							LOGGER.debug("Processing {} {}[{}]", pluginType, entry.getKey(), i);
						}

						Iterator<Entry<String, JsonNode>> itemIter = n.get(i).fields();
						List<Node> itemChildren = item.getChildren();

						while (itemIter.hasNext()) {
							Entry<String, JsonNode> itemEntry = (Entry<String, JsonNode>)itemIter.next();
							if (((JsonNode)itemEntry.getValue()).isObject()) {
								LOGGER.debug("Processing node for object {}", itemEntry.getKey());
								itemChildren.add(this.constructNode((String)itemEntry.getKey(), item, (JsonNode)itemEntry.getValue()));
							} else if (((JsonNode)itemEntry.getValue()).isArray()) {
								JsonNode array = (JsonNode)itemEntry.getValue();
								String entryName = (String)itemEntry.getKey();
								LOGGER.debug("Processing array for object {}", entryName);

								for (int j = 0; j < array.size(); j++) {
									itemChildren.add(this.constructNode(entryName, item, array.get(j)));
								}
							}
						}

						children.add(item);
					}
				}
			}
		}

		String t;
		if (type == null) {
			t = "null";
		} else {
			t = type.getElementName() + ':' + type.getPluginClass();
		}

		String p = node.getParent() == null ? "null" : (node.getParent().getName() == null ? "root" : node.getParent().getName());
		LOGGER.debug("Returning {} with parent {} of type {}", node.getName(), p, t);
		return node;
	}

	private String getType(JsonNode node, String name) {
		Iterator<Entry<String, JsonNode>> iter = node.fields();

		while (iter.hasNext()) {
			Entry<String, JsonNode> entry = (Entry<String, JsonNode>)iter.next();
			if (((String)entry.getKey()).equalsIgnoreCase("type")) {
				JsonNode n = (JsonNode)entry.getValue();
				if (n.isValueNode()) {
					return n.asText();
				}
			}
		}

		return name;
	}

	private void processAttributes(Node parent, JsonNode node) {
		Map<String, String> attrs = parent.getAttributes();
		Iterator<Entry<String, JsonNode>> iter = node.fields();

		while (iter.hasNext()) {
			Entry<String, JsonNode> entry = (Entry<String, JsonNode>)iter.next();
			if (!((String)entry.getKey()).equalsIgnoreCase("type")) {
				JsonNode n = (JsonNode)entry.getValue();
				if (n.isValueNode()) {
					attrs.put(entry.getKey(), n.asText());
				}
			}
		}
	}

	public String toString() {
		return this.getClass().getSimpleName() + "[location=" + this.getConfigurationSource() + "]";
	}

	private static enum ErrorType {
		CLASS_NOT_FOUND;
	}

	private static class Status {
		private final JsonNode node;
		private final String name;
		private final JsonConfiguration.ErrorType errorType;

		public Status(String name, JsonNode node, JsonConfiguration.ErrorType errorType) {
			this.name = name;
			this.node = node;
			this.errorType = errorType;
		}

		public String toString() {
			return "Status [name=" + this.name + ", errorType=" + this.errorType + ", node=" + this.node + "]";
		}
	}
}
