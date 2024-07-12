package org.apache.logging.log4j.util;

import java.net.URL;
import java.security.Permission;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.apache.logging.log4j.status.StatusLogger;
import org.osgi.framework.AdaptPermission;
import org.osgi.framework.AdminPermission;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.SynchronousBundleListener;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

public class Activator implements BundleActivator, SynchronousBundleListener {
	private static final SecurityManager SECURITY_MANAGER = System.getSecurityManager();
	private static final Logger LOGGER = StatusLogger.getLogger();
	private boolean lockingProviderUtil;

	private static void checkPermission(Permission permission) {
		if (SECURITY_MANAGER != null) {
			SECURITY_MANAGER.checkPermission(permission);
		}
	}

	private void loadProvider(Bundle bundle) {
		if (bundle.getState() != 1) {
			try {
				checkPermission(new AdminPermission(bundle, "resource"));
				checkPermission(new AdaptPermission(BundleWiring.class.getName(), bundle, "adapt"));
				this.loadProvider((BundleWiring)bundle.adapt(BundleWiring.class));
			} catch (SecurityException var3) {
				LOGGER.debug("Cannot access bundle [{}] contents. Ignoring.", bundle.getSymbolicName(), var3);
			} catch (Exception var4) {
				LOGGER.warn("Problem checking bundle {} for Log4j 2 provider.", bundle.getSymbolicName(), var4);
			}
		}
	}

	private void loadProvider(BundleWiring provider) {
		for (URL url : provider.findEntries("META-INF", "log4j-provider.properties", 0)) {
			ProviderUtil.loadProvider(url, provider.getClassLoader());
		}
	}

	public void start(BundleContext context) throws Exception {
		ProviderUtil.STARTUP_LOCK.lock();
		this.lockingProviderUtil = true;
		BundleWiring self = (BundleWiring)context.getBundle().adapt(BundleWiring.class);

		for (BundleWire wire : self.getRequiredWires(LoggerContextFactory.class.getName())) {
			this.loadProvider(wire.getProviderWiring());
		}

		context.addBundleListener(this);
		Bundle[] bundles = context.getBundles();

		for (Bundle bundle : bundles) {
			this.loadProvider(bundle);
		}

		this.unlockIfReady();
	}

	private void unlockIfReady() {
		if (this.lockingProviderUtil && !ProviderUtil.PROVIDERS.isEmpty()) {
			ProviderUtil.STARTUP_LOCK.unlock();
			this.lockingProviderUtil = false;
		}
	}

	public void stop(BundleContext context) throws Exception {
		context.removeBundleListener(this);
		this.unlockIfReady();
	}

	public void bundleChanged(BundleEvent event) {
		switch (event.getType()) {
			case 2:
				this.loadProvider(event.getBundle());
				this.unlockIfReady();
		}
	}
}
