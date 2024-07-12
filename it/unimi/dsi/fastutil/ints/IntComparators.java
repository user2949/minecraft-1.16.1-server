package it.unimi.dsi.fastutil.ints;

import java.io.Serializable;
import java.util.Comparator;

public final class IntComparators {
	public static final IntComparator NATURAL_COMPARATOR = new IntComparators.NaturalImplicitComparator();
	public static final IntComparator OPPOSITE_COMPARATOR = new IntComparators.OppositeImplicitComparator();

	private IntComparators() {
	}

	public static IntComparator oppositeComparator(IntComparator c) {
		return new IntComparators.OppositeComparator(c);
	}

	public static IntComparator asIntComparator(Comparator<? super Integer> c) {
		return c != null && !(c instanceof IntComparator) ? new IntComparator() {
			@Override
			public int compare(int x, int y) {
				return c.compare(x, y);
			}

			@Override
			public int compare(Integer x, Integer y) {
				return c.compare(x, y);
			}
		} : (IntComparator)c;
	}

	protected static class NaturalImplicitComparator implements IntComparator, Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		public final int compare(int a, int b) {
			return Integer.compare(a, b);
		}

		private Object readResolve() {
			return IntComparators.NATURAL_COMPARATOR;
		}
	}

	protected static class OppositeComparator implements IntComparator, Serializable {
		private static final long serialVersionUID = 1L;
		private final IntComparator comparator;

		protected OppositeComparator(IntComparator c) {
			this.comparator = c;
		}

		@Override
		public final int compare(int a, int b) {
			return this.comparator.compare(b, a);
		}
	}

	protected static class OppositeImplicitComparator implements IntComparator, Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		public final int compare(int a, int b) {
			return -Integer.compare(a, b);
		}

		private Object readResolve() {
			return IntComparators.OPPOSITE_COMPARATOR;
		}
	}
}
