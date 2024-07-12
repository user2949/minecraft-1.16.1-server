package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.BooleanCollections.EmptyCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollections.SynchronizedCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollections.UnmodifiableCollection;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;

public final class BooleanLists {
	public static final BooleanLists.EmptyList EMPTY_LIST = new BooleanLists.EmptyList();

	private BooleanLists() {
	}

	public static BooleanList shuffle(BooleanList l, Random random) {
		int i = l.size();

		while (i-- != 0) {
			int p = random.nextInt(i + 1);
			boolean t = l.getBoolean(i);
			l.set(i, l.getBoolean(p));
			l.set(p, t);
		}

		return l;
	}

	public static BooleanList singleton(boolean element) {
		return new BooleanLists.Singleton(element);
	}

	public static BooleanList singleton(Object element) {
		return new BooleanLists.Singleton((Boolean)element);
	}

	public static BooleanList synchronize(BooleanList l) {
		return (BooleanList)(l instanceof RandomAccess ? new BooleanLists.SynchronizedRandomAccessList(l) : new BooleanLists.SynchronizedList(l));
	}

	public static BooleanList synchronize(BooleanList l, Object sync) {
		return (BooleanList)(l instanceof RandomAccess ? new BooleanLists.SynchronizedRandomAccessList(l, sync) : new BooleanLists.SynchronizedList(l, sync));
	}

	public static BooleanList unmodifiable(BooleanList l) {
		return (BooleanList)(l instanceof RandomAccess ? new BooleanLists.UnmodifiableRandomAccessList(l) : new BooleanLists.UnmodifiableList(l));
	}

	public static class EmptyList extends EmptyCollection implements BooleanList, RandomAccess, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyList() {
		}

		@Override
		public boolean getBoolean(int i) {
			throw new IndexOutOfBoundsException();
		}

		@Override
		public boolean rem(boolean k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeBoolean(int i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(int index, boolean k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean set(int index, boolean k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int indexOf(boolean k) {
			return -1;
		}

		@Override
		public int lastIndexOf(boolean k) {
			return -1;
		}

		public boolean addAll(int i, Collection<? extends Boolean> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(BooleanList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, BooleanCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, BooleanList c) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public void add(int index, Boolean k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean get(int index) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean add(Boolean k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean set(int index, Boolean k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean remove(int k) {
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
		public BooleanListIterator listIterator() {
			return BooleanIterators.EMPTY_ITERATOR;
		}

		@Override
		public BooleanListIterator iterator() {
			return BooleanIterators.EMPTY_ITERATOR;
		}

		@Override
		public BooleanListIterator listIterator(int i) {
			if (i == 0) {
				return BooleanIterators.EMPTY_ITERATOR;
			} else {
				throw new IndexOutOfBoundsException(String.valueOf(i));
			}
		}

		@Override
		public BooleanList subList(int from, int to) {
			if (from == 0 && to == 0) {
				return this;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public void getElements(int from, boolean[] a, int offset, int length) {
			if (from != 0 || length != 0 || offset < 0 || offset > a.length) {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public void removeElements(int from, int to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, boolean[] a, int offset, int length) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, boolean[] a) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void size(int s) {
			throw new UnsupportedOperationException();
		}

		public int compareTo(List<? extends Boolean> o) {
			if (o == this) {
				return 0;
			} else {
				return o.isEmpty() ? 0 : -1;
			}
		}

		public Object clone() {
			return BooleanLists.EMPTY_LIST;
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
			return BooleanLists.EMPTY_LIST;
		}
	}

	public static class Singleton extends AbstractBooleanList implements RandomAccess, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		private final boolean element;

		protected Singleton(boolean element) {
			this.element = element;
		}

		@Override
		public boolean getBoolean(int i) {
			if (i == 0) {
				return this.element;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public boolean rem(boolean k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeBoolean(int i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean contains(boolean k) {
			return k == this.element;
		}

		@Override
		public boolean[] toBooleanArray() {
			return new boolean[]{this.element};
		}

		@Override
		public BooleanListIterator listIterator() {
			return BooleanIterators.singleton(this.element);
		}

		@Override
		public BooleanListIterator iterator() {
			return this.listIterator();
		}

		@Override
		public BooleanListIterator listIterator(int i) {
			if (i <= 1 && i >= 0) {
				BooleanListIterator l = this.listIterator();
				if (i == 1) {
					l.nextBoolean();
				}

				return l;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public BooleanList subList(int from, int to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return (BooleanList)(from == 0 && to == 1 ? this : BooleanLists.EMPTY_LIST);
			}
		}

		@Override
		public boolean addAll(int i, Collection<? extends Boolean> c) {
			throw new UnsupportedOperationException();
		}

		@Override
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
		public boolean addAll(BooleanList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, BooleanList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, BooleanCollection c) {
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

	public static class SynchronizedList extends SynchronizedCollection implements BooleanList, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final BooleanList list;

		protected SynchronizedList(BooleanList l, Object sync) {
			super(l, sync);
			this.list = l;
		}

		protected SynchronizedList(BooleanList l) {
			super(l);
			this.list = l;
		}

		@Override
		public boolean getBoolean(int i) {
			synchronized (this.sync) {
				return this.list.getBoolean(i);
			}
		}

		@Override
		public boolean set(int i, boolean k) {
			synchronized (this.sync) {
				return this.list.set(i, k);
			}
		}

		@Override
		public void add(int i, boolean k) {
			synchronized (this.sync) {
				this.list.add(i, k);
			}
		}

		@Override
		public boolean removeBoolean(int i) {
			synchronized (this.sync) {
				return this.list.removeBoolean(i);
			}
		}

		@Override
		public int indexOf(boolean k) {
			synchronized (this.sync) {
				return this.list.indexOf(k);
			}
		}

		@Override
		public int lastIndexOf(boolean k) {
			synchronized (this.sync) {
				return this.list.lastIndexOf(k);
			}
		}

		public boolean addAll(int index, Collection<? extends Boolean> c) {
			synchronized (this.sync) {
				return this.list.addAll(index, c);
			}
		}

		@Override
		public void getElements(int from, boolean[] a, int offset, int length) {
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
		public void addElements(int index, boolean[] a, int offset, int length) {
			synchronized (this.sync) {
				this.list.addElements(index, a, offset, length);
			}
		}

		@Override
		public void addElements(int index, boolean[] a) {
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
		public BooleanListIterator listIterator() {
			return this.list.listIterator();
		}

		@Override
		public BooleanListIterator iterator() {
			return this.listIterator();
		}

		@Override
		public BooleanListIterator listIterator(int i) {
			return this.list.listIterator(i);
		}

		@Override
		public BooleanList subList(int from, int to) {
			synchronized (this.sync) {
				return new BooleanLists.SynchronizedList(this.list.subList(from, to), this.sync);
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

		public int compareTo(List<? extends Boolean> o) {
			synchronized (this.sync) {
				return this.list.compareTo(o);
			}
		}

		@Override
		public boolean addAll(int index, BooleanCollection c) {
			synchronized (this.sync) {
				return this.list.addAll(index, c);
			}
		}

		@Override
		public boolean addAll(int index, BooleanList l) {
			synchronized (this.sync) {
				return this.list.addAll(index, l);
			}
		}

		@Override
		public boolean addAll(BooleanList l) {
			synchronized (this.sync) {
				return this.list.addAll(l);
			}
		}

		@Deprecated
		@Override
		public Boolean get(int i) {
			synchronized (this.sync) {
				return this.list.get(i);
			}
		}

		@Deprecated
		@Override
		public void add(int i, Boolean k) {
			synchronized (this.sync) {
				this.list.add(i, k);
			}
		}

		@Deprecated
		@Override
		public Boolean set(int index, Boolean k) {
			synchronized (this.sync) {
				return this.list.set(index, k);
			}
		}

		@Deprecated
		@Override
		public Boolean remove(int i) {
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

	public static class SynchronizedRandomAccessList extends BooleanLists.SynchronizedList implements RandomAccess, Serializable {
		private static final long serialVersionUID = 0L;

		protected SynchronizedRandomAccessList(BooleanList l, Object sync) {
			super(l, sync);
		}

		protected SynchronizedRandomAccessList(BooleanList l) {
			super(l);
		}

		@Override
		public BooleanList subList(int from, int to) {
			synchronized (this.sync) {
				return new BooleanLists.SynchronizedRandomAccessList(this.list.subList(from, to), this.sync);
			}
		}
	}

	public static class UnmodifiableList extends UnmodifiableCollection implements BooleanList, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final BooleanList list;

		protected UnmodifiableList(BooleanList l) {
			super(l);
			this.list = l;
		}

		@Override
		public boolean getBoolean(int i) {
			return this.list.getBoolean(i);
		}

		@Override
		public boolean set(int i, boolean k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(int i, boolean k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeBoolean(int i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int indexOf(boolean k) {
			return this.list.indexOf(k);
		}

		@Override
		public int lastIndexOf(boolean k) {
			return this.list.lastIndexOf(k);
		}

		public boolean addAll(int index, Collection<? extends Boolean> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void getElements(int from, boolean[] a, int offset, int length) {
			this.list.getElements(from, a, offset, length);
		}

		@Override
		public void removeElements(int from, int to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, boolean[] a, int offset, int length) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, boolean[] a) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void size(int size) {
			this.list.size(size);
		}

		@Override
		public BooleanListIterator listIterator() {
			return BooleanIterators.unmodifiable(this.list.listIterator());
		}

		@Override
		public BooleanListIterator iterator() {
			return this.listIterator();
		}

		@Override
		public BooleanListIterator listIterator(int i) {
			return BooleanIterators.unmodifiable(this.list.listIterator(i));
		}

		@Override
		public BooleanList subList(int from, int to) {
			return new BooleanLists.UnmodifiableList(this.list.subList(from, to));
		}

		@Override
		public boolean equals(Object o) {
			return o == this ? true : this.collection.equals(o);
		}

		@Override
		public int hashCode() {
			return this.collection.hashCode();
		}

		public int compareTo(List<? extends Boolean> o) {
			return this.list.compareTo(o);
		}

		@Override
		public boolean addAll(int index, BooleanCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(BooleanList l) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int index, BooleanList l) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean get(int i) {
			return this.list.get(i);
		}

		@Deprecated
		@Override
		public void add(int i, Boolean k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean set(int index, Boolean k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean remove(int i) {
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

	public static class UnmodifiableRandomAccessList extends BooleanLists.UnmodifiableList implements RandomAccess, Serializable {
		private static final long serialVersionUID = 0L;

		protected UnmodifiableRandomAccessList(BooleanList l) {
			super(l);
		}

		@Override
		public BooleanList subList(int from, int to) {
			return new BooleanLists.UnmodifiableRandomAccessList(this.list.subList(from, to));
		}
	}
}
