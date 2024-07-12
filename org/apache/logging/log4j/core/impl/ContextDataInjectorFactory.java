package org.apache.logging.log4j.core.impl;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.ContextDataInjector;
import org.apache.logging.log4j.core.impl.ThreadContextDataInjector.ForCopyOnWriteThreadContextMap;
import org.apache.logging.log4j.core.impl.ThreadContextDataInjector.ForDefaultThreadContextMap;
import org.apache.logging.log4j.core.impl.ThreadContextDataInjector.ForGarbageFreeThreadContextMap;
import org.apache.logging.log4j.spi.CopyOnWrite;
import org.apache.logging.log4j.spi.DefaultThreadContextMap;
import org.apache.logging.log4j.spi.ReadOnlyThreadContextMap;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;

public class ContextDataInjectorFactory {
	public static ContextDataInjector createInjector() {
		String className = PropertiesUtil.getProperties().getStringProperty("log4j2.ContextDataInjector");
		if (className == null) {
			return createDefaultInjector();
		} else {
			try {
				Class<? extends ContextDataInjector> cls = LoaderUtil.loadClass(className).asSubclass(ContextDataInjector.class);
				return (ContextDataInjector)cls.newInstance();
			} catch (Exception var3) {
				ContextDataInjector result = createDefaultInjector();
				StatusLogger.getLogger().warn("Could not create ContextDataInjector for '{}', using default {}: {}", className, result.getClass().getName(), var3);
				return result;
			}
		}
	}

	private static ContextDataInjector createDefaultInjector() {
		ReadOnlyThreadContextMap threadContextMap = ThreadContext.getThreadContextMap();
		if (threadContextMap instanceof DefaultThreadContextMap || threadContextMap == null) {
			return new ForDefaultThreadContextMap();
		} else {
			return (ContextDataInjector)(threadContextMap instanceof CopyOnWrite ? new ForCopyOnWriteThreadContextMap() : new ForGarbageFreeThreadContextMap());
		}
	}
}
