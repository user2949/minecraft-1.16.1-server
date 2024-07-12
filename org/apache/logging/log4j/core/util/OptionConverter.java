package org.apache.logging.log4j.core.util;

import java.util.Locale;
import java.util.Properties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.Strings;

public final class OptionConverter {
	private static final Logger LOGGER = StatusLogger.getLogger();
	private static final String DELIM_START = "${";
	private static final char DELIM_STOP = '}';
	private static final int DELIM_START_LEN = 2;
	private static final int DELIM_STOP_LEN = 1;
	private static final int ONE_K = 1024;

	private OptionConverter() {
	}

	public static String[] concatenateArrays(String[] l, String[] r) {
		int len = l.length + r.length;
		String[] a = new String[len];
		System.arraycopy(l, 0, a, 0, l.length);
		System.arraycopy(r, 0, a, l.length, r.length);
		return a;
	}

	public static String convertSpecialChars(String s) {
		int len = s.length();
		StringBuilder sbuf = new StringBuilder(len);
		int i = 0;

		while (i < len) {
			char c = s.charAt(i++);
			if (c == '\\') {
				c = s.charAt(i++);
				switch (c) {
					case '"':
						c = '"';
						break;
					case '\'':
						c = '\'';
						break;
					case '\\':
						c = '\\';
						break;
					case 'b':
						c = '\b';
						break;
					case 'f':
						c = '\f';
						break;
					case 'n':
						c = '\n';
						break;
					case 'r':
						c = '\r';
						break;
					case 't':
						c = '\t';
				}
			}

			sbuf.append(c);
		}

		return sbuf.toString();
	}

	public static Object instantiateByKey(Properties props, String key, Class<?> superClass, Object defaultValue) {
		String className = findAndSubst(key, props);
		if (className == null) {
			LOGGER.error("Could not find value for key {}", key);
			return defaultValue;
		} else {
			return instantiateByClassName(className.trim(), superClass, defaultValue);
		}
	}

	public static boolean toBoolean(String value, boolean defaultValue) {
		if (value == null) {
			return defaultValue;
		} else {
			String trimmedVal = value.trim();
			if ("true".equalsIgnoreCase(trimmedVal)) {
				return true;
			} else {
				return "false".equalsIgnoreCase(trimmedVal) ? false : defaultValue;
			}
		}
	}

	public static int toInt(String value, int defaultValue) {
		if (value != null) {
			String s = value.trim();

			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException var4) {
				LOGGER.error("[{}] is not in proper int form.", s, var4);
			}
		}

		return defaultValue;
	}

	public static long toFileSize(String value, long defaultValue) {
		if (value == null) {
			return defaultValue;
		} else {
			String str = value.trim().toUpperCase(Locale.ENGLISH);
			long multiplier = 1L;
			int index;
			if ((index = str.indexOf("KB")) != -1) {
				multiplier = 1024L;
				str = str.substring(0, index);
			} else if ((index = str.indexOf("MB")) != -1) {
				multiplier = 1048576L;
				str = str.substring(0, index);
			} else if ((index = str.indexOf("GB")) != -1) {
				multiplier = 1073741824L;
				str = str.substring(0, index);
			}

			try {
				return Long.parseLong(str) * multiplier;
			} catch (NumberFormatException var8) {
				LOGGER.error("[{}] is not in proper int form.", str);
				LOGGER.error("[{}] not in expected format.", value, var8);
				return defaultValue;
			}
		}
	}

	public static String findAndSubst(String key, Properties props) {
		String value = props.getProperty(key);
		if (value == null) {
			return null;
		} else {
			try {
				return substVars(value, props);
			} catch (IllegalArgumentException var4) {
				LOGGER.error("Bad option value [{}].", value, var4);
				return value;
			}
		}
	}

	public static Object instantiateByClassName(String className, Class<?> superClass, Object defaultValue) {
		if (className != null) {
			try {
				Class<?> classObj = LoaderUtil.loadClass(className);
				if (!superClass.isAssignableFrom(classObj)) {
					LOGGER.error("A \"{}\" object is not assignable to a \"{}\" variable.", className, superClass.getName());
					LOGGER.error(
						"The class \"{}\" was loaded by [{}] whereas object of type [{}] was loaded by [{}].",
						superClass.getName(),
						superClass.getClassLoader(),
						classObj.getName()
					);
					return defaultValue;
				}

				return classObj.newInstance();
			} catch (Exception var4) {
				LOGGER.error("Could not instantiate class [{}].", className, var4);
			}
		}

		return defaultValue;
	}

	public static String substVars(String val, Properties props) throws IllegalArgumentException {
		StringBuilder sbuf = new StringBuilder();
		int i = 0;

		while (true) {
			int j = val.indexOf("${", i);
			if (j == -1) {
				if (i == 0) {
					return val;
				} else {
					sbuf.append(val.substring(i, val.length()));
					return sbuf.toString();
				}
			}

			sbuf.append(val.substring(i, j));
			int k = val.indexOf(125, j);
			if (k == -1) {
				throw new IllegalArgumentException(Strings.dquote(val) + " has no closing brace. Opening brace at position " + j + '.');
			}

			j += 2;
			String key = val.substring(j, k);
			String replacement = PropertiesUtil.getProperties().getStringProperty(key, null);
			if (replacement == null && props != null) {
				replacement = props.getProperty(key);
			}

			if (replacement != null) {
				String recursiveReplacement = substVars(replacement, props);
				sbuf.append(recursiveReplacement);
			}

			i = k + 1;
		}
	}
}
