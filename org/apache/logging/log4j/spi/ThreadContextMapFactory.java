package org.apache.logging.log4j.spi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.Constants;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.ProviderUtil;

public final class ThreadContextMapFactory {
	private static final Logger LOGGER = StatusLogger.getLogger();
	private static final String THREAD_CONTEXT_KEY = "log4j2.threadContextMap";
	private static final String GC_FREE_THREAD_CONTEXT_KEY = "log4j2.garbagefree.threadContextMap";

	private ThreadContextMapFactory() {
	}

	public static ThreadContextMap createThreadContextMap() {
		PropertiesUtil managerProps = PropertiesUtil.getProperties();
		String threadContextMapName = managerProps.getStringProperty("log4j2.threadContextMap");
		ClassLoader cl = ProviderUtil.findClassLoader();
		ThreadContextMap result = null;
		if (threadContextMapName != null) {
			try {
				Class<?> clazz = cl.loadClass(threadContextMapName);
				if (ThreadContextMap.class.isAssignableFrom(clazz)) {
					result = (ThreadContextMap)clazz.newInstance();
				}
			} catch (ClassNotFoundException var9) {
				LOGGER.error("Unable to locate configured ThreadContextMap {}", threadContextMapName);
			} catch (Exception var10) {
				LOGGER.error("Unable to create configured ThreadContextMap {}", threadContextMapName, var10);
			}
		}

		if (result == null && ProviderUtil.hasProviders() && LogManager.getFactory() != null) {
			String factoryClassName = LogManager.getFactory().getClass().getName();

			for (Provider provider : ProviderUtil.getProviders()) {
				if (factoryClassName.equals(provider.getClassName())) {
					Class<? extends ThreadContextMap> clazz = provider.loadThreadContextMap();
					if (clazz != null) {
						try {
							result = (ThreadContextMap)clazz.newInstance();
							break;
						} catch (Exception var11) {
							LOGGER.error("Unable to locate or load configured ThreadContextMap {}", provider.getThreadContextMap(), var11);
							result = createDefaultThreadContextMap();
						}
					}
				}
			}
		}

		if (result == null) {
			result = createDefaultThreadContextMap();
		}

		return result;
	}

	private static ThreadContextMap createDefaultThreadContextMap() {
		if (Constants.ENABLE_THREADLOCALS) {
			return (ThreadContextMap)(PropertiesUtil.getProperties().getBooleanProperty("log4j2.garbagefree.threadContextMap")
				? new GarbageFreeSortedArrayThreadContextMap()
				: new CopyOnWriteSortedArrayThreadContextMap());
		} else {
			return new DefaultThreadContextMap(true);
		}
	}
}
