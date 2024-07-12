package it.unimi.dsi.fastutil.doubles;

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

public class DoubleOpenCustomHashSet extends AbstractDoubleSet implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient double[] key;
	protected transient int mask;
	protected transient boolean containsNull;
	protected DoubleHash.Strategy strategy;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;

	public DoubleOpenCustomHashSet(int expected, float f, DoubleHash.Strategy strategy) {
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
			this.key = new double[this.n + 1];
		}
	}

	public DoubleOpenCustomHashSet(int expected, DoubleHash.Strategy strategy) {
		this(expected, 0.75F, strategy);
	}

	public DoubleOpenCustomHashSet(DoubleHash.Strategy strategy) {
		this(16, 0.75F, strategy);
	}

	public DoubleOpenCustomHashSet(Collection<? extends Double> c, float f, DoubleHash.Strategy strategy) {
		this(c.size(), f, strategy);
		this.addAll(c);
	}

	public DoubleOpenCustomHashSet(Collection<? extends Double> c, DoubleHash.Strategy strategy) {
		this(c, 0.75F, strategy);
	}

	public DoubleOpenCustomHashSet(DoubleCollection c, float f, DoubleHash.Strategy strategy) {
		this(c.size(), f, strategy);
		this.addAll(c);
	}

	public DoubleOpenCustomHashSet(DoubleCollection c, DoubleHash.Strategy strategy) {
		this(c, 0.75F, strategy);
	}

	public DoubleOpenCustomHashSet(DoubleIterator i, float f, DoubleHash.Strategy strategy) {
		this(16, f, strategy);

		while (i.hasNext()) {
			this.add(i.nextDouble());
		}
	}

	public DoubleOpenCustomHashSet(DoubleIterator i, DoubleHash.Strategy strategy) {
		this(i, 0.75F, strategy);
	}

	public DoubleOpenCustomHashSet(Iterator<?> i, float f, DoubleHash.Strategy strategy) {
		this(DoubleIterators.asDoubleIterator(i), f, strategy);
	}

	public DoubleOpenCustomHashSet(Iterator<?> i, DoubleHash.Strategy strategy) {
		this(DoubleIterators.asDoubleIterator(i), strategy);
	}

	public DoubleOpenCustomHashSet(double[] a, int offset, int length, float f, DoubleHash.Strategy strategy) {
		this(length < 0 ? 0 : length, f, strategy);
		DoubleArrays.ensureOffsetLength(a, offset, length);

		for (int i = 0; i < length; i++) {
			this.add(a[offset + i]);
		}
	}

	public DoubleOpenCustomHashSet(double[] a, int offset, int length, DoubleHash.Strategy strategy) {
		this(a, offset, length, 0.75F, strategy);
	}

	public DoubleOpenCustomHashSet(double[] a, float f, DoubleHash.Strategy strategy) {
		this(a, 0, a.length, f, strategy);
	}

	public DoubleOpenCustomHashSet(double[] a, DoubleHash.Strategy strategy) {
		this(a, 0.75F, strategy);
	}

	public DoubleHash.Strategy strategy() {
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
	public boolean addAll(DoubleCollection c) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(c.size());
		} else {
			this.tryCapacity((long)(this.size() + c.size()));
		}

		return super.addAll(c);
	}

	public boolean addAll(Collection<? extends Double> c) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(c.size());
		} else {
			this.tryCapacity((long)(this.size() + c.size()));
		}

		return super.addAll(c);
	}

	@Override
	public boolean add(double k) {
		if (this.strategy.equals(k, 0.0)) {
			if (this.containsNull) {
				return false;
			}

			this.containsNull = true;
			this.key[this.n] = k;
		} else {
			double[] key = this.key;
			int pos;
			double curr;
			if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0L) {
				if (this.strategy.equals(curr, k)) {
					return false;
				}

				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
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
		double[] key = this.key;

		label30:
		while (true) {
			int last = pos;

			double curr;
			for (pos = pos + 1 & this.mask; Double.doubleToLongBits(curr = key[pos]) != 0L; pos = pos + 1 & this.mask) {
				int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					key[last] = curr;
					continue label30;
				}
			}

			key[last] = 0.0;
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
		this.key[this.n] = 0.0;
		this.size--;
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return true;
	}

	@Override
	public boolean remove(double k) {
		if (this.strategy.equals(k, 0.0)) {
			return this.containsNull ? this.removeNullEntry() : false;
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) {
				return false;
			} else if (this.strategy.equals(k, curr)) {
				return this.removeEntry(pos);
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (this.strategy.equals(k, curr)) {
						return this.removeEntry(pos);
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean contains(double k) {
		if (this.strategy.equals(k, 0.0)) {
			return this.containsNull;
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) {
				return false;
			} else if (this.strategy.equals(k, curr)) {
				return true;
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
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
			Arrays.fill(this.key, 0.0);
		}
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	@Override
	public DoubleIterator iterator() {
		return new DoubleOpenCustomHashSet.SetIterator();
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
		double[] key = this.key;
		int mask = newN - 1;
		double[] newKey = new double[newN + 1];
		int i = this.n;
		int j = this.realSize();

		while (j-- != 0) {
			while (Double.doubleToLongBits(key[--i]) == 0L) {
			}

			int pos;
			if (Double.doubleToLongBits(newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0L) {
				while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L) {
				}
			}

			newKey[pos] = key[i];
		}

		this.n = newN;
		this.mask = mask;
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.key = newKey;
	}

	public DoubleOpenCustomHashSet clone() {
		DoubleOpenCustomHashSet c;
		try {
			c = (DoubleOpenCustomHashSet)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (double[])this.key.clone();
		c.containsNull = this.containsNull;
		c.strategy = this.strategy;
		return c;
	}

	@Override
	public int hashCode() {
		int h = 0;
		int j = this.realSize();

		for (int i = 0; j-- != 0; i++) {
			while (Double.doubleToLongBits(this.key[i]) == 0L) {
				i++;
			}

			h += this.strategy.hashCode(this.key[i]);
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		DoubleIterator i = this.iterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			s.writeDouble(i.nextDouble());
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		double[] key = this.key = new double[this.n + 1];
		int i = this.size;

		while (i-- != 0) {
			double k = s.readDouble();
			int pos;
			if (this.strategy.equals(k, 0.0)) {
				pos = this.n;
				this.containsNull = true;
			} else if (Double.doubleToLongBits(key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0L) {
				while (Double.doubleToLongBits(key[pos = pos + 1 & this.mask]) != 0L) {
				}
			}

			key[pos] = k;
		}
	}

	private void checkTable() {
	}

	private class SetIterator implements DoubleIterator {
		int pos = DoubleOpenCustomHashSet.this.n;
		int last = -1;
		int c = DoubleOpenCustomHashSet.this.size;
		boolean mustReturnNull = DoubleOpenCustomHashSet.this.containsNull;
		DoubleArrayList wrapped;

		private SetIterator() {
		}

		public boolean hasNext() {
			return this.c != 0;
		}

		@Override
		public double nextDouble() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.c--;
				if (this.mustReturnNull) {
					this.mustReturnNull = false;
					this.last = DoubleOpenCustomHashSet.this.n;
					return DoubleOpenCustomHashSet.this.key[DoubleOpenCustomHashSet.this.n];
				} else {
					double[] key = DoubleOpenCustomHashSet.this.key;

					while (--this.pos >= 0) {
						if (Double.doubleToLongBits(key[this.pos]) != 0L) {
							return key[this.last = this.pos];
						}
					}

					this.last = Integer.MIN_VALUE;
					return this.wrapped.getDouble(-this.pos - 1);
				}
			}
		}

		private final void shiftKeys(int pos) {
			double[] key = DoubleOpenCustomHashSet.this.key;

			label38:
			while (true) {
				int last = pos;

				double curr;
				for (pos = pos + 1 & DoubleOpenCustomHashSet.this.mask; Double.doubleToLongBits(curr = key[pos]) != 0L; pos = pos + 1 & DoubleOpenCustomHashSet.this.mask) {
					int slot = HashCommon.mix(DoubleOpenCustomHashSet.this.strategy.hashCode(curr)) & DoubleOpenCustomHashSet.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new DoubleArrayList(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						continue label38;
					}
				}

				key[last] = 0.0;
				return;
			}
		}

		public void remove() {
			if (this.last == -1) {
				throw new IllegalStateException();
			} else {
				if (this.last == DoubleOpenCustomHashSet.this.n) {
					DoubleOpenCustomHashSet.this.containsNull = false;
					DoubleOpenCustomHashSet.this.key[DoubleOpenCustomHashSet.this.n] = 0.0;
				} else {
					if (this.pos < 0) {
						DoubleOpenCustomHashSet.this.remove(this.wrapped.getDouble(-this.pos - 1));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				DoubleOpenCustomHashSet.this.size--;
				this.last = -1;
			}
		}
	}
}
