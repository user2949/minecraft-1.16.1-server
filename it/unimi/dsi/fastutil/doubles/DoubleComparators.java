package it.unimi.dsi.fastutil.doubles;

import java.io.Serializable;
import java.util.Comparator;

public final class DoubleComparators {
	public static final DoubleComparator NATURAL_COMPARATOR = new DoubleComparators.NaturalImplicitComparator();
	public static final DoubleComparator OPPOSITE_COMPARATOR = new DoubleComparators.OppositeImplicitComparator();

	private DoubleComparators() {
	}

	public static DoubleComparator oppositeComparator(DoubleComparator c) {
		return new DoubleComparators.OppositeComparator(c);
	}

	public static DoubleComparator asDoubleComparator(Comparator<? super Double> c) {
		return c != null && !(c instanceof DoubleComparator) ? new DoubleComparator() {
			@Override
			public int compare(double x, double y) {
				return c.compare(x, y);
			}

			@Override
			public int compare(Double x, Double y) {
				return c.compare(x, y);
			}
		} : (DoubleComparator)c;
	}

	protected static class NaturalImplicitComparator implements DoubleComparator, Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		public final int compare(double a, double b) {
			return Double.compare(a, b);
		}

		private Object readResolve() {
			return DoubleComparators.NATURAL_COMPARATOR;
		}
	}

	protected static class OppositeComparator implements DoubleComparator, Serializable {
		private static final long serialVersionUID = 1L;
		private final DoubleComparator comparator;

		protected OppositeComparator(DoubleComparator c) {
			this.comparator = c;
		}

		@Override
		public final int compare(double a, double b) {
			return this.comparator.compare(b, a);
		}
	}

	protected static class OppositeImplicitComparator implements DoubleComparator, Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		public final int compare(double a, double b) {
			return -Double.compare(a, b);
		}

		private Object readResolve() {
			return DoubleComparators.OPPOSITE_COMPARATOR;
		}
	}
}
