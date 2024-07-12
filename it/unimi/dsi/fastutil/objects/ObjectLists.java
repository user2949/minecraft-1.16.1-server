package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectCollections.EmptyCollection;
import it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection;
import it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.RandomAccess;

public final class ObjectLists {
	public static final ObjectLists.EmptyList EMPTY_LIST = new ObjectLists.EmptyList();

	private ObjectLists() {
	}

	public static <K> ObjectList<K> shuffle(ObjectList<K> l, Random random) {
		int i = l.size();

		while (i-- != 0) {
			int p = random.nextInt(i + 1);
			K t = (K)l.get(i);
			l.set(i, l.get(p));
			l.set(p, t);
		}

		return l;
	}

	public static <K> ObjectList<K> emptyList() {
		return EMPTY_LIST;
	}

	public static <K> ObjectList<K> singleton(K element) {
		return new ObjectLists.Singleton<>(element);
	}

	public static <K> ObjectList<K> synchronize(ObjectList<K> l) {
		return (ObjectList<K>)(l instanceof RandomAccess ? new ObjectLists.SynchronizedRandomAccessList<>(l) : new ObjectLists.SynchronizedList<>(l));
	}

	public static <K> ObjectList<K> synchronize(ObjectList<K> l, Object sync) {
		return (ObjectList<K>)(l instanceof RandomAccess ? new ObjectLists.SynchronizedRandomAccessList<>(l, sync) : new ObjectLists.SynchronizedList<>(l, sync));
	}

	public static <K> ObjectList<K> unmodifiable(ObjectList<K> l) {
		return (ObjectList<K>)(l instanceof RandomAccess ? new ObjectLists.UnmodifiableRandomAccessList<>(l) : new ObjectLists.UnmodifiableList<>(l));
	}

	public static class EmptyList<K> extends EmptyCollection<K> implements ObjectList<K>, RandomAccess, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyList() {
		}

		public K get(int i) {
			throw new IndexOutOfBoundsException();
		}

		public boolean remove(Object k) {
			throw new UnsupportedOperationException();
		}

		public K remove(int i) {
			throw new UnsupportedOperationException();
		}

		public void add(int index, K k) {
			throw new UnsupportedOperationException();
		}

		public K set(int index, K k) {
			throw new UnsupportedOperationException();
		}

		public int indexOf(Object k) {
			return -1;
		}

		public int lastIndexOf(Object k) {
			return -1;
		}

		public boolean addAll(int i, Collection<? extends K> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectListIterator<K> listIterator() {
			return ObjectIterators.EMPTY_ITERATOR;
		}

		@Override
		public ObjectListIterator<K> iterator() {
			return ObjectIterators.EMPTY_ITERATOR;
		}

		@Override
		public ObjectListIterator<K> listIterator(int i) {
			if (i == 0) {
				return ObjectIterators.EMPTY_ITERATOR;
			} else {
				throw new IndexOutOfBoundsException(String.valueOf(i));
			}
		}

		@Override
		public ObjectList<K> subList(int from, int to) {
			if (from == 0 && to == 0) {
				return this;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public void getElements(int from, Object[] a, int offset, int length) {
			if (from != 0 || length != 0 || offset < 0 || offset > a.length) {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public void removeElements(int from, int to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, K[] a, int offset, int length) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, K[] a) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void size(int s) {
			throw new UnsupportedOperationException();
		}

		public int compareTo(List<? extends K> o) {
			if (o == this) {
				return 0;
			} else {
				return o.isEmpty() ? 0 : -1;
			}
		}

		public Object clone() {
			return ObjectLists.EMPTY_LIST;
		}

		@Override
		public int hashCode() {
			return 1;
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof List && ((List)o).isEmpty();
		}

		@Override
		public String toString() {
			return "[]";
		}

		private Object readResolve() {
			return ObjectLists.EMPTY_LIST;
		}
	}

	public static class Singleton<K> extends AbstractObjectList<K> implements RandomAccess, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		private final K element;

		protected Singleton(K element) {
			this.element = element;
		}

		public K get(int i) {
			if (i == 0) {
				return this.element;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		public boolean remove(Object k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public K remove(int i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean contains(Object k) {
			return Objects.equals(k, this.element);
		}

		public Object[] toArray() {
			return new Object[]{this.element};
		}

		@Override
		public ObjectListIterator<K> listIterator() {
			return ObjectIterators.singleton(this.element);
		}

		@Override
		public ObjectListIterator<K> iterator() {
			return this.listIterator();
		}

		@Override
		public ObjectListIterator<K> listIterator(int i) {
			if (i <= 1 && i >= 0) {
				ObjectListIterator<K> l = this.listIterator();
				if (i == 1) {
					l.next();
				}

				return l;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public ObjectList<K> subList(int from, int to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return (ObjectList<K>)(from == 0 && to == 1 ? this : ObjectLists.EMPTY_LIST);
			}
		}

		@Override
		public boolean addAll(int i, Collection<? extends K> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(Collection<? extends K> c) {
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public int size() {
			return 1;
		}

		@Override
		public void size(int size) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		public Object clone() {
			return this;
		}
	}

	public static class SynchronizedList<K> extends SynchronizedCollection<K> implements ObjectList<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ObjectList<K> list;

		protected SynchronizedList(ObjectList<K> l, Object sync) {
			super(l, sync);
			this.list = l;
		}

		protected SynchronizedList(ObjectList<K> l) {
			super(l);
			this.list = l;
		}

		public K get(int i) {
			synchronized (this.sync) {
				return (K)this.list.get(i);
			}
		}

		public K set(int i, K k) {
			synchronized (this.sync) {
				return (K)this.list.set(i, k);
			}
		}

		public void add(int i, K k) {
			synchronized (this.sync) {
				this.list.add(i, k);
			}
		}

		public K remove(int i) {
			synchronized (this.sync) {
				return (K)this.list.remove(i);
			}
		}

		public int indexOf(Object k) {
			synchronized (this.sync) {
				return this.list.indexOf(k);
			}
		}

		public int lastIndexOf(Object k) {
			synchronized (this.sync) {
				return this.list.lastIndexOf(k);
			}
		}

		public boolean addAll(int index, Collection<? extends K> c) {
			synchronized (this.sync) {
				return this.list.addAll(index, c);
			}
		}

		@Override
		public void getElements(int from, Object[] a, int offset, int length) {
			synchronized (this.sync) {
				this.list.getElements(from, a, offset, length);
			}
		}

		@Override
		public void removeElements(int from, int to) {
			synchronized (this.sync) {
				this.list.removeElements(from, to);
			}
		}

		@Override
		public void addElements(int index, K[] a, int offset, int length) {
			synchronized (this.sync) {
				this.list.addElements(index, a, offset, length);
			}
		}

		@Override
		public void addElements(int index, K[] a) {
			synchronized (this.sync) {
				this.list.addElements(index, a);
			}
		}

		@Override
		public void size(int size) {
			synchronized (this.sync) {
				this.list.size(size);
			}
		}

		@Override
		public ObjectListIterator<K> listIterator() {
			return this.list.listIterator();
		}

		@Override
		public ObjectListIterator<K> iterator() {
			return this.listIterator();
		}

		@Override
		public ObjectListIterator<K> listIterator(int i) {
			return this.list.listIterator(i);
		}

		@Override
		public ObjectList<K> subList(int from, int to) {
			synchronized (this.sync) {
				return new ObjectLists.SynchronizedList<>(this.list.subList(from, to), this.sync);
			}
		}

		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			} else {
				synchronized (this.sync) {
					return this.collection.equals(o);
				}
			}
		}

		@Override
		public int hashCode() {
			synchronized (this.sync) {
				return this.collection.hashCode();
			}
		}

		public int compareTo(List<? extends K> o) {
			synchronized (this.sync) {
				return this.list.compareTo(o);
			}
		}

		private void writeObject(ObjectOutputStream s) throws IOException {
			synchronized (this.sync) {
				s.defaultWriteObject();
			}
		}
	}

	public static class SynchronizedRandomAccessList<K> extends ObjectLists.SynchronizedList<K> implements RandomAccess, Serializable {
		private static final long serialVersionUID = 0L;

		protected SynchronizedRandomAccessList(ObjectList<K> l, Object sync) {
			super(l, sync);
		}

		protected SynchronizedRandomAccessList(ObjectList<K> l) {
			super(l);
		}

		@Override
		public ObjectList<K> subList(int from, int to) {
			synchronized (this.sync) {
				return new ObjectLists.SynchronizedRandomAccessList<>(this.list.subList(from, to), this.sync);
			}
		}
	}

	public static class UnmodifiableList<K> extends UnmodifiableCollection<K> implements ObjectList<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ObjectList<K> list;

		protected UnmodifiableList(ObjectList<K> l) {
			super(l);
			this.list = l;
		}

		public K get(int i) {
			return (K)this.list.get(i);
		}

		public K set(int i, K k) {
			throw new UnsupportedOperationException();
		}

		public void add(int i, K k) {
			throw new UnsupportedOperationException();
		}

		public K remove(int i) {
			throw new UnsupportedOperationException();
		}

		public int indexOf(Object k) {
			return this.list.indexOf(k);
		}

		public int lastIndexOf(Object k) {
			return this.list.lastIndexOf(k);
		}

		public boolean addAll(int index, Collection<? extends K> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void getElements(int from, Object[] a, int offset, int length) {
			this.list.getElements(from, a, offset, length);
		}

		@Override
		public void removeElements(int from, int to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, K[] a, int offset, int length) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, K[] a) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void size(int size) {
			this.list.size(size);
		}

		@Override
		public ObjectListIterator<K> listIterator() {
			return ObjectIterators.unmodifiable(this.list.listIterator());
		}

		@Override
		public ObjectListIterator<K> iterator() {
			return this.listIterator();
		}

		@Override
		public ObjectListIterator<K> listIterator(int i) {
			return ObjectIterators.unmodifiable(this.list.listIterator(i));
		}

		@Override
		public ObjectList<K> subList(int from, int to) {
			return new ObjectLists.UnmodifiableList<>(this.list.subList(from, to));
		}

		@Override
		public boolean equals(Object o) {
			return o == this ? true : this.collection.equals(o);
		}

		@Override
		public int hashCode() {
			return this.collection.hashCode();
		}

		public int compareTo(List<? extends K> o) {
			return this.list.compareTo(o);
		}
	}

	public static class UnmodifiableRandomAccessList<K> extends ObjectLists.UnmodifiableList<K> implements RandomAccess, Serializable {
		private static final long serialVersionUID = 0L;

		protected UnmodifiableRandomAccessList(ObjectList<K> l) {
			super(l);
		}

		@Override
		public ObjectList<K> subList(int from, int to) {
			return new ObjectLists.UnmodifiableRandomAccessList<>(this.list.subList(from, to));
		}
	}
}
