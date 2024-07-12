package org.apache.logging.log4j.core;

import java.io.Serializable;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext.ContextStack;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.ReadOnlyStringMap;

public interface LogEvent extends Serializable {
	LogEvent toImmutable();

	@Deprecated
	Map<String, String> getContextMap();

	ReadOnlyStringMap getContextData();

	ContextStack getContextStack();

	String getLoggerFqcn();

	Level getLevel();

	String getLoggerName();

	Marker getMarker();

	Message getMessage();

	long getTimeMillis();

	StackTraceElement getSource();

	String getThreadName();

	long getThreadId();

	int getThreadPriority();

	Throwable getThrown();

	ThrowableProxy getThrownProxy();

	boolean isEndOfBatch();

	boolean isIncludeLocation();

	void setEndOfBatch(boolean boolean1);

	void setIncludeLocation(boolean boolean1);

	long getNanoTime();
}
