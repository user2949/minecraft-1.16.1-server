package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.BigArrays;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

public class ReferenceBigArrayBigList<K> extends AbstractReferenceBigList<K> implements RandomAccess, Cloneable, Serializable {
	private static final long serialVersionUID = -7046029254386353131L;
	public static final int DEFAULT_INITIAL_CAPACITY = 10;
	protected final boolean wrapped;
	protected transient K[][] a;
	protected long size;

	protected ReferenceBigArrayBigList(K[][] a, boolean dummy) {
		this.a = a;
		this.wrapped = true;
	}

	public ReferenceBigArrayBigList(long capacity) {
		if (capacity < 0L) {
			throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
		} else {
			if (capacity == 0L) {
				this.a = (K[][])ObjectBigArrays.EMPTY_BIG_ARRAY;
			} else {
				this.a = (K[][])ObjectBigArrays.newBigArray(capacity);
			}

			this.wrapped = false;
		}
	}

	public ReferenceBigArrayBigList() {
		this.a = (K[][])ObjectBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
		this.wrapped = false;
	}

	public ReferenceBigArrayBigList(ReferenceCollection<? extends K> c) {
		this((long)c.size());
		ObjectIterator<? extends K> i = c.iterator();

		while (i.hasNext()) {
			this.add((K)i.next());
		}
	}

	public ReferenceBigArrayBigList(ReferenceBigList<? extends K> l) {
		this(l.size64());
		l.getElements(0L, this.a, 0L, this.size = l.size64());
	}

	public ReferenceBigArrayBigList(K[][] a) {
		this(a, 0L, ObjectBigArrays.length(a));
	}

	public ReferenceBigArrayBigList(K[][] a, long offset, long length) {
		this(length);
		ObjectBigArrays.copy(a, offset, this.a, 0L, length);
		this.size = length;
	}

	public ReferenceBigArrayBigList(Iterator<? extends K> i) {
		this();

		while (i.hasNext()) {
			this.add((K)i.next());
		}
	}

	public ReferenceBigArrayBigList(ObjectIterator<? extends K> i) {
		this();

		while (i.hasNext()) {
			this.add((K)i.next());
		}
	}

	public K[][] elements() {
		return this.a;
	}

	public static <K> ReferenceBigArrayBigList<K> wrap(K[][] a, long length) {
		if (length > ObjectBigArrays.length(a)) {
			throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + ObjectBigArrays.length(a) + ")");
		} else {
			ReferenceBigArrayBigList<K> l = new ReferenceBigArrayBigList<>(a, false);
			l.size = length;
			return l;
		}
	}

	public static <K> ReferenceBigArrayBigList<K> wrap(K[][] a) {
		return wrap(a, ObjectBigArrays.length(a));
	}

	public void ensureCapacity(long capacity) {
		if (capacity > (long)this.a.length && this.a != ObjectBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
			if (this.wrapped) {
				this.a = (K[][])ObjectBigArrays.forceCapacity(this.a, capacity, this.size);
			} else if (capacity > ObjectBigArrays.length(this.a)) {
				Object[][] t = ObjectBigArrays.newBigArray(capacity);
				ObjectBigArrays.copy(this.a, 0L, t, 0L, this.size);
				this.a = (K[][])t;
			}

			assert this.size <= ObjectBigArrays.length(this.a);
		}
	}

	private void grow(long capacity) {
		long oldLength = ObjectBigArrays.length(this.a);
		if (capacity > oldLength) {
			if (this.a != ObjectBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
				capacity = Math.max(oldLength + (oldLength >> 1), capacity);
			} else if (capacity < 10L) {
				capacity = 10L;
			}

			if (this.wrapped) {
				this.a = (K[][])ObjectBigArrays.forceCapacity(this.a, capacity, this.size);
			} else {
				Object[][] t = ObjectBigArrays.newBigArray(capacity);
				ObjectBigArrays.copy(this.a, 0L, t, 0L, this.size);
				this.a = (K[][])t;
			}

			assert this.size <= ObjectBigArrays.length(this.a);
		}
	}

	@Override
	public void add(long index, K k) {
		this.ensureIndex(index);
		this.grow(this.size + 1L);
		if (index != this.size) {
			ObjectBigArrays.copy(this.a, index, this.a, index + 1L, this.size - index);
		}

		ObjectBigArrays.set(this.a, index, k);
		this.size++;

		assert this.size <= ObjectBigArrays.length(this.a);
	}

	@Override
	public boolean add(K k) {
		this.grow(this.size + 1L);
		ObjectBigArrays.set(this.a, this.size++, k);

		assert this.size <= ObjectBigArrays.length(this.a);

		return true;
	}

	@Override
	public K get(long index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			return ObjectBigArrays.get(this.a, index);
		}
	}

	@Override
	public long indexOf(Object k) {
		for (long i = 0L; i < this.size; i++) {
			if (k == ObjectBigArrays.get(this.a, i)) {
				return i;
			}
		}

		return -1L;
	}

	@Override
	public long lastIndexOf(Object k) {
		long i = this.size;

		while (i-- != 0L) {
			if (k == ObjectBigArrays.get(this.a, i)) {
				return i;
			}
		}

		return -1L;
	}

	@Override
	public K remove(long index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			K old = ObjectBigArrays.get(this.a, index);
			this.size--;
			if (index != this.size) {
				ObjectBigArrays.copy(this.a, index + 1L, this.a, index, this.size - index);
			}

			ObjectBigArrays.set(this.a, this.size, null);

			assert this.size <= ObjectBigArrays.length(this.a);

			return old;
		}
	}

	public boolean remove(Object k) {
		long index = this.indexOf(k);
		if (index == -1L) {
			return false;
		} else {
			this.remove(index);

			assert this.size <= ObjectBigArrays.length(this.a);

			return true;
		}
	}

	@Override
	public K set(long index, K k) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			K old = ObjectBigArrays.get(this.a, index);
			ObjectBigArrays.set(this.a, index, k);
			return old;
		}
	}

	public boolean removeAll(Collection<?> c) {
		K[] s = null;
		K[] d = null;
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
		ObjectBigArrays.fill(this.a, j, this.size, null);
		boolean modified = this.size != j;
		this.size = j;
		return modified;
	}

	@Override
	public void clear() {
		ObjectBigArrays.fill(this.a, 0L, this.size, null);
		this.size = 0L;

		assert this.size <= ObjectBigArrays.length(this.a);
	}

	@Override
	public long size64() {
		return this.size;
	}

	@Override
	public void size(long size) {
		if (size > ObjectBigArrays.length(this.a)) {
			this.ensureCapacity(size);
		}

		if (size > this.size) {
			ObjectBigArrays.fill(this.a, this.size, size, null);
		} else {
			ObjectBigArrays.fill(this.a, size, this.size, null);
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
		long arrayLength = ObjectBigArrays.length(this.a);
		if (n < arrayLength && this.size != arrayLength) {
			this.a = (K[][])ObjectBigArrays.trim(this.a, Math.max(n, this.size));

			assert this.size <= ObjectBigArrays.length(this.a);
		}
	}

	@Override
	public void getElements(long from, Object[][] a, long offset, long length) {
		ObjectBigArrays.copy(this.a, from, a, offset, length);
	}

	@Override
	public void removeElements(long from, long to) {
		BigArrays.ensureFromTo(this.size, from, to);
		ObjectBigArrays.copy(this.a, to, this.a, from, this.size - to);
		this.size -= to - from;
		ObjectBigArrays.fill(this.a, this.size, this.size + to - from, null);
	}

	@Override
	public void addElements(long index, K[][] a, long offset, long length) {
		this.ensureIndex(index);
		ObjectBigArrays.ensureOffsetLength(a, offset, length);
		this.grow(this.size + length);
		ObjectBigArrays.copy(this.a, index, this.a, index + length, this.size - index);
		ObjectBigArrays.copy(a, offset, this.a, index, length);
		this.size += length;
	}

	@Override
	public ObjectBigListIterator<K> listIterator(long index) {
		this.ensureIndex(index);
		return new ObjectBigListIterator<K>() {
			long pos = index;
			long last = -1L;

			public boolean hasNext() {
				return this.pos < ReferenceBigArrayBigList.this.size;
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0L;
			}

			public K next() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return ObjectBigArrays.get(ReferenceBigArrayBigList.this.a, this.last = this.pos++);
				}
			}

			@Override
			public K previous() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return ObjectBigArrays.get(ReferenceBigArrayBigList.this.a, this.last = --this.pos);
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
			public void add(K k) {
				ReferenceBigArrayBigList.this.add(this.pos++, k);
				this.last = -1L;
			}

			@Override
			public void set(K k) {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					ReferenceBigArrayBigList.this.set(this.last, k);
				}
			}

			public void remove() {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					ReferenceBigArrayBigList.this.remove(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1L;
				}
			}
		};
	}

	public ReferenceBigArrayBigList<K> clone() {
		ReferenceBigArrayBigList<K> c = new ReferenceBigArrayBigList<>(this.size);
		ObjectBigArrays.copy(this.a, 0L, c.a, 0L, this.size);
		c.size = this.size;
		return c;
	}

	public boolean equals(ReferenceBigArrayBigList<K> l) {
		if (l == this) {
			return true;
		} else {
			long s = this.size64();
			if (s != l.size64()) {
				return false;
			} else {
				K[][] a1 = this.a;
				K[][] a2 = l.a;

				while (s-- != 0L) {
					if (ObjectBigArrays.get(a1, s) != ObjectBigArrays.get(a2, s)) {
						return false;
					}
				}

				return true;
			}
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; (long)i < this.size; i++) {
			s.writeObject(ObjectBigArrays.get(this.a, (long)i));
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.a = (K[][])ObjectBigArrays.newBigArray(this.size);

		for (int i = 0; (long)i < this.size; i++) {
			ObjectBigArrays.set(this.a, (long)i, s.readObject());
		}
	}
}
