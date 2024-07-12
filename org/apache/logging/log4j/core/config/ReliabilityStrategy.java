package org.apache.logging.log4j.core.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.Supplier;

public interface ReliabilityStrategy {
	void log(Supplier<LoggerConfig> supplier, String string2, String string3, Marker marker, Level level, Message message, Throwable throwable);

	void log(Supplier<LoggerConfig> supplier, LogEvent logEvent);

	LoggerConfig getActiveLoggerConfig(Supplier<LoggerConfig> supplier);

	void afterLogEvent();

	void beforeStopAppenders();

	void beforeStopConfiguration(Configuration configuration);
}
