package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.function.IntPredicate;

public final class ByteCollections {
	private ByteCollections() {
	}

	public static ByteCollection synchronize(ByteCollection c) {
		return new ByteCollections.SynchronizedCollection(c);
	}

	public static ByteCollection synchronize(ByteCollection c, Object sync) {
		return new ByteCollections.SynchronizedCollection(c, sync);
	}

	public static ByteCollection unmodifiable(ByteCollection c) {
		return new ByteCollections.UnmodifiableCollection(c);
	}

	public static ByteCollection asCollection(ByteIterable iterable) {
		return (ByteCollection)(iterable instanceof ByteCollection ? (ByteCollection)iterable : new ByteCollections.IterableCollection(iterable));
	}

	public abstract static class EmptyCollection extends AbstractByteCollection {
		protected EmptyCollection() {
		}

		@Override
		public boolean contains(byte k) {
			return false;
		}

		public Object[] toArray() {
			return ObjectArrays.EMPTY_ARRAY;
		}

		public ByteBidirectionalIterator iterator() {
			return ByteIterators.EMPTY_ITERATOR;
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

		public boolean addAll(Collection<? extends Byte> c) {
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(ByteCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(ByteCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(ByteCollection c) {
			throw new UnsupportedOperationException();
		}
	}

	public static class IterableCollection extends AbstractByteCollection implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ByteIterable iterable;

		protected IterableCollection(ByteIterable iterable) {
			if (iterable == null) {
				throw new NullPointerException();
			} else {
				this.iterable = iterable;
			}
		}

		public int size() {
			int c = 0;

			for (ByteIterator iterator = this.iterator(); iterator.hasNext(); c++) {
				iterator.nextByte();
			}

			return c;
		}

		public boolean isEmpty() {
			return !this.iterable.iterator().hasNext();
		}

		@Override
		public ByteIterator iterator() {
			return this.iterable.iterator();
		}
	}

	public static class SynchronizedCollection implements ByteCollection, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ByteCollection collection;
		protected final Object sync;

		protected SynchronizedCollection(ByteCollection c, Object sync) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
				this.sync = sync;
			}
		}

		protected SynchronizedCollection(ByteCollection c) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
				this.sync = this;
			}
		}

		@Override
		public boolean add(byte k) {
			synchronized (this.sync) {
				return this.collection.add(k);
			}
		}

		@Override
		public boolean contains(byte k) {
			synchronized (this.sync) {
				return this.collection.contains(k);
			}
		}

		@Override
		public boolean rem(byte k) {
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
		public byte[] toByteArray() {
			synchronized (this.sync) {
				return this.collection.toByteArray();
			}
		}

		public Object[] toArray() {
			synchronized (this.sync) {
				return this.collection.toArray();
			}
		}

		@Deprecated
		@Override
		public byte[] toByteArray(byte[] a) {
			return this.toArray(a);
		}

		@Override
		public byte[] toArray(byte[] a) {
			synchronized (this.sync) {
				return this.collection.toArray(a);
			}
		}

		@Override
		public boolean addAll(ByteCollection c) {
			synchronized (this.sync) {
				return this.collection.addAll(c);
			}
		}

		@Override
		public boolean containsAll(ByteCollection c) {
			synchronized (this.sync) {
				return this.collection.containsAll(c);
			}
		}

		@Override
		public boolean removeAll(ByteCollection c) {
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
		public boolean retainAll(ByteCollection c) {
			synchronized (this.sync) {
				return this.collection.retainAll(c);
			}
		}

		@Deprecated
		@Override
		public boolean add(Byte k) {
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
		public ByteIterator iterator() {
			return this.collection.iterator();
		}

		public boolean addAll(Collection<? extends Byte> c) {
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

	public static class UnmodifiableCollection implements ByteCollection, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ByteCollection collection;

		protected UnmodifiableCollection(ByteCollection c) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
			}
		}

		@Override
		public boolean add(byte k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean rem(byte k) {
			throw new UnsupportedOperationException();
		}

		public int size() {
			return this.collection.size();
		}

		public boolean isEmpty() {
			return this.collection.isEmpty();
		}

		@Override
		public boolean contains(byte o) {
			return this.collection.contains(o);
		}

		@Override
		public ByteIterator iterator() {
			return ByteIterators.unmodifiable(this.collection.iterator());
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

		public boolean addAll(Collection<? extends Byte> c) {
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
		public boolean add(Byte k) {
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
		public byte[] toByteArray() {
			return this.collection.toByteArray();
		}

		@Deprecated
		@Override
		public byte[] toByteArray(byte[] a) {
			return this.toArray(a);
		}

		@Override
		public byte[] toArray(byte[] a) {
			return this.collection.toArray(a);
		}

		@Override
		public boolean containsAll(ByteCollection c) {
			return this.collection.containsAll(c);
		}

		@Override
		public boolean addAll(ByteCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(ByteCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(ByteCollection c) {
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
