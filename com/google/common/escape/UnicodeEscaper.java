package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;

@Beta
@GwtCompatible
public abstract class UnicodeEscaper extends Escaper {
	private static final int DEST_PAD = 32;

	protected UnicodeEscaper() {
	}

	protected abstract char[] escape(int integer);

	protected int nextEscapeIndex(CharSequence csq, int start, int end) {
		int index = start;

		while (index < end) {
			int cp = codePointAt(csq, index, end);
			if (cp < 0 || this.escape(cp) != null) {
				break;
			}

			index += Character.isSupplementaryCodePoint(cp) ? 2 : 1;
		}

		return index;
	}

	@Override
	public String escape(String string) {
		Preconditions.checkNotNull(string);
		int end = string.length();
		int index = this.nextEscapeIndex(string, 0, end);
		return index == end ? string : this.escapeSlow(string, index);
	}

	protected final String escapeSlow(String s, int index) {
		int end = s.length();
		char[] dest = Platform.charBufferFromThreadLocal();
		int destIndex = 0;
		int unescapedChunkStart = 0;

		while (index < end) {
			int cp = codePointAt(s, index, end);
			if (cp < 0) {
				throw new IllegalArgumentException("Trailing high surrogate at end of input");
			}

			char[] escaped = this.escape(cp);
			int nextIndex = index + (Character.isSupplementaryCodePoint(cp) ? 2 : 1);
			if (escaped != null) {
				int charsSkipped = index - unescapedChunkStart;
				int sizeNeeded = destIndex + charsSkipped + escaped.length;
				if (dest.length < sizeNeeded) {
					int destLength = sizeNeeded + (end - index) + 32;
					dest = growBuffer(dest, destIndex, destLength);
				}

				if (charsSkipped > 0) {
					s.getChars(unescapedChunkStart, index, dest, destIndex);
					destIndex += charsSkipped;
				}

				if (escaped.length > 0) {
					System.arraycopy(escaped, 0, dest, destIndex, escaped.length);
					destIndex += escaped.length;
				}

				unescapedChunkStart = nextIndex;
			}

			index = this.nextEscapeIndex(s, nextIndex, end);
		}

		int charsSkippedx = end - unescapedChunkStart;
		if (charsSkippedx > 0) {
			int endIndex = destIndex + charsSkippedx;
			if (dest.length < endIndex) {
				dest = growBuffer(dest, destIndex, endIndex);
			}

			s.getChars(unescapedChunkStart, end, dest, destIndex);
			destIndex = endIndex;
		}

		return new String(dest, 0, destIndex);
	}

	protected static int codePointAt(CharSequence seq, int index, int end) {
		Preconditions.checkNotNull(seq);
		if (index < end) {
			char c1 = seq.charAt(index++);
			if (c1 < '\ud800' || c1 > '\udfff') {
				return c1;
			} else if (c1 > '\udbff') {
				throw new IllegalArgumentException("Unexpected low surrogate character '" + c1 + "' with value " + c1 + " at index " + (index - 1) + " in '" + seq + "'");
			} else if (index == end) {
				return -c1;
			} else {
				char c2 = seq.charAt(index);
				if (Character.isLowSurrogate(c2)) {
					return Character.toCodePoint(c1, c2);
				} else {
					throw new IllegalArgumentException("Expected low surrogate but got char '" + c2 + "' with value " + c2 + " at index " + index + " in '" + seq + "'");
				}
			}
		} else {
			throw new IndexOutOfBoundsException("Index exceeds specified range");
		}
	}

	private static char[] growBuffer(char[] dest, int index, int size) {
		if (size < 0) {
			throw new AssertionError("Cannot increase internal buffer any further");
		} else {
			char[] copy = new char[size];
			if (index > 0) {
				System.arraycopy(dest, 0, copy, 0, index);
			}

			return copy;
		}
	}
}
