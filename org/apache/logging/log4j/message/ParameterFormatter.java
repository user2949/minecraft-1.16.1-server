package org.apache.logging.log4j.message;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.apache.logging.log4j.util.StringBuilderFormattable;

final class ParameterFormatter {
	static final String RECURSION_PREFIX = "[...";
	static final String RECURSION_SUFFIX = "...]";
	static final String ERROR_PREFIX = "[!!!";
	static final String ERROR_SEPARATOR = "=>";
	static final String ERROR_MSG_SEPARATOR = ":";
	static final String ERROR_SUFFIX = "!!!]";
	private static final char DELIM_START = '{';
	private static final char DELIM_STOP = '}';
	private static final char ESCAPE_CHAR = '\\';
	private static ThreadLocal<SimpleDateFormat> threadLocalSimpleDateFormat = new ThreadLocal();

	private ParameterFormatter() {
	}

	static int countArgumentPlaceholders(String messagePattern) {
		if (messagePattern == null) {
			return 0;
		} else {
			int length = messagePattern.length();
			int result = 0;
			boolean isEscaped = false;

			for (int i = 0; i < length - 1; i++) {
				char curChar = messagePattern.charAt(i);
				if (curChar == '\\') {
					isEscaped = !isEscaped;
				} else if (curChar == '{') {
					if (!isEscaped && messagePattern.charAt(i + 1) == '}') {
						result++;
						i++;
					}

					isEscaped = false;
				} else {
					isEscaped = false;
				}
			}

			return result;
		}
	}

	static int countArgumentPlaceholders2(String messagePattern, int[] indices) {
		if (messagePattern == null) {
			return 0;
		} else {
			int length = messagePattern.length();
			int result = 0;
			boolean isEscaped = false;

			for (int i = 0; i < length - 1; i++) {
				char curChar = messagePattern.charAt(i);
				if (curChar == '\\') {
					isEscaped = !isEscaped;
					indices[0] = -1;
					result++;
				} else if (curChar == '{') {
					if (!isEscaped && messagePattern.charAt(i + 1) == '}') {
						indices[result] = i;
						result++;
						i++;
					}

					isEscaped = false;
				} else {
					isEscaped = false;
				}
			}

			return result;
		}
	}

	static int countArgumentPlaceholders3(char[] messagePattern, int length, int[] indices) {
		int result = 0;
		boolean isEscaped = false;

		for (int i = 0; i < length - 1; i++) {
			char curChar = messagePattern[i];
			if (curChar == '\\') {
				isEscaped = !isEscaped;
			} else if (curChar == '{') {
				if (!isEscaped && messagePattern[i + 1] == '}') {
					indices[result] = i;
					result++;
					i++;
				}

				isEscaped = false;
			} else {
				isEscaped = false;
			}
		}

		return result;
	}

	static String format(String messagePattern, Object[] arguments) {
		StringBuilder result = new StringBuilder();
		int argCount = arguments == null ? 0 : arguments.length;
		formatMessage(result, messagePattern, arguments, argCount);
		return result.toString();
	}

	static void formatMessage2(StringBuilder buffer, String messagePattern, Object[] arguments, int argCount, int[] indices) {
		if (messagePattern != null && arguments != null && argCount != 0) {
			int previous = 0;

			for (int i = 0; i < argCount; i++) {
				buffer.append(messagePattern, previous, indices[i]);
				previous = indices[i] + 2;
				recursiveDeepToString(arguments[i], buffer, null);
			}

			buffer.append(messagePattern, previous, messagePattern.length());
		} else {
			buffer.append(messagePattern);
		}
	}

	static void formatMessage3(StringBuilder buffer, char[] messagePattern, int patternLength, Object[] arguments, int argCount, int[] indices) {
		if (messagePattern != null) {
			if (arguments != null && argCount != 0) {
				int previous = 0;

				for (int i = 0; i < argCount; i++) {
					buffer.append(messagePattern, previous, indices[i]);
					previous = indices[i] + 2;
					recursiveDeepToString(arguments[i], buffer, null);
				}

				buffer.append(messagePattern, previous, patternLength);
			} else {
				buffer.append(messagePattern);
			}
		}
	}

	static void formatMessage(StringBuilder buffer, String messagePattern, Object[] arguments, int argCount) {
		if (messagePattern != null && arguments != null && argCount != 0) {
			int escapeCounter = 0;
			int currentArgument = 0;
			int i = 0;

			int len;
			for (len = messagePattern.length(); i < len - 1; i++) {
				char curChar = messagePattern.charAt(i);
				if (curChar == '\\') {
					escapeCounter++;
				} else {
					if (isDelimPair(curChar, messagePattern, i)) {
						i++;
						writeEscapedEscapeChars(escapeCounter, buffer);
						if (isOdd(escapeCounter)) {
							writeDelimPair(buffer);
						} else {
							writeArgOrDelimPair(arguments, argCount, currentArgument, buffer);
							currentArgument++;
						}
					} else {
						handleLiteralChar(buffer, escapeCounter, curChar);
					}

					escapeCounter = 0;
				}
			}

			handleRemainingCharIfAny(messagePattern, len, buffer, escapeCounter, i);
		} else {
			buffer.append(messagePattern);
		}
	}

	private static boolean isDelimPair(char curChar, String messagePattern, int curCharIndex) {
		return curChar == '{' && messagePattern.charAt(curCharIndex + 1) == '}';
	}

	private static void handleRemainingCharIfAny(String messagePattern, int len, StringBuilder buffer, int escapeCounter, int i) {
		if (i == len - 1) {
			char curChar = messagePattern.charAt(i);
			handleLastChar(buffer, escapeCounter, curChar);
		}
	}

	private static void handleLastChar(StringBuilder buffer, int escapeCounter, char curChar) {
		if (curChar == '\\') {
			writeUnescapedEscapeChars(escapeCounter + 1, buffer);
		} else {
			handleLiteralChar(buffer, escapeCounter, curChar);
		}
	}

	private static void handleLiteralChar(StringBuilder buffer, int escapeCounter, char curChar) {
		writeUnescapedEscapeChars(escapeCounter, buffer);
		buffer.append(curChar);
	}

	private static void writeDelimPair(StringBuilder buffer) {
		buffer.append('{');
		buffer.append('}');
	}

	private static boolean isOdd(int number) {
		return (number & 1) == 1;
	}

	private static void writeEscapedEscapeChars(int escapeCounter, StringBuilder buffer) {
		int escapedEscapes = escapeCounter >> 1;
		writeUnescapedEscapeChars(escapedEscapes, buffer);
	}

	private static void writeUnescapedEscapeChars(int escapeCounter, StringBuilder buffer) {
		while (escapeCounter > 0) {
			buffer.append('\\');
			escapeCounter--;
		}
	}

	private static void writeArgOrDelimPair(Object[] arguments, int argCount, int currentArgument, StringBuilder buffer) {
		if (currentArgument < argCount) {
			recursiveDeepToString(arguments[currentArgument], buffer, null);
		} else {
			writeDelimPair(buffer);
		}
	}

	static String deepToString(Object o) {
		if (o == null) {
			return null;
		} else if (o instanceof String) {
			return (String)o;
		} else {
			StringBuilder str = new StringBuilder();
			Set<String> dejaVu = new HashSet();
			recursiveDeepToString(o, str, dejaVu);
			return str.toString();
		}
	}

	private static void recursiveDeepToString(Object o, StringBuilder str, Set<String> dejaVu) {
		if (!appendSpecialTypes(o, str)) {
			if (isMaybeRecursive(o)) {
				appendPotentiallyRecursiveValue(o, str, dejaVu);
			} else {
				tryObjectToString(o, str);
			}
		}
	}

	private static boolean appendSpecialTypes(Object o, StringBuilder str) {
		if (o == null || o instanceof String) {
			str.append((String)o);
			return true;
		} else if (o instanceof CharSequence) {
			str.append((CharSequence)o);
			return true;
		} else if (o instanceof StringBuilderFormattable) {
			((StringBuilderFormattable)o).formatTo(str);
			return true;
		} else if (o instanceof Integer) {
			str.append((Integer)o);
			return true;
		} else if (o instanceof Long) {
			str.append((Long)o);
			return true;
		} else if (o instanceof Double) {
			str.append((Double)o);
			return true;
		} else if (o instanceof Boolean) {
			str.append((Boolean)o);
			return true;
		} else if (o instanceof Character) {
			str.append((Character)o);
			return true;
		} else if (o instanceof Short) {
			str.append((Short)o);
			return true;
		} else if (o instanceof Float) {
			str.append((Float)o);
			return true;
		} else {
			return appendDate(o, str);
		}
	}

	private static boolean appendDate(Object o, StringBuilder str) {
		if (!(o instanceof Date)) {
			return false;
		} else {
			Date date = (Date)o;
			SimpleDateFormat format = getSimpleDateFormat();
			str.append(format.format(date));
			return true;
		}
	}

	private static SimpleDateFormat getSimpleDateFormat() {
		SimpleDateFormat result = (SimpleDateFormat)threadLocalSimpleDateFormat.get();
		if (result == null) {
			result = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			threadLocalSimpleDateFormat.set(result);
		}

		return result;
	}

	private static boolean isMaybeRecursive(Object o) {
		return o.getClass().isArray() || o instanceof Map || o instanceof Collection;
	}

	private static void appendPotentiallyRecursiveValue(Object o, StringBuilder str, Set<String> dejaVu) {
		Class<?> oClass = o.getClass();
		if (oClass.isArray()) {
			appendArray(o, str, dejaVu, oClass);
		} else if (o instanceof Map) {
			appendMap(o, str, dejaVu);
		} else if (o instanceof Collection) {
			appendCollection(o, str, dejaVu);
		}
	}

	private static void appendArray(Object o, StringBuilder str, Set<String> dejaVu, Class<?> oClass) {
		if (oClass == byte[].class) {
			str.append(Arrays.toString((byte[])o));
		} else if (oClass == short[].class) {
			str.append(Arrays.toString((short[])o));
		} else if (oClass == int[].class) {
			str.append(Arrays.toString((int[])o));
		} else if (oClass == long[].class) {
			str.append(Arrays.toString((long[])o));
		} else if (oClass == float[].class) {
			str.append(Arrays.toString((float[])o));
		} else if (oClass == double[].class) {
			str.append(Arrays.toString((double[])o));
		} else if (oClass == boolean[].class) {
			str.append(Arrays.toString((boolean[])o));
		} else if (oClass == char[].class) {
			str.append(Arrays.toString((char[])o));
		} else {
			if (dejaVu == null) {
				dejaVu = new HashSet();
			}

			String id = identityToString(o);
			if (dejaVu.contains(id)) {
				str.append("[...").append(id).append("...]");
			} else {
				dejaVu.add(id);
				Object[] oArray = (Object[])o;
				str.append('[');
				boolean first = true;

				for (Object current : oArray) {
					if (first) {
						first = false;
					} else {
						str.append(", ");
					}

					recursiveDeepToString(current, str, new HashSet(dejaVu));
				}

				str.append(']');
			}
		}
	}

	private static void appendMap(Object o, StringBuilder str, Set<String> dejaVu) {
		if (dejaVu == null) {
			dejaVu = new HashSet();
		}

		String id = identityToString(o);
		if (dejaVu.contains(id)) {
			str.append("[...").append(id).append("...]");
		} else {
			dejaVu.add(id);
			Map<?, ?> oMap = (Map<?, ?>)o;
			str.append('{');
			boolean isFirst = true;

			for (Object o1 : oMap.entrySet()) {
				Entry<?, ?> current = (Entry<?, ?>)o1;
				if (isFirst) {
					isFirst = false;
				} else {
					str.append(", ");
				}

				Object key = current.getKey();
				Object value = current.getValue();
				recursiveDeepToString(key, str, new HashSet(dejaVu));
				str.append('=');
				recursiveDeepToString(value, str, new HashSet(dejaVu));
			}

			str.append('}');
		}
	}

	private static void appendCollection(Object o, StringBuilder str, Set<String> dejaVu) {
		if (dejaVu == null) {
			dejaVu = new HashSet();
		}

		String id = identityToString(o);
		if (dejaVu.contains(id)) {
			str.append("[...").append(id).append("...]");
		} else {
			dejaVu.add(id);
			Collection<?> oCol = (Collection<?>)o;
			str.append('[');
			boolean isFirst = true;

			for (Object anOCol : oCol) {
				if (isFirst) {
					isFirst = false;
				} else {
					str.append(", ");
				}

				recursiveDeepToString(anOCol, str, new HashSet(dejaVu));
			}

			str.append(']');
		}
	}

	private static void tryObjectToString(Object o, StringBuilder str) {
		try {
			str.append(o.toString());
		} catch (Throwable var3) {
			handleErrorInObjectToString(o, str, var3);
		}
	}

	private static void handleErrorInObjectToString(Object o, StringBuilder str, Throwable t) {
		str.append("[!!!");
		str.append(identityToString(o));
		str.append("=>");
		String msg = t.getMessage();
		String className = t.getClass().getName();
		str.append(className);
		if (!className.equals(msg)) {
			str.append(":");
			str.append(msg);
		}

		str.append("!!!]");
	}

	static String identityToString(Object obj) {
		return obj == null ? null : obj.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(obj));
	}
}
