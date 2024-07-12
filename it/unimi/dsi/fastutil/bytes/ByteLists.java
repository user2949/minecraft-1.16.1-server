package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteCollections.EmptyCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollections.SynchronizedCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollections.UnmodifiableCollection;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;

public final class ByteLists {
	public static final ByteLists.EmptyList EMPTY_LIST = new ByteLists.EmptyList();

	private ByteLists() {
	}

	public static ByteList shuffle(ByteList l, Random random) {
		int i = l.size();

		while (i-- != 0) {
			int p = random.nextInt(i + 1);
			byte t = l.getByte(i);
			l.set(i, l.getByte(p));
			l.set(p, t);
		}

		return l;
	}

	public static ByteList singleton(byte element) {
		return new ByteLists.Singleton(element);
	}

	public static ByteList singleton(Object element) {
		return new ByteLists.Singleton((Byte)element);
	}

	public static ByteList synchronize(ByteList l) {
		return (ByteList)(l instanceof RandomAccess ? new ByteLists.SynchronizedRandomAccessList(l) : new ByteLists.SynchronizedList(l));
	}

	public static ByteList synchronize(ByteList l, Object sync) {
		return (ByteList)(l instanceof RandomAccess ? new ByteLists.SynchronizedRandomAccessList(l, sync) : new ByteLists.SynchronizedList(l, sync));
	}

	public static ByteList unmodifiable(ByteList l) {
		return (ByteList)(l instanceof RandomAccess ? new ByteLists.UnmodifiableRandomAccessList(l) : new ByteLists.UnmodifiableList(l));
	}

	public static class EmptyList extends EmptyCollection implements ByteList, RandomAccess, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyList() {
		}

		@Override
		public byte getByte(int i) {
			throw new IndexOutOfBoundsException();
		}

		@Override
		public boolean rem(byte k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte removeByte(int i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(int index, byte k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte set(int index, byte k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int indexOf(byte k) {
			return -1;
		}

		@Override
		public int lastIndexOf(byte k) {
			return -1;
		}

		public boolean addAll(int i, Collection<? extends Byte> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(ByteList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, ByteCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, ByteList c) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public void add(int index, Byte k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte get(int index) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean add(Byte k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte set(int index, Byte k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte remove(int k) {
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
		public ByteListIterator listIterator() {
			return ByteIterators.EMPTY_ITERATOR;
		}

		@Override
		public ByteListIterator iterator() {
			return ByteIterators.EMPTY_ITERATOR;
		}

		@Override
		public ByteListIterator listIterator(int i) {
			if (i == 0) {
				return ByteIterators.EMPTY_ITERATOR;
			} else {
				throw new IndexOutOfBoundsException(String.valueOf(i));
			}
		}

		@Override
		public ByteList subList(int from, int to) {
			if (from == 0 && to == 0) {
				return this;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public void getElements(int from, byte[] a, int offset, int length) {
			if (from != 0 || length != 0 || offset < 0 || offset > a.length) {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public void removeElements(int from, int to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, byte[] a, int offset, int length) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, byte[] a) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void size(int s) {
			throw new UnsupportedOperationException();
		}

		public int compareTo(List<? extends Byte> o) {
			if (o == this) {
				return 0;
			} else {
				return o.isEmpty() ? 0 : -1;
			}
		}

		public Object clone() {
			return ByteLists.EMPTY_LIST;
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
			return ByteLists.EMPTY_LIST;
		}
	}

	public static class Singleton extends AbstractByteList implements RandomAccess, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		private final byte element;

		protected Singleton(byte element) {
			this.element = element;
		}

		@Override
		public byte getByte(int i) {
			if (i == 0) {
				return this.element;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public boolean rem(byte k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte removeByte(int i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean contains(byte k) {
			return k == this.element;
		}

		@Override
		public byte[] toByteArray() {
			return new byte[]{this.element};
		}

		@Override
		public ByteListIterator listIterator() {
			return ByteIterators.singleton(this.element);
		}

		@Override
		public ByteListIterator iterator() {
			return this.listIterator();
		}

		@Override
		public ByteListIterator listIterator(int i) {
			if (i <= 1 && i >= 0) {
				ByteListIterator l = this.listIterator();
				if (i == 1) {
					l.nextByte();
				}

				return l;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public ByteList subList(int from, int to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return (ByteList)(from == 0 && to == 1 ? this : ByteLists.EMPTY_LIST);
			}
		}

		@Override
		public boolean addAll(int i, Collection<? extends Byte> c) {
			throw new UnsupportedOperationException();
		}

		@Override
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
		public boolean addAll(ByteList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, ByteList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, ByteCollection c) {
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

	public static class SynchronizedList extends SynchronizedCollection implements ByteList, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ByteList list;

		protected SynchronizedList(ByteList l, Object sync) {
			super(l, sync);
			this.list = l;
		}

		protected SynchronizedList(ByteList l) {
			super(l);
			this.list = l;
		}

		@Override
		public byte getByte(int i) {
			synchronized (this.sync) {
				return this.list.getByte(i);
			}
		}

		@Override
		public byte set(int i, byte k) {
			synchronized (this.sync) {
				return this.list.set(i, k);
			}
		}

		@Override
		public void add(int i, byte k) {
			synchronized (this.sync) {
				this.list.add(i, k);
			}
		}

		@Override
		public byte removeByte(int i) {
			synchronized (this.sync) {
				return this.list.removeByte(i);
			}
		}

		@Override
		public int indexOf(byte k) {
			synchronized (this.sync) {
				return this.list.indexOf(k);
			}
		}

		@Override
		public int lastIndexOf(byte k) {
			synchronized (this.sync) {
				return this.list.lastIndexOf(k);
			}
		}

		public boolean addAll(int index, Collection<? extends Byte> c) {
			synchronized (this.sync) {
				return this.list.addAll(index, c);
			}
		}

		@Override
		public void getElements(int from, byte[] a, int offset, int length) {
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
		public void addElements(int index, byte[] a, int offset, int length) {
			synchronized (this.sync) {
				this.list.addElements(index, a, offset, length);
			}
		}

		@Override
		public void addElements(int index, byte[] a) {
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
		public ByteListIterator listIterator() {
			return this.list.listIterator();
		}

		@Override
		public ByteListIterator iterator() {
			return this.listIterator();
		}

		@Override
		public ByteListIterator listIterator(int i) {
			return this.list.listIterator(i);
		}

		@Override
		public ByteList subList(int from, int to) {
			synchronized (this.sync) {
				return new ByteLists.SynchronizedList(this.list.subList(from, to), this.sync);
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

		public int compareTo(List<? extends Byte> o) {
			synchronized (this.sync) {
				return this.list.compareTo(o);
			}
		}

		@Override
		public boolean addAll(int index, ByteCollection c) {
			synchronized (this.sync) {
				return this.list.addAll(index, c);
			}
		}

		@Override
		public boolean addAll(int index, ByteList l) {
			synchronized (this.sync) {
				return this.list.addAll(index, l);
			}
		}

		@Override
		public boolean addAll(ByteList l) {
			synchronized (this.sync) {
				return this.list.addAll(l);
			}
		}

		@Deprecated
		@Override
		public Byte get(int i) {
			synchronized (this.sync) {
				return this.list.get(i);
			}
		}

		@Deprecated
		@Override
		public void add(int i, Byte k) {
			synchronized (this.sync) {
				this.list.add(i, k);
			}
		}

		@Deprecated
		@Override
		public Byte set(int index, Byte k) {
			synchronized (this.sync) {
				return this.list.set(index, k);
			}
		}

		@Deprecated
		@Override
		public Byte remove(int i) {
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

	public static class SynchronizedRandomAccessList extends ByteLists.SynchronizedList implements RandomAccess, Serializable {
		private static final long serialVersionUID = 0L;

		protected SynchronizedRandomAccessList(ByteList l, Object sync) {
			super(l, sync);
		}

		protected SynchronizedRandomAccessList(ByteList l) {
			super(l);
		}

		@Override
		public ByteList subList(int from, int to) {
			synchronized (this.sync) {
				return new ByteLists.SynchronizedRandomAccessList(this.list.subList(from, to), this.sync);
			}
		}
	}

	public static class UnmodifiableList extends UnmodifiableCollection implements ByteList, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ByteList list;

		protected UnmodifiableList(ByteList l) {
			super(l);
			this.list = l;
		}

		@Override
		public byte getByte(int i) {
			return this.list.getByte(i);
		}

		@Override
		public byte set(int i, byte k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(int i, byte k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte removeByte(int i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int indexOf(byte k) {
			return this.list.indexOf(k);
		}

		@Override
		public int lastIndexOf(byte k) {
			return this.list.lastIndexOf(k);
		}

		public boolean addAll(int index, Collection<? extends Byte> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void getElements(int from, byte[] a, int offset, int length) {
			this.list.getElements(from, a, offset, length);
		}

		@Override
		public void removeElements(int from, int to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, byte[] a, int offset, int length) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, byte[] a) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void size(int size) {
			this.list.size(size);
		}

		@Override
		public ByteListIterator listIterator() {
			return ByteIterators.unmodifiable(this.list.listIterator());
		}

		@Override
		public ByteListIterator iterator() {
			return this.listIterator();
		}

		@Override
		public ByteListIterator listIterator(int i) {
			return ByteIterators.unmodifiable(this.list.listIterator(i));
		}

		@Override
		public ByteList subList(int from, int to) {
			return new ByteLists.UnmodifiableList(this.list.subList(from, to));
		}

		@Override
		public boolean equals(Object o) {
			return o == this ? true : this.collection.equals(o);
		}

		@Override
		public int hashCode() {
			return this.collection.hashCode();
		}

		public int compareTo(List<? extends Byte> o) {
			return this.list.compareTo(o);
		}

		@Override
		public boolean addAll(int index, ByteCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(ByteList l) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int index, ByteList l) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte get(int i) {
			return this.list.get(i);
		}

		@Deprecated
		@Override
		public void add(int i, Byte k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte set(int index, Byte k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte remove(int i) {
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

	public static class UnmodifiableRandomAccessList extends ByteLists.UnmodifiableList implements RandomAccess, Serializable {
		private static final long serialVersionUID = 0L;

		protected UnmodifiableRandomAccessList(ByteList l) {
			super(l);
		}

		@Override
		public ByteList subList(int from, int to) {
			return new ByteLists.UnmodifiableRandomAccessList(this.list.subList(from, to));
		}
	}
}
