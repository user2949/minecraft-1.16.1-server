package org.apache.logging.log4j.core.async;

import org.apache.logging.log4j.Level;

public class DefaultAsyncQueueFullPolicy implements AsyncQueueFullPolicy {
	@Override
	public EventRoute getRoute(long backgroundThreadId, Level level) {
		return EventRoute.SYNCHRONOUS;
	}
}
