package it.unimi.dsi.fastutil.objects;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;

public final class ObjectCollections {
	private ObjectCollections() {
	}

	public static <K> ObjectCollection<K> synchronize(ObjectCollection<K> c) {
		return new ObjectCollections.SynchronizedCollection<>(c);
	}

	public static <K> ObjectCollection<K> synchronize(ObjectCollection<K> c, Object sync) {
		return new ObjectCollections.SynchronizedCollection<>(c, sync);
	}

	public static <K> ObjectCollection<K> unmodifiable(ObjectCollection<K> c) {
		return new ObjectCollections.UnmodifiableCollection<>(c);
	}

	public static <K> ObjectCollection<K> asCollection(ObjectIterable<K> iterable) {
		return (ObjectCollection<K>)(iterable instanceof ObjectCollection ? (ObjectCollection)iterable : new ObjectCollections.IterableCollection<>(iterable));
	}

	public abstract static class EmptyCollection<K> extends AbstractObjectCollection<K> {
		protected EmptyCollection() {
		}

		public boolean contains(Object k) {
			return false;
		}

		public Object[] toArray() {
			return ObjectArrays.EMPTY_ARRAY;
		}

		public ObjectBidirectionalIterator<K> iterator() {
			return ObjectIterators.EMPTY_ITERATOR;
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

		public boolean addAll(Collection<? extends K> c) {
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}
	}

	public static class IterableCollection<K> extends AbstractObjectCollection<K> implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ObjectIterable<K> iterable;

		protected IterableCollection(ObjectIterable<K> iterable) {
			if (iterable == null) {
				throw new NullPointerException();
			} else {
				this.iterable = iterable;
			}
		}

		public int size() {
			int c = 0;

			for (ObjectIterator<K> iterator = this.iterator(); iterator.hasNext(); c++) {
				iterator.next();
			}

			return c;
		}

		public boolean isEmpty() {
			return !this.iterable.iterator().hasNext();
		}

		@Override
		public ObjectIterator<K> iterator() {
			return this.iterable.iterator();
		}
	}

	public static class SynchronizedCollection<K> implements ObjectCollection<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ObjectCollection<K> collection;
		protected final Object sync;

		protected SynchronizedCollection(ObjectCollection<K> c, Object sync) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
				this.sync = sync;
			}
		}

		protected SynchronizedCollection(ObjectCollection<K> c) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
				this.sync = this;
			}
		}

		public boolean add(K k) {
			synchronized (this.sync) {
				return this.collection.add(k);
			}
		}

		public boolean contains(Object k) {
			synchronized (this.sync) {
				return this.collection.contains(k);
			}
		}

		public boolean remove(Object k) {
			synchronized (this.sync) {
				return this.collection.remove(k);
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

		public Object[] toArray() {
			synchronized (this.sync) {
				return this.collection.toArray();
			}
		}

		public <T> T[] toArray(T[] a) {
			synchronized (this.sync) {
				return (T[])this.collection.toArray(a);
			}
		}

		@Override
		public ObjectIterator<K> iterator() {
			return this.collection.iterator();
		}

		public boolean addAll(Collection<? extends K> c) {
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

	public static class UnmodifiableCollection<K> implements ObjectCollection<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ObjectCollection<K> collection;

		protected UnmodifiableCollection(ObjectCollection<K> c) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
			}
		}

		public boolean add(K k) {
			throw new UnsupportedOperationException();
		}

		public boolean remove(Object k) {
			throw new UnsupportedOperationException();
		}

		public int size() {
			return this.collection.size();
		}

		public boolean isEmpty() {
			return this.collection.isEmpty();
		}

		public boolean contains(Object o) {
			return this.collection.contains(o);
		}

		@Override
		public ObjectIterator<K> iterator() {
			return ObjectIterators.unmodifiable(this.collection.iterator());
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

		public boolean addAll(Collection<? extends K> c) {
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c) {
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
