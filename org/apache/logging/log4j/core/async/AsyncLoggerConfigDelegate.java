package org.apache.logging.log4j.core.async;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.impl.LogEventFactory;
import org.apache.logging.log4j.core.jmx.RingBufferAdmin;

public interface AsyncLoggerConfigDelegate {
	RingBufferAdmin createRingBufferAdmin(String string1, String string2);

	EventRoute getEventRoute(Level level);

	void enqueueEvent(LogEvent logEvent, AsyncLoggerConfig asyncLoggerConfig);

	boolean tryEnqueue(LogEvent logEvent, AsyncLoggerConfig asyncLoggerConfig);

	void setLogEventFactory(LogEventFactory logEventFactory);
}
