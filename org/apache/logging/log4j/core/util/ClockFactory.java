package org.apache.logging.log4j.core.util;

import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.PropertiesUtil;

public final class ClockFactory {
	public static final String PROPERTY_NAME = "log4j.Clock";
	private static final StatusLogger LOGGER = StatusLogger.getLogger();

	private ClockFactory() {
	}

	public static Clock getClock() {
		return createClock();
	}

	private static Clock createClock() {
		String userRequest = PropertiesUtil.getProperties().getStringProperty("log4j.Clock");
		if (userRequest != null && !"SystemClock".equals(userRequest)) {
			if (!CachedClock.class.getName().equals(userRequest) && !"CachedClock".equals(userRequest)) {
				if (!CoarseCachedClock.class.getName().equals(userRequest) && !"CoarseCachedClock".equals(userRequest)) {
					try {
						Clock result = Loader.newCheckedInstanceOf(userRequest, Clock.class);
						LOGGER.trace("Using {} for timestamps.", result.getClass().getName());
						return result;
					} catch (Exception var3) {
						String fmt = "Could not create {}: {}, using default SystemClock for timestamps.";
						LOGGER.error("Could not create {}: {}, using default SystemClock for timestamps.", userRequest, var3);
						return new SystemClock();
					}
				} else {
					LOGGER.trace("Using specified CoarseCachedClock for timestamps.");
					return CoarseCachedClock.instance();
				}
			} else {
				LOGGER.trace("Using specified CachedClock for timestamps.");
				return CachedClock.instance();
			}
		} else {
			LOGGER.trace("Using default SystemClock for timestamps.");
			return new SystemClock();
		}
	}
}
