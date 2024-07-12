package it.unimi.dsi.fastutil.floats;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

public class FloatArrayList extends AbstractFloatList implements RandomAccess, Cloneable, Serializable {
	private static final long serialVersionUID = -7046029254386353130L;
	public static final int DEFAULT_INITIAL_CAPACITY = 10;
	protected transient float[] a;
	protected int size;

	protected FloatArrayList(float[] a, boolean dummy) {
		this.a = a;
	}

	public FloatArrayList(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
		} else {
			if (capacity == 0) {
				this.a = FloatArrays.EMPTY_ARRAY;
			} else {
				this.a = new float[capacity];
			}
		}
	}

	public FloatArrayList() {
		this.a = FloatArrays.DEFAULT_EMPTY_ARRAY;
	}

	public FloatArrayList(Collection<? extends Float> c) {
		this(c.size());
		this.size = FloatIterators.unwrap(FloatIterators.asFloatIterator(c.iterator()), this.a);
	}

	public FloatArrayList(FloatCollection c) {
		this(c.size());
		this.size = FloatIterators.unwrap(c.iterator(), this.a);
	}

	public FloatArrayList(FloatList l) {
		this(l.size());
		l.getElements(0, this.a, 0, this.size = l.size());
	}

	public FloatArrayList(float[] a) {
		this(a, 0, a.length);
	}

	public FloatArrayList(float[] a, int offset, int length) {
		this(length);
		System.arraycopy(a, offset, this.a, 0, length);
		this.size = length;
	}

	public FloatArrayList(Iterator<? extends Float> i) {
		this();

		while (i.hasNext()) {
			this.add((Float)i.next());
		}
	}

	public FloatArrayList(FloatIterator i) {
		this();

		while (i.hasNext()) {
			this.add(i.nextFloat());
		}
	}

	public float[] elements() {
		return this.a;
	}

	public static FloatArrayList wrap(float[] a, int length) {
		if (length > a.length) {
			throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + a.length + ")");
		} else {
			FloatArrayList l = new FloatArrayList(a, false);
			l.size = length;
			return l;
		}
	}

	public static FloatArrayList wrap(float[] a) {
		return wrap(a, a.length);
	}

	public void ensureCapacity(int capacity) {
		if (capacity > this.a.length && this.a != FloatArrays.DEFAULT_EMPTY_ARRAY) {
			this.a = FloatArrays.ensureCapacity(this.a, capacity, this.size);

			assert this.size <= this.a.length;
		}
	}

	private void grow(int capacity) {
		if (capacity > this.a.length) {
			if (this.a != FloatArrays.DEFAULT_EMPTY_ARRAY) {
				capacity = (int)Math.max(Math.min((long)this.a.length + (long)(this.a.length >> 1), 2147483639L), (long)capacity);
			} else if (capacity < 10) {
				capacity = 10;
			}

			this.a = FloatArrays.forceCapacity(this.a, capacity, this.size);

			assert this.size <= this.a.length;
		}
	}

	@Override
	public void add(int index, float k) {
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
	public boolean add(float k) {
		this.grow(this.size + 1);
		this.a[this.size++] = k;

		assert this.size <= this.a.length;

		return true;
	}

	@Override
	public float getFloat(int index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			return this.a[index];
		}
	}

	@Override
	public int indexOf(float k) {
		for (int i = 0; i < this.size; i++) {
			if (Float.floatToIntBits(k) == Float.floatToIntBits(this.a[i])) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public int lastIndexOf(float k) {
		int i = this.size;

		while (i-- != 0) {
			if (Float.floatToIntBits(k) == Float.floatToIntBits(this.a[i])) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public float removeFloat(int index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			float old = this.a[index];
			this.size--;
			if (index != this.size) {
				System.arraycopy(this.a, index + 1, this.a, index, this.size - index);
			}

			assert this.size <= this.a.length;

			return old;
		}
	}

	@Override
	public boolean rem(float k) {
		int index = this.indexOf(k);
		if (index == -1) {
			return false;
		} else {
			this.removeFloat(index);

			assert this.size <= this.a.length;

			return true;
		}
	}

	@Override
	public float set(int index, float k) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
		} else {
			float old = this.a[index];
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
			Arrays.fill(this.a, this.size, size, 0.0F);
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
			float[] t = new float[Math.max(n, this.size)];
			System.arraycopy(this.a, 0, t, 0, this.size);
			this.a = t;

			assert this.size <= this.a.length;
		}
	}

	@Override
	public void getElements(int from, float[] a, int offset, int length) {
		FloatArrays.ensureOffsetLength(a, offset, length);
		System.arraycopy(this.a, from, a, offset, length);
	}

	@Override
	public void removeElements(int from, int to) {
		it.unimi.dsi.fastutil.Arrays.ensureFromTo(this.size, from, to);
		System.arraycopy(this.a, to, this.a, from, this.size - to);
		this.size -= to - from;
	}

	@Override
	public void addElements(int index, float[] a, int offset, int length) {
		this.ensureIndex(index);
		FloatArrays.ensureOffsetLength(a, offset, length);
		this.grow(this.size + length);
		System.arraycopy(this.a, index, this.a, index + length, this.size - index);
		System.arraycopy(a, offset, this.a, index, length);
		this.size += length;
	}

	@Override
	public float[] toArray(float[] a) {
		if (a == null || a.length < this.size) {
			a = new float[this.size];
		}

		System.arraycopy(this.a, 0, a, 0, this.size);
		return a;
	}

	@Override
	public boolean addAll(int index, FloatCollection c) {
		this.ensureIndex(index);
		int n = c.size();
		if (n == 0) {
			return false;
		} else {
			this.grow(this.size + n);
			if (index != this.size) {
				System.arraycopy(this.a, index, this.a, index + n, this.size - index);
			}

			FloatIterator i = c.iterator();
			this.size += n;

			while (n-- != 0) {
				this.a[index++] = i.nextFloat();
			}

			assert this.size <= this.a.length;

			return true;
		}
	}

	@Override
	public boolean addAll(int index, FloatList l) {
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
	public boolean removeAll(FloatCollection c) {
		float[] a = this.a;
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
		float[] a = this.a;
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
	public FloatListIterator listIterator(int index) {
		this.ensureIndex(index);
		return new FloatListIterator() {
			int pos = index;
			int last = -1;

			public boolean hasNext() {
				return this.pos < FloatArrayList.this.size;
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0;
			}

			@Override
			public float nextFloat() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return FloatArrayList.this.a[this.last = this.pos++];
				}
			}

			@Override
			public float previousFloat() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return FloatArrayList.this.a[this.last = --this.pos];
				}
			}

			public int nextIndex() {
				return this.pos;
			}

			public int previousIndex() {
				return this.pos - 1;
			}

			@Override
			public void add(float k) {
				FloatArrayList.this.add(this.pos++, k);
				this.last = -1;
			}

			@Override
			public void set(float k) {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					FloatArrayList.this.set(this.last, k);
				}
			}

			@Override
			public void remove() {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					FloatArrayList.this.removeFloat(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1;
				}
			}
		};
	}

	public FloatArrayList clone() {
		FloatArrayList c = new FloatArrayList(this.size);
		System.arraycopy(this.a, 0, c.a, 0, this.size);
		c.size = this.size;
		return c;
	}

	public boolean equals(FloatArrayList l) {
		if (l == this) {
			return true;
		} else {
			int s = this.size();
			if (s != l.size()) {
				return false;
			} else {
				float[] a1 = this.a;
				float[] a2 = l.a;

				while (s-- != 0) {
					if (a1[s] != a2[s]) {
						return false;
					}
				}

				return true;
			}
		}
	}

	public int compareTo(FloatArrayList l) {
		int s1 = this.size();
		int s2 = l.size();
		float[] a1 = this.a;
		float[] a2 = l.a;

		int i;
		for (i = 0; i < s1 && i < s2; i++) {
			float e1 = a1[i];
			float e2 = a2[i];
			int r;
			if ((r = Float.compare(e1, e2)) != 0) {
				return r;
			}
		}

		return i < s2 ? -1 : (i < s1 ? 1 : 0);
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeFloat(this.a[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.a = new float[this.size];

		for (int i = 0; i < this.size; i++) {
			this.a[i] = s.readFloat();
		}
	}
}
