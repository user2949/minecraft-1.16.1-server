package org.apache.logging.log4j.core.config.properties;

import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationException;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.AppenderRefComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.FilterableComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LoggableComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ScriptComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ScriptFileComponentBuilder;
import org.apache.logging.log4j.core.util.Builder;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.Strings;

public class PropertiesConfigurationBuilder extends ConfigurationBuilderFactory implements Builder<PropertiesConfiguration> {
	private static final String ADVERTISER_KEY = "advertiser";
	private static final String STATUS_KEY = "status";
	private static final String SHUTDOWN_HOOK = "shutdownHook";
	private static final String SHUTDOWN_TIMEOUT = "shutdownTimeout";
	private static final String VERBOSE = "verbose";
	private static final String DEST = "dest";
	private static final String PACKAGES = "packages";
	private static final String CONFIG_NAME = "name";
	private static final String MONITOR_INTERVAL = "monitorInterval";
	private static final String CONFIG_TYPE = "type";
	private final ConfigurationBuilder<PropertiesConfiguration> builder = newConfigurationBuilder(PropertiesConfiguration.class);
	private LoggerContext loggerContext;
	private Properties rootProperties;

	public PropertiesConfigurationBuilder setRootProperties(Properties rootProperties) {
		this.rootProperties = rootProperties;
		return this;
	}

	public PropertiesConfigurationBuilder setConfigurationSource(ConfigurationSource source) {
		this.builder.setConfigurationSource(source);
		return this;
	}

	public PropertiesConfiguration build() {
		for (String key : this.rootProperties.stringPropertyNames()) {
			if (!key.contains(".")) {
				this.builder.addRootProperty(key, this.rootProperties.getProperty(key));
			}
		}

		this.builder
			.setStatusLevel(Level.toLevel(this.rootProperties.getProperty("status"), Level.ERROR))
			.setShutdownHook(this.rootProperties.getProperty("shutdownHook"))
			.setShutdownTimeout(Long.parseLong(this.rootProperties.getProperty("shutdownTimeout", "0")), TimeUnit.MILLISECONDS)
			.setVerbosity(this.rootProperties.getProperty("verbose"))
			.setDestination(this.rootProperties.getProperty("dest"))
			.setPackages(this.rootProperties.getProperty("packages"))
			.setConfigurationName(this.rootProperties.getProperty("name"))
			.setMonitorInterval(this.rootProperties.getProperty("monitorInterval", "0"))
			.setAdvertiser(this.rootProperties.getProperty("advertiser"));
		Properties propertyPlaceholders = PropertiesUtil.extractSubset(this.rootProperties, "property");

		for (String keyx : propertyPlaceholders.stringPropertyNames()) {
			this.builder.addProperty(keyx, propertyPlaceholders.getProperty(keyx));
		}

		Map<String, Properties> scripts = PropertiesUtil.partitionOnCommonPrefixes(PropertiesUtil.extractSubset(this.rootProperties, "script"));

		for (Entry<String, Properties> entry : scripts.entrySet()) {
			Properties scriptProps = (Properties)entry.getValue();
			String type = (String)scriptProps.remove("type");
			if (type == null) {
				throw new ConfigurationException("No type provided for script - must be Script or ScriptFile");
			}

			if (type.equalsIgnoreCase("script")) {
				this.builder.add(this.createScript(scriptProps));
			} else {
				this.builder.add(this.createScriptFile(scriptProps));
			}
		}

		Properties levelProps = PropertiesUtil.extractSubset(this.rootProperties, "customLevel");
		if (levelProps.size() > 0) {
			for (String keyx : levelProps.stringPropertyNames()) {
				this.builder.add(this.builder.newCustomLevel(keyx, Integer.parseInt(levelProps.getProperty(keyx))));
			}
		}

		String filterProp = this.rootProperties.getProperty("filters");
		if (filterProp != null) {
			String[] filterNames = filterProp.split(",");

			for (String filterName : filterNames) {
				String name = filterName.trim();
				this.builder.add(this.createFilter(name, PropertiesUtil.extractSubset(this.rootProperties, "filter." + name)));
			}
		} else {
			Map<String, Properties> filters = PropertiesUtil.partitionOnCommonPrefixes(PropertiesUtil.extractSubset(this.rootProperties, "filter"));

			for (Entry<String, Properties> entry : filters.entrySet()) {
				this.builder.add(this.createFilter(((String)entry.getKey()).trim(), (Properties)entry.getValue()));
			}
		}

		String appenderProp = this.rootProperties.getProperty("appenders");
		if (appenderProp != null) {
			String[] appenderNames = appenderProp.split(",");

			for (String appenderName : appenderNames) {
				String name = appenderName.trim();
				this.builder.add(this.createAppender(appenderName.trim(), PropertiesUtil.extractSubset(this.rootProperties, "appender." + name)));
			}
		} else {
			Map<String, Properties> appenders = PropertiesUtil.partitionOnCommonPrefixes(PropertiesUtil.extractSubset(this.rootProperties, "appender"));

			for (Entry<String, Properties> entry : appenders.entrySet()) {
				this.builder.add(this.createAppender(((String)entry.getKey()).trim(), (Properties)entry.getValue()));
			}
		}

		String loggerProp = this.rootProperties.getProperty("loggers");
		if (loggerProp != null) {
			String[] loggerNames = loggerProp.split(",");

			for (String loggerName : loggerNames) {
				String name = loggerName.trim();
				if (!name.equals("root")) {
					this.builder.add(this.createLogger(name, PropertiesUtil.extractSubset(this.rootProperties, "logger." + name)));
				}
			}
		} else {
			Map<String, Properties> loggers = PropertiesUtil.partitionOnCommonPrefixes(PropertiesUtil.extractSubset(this.rootProperties, "logger"));

			for (Entry<String, Properties> entry : loggers.entrySet()) {
				String name = ((String)entry.getKey()).trim();
				if (!name.equals("root")) {
					this.builder.add(this.createLogger(name, (Properties)entry.getValue()));
				}
			}
		}

		Properties props = PropertiesUtil.extractSubset(this.rootProperties, "rootLogger");
		if (props.size() > 0) {
			this.builder.add(this.createRootLogger(props));
		}

		this.builder.setLoggerContext(this.loggerContext);
		return this.builder.build(false);
	}

	private ScriptComponentBuilder createScript(Properties properties) {
		String name = (String)properties.remove("name");
		String language = (String)properties.remove("language");
		String text = (String)properties.remove("text");
		ScriptComponentBuilder scriptBuilder = this.builder.newScript(name, language, text);
		return processRemainingProperties(scriptBuilder, properties);
	}

	private ScriptFileComponentBuilder createScriptFile(Properties properties) {
		String name = (String)properties.remove("name");
		String path = (String)properties.remove("path");
		ScriptFileComponentBuilder scriptFileBuilder = this.builder.newScriptFile(name, path);
		return processRemainingProperties(scriptFileBuilder, properties);
	}

	private AppenderComponentBuilder createAppender(String key, Properties properties) {
		String name = (String)properties.remove("name");
		if (Strings.isEmpty(name)) {
			throw new ConfigurationException("No name attribute provided for Appender " + key);
		} else {
			String type = (String)properties.remove("type");
			if (Strings.isEmpty(type)) {
				throw new ConfigurationException("No type attribute provided for Appender " + key);
			} else {
				AppenderComponentBuilder appenderBuilder = this.builder.newAppender(name, type);
				this.addFiltersToComponent(appenderBuilder, properties);
				Properties layoutProps = PropertiesUtil.extractSubset(properties, "layout");
				if (layoutProps.size() > 0) {
					appenderBuilder.add(this.createLayout(name, layoutProps));
				}

				return processRemainingProperties(appenderBuilder, properties);
			}
		}
	}

	private FilterComponentBuilder createFilter(String key, Properties properties) {
		String type = (String)properties.remove("type");
		if (Strings.isEmpty(type)) {
			throw new ConfigurationException("No type attribute provided for Appender " + key);
		} else {
			String onMatch = (String)properties.remove("onMatch");
			String onMisMatch = (String)properties.remove("onMisMatch");
			FilterComponentBuilder filterBuilder = this.builder.newFilter(type, onMatch, onMisMatch);
			return processRemainingProperties(filterBuilder, properties);
		}
	}

	private AppenderRefComponentBuilder createAppenderRef(String key, Properties properties) {
		String ref = (String)properties.remove("ref");
		if (Strings.isEmpty(ref)) {
			throw new ConfigurationException("No ref attribute provided for AppenderRef " + key);
		} else {
			AppenderRefComponentBuilder appenderRefBuilder = this.builder.newAppenderRef(ref);
			String level = (String)properties.remove("level");
			if (!Strings.isEmpty(level)) {
				appenderRefBuilder.addAttribute("level", level);
			}

			return this.addFiltersToComponent(appenderRefBuilder, properties);
		}
	}

	private LoggerComponentBuilder createLogger(String key, Properties properties) {
		String name = (String)properties.remove("name");
		String location = (String)properties.remove("includeLocation");
		if (Strings.isEmpty(name)) {
			throw new ConfigurationException("No name attribute provided for Logger " + key);
		} else {
			String level = (String)properties.remove("level");
			String type = (String)properties.remove("type");
			LoggerComponentBuilder loggerBuilder;
			if (type != null) {
				if (!type.equalsIgnoreCase("asyncLogger")) {
					throw new ConfigurationException("Unknown Logger type " + type + " for Logger " + name);
				}

				if (location != null) {
					boolean includeLocation = Boolean.parseBoolean(location);
					loggerBuilder = this.builder.newAsyncLogger(name, level, includeLocation);
				} else {
					loggerBuilder = this.builder.newAsyncLogger(name, level);
				}
			} else if (location != null) {
				boolean includeLocation = Boolean.parseBoolean(location);
				loggerBuilder = this.builder.newLogger(name, level, includeLocation);
			} else {
				loggerBuilder = this.builder.newLogger(name, level);
			}

			this.addLoggersToComponent(loggerBuilder, properties);
			this.addFiltersToComponent(loggerBuilder, properties);
			String additivity = (String)properties.remove("additivity");
			if (!Strings.isEmpty(additivity)) {
				loggerBuilder.addAttribute("additivity", additivity);
			}

			return loggerBuilder;
		}
	}

	private RootLoggerComponentBuilder createRootLogger(Properties properties) {
		String level = (String)properties.remove("level");
		String type = (String)properties.remove("type");
		String location = (String)properties.remove("includeLocation");
		RootLoggerComponentBuilder loggerBuilder;
		if (type != null) {
			if (!type.equalsIgnoreCase("asyncRoot")) {
				throw new ConfigurationException("Unknown Logger type for root logger" + type);
			}

			if (location != null) {
				boolean includeLocation = Boolean.parseBoolean(location);
				loggerBuilder = this.builder.newAsyncRootLogger(level, includeLocation);
			} else {
				loggerBuilder = this.builder.newAsyncRootLogger(level);
			}
		} else if (location != null) {
			boolean includeLocation = Boolean.parseBoolean(location);
			loggerBuilder = this.builder.newRootLogger(level, includeLocation);
		} else {
			loggerBuilder = this.builder.newRootLogger(level);
		}

		this.addLoggersToComponent(loggerBuilder, properties);
		return this.addFiltersToComponent(loggerBuilder, properties);
	}

	private LayoutComponentBuilder createLayout(String appenderName, Properties properties) {
		String type = (String)properties.remove("type");
		if (Strings.isEmpty(type)) {
			throw new ConfigurationException("No type attribute provided for Layout on Appender " + appenderName);
		} else {
			LayoutComponentBuilder layoutBuilder = this.builder.newLayout(type);
			return processRemainingProperties(layoutBuilder, properties);
		}
	}

	private static <B extends ComponentBuilder<B>> ComponentBuilder<B> createComponent(ComponentBuilder<?> parent, String key, Properties properties) {
		String name = (String)properties.remove("name");
		String type = (String)properties.remove("type");
		if (Strings.isEmpty(type)) {
			throw new ConfigurationException("No type attribute provided for component " + key);
		} else {
			ComponentBuilder<B> componentBuilder = parent.getBuilder().newComponent(name, type);
			return processRemainingProperties(componentBuilder, properties);
		}
	}

	private static <B extends ComponentBuilder<?>> B processRemainingProperties(B builder, Properties properties) {
		while (properties.size() > 0) {
			String propertyName = (String)properties.stringPropertyNames().iterator().next();
			int index = propertyName.indexOf(46);
			if (index > 0) {
				String prefix = propertyName.substring(0, index);
				Properties componentProperties = PropertiesUtil.extractSubset(properties, prefix);
				builder.addComponent(createComponent(builder, prefix, componentProperties));
			} else {
				builder.addAttribute(propertyName, properties.getProperty(propertyName));
				properties.remove(propertyName);
			}
		}

		return builder;
	}

	private <B extends FilterableComponentBuilder<? extends ComponentBuilder<?>>> B addFiltersToComponent(B componentBuilder, Properties properties) {
		Map<String, Properties> filters = PropertiesUtil.partitionOnCommonPrefixes(PropertiesUtil.extractSubset(properties, "filter"));

		for (Entry<String, Properties> entry : filters.entrySet()) {
			componentBuilder.add(this.createFilter(((String)entry.getKey()).trim(), (Properties)entry.getValue()));
		}

		return componentBuilder;
	}

	private <B extends LoggableComponentBuilder<? extends ComponentBuilder<?>>> B addLoggersToComponent(B loggerBuilder, Properties properties) {
		Map<String, Properties> appenderRefs = PropertiesUtil.partitionOnCommonPrefixes(PropertiesUtil.extractSubset(properties, "appenderRef"));

		for (Entry<String, Properties> entry : appenderRefs.entrySet()) {
			loggerBuilder.add(this.createAppenderRef(((String)entry.getKey()).trim(), (Properties)entry.getValue()));
		}

		return loggerBuilder;
	}

	public PropertiesConfigurationBuilder setLoggerContext(LoggerContext loggerContext) {
		this.loggerContext = loggerContext;
		return this;
	}

	public LoggerContext getLoggerContext() {
		return this.loggerContext;
	}
}
