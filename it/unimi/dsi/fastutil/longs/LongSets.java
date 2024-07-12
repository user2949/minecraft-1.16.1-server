package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongCollections.EmptyCollection;
import it.unimi.dsi.fastutil.longs.LongCollections.SynchronizedCollection;
import it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public final class LongSets {
	public static final LongSets.EmptySet EMPTY_SET = new LongSets.EmptySet();

	private LongSets() {
	}

	public static LongSet singleton(long element) {
		return new LongSets.Singleton(element);
	}

	public static LongSet singleton(Long element) {
		return new LongSets.Singleton(element);
	}

	public static LongSet synchronize(LongSet s) {
		return new LongSets.SynchronizedSet(s);
	}

	public static LongSet synchronize(LongSet s, Object sync) {
		return new LongSets.SynchronizedSet(s, sync);
	}

	public static LongSet unmodifiable(LongSet s) {
		return new LongSets.UnmodifiableSet(s);
	}

	public static class EmptySet extends EmptyCollection implements LongSet, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptySet() {
		}

		@Override
		public boolean remove(long ok) {
			throw new UnsupportedOperationException();
		}

		public Object clone() {
			return LongSets.EMPTY_SET;
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof Set && ((Set)o).isEmpty();
		}

		@Deprecated
		@Override
		public boolean rem(long k) {
			return super.rem(k);
		}

		private Object readResolve() {
			return LongSets.EMPTY_SET;
		}
	}

	public static class Singleton extends AbstractLongSet implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final long element;

		protected Singleton(long element) {
			this.element = element;
		}

		@Override
		public boolean contains(long k) {
			return k == this.element;
		}

		@Override
		public boolean remove(long k) {
			throw new UnsupportedOperationException();
		}

		public LongListIterator iterator() {
			return LongIterators.singleton(this.element);
		}

		public int size() {
			return 1;
		}

		public boolean addAll(Collection<? extends Long> c) {
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(LongCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(LongCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(LongCollection c) {
			throw new UnsupportedOperationException();
		}

		public Object clone() {
			return this;
		}
	}

	public static class SynchronizedSet extends SynchronizedCollection implements LongSet, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected SynchronizedSet(LongSet s, Object sync) {
			super(s, sync);
		}

		protected SynchronizedSet(LongSet s) {
			super(s);
		}

		@Override
		public boolean remove(long k) {
			synchronized (this.sync) {
				return this.collection.rem(k);
			}
		}

		@Deprecated
		@Override
		public boolean rem(long k) {
			return super.rem(k);
		}
	}

	public static class UnmodifiableSet extends UnmodifiableCollection implements LongSet, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected UnmodifiableSet(LongSet s) {
			super(s);
		}

		@Override
		public boolean remove(long k) {
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
		public boolean rem(long k) {
			return super.rem(k);
		}
	}
}
