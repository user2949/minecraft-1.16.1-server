package org.apache.logging.log4j.core.selector;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.naming.NamingException;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.impl.ContextAnchor;
import org.apache.logging.log4j.core.net.JndiManager;
import org.apache.logging.log4j.status.StatusLogger;

public class JndiContextSelector implements NamedContextSelector {
	private static final LoggerContext CONTEXT = new LoggerContext("Default");
	private static final ConcurrentMap<String, LoggerContext> CONTEXT_MAP = new ConcurrentHashMap();
	private static final StatusLogger LOGGER = StatusLogger.getLogger();

	@Override
	public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext) {
		return this.getContext(fqcn, loader, currentContext, null);
	}

	@Override
	public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext, URI configLocation) {
		LoggerContext lc = (LoggerContext)ContextAnchor.THREAD_CONTEXT.get();
		if (lc != null) {
			return lc;
		} else {
			String loggingContextName = null;
			JndiManager jndiManager = JndiManager.getDefaultManager();

			try {
				loggingContextName = jndiManager.lookup("java:comp/env/log4j/context-name");
			} catch (NamingException var12) {
				LOGGER.error("Unable to lookup {}", "java:comp/env/log4j/context-name", var12);
			} finally {
				jndiManager.close();
			}

			return loggingContextName == null ? CONTEXT : this.locateContext(loggingContextName, null, configLocation);
		}
	}

	@Override
	public LoggerContext locateContext(String name, Object externalContext, URI configLocation) {
		if (name == null) {
			LOGGER.error("A context name is required to locate a LoggerContext");
			return null;
		} else {
			if (!CONTEXT_MAP.containsKey(name)) {
				LoggerContext ctx = new LoggerContext(name, externalContext, configLocation);
				CONTEXT_MAP.putIfAbsent(name, ctx);
			}

			return (LoggerContext)CONTEXT_MAP.get(name);
		}
	}

	@Override
	public void removeContext(LoggerContext context) {
		for (Entry<String, LoggerContext> entry : CONTEXT_MAP.entrySet()) {
			if (((LoggerContext)entry.getValue()).equals(context)) {
				CONTEXT_MAP.remove(entry.getKey());
			}
		}
	}

	@Override
	public LoggerContext removeContext(String name) {
		return (LoggerContext)CONTEXT_MAP.remove(name);
	}

	@Override
	public List<LoggerContext> getLoggerContexts() {
		List<LoggerContext> list = new ArrayList(CONTEXT_MAP.values());
		return Collections.unmodifiableList(list);
	}
}
