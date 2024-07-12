package org.apache.logging.log4j.core.async;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;

public class AsyncQueueFullPolicyFactory {
	static final String PROPERTY_NAME_ASYNC_EVENT_ROUTER = "log4j2.AsyncQueueFullPolicy";
	static final String PROPERTY_VALUE_DEFAULT_ASYNC_EVENT_ROUTER = "Default";
	static final String PROPERTY_VALUE_DISCARDING_ASYNC_EVENT_ROUTER = "Discard";
	static final String PROPERTY_NAME_DISCARDING_THRESHOLD_LEVEL = "log4j2.DiscardThreshold";
	private static final Logger LOGGER = StatusLogger.getLogger();

	public static AsyncQueueFullPolicy create() {
		String router = PropertiesUtil.getProperties().getStringProperty("log4j2.AsyncQueueFullPolicy");
		if (router == null
			|| "Default".equals(router)
			|| DefaultAsyncQueueFullPolicy.class.getSimpleName().equals(router)
			|| DefaultAsyncQueueFullPolicy.class.getName().equals(router)) {
			return new DefaultAsyncQueueFullPolicy();
		} else {
			return !"Discard".equals(router)
					&& !DiscardingAsyncQueueFullPolicy.class.getSimpleName().equals(router)
					&& !DiscardingAsyncQueueFullPolicy.class.getName().equals(router)
				? createCustomRouter(router)
				: createDiscardingAsyncQueueFullPolicy();
		}
	}

	private static AsyncQueueFullPolicy createCustomRouter(String router) {
		try {
			Class<? extends AsyncQueueFullPolicy> cls = LoaderUtil.loadClass(router).asSubclass(AsyncQueueFullPolicy.class);
			LOGGER.debug("Creating custom AsyncQueueFullPolicy '{}'", router);
			return (AsyncQueueFullPolicy)cls.newInstance();
		} catch (Exception var2) {
			LOGGER.debug("Using DefaultAsyncQueueFullPolicy. Could not create custom AsyncQueueFullPolicy '{}': {}", router, var2.toString());
			return new DefaultAsyncQueueFullPolicy();
		}
	}

	private static AsyncQueueFullPolicy createDiscardingAsyncQueueFullPolicy() {
		PropertiesUtil util = PropertiesUtil.getProperties();
		String level = util.getStringProperty("log4j2.DiscardThreshold", Level.INFO.name());
		Level thresholdLevel = Level.toLevel(level, Level.INFO);
		LOGGER.debug("Creating custom DiscardingAsyncQueueFullPolicy(discardThreshold:{})", thresholdLevel);
		return new DiscardingAsyncQueueFullPolicy(thresholdLevel);
	}
}
