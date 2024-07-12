package org.apache.logging.log4j.core.osgi;

import java.lang.ref.WeakReference;
import java.net.URI;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.impl.ContextAnchor;
import org.apache.logging.log4j.core.selector.ClassLoaderContextSelector;
import org.apache.logging.log4j.util.ReflectionUtil;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleReference;
import org.osgi.framework.FrameworkUtil;

public class BundleContextSelector extends ClassLoaderContextSelector {
	@Override
	public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext, URI configLocation) {
		if (currentContext) {
			LoggerContext ctx = (LoggerContext)ContextAnchor.THREAD_CONTEXT.get();
			return ctx != null ? ctx : this.getDefault();
		} else if (loader instanceof BundleReference) {
			return locateContext(((BundleReference)loader).getBundle(), configLocation);
		} else {
			Class<?> callerClass = ReflectionUtil.getCallerClass(fqcn);
			if (callerClass != null) {
				return locateContext(FrameworkUtil.getBundle(callerClass), configLocation);
			} else {
				LoggerContext lc = (LoggerContext)ContextAnchor.THREAD_CONTEXT.get();
				return lc == null ? this.getDefault() : lc;
			}
		}
	}

	private static LoggerContext locateContext(Bundle bundle, URI configLocation) {
		String name = ((Bundle)Objects.requireNonNull(bundle, "No Bundle provided")).getSymbolicName();
		AtomicReference<WeakReference<LoggerContext>> ref = (AtomicReference<WeakReference<LoggerContext>>)CONTEXT_MAP.get(name);
		if (ref == null) {
			LoggerContext context = new LoggerContext(name, bundle, configLocation);
			CONTEXT_MAP.putIfAbsent(name, new AtomicReference(new WeakReference(context)));
			return (LoggerContext)((WeakReference)((AtomicReference)CONTEXT_MAP.get(name)).get()).get();
		} else {
			WeakReference<LoggerContext> r = (WeakReference<LoggerContext>)ref.get();
			LoggerContext ctx = (LoggerContext)r.get();
			if (ctx == null) {
				LoggerContext context = new LoggerContext(name, bundle, configLocation);
				ref.compareAndSet(r, new WeakReference(context));
				return (LoggerContext)((WeakReference)ref.get()).get();
			} else {
				URI oldConfigLocation = ctx.getConfigLocation();
				if (oldConfigLocation == null && configLocation != null) {
					LOGGER.debug("Setting bundle ({}) configuration to {}", name, configLocation);
					ctx.setConfigLocation(configLocation);
				} else if (oldConfigLocation != null && configLocation != null && !configLocation.equals(oldConfigLocation)) {
					LOGGER.warn("locateContext called with URI [{}], but existing LoggerContext has URI [{}]", configLocation, oldConfigLocation);
				}

				return ctx;
			}
		}
	}
}
