package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2ShortMap.BasicEntry;
import it.unimi.dsi.fastutil.doubles.Double2ShortMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleToIntFunction;
import java.util.function.IntConsumer;

public class Double2ShortOpenHashMap extends AbstractDouble2ShortMap implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient double[] key;
	protected transient short[] value;
	protected transient int mask;
	protected transient boolean containsNullKey;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;
	protected transient FastEntrySet entries;
	protected transient DoubleSet keys;
	protected transient ShortCollection values;

	public Double2ShortOpenHashMap(int expected, float f) {
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
			this.value = new short[this.n + 1];
		}
	}

	public Double2ShortOpenHashMap(int expected) {
		this(expected, 0.75F);
	}

	public Double2ShortOpenHashMap() {
		this(16, 0.75F);
	}

	public Double2ShortOpenHashMap(Map<? extends Double, ? extends Short> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Double2ShortOpenHashMap(Map<? extends Double, ? extends Short> m) {
		this(m, 0.75F);
	}

	public Double2ShortOpenHashMap(Double2ShortMap m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Double2ShortOpenHashMap(Double2ShortMap m) {
		this(m, 0.75F);
	}

	public Double2ShortOpenHashMap(double[] k, short[] v, float f) {
		this(k.length, f);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Double2ShortOpenHashMap(double[] k, short[] v) {
		this(k, v, 0.75F);
	}

	private int realSize() {
		return this.containsNullKey ? this.size - 1 : this.size;
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

	private short removeEntry(int pos) {
		short oldValue = this.value[pos];
		this.size--;
		this.shiftKeys(pos);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	private short removeNullEntry() {
		this.containsNullKey = false;
		short oldValue = this.value[this.n];
		this.size--;
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	@Override
	public void putAll(Map<? extends Double, ? extends Short> m) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(m.size());
		} else {
			this.tryCapacity((long)(this.size() + m.size()));
		}

		super.putAll(m);
	}

	private int find(double k) {
		if (Double.doubleToLongBits(k) == 0L) {
			return this.containsNullKey ? this.n : -(this.n + 1);
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) {
				return -(pos + 1);
			} else if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
				return pos;
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
						return pos;
					}
				}

				return -(pos + 1);
			}
		}
	}

	private void insert(int pos, double k, short v) {
		if (pos == this.n) {
			this.containsNullKey = true;
		}

		this.key[pos] = k;
		this.value[pos] = v;
		if (this.size++ >= this.maxFill) {
			this.rehash(HashCommon.arraySize(this.size + 1, this.f));
		}
	}

	@Override
	public short put(double k, short v) {
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		} else {
			short oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	private short addToValue(int pos, short incr) {
		short oldValue = this.value[pos];
		this.value[pos] = (short)(oldValue + incr);
		return oldValue;
	}

	public short addTo(double k, short incr) {
		int pos;
		if (Double.doubleToLongBits(k) == 0L) {
			if (this.containsNullKey) {
				return this.addToValue(this.n, incr);
			}

			pos = this.n;
			this.containsNullKey = true;
		} else {
			double[] key = this.key;
			double curr;
			if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
				if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
					return this.addToValue(pos, incr);
				}

				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
						return this.addToValue(pos, incr);
					}
				}
			}
		}

		this.key[pos] = k;
		this.value[pos] = (short)(this.defRetValue + incr);
		if (this.size++ >= this.maxFill) {
			this.rehash(HashCommon.arraySize(this.size + 1, this.f));
		}

		return this.defRetValue;
	}

	protected final void shiftKeys(int pos) {
		double[] key = this.key;

		label30:
		while (true) {
			int last = pos;

			double curr;
			for (pos = pos + 1 & this.mask; Double.doubleToLongBits(curr = key[pos]) != 0L; pos = pos + 1 & this.mask) {
				int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					key[last] = curr;
					this.value[last] = this.value[pos];
					continue label30;
				}
			}

			key[last] = 0.0;
			return;
		}
	}

	@Override
	public short remove(double k) {
		if (Double.doubleToLongBits(k) == 0L) {
			return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) {
				return this.defRetValue;
			} else if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
				return this.removeEntry(pos);
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
						return this.removeEntry(pos);
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public short get(double k) {
		if (Double.doubleToLongBits(k) == 0L) {
			return this.containsNullKey ? this.value[this.n] : this.defRetValue;
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) {
				return this.defRetValue;
			} else if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
				return this.value[pos];
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public boolean containsKey(double k) {
		if (Double.doubleToLongBits(k) == 0L) {
			return this.containsNullKey;
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) {
				return false;
			} else if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
				return true;
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean containsValue(short v) {
		short[] value = this.value;
		double[] key = this.key;
		if (this.containsNullKey && value[this.n] == v) {
			return true;
		} else {
			int i = this.n;

			while (i-- != 0) {
				if (Double.doubleToLongBits(key[i]) != 0L && value[i] == v) {
					return true;
				}
			}

			return false;
		}
	}

	@Override
	public short getOrDefault(double k, short defaultValue) {
		if (Double.doubleToLongBits(k) == 0L) {
			return this.containsNullKey ? this.value[this.n] : defaultValue;
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) {
				return defaultValue;
			} else if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
				return this.value[pos];
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
						return this.value[pos];
					}
				}

				return defaultValue;
			}
		}
	}

	@Override
	public short putIfAbsent(double k, short v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(double k, short v) {
		if (Double.doubleToLongBits(k) == 0L) {
			if (this.containsNullKey && v == this.value[this.n]) {
				this.removeNullEntry();
				return true;
			} else {
				return false;
			}
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) {
				return false;
			} else if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
				this.removeEntry(pos);
				return true;
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
						this.removeEntry(pos);
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean replace(double k, short oldValue, short v) {
		int pos = this.find(k);
		if (pos >= 0 && oldValue == this.value[pos]) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public short replace(double k, short v) {
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			short oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	@Override
	public short computeIfAbsent(double k, DoubleToIntFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(k));
			this.insert(-pos - 1, k, newValue);
			return newValue;
		}
	}

	@Override
	public short computeIfAbsentNullable(double k, DoubleFunction<? extends Short> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			Short newValue = (Short)mappingFunction.apply(k);
			if (newValue == null) {
				return this.defRetValue;
			} else {
				short v = newValue;
				this.insert(-pos - 1, k, v);
				return v;
			}
		}
	}

	@Override
	public short computeIfPresent(double k, BiFunction<? super Double, ? super Short, ? extends Short> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			Short newValue = (Short)remappingFunction.apply(k, this.value[pos]);
			if (newValue == null) {
				if (Double.doubleToLongBits(k) == 0L) {
					this.removeNullEntry();
				} else {
					this.removeEntry(pos);
				}

				return this.defRetValue;
			} else {
				return this.value[pos] = newValue;
			}
		}
	}

	@Override
	public short compute(double k, BiFunction<? super Double, ? super Short, ? extends Short> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		Short newValue = (Short)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
		if (newValue == null) {
			if (pos >= 0) {
				if (Double.doubleToLongBits(k) == 0L) {
					this.removeNullEntry();
				} else {
					this.removeEntry(pos);
				}
			}

			return this.defRetValue;
		} else {
			short newVal = newValue;
			if (pos < 0) {
				this.insert(-pos - 1, k, newVal);
				return newVal;
			} else {
				return this.value[pos] = newVal;
			}
		}
	}

	@Override
	public short merge(double k, short v, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return v;
		} else {
			Short newValue = (Short)remappingFunction.apply(this.value[pos], v);
			if (newValue == null) {
				if (Double.doubleToLongBits(k) == 0L) {
					this.removeNullEntry();
				} else {
					this.removeEntry(pos);
				}

				return this.defRetValue;
			} else {
				return this.value[pos] = newValue;
			}
		}
	}

	@Override
	public void clear() {
		if (this.size != 0) {
			this.size = 0;
			this.containsNullKey = false;
			Arrays.fill(this.key, 0.0);
		}
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	public FastEntrySet double2ShortEntrySet() {
		if (this.entries == null) {
			this.entries = new Double2ShortOpenHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public DoubleSet keySet() {
		if (this.keys == null) {
			this.keys = new Double2ShortOpenHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public ShortCollection values() {
		if (this.values == null) {
			this.values = new AbstractShortCollection() {
				@Override
				public ShortIterator iterator() {
					return Double2ShortOpenHashMap.this.new ValueIterator();
				}

				public int size() {
					return Double2ShortOpenHashMap.this.size;
				}

				@Override
				public boolean contains(short v) {
					return Double2ShortOpenHashMap.this.containsValue(v);
				}

				public void clear() {
					Double2ShortOpenHashMap.this.clear();
				}

				@Override
				public void forEach(IntConsumer consumer) {
					if (Double2ShortOpenHashMap.this.containsNullKey) {
						consumer.accept(Double2ShortOpenHashMap.this.value[Double2ShortOpenHashMap.this.n]);
					}

					int pos = Double2ShortOpenHashMap.this.n;

					while (pos-- != 0) {
						if (Double.doubleToLongBits(Double2ShortOpenHashMap.this.key[pos]) != 0L) {
							consumer.accept(Double2ShortOpenHashMap.this.value[pos]);
						}
					}
				}
			};
		}

		return this.values;
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
		short[] value = this.value;
		int mask = newN - 1;
		double[] newKey = new double[newN + 1];
		short[] newValue = new short[newN + 1];
		int i = this.n;
		int j = this.realSize();

		while (j-- != 0) {
			while (Double.doubleToLongBits(key[--i]) == 0L) {
			}

			int pos;
			if (Double.doubleToLongBits(newKey[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask]) != 0L) {
				while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L) {
				}
			}

			newKey[pos] = key[i];
			newValue[pos] = value[i];
		}

		newValue[newN] = value[this.n];
		this.n = newN;
		this.mask = mask;
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.key = newKey;
		this.value = newValue;
	}

	public Double2ShortOpenHashMap clone() {
		Double2ShortOpenHashMap c;
		try {
			c = (Double2ShortOpenHashMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (double[])this.key.clone();
		c.value = (short[])this.value.clone();
		return c;
	}

	@Override
	public int hashCode() {
		int h = 0;
		int j = this.realSize();
		int i = 0;

		for (int t = 0; j-- != 0; i++) {
			while (Double.doubleToLongBits(this.key[i]) == 0L) {
				i++;
			}

			t = HashCommon.double2int(this.key[i]);
			t ^= this.value[i];
			h += t;
		}

		if (this.containsNullKey) {
			h += this.value[this.n];
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		double[] key = this.key;
		short[] value = this.value;
		Double2ShortOpenHashMap.MapIterator i = new Double2ShortOpenHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeDouble(key[e]);
			s.writeShort(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		double[] key = this.key = new double[this.n + 1];
		short[] value = this.value = new short[this.n + 1];
		int i = this.size;

		while (i-- != 0) {
			double k = s.readDouble();
			short v = s.readShort();
			int pos;
			if (Double.doubleToLongBits(k) == 0L) {
				pos = this.n;
				this.containsNullKey = true;
			} else {
				pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;

				while (Double.doubleToLongBits(key[pos]) != 0L) {
					pos = pos + 1 & this.mask;
				}
			}

			key[pos] = k;
			value[pos] = v;
		}
	}

	private void checkTable() {
	}

	private class EntryIterator extends Double2ShortOpenHashMap.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2ShortMap.Entry> {
		private Double2ShortOpenHashMap.MapEntry entry;

		private EntryIterator() {
		}

		public Double2ShortOpenHashMap.MapEntry next() {
			return this.entry = Double2ShortOpenHashMap.this.new MapEntry(this.nextEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator extends Double2ShortOpenHashMap.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2ShortMap.Entry> {
		private final Double2ShortOpenHashMap.MapEntry entry = Double2ShortOpenHashMap.this.new MapEntry();

		private FastEntryIterator() {
		}

		public Double2ShortOpenHashMap.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Double2ShortOpenHashMap.MapIterator implements DoubleIterator {
		public KeyIterator() {
		}

		@Override
		public double nextDouble() {
			return Double2ShortOpenHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractDoubleSet {
		private KeySet() {
		}

		@Override
		public DoubleIterator iterator() {
			return Double2ShortOpenHashMap.this.new KeyIterator();
		}

		@Override
		public void forEach(java.util.function.DoubleConsumer consumer) {
			if (Double2ShortOpenHashMap.this.containsNullKey) {
				consumer.accept(Double2ShortOpenHashMap.this.key[Double2ShortOpenHashMap.this.n]);
			}

			int pos = Double2ShortOpenHashMap.this.n;

			while (pos-- != 0) {
				double k = Double2ShortOpenHashMap.this.key[pos];
				if (Double.doubleToLongBits(k) != 0L) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Double2ShortOpenHashMap.this.size;
		}

		@Override
		public boolean contains(double k) {
			return Double2ShortOpenHashMap.this.containsKey(k);
		}

		@Override
		public boolean remove(double k) {
			int oldSize = Double2ShortOpenHashMap.this.size;
			Double2ShortOpenHashMap.this.remove(k);
			return Double2ShortOpenHashMap.this.size != oldSize;
		}

		public void clear() {
			Double2ShortOpenHashMap.this.clear();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.doubles.Double2ShortMap.Entry, java.util.Map.Entry<Double, Short> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		@Override
		public double getDoubleKey() {
			return Double2ShortOpenHashMap.this.key[this.index];
		}

		@Override
		public short getShortValue() {
			return Double2ShortOpenHashMap.this.value[this.index];
		}

		@Override
		public short setValue(short v) {
			short oldValue = Double2ShortOpenHashMap.this.value[this.index];
			Double2ShortOpenHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Double getKey() {
			return Double2ShortOpenHashMap.this.key[this.index];
		}

		@Deprecated
		@Override
		public Short getValue() {
			return Double2ShortOpenHashMap.this.value[this.index];
		}

		@Deprecated
		@Override
		public Short setValue(Short v) {
			return this.setValue(v.shortValue());
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<Double, Short> e = (java.util.Map.Entry<Double, Short>)o;
				return Double.doubleToLongBits(Double2ShortOpenHashMap.this.key[this.index]) == Double.doubleToLongBits((Double)e.getKey())
					&& Double2ShortOpenHashMap.this.value[this.index] == (Short)e.getValue();
			}
		}

		public int hashCode() {
			return HashCommon.double2int(Double2ShortOpenHashMap.this.key[this.index]) ^ Double2ShortOpenHashMap.this.value[this.index];
		}

		public String toString() {
			return Double2ShortOpenHashMap.this.key[this.index] + "=>" + Double2ShortOpenHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.doubles.Double2ShortMap.Entry> implements FastEntrySet {
		private MapEntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2ShortMap.Entry> iterator() {
			return Double2ShortOpenHashMap.this.new EntryIterator();
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2ShortMap.Entry> fastIterator() {
			return Double2ShortOpenHashMap.this.new FastEntryIterator();
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Double)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Short) {
					double k = (Double)e.getKey();
					short v = (Short)e.getValue();
					if (Double.doubleToLongBits(k) == 0L) {
						return Double2ShortOpenHashMap.this.containsNullKey && Double2ShortOpenHashMap.this.value[Double2ShortOpenHashMap.this.n] == v;
					} else {
						double[] key = Double2ShortOpenHashMap.this.key;
						double curr;
						int pos;
						if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ShortOpenHashMap.this.mask]) == 0L) {
							return false;
						} else if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
							return Double2ShortOpenHashMap.this.value[pos] == v;
						} else {
							while (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ShortOpenHashMap.this.mask]) != 0L) {
								if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
									return Double2ShortOpenHashMap.this.value[pos] == v;
								}
							}

							return false;
						}
					}
				} else {
					return false;
				}
			}
		}

		public boolean remove(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() != null && e.getKey() instanceof Double) {
					if (e.getValue() != null && e.getValue() instanceof Short) {
						double k = (Double)e.getKey();
						short v = (Short)e.getValue();
						if (Double.doubleToLongBits(k) == 0L) {
							if (Double2ShortOpenHashMap.this.containsNullKey && Double2ShortOpenHashMap.this.value[Double2ShortOpenHashMap.this.n] == v) {
								Double2ShortOpenHashMap.this.removeNullEntry();
								return true;
							} else {
								return false;
							}
						} else {
							double[] key = Double2ShortOpenHashMap.this.key;
							double curr;
							int pos;
							if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ShortOpenHashMap.this.mask]) == 0L) {
								return false;
							} else if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
								if (Double2ShortOpenHashMap.this.value[pos] == v) {
									Double2ShortOpenHashMap.this.removeEntry(pos);
									return true;
								} else {
									return false;
								}
							} else {
								while (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ShortOpenHashMap.this.mask]) != 0L) {
									if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && Double2ShortOpenHashMap.this.value[pos] == v) {
										Double2ShortOpenHashMap.this.removeEntry(pos);
										return true;
									}
								}

								return false;
							}
						}
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		}

		public int size() {
			return Double2ShortOpenHashMap.this.size;
		}

		public void clear() {
			Double2ShortOpenHashMap.this.clear();
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.doubles.Double2ShortMap.Entry> consumer) {
			if (Double2ShortOpenHashMap.this.containsNullKey) {
				consumer.accept(
					new BasicEntry(Double2ShortOpenHashMap.this.key[Double2ShortOpenHashMap.this.n], Double2ShortOpenHashMap.this.value[Double2ShortOpenHashMap.this.n])
				);
			}

			int pos = Double2ShortOpenHashMap.this.n;

			while (pos-- != 0) {
				if (Double.doubleToLongBits(Double2ShortOpenHashMap.this.key[pos]) != 0L) {
					consumer.accept(new BasicEntry(Double2ShortOpenHashMap.this.key[pos], Double2ShortOpenHashMap.this.value[pos]));
				}
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.doubles.Double2ShortMap.Entry> consumer) {
			BasicEntry entry = new BasicEntry();
			if (Double2ShortOpenHashMap.this.containsNullKey) {
				entry.key = Double2ShortOpenHashMap.this.key[Double2ShortOpenHashMap.this.n];
				entry.value = Double2ShortOpenHashMap.this.value[Double2ShortOpenHashMap.this.n];
				consumer.accept(entry);
			}

			int pos = Double2ShortOpenHashMap.this.n;

			while (pos-- != 0) {
				if (Double.doubleToLongBits(Double2ShortOpenHashMap.this.key[pos]) != 0L) {
					entry.key = Double2ShortOpenHashMap.this.key[pos];
					entry.value = Double2ShortOpenHashMap.this.value[pos];
					consumer.accept(entry);
				}
			}
		}
	}

	private class MapIterator {
		int pos = Double2ShortOpenHashMap.this.n;
		int last = -1;
		int c = Double2ShortOpenHashMap.this.size;
		boolean mustReturnNullKey = Double2ShortOpenHashMap.this.containsNullKey;
		DoubleArrayList wrapped;

		private MapIterator() {
		}

		public boolean hasNext() {
			return this.c != 0;
		}

		public int nextEntry() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.c--;
				if (this.mustReturnNullKey) {
					this.mustReturnNullKey = false;
					return this.last = Double2ShortOpenHashMap.this.n;
				} else {
					double[] key = Double2ShortOpenHashMap.this.key;

					while (--this.pos >= 0) {
						if (Double.doubleToLongBits(key[this.pos]) != 0L) {
							return this.last = this.pos;
						}
					}

					this.last = Integer.MIN_VALUE;
					double k = this.wrapped.getDouble(-this.pos - 1);
					int p = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ShortOpenHashMap.this.mask;

					while (Double.doubleToLongBits(k) != Double.doubleToLongBits(key[p])) {
						p = p + 1 & Double2ShortOpenHashMap.this.mask;
					}

					return p;
				}
			}
		}

		private void shiftKeys(int pos) {
			double[] key = Double2ShortOpenHashMap.this.key;

			label38:
			while (true) {
				int last = pos;

				double curr;
				for (pos = pos + 1 & Double2ShortOpenHashMap.this.mask; Double.doubleToLongBits(curr = key[pos]) != 0L; pos = pos + 1 & Double2ShortOpenHashMap.this.mask) {
					int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2ShortOpenHashMap.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new DoubleArrayList(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						Double2ShortOpenHashMap.this.value[last] = Double2ShortOpenHashMap.this.value[pos];
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
				if (this.last == Double2ShortOpenHashMap.this.n) {
					Double2ShortOpenHashMap.this.containsNullKey = false;
				} else {
					if (this.pos < 0) {
						Double2ShortOpenHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				Double2ShortOpenHashMap.this.size--;
				this.last = -1;
			}
		}

		public int skip(int n) {
			int i = n;

			while (i-- != 0 && this.hasNext()) {
				this.nextEntry();
			}

			return n - i - 1;
		}
	}

	private final class ValueIterator extends Double2ShortOpenHashMap.MapIterator implements ShortIterator {
		public ValueIterator() {
		}

		@Override
		public short nextShort() {
			return Double2ShortOpenHashMap.this.value[this.nextEntry()];
		}
	}
}
