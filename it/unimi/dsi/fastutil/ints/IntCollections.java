package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.function.IntPredicate;

public final class IntCollections {
	private IntCollections() {
	}

	public static IntCollection synchronize(IntCollection c) {
		return new IntCollections.SynchronizedCollection(c);
	}

	public static IntCollection synchronize(IntCollection c, Object sync) {
		return new IntCollections.SynchronizedCollection(c, sync);
	}

	public static IntCollection unmodifiable(IntCollection c) {
		return new IntCollections.UnmodifiableCollection(c);
	}

	public static IntCollection asCollection(IntIterable iterable) {
		return (IntCollection)(iterable instanceof IntCollection ? (IntCollection)iterable : new IntCollections.IterableCollection(iterable));
	}

	public abstract static class EmptyCollection extends AbstractIntCollection {
		protected EmptyCollection() {
		}

		@Override
		public boolean contains(int k) {
			return false;
		}

		public Object[] toArray() {
			return ObjectArrays.EMPTY_ARRAY;
		}

		public IntBidirectionalIterator iterator() {
			return IntIterators.EMPTY_ITERATOR;
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
	}

	public static class IterableCollection extends AbstractIntCollection implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final IntIterable iterable;

		protected IterableCollection(IntIterable iterable) {
			if (iterable == null) {
				throw new NullPointerException();
			} else {
				this.iterable = iterable;
			}
		}

		public int size() {
			int c = 0;

			for (IntIterator iterator = this.iterator(); iterator.hasNext(); c++) {
				iterator.nextInt();
			}

			return c;
		}

		public boolean isEmpty() {
			return !this.iterable.iterator().hasNext();
		}

		@Override
		public IntIterator iterator() {
			return this.iterable.iterator();
		}
	}

	public static class SynchronizedCollection implements IntCollection, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final IntCollection collection;
		protected final Object sync;

		protected SynchronizedCollection(IntCollection c, Object sync) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
				this.sync = sync;
			}
		}

		protected SynchronizedCollection(IntCollection c) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
				this.sync = this;
			}
		}

		@Override
		public boolean add(int k) {
			synchronized (this.sync) {
				return this.collection.add(k);
			}
		}

		@Override
		public boolean contains(int k) {
			synchronized (this.sync) {
				return this.collection.contains(k);
			}
		}

		@Override
		public boolean rem(int k) {
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
		public int[] toIntArray() {
			synchronized (this.sync) {
				return this.collection.toIntArray();
			}
		}

		public Object[] toArray() {
			synchronized (this.sync) {
				return this.collection.toArray();
			}
		}

		@Deprecated
		@Override
		public int[] toIntArray(int[] a) {
			return this.toArray(a);
		}

		@Override
		public int[] toArray(int[] a) {
			synchronized (this.sync) {
				return this.collection.toArray(a);
			}
		}

		@Override
		public boolean addAll(IntCollection c) {
			synchronized (this.sync) {
				return this.collection.addAll(c);
			}
		}

		@Override
		public boolean containsAll(IntCollection c) {
			synchronized (this.sync) {
				return this.collection.containsAll(c);
			}
		}

		@Override
		public boolean removeAll(IntCollection c) {
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
		public boolean retainAll(IntCollection c) {
			synchronized (this.sync) {
				return this.collection.retainAll(c);
			}
		}

		@Deprecated
		@Override
		public boolean add(Integer k) {
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
		public IntIterator iterator() {
			return this.collection.iterator();
		}

		public boolean addAll(Collection<? extends Integer> c) {
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

	public static class UnmodifiableCollection implements IntCollection, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final IntCollection collection;

		protected UnmodifiableCollection(IntCollection c) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
			}
		}

		@Override
		public boolean add(int k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean rem(int k) {
			throw new UnsupportedOperationException();
		}

		public int size() {
			return this.collection.size();
		}

		public boolean isEmpty() {
			return this.collection.isEmpty();
		}

		@Override
		public boolean contains(int o) {
			return this.collection.contains(o);
		}

		@Override
		public IntIterator iterator() {
			return IntIterators.unmodifiable(this.collection.iterator());
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

		public boolean addAll(Collection<? extends Integer> c) {
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
		public boolean add(Integer k) {
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
		public int[] toIntArray() {
			return this.collection.toIntArray();
		}

		@Deprecated
		@Override
		public int[] toIntArray(int[] a) {
			return this.toArray(a);
		}

		@Override
		public int[] toArray(int[] a) {
			return this.collection.toArray(a);
		}

		@Override
		public boolean containsAll(IntCollection c) {
			return this.collection.containsAll(c);
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
