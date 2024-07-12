package it.unimi.dsi.fastutil.booleans;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

public class BooleanArrayList extends AbstractBooleanList implements RandomAccess, Cloneable, Serializable {
	private static final long serialVersionUID = -7046029254386353130L;
	public static final int DEFAULT_INITIAL_CAPACITY = 10;
	protected transient boolean[] a;
	protected int size;

	protected BooleanArrayList(boolean[] a, boolean dummy) {
		this.a = a;
	}

	public BooleanArrayList(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
		} else {
			if (capacity == 0) {
				this.a = BooleanArrays.EMPTY_ARRAY;
			} else {
				this.a = new boolean[capacity];
			}
		}
	}

	public BooleanArrayList() {
		this.a = BooleanArrays.DEFAULT_EMPTY_ARRAY;
	}

	public BooleanArrayList(Collection<? extends Boolean> c) {
		this(c.size());
		this.size = BooleanIterators.unwrap(BooleanIterators.asBooleanIterator(c.iterator()), this.a);
	}

	public BooleanArrayList(BooleanCollection c) {
		this(c.size());
		this.size = BooleanIterators.unwrap(c.iterator(), this.a);
	}

	public BooleanArrayList(BooleanList l) {
		this(l.size());
		l.getElements(0, this.a, 0, this.size = l.size());
	}

	public BooleanArrayList(boolean[] a) {
		this(a, 0, a.length);
	}

	public BooleanArrayList(boolean[] a, int offset, int length) {
		this(length);
		System.arraycopy(a, offset, this.a, 0, length);
		this.size = length;
	}

	public BooleanArrayList(Iterator<? extends Boolean> i) {
		this();

		while (i.hasNext()) {
			this.add((Boolean)i.next());
		}
	}

	public BooleanArrayList(BooleanIterator i) {
		this();

		while (i.hasNext()) {
			this.add(i.nextBoolean());
		}
	}

	public boolean[] elements() {
		return this.a;
	}

	public static BooleanArrayList wrap(boolean[] a, int length) {
		if (length > a.length) {
			throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + a.length + ")");
		} else {
			BooleanArrayList l = new BooleanArrayList(a, false);
			l.size = length;
			return l;
		}
	}

	public static BooleanArrayList wrap(boolean[] a) {
		return wrap(a, a.length);
	}

	public void ensureCapacity(int capacity) {
		if (capacity > this.a.length && this.a != BooleanArrays.DEFAULT_EMPTY_ARRAY) {
			this.a = BooleanArrays.ensureCapacity(this.a, capacity, this.size);

			assert this.size <= this.a.length;
		}
	}

	private void grow(int capacity) {
		if (capacity > this.a.length) {
			if (this.a != BooleanArrays.DEFAULT_EMPTY_ARRAY) {
				capacity = (int)Math.max(Math.min((long)this.a.length + (long)(this.a.length >> 1), 2147483639L), (long)capacity);
			} else if (capacity < 10) {
				capacity = 10;
			}

			this.a = BooleanArrays.forceCapacity(this.a, capacity, this.size);

			assert this.size <= this.a.length;
		}
	}

	@Override
	public void add(int index, boolean k) {
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
	public boolean add(boolean k) {
		this.grow(this.size + 1);
		this.a[this.size++] = k;

		assert this.size <= this.a.length;

		return true;
	}

	@Override
	public boolean getBoolean(int index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			return this.a[index];
		}
	}

	@Override
	public int indexOf(boolean k) {
		for (int i = 0; i < this.size; i++) {
			if (k == this.a[i]) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public int lastIndexOf(boolean k) {
		int i = this.size;

		while (i-- != 0) {
			if (k == this.a[i]) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public boolean removeBoolean(int index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			boolean old = this.a[index];
			this.size--;
			if (index != this.size) {
				System.arraycopy(this.a, index + 1, this.a, index, this.size - index);
			}

			assert this.size <= this.a.length;

			return old;
		}
	}

	@Override
	public boolean rem(boolean k) {
		int index = this.indexOf(k);
		if (index == -1) {
			return false;
		} else {
			this.removeBoolean(index);

			assert this.size <= this.a.length;

			return true;
		}
	}

	@Override
	public boolean set(int index, boolean k) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			boolean old = this.a[index];
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
			Arrays.fill(this.a, this.size, size, false);
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
			boolean[] t = new boolean[Math.max(n, this.size)];
			System.arraycopy(this.a, 0, t, 0, this.size);
			this.a = t;

			assert this.size <= this.a.length;
		}
	}

	@Override
	public void getElements(int from, boolean[] a, int offset, int length) {
		BooleanArrays.ensureOffsetLength(a, offset, length);
		System.arraycopy(this.a, from, a, offset, length);
	}

	@Override
	public void removeElements(int from, int to) {
		it.unimi.dsi.fastutil.Arrays.ensureFromTo(this.size, from, to);
		System.arraycopy(this.a, to, this.a, from, this.size - to);
		this.size -= to - from;
	}

	@Override
	public void addElements(int index, boolean[] a, int offset, int length) {
		this.ensureIndex(index);
		BooleanArrays.ensureOffsetLength(a, offset, length);
		this.grow(this.size + length);
		System.arraycopy(this.a, index, this.a, index + length, this.size - index);
		System.arraycopy(a, offset, this.a, index, length);
		this.size += length;
	}

	@Override
	public boolean[] toArray(boolean[] a) {
		if (a == null || a.length < this.size) {
			a = new boolean[this.size];
		}

		System.arraycopy(this.a, 0, a, 0, this.size);
		return a;
	}

	@Override
	public boolean addAll(int index, BooleanCollection c) {
		this.ensureIndex(index);
		int n = c.size();
		if (n == 0) {
			return false;
		} else {
			this.grow(this.size + n);
			if (index != this.size) {
				System.arraycopy(this.a, index, this.a, index + n, this.size - index);
			}

			BooleanIterator i = c.iterator();
			this.size += n;

			while (n-- != 0) {
				this.a[index++] = i.nextBoolean();
			}

			assert this.size <= this.a.length;

			return true;
		}
	}

	@Override
	public boolean addAll(int index, BooleanList l) {
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
	public boolean removeAll(BooleanCollection c) {
		boolean[] a = this.a;
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
		boolean[] a = this.a;
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
	public BooleanListIterator listIterator(int index) {
		this.ensureIndex(index);
		return new BooleanListIterator() {
			int pos = index;
			int last = -1;

			public boolean hasNext() {
				return this.pos < BooleanArrayList.this.size;
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0;
			}

			@Override
			public boolean nextBoolean() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return BooleanArrayList.this.a[this.last = this.pos++];
				}
			}

			@Override
			public boolean previousBoolean() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return BooleanArrayList.this.a[this.last = --this.pos];
				}
			}

			public int nextIndex() {
				return this.pos;
			}

			public int previousIndex() {
				return this.pos - 1;
			}

			@Override
			public void add(boolean k) {
				BooleanArrayList.this.add(this.pos++, k);
				this.last = -1;
			}

			@Override
			public void set(boolean k) {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					BooleanArrayList.this.set(this.last, k);
				}
			}

			@Override
			public void remove() {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					BooleanArrayList.this.removeBoolean(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1;
				}
			}
		};
	}

	public BooleanArrayList clone() {
		BooleanArrayList c = new BooleanArrayList(this.size);
		System.arraycopy(this.a, 0, c.a, 0, this.size);
		c.size = this.size;
		return c;
	}

	public boolean equals(BooleanArrayList l) {
		if (l == this) {
			return true;
		} else {
			int s = this.size();
			if (s != l.size()) {
				return false;
			} else {
				boolean[] a1 = this.a;
				boolean[] a2 = l.a;

				while (s-- != 0) {
					if (a1[s] != a2[s]) {
						return false;
					}
				}

				return true;
			}
		}
	}

	public int compareTo(BooleanArrayList l) {
		int s1 = this.size();
		int s2 = l.size();
		boolean[] a1 = this.a;
		boolean[] a2 = l.a;

		int i;
		for (i = 0; i < s1 && i < s2; i++) {
			boolean e1 = a1[i];
			boolean e2 = a2[i];
			int r;
			if ((r = Boolean.compare(e1, e2)) != 0) {
				return r;
			}
		}

		return i < s2 ? -1 : (i < s1 ? 1 : 0);
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeBoolean(this.a[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.a = new boolean[this.size];

		for (int i = 0; i < this.size; i++) {
			this.a[i] = s.readBoolean();
		}
	}
}
