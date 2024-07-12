package org.apache.logging.log4j.core;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URI;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationListener;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.config.NullConfiguration;
import org.apache.logging.log4j.core.config.Reconfigurable;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.jmx.Server;
import org.apache.logging.log4j.core.util.Cancellable;
import org.apache.logging.log4j.core.util.ExecutorServices;
import org.apache.logging.log4j.core.util.NetUtils;
import org.apache.logging.log4j.core.util.ShutdownCallbackRegistry;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.apache.logging.log4j.spi.LoggerRegistry;
import org.apache.logging.log4j.spi.Terminable;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;

public class LoggerContext extends AbstractLifeCycle implements org.apache.logging.log4j.spi.LoggerContext, AutoCloseable, Terminable, ConfigurationListener {
	public static final String PROPERTY_CONFIG = "config";
	private static final Configuration NULL_CONFIGURATION;
	private final LoggerRegistry<Logger> loggerRegistry = new LoggerRegistry<>();
	private final CopyOnWriteArrayList<PropertyChangeListener> propertyChangeListeners = new CopyOnWriteArrayList();
	private volatile Configuration configuration = new DefaultConfiguration();
	private Object externalContext;
	private String contextName;
	private volatile URI configLocation;
	private Cancellable shutdownCallback;
	private final Lock configLock = new ReentrantLock();

	public LoggerContext(String name) {
		this(name, null, (URI)null);
	}

	public LoggerContext(String name, Object externalContext) {
		this(name, externalContext, (URI)null);
	}

	public LoggerContext(String name, Object externalContext, URI configLocn) {
		this.contextName = name;
		this.externalContext = externalContext;
		this.configLocation = configLocn;
	}

	public LoggerContext(String name, Object externalContext, String configLocn) {
		this.contextName = name;
		this.externalContext = externalContext;
		if (configLocn != null) {
			URI uri;
			try {
				uri = new File(configLocn).toURI();
			} catch (Exception var6) {
				uri = null;
			}

			this.configLocation = uri;
		} else {
			this.configLocation = null;
		}
	}

	public static LoggerContext getContext() {
		return (LoggerContext)LogManager.getContext();
	}

	public static LoggerContext getContext(boolean currentContext) {
		return (LoggerContext)LogManager.getContext(currentContext);
	}

	public static LoggerContext getContext(ClassLoader loader, boolean currentContext, URI configLocation) {
		return (LoggerContext)LogManager.getContext(loader, currentContext, configLocation);
	}

	@Override
	public void start() {
		LOGGER.debug("Starting LoggerContext[name={}, {}]...", this.getName(), this);
		if (PropertiesUtil.getProperties().getBooleanProperty("log4j.LoggerContext.stacktrace.on.start", false)) {
			LOGGER.debug("Stack trace to locate invoker", (Throwable)(new Exception("Not a real error, showing stack trace to locate invoker")));
		}

		if (this.configLock.tryLock()) {
			try {
				if (this.isInitialized() || this.isStopped()) {
					this.setStarting();
					this.reconfigure();
					if (this.configuration.isShutdownHookEnabled()) {
						this.setUpShutdownHook();
					}

					this.setStarted();
				}
			} finally {
				this.configLock.unlock();
			}
		}

		LOGGER.debug("LoggerContext[name={}, {}] started OK.", this.getName(), this);
	}

	public void start(Configuration config) {
		LOGGER.debug("Starting LoggerContext[name={}, {}] with configuration {}...", this.getName(), this, config);
		if (this.configLock.tryLock()) {
			try {
				if (this.isInitialized() || this.isStopped()) {
					if (this.configuration.isShutdownHookEnabled()) {
						this.setUpShutdownHook();
					}

					this.setStarted();
				}
			} finally {
				this.configLock.unlock();
			}
		}

		this.setConfiguration(config);
		LOGGER.debug("LoggerContext[name={}, {}] started OK with configuration {}.", this.getName(), this, config);
	}

	private void setUpShutdownHook() {
		if (this.shutdownCallback == null) {
			LoggerContextFactory factory = LogManager.getFactory();
			if (factory instanceof ShutdownCallbackRegistry) {
				LOGGER.debug(ShutdownCallbackRegistry.SHUTDOWN_HOOK_MARKER, "Shutdown hook enabled. Registering a new one.");

				try {
					final long shutdownTimeoutMillis = this.configuration.getShutdownTimeoutMillis();
					this.shutdownCallback = ((ShutdownCallbackRegistry)factory).addShutdownCallback(new Runnable() {
						public void run() {
							LoggerContext context = LoggerContext.this;
							AbstractLifeCycle.LOGGER.debug(ShutdownCallbackRegistry.SHUTDOWN_HOOK_MARKER, "Stopping LoggerContext[name={}, {}]", context.getName(), context);
							context.stop(shutdownTimeoutMillis, TimeUnit.MILLISECONDS);
						}

						public String toString() {
							return "Shutdown callback for LoggerContext[name=" + LoggerContext.this.getName() + ']';
						}
					});
				} catch (IllegalStateException var4) {
					throw new IllegalStateException("Unable to register Log4j shutdown hook because JVM is shutting down.", var4);
				} catch (SecurityException var5) {
					LOGGER.error(ShutdownCallbackRegistry.SHUTDOWN_HOOK_MARKER, "Unable to register shutdown hook due to security restrictions", (Throwable)var5);
				}
			}
		}
	}

	public void close() {
		this.stop();
	}

	@Override
	public void terminate() {
		this.stop();
	}

	@Override
	public boolean stop(long timeout, TimeUnit timeUnit) {
		LOGGER.debug("Stopping LoggerContext[name={}, {}]...", this.getName(), this);
		this.configLock.lock();

		label65: {
			boolean prev;
			try {
				if (!this.isStopped()) {
					this.setStopping();

					try {
						Server.unregisterLoggerContext(this.getName());
					} catch (Exception | LinkageError var8) {
						LOGGER.error("Unable to unregister MBeans", (Throwable)var8);
					}

					if (this.shutdownCallback != null) {
						this.shutdownCallback.cancel();
						this.shutdownCallback = null;
					}

					Configuration prevx = this.configuration;
					this.configuration = NULL_CONFIGURATION;
					this.updateLoggers();
					if (prevx instanceof LifeCycle2) {
						((LifeCycle2)prevx).stop(timeout, timeUnit);
					} else {
						prevx.stop();
					}

					this.externalContext = null;
					LogManager.getFactory().removeContext(this);
					break label65;
				}

				prev = true;
			} finally {
				this.configLock.unlock();
				this.setStopped();
			}

			return prev;
		}

		LOGGER.debug("Stopped LoggerContext[name={}, {}] with status {}", this.getName(), this, true);
		return true;
	}

	public String getName() {
		return this.contextName;
	}

	public Logger getRootLogger() {
		return this.getLogger("");
	}

	public void setName(String name) {
		this.contextName = (String)Objects.requireNonNull(name);
	}

	public void setExternalContext(Object context) {
		this.externalContext = context;
	}

	@Override
	public Object getExternalContext() {
		return this.externalContext;
	}

	public Logger getLogger(String name) {
		return this.getLogger(name, null);
	}

	public Collection<Logger> getLoggers() {
		return this.loggerRegistry.getLoggers();
	}

	public Logger getLogger(String name, MessageFactory messageFactory) {
		Logger logger = this.loggerRegistry.getLogger(name, messageFactory);
		if (logger != null) {
			AbstractLogger.checkMessageFactory(logger, messageFactory);
			return logger;
		} else {
			logger = this.newInstance(this, name, messageFactory);
			this.loggerRegistry.putIfAbsent(name, messageFactory, logger);
			return this.loggerRegistry.getLogger(name, messageFactory);
		}
	}

	@Override
	public boolean hasLogger(String name) {
		return this.loggerRegistry.hasLogger(name);
	}

	@Override
	public boolean hasLogger(String name, MessageFactory messageFactory) {
		return this.loggerRegistry.hasLogger(name, messageFactory);
	}

	@Override
	public boolean hasLogger(String name, Class<? extends MessageFactory> messageFactoryClass) {
		return this.loggerRegistry.hasLogger(name, messageFactoryClass);
	}

	public Configuration getConfiguration() {
		return this.configuration;
	}

	public void addFilter(Filter filter) {
		this.configuration.addFilter(filter);
	}

	public void removeFilter(Filter filter) {
		this.configuration.removeFilter(filter);
	}

	private Configuration setConfiguration(Configuration config) {
		if (config == null) {
			LOGGER.error("No configuration found for context '{}'.", this.contextName);
			return this.configuration;
		} else {
			this.configLock.lock();

			Configuration e;
			try {
				Configuration prev = this.configuration;
				config.addListener(this);
				ConcurrentMap<String, String> map = config.getComponent("ContextProperties");

				try {
					map.putIfAbsent("hostName", NetUtils.getLocalHostname());
				} catch (Exception var10) {
					LOGGER.debug("Ignoring {}, setting hostName to 'unknown'", var10.toString());
					map.putIfAbsent("hostName", "unknown");
				}

				map.putIfAbsent("contextName", this.contextName);
				config.start();
				this.configuration = config;
				this.updateLoggers();
				if (prev != null) {
					prev.removeListener(this);
					prev.stop();
				}

				this.firePropertyChangeEvent(new PropertyChangeEvent(this, "config", prev, config));

				try {
					Server.reregisterMBeansAfterReconfigure();
				} catch (Exception | LinkageError var9) {
					LOGGER.error("Could not reconfigure JMX", (Throwable)var9);
				}

				Log4jLogEvent.setNanoClock(this.configuration.getNanoClock());
				e = prev;
			} finally {
				this.configLock.unlock();
			}

			return e;
		}
	}

	private void firePropertyChangeEvent(PropertyChangeEvent event) {
		for (PropertyChangeListener listener : this.propertyChangeListeners) {
			listener.propertyChange(event);
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeListeners.add(Objects.requireNonNull(listener, "listener"));
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeListeners.remove(listener);
	}

	public URI getConfigLocation() {
		return this.configLocation;
	}

	public void setConfigLocation(URI configLocation) {
		this.configLocation = configLocation;
		this.reconfigure(configLocation);
	}

	private void reconfigure(URI configURI) {
		ClassLoader cl = ClassLoader.class.isInstance(this.externalContext) ? (ClassLoader)this.externalContext : null;
		LOGGER.debug("Reconfiguration started for context[name={}] at URI {} ({}) with optional ClassLoader: {}", this.contextName, configURI, this, cl);
		Configuration instance = ConfigurationFactory.getInstance().getConfiguration(this, this.contextName, configURI, cl);
		if (instance == null) {
			LOGGER.error("Reconfiguration failed: No configuration found for '{}' at '{}' in '{}'", this.contextName, configURI, cl);
		} else {
			this.setConfiguration(instance);
			String location = this.configuration == null ? "?" : String.valueOf(this.configuration.getConfigurationSource());
			LOGGER.debug("Reconfiguration complete for context[name={}] at URI {} ({}) with optional ClassLoader: {}", this.contextName, location, this, cl);
		}
	}

	public void reconfigure() {
		this.reconfigure(this.configLocation);
	}

	public void updateLoggers() {
		this.updateLoggers(this.configuration);
	}

	public void updateLoggers(Configuration config) {
		Configuration old = this.configuration;

		for (Logger logger : this.loggerRegistry.getLoggers()) {
			logger.updateConfiguration(config);
		}

		this.firePropertyChangeEvent(new PropertyChangeEvent(this, "config", old, config));
	}

	@Override
	public synchronized void onChange(Reconfigurable reconfigurable) {
		LOGGER.debug("Reconfiguration started for context {} ({})", this.contextName, this);
		Configuration newConfig = reconfigurable.reconfigure();
		if (newConfig != null) {
			this.setConfiguration(newConfig);
			LOGGER.debug("Reconfiguration completed for {} ({})", this.contextName, this);
		} else {
			LOGGER.debug("Reconfiguration failed for {} ({})", this.contextName, this);
		}
	}

	protected Logger newInstance(LoggerContext ctx, String name, MessageFactory messageFactory) {
		return new Logger(ctx, name, messageFactory);
	}

	static {
		try {
			LoaderUtil.loadClass(ExecutorServices.class.getName());
		} catch (Exception var1) {
			LOGGER.error("Failed to preload ExecutorServices class.", (Throwable)var1);
		}

		NULL_CONFIGURATION = new NullConfiguration();
	}
}
