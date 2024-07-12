package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.Size64;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LongOpenHashBigSet extends AbstractLongSet implements Serializable, Cloneable, Hash, Size64 {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient long[][] key;
	protected transient long mask;
	protected transient int segmentMask;
	protected transient int baseMask;
	protected transient boolean containsNull;
	protected transient long n;
	protected transient long maxFill;
	protected final transient long minN;
	protected final float f;
	protected long size;

	private void initMasks() {
		this.mask = this.n - 1L;
		this.segmentMask = this.key[0].length - 1;
		this.baseMask = this.key.length - 1;
	}

	public LongOpenHashBigSet(long expected, float f) {
		if (f <= 0.0F || f > 1.0F) {
			throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
		} else if (this.n < 0L) {
			throw new IllegalArgumentException("The expected number of elements must be nonnegative");
		} else {
			this.f = f;
			this.minN = this.n = HashCommon.bigArraySize(expected, f);
			this.maxFill = HashCommon.maxFill(this.n, f);
			this.key = LongBigArrays.newBigArray(this.n);
			this.initMasks();
		}
	}

	public LongOpenHashBigSet(long expected) {
		this(expected, 0.75F);
	}

	public LongOpenHashBigSet() {
		this(16L, 0.75F);
	}

	public LongOpenHashBigSet(Collection<? extends Long> c, float f) {
		this((long)c.size(), f);
		this.addAll(c);
	}

	public LongOpenHashBigSet(Collection<? extends Long> c) {
		this(c, 0.75F);
	}

	public LongOpenHashBigSet(LongCollection c, float f) {
		this((long)c.size(), f);
		this.addAll(c);
	}

	public LongOpenHashBigSet(LongCollection c) {
		this(c, 0.75F);
	}

	public LongOpenHashBigSet(LongIterator i, float f) {
		this(16L, f);

		while (i.hasNext()) {
			this.add(i.nextLong());
		}
	}

	public LongOpenHashBigSet(LongIterator i) {
		this(i, 0.75F);
	}

	public LongOpenHashBigSet(Iterator<?> i, float f) {
		this(LongIterators.asLongIterator(i), f);
	}

	public LongOpenHashBigSet(Iterator<?> i) {
		this(LongIterators.asLongIterator(i));
	}

	public LongOpenHashBigSet(long[] a, int offset, int length, float f) {
		this(length < 0 ? 0L : (long)length, f);
		LongArrays.ensureOffsetLength(a, offset, length);

		for (int i = 0; i < length; i++) {
			this.add(a[offset + i]);
		}
	}

	public LongOpenHashBigSet(long[] a, int offset, int length) {
		this(a, offset, length, 0.75F);
	}

	public LongOpenHashBigSet(long[] a, float f) {
		this(a, 0, a.length, f);
	}

	public LongOpenHashBigSet(long[] a) {
		this(a, 0.75F);
	}

	private long realSize() {
		return this.containsNull ? this.size - 1L : this.size;
	}

	private void ensureCapacity(long capacity) {
		long needed = HashCommon.bigArraySize(capacity, this.f);
		if (needed > this.n) {
			this.rehash(needed);
		}
	}

	public boolean addAll(Collection<? extends Long> c) {
		long size = c instanceof Size64 ? ((Size64)c).size64() : (long)c.size();
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(size);
		} else {
			this.ensureCapacity(this.size64() + size);
		}

		return super.addAll(c);
	}

	@Override
	public boolean addAll(LongCollection c) {
		long size = c instanceof Size64 ? ((Size64)c).size64() : (long)c.size();
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(size);
		} else {
			this.ensureCapacity(this.size64() + size);
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
			long[][] key = this.key;
			long h = HashCommon.mix(k);
			int displ;
			int base;
			long curr;
			if ((curr = key[base = (int)((h & this.mask) >>> 27)][displ = (int)(h & (long)this.segmentMask)]) != 0L) {
				if (curr == k) {
					return false;
				}

				while ((curr = key[base = base + ((displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][displ]) != 0L) {
					if (curr == k) {
						return false;
					}
				}
			}

			key[base][displ] = k;
		}

		if (this.size++ >= this.maxFill) {
			this.rehash(2L * this.n);
		}

		return true;
	}

	protected final void shiftKeys(long pos) {
		long[][] key = this.key;

		label30:
		while (true) {
			long last = pos;

			for (pos = pos + 1L & this.mask; LongBigArrays.get(key, pos) != 0L; pos = pos + 1L & this.mask) {
				long slot = HashCommon.mix(LongBigArrays.get(key, pos)) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					LongBigArrays.set(key, last, LongBigArrays.get(key, pos));
					continue label30;
				}
			}

			LongBigArrays.set(key, last, 0L);
			return;
		}
	}

	private boolean removeEntry(int base, int displ) {
		this.size--;
		this.shiftKeys((long)base * 134217728L + (long)displ);
		if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L) {
			this.rehash(this.n / 2L);
		}

		return true;
	}

	private boolean removeNullEntry() {
		this.containsNull = false;
		this.size--;
		if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L) {
			this.rehash(this.n / 2L);
		}

		return true;
	}

	@Override
	public boolean remove(long k) {
		if (k == 0L) {
			return this.containsNull ? this.removeNullEntry() : false;
		} else {
			long[][] key = this.key;
			long h = HashCommon.mix(k);
			long curr;
			int displ;
			int base;
			if ((curr = key[base = (int)((h & this.mask) >>> 27)][displ = (int)(h & (long)this.segmentMask)]) == 0L) {
				return false;
			} else if (curr == k) {
				return this.removeEntry(base, displ);
			} else {
				while ((curr = key[base = base + ((displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][displ]) != 0L) {
					if (curr == k) {
						return this.removeEntry(base, displ);
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
			long[][] key = this.key;
			long h = HashCommon.mix(k);
			long curr;
			int displ;
			int base;
			if ((curr = key[base = (int)((h & this.mask) >>> 27)][displ = (int)(h & (long)this.segmentMask)]) == 0L) {
				return false;
			} else if (curr == k) {
				return true;
			} else {
				while ((curr = key[base = base + ((displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][displ]) != 0L) {
					if (curr == k) {
						return true;
					}
				}

				return false;
			}
		}
	}

	public void clear() {
		if (this.size != 0L) {
			this.size = 0L;
			this.containsNull = false;
			LongBigArrays.fill(this.key, 0L);
		}
	}

	@Override
	public LongIterator iterator() {
		return new LongOpenHashBigSet.SetIterator();
	}

	public boolean trim() {
		long l = HashCommon.bigArraySize(this.size, this.f);
		if (l < this.n && this.size <= HashCommon.maxFill(l, this.f)) {
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

	public boolean trim(long n) {
		long l = HashCommon.bigArraySize(n, this.f);
		if (this.n <= l) {
			return true;
		} else {
			try {
				this.rehash(l);
				return true;
			} catch (OutOfMemoryError var6) {
				return false;
			}
		}
	}

	protected void rehash(long newN) {
		long[][] key = this.key;
		long[][] newKey = LongBigArrays.newBigArray(newN);
		long mask = newN - 1L;
		int newSegmentMask = newKey[0].length - 1;
		int newBaseMask = newKey.length - 1;
		int base = 0;
		int displ = 0;

		for (long i = this.realSize(); i-- != 0L; base += (displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0) {
			while (key[base][displ] == 0L) {
				base += (displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0;
			}

			long k = key[base][displ];
			long h = HashCommon.mix(k);
			int b;
			int d;
			if (newKey[b = (int)((h & mask) >>> 27)][d = (int)(h & (long)newSegmentMask)] != 0L) {
				while (newKey[b = b + ((d = d + 1 & newSegmentMask) == 0 ? 1 : 0) & newBaseMask][d] != 0L) {
				}
			}

			newKey[b][d] = k;
		}

		this.n = newN;
		this.key = newKey;
		this.initMasks();
		this.maxFill = HashCommon.maxFill(this.n, this.f);
	}

	@Deprecated
	@Override
	public int size() {
		return (int)Math.min(2147483647L, this.size);
	}

	@Override
	public long size64() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0L;
	}

	public LongOpenHashBigSet clone() {
		LongOpenHashBigSet c;
		try {
			c = (LongOpenHashBigSet)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = LongBigArrays.copy(this.key);
		c.containsNull = this.containsNull;
		return c;
	}

	@Override
	public int hashCode() {
		long[][] key = this.key;
		int h = 0;
		int base = 0;
		int displ = 0;

		for (long j = this.realSize(); j-- != 0L; base += (displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0) {
			while (key[base][displ] == 0L) {
				base += (displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0;
			}

			h += HashCommon.long2int(key[base][displ]);
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		LongIterator i = this.iterator();
		s.defaultWriteObject();
		long j = this.size;

		while (j-- != 0L) {
			s.writeLong(i.nextLong());
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.bigArraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		long[][] key = this.key = LongBigArrays.newBigArray(this.n);
		this.initMasks();
		long i = this.size;

		while (i-- != 0L) {
			long k = s.readLong();
			if (k == 0L) {
				this.containsNull = true;
			} else {
				long h = HashCommon.mix(k);
				int base;
				int displ;
				if (key[base = (int)((h & this.mask) >>> 27)][displ = (int)(h & (long)this.segmentMask)] != 0L) {
					while (key[base = base + ((displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][displ] != 0L) {
					}
				}

				key[base][displ] = k;
			}
		}
	}

	private void checkTable() {
	}

	private class SetIterator implements LongIterator {
		int base = LongOpenHashBigSet.this.key.length;
		int displ;
		long last = -1L;
		long c = LongOpenHashBigSet.this.size;
		boolean mustReturnNull = LongOpenHashBigSet.this.containsNull;
		LongArrayList wrapped;

		private SetIterator() {
		}

		public boolean hasNext() {
			return this.c != 0L;
		}

		@Override
		public long nextLong() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.c--;
				if (this.mustReturnNull) {
					this.mustReturnNull = false;
					this.last = LongOpenHashBigSet.this.n;
					return 0L;
				} else {
					long[][] key = LongOpenHashBigSet.this.key;

					while (this.displ != 0 || this.base > 0) {
						if (this.displ-- == 0) {
							this.displ = key[--this.base].length - 1;
						}

						long k = key[this.base][this.displ];
						if (k != 0L) {
							this.last = (long)this.base * 134217728L + (long)this.displ;
							return k;
						}
					}

					this.last = Long.MIN_VALUE;
					return this.wrapped.getLong(-(--this.base) - 1);
				}
			}
		}

		private final void shiftKeys(long pos) {
			long[][] key = LongOpenHashBigSet.this.key;

			label38:
			while (true) {
				long last = pos;

				long curr;
				for (pos = pos + 1L & LongOpenHashBigSet.this.mask; (curr = LongBigArrays.get(key, pos)) != 0L; pos = pos + 1L & LongOpenHashBigSet.this.mask) {
					long slot = HashCommon.mix(curr) & LongOpenHashBigSet.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new LongArrayList();
							}

							this.wrapped.add(LongBigArrays.get(key, pos));
						}

						LongBigArrays.set(key, last, curr);
						continue label38;
					}
				}

				LongBigArrays.set(key, last, 0L);
				return;
			}
		}

		public void remove() {
			if (this.last == -1L) {
				throw new IllegalStateException();
			} else {
				if (this.last == LongOpenHashBigSet.this.n) {
					LongOpenHashBigSet.this.containsNull = false;
				} else {
					if (this.base < 0) {
						LongOpenHashBigSet.this.remove(this.wrapped.getLong(-this.base - 1));
						this.last = -1L;
						return;
					}

					this.shiftKeys(this.last);
				}

				LongOpenHashBigSet.this.size--;
				this.last = -1L;
			}
		}
	}
}
