package org.apache.commons.lang3;

public class CharSequenceUtils {
	private static final int NOT_FOUND = -1;

	public static CharSequence subSequence(CharSequence cs, int start) {
		return cs == null ? null : cs.subSequence(start, cs.length());
	}

	static int indexOf(CharSequence cs, int searchChar, int start) {
		if (cs instanceof String) {
			return ((String)cs).indexOf(searchChar, start);
		} else {
			int sz = cs.length();
			if (start < 0) {
				start = 0;
			}

			for (int i = start; i < sz; i++) {
				if (cs.charAt(i) == searchChar) {
					return i;
				}
			}

			return -1;
		}
	}

	static int indexOf(CharSequence cs, CharSequence searchChar, int start) {
		return cs.toString().indexOf(searchChar.toString(), start);
	}

	static int lastIndexOf(CharSequence cs, int searchChar, int start) {
		if (cs instanceof String) {
			return ((String)cs).lastIndexOf(searchChar, start);
		} else {
			int sz = cs.length();
			if (start < 0) {
				return -1;
			} else {
				if (start >= sz) {
					start = sz - 1;
				}

				for (int i = start; i >= 0; i--) {
					if (cs.charAt(i) == searchChar) {
						return i;
					}
				}

				return -1;
			}
		}
	}

	static int lastIndexOf(CharSequence cs, CharSequence searchChar, int start) {
		return cs.toString().lastIndexOf(searchChar.toString(), start);
	}

	static char[] toCharArray(CharSequence cs) {
		if (cs instanceof String) {
			return ((String)cs).toCharArray();
		} else {
			int sz = cs.length();
			char[] array = new char[cs.length()];

			for (int i = 0; i < sz; i++) {
				array[i] = cs.charAt(i);
			}

			return array;
		}
	}

	static boolean regionMatches(CharSequence cs, boolean ignoreCase, int thisStart, CharSequence substring, int start, int length) {
		if (cs instanceof String && substring instanceof String) {
			return ((String)cs).regionMatches(ignoreCase, thisStart, (String)substring, start, length);
		} else {
			int index1 = thisStart;
			int index2 = start;
			int tmpLen = length;
			int srcLen = cs.length() - thisStart;
			int otherLen = substring.length() - start;
			if (thisStart >= 0 && start >= 0 && length >= 0) {
				if (srcLen >= length && otherLen >= length) {
					while (tmpLen-- > 0) {
						char c1 = cs.charAt(index1++);
						char c2 = substring.charAt(index2++);
						if (c1 != c2) {
							if (!ignoreCase) {
								return false;
							}

							if (Character.toUpperCase(c1) != Character.toUpperCase(c2) && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
								return false;
							}
						}
					}

					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}
}
