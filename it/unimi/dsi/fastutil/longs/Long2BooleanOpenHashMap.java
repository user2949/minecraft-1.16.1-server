package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2BooleanMap.BasicEntry;
import it.unimi.dsi.fastutil.longs.Long2BooleanMap.FastEntrySet;
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
import java.util.function.LongFunction;
import java.util.function.LongPredicate;

public class Long2BooleanOpenHashMap extends AbstractLong2BooleanMap implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient long[] key;
	protected transient boolean[] value;
	protected transient int mask;
	protected transient boolean containsNullKey;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;
	protected transient FastEntrySet entries;
	protected transient LongSet keys;
	protected transient BooleanCollection values;

	public Long2BooleanOpenHashMap(int expected, float f) {
		if (f <= 0.0F || f > 1.0F) {
			throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
		} else if (expected < 0) {
			throw new IllegalArgumentException("The expected number of elements must be nonnegative");
		} else {
			this.f = f;
			this.minN = this.n = HashCommon.arraySize(expected, f);
			this.mask = this.n - 1;
			this.maxFill = HashCommon.maxFill(this.n, f);
			this.key = new long[this.n + 1];
			this.value = new boolean[this.n + 1];
		}
	}

	public Long2BooleanOpenHashMap(int expected) {
		this(expected, 0.75F);
	}

	public Long2BooleanOpenHashMap() {
		this(16, 0.75F);
	}

	public Long2BooleanOpenHashMap(Map<? extends Long, ? extends Boolean> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Long2BooleanOpenHashMap(Map<? extends Long, ? extends Boolean> m) {
		this(m, 0.75F);
	}

	public Long2BooleanOpenHashMap(Long2BooleanMap m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Long2BooleanOpenHashMap(Long2BooleanMap m) {
		this(m, 0.75F);
	}

	public Long2BooleanOpenHashMap(long[] k, boolean[] v, float f) {
		this(k.length, f);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Long2BooleanOpenHashMap(long[] k, boolean[] v) {
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
	public void putAll(Map<? extends Long, ? extends Boolean> m) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(m.size());
		} else {
			this.tryCapacity((long)(this.size() + m.size()));
		}

		super.putAll(m);
	}

	private int find(long k) {
		if (k == 0L) {
			return this.containsNullKey ? this.n : -(this.n + 1);
		} else {
			long[] key = this.key;
			long curr;
			int pos;
			if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) {
				return -(pos + 1);
			} else if (k == curr) {
				return pos;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (k == curr) {
						return pos;
					}
				}

				return -(pos + 1);
			}
		}
	}

	private void insert(int pos, long k, boolean v) {
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
	public boolean put(long k, boolean v) {
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
		long[] key = this.key;

		label30:
		while (true) {
			int last = pos;

			long curr;
			for (pos = pos + 1 & this.mask; (curr = key[pos]) != 0L; pos = pos + 1 & this.mask) {
				int slot = (int)HashCommon.mix(curr) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					key[last] = curr;
					this.value[last] = this.value[pos];
					continue label30;
				}
			}

			key[last] = 0L;
			return;
		}
	}

	@Override
	public boolean remove(long k) {
		if (k == 0L) {
			return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
		} else {
			long[] key = this.key;
			long curr;
			int pos;
			if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) {
				return this.defRetValue;
			} else if (k == curr) {
				return this.removeEntry(pos);
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (k == curr) {
						return this.removeEntry(pos);
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public boolean get(long k) {
		if (k == 0L) {
			return this.containsNullKey ? this.value[this.n] : this.defRetValue;
		} else {
			long[] key = this.key;
			long curr;
			int pos;
			if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) {
				return this.defRetValue;
			} else if (k == curr) {
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (k == curr) {
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public boolean containsKey(long k) {
		if (k == 0L) {
			return this.containsNullKey;
		} else {
			long[] key = this.key;
			long curr;
			int pos;
			if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) {
				return false;
			} else if (k == curr) {
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (k == curr) {
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
		long[] key = this.key;
		if (this.containsNullKey && value[this.n] == v) {
			return true;
		} else {
			int i = this.n;

			while (i-- != 0) {
				if (key[i] != 0L && value[i] == v) {
					return true;
				}
			}

			return false;
		}
	}

	@Override
	public boolean getOrDefault(long k, boolean defaultValue) {
		if (k == 0L) {
			return this.containsNullKey ? this.value[this.n] : defaultValue;
		} else {
			long[] key = this.key;
			long curr;
			int pos;
			if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) {
				return defaultValue;
			} else if (k == curr) {
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (k == curr) {
						return this.value[pos];
					}
				}

				return defaultValue;
			}
		}
	}

	@Override
	public boolean putIfAbsent(long k, boolean v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(long k, boolean v) {
		if (k == 0L) {
			if (this.containsNullKey && v == this.value[this.n]) {
				this.removeNullEntry();
				return true;
			} else {
				return false;
			}
		} else {
			long[] key = this.key;
			long curr;
			int pos;
			if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) {
				return false;
			} else if (k == curr && v == this.value[pos]) {
				this.removeEntry(pos);
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
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
	public boolean replace(long k, boolean oldValue, boolean v) {
		int pos = this.find(k);
		if (pos >= 0 && oldValue == this.value[pos]) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean replace(long k, boolean v) {
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
	public boolean computeIfAbsent(long k, LongPredicate mappingFunction) {
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
	public boolean computeIfAbsentNullable(long k, LongFunction<? extends Boolean> mappingFunction) {
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
	public boolean computeIfPresent(long k, BiFunction<? super Long, ? super Boolean, ? extends Boolean> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			Boolean newValue = (Boolean)remappingFunction.apply(k, this.value[pos]);
			if (newValue == null) {
				if (k == 0L) {
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
	public boolean compute(long k, BiFunction<? super Long, ? super Boolean, ? extends Boolean> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		Boolean newValue = (Boolean)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
		if (newValue == null) {
			if (pos >= 0) {
				if (k == 0L) {
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
	public boolean merge(long k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return v;
		} else {
			Boolean newValue = (Boolean)remappingFunction.apply(this.value[pos], v);
			if (newValue == null) {
				if (k == 0L) {
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
			Arrays.fill(this.key, 0L);
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

	public FastEntrySet long2BooleanEntrySet() {
		if (this.entries == null) {
			this.entries = new Long2BooleanOpenHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public LongSet keySet() {
		if (this.keys == null) {
			this.keys = new Long2BooleanOpenHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public BooleanCollection values() {
		if (this.values == null) {
			this.values = new AbstractBooleanCollection() {
				@Override
				public BooleanIterator iterator() {
					return Long2BooleanOpenHashMap.this.new ValueIterator();
				}

				public int size() {
					return Long2BooleanOpenHashMap.this.size;
				}

				@Override
				public boolean contains(boolean v) {
					return Long2BooleanOpenHashMap.this.containsValue(v);
				}

				public void clear() {
					Long2BooleanOpenHashMap.this.clear();
				}

				@Override
				public void forEach(BooleanConsumer consumer) {
					if (Long2BooleanOpenHashMap.this.containsNullKey) {
						consumer.accept(Long2BooleanOpenHashMap.this.value[Long2BooleanOpenHashMap.this.n]);
					}

					int pos = Long2BooleanOpenHashMap.this.n;

					while (pos-- != 0) {
						if (Long2BooleanOpenHashMap.this.key[pos] != 0L) {
							consumer.accept(Long2BooleanOpenHashMap.this.value[pos]);
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
		long[] key = this.key;
		boolean[] value = this.value;
		int mask = newN - 1;
		long[] newKey = new long[newN + 1];
		boolean[] newValue = new boolean[newN + 1];
		int i = this.n;
		int j = this.realSize();

		while (j-- != 0) {
			while (key[--i] == 0L) {
			}

			int pos;
			if (newKey[pos = (int)HashCommon.mix(key[i]) & mask] != 0L) {
				while (newKey[pos = pos + 1 & mask] != 0L) {
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

	public Long2BooleanOpenHashMap clone() {
		Long2BooleanOpenHashMap c;
		try {
			c = (Long2BooleanOpenHashMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (long[])this.key.clone();
		c.value = (boolean[])this.value.clone();
		return c;
	}

	@Override
	public int hashCode() {
		int h = 0;
		int j = this.realSize();
		int i = 0;

		for (int t = 0; j-- != 0; i++) {
			while (this.key[i] == 0L) {
				i++;
			}

			t = HashCommon.long2int(this.key[i]);
			t ^= this.value[i] ? 1231 : 1237;
			h += t;
		}

		if (this.containsNullKey) {
			h += this.value[this.n] ? 1231 : 1237;
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		long[] key = this.key;
		boolean[] value = this.value;
		Long2BooleanOpenHashMap.MapIterator i = new Long2BooleanOpenHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeLong(key[e]);
			s.writeBoolean(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		long[] key = this.key = new long[this.n + 1];
		boolean[] value = this.value = new boolean[this.n + 1];
		int i = this.size;

		while (i-- != 0) {
			long k = s.readLong();
			boolean v = s.readBoolean();
			int pos;
			if (k == 0L) {
				pos = this.n;
				this.containsNullKey = true;
			} else {
				pos = (int)HashCommon.mix(k) & this.mask;

				while (key[pos] != 0L) {
					pos = pos + 1 & this.mask;
				}
			}

			key[pos] = k;
			value[pos] = v;
		}
	}

	private void checkTable() {
	}

	private class EntryIterator extends Long2BooleanOpenHashMap.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> {
		private Long2BooleanOpenHashMap.MapEntry entry;

		private EntryIterator() {
		}

		public Long2BooleanOpenHashMap.MapEntry next() {
			return this.entry = Long2BooleanOpenHashMap.this.new MapEntry(this.nextEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator extends Long2BooleanOpenHashMap.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> {
		private final Long2BooleanOpenHashMap.MapEntry entry = Long2BooleanOpenHashMap.this.new MapEntry();

		private FastEntryIterator() {
		}

		public Long2BooleanOpenHashMap.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Long2BooleanOpenHashMap.MapIterator implements LongIterator {
		public KeyIterator() {
		}

		@Override
		public long nextLong() {
			return Long2BooleanOpenHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractLongSet {
		private KeySet() {
		}

		@Override
		public LongIterator iterator() {
			return Long2BooleanOpenHashMap.this.new KeyIterator();
		}

		@Override
		public void forEach(java.util.function.LongConsumer consumer) {
			if (Long2BooleanOpenHashMap.this.containsNullKey) {
				consumer.accept(Long2BooleanOpenHashMap.this.key[Long2BooleanOpenHashMap.this.n]);
			}

			int pos = Long2BooleanOpenHashMap.this.n;

			while (pos-- != 0) {
				long k = Long2BooleanOpenHashMap.this.key[pos];
				if (k != 0L) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Long2BooleanOpenHashMap.this.size;
		}

		@Override
		public boolean contains(long k) {
			return Long2BooleanOpenHashMap.this.containsKey(k);
		}

		@Override
		public boolean remove(long k) {
			int oldSize = Long2BooleanOpenHashMap.this.size;
			Long2BooleanOpenHashMap.this.remove(k);
			return Long2BooleanOpenHashMap.this.size != oldSize;
		}

		public void clear() {
			Long2BooleanOpenHashMap.this.clear();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry, java.util.Map.Entry<Long, Boolean> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		@Override
		public long getLongKey() {
			return Long2BooleanOpenHashMap.this.key[this.index];
		}

		@Override
		public boolean getBooleanValue() {
			return Long2BooleanOpenHashMap.this.value[this.index];
		}

		@Override
		public boolean setValue(boolean v) {
			boolean oldValue = Long2BooleanOpenHashMap.this.value[this.index];
			Long2BooleanOpenHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Long getKey() {
			return Long2BooleanOpenHashMap.this.key[this.index];
		}

		@Deprecated
		@Override
		public Boolean getValue() {
			return Long2BooleanOpenHashMap.this.value[this.index];
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
				java.util.Map.Entry<Long, Boolean> e = (java.util.Map.Entry<Long, Boolean>)o;
				return Long2BooleanOpenHashMap.this.key[this.index] == (Long)e.getKey() && Long2BooleanOpenHashMap.this.value[this.index] == (Boolean)e.getValue();
			}
		}

		public int hashCode() {
			return HashCommon.long2int(Long2BooleanOpenHashMap.this.key[this.index]) ^ (Long2BooleanOpenHashMap.this.value[this.index] ? 1231 : 1237);
		}

		public String toString() {
			return Long2BooleanOpenHashMap.this.key[this.index] + "=>" + Long2BooleanOpenHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> implements FastEntrySet {
		private MapEntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> iterator() {
			return Long2BooleanOpenHashMap.this.new EntryIterator();
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> fastIterator() {
			return Long2BooleanOpenHashMap.this.new FastEntryIterator();
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Long)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Boolean) {
					long k = (Long)e.getKey();
					boolean v = (Boolean)e.getValue();
					if (k == 0L) {
						return Long2BooleanOpenHashMap.this.containsNullKey && Long2BooleanOpenHashMap.this.value[Long2BooleanOpenHashMap.this.n] == v;
					} else {
						long[] key = Long2BooleanOpenHashMap.this.key;
						long curr;
						int pos;
						if ((curr = key[pos = (int)HashCommon.mix(k) & Long2BooleanOpenHashMap.this.mask]) == 0L) {
							return false;
						} else if (k == curr) {
							return Long2BooleanOpenHashMap.this.value[pos] == v;
						} else {
							while ((curr = key[pos = pos + 1 & Long2BooleanOpenHashMap.this.mask]) != 0L) {
								if (k == curr) {
									return Long2BooleanOpenHashMap.this.value[pos] == v;
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
				if (e.getKey() != null && e.getKey() instanceof Long) {
					if (e.getValue() != null && e.getValue() instanceof Boolean) {
						long k = (Long)e.getKey();
						boolean v = (Boolean)e.getValue();
						if (k == 0L) {
							if (Long2BooleanOpenHashMap.this.containsNullKey && Long2BooleanOpenHashMap.this.value[Long2BooleanOpenHashMap.this.n] == v) {
								Long2BooleanOpenHashMap.this.removeNullEntry();
								return true;
							} else {
								return false;
							}
						} else {
							long[] key = Long2BooleanOpenHashMap.this.key;
							long curr;
							int pos;
							if ((curr = key[pos = (int)HashCommon.mix(k) & Long2BooleanOpenHashMap.this.mask]) == 0L) {
								return false;
							} else if (curr == k) {
								if (Long2BooleanOpenHashMap.this.value[pos] == v) {
									Long2BooleanOpenHashMap.this.removeEntry(pos);
									return true;
								} else {
									return false;
								}
							} else {
								while ((curr = key[pos = pos + 1 & Long2BooleanOpenHashMap.this.mask]) != 0L) {
									if (curr == k && Long2BooleanOpenHashMap.this.value[pos] == v) {
										Long2BooleanOpenHashMap.this.removeEntry(pos);
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
			return Long2BooleanOpenHashMap.this.size;
		}

		public void clear() {
			Long2BooleanOpenHashMap.this.clear();
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> consumer) {
			if (Long2BooleanOpenHashMap.this.containsNullKey) {
				consumer.accept(
					new BasicEntry(Long2BooleanOpenHashMap.this.key[Long2BooleanOpenHashMap.this.n], Long2BooleanOpenHashMap.this.value[Long2BooleanOpenHashMap.this.n])
				);
			}

			int pos = Long2BooleanOpenHashMap.this.n;

			while (pos-- != 0) {
				if (Long2BooleanOpenHashMap.this.key[pos] != 0L) {
					consumer.accept(new BasicEntry(Long2BooleanOpenHashMap.this.key[pos], Long2BooleanOpenHashMap.this.value[pos]));
				}
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> consumer) {
			BasicEntry entry = new BasicEntry();
			if (Long2BooleanOpenHashMap.this.containsNullKey) {
				entry.key = Long2BooleanOpenHashMap.this.key[Long2BooleanOpenHashMap.this.n];
				entry.value = Long2BooleanOpenHashMap.this.value[Long2BooleanOpenHashMap.this.n];
				consumer.accept(entry);
			}

			int pos = Long2BooleanOpenHashMap.this.n;

			while (pos-- != 0) {
				if (Long2BooleanOpenHashMap.this.key[pos] != 0L) {
					entry.key = Long2BooleanOpenHashMap.this.key[pos];
					entry.value = Long2BooleanOpenHashMap.this.value[pos];
					consumer.accept(entry);
				}
			}
		}
	}

	private class MapIterator {
		int pos = Long2BooleanOpenHashMap.this.n;
		int last = -1;
		int c = Long2BooleanOpenHashMap.this.size;
		boolean mustReturnNullKey = Long2BooleanOpenHashMap.this.containsNullKey;
		LongArrayList wrapped;

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
					return this.last = Long2BooleanOpenHashMap.this.n;
				} else {
					long[] key = Long2BooleanOpenHashMap.this.key;

					while (--this.pos >= 0) {
						if (key[this.pos] != 0L) {
							return this.last = this.pos;
						}
					}

					this.last = Integer.MIN_VALUE;
					long k = this.wrapped.getLong(-this.pos - 1);
					int p = (int)HashCommon.mix(k) & Long2BooleanOpenHashMap.this.mask;

					while (k != key[p]) {
						p = p + 1 & Long2BooleanOpenHashMap.this.mask;
					}

					return p;
				}
			}
		}

		private void shiftKeys(int pos) {
			long[] key = Long2BooleanOpenHashMap.this.key;

			label38:
			while (true) {
				int last = pos;

				long curr;
				for (pos = pos + 1 & Long2BooleanOpenHashMap.this.mask; (curr = key[pos]) != 0L; pos = pos + 1 & Long2BooleanOpenHashMap.this.mask) {
					int slot = (int)HashCommon.mix(curr) & Long2BooleanOpenHashMap.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new LongArrayList(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						Long2BooleanOpenHashMap.this.value[last] = Long2BooleanOpenHashMap.this.value[pos];
						continue label38;
					}
				}

				key[last] = 0L;
				return;
			}
		}

		public void remove() {
			if (this.last == -1) {
				throw new IllegalStateException();
			} else {
				if (this.last == Long2BooleanOpenHashMap.this.n) {
					Long2BooleanOpenHashMap.this.containsNullKey = false;
				} else {
					if (this.pos < 0) {
						Long2BooleanOpenHashMap.this.remove(this.wrapped.getLong(-this.pos - 1));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				Long2BooleanOpenHashMap.this.size--;
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

	private final class ValueIterator extends Long2BooleanOpenHashMap.MapIterator implements BooleanIterator {
		public ValueIterator() {
		}

		@Override
		public boolean nextBoolean() {
			return Long2BooleanOpenHashMap.this.value[this.nextEntry()];
		}
	}
}
