package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.function.DoublePredicate;

public final class FloatCollections {
	private FloatCollections() {
	}

	public static FloatCollection synchronize(FloatCollection c) {
		return new FloatCollections.SynchronizedCollection(c);
	}

	public static FloatCollection synchronize(FloatCollection c, Object sync) {
		return new FloatCollections.SynchronizedCollection(c, sync);
	}

	public static FloatCollection unmodifiable(FloatCollection c) {
		return new FloatCollections.UnmodifiableCollection(c);
	}

	public static FloatCollection asCollection(FloatIterable iterable) {
		return (FloatCollection)(iterable instanceof FloatCollection ? (FloatCollection)iterable : new FloatCollections.IterableCollection(iterable));
	}

	public abstract static class EmptyCollection extends AbstractFloatCollection {
		protected EmptyCollection() {
		}

		@Override
		public boolean contains(float k) {
			return false;
		}

		public Object[] toArray() {
			return ObjectArrays.EMPTY_ARRAY;
		}

		public FloatBidirectionalIterator iterator() {
			return FloatIterators.EMPTY_ITERATOR;
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

		public boolean addAll(Collection<? extends Float> c) {
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(FloatCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(FloatCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(FloatCollection c) {
			throw new UnsupportedOperationException();
		}
	}

	public static class IterableCollection extends AbstractFloatCollection implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final FloatIterable iterable;

		protected IterableCollection(FloatIterable iterable) {
			if (iterable == null) {
				throw new NullPointerException();
			} else {
				this.iterable = iterable;
			}
		}

		public int size() {
			int c = 0;

			for (FloatIterator iterator = this.iterator(); iterator.hasNext(); c++) {
				iterator.nextFloat();
			}

			return c;
		}

		public boolean isEmpty() {
			return !this.iterable.iterator().hasNext();
		}

		@Override
		public FloatIterator iterator() {
			return this.iterable.iterator();
		}
	}

	public static class SynchronizedCollection implements FloatCollection, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final FloatCollection collection;
		protected final Object sync;

		protected SynchronizedCollection(FloatCollection c, Object sync) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
				this.sync = sync;
			}
		}

		protected SynchronizedCollection(FloatCollection c) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
				this.sync = this;
			}
		}

		@Override
		public boolean add(float k) {
			synchronized (this.sync) {
				return this.collection.add(k);
			}
		}

		@Override
		public boolean contains(float k) {
			synchronized (this.sync) {
				return this.collection.contains(k);
			}
		}

		@Override
		public boolean rem(float k) {
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
		public float[] toFloatArray() {
			synchronized (this.sync) {
				return this.collection.toFloatArray();
			}
		}

		public Object[] toArray() {
			synchronized (this.sync) {
				return this.collection.toArray();
			}
		}

		@Deprecated
		@Override
		public float[] toFloatArray(float[] a) {
			return this.toArray(a);
		}

		@Override
		public float[] toArray(float[] a) {
			synchronized (this.sync) {
				return this.collection.toArray(a);
			}
		}

		@Override
		public boolean addAll(FloatCollection c) {
			synchronized (this.sync) {
				return this.collection.addAll(c);
			}
		}

		@Override
		public boolean containsAll(FloatCollection c) {
			synchronized (this.sync) {
				return this.collection.containsAll(c);
			}
		}

		@Override
		public boolean removeAll(FloatCollection c) {
			synchronized (this.sync) {
				return this.collection.removeAll(c);
			}
		}

		@Override
		public boolean removeIf(DoublePredicate filter) {
			synchronized (this.sync) {
				return this.collection.removeIf(filter);
			}
		}

		@Override
		public boolean retainAll(FloatCollection c) {
			synchronized (this.sync) {
				return this.collection.retainAll(c);
			}
		}

		@Deprecated
		@Override
		public boolean add(Float k) {
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
		public FloatIterator iterator() {
			return this.collection.iterator();
		}

		public boolean addAll(Collection<? extends Float> c) {
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

	public static class UnmodifiableCollection implements FloatCollection, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final FloatCollection collection;

		protected UnmodifiableCollection(FloatCollection c) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
			}
		}

		@Override
		public boolean add(float k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean rem(float k) {
			throw new UnsupportedOperationException();
		}

		public int size() {
			return this.collection.size();
		}

		public boolean isEmpty() {
			return this.collection.isEmpty();
		}

		@Override
		public boolean contains(float o) {
			return this.collection.contains(o);
		}

		@Override
		public FloatIterator iterator() {
			return FloatIterators.unmodifiable(this.collection.iterator());
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

		public boolean addAll(Collection<? extends Float> c) {
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
		public boolean add(Float k) {
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
		public float[] toFloatArray() {
			return this.collection.toFloatArray();
		}

		@Deprecated
		@Override
		public float[] toFloatArray(float[] a) {
			return this.toArray(a);
		}

		@Override
		public float[] toArray(float[] a) {
			return this.collection.toArray(a);
		}

		@Override
		public boolean containsAll(FloatCollection c) {
			return this.collection.containsAll(c);
		}

		@Override
		public boolean addAll(FloatCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(FloatCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(FloatCollection c) {
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
