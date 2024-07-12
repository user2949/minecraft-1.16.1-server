package it.unimi.dsi.fastutil.ints;

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

public class IntOpenHashBigSet extends AbstractIntSet implements Serializable, Cloneable, Hash, Size64 {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient int[][] key;
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

	public IntOpenHashBigSet(long expected, float f) {
		if (f <= 0.0F || f > 1.0F) {
			throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
		} else if (this.n < 0L) {
			throw new IllegalArgumentException("The expected number of elements must be nonnegative");
		} else {
			this.f = f;
			this.minN = this.n = HashCommon.bigArraySize(expected, f);
			this.maxFill = HashCommon.maxFill(this.n, f);
			this.key = IntBigArrays.newBigArray(this.n);
			this.initMasks();
		}
	}

	public IntOpenHashBigSet(long expected) {
		this(expected, 0.75F);
	}

	public IntOpenHashBigSet() {
		this(16L, 0.75F);
	}

	public IntOpenHashBigSet(Collection<? extends Integer> c, float f) {
		this((long)c.size(), f);
		this.addAll(c);
	}

	public IntOpenHashBigSet(Collection<? extends Integer> c) {
		this(c, 0.75F);
	}

	public IntOpenHashBigSet(IntCollection c, float f) {
		this((long)c.size(), f);
		this.addAll(c);
	}

	public IntOpenHashBigSet(IntCollection c) {
		this(c, 0.75F);
	}

	public IntOpenHashBigSet(IntIterator i, float f) {
		this(16L, f);

		while (i.hasNext()) {
			this.add(i.nextInt());
		}
	}

	public IntOpenHashBigSet(IntIterator i) {
		this(i, 0.75F);
	}

	public IntOpenHashBigSet(Iterator<?> i, float f) {
		this(IntIterators.asIntIterator(i), f);
	}

	public IntOpenHashBigSet(Iterator<?> i) {
		this(IntIterators.asIntIterator(i));
	}

	public IntOpenHashBigSet(int[] a, int offset, int length, float f) {
		this(length < 0 ? 0L : (long)length, f);
		IntArrays.ensureOffsetLength(a, offset, length);

		for (int i = 0; i < length; i++) {
			this.add(a[offset + i]);
		}
	}

	public IntOpenHashBigSet(int[] a, int offset, int length) {
		this(a, offset, length, 0.75F);
	}

	public IntOpenHashBigSet(int[] a, float f) {
		this(a, 0, a.length, f);
	}

	public IntOpenHashBigSet(int[] a) {
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

	public boolean addAll(Collection<? extends Integer> c) {
		long size = c instanceof Size64 ? ((Size64)c).size64() : (long)c.size();
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(size);
		} else {
			this.ensureCapacity(this.size64() + size);
		}

		return super.addAll(c);
	}

	@Override
	public boolean addAll(IntCollection c) {
		long size = c instanceof Size64 ? ((Size64)c).size64() : (long)c.size();
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(size);
		} else {
			this.ensureCapacity(this.size64() + size);
		}

		return super.addAll(c);
	}

	@Override
	public boolean add(int k) {
		if (k == 0) {
			if (this.containsNull) {
				return false;
			}

			this.containsNull = true;
		} else {
			int[][] key = this.key;
			long h = HashCommon.mix((long)k);
			int displ;
			int base;
			int curr;
			if ((curr = key[base = (int)((h & this.mask) >>> 27)][displ = (int)(h & (long)this.segmentMask)]) != 0) {
				if (curr == k) {
					return false;
				}

				while ((curr = key[base = base + ((displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][displ]) != 0) {
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
		int[][] key = this.key;

		label30:
		while (true) {
			long last = pos;

			for (pos = pos + 1L & this.mask; IntBigArrays.get(key, pos) != 0; pos = pos + 1L & this.mask) {
				long slot = HashCommon.mix((long)IntBigArrays.get(key, pos)) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					IntBigArrays.set(key, last, IntBigArrays.get(key, pos));
					continue label30;
				}
			}

			IntBigArrays.set(key, last, 0);
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
	public boolean remove(int k) {
		if (k == 0) {
			return this.containsNull ? this.removeNullEntry() : false;
		} else {
			int[][] key = this.key;
			long h = HashCommon.mix((long)k);
			int curr;
			int displ;
			int base;
			if ((curr = key[base = (int)((h & this.mask) >>> 27)][displ = (int)(h & (long)this.segmentMask)]) == 0) {
				return false;
			} else if (curr == k) {
				return this.removeEntry(base, displ);
			} else {
				while ((curr = key[base = base + ((displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][displ]) != 0) {
					if (curr == k) {
						return this.removeEntry(base, displ);
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean contains(int k) {
		if (k == 0) {
			return this.containsNull;
		} else {
			int[][] key = this.key;
			long h = HashCommon.mix((long)k);
			int curr;
			int displ;
			int base;
			if ((curr = key[base = (int)((h & this.mask) >>> 27)][displ = (int)(h & (long)this.segmentMask)]) == 0) {
				return false;
			} else if (curr == k) {
				return true;
			} else {
				while ((curr = key[base = base + ((displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][displ]) != 0) {
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
			IntBigArrays.fill(this.key, 0);
		}
	}

	@Override
	public IntIterator iterator() {
		return new IntOpenHashBigSet.SetIterator();
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
		int[][] key = this.key;
		int[][] newKey = IntBigArrays.newBigArray(newN);
		long mask = newN - 1L;
		int newSegmentMask = newKey[0].length - 1;
		int newBaseMask = newKey.length - 1;
		int base = 0;
		int displ = 0;

		for (long i = this.realSize(); i-- != 0L; base += (displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0) {
			while (key[base][displ] == 0) {
				base += (displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0;
			}

			int k = key[base][displ];
			long h = HashCommon.mix((long)k);
			int b;
			int d;
			if (newKey[b = (int)((h & mask) >>> 27)][d = (int)(h & (long)newSegmentMask)] != 0) {
				while (newKey[b = b + ((d = d + 1 & newSegmentMask) == 0 ? 1 : 0) & newBaseMask][d] != 0) {
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

	public IntOpenHashBigSet clone() {
		IntOpenHashBigSet c;
		try {
			c = (IntOpenHashBigSet)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = IntBigArrays.copy(this.key);
		c.containsNull = this.containsNull;
		return c;
	}

	@Override
	public int hashCode() {
		int[][] key = this.key;
		int h = 0;
		int base = 0;
		int displ = 0;

		for (long j = this.realSize(); j-- != 0L; base += (displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0) {
			while (key[base][displ] == 0) {
				base += (displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0;
			}

			h += key[base][displ];
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		IntIterator i = this.iterator();
		s.defaultWriteObject();
		long j = this.size;

		while (j-- != 0L) {
			s.writeInt(i.nextInt());
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.bigArraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		int[][] key = this.key = IntBigArrays.newBigArray(this.n);
		this.initMasks();
		long i = this.size;

		while (i-- != 0L) {
			int k = s.readInt();
			if (k == 0) {
				this.containsNull = true;
			} else {
				long h = HashCommon.mix((long)k);
				int base;
				int displ;
				if (key[base = (int)((h & this.mask) >>> 27)][displ = (int)(h & (long)this.segmentMask)] != 0) {
					while (key[base = base + ((displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][displ] != 0) {
					}
				}

				key[base][displ] = k;
			}
		}
	}

	private void checkTable() {
	}

	private class SetIterator implements IntIterator {
		int base = IntOpenHashBigSet.this.key.length;
		int displ;
		long last = -1L;
		long c = IntOpenHashBigSet.this.size;
		boolean mustReturnNull = IntOpenHashBigSet.this.containsNull;
		IntArrayList wrapped;

		private SetIterator() {
		}

		public boolean hasNext() {
			return this.c != 0L;
		}

		@Override
		public int nextInt() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.c--;
				if (this.mustReturnNull) {
					this.mustReturnNull = false;
					this.last = IntOpenHashBigSet.this.n;
					return 0;
				} else {
					int[][] key = IntOpenHashBigSet.this.key;

					while (this.displ != 0 || this.base > 0) {
						if (this.displ-- == 0) {
							this.displ = key[--this.base].length - 1;
						}

						int k = key[this.base][this.displ];
						if (k != 0) {
							this.last = (long)this.base * 134217728L + (long)this.displ;
							return k;
						}
					}

					this.last = Long.MIN_VALUE;
					return this.wrapped.getInt(-(--this.base) - 1);
				}
			}
		}

		private final void shiftKeys(long pos) {
			int[][] key = IntOpenHashBigSet.this.key;

			label38:
			while (true) {
				long last = pos;

				int curr;
				for (pos = pos + 1L & IntOpenHashBigSet.this.mask; (curr = IntBigArrays.get(key, pos)) != 0; pos = pos + 1L & IntOpenHashBigSet.this.mask) {
					long slot = HashCommon.mix((long)curr) & IntOpenHashBigSet.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new IntArrayList();
							}

							this.wrapped.add(IntBigArrays.get(key, pos));
						}

						IntBigArrays.set(key, last, curr);
						continue label38;
					}
				}

				IntBigArrays.set(key, last, 0);
				return;
			}
		}

		public void remove() {
			if (this.last == -1L) {
				throw new IllegalStateException();
			} else {
				if (this.last == IntOpenHashBigSet.this.n) {
					IntOpenHashBigSet.this.containsNull = false;
				} else {
					if (this.base < 0) {
						IntOpenHashBigSet.this.remove(this.wrapped.getInt(-this.base - 1));
						this.last = -1L;
						return;
					}

					this.shiftKeys(this.last);
				}

				IntOpenHashBigSet.this.size--;
				this.last = -1L;
			}
		}
	}
}
