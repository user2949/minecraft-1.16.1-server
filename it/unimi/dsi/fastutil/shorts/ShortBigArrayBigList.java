package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.BigArrays;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

public class ShortBigArrayBigList extends AbstractShortBigList implements RandomAccess, Cloneable, Serializable {
	private static final long serialVersionUID = -7046029254386353130L;
	public static final int DEFAULT_INITIAL_CAPACITY = 10;
	protected transient short[][] a;
	protected long size;

	protected ShortBigArrayBigList(short[][] a, boolean dummy) {
		this.a = a;
	}

	public ShortBigArrayBigList(long capacity) {
		if (capacity < 0L) {
			throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
		} else {
			if (capacity == 0L) {
				this.a = ShortBigArrays.EMPTY_BIG_ARRAY;
			} else {
				this.a = ShortBigArrays.newBigArray(capacity);
			}
		}
	}

	public ShortBigArrayBigList() {
		this.a = ShortBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
	}

	public ShortBigArrayBigList(ShortCollection c) {
		this((long)c.size());
		ShortIterator i = c.iterator();

		while (i.hasNext()) {
			this.add(i.nextShort());
		}
	}

	public ShortBigArrayBigList(ShortBigList l) {
		this(l.size64());
		l.getElements(0L, this.a, 0L, this.size = l.size64());
	}

	public ShortBigArrayBigList(short[][] a) {
		this(a, 0L, ShortBigArrays.length(a));
	}

	public ShortBigArrayBigList(short[][] a, long offset, long length) {
		this(length);
		ShortBigArrays.copy(a, offset, this.a, 0L, length);
		this.size = length;
	}

	public ShortBigArrayBigList(Iterator<? extends Short> i) {
		this();

		while (i.hasNext()) {
			this.add((Short)i.next());
		}
	}

	public ShortBigArrayBigList(ShortIterator i) {
		this();

		while (i.hasNext()) {
			this.add(i.nextShort());
		}
	}

	public short[][] elements() {
		return this.a;
	}

	public static ShortBigArrayBigList wrap(short[][] a, long length) {
		if (length > ShortBigArrays.length(a)) {
			throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + ShortBigArrays.length(a) + ")");
		} else {
			ShortBigArrayBigList l = new ShortBigArrayBigList(a, false);
			l.size = length;
			return l;
		}
	}

	public static ShortBigArrayBigList wrap(short[][] a) {
		return wrap(a, ShortBigArrays.length(a));
	}

	public void ensureCapacity(long capacity) {
		if (capacity > (long)this.a.length && this.a != ShortBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
			this.a = ShortBigArrays.forceCapacity(this.a, capacity, this.size);

			assert this.size <= ShortBigArrays.length(this.a);
		}
	}

	private void grow(long capacity) {
		long oldLength = ShortBigArrays.length(this.a);
		if (capacity > oldLength) {
			if (this.a != ShortBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
				capacity = Math.max(oldLength + (oldLength >> 1), capacity);
			} else if (capacity < 10L) {
				capacity = 10L;
			}

			this.a = ShortBigArrays.forceCapacity(this.a, capacity, this.size);

			assert this.size <= ShortBigArrays.length(this.a);
		}
	}

	@Override
	public void add(long index, short k) {
		this.ensureIndex(index);
		this.grow(this.size + 1L);
		if (index != this.size) {
			ShortBigArrays.copy(this.a, index, this.a, index + 1L, this.size - index);
		}

		ShortBigArrays.set(this.a, index, k);
		this.size++;

		assert this.size <= ShortBigArrays.length(this.a);
	}

	@Override
	public boolean add(short k) {
		this.grow(this.size + 1L);
		ShortBigArrays.set(this.a, this.size++, k);

		assert this.size <= ShortBigArrays.length(this.a);

		return true;
	}

	@Override
	public short getShort(long index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			return ShortBigArrays.get(this.a, index);
		}
	}

	@Override
	public long indexOf(short k) {
		for (long i = 0L; i < this.size; i++) {
			if (k == ShortBigArrays.get(this.a, i)) {
				return i;
			}
		}

		return -1L;
	}

	@Override
	public long lastIndexOf(short k) {
		long i = this.size;

		while (i-- != 0L) {
			if (k == ShortBigArrays.get(this.a, i)) {
				return i;
			}
		}

		return -1L;
	}

	@Override
	public short removeShort(long index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			short old = ShortBigArrays.get(this.a, index);
			this.size--;
			if (index != this.size) {
				ShortBigArrays.copy(this.a, index + 1L, this.a, index, this.size - index);
			}

			assert this.size <= ShortBigArrays.length(this.a);

			return old;
		}
	}

	@Override
	public boolean rem(short k) {
		long index = this.indexOf(k);
		if (index == -1L) {
			return false;
		} else {
			this.removeShort(index);

			assert this.size <= ShortBigArrays.length(this.a);

			return true;
		}
	}

	@Override
	public short set(long index, short k) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			short old = ShortBigArrays.get(this.a, index);
			ShortBigArrays.set(this.a, index, k);
			return old;
		}
	}

	@Override
	public boolean removeAll(ShortCollection c) {
		short[] s = null;
		short[] d = null;
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
		short[] s = null;
		short[] d = null;
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

		assert this.size <= ShortBigArrays.length(this.a);
	}

	@Override
	public long size64() {
		return this.size;
	}

	@Override
	public void size(long size) {
		if (size > ShortBigArrays.length(this.a)) {
			this.ensureCapacity(size);
		}

		if (size > this.size) {
			ShortBigArrays.fill(this.a, this.size, size, (short)0);
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
		long arrayLength = ShortBigArrays.length(this.a);
		if (n < arrayLength && this.size != arrayLength) {
			this.a = ShortBigArrays.trim(this.a, Math.max(n, this.size));

			assert this.size <= ShortBigArrays.length(this.a);
		}
	}

	@Override
	public void getElements(long from, short[][] a, long offset, long length) {
		ShortBigArrays.copy(this.a, from, a, offset, length);
	}

	@Override
	public void removeElements(long from, long to) {
		BigArrays.ensureFromTo(this.size, from, to);
		ShortBigArrays.copy(this.a, to, this.a, from, this.size - to);
		this.size -= to - from;
	}

	@Override
	public void addElements(long index, short[][] a, long offset, long length) {
		this.ensureIndex(index);
		ShortBigArrays.ensureOffsetLength(a, offset, length);
		this.grow(this.size + length);
		ShortBigArrays.copy(this.a, index, this.a, index + length, this.size - index);
		ShortBigArrays.copy(a, offset, this.a, index, length);
		this.size += length;
	}

	@Override
	public ShortBigListIterator listIterator(long index) {
		this.ensureIndex(index);
		return new ShortBigListIterator() {
			long pos = index;
			long last = -1L;

			public boolean hasNext() {
				return this.pos < ShortBigArrayBigList.this.size;
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0L;
			}

			@Override
			public short nextShort() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return ShortBigArrays.get(ShortBigArrayBigList.this.a, this.last = this.pos++);
				}
			}

			@Override
			public short previousShort() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return ShortBigArrays.get(ShortBigArrayBigList.this.a, this.last = --this.pos);
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
			public void add(short k) {
				ShortBigArrayBigList.this.add(this.pos++, k);
				this.last = -1L;
			}

			@Override
			public void set(short k) {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					ShortBigArrayBigList.this.set(this.last, k);
				}
			}

			public void remove() {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					ShortBigArrayBigList.this.removeShort(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1L;
				}
			}
		};
	}

	public ShortBigArrayBigList clone() {
		ShortBigArrayBigList c = new ShortBigArrayBigList(this.size);
		ShortBigArrays.copy(this.a, 0L, c.a, 0L, this.size);
		c.size = this.size;
		return c;
	}

	public boolean equals(ShortBigArrayBigList l) {
		if (l == this) {
			return true;
		} else {
			long s = this.size64();
			if (s != l.size64()) {
				return false;
			} else {
				short[][] a1 = this.a;
				short[][] a2 = l.a;

				while (s-- != 0L) {
					if (ShortBigArrays.get(a1, s) != ShortBigArrays.get(a2, s)) {
						return false;
					}
				}

				return true;
			}
		}
	}

	public int compareTo(ShortBigArrayBigList l) {
		long s1 = this.size64();
		long s2 = l.size64();
		short[][] a1 = this.a;
		short[][] a2 = l.a;

		int i;
		for (i = 0; (long)i < s1 && (long)i < s2; i++) {
			short e1 = ShortBigArrays.get(a1, (long)i);
			short e2 = ShortBigArrays.get(a2, (long)i);
			int r;
			if ((r = Short.compare(e1, e2)) != 0) {
				return r;
			}
		}

		return (long)i < s2 ? -1 : ((long)i < s1 ? 1 : 0);
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; (long)i < this.size; i++) {
			s.writeShort(ShortBigArrays.get(this.a, (long)i));
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.a = ShortBigArrays.newBigArray(this.size);

		for (int i = 0; (long)i < this.size; i++) {
			ShortBigArrays.set(this.a, (long)i, s.readShort());
		}
	}
}
