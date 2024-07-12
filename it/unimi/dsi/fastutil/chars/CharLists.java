package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharCollections.EmptyCollection;
import it.unimi.dsi.fastutil.chars.CharCollections.SynchronizedCollection;
import it.unimi.dsi.fastutil.chars.CharCollections.UnmodifiableCollection;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;

public final class CharLists {
	public static final CharLists.EmptyList EMPTY_LIST = new CharLists.EmptyList();

	private CharLists() {
	}

	public static CharList shuffle(CharList l, Random random) {
		int i = l.size();

		while (i-- != 0) {
			int p = random.nextInt(i + 1);
			char t = l.getChar(i);
			l.set(i, l.getChar(p));
			l.set(p, t);
		}

		return l;
	}

	public static CharList singleton(char element) {
		return new CharLists.Singleton(element);
	}

	public static CharList singleton(Object element) {
		return new CharLists.Singleton((Character)element);
	}

	public static CharList synchronize(CharList l) {
		return (CharList)(l instanceof RandomAccess ? new CharLists.SynchronizedRandomAccessList(l) : new CharLists.SynchronizedList(l));
	}

	public static CharList synchronize(CharList l, Object sync) {
		return (CharList)(l instanceof RandomAccess ? new CharLists.SynchronizedRandomAccessList(l, sync) : new CharLists.SynchronizedList(l, sync));
	}

	public static CharList unmodifiable(CharList l) {
		return (CharList)(l instanceof RandomAccess ? new CharLists.UnmodifiableRandomAccessList(l) : new CharLists.UnmodifiableList(l));
	}

	public static class EmptyList extends EmptyCollection implements CharList, RandomAccess, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyList() {
		}

		@Override
		public char getChar(int i) {
			throw new IndexOutOfBoundsException();
		}

		@Override
		public boolean rem(char k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char removeChar(int i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(int index, char k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char set(int index, char k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int indexOf(char k) {
			return -1;
		}

		@Override
		public int lastIndexOf(char k) {
			return -1;
		}

		public boolean addAll(int i, Collection<? extends Character> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(CharList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, CharCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, CharList c) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public void add(int index, Character k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character get(int index) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean add(Character k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character set(int index, Character k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character remove(int k) {
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
		public CharListIterator listIterator() {
			return CharIterators.EMPTY_ITERATOR;
		}

		@Override
		public CharListIterator iterator() {
			return CharIterators.EMPTY_ITERATOR;
		}

		@Override
		public CharListIterator listIterator(int i) {
			if (i == 0) {
				return CharIterators.EMPTY_ITERATOR;
			} else {
				throw new IndexOutOfBoundsException(String.valueOf(i));
			}
		}

		@Override
		public CharList subList(int from, int to) {
			if (from == 0 && to == 0) {
				return this;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public void getElements(int from, char[] a, int offset, int length) {
			if (from != 0 || length != 0 || offset < 0 || offset > a.length) {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public void removeElements(int from, int to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, char[] a, int offset, int length) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, char[] a) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void size(int s) {
			throw new UnsupportedOperationException();
		}

		public int compareTo(List<? extends Character> o) {
			if (o == this) {
				return 0;
			} else {
				return o.isEmpty() ? 0 : -1;
			}
		}

		public Object clone() {
			return CharLists.EMPTY_LIST;
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
			return CharLists.EMPTY_LIST;
		}
	}

	public static class Singleton extends AbstractCharList implements RandomAccess, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		private final char element;

		protected Singleton(char element) {
			this.element = element;
		}

		@Override
		public char getChar(int i) {
			if (i == 0) {
				return this.element;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public boolean rem(char k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char removeChar(int i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean contains(char k) {
			return k == this.element;
		}

		@Override
		public char[] toCharArray() {
			return new char[]{this.element};
		}

		@Override
		public CharListIterator listIterator() {
			return CharIterators.singleton(this.element);
		}

		@Override
		public CharListIterator iterator() {
			return this.listIterator();
		}

		@Override
		public CharListIterator listIterator(int i) {
			if (i <= 1 && i >= 0) {
				CharListIterator l = this.listIterator();
				if (i == 1) {
					l.nextChar();
				}

				return l;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public CharList subList(int from, int to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return (CharList)(from == 0 && to == 1 ? this : CharLists.EMPTY_LIST);
			}
		}

		@Override
		public boolean addAll(int i, Collection<? extends Character> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(Collection<? extends Character> c) {
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(CharList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, CharList c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int i, CharCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(CharCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(CharCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(CharCollection c) {
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

	public static class SynchronizedList extends SynchronizedCollection implements CharList, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final CharList list;

		protected SynchronizedList(CharList l, Object sync) {
			super(l, sync);
			this.list = l;
		}

		protected SynchronizedList(CharList l) {
			super(l);
			this.list = l;
		}

		@Override
		public char getChar(int i) {
			synchronized (this.sync) {
				return this.list.getChar(i);
			}
		}

		@Override
		public char set(int i, char k) {
			synchronized (this.sync) {
				return this.list.set(i, k);
			}
		}

		@Override
		public void add(int i, char k) {
			synchronized (this.sync) {
				this.list.add(i, k);
			}
		}

		@Override
		public char removeChar(int i) {
			synchronized (this.sync) {
				return this.list.removeChar(i);
			}
		}

		@Override
		public int indexOf(char k) {
			synchronized (this.sync) {
				return this.list.indexOf(k);
			}
		}

		@Override
		public int lastIndexOf(char k) {
			synchronized (this.sync) {
				return this.list.lastIndexOf(k);
			}
		}

		public boolean addAll(int index, Collection<? extends Character> c) {
			synchronized (this.sync) {
				return this.list.addAll(index, c);
			}
		}

		@Override
		public void getElements(int from, char[] a, int offset, int length) {
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
		public void addElements(int index, char[] a, int offset, int length) {
			synchronized (this.sync) {
				this.list.addElements(index, a, offset, length);
			}
		}

		@Override
		public void addElements(int index, char[] a) {
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
		public CharListIterator listIterator() {
			return this.list.listIterator();
		}

		@Override
		public CharListIterator iterator() {
			return this.listIterator();
		}

		@Override
		public CharListIterator listIterator(int i) {
			return this.list.listIterator(i);
		}

		@Override
		public CharList subList(int from, int to) {
			synchronized (this.sync) {
				return new CharLists.SynchronizedList(this.list.subList(from, to), this.sync);
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

		public int compareTo(List<? extends Character> o) {
			synchronized (this.sync) {
				return this.list.compareTo(o);
			}
		}

		@Override
		public boolean addAll(int index, CharCollection c) {
			synchronized (this.sync) {
				return this.list.addAll(index, c);
			}
		}

		@Override
		public boolean addAll(int index, CharList l) {
			synchronized (this.sync) {
				return this.list.addAll(index, l);
			}
		}

		@Override
		public boolean addAll(CharList l) {
			synchronized (this.sync) {
				return this.list.addAll(l);
			}
		}

		@Deprecated
		@Override
		public Character get(int i) {
			synchronized (this.sync) {
				return this.list.get(i);
			}
		}

		@Deprecated
		@Override
		public void add(int i, Character k) {
			synchronized (this.sync) {
				this.list.add(i, k);
			}
		}

		@Deprecated
		@Override
		public Character set(int index, Character k) {
			synchronized (this.sync) {
				return this.list.set(index, k);
			}
		}

		@Deprecated
		@Override
		public Character remove(int i) {
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

	public static class SynchronizedRandomAccessList extends CharLists.SynchronizedList implements RandomAccess, Serializable {
		private static final long serialVersionUID = 0L;

		protected SynchronizedRandomAccessList(CharList l, Object sync) {
			super(l, sync);
		}

		protected SynchronizedRandomAccessList(CharList l) {
			super(l);
		}

		@Override
		public CharList subList(int from, int to) {
			synchronized (this.sync) {
				return new CharLists.SynchronizedRandomAccessList(this.list.subList(from, to), this.sync);
			}
		}
	}

	public static class UnmodifiableList extends UnmodifiableCollection implements CharList, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final CharList list;

		protected UnmodifiableList(CharList l) {
			super(l);
			this.list = l;
		}

		@Override
		public char getChar(int i) {
			return this.list.getChar(i);
		}

		@Override
		public char set(int i, char k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(int i, char k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char removeChar(int i) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int indexOf(char k) {
			return this.list.indexOf(k);
		}

		@Override
		public int lastIndexOf(char k) {
			return this.list.lastIndexOf(k);
		}

		public boolean addAll(int index, Collection<? extends Character> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void getElements(int from, char[] a, int offset, int length) {
			this.list.getElements(from, a, offset, length);
		}

		@Override
		public void removeElements(int from, int to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, char[] a, int offset, int length) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addElements(int index, char[] a) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void size(int size) {
			this.list.size(size);
		}

		@Override
		public CharListIterator listIterator() {
			return CharIterators.unmodifiable(this.list.listIterator());
		}

		@Override
		public CharListIterator iterator() {
			return this.listIterator();
		}

		@Override
		public CharListIterator listIterator(int i) {
			return CharIterators.unmodifiable(this.list.listIterator(i));
		}

		@Override
		public CharList subList(int from, int to) {
			return new CharLists.UnmodifiableList(this.list.subList(from, to));
		}

		@Override
		public boolean equals(Object o) {
			return o == this ? true : this.collection.equals(o);
		}

		@Override
		public int hashCode() {
			return this.collection.hashCode();
		}

		public int compareTo(List<? extends Character> o) {
			return this.list.compareTo(o);
		}

		@Override
		public boolean addAll(int index, CharCollection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(CharList l) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int index, CharList l) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character get(int i) {
			return this.list.get(i);
		}

		@Deprecated
		@Override
		public void add(int i, Character k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character set(int index, Character k) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character remove(int i) {
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

	public static class UnmodifiableRandomAccessList extends CharLists.UnmodifiableList implements RandomAccess, Serializable {
		private static final long serialVersionUID = 0L;

		protected UnmodifiableRandomAccessList(CharList l) {
			super(l);
		}

		@Override
		public CharList subList(int from, int to) {
			return new CharLists.UnmodifiableRandomAccessList(this.list.subList(from, to));
		}
	}
}
