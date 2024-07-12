package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.function.IntPredicate;

public final class CharCollections {
	private CharCollections() {
	}

	public static CharCollection synchronize(CharCollection c) {
		return new CharCollections.SynchronizedCollection(c);
	}

	public static CharCollection synchronize(CharCollection c, Object sync) {
		return new CharCollections.SynchronizedCollection(c, sync);
	}

	public static CharCollection unmodifiable(CharCollection c) {
		return new CharCollections.UnmodifiableCollection(c);
	}

	public static CharCollection asCollection(CharIterable iterable) {
		return (CharCollection)(iterable instanceof CharCollection ? (CharCollection)iterable : new CharCollections.IterableCollection(iterable));
	}

	public abstract static class EmptyCollection extends AbstractCharCollection {
		protected EmptyCollection() {
		}

		@Override
		public boolean contains(char k) {
			return false;
		}

		public Object[] toArray() {
			return ObjectArrays.EMPTY_ARRAY;
		}

		public CharBidirectionalIterator iterator() {
			return CharIterators.EMPTY_ITERATOR;
		}

		public int size() {
			return 0;
		}

		public void clear() {
		}

		public int hashCode() {
			return 0;
		}

		public boolean equals(Object o) {
			if (o == this) {
				return true;
			} else {
				return !(o instanceof Collection) ? false : ((Collection)o).isEmpty();
			}
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
	}

	public static class IterableCollection extends AbstractCharCollection implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final CharIterable iterable;

		protected IterableCollection(CharIterable iterable) {
			if (iterable == null) {
				throw new NullPointerException();
			} else {
				this.iterable = iterable;
			}
		}

		public int size() {
			int c = 0;

			for (CharIterator iterator = this.iterator(); iterator.hasNext(); c++) {
				iterator.nextChar();
			}

			return c;
		}

		public boolean isEmpty() {
			return !this.iterable.iterator().hasNext();
		}

		@Override
		public CharIterator iterator() {
			return this.iterable.iterator();
		}
	}

	public static class SynchronizedCollection implements CharCollection, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final CharCollection collection;
		protected final Object sync;

		protected SynchronizedCollection(CharCollection c, Object sync) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
				this.sync = sync;
			}
		}

		protected SynchronizedCollection(CharCollection c) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
				this.sync = this;
			}
		}

		@Override
		public boolean add(char k) {
			synchronized (this.sync) {
				return this.collection.add(k);
			}
		}

		@Override
		public boolean contains(char k) {
			synchronized (this.sync) {
				return this.collection.contains(k);
			}
		}

		@Override
		public boolean rem(char k) {
			synchronized (this.sync) {
				return this.collection.rem(k);
			}
		}

		public int size() {
			synchronized (this.sync) {
				return this.collection.size();
			}
		}

		public boolean isEmpty() {
			synchronized (this.sync) {
				return this.collection.isEmpty();
			}
		}

		@Override
		public char[] toCharArray() {
			synchronized (this.sync) {
				return this.collection.toCharArray();
			}
		}

		public Object[] toArray() {
			synchronized (this.sync) {
				return this.collection.toArray();
			}
		}

		@Deprecated
		@Override
		public char[] toCharArray(char[] a) {
			return this.toArray(a);
		}

		@Override
		public char[] toArray(char[] a) {
			synchronized (this.sync) {
				return this.collection.toArray(a);
			}
		}

		@Override
		public boolean addAll(CharCollection c) {
			synchronized (this.sync) {
				return this.collection.addAll(c);
			}
		}

		@Override
		public boolean containsAll(CharCollection c) {
			synchronized (this.sync) {
				return this.collection.containsAll(c);
			}
		}

		@Override
		public boolean removeAll(CharCollection c) {
			synchronized (this.sync) {
				return this.collection.removeAll(c);
			}
		}

		@Override
		public boolean removeIf(IntPredicate filter) {
			synchronized (this.sync) {
				return this.collection.removeIf(filter);
			}
		}

		@Override
		public boolean retainAll(CharCollection c) {
			synchronized (this.sync) {
				return this.collection.retainAll(c);
			}
		}

		@Deprecated
		@Override
		public boolean add(Character k) {
			synchronized (this.sync) {
				return this.collection.add(k);
			}
		}

		@Deprecated
		@Override
		public boolean contains(Object k) {
			synchronized (this.sync) {
				return this.collection.contains(k);
			}
		}

		@Deprecated
		@Override
		public boolean remove(Object k) {
			synchronized (this.sync) {
				return this.collection.remove(k);
			}
		}

		public <T> T[] toArray(T[] a) {
			synchronized (this.sync) {
				return (T[])this.collection.toArray(a);
			}
		}

		@Override
		public CharIterator iterator() {
			return this.collection.iterator();
		}

		public boolean addAll(Collection<? extends Character> c) {
			synchronized (this.sync) {
				return this.collection.addAll(c);
			}
		}

		public boolean containsAll(Collection<?> c) {
			synchronized (this.sync) {
				return this.collection.containsAll(c);
			}
		}

		public boolean removeAll(Collection<?> c) {
			synchronized (this.sync) {
				return this.collection.removeAll(c);
			}
		}

		public boolean retainAll(Collection<?> c) {
			synchronized (this.sync) {
				return this.collection.retainAll(c);
			}
		}

		public void clear() {
			synchronized (this.sync) {
				this.collection.clear();
			}
		}

		public String toString() {
			synchronized (this.sync) {
				return this.collection.toString();
			}
		}

		public int hashCode() {
			synchronized (this.sync) {
				return this.collection.hashCode();
			}
		}

		public boolean equals(Object o) {
			if (o == this) {
				return true;
			} else {
				synchronized (this.sync) {
					return this.collection.equals(o);
				}
			}
		}

		private void writeObject(ObjectOutputStream s) throws IOException {
			synchronized (this.sync) {
				s.defaultWriteObject();
			}
		}
	}

	public static class UnmodifiableCollection implements CharCollection, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final CharCollection collection;

		protected UnmodifiableCollection(CharCollection c) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
			}
		}

		@Override
		public boolean add(char k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean rem(char k) {
			throw new UnsupportedOperationException();
		}

		public int size() {
			return this.collection.size();
		}

		public boolean isEmpty() {
			return this.collection.isEmpty();
		}

		@Override
		public boolean contains(char o) {
			return this.collection.contains(o);
		}

		@Override
		public CharIterator iterator() {
			return CharIterators.unmodifiable(this.collection.iterator());
		}

		public void clear() {
			throw new UnsupportedOperationException();
		}

		public <T> T[] toArray(T[] a) {
			return (T[])this.collection.toArray(a);
		}

		public Object[] toArray() {
			return this.collection.toArray();
		}

		public boolean containsAll(Collection<?> c) {
			return this.collection.containsAll(c);
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

		@Deprecated
		@Override
		public boolean add(Character k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean contains(Object k) {
			return this.collection.contains(k);
		}

		@Deprecated
		@Override
		public boolean remove(Object k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char[] toCharArray() {
			return this.collection.toCharArray();
		}

		@Deprecated
		@Override
		public char[] toCharArray(char[] a) {
			return this.toArray(a);
		}

		@Override
		public char[] toArray(char[] a) {
			return this.collection.toArray(a);
		}

		@Override
		public boolean containsAll(CharCollection c) {
			return this.collection.containsAll(c);
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

		public String toString() {
			return this.collection.toString();
		}

		public int hashCode() {
			return this.collection.hashCode();
		}

		public boolean equals(Object o) {
			return o == this ? true : this.collection.equals(o);
		}
	}
}
