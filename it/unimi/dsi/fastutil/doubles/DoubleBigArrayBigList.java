package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.BigArrays;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

public class DoubleBigArrayBigList extends AbstractDoubleBigList implements RandomAccess, Cloneable, Serializable {
	private static final long serialVersionUID = -7046029254386353130L;
	public static final int DEFAULT_INITIAL_CAPACITY = 10;
	protected transient double[][] a;
	protected long size;

	protected DoubleBigArrayBigList(double[][] a, boolean dummy) {
		this.a = a;
	}

	public DoubleBigArrayBigList(long capacity) {
		if (capacity < 0L) {
			throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
		} else {
			if (capacity == 0L) {
				this.a = DoubleBigArrays.EMPTY_BIG_ARRAY;
			} else {
				this.a = DoubleBigArrays.newBigArray(capacity);
			}
		}
	}

	public DoubleBigArrayBigList() {
		this.a = DoubleBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
	}

	public DoubleBigArrayBigList(DoubleCollection c) {
		this((long)c.size());
		DoubleIterator i = c.iterator();

		while (i.hasNext()) {
			this.add(i.nextDouble());
		}
	}

	public DoubleBigArrayBigList(DoubleBigList l) {
		this(l.size64());
		l.getElements(0L, this.a, 0L, this.size = l.size64());
	}

	public DoubleBigArrayBigList(double[][] a) {
		this(a, 0L, DoubleBigArrays.length(a));
	}

	public DoubleBigArrayBigList(double[][] a, long offset, long length) {
		this(length);
		DoubleBigArrays.copy(a, offset, this.a, 0L, length);
		this.size = length;
	}

	public DoubleBigArrayBigList(Iterator<? extends Double> i) {
		this();

		while (i.hasNext()) {
			this.add((Double)i.next());
		}
	}

	public DoubleBigArrayBigList(DoubleIterator i) {
		this();

		while (i.hasNext()) {
			this.add(i.nextDouble());
		}
	}

	public double[][] elements() {
		return this.a;
	}

	public static DoubleBigArrayBigList wrap(double[][] a, long length) {
		if (length > DoubleBigArrays.length(a)) {
			throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + DoubleBigArrays.length(a) + ")");
		} else {
			DoubleBigArrayBigList l = new DoubleBigArrayBigList(a, false);
			l.size = length;
			return l;
		}
	}

	public static DoubleBigArrayBigList wrap(double[][] a) {
		return wrap(a, DoubleBigArrays.length(a));
	}

	public void ensureCapacity(long capacity) {
		if (capacity > (long)this.a.length && this.a != DoubleBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
			this.a = DoubleBigArrays.forceCapacity(this.a, capacity, this.size);

			assert this.size <= DoubleBigArrays.length(this.a);
		}
	}

	private void grow(long capacity) {
		long oldLength = DoubleBigArrays.length(this.a);
		if (capacity > oldLength) {
			if (this.a != DoubleBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
				capacity = Math.max(oldLength + (oldLength >> 1), capacity);
			} else if (capacity < 10L) {
				capacity = 10L;
			}

			this.a = DoubleBigArrays.forceCapacity(this.a, capacity, this.size);

			assert this.size <= DoubleBigArrays.length(this.a);
		}
	}

	@Override
	public void add(long index, double k) {
		this.ensureIndex(index);
		this.grow(this.size + 1L);
		if (index != this.size) {
			DoubleBigArrays.copy(this.a, index, this.a, index + 1L, this.size - index);
		}

		DoubleBigArrays.set(this.a, index, k);
		this.size++;

		assert this.size <= DoubleBigArrays.length(this.a);
	}

	@Override
	public boolean add(double k) {
		this.grow(this.size + 1L);
		DoubleBigArrays.set(this.a, this.size++, k);

		assert this.size <= DoubleBigArrays.length(this.a);

		return true;
	}

	@Override
	public double getDouble(long index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			return DoubleBigArrays.get(this.a, index);
		}
	}

	@Override
	public long indexOf(double k) {
		for (long i = 0L; i < this.size; i++) {
			if (Double.doubleToLongBits(k) == Double.doubleToLongBits(DoubleBigArrays.get(this.a, i))) {
				return i;
			}
		}

		return -1L;
	}

	@Override
	public long lastIndexOf(double k) {
		long i = this.size;

		while (i-- != 0L) {
			if (Double.doubleToLongBits(k) == Double.doubleToLongBits(DoubleBigArrays.get(this.a, i))) {
				return i;
			}
		}

		return -1L;
	}

	@Override
	public double removeDouble(long index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			double old = DoubleBigArrays.get(this.a, index);
			this.size--;
			if (index != this.size) {
				DoubleBigArrays.copy(this.a, index + 1L, this.a, index, this.size - index);
			}

			assert this.size <= DoubleBigArrays.length(this.a);

			return old;
		}
	}

	@Override
	public boolean rem(double k) {
		long index = this.indexOf(k);
		if (index == -1L) {
			return false;
		} else {
			this.removeDouble(index);

			assert this.size <= DoubleBigArrays.length(this.a);

			return true;
		}
	}

	@Override
	public double set(long index, double k) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			double old = DoubleBigArrays.get(this.a, index);
			DoubleBigArrays.set(this.a, index, k);
			return old;
		}
	}

	@Override
	public boolean removeAll(DoubleCollection c) {
		double[] s = null;
		double[] d = null;
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
		double[] s = null;
		double[] d = null;
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

		assert this.size <= DoubleBigArrays.length(this.a);
	}

	@Override
	public long size64() {
		return this.size;
	}

	@Override
	public void size(long size) {
		if (size > DoubleBigArrays.length(this.a)) {
			this.ensureCapacity(size);
		}

		if (size > this.size) {
			DoubleBigArrays.fill(this.a, this.size, size, 0.0);
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
		long arrayLength = DoubleBigArrays.length(this.a);
		if (n < arrayLength && this.size != arrayLength) {
			this.a = DoubleBigArrays.trim(this.a, Math.max(n, this.size));

			assert this.size <= DoubleBigArrays.length(this.a);
		}
	}

	@Override
	public void getElements(long from, double[][] a, long offset, long length) {
		DoubleBigArrays.copy(this.a, from, a, offset, length);
	}

	@Override
	public void removeElements(long from, long to) {
		BigArrays.ensureFromTo(this.size, from, to);
		DoubleBigArrays.copy(this.a, to, this.a, from, this.size - to);
		this.size -= to - from;
	}

	@Override
	public void addElements(long index, double[][] a, long offset, long length) {
		this.ensureIndex(index);
		DoubleBigArrays.ensureOffsetLength(a, offset, length);
		this.grow(this.size + length);
		DoubleBigArrays.copy(this.a, index, this.a, index + length, this.size - index);
		DoubleBigArrays.copy(a, offset, this.a, index, length);
		this.size += length;
	}

	@Override
	public DoubleBigListIterator listIterator(long index) {
		this.ensureIndex(index);
		return new DoubleBigListIterator() {
			long pos = index;
			long last = -1L;

			public boolean hasNext() {
				return this.pos < DoubleBigArrayBigList.this.size;
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0L;
			}

			@Override
			public double nextDouble() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return DoubleBigArrays.get(DoubleBigArrayBigList.this.a, this.last = this.pos++);
				}
			}

			@Override
			public double previousDouble() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return DoubleBigArrays.get(DoubleBigArrayBigList.this.a, this.last = --this.pos);
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
			public void add(double k) {
				DoubleBigArrayBigList.this.add(this.pos++, k);
				this.last = -1L;
			}

			@Override
			public void set(double k) {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					DoubleBigArrayBigList.this.set(this.last, k);
				}
			}

			public void remove() {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					DoubleBigArrayBigList.this.removeDouble(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1L;
				}
			}
		};
	}

	public DoubleBigArrayBigList clone() {
		DoubleBigArrayBigList c = new DoubleBigArrayBigList(this.size);
		DoubleBigArrays.copy(this.a, 0L, c.a, 0L, this.size);
		c.size = this.size;
		return c;
	}

	public boolean equals(DoubleBigArrayBigList l) {
		if (l == this) {
			return true;
		} else {
			long s = this.size64();
			if (s != l.size64()) {
				return false;
			} else {
				double[][] a1 = this.a;
				double[][] a2 = l.a;

				while (s-- != 0L) {
					if (DoubleBigArrays.get(a1, s) != DoubleBigArrays.get(a2, s)) {
						return false;
					}
				}

				return true;
			}
		}
	}

	public int compareTo(DoubleBigArrayBigList l) {
		long s1 = this.size64();
		long s2 = l.size64();
		double[][] a1 = this.a;
		double[][] a2 = l.a;

		int i;
		for (i = 0; (long)i < s1 && (long)i < s2; i++) {
			double e1 = DoubleBigArrays.get(a1, (long)i);
			double e2 = DoubleBigArrays.get(a2, (long)i);
			int r;
			if ((r = Double.compare(e1, e2)) != 0) {
				return r;
			}
		}

		return (long)i < s2 ? -1 : ((long)i < s1 ? 1 : 0);
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; (long)i < this.size; i++) {
			s.writeDouble(DoubleBigArrays.get(this.a, (long)i));
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.a = DoubleBigArrays.newBigArray(this.size);

		for (int i = 0; (long)i < this.size; i++) {
			DoubleBigArrays.set(this.a, (long)i, s.readDouble());
		}
	}
}
