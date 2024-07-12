package org.apache.logging.log4j.core.config.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
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
import org.apache.logging.log4j.core.util.Closer;
import org.apache.logging.log4j.core.util.FileWatcher;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.core.util.Patterns;
import org.apache.logging.log4j.core.util.Throwables;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlConfiguration extends AbstractConfiguration implements Reconfigurable {
	private static final String XINCLUDE_FIXUP_LANGUAGE = "http://apache.org/xml/features/xinclude/fixup-language";
	private static final String XINCLUDE_FIXUP_BASE_URIS = "http://apache.org/xml/features/xinclude/fixup-base-uris";
	private static final String[] VERBOSE_CLASSES = new String[]{ResolverUtil.class.getName()};
	private static final String LOG4J_XSD = "Log4j-config.xsd";
	private final List<XmlConfiguration.Status> status = new ArrayList();
	private Element rootElement;
	private boolean strict;
	private String schemaResource;

	public XmlConfiguration(LoggerContext loggerContext, ConfigurationSource configSource) {
		super(loggerContext, configSource);
		File configFile = configSource.getFile();
		byte[] buffer = null;

		try {
			InputStream configStream = configSource.getInputStream();

			try {
				buffer = toByteArray(configStream);
			} finally {
				Closer.closeSilently(configStream);
			}

			InputSource source = new InputSource(new ByteArrayInputStream(buffer));
			source.setSystemId(configSource.getLocation());
			DocumentBuilder x2 = newDocumentBuilder(true);

			Document document;
			try {
				document = x2.parse(source);
			} catch (Exception var48) {
				Throwable throwable = Throwables.getRootCause(var48);
				if (!(throwable instanceof UnsupportedOperationException)) {
					throw var48;
				}

				LOGGER.warn("The DocumentBuilder {} does not support an operation: {}.Trying again without XInclude...", x2, var48);
				document = newDocumentBuilder(false).parse(source);
			}

			this.rootElement = document.getDocumentElement();
			Map<String, String> attrs = this.processAttributes(this.rootNode, this.rootElement);
			StatusConfiguration statusConfig = new StatusConfiguration().withVerboseClasses(VERBOSE_CLASSES).withStatus(this.getDefaultStatus());

			for (Entry<String, String> entry : attrs.entrySet()) {
				String key = (String)entry.getKey();
				String value = this.getStrSubstitutor().replace((String)entry.getValue());
				if ("status".equalsIgnoreCase(key)) {
					statusConfig.withStatus(value);
				} else if ("dest".equalsIgnoreCase(key)) {
					statusConfig.withDestination(value);
				} else if ("shutdownHook".equalsIgnoreCase(key)) {
					this.isShutdownHookEnabled = !"disable".equalsIgnoreCase(value);
				} else if ("shutdownTimeout".equalsIgnoreCase(key)) {
					this.shutdownTimeoutMillis = Long.parseLong(value);
				} else if ("verbose".equalsIgnoreCase(key)) {
					statusConfig.withVerbosity(value);
				} else if ("packages".equalsIgnoreCase(key)) {
					this.pluginPackages.addAll(Arrays.asList(value.split(Patterns.COMMA_SEPARATOR)));
				} else if ("name".equalsIgnoreCase(key)) {
					this.setName(value);
				} else if ("strict".equalsIgnoreCase(key)) {
					this.strict = Boolean.parseBoolean(value);
				} else if ("schema".equalsIgnoreCase(key)) {
					this.schemaResource = value;
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
					this.createAdvertiser(value, configSource, buffer, "text/xml");
				}
			}

			statusConfig.initialize();
		} catch (IOException | ParserConfigurationException | SAXException var49) {
			LOGGER.error("Error parsing " + configSource.getLocation(), (Throwable)var49);
		}

		if (this.strict && this.schemaResource != null && buffer != null) {
			try {
				InputStream is = Loader.getResourceAsStream(this.schemaResource, XmlConfiguration.class.getClassLoader());
				Throwable var51 = null;

				try {
					if (is != null) {
						Source src = new StreamSource(is, "Log4j-config.xsd");
						SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
						Schema schema = null;

						try {
							schema = factory.newSchema(src);
						} catch (SAXException var43) {
							LOGGER.error("Error parsing Log4j schema", (Throwable)var43);
						}

						if (schema != null) {
							Validator validator = schema.newValidator();

							try {
								validator.validate(new StreamSource(new ByteArrayInputStream(buffer)));
							} catch (IOException var41) {
								LOGGER.error("Error reading configuration for validation", (Throwable)var41);
							} catch (SAXException var42) {
								LOGGER.error("Error validating configuration", (Throwable)var42);
							}
						}
					}
				} catch (Throwable var44) {
					var51 = var44;
					throw var44;
				} finally {
					if (is != null) {
						if (var51 != null) {
							try {
								is.close();
							} catch (Throwable var40) {
								var51.addSuppressed(var40);
							}
						} else {
							is.close();
						}
					}
				}
			} catch (Exception var47) {
				LOGGER.error("Unable to access schema {}", this.schemaResource, var47);
			}
		}

		if (this.getName() == null) {
			this.setName(configSource.getLocation());
		}
	}

	static DocumentBuilder newDocumentBuilder(boolean xIncludeAware) throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		if (xIncludeAware) {
			enableXInclude(factory);
		}

		return factory.newDocumentBuilder();
	}

	private static void enableXInclude(DocumentBuilderFactory factory) {
		try {
			factory.setXIncludeAware(true);
		} catch (UnsupportedOperationException var6) {
			LOGGER.warn("The DocumentBuilderFactory [{}] does not support XInclude: {}", factory, var6);
		} catch (NoSuchMethodError | AbstractMethodError var7) {
			LOGGER.warn("The DocumentBuilderFactory [{}] is out of date and does not support XInclude: {}", factory, var7);
		}

		try {
			factory.setFeature("http://apache.org/xml/features/xinclude/fixup-base-uris", true);
		} catch (ParserConfigurationException var4) {
			LOGGER.warn(
				"The DocumentBuilderFactory [{}] does not support the feature [{}]: {}", factory, "http://apache.org/xml/features/xinclude/fixup-base-uris", var4
			);
		} catch (AbstractMethodError var5) {
			LOGGER.warn("The DocumentBuilderFactory [{}] is out of date and does not support setFeature: {}", factory, var5);
		}

		try {
			factory.setFeature("http://apache.org/xml/features/xinclude/fixup-language", true);
		} catch (ParserConfigurationException var2) {
			LOGGER.warn("The DocumentBuilderFactory [{}] does not support the feature [{}]: {}", factory, "http://apache.org/xml/features/xinclude/fixup-language", var2);
		} catch (AbstractMethodError var3) {
			LOGGER.warn("The DocumentBuilderFactory [{}] is out of date and does not support setFeature: {}", factory, var3);
		}
	}

	@Override
	public void setup() {
		if (this.rootElement == null) {
			LOGGER.error("No logging configuration");
		} else {
			this.constructHierarchy(this.rootNode, this.rootElement);
			if (this.status.size() <= 0) {
				this.rootElement = null;
			} else {
				for (XmlConfiguration.Status s : this.status) {
					LOGGER.error("Error processing element {} ({}): {}", s.name, s.element, s.errorType);
				}
			}
		}
	}

	@Override
	public Configuration reconfigure() {
		try {
			ConfigurationSource source = this.getConfigurationSource().resetInputStream();
			if (source == null) {
				return null;
			} else {
				XmlConfiguration config = new XmlConfiguration(this.getLoggerContext(), source);
				return config.rootElement == null ? null : config;
			}
		} catch (IOException var3) {
			LOGGER.error("Cannot locate file {}", this.getConfigurationSource(), var3);
			return null;
		}
	}

	private void constructHierarchy(Node node, Element element) {
		this.processAttributes(node, element);
		StringBuilder buffer = new StringBuilder();
		NodeList list = element.getChildNodes();
		List<Node> children = node.getChildren();

		for (int i = 0; i < list.getLength(); i++) {
			org.w3c.dom.Node w3cNode = list.item(i);
			if (w3cNode instanceof Element) {
				Element child = (Element)w3cNode;
				String name = this.getType(child);
				PluginType<?> type = this.pluginManager.getPluginType(name);
				Node childNode = new Node(node, name, type);
				this.constructHierarchy(childNode, child);
				if (type == null) {
					String value = childNode.getValue();
					if (!childNode.hasChildren() && value != null) {
						node.getAttributes().put(name, value);
					} else {
						this.status.add(new XmlConfiguration.Status(name, element, XmlConfiguration.ErrorType.CLASS_NOT_FOUND));
					}
				} else {
					children.add(childNode);
				}
			} else if (w3cNode instanceof Text) {
				Text data = (Text)w3cNode;
				buffer.append(data.getData());
			}
		}

		String text = buffer.toString().trim();
		if (text.length() > 0 || !node.hasChildren() && !node.isRoot()) {
			node.setValue(text);
		}
	}

	private String getType(Element element) {
		if (this.strict) {
			NamedNodeMap attrs = element.getAttributes();

			for (int i = 0; i < attrs.getLength(); i++) {
				org.w3c.dom.Node w3cNode = attrs.item(i);
				if (w3cNode instanceof Attr) {
					Attr attr = (Attr)w3cNode;
					if (attr.getName().equalsIgnoreCase("type")) {
						String type = attr.getValue();
						attrs.removeNamedItem(attr.getName());
						return type;
					}
				}
			}
		}

		return element.getTagName();
	}

	private Map<String, String> processAttributes(Node node, Element element) {
		NamedNodeMap attrs = element.getAttributes();
		Map<String, String> attributes = node.getAttributes();

		for (int i = 0; i < attrs.getLength(); i++) {
			org.w3c.dom.Node w3cNode = attrs.item(i);
			if (w3cNode instanceof Attr) {
				Attr attr = (Attr)w3cNode;
				if (!attr.getName().equals("xml:base")) {
					attributes.put(attr.getName(), attr.getValue());
				}
			}
		}

		return attributes;
	}

	public String toString() {
		return this.getClass().getSimpleName() + "[location=" + this.getConfigurationSource() + "]";
	}

	private static enum ErrorType {
		CLASS_NOT_FOUND;
	}

	private static class Status {
		private final Element element;
		private final String name;
		private final XmlConfiguration.ErrorType errorType;

		public Status(String name, Element element, XmlConfiguration.ErrorType errorType) {
			this.name = name;
			this.element = element;
			this.errorType = errorType;
		}

		public String toString() {
			return "Status [name=" + this.name + ", element=" + this.element + ", errorType=" + this.errorType + "]";
		}
	}
}
