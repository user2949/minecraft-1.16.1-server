package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2BooleanMap.BasicEntry;
import it.unimi.dsi.fastutil.ints.Int2BooleanMap.FastEntrySet;
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
import java.util.function.IntFunction;
import java.util.function.IntPredicate;

public class Int2BooleanOpenCustomHashMap extends AbstractInt2BooleanMap implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient int[] key;
	protected transient boolean[] value;
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
	protected transient BooleanCollection values;

	public Int2BooleanOpenCustomHashMap(int expected, float f, IntHash.Strategy strategy) {
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
			this.value = new boolean[this.n + 1];
		}
	}

	public Int2BooleanOpenCustomHashMap(int expected, IntHash.Strategy strategy) {
		this(expected, 0.75F, strategy);
	}

	public Int2BooleanOpenCustomHashMap(IntHash.Strategy strategy) {
		this(16, 0.75F, strategy);
	}

	public Int2BooleanOpenCustomHashMap(Map<? extends Integer, ? extends Boolean> m, float f, IntHash.Strategy strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Int2BooleanOpenCustomHashMap(Map<? extends Integer, ? extends Boolean> m, IntHash.Strategy strategy) {
		this(m, 0.75F, strategy);
	}

	public Int2BooleanOpenCustomHashMap(Int2BooleanMap m, float f, IntHash.Strategy strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Int2BooleanOpenCustomHashMap(Int2BooleanMap m, IntHash.Strategy strategy) {
		this(m, 0.75F, strategy);
	}

	public Int2BooleanOpenCustomHashMap(int[] k, boolean[] v, float f, IntHash.Strategy strategy) {
		this(k.length, f, strategy);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Int2BooleanOpenCustomHashMap(int[] k, boolean[] v, IntHash.Strategy strategy) {
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
	public void putAll(Map<? extends Integer, ? extends Boolean> m) {
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

	private void insert(int pos, int k, boolean v) {
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
	public boolean put(int k, boolean v) {
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
	public boolean remove(int k) {
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
	public boolean get(int k) {
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
	public boolean containsValue(boolean v) {
		boolean[] value = this.value;
		int[] key = this.key;
		if (this.containsNullKey && value[this.n] == v) {
			return true;
		} else {
			int i = this.n;

			while (i-- != 0) {
				if (key[i] != 0 && value[i] == v) {
					return true;
				}
			}

			return false;
		}
	}

	@Override
	public boolean getOrDefault(int k, boolean defaultValue) {
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
	public boolean putIfAbsent(int k, boolean v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(int k, boolean v) {
		if (this.strategy.equals(k, 0)) {
			if (this.containsNullKey && v == this.value[this.n]) {
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
			} else if (this.strategy.equals(k, curr) && v == this.value[pos]) {
				this.removeEntry(pos);
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
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
	public boolean replace(int k, boolean oldValue, boolean v) {
		int pos = this.find(k);
		if (pos >= 0 && oldValue == this.value[pos]) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean replace(int k, boolean v) {
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
	public boolean computeIfAbsent(int k, IntPredicate mappingFunction) {
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
	public boolean computeIfAbsentNullable(int k, IntFunction<? extends Boolean> mappingFunction) {
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
	public boolean computeIfPresent(int k, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			Boolean newValue = (Boolean)remappingFunction.apply(k, this.value[pos]);
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
	public boolean compute(int k, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		Boolean newValue = (Boolean)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
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
	public boolean merge(int k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return v;
		} else {
			Boolean newValue = (Boolean)remappingFunction.apply(this.value[pos], v);
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

	public FastEntrySet int2BooleanEntrySet() {
		if (this.entries == null) {
			this.entries = new Int2BooleanOpenCustomHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public IntSet keySet() {
		if (this.keys == null) {
			this.keys = new Int2BooleanOpenCustomHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public BooleanCollection values() {
		if (this.values == null) {
			this.values = new AbstractBooleanCollection() {
				@Override
				public BooleanIterator iterator() {
					return Int2BooleanOpenCustomHashMap.this.new ValueIterator();
				}

				public int size() {
					return Int2BooleanOpenCustomHashMap.this.size;
				}

				@Override
				public boolean contains(boolean v) {
					return Int2BooleanOpenCustomHashMap.this.containsValue(v);
				}

				public void clear() {
					Int2BooleanOpenCustomHashMap.this.clear();
				}

				@Override
				public void forEach(BooleanConsumer consumer) {
					if (Int2BooleanOpenCustomHashMap.this.containsNullKey) {
						consumer.accept(Int2BooleanOpenCustomHashMap.this.value[Int2BooleanOpenCustomHashMap.this.n]);
					}

					int pos = Int2BooleanOpenCustomHashMap.this.n;

					while (pos-- != 0) {
						if (Int2BooleanOpenCustomHashMap.this.key[pos] != 0) {
							consumer.accept(Int2BooleanOpenCustomHashMap.this.value[pos]);
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
		boolean[] value = this.value;
		int mask = newN - 1;
		int[] newKey = new int[newN + 1];
		boolean[] newValue = new boolean[newN + 1];
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

	public Int2BooleanOpenCustomHashMap clone() {
		Int2BooleanOpenCustomHashMap c;
		try {
			c = (Int2BooleanOpenCustomHashMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (int[])this.key.clone();
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
			while (this.key[i] == 0) {
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
		int[] key = this.key;
		boolean[] value = this.value;
		Int2BooleanOpenCustomHashMap.MapIterator i = new Int2BooleanOpenCustomHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeInt(key[e]);
			s.writeBoolean(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		int[] key = this.key = new int[this.n + 1];
		boolean[] value = this.value = new boolean[this.n + 1];
		int i = this.size;

		while (i-- != 0) {
			int k = s.readInt();
			boolean v = s.readBoolean();
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

	private class EntryIterator extends Int2BooleanOpenCustomHashMap.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.ints.Int2BooleanMap.Entry> {
		private Int2BooleanOpenCustomHashMap.MapEntry entry;

		private EntryIterator() {
		}

		public Int2BooleanOpenCustomHashMap.MapEntry next() {
			return this.entry = Int2BooleanOpenCustomHashMap.this.new MapEntry(this.nextEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator extends Int2BooleanOpenCustomHashMap.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.ints.Int2BooleanMap.Entry> {
		private final Int2BooleanOpenCustomHashMap.MapEntry entry = Int2BooleanOpenCustomHashMap.this.new MapEntry();

		private FastEntryIterator() {
		}

		public Int2BooleanOpenCustomHashMap.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Int2BooleanOpenCustomHashMap.MapIterator implements IntIterator {
		public KeyIterator() {
		}

		@Override
		public int nextInt() {
			return Int2BooleanOpenCustomHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractIntSet {
		private KeySet() {
		}

		@Override
		public IntIterator iterator() {
			return Int2BooleanOpenCustomHashMap.this.new KeyIterator();
		}

		@Override
		public void forEach(java.util.function.IntConsumer consumer) {
			if (Int2BooleanOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(Int2BooleanOpenCustomHashMap.this.key[Int2BooleanOpenCustomHashMap.this.n]);
			}

			int pos = Int2BooleanOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				int k = Int2BooleanOpenCustomHashMap.this.key[pos];
				if (k != 0) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Int2BooleanOpenCustomHashMap.this.size;
		}

		@Override
		public boolean contains(int k) {
			return Int2BooleanOpenCustomHashMap.this.containsKey(k);
		}

		@Override
		public boolean remove(int k) {
			int oldSize = Int2BooleanOpenCustomHashMap.this.size;
			Int2BooleanOpenCustomHashMap.this.remove(k);
			return Int2BooleanOpenCustomHashMap.this.size != oldSize;
		}

		public void clear() {
			Int2BooleanOpenCustomHashMap.this.clear();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.ints.Int2BooleanMap.Entry, java.util.Map.Entry<Integer, Boolean> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		@Override
		public int getIntKey() {
			return Int2BooleanOpenCustomHashMap.this.key[this.index];
		}

		@Override
		public boolean getBooleanValue() {
			return Int2BooleanOpenCustomHashMap.this.value[this.index];
		}

		@Override
		public boolean setValue(boolean v) {
			boolean oldValue = Int2BooleanOpenCustomHashMap.this.value[this.index];
			Int2BooleanOpenCustomHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Integer getKey() {
			return Int2BooleanOpenCustomHashMap.this.key[this.index];
		}

		@Deprecated
		@Override
		public Boolean getValue() {
			return Int2BooleanOpenCustomHashMap.this.value[this.index];
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
				java.util.Map.Entry<Integer, Boolean> e = (java.util.Map.Entry<Integer, Boolean>)o;
				return Int2BooleanOpenCustomHashMap.this.strategy.equals(Int2BooleanOpenCustomHashMap.this.key[this.index], (Integer)e.getKey())
					&& Int2BooleanOpenCustomHashMap.this.value[this.index] == (Boolean)e.getValue();
			}
		}

		public int hashCode() {
			return Int2BooleanOpenCustomHashMap.this.strategy.hashCode(Int2BooleanOpenCustomHashMap.this.key[this.index])
				^ (Int2BooleanOpenCustomHashMap.this.value[this.index] ? 1231 : 1237);
		}

		public String toString() {
			return Int2BooleanOpenCustomHashMap.this.key[this.index] + "=>" + Int2BooleanOpenCustomHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.ints.Int2BooleanMap.Entry> implements FastEntrySet {
		private MapEntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.ints.Int2BooleanMap.Entry> iterator() {
			return Int2BooleanOpenCustomHashMap.this.new EntryIterator();
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.ints.Int2BooleanMap.Entry> fastIterator() {
			return Int2BooleanOpenCustomHashMap.this.new FastEntryIterator();
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Integer)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Boolean) {
					int k = (Integer)e.getKey();
					boolean v = (Boolean)e.getValue();
					if (Int2BooleanOpenCustomHashMap.this.strategy.equals(k, 0)) {
						return Int2BooleanOpenCustomHashMap.this.containsNullKey && Int2BooleanOpenCustomHashMap.this.value[Int2BooleanOpenCustomHashMap.this.n] == v;
					} else {
						int[] key = Int2BooleanOpenCustomHashMap.this.key;
						int curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(Int2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Int2BooleanOpenCustomHashMap.this.mask]) == 0) {
							return false;
						} else if (Int2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) {
							return Int2BooleanOpenCustomHashMap.this.value[pos] == v;
						} else {
							while ((curr = key[pos = pos + 1 & Int2BooleanOpenCustomHashMap.this.mask]) != 0) {
								if (Int2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) {
									return Int2BooleanOpenCustomHashMap.this.value[pos] == v;
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
					if (e.getValue() != null && e.getValue() instanceof Boolean) {
						int k = (Integer)e.getKey();
						boolean v = (Boolean)e.getValue();
						if (Int2BooleanOpenCustomHashMap.this.strategy.equals(k, 0)) {
							if (Int2BooleanOpenCustomHashMap.this.containsNullKey && Int2BooleanOpenCustomHashMap.this.value[Int2BooleanOpenCustomHashMap.this.n] == v) {
								Int2BooleanOpenCustomHashMap.this.removeNullEntry();
								return true;
							} else {
								return false;
							}
						} else {
							int[] key = Int2BooleanOpenCustomHashMap.this.key;
							int curr;
							int pos;
							if ((curr = key[pos = HashCommon.mix(Int2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Int2BooleanOpenCustomHashMap.this.mask]) == 0) {
								return false;
							} else if (Int2BooleanOpenCustomHashMap.this.strategy.equals(curr, k)) {
								if (Int2BooleanOpenCustomHashMap.this.value[pos] == v) {
									Int2BooleanOpenCustomHashMap.this.removeEntry(pos);
									return true;
								} else {
									return false;
								}
							} else {
								while ((curr = key[pos = pos + 1 & Int2BooleanOpenCustomHashMap.this.mask]) != 0) {
									if (Int2BooleanOpenCustomHashMap.this.strategy.equals(curr, k) && Int2BooleanOpenCustomHashMap.this.value[pos] == v) {
										Int2BooleanOpenCustomHashMap.this.removeEntry(pos);
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
			return Int2BooleanOpenCustomHashMap.this.size;
		}

		public void clear() {
			Int2BooleanOpenCustomHashMap.this.clear();
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.ints.Int2BooleanMap.Entry> consumer) {
			if (Int2BooleanOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(
					new BasicEntry(
						Int2BooleanOpenCustomHashMap.this.key[Int2BooleanOpenCustomHashMap.this.n], Int2BooleanOpenCustomHashMap.this.value[Int2BooleanOpenCustomHashMap.this.n]
					)
				);
			}

			int pos = Int2BooleanOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Int2BooleanOpenCustomHashMap.this.key[pos] != 0) {
					consumer.accept(new BasicEntry(Int2BooleanOpenCustomHashMap.this.key[pos], Int2BooleanOpenCustomHashMap.this.value[pos]));
				}
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.ints.Int2BooleanMap.Entry> consumer) {
			BasicEntry entry = new BasicEntry();
			if (Int2BooleanOpenCustomHashMap.this.containsNullKey) {
				entry.key = Int2BooleanOpenCustomHashMap.this.key[Int2BooleanOpenCustomHashMap.this.n];
				entry.value = Int2BooleanOpenCustomHashMap.this.value[Int2BooleanOpenCustomHashMap.this.n];
				consumer.accept(entry);
			}

			int pos = Int2BooleanOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Int2BooleanOpenCustomHashMap.this.key[pos] != 0) {
					entry.key = Int2BooleanOpenCustomHashMap.this.key[pos];
					entry.value = Int2BooleanOpenCustomHashMap.this.value[pos];
					consumer.accept(entry);
				}
			}
		}
	}

	private class MapIterator {
		int pos = Int2BooleanOpenCustomHashMap.this.n;
		int last = -1;
		int c = Int2BooleanOpenCustomHashMap.this.size;
		boolean mustReturnNullKey = Int2BooleanOpenCustomHashMap.this.containsNullKey;
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
					return this.last = Int2BooleanOpenCustomHashMap.this.n;
				} else {
					int[] key = Int2BooleanOpenCustomHashMap.this.key;

					while (--this.pos >= 0) {
						if (key[this.pos] != 0) {
							return this.last = this.pos;
						}
					}

					this.last = Integer.MIN_VALUE;
					int k = this.wrapped.getInt(-this.pos - 1);
					int p = HashCommon.mix(Int2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Int2BooleanOpenCustomHashMap.this.mask;

					while (!Int2BooleanOpenCustomHashMap.this.strategy.equals(k, key[p])) {
						p = p + 1 & Int2BooleanOpenCustomHashMap.this.mask;
					}

					return p;
				}
			}
		}

		private void shiftKeys(int pos) {
			int[] key = Int2BooleanOpenCustomHashMap.this.key;

			label38:
			while (true) {
				int last = pos;

				int curr;
				for (pos = pos + 1 & Int2BooleanOpenCustomHashMap.this.mask; (curr = key[pos]) != 0; pos = pos + 1 & Int2BooleanOpenCustomHashMap.this.mask) {
					int slot = HashCommon.mix(Int2BooleanOpenCustomHashMap.this.strategy.hashCode(curr)) & Int2BooleanOpenCustomHashMap.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new IntArrayList(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						Int2BooleanOpenCustomHashMap.this.value[last] = Int2BooleanOpenCustomHashMap.this.value[pos];
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
				if (this.last == Int2BooleanOpenCustomHashMap.this.n) {
					Int2BooleanOpenCustomHashMap.this.containsNullKey = false;
				} else {
					if (this.pos < 0) {
						Int2BooleanOpenCustomHashMap.this.remove(this.wrapped.getInt(-this.pos - 1));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				Int2BooleanOpenCustomHashMap.this.size--;
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

	private final class ValueIterator extends Int2BooleanOpenCustomHashMap.MapIterator implements BooleanIterator {
		public ValueIterator() {
		}

		@Override
		public boolean nextBoolean() {
			return Int2BooleanOpenCustomHashMap.this.value[this.nextEntry()];
		}
	}
}
