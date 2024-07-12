package org.apache.logging.log4j.spi;

import java.net.URI;

public interface LoggerContextFactory {
	LoggerContext getContext(String string, ClassLoader classLoader, Object object, boolean boolean4);

	LoggerContext getContext(String string1, ClassLoader classLoader, Object object, boolean boolean4, URI uRI, String string6);

	void removeContext(LoggerContext loggerContext);
}
