package org.apache.logging.log4j.util;

import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;

public final class Strings {
	public static final String EMPTY = "";
	public static final String LINE_SEPARATOR = PropertiesUtil.getProperties().getStringProperty("line.separator", "\n");

	private Strings() {
	}

	public static String dquote(String str) {
		return '"' + str + '"';
	}

	public static boolean isBlank(String s) {
		return s == null || s.trim().isEmpty();
	}

	public static boolean isEmpty(CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	public static boolean isNotBlank(String s) {
		return !isBlank(s);
	}

	public static boolean isNotEmpty(CharSequence cs) {
		return !isEmpty(cs);
	}

	public static String quote(String str) {
		return '\'' + str + '\'';
	}

	public String toRootUpperCase(String str) {
		return str.toUpperCase(Locale.ROOT);
	}

	public static String trimToNull(String str) {
		String ts = str == null ? null : str.trim();
		return isEmpty(ts) ? null : ts;
	}

	public static String join(Iterable<?> iterable, char separator) {
		return iterable == null ? null : join(iterable.iterator(), separator);
	}

	public static String join(Iterator<?> iterator, char separator) {
		if (iterator == null) {
			return null;
		} else if (!iterator.hasNext()) {
			return "";
		} else {
			Object first = iterator.next();
			if (!iterator.hasNext()) {
				return Objects.toString(first);
			} else {
				StringBuilder buf = new StringBuilder(256);
				if (first != null) {
					buf.append(first);
				}

				while (iterator.hasNext()) {
					buf.append(separator);
					Object obj = iterator.next();
					if (obj != null) {
						buf.append(obj);
					}
				}

				return buf.toString();
			}
		}
	}
}
