package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.BigArrays;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

public class CharBigArrayBigList extends AbstractCharBigList implements RandomAccess, Cloneable, Serializable {
	private static final long serialVersionUID = -7046029254386353130L;
	public static final int DEFAULT_INITIAL_CAPACITY = 10;
	protected transient char[][] a;
	protected long size;

	protected CharBigArrayBigList(char[][] a, boolean dummy) {
		this.a = a;
	}

	public CharBigArrayBigList(long capacity) {
		if (capacity < 0L) {
			throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
		} else {
			if (capacity == 0L) {
				this.a = CharBigArrays.EMPTY_BIG_ARRAY;
			} else {
				this.a = CharBigArrays.newBigArray(capacity);
			}
		}
	}

	public CharBigArrayBigList() {
		this.a = CharBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
	}

	public CharBigArrayBigList(CharCollection c) {
		this((long)c.size());
		CharIterator i = c.iterator();

		while (i.hasNext()) {
			this.add(i.nextChar());
		}
	}

	public CharBigArrayBigList(CharBigList l) {
		this(l.size64());
		l.getElements(0L, this.a, 0L, this.size = l.size64());
	}

	public CharBigArrayBigList(char[][] a) {
		this(a, 0L, CharBigArrays.length(a));
	}

	public CharBigArrayBigList(char[][] a, long offset, long length) {
		this(length);
		CharBigArrays.copy(a, offset, this.a, 0L, length);
		this.size = length;
	}

	public CharBigArrayBigList(Iterator<? extends Character> i) {
		this();

		while (i.hasNext()) {
			this.add((Character)i.next());
		}
	}

	public CharBigArrayBigList(CharIterator i) {
		this();

		while (i.hasNext()) {
			this.add(i.nextChar());
		}
	}

	public char[][] elements() {
		return this.a;
	}

	public static CharBigArrayBigList wrap(char[][] a, long length) {
		if (length > CharBigArrays.length(a)) {
			throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + CharBigArrays.length(a) + ")");
		} else {
			CharBigArrayBigList l = new CharBigArrayBigList(a, false);
			l.size = length;
			return l;
		}
	}

	public static CharBigArrayBigList wrap(char[][] a) {
		return wrap(a, CharBigArrays.length(a));
	}

	public void ensureCapacity(long capacity) {
		if (capacity > (long)this.a.length && this.a != CharBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
			this.a = CharBigArrays.forceCapacity(this.a, capacity, this.size);

			assert this.size <= CharBigArrays.length(this.a);
		}
	}

	private void grow(long capacity) {
		long oldLength = CharBigArrays.length(this.a);
		if (capacity > oldLength) {
			if (this.a != CharBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
				capacity = Math.max(oldLength + (oldLength >> 1), capacity);
			} else if (capacity < 10L) {
				capacity = 10L;
			}

			this.a = CharBigArrays.forceCapacity(this.a, capacity, this.size);

			assert this.size <= CharBigArrays.length(this.a);
		}
	}

	@Override
	public void add(long index, char k) {
		this.ensureIndex(index);
		this.grow(this.size + 1L);
		if (index != this.size) {
			CharBigArrays.copy(this.a, index, this.a, index + 1L, this.size - index);
		}

		CharBigArrays.set(this.a, index, k);
		this.size++;

		assert this.size <= CharBigArrays.length(this.a);
	}

	@Override
	public boolean add(char k) {
		this.grow(this.size + 1L);
		CharBigArrays.set(this.a, this.size++, k);

		assert this.size <= CharBigArrays.length(this.a);

		return true;
	}

	@Override
	public char getChar(long index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			return CharBigArrays.get(this.a, index);
		}
	}

	@Override
	public long indexOf(char k) {
		for (long i = 0L; i < this.size; i++) {
			if (k == CharBigArrays.get(this.a, i)) {
				return i;
			}
		}

		return -1L;
	}

	@Override
	public long lastIndexOf(char k) {
		long i = this.size;

		while (i-- != 0L) {
			if (k == CharBigArrays.get(this.a, i)) {
				return i;
			}
		}

		return -1L;
	}

	@Override
	public char removeChar(long index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			char old = CharBigArrays.get(this.a, index);
			this.size--;
			if (index != this.size) {
				CharBigArrays.copy(this.a, index + 1L, this.a, index, this.size - index);
			}

			assert this.size <= CharBigArrays.length(this.a);

			return old;
		}
	}

	@Override
	public boolean rem(char k) {
		long index = this.indexOf(k);
		if (index == -1L) {
			return false;
		} else {
			this.removeChar(index);

			assert this.size <= CharBigArrays.length(this.a);

			return true;
		}
	}

	@Override
	public char set(long index, char k) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			char old = CharBigArrays.get(this.a, index);
			CharBigArrays.set(this.a, index, k);
			return old;
		}
	}

	@Override
	public boolean removeAll(CharCollection c) {
		char[] s = null;
		char[] d = null;
		int ss = -1;
		int sd = 134217728;
		int ds = -1;
		int dd = 134217728;

		for (long i = 0L; i < this.size; i++) {
			if (sd == 134217728) {
				sd = 0;
				s = this.a[++ss];
			}

			if (!c.contains(s[sd])) {
				if (dd == 134217728) {
					d = this.a[++ds];
					dd = 0;
				}

				d[dd++] = s[sd];
			}

			sd++;
		}

		long j = BigArrays.index(ds, dd);
		boolean modified = this.size != j;
		this.size = j;
		return modified;
	}

	public boolean removeAll(Collection<?> c) {
		char[] s = null;
		char[] d = null;
		int ss = -1;
		int sd = 134217728;
		int ds = -1;
		int dd = 134217728;

		for (long i = 0L; i < this.size; i++) {
			if (sd == 134217728) {
				sd = 0;
				s = this.a[++ss];
			}

			if (!c.contains(s[sd])) {
				if (dd == 134217728) {
					d = this.a[++ds];
					dd = 0;
				}

				d[dd++] = s[sd];
			}

			sd++;
		}

		long j = BigArrays.index(ds, dd);
		boolean modified = this.size != j;
		this.size = j;
		return modified;
	}

	@Override
	public void clear() {
		this.size = 0L;

		assert this.size <= CharBigArrays.length(this.a);
	}

	@Override
	public long size64() {
		return this.size;
	}

	@Override
	public void size(long size) {
		if (size > CharBigArrays.length(this.a)) {
			this.ensureCapacity(size);
		}

		if (size > this.size) {
			CharBigArrays.fill(this.a, this.size, size, '\u0000');
		}

		this.size = size;
	}

	@Override
	public boolean isEmpty() {
		return this.size == 0L;
	}

	public void trim() {
		this.trim(0L);
	}

	public void trim(long n) {
		long arrayLength = CharBigArrays.length(this.a);
		if (n < arrayLength && this.size != arrayLength) {
			this.a = CharBigArrays.trim(this.a, Math.max(n, this.size));

			assert this.size <= CharBigArrays.length(this.a);
		}
	}

	@Override
	public void getElements(long from, char[][] a, long offset, long length) {
		CharBigArrays.copy(this.a, from, a, offset, length);
	}

	@Override
	public void removeElements(long from, long to) {
		BigArrays.ensureFromTo(this.size, from, to);
		CharBigArrays.copy(this.a, to, this.a, from, this.size - to);
		this.size -= to - from;
	}

	@Override
	public void addElements(long index, char[][] a, long offset, long length) {
		this.ensureIndex(index);
		CharBigArrays.ensureOffsetLength(a, offset, length);
		this.grow(this.size + length);
		CharBigArrays.copy(this.a, index, this.a, index + length, this.size - index);
		CharBigArrays.copy(a, offset, this.a, index, length);
		this.size += length;
	}

	@Override
	public CharBigListIterator listIterator(long index) {
		this.ensureIndex(index);
		return new CharBigListIterator() {
			long pos = index;
			long last = -1L;

			public boolean hasNext() {
				return this.pos < CharBigArrayBigList.this.size;
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0L;
			}

			@Override
			public char nextChar() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return CharBigArrays.get(CharBigArrayBigList.this.a, this.last = this.pos++);
				}
			}

			@Override
			public char previousChar() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return CharBigArrays.get(CharBigArrayBigList.this.a, this.last = --this.pos);
				}
			}

			@Override
			public long nextIndex() {
				return this.pos;
			}

			@Override
			public long previousIndex() {
				return this.pos - 1L;
			}

			@Override
			public void add(char k) {
				CharBigArrayBigList.this.add(this.pos++, k);
				this.last = -1L;
			}

			@Override
			public void set(char k) {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					CharBigArrayBigList.this.set(this.last, k);
				}
			}

			public void remove() {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					CharBigArrayBigList.this.removeChar(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1L;
				}
			}
		};
	}

	public CharBigArrayBigList clone() {
		CharBigArrayBigList c = new CharBigArrayBigList(this.size);
		CharBigArrays.copy(this.a, 0L, c.a, 0L, this.size);
		c.size = this.size;
		return c;
	}

	public boolean equals(CharBigArrayBigList l) {
		if (l == this) {
			return true;
		} else {
			long s = this.size64();
			if (s != l.size64()) {
				return false;
			} else {
				char[][] a1 = this.a;
				char[][] a2 = l.a;

				while (s-- != 0L) {
					if (CharBigArrays.get(a1, s) != CharBigArrays.get(a2, s)) {
						return false;
					}
				}

				return true;
			}
		}
	}

	public int compareTo(CharBigArrayBigList l) {
		long s1 = this.size64();
		long s2 = l.size64();
		char[][] a1 = this.a;
		char[][] a2 = l.a;

		int i;
		for (i = 0; (long)i < s1 && (long)i < s2; i++) {
			char e1 = CharBigArrays.get(a1, (long)i);
			char e2 = CharBigArrays.get(a2, (long)i);
			int r;
			if ((r = Character.compare(e1, e2)) != 0) {
				return r;
			}
		}

		return (long)i < s2 ? -1 : ((long)i < s1 ? 1 : 0);
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; (long)i < this.size; i++) {
			s.writeChar(CharBigArrays.get(this.a, (long)i));
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.a = CharBigArrays.newBigArray(this.size);

		for (int i = 0; (long)i < this.size; i++) {
			CharBigArrays.set(this.a, (long)i, s.readChar());
		}
	}
}
