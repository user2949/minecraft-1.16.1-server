package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;

public final class BooleanCollections {
	private BooleanCollections() {
	}

	public static BooleanCollection synchronize(BooleanCollection c) {
		return new BooleanCollections.SynchronizedCollection(c);
	}

	public static BooleanCollection synchronize(BooleanCollection c, Object sync) {
		return new BooleanCollections.SynchronizedCollection(c, sync);
	}

	public static BooleanCollection unmodifiable(BooleanCollection c) {
		return new BooleanCollections.UnmodifiableCollection(c);
	}

	public static BooleanCollection asCollection(BooleanIterable iterable) {
		return (BooleanCollection)(iterable instanceof BooleanCollection ? (BooleanCollection)iterable : new BooleanCollections.IterableCollection(iterable));
	}

	public abstract static class EmptyCollection extends AbstractBooleanCollection {
		protected EmptyCollection() {
		}

		@Override
		public boolean contains(boolean k) {
			return false;
		}

		public Object[] toArray() {
			return ObjectArrays.EMPTY_ARRAY;
		}

		public BooleanBidirectionalIterator iterator() {
			return BooleanIterators.EMPTY_ITERATOR;
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
	}

	public static class IterableCollection extends AbstractBooleanCollection implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final BooleanIterable iterable;

		protected IterableCollection(BooleanIterable iterable) {
			if (iterable == null) {
				throw new NullPointerException();
			} else {
				this.iterable = iterable;
			}
		}

		public int size() {
			int c = 0;

			for (BooleanIterator iterator = this.iterator(); iterator.hasNext(); c++) {
				iterator.nextBoolean();
			}

			return c;
		}

		public boolean isEmpty() {
			return !this.iterable.iterator().hasNext();
		}

		@Override
		public BooleanIterator iterator() {
			return this.iterable.iterator();
		}
	}

	public static class SynchronizedCollection implements BooleanCollection, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final BooleanCollection collection;
		protected final Object sync;

		protected SynchronizedCollection(BooleanCollection c, Object sync) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
				this.sync = sync;
			}
		}

		protected SynchronizedCollection(BooleanCollection c) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
				this.sync = this;
			}
		}

		@Override
		public boolean add(boolean k) {
			synchronized (this.sync) {
				return this.collection.add(k);
			}
		}

		@Override
		public boolean contains(boolean k) {
			synchronized (this.sync) {
				return this.collection.contains(k);
			}
		}

		@Override
		public boolean rem(boolean k) {
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
		public boolean[] toBooleanArray() {
			synchronized (this.sync) {
				return this.collection.toBooleanArray();
			}
		}

		public Object[] toArray() {
			synchronized (this.sync) {
				return this.collection.toArray();
			}
		}

		@Deprecated
		@Override
		public boolean[] toBooleanArray(boolean[] a) {
			return this.toArray(a);
		}

		@Override
		public boolean[] toArray(boolean[] a) {
			synchronized (this.sync) {
				return this.collection.toArray(a);
			}
		}

		@Override
		public boolean addAll(BooleanCollection c) {
			synchronized (this.sync) {
				return this.collection.addAll(c);
			}
		}

		@Override
		public boolean containsAll(BooleanCollection c) {
			synchronized (this.sync) {
				return this.collection.containsAll(c);
			}
		}

		@Override
		public boolean removeAll(BooleanCollection c) {
			synchronized (this.sync) {
				return this.collection.removeAll(c);
			}
		}

		@Override
		public boolean retainAll(BooleanCollection c) {
			synchronized (this.sync) {
				return this.collection.retainAll(c);
			}
		}

		@Deprecated
		@Override
		public boolean add(Boolean k) {
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
		public BooleanIterator iterator() {
			return this.collection.iterator();
		}

		public boolean addAll(Collection<? extends Boolean> c) {
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

	public static class UnmodifiableCollection implements BooleanCollection, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final BooleanCollection collection;

		protected UnmodifiableCollection(BooleanCollection c) {
			if (c == null) {
				throw new NullPointerException();
			} else {
				this.collection = c;
			}
		}

		@Override
		public boolean add(boolean k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean rem(boolean k) {
			throw new UnsupportedOperationException();
		}

		public int size() {
			return this.collection.size();
		}

		public boolean isEmpty() {
			return this.collection.isEmpty();
		}

		@Override
		public boolean contains(boolean o) {
			return this.collection.contains(o);
		}

		@Override
		public BooleanIterator iterator() {
			return BooleanIterators.unmodifiable(this.collection.iterator());
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

		public boolean addAll(Collection<? extends Boolean> c) {
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
		public boolean add(Boolean k) {
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
		public boolean[] toBooleanArray() {
			return this.collection.toBooleanArray();
		}

		@Deprecated
		@Override
		public boolean[] toBooleanArray(boolean[] a) {
			return this.toArray(a);
		}

		@Override
		public boolean[] toArray(boolean[] a) {
			return this.collection.toArray(a);
		}

		@Override
		public boolean containsAll(BooleanCollection c) {
			return this.collection.containsAll(c);
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
