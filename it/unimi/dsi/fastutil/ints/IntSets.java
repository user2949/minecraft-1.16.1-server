package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntCollections.EmptyCollection;
import it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection;
import it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public final class IntSets {
	public static final IntSets.EmptySet EMPTY_SET = new IntSets.EmptySet();

	private IntSets() {
	}

	public static IntSet singleton(int element) {
		return new IntSets.Singleton(element);
	}

	public static IntSet singleton(Integer element) {
		return new IntSets.Singleton(element);
	}

	public static IntSet synchronize(IntSet s) {
		return new IntSets.SynchronizedSet(s);
	}

	public static IntSet synchronize(IntSet s, Object sync) {
		return new IntSets.SynchronizedSet(s, sync);
	}

	public static IntSet unmodifiable(IntSet s) {
		return new IntSets.UnmodifiableSet(s);
	}

	public static class EmptySet extends EmptyCollection implements IntSet, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptySet() {
		}

		@Override
		public boolean remove(int ok) {
			throw new UnsupportedOperationException();
		}

		public Object clone() {
			return IntSets.EMPTY_SET;
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof Set && ((Set)o).isEmpty();
		}

		@Deprecated
		@Override
		public boolean rem(int k) {
			return super.rem(k);
		}

		private Object readResolve() {
			return IntSets.EMPTY_SET;
		}
	}

	public static class Singleton extends AbstractIntSet implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final int element;

		protected Singleton(int element) {
			this.element = element;
		}

		@Override
		public boolean contains(int k) {
			return k == this.element;
		}

		@Override
		public boolean remove(int k) {
			throw new UnsupportedOperationException();
		}

		public IntListIterator iterator() {
			return IntIterators.singleton(this.element);
		}

		public int size() {
			return 1;
		}

		public boolean addAll(Collection<? extends Integer> c) {
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(IntCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(IntCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(IntCollection c) {
			throw new UnsupportedOperationException();
		}

		public Object clone() {
			return this;
		}
	}

	public static class SynchronizedSet extends SynchronizedCollection implements IntSet, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected SynchronizedSet(IntSet s, Object sync) {
			super(s, sync);
		}

		protected SynchronizedSet(IntSet s) {
			super(s);
		}

		@Override
		public boolean remove(int k) {
			synchronized (this.sync) {
				return this.collection.rem(k);
			}
		}

		@Deprecated
		@Override
		public boolean rem(int k) {
			return super.rem(k);
		}
	}

	public static class UnmodifiableSet extends UnmodifiableCollection implements IntSet, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected UnmodifiableSet(IntSet s) {
			super(s);
		}

		@Override
		public boolean remove(int k) {
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
		public boolean rem(int k) {
			return super.rem(k);
		}
	}
}
