package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReferenceOpenHashSet<K> extends AbstractReferenceSet<K> implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient K[] key;
	protected transient int mask;
	protected transient boolean containsNull;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;

	public ReferenceOpenHashSet(int expected, float f) {
		if (f <= 0.0F || f > 1.0F) {
			throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
		} else if (expected < 0) {
			throw new IllegalArgumentException("The expected number of elements must be nonnegative");
		} else {
			this.f = f;
			this.minN = this.n = HashCommon.arraySize(expected, f);
			this.mask = this.n - 1;
			this.maxFill = HashCommon.maxFill(this.n, f);
			this.key = (K[])(new Object[this.n + 1]);
		}
	}

	public ReferenceOpenHashSet(int expected) {
		this(expected, 0.75F);
	}

	public ReferenceOpenHashSet() {
		this(16, 0.75F);
	}

	public ReferenceOpenHashSet(Collection<? extends K> c, float f) {
		this(c.size(), f);
		this.addAll(c);
	}

	public ReferenceOpenHashSet(Collection<? extends K> c) {
		this(c, 0.75F);
	}

	public ReferenceOpenHashSet(ReferenceCollection<? extends K> c, float f) {
		this(c.size(), f);
		this.addAll(c);
	}

	public ReferenceOpenHashSet(ReferenceCollection<? extends K> c) {
		this(c, 0.75F);
	}

	public ReferenceOpenHashSet(Iterator<? extends K> i, float f) {
		this(16, f);

		while (i.hasNext()) {
			this.add((K)i.next());
		}
	}

	public ReferenceOpenHashSet(Iterator<? extends K> i) {
		this(i, 0.75F);
	}

	public ReferenceOpenHashSet(K[] a, int offset, int length, float f) {
		this(length < 0 ? 0 : length, f);
		ObjectArrays.ensureOffsetLength(a, offset, length);

		for (int i = 0; i < length; i++) {
			this.add(a[offset + i]);
		}
	}

	public ReferenceOpenHashSet(K[] a, int offset, int length) {
		this(a, offset, length, 0.75F);
	}

	public ReferenceOpenHashSet(K[] a, float f) {
		this(a, 0, a.length, f);
	}

	public ReferenceOpenHashSet(K[] a) {
		this(a, 0.75F);
	}

	private int realSize() {
		return this.containsNull ? this.size - 1 : this.size;
	}

	private void ensureCapacity(int capacity) {
		int needed = HashCommon.arraySize(capacity, this.f);
		if (needed > this.n) {
			this.rehash(needed);
		}
	}

	private void tryCapacity(long capacity) {
		int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil((double)((float)capacity / this.f)))));
		if (needed > this.n) {
			this.rehash(needed);
		}
	}

	public boolean addAll(Collection<? extends K> c) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(c.size());
		} else {
			this.tryCapacity((long)(this.size() + c.size()));
		}

		return super.addAll(c);
	}

	public boolean add(K k) {
		if (k == null) {
			if (this.containsNull) {
				return false;
			}

			this.containsNull = true;
		} else {
			K[] key = this.key;
			int pos;
			K curr;
			if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
				if (curr == k) {
					return false;
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (curr == k) {
						return false;
					}
				}
			}

			key[pos] = k;
		}

		if (this.size++ >= this.maxFill) {
			this.rehash(HashCommon.arraySize(this.size + 1, this.f));
		}

		return true;
	}

	protected final void shiftKeys(int pos) {
		K[] key = this.key;

		label30:
		while (true) {
			int last = pos;

			K curr;
			for (pos = pos + 1 & this.mask; (curr = key[pos]) != null; pos = pos + 1 & this.mask) {
				int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					key[last] = curr;
					continue label30;
				}
			}

			key[last] = null;
			return;
		}
	}

	private boolean removeEntry(int pos) {
		this.size--;
		this.shiftKeys(pos);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return true;
	}

	private boolean removeNullEntry() {
		this.containsNull = false;
		this.key[this.n] = null;
		this.size--;
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return true;
	}

	public boolean remove(Object k) {
		if (k == null) {
			return this.containsNull ? this.removeNullEntry() : false;
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
				return false;
			} else if (k == curr) {
				return this.removeEntry(pos);
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (k == curr) {
						return this.removeEntry(pos);
					}
				}

				return false;
			}
		}
	}

	public boolean contains(Object k) {
		if (k == null) {
			return this.containsNull;
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
				return false;
			} else if (k == curr) {
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (k == curr) {
						return true;
					}
				}

				return false;
			}
		}
	}

	public void clear() {
		if (this.size != 0) {
			this.size = 0;
			this.containsNull = false;
			Arrays.fill(this.key, null);
		}
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	@Override
	public ObjectIterator<K> iterator() {
		return new ReferenceOpenHashSet.SetIterator();
	}

	public boolean trim() {
		int l = HashCommon.arraySize(this.size, this.f);
		if (l < this.n && this.size <= HashCommon.maxFill(l, this.f)) {
			try {
				this.rehash(l);
				return true;
			} catch (OutOfMemoryError var3) {
				return false;
			}
		} else {
			return true;
		}
	}

	public boolean trim(int n) {
		int l = HashCommon.nextPowerOfTwo((int)Math.ceil((double)((float)n / this.f)));
		if (l < n && this.size <= HashCommon.maxFill(l, this.f)) {
			try {
				this.rehash(l);
				return true;
			} catch (OutOfMemoryError var4) {
				return false;
			}
		} else {
			return true;
		}
	}

	protected void rehash(int newN) {
		K[] key = this.key;
		int mask = newN - 1;
		K[] newKey = (K[])(new Object[newN + 1]);
		int i = this.n;
		int j = this.realSize();

		while (j-- != 0) {
			while (key[--i] == null) {
			}

			int pos;
			if (newKey[pos = HashCommon.mix(System.identityHashCode(key[i])) & mask] != null) {
				while (newKey[pos = pos + 1 & mask] != null) {
				}
			}

			newKey[pos] = key[i];
		}

		this.n = newN;
		this.mask = mask;
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.key = newKey;
	}

	public ReferenceOpenHashSet<K> clone() {
		ReferenceOpenHashSet<K> c;
		try {
			c = (ReferenceOpenHashSet<K>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (K[])((Object[])this.key.clone());
		c.containsNull = this.containsNull;
		return c;
	}

	@Override
	public int hashCode() {
		int h = 0;
		int j = this.realSize();

		for (int i = 0; j-- != 0; i++) {
			while (this.key[i] == null) {
				i++;
			}

			if (this != this.key[i]) {
				h += System.identityHashCode(this.key[i]);
			}
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		ObjectIterator<K> i = this.iterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			s.writeObject(i.next());
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		K[] key = this.key = (K[])(new Object[this.n + 1]);
		int i = this.size;

		while (i-- != 0) {
			K k = (K)s.readObject();
			int pos;
			if (k == null) {
				pos = this.n;
				this.containsNull = true;
			} else if (key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask] != null) {
				while (key[pos = pos + 1 & this.mask] != null) {
				}
			}

			key[pos] = k;
		}
	}

	private void checkTable() {
	}

	private class SetIterator implements ObjectIterator<K> {
		int pos = ReferenceOpenHashSet.this.n;
		int last = -1;
		int c = ReferenceOpenHashSet.this.size;
		boolean mustReturnNull = ReferenceOpenHashSet.this.containsNull;
		ReferenceArrayList<K> wrapped;

		private SetIterator() {
		}

		public boolean hasNext() {
			return this.c != 0;
		}

		public K next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.c--;
				if (this.mustReturnNull) {
					this.mustReturnNull = false;
					this.last = ReferenceOpenHashSet.this.n;
					return ReferenceOpenHashSet.this.key[ReferenceOpenHashSet.this.n];
				} else {
					K[] key = ReferenceOpenHashSet.this.key;

					while (--this.pos >= 0) {
						if (key[this.pos] != null) {
							return key[this.last = this.pos];
						}
					}

					this.last = Integer.MIN_VALUE;
					return this.wrapped.get(-this.pos - 1);
				}
			}
		}

		private final void shiftKeys(int pos) {
			K[] key = ReferenceOpenHashSet.this.key;

			label38:
			while (true) {
				int last = pos;

				K curr;
				for (pos = pos + 1 & ReferenceOpenHashSet.this.mask; (curr = key[pos]) != null; pos = pos + 1 & ReferenceOpenHashSet.this.mask) {
					int slot = HashCommon.mix(System.identityHashCode(curr)) & ReferenceOpenHashSet.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new ReferenceArrayList<>(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						continue label38;
					}
				}

				key[last] = null;
				return;
			}
		}

		public void remove() {
			if (this.last == -1) {
				throw new IllegalStateException();
			} else {
				if (this.last == ReferenceOpenHashSet.this.n) {
					ReferenceOpenHashSet.this.containsNull = false;
					ReferenceOpenHashSet.this.key[ReferenceOpenHashSet.this.n] = null;
				} else {
					if (this.pos < 0) {
						ReferenceOpenHashSet.this.remove(this.wrapped.set(-this.pos - 1, null));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				ReferenceOpenHashSet.this.size--;
				this.last = -1;
			}
		}
	}
}
