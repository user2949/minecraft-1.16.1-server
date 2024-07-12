package org.apache.logging.log4j.core.appender.routing;

import org.apache.logging.log4j.core.LogEvent;

public interface PurgePolicy {
	void purge();

	void update(String string, LogEvent logEvent);

	void initialize(RoutingAppender routingAppender);
}
