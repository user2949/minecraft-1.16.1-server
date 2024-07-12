package org.apache.logging.log4j.core;

public interface ErrorHandler {
	void error(String string);

	void error(String string, Throwable throwable);

	void error(String string, LogEvent logEvent, Throwable throwable);
}
