package org.apache.logging.log4j.core.impl;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.SortedArrayStringMap;
import org.apache.logging.log4j.util.StringMap;

public class ContextDataFactory {
	private static final Lookup LOOKUP = MethodHandles.lookup();
	private static final String CLASS_NAME = PropertiesUtil.getProperties().getStringProperty("log4j2.ContextData");
	private static final Class<? extends StringMap> CACHED_CLASS = createCachedClass(CLASS_NAME);
	private static final MethodHandle DEFAULT_CONSTRUCTOR = createDefaultConstructor(CACHED_CLASS);
	private static final MethodHandle INITIAL_CAPACITY_CONSTRUCTOR = createInitialCapacityConstructor(CACHED_CLASS);
	private static final StringMap EMPTY_STRING_MAP = createContextData(1);

	private static Class<? extends StringMap> createCachedClass(String className) {
		if (className == null) {
			return null;
		} else {
			try {
				return LoaderUtil.loadClass(className).asSubclass(StringMap.class);
			} catch (Exception var2) {
				return null;
			}
		}
	}

	private static MethodHandle createDefaultConstructor(Class<? extends StringMap> cachedClass) {
		if (cachedClass == null) {
			return null;
		} else {
			try {
				return LOOKUP.findConstructor(cachedClass, MethodType.methodType(void.class));
			} catch (IllegalAccessException | NoSuchMethodException var2) {
				return null;
			}
		}
	}

	private static MethodHandle createInitialCapacityConstructor(Class<? extends StringMap> cachedClass) {
		if (cachedClass == null) {
			return null;
		} else {
			try {
				return LOOKUP.findConstructor(cachedClass, MethodType.methodType(void.class, int.class));
			} catch (IllegalAccessException | NoSuchMethodException var2) {
				return null;
			}
		}
	}

	public static StringMap createContextData() {
		if (DEFAULT_CONSTRUCTOR == null) {
			return new SortedArrayStringMap();
		} else {
			try {
				return (StringMap)DEFAULT_CONSTRUCTOR.invoke();
			} catch (Throwable var1) {
				return new SortedArrayStringMap();
			}
		}
	}

	public static StringMap createContextData(int initialCapacity) {
		if (INITIAL_CAPACITY_CONSTRUCTOR == null) {
			return new SortedArrayStringMap(initialCapacity);
		} else {
			try {
				return (StringMap)INITIAL_CAPACITY_CONSTRUCTOR.invoke(initialCapacity);
			} catch (Throwable var2) {
				return new SortedArrayStringMap(initialCapacity);
			}
		}
	}

	public static StringMap emptyFrozenContextData() {
		return EMPTY_STRING_MAP;
	}

	static {
		EMPTY_STRING_MAP.freeze();
	}
}
