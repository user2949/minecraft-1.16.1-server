package io.netty.util.internal;

import java.util.Collection;

public final class ObjectUtil {
	private ObjectUtil() {
	}

	public static <T> T checkNotNull(T arg, String text) {
		if (arg == null) {
			throw new NullPointerException(text);
		} else {
			return arg;
		}
	}

	public static int checkPositive(int i, String name) {
		if (i <= 0) {
			throw new IllegalArgumentException(name + ": " + i + " (expected: > 0)");
		} else {
			return i;
		}
	}

	public static long checkPositive(long i, String name) {
		if (i <= 0L) {
			throw new IllegalArgumentException(name + ": " + i + " (expected: > 0)");
		} else {
			return i;
		}
	}

	public static int checkPositiveOrZero(int i, String name) {
		if (i < 0) {
			throw new IllegalArgumentException(name + ": " + i + " (expected: >= 0)");
		} else {
			return i;
		}
	}

	public static long checkPositiveOrZero(long i, String name) {
		if (i < 0L) {
			throw new IllegalArgumentException(name + ": " + i + " (expected: >= 0)");
		} else {
			return i;
		}
	}

	public static <T> T[] checkNonEmpty(T[] array, String name) {
		checkNotNull((T)array, name);
		checkPositive(array.length, name + ".length");
		return array;
	}

	public static <T extends Collection<?>> T checkNonEmpty(T collection, String name) {
		checkNotNull(collection, name);
		checkPositive(collection.size(), name + ".size");
		return collection;
	}

	public static int intValue(Integer wrapper, int defaultValue) {
		return wrapper != null ? wrapper : defaultValue;
	}

	public static long longValue(Long wrapper, long defaultValue) {
		return wrapper != null ? wrapper : defaultValue;
	}
}
