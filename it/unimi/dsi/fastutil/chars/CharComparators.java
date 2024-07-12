package it.unimi.dsi.fastutil.chars;

import java.io.Serializable;
import java.util.Comparator;

public final class CharComparators {
	public static final CharComparator NATURAL_COMPARATOR = new CharComparators.NaturalImplicitComparator();
	public static final CharComparator OPPOSITE_COMPARATOR = new CharComparators.OppositeImplicitComparator();

	private CharComparators() {
	}

	public static CharComparator oppositeComparator(CharComparator c) {
		return new CharComparators.OppositeComparator(c);
	}

	public static CharComparator asCharComparator(Comparator<? super Character> c) {
		return c != null && !(c instanceof CharComparator) ? new CharComparator() {
			@Override
			public int compare(char x, char y) {
				return c.compare(x, y);
			}

			@Override
			public int compare(Character x, Character y) {
				return c.compare(x, y);
			}
		} : (CharComparator)c;
	}

	protected static class NaturalImplicitComparator implements CharComparator, Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		public final int compare(char a, char b) {
			return Character.compare(a, b);
		}

		private Object readResolve() {
			return CharComparators.NATURAL_COMPARATOR;
		}
	}

	protected static class OppositeComparator implements CharComparator, Serializable {
		private static final long serialVersionUID = 1L;
		private final CharComparator comparator;

		protected OppositeComparator(CharComparator c) {
			this.comparator = c;
		}

		@Override
		public final int compare(char a, char b) {
			return this.comparator.compare(b, a);
		}
	}

	protected static class OppositeImplicitComparator implements CharComparator, Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		public final int compare(char a, char b) {
			return -Character.compare(a, b);
		}

		private Object readResolve() {
			return CharComparators.OPPOSITE_COMPARATOR;
		}
	}
}
