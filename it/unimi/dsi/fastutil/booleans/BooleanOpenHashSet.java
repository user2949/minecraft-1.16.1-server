package it.unimi.dsi.fastutil.booleans;

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

public class BooleanOpenHashSet extends AbstractBooleanSet implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient boolean[] key;
	protected transient int mask;
	protected transient boolean containsNull;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;

	public BooleanOpenHashSet(int expected, float f) {
		if (f <= 0.0F || f > 1.0F) {
			throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
		} else if (expected < 0) {
			throw new IllegalArgumentException("The expected number of elements must be nonnegative");
		} else {
			this.f = f;
			this.minN = this.n = HashCommon.arraySize(expected, f);
			this.mask = this.n - 1;
			this.maxFill = HashCommon.maxFill(this.n, f);
			this.key = new boolean[this.n + 1];
		}
	}

	public BooleanOpenHashSet(int expected) {
		this(expected, 0.75F);
	}

	public BooleanOpenHashSet() {
		this(16, 0.75F);
	}

	public BooleanOpenHashSet(Collection<? extends Boolean> c, float f) {
		this(c.size(), f);
		this.addAll(c);
	}

	public BooleanOpenHashSet(Collection<? extends Boolean> c) {
		this(c, 0.75F);
	}

	public BooleanOpenHashSet(BooleanCollection c, float f) {
		this(c.size(), f);
		this.addAll(c);
	}

	public BooleanOpenHashSet(BooleanCollection c) {
		this(c, 0.75F);
	}

	public BooleanOpenHashSet(BooleanIterator i, float f) {
		this(16, f);

		while (i.hasNext()) {
			this.add(i.nextBoolean());
		}
	}

	public BooleanOpenHashSet(BooleanIterator i) {
		this(i, 0.75F);
	}

	public BooleanOpenHashSet(Iterator<?> i, float f) {
		this(BooleanIterators.asBooleanIterator(i), f);
	}

	public BooleanOpenHashSet(Iterator<?> i) {
		this(BooleanIterators.asBooleanIterator(i));
	}

	public BooleanOpenHashSet(boolean[] a, int offset, int length, float f) {
		this(length < 0 ? 0 : length, f);
		BooleanArrays.ensureOffsetLength(a, offset, length);

		for (int i = 0; i < length; i++) {
			this.add(a[offset + i]);
		}
	}

	public BooleanOpenHashSet(boolean[] a, int offset, int length) {
		this(a, offset, length, 0.75F);
	}

	public BooleanOpenHashSet(boolean[] a, float f) {
		this(a, 0, a.length, f);
	}

	public BooleanOpenHashSet(boolean[] a) {
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
	public boolean addAll(BooleanCollection c) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(c.size());
		} else {
			this.tryCapacity((long)(this.size() + c.size()));
		}

		return super.addAll(c);
	}

	public boolean addAll(Collection<? extends Boolean> c) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(c.size());
		} else {
			this.tryCapacity((long)(this.size() + c.size()));
		}

		return super.addAll(c);
	}

	@Override
	public boolean add(boolean k) {
		if (!k) {
			if (this.containsNull) {
				return false;
			}

			this.containsNull = true;
		} else {
			boolean[] key = this.key;
			int pos;
			boolean curr;
			if (curr = key[pos = (k ? 262886248 : -878682501) & this.mask]) {
				if (curr == k) {
					return false;
				}

				while (curr = key[pos = pos + 1 & this.mask]) {
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
		boolean[] key = this.key;

		label35:
		while (true) {
			int last = pos;

			boolean curr;
			for (pos = pos + 1 & this.mask; curr = key[pos]; pos = pos + 1 & this.mask) {
				int slot = (curr ? 262886248 : -878682501) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					key[last] = curr;
					continue label35;
				}
			}

			key[last] = false;
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
		this.key[this.n] = false;
		this.size--;
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return true;
	}

	@Override
	public boolean remove(boolean k) {
		if (!k) {
			return this.containsNull ? this.removeNullEntry() : false;
		} else {
			boolean[] key = this.key;
			boolean curr;
			int pos;
			if (!(curr = key[pos = (k ? 262886248 : -878682501) & this.mask])) {
				return false;
			} else if (k == curr) {
				return this.removeEntry(pos);
			} else {
				while (curr = key[pos = pos + 1 & this.mask]) {
					if (k == curr) {
						return this.removeEntry(pos);
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean contains(boolean k) {
		if (!k) {
			return this.containsNull;
		} else {
			boolean[] key = this.key;
			boolean curr;
			int pos;
			if (!(curr = key[pos = (k ? 262886248 : -878682501) & this.mask])) {
				return false;
			} else if (k == curr) {
				return true;
			} else {
				while (curr = key[pos = pos + 1 & this.mask]) {
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
			Arrays.fill(this.key, false);
		}
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	@Override
	public BooleanIterator iterator() {
		return new BooleanOpenHashSet.SetIterator();
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
		boolean[] key = this.key;
		int mask = newN - 1;
		boolean[] newKey = new boolean[newN + 1];
		int i = this.n;
		int j = this.realSize();

		while (j-- != 0) {
			while (!key[--i]) {
			}

			int pos;
			if (newKey[pos = (key[i] ? 262886248 : -878682501) & mask]) {
				while (newKey[pos = pos + 1 & mask]) {
				}
			}

			newKey[pos] = key[i];
		}

		this.n = newN;
		this.mask = mask;
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.key = newKey;
	}

	public BooleanOpenHashSet clone() {
		BooleanOpenHashSet c;
		try {
			c = (BooleanOpenHashSet)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (boolean[])this.key.clone();
		c.containsNull = this.containsNull;
		return c;
	}

	@Override
	public int hashCode() {
		int h = 0;
		int j = this.realSize();

		for (int i = 0; j-- != 0; i++) {
			while (!this.key[i]) {
				i++;
			}

			h += this.key[i] ? 1231 : 1237;
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		BooleanIterator i = this.iterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			s.writeBoolean(i.nextBoolean());
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		boolean[] key = this.key = new boolean[this.n + 1];
		int i = this.size;

		while (i-- != 0) {
			boolean k = s.readBoolean();
			int pos;
			if (!k) {
				pos = this.n;
				this.containsNull = true;
			} else if (key[pos = (k ? 262886248 : -878682501) & this.mask]) {
				while (key[pos = pos + 1 & this.mask]) {
				}
			}

			key[pos] = k;
		}
	}

	private void checkTable() {
	}

	private class SetIterator implements BooleanIterator {
		int pos = BooleanOpenHashSet.this.n;
		int last = -1;
		int c = BooleanOpenHashSet.this.size;
		boolean mustReturnNull = BooleanOpenHashSet.this.containsNull;
		BooleanArrayList wrapped;

		private SetIterator() {
		}

		public boolean hasNext() {
			return this.c != 0;
		}

		@Override
		public boolean nextBoolean() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.c--;
				if (this.mustReturnNull) {
					this.mustReturnNull = false;
					this.last = BooleanOpenHashSet.this.n;
					return BooleanOpenHashSet.this.key[BooleanOpenHashSet.this.n];
				} else {
					boolean[] key = BooleanOpenHashSet.this.key;

					while (--this.pos >= 0) {
						if (key[this.pos]) {
							return key[this.last = this.pos];
						}
					}

					this.last = Integer.MIN_VALUE;
					return this.wrapped.getBoolean(-this.pos - 1);
				}
			}
		}

		private final void shiftKeys(int pos) {
			boolean[] key = BooleanOpenHashSet.this.key;

			label43:
			while (true) {
				int last = pos;

				boolean curr;
				for (pos = pos + 1 & BooleanOpenHashSet.this.mask; curr = key[pos]; pos = pos + 1 & BooleanOpenHashSet.this.mask) {
					int slot = (curr ? 262886248 : -878682501) & BooleanOpenHashSet.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new BooleanArrayList(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						continue label43;
					}
				}

				key[last] = false;
				return;
			}
		}

		public void remove() {
			if (this.last == -1) {
				throw new IllegalStateException();
			} else {
				if (this.last == BooleanOpenHashSet.this.n) {
					BooleanOpenHashSet.this.containsNull = false;
					BooleanOpenHashSet.this.key[BooleanOpenHashSet.this.n] = false;
				} else {
					if (this.pos < 0) {
						BooleanOpenHashSet.this.remove(this.wrapped.getBoolean(-this.pos - 1));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				BooleanOpenHashSet.this.size--;
				this.last = -1;
			}
		}
	}
}
