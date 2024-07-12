package it.unimi.dsi.fastutil.floats;

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

public class FloatOpenHashBigSet extends AbstractFloatSet implements Serializable, Cloneable, Hash, Size64 {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient float[][] key;
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

	public FloatOpenHashBigSet(long expected, float f) {
		if (f <= 0.0F || f > 1.0F) {
			throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
		} else if (this.n < 0L) {
			throw new IllegalArgumentException("The expected number of elements must be nonnegative");
		} else {
			this.f = f;
			this.minN = this.n = HashCommon.bigArraySize(expected, f);
			this.maxFill = HashCommon.maxFill(this.n, f);
			this.key = FloatBigArrays.newBigArray(this.n);
			this.initMasks();
		}
	}

	public FloatOpenHashBigSet(long expected) {
		this(expected, 0.75F);
	}

	public FloatOpenHashBigSet() {
		this(16L, 0.75F);
	}

	public FloatOpenHashBigSet(Collection<? extends Float> c, float f) {
		this((long)c.size(), f);
		this.addAll(c);
	}

	public FloatOpenHashBigSet(Collection<? extends Float> c) {
		this(c, 0.75F);
	}

	public FloatOpenHashBigSet(FloatCollection c, float f) {
		this((long)c.size(), f);
		this.addAll(c);
	}

	public FloatOpenHashBigSet(FloatCollection c) {
		this(c, 0.75F);
	}

	public FloatOpenHashBigSet(FloatIterator i, float f) {
		this(16L, f);

		while (i.hasNext()) {
			this.add(i.nextFloat());
		}
	}

	public FloatOpenHashBigSet(FloatIterator i) {
		this(i, 0.75F);
	}

	public FloatOpenHashBigSet(Iterator<?> i, float f) {
		this(FloatIterators.asFloatIterator(i), f);
	}

	public FloatOpenHashBigSet(Iterator<?> i) {
		this(FloatIterators.asFloatIterator(i));
	}

	public FloatOpenHashBigSet(float[] a, int offset, int length, float f) {
		this(length < 0 ? 0L : (long)length, f);
		FloatArrays.ensureOffsetLength(a, offset, length);

		for (int i = 0; i < length; i++) {
			this.add(a[offset + i]);
		}
	}

	public FloatOpenHashBigSet(float[] a, int offset, int length) {
		this(a, offset, length, 0.75F);
	}

	public FloatOpenHashBigSet(float[] a, float f) {
		this(a, 0, a.length, f);
	}

	public FloatOpenHashBigSet(float[] a) {
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

	public boolean addAll(Collection<? extends Float> c) {
		long size = c instanceof Size64 ? ((Size64)c).size64() : (long)c.size();
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(size);
		} else {
			this.ensureCapacity(this.size64() + size);
		}

		return super.addAll(c);
	}

	@Override
	public boolean addAll(FloatCollection c) {
		long size = c instanceof Size64 ? ((Size64)c).size64() : (long)c.size();
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(size);
		} else {
			this.ensureCapacity(this.size64() + size);
		}

		return super.addAll(c);
	}

	@Override
	public boolean add(float k) {
		if (Float.floatToIntBits(k) == 0) {
			if (this.containsNull) {
				return false;
			}

			this.containsNull = true;
		} else {
			float[][] key = this.key;
			long h = HashCommon.mix((long)HashCommon.float2int(k));
			int displ;
			int base;
			float curr;
			if (Float.floatToIntBits(curr = key[base = (int)((h & this.mask) >>> 27)][displ = (int)(h & (long)this.segmentMask)]) != 0) {
				if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
					return false;
				}

				while (Float.floatToIntBits(curr = key[base = base + ((displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][displ]) != 0) {
					if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
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
		float[][] key = this.key;

		label30:
		while (true) {
			long last = pos;

			for (pos = pos + 1L & this.mask; Float.floatToIntBits(FloatBigArrays.get(key, pos)) != 0; pos = pos + 1L & this.mask) {
				long slot = HashCommon.mix((long)HashCommon.float2int(FloatBigArrays.get(key, pos))) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					FloatBigArrays.set(key, last, FloatBigArrays.get(key, pos));
					continue label30;
				}
			}

			FloatBigArrays.set(key, last, 0.0F);
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
	public boolean remove(float k) {
		if (Float.floatToIntBits(k) == 0) {
			return this.containsNull ? this.removeNullEntry() : false;
		} else {
			float[][] key = this.key;
			long h = HashCommon.mix((long)HashCommon.float2int(k));
			float curr;
			int displ;
			int base;
			if (Float.floatToIntBits(curr = key[base = (int)((h & this.mask) >>> 27)][displ = (int)(h & (long)this.segmentMask)]) == 0) {
				return false;
			} else if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
				return this.removeEntry(base, displ);
			} else {
				while (Float.floatToIntBits(curr = key[base = base + ((displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][displ]) != 0) {
					if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
						return this.removeEntry(base, displ);
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean contains(float k) {
		if (Float.floatToIntBits(k) == 0) {
			return this.containsNull;
		} else {
			float[][] key = this.key;
			long h = HashCommon.mix((long)HashCommon.float2int(k));
			float curr;
			int displ;
			int base;
			if (Float.floatToIntBits(curr = key[base = (int)((h & this.mask) >>> 27)][displ = (int)(h & (long)this.segmentMask)]) == 0) {
				return false;
			} else if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
				return true;
			} else {
				while (Float.floatToIntBits(curr = key[base = base + ((displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][displ]) != 0) {
					if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
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
			FloatBigArrays.fill(this.key, 0.0F);
		}
	}

	@Override
	public FloatIterator iterator() {
		return new FloatOpenHashBigSet.SetIterator();
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
		float[][] key = this.key;
		float[][] newKey = FloatBigArrays.newBigArray(newN);
		long mask = newN - 1L;
		int newSegmentMask = newKey[0].length - 1;
		int newBaseMask = newKey.length - 1;
		int base = 0;
		int displ = 0;

		for (long i = this.realSize(); i-- != 0L; base += (displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0) {
			while (Float.floatToIntBits(key[base][displ]) == 0) {
				base += (displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0;
			}

			float k = key[base][displ];
			long h = HashCommon.mix((long)HashCommon.float2int(k));
			int b;
			int d;
			if (Float.floatToIntBits(newKey[b = (int)((h & mask) >>> 27)][d = (int)(h & (long)newSegmentMask)]) != 0) {
				while (Float.floatToIntBits(newKey[b = b + ((d = d + 1 & newSegmentMask) == 0 ? 1 : 0) & newBaseMask][d]) != 0) {
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

	public FloatOpenHashBigSet clone() {
		FloatOpenHashBigSet c;
		try {
			c = (FloatOpenHashBigSet)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = FloatBigArrays.copy(this.key);
		c.containsNull = this.containsNull;
		return c;
	}

	@Override
	public int hashCode() {
		float[][] key = this.key;
		int h = 0;
		int base = 0;
		int displ = 0;

		for (long j = this.realSize(); j-- != 0L; base += (displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0) {
			while (Float.floatToIntBits(key[base][displ]) == 0) {
				base += (displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0;
			}

			h += HashCommon.float2int(key[base][displ]);
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		FloatIterator i = this.iterator();
		s.defaultWriteObject();
		long j = this.size;

		while (j-- != 0L) {
			s.writeFloat(i.nextFloat());
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.bigArraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		float[][] key = this.key = FloatBigArrays.newBigArray(this.n);
		this.initMasks();
		long i = this.size;

		while (i-- != 0L) {
			float k = s.readFloat();
			if (Float.floatToIntBits(k) == 0) {
				this.containsNull = true;
			} else {
				long h = HashCommon.mix((long)HashCommon.float2int(k));
				int base;
				int displ;
				if (Float.floatToIntBits(key[base = (int)((h & this.mask) >>> 27)][displ = (int)(h & (long)this.segmentMask)]) != 0) {
					while (Float.floatToIntBits(key[base = base + ((displ = displ + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][displ]) != 0) {
					}
				}

				key[base][displ] = k;
			}
		}
	}

	private void checkTable() {
	}

	private class SetIterator implements FloatIterator {
		int base = FloatOpenHashBigSet.this.key.length;
		int displ;
		long last = -1L;
		long c = FloatOpenHashBigSet.this.size;
		boolean mustReturnNull = FloatOpenHashBigSet.this.containsNull;
		FloatArrayList wrapped;

		private SetIterator() {
		}

		public boolean hasNext() {
			return this.c != 0L;
		}

		@Override
		public float nextFloat() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.c--;
				if (this.mustReturnNull) {
					this.mustReturnNull = false;
					this.last = FloatOpenHashBigSet.this.n;
					return 0.0F;
				} else {
					float[][] key = FloatOpenHashBigSet.this.key;

					while (this.displ != 0 || this.base > 0) {
						if (this.displ-- == 0) {
							this.displ = key[--this.base].length - 1;
						}

						float k = key[this.base][this.displ];
						if (Float.floatToIntBits(k) != 0) {
							this.last = (long)this.base * 134217728L + (long)this.displ;
							return k;
						}
					}

					this.last = Long.MIN_VALUE;
					return this.wrapped.getFloat(-(--this.base) - 1);
				}
			}
		}

		private final void shiftKeys(long pos) {
			float[][] key = FloatOpenHashBigSet.this.key;

			label38:
			while (true) {
				long last = pos;

				float curr;
				for (pos = pos + 1L & FloatOpenHashBigSet.this.mask;
					Float.floatToIntBits(curr = FloatBigArrays.get(key, pos)) != 0;
					pos = pos + 1L & FloatOpenHashBigSet.this.mask
				) {
					long slot = HashCommon.mix((long)HashCommon.float2int(curr)) & FloatOpenHashBigSet.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new FloatArrayList();
							}

							this.wrapped.add(FloatBigArrays.get(key, pos));
						}

						FloatBigArrays.set(key, last, curr);
						continue label38;
					}
				}

				FloatBigArrays.set(key, last, 0.0F);
				return;
			}
		}

		public void remove() {
			if (this.last == -1L) {
				throw new IllegalStateException();
			} else {
				if (this.last == FloatOpenHashBigSet.this.n) {
					FloatOpenHashBigSet.this.containsNull = false;
				} else {
					if (this.base < 0) {
						FloatOpenHashBigSet.this.remove(this.wrapped.getFloat(-this.base - 1));
						this.last = -1L;
						return;
					}

					this.shiftKeys(this.last);
				}

				FloatOpenHashBigSet.this.size--;
				this.last = -1L;
			}
		}
	}
}
