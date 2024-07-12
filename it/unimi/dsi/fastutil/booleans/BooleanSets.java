package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.BooleanCollections.EmptyCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollections.SynchronizedCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollections.UnmodifiableCollection;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public final class BooleanSets {
	public static final BooleanSets.EmptySet EMPTY_SET = new BooleanSets.EmptySet();

	private BooleanSets() {
	}

	public static BooleanSet singleton(boolean element) {
		return new BooleanSets.Singleton(element);
	}

	public static BooleanSet singleton(Boolean element) {
		return new BooleanSets.Singleton(element);
	}

	public static BooleanSet synchronize(BooleanSet s) {
		return new BooleanSets.SynchronizedSet(s);
	}

	public static BooleanSet synchronize(BooleanSet s, Object sync) {
		return new BooleanSets.SynchronizedSet(s, sync);
	}

	public static BooleanSet unmodifiable(BooleanSet s) {
		return new BooleanSets.UnmodifiableSet(s);
	}

	public static class EmptySet extends EmptyCollection implements BooleanSet, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptySet() {
		}

		@Override
		public boolean remove(boolean ok) {
			throw new UnsupportedOperationException();
		}

		public Object clone() {
			return BooleanSets.EMPTY_SET;
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof Set && ((Set)o).isEmpty();
		}

		@Deprecated
		@Override
		public boolean rem(boolean k) {
			return super.rem(k);
		}

		private Object readResolve() {
			return BooleanSets.EMPTY_SET;
		}
	}

	public static class Singleton extends AbstractBooleanSet implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final boolean element;

		protected Singleton(boolean element) {
			this.element = element;
		}

		@Override
		public boolean contains(boolean k) {
			return k == this.element;
		}

		@Override
		public boolean remove(boolean k) {
			throw new UnsupportedOperationException();
		}

		public BooleanListIterator iterator() {
			return BooleanIterators.singleton(this.element);
		}

		public int size() {
			return 1;
		}

		public boolean addAll(Collection<? extends Boolean> c) {
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(BooleanCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(BooleanCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(BooleanCollection c) {
			throw new UnsupportedOperationException();
		}

		public Object clone() {
			return this;
		}
	}

	public static class SynchronizedSet extends SynchronizedCollection implements BooleanSet, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected SynchronizedSet(BooleanSet s, Object sync) {
			super(s, sync);
		}

		protected SynchronizedSet(BooleanSet s) {
			super(s);
		}

		@Override
		public boolean remove(boolean k) {
			synchronized (this.sync) {
				return this.collection.rem(k);
			}
		}

		@Deprecated
		@Override
		public boolean rem(boolean k) {
			return super.rem(k);
		}
	}

	public static class UnmodifiableSet extends UnmodifiableCollection implements BooleanSet, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected UnmodifiableSet(BooleanSet s) {
			super(s);
		}

		@Override
		public boolean remove(boolean k) {
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
		public boolean rem(boolean k) {
			return super.rem(k);
		}
	}
}
