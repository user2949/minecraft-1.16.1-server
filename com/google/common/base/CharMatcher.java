package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import java.util.Arrays;
import java.util.BitSet;

@GwtCompatible(
	emulated = true
)
public abstract class CharMatcher implements Predicate<Character> {
	@Deprecated
	public static final CharMatcher WHITESPACE = whitespace();
	@Deprecated
	public static final CharMatcher BREAKING_WHITESPACE = breakingWhitespace();
	@Deprecated
	public static final CharMatcher ASCII = ascii();
	@Deprecated
	public static final CharMatcher DIGIT = digit();
	@Deprecated
	public static final CharMatcher JAVA_DIGIT = javaDigit();
	@Deprecated
	public static final CharMatcher JAVA_LETTER = javaLetter();
	@Deprecated
	public static final CharMatcher JAVA_LETTER_OR_DIGIT = javaLetterOrDigit();
	@Deprecated
	public static final CharMatcher JAVA_UPPER_CASE = javaUpperCase();
	@Deprecated
	public static final CharMatcher JAVA_LOWER_CASE = javaLowerCase();
	@Deprecated
	public static final CharMatcher JAVA_ISO_CONTROL = javaIsoControl();
	@Deprecated
	public static final CharMatcher INVISIBLE = invisible();
	@Deprecated
	public static final CharMatcher SINGLE_WIDTH = singleWidth();
	@Deprecated
	public static final CharMatcher ANY = any();
	@Deprecated
	public static final CharMatcher NONE = none();
	private static final int DISTINCT_CHARS = 65536;

	public static CharMatcher any() {
		return CharMatcher.Any.INSTANCE;
	}

	public static CharMatcher none() {
		return CharMatcher.None.INSTANCE;
	}

	public static CharMatcher whitespace() {
		return CharMatcher.Whitespace.INSTANCE;
	}

	public static CharMatcher breakingWhitespace() {
		return CharMatcher.BreakingWhitespace.INSTANCE;
	}

	public static CharMatcher ascii() {
		return CharMatcher.Ascii.INSTANCE;
	}

	public static CharMatcher digit() {
		return CharMatcher.Digit.INSTANCE;
	}

	public static CharMatcher javaDigit() {
		return CharMatcher.JavaDigit.INSTANCE;
	}

	public static CharMatcher javaLetter() {
		return CharMatcher.JavaLetter.INSTANCE;
	}

	public static CharMatcher javaLetterOrDigit() {
		return CharMatcher.JavaLetterOrDigit.INSTANCE;
	}

	public static CharMatcher javaUpperCase() {
		return CharMatcher.JavaUpperCase.INSTANCE;
	}

	public static CharMatcher javaLowerCase() {
		return CharMatcher.JavaLowerCase.INSTANCE;
	}

	public static CharMatcher javaIsoControl() {
		return CharMatcher.JavaIsoControl.INSTANCE;
	}

	public static CharMatcher invisible() {
		return CharMatcher.Invisible.INSTANCE;
	}

	public static CharMatcher singleWidth() {
		return CharMatcher.SingleWidth.INSTANCE;
	}

	public static CharMatcher is(char match) {
		return new CharMatcher.Is(match);
	}

	public static CharMatcher isNot(char match) {
		return new CharMatcher.IsNot(match);
	}

	public static CharMatcher anyOf(CharSequence sequence) {
		switch (sequence.length()) {
			case 0:
				return none();
			case 1:
				return is(sequence.charAt(0));
			case 2:
				return isEither(sequence.charAt(0), sequence.charAt(1));
			default:
				return new CharMatcher.AnyOf(sequence);
		}
	}

	public static CharMatcher noneOf(CharSequence sequence) {
		return anyOf(sequence).negate();
	}

	public static CharMatcher inRange(char startInclusive, char endInclusive) {
		return new CharMatcher.InRange(startInclusive, endInclusive);
	}

	public static CharMatcher forPredicate(Predicate<? super Character> predicate) {
		return (CharMatcher)(predicate instanceof CharMatcher ? (CharMatcher)predicate : new CharMatcher.ForPredicate(predicate));
	}

	protected CharMatcher() {
	}

	public abstract boolean matches(char character);

	public CharMatcher negate() {
		return new CharMatcher.Negated(this);
	}

	public CharMatcher and(CharMatcher other) {
		return new CharMatcher.And(this, other);
	}

	public CharMatcher or(CharMatcher other) {
		return new CharMatcher.Or(this, other);
	}

	public CharMatcher precomputed() {
		return Platform.precomputeCharMatcher(this);
	}

	@GwtIncompatible
	CharMatcher precomputedInternal() {
		BitSet table = new BitSet();
		this.setBits(table);
		int totalCharacters = table.cardinality();
		if (totalCharacters * 2 <= 65536) {
			return precomputedPositive(totalCharacters, table, this.toString());
		} else {
			table.flip(0, 65536);
			int negatedCharacters = 65536 - totalCharacters;
			String suffix = ".negate()";
			final String description = this.toString();
			String negatedDescription = description.endsWith(suffix) ? description.substring(0, description.length() - suffix.length()) : description + suffix;
			return new CharMatcher.NegatedFastMatcher(precomputedPositive(negatedCharacters, table, negatedDescription)) {
				@Override
				public String toString() {
					return description;
				}
			};
		}
	}

	@GwtIncompatible
	private static CharMatcher precomputedPositive(int totalCharacters, BitSet table, String description) {
		switch (totalCharacters) {
			case 0:
				return none();
			case 1:
				return is((char)table.nextSetBit(0));
			case 2:
				char c1 = (char)table.nextSetBit(0);
				char c2 = (char)table.nextSetBit(c1 + 1);
				return isEither(c1, c2);
			default:
				return (CharMatcher)(isSmall(totalCharacters, table.length())
					? SmallCharMatcher.from(table, description)
					: new CharMatcher.BitSetMatcher(table, description));
		}
	}

	@GwtIncompatible
	private static boolean isSmall(int totalCharacters, int tableLength) {
		return totalCharacters <= 1023 && tableLength > totalCharacters * 4 * 16;
	}

	@GwtIncompatible
	void setBits(BitSet table) {
		for (int c = 65535; c >= 0; c--) {
			if (this.matches((char)c)) {
				table.set(c);
			}
		}
	}

	public boolean matchesAnyOf(CharSequence sequence) {
		return !this.matchesNoneOf(sequence);
	}

	public boolean matchesAllOf(CharSequence sequence) {
		for (int i = sequence.length() - 1; i >= 0; i--) {
			if (!this.matches(sequence.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	public boolean matchesNoneOf(CharSequence sequence) {
		return this.indexIn(sequence) == -1;
	}

	public int indexIn(CharSequence sequence) {
		return this.indexIn(sequence, 0);
	}

	public int indexIn(CharSequence sequence, int start) {
		int length = sequence.length();
		Preconditions.checkPositionIndex(start, length);

		for (int i = start; i < length; i++) {
			if (this.matches(sequence.charAt(i))) {
				return i;
			}
		}

		return -1;
	}

	public int lastIndexIn(CharSequence sequence) {
		for (int i = sequence.length() - 1; i >= 0; i--) {
			if (this.matches(sequence.charAt(i))) {
				return i;
			}
		}

		return -1;
	}

	public int countIn(CharSequence sequence) {
		int count = 0;

		for (int i = 0; i < sequence.length(); i++) {
			if (this.matches(sequence.charAt(i))) {
				count++;
			}
		}

		return count;
	}

	public String removeFrom(CharSequence sequence) {
		String string = sequence.toString();
		int pos = this.indexIn(string);
		if (pos == -1) {
			return string;
		} else {
			char[] chars = string.toCharArray();
			int spread = 1;

			label25:
			while (true) {
				pos++;

				while (pos != chars.length) {
					if (this.matches(chars[pos])) {
						spread++;
						continue label25;
					}

					chars[pos - spread] = chars[pos];
					pos++;
				}

				return new String(chars, 0, pos - spread);
			}
		}
	}

	public String retainFrom(CharSequence sequence) {
		return this.negate().removeFrom(sequence);
	}

	public String replaceFrom(CharSequence sequence, char replacement) {
		String string = sequence.toString();
		int pos = this.indexIn(string);
		if (pos == -1) {
			return string;
		} else {
			char[] chars = string.toCharArray();
			chars[pos] = replacement;

			for (int i = pos + 1; i < chars.length; i++) {
				if (this.matches(chars[i])) {
					chars[i] = replacement;
				}
			}

			return new String(chars);
		}
	}

	public String replaceFrom(CharSequence sequence, CharSequence replacement) {
		int replacementLen = replacement.length();
		if (replacementLen == 0) {
			return this.removeFrom(sequence);
		} else if (replacementLen == 1) {
			return this.replaceFrom(sequence, replacement.charAt(0));
		} else {
			String string = sequence.toString();
			int pos = this.indexIn(string);
			if (pos == -1) {
				return string;
			} else {
				int len = string.length();
				StringBuilder buf = new StringBuilder(len * 3 / 2 + 16);
				int oldpos = 0;

				do {
					buf.append(string, oldpos, pos);
					buf.append(replacement);
					oldpos = pos + 1;
					pos = this.indexIn(string, oldpos);
				} while (pos != -1);

				buf.append(string, oldpos, len);
				return buf.toString();
			}
		}
	}

	public String trimFrom(CharSequence sequence) {
		int len = sequence.length();
		int first = 0;

		while (first < len && this.matches(sequence.charAt(first))) {
			first++;
		}

		int last = len - 1;

		while (last > first && this.matches(sequence.charAt(last))) {
			last--;
		}

		return sequence.subSequence(first, last + 1).toString();
	}

	public String trimLeadingFrom(CharSequence sequence) {
		int len = sequence.length();

		for (int first = 0; first < len; first++) {
			if (!this.matches(sequence.charAt(first))) {
				return sequence.subSequence(first, len).toString();
			}
		}

		return "";
	}

	public String trimTrailingFrom(CharSequence sequence) {
		int len = sequence.length();

		for (int last = len - 1; last >= 0; last--) {
			if (!this.matches(sequence.charAt(last))) {
				return sequence.subSequence(0, last + 1).toString();
			}
		}

		return "";
	}

	public String collapseFrom(CharSequence sequence, char replacement) {
		int len = sequence.length();

		for (int i = 0; i < len; i++) {
			char c = sequence.charAt(i);
			if (this.matches(c)) {
				if (c != replacement || i != len - 1 && this.matches(sequence.charAt(i + 1))) {
					StringBuilder builder = new StringBuilder(len).append(sequence, 0, i).append(replacement);
					return this.finishCollapseFrom(sequence, i + 1, len, replacement, builder, true);
				}

				i++;
			}
		}

		return sequence.toString();
	}

	public String trimAndCollapseFrom(CharSequence sequence, char replacement) {
		int len = sequence.length();
		int first = 0;
		int last = len - 1;

		while (first < len && this.matches(sequence.charAt(first))) {
			first++;
		}

		while (last > first && this.matches(sequence.charAt(last))) {
			last--;
		}

		return first == 0 && last == len - 1
			? this.collapseFrom(sequence, replacement)
			: this.finishCollapseFrom(sequence, first, last + 1, replacement, new StringBuilder(last + 1 - first), false);
	}

	private String finishCollapseFrom(CharSequence sequence, int start, int end, char replacement, StringBuilder builder, boolean inMatchingGroup) {
		for (int i = start; i < end; i++) {
			char c = sequence.charAt(i);
			if (this.matches(c)) {
				if (!inMatchingGroup) {
					builder.append(replacement);
					inMatchingGroup = true;
				}
			} else {
				builder.append(c);
				inMatchingGroup = false;
			}
		}

		return builder.toString();
	}

	@Deprecated
	public boolean apply(Character character) {
		return this.matches(character);
	}

	public String toString() {
		return super.toString();
	}

	private static String showCharacter(char c) {
		String hex = "0123456789ABCDEF";
		char[] tmp = new char[]{'\\', 'u', '\u0000', '\u0000', '\u0000', '\u0000'};

		for (int i = 0; i < 4; i++) {
			tmp[5 - i] = hex.charAt(c & 15);
			c = (char)(c >> 4);
		}

		return String.copyValueOf(tmp);
	}

	private static CharMatcher.IsEither isEither(char c1, char c2) {
		return new CharMatcher.IsEither(c1, c2);
	}

	private static final class And extends CharMatcher {
		final CharMatcher first;
		final CharMatcher second;

		And(CharMatcher a, CharMatcher b) {
			this.first = Preconditions.checkNotNull(a);
			this.second = Preconditions.checkNotNull(b);
		}

		@Override
		public boolean matches(char c) {
			return this.first.matches(c) && this.second.matches(c);
		}

		@GwtIncompatible
		@Override
		void setBits(BitSet table) {
			BitSet tmp1 = new BitSet();
			this.first.setBits(tmp1);
			BitSet tmp2 = new BitSet();
			this.second.setBits(tmp2);
			tmp1.and(tmp2);
			table.or(tmp1);
		}

		@Override
		public String toString() {
			return "CharMatcher.and(" + this.first + ", " + this.second + ")";
		}
	}

	private static final class Any extends CharMatcher.NamedFastMatcher {
		static final CharMatcher.Any INSTANCE = new CharMatcher.Any();

		private Any() {
			super("CharMatcher.any()");
		}

		@Override
		public boolean matches(char c) {
			return true;
		}

		@Override
		public int indexIn(CharSequence sequence) {
			return sequence.length() == 0 ? -1 : 0;
		}

		@Override
		public int indexIn(CharSequence sequence, int start) {
			int length = sequence.length();
			Preconditions.checkPositionIndex(start, length);
			return start == length ? -1 : start;
		}

		@Override
		public int lastIndexIn(CharSequence sequence) {
			return sequence.length() - 1;
		}

		@Override
		public boolean matchesAllOf(CharSequence sequence) {
			Preconditions.checkNotNull(sequence);
			return true;
		}

		@Override
		public boolean matchesNoneOf(CharSequence sequence) {
			return sequence.length() == 0;
		}

		@Override
		public String removeFrom(CharSequence sequence) {
			Preconditions.checkNotNull(sequence);
			return "";
		}

		@Override
		public String replaceFrom(CharSequence sequence, char replacement) {
			char[] array = new char[sequence.length()];
			Arrays.fill(array, replacement);
			return new String(array);
		}

		@Override
		public String replaceFrom(CharSequence sequence, CharSequence replacement) {
			StringBuilder result = new StringBuilder(sequence.length() * replacement.length());

			for (int i = 0; i < sequence.length(); i++) {
				result.append(replacement);
			}

			return result.toString();
		}

		@Override
		public String collapseFrom(CharSequence sequence, char replacement) {
			return sequence.length() == 0 ? "" : String.valueOf(replacement);
		}

		@Override
		public String trimFrom(CharSequence sequence) {
			Preconditions.checkNotNull(sequence);
			return "";
		}

		@Override
		public int countIn(CharSequence sequence) {
			return sequence.length();
		}

		@Override
		public CharMatcher and(CharMatcher other) {
			return Preconditions.checkNotNull(other);
		}

		@Override
		public CharMatcher or(CharMatcher other) {
			Preconditions.checkNotNull(other);
			return this;
		}

		@Override
		public CharMatcher negate() {
			return none();
		}
	}

	private static final class AnyOf extends CharMatcher {
		private final char[] chars;

		public AnyOf(CharSequence chars) {
			this.chars = chars.toString().toCharArray();
			Arrays.sort(this.chars);
		}

		@Override
		public boolean matches(char c) {
			return Arrays.binarySearch(this.chars, c) >= 0;
		}

		@GwtIncompatible
		@Override
		void setBits(BitSet table) {
			for (char c : this.chars) {
				table.set(c);
			}
		}

		@Override
		public String toString() {
			StringBuilder description = new StringBuilder("CharMatcher.anyOf(\"");

			for (char c : this.chars) {
				description.append(CharMatcher.showCharacter(c));
			}

			description.append("\")");
			return description.toString();
		}
	}

	private static final class Ascii extends CharMatcher.NamedFastMatcher {
		static final CharMatcher.Ascii INSTANCE = new CharMatcher.Ascii();

		Ascii() {
			super("CharMatcher.ascii()");
		}

		@Override
		public boolean matches(char c) {
			return c <= 127;
		}
	}

	@GwtIncompatible
	private static final class BitSetMatcher extends CharMatcher.NamedFastMatcher {
		private final BitSet table;

		private BitSetMatcher(BitSet table, String description) {
			super(description);
			if (table.length() + 64 < table.size()) {
				table = (BitSet)table.clone();
			}

			this.table = table;
		}

		@Override
		public boolean matches(char c) {
			return this.table.get(c);
		}

		@Override
		void setBits(BitSet bitSet) {
			bitSet.or(this.table);
		}
	}

	private static final class BreakingWhitespace extends CharMatcher {
		static final CharMatcher INSTANCE = new CharMatcher.BreakingWhitespace();

		@Override
		public boolean matches(char c) {
			switch (c) {
				case '\t':
				case '\n':
				case '\u000b':
				case '\f':
				case '\r':
				case ' ':
				case '\u0085':
				case ' ':
				case '\u2028':
				case '\u2029':
				case ' ':
				case '　':
					return true;
				case ' ':
					return false;
				default:
					return c >= 8192 && c <= 8202;
			}
		}

		@Override
		public String toString() {
			return "CharMatcher.breakingWhitespace()";
		}
	}

	private static final class Digit extends CharMatcher.RangesMatcher {
		private static final String ZEROES = "0٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０";
		static final CharMatcher.Digit INSTANCE = new CharMatcher.Digit();

		private static char[] zeroes() {
			return "0٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０".toCharArray();
		}

		private static char[] nines() {
			char[] nines = new char["0٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０".length()];

			for (int i = 0; i < "0٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０".length(); i++) {
				nines[i] = (char)("0٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０".charAt(i) + '\t');
			}

			return nines;
		}

		private Digit() {
			super("CharMatcher.digit()", zeroes(), nines());
		}
	}

	abstract static class FastMatcher extends CharMatcher {
		@Override
		public final CharMatcher precomputed() {
			return this;
		}

		@Override
		public CharMatcher negate() {
			return new CharMatcher.NegatedFastMatcher(this);
		}
	}

	private static final class ForPredicate extends CharMatcher {
		private final Predicate<? super Character> predicate;

		ForPredicate(Predicate<? super Character> predicate) {
			this.predicate = Preconditions.checkNotNull(predicate);
		}

		@Override
		public boolean matches(char c) {
			return this.predicate.apply(c);
		}

		@Override
		public boolean apply(Character character) {
			return this.predicate.apply(Preconditions.checkNotNull(character));
		}

		@Override
		public String toString() {
			return "CharMatcher.forPredicate(" + this.predicate + ")";
		}
	}

	private static final class InRange extends CharMatcher.FastMatcher {
		private final char startInclusive;
		private final char endInclusive;

		InRange(char startInclusive, char endInclusive) {
			Preconditions.checkArgument(endInclusive >= startInclusive);
			this.startInclusive = startInclusive;
			this.endInclusive = endInclusive;
		}

		@Override
		public boolean matches(char c) {
			return this.startInclusive <= c && c <= this.endInclusive;
		}

		@GwtIncompatible
		@Override
		void setBits(BitSet table) {
			table.set(this.startInclusive, this.endInclusive + 1);
		}

		@Override
		public String toString() {
			return "CharMatcher.inRange('" + CharMatcher.showCharacter(this.startInclusive) + "', '" + CharMatcher.showCharacter(this.endInclusive) + "')";
		}
	}

	private static final class Invisible extends CharMatcher.RangesMatcher {
		private static final String RANGE_STARTS = "\u0000\u007f\u00ad\u0600\u061c\u06dd\u070f \u180e \u2028 \u2066\u2067\u2068\u2069\u206a　\ud800\ufeff\ufff9\ufffa";
		private static final String RANGE_ENDS = "  \u00ad\u0604\u061c\u06dd\u070f \u180e\u200f \u2064\u2066\u2067\u2068\u2069\u206f　\uf8ff\ufeff\ufff9\ufffb";
		static final CharMatcher.Invisible INSTANCE = new CharMatcher.Invisible();

		private Invisible() {
			super(
				"CharMatcher.invisible()",
				"\u0000\u007f\u00ad\u0600\u061c\u06dd\u070f \u180e \u2028 \u2066\u2067\u2068\u2069\u206a　\ud800\ufeff\ufff9\ufffa".toCharArray(),
				"  \u00ad\u0604\u061c\u06dd\u070f \u180e\u200f \u2064\u2066\u2067\u2068\u2069\u206f　\uf8ff\ufeff\ufff9\ufffb".toCharArray()
			);
		}
	}

	private static final class Is extends CharMatcher.FastMatcher {
		private final char match;

		Is(char match) {
			this.match = match;
		}

		@Override
		public boolean matches(char c) {
			return c == this.match;
		}

		@Override
		public String replaceFrom(CharSequence sequence, char replacement) {
			return sequence.toString().replace(this.match, replacement);
		}

		@Override
		public CharMatcher and(CharMatcher other) {
			return (CharMatcher)(other.matches(this.match) ? this : none());
		}

		@Override
		public CharMatcher or(CharMatcher other) {
			return other.matches(this.match) ? other : super.or(other);
		}

		@Override
		public CharMatcher negate() {
			return isNot(this.match);
		}

		@GwtIncompatible
		@Override
		void setBits(BitSet table) {
			table.set(this.match);
		}

		@Override
		public String toString() {
			return "CharMatcher.is('" + CharMatcher.showCharacter(this.match) + "')";
		}
	}

	private static final class IsEither extends CharMatcher.FastMatcher {
		private final char match1;
		private final char match2;

		IsEither(char match1, char match2) {
			this.match1 = match1;
			this.match2 = match2;
		}

		@Override
		public boolean matches(char c) {
			return c == this.match1 || c == this.match2;
		}

		@GwtIncompatible
		@Override
		void setBits(BitSet table) {
			table.set(this.match1);
			table.set(this.match2);
		}

		@Override
		public String toString() {
			return "CharMatcher.anyOf(\"" + CharMatcher.showCharacter(this.match1) + CharMatcher.showCharacter(this.match2) + "\")";
		}
	}

	private static final class IsNot extends CharMatcher.FastMatcher {
		private final char match;

		IsNot(char match) {
			this.match = match;
		}

		@Override
		public boolean matches(char c) {
			return c != this.match;
		}

		@Override
		public CharMatcher and(CharMatcher other) {
			return other.matches(this.match) ? super.and(other) : other;
		}

		@Override
		public CharMatcher or(CharMatcher other) {
			return (CharMatcher)(other.matches(this.match) ? any() : this);
		}

		@GwtIncompatible
		@Override
		void setBits(BitSet table) {
			table.set(0, this.match);
			table.set(this.match + 1, 65536);
		}

		@Override
		public CharMatcher negate() {
			return is(this.match);
		}

		@Override
		public String toString() {
			return "CharMatcher.isNot('" + CharMatcher.showCharacter(this.match) + "')";
		}
	}

	private static final class JavaDigit extends CharMatcher {
		static final CharMatcher.JavaDigit INSTANCE = new CharMatcher.JavaDigit();

		@Override
		public boolean matches(char c) {
			return Character.isDigit(c);
		}

		@Override
		public String toString() {
			return "CharMatcher.javaDigit()";
		}
	}

	private static final class JavaIsoControl extends CharMatcher.NamedFastMatcher {
		static final CharMatcher.JavaIsoControl INSTANCE = new CharMatcher.JavaIsoControl();

		private JavaIsoControl() {
			super("CharMatcher.javaIsoControl()");
		}

		@Override
		public boolean matches(char c) {
			return c <= 31 || c >= 127 && c <= 159;
		}
	}

	private static final class JavaLetter extends CharMatcher {
		static final CharMatcher.JavaLetter INSTANCE = new CharMatcher.JavaLetter();

		@Override
		public boolean matches(char c) {
			return Character.isLetter(c);
		}

		@Override
		public String toString() {
			return "CharMatcher.javaLetter()";
		}
	}

	private static final class JavaLetterOrDigit extends CharMatcher {
		static final CharMatcher.JavaLetterOrDigit INSTANCE = new CharMatcher.JavaLetterOrDigit();

		@Override
		public boolean matches(char c) {
			return Character.isLetterOrDigit(c);
		}

		@Override
		public String toString() {
			return "CharMatcher.javaLetterOrDigit()";
		}
	}

	private static final class JavaLowerCase extends CharMatcher {
		static final CharMatcher.JavaLowerCase INSTANCE = new CharMatcher.JavaLowerCase();

		@Override
		public boolean matches(char c) {
			return Character.isLowerCase(c);
		}

		@Override
		public String toString() {
			return "CharMatcher.javaLowerCase()";
		}
	}

	private static final class JavaUpperCase extends CharMatcher {
		static final CharMatcher.JavaUpperCase INSTANCE = new CharMatcher.JavaUpperCase();

		@Override
		public boolean matches(char c) {
			return Character.isUpperCase(c);
		}

		@Override
		public String toString() {
			return "CharMatcher.javaUpperCase()";
		}
	}

	abstract static class NamedFastMatcher extends CharMatcher.FastMatcher {
		private final String description;

		NamedFastMatcher(String description) {
			this.description = Preconditions.checkNotNull(description);
		}

		@Override
		public final String toString() {
			return this.description;
		}
	}

	private static class Negated extends CharMatcher {
		final CharMatcher original;

		Negated(CharMatcher original) {
			this.original = Preconditions.checkNotNull(original);
		}

		@Override
		public boolean matches(char c) {
			return !this.original.matches(c);
		}

		@Override
		public boolean matchesAllOf(CharSequence sequence) {
			return this.original.matchesNoneOf(sequence);
		}

		@Override
		public boolean matchesNoneOf(CharSequence sequence) {
			return this.original.matchesAllOf(sequence);
		}

		@Override
		public int countIn(CharSequence sequence) {
			return sequence.length() - this.original.countIn(sequence);
		}

		@GwtIncompatible
		@Override
		void setBits(BitSet table) {
			BitSet tmp = new BitSet();
			this.original.setBits(tmp);
			tmp.flip(0, 65536);
			table.or(tmp);
		}

		@Override
		public CharMatcher negate() {
			return this.original;
		}

		@Override
		public String toString() {
			return this.original + ".negate()";
		}
	}

	static class NegatedFastMatcher extends CharMatcher.Negated {
		NegatedFastMatcher(CharMatcher original) {
			super(original);
		}

		@Override
		public final CharMatcher precomputed() {
			return this;
		}
	}

	private static final class None extends CharMatcher.NamedFastMatcher {
		static final CharMatcher.None INSTANCE = new CharMatcher.None();

		private None() {
			super("CharMatcher.none()");
		}

		@Override
		public boolean matches(char c) {
			return false;
		}

		@Override
		public int indexIn(CharSequence sequence) {
			Preconditions.checkNotNull(sequence);
			return -1;
		}

		@Override
		public int indexIn(CharSequence sequence, int start) {
			int length = sequence.length();
			Preconditions.checkPositionIndex(start, length);
			return -1;
		}

		@Override
		public int lastIndexIn(CharSequence sequence) {
			Preconditions.checkNotNull(sequence);
			return -1;
		}

		@Override
		public boolean matchesAllOf(CharSequence sequence) {
			return sequence.length() == 0;
		}

		@Override
		public boolean matchesNoneOf(CharSequence sequence) {
			Preconditions.checkNotNull(sequence);
			return true;
		}

		@Override
		public String removeFrom(CharSequence sequence) {
			return sequence.toString();
		}

		@Override
		public String replaceFrom(CharSequence sequence, char replacement) {
			return sequence.toString();
		}

		@Override
		public String replaceFrom(CharSequence sequence, CharSequence replacement) {
			Preconditions.checkNotNull(replacement);
			return sequence.toString();
		}

		@Override
		public String collapseFrom(CharSequence sequence, char replacement) {
			return sequence.toString();
		}

		@Override
		public String trimFrom(CharSequence sequence) {
			return sequence.toString();
		}

		@Override
		public String trimLeadingFrom(CharSequence sequence) {
			return sequence.toString();
		}

		@Override
		public String trimTrailingFrom(CharSequence sequence) {
			return sequence.toString();
		}

		@Override
		public int countIn(CharSequence sequence) {
			Preconditions.checkNotNull(sequence);
			return 0;
		}

		@Override
		public CharMatcher and(CharMatcher other) {
			Preconditions.checkNotNull(other);
			return this;
		}

		@Override
		public CharMatcher or(CharMatcher other) {
			return Preconditions.checkNotNull(other);
		}

		@Override
		public CharMatcher negate() {
			return any();
		}
	}

	private static final class Or extends CharMatcher {
		final CharMatcher first;
		final CharMatcher second;

		Or(CharMatcher a, CharMatcher b) {
			this.first = Preconditions.checkNotNull(a);
			this.second = Preconditions.checkNotNull(b);
		}

		@GwtIncompatible
		@Override
		void setBits(BitSet table) {
			this.first.setBits(table);
			this.second.setBits(table);
		}

		@Override
		public boolean matches(char c) {
			return this.first.matches(c) || this.second.matches(c);
		}

		@Override
		public String toString() {
			return "CharMatcher.or(" + this.first + ", " + this.second + ")";
		}
	}

	private static class RangesMatcher extends CharMatcher {
		private final String description;
		private final char[] rangeStarts;
		private final char[] rangeEnds;

		RangesMatcher(String description, char[] rangeStarts, char[] rangeEnds) {
			this.description = description;
			this.rangeStarts = rangeStarts;
			this.rangeEnds = rangeEnds;
			Preconditions.checkArgument(rangeStarts.length == rangeEnds.length);

			for (int i = 0; i < rangeStarts.length; i++) {
				Preconditions.checkArgument(rangeStarts[i] <= rangeEnds[i]);
				if (i + 1 < rangeStarts.length) {
					Preconditions.checkArgument(rangeEnds[i] < rangeStarts[i + 1]);
				}
			}
		}

		@Override
		public boolean matches(char c) {
			int index = Arrays.binarySearch(this.rangeStarts, c);
			if (index >= 0) {
				return true;
			} else {
				index = ~index - 1;
				return index >= 0 && c <= this.rangeEnds[index];
			}
		}

		@Override
		public String toString() {
			return this.description;
		}
	}

	private static final class SingleWidth extends CharMatcher.RangesMatcher {
		static final CharMatcher.SingleWidth INSTANCE = new CharMatcher.SingleWidth();

		private SingleWidth() {
			super("CharMatcher.singleWidth()", "\u0000־א׳\u0600ݐ\u0e00Ḁ℀ﭐﹰ｡".toCharArray(), "ӹ־ת״ۿݿ\u0e7f₯℺﷿\ufeffￜ".toCharArray());
		}
	}

	@VisibleForTesting
	static final class Whitespace extends CharMatcher.NamedFastMatcher {
		static final String TABLE = " 　\r\u0085   　\u2029\u000b　   　 \t     \f 　 　　\u2028\n 　";
		static final int MULTIPLIER = 1682554634;
		static final int SHIFT = Integer.numberOfLeadingZeros(" 　\r\u0085   　\u2029\u000b　   　 \t     \f 　 　　\u2028\n 　".length() - 1);
		static final CharMatcher.Whitespace INSTANCE = new CharMatcher.Whitespace();

		Whitespace() {
			super("CharMatcher.whitespace()");
		}

		@Override
		public boolean matches(char c) {
			return " 　\r\u0085   　\u2029\u000b　   　 \t     \f 　 　　\u2028\n 　".charAt(1682554634 * c >>> SHIFT) == c;
		}

		@GwtIncompatible
		@Override
		void setBits(BitSet table) {
			for (int i = 0; i < " 　\r\u0085   　\u2029\u000b　   　 \t     \f 　 　　\u2028\n 　".length(); i++) {
				table.set(" 　\r\u0085   　\u2029\u000b　   　 \t     \f 　 　　\u2028\n 　".charAt(i));
			}
		}
	}
}