package org.apache.logging.log4j.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.composite.CompositeConfiguration;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;
import org.apache.logging.log4j.core.lookup.Interpolator;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.apache.logging.log4j.core.util.FileUtils;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.core.util.NetUtils;
import org.apache.logging.log4j.core.util.ReflectionUtil;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.Strings;

public abstract class ConfigurationFactory extends ConfigurationBuilderFactory {
	public static final String CONFIGURATION_FACTORY_PROPERTY = "log4j.configurationFactory";
	public static final String CONFIGURATION_FILE_PROPERTY = "log4j.configurationFile";
	public static final String CATEGORY = "ConfigurationFactory";
	protected static final Logger LOGGER = StatusLogger.getLogger();
	protected static final String TEST_PREFIX = "log4j2-test";
	protected static final String DEFAULT_PREFIX = "log4j2";
	private static final String CLASS_LOADER_SCHEME = "classloader";
	private static final String CLASS_PATH_SCHEME = "classpath";
	private static volatile List<ConfigurationFactory> factories = null;
	private static ConfigurationFactory configFactory = new ConfigurationFactory.Factory();
	protected final StrSubstitutor substitutor = new StrSubstitutor(new Interpolator());
	private static final Lock LOCK = new ReentrantLock();

	public static ConfigurationFactory getInstance() {
		if (factories == null) {
			LOCK.lock();

			try {
				if (factories == null) {
					List<ConfigurationFactory> list = new ArrayList();
					String factoryClass = PropertiesUtil.getProperties().getStringProperty("log4j.configurationFactory");
					if (factoryClass != null) {
						addFactory(list, factoryClass);
					}

					PluginManager manager = new PluginManager("ConfigurationFactory");
					manager.collectPlugins();
					Map<String, PluginType<?>> plugins = manager.getPlugins();
					List<Class<? extends ConfigurationFactory>> ordered = new ArrayList(plugins.size());

					for (PluginType<?> type : plugins.values()) {
						try {
							ordered.add(type.getPluginClass().asSubclass(ConfigurationFactory.class));
						} catch (Exception var11) {
							LOGGER.warn("Unable to add class {}", type.getPluginClass(), var11);
						}
					}

					Collections.sort(ordered, OrderComparator.getInstance());

					for (Class<? extends ConfigurationFactory> clazz : ordered) {
						addFactory(list, clazz);
					}

					factories = Collections.unmodifiableList(list);
				}
			} finally {
				LOCK.unlock();
			}
		}

		LOGGER.debug("Using configurationFactory {}", configFactory);
		return configFactory;
	}

	private static void addFactory(Collection<ConfigurationFactory> list, String factoryClass) {
		try {
			addFactory(list, LoaderUtil.loadClass(factoryClass).asSubclass(ConfigurationFactory.class));
		} catch (Exception var3) {
			LOGGER.error("Unable to load class {}", factoryClass, var3);
		}
	}

	private static void addFactory(Collection<ConfigurationFactory> list, Class<? extends ConfigurationFactory> factoryClass) {
		try {
			list.add(ReflectionUtil.instantiate(factoryClass));
		} catch (Exception var3) {
			LOGGER.error("Unable to create instance of {}", factoryClass.getName(), var3);
		}
	}

	public static void setConfigurationFactory(ConfigurationFactory factory) {
		configFactory = factory;
	}

	public static void resetConfigurationFactory() {
		configFactory = new ConfigurationFactory.Factory();
	}

	public static void removeConfigurationFactory(ConfigurationFactory factory) {
		if (configFactory == factory) {
			configFactory = new ConfigurationFactory.Factory();
		}
	}

	protected abstract String[] getSupportedTypes();

	protected boolean isActive() {
		return true;
	}

	public abstract Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource configurationSource);

	public Configuration getConfiguration(LoggerContext loggerContext, String name, URI configLocation) {
		if (!this.isActive()) {
			return null;
		} else {
			if (configLocation != null) {
				ConfigurationSource source = this.getInputFromUri(configLocation);
				if (source != null) {
					return this.getConfiguration(loggerContext, source);
				}
			}

			return null;
		}
	}

	public Configuration getConfiguration(LoggerContext loggerContext, String name, URI configLocation, ClassLoader loader) {
		if (!this.isActive()) {
			return null;
		} else if (loader == null) {
			return this.getConfiguration(loggerContext, name, configLocation);
		} else {
			if (isClassLoaderUri(configLocation)) {
				String path = extractClassLoaderUriPath(configLocation);
				ConfigurationSource source = this.getInputFromResource(path, loader);
				if (source != null) {
					Configuration configuration = this.getConfiguration(loggerContext, source);
					if (configuration != null) {
						return configuration;
					}
				}
			}

			return this.getConfiguration(loggerContext, name, configLocation);
		}
	}

	protected ConfigurationSource getInputFromUri(URI configLocation) {
		File configFile = FileUtils.fileFromUri(configLocation);
		if (configFile != null && configFile.exists() && configFile.canRead()) {
			try {
				return new ConfigurationSource(new FileInputStream(configFile), configFile);
			} catch (FileNotFoundException var8) {
				LOGGER.error("Cannot locate file {}", configLocation.getPath(), var8);
			}
		}

		if (isClassLoaderUri(configLocation)) {
			ClassLoader loader = LoaderUtil.getThreadContextClassLoader();
			String path = extractClassLoaderUriPath(configLocation);
			ConfigurationSource source = this.getInputFromResource(path, loader);
			if (source != null) {
				return source;
			}
		}

		if (!configLocation.isAbsolute()) {
			LOGGER.error("File not found in file system or classpath: {}", configLocation.toString());
			return null;
		} else {
			try {
				return new ConfigurationSource(configLocation.toURL().openStream(), configLocation.toURL());
			} catch (MalformedURLException var6) {
				LOGGER.error("Invalid URL {}", configLocation.toString(), var6);
			} catch (Exception var7) {
				LOGGER.error("Unable to access {}", configLocation.toString(), var7);
			}

			return null;
		}
	}

	private static boolean isClassLoaderUri(URI uri) {
		if (uri == null) {
			return false;
		} else {
			String scheme = uri.getScheme();
			return scheme == null || scheme.equals("classloader") || scheme.equals("classpath");
		}
	}

	private static String extractClassLoaderUriPath(URI uri) {
		return uri.getScheme() == null ? uri.getPath() : uri.getSchemeSpecificPart();
	}

	protected ConfigurationSource getInputFromString(String config, ClassLoader loader) {
		try {
			URL url = new URL(config);
			return new ConfigurationSource(url.openStream(), FileUtils.fileFromUri(url.toURI()));
		} catch (Exception var7) {
			ConfigurationSource source = this.getInputFromResource(config, loader);
			if (source == null) {
				try {
					File file = new File(config);
					return new ConfigurationSource(new FileInputStream(file), file);
				} catch (FileNotFoundException var6) {
					LOGGER.catching(Level.DEBUG, var6);
				}
			}

			return source;
		}
	}

	protected ConfigurationSource getInputFromResource(String resource, ClassLoader loader) {
		URL url = Loader.getResource(resource, loader);
		if (url == null) {
			return null;
		} else {
			InputStream is = null;

			try {
				is = url.openStream();
			} catch (IOException var6) {
				LOGGER.catching(Level.DEBUG, var6);
				return null;
			}

			if (is == null) {
				return null;
			} else {
				if (FileUtils.isFile(url)) {
					try {
						return new ConfigurationSource(is, FileUtils.fileFromUri(url.toURI()));
					} catch (URISyntaxException var7) {
						LOGGER.catching(Level.DEBUG, var7);
					}
				}

				return new ConfigurationSource(is, url);
			}
		}
	}

	static List<ConfigurationFactory> getFactories() {
		return factories;
	}

	private static class Factory extends ConfigurationFactory {
		private static final String ALL_TYPES = "*";

		private Factory() {
		}

		@Override
		public Configuration getConfiguration(LoggerContext loggerContext, String name, URI configLocation) {
			if (configLocation == null) {
				String configLocationStr = this.substitutor.replace(PropertiesUtil.getProperties().getStringProperty("log4j.configurationFile"));
				if (configLocationStr != null) {
					String[] sources = configLocationStr.split(",");
					if (sources.length <= 1) {
						return this.getConfiguration(loggerContext, configLocationStr);
					}

					List<AbstractConfiguration> configs = new ArrayList();

					for (String sourceLocation : sources) {
						Configuration config = this.getConfiguration(loggerContext, sourceLocation.trim());
						if (config == null || !(config instanceof AbstractConfiguration)) {
							LOGGER.error("Failed to created configuration at {}", sourceLocation);
							return null;
						}

						configs.add((AbstractConfiguration)config);
					}

					return new CompositeConfiguration(configs);
				}

				for (ConfigurationFactory factory : getFactories()) {
					String[] types = factory.getSupportedTypes();
					if (types != null) {
						for (String type : types) {
							if (type.equals("*")) {
								Configuration config = factory.getConfiguration(loggerContext, name, configLocation);
								if (config != null) {
									return config;
								}
							}
						}
					}
				}
			} else {
				String configLocationStr = configLocation.toString();

				for (ConfigurationFactory factoryx : getFactories()) {
					String[] types = factoryx.getSupportedTypes();
					if (types != null) {
						for (String typex : types) {
							if (typex.equals("*") || configLocationStr.endsWith(typex)) {
								Configuration config = factoryx.getConfiguration(loggerContext, name, configLocation);
								if (config != null) {
									return config;
								}
							}
						}
					}
				}
			}

			Configuration config = this.getConfiguration(loggerContext, true, name);
			if (config == null) {
				config = this.getConfiguration(loggerContext, true, null);
				if (config == null) {
					config = this.getConfiguration(loggerContext, false, name);
					if (config == null) {
						config = this.getConfiguration(loggerContext, false, null);
					}
				}
			}

			if (config != null) {
				return config;
			} else {
				LOGGER.error(
					"No log4j2 configuration file found. Using default configuration: logging only errors to the console. Set system property 'org.apache.logging.log4j.simplelog.StatusLogger.level' to TRACE to show Log4j2 internal initialization logging."
				);
				return new DefaultConfiguration();
			}
		}

		private Configuration getConfiguration(LoggerContext loggerContext, String configLocationStr) {
			ConfigurationSource source = null;

			try {
				source = this.getInputFromUri(NetUtils.toURI(configLocationStr));
			} catch (Exception var12) {
				LOGGER.catching(Level.DEBUG, var12);
			}

			if (source == null) {
				ClassLoader loader = LoaderUtil.getThreadContextClassLoader();
				source = this.getInputFromString(configLocationStr, loader);
			}

			if (source != null) {
				for (ConfigurationFactory factory : getFactories()) {
					String[] types = factory.getSupportedTypes();
					if (types != null) {
						for (String type : types) {
							if (type.equals("*") || configLocationStr.endsWith(type)) {
								Configuration config = factory.getConfiguration(loggerContext, source);
								if (config != null) {
									return config;
								}
							}
						}
					}
				}
			}

			return null;
		}

		private Configuration getConfiguration(LoggerContext loggerContext, boolean isTest, String name) {
			boolean named = Strings.isNotEmpty(name);
			ClassLoader loader = LoaderUtil.getThreadContextClassLoader();

			for (ConfigurationFactory factory : getFactories()) {
				String prefix = isTest ? "log4j2-test" : "log4j2";
				String[] types = factory.getSupportedTypes();
				if (types != null) {
					for (String suffix : types) {
						if (!suffix.equals("*")) {
							String configName = named ? prefix + name + suffix : prefix + suffix;
							ConfigurationSource source = this.getInputFromResource(configName, loader);
							if (source != null) {
								return factory.getConfiguration(loggerContext, source);
							}
						}
					}
				}
			}

			return null;
		}

		@Override
		public String[] getSupportedTypes() {
			return null;
		}

		@Override
		public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource source) {
			if (source != null) {
				String config = source.getLocation();

				for (ConfigurationFactory factory : getFactories()) {
					String[] types = factory.getSupportedTypes();
					if (types != null) {
						for (String type : types) {
							if (type.equals("*") || config != null && config.endsWith(type)) {
								Configuration c = factory.getConfiguration(loggerContext, source);
								if (c != null) {
									LOGGER.debug("Loaded configuration from {}", source);
									return c;
								}

								LOGGER.error("Cannot determine the ConfigurationFactory to use for {}", config);
								return null;
							}
						}
					}
				}
			}

			LOGGER.error("Cannot process configuration, input source is null");
			return null;
		}
	}
}
