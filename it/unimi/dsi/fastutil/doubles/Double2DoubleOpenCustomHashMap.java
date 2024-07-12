package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2DoubleMap.BasicEntry;
import it.unimi.dsi.fastutil.doubles.Double2DoubleMap.FastEntrySet;
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
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;

public class Double2DoubleOpenCustomHashMap extends AbstractDouble2DoubleMap implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient double[] key;
	protected transient double[] value;
	protected transient int mask;
	protected transient boolean containsNullKey;
	protected DoubleHash.Strategy strategy;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;
	protected transient FastEntrySet entries;
	protected transient DoubleSet keys;
	protected transient DoubleCollection values;

	public Double2DoubleOpenCustomHashMap(int expected, float f, DoubleHash.Strategy strategy) {
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
			this.value = new double[this.n + 1];
		}
	}

	public Double2DoubleOpenCustomHashMap(int expected, DoubleHash.Strategy strategy) {
		this(expected, 0.75F, strategy);
	}

	public Double2DoubleOpenCustomHashMap(DoubleHash.Strategy strategy) {
		this(16, 0.75F, strategy);
	}

	public Double2DoubleOpenCustomHashMap(Map<? extends Double, ? extends Double> m, float f, DoubleHash.Strategy strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Double2DoubleOpenCustomHashMap(Map<? extends Double, ? extends Double> m, DoubleHash.Strategy strategy) {
		this(m, 0.75F, strategy);
	}

	public Double2DoubleOpenCustomHashMap(Double2DoubleMap m, float f, DoubleHash.Strategy strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Double2DoubleOpenCustomHashMap(Double2DoubleMap m, DoubleHash.Strategy strategy) {
		this(m, 0.75F, strategy);
	}

	public Double2DoubleOpenCustomHashMap(double[] k, double[] v, float f, DoubleHash.Strategy strategy) {
		this(k.length, f, strategy);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Double2DoubleOpenCustomHashMap(double[] k, double[] v, DoubleHash.Strategy strategy) {
		this(k, v, 0.75F, strategy);
	}

	public DoubleHash.Strategy strategy() {
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
	public void putAll(Map<? extends Double, ? extends Double> m) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(m.size());
		} else {
			this.tryCapacity((long)(this.size() + m.size()));
		}

		super.putAll(m);
	}

	private int find(double k) {
		if (this.strategy.equals(k, 0.0)) {
			return this.containsNullKey ? this.n : -(this.n + 1);
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) {
				return -(pos + 1);
			} else if (this.strategy.equals(k, curr)) {
				return pos;
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (this.strategy.equals(k, curr)) {
						return pos;
					}
				}

				return -(pos + 1);
			}
		}
	}

	private void insert(int pos, double k, double v) {
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
	public double put(double k, double v) {
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

	public double addTo(double k, double incr) {
		int pos;
		if (this.strategy.equals(k, 0.0)) {
			if (this.containsNullKey) {
				return this.addToValue(this.n, incr);
			}

			pos = this.n;
			this.containsNullKey = true;
		} else {
			double[] key = this.key;
			double curr;
			if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0L) {
				if (this.strategy.equals(curr, k)) {
					return this.addToValue(pos, incr);
				}

				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
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
		double[] key = this.key;

		label30:
		while (true) {
			int last = pos;

			double curr;
			for (pos = pos + 1 & this.mask; Double.doubleToLongBits(curr = key[pos]) != 0L; pos = pos + 1 & this.mask) {
				int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
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
	public double remove(double k) {
		if (this.strategy.equals(k, 0.0)) {
			return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) {
				return this.defRetValue;
			} else if (this.strategy.equals(k, curr)) {
				return this.removeEntry(pos);
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (this.strategy.equals(k, curr)) {
						return this.removeEntry(pos);
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public double get(double k) {
		if (this.strategy.equals(k, 0.0)) {
			return this.containsNullKey ? this.value[this.n] : this.defRetValue;
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) {
				return this.defRetValue;
			} else if (this.strategy.equals(k, curr)) {
				return this.value[pos];
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (this.strategy.equals(k, curr)) {
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public boolean containsKey(double k) {
		if (this.strategy.equals(k, 0.0)) {
			return this.containsNullKey;
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

	@Override
	public boolean containsValue(double v) {
		double[] value = this.value;
		double[] key = this.key;
		if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v)) {
			return true;
		} else {
			int i = this.n;

			while (i-- != 0) {
				if (Double.doubleToLongBits(key[i]) != 0L && Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v)) {
					return true;
				}
			}

			return false;
		}
	}

	@Override
	public double getOrDefault(double k, double defaultValue) {
		if (this.strategy.equals(k, 0.0)) {
			return this.containsNullKey ? this.value[this.n] : defaultValue;
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) {
				return defaultValue;
			} else if (this.strategy.equals(k, curr)) {
				return this.value[pos];
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (this.strategy.equals(k, curr)) {
						return this.value[pos];
					}
				}

				return defaultValue;
			}
		}
	}

	@Override
	public double putIfAbsent(double k, double v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(double k, double v) {
		if (this.strategy.equals(k, 0.0)) {
			if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
				this.removeNullEntry();
				return true;
			} else {
				return false;
			}
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) {
				return false;
			} else if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
				this.removeEntry(pos);
				return true;
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
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
	public boolean replace(double k, double oldValue, double v) {
		int pos = this.find(k);
		if (pos >= 0 && Double.doubleToLongBits(oldValue) == Double.doubleToLongBits(this.value[pos])) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public double replace(double k, double v) {
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
	public double computeIfAbsent(double k, DoubleUnaryOperator mappingFunction) {
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
	public double computeIfAbsentNullable(double k, DoubleFunction<? extends Double> mappingFunction) {
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
	public double computeIfPresent(double k, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			Double newValue = (Double)remappingFunction.apply(k, this.value[pos]);
			if (newValue == null) {
				if (this.strategy.equals(k, 0.0)) {
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
	public double compute(double k, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		Double newValue = (Double)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
		if (newValue == null) {
			if (pos >= 0) {
				if (this.strategy.equals(k, 0.0)) {
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
	public double merge(double k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return v;
		} else {
			Double newValue = (Double)remappingFunction.apply(this.value[pos], v);
			if (newValue == null) {
				if (this.strategy.equals(k, 0.0)) {
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

	public FastEntrySet double2DoubleEntrySet() {
		if (this.entries == null) {
			this.entries = new Double2DoubleOpenCustomHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public DoubleSet keySet() {
		if (this.keys == null) {
			this.keys = new Double2DoubleOpenCustomHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public DoubleCollection values() {
		if (this.values == null) {
			this.values = new AbstractDoubleCollection() {
				@Override
				public DoubleIterator iterator() {
					return Double2DoubleOpenCustomHashMap.this.new ValueIterator();
				}

				public int size() {
					return Double2DoubleOpenCustomHashMap.this.size;
				}

				@Override
				public boolean contains(double v) {
					return Double2DoubleOpenCustomHashMap.this.containsValue(v);
				}

				public void clear() {
					Double2DoubleOpenCustomHashMap.this.clear();
				}

				@Override
				public void forEach(java.util.function.DoubleConsumer consumer) {
					if (Double2DoubleOpenCustomHashMap.this.containsNullKey) {
						consumer.accept(Double2DoubleOpenCustomHashMap.this.value[Double2DoubleOpenCustomHashMap.this.n]);
					}

					int pos = Double2DoubleOpenCustomHashMap.this.n;

					while (pos-- != 0) {
						if (Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.key[pos]) != 0L) {
							consumer.accept(Double2DoubleOpenCustomHashMap.this.value[pos]);
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
		double[] value = this.value;
		int mask = newN - 1;
		double[] newKey = new double[newN + 1];
		double[] newValue = new double[newN + 1];
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
			newValue[pos] = value[i];
		}

		newValue[newN] = value[this.n];
		this.n = newN;
		this.mask = mask;
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.key = newKey;
		this.value = newValue;
	}

	public Double2DoubleOpenCustomHashMap clone() {
		Double2DoubleOpenCustomHashMap c;
		try {
			c = (Double2DoubleOpenCustomHashMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (double[])this.key.clone();
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
			while (Double.doubleToLongBits(this.key[i]) == 0L) {
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
		double[] key = this.key;
		double[] value = this.value;
		Double2DoubleOpenCustomHashMap.MapIterator i = new Double2DoubleOpenCustomHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeDouble(key[e]);
			s.writeDouble(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		double[] key = this.key = new double[this.n + 1];
		double[] value = this.value = new double[this.n + 1];
		int i = this.size;

		while (i-- != 0) {
			double k = s.readDouble();
			double v = s.readDouble();
			int pos;
			if (this.strategy.equals(k, 0.0)) {
				pos = this.n;
				this.containsNullKey = true;
			} else {
				pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;

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

	private class EntryIterator extends Double2DoubleOpenCustomHashMap.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> {
		private Double2DoubleOpenCustomHashMap.MapEntry entry;

		private EntryIterator() {
		}

		public Double2DoubleOpenCustomHashMap.MapEntry next() {
			return this.entry = Double2DoubleOpenCustomHashMap.this.new MapEntry(this.nextEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator
		extends Double2DoubleOpenCustomHashMap.MapIterator
		implements ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> {
		private final Double2DoubleOpenCustomHashMap.MapEntry entry = Double2DoubleOpenCustomHashMap.this.new MapEntry();

		private FastEntryIterator() {
		}

		public Double2DoubleOpenCustomHashMap.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Double2DoubleOpenCustomHashMap.MapIterator implements DoubleIterator {
		public KeyIterator() {
		}

		@Override
		public double nextDouble() {
			return Double2DoubleOpenCustomHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractDoubleSet {
		private KeySet() {
		}

		@Override
		public DoubleIterator iterator() {
			return Double2DoubleOpenCustomHashMap.this.new KeyIterator();
		}

		@Override
		public void forEach(java.util.function.DoubleConsumer consumer) {
			if (Double2DoubleOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(Double2DoubleOpenCustomHashMap.this.key[Double2DoubleOpenCustomHashMap.this.n]);
			}

			int pos = Double2DoubleOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				double k = Double2DoubleOpenCustomHashMap.this.key[pos];
				if (Double.doubleToLongBits(k) != 0L) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Double2DoubleOpenCustomHashMap.this.size;
		}

		@Override
		public boolean contains(double k) {
			return Double2DoubleOpenCustomHashMap.this.containsKey(k);
		}

		@Override
		public boolean remove(double k) {
			int oldSize = Double2DoubleOpenCustomHashMap.this.size;
			Double2DoubleOpenCustomHashMap.this.remove(k);
			return Double2DoubleOpenCustomHashMap.this.size != oldSize;
		}

		public void clear() {
			Double2DoubleOpenCustomHashMap.this.clear();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry, java.util.Map.Entry<Double, Double> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		@Override
		public double getDoubleKey() {
			return Double2DoubleOpenCustomHashMap.this.key[this.index];
		}

		@Override
		public double getDoubleValue() {
			return Double2DoubleOpenCustomHashMap.this.value[this.index];
		}

		@Override
		public double setValue(double v) {
			double oldValue = Double2DoubleOpenCustomHashMap.this.value[this.index];
			Double2DoubleOpenCustomHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Double getKey() {
			return Double2DoubleOpenCustomHashMap.this.key[this.index];
		}

		@Deprecated
		@Override
		public Double getValue() {
			return Double2DoubleOpenCustomHashMap.this.value[this.index];
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
				java.util.Map.Entry<Double, Double> e = (java.util.Map.Entry<Double, Double>)o;
				return Double2DoubleOpenCustomHashMap.this.strategy.equals(Double2DoubleOpenCustomHashMap.this.key[this.index], (Double)e.getKey())
					&& Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[this.index]) == Double.doubleToLongBits((Double)e.getValue());
			}
		}

		public int hashCode() {
			return Double2DoubleOpenCustomHashMap.this.strategy.hashCode(Double2DoubleOpenCustomHashMap.this.key[this.index])
				^ HashCommon.double2int(Double2DoubleOpenCustomHashMap.this.value[this.index]);
		}

		public String toString() {
			return Double2DoubleOpenCustomHashMap.this.key[this.index] + "=>" + Double2DoubleOpenCustomHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> implements FastEntrySet {
		private MapEntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> iterator() {
			return Double2DoubleOpenCustomHashMap.this.new EntryIterator();
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> fastIterator() {
			return Double2DoubleOpenCustomHashMap.this.new FastEntryIterator();
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Double)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Double) {
					double k = (Double)e.getKey();
					double v = (Double)e.getValue();
					if (Double2DoubleOpenCustomHashMap.this.strategy.equals(k, 0.0)) {
						return Double2DoubleOpenCustomHashMap.this.containsNullKey
							&& Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[Double2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v);
					} else {
						double[] key = Double2DoubleOpenCustomHashMap.this.key;
						double curr;
						int pos;
						if (Double.doubleToLongBits(
								curr = key[pos = HashCommon.mix(Double2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Double2DoubleOpenCustomHashMap.this.mask]
							)
							== 0L) {
							return false;
						} else if (Double2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) {
							return Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v);
						} else {
							while (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2DoubleOpenCustomHashMap.this.mask]) != 0L) {
								if (Double2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) {
									return Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v);
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
					if (e.getValue() != null && e.getValue() instanceof Double) {
						double k = (Double)e.getKey();
						double v = (Double)e.getValue();
						if (Double2DoubleOpenCustomHashMap.this.strategy.equals(k, 0.0)) {
							if (Double2DoubleOpenCustomHashMap.this.containsNullKey
								&& Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[Double2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v)) {
								Double2DoubleOpenCustomHashMap.this.removeNullEntry();
								return true;
							} else {
								return false;
							}
						} else {
							double[] key = Double2DoubleOpenCustomHashMap.this.key;
							double curr;
							int pos;
							if (Double.doubleToLongBits(
									curr = key[pos = HashCommon.mix(Double2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Double2DoubleOpenCustomHashMap.this.mask]
								)
								== 0L) {
								return false;
							} else if (Double2DoubleOpenCustomHashMap.this.strategy.equals(curr, k)) {
								if (Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
									Double2DoubleOpenCustomHashMap.this.removeEntry(pos);
									return true;
								} else {
									return false;
								}
							} else {
								while (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2DoubleOpenCustomHashMap.this.mask]) != 0L) {
									if (Double2DoubleOpenCustomHashMap.this.strategy.equals(curr, k)
										&& Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
										Double2DoubleOpenCustomHashMap.this.removeEntry(pos);
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
			return Double2DoubleOpenCustomHashMap.this.size;
		}

		public void clear() {
			Double2DoubleOpenCustomHashMap.this.clear();
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> consumer) {
			if (Double2DoubleOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(
					new BasicEntry(
						Double2DoubleOpenCustomHashMap.this.key[Double2DoubleOpenCustomHashMap.this.n],
						Double2DoubleOpenCustomHashMap.this.value[Double2DoubleOpenCustomHashMap.this.n]
					)
				);
			}

			int pos = Double2DoubleOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.key[pos]) != 0L) {
					consumer.accept(new BasicEntry(Double2DoubleOpenCustomHashMap.this.key[pos], Double2DoubleOpenCustomHashMap.this.value[pos]));
				}
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> consumer) {
			BasicEntry entry = new BasicEntry();
			if (Double2DoubleOpenCustomHashMap.this.containsNullKey) {
				entry.key = Double2DoubleOpenCustomHashMap.this.key[Double2DoubleOpenCustomHashMap.this.n];
				entry.value = Double2DoubleOpenCustomHashMap.this.value[Double2DoubleOpenCustomHashMap.this.n];
				consumer.accept(entry);
			}

			int pos = Double2DoubleOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.key[pos]) != 0L) {
					entry.key = Double2DoubleOpenCustomHashMap.this.key[pos];
					entry.value = Double2DoubleOpenCustomHashMap.this.value[pos];
					consumer.accept(entry);
				}
			}
		}
	}

	private class MapIterator {
		int pos = Double2DoubleOpenCustomHashMap.this.n;
		int last = -1;
		int c = Double2DoubleOpenCustomHashMap.this.size;
		boolean mustReturnNullKey = Double2DoubleOpenCustomHashMap.this.containsNullKey;
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
					return this.last = Double2DoubleOpenCustomHashMap.this.n;
				} else {
					double[] key = Double2DoubleOpenCustomHashMap.this.key;

					while (--this.pos >= 0) {
						if (Double.doubleToLongBits(key[this.pos]) != 0L) {
							return this.last = this.pos;
						}
					}

					this.last = Integer.MIN_VALUE;
					double k = this.wrapped.getDouble(-this.pos - 1);
					int p = HashCommon.mix(Double2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Double2DoubleOpenCustomHashMap.this.mask;

					while (!Double2DoubleOpenCustomHashMap.this.strategy.equals(k, key[p])) {
						p = p + 1 & Double2DoubleOpenCustomHashMap.this.mask;
					}

					return p;
				}
			}
		}

		private void shiftKeys(int pos) {
			double[] key = Double2DoubleOpenCustomHashMap.this.key;

			label38:
			while (true) {
				int last = pos;

				double curr;
				for (pos = pos + 1 & Double2DoubleOpenCustomHashMap.this.mask;
					Double.doubleToLongBits(curr = key[pos]) != 0L;
					pos = pos + 1 & Double2DoubleOpenCustomHashMap.this.mask
				) {
					int slot = HashCommon.mix(Double2DoubleOpenCustomHashMap.this.strategy.hashCode(curr)) & Double2DoubleOpenCustomHashMap.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new DoubleArrayList(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						Double2DoubleOpenCustomHashMap.this.value[last] = Double2DoubleOpenCustomHashMap.this.value[pos];
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
				if (this.last == Double2DoubleOpenCustomHashMap.this.n) {
					Double2DoubleOpenCustomHashMap.this.containsNullKey = false;
				} else {
					if (this.pos < 0) {
						Double2DoubleOpenCustomHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				Double2DoubleOpenCustomHashMap.this.size--;
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

	private final class ValueIterator extends Double2DoubleOpenCustomHashMap.MapIterator implements DoubleIterator {
		public ValueIterator() {
		}

		@Override
		public double nextDouble() {
			return Double2DoubleOpenCustomHashMap.this.value[this.nextEntry()];
		}
	}
}
