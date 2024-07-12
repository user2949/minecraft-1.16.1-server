package org.apache.logging.log4j.core.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.LifeCycle.State;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.composite.CompositeConfiguration;
import org.apache.logging.log4j.core.selector.ClassLoaderContextSelector;
import org.apache.logging.log4j.core.selector.ContextSelector;
import org.apache.logging.log4j.core.util.Cancellable;
import org.apache.logging.log4j.core.util.DefaultShutdownCallbackRegistry;
import org.apache.logging.log4j.core.util.ShutdownCallbackRegistry;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;

public class Log4jContextFactory implements LoggerContextFactory, ShutdownCallbackRegistry {
	private static final StatusLogger LOGGER = StatusLogger.getLogger();
	private static final boolean SHUTDOWN_HOOK_ENABLED = PropertiesUtil.getProperties().getBooleanProperty("log4j.shutdownHookEnabled", true);
	private final ContextSelector selector;
	private final ShutdownCallbackRegistry shutdownCallbackRegistry;

	public Log4jContextFactory() {
		this(createContextSelector(), createShutdownCallbackRegistry());
	}

	public Log4jContextFactory(ContextSelector selector) {
		this(selector, createShutdownCallbackRegistry());
	}

	public Log4jContextFactory(ShutdownCallbackRegistry shutdownCallbackRegistry) {
		this(createContextSelector(), shutdownCallbackRegistry);
	}

	public Log4jContextFactory(ContextSelector selector, ShutdownCallbackRegistry shutdownCallbackRegistry) {
		this.selector = (ContextSelector)Objects.requireNonNull(selector, "No ContextSelector provided");
		this.shutdownCallbackRegistry = (ShutdownCallbackRegistry)Objects.requireNonNull(shutdownCallbackRegistry, "No ShutdownCallbackRegistry provided");
		LOGGER.debug("Using ShutdownCallbackRegistry {}", shutdownCallbackRegistry.getClass());
		this.initializeShutdownCallbackRegistry();
	}

	private static ContextSelector createContextSelector() {
		try {
			ContextSelector selector = LoaderUtil.newCheckedInstanceOfProperty("Log4jContextSelector", ContextSelector.class);
			if (selector != null) {
				return selector;
			}
		} catch (Exception var1) {
			LOGGER.error("Unable to create custom ContextSelector. Falling back to default.", var1);
		}

		return new ClassLoaderContextSelector();
	}

	private static ShutdownCallbackRegistry createShutdownCallbackRegistry() {
		try {
			ShutdownCallbackRegistry registry = LoaderUtil.newCheckedInstanceOfProperty("log4j.shutdownCallbackRegistry", ShutdownCallbackRegistry.class);
			if (registry != null) {
				return registry;
			}
		} catch (Exception var1) {
			LOGGER.error("Unable to create custom ShutdownCallbackRegistry. Falling back to default.", var1);
		}

		return new DefaultShutdownCallbackRegistry();
	}

	private void initializeShutdownCallbackRegistry() {
		if (SHUTDOWN_HOOK_ENABLED && this.shutdownCallbackRegistry instanceof LifeCycle) {
			try {
				((LifeCycle)this.shutdownCallbackRegistry).start();
			} catch (IllegalStateException var2) {
				LOGGER.error("Cannot start ShutdownCallbackRegistry, already shutting down.");
				throw var2;
			} catch (RuntimeException var3) {
				LOGGER.error("There was an error starting the ShutdownCallbackRegistry.", var3);
			}
		}
	}

	public LoggerContext getContext(String fqcn, ClassLoader loader, Object externalContext, boolean currentContext) {
		LoggerContext ctx = this.selector.getContext(fqcn, loader, currentContext);
		if (externalContext != null && ctx.getExternalContext() == null) {
			ctx.setExternalContext(externalContext);
		}

		if (ctx.getState() == State.INITIALIZED) {
			ctx.start();
		}

		return ctx;
	}

	public LoggerContext getContext(String fqcn, ClassLoader loader, Object externalContext, boolean currentContext, ConfigurationSource source) {
		LoggerContext ctx = this.selector.getContext(fqcn, loader, currentContext, null);
		if (externalContext != null && ctx.getExternalContext() == null) {
			ctx.setExternalContext(externalContext);
		}

		if (ctx.getState() == State.INITIALIZED) {
			if (source != null) {
				ContextAnchor.THREAD_CONTEXT.set(ctx);
				Configuration config = ConfigurationFactory.getInstance().getConfiguration(ctx, source);
				LOGGER.debug("Starting LoggerContext[name={}] from configuration {}", ctx.getName(), source);
				ctx.start(config);
				ContextAnchor.THREAD_CONTEXT.remove();
			} else {
				ctx.start();
			}
		}

		return ctx;
	}

	public LoggerContext getContext(String fqcn, ClassLoader loader, Object externalContext, boolean currentContext, Configuration configuration) {
		LoggerContext ctx = this.selector.getContext(fqcn, loader, currentContext, null);
		if (externalContext != null && ctx.getExternalContext() == null) {
			ctx.setExternalContext(externalContext);
		}

		if (ctx.getState() == State.INITIALIZED) {
			ContextAnchor.THREAD_CONTEXT.set(ctx);

			try {
				ctx.start(configuration);
			} finally {
				ContextAnchor.THREAD_CONTEXT.remove();
			}
		}

		return ctx;
	}

	public LoggerContext getContext(String fqcn, ClassLoader loader, Object externalContext, boolean currentContext, URI configLocation, String name) {
		LoggerContext ctx = this.selector.getContext(fqcn, loader, currentContext, configLocation);
		if (externalContext != null && ctx.getExternalContext() == null) {
			ctx.setExternalContext(externalContext);
		}

		if (name != null) {
			ctx.setName(name);
		}

		if (ctx.getState() == State.INITIALIZED) {
			if (configLocation == null && name == null) {
				ctx.start();
			} else {
				ContextAnchor.THREAD_CONTEXT.set(ctx);
				Configuration config = ConfigurationFactory.getInstance().getConfiguration(ctx, name, configLocation);
				LOGGER.debug("Starting LoggerContext[name={}] from configuration at {}", ctx.getName(), configLocation);
				ctx.start(config);
				ContextAnchor.THREAD_CONTEXT.remove();
			}
		}

		return ctx;
	}

	public LoggerContext getContext(String fqcn, ClassLoader loader, Object externalContext, boolean currentContext, List<URI> configLocations, String name) {
		LoggerContext ctx = this.selector.getContext(fqcn, loader, currentContext, null);
		if (externalContext != null && ctx.getExternalContext() == null) {
			ctx.setExternalContext(externalContext);
		}

		if (name != null) {
			ctx.setName(name);
		}

		if (ctx.getState() == State.INITIALIZED) {
			if (configLocations != null && !configLocations.isEmpty()) {
				ContextAnchor.THREAD_CONTEXT.set(ctx);
				List<AbstractConfiguration> configurations = new ArrayList(configLocations.size());

				for (URI configLocation : configLocations) {
					Configuration currentReadConfiguration = ConfigurationFactory.getInstance().getConfiguration(ctx, name, configLocation);
					if (currentReadConfiguration instanceof AbstractConfiguration) {
						configurations.add((AbstractConfiguration)currentReadConfiguration);
					} else {
						LOGGER.error("Found configuration {}, which is not an AbstractConfiguration and can't be handled by CompositeConfiguration", configLocation);
					}
				}

				CompositeConfiguration compositeConfiguration = new CompositeConfiguration(configurations);
				LOGGER.debug("Starting LoggerContext[name={}] from configurations at {}", ctx.getName(), configLocations);
				ctx.start(compositeConfiguration);
				ContextAnchor.THREAD_CONTEXT.remove();
			} else {
				ctx.start();
			}
		}

		return ctx;
	}

	public ContextSelector getSelector() {
		return this.selector;
	}

	public ShutdownCallbackRegistry getShutdownCallbackRegistry() {
		return this.shutdownCallbackRegistry;
	}

	@Override
	public void removeContext(org.apache.logging.log4j.spi.LoggerContext context) {
		if (context instanceof LoggerContext) {
			this.selector.removeContext((LoggerContext)context);
		}
	}

	@Override
	public Cancellable addShutdownCallback(Runnable callback) {
		return SHUTDOWN_HOOK_ENABLED ? this.shutdownCallbackRegistry.addShutdownCallback(callback) : null;
	}
}
