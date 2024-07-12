package it.unimi.dsi.fastutil.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ObjectArraySet<K> extends AbstractObjectSet<K> implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient Object[] a;
	private int size;

	public ObjectArraySet(Object[] a) {
		this.a = a;
		this.size = a.length;
	}

	public ObjectArraySet() {
		this.a = ObjectArrays.EMPTY_ARRAY;
	}

	public ObjectArraySet(int capacity) {
		this.a = new Object[capacity];
	}

	public ObjectArraySet(ObjectCollection<K> c) {
		this(c.size());
		this.addAll(c);
	}

	public ObjectArraySet(Collection<? extends K> c) {
		this(c.size());
		this.addAll(c);
	}

	public ObjectArraySet(Object[] a, int size) {
		this.a = a;
		this.size = size;
		if (size > a.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the array size (" + a.length + ")");
		}
	}

	private int findKey(Object o) {
		int i = this.size;

		while (i-- != 0) {
			if (Objects.equals(this.a[i], o)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public ObjectIterator<K> iterator() {
		return new ObjectIterator<K>() {
			int next = 0;

			public boolean hasNext() {
				return this.next < ObjectArraySet.this.size;
			}

			public K next() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return (K)ObjectArraySet.this.a[this.next++];
				}
			}

			public void remove() {
				int tail = ObjectArraySet.this.size-- - this.next--;
				System.arraycopy(ObjectArraySet.this.a, this.next + 1, ObjectArraySet.this.a, this.next, tail);
				ObjectArraySet.this.a[ObjectArraySet.this.size] = null;
			}
		};
	}

	public boolean contains(Object k) {
		return this.findKey(k) != -1;
	}

	public int size() {
		return this.size;
	}

	public boolean remove(Object k) {
		int pos = this.findKey(k);
		if (pos == -1) {
			return false;
		} else {
			int tail = this.size - pos - 1;

			for (int i = 0; i < tail; i++) {
				this.a[pos + i] = this.a[pos + i + 1];
			}

			this.size--;
			this.a[this.size] = null;
			return true;
		}
	}

	public boolean add(K k) {
		int pos = this.findKey(k);
		if (pos != -1) {
			return false;
		} else {
			if (this.size == this.a.length) {
				Object[] b = new Object[this.size == 0 ? 2 : this.size * 2];
				int i = this.size;

				while (i-- != 0) {
					b[i] = this.a[i];
				}

				this.a = b;
			}

			this.a[this.size++] = k;
			return true;
		}
	}

	public void clear() {
		Arrays.fill(this.a, 0, this.size, null);
		this.size = 0;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public ObjectArraySet<K> clone() {
		ObjectArraySet<K> c;
		try {
			c = (ObjectArraySet<K>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.a = (Object[])this.a.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeObject(this.a[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.a = new Object[this.size];

		for (int i = 0; i < this.size; i++) {
			this.a[i] = s.readObject();
		}
	}
}
