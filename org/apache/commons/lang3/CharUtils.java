package org.apache.commons.lang3;

public class CharUtils {
	private static final String[] CHAR_STRING_ARRAY = new String[128];
	private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	public static final char LF = '\n';
	public static final char CR = '\r';

	@Deprecated
	public static Character toCharacterObject(char ch) {
		return ch;
	}

	public static Character toCharacterObject(String str) {
		return StringUtils.isEmpty(str) ? null : str.charAt(0);
	}

	public static char toChar(Character ch) {
		if (ch == null) {
			throw new IllegalArgumentException("The Character must not be null");
		} else {
			return ch;
		}
	}

	public static char toChar(Character ch, char defaultValue) {
		return ch == null ? defaultValue : ch;
	}

	public static char toChar(String str) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException("The String must not be empty");
		} else {
			return str.charAt(0);
		}
	}

	public static char toChar(String str, char defaultValue) {
		return StringUtils.isEmpty(str) ? defaultValue : str.charAt(0);
	}

	public static int toIntValue(char ch) {
		if (!isAsciiNumeric(ch)) {
			throw new IllegalArgumentException("The character " + ch + " is not in the range '0' - '9'");
		} else {
			return ch - 48;
		}
	}

	public static int toIntValue(char ch, int defaultValue) {
		return !isAsciiNumeric(ch) ? defaultValue : ch - 48;
	}

	public static int toIntValue(Character ch) {
		if (ch == null) {
			throw new IllegalArgumentException("The character must not be null");
		} else {
			return toIntValue(ch.charValue());
		}
	}

	public static int toIntValue(Character ch, int defaultValue) {
		return ch == null ? defaultValue : toIntValue(ch.charValue(), defaultValue);
	}

	public static String toString(char ch) {
		return ch < 128 ? CHAR_STRING_ARRAY[ch] : new String(new char[]{ch});
	}

	public static String toString(Character ch) {
		return ch == null ? null : toString(ch.charValue());
	}

	public static String unicodeEscaped(char ch) {
		StringBuilder sb = new StringBuilder(6);
		sb.append("\\u");
		sb.append(HEX_DIGITS[ch >> '\f' & 15]);
		sb.append(HEX_DIGITS[ch >> '\b' & 15]);
		sb.append(HEX_DIGITS[ch >> 4 & 15]);
		sb.append(HEX_DIGITS[ch & 15]);
		return sb.toString();
	}

	public static String unicodeEscaped(Character ch) {
		return ch == null ? null : unicodeEscaped(ch.charValue());
	}

	public static boolean isAscii(char ch) {
		return ch < 128;
	}

	public static boolean isAsciiPrintable(char ch) {
		return ch >= ' ' && ch < 127;
	}

	public static boolean isAsciiControl(char ch) {
		return ch < ' ' || ch == 127;
	}

	public static boolean isAsciiAlpha(char ch) {
		return isAsciiAlphaUpper(ch) || isAsciiAlphaLower(ch);
	}

	public static boolean isAsciiAlphaUpper(char ch) {
		return ch >= 'A' && ch <= 'Z';
	}

	public static boolean isAsciiAlphaLower(char ch) {
		return ch >= 'a' && ch <= 'z';
	}

	public static boolean isAsciiNumeric(char ch) {
		return ch >= '0' && ch <= '9';
	}

	public static boolean isAsciiAlphanumeric(char ch) {
		return isAsciiAlpha(ch) || isAsciiNumeric(ch);
	}

	public static int compare(char x, char y) {
		return x - y;
	}

	static {
		for (char c = 0; c < CHAR_STRING_ARRAY.length; c++) {
			CHAR_STRING_ARRAY[c] = String.valueOf(c);
		}
	}
}
