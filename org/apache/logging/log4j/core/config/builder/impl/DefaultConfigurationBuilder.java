package org.apache.logging.log4j.core.config.builder.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.core.config.ConfigurationException;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.AppenderRefComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.Component;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.CustomLevelComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ScriptComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ScriptFileComponentBuilder;
import org.apache.logging.log4j.core.util.Throwables;

public class DefaultConfigurationBuilder<T extends BuiltConfiguration> implements ConfigurationBuilder<T> {
	private static final String INDENT = "  ";
	private static final String EOL = System.lineSeparator();
	private final Component root = new Component();
	private Component loggers;
	private Component appenders;
	private Component filters;
	private Component properties;
	private Component customLevels;
	private Component scripts;
	private final Class<T> clazz;
	private ConfigurationSource source;
	private int monitorInterval;
	private Level level;
	private String verbosity;
	private String destination;
	private String packages;
	private String shutdownFlag;
	private long shutdownTimeoutMillis;
	private String advertiser;
	private LoggerContext loggerContext;
	private String name;

	public DefaultConfigurationBuilder() {
		this((Class<T>)BuiltConfiguration.class);
		this.root.addAttribute("name", "Built");
	}

	public DefaultConfigurationBuilder(Class<T> clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("A Configuration class must be provided");
		} else {
			this.clazz = clazz;
			List<Component> components = this.root.getComponents();
			this.properties = new Component("Properties");
			components.add(this.properties);
			this.scripts = new Component("Scripts");
			components.add(this.scripts);
			this.customLevels = new Component("CustomLevels");
			components.add(this.customLevels);
			this.filters = new Component("Filters");
			components.add(this.filters);
			this.appenders = new Component("Appenders");
			components.add(this.appenders);
			this.loggers = new Component("Loggers");
			components.add(this.loggers);
		}
	}

	protected ConfigurationBuilder<T> add(Component parent, ComponentBuilder<?> builder) {
		parent.getComponents().add(builder.build());
		return this;
	}

	@Override
	public ConfigurationBuilder<T> add(AppenderComponentBuilder builder) {
		return this.add(this.appenders, builder);
	}

	@Override
	public ConfigurationBuilder<T> add(CustomLevelComponentBuilder builder) {
		return this.add(this.customLevels, builder);
	}

	@Override
	public ConfigurationBuilder<T> add(FilterComponentBuilder builder) {
		return this.add(this.filters, builder);
	}

	@Override
	public ConfigurationBuilder<T> add(ScriptComponentBuilder builder) {
		return this.add(this.scripts, builder);
	}

	@Override
	public ConfigurationBuilder<T> add(ScriptFileComponentBuilder builder) {
		return this.add(this.scripts, builder);
	}

	@Override
	public ConfigurationBuilder<T> add(LoggerComponentBuilder builder) {
		return this.add(this.loggers, builder);
	}

	@Override
	public ConfigurationBuilder<T> add(RootLoggerComponentBuilder builder) {
		for (Component c : this.loggers.getComponents()) {
			if (c.getPluginType().equals("root")) {
				throw new ConfigurationException("Root Logger was previously defined");
			}
		}

		return this.add(this.loggers, builder);
	}

	@Override
	public ConfigurationBuilder<T> addProperty(String key, String value) {
		this.properties.addComponent((Component)this.newComponent(key, "Property", value).build());
		return this;
	}

	public T build() {
		return this.build(true);
	}

	public T build(boolean initialize) {
		T configuration;
		try {
			if (this.source == null) {
				this.source = ConfigurationSource.NULL_SOURCE;
			}

			Constructor<T> constructor = this.clazz.getConstructor(LoggerContext.class, ConfigurationSource.class, Component.class);
			configuration = (T)constructor.newInstance(this.loggerContext, this.source, this.root);
			configuration.setMonitorInterval(this.monitorInterval);
			configuration.getRootNode().getAttributes().putAll(this.root.getAttributes());
			if (this.name != null) {
				configuration.setName(this.name);
			}

			if (this.level != null) {
				configuration.getStatusConfiguration().withStatus(this.level);
			}

			if (this.verbosity != null) {
				configuration.getStatusConfiguration().withVerbosity(this.verbosity);
			}

			if (this.destination != null) {
				configuration.getStatusConfiguration().withDestination(this.destination);
			}

			if (this.packages != null) {
				configuration.setPluginPackages(this.packages);
			}

			if (this.shutdownFlag != null) {
				configuration.setShutdownHook(this.shutdownFlag);
			}

			if (this.shutdownTimeoutMillis > 0L) {
				configuration.setShutdownTimeoutMillis(this.shutdownTimeoutMillis);
			}

			if (this.advertiser != null) {
				configuration.createAdvertiser(this.advertiser, this.source);
			}
		} catch (Exception var4) {
			throw new IllegalArgumentException("Invalid Configuration class specified", var4);
		}

		configuration.getStatusConfiguration().initialize();
		if (initialize) {
			configuration.initialize();
		}

		return configuration;
	}

	@Override
	public void writeXmlConfiguration(OutputStream output) throws IOException {
		try {
			XMLStreamWriter xmlWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(output);
			this.writeXmlConfiguration(xmlWriter);
			xmlWriter.close();
		} catch (XMLStreamException var3) {
			if (var3.getNestedException() instanceof IOException) {
				throw (IOException)var3.getNestedException();
			}

			Throwables.rethrow(var3);
		}
	}

	@Override
	public String toXmlConfiguration() {
		StringWriter sw = new StringWriter();

		try {
			XMLStreamWriter xmlWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(sw);
			this.writeXmlConfiguration(xmlWriter);
			xmlWriter.close();
		} catch (XMLStreamException var3) {
			Throwables.rethrow(var3);
		}

		return sw.toString();
	}

	private void writeXmlConfiguration(XMLStreamWriter xmlWriter) throws XMLStreamException {
		xmlWriter.writeStartDocument();
		xmlWriter.writeCharacters(EOL);
		xmlWriter.writeStartElement("Configuration");
		if (this.name != null) {
			xmlWriter.writeAttribute("name", this.name);
		}

		if (this.level != null) {
			xmlWriter.writeAttribute("status", this.level.name());
		}

		if (this.verbosity != null) {
			xmlWriter.writeAttribute("verbose", this.verbosity);
		}

		if (this.destination != null) {
			xmlWriter.writeAttribute("dest", this.destination);
		}

		if (this.packages != null) {
			xmlWriter.writeAttribute("packages", this.packages);
		}

		if (this.shutdownFlag != null) {
			xmlWriter.writeAttribute("shutdownHook", this.shutdownFlag);
		}

		if (this.shutdownTimeoutMillis > 0L) {
			xmlWriter.writeAttribute("shutdownTimeout", String.valueOf(this.shutdownTimeoutMillis));
		}

		if (this.advertiser != null) {
			xmlWriter.writeAttribute("advertiser", this.advertiser);
		}

		if (this.monitorInterval > 0) {
			xmlWriter.writeAttribute("monitorInterval", String.valueOf(this.monitorInterval));
		}

		xmlWriter.writeCharacters(EOL);
		this.writeXmlSection(xmlWriter, this.properties);
		this.writeXmlSection(xmlWriter, this.scripts);
		this.writeXmlSection(xmlWriter, this.customLevels);
		if (this.filters.getComponents().size() == 1) {
			this.writeXmlComponent(xmlWriter, (Component)this.filters.getComponents().get(0), 1);
		} else if (this.filters.getComponents().size() > 1) {
			this.writeXmlSection(xmlWriter, this.filters);
		}

		this.writeXmlSection(xmlWriter, this.appenders);
		this.writeXmlSection(xmlWriter, this.loggers);
		xmlWriter.writeEndElement();
		xmlWriter.writeCharacters(EOL);
		xmlWriter.writeEndDocument();
	}

	private void writeXmlSection(XMLStreamWriter xmlWriter, Component component) throws XMLStreamException {
		if (!component.getAttributes().isEmpty() || !component.getComponents().isEmpty() || component.getValue() != null) {
			this.writeXmlComponent(xmlWriter, component, 1);
		}
	}

	private void writeXmlComponent(XMLStreamWriter xmlWriter, Component component, int nesting) throws XMLStreamException {
		if (component.getComponents().isEmpty() && component.getValue() == null) {
			this.writeXmlIndent(xmlWriter, nesting);
			xmlWriter.writeEmptyElement(component.getPluginType());
			this.writeXmlAttributes(xmlWriter, component);
		} else {
			this.writeXmlIndent(xmlWriter, nesting);
			xmlWriter.writeStartElement(component.getPluginType());
			this.writeXmlAttributes(xmlWriter, component);
			if (!component.getComponents().isEmpty()) {
				xmlWriter.writeCharacters(EOL);
			}

			for (Component subComponent : component.getComponents()) {
				this.writeXmlComponent(xmlWriter, subComponent, nesting + 1);
			}

			if (component.getValue() != null) {
				xmlWriter.writeCharacters(component.getValue());
			}

			if (!component.getComponents().isEmpty()) {
				this.writeXmlIndent(xmlWriter, nesting);
			}

			xmlWriter.writeEndElement();
		}

		xmlWriter.writeCharacters(EOL);
	}

	private void writeXmlIndent(XMLStreamWriter xmlWriter, int nesting) throws XMLStreamException {
		for (int i = 0; i < nesting; i++) {
			xmlWriter.writeCharacters("  ");
		}
	}

	private void writeXmlAttributes(XMLStreamWriter xmlWriter, Component component) throws XMLStreamException {
		for (Entry<String, String> attribute : component.getAttributes().entrySet()) {
			xmlWriter.writeAttribute((String)attribute.getKey(), (String)attribute.getValue());
		}
	}

	@Override
	public ScriptComponentBuilder newScript(String name, String language, String text) {
		return new DefaultScriptComponentBuilder(this, name, language, text);
	}

	@Override
	public ScriptFileComponentBuilder newScriptFile(String path) {
		return new DefaultScriptFileComponentBuilder(this, path, path);
	}

	@Override
	public ScriptFileComponentBuilder newScriptFile(String name, String path) {
		return new DefaultScriptFileComponentBuilder(this, name, path);
	}

	@Override
	public AppenderComponentBuilder newAppender(String name, String type) {
		return new DefaultAppenderComponentBuilder(this, name, type);
	}

	@Override
	public AppenderRefComponentBuilder newAppenderRef(String ref) {
		return new DefaultAppenderRefComponentBuilder(this, ref);
	}

	@Override
	public LoggerComponentBuilder newAsyncLogger(String name, Level level) {
		return new DefaultLoggerComponentBuilder(this, name, level.toString(), "AsyncLogger");
	}

	@Override
	public LoggerComponentBuilder newAsyncLogger(String name, Level level, boolean includeLocation) {
		return new DefaultLoggerComponentBuilder(this, name, level.toString(), "AsyncLogger", includeLocation);
	}

	@Override
	public LoggerComponentBuilder newAsyncLogger(String name, String level) {
		return new DefaultLoggerComponentBuilder(this, name, level, "AsyncLogger");
	}

	@Override
	public LoggerComponentBuilder newAsyncLogger(String name, String level, boolean includeLocation) {
		return new DefaultLoggerComponentBuilder(this, name, level, "AsyncLogger");
	}

	@Override
	public RootLoggerComponentBuilder newAsyncRootLogger(Level level) {
		return new DefaultRootLoggerComponentBuilder(this, level.toString(), "AsyncRoot");
	}

	@Override
	public RootLoggerComponentBuilder newAsyncRootLogger(Level level, boolean includeLocation) {
		return new DefaultRootLoggerComponentBuilder(this, level.toString(), "AsyncRoot", includeLocation);
	}

	@Override
	public RootLoggerComponentBuilder newAsyncRootLogger(String level) {
		return new DefaultRootLoggerComponentBuilder(this, level, "AsyncRoot");
	}

	@Override
	public RootLoggerComponentBuilder newAsyncRootLogger(String level, boolean includeLocation) {
		return new DefaultRootLoggerComponentBuilder(this, level, "AsyncRoot", includeLocation);
	}

	@Override
	public <B extends ComponentBuilder<B>> ComponentBuilder<B> newComponent(String type) {
		return new DefaultComponentBuilder<>(this, type);
	}

	@Override
	public <B extends ComponentBuilder<B>> ComponentBuilder<B> newComponent(String name, String type) {
		return new DefaultComponentBuilder<>(this, name, type);
	}

	@Override
	public <B extends ComponentBuilder<B>> ComponentBuilder<B> newComponent(String name, String type, String value) {
		return new DefaultComponentBuilder<>(this, name, type, value);
	}

	@Override
	public CustomLevelComponentBuilder newCustomLevel(String name, int level) {
		return new DefaultCustomLevelComponentBuilder(this, name, level);
	}

	@Override
	public FilterComponentBuilder newFilter(String type, Result onMatch, Result onMisMatch) {
		return new DefaultFilterComponentBuilder(this, type, onMatch.name(), onMisMatch.name());
	}

	@Override
	public FilterComponentBuilder newFilter(String type, String onMatch, String onMisMatch) {
		return new DefaultFilterComponentBuilder(this, type, onMatch, onMisMatch);
	}

	@Override
	public LayoutComponentBuilder newLayout(String type) {
		return new DefaultLayoutComponentBuilder(this, type);
	}

	@Override
	public LoggerComponentBuilder newLogger(String name, Level level) {
		return new DefaultLoggerComponentBuilder(this, name, level.toString());
	}

	@Override
	public LoggerComponentBuilder newLogger(String name, Level level, boolean includeLocation) {
		return new DefaultLoggerComponentBuilder(this, name, level.toString(), includeLocation);
	}

	@Override
	public LoggerComponentBuilder newLogger(String name, String level) {
		return new DefaultLoggerComponentBuilder(this, name, level);
	}

	@Override
	public LoggerComponentBuilder newLogger(String name, String level, boolean includeLocation) {
		return new DefaultLoggerComponentBuilder(this, name, level, includeLocation);
	}

	@Override
	public RootLoggerComponentBuilder newRootLogger(Level level) {
		return new DefaultRootLoggerComponentBuilder(this, level.toString());
	}

	@Override
	public RootLoggerComponentBuilder newRootLogger(Level level, boolean includeLocation) {
		return new DefaultRootLoggerComponentBuilder(this, level.toString(), includeLocation);
	}

	@Override
	public RootLoggerComponentBuilder newRootLogger(String level) {
		return new DefaultRootLoggerComponentBuilder(this, level);
	}

	@Override
	public RootLoggerComponentBuilder newRootLogger(String level, boolean includeLocation) {
		return new DefaultRootLoggerComponentBuilder(this, level, includeLocation);
	}

	@Override
	public ConfigurationBuilder<T> setAdvertiser(String advertiser) {
		this.advertiser = advertiser;
		return this;
	}

	@Override
	public ConfigurationBuilder<T> setConfigurationName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public ConfigurationBuilder<T> setConfigurationSource(ConfigurationSource configurationSource) {
		this.source = configurationSource;
		return this;
	}

	@Override
	public ConfigurationBuilder<T> setMonitorInterval(String intervalSeconds) {
		this.monitorInterval = Integer.parseInt(intervalSeconds);
		return this;
	}

	@Override
	public ConfigurationBuilder<T> setPackages(String packages) {
		this.packages = packages;
		return this;
	}

	@Override
	public ConfigurationBuilder<T> setShutdownHook(String flag) {
		this.shutdownFlag = flag;
		return this;
	}

	@Override
	public ConfigurationBuilder<T> setShutdownTimeout(long timeout, TimeUnit timeUnit) {
		this.shutdownTimeoutMillis = timeUnit.toMillis(timeout);
		return this;
	}

	@Override
	public ConfigurationBuilder<T> setStatusLevel(Level level) {
		this.level = level;
		return this;
	}

	@Override
	public ConfigurationBuilder<T> setVerbosity(String verbosity) {
		this.verbosity = verbosity;
		return this;
	}

	@Override
	public ConfigurationBuilder<T> setDestination(String destination) {
		this.destination = destination;
		return this;
	}

	@Override
	public void setLoggerContext(LoggerContext loggerContext) {
		this.loggerContext = loggerContext;
	}

	@Override
	public ConfigurationBuilder<T> addRootProperty(String key, String value) {
		this.root.getAttributes().put(key, value);
		return this;
	}
}
