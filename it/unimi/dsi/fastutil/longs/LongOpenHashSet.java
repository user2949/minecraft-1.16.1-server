package it.unimi.dsi.fastutil.longs;

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

public class LongOpenHashSet extends AbstractLongSet implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient long[] key;
	protected transient int mask;
	protected transient boolean containsNull;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;

	public LongOpenHashSet(int expected, float f) {
		if (f <= 0.0F || f > 1.0F) {
			throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
		} else if (expected < 0) {
			throw new IllegalArgumentException("The expected number of elements must be nonnegative");
		} else {
			this.f = f;
			this.minN = this.n = HashCommon.arraySize(expected, f);
			this.mask = this.n - 1;
			this.maxFill = HashCommon.maxFill(this.n, f);
			this.key = new long[this.n + 1];
		}
	}

	public LongOpenHashSet(int expected) {
		this(expected, 0.75F);
	}

	public LongOpenHashSet() {
		this(16, 0.75F);
	}

	public LongOpenHashSet(Collection<? extends Long> c, float f) {
		this(c.size(), f);
		this.addAll(c);
	}

	public LongOpenHashSet(Collection<? extends Long> c) {
		this(c, 0.75F);
	}

	public LongOpenHashSet(LongCollection c, float f) {
		this(c.size(), f);
		this.addAll(c);
	}

	public LongOpenHashSet(LongCollection c) {
		this(c, 0.75F);
	}

	public LongOpenHashSet(LongIterator i, float f) {
		this(16, f);

		while (i.hasNext()) {
			this.add(i.nextLong());
		}
	}

	public LongOpenHashSet(LongIterator i) {
		this(i, 0.75F);
	}

	public LongOpenHashSet(Iterator<?> i, float f) {
		this(LongIterators.asLongIterator(i), f);
	}

	public LongOpenHashSet(Iterator<?> i) {
		this(LongIterators.asLongIterator(i));
	}

	public LongOpenHashSet(long[] a, int offset, int length, float f) {
		this(length < 0 ? 0 : length, f);
		LongArrays.ensureOffsetLength(a, offset, length);

		for (int i = 0; i < length; i++) {
			this.add(a[offset + i]);
		}
	}

	public LongOpenHashSet(long[] a, int offset, int length) {
		this(a, offset, length, 0.75F);
	}

	public LongOpenHashSet(long[] a, float f) {
		this(a, 0, a.length, f);
	}

	public LongOpenHashSet(long[] a) {
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

	@Override
	public boolean addAll(LongCollection c) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(c.size());
		} else {
			this.tryCapacity((long)(this.size() + c.size()));
		}

		return super.addAll(c);
	}

	public boolean addAll(Collection<? extends Long> c) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(c.size());
		} else {
			this.tryCapacity((long)(this.size() + c.size()));
		}

		return super.addAll(c);
	}

	@Override
	public boolean add(long k) {
		if (k == 0L) {
			if (this.containsNull) {
				return false;
			}

			this.containsNull = true;
		} else {
			long[] key = this.key;
			int pos;
			long curr;
			if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
				if (curr == k) {
					return false;
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
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
		long[] key = this.key;

		label30:
		while (true) {
			int last = pos;

			long curr;
			for (pos = pos + 1 & this.mask; (curr = key[pos]) != 0L; pos = pos + 1 & this.mask) {
				int slot = (int)HashCommon.mix(curr) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					key[last] = curr;
					continue label30;
				}
			}

			key[last] = 0L;
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
		this.key[this.n] = 0L;
		this.size--;
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return true;
	}

	@Override
	public boolean remove(long k) {
		if (k == 0L) {
			return this.containsNull ? this.removeNullEntry() : false;
		} else {
			long[] key = this.key;
			long curr;
			int pos;
			if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) {
				return false;
			} else if (k == curr) {
				return this.removeEntry(pos);
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (k == curr) {
						return this.removeEntry(pos);
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean contains(long k) {
		if (k == 0L) {
			return this.containsNull;
		} else {
			long[] key = this.key;
			long curr;
			int pos;
			if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) {
				return false;
			} else if (k == curr) {
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
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
			Arrays.fill(this.key, 0L);
		}
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	@Override
	public LongIterator iterator() {
		return new LongOpenHashSet.SetIterator();
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
		long[] key = this.key;
		int mask = newN - 1;
		long[] newKey = new long[newN + 1];
		int i = this.n;
		int j = this.realSize();

		while (j-- != 0) {
			while (key[--i] == 0L) {
			}

			int pos;
			if (newKey[pos = (int)HashCommon.mix(key[i]) & mask] != 0L) {
				while (newKey[pos = pos + 1 & mask] != 0L) {
				}
			}

			newKey[pos] = key[i];
		}

		this.n = newN;
		this.mask = mask;
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.key = newKey;
	}

	public LongOpenHashSet clone() {
		LongOpenHashSet c;
		try {
			c = (LongOpenHashSet)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (long[])this.key.clone();
		c.containsNull = this.containsNull;
		return c;
	}

	@Override
	public int hashCode() {
		int h = 0;
		int j = this.realSize();

		for (int i = 0; j-- != 0; i++) {
			while (this.key[i] == 0L) {
				i++;
			}

			h += HashCommon.long2int(this.key[i]);
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		LongIterator i = this.iterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			s.writeLong(i.nextLong());
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		long[] key = this.key = new long[this.n + 1];
		int i = this.size;

		while (i-- != 0) {
			long k = s.readLong();
			int pos;
			if (k == 0L) {
				pos = this.n;
				this.containsNull = true;
			} else if (key[pos = (int)HashCommon.mix(k) & this.mask] != 0L) {
				while (key[pos = pos + 1 & this.mask] != 0L) {
				}
			}

			key[pos] = k;
		}
	}

	private void checkTable() {
	}

	private class SetIterator implements LongIterator {
		int pos = LongOpenHashSet.this.n;
		int last = -1;
		int c = LongOpenHashSet.this.size;
		boolean mustReturnNull = LongOpenHashSet.this.containsNull;
		LongArrayList wrapped;

		private SetIterator() {
		}

		public boolean hasNext() {
			return this.c != 0;
		}

		@Override
		public long nextLong() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.c--;
				if (this.mustReturnNull) {
					this.mustReturnNull = false;
					this.last = LongOpenHashSet.this.n;
					return LongOpenHashSet.this.key[LongOpenHashSet.this.n];
				} else {
					long[] key = LongOpenHashSet.this.key;

					while (--this.pos >= 0) {
						if (key[this.pos] != 0L) {
							return key[this.last = this.pos];
						}
					}

					this.last = Integer.MIN_VALUE;
					return this.wrapped.getLong(-this.pos - 1);
				}
			}
		}

		private final void shiftKeys(int pos) {
			long[] key = LongOpenHashSet.this.key;

			label38:
			while (true) {
				int last = pos;

				long curr;
				for (pos = pos + 1 & LongOpenHashSet.this.mask; (curr = key[pos]) != 0L; pos = pos + 1 & LongOpenHashSet.this.mask) {
					int slot = (int)HashCommon.mix(curr) & LongOpenHashSet.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new LongArrayList(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						continue label38;
					}
				}

				key[last] = 0L;
				return;
			}
		}

		public void remove() {
			if (this.last == -1) {
				throw new IllegalStateException();
			} else {
				if (this.last == LongOpenHashSet.this.n) {
					LongOpenHashSet.this.containsNull = false;
					LongOpenHashSet.this.key[LongOpenHashSet.this.n] = 0L;
				} else {
					if (this.pos < 0) {
						LongOpenHashSet.this.remove(this.wrapped.getLong(-this.pos - 1));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				LongOpenHashSet.this.size--;
				this.last = -1;
			}
		}
	}
}
