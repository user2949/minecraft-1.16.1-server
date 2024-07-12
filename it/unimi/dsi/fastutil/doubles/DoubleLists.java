package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleCollections.EmptyCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollections.SynchronizedCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;

public final class DoubleLists {
	public static final DoubleLists.EmptyList EMPTY_LIST = new DoubleLists.EmptyList();

	private DoubleLists() {
	}

	public static DoubleList shuffle(DoubleList l, Random random) {
		int i = l.size();

		while (i-- != 0) {
			int p = random.nextInt(i + 1);
			double t = l.getDouble(i);
			l.set(i, l.getDouble(p));
			l.set(p, t);
		}

		return l;
	}

	public static DoubleList singleton(double element) {
		return new DoubleLists.Singleton(element);
	}

	public static DoubleList singleton(Object element) {
		return new DoubleLists.Singleton((Double)element);
	}

	public static DoubleList synchronize(DoubleList l) {
		return (DoubleList)(l instanceof RandomAccess ? new DoubleLists.SynchronizedRandomAccessList(l) : new DoubleLists.SynchronizedList(l));
	}

	public static DoubleList synchronize(DoubleList l, Object sync) {
		return (DoubleList)(l instanceof RandomAccess ? new DoubleLists.SynchronizedRandomAccessList(l, sync) : new DoubleLists.SynchronizedList(l, sync));
	}

	public static DoubleList unmodifiable(DoubleList l) {
		return (DoubleList)(l instanceof RandomAccess ? new DoubleLists.UnmodifiableRandomAccessList(l) : new DoubleLists.UnmodifiableList(l));
	}

	public static class EmptyList extends EmptyCollection implements DoubleList, RandomAccess, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyList() {
		}

		@Override
		public double getDouble(int i) {
			throw new IndexOutOfBoundsException();
		}

		@Override
		public boolean rem(double k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public double removeDouble(int i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(int index, double k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public double set(int index, double k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int indexOf(double k) {
			return -1;
		}

		@Override
		public int lastIndexOf(double k) {
			return -1;
		}

		public boolean addAll(int i, Collection<? extends Double> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(DoubleList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, DoubleCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, DoubleList c) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public void add(int index, Double k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Double get(int index) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean add(Double k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Double set(int index, Double k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Double remove(int k) {
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
		public DoubleListIterator listIterator() {
			return DoubleIterators.EMPTY_ITERATOR;
		}

		@Override
		public DoubleListIterator iterator() {
			return DoubleIterators.EMPTY_ITERATOR;
		}

		@Override
		public DoubleListIterator listIterator(int i) {
			if (i == 0) {
				return DoubleIterators.EMPTY_ITERATOR;
			} else {
				throw new IndexOutOfBoundsException(String.valueOf(i));
			}
		}

		@Override
		public DoubleList subList(int from, int to) {
			if (from == 0 && to == 0) {
				return this;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public void getElements(int from, double[] a, int offset, int length) {
			if (from != 0 || length != 0 || offset < 0 || offset > a.length) {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public void removeElements(int from, int to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, double[] a, int offset, int length) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, double[] a) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void size(int s) {
			throw new UnsupportedOperationException();
		}

		public int compareTo(List<? extends Double> o) {
			if (o == this) {
				return 0;
			} else {
				return o.isEmpty() ? 0 : -1;
			}
		}

		public Object clone() {
			return DoubleLists.EMPTY_LIST;
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
			return DoubleLists.EMPTY_LIST;
		}
	}

	public static class Singleton extends AbstractDoubleList implements RandomAccess, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		private final double element;

		protected Singleton(double element) {
			this.element = element;
		}

		@Override
		public double getDouble(int i) {
			if (i == 0) {
				return this.element;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public boolean rem(double k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public double removeDouble(int i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean contains(double k) {
			return Double.doubleToLongBits(k) == Double.doubleToLongBits(this.element);
		}

		@Override
		public double[] toDoubleArray() {
			return new double[]{this.element};
		}

		@Override
		public DoubleListIterator listIterator() {
			return DoubleIterators.singleton(this.element);
		}

		@Override
		public DoubleListIterator iterator() {
			return this.listIterator();
		}

		@Override
		public DoubleListIterator listIterator(int i) {
			if (i <= 1 && i >= 0) {
				DoubleListIterator l = this.listIterator();
				if (i == 1) {
					l.nextDouble();
				}

				return l;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public DoubleList subList(int from, int to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return (DoubleList)(from == 0 && to == 1 ? this : DoubleLists.EMPTY_LIST);
			}
		}

		@Override
		public boolean addAll(int i, Collection<? extends Double> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(Collection<? extends Double> c) {
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(DoubleList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, DoubleList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, DoubleCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(DoubleCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(DoubleCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(DoubleCollection c) {
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

	public static class SynchronizedList extends SynchronizedCollection implements DoubleList, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final DoubleList list;

		protected SynchronizedList(DoubleList l, Object sync) {
			super(l, sync);
			this.list = l;
		}

		protected SynchronizedList(DoubleList l) {
			super(l);
			this.list = l;
		}

		@Override
		public double getDouble(int i) {
			synchronized (this.sync) {
				return this.list.getDouble(i);
			}
		}

		@Override
		public double set(int i, double k) {
			synchronized (this.sync) {
				return this.list.set(i, k);
			}
		}

		@Override
		public void add(int i, double k) {
			synchronized (this.sync) {
				this.list.add(i, k);
			}
		}

		@Override
		public double removeDouble(int i) {
			synchronized (this.sync) {
				return this.list.removeDouble(i);
			}
		}

		@Override
		public int indexOf(double k) {
			synchronized (this.sync) {
				return this.list.indexOf(k);
			}
		}

		@Override
		public int lastIndexOf(double k) {
			synchronized (this.sync) {
				return this.list.lastIndexOf(k);
			}
		}

		public boolean addAll(int index, Collection<? extends Double> c) {
			synchronized (this.sync) {
				return this.list.addAll(index, c);
			}
		}

		@Override
		public void getElements(int from, double[] a, int offset, int length) {
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
		public void addElements(int index, double[] a, int offset, int length) {
			synchronized (this.sync) {
				this.list.addElements(index, a, offset, length);
			}
		}

		@Override
		public void addElements(int index, double[] a) {
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
		public DoubleListIterator listIterator() {
			return this.list.listIterator();
		}

		@Override
		public DoubleListIterator iterator() {
			return this.listIterator();
		}

		@Override
		public DoubleListIterator listIterator(int i) {
			return this.list.listIterator(i);
		}

		@Override
		public DoubleList subList(int from, int to) {
			synchronized (this.sync) {
				return new DoubleLists.SynchronizedList(this.list.subList(from, to), this.sync);
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

		public int compareTo(List<? extends Double> o) {
			synchronized (this.sync) {
				return this.list.compareTo(o);
			}
		}

		@Override
		public boolean addAll(int index, DoubleCollection c) {
			synchronized (this.sync) {
				return this.list.addAll(index, c);
			}
		}

		@Override
		public boolean addAll(int index, DoubleList l) {
			synchronized (this.sync) {
				return this.list.addAll(index, l);
			}
		}

		@Override
		public boolean addAll(DoubleList l) {
			synchronized (this.sync) {
				return this.list.addAll(l);
			}
		}

		@Deprecated
		@Override
		public Double get(int i) {
			synchronized (this.sync) {
				return this.list.get(i);
			}
		}

		@Deprecated
		@Override
		public void add(int i, Double k) {
			synchronized (this.sync) {
				this.list.add(i, k);
			}
		}

		@Deprecated
		@Override
		public Double set(int index, Double k) {
			synchronized (this.sync) {
				return this.list.set(index, k);
			}
		}

		@Deprecated
		@Override
		public Double remove(int i) {
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

	public static class SynchronizedRandomAccessList extends DoubleLists.SynchronizedList implements RandomAccess, Serializable {
		private static final long serialVersionUID = 0L;

		protected SynchronizedRandomAccessList(DoubleList l, Object sync) {
			super(l, sync);
		}

		protected SynchronizedRandomAccessList(DoubleList l) {
			super(l);
		}

		@Override
		public DoubleList subList(int from, int to) {
			synchronized (this.sync) {
				return new DoubleLists.SynchronizedRandomAccessList(this.list.subList(from, to), this.sync);
			}
		}
	}

	public static class UnmodifiableList extends UnmodifiableCollection implements DoubleList, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final DoubleList list;

		protected UnmodifiableList(DoubleList l) {
			super(l);
			this.list = l;
		}

		@Override
		public double getDouble(int i) {
			return this.list.getDouble(i);
		}

		@Override
		public double set(int i, double k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(int i, double k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public double removeDouble(int i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int indexOf(double k) {
			return this.list.indexOf(k);
		}

		@Override
		public int lastIndexOf(double k) {
			return this.list.lastIndexOf(k);
		}

		public boolean addAll(int index, Collection<? extends Double> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void getElements(int from, double[] a, int offset, int length) {
			this.list.getElements(from, a, offset, length);
		}

		@Override
		public void removeElements(int from, int to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, double[] a, int offset, int length) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, double[] a) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void size(int size) {
			this.list.size(size);
		}

		@Override
		public DoubleListIterator listIterator() {
			return DoubleIterators.unmodifiable(this.list.listIterator());
		}

		@Override
		public DoubleListIterator iterator() {
			return this.listIterator();
		}

		@Override
		public DoubleListIterator listIterator(int i) {
			return DoubleIterators.unmodifiable(this.list.listIterator(i));
		}

		@Override
		public DoubleList subList(int from, int to) {
			return new DoubleLists.UnmodifiableList(this.list.subList(from, to));
		}

		@Override
		public boolean equals(Object o) {
			return o == this ? true : this.collection.equals(o);
		}

		@Override
		public int hashCode() {
			return this.collection.hashCode();
		}

		public int compareTo(List<? extends Double> o) {
			return this.list.compareTo(o);
		}

		@Override
		public boolean addAll(int index, DoubleCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(DoubleList l) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int index, DoubleList l) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Double get(int i) {
			return this.list.get(i);
		}

		@Deprecated
		@Override
		public void add(int i, Double k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Double set(int index, Double k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Double remove(int i) {
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

	public static class UnmodifiableRandomAccessList extends DoubleLists.UnmodifiableList implements RandomAccess, Serializable {
		private static final long serialVersionUID = 0L;

		protected UnmodifiableRandomAccessList(DoubleList l) {
			super(l);
		}

		@Override
		public DoubleList subList(int from, int to) {
			return new DoubleLists.UnmodifiableRandomAccessList(this.list.subList(from, to));
		}
	}
}
