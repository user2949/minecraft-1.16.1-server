package it.unimi.dsi.fastutil.doubles;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

public class DoubleArrayList extends AbstractDoubleList implements RandomAccess, Cloneable, Serializable {
	private static final long serialVersionUID = -7046029254386353130L;
	public static final int DEFAULT_INITIAL_CAPACITY = 10;
	protected transient double[] a;
	protected int size;

	protected DoubleArrayList(double[] a, boolean dummy) {
		this.a = a;
	}

	public DoubleArrayList(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
		} else {
			if (capacity == 0) {
				this.a = DoubleArrays.EMPTY_ARRAY;
			} else {
				this.a = new double[capacity];
			}
		}
	}

	public DoubleArrayList() {
		this.a = DoubleArrays.DEFAULT_EMPTY_ARRAY;
	}

	public DoubleArrayList(Collection<? extends Double> c) {
		this(c.size());
		this.size = DoubleIterators.unwrap(DoubleIterators.asDoubleIterator(c.iterator()), this.a);
	}

	public DoubleArrayList(DoubleCollection c) {
		this(c.size());
		this.size = DoubleIterators.unwrap(c.iterator(), this.a);
	}

	public DoubleArrayList(DoubleList l) {
		this(l.size());
		l.getElements(0, this.a, 0, this.size = l.size());
	}

	public DoubleArrayList(double[] a) {
		this(a, 0, a.length);
	}

	public DoubleArrayList(double[] a, int offset, int length) {
		this(length);
		System.arraycopy(a, offset, this.a, 0, length);
		this.size = length;
	}

	public DoubleArrayList(Iterator<? extends Double> i) {
		this();

		while (i.hasNext()) {
			this.add((Double)i.next());
		}
	}

	public DoubleArrayList(DoubleIterator i) {
		this();

		while (i.hasNext()) {
			this.add(i.nextDouble());
		}
	}

	public double[] elements() {
		return this.a;
	}

	public static DoubleArrayList wrap(double[] a, int length) {
		if (length > a.length) {
			throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + a.length + ")");
		} else {
			DoubleArrayList l = new DoubleArrayList(a, false);
			l.size = length;
			return l;
		}
	}

	public static DoubleArrayList wrap(double[] a) {
		return wrap(a, a.length);
	}

	public void ensureCapacity(int capacity) {
		if (capacity > this.a.length && this.a != DoubleArrays.DEFAULT_EMPTY_ARRAY) {
			this.a = DoubleArrays.ensureCapacity(this.a, capacity, this.size);

			assert this.size <= this.a.length;
		}
	}

	private void grow(int capacity) {
		if (capacity > this.a.length) {
			if (this.a != DoubleArrays.DEFAULT_EMPTY_ARRAY) {
				capacity = (int)Math.max(Math.min((long)this.a.length + (long)(this.a.length >> 1), 2147483639L), (long)capacity);
			} else if (capacity < 10) {
				capacity = 10;
			}

			this.a = DoubleArrays.forceCapacity(this.a, capacity, this.size);

			assert this.size <= this.a.length;
		}
	}

	@Override
	public void add(int index, double k) {
		this.ensureIndex(index);
		this.grow(this.size + 1);
		if (index != this.size) {
			System.arraycopy(this.a, index, this.a, index + 1, this.size - index);
		}

		this.a[index] = k;
		this.size++;

		assert this.size <= this.a.length;
	}

	@Override
	public boolean add(double k) {
		this.grow(this.size + 1);
		this.a[this.size++] = k;

		assert this.size <= this.a.length;

		return true;
	}

	@Override
	public double getDouble(int index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			return this.a[index];
		}
	}

	@Override
	public int indexOf(double k) {
		for (int i = 0; i < this.size; i++) {
			if (Double.doubleToLongBits(k) == Double.doubleToLongBits(this.a[i])) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public int lastIndexOf(double k) {
		int i = this.size;

		while (i-- != 0) {
			if (Double.doubleToLongBits(k) == Double.doubleToLongBits(this.a[i])) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public double removeDouble(int index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			double old = this.a[index];
			this.size--;
			if (index != this.size) {
				System.arraycopy(this.a, index + 1, this.a, index, this.size - index);
			}

			assert this.size <= this.a.length;

			return old;
		}
	}

	@Override
	public boolean rem(double k) {
		int index = this.indexOf(k);
		if (index == -1) {
			return false;
		} else {
			this.removeDouble(index);

			assert this.size <= this.a.length;

			return true;
		}
	}

	@Override
	public double set(int index, double k) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			double old = this.a[index];
			this.a[index] = k;
			return old;
		}
	}

	@Override
	public void clear() {
		this.size = 0;

		assert this.size <= this.a.length;
	}

	public int size() {
		return this.size;
	}

	@Override
	public void size(int size) {
		if (size > this.a.length) {
			this.ensureCapacity(size);
		}

		if (size > this.size) {
			Arrays.fill(this.a, this.size, size, 0.0);
		}

		this.size = size;
	}

	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	public void trim() {
		this.trim(0);
	}

	public void trim(int n) {
		if (n < this.a.length && this.size != this.a.length) {
			double[] t = new double[Math.max(n, this.size)];
			System.arraycopy(this.a, 0, t, 0, this.size);
			this.a = t;

			assert this.size <= this.a.length;
		}
	}

	@Override
	public void getElements(int from, double[] a, int offset, int length) {
		DoubleArrays.ensureOffsetLength(a, offset, length);
		System.arraycopy(this.a, from, a, offset, length);
	}

	@Override
	public void removeElements(int from, int to) {
		it.unimi.dsi.fastutil.Arrays.ensureFromTo(this.size, from, to);
		System.arraycopy(this.a, to, this.a, from, this.size - to);
		this.size -= to - from;
	}

	@Override
	public void addElements(int index, double[] a, int offset, int length) {
		this.ensureIndex(index);
		DoubleArrays.ensureOffsetLength(a, offset, length);
		this.grow(this.size + length);
		System.arraycopy(this.a, index, this.a, index + length, this.size - index);
		System.arraycopy(a, offset, this.a, index, length);
		this.size += length;
	}

	@Override
	public double[] toArray(double[] a) {
		if (a == null || a.length < this.size) {
			a = new double[this.size];
		}

		System.arraycopy(this.a, 0, a, 0, this.size);
		return a;
	}

	@Override
	public boolean addAll(int index, DoubleCollection c) {
		this.ensureIndex(index);
		int n = c.size();
		if (n == 0) {
			return false;
		} else {
			this.grow(this.size + n);
			if (index != this.size) {
				System.arraycopy(this.a, index, this.a, index + n, this.size - index);
			}

			DoubleIterator i = c.iterator();
			this.size += n;

			while (n-- != 0) {
				this.a[index++] = i.nextDouble();
			}

			assert this.size <= this.a.length;

			return true;
		}
	}

	@Override
	public boolean addAll(int index, DoubleList l) {
		this.ensureIndex(index);
		int n = l.size();
		if (n == 0) {
			return false;
		} else {
			this.grow(this.size + n);
			if (index != this.size) {
				System.arraycopy(this.a, index, this.a, index + n, this.size - index);
			}

			l.getElements(0, this.a, index, n);
			this.size += n;

			assert this.size <= this.a.length;

			return true;
		}
	}

	@Override
	public boolean removeAll(DoubleCollection c) {
		double[] a = this.a;
		int j = 0;

		for (int i = 0; i < this.size; i++) {
			if (!c.contains(a[i])) {
				a[j++] = a[i];
			}
		}

		boolean modified = this.size != j;
		this.size = j;
		return modified;
	}

	public boolean removeAll(Collection<?> c) {
		double[] a = this.a;
		int j = 0;

		for (int i = 0; i < this.size; i++) {
			if (!c.contains(a[i])) {
				a[j++] = a[i];
			}
		}

		boolean modified = this.size != j;
		this.size = j;
		return modified;
	}

	@Override
	public DoubleListIterator listIterator(int index) {
		this.ensureIndex(index);
		return new DoubleListIterator() {
			int pos = index;
			int last = -1;

			public boolean hasNext() {
				return this.pos < DoubleArrayList.this.size;
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0;
			}

			@Override
			public double nextDouble() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return DoubleArrayList.this.a[this.last = this.pos++];
				}
			}

			@Override
			public double previousDouble() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return DoubleArrayList.this.a[this.last = --this.pos];
				}
			}

			public int nextIndex() {
				return this.pos;
			}

			public int previousIndex() {
				return this.pos - 1;
			}

			@Override
			public void add(double k) {
				DoubleArrayList.this.add(this.pos++, k);
				this.last = -1;
			}

			@Override
			public void set(double k) {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					DoubleArrayList.this.set(this.last, k);
				}
			}

			@Override
			public void remove() {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					DoubleArrayList.this.removeDouble(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1;
				}
			}
		};
	}

	public DoubleArrayList clone() {
		DoubleArrayList c = new DoubleArrayList(this.size);
		System.arraycopy(this.a, 0, c.a, 0, this.size);
		c.size = this.size;
		return c;
	}

	public boolean equals(DoubleArrayList l) {
		if (l == this) {
			return true;
		} else {
			int s = this.size();
			if (s != l.size()) {
				return false;
			} else {
				double[] a1 = this.a;
				double[] a2 = l.a;

				while (s-- != 0) {
					if (a1[s] != a2[s]) {
						return false;
					}
				}

				return true;
			}
		}
	}

	public int compareTo(DoubleArrayList l) {
		int s1 = this.size();
		int s2 = l.size();
		double[] a1 = this.a;
		double[] a2 = l.a;

		int i;
		for (i = 0; i < s1 && i < s2; i++) {
			double e1 = a1[i];
			double e2 = a2[i];
			int r;
			if ((r = Double.compare(e1, e2)) != 0) {
				return r;
			}
		}

		return i < s2 ? -1 : (i < s1 ? 1 : 0);
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeDouble(this.a[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.a = new double[this.size];

		for (int i = 0; i < this.size; i++) {
			this.a[i] = s.readDouble();
		}
	}
}
