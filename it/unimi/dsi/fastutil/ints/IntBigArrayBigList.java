package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.BigArrays;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

public class IntBigArrayBigList extends AbstractIntBigList implements RandomAccess, Cloneable, Serializable {
	private static final long serialVersionUID = -7046029254386353130L;
	public static final int DEFAULT_INITIAL_CAPACITY = 10;
	protected transient int[][] a;
	protected long size;

	protected IntBigArrayBigList(int[][] a, boolean dummy) {
		this.a = a;
	}

	public IntBigArrayBigList(long capacity) {
		if (capacity < 0L) {
			throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
		} else {
			if (capacity == 0L) {
				this.a = IntBigArrays.EMPTY_BIG_ARRAY;
			} else {
				this.a = IntBigArrays.newBigArray(capacity);
			}
		}
	}

	public IntBigArrayBigList() {
		this.a = IntBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
	}

	public IntBigArrayBigList(IntCollection c) {
		this((long)c.size());
		IntIterator i = c.iterator();

		while (i.hasNext()) {
			this.add(i.nextInt());
		}
	}

	public IntBigArrayBigList(IntBigList l) {
		this(l.size64());
		l.getElements(0L, this.a, 0L, this.size = l.size64());
	}

	public IntBigArrayBigList(int[][] a) {
		this(a, 0L, IntBigArrays.length(a));
	}

	public IntBigArrayBigList(int[][] a, long offset, long length) {
		this(length);
		IntBigArrays.copy(a, offset, this.a, 0L, length);
		this.size = length;
	}

	public IntBigArrayBigList(Iterator<? extends Integer> i) {
		this();

		while (i.hasNext()) {
			this.add((Integer)i.next());
		}
	}

	public IntBigArrayBigList(IntIterator i) {
		this();

		while (i.hasNext()) {
			this.add(i.nextInt());
		}
	}

	public int[][] elements() {
		return this.a;
	}

	public static IntBigArrayBigList wrap(int[][] a, long length) {
		if (length > IntBigArrays.length(a)) {
			throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + IntBigArrays.length(a) + ")");
		} else {
			IntBigArrayBigList l = new IntBigArrayBigList(a, false);
			l.size = length;
			return l;
		}
	}

	public static IntBigArrayBigList wrap(int[][] a) {
		return wrap(a, IntBigArrays.length(a));
	}

	public void ensureCapacity(long capacity) {
		if (capacity > (long)this.a.length && this.a != IntBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
			this.a = IntBigArrays.forceCapacity(this.a, capacity, this.size);

			assert this.size <= IntBigArrays.length(this.a);
		}
	}

	private void grow(long capacity) {
		long oldLength = IntBigArrays.length(this.a);
		if (capacity > oldLength) {
			if (this.a != IntBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
				capacity = Math.max(oldLength + (oldLength >> 1), capacity);
			} else if (capacity < 10L) {
				capacity = 10L;
			}

			this.a = IntBigArrays.forceCapacity(this.a, capacity, this.size);

			assert this.size <= IntBigArrays.length(this.a);
		}
	}

	@Override
	public void add(long index, int k) {
		this.ensureIndex(index);
		this.grow(this.size + 1L);
		if (index != this.size) {
			IntBigArrays.copy(this.a, index, this.a, index + 1L, this.size - index);
		}

		IntBigArrays.set(this.a, index, k);
		this.size++;

		assert this.size <= IntBigArrays.length(this.a);
	}

	@Override
	public boolean add(int k) {
		this.grow(this.size + 1L);
		IntBigArrays.set(this.a, this.size++, k);

		assert this.size <= IntBigArrays.length(this.a);

		return true;
	}

	@Override
	public int getInt(long index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			return IntBigArrays.get(this.a, index);
		}
	}

	@Override
	public long indexOf(int k) {
		for (long i = 0L; i < this.size; i++) {
			if (k == IntBigArrays.get(this.a, i)) {
				return i;
			}
		}

		return -1L;
	}

	@Override
	public long lastIndexOf(int k) {
		long i = this.size;

		while (i-- != 0L) {
			if (k == IntBigArrays.get(this.a, i)) {
				return i;
			}
		}

		return -1L;
	}

	@Override
	public int removeInt(long index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			int old = IntBigArrays.get(this.a, index);
			this.size--;
			if (index != this.size) {
				IntBigArrays.copy(this.a, index + 1L, this.a, index, this.size - index);
			}

			assert this.size <= IntBigArrays.length(this.a);

			return old;
		}
	}

	@Override
	public boolean rem(int k) {
		long index = this.indexOf(k);
		if (index == -1L) {
			return false;
		} else {
			this.removeInt(index);

			assert this.size <= IntBigArrays.length(this.a);

			return true;
		}
	}

	@Override
	public int set(long index, int k) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			int old = IntBigArrays.get(this.a, index);
			IntBigArrays.set(this.a, index, k);
			return old;
		}
	}

	@Override
	public boolean removeAll(IntCollection c) {
		int[] s = null;
		int[] d = null;
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
		int[] s = null;
		int[] d = null;
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

		assert this.size <= IntBigArrays.length(this.a);
	}

	@Override
	public long size64() {
		return this.size;
	}

	@Override
	public void size(long size) {
		if (size > IntBigArrays.length(this.a)) {
			this.ensureCapacity(size);
		}

		if (size > this.size) {
			IntBigArrays.fill(this.a, this.size, size, 0);
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
		long arrayLength = IntBigArrays.length(this.a);
		if (n < arrayLength && this.size != arrayLength) {
			this.a = IntBigArrays.trim(this.a, Math.max(n, this.size));

			assert this.size <= IntBigArrays.length(this.a);
		}
	}

	@Override
	public void getElements(long from, int[][] a, long offset, long length) {
		IntBigArrays.copy(this.a, from, a, offset, length);
	}

	@Override
	public void removeElements(long from, long to) {
		BigArrays.ensureFromTo(this.size, from, to);
		IntBigArrays.copy(this.a, to, this.a, from, this.size - to);
		this.size -= to - from;
	}

	@Override
	public void addElements(long index, int[][] a, long offset, long length) {
		this.ensureIndex(index);
		IntBigArrays.ensureOffsetLength(a, offset, length);
		this.grow(this.size + length);
		IntBigArrays.copy(this.a, index, this.a, index + length, this.size - index);
		IntBigArrays.copy(a, offset, this.a, index, length);
		this.size += length;
	}

	@Override
	public IntBigListIterator listIterator(long index) {
		this.ensureIndex(index);
		return new IntBigListIterator() {
			long pos = index;
			long last = -1L;

			public boolean hasNext() {
				return this.pos < IntBigArrayBigList.this.size;
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0L;
			}

			@Override
			public int nextInt() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return IntBigArrays.get(IntBigArrayBigList.this.a, this.last = this.pos++);
				}
			}

			@Override
			public int previousInt() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return IntBigArrays.get(IntBigArrayBigList.this.a, this.last = --this.pos);
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
			public void add(int k) {
				IntBigArrayBigList.this.add(this.pos++, k);
				this.last = -1L;
			}

			@Override
			public void set(int k) {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					IntBigArrayBigList.this.set(this.last, k);
				}
			}

			public void remove() {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					IntBigArrayBigList.this.removeInt(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1L;
				}
			}
		};
	}

	public IntBigArrayBigList clone() {
		IntBigArrayBigList c = new IntBigArrayBigList(this.size);
		IntBigArrays.copy(this.a, 0L, c.a, 0L, this.size);
		c.size = this.size;
		return c;
	}

	public boolean equals(IntBigArrayBigList l) {
		if (l == this) {
			return true;
		} else {
			long s = this.size64();
			if (s != l.size64()) {
				return false;
			} else {
				int[][] a1 = this.a;
				int[][] a2 = l.a;

				while (s-- != 0L) {
					if (IntBigArrays.get(a1, s) != IntBigArrays.get(a2, s)) {
						return false;
					}
				}

				return true;
			}
		}
	}

	public int compareTo(IntBigArrayBigList l) {
		long s1 = this.size64();
		long s2 = l.size64();
		int[][] a1 = this.a;
		int[][] a2 = l.a;

		int i;
		for (i = 0; (long)i < s1 && (long)i < s2; i++) {
			int e1 = IntBigArrays.get(a1, (long)i);
			int e2 = IntBigArrays.get(a2, (long)i);
			int r;
			if ((r = Integer.compare(e1, e2)) != 0) {
				return r;
			}
		}

		return (long)i < s2 ? -1 : ((long)i < s1 ? 1 : 0);
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; (long)i < this.size; i++) {
			s.writeInt(IntBigArrays.get(this.a, (long)i));
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.a = IntBigArrays.newBigArray(this.size);

		for (int i = 0; (long)i < this.size; i++) {
			IntBigArrays.set(this.a, (long)i, s.readInt());
		}
	}
}
