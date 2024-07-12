package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntCollections.EmptyCollection;
import it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection;
import it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;

public final class IntLists {
	public static final IntLists.EmptyList EMPTY_LIST = new IntLists.EmptyList();

	private IntLists() {
	}

	public static IntList shuffle(IntList l, Random random) {
		int i = l.size();

		while (i-- != 0) {
			int p = random.nextInt(i + 1);
			int t = l.getInt(i);
			l.set(i, l.getInt(p));
			l.set(p, t);
		}

		return l;
	}

	public static IntList singleton(int element) {
		return new IntLists.Singleton(element);
	}

	public static IntList singleton(Object element) {
		return new IntLists.Singleton((Integer)element);
	}

	public static IntList synchronize(IntList l) {
		return (IntList)(l instanceof RandomAccess ? new IntLists.SynchronizedRandomAccessList(l) : new IntLists.SynchronizedList(l));
	}

	public static IntList synchronize(IntList l, Object sync) {
		return (IntList)(l instanceof RandomAccess ? new IntLists.SynchronizedRandomAccessList(l, sync) : new IntLists.SynchronizedList(l, sync));
	}

	public static IntList unmodifiable(IntList l) {
		return (IntList)(l instanceof RandomAccess ? new IntLists.UnmodifiableRandomAccessList(l) : new IntLists.UnmodifiableList(l));
	}

	public static class EmptyList extends EmptyCollection implements IntList, RandomAccess, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyList() {
		}

		@Override
		public int getInt(int i) {
			throw new IndexOutOfBoundsException();
		}

		@Override
		public boolean rem(int k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int removeInt(int i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(int index, int k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int set(int index, int k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int indexOf(int k) {
			return -1;
		}

		@Override
		public int lastIndexOf(int k) {
			return -1;
		}

		public boolean addAll(int i, Collection<? extends Integer> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(IntList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, IntCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, IntList c) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public void add(int index, Integer k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer get(int index) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean add(Integer k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer set(int index, Integer k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer remove(int k) {
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
		public IntListIterator listIterator() {
			return IntIterators.EMPTY_ITERATOR;
		}

		@Override
		public IntListIterator iterator() {
			return IntIterators.EMPTY_ITERATOR;
		}

		@Override
		public IntListIterator listIterator(int i) {
			if (i == 0) {
				return IntIterators.EMPTY_ITERATOR;
			} else {
				throw new IndexOutOfBoundsException(String.valueOf(i));
			}
		}

		@Override
		public IntList subList(int from, int to) {
			if (from == 0 && to == 0) {
				return this;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public void getElements(int from, int[] a, int offset, int length) {
			if (from != 0 || length != 0 || offset < 0 || offset > a.length) {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public void removeElements(int from, int to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, int[] a, int offset, int length) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, int[] a) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void size(int s) {
			throw new UnsupportedOperationException();
		}

		public int compareTo(List<? extends Integer> o) {
			if (o == this) {
				return 0;
			} else {
				return o.isEmpty() ? 0 : -1;
			}
		}

		public Object clone() {
			return IntLists.EMPTY_LIST;
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
			return IntLists.EMPTY_LIST;
		}
	}

	public static class Singleton extends AbstractIntList implements RandomAccess, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		private final int element;

		protected Singleton(int element) {
			this.element = element;
		}

		@Override
		public int getInt(int i) {
			if (i == 0) {
				return this.element;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public boolean rem(int k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int removeInt(int i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean contains(int k) {
			return k == this.element;
		}

		@Override
		public int[] toIntArray() {
			return new int[]{this.element};
		}

		@Override
		public IntListIterator listIterator() {
			return IntIterators.singleton(this.element);
		}

		@Override
		public IntListIterator iterator() {
			return this.listIterator();
		}

		@Override
		public IntListIterator listIterator(int i) {
			if (i <= 1 && i >= 0) {
				IntListIterator l = this.listIterator();
				if (i == 1) {
					l.nextInt();
				}

				return l;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public IntList subList(int from, int to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return (IntList)(from == 0 && to == 1 ? this : IntLists.EMPTY_LIST);
			}
		}

		@Override
		public boolean addAll(int i, Collection<? extends Integer> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(Collection<? extends Integer> c) {
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(IntList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, IntList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, IntCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(IntCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(IntCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(IntCollection c) {
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

	public static class SynchronizedList extends SynchronizedCollection implements IntList, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final IntList list;

		protected SynchronizedList(IntList l, Object sync) {
			super(l, sync);
			this.list = l;
		}

		protected SynchronizedList(IntList l) {
			super(l);
			this.list = l;
		}

		@Override
		public int getInt(int i) {
			synchronized (this.sync) {
				return this.list.getInt(i);
			}
		}

		@Override
		public int set(int i, int k) {
			synchronized (this.sync) {
				return this.list.set(i, k);
			}
		}

		@Override
		public void add(int i, int k) {
			synchronized (this.sync) {
				this.list.add(i, k);
			}
		}

		@Override
		public int removeInt(int i) {
			synchronized (this.sync) {
				return this.list.removeInt(i);
			}
		}

		@Override
		public int indexOf(int k) {
			synchronized (this.sync) {
				return this.list.indexOf(k);
			}
		}

		@Override
		public int lastIndexOf(int k) {
			synchronized (this.sync) {
				return this.list.lastIndexOf(k);
			}
		}

		public boolean addAll(int index, Collection<? extends Integer> c) {
			synchronized (this.sync) {
				return this.list.addAll(index, c);
			}
		}

		@Override
		public void getElements(int from, int[] a, int offset, int length) {
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
		public void addElements(int index, int[] a, int offset, int length) {
			synchronized (this.sync) {
				this.list.addElements(index, a, offset, length);
			}
		}

		@Override
		public void addElements(int index, int[] a) {
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
		public IntListIterator listIterator() {
			return this.list.listIterator();
		}

		@Override
		public IntListIterator iterator() {
			return this.listIterator();
		}

		@Override
		public IntListIterator listIterator(int i) {
			return this.list.listIterator(i);
		}

		@Override
		public IntList subList(int from, int to) {
			synchronized (this.sync) {
				return new IntLists.SynchronizedList(this.list.subList(from, to), this.sync);
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

		public int compareTo(List<? extends Integer> o) {
			synchronized (this.sync) {
				return this.list.compareTo(o);
			}
		}

		@Override
		public boolean addAll(int index, IntCollection c) {
			synchronized (this.sync) {
				return this.list.addAll(index, c);
			}
		}

		@Override
		public boolean addAll(int index, IntList l) {
			synchronized (this.sync) {
				return this.list.addAll(index, l);
			}
		}

		@Override
		public boolean addAll(IntList l) {
			synchronized (this.sync) {
				return this.list.addAll(l);
			}
		}

		@Deprecated
		@Override
		public Integer get(int i) {
			synchronized (this.sync) {
				return this.list.get(i);
			}
		}

		@Deprecated
		@Override
		public void add(int i, Integer k) {
			synchronized (this.sync) {
				this.list.add(i, k);
			}
		}

		@Deprecated
		@Override
		public Integer set(int index, Integer k) {
			synchronized (this.sync) {
				return this.list.set(index, k);
			}
		}

		@Deprecated
		@Override
		public Integer remove(int i) {
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

	public static class SynchronizedRandomAccessList extends IntLists.SynchronizedList implements RandomAccess, Serializable {
		private static final long serialVersionUID = 0L;

		protected SynchronizedRandomAccessList(IntList l, Object sync) {
			super(l, sync);
		}

		protected SynchronizedRandomAccessList(IntList l) {
			super(l);
		}

		@Override
		public IntList subList(int from, int to) {
			synchronized (this.sync) {
				return new IntLists.SynchronizedRandomAccessList(this.list.subList(from, to), this.sync);
			}
		}
	}

	public static class UnmodifiableList extends UnmodifiableCollection implements IntList, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final IntList list;

		protected UnmodifiableList(IntList l) {
			super(l);
			this.list = l;
		}

		@Override
		public int getInt(int i) {
			return this.list.getInt(i);
		}

		@Override
		public int set(int i, int k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(int i, int k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int removeInt(int i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int indexOf(int k) {
			return this.list.indexOf(k);
		}

		@Override
		public int lastIndexOf(int k) {
			return this.list.lastIndexOf(k);
		}

		public boolean addAll(int index, Collection<? extends Integer> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void getElements(int from, int[] a, int offset, int length) {
			this.list.getElements(from, a, offset, length);
		}

		@Override
		public void removeElements(int from, int to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, int[] a, int offset, int length) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, int[] a) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void size(int size) {
			this.list.size(size);
		}

		@Override
		public IntListIterator listIterator() {
			return IntIterators.unmodifiable(this.list.listIterator());
		}

		@Override
		public IntListIterator iterator() {
			return this.listIterator();
		}

		@Override
		public IntListIterator listIterator(int i) {
			return IntIterators.unmodifiable(this.list.listIterator(i));
		}

		@Override
		public IntList subList(int from, int to) {
			return new IntLists.UnmodifiableList(this.list.subList(from, to));
		}

		@Override
		public boolean equals(Object o) {
			return o == this ? true : this.collection.equals(o);
		}

		@Override
		public int hashCode() {
			return this.collection.hashCode();
		}

		public int compareTo(List<? extends Integer> o) {
			return this.list.compareTo(o);
		}

		@Override
		public boolean addAll(int index, IntCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(IntList l) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int index, IntList l) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer get(int i) {
			return this.list.get(i);
		}

		@Deprecated
		@Override
		public void add(int i, Integer k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer set(int index, Integer k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer remove(int i) {
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

	public static class UnmodifiableRandomAccessList extends IntLists.UnmodifiableList implements RandomAccess, Serializable {
		private static final long serialVersionUID = 0L;

		protected UnmodifiableRandomAccessList(IntList l) {
			super(l);
		}

		@Override
		public IntList subList(int from, int to) {
			return new IntLists.UnmodifiableRandomAccessList(this.list.subList(from, to));
		}
	}
}
