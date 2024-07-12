package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.doubles.DoubleCollections.EmptyCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollections.SynchronizedCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection;
import java.io.Serializable;
import java.util.Collection;
import java.util.Random;

public final class DoubleBigLists {
	public static final DoubleBigLists.EmptyBigList EMPTY_BIG_LIST = new DoubleBigLists.EmptyBigList();

	private DoubleBigLists() {
	}

	public static DoubleBigList shuffle(DoubleBigList l, Random random) {
		long i = l.size64();

		while (i-- != 0L) {
			long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
			double t = l.getDouble(i);
			l.set(i, l.getDouble(p));
			l.set(p, t);
		}

		return l;
	}

	public static DoubleBigList singleton(double element) {
		return new DoubleBigLists.Singleton(element);
	}

	public static DoubleBigList singleton(Object element) {
		return new DoubleBigLists.Singleton((Double)element);
	}

	public static DoubleBigList synchronize(DoubleBigList l) {
		return new DoubleBigLists.SynchronizedBigList(l);
	}

	public static DoubleBigList synchronize(DoubleBigList l, Object sync) {
		return new DoubleBigLists.SynchronizedBigList(l, sync);
	}

	public static DoubleBigList unmodifiable(DoubleBigList l) {
		return new DoubleBigLists.UnmodifiableBigList(l);
	}

	public static DoubleBigList asBigList(DoubleList list) {
		return new DoubleBigLists.ListBigList(list);
	}

	public static class EmptyBigList extends EmptyCollection implements DoubleBigList, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyBigList() {
		}

		@Override
		public double getDouble(long i) {
			throw new IndexOutOfBoundsException();
		}

		@Override
		public boolean rem(double k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public double removeDouble(long i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(long index, double k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public double set(long index, double k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long indexOf(double k) {
			return -1L;
		}

		@Override
		public long lastIndexOf(double k) {
			return -1L;
		}

		@Override
		public boolean addAll(long i, Collection<? extends Double> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(DoubleCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(DoubleBigList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(long i, DoubleCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(long i, DoubleBigList c) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public void add(long index, Double k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean add(Double k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Double get(long i) {
			throw new IndexOutOfBoundsException();
		}

		@Deprecated
		@Override
		public Double set(long index, Double k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Double remove(long k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public long indexOf(Object k) {
			return -1L;
		}

		@Deprecated
		@Override
		public long lastIndexOf(Object k) {
			return -1L;
		}

		@Override
		public DoubleBigListIterator listIterator() {
			return DoubleBigListIterators.EMPTY_BIG_LIST_ITERATOR;
		}

		@Override
		public DoubleBigListIterator iterator() {
			return DoubleBigListIterators.EMPTY_BIG_LIST_ITERATOR;
		}

		@Override
		public DoubleBigListIterator listIterator(long i) {
			if (i == 0L) {
				return DoubleBigListIterators.EMPTY_BIG_LIST_ITERATOR;
			} else {
				throw new IndexOutOfBoundsException(String.valueOf(i));
			}
		}

		@Override
		public DoubleBigList subList(long from, long to) {
			if (from == 0L && to == 0L) {
				return this;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public void getElements(long from, double[][] a, long offset, long length) {
			DoubleBigArrays.ensureOffsetLength(a, offset, length);
			if (from != 0L) {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public void removeElements(long from, long to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(long index, double[][] a, long offset, long length) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(long index, double[][] a) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void size(long s) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long size64() {
			return 0L;
		}

		public int compareTo(BigList<? extends Double> o) {
			if (o == this) {
				return 0;
			} else {
				return o.isEmpty() ? 0 : -1;
			}
		}

		public Object clone() {
			return DoubleBigLists.EMPTY_BIG_LIST;
		}

		@Override
		public int hashCode() {
			return 1;
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof BigList && ((BigList)o).isEmpty();
		}

		@Override
		public String toString() {
			return "[]";
		}

		private Object readResolve() {
			return DoubleBigLists.EMPTY_BIG_LIST;
		}
	}

	public static class ListBigList extends AbstractDoubleBigList implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		private final DoubleList list;

		protected ListBigList(DoubleList list) {
			this.list = list;
		}

		private int intIndex(long index) {
			if (index >= 2147483647L) {
				throw new IndexOutOfBoundsException("This big list is restricted to 32-bit indices");
			} else {
				return (int)index;
			}
		}

		@Override
		public long size64() {
			return (long)this.list.size();
		}

		@Override
		public void size(long size) {
			this.list.size(this.intIndex(size));
		}

		@Override
		public DoubleBigListIterator iterator() {
			return DoubleBigListIterators.asBigListIterator(this.list.iterator());
		}

		@Override
		public DoubleBigListIterator listIterator() {
			return DoubleBigListIterators.asBigListIterator(this.list.listIterator());
		}

		@Override
		public DoubleBigListIterator listIterator(long index) {
			return DoubleBigListIterators.asBigListIterator(this.list.listIterator(this.intIndex(index)));
		}

		@Override
		public boolean addAll(long index, Collection<? extends Double> c) {
			return this.list.addAll(this.intIndex(index), c);
		}

		@Override
		public DoubleBigList subList(long from, long to) {
			return new DoubleBigLists.ListBigList(this.list.subList(this.intIndex(from), this.intIndex(to)));
		}

		@Override
		public boolean contains(double key) {
			return this.list.contains(key);
		}

		@Override
		public double[] toDoubleArray() {
			return this.list.toDoubleArray();
		}

		@Override
		public void removeElements(long from, long to) {
			this.list.removeElements(this.intIndex(from), this.intIndex(to));
		}

		@Deprecated
		@Override
		public double[] toDoubleArray(double[] a) {
			return this.list.toArray(a);
		}

		@Override
		public boolean addAll(long index, DoubleCollection c) {
			return this.list.addAll(this.intIndex(index), c);
		}

		@Override
		public boolean addAll(DoubleCollection c) {
			return this.list.addAll(c);
		}

		@Override
		public boolean addAll(long index, DoubleBigList c) {
			return this.list.addAll(this.intIndex(index), c);
		}

		@Override
		public boolean addAll(DoubleBigList c) {
			return this.list.addAll(c);
		}

		@Override
		public boolean containsAll(DoubleCollection c) {
			return this.list.containsAll(c);
		}

		@Override
		public boolean removeAll(DoubleCollection c) {
			return this.list.removeAll(c);
		}

		@Override
		public boolean retainAll(DoubleCollection c) {
			return this.list.retainAll(c);
		}

		@Override
		public void add(long index, double key) {
			this.list.add(this.intIndex(index), key);
		}

		@Override
		public boolean add(double key) {
			return this.list.add(key);
		}

		@Override
		public double getDouble(long index) {
			return this.list.getDouble(this.intIndex(index));
		}

		@Override
		public long indexOf(double k) {
			return (long)this.list.indexOf(k);
		}

		@Override
		public long lastIndexOf(double k) {
			return (long)this.list.lastIndexOf(k);
		}

		@Override
		public double removeDouble(long index) {
			return this.list.removeDouble(this.intIndex(index));
		}

		@Override
		public double set(long index, double k) {
			return this.list.set(this.intIndex(index), k);
		}

		@Override
		public boolean isEmpty() {
			return this.list.isEmpty();
		}

		public <T> T[] toArray(T[] a) {
			return (T[])this.list.toArray(a);
		}

		public boolean containsAll(Collection<?> c) {
			return this.list.containsAll(c);
		}

		@Override
		public boolean addAll(Collection<? extends Double> c) {
			return this.list.addAll(c);
		}

		public boolean removeAll(Collection<?> c) {
			return this.list.removeAll(c);
		}

		public boolean retainAll(Collection<?> c) {
			return this.list.retainAll(c);
		}

		@Override
		public void clear() {
			this.list.clear();
		}

		@Override
		public int hashCode() {
			return this.list.hashCode();
		}
	}

	public static class Singleton extends AbstractDoubleBigList implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		private final double element;

		protected Singleton(double element) {
			this.element = element;
		}

		@Override
		public double getDouble(long i) {
			if (i == 0L) {
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
		public double removeDouble(long i) {
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
		public DoubleBigListIterator listIterator() {
			return DoubleBigListIterators.singleton(this.element);
		}

		@Override
		public DoubleBigListIterator listIterator(long i) {
			if (i <= 1L && i >= 0L) {
				DoubleBigListIterator l = this.listIterator();
				if (i == 1L) {
					l.nextDouble();
				}

				return l;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public DoubleBigList subList(long from, long to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return (DoubleBigList)(from == 0L && to == 1L ? this : DoubleBigLists.EMPTY_BIG_LIST);
			}
		}

		@Override
		public boolean addAll(long i, Collection<? extends Double> c) {
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
		public boolean addAll(DoubleBigList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(long i, DoubleBigList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(long i, DoubleCollection c) {
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

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Override
		public long size64() {
			return 1L;
		}

		public Object clone() {
			return this;
		}
	}

	public static class SynchronizedBigList extends SynchronizedCollection implements DoubleBigList, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final DoubleBigList list;

		protected SynchronizedBigList(DoubleBigList l, Object sync) {
			super(l, sync);
			this.list = l;
		}

		protected SynchronizedBigList(DoubleBigList l) {
			super(l);
			this.list = l;
		}

		@Override
		public double getDouble(long i) {
			synchronized (this.sync) {
				return this.list.getDouble(i);
			}
		}

		@Override
		public double set(long i, double k) {
			synchronized (this.sync) {
				return this.list.set(i, k);
			}
		}

		@Override
		public void add(long i, double k) {
			synchronized (this.sync) {
				this.list.add(i, k);
			}
		}

		@Override
		public double removeDouble(long i) {
			synchronized (this.sync) {
				return this.list.removeDouble(i);
			}
		}

		@Override
		public long indexOf(double k) {
			synchronized (this.sync) {
				return this.list.indexOf(k);
			}
		}

		@Override
		public long lastIndexOf(double k) {
			synchronized (this.sync) {
				return this.list.lastIndexOf(k);
			}
		}

		@Override
		public boolean addAll(long index, Collection<? extends Double> c) {
			synchronized (this.sync) {
				return this.list.addAll(index, c);
			}
		}

		@Override
		public void getElements(long from, double[][] a, long offset, long length) {
			synchronized (this.sync) {
				this.list.getElements(from, a, offset, length);
			}
		}

		@Override
		public void removeElements(long from, long to) {
			synchronized (this.sync) {
				this.list.removeElements(from, to);
			}
		}

		@Override
		public void addElements(long index, double[][] a, long offset, long length) {
			synchronized (this.sync) {
				this.list.addElements(index, a, offset, length);
			}
		}

		@Override
		public void addElements(long index, double[][] a) {
			synchronized (this.sync) {
				this.list.addElements(index, a);
			}
		}

		@Deprecated
		@Override
		public void size(long size) {
			synchronized (this.sync) {
				this.list.size(size);
			}
		}

		@Override
		public long size64() {
			synchronized (this.sync) {
				return this.list.size64();
			}
		}

		@Override
		public DoubleBigListIterator iterator() {
			return this.list.listIterator();
		}

		@Override
		public DoubleBigListIterator listIterator() {
			return this.list.listIterator();
		}

		@Override
		public DoubleBigListIterator listIterator(long i) {
			return this.list.listIterator(i);
		}

		@Override
		public DoubleBigList subList(long from, long to) {
			synchronized (this.sync) {
				return DoubleBigLists.synchronize(this.list.subList(from, to), this.sync);
			}
		}

		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			} else {
				synchronized (this.sync) {
					return this.list.equals(o);
				}
			}
		}

		@Override
		public int hashCode() {
			synchronized (this.sync) {
				return this.list.hashCode();
			}
		}

		public int compareTo(BigList<? extends Double> o) {
			synchronized (this.sync) {
				return this.list.compareTo(o);
			}
		}

		@Override
		public boolean addAll(long index, DoubleCollection c) {
			synchronized (this.sync) {
				return this.list.addAll(index, c);
			}
		}

		@Override
		public boolean addAll(long index, DoubleBigList l) {
			synchronized (this.sync) {
				return this.list.addAll(index, l);
			}
		}

		@Override
		public boolean addAll(DoubleBigList l) {
			synchronized (this.sync) {
				return this.list.addAll(l);
			}
		}

		@Deprecated
		@Override
		public void add(long i, Double k) {
			synchronized (this.sync) {
				this.list.add(i, k);
			}
		}

		@Deprecated
		@Override
		public Double get(long i) {
			synchronized (this.sync) {
				return this.list.get(i);
			}
		}

		@Deprecated
		@Override
		public Double set(long index, Double k) {
			synchronized (this.sync) {
				return this.list.set(index, k);
			}
		}

		@Deprecated
		@Override
		public Double remove(long i) {
			synchronized (this.sync) {
				return this.list.remove(i);
			}
		}

		@Deprecated
		@Override
		public long indexOf(Object o) {
			synchronized (this.sync) {
				return this.list.indexOf(o);
			}
		}

		@Deprecated
		@Override
		public long lastIndexOf(Object o) {
			synchronized (this.sync) {
				return this.list.lastIndexOf(o);
			}
		}
	}

	public static class UnmodifiableBigList extends UnmodifiableCollection implements DoubleBigList, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final DoubleBigList list;

		protected UnmodifiableBigList(DoubleBigList l) {
			super(l);
			this.list = l;
		}

		@Override
		public double getDouble(long i) {
			return this.list.getDouble(i);
		}

		@Override
		public double set(long i, double k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(long i, double k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public double removeDouble(long i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long indexOf(double k) {
			return this.list.indexOf(k);
		}

		@Override
		public long lastIndexOf(double k) {
			return this.list.lastIndexOf(k);
		}

		@Override
		public boolean addAll(long index, Collection<? extends Double> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void getElements(long from, double[][] a, long offset, long length) {
			this.list.getElements(from, a, offset, length);
		}

		@Override
		public void removeElements(long from, long to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(long index, double[][] a, long offset, long length) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(long index, double[][] a) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public void size(long size) {
			this.list.size(size);
		}

		@Override
		public long size64() {
			return this.list.size64();
		}

		@Override
		public DoubleBigListIterator iterator() {
			return this.listIterator();
		}

		@Override
		public DoubleBigListIterator listIterator() {
			return DoubleBigListIterators.unmodifiable(this.list.listIterator());
		}

		@Override
		public DoubleBigListIterator listIterator(long i) {
			return DoubleBigListIterators.unmodifiable(this.list.listIterator(i));
		}

		@Override
		public DoubleBigList subList(long from, long to) {
			return DoubleBigLists.unmodifiable(this.list.subList(from, to));
		}

		@Override
		public boolean equals(Object o) {
			return o == this ? true : this.list.equals(o);
		}

		@Override
		public int hashCode() {
			return this.list.hashCode();
		}

		public int compareTo(BigList<? extends Double> o) {
			return this.list.compareTo(o);
		}

		@Override
		public boolean addAll(long index, DoubleCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(DoubleBigList l) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(long index, DoubleBigList l) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Double get(long i) {
			return this.list.get(i);
		}

		@Deprecated
		@Override
		public void add(long i, Double k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Double set(long index, Double k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Double remove(long i) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public long indexOf(Object o) {
			return this.list.indexOf(o);
		}

		@Deprecated
		@Override
		public long lastIndexOf(Object o) {
			return this.list.lastIndexOf(o);
		}
	}
}
