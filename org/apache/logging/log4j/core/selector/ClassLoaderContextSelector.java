package org.apache.logging.log4j.core.selector;

import java.lang.ref.WeakReference;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.impl.ContextAnchor;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.ReflectionUtil;

public class ClassLoaderContextSelector implements ContextSelector {
	private static final AtomicReference<LoggerContext> DEFAULT_CONTEXT = new AtomicReference();
	protected static final StatusLogger LOGGER = StatusLogger.getLogger();
	protected static final ConcurrentMap<String, AtomicReference<WeakReference<LoggerContext>>> CONTEXT_MAP = new ConcurrentHashMap();

	@Override
	public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext) {
		return this.getContext(fqcn, loader, currentContext, null);
	}

	@Override
	public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext, URI configLocation) {
		if (currentContext) {
			LoggerContext ctx = (LoggerContext)ContextAnchor.THREAD_CONTEXT.get();
			return ctx != null ? ctx : this.getDefault();
		} else if (loader != null) {
			return this.locateContext(loader, configLocation);
		} else {
			Class<?> clazz = ReflectionUtil.getCallerClass(fqcn);
			if (clazz != null) {
				return this.locateContext(clazz.getClassLoader(), configLocation);
			} else {
				LoggerContext lc = (LoggerContext)ContextAnchor.THREAD_CONTEXT.get();
				return lc != null ? lc : this.getDefault();
			}
		}
	}

	@Override
	public void removeContext(LoggerContext context) {
		for (Entry<String, AtomicReference<WeakReference<LoggerContext>>> entry : CONTEXT_MAP.entrySet()) {
			LoggerContext ctx = (LoggerContext)((WeakReference)((AtomicReference)entry.getValue()).get()).get();
			if (ctx == context) {
				CONTEXT_MAP.remove(entry.getKey());
			}
		}
	}

	@Override
	public List<LoggerContext> getLoggerContexts() {
		List<LoggerContext> list = new ArrayList();

		for (AtomicReference<WeakReference<LoggerContext>> ref : CONTEXT_MAP.values()) {
			LoggerContext ctx = (LoggerContext)((WeakReference)ref.get()).get();
			if (ctx != null) {
				list.add(ctx);
			}
		}

		return Collections.unmodifiableList(list);
	}

	private LoggerContext locateContext(ClassLoader loaderOrNull, URI configLocation) {
		ClassLoader loader = loaderOrNull != null ? loaderOrNull : ClassLoader.getSystemClassLoader();
		String name = this.toContextMapKey(loader);
		AtomicReference<WeakReference<LoggerContext>> ref = (AtomicReference<WeakReference<LoggerContext>>)CONTEXT_MAP.get(name);
		if (ref == null) {
			if (configLocation == null) {
				for (ClassLoader parent = loader.getParent(); parent != null; parent = parent.getParent()) {
					ref = (AtomicReference<WeakReference<LoggerContext>>)CONTEXT_MAP.get(this.toContextMapKey(parent));
					if (ref != null) {
						WeakReference<LoggerContext> r = (WeakReference<LoggerContext>)ref.get();
						LoggerContext ctx = (LoggerContext)r.get();
						if (ctx != null) {
							return ctx;
						}
					}
				}
			}

			LoggerContext ctx = this.createContext(name, configLocation);
			AtomicReference<WeakReference<LoggerContext>> r = new AtomicReference();
			r.set(new WeakReference(ctx));
			CONTEXT_MAP.putIfAbsent(name, r);
			return (LoggerContext)((WeakReference)((AtomicReference)CONTEXT_MAP.get(name)).get()).get();
		} else {
			WeakReference<LoggerContext> weakRef = (WeakReference<LoggerContext>)ref.get();
			LoggerContext ctx = (LoggerContext)weakRef.get();
			if (ctx == null) {
				ctx = this.createContext(name, configLocation);
				ref.compareAndSet(weakRef, new WeakReference(ctx));
				return ctx;
			} else {
				if (ctx.getConfigLocation() == null && configLocation != null) {
					LOGGER.debug("Setting configuration to {}", configLocation);
					ctx.setConfigLocation(configLocation);
				} else if (ctx.getConfigLocation() != null && configLocation != null && !ctx.getConfigLocation().equals(configLocation)) {
					LOGGER.warn("locateContext called with URI {}. Existing LoggerContext has URI {}", configLocation, ctx.getConfigLocation());
				}

				return ctx;
			}
		}
	}

	protected LoggerContext createContext(String name, URI configLocation) {
		return new LoggerContext(name, null, configLocation);
	}

	protected String toContextMapKey(ClassLoader loader) {
		return Integer.toHexString(System.identityHashCode(loader));
	}

	protected LoggerContext getDefault() {
		LoggerContext ctx = (LoggerContext)DEFAULT_CONTEXT.get();
		if (ctx != null) {
			return ctx;
		} else {
			DEFAULT_CONTEXT.compareAndSet(null, this.createContext(this.defaultContextName(), null));
			return (LoggerContext)DEFAULT_CONTEXT.get();
		}
	}

	protected String defaultContextName() {
		return "Default";
	}
}
