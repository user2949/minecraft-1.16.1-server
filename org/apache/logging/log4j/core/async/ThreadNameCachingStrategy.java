package org.apache.logging.log4j.core.async;

import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.PropertiesUtil;

public enum ThreadNameCachingStrategy {
	CACHED {
		@Override
		public String getThreadName() {
			String result = (String)ThreadNameCachingStrategy.THREADLOCAL_NAME.get();
			if (result == null) {
				result = Thread.currentThread().getName();
				ThreadNameCachingStrategy.THREADLOCAL_NAME.set(result);
			}

			return result;
		}
	},
	UNCACHED {
		@Override
		public String getThreadName() {
			return Thread.currentThread().getName();
		}
	};

	private static final StatusLogger LOGGER = StatusLogger.getLogger();
	private static final ThreadLocal<String> THREADLOCAL_NAME = new ThreadLocal();

	private ThreadNameCachingStrategy() {
	}

	abstract String getThreadName();

	public static ThreadNameCachingStrategy create() {
		String name = PropertiesUtil.getProperties().getStringProperty("AsyncLogger.ThreadNameStrategy", CACHED.name());

		try {
			ThreadNameCachingStrategy result = valueOf(name);
			LOGGER.debug("AsyncLogger.ThreadNameStrategy={}", result);
			return result;
		} catch (Exception var2) {
			LOGGER.debug("Using AsyncLogger.ThreadNameStrategy.CACHED: '{}' not valid: {}", name, var2.toString());
			return CACHED;
		}
	}
}
