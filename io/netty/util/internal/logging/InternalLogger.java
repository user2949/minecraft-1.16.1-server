package io.netty.util.internal.logging;

public interface InternalLogger {
	String name();

	boolean isTraceEnabled();

	void trace(String string);

	void trace(String string, Object object);

	void trace(String string, Object object2, Object object3);

	void trace(String string, Object... arr);

	void trace(String string, Throwable throwable);

	void trace(Throwable throwable);

	boolean isDebugEnabled();

	void debug(String string);

	void debug(String string, Object object);

	void debug(String string, Object object2, Object object3);

	void debug(String string, Object... arr);

	void debug(String string, Throwable throwable);

	void debug(Throwable throwable);

	boolean isInfoEnabled();

	void info(String string);

	void info(String string, Object object);

	void info(String string, Object object2, Object object3);

	void info(String string, Object... arr);

	void info(String string, Throwable throwable);

	void info(Throwable throwable);

	boolean isWarnEnabled();

	void warn(String string);

	void warn(String string, Object object);

	void warn(String string, Object... arr);

	void warn(String string, Object object2, Object object3);

	void warn(String string, Throwable throwable);

	void warn(Throwable throwable);

	boolean isErrorEnabled();

	void error(String string);

	void error(String string, Object object);

	void error(String string, Object object2, Object object3);

	void error(String string, Object... arr);

	void error(String string, Throwable throwable);

	void error(Throwable throwable);

	boolean isEnabled(InternalLogLevel internalLogLevel);

	void log(InternalLogLevel internalLogLevel, String string);

	void log(InternalLogLevel internalLogLevel, String string, Object object);

	void log(InternalLogLevel internalLogLevel, String string, Object object3, Object object4);

	void log(InternalLogLevel internalLogLevel, String string, Object... arr);

	void log(InternalLogLevel internalLogLevel, String string, Throwable throwable);

	void log(InternalLogLevel internalLogLevel, Throwable throwable);
}
