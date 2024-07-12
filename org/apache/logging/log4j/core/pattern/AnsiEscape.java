package org.apache.logging.log4j.core.pattern;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.logging.log4j.core.util.Patterns;
import org.apache.logging.log4j.util.EnglishEnums;

public enum AnsiEscape {
	CSI("\u001b["),
	SUFFIX("m"),
	SEPARATOR(";"),
	NORMAL("0"),
	BRIGHT("1"),
	DIM("2"),
	UNDERLINE("3"),
	BLINK("5"),
	REVERSE("7"),
	HIDDEN("8"),
	BLACK("30"),
	FG_BLACK("30"),
	RED("31"),
	FG_RED("31"),
	GREEN("32"),
	FG_GREEN("32"),
	YELLOW("33"),
	FG_YELLOW("33"),
	BLUE("34"),
	FG_BLUE("34"),
	MAGENTA("35"),
	FG_MAGENTA("35"),
	CYAN("36"),
	FG_CYAN("36"),
	WHITE("37"),
	FG_WHITE("37"),
	DEFAULT("39"),
	FG_DEFAULT("39"),
	BG_BLACK("40"),
	BG_RED("41"),
	BG_GREEN("42"),
	BG_YELLOW("43"),
	BG_BLUE("44"),
	BG_MAGENTA("45"),
	BG_CYAN("46"),
	BG_WHITE("47");

	private static final String DEFAULT_STYLE = CSI.getCode() + SUFFIX.getCode();
	private final String code;

	private AnsiEscape(String code) {
		this.code = code;
	}

	public static String getDefaultStyle() {
		return DEFAULT_STYLE;
	}

	public String getCode() {
		return this.code;
	}

	public static Map<String, String> createMap(String values, String[] dontEscapeKeys) {
		return createMap(values.split(Patterns.COMMA_SEPARATOR), dontEscapeKeys);
	}

	public static Map<String, String> createMap(String[] values, String[] dontEscapeKeys) {
		String[] sortedIgnoreKeys = dontEscapeKeys != null ? (String[])dontEscapeKeys.clone() : new String[0];
		Arrays.sort(sortedIgnoreKeys);
		Map<String, String> map = new HashMap();

		for (String string : values) {
			String[] keyValue = string.split(Patterns.toWhitespaceSeparator("="));
			if (keyValue.length > 1) {
				String key = keyValue[0].toUpperCase(Locale.ENGLISH);
				String value = keyValue[1];
				boolean escape = Arrays.binarySearch(sortedIgnoreKeys, key) < 0;
				map.put(key, escape ? createSequence(value.split("\\s")) : value);
			}
		}

		return map;
	}

	public static String createSequence(String... names) {
		if (names == null) {
			return getDefaultStyle();
		} else {
			StringBuilder sb = new StringBuilder(CSI.getCode());
			boolean first = true;

			for (String name : names) {
				try {
					AnsiEscape escape = EnglishEnums.valueOf(AnsiEscape.class, name.trim());
					if (!first) {
						sb.append(SEPARATOR.getCode());
					}

					first = false;
					sb.append(escape.getCode());
				} catch (Exception var8) {
				}
			}

			sb.append(SUFFIX.getCode());
			return sb.toString();
		}
	}
}
