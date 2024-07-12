package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2DoubleMap.BasicEntry;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
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
import java.util.function.DoubleConsumer;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;

public class Int2DoubleOpenCustomHashMap extends AbstractInt2DoubleMap implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient int[] key;
	protected transient double[] value;
	protected transient int mask;
	protected transient boolean containsNullKey;
	protected IntHash.Strategy strategy;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;
	protected transient FastEntrySet entries;
	protected transient IntSet keys;
	protected transient DoubleCollection values;

	public Int2DoubleOpenCustomHashMap(int expected, float f, IntHash.Strategy strategy) {
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
			this.key = new int[this.n + 1];
			this.value = new double[this.n + 1];
		}
	}

	public Int2DoubleOpenCustomHashMap(int expected, IntHash.Strategy strategy) {
		this(expected, 0.75F, strategy);
	}

	public Int2DoubleOpenCustomHashMap(IntHash.Strategy strategy) {
		this(16, 0.75F, strategy);
	}

	public Int2DoubleOpenCustomHashMap(Map<? extends Integer, ? extends Double> m, float f, IntHash.Strategy strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Int2DoubleOpenCustomHashMap(Map<? extends Integer, ? extends Double> m, IntHash.Strategy strategy) {
		this(m, 0.75F, strategy);
	}

	public Int2DoubleOpenCustomHashMap(Int2DoubleMap m, float f, IntHash.Strategy strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Int2DoubleOpenCustomHashMap(Int2DoubleMap m, IntHash.Strategy strategy) {
		this(m, 0.75F, strategy);
	}

	public Int2DoubleOpenCustomHashMap(int[] k, double[] v, float f, IntHash.Strategy strategy) {
		this(k.length, f, strategy);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Int2DoubleOpenCustomHashMap(int[] k, double[] v, IntHash.Strategy strategy) {
		this(k, v, 0.75F, strategy);
	}

	public IntHash.Strategy strategy() {
		return this.strategy;
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

	private double removeEntry(int pos) {
		double oldValue = this.value[pos];
		this.size--;
		this.shiftKeys(pos);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	private double removeNullEntry() {
		this.containsNullKey = false;
		double oldValue = this.value[this.n];
		this.size--;
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Double> m) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(m.size());
		} else {
			this.tryCapacity((long)(this.size() + m.size()));
		}

		super.putAll(m);
	}

	private int find(int k) {
		if (this.strategy.equals(k, 0)) {
			return this.containsNullKey ? this.n : -(this.n + 1);
		} else {
			int[] key = this.key;
			int curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return -(pos + 1);
			} else if (this.strategy.equals(k, curr)) {
				return pos;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr)) {
						return pos;
					}
				}

				return -(pos + 1);
			}
		}
	}

	private void insert(int pos, int k, double v) {
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
	public double put(int k, double v) {
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		} else {
			double oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	private double addToValue(int pos, double incr) {
		double oldValue = this.value[pos];
		this.value[pos] = oldValue + incr;
		return oldValue;
	}

	public double addTo(int k, double incr) {
		int pos;
		if (this.strategy.equals(k, 0)) {
			if (this.containsNullKey) {
				return this.addToValue(this.n, incr);
			}

			pos = this.n;
			this.containsNullKey = true;
		} else {
			int[] key = this.key;
			int curr;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
				if (this.strategy.equals(curr, k)) {
					return this.addToValue(pos, incr);
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(curr, k)) {
						return this.addToValue(pos, incr);
					}
				}
			}
		}

		this.key[pos] = k;
		this.value[pos] = this.defRetValue + incr;
		if (this.size++ >= this.maxFill) {
			this.rehash(HashCommon.arraySize(this.size + 1, this.f));
		}

		return this.defRetValue;
	}

	protected final void shiftKeys(int pos) {
		int[] key = this.key;

		label30:
		while (true) {
			int last = pos;

			int curr;
			for (pos = pos + 1 & this.mask; (curr = key[pos]) != 0; pos = pos + 1 & this.mask) {
				int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					key[last] = curr;
					this.value[last] = this.value[pos];
					continue label30;
				}
			}

			key[last] = 0;
			return;
		}
	}

	@Override
	public double remove(int k) {
		if (this.strategy.equals(k, 0)) {
			return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
		} else {
			int[] key = this.key;
			int curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return this.defRetValue;
			} else if (this.strategy.equals(k, curr)) {
				return this.removeEntry(pos);
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr)) {
						return this.removeEntry(pos);
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public double get(int k) {
		if (this.strategy.equals(k, 0)) {
			return this.containsNullKey ? this.value[this.n] : this.defRetValue;
		} else {
			int[] key = this.key;
			int curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return this.defRetValue;
			} else if (this.strategy.equals(k, curr)) {
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr)) {
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public boolean containsKey(int k) {
		if (this.strategy.equals(k, 0)) {
			return this.containsNullKey;
		} else {
			int[] key = this.key;
			int curr;
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

	@Override
	public boolean containsValue(double v) {
		double[] value = this.value;
		int[] key = this.key;
		if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v)) {
			return true;
		} else {
			int i = this.n;

			while (i-- != 0) {
				if (key[i] != 0 && Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v)) {
					return true;
				}
			}

			return false;
		}
	}

	@Override
	public double getOrDefault(int k, double defaultValue) {
		if (this.strategy.equals(k, 0)) {
			return this.containsNullKey ? this.value[this.n] : defaultValue;
		} else {
			int[] key = this.key;
			int curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return defaultValue;
			} else if (this.strategy.equals(k, curr)) {
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr)) {
						return this.value[pos];
					}
				}

				return defaultValue;
			}
		}
	}

	@Override
	public double putIfAbsent(int k, double v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(int k, double v) {
		if (this.strategy.equals(k, 0)) {
			if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
				this.removeNullEntry();
				return true;
			} else {
				return false;
			}
		} else {
			int[] key = this.key;
			int curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return false;
			} else if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
				this.removeEntry(pos);
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
						this.removeEntry(pos);
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean replace(int k, double oldValue, double v) {
		int pos = this.find(k);
		if (pos >= 0 && Double.doubleToLongBits(oldValue) == Double.doubleToLongBits(this.value[pos])) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public double replace(int k, double v) {
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			double oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	@Override
	public double computeIfAbsent(int k, IntToDoubleFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			double newValue = mappingFunction.applyAsDouble(k);
			this.insert(-pos - 1, k, newValue);
			return newValue;
		}
	}

	@Override
	public double computeIfAbsentNullable(int k, IntFunction<? extends Double> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			Double newValue = (Double)mappingFunction.apply(k);
			if (newValue == null) {
				return this.defRetValue;
			} else {
				double v = newValue;
				this.insert(-pos - 1, k, v);
				return v;
			}
		}
	}

	@Override
	public double computeIfPresent(int k, BiFunction<? super Integer, ? super Double, ? extends Double> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			Double newValue = (Double)remappingFunction.apply(k, this.value[pos]);
			if (newValue == null) {
				if (this.strategy.equals(k, 0)) {
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
	public double compute(int k, BiFunction<? super Integer, ? super Double, ? extends Double> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		Double newValue = (Double)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
		if (newValue == null) {
			if (pos >= 0) {
				if (this.strategy.equals(k, 0)) {
					this.removeNullEntry();
				} else {
					this.removeEntry(pos);
				}
			}

			return this.defRetValue;
		} else {
			double newVal = newValue;
			if (pos < 0) {
				this.insert(-pos - 1, k, newVal);
				return newVal;
			} else {
				return this.value[pos] = newVal;
			}
		}
	}

	@Override
	public double merge(int k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return v;
		} else {
			Double newValue = (Double)remappingFunction.apply(this.value[pos], v);
			if (newValue == null) {
				if (this.strategy.equals(k, 0)) {
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
			Arrays.fill(this.key, 0);
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

	public FastEntrySet int2DoubleEntrySet() {
		if (this.entries == null) {
			this.entries = new Int2DoubleOpenCustomHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public IntSet keySet() {
		if (this.keys == null) {
			this.keys = new Int2DoubleOpenCustomHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public DoubleCollection values() {
		if (this.values == null) {
			this.values = new AbstractDoubleCollection() {
				@Override
				public DoubleIterator iterator() {
					return Int2DoubleOpenCustomHashMap.this.new ValueIterator();
				}

				public int size() {
					return Int2DoubleOpenCustomHashMap.this.size;
				}

				@Override
				public boolean contains(double v) {
					return Int2DoubleOpenCustomHashMap.this.containsValue(v);
				}

				public void clear() {
					Int2DoubleOpenCustomHashMap.this.clear();
				}

				@Override
				public void forEach(DoubleConsumer consumer) {
					if (Int2DoubleOpenCustomHashMap.this.containsNullKey) {
						consumer.accept(Int2DoubleOpenCustomHashMap.this.value[Int2DoubleOpenCustomHashMap.this.n]);
					}

					int pos = Int2DoubleOpenCustomHashMap.this.n;

					while (pos-- != 0) {
						if (Int2DoubleOpenCustomHashMap.this.key[pos] != 0) {
							consumer.accept(Int2DoubleOpenCustomHashMap.this.value[pos]);
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
		int[] key = this.key;
		double[] value = this.value;
		int mask = newN - 1;
		int[] newKey = new int[newN + 1];
		double[] newValue = new double[newN + 1];
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
			newValue[pos] = value[i];
		}

		newValue[newN] = value[this.n];
		this.n = newN;
		this.mask = mask;
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.key = newKey;
		this.value = newValue;
	}

	public Int2DoubleOpenCustomHashMap clone() {
		Int2DoubleOpenCustomHashMap c;
		try {
			c = (Int2DoubleOpenCustomHashMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (int[])this.key.clone();
		c.value = (double[])this.value.clone();
		c.strategy = this.strategy;
		return c;
	}

	@Override
	public int hashCode() {
		int h = 0;
		int j = this.realSize();
		int i = 0;

		for (int t = 0; j-- != 0; i++) {
			while (this.key[i] == 0) {
				i++;
			}

			t = this.strategy.hashCode(this.key[i]);
			t ^= HashCommon.double2int(this.value[i]);
			h += t;
		}

		if (this.containsNullKey) {
			h += HashCommon.double2int(this.value[this.n]);
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		int[] key = this.key;
		double[] value = this.value;
		Int2DoubleOpenCustomHashMap.MapIterator i = new Int2DoubleOpenCustomHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeInt(key[e]);
			s.writeDouble(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		int[] key = this.key = new int[this.n + 1];
		double[] value = this.value = new double[this.n + 1];
		int i = this.size;

		while (i-- != 0) {
			int k = s.readInt();
			double v = s.readDouble();
			int pos;
			if (this.strategy.equals(k, 0)) {
				pos = this.n;
				this.containsNullKey = true;
			} else {
				pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;

				while (key[pos] != 0) {
					pos = pos + 1 & this.mask;
				}
			}

			key[pos] = k;
			value[pos] = v;
		}
	}

	private void checkTable() {
	}

	private class EntryIterator extends Int2DoubleOpenCustomHashMap.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.ints.Int2DoubleMap.Entry> {
		private Int2DoubleOpenCustomHashMap.MapEntry entry;

		private EntryIterator() {
		}

		public Int2DoubleOpenCustomHashMap.MapEntry next() {
			return this.entry = Int2DoubleOpenCustomHashMap.this.new MapEntry(this.nextEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator extends Int2DoubleOpenCustomHashMap.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.ints.Int2DoubleMap.Entry> {
		private final Int2DoubleOpenCustomHashMap.MapEntry entry = Int2DoubleOpenCustomHashMap.this.new MapEntry();

		private FastEntryIterator() {
		}

		public Int2DoubleOpenCustomHashMap.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Int2DoubleOpenCustomHashMap.MapIterator implements IntIterator {
		public KeyIterator() {
		}

		@Override
		public int nextInt() {
			return Int2DoubleOpenCustomHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractIntSet {
		private KeySet() {
		}

		@Override
		public IntIterator iterator() {
			return Int2DoubleOpenCustomHashMap.this.new KeyIterator();
		}

		@Override
		public void forEach(java.util.function.IntConsumer consumer) {
			if (Int2DoubleOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(Int2DoubleOpenCustomHashMap.this.key[Int2DoubleOpenCustomHashMap.this.n]);
			}

			int pos = Int2DoubleOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				int k = Int2DoubleOpenCustomHashMap.this.key[pos];
				if (k != 0) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Int2DoubleOpenCustomHashMap.this.size;
		}

		@Override
		public boolean contains(int k) {
			return Int2DoubleOpenCustomHashMap.this.containsKey(k);
		}

		@Override
		public boolean remove(int k) {
			int oldSize = Int2DoubleOpenCustomHashMap.this.size;
			Int2DoubleOpenCustomHashMap.this.remove(k);
			return Int2DoubleOpenCustomHashMap.this.size != oldSize;
		}

		public void clear() {
			Int2DoubleOpenCustomHashMap.this.clear();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.ints.Int2DoubleMap.Entry, java.util.Map.Entry<Integer, Double> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		@Override
		public int getIntKey() {
			return Int2DoubleOpenCustomHashMap.this.key[this.index];
		}

		@Override
		public double getDoubleValue() {
			return Int2DoubleOpenCustomHashMap.this.value[this.index];
		}

		@Override
		public double setValue(double v) {
			double oldValue = Int2DoubleOpenCustomHashMap.this.value[this.index];
			Int2DoubleOpenCustomHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Integer getKey() {
			return Int2DoubleOpenCustomHashMap.this.key[this.index];
		}

		@Deprecated
		@Override
		public Double getValue() {
			return Int2DoubleOpenCustomHashMap.this.value[this.index];
		}

		@Deprecated
		@Override
		public Double setValue(Double v) {
			return this.setValue(v.doubleValue());
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<Integer, Double> e = (java.util.Map.Entry<Integer, Double>)o;
				return Int2DoubleOpenCustomHashMap.this.strategy.equals(Int2DoubleOpenCustomHashMap.this.key[this.index], (Integer)e.getKey())
					&& Double.doubleToLongBits(Int2DoubleOpenCustomHashMap.this.value[this.index]) == Double.doubleToLongBits((Double)e.getValue());
			}
		}

		public int hashCode() {
			return Int2DoubleOpenCustomHashMap.this.strategy.hashCode(Int2DoubleOpenCustomHashMap.this.key[this.index])
				^ HashCommon.double2int(Int2DoubleOpenCustomHashMap.this.value[this.index]);
		}

		public String toString() {
			return Int2DoubleOpenCustomHashMap.this.key[this.index] + "=>" + Int2DoubleOpenCustomHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.ints.Int2DoubleMap.Entry> implements FastEntrySet {
		private MapEntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.ints.Int2DoubleMap.Entry> iterator() {
			return Int2DoubleOpenCustomHashMap.this.new EntryIterator();
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.ints.Int2DoubleMap.Entry> fastIterator() {
			return Int2DoubleOpenCustomHashMap.this.new FastEntryIterator();
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Integer)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Double) {
					int k = (Integer)e.getKey();
					double v = (Double)e.getValue();
					if (Int2DoubleOpenCustomHashMap.this.strategy.equals(k, 0)) {
						return Int2DoubleOpenCustomHashMap.this.containsNullKey
							&& Double.doubleToLongBits(Int2DoubleOpenCustomHashMap.this.value[Int2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v);
					} else {
						int[] key = Int2DoubleOpenCustomHashMap.this.key;
						int curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(Int2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Int2DoubleOpenCustomHashMap.this.mask]) == 0) {
							return false;
						} else if (Int2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) {
							return Double.doubleToLongBits(Int2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v);
						} else {
							while ((curr = key[pos = pos + 1 & Int2DoubleOpenCustomHashMap.this.mask]) != 0) {
								if (Int2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) {
									return Double.doubleToLongBits(Int2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v);
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
				if (e.getKey() != null && e.getKey() instanceof Integer) {
					if (e.getValue() != null && e.getValue() instanceof Double) {
						int k = (Integer)e.getKey();
						double v = (Double)e.getValue();
						if (Int2DoubleOpenCustomHashMap.this.strategy.equals(k, 0)) {
							if (Int2DoubleOpenCustomHashMap.this.containsNullKey
								&& Double.doubleToLongBits(Int2DoubleOpenCustomHashMap.this.value[Int2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v)) {
								Int2DoubleOpenCustomHashMap.this.removeNullEntry();
								return true;
							} else {
								return false;
							}
						} else {
							int[] key = Int2DoubleOpenCustomHashMap.this.key;
							int curr;
							int pos;
							if ((curr = key[pos = HashCommon.mix(Int2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Int2DoubleOpenCustomHashMap.this.mask]) == 0) {
								return false;
							} else if (Int2DoubleOpenCustomHashMap.this.strategy.equals(curr, k)) {
								if (Double.doubleToLongBits(Int2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
									Int2DoubleOpenCustomHashMap.this.removeEntry(pos);
									return true;
								} else {
									return false;
								}
							} else {
								while ((curr = key[pos = pos + 1 & Int2DoubleOpenCustomHashMap.this.mask]) != 0) {
									if (Int2DoubleOpenCustomHashMap.this.strategy.equals(curr, k)
										&& Double.doubleToLongBits(Int2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
										Int2DoubleOpenCustomHashMap.this.removeEntry(pos);
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
			return Int2DoubleOpenCustomHashMap.this.size;
		}

		public void clear() {
			Int2DoubleOpenCustomHashMap.this.clear();
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.ints.Int2DoubleMap.Entry> consumer) {
			if (Int2DoubleOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(
					new BasicEntry(
						Int2DoubleOpenCustomHashMap.this.key[Int2DoubleOpenCustomHashMap.this.n], Int2DoubleOpenCustomHashMap.this.value[Int2DoubleOpenCustomHashMap.this.n]
					)
				);
			}

			int pos = Int2DoubleOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Int2DoubleOpenCustomHashMap.this.key[pos] != 0) {
					consumer.accept(new BasicEntry(Int2DoubleOpenCustomHashMap.this.key[pos], Int2DoubleOpenCustomHashMap.this.value[pos]));
				}
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.ints.Int2DoubleMap.Entry> consumer) {
			BasicEntry entry = new BasicEntry();
			if (Int2DoubleOpenCustomHashMap.this.containsNullKey) {
				entry.key = Int2DoubleOpenCustomHashMap.this.key[Int2DoubleOpenCustomHashMap.this.n];
				entry.value = Int2DoubleOpenCustomHashMap.this.value[Int2DoubleOpenCustomHashMap.this.n];
				consumer.accept(entry);
			}

			int pos = Int2DoubleOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Int2DoubleOpenCustomHashMap.this.key[pos] != 0) {
					entry.key = Int2DoubleOpenCustomHashMap.this.key[pos];
					entry.value = Int2DoubleOpenCustomHashMap.this.value[pos];
					consumer.accept(entry);
				}
			}
		}
	}

	private class MapIterator {
		int pos = Int2DoubleOpenCustomHashMap.this.n;
		int last = -1;
		int c = Int2DoubleOpenCustomHashMap.this.size;
		boolean mustReturnNullKey = Int2DoubleOpenCustomHashMap.this.containsNullKey;
		IntArrayList wrapped;

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
					return this.last = Int2DoubleOpenCustomHashMap.this.n;
				} else {
					int[] key = Int2DoubleOpenCustomHashMap.this.key;

					while (--this.pos >= 0) {
						if (key[this.pos] != 0) {
							return this.last = this.pos;
						}
					}

					this.last = Integer.MIN_VALUE;
					int k = this.wrapped.getInt(-this.pos - 1);
					int p = HashCommon.mix(Int2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Int2DoubleOpenCustomHashMap.this.mask;

					while (!Int2DoubleOpenCustomHashMap.this.strategy.equals(k, key[p])) {
						p = p + 1 & Int2DoubleOpenCustomHashMap.this.mask;
					}

					return p;
				}
			}
		}

		private void shiftKeys(int pos) {
			int[] key = Int2DoubleOpenCustomHashMap.this.key;

			label38:
			while (true) {
				int last = pos;

				int curr;
				for (pos = pos + 1 & Int2DoubleOpenCustomHashMap.this.mask; (curr = key[pos]) != 0; pos = pos + 1 & Int2DoubleOpenCustomHashMap.this.mask) {
					int slot = HashCommon.mix(Int2DoubleOpenCustomHashMap.this.strategy.hashCode(curr)) & Int2DoubleOpenCustomHashMap.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new IntArrayList(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						Int2DoubleOpenCustomHashMap.this.value[last] = Int2DoubleOpenCustomHashMap.this.value[pos];
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
				if (this.last == Int2DoubleOpenCustomHashMap.this.n) {
					Int2DoubleOpenCustomHashMap.this.containsNullKey = false;
				} else {
					if (this.pos < 0) {
						Int2DoubleOpenCustomHashMap.this.remove(this.wrapped.getInt(-this.pos - 1));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				Int2DoubleOpenCustomHashMap.this.size--;
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

	private final class ValueIterator extends Int2DoubleOpenCustomHashMap.MapIterator implements DoubleIterator {
		public ValueIterator() {
		}

		@Override
		public double nextDouble() {
			return Int2DoubleOpenCustomHashMap.this.value[this.nextEntry()];
		}
	}
}
