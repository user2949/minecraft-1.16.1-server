package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2BooleanMap.BasicEntry;
import it.unimi.dsi.fastutil.doubles.Double2BooleanMap.FastEntrySet;
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
import java.util.function.DoublePredicate;

public class Double2BooleanOpenCustomHashMap extends AbstractDouble2BooleanMap implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient double[] key;
	protected transient boolean[] value;
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
	protected transient BooleanCollection values;

	public Double2BooleanOpenCustomHashMap(int expected, float f, DoubleHash.Strategy strategy) {
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
			this.value = new boolean[this.n + 1];
		}
	}

	public Double2BooleanOpenCustomHashMap(int expected, DoubleHash.Strategy strategy) {
		this(expected, 0.75F, strategy);
	}

	public Double2BooleanOpenCustomHashMap(DoubleHash.Strategy strategy) {
		this(16, 0.75F, strategy);
	}

	public Double2BooleanOpenCustomHashMap(Map<? extends Double, ? extends Boolean> m, float f, DoubleHash.Strategy strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Double2BooleanOpenCustomHashMap(Map<? extends Double, ? extends Boolean> m, DoubleHash.Strategy strategy) {
		this(m, 0.75F, strategy);
	}

	public Double2BooleanOpenCustomHashMap(Double2BooleanMap m, float f, DoubleHash.Strategy strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Double2BooleanOpenCustomHashMap(Double2BooleanMap m, DoubleHash.Strategy strategy) {
		this(m, 0.75F, strategy);
	}

	public Double2BooleanOpenCustomHashMap(double[] k, boolean[] v, float f, DoubleHash.Strategy strategy) {
		this(k.length, f, strategy);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Double2BooleanOpenCustomHashMap(double[] k, boolean[] v, DoubleHash.Strategy strategy) {
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

	private boolean removeEntry(int pos) {
		boolean oldValue = this.value[pos];
		this.size--;
		this.shiftKeys(pos);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	private boolean removeNullEntry() {
		this.containsNullKey = false;
		boolean oldValue = this.value[this.n];
		this.size--;
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	@Override
	public void putAll(Map<? extends Double, ? extends Boolean> m) {
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

	private void insert(int pos, double k, boolean v) {
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
	public boolean put(double k, boolean v) {
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		} else {
			boolean oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
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
	public boolean remove(double k) {
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
	public boolean get(double k) {
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
	public boolean containsValue(boolean v) {
		boolean[] value = this.value;
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
	public boolean getOrDefault(double k, boolean defaultValue) {
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
	public boolean putIfAbsent(double k, boolean v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(double k, boolean v) {
		if (this.strategy.equals(k, 0.0)) {
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
			if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) {
				return false;
			} else if (this.strategy.equals(k, curr) && v == this.value[pos]) {
				this.removeEntry(pos);
				return true;
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (this.strategy.equals(k, curr) && v == this.value[pos]) {
						this.removeEntry(pos);
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean replace(double k, boolean oldValue, boolean v) {
		int pos = this.find(k);
		if (pos >= 0 && oldValue == this.value[pos]) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean replace(double k, boolean v) {
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			boolean oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	@Override
	public boolean computeIfAbsent(double k, DoublePredicate mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			boolean newValue = mappingFunction.test(k);
			this.insert(-pos - 1, k, newValue);
			return newValue;
		}
	}

	@Override
	public boolean computeIfAbsentNullable(double k, DoubleFunction<? extends Boolean> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			Boolean newValue = (Boolean)mappingFunction.apply(k);
			if (newValue == null) {
				return this.defRetValue;
			} else {
				boolean v = newValue;
				this.insert(-pos - 1, k, v);
				return v;
			}
		}
	}

	@Override
	public boolean computeIfPresent(double k, BiFunction<? super Double, ? super Boolean, ? extends Boolean> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			Boolean newValue = (Boolean)remappingFunction.apply(k, this.value[pos]);
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
	public boolean compute(double k, BiFunction<? super Double, ? super Boolean, ? extends Boolean> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		Boolean newValue = (Boolean)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
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
			boolean newVal = newValue;
			if (pos < 0) {
				this.insert(-pos - 1, k, newVal);
				return newVal;
			} else {
				return this.value[pos] = newVal;
			}
		}
	}

	@Override
	public boolean merge(double k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return v;
		} else {
			Boolean newValue = (Boolean)remappingFunction.apply(this.value[pos], v);
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

	public FastEntrySet double2BooleanEntrySet() {
		if (this.entries == null) {
			this.entries = new Double2BooleanOpenCustomHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public DoubleSet keySet() {
		if (this.keys == null) {
			this.keys = new Double2BooleanOpenCustomHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public BooleanCollection values() {
		if (this.values == null) {
			this.values = new AbstractBooleanCollection() {
				@Override
				public BooleanIterator iterator() {
					return Double2BooleanOpenCustomHashMap.this.new ValueIterator();
				}

				public int size() {
					return Double2BooleanOpenCustomHashMap.this.size;
				}

				@Override
				public boolean contains(boolean v) {
					return Double2BooleanOpenCustomHashMap.this.containsValue(v);
				}

				public void clear() {
					Double2BooleanOpenCustomHashMap.this.clear();
				}

				@Override
				public void forEach(BooleanConsumer consumer) {
					if (Double2BooleanOpenCustomHashMap.this.containsNullKey) {
						consumer.accept(Double2BooleanOpenCustomHashMap.this.value[Double2BooleanOpenCustomHashMap.this.n]);
					}

					int pos = Double2BooleanOpenCustomHashMap.this.n;

					while (pos-- != 0) {
						if (Double.doubleToLongBits(Double2BooleanOpenCustomHashMap.this.key[pos]) != 0L) {
							consumer.accept(Double2BooleanOpenCustomHashMap.this.value[pos]);
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
		boolean[] value = this.value;
		int mask = newN - 1;
		double[] newKey = new double[newN + 1];
		boolean[] newValue = new boolean[newN + 1];
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

	public Double2BooleanOpenCustomHashMap clone() {
		Double2BooleanOpenCustomHashMap c;
		try {
			c = (Double2BooleanOpenCustomHashMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (double[])this.key.clone();
		c.value = (boolean[])this.value.clone();
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
			t ^= this.value[i] ? 1231 : 1237;
			h += t;
		}

		if (this.containsNullKey) {
			h += this.value[this.n] ? 1231 : 1237;
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		double[] key = this.key;
		boolean[] value = this.value;
		Double2BooleanOpenCustomHashMap.MapIterator i = new Double2BooleanOpenCustomHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeDouble(key[e]);
			s.writeBoolean(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		double[] key = this.key = new double[this.n + 1];
		boolean[] value = this.value = new boolean[this.n + 1];
		int i = this.size;

		while (i-- != 0) {
			double k = s.readDouble();
			boolean v = s.readBoolean();
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

	private class EntryIterator
		extends Double2BooleanOpenCustomHashMap.MapIterator
		implements ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> {
		private Double2BooleanOpenCustomHashMap.MapEntry entry;

		private EntryIterator() {
		}

		public Double2BooleanOpenCustomHashMap.MapEntry next() {
			return this.entry = Double2BooleanOpenCustomHashMap.this.new MapEntry(this.nextEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator
		extends Double2BooleanOpenCustomHashMap.MapIterator
		implements ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> {
		private final Double2BooleanOpenCustomHashMap.MapEntry entry = Double2BooleanOpenCustomHashMap.this.new MapEntry();

		private FastEntryIterator() {
		}

		public Double2BooleanOpenCustomHashMap.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Double2BooleanOpenCustomHashMap.MapIterator implements DoubleIterator {
		public KeyIterator() {
		}

		@Override
		public double nextDouble() {
			return Double2BooleanOpenCustomHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractDoubleSet {
		private KeySet() {
		}

		@Override
		public DoubleIterator iterator() {
			return Double2BooleanOpenCustomHashMap.this.new KeyIterator();
		}

		@Override
		public void forEach(java.util.function.DoubleConsumer consumer) {
			if (Double2BooleanOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(Double2BooleanOpenCustomHashMap.this.key[Double2BooleanOpenCustomHashMap.this.n]);
			}

			int pos = Double2BooleanOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				double k = Double2BooleanOpenCustomHashMap.this.key[pos];
				if (Double.doubleToLongBits(k) != 0L) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Double2BooleanOpenCustomHashMap.this.size;
		}

		@Override
		public boolean contains(double k) {
			return Double2BooleanOpenCustomHashMap.this.containsKey(k);
		}

		@Override
		public boolean remove(double k) {
			int oldSize = Double2BooleanOpenCustomHashMap.this.size;
			Double2BooleanOpenCustomHashMap.this.remove(k);
			return Double2BooleanOpenCustomHashMap.this.size != oldSize;
		}

		public void clear() {
			Double2BooleanOpenCustomHashMap.this.clear();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry, java.util.Map.Entry<Double, Boolean> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		@Override
		public double getDoubleKey() {
			return Double2BooleanOpenCustomHashMap.this.key[this.index];
		}

		@Override
		public boolean getBooleanValue() {
			return Double2BooleanOpenCustomHashMap.this.value[this.index];
		}

		@Override
		public boolean setValue(boolean v) {
			boolean oldValue = Double2BooleanOpenCustomHashMap.this.value[this.index];
			Double2BooleanOpenCustomHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Double getKey() {
			return Double2BooleanOpenCustomHashMap.this.key[this.index];
		}

		@Deprecated
		@Override
		public Boolean getValue() {
			return Double2BooleanOpenCustomHashMap.this.value[this.index];
		}

		@Deprecated
		@Override
		public Boolean setValue(Boolean v) {
			return this.setValue(v.booleanValue());
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<Double, Boolean> e = (java.util.Map.Entry<Double, Boolean>)o;
				return Double2BooleanOpenCustomHashMap.this.strategy.equals(Double2BooleanOpenCustomHashMap.this.key[this.index], (Double)e.getKey())
					&& Double2BooleanOpenCustomHashMap.this.value[this.index] == (Boolean)e.getValue();
			}
		}

		public int hashCode() {
			return Double2BooleanOpenCustomHashMap.this.strategy.hashCode(Double2BooleanOpenCustomHashMap.this.key[this.index])
				^ (Double2BooleanOpenCustomHashMap.this.value[this.index] ? 1231 : 1237);
		}

		public String toString() {
			return Double2BooleanOpenCustomHashMap.this.key[this.index] + "=>" + Double2BooleanOpenCustomHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> implements FastEntrySet {
		private MapEntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> iterator() {
			return Double2BooleanOpenCustomHashMap.this.new EntryIterator();
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> fastIterator() {
			return Double2BooleanOpenCustomHashMap.this.new FastEntryIterator();
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Double)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Boolean) {
					double k = (Double)e.getKey();
					boolean v = (Boolean)e.getValue();
					if (Double2BooleanOpenCustomHashMap.this.strategy.equals(k, 0.0)) {
						return Double2BooleanOpenCustomHashMap.this.containsNullKey && Double2BooleanOpenCustomHashMap.this.value[Double2BooleanOpenCustomHashMap.this.n] == v;
					} else {
						double[] key = Double2BooleanOpenCustomHashMap.this.key;
						double curr;
						int pos;
						if (Double.doubleToLongBits(
								curr = key[pos = HashCommon.mix(Double2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Double2BooleanOpenCustomHashMap.this.mask]
							)
							== 0L) {
							return false;
						} else if (Double2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) {
							return Double2BooleanOpenCustomHashMap.this.value[pos] == v;
						} else {
							while (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2BooleanOpenCustomHashMap.this.mask]) != 0L) {
								if (Double2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) {
									return Double2BooleanOpenCustomHashMap.this.value[pos] == v;
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
					if (e.getValue() != null && e.getValue() instanceof Boolean) {
						double k = (Double)e.getKey();
						boolean v = (Boolean)e.getValue();
						if (Double2BooleanOpenCustomHashMap.this.strategy.equals(k, 0.0)) {
							if (Double2BooleanOpenCustomHashMap.this.containsNullKey && Double2BooleanOpenCustomHashMap.this.value[Double2BooleanOpenCustomHashMap.this.n] == v) {
								Double2BooleanOpenCustomHashMap.this.removeNullEntry();
								return true;
							} else {
								return false;
							}
						} else {
							double[] key = Double2BooleanOpenCustomHashMap.this.key;
							double curr;
							int pos;
							if (Double.doubleToLongBits(
									curr = key[pos = HashCommon.mix(Double2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Double2BooleanOpenCustomHashMap.this.mask]
								)
								== 0L) {
								return false;
							} else if (Double2BooleanOpenCustomHashMap.this.strategy.equals(curr, k)) {
								if (Double2BooleanOpenCustomHashMap.this.value[pos] == v) {
									Double2BooleanOpenCustomHashMap.this.removeEntry(pos);
									return true;
								} else {
									return false;
								}
							} else {
								while (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2BooleanOpenCustomHashMap.this.mask]) != 0L) {
									if (Double2BooleanOpenCustomHashMap.this.strategy.equals(curr, k) && Double2BooleanOpenCustomHashMap.this.value[pos] == v) {
										Double2BooleanOpenCustomHashMap.this.removeEntry(pos);
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
			return Double2BooleanOpenCustomHashMap.this.size;
		}

		public void clear() {
			Double2BooleanOpenCustomHashMap.this.clear();
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> consumer) {
			if (Double2BooleanOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(
					new BasicEntry(
						Double2BooleanOpenCustomHashMap.this.key[Double2BooleanOpenCustomHashMap.this.n],
						Double2BooleanOpenCustomHashMap.this.value[Double2BooleanOpenCustomHashMap.this.n]
					)
				);
			}

			int pos = Double2BooleanOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Double.doubleToLongBits(Double2BooleanOpenCustomHashMap.this.key[pos]) != 0L) {
					consumer.accept(new BasicEntry(Double2BooleanOpenCustomHashMap.this.key[pos], Double2BooleanOpenCustomHashMap.this.value[pos]));
				}
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> consumer) {
			BasicEntry entry = new BasicEntry();
			if (Double2BooleanOpenCustomHashMap.this.containsNullKey) {
				entry.key = Double2BooleanOpenCustomHashMap.this.key[Double2BooleanOpenCustomHashMap.this.n];
				entry.value = Double2BooleanOpenCustomHashMap.this.value[Double2BooleanOpenCustomHashMap.this.n];
				consumer.accept(entry);
			}

			int pos = Double2BooleanOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Double.doubleToLongBits(Double2BooleanOpenCustomHashMap.this.key[pos]) != 0L) {
					entry.key = Double2BooleanOpenCustomHashMap.this.key[pos];
					entry.value = Double2BooleanOpenCustomHashMap.this.value[pos];
					consumer.accept(entry);
				}
			}
		}
	}

	private class MapIterator {
		int pos = Double2BooleanOpenCustomHashMap.this.n;
		int last = -1;
		int c = Double2BooleanOpenCustomHashMap.this.size;
		boolean mustReturnNullKey = Double2BooleanOpenCustomHashMap.this.containsNullKey;
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
					return this.last = Double2BooleanOpenCustomHashMap.this.n;
				} else {
					double[] key = Double2BooleanOpenCustomHashMap.this.key;

					while (--this.pos >= 0) {
						if (Double.doubleToLongBits(key[this.pos]) != 0L) {
							return this.last = this.pos;
						}
					}

					this.last = Integer.MIN_VALUE;
					double k = this.wrapped.getDouble(-this.pos - 1);
					int p = HashCommon.mix(Double2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Double2BooleanOpenCustomHashMap.this.mask;

					while (!Double2BooleanOpenCustomHashMap.this.strategy.equals(k, key[p])) {
						p = p + 1 & Double2BooleanOpenCustomHashMap.this.mask;
					}

					return p;
				}
			}
		}

		private void shiftKeys(int pos) {
			double[] key = Double2BooleanOpenCustomHashMap.this.key;

			label38:
			while (true) {
				int last = pos;

				double curr;
				for (pos = pos + 1 & Double2BooleanOpenCustomHashMap.this.mask;
					Double.doubleToLongBits(curr = key[pos]) != 0L;
					pos = pos + 1 & Double2BooleanOpenCustomHashMap.this.mask
				) {
					int slot = HashCommon.mix(Double2BooleanOpenCustomHashMap.this.strategy.hashCode(curr)) & Double2BooleanOpenCustomHashMap.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new DoubleArrayList(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						Double2BooleanOpenCustomHashMap.this.value[last] = Double2BooleanOpenCustomHashMap.this.value[pos];
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
				if (this.last == Double2BooleanOpenCustomHashMap.this.n) {
					Double2BooleanOpenCustomHashMap.this.containsNullKey = false;
				} else {
					if (this.pos < 0) {
						Double2BooleanOpenCustomHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				Double2BooleanOpenCustomHashMap.this.size--;
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

	private final class ValueIterator extends Double2BooleanOpenCustomHashMap.MapIterator implements BooleanIterator {
		public ValueIterator() {
		}

		@Override
		public boolean nextBoolean() {
			return Double2BooleanOpenCustomHashMap.this.value[this.nextEntry()];
		}
	}
}
