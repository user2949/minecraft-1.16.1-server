package org.apache.logging.log4j.core.config;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.impl.Log4jContextFactory;
import org.apache.logging.log4j.core.util.NetUtils;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.Strings;

public final class Configurator {
	private static final String FQCN = Configurator.class.getName();
	private static final Logger LOGGER = StatusLogger.getLogger();

	private static Log4jContextFactory getFactory() {
		LoggerContextFactory factory = LogManager.getFactory();
		if (factory instanceof Log4jContextFactory) {
			return (Log4jContextFactory)factory;
		} else if (factory != null) {
			LOGGER.error(
				"LogManager returned an instance of {} which does not implement {}. Unable to initialize Log4j.",
				factory.getClass().getName(),
				Log4jContextFactory.class.getName()
			);
			return null;
		} else {
			LOGGER.fatal("LogManager did not return a LoggerContextFactory. This indicates something has gone terribly wrong!");
			return null;
		}
	}

	public static LoggerContext initialize(ClassLoader loader, ConfigurationSource source) {
		return initialize(loader, source, null);
	}

	public static LoggerContext initialize(ClassLoader loader, ConfigurationSource source, Object externalContext) {
		try {
			Log4jContextFactory factory = getFactory();
			return factory == null ? null : factory.getContext(FQCN, loader, externalContext, false, source);
		} catch (Exception var4) {
			LOGGER.error("There was a problem obtaining a LoggerContext using the configuration source [{}]", source, var4);
			return null;
		}
	}

	public static LoggerContext initialize(String name, ClassLoader loader, String configLocation) {
		return initialize(name, loader, configLocation, null);
	}

	public static LoggerContext initialize(String name, ClassLoader loader, String configLocation, Object externalContext) {
		if (Strings.isBlank(configLocation)) {
			return initialize(name, loader, (URI)null, externalContext);
		} else if (configLocation.contains(",")) {
			String[] parts = configLocation.split(",");
			String scheme = null;
			List<URI> uris = new ArrayList(parts.length);

			for (String part : parts) {
				URI uri = NetUtils.toURI(scheme != null ? scheme + ":" + part.trim() : part.trim());
				if (scheme == null && uri.getScheme() != null) {
					scheme = uri.getScheme();
				}

				uris.add(uri);
			}

			return initialize(name, loader, uris, externalContext);
		} else {
			return initialize(name, loader, NetUtils.toURI(configLocation), externalContext);
		}
	}

	public static LoggerContext initialize(String name, ClassLoader loader, URI configLocation) {
		return initialize(name, loader, configLocation, null);
	}

	public static LoggerContext initialize(String name, ClassLoader loader, URI configLocation, Object externalContext) {
		try {
			Log4jContextFactory factory = getFactory();
			return factory == null ? null : factory.getContext(FQCN, loader, externalContext, false, configLocation, name);
		} catch (Exception var5) {
			LOGGER.error("There was a problem initializing the LoggerContext [{}] using configuration at [{}].", name, configLocation, var5);
			return null;
		}
	}

	public static LoggerContext initialize(String name, ClassLoader loader, List<URI> configLocations, Object externalContext) {
		try {
			Log4jContextFactory factory = getFactory();
			return factory == null ? null : factory.getContext(FQCN, loader, externalContext, false, configLocations, name);
		} catch (Exception var5) {
			LOGGER.error("There was a problem initializing the LoggerContext [{}] using configurations at [{}].", name, configLocations, var5);
			return null;
		}
	}

	public static LoggerContext initialize(String name, String configLocation) {
		return initialize(name, null, configLocation);
	}

	public static LoggerContext initialize(Configuration configuration) {
		return initialize(null, configuration, null);
	}

	public static LoggerContext initialize(ClassLoader loader, Configuration configuration) {
		return initialize(loader, configuration, null);
	}

	public static LoggerContext initialize(ClassLoader loader, Configuration configuration, Object externalContext) {
		try {
			Log4jContextFactory factory = getFactory();
			return factory == null ? null : factory.getContext(FQCN, loader, externalContext, false, configuration);
		} catch (Exception var4) {
			LOGGER.error("There was a problem initializing the LoggerContext using configuration {}", configuration.getName(), var4);
			return null;
		}
	}

	public static void setAllLevels(String parentLogger, Level level) {
		LoggerContext loggerContext = LoggerContext.getContext(false);
		Configuration config = loggerContext.getConfiguration();
		boolean set = setLevel(parentLogger, level, config);

		for (Entry<String, LoggerConfig> entry : config.getLoggers().entrySet()) {
			if (((String)entry.getKey()).startsWith(parentLogger)) {
				set |= setLevel((LoggerConfig)entry.getValue(), level);
			}
		}

		if (set) {
			loggerContext.updateLoggers();
		}
	}

	private static boolean setLevel(LoggerConfig loggerConfig, Level level) {
		boolean set = !loggerConfig.getLevel().equals(level);
		if (set) {
			loggerConfig.setLevel(level);
		}

		return set;
	}

	public static void setLevel(Map<String, Level> levelMap) {
		LoggerContext loggerContext = LoggerContext.getContext(false);
		Configuration config = loggerContext.getConfiguration();
		boolean set = false;

		for (Entry<String, Level> entry : levelMap.entrySet()) {
			String loggerName = (String)entry.getKey();
			Level level = (Level)entry.getValue();
			set |= setLevel(loggerName, level, config);
		}

		if (set) {
			loggerContext.updateLoggers();
		}
	}

	public static void setLevel(String loggerName, Level level) {
		LoggerContext loggerContext = LoggerContext.getContext(false);
		if (Strings.isEmpty(loggerName)) {
			setRootLevel(level);
		} else if (setLevel(loggerName, level, loggerContext.getConfiguration())) {
			loggerContext.updateLoggers();
		}
	}

	private static boolean setLevel(String loggerName, Level level, Configuration config) {
		LoggerConfig loggerConfig = config.getLoggerConfig(loggerName);
		boolean set;
		if (!loggerName.equals(loggerConfig.getName())) {
			loggerConfig = new LoggerConfig(loggerName, level, true);
			config.addLogger(loggerName, loggerConfig);
			loggerConfig.setLevel(level);
			set = true;
		} else {
			set = setLevel(loggerConfig, level);
		}

		return set;
	}

	public static void setRootLevel(Level level) {
		LoggerContext loggerContext = LoggerContext.getContext(false);
		LoggerConfig loggerConfig = loggerContext.getConfiguration().getRootLogger();
		if (!loggerConfig.getLevel().equals(level)) {
			loggerConfig.setLevel(level);
			loggerContext.updateLoggers();
		}
	}

	public static void shutdown(LoggerContext ctx) {
		if (ctx != null) {
			ctx.stop();
		}
	}

	public static boolean shutdown(LoggerContext ctx, long timeout, TimeUnit timeUnit) {
		return ctx != null ? ctx.stop(timeout, timeUnit) : true;
	}

	private Configurator() {
	}
}
