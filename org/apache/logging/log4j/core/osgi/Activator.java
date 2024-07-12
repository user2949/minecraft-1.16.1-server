package org.apache.logging.log4j.core.osgi;

import java.util.concurrent.atomic.AtomicReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.util.PluginRegistry;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.SynchronousBundleListener;
import org.osgi.framework.wiring.BundleWiring;

public final class Activator implements BundleActivator, SynchronousBundleListener {
	private static final Logger LOGGER = StatusLogger.getLogger();
	private final AtomicReference<BundleContext> contextRef = new AtomicReference();

	public void start(BundleContext context) throws Exception {
		if (PropertiesUtil.getProperties().getStringProperty("Log4jContextSelector") == null) {
			System.setProperty("Log4jContextSelector", BundleContextSelector.class.getName());
		}

		if (this.contextRef.compareAndSet(null, context)) {
			context.addBundleListener(this);
			scanInstalledBundlesForPlugins(context);
		}
	}

	private static void scanInstalledBundlesForPlugins(BundleContext context) {
		Bundle[] bundles = context.getBundles();

		for (Bundle bundle : bundles) {
			if (bundle.getState() == 32 && bundle.getBundleId() != 0L) {
				scanBundleForPlugins(bundle);
			}
		}
	}

	private static void scanBundleForPlugins(Bundle bundle) {
		LOGGER.trace("Scanning bundle [{}] for plugins.", bundle.getSymbolicName());
		PluginRegistry.getInstance().loadFromBundle(bundle.getBundleId(), ((BundleWiring)bundle.adapt(BundleWiring.class)).getClassLoader());
	}

	private static void stopBundlePlugins(Bundle bundle) {
		LOGGER.trace("Stopping bundle [{}] plugins.", bundle.getSymbolicName());
		PluginRegistry.getInstance().clearBundlePlugins(bundle.getBundleId());
	}

	public void stop(BundleContext context) throws Exception {
		this.contextRef.compareAndSet(context, null);
		LogManager.shutdown();
	}

	public void bundleChanged(BundleEvent event) {
		switch (event.getType()) {
			case 2:
				scanBundleForPlugins(event.getBundle());
				break;
			case 256:
				stopBundlePlugins(event.getBundle());
		}
	}
}
