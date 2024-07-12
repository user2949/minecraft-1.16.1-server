package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.BigArrays;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

public class BooleanBigArrayBigList extends AbstractBooleanBigList implements RandomAccess, Cloneable, Serializable {
	private static final long serialVersionUID = -7046029254386353130L;
	public static final int DEFAULT_INITIAL_CAPACITY = 10;
	protected transient boolean[][] a;
	protected long size;

	protected BooleanBigArrayBigList(boolean[][] a, boolean dummy) {
		this.a = a;
	}

	public BooleanBigArrayBigList(long capacity) {
		if (capacity < 0L) {
			throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
		} else {
			if (capacity == 0L) {
				this.a = BooleanBigArrays.EMPTY_BIG_ARRAY;
			} else {
				this.a = BooleanBigArrays.newBigArray(capacity);
			}
		}
	}

	public BooleanBigArrayBigList() {
		this.a = BooleanBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
	}

	public BooleanBigArrayBigList(BooleanCollection c) {
		this((long)c.size());
		BooleanIterator i = c.iterator();

		while (i.hasNext()) {
			this.add(i.nextBoolean());
		}
	}

	public BooleanBigArrayBigList(BooleanBigList l) {
		this(l.size64());
		l.getElements(0L, this.a, 0L, this.size = l.size64());
	}

	public BooleanBigArrayBigList(boolean[][] a) {
		this(a, 0L, BooleanBigArrays.length(a));
	}

	public BooleanBigArrayBigList(boolean[][] a, long offset, long length) {
		this(length);
		BooleanBigArrays.copy(a, offset, this.a, 0L, length);
		this.size = length;
	}

	public BooleanBigArrayBigList(Iterator<? extends Boolean> i) {
		this();

		while (i.hasNext()) {
			this.add((Boolean)i.next());
		}
	}

	public BooleanBigArrayBigList(BooleanIterator i) {
		this();

		while (i.hasNext()) {
			this.add(i.nextBoolean());
		}
	}

	public boolean[][] elements() {
		return this.a;
	}

	public static BooleanBigArrayBigList wrap(boolean[][] a, long length) {
		if (length > BooleanBigArrays.length(a)) {
			throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + BooleanBigArrays.length(a) + ")");
		} else {
			BooleanBigArrayBigList l = new BooleanBigArrayBigList(a, false);
			l.size = length;
			return l;
		}
	}

	public static BooleanBigArrayBigList wrap(boolean[][] a) {
		return wrap(a, BooleanBigArrays.length(a));
	}

	public void ensureCapacity(long capacity) {
		if (capacity > (long)this.a.length && this.a != BooleanBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
			this.a = BooleanBigArrays.forceCapacity(this.a, capacity, this.size);

			assert this.size <= BooleanBigArrays.length(this.a);
		}
	}

	private void grow(long capacity) {
		long oldLength = BooleanBigArrays.length(this.a);
		if (capacity > oldLength) {
			if (this.a != BooleanBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
				capacity = Math.max(oldLength + (oldLength >> 1), capacity);
			} else if (capacity < 10L) {
				capacity = 10L;
			}

			this.a = BooleanBigArrays.forceCapacity(this.a, capacity, this.size);

			assert this.size <= BooleanBigArrays.length(this.a);
		}
	}

	@Override
	public void add(long index, boolean k) {
		this.ensureIndex(index);
		this.grow(this.size + 1L);
		if (index != this.size) {
			BooleanBigArrays.copy(this.a, index, this.a, index + 1L, this.size - index);
		}

		BooleanBigArrays.set(this.a, index, k);
		this.size++;

		assert this.size <= BooleanBigArrays.length(this.a);
	}

	@Override
	public boolean add(boolean k) {
		this.grow(this.size + 1L);
		BooleanBigArrays.set(this.a, this.size++, k);

		assert this.size <= BooleanBigArrays.length(this.a);

		return true;
	}

	@Override
	public boolean getBoolean(long index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			return BooleanBigArrays.get(this.a, index);
		}
	}

	@Override
	public long indexOf(boolean k) {
		for (long i = 0L; i < this.size; i++) {
			if (k == BooleanBigArrays.get(this.a, i)) {
				return i;
			}
		}

		return -1L;
	}

	@Override
	public long lastIndexOf(boolean k) {
		long i = this.size;

		while (i-- != 0L) {
			if (k == BooleanBigArrays.get(this.a, i)) {
				return i;
			}
		}

		return -1L;
	}

	@Override
	public boolean removeBoolean(long index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			boolean old = BooleanBigArrays.get(this.a, index);
			this.size--;
			if (index != this.size) {
				BooleanBigArrays.copy(this.a, index + 1L, this.a, index, this.size - index);
			}

			assert this.size <= BooleanBigArrays.length(this.a);

			return old;
		}
	}

	@Override
	public boolean rem(boolean k) {
		long index = this.indexOf(k);
		if (index == -1L) {
			return false;
		} else {
			this.removeBoolean(index);

			assert this.size <= BooleanBigArrays.length(this.a);

			return true;
		}
	}

	@Override
	public boolean set(long index, boolean k) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			boolean old = BooleanBigArrays.get(this.a, index);
			BooleanBigArrays.set(this.a, index, k);
			return old;
		}
	}

	@Override
	public boolean removeAll(BooleanCollection c) {
		boolean[] s = null;
		boolean[] d = null;
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
		boolean[] s = null;
		boolean[] d = null;
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

		assert this.size <= BooleanBigArrays.length(this.a);
	}

	@Override
	public long size64() {
		return this.size;
	}

	@Override
	public void size(long size) {
		if (size > BooleanBigArrays.length(this.a)) {
			this.ensureCapacity(size);
		}

		if (size > this.size) {
			BooleanBigArrays.fill(this.a, this.size, size, false);
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
		long arrayLength = BooleanBigArrays.length(this.a);
		if (n < arrayLength && this.size != arrayLength) {
			this.a = BooleanBigArrays.trim(this.a, Math.max(n, this.size));

			assert this.size <= BooleanBigArrays.length(this.a);
		}
	}

	@Override
	public void getElements(long from, boolean[][] a, long offset, long length) {
		BooleanBigArrays.copy(this.a, from, a, offset, length);
	}

	@Override
	public void removeElements(long from, long to) {
		BigArrays.ensureFromTo(this.size, from, to);
		BooleanBigArrays.copy(this.a, to, this.a, from, this.size - to);
		this.size -= to - from;
	}

	@Override
	public void addElements(long index, boolean[][] a, long offset, long length) {
		this.ensureIndex(index);
		BooleanBigArrays.ensureOffsetLength(a, offset, length);
		this.grow(this.size + length);
		BooleanBigArrays.copy(this.a, index, this.a, index + length, this.size - index);
		BooleanBigArrays.copy(a, offset, this.a, index, length);
		this.size += length;
	}

	@Override
	public BooleanBigListIterator listIterator(long index) {
		this.ensureIndex(index);
		return new BooleanBigListIterator() {
			long pos = index;
			long last = -1L;

			public boolean hasNext() {
				return this.pos < BooleanBigArrayBigList.this.size;
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0L;
			}

			@Override
			public boolean nextBoolean() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return BooleanBigArrays.get(BooleanBigArrayBigList.this.a, this.last = this.pos++);
				}
			}

			@Override
			public boolean previousBoolean() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return BooleanBigArrays.get(BooleanBigArrayBigList.this.a, this.last = --this.pos);
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
			public void add(boolean k) {
				BooleanBigArrayBigList.this.add(this.pos++, k);
				this.last = -1L;
			}

			@Override
			public void set(boolean k) {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					BooleanBigArrayBigList.this.set(this.last, k);
				}
			}

			public void remove() {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					BooleanBigArrayBigList.this.removeBoolean(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1L;
				}
			}
		};
	}

	public BooleanBigArrayBigList clone() {
		BooleanBigArrayBigList c = new BooleanBigArrayBigList(this.size);
		BooleanBigArrays.copy(this.a, 0L, c.a, 0L, this.size);
		c.size = this.size;
		return c;
	}

	public boolean equals(BooleanBigArrayBigList l) {
		if (l == this) {
			return true;
		} else {
			long s = this.size64();
			if (s != l.size64()) {
				return false;
			} else {
				boolean[][] a1 = this.a;
				boolean[][] a2 = l.a;

				while (s-- != 0L) {
					if (BooleanBigArrays.get(a1, s) != BooleanBigArrays.get(a2, s)) {
						return false;
					}
				}

				return true;
			}
		}
	}

	public int compareTo(BooleanBigArrayBigList l) {
		long s1 = this.size64();
		long s2 = l.size64();
		boolean[][] a1 = this.a;
		boolean[][] a2 = l.a;

		int i;
		for (i = 0; (long)i < s1 && (long)i < s2; i++) {
			boolean e1 = BooleanBigArrays.get(a1, (long)i);
			boolean e2 = BooleanBigArrays.get(a2, (long)i);
			int r;
			if ((r = Boolean.compare(e1, e2)) != 0) {
				return r;
			}
		}

		return (long)i < s2 ? -1 : ((long)i < s1 ? 1 : 0);
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; (long)i < this.size; i++) {
			s.writeBoolean(BooleanBigArrays.get(this.a, (long)i));
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.a = BooleanBigArrays.newBigArray(this.size);

		for (int i = 0; (long)i < this.size; i++) {
			BooleanBigArrays.set(this.a, (long)i, s.readBoolean());
		}
	}
}
