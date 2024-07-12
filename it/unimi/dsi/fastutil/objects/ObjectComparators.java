package it.unimi.dsi.fastutil.objects;

import java.io.Serializable;
import java.util.Comparator;

public final class ObjectComparators {
	public static final Comparator NATURAL_COMPARATOR = new ObjectComparators.NaturalImplicitComparator();
	public static final Comparator OPPOSITE_COMPARATOR = new ObjectComparators.OppositeImplicitComparator();

	private ObjectComparators() {
	}

	public static <K> Comparator<K> oppositeComparator(Comparator<K> c) {
		return new ObjectComparators.OppositeComparator<>(c);
	}

	protected static class NaturalImplicitComparator implements Comparator, Serializable {
		private static final long serialVersionUID = 1L;

		public final int compare(Object a, Object b) {
			return ((Comparable)a).compareTo(b);
		}

		private Object readResolve() {
			return ObjectComparators.NATURAL_COMPARATOR;
		}
	}

	protected static class OppositeComparator<K> implements Comparator<K>, Serializable {
		private static final long serialVersionUID = 1L;
		private final Comparator<K> comparator;

		protected OppositeComparator(Comparator<K> c) {
			this.comparator = c;
		}

		public final int compare(K a, K b) {
			return this.comparator.compare(b, a);
		}
	}

	protected static class OppositeImplicitComparator implements Comparator, Serializable {
		private static final long serialVersionUID = 1L;

		public final int compare(Object a, Object b) {
			return ((Comparable)b).compareTo(a);
		}

		private Object readResolve() {
			return ObjectComparators.OPPOSITE_COMPARATOR;
		}
	}
}
