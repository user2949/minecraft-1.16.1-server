package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.ints.AbstractInt2LongMap.BasicEntry;
import it.unimi.dsi.fastutil.ints.Int2LongMap.FastEntrySet;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
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
import java.util.function.IntToLongFunction;
import java.util.function.LongConsumer;

public class Int2LongOpenHashMap extends AbstractInt2LongMap implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient int[] key;
	protected transient long[] value;
	protected transient int mask;
	protected transient boolean containsNullKey;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;
	protected transient FastEntrySet entries;
	protected transient IntSet keys;
	protected transient LongCollection values;

	public Int2LongOpenHashMap(int expected, float f) {
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
			this.value = new long[this.n + 1];
		}
	}

	public Int2LongOpenHashMap(int expected) {
		this(expected, 0.75F);
	}

	public Int2LongOpenHashMap() {
		this(16, 0.75F);
	}

	public Int2LongOpenHashMap(Map<? extends Integer, ? extends Long> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Int2LongOpenHashMap(Map<? extends Integer, ? extends Long> m) {
		this(m, 0.75F);
	}

	public Int2LongOpenHashMap(Int2LongMap m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Int2LongOpenHashMap(Int2LongMap m) {
		this(m, 0.75F);
	}

	public Int2LongOpenHashMap(int[] k, long[] v, float f) {
		this(k.length, f);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Int2LongOpenHashMap(int[] k, long[] v) {
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

	private long removeEntry(int pos) {
		long oldValue = this.value[pos];
		this.size--;
		this.shiftKeys(pos);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	private long removeNullEntry() {
		this.containsNullKey = false;
		long oldValue = this.value[this.n];
		this.size--;
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Long> m) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(m.size());
		} else {
			this.tryCapacity((long)(this.size() + m.size()));
		}

		super.putAll(m);
	}

	private int find(int k) {
		if (k == 0) {
			return this.containsNullKey ? this.n : -(this.n + 1);
		} else {
			int[] key = this.key;
			int curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
				return -(pos + 1);
			} else if (k == curr) {
				return pos;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (k == curr) {
						return pos;
					}
				}

				return -(pos + 1);
			}
		}
	}

	private void insert(int pos, int k, long v) {
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
	public long put(int k, long v) {
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		} else {
			long oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	private long addToValue(int pos, long incr) {
		long oldValue = this.value[pos];
		this.value[pos] = oldValue + incr;
		return oldValue;
	}

	public long addTo(int k, long incr) {
		int pos;
		if (k == 0) {
			if (this.containsNullKey) {
				return this.addToValue(this.n, incr);
			}

			pos = this.n;
			this.containsNullKey = true;
		} else {
			int[] key = this.key;
			int curr;
			if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
				if (curr == k) {
					return this.addToValue(pos, incr);
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (curr == k) {
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
				int slot = HashCommon.mix(curr) & this.mask;
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
	public long remove(int k) {
		if (k == 0) {
			return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
		} else {
			int[] key = this.key;
			int curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
				return this.defRetValue;
			} else if (k == curr) {
				return this.removeEntry(pos);
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (k == curr) {
						return this.removeEntry(pos);
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public long get(int k) {
		if (k == 0) {
			return this.containsNullKey ? this.value[this.n] : this.defRetValue;
		} else {
			int[] key = this.key;
			int curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
				return this.defRetValue;
			} else if (k == curr) {
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (k == curr) {
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public boolean containsKey(int k) {
		if (k == 0) {
			return this.containsNullKey;
		} else {
			int[] key = this.key;
			int curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
				return false;
			} else if (k == curr) {
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (k == curr) {
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean containsValue(long v) {
		long[] value = this.value;
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
	public long getOrDefault(int k, long defaultValue) {
		if (k == 0) {
			return this.containsNullKey ? this.value[this.n] : defaultValue;
		} else {
			int[] key = this.key;
			int curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
				return defaultValue;
			} else if (k == curr) {
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (k == curr) {
						return this.value[pos];
					}
				}

				return defaultValue;
			}
		}
	}

	@Override
	public long putIfAbsent(int k, long v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(int k, long v) {
		if (k == 0) {
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
			if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
				return false;
			} else if (k == curr && v == this.value[pos]) {
				this.removeEntry(pos);
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (k == curr && v == this.value[pos]) {
						this.removeEntry(pos);
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean replace(int k, long oldValue, long v) {
		int pos = this.find(k);
		if (pos >= 0 && oldValue == this.value[pos]) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public long replace(int k, long v) {
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			long oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	@Override
	public long computeIfAbsent(int k, IntToLongFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			long newValue = mappingFunction.applyAsLong(k);
			this.insert(-pos - 1, k, newValue);
			return newValue;
		}
	}

	@Override
	public long computeIfAbsentNullable(int k, IntFunction<? extends Long> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			Long newValue = (Long)mappingFunction.apply(k);
			if (newValue == null) {
				return this.defRetValue;
			} else {
				long v = newValue;
				this.insert(-pos - 1, k, v);
				return v;
			}
		}
	}

	@Override
	public long computeIfPresent(int k, BiFunction<? super Integer, ? super Long, ? extends Long> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			Long newValue = (Long)remappingFunction.apply(k, this.value[pos]);
			if (newValue == null) {
				if (k == 0) {
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
	public long compute(int k, BiFunction<? super Integer, ? super Long, ? extends Long> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		Long newValue = (Long)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
		if (newValue == null) {
			if (pos >= 0) {
				if (k == 0) {
					this.removeNullEntry();
				} else {
					this.removeEntry(pos);
				}
			}

			return this.defRetValue;
		} else {
			long newVal = newValue;
			if (pos < 0) {
				this.insert(-pos - 1, k, newVal);
				return newVal;
			} else {
				return this.value[pos] = newVal;
			}
		}
	}

	@Override
	public long merge(int k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return v;
		} else {
			Long newValue = (Long)remappingFunction.apply(this.value[pos], v);
			if (newValue == null) {
				if (k == 0) {
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

	public FastEntrySet int2LongEntrySet() {
		if (this.entries == null) {
			this.entries = new Int2LongOpenHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public IntSet keySet() {
		if (this.keys == null) {
			this.keys = new Int2LongOpenHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public LongCollection values() {
		if (this.values == null) {
			this.values = new AbstractLongCollection() {
				@Override
				public LongIterator iterator() {
					return Int2LongOpenHashMap.this.new ValueIterator();
				}

				public int size() {
					return Int2LongOpenHashMap.this.size;
				}

				@Override
				public boolean contains(long v) {
					return Int2LongOpenHashMap.this.containsValue(v);
				}

				public void clear() {
					Int2LongOpenHashMap.this.clear();
				}

				@Override
				public void forEach(LongConsumer consumer) {
					if (Int2LongOpenHashMap.this.containsNullKey) {
						consumer.accept(Int2LongOpenHashMap.this.value[Int2LongOpenHashMap.this.n]);
					}

					int pos = Int2LongOpenHashMap.this.n;

					while (pos-- != 0) {
						if (Int2LongOpenHashMap.this.key[pos] != 0) {
							consumer.accept(Int2LongOpenHashMap.this.value[pos]);
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
		long[] value = this.value;
		int mask = newN - 1;
		int[] newKey = new int[newN + 1];
		long[] newValue = new long[newN + 1];
		int i = this.n;
		int j = this.realSize();

		while (j-- != 0) {
			while (key[--i] == 0) {
			}

			int pos;
			if (newKey[pos = HashCommon.mix(key[i]) & mask] != 0) {
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

	public Int2LongOpenHashMap clone() {
		Int2LongOpenHashMap c;
		try {
			c = (Int2LongOpenHashMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (int[])this.key.clone();
		c.value = (long[])this.value.clone();
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

			t = this.key[i];
			t ^= HashCommon.long2int(this.value[i]);
			h += t;
		}

		if (this.containsNullKey) {
			h += HashCommon.long2int(this.value[this.n]);
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		int[] key = this.key;
		long[] value = this.value;
		Int2LongOpenHashMap.MapIterator i = new Int2LongOpenHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeInt(key[e]);
			s.writeLong(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		int[] key = this.key = new int[this.n + 1];
		long[] value = this.value = new long[this.n + 1];
		int i = this.size;

		while (i-- != 0) {
			int k = s.readInt();
			long v = s.readLong();
			int pos;
			if (k == 0) {
				pos = this.n;
				this.containsNullKey = true;
			} else {
				pos = HashCommon.mix(k) & this.mask;

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

	private class EntryIterator extends Int2LongOpenHashMap.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.ints.Int2LongMap.Entry> {
		private Int2LongOpenHashMap.MapEntry entry;

		private EntryIterator() {
		}

		public Int2LongOpenHashMap.MapEntry next() {
			return this.entry = Int2LongOpenHashMap.this.new MapEntry(this.nextEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator extends Int2LongOpenHashMap.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.ints.Int2LongMap.Entry> {
		private final Int2LongOpenHashMap.MapEntry entry = Int2LongOpenHashMap.this.new MapEntry();

		private FastEntryIterator() {
		}

		public Int2LongOpenHashMap.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Int2LongOpenHashMap.MapIterator implements IntIterator {
		public KeyIterator() {
		}

		@Override
		public int nextInt() {
			return Int2LongOpenHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractIntSet {
		private KeySet() {
		}

		@Override
		public IntIterator iterator() {
			return Int2LongOpenHashMap.this.new KeyIterator();
		}

		@Override
		public void forEach(java.util.function.IntConsumer consumer) {
			if (Int2LongOpenHashMap.this.containsNullKey) {
				consumer.accept(Int2LongOpenHashMap.this.key[Int2LongOpenHashMap.this.n]);
			}

			int pos = Int2LongOpenHashMap.this.n;

			while (pos-- != 0) {
				int k = Int2LongOpenHashMap.this.key[pos];
				if (k != 0) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Int2LongOpenHashMap.this.size;
		}

		@Override
		public boolean contains(int k) {
			return Int2LongOpenHashMap.this.containsKey(k);
		}

		@Override
		public boolean remove(int k) {
			int oldSize = Int2LongOpenHashMap.this.size;
			Int2LongOpenHashMap.this.remove(k);
			return Int2LongOpenHashMap.this.size != oldSize;
		}

		public void clear() {
			Int2LongOpenHashMap.this.clear();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.ints.Int2LongMap.Entry, java.util.Map.Entry<Integer, Long> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		@Override
		public int getIntKey() {
			return Int2LongOpenHashMap.this.key[this.index];
		}

		@Override
		public long getLongValue() {
			return Int2LongOpenHashMap.this.value[this.index];
		}

		@Override
		public long setValue(long v) {
			long oldValue = Int2LongOpenHashMap.this.value[this.index];
			Int2LongOpenHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Integer getKey() {
			return Int2LongOpenHashMap.this.key[this.index];
		}

		@Deprecated
		@Override
		public Long getValue() {
			return Int2LongOpenHashMap.this.value[this.index];
		}

		@Deprecated
		@Override
		public Long setValue(Long v) {
			return this.setValue(v.longValue());
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<Integer, Long> e = (java.util.Map.Entry<Integer, Long>)o;
				return Int2LongOpenHashMap.this.key[this.index] == (Integer)e.getKey() && Int2LongOpenHashMap.this.value[this.index] == (Long)e.getValue();
			}
		}

		public int hashCode() {
			return Int2LongOpenHashMap.this.key[this.index] ^ HashCommon.long2int(Int2LongOpenHashMap.this.value[this.index]);
		}

		public String toString() {
			return Int2LongOpenHashMap.this.key[this.index] + "=>" + Int2LongOpenHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.ints.Int2LongMap.Entry> implements FastEntrySet {
		private MapEntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.ints.Int2LongMap.Entry> iterator() {
			return Int2LongOpenHashMap.this.new EntryIterator();
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.ints.Int2LongMap.Entry> fastIterator() {
			return Int2LongOpenHashMap.this.new FastEntryIterator();
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Integer)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Long) {
					int k = (Integer)e.getKey();
					long v = (Long)e.getValue();
					if (k == 0) {
						return Int2LongOpenHashMap.this.containsNullKey && Int2LongOpenHashMap.this.value[Int2LongOpenHashMap.this.n] == v;
					} else {
						int[] key = Int2LongOpenHashMap.this.key;
						int curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(k) & Int2LongOpenHashMap.this.mask]) == 0) {
							return false;
						} else if (k == curr) {
							return Int2LongOpenHashMap.this.value[pos] == v;
						} else {
							while ((curr = key[pos = pos + 1 & Int2LongOpenHashMap.this.mask]) != 0) {
								if (k == curr) {
									return Int2LongOpenHashMap.this.value[pos] == v;
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
					if (e.getValue() != null && e.getValue() instanceof Long) {
						int k = (Integer)e.getKey();
						long v = (Long)e.getValue();
						if (k == 0) {
							if (Int2LongOpenHashMap.this.containsNullKey && Int2LongOpenHashMap.this.value[Int2LongOpenHashMap.this.n] == v) {
								Int2LongOpenHashMap.this.removeNullEntry();
								return true;
							} else {
								return false;
							}
						} else {
							int[] key = Int2LongOpenHashMap.this.key;
							int curr;
							int pos;
							if ((curr = key[pos = HashCommon.mix(k) & Int2LongOpenHashMap.this.mask]) == 0) {
								return false;
							} else if (curr == k) {
								if (Int2LongOpenHashMap.this.value[pos] == v) {
									Int2LongOpenHashMap.this.removeEntry(pos);
									return true;
								} else {
									return false;
								}
							} else {
								while ((curr = key[pos = pos + 1 & Int2LongOpenHashMap.this.mask]) != 0) {
									if (curr == k && Int2LongOpenHashMap.this.value[pos] == v) {
										Int2LongOpenHashMap.this.removeEntry(pos);
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
			return Int2LongOpenHashMap.this.size;
		}

		public void clear() {
			Int2LongOpenHashMap.this.clear();
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.ints.Int2LongMap.Entry> consumer) {
			if (Int2LongOpenHashMap.this.containsNullKey) {
				consumer.accept(new BasicEntry(Int2LongOpenHashMap.this.key[Int2LongOpenHashMap.this.n], Int2LongOpenHashMap.this.value[Int2LongOpenHashMap.this.n]));
			}

			int pos = Int2LongOpenHashMap.this.n;

			while (pos-- != 0) {
				if (Int2LongOpenHashMap.this.key[pos] != 0) {
					consumer.accept(new BasicEntry(Int2LongOpenHashMap.this.key[pos], Int2LongOpenHashMap.this.value[pos]));
				}
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.ints.Int2LongMap.Entry> consumer) {
			BasicEntry entry = new BasicEntry();
			if (Int2LongOpenHashMap.this.containsNullKey) {
				entry.key = Int2LongOpenHashMap.this.key[Int2LongOpenHashMap.this.n];
				entry.value = Int2LongOpenHashMap.this.value[Int2LongOpenHashMap.this.n];
				consumer.accept(entry);
			}

			int pos = Int2LongOpenHashMap.this.n;

			while (pos-- != 0) {
				if (Int2LongOpenHashMap.this.key[pos] != 0) {
					entry.key = Int2LongOpenHashMap.this.key[pos];
					entry.value = Int2LongOpenHashMap.this.value[pos];
					consumer.accept(entry);
				}
			}
		}
	}

	private class MapIterator {
		int pos = Int2LongOpenHashMap.this.n;
		int last = -1;
		int c = Int2LongOpenHashMap.this.size;
		boolean mustReturnNullKey = Int2LongOpenHashMap.this.containsNullKey;
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
					return this.last = Int2LongOpenHashMap.this.n;
				} else {
					int[] key = Int2LongOpenHashMap.this.key;

					while (--this.pos >= 0) {
						if (key[this.pos] != 0) {
							return this.last = this.pos;
						}
					}

					this.last = Integer.MIN_VALUE;
					int k = this.wrapped.getInt(-this.pos - 1);
					int p = HashCommon.mix(k) & Int2LongOpenHashMap.this.mask;

					while (k != key[p]) {
						p = p + 1 & Int2LongOpenHashMap.this.mask;
					}

					return p;
				}
			}
		}

		private void shiftKeys(int pos) {
			int[] key = Int2LongOpenHashMap.this.key;

			label38:
			while (true) {
				int last = pos;

				int curr;
				for (pos = pos + 1 & Int2LongOpenHashMap.this.mask; (curr = key[pos]) != 0; pos = pos + 1 & Int2LongOpenHashMap.this.mask) {
					int slot = HashCommon.mix(curr) & Int2LongOpenHashMap.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new IntArrayList(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						Int2LongOpenHashMap.this.value[last] = Int2LongOpenHashMap.this.value[pos];
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
				if (this.last == Int2LongOpenHashMap.this.n) {
					Int2LongOpenHashMap.this.containsNullKey = false;
				} else {
					if (this.pos < 0) {
						Int2LongOpenHashMap.this.remove(this.wrapped.getInt(-this.pos - 1));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				Int2LongOpenHashMap.this.size--;
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

	private final class ValueIterator extends Int2LongOpenHashMap.MapIterator implements LongIterator {
		public ValueIterator() {
		}

		@Override
		public long nextLong() {
			return Int2LongOpenHashMap.this.value[this.nextEntry()];
		}
	}
}
