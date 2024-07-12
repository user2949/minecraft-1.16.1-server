package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.function.LongPredicate;

public final class LongCollections {
	private LongCollections() {
	}

	public static LongCollection synchronize(LongCollection c) {
		return new LongCollections.SynchronizedCollection(c);
	}

	public static LongCollection synchronize(LongCollection c, Object sync) {
		return new LongCollections.SynchronizedCollection(c, sync);
	}

	public static LongCollection unmodifiable(LongCollection c) {
		return new LongCollections.UnmodifiableCollection(c);
	}

	public static LongCollection asCollection(LongIterable iterable) {
		return (LongCollection)(iterable instanceof LongCollection ? (LongCollection)iterable : new LongCollections.IterableCollection(iterable));
	}

	public abstract static class EmptyCollection extends AbstractLongCollection {
		protected EmptyCollection() {
		}

		@Override
		public boolean contains(long k) {
			return false;
		}

		public Object[] toArray() {
			return ObjectArrays.EMPTY_ARRAY;
		}

		public LongBidirectionalIterator iterator() {
			return LongIterators.EMPTY_ITERATOR;
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
	}

	public static class IterableCollection extends AbstractLongCollection implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final LongIterable iterable;

		protected IterableCollection(LongIterable iterable) {
			if (iterable == null) {
				throw new NullPointerException();
			} else {
				this.iterable = iterable;
			}
		}

		public int size() {
			int c = 0;

			for (LongIterator iterator = this.iterator(); iterator.hasNext(); c++) {
				iterator.nextLong();
			}

			return c;
		}

		public boolean isEmpty() {
			return !this.iterable.iterator().hasNext();
		}

		@Override
		public LongIterator iterator() {
			return this.iterable.iterator();
		}
	}

	public static class SynchronizedCollection implements LongCollection, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final LongCollection collection;
		protected final Object sync;

		protected SynchronizedCollection(LongCollection c, Object sync) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
				this.sync = sync;
			}
		}

		protected SynchronizedCollection(LongCollection c) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
				this.sync = this;
			}
		}

		@Override
		public boolean add(long k) {
			synchronized (this.sync) {
				return this.collection.add(k);
			}
		}

		@Override
		public boolean contains(long k) {
			synchronized (this.sync) {
				return this.collection.contains(k);
			}
		}

		@Override
		public boolean rem(long k) {
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
		public long[] toLongArray() {
			synchronized (this.sync) {
				return this.collection.toLongArray();
			}
		}

		public Object[] toArray() {
			synchronized (this.sync) {
				return this.collection.toArray();
			}
		}

		@Deprecated
		@Override
		public long[] toLongArray(long[] a) {
			return this.toArray(a);
		}

		@Override
		public long[] toArray(long[] a) {
			synchronized (this.sync) {
				return this.collection.toArray(a);
			}
		}

		@Override
		public boolean addAll(LongCollection c) {
			synchronized (this.sync) {
				return this.collection.addAll(c);
			}
		}

		@Override
		public boolean containsAll(LongCollection c) {
			synchronized (this.sync) {
				return this.collection.containsAll(c);
			}
		}

		@Override
		public boolean removeAll(LongCollection c) {
			synchronized (this.sync) {
				return this.collection.removeAll(c);
			}
		}

		@Override
		public boolean removeIf(LongPredicate filter) {
			synchronized (this.sync) {
				return this.collection.removeIf(filter);
			}
		}

		@Override
		public boolean retainAll(LongCollection c) {
			synchronized (this.sync) {
				return this.collection.retainAll(c);
			}
		}

		@Deprecated
		@Override
		public boolean add(Long k) {
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
		public LongIterator iterator() {
			return this.collection.iterator();
		}

		public boolean addAll(Collection<? extends Long> c) {
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

	public static class UnmodifiableCollection implements LongCollection, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final LongCollection collection;

		protected UnmodifiableCollection(LongCollection c) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
			}
		}

		@Override
		public boolean add(long k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean rem(long k) {
			throw new UnsupportedOperationException();
		}

		public int size() {
			return this.collection.size();
		}

		public boolean isEmpty() {
			return this.collection.isEmpty();
		}

		@Override
		public boolean contains(long o) {
			return this.collection.contains(o);
		}

		@Override
		public LongIterator iterator() {
			return LongIterators.unmodifiable(this.collection.iterator());
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

		public boolean addAll(Collection<? extends Long> c) {
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
		public boolean add(Long k) {
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
		public long[] toLongArray() {
			return this.collection.toLongArray();
		}

		@Deprecated
		@Override
		public long[] toLongArray(long[] a) {
			return this.toArray(a);
		}

		@Override
		public long[] toArray(long[] a) {
			return this.collection.toArray(a);
		}

		@Override
		public boolean containsAll(LongCollection c) {
			return this.collection.containsAll(c);
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
