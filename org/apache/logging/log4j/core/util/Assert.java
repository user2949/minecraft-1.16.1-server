package org.apache.logging.log4j.core.util;

import java.util.Collection;
import java.util.Map;

public final class Assert {
	private Assert() {
	}

	public static boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		} else if (o instanceof CharSequence) {
			return ((CharSequence)o).length() == 0;
		} else if (o.getClass().isArray()) {
			return ((Object[])o).length == 0;
		} else if (o instanceof Collection) {
			return ((Collection)o).isEmpty();
		} else {
			return o instanceof Map ? ((Map)o).isEmpty() : false;
		}
	}

	public static boolean isNonEmpty(Object o) {
		return !isEmpty(o);
	}

	public static <T> T requireNonEmpty(T value) {
		return requireNonEmpty(value, "");
	}

	public static <T> T requireNonEmpty(T value, String message) {
		if (isEmpty(value)) {
			throw new IllegalArgumentException(message);
		} else {
			return value;
		}
	}

	public static int valueIsAtLeast(int value, int minValue) {
		if (value < minValue) {
			throw new IllegalArgumentException("Value should be at least " + minValue + " but was " + value);
		} else {
			return value;
		}
	}
}