package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatCollections.EmptyCollection;
import it.unimi.dsi.fastutil.floats.FloatCollections.SynchronizedCollection;
import it.unimi.dsi.fastutil.floats.FloatCollections.UnmodifiableCollection;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;

public final class FloatLists {
	public static final FloatLists.EmptyList EMPTY_LIST = new FloatLists.EmptyList();

	private FloatLists() {
	}

	public static FloatList shuffle(FloatList l, Random random) {
		int i = l.size();

		while (i-- != 0) {
			int p = random.nextInt(i + 1);
			float t = l.getFloat(i);
			l.set(i, l.getFloat(p));
			l.set(p, t);
		}

		return l;
	}

	public static FloatList singleton(float element) {
		return new FloatLists.Singleton(element);
	}

	public static FloatList singleton(Object element) {
		return new FloatLists.Singleton((Float)element);
	}

	public static FloatList synchronize(FloatList l) {
		return (FloatList)(l instanceof RandomAccess ? new FloatLists.SynchronizedRandomAccessList(l) : new FloatLists.SynchronizedList(l));
	}

	public static FloatList synchronize(FloatList l, Object sync) {
		return (FloatList)(l instanceof RandomAccess ? new FloatLists.SynchronizedRandomAccessList(l, sync) : new FloatLists.SynchronizedList(l, sync));
	}

	public static FloatList unmodifiable(FloatList l) {
		return (FloatList)(l instanceof RandomAccess ? new FloatLists.UnmodifiableRandomAccessList(l) : new FloatLists.UnmodifiableList(l));
	}

	public static class EmptyList extends EmptyCollection implements FloatList, RandomAccess, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyList() {
		}

		@Override
		public float getFloat(int i) {
			throw new IndexOutOfBoundsException();
		}

		@Override
		public boolean rem(float k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float removeFloat(int i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(int index, float k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float set(int index, float k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int indexOf(float k) {
			return -1;
		}

		@Override
		public int lastIndexOf(float k) {
			return -1;
		}

		public boolean addAll(int i, Collection<? extends Float> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(FloatList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, FloatCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, FloatList c) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public void add(int index, Float k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float get(int index) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean add(Float k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float set(int index, Float k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float remove(int k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public int indexOf(Object k) {
			return -1;
		}

		@Deprecated
		@Override
		public int lastIndexOf(Object k) {
			return -1;
		}

		@Override
		public FloatListIterator listIterator() {
			return FloatIterators.EMPTY_ITERATOR;
		}

		@Override
		public FloatListIterator iterator() {
			return FloatIterators.EMPTY_ITERATOR;
		}

		@Override
		public FloatListIterator listIterator(int i) {
			if (i == 0) {
				return FloatIterators.EMPTY_ITERATOR;
			} else {
				throw new IndexOutOfBoundsException(String.valueOf(i));
			}
		}

		@Override
		public FloatList subList(int from, int to) {
			if (from == 0 && to == 0) {
				return this;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public void getElements(int from, float[] a, int offset, int length) {
			if (from != 0 || length != 0 || offset < 0 || offset > a.length) {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public void removeElements(int from, int to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, float[] a, int offset, int length) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, float[] a) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void size(int s) {
			throw new UnsupportedOperationException();
		}

		public int compareTo(List<? extends Float> o) {
			if (o == this) {
				return 0;
			} else {
				return o.isEmpty() ? 0 : -1;
			}
		}

		public Object clone() {
			return FloatLists.EMPTY_LIST;
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
			return FloatLists.EMPTY_LIST;
		}
	}

	public static class Singleton extends AbstractFloatList implements RandomAccess, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		private final float element;

		protected Singleton(float element) {
			this.element = element;
		}

		@Override
		public float getFloat(int i) {
			if (i == 0) {
				return this.element;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public boolean rem(float k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float removeFloat(int i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean contains(float k) {
			return Float.floatToIntBits(k) == Float.floatToIntBits(this.element);
		}

		@Override
		public float[] toFloatArray() {
			return new float[]{this.element};
		}

		@Override
		public FloatListIterator listIterator() {
			return FloatIterators.singleton(this.element);
		}

		@Override
		public FloatListIterator iterator() {
			return this.listIterator();
		}

		@Override
		public FloatListIterator listIterator(int i) {
			if (i <= 1 && i >= 0) {
				FloatListIterator l = this.listIterator();
				if (i == 1) {
					l.nextFloat();
				}

				return l;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public FloatList subList(int from, int to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return (FloatList)(from == 0 && to == 1 ? this : FloatLists.EMPTY_LIST);
			}
		}

		@Override
		public boolean addAll(int i, Collection<? extends Float> c) {
			throw new UnsupportedOperationException();
		}

		@Override
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
		public boolean addAll(FloatList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, FloatList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, FloatCollection c) {
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

	public static class SynchronizedList extends SynchronizedCollection implements FloatList, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final FloatList list;

		protected SynchronizedList(FloatList l, Object sync) {
			super(l, sync);
			this.list = l;
		}

		protected SynchronizedList(FloatList l) {
			super(l);
			this.list = l;
		}

		@Override
		public float getFloat(int i) {
			synchronized (this.sync) {
				return this.list.getFloat(i);
			}
		}

		@Override
		public float set(int i, float k) {
			synchronized (this.sync) {
				return this.list.set(i, k);
			}
		}

		@Override
		public void add(int i, float k) {
			synchronized (this.sync) {
				this.list.add(i, k);
			}
		}

		@Override
		public float removeFloat(int i) {
			synchronized (this.sync) {
				return this.list.removeFloat(i);
			}
		}

		@Override
		public int indexOf(float k) {
			synchronized (this.sync) {
				return this.list.indexOf(k);
			}
		}

		@Override
		public int lastIndexOf(float k) {
			synchronized (this.sync) {
				return this.list.lastIndexOf(k);
			}
		}

		public boolean addAll(int index, Collection<? extends Float> c) {
			synchronized (this.sync) {
				return this.list.addAll(index, c);
			}
		}

		@Override
		public void getElements(int from, float[] a, int offset, int length) {
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
		public void addElements(int index, float[] a, int offset, int length) {
			synchronized (this.sync) {
				this.list.addElements(index, a, offset, length);
			}
		}

		@Override
		public void addElements(int index, float[] a) {
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
		public FloatListIterator listIterator() {
			return this.list.listIterator();
		}

		@Override
		public FloatListIterator iterator() {
			return this.listIterator();
		}

		@Override
		public FloatListIterator listIterator(int i) {
			return this.list.listIterator(i);
		}

		@Override
		public FloatList subList(int from, int to) {
			synchronized (this.sync) {
				return new FloatLists.SynchronizedList(this.list.subList(from, to), this.sync);
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

		public int compareTo(List<? extends Float> o) {
			synchronized (this.sync) {
				return this.list.compareTo(o);
			}
		}

		@Override
		public boolean addAll(int index, FloatCollection c) {
			synchronized (this.sync) {
				return this.list.addAll(index, c);
			}
		}

		@Override
		public boolean addAll(int index, FloatList l) {
			synchronized (this.sync) {
				return this.list.addAll(index, l);
			}
		}

		@Override
		public boolean addAll(FloatList l) {
			synchronized (this.sync) {
				return this.list.addAll(l);
			}
		}

		@Deprecated
		@Override
		public Float get(int i) {
			synchronized (this.sync) {
				return this.list.get(i);
			}
		}

		@Deprecated
		@Override
		public void add(int i, Float k) {
			synchronized (this.sync) {
				this.list.add(i, k);
			}
		}

		@Deprecated
		@Override
		public Float set(int index, Float k) {
			synchronized (this.sync) {
				return this.list.set(index, k);
			}
		}

		@Deprecated
		@Override
		public Float remove(int i) {
			synchronized (this.sync) {
				return this.list.remove(i);
			}
		}

		@Deprecated
		@Override
		public int indexOf(Object o) {
			synchronized (this.sync) {
				return this.list.indexOf(o);
			}
		}

		@Deprecated
		@Override
		public int lastIndexOf(Object o) {
			synchronized (this.sync) {
				return this.list.lastIndexOf(o);
			}
		}

		private void writeObject(ObjectOutputStream s) throws IOException {
			synchronized (this.sync) {
				s.defaultWriteObject();
			}
		}
	}

	public static class SynchronizedRandomAccessList extends FloatLists.SynchronizedList implements RandomAccess, Serializable {
		private static final long serialVersionUID = 0L;

		protected SynchronizedRandomAccessList(FloatList l, Object sync) {
			super(l, sync);
		}

		protected SynchronizedRandomAccessList(FloatList l) {
			super(l);
		}

		@Override
		public FloatList subList(int from, int to) {
			synchronized (this.sync) {
				return new FloatLists.SynchronizedRandomAccessList(this.list.subList(from, to), this.sync);
			}
		}
	}

	public static class UnmodifiableList extends UnmodifiableCollection implements FloatList, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final FloatList list;

		protected UnmodifiableList(FloatList l) {
			super(l);
			this.list = l;
		}

		@Override
		public float getFloat(int i) {
			return this.list.getFloat(i);
		}

		@Override
		public float set(int i, float k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(int i, float k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float removeFloat(int i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int indexOf(float k) {
			return this.list.indexOf(k);
		}

		@Override
		public int lastIndexOf(float k) {
			return this.list.lastIndexOf(k);
		}

		public boolean addAll(int index, Collection<? extends Float> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void getElements(int from, float[] a, int offset, int length) {
			this.list.getElements(from, a, offset, length);
		}

		@Override
		public void removeElements(int from, int to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, float[] a, int offset, int length) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, float[] a) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void size(int size) {
			this.list.size(size);
		}

		@Override
		public FloatListIterator listIterator() {
			return FloatIterators.unmodifiable(this.list.listIterator());
		}

		@Override
		public FloatListIterator iterator() {
			return this.listIterator();
		}

		@Override
		public FloatListIterator listIterator(int i) {
			return FloatIterators.unmodifiable(this.list.listIterator(i));
		}

		@Override
		public FloatList subList(int from, int to) {
			return new FloatLists.UnmodifiableList(this.list.subList(from, to));
		}

		@Override
		public boolean equals(Object o) {
			return o == this ? true : this.collection.equals(o);
		}

		@Override
		public int hashCode() {
			return this.collection.hashCode();
		}

		public int compareTo(List<? extends Float> o) {
			return this.list.compareTo(o);
		}

		@Override
		public boolean addAll(int index, FloatCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(FloatList l) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int index, FloatList l) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float get(int i) {
			return this.list.get(i);
		}

		@Deprecated
		@Override
		public void add(int i, Float k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float set(int index, Float k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float remove(int i) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public int indexOf(Object o) {
			return this.list.indexOf(o);
		}

		@Deprecated
		@Override
		public int lastIndexOf(Object o) {
			return this.list.lastIndexOf(o);
		}
	}

	public static class UnmodifiableRandomAccessList extends FloatLists.UnmodifiableList implements RandomAccess, Serializable {
		private static final long serialVersionUID = 0L;

		protected UnmodifiableRandomAccessList(FloatList l) {
			super(l);
		}

		@Override
		public FloatList subList(int from, int to) {
			return new FloatLists.UnmodifiableRandomAccessList(this.list.subList(from, to));
		}
	}
}
