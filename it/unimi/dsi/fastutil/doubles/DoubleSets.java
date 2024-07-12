package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleCollections.EmptyCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollections.SynchronizedCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public final class DoubleSets {
	public static final DoubleSets.EmptySet EMPTY_SET = new DoubleSets.EmptySet();

	private DoubleSets() {
	}

	public static DoubleSet singleton(double element) {
		return new DoubleSets.Singleton(element);
	}

	public static DoubleSet singleton(Double element) {
		return new DoubleSets.Singleton(element);
	}

	public static DoubleSet synchronize(DoubleSet s) {
		return new DoubleSets.SynchronizedSet(s);
	}

	public static DoubleSet synchronize(DoubleSet s, Object sync) {
		return new DoubleSets.SynchronizedSet(s, sync);
	}

	public static DoubleSet unmodifiable(DoubleSet s) {
		return new DoubleSets.UnmodifiableSet(s);
	}

	public static class EmptySet extends EmptyCollection implements DoubleSet, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptySet() {
		}

		@Override
		public boolean remove(double ok) {
			throw new UnsupportedOperationException();
		}

		public Object clone() {
			return DoubleSets.EMPTY_SET;
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof Set && ((Set)o).isEmpty();
		}

		@Deprecated
		@Override
		public boolean rem(double k) {
			return super.rem(k);
		}

		private Object readResolve() {
			return DoubleSets.EMPTY_SET;
		}
	}

	public static class Singleton extends AbstractDoubleSet implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final double element;

		protected Singleton(double element) {
			this.element = element;
		}

		@Override
		public boolean contains(double k) {
			return Double.doubleToLongBits(k) == Double.doubleToLongBits(this.element);
		}

		@Override
		public boolean remove(double k) {
			throw new UnsupportedOperationException();
		}

		public DoubleListIterator iterator() {
			return DoubleIterators.singleton(this.element);
		}

		public int size() {
			return 1;
		}

		public boolean addAll(Collection<? extends Double> c) {
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(DoubleCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(DoubleCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(DoubleCollection c) {
			throw new UnsupportedOperationException();
		}

		public Object clone() {
			return this;
		}
	}

	public static class SynchronizedSet extends SynchronizedCollection implements DoubleSet, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected SynchronizedSet(DoubleSet s, Object sync) {
			super(s, sync);
		}

		protected SynchronizedSet(DoubleSet s) {
			super(s);
		}

		@Override
		public boolean remove(double k) {
			synchronized (this.sync) {
				return this.collection.rem(k);
			}
		}

		@Deprecated
		@Override
		public boolean rem(double k) {
			return super.rem(k);
		}
	}

	public static class UnmodifiableSet extends UnmodifiableCollection implements DoubleSet, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected UnmodifiableSet(DoubleSet s) {
			super(s);
		}

		@Override
		public boolean remove(double k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean equals(Object o) {
			return o == this ? true : this.collection.equals(o);
		}

		@Override
		public int hashCode() {
			return this.collection.hashCode();
		}

		@Deprecated
		@Override
		public boolean rem(double k) {
			return super.rem(k);
		}
	}
}
