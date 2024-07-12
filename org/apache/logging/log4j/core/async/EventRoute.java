package org.apache.logging.log4j.core.async;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AsyncAppender;
import org.apache.logging.log4j.message.Message;

public enum EventRoute {
	ENQUEUE {
		@Override
		public void logMessage(AsyncLogger asyncLogger, String fqcn, Level level, Marker marker, Message message, Throwable thrown) {
		}

		@Override
		public void logMessage(AsyncLoggerConfig asyncLoggerConfig, LogEvent event) {
			asyncLoggerConfig.callAppendersInBackgroundThread(event);
		}

		@Override
		public void logMessage(AsyncAppender asyncAppender, LogEvent logEvent) {
			asyncAppender.logMessageInBackgroundThread(logEvent);
		}
	},
	SYNCHRONOUS {
		@Override
		public void logMessage(AsyncLogger asyncLogger, String fqcn, Level level, Marker marker, Message message, Throwable thrown) {
		}

		@Override
		public void logMessage(AsyncLoggerConfig asyncLoggerConfig, LogEvent event) {
			asyncLoggerConfig.callAppendersInCurrentThread(event);
		}

		@Override
		public void logMessage(AsyncAppender asyncAppender, LogEvent logEvent) {
			asyncAppender.logMessageInCurrentThread(logEvent);
		}
	},
	DISCARD {
		@Override
		public void logMessage(AsyncLogger asyncLogger, String fqcn, Level level, Marker marker, Message message, Throwable thrown) {
		}

		@Override
		public void logMessage(AsyncLoggerConfig asyncLoggerConfig, LogEvent event) {
		}

		@Override
		public void logMessage(AsyncAppender asyncAppender, LogEvent coreEvent) {
		}
	};

	private EventRoute() {
	}

	public abstract void logMessage(AsyncLogger asyncLogger, String string, Level level, Marker marker, Message message, Throwable throwable);

	public abstract void logMessage(AsyncLoggerConfig asyncLoggerConfig, LogEvent logEvent);

	public abstract void logMessage(AsyncAppender asyncAppender, LogEvent logEvent);
}
