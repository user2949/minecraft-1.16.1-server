package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.function.IntPredicate;

public final class ShortCollections {
	private ShortCollections() {
	}

	public static ShortCollection synchronize(ShortCollection c) {
		return new ShortCollections.SynchronizedCollection(c);
	}

	public static ShortCollection synchronize(ShortCollection c, Object sync) {
		return new ShortCollections.SynchronizedCollection(c, sync);
	}

	public static ShortCollection unmodifiable(ShortCollection c) {
		return new ShortCollections.UnmodifiableCollection(c);
	}

	public static ShortCollection asCollection(ShortIterable iterable) {
		return (ShortCollection)(iterable instanceof ShortCollection ? (ShortCollection)iterable : new ShortCollections.IterableCollection(iterable));
	}

	public abstract static class EmptyCollection extends AbstractShortCollection {
		protected EmptyCollection() {
		}

		@Override
		public boolean contains(short k) {
			return false;
		}

		public Object[] toArray() {
			return ObjectArrays.EMPTY_ARRAY;
		}

		public ShortBidirectionalIterator iterator() {
			return ShortIterators.EMPTY_ITERATOR;
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

		public boolean addAll(Collection<? extends Short> c) {
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(ShortCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(ShortCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(ShortCollection c) {
			throw new UnsupportedOperationException();
		}
	}

	public static class IterableCollection extends AbstractShortCollection implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ShortIterable iterable;

		protected IterableCollection(ShortIterable iterable) {
			if (iterable == null) {
				throw new NullPointerException();
			} else {
				this.iterable = iterable;
			}
		}

		public int size() {
			int c = 0;

			for (ShortIterator iterator = this.iterator(); iterator.hasNext(); c++) {
				iterator.nextShort();
			}

			return c;
		}

		public boolean isEmpty() {
			return !this.iterable.iterator().hasNext();
		}

		@Override
		public ShortIterator iterator() {
			return this.iterable.iterator();
		}
	}

	public static class SynchronizedCollection implements ShortCollection, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ShortCollection collection;
		protected final Object sync;

		protected SynchronizedCollection(ShortCollection c, Object sync) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
				this.sync = sync;
			}
		}

		protected SynchronizedCollection(ShortCollection c) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
				this.sync = this;
			}
		}

		@Override
		public boolean add(short k) {
			synchronized (this.sync) {
				return this.collection.add(k);
			}
		}

		@Override
		public boolean contains(short k) {
			synchronized (this.sync) {
				return this.collection.contains(k);
			}
		}

		@Override
		public boolean rem(short k) {
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
		public short[] toShortArray() {
			synchronized (this.sync) {
				return this.collection.toShortArray();
			}
		}

		public Object[] toArray() {
			synchronized (this.sync) {
				return this.collection.toArray();
			}
		}

		@Deprecated
		@Override
		public short[] toShortArray(short[] a) {
			return this.toArray(a);
		}

		@Override
		public short[] toArray(short[] a) {
			synchronized (this.sync) {
				return this.collection.toArray(a);
			}
		}

		@Override
		public boolean addAll(ShortCollection c) {
			synchronized (this.sync) {
				return this.collection.addAll(c);
			}
		}

		@Override
		public boolean containsAll(ShortCollection c) {
			synchronized (this.sync) {
				return this.collection.containsAll(c);
			}
		}

		@Override
		public boolean removeAll(ShortCollection c) {
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
		public boolean retainAll(ShortCollection c) {
			synchronized (this.sync) {
				return this.collection.retainAll(c);
			}
		}

		@Deprecated
		@Override
		public boolean add(Short k) {
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
		public ShortIterator iterator() {
			return this.collection.iterator();
		}

		public boolean addAll(Collection<? extends Short> c) {
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

	public static class UnmodifiableCollection implements ShortCollection, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ShortCollection collection;

		protected UnmodifiableCollection(ShortCollection c) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
			}
		}

		@Override
		public boolean add(short k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean rem(short k) {
			throw new UnsupportedOperationException();
		}

		public int size() {
			return this.collection.size();
		}

		public boolean isEmpty() {
			return this.collection.isEmpty();
		}

		@Override
		public boolean contains(short o) {
			return this.collection.contains(o);
		}

		@Override
		public ShortIterator iterator() {
			return ShortIterators.unmodifiable(this.collection.iterator());
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

		public boolean addAll(Collection<? extends Short> c) {
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
		public boolean add(Short k) {
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
		public short[] toShortArray() {
			return this.collection.toShortArray();
		}

		@Deprecated
		@Override
		public short[] toShortArray(short[] a) {
			return this.toArray(a);
		}

		@Override
		public short[] toArray(short[] a) {
			return this.collection.toArray(a);
		}

		@Override
		public boolean containsAll(ShortCollection c) {
			return this.collection.containsAll(c);
		}

		@Override
		public boolean addAll(ShortCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(ShortCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(ShortCollection c) {
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
