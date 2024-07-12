package it.unimi.dsi.fastutil.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

public class ReferenceArrayList<K> extends AbstractReferenceList<K> implements RandomAccess, Cloneable, Serializable {
	private static final long serialVersionUID = -7046029254386353131L;
	public static final int DEFAULT_INITIAL_CAPACITY = 10;
	protected final boolean wrapped;
	protected transient K[] a;
	protected int size;

	protected ReferenceArrayList(K[] a, boolean dummy) {
		this.a = a;
		this.wrapped = true;
	}

	public ReferenceArrayList(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
		} else {
			if (capacity == 0) {
				this.a = (K[])ObjectArrays.EMPTY_ARRAY;
			} else {
				this.a = (K[])(new Object[capacity]);
			}

			this.wrapped = false;
		}
	}

	public ReferenceArrayList() {
		this.a = (K[])ObjectArrays.DEFAULT_EMPTY_ARRAY;
		this.wrapped = false;
	}

	public ReferenceArrayList(Collection<? extends K> c) {
		this(c.size());
		this.size = ObjectIterators.unwrap(c.iterator(), this.a);
	}

	public ReferenceArrayList(ReferenceCollection<? extends K> c) {
		this(c.size());
		this.size = ObjectIterators.unwrap(c.iterator(), this.a);
	}

	public ReferenceArrayList(ReferenceList<? extends K> l) {
		this(l.size());
		l.getElements(0, this.a, 0, this.size = l.size());
	}

	public ReferenceArrayList(K[] a) {
		this(a, 0, a.length);
	}

	public ReferenceArrayList(K[] a, int offset, int length) {
		this(length);
		System.arraycopy(a, offset, this.a, 0, length);
		this.size = length;
	}

	public ReferenceArrayList(Iterator<? extends K> i) {
		this();

		while (i.hasNext()) {
			this.add((K)i.next());
		}
	}

	public ReferenceArrayList(ObjectIterator<? extends K> i) {
		this();

		while (i.hasNext()) {
			this.add((K)i.next());
		}
	}

	public K[] elements() {
		return this.a;
	}

	public static <K> ReferenceArrayList<K> wrap(K[] a, int length) {
		if (length > a.length) {
			throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + a.length + ")");
		} else {
			ReferenceArrayList<K> l = new ReferenceArrayList<>(a, false);
			l.size = length;
			return l;
		}
	}

	public static <K> ReferenceArrayList<K> wrap(K[] a) {
		return wrap(a, a.length);
	}

	public void ensureCapacity(int capacity) {
		if (capacity > this.a.length && this.a != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
			if (this.wrapped) {
				this.a = (K[])ObjectArrays.ensureCapacity(this.a, capacity, this.size);
			} else if (capacity > this.a.length) {
				Object[] t = new Object[capacity];
				System.arraycopy(this.a, 0, t, 0, this.size);
				this.a = (K[])t;
			}

			assert this.size <= this.a.length;
		}
	}

	private void grow(int capacity) {
		if (capacity > this.a.length) {
			if (this.a != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
				capacity = (int)Math.max(Math.min((long)this.a.length + (long)(this.a.length >> 1), 2147483639L), (long)capacity);
			} else if (capacity < 10) {
				capacity = 10;
			}

			if (this.wrapped) {
				this.a = (K[])ObjectArrays.forceCapacity(this.a, capacity, this.size);
			} else {
				Object[] t = new Object[capacity];
				System.arraycopy(this.a, 0, t, 0, this.size);
				this.a = (K[])t;
			}

			assert this.size <= this.a.length;
		}
	}

	@Override
	public void add(int index, K k) {
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
	public boolean add(K k) {
		this.grow(this.size + 1);
		this.a[this.size++] = k;

		assert this.size <= this.a.length;

		return true;
	}

	public K get(int index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			return this.a[index];
		}
	}

	@Override
	public int indexOf(Object k) {
		for (int i = 0; i < this.size; i++) {
			if (k == this.a[i]) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public int lastIndexOf(Object k) {
		int i = this.size;

		while (i-- != 0) {
			if (k == this.a[i]) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public K remove(int index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			K old = this.a[index];
			this.size--;
			if (index != this.size) {
				System.arraycopy(this.a, index + 1, this.a, index, this.size - index);
			}

			this.a[this.size] = null;

			assert this.size <= this.a.length;

			return old;
		}
	}

	public boolean remove(Object k) {
		int index = this.indexOf(k);
		if (index == -1) {
			return false;
		} else {
			this.remove(index);

			assert this.size <= this.a.length;

			return true;
		}
	}

	@Override
	public K set(int index, K k) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			K old = this.a[index];
			this.a[index] = k;
			return old;
		}
	}

	@Override
	public void clear() {
		Arrays.fill(this.a, 0, this.size, null);
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
			Arrays.fill(this.a, this.size, size, null);
		} else {
			Arrays.fill(this.a, size, this.size, null);
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
			K[] t = (K[])(new Object[Math.max(n, this.size)]);
			System.arraycopy(this.a, 0, t, 0, this.size);
			this.a = t;

			assert this.size <= this.a.length;
		}
	}

	@Override
	public void getElements(int from, Object[] a, int offset, int length) {
		ObjectArrays.ensureOffsetLength(a, offset, length);
		System.arraycopy(this.a, from, a, offset, length);
	}

	@Override
	public void removeElements(int from, int to) {
		it.unimi.dsi.fastutil.Arrays.ensureFromTo(this.size, from, to);
		System.arraycopy(this.a, to, this.a, from, this.size - to);
		this.size -= to - from;
		int i = to - from;

		while (i-- != 0) {
			this.a[this.size + i] = null;
		}
	}

	@Override
	public void addElements(int index, K[] a, int offset, int length) {
		this.ensureIndex(index);
		ObjectArrays.ensureOffsetLength(a, offset, length);
		this.grow(this.size + length);
		System.arraycopy(this.a, index, this.a, index + length, this.size - index);
		System.arraycopy(a, offset, this.a, index, length);
		this.size += length;
	}

	public boolean removeAll(Collection<?> c) {
		Object[] a = this.a;
		int j = 0;

		for (int i = 0; i < this.size; i++) {
			if (!c.contains(a[i])) {
				a[j++] = a[i];
			}
		}

		Arrays.fill(a, j, this.size, null);
		boolean modified = this.size != j;
		this.size = j;
		return modified;
	}

	@Override
	public ObjectListIterator<K> listIterator(int index) {
		this.ensureIndex(index);
		return new ObjectListIterator<K>() {
			int pos = index;
			int last = -1;

			public boolean hasNext() {
				return this.pos < ReferenceArrayList.this.size;
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0;
			}

			public K next() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return ReferenceArrayList.this.a[this.last = this.pos++];
				}
			}

			@Override
			public K previous() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return ReferenceArrayList.this.a[this.last = --this.pos];
				}
			}

			public int nextIndex() {
				return this.pos;
			}

			public int previousIndex() {
				return this.pos - 1;
			}

			@Override
			public void add(K k) {
				ReferenceArrayList.this.add(this.pos++, k);
				this.last = -1;
			}

			@Override
			public void set(K k) {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					ReferenceArrayList.this.set(this.last, k);
				}
			}

			@Override
			public void remove() {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					ReferenceArrayList.this.remove(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1;
				}
			}
		};
	}

	public ReferenceArrayList<K> clone() {
		ReferenceArrayList<K> c = new ReferenceArrayList<>(this.size);
		System.arraycopy(this.a, 0, c.a, 0, this.size);
		c.size = this.size;
		return c;
	}

	public boolean equals(ReferenceArrayList<K> l) {
		if (l == this) {
			return true;
		} else {
			int s = this.size();
			if (s != l.size()) {
				return false;
			} else {
				K[] a1 = this.a;
				K[] a2 = l.a;

				while (s-- != 0) {
					if (a1[s] != a2[s]) {
						return false;
					}
				}

				return true;
			}
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeObject(this.a[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.a = (K[])(new Object[this.size]);

		for (int i = 0; i < this.size; i++) {
			this.a[i] = (K)s.readObject();
		}
	}
}
