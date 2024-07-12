package it.unimi.dsi.fastutil.bytes;

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

public class ByteOpenCustomHashSet extends AbstractByteSet implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient byte[] key;
	protected transient int mask;
	protected transient boolean containsNull;
	protected ByteHash.Strategy strategy;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;

	public ByteOpenCustomHashSet(int expected, float f, ByteHash.Strategy strategy) {
		this.strategy = strategy;
		if (f <= 0.0F || f > 1.0F) {
			throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
		} else if (expected < 0) {
			throw new IllegalArgumentException("The expected number of elements must be nonnegative");
		} else {
			this.f = f;
			this.minN = this.n = HashCommon.arraySize(expected, f);
			this.mask = this.n - 1;
			this.maxFill = HashCommon.maxFill(this.n, f);
			this.key = new byte[this.n + 1];
		}
	}

	public ByteOpenCustomHashSet(int expected, ByteHash.Strategy strategy) {
		this(expected, 0.75F, strategy);
	}

	public ByteOpenCustomHashSet(ByteHash.Strategy strategy) {
		this(16, 0.75F, strategy);
	}

	public ByteOpenCustomHashSet(Collection<? extends Byte> c, float f, ByteHash.Strategy strategy) {
		this(c.size(), f, strategy);
		this.addAll(c);
	}

	public ByteOpenCustomHashSet(Collection<? extends Byte> c, ByteHash.Strategy strategy) {
		this(c, 0.75F, strategy);
	}

	public ByteOpenCustomHashSet(ByteCollection c, float f, ByteHash.Strategy strategy) {
		this(c.size(), f, strategy);
		this.addAll(c);
	}

	public ByteOpenCustomHashSet(ByteCollection c, ByteHash.Strategy strategy) {
		this(c, 0.75F, strategy);
	}

	public ByteOpenCustomHashSet(ByteIterator i, float f, ByteHash.Strategy strategy) {
		this(16, f, strategy);

		while (i.hasNext()) {
			this.add(i.nextByte());
		}
	}

	public ByteOpenCustomHashSet(ByteIterator i, ByteHash.Strategy strategy) {
		this(i, 0.75F, strategy);
	}

	public ByteOpenCustomHashSet(Iterator<?> i, float f, ByteHash.Strategy strategy) {
		this(ByteIterators.asByteIterator(i), f, strategy);
	}

	public ByteOpenCustomHashSet(Iterator<?> i, ByteHash.Strategy strategy) {
		this(ByteIterators.asByteIterator(i), strategy);
	}

	public ByteOpenCustomHashSet(byte[] a, int offset, int length, float f, ByteHash.Strategy strategy) {
		this(length < 0 ? 0 : length, f, strategy);
		ByteArrays.ensureOffsetLength(a, offset, length);

		for (int i = 0; i < length; i++) {
			this.add(a[offset + i]);
		}
	}

	public ByteOpenCustomHashSet(byte[] a, int offset, int length, ByteHash.Strategy strategy) {
		this(a, offset, length, 0.75F, strategy);
	}

	public ByteOpenCustomHashSet(byte[] a, float f, ByteHash.Strategy strategy) {
		this(a, 0, a.length, f, strategy);
	}

	public ByteOpenCustomHashSet(byte[] a, ByteHash.Strategy strategy) {
		this(a, 0.75F, strategy);
	}

	public ByteHash.Strategy strategy() {
		return this.strategy;
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
	public boolean addAll(ByteCollection c) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(c.size());
		} else {
			this.tryCapacity((long)(this.size() + c.size()));
		}

		return super.addAll(c);
	}

	public boolean addAll(Collection<? extends Byte> c) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(c.size());
		} else {
			this.tryCapacity((long)(this.size() + c.size()));
		}

		return super.addAll(c);
	}

	@Override
	public boolean add(byte k) {
		if (this.strategy.equals(k, (byte)0)) {
			if (this.containsNull) {
				return false;
			}

			this.containsNull = true;
			this.key[this.n] = k;
		} else {
			byte[] key = this.key;
			int pos;
			byte curr;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
				if (this.strategy.equals(curr, k)) {
					return false;
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(curr, k)) {
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
		byte[] key = this.key;

		label30:
		while (true) {
			int last = pos;

			byte curr;
			for (pos = pos + 1 & this.mask; (curr = key[pos]) != 0; pos = pos + 1 & this.mask) {
				int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					key[last] = curr;
					continue label30;
				}
			}

			key[last] = 0;
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
		this.key[this.n] = 0;
		this.size--;
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return true;
	}

	@Override
	public boolean remove(byte k) {
		if (this.strategy.equals(k, (byte)0)) {
			return this.containsNull ? this.removeNullEntry() : false;
		} else {
			byte[] key = this.key;
			byte curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return false;
			} else if (this.strategy.equals(k, curr)) {
				return this.removeEntry(pos);
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr)) {
						return this.removeEntry(pos);
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean contains(byte k) {
		if (this.strategy.equals(k, (byte)0)) {
			return this.containsNull;
		} else {
			byte[] key = this.key;
			byte curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return false;
			} else if (this.strategy.equals(k, curr)) {
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr)) {
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
			Arrays.fill(this.key, (byte)0);
		}
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	@Override
	public ByteIterator iterator() {
		return new ByteOpenCustomHashSet.SetIterator();
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
		byte[] key = this.key;
		int mask = newN - 1;
		byte[] newKey = new byte[newN + 1];
		int i = this.n;
		int j = this.realSize();

		while (j-- != 0) {
			while (key[--i] == 0) {
			}

			int pos;
			if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0) {
				while (newKey[pos = pos + 1 & mask] != 0) {
				}
			}

			newKey[pos] = key[i];
		}

		this.n = newN;
		this.mask = mask;
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.key = newKey;
	}

	public ByteOpenCustomHashSet clone() {
		ByteOpenCustomHashSet c;
		try {
			c = (ByteOpenCustomHashSet)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (byte[])this.key.clone();
		c.containsNull = this.containsNull;
		c.strategy = this.strategy;
		return c;
	}

	@Override
	public int hashCode() {
		int h = 0;
		int j = this.realSize();

		for (int i = 0; j-- != 0; i++) {
			while (this.key[i] == 0) {
				i++;
			}

			h += this.strategy.hashCode(this.key[i]);
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		ByteIterator i = this.iterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			s.writeByte(i.nextByte());
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		byte[] key = this.key = new byte[this.n + 1];
		int i = this.size;

		while (i-- != 0) {
			byte k = s.readByte();
			int pos;
			if (this.strategy.equals(k, (byte)0)) {
				pos = this.n;
				this.containsNull = true;
			} else if (key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask] != 0) {
				while (key[pos = pos + 1 & this.mask] != 0) {
				}
			}

			key[pos] = k;
		}
	}

	private void checkTable() {
	}

	private class SetIterator implements ByteIterator {
		int pos = ByteOpenCustomHashSet.this.n;
		int last = -1;
		int c = ByteOpenCustomHashSet.this.size;
		boolean mustReturnNull = ByteOpenCustomHashSet.this.containsNull;
		ByteArrayList wrapped;

		private SetIterator() {
		}

		public boolean hasNext() {
			return this.c != 0;
		}

		@Override
		public byte nextByte() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.c--;
				if (this.mustReturnNull) {
					this.mustReturnNull = false;
					this.last = ByteOpenCustomHashSet.this.n;
					return ByteOpenCustomHashSet.this.key[ByteOpenCustomHashSet.this.n];
				} else {
					byte[] key = ByteOpenCustomHashSet.this.key;

					while (--this.pos >= 0) {
						if (key[this.pos] != 0) {
							return key[this.last = this.pos];
						}
					}

					this.last = Integer.MIN_VALUE;
					return this.wrapped.getByte(-this.pos - 1);
				}
			}
		}

		private final void shiftKeys(int pos) {
			byte[] key = ByteOpenCustomHashSet.this.key;

			label38:
			while (true) {
				int last = pos;

				byte curr;
				for (pos = pos + 1 & ByteOpenCustomHashSet.this.mask; (curr = key[pos]) != 0; pos = pos + 1 & ByteOpenCustomHashSet.this.mask) {
					int slot = HashCommon.mix(ByteOpenCustomHashSet.this.strategy.hashCode(curr)) & ByteOpenCustomHashSet.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new ByteArrayList(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						continue label38;
					}
				}

				key[last] = 0;
				return;
			}
		}

		public void remove() {
			if (this.last == -1) {
				throw new IllegalStateException();
			} else {
				if (this.last == ByteOpenCustomHashSet.this.n) {
					ByteOpenCustomHashSet.this.containsNull = false;
					ByteOpenCustomHashSet.this.key[ByteOpenCustomHashSet.this.n] = 0;
				} else {
					if (this.pos < 0) {
						ByteOpenCustomHashSet.this.remove(this.wrapped.getByte(-this.pos - 1));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				ByteOpenCustomHashSet.this.size--;
				this.last = -1;
			}
		}
	}
}
