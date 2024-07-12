package org.apache.logging.log4j.core.selector;

import java.net.URI;
import java.util.List;
import org.apache.logging.log4j.core.LoggerContext;

public interface ContextSelector {
	LoggerContext getContext(String string, ClassLoader classLoader, boolean boolean3);

	LoggerContext getContext(String string, ClassLoader classLoader, boolean boolean3, URI uRI);

	List<LoggerContext> getLoggerContexts();

	void removeContext(LoggerContext loggerContext);
}
