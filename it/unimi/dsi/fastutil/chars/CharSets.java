package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharCollections.EmptyCollection;
import it.unimi.dsi.fastutil.chars.CharCollections.SynchronizedCollection;
import it.unimi.dsi.fastutil.chars.CharCollections.UnmodifiableCollection;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public final class CharSets {
	public static final CharSets.EmptySet EMPTY_SET = new CharSets.EmptySet();

	private CharSets() {
	}

	public static CharSet singleton(char element) {
		return new CharSets.Singleton(element);
	}

	public static CharSet singleton(Character element) {
		return new CharSets.Singleton(element);
	}

	public static CharSet synchronize(CharSet s) {
		return new CharSets.SynchronizedSet(s);
	}

	public static CharSet synchronize(CharSet s, Object sync) {
		return new CharSets.SynchronizedSet(s, sync);
	}

	public static CharSet unmodifiable(CharSet s) {
		return new CharSets.UnmodifiableSet(s);
	}

	public static class EmptySet extends EmptyCollection implements CharSet, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptySet() {
		}

		@Override
		public boolean remove(char ok) {
			throw new UnsupportedOperationException();
		}

		public Object clone() {
			return CharSets.EMPTY_SET;
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof Set && ((Set)o).isEmpty();
		}

		@Deprecated
		@Override
		public boolean rem(char k) {
			return super.rem(k);
		}

		private Object readResolve() {
			return CharSets.EMPTY_SET;
		}
	}

	public static class Singleton extends AbstractCharSet implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final char element;

		protected Singleton(char element) {
			this.element = element;
		}

		@Override
		public boolean contains(char k) {
			return k == this.element;
		}

		@Override
		public boolean remove(char k) {
			throw new UnsupportedOperationException();
		}

		public CharListIterator iterator() {
			return CharIterators.singleton(this.element);
		}

		public int size() {
			return 1;
		}

		public boolean addAll(Collection<? extends Character> c) {
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(CharCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(CharCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(CharCollection c) {
			throw new UnsupportedOperationException();
		}

		public Object clone() {
			return this;
		}
	}

	public static class SynchronizedSet extends SynchronizedCollection implements CharSet, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected SynchronizedSet(CharSet s, Object sync) {
			super(s, sync);
		}

		protected SynchronizedSet(CharSet s) {
			super(s);
		}

		@Override
		public boolean remove(char k) {
			synchronized (this.sync) {
				return this.collection.rem(k);
			}
		}

		@Deprecated
		@Override
		public boolean rem(char k) {
			return super.rem(k);
		}
	}

	public static class UnmodifiableSet extends UnmodifiableCollection implements CharSet, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected UnmodifiableSet(CharSet s) {
			super(s);
		}

		@Override
		public boolean remove(char k) {
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
		public boolean rem(char k) {
			return super.rem(k);
		}
	}
}
