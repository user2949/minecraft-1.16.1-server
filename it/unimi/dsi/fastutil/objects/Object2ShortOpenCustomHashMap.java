package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.Hash.Strategy;
import it.unimi.dsi.fastutil.objects.AbstractObject2ShortMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2ShortMap.FastEntrySet;
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
import java.util.function.IntConsumer;
import java.util.function.ToIntFunction;

public class Object2ShortOpenCustomHashMap<K> extends AbstractObject2ShortMap<K> implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient K[] key;
	protected transient short[] value;
	protected transient int mask;
	protected transient boolean containsNullKey;
	protected Strategy<K> strategy;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;
	protected transient FastEntrySet<K> entries;
	protected transient ObjectSet<K> keys;
	protected transient ShortCollection values;

	public Object2ShortOpenCustomHashMap(int expected, float f, Strategy<K> strategy) {
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
			this.key = (K[])(new Object[this.n + 1]);
			this.value = new short[this.n + 1];
		}
	}

	public Object2ShortOpenCustomHashMap(int expected, Strategy<K> strategy) {
		this(expected, 0.75F, strategy);
	}

	public Object2ShortOpenCustomHashMap(Strategy<K> strategy) {
		this(16, 0.75F, strategy);
	}

	public Object2ShortOpenCustomHashMap(Map<? extends K, ? extends Short> m, float f, Strategy<K> strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Object2ShortOpenCustomHashMap(Map<? extends K, ? extends Short> m, Strategy<K> strategy) {
		this(m, 0.75F, strategy);
	}

	public Object2ShortOpenCustomHashMap(Object2ShortMap<K> m, float f, Strategy<K> strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Object2ShortOpenCustomHashMap(Object2ShortMap<K> m, Strategy<K> strategy) {
		this(m, 0.75F, strategy);
	}

	public Object2ShortOpenCustomHashMap(K[] k, short[] v, float f, Strategy<K> strategy) {
		this(k.length, f, strategy);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Object2ShortOpenCustomHashMap(K[] k, short[] v, Strategy<K> strategy) {
		this(k, v, 0.75F, strategy);
	}

	public Strategy<K> strategy() {
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
		this.key[this.n] = null;
		short oldValue = this.value[this.n];
		this.size--;
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	@Override
	public void putAll(Map<? extends K, ? extends Short> m) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(m.size());
		} else {
			this.tryCapacity((long)(this.size() + m.size()));
		}

		super.putAll(m);
	}

	private int find(K k) {
		if (this.strategy.equals(k, null)) {
			return this.containsNullKey ? this.n : -(this.n + 1);
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) {
				return -(pos + 1);
			} else if (this.strategy.equals(k, curr)) {
				return pos;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (this.strategy.equals(k, curr)) {
						return pos;
					}
				}

				return -(pos + 1);
			}
		}
	}

	private void insert(int pos, K k, short v) {
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
	public short put(K k, short v) {
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

	public short addTo(K k, short incr) {
		int pos;
		if (this.strategy.equals(k, null)) {
			if (this.containsNullKey) {
				return this.addToValue(this.n, incr);
			}

			pos = this.n;
			this.containsNullKey = true;
		} else {
			K[] key = this.key;
			K curr;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
				if (this.strategy.equals(curr, k)) {
					return this.addToValue(pos, incr);
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (this.strategy.equals(curr, k)) {
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
		K[] key = this.key;

		label30:
		while (true) {
			int last = pos;

			K curr;
			for (pos = pos + 1 & this.mask; (curr = key[pos]) != null; pos = pos + 1 & this.mask) {
				int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					key[last] = curr;
					this.value[last] = this.value[pos];
					continue label30;
				}
			}

			key[last] = null;
			return;
		}
	}

	@Override
	public short removeShort(Object k) {
		if (this.strategy.equals((K)k, null)) {
			return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode((K)k)) & this.mask]) == null) {
				return this.defRetValue;
			} else if (this.strategy.equals((K)k, curr)) {
				return this.removeEntry(pos);
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (this.strategy.equals((K)k, curr)) {
						return this.removeEntry(pos);
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public short getShort(Object k) {
		if (this.strategy.equals((K)k, null)) {
			return this.containsNullKey ? this.value[this.n] : this.defRetValue;
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode((K)k)) & this.mask]) == null) {
				return this.defRetValue;
			} else if (this.strategy.equals((K)k, curr)) {
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (this.strategy.equals((K)k, curr)) {
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public boolean containsKey(Object k) {
		if (this.strategy.equals((K)k, null)) {
			return this.containsNullKey;
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode((K)k)) & this.mask]) == null) {
				return false;
			} else if (this.strategy.equals((K)k, curr)) {
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (this.strategy.equals((K)k, curr)) {
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
		K[] key = this.key;
		if (this.containsNullKey && value[this.n] == v) {
			return true;
		} else {
			int i = this.n;

			while (i-- != 0) {
				if (key[i] != null && value[i] == v) {
					return true;
				}
			}

			return false;
		}
	}

	@Override
	public short getOrDefault(Object k, short defaultValue) {
		if (this.strategy.equals((K)k, null)) {
			return this.containsNullKey ? this.value[this.n] : defaultValue;
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode((K)k)) & this.mask]) == null) {
				return defaultValue;
			} else if (this.strategy.equals((K)k, curr)) {
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (this.strategy.equals((K)k, curr)) {
						return this.value[pos];
					}
				}

				return defaultValue;
			}
		}
	}

	@Override
	public short putIfAbsent(K k, short v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(Object k, short v) {
		if (this.strategy.equals((K)k, null)) {
			if (this.containsNullKey && v == this.value[this.n]) {
				this.removeNullEntry();
				return true;
			} else {
				return false;
			}
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode((K)k)) & this.mask]) == null) {
				return false;
			} else if (this.strategy.equals((K)k, curr) && v == this.value[pos]) {
				this.removeEntry(pos);
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (this.strategy.equals((K)k, curr) && v == this.value[pos]) {
						this.removeEntry(pos);
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean replace(K k, short oldValue, short v) {
		int pos = this.find(k);
		if (pos >= 0 && oldValue == this.value[pos]) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public short replace(K k, short v) {
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
	public short computeShortIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
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
	public short computeShortIfPresent(K k, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			Short newValue = (Short)remappingFunction.apply(k, this.value[pos]);
			if (newValue == null) {
				if (this.strategy.equals(k, null)) {
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
	public short computeShort(K k, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		Short newValue = (Short)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
		if (newValue == null) {
			if (pos >= 0) {
				if (this.strategy.equals(k, null)) {
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
	public short mergeShort(K k, short v, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return v;
		} else {
			Short newValue = (Short)remappingFunction.apply(this.value[pos], v);
			if (newValue == null) {
				if (this.strategy.equals(k, null)) {
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
			Arrays.fill(this.key, null);
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

	public FastEntrySet<K> object2ShortEntrySet() {
		if (this.entries == null) {
			this.entries = new Object2ShortOpenCustomHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public ObjectSet<K> keySet() {
		if (this.keys == null) {
			this.keys = new Object2ShortOpenCustomHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public ShortCollection values() {
		if (this.values == null) {
			this.values = new AbstractShortCollection() {
				@Override
				public ShortIterator iterator() {
					return Object2ShortOpenCustomHashMap.this.new ValueIterator();
				}

				public int size() {
					return Object2ShortOpenCustomHashMap.this.size;
				}

				@Override
				public boolean contains(short v) {
					return Object2ShortOpenCustomHashMap.this.containsValue(v);
				}

				public void clear() {
					Object2ShortOpenCustomHashMap.this.clear();
				}

				@Override
				public void forEach(IntConsumer consumer) {
					if (Object2ShortOpenCustomHashMap.this.containsNullKey) {
						consumer.accept(Object2ShortOpenCustomHashMap.this.value[Object2ShortOpenCustomHashMap.this.n]);
					}

					int pos = Object2ShortOpenCustomHashMap.this.n;

					while (pos-- != 0) {
						if (Object2ShortOpenCustomHashMap.this.key[pos] != null) {
							consumer.accept(Object2ShortOpenCustomHashMap.this.value[pos]);
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
		K[] key = this.key;
		short[] value = this.value;
		int mask = newN - 1;
		K[] newKey = (K[])(new Object[newN + 1]);
		short[] newValue = new short[newN + 1];
		int i = this.n;
		int j = this.realSize();

		while (j-- != 0) {
			while (key[--i] == null) {
			}

			int pos;
			if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != null) {
				while (newKey[pos = pos + 1 & mask] != null) {
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

	public Object2ShortOpenCustomHashMap<K> clone() {
		Object2ShortOpenCustomHashMap<K> c;
		try {
			c = (Object2ShortOpenCustomHashMap<K>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (K[])((Object[])this.key.clone());
		c.value = (short[])this.value.clone();
		c.strategy = this.strategy;
		return c;
	}

	@Override
	public int hashCode() {
		int h = 0;
		int j = this.realSize();
		int i = 0;

		for (int t = 0; j-- != 0; i++) {
			while (this.key[i] == null) {
				i++;
			}

			if (this != this.key[i]) {
				t = this.strategy.hashCode(this.key[i]);
			}

			t ^= this.value[i];
			h += t;
		}

		if (this.containsNullKey) {
			h += this.value[this.n];
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		K[] key = this.key;
		short[] value = this.value;
		Object2ShortOpenCustomHashMap<K>.MapIterator i = new Object2ShortOpenCustomHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeObject(key[e]);
			s.writeShort(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		K[] key = this.key = (K[])(new Object[this.n + 1]);
		short[] value = this.value = new short[this.n + 1];
		int i = this.size;

		while (i-- != 0) {
			K k = (K)s.readObject();
			short v = s.readShort();
			int pos;
			if (this.strategy.equals(k, null)) {
				pos = this.n;
				this.containsNullKey = true;
			} else {
				pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;

				while (key[pos] != null) {
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
		extends Object2ShortOpenCustomHashMap<K>.MapIterator
		implements ObjectIterator<it.unimi.dsi.fastutil.objects.Object2ShortMap.Entry<K>> {
		private Object2ShortOpenCustomHashMap<K>.MapEntry entry;

		private EntryIterator() {
		}

		public Object2ShortOpenCustomHashMap<K>.MapEntry next() {
			return this.entry = Object2ShortOpenCustomHashMap.this.new MapEntry(this.nextEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator
		extends Object2ShortOpenCustomHashMap<K>.MapIterator
		implements ObjectIterator<it.unimi.dsi.fastutil.objects.Object2ShortMap.Entry<K>> {
		private final Object2ShortOpenCustomHashMap<K>.MapEntry entry = Object2ShortOpenCustomHashMap.this.new MapEntry();

		private FastEntryIterator() {
		}

		public Object2ShortOpenCustomHashMap<K>.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Object2ShortOpenCustomHashMap<K>.MapIterator implements ObjectIterator<K> {
		public KeyIterator() {
		}

		public K next() {
			return Object2ShortOpenCustomHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractObjectSet<K> {
		private KeySet() {
		}

		@Override
		public ObjectIterator<K> iterator() {
			return Object2ShortOpenCustomHashMap.this.new KeyIterator();
		}

		public void forEach(Consumer<? super K> consumer) {
			if (Object2ShortOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(Object2ShortOpenCustomHashMap.this.key[Object2ShortOpenCustomHashMap.this.n]);
			}

			int pos = Object2ShortOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				K k = Object2ShortOpenCustomHashMap.this.key[pos];
				if (k != null) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Object2ShortOpenCustomHashMap.this.size;
		}

		public boolean contains(Object k) {
			return Object2ShortOpenCustomHashMap.this.containsKey(k);
		}

		public boolean remove(Object k) {
			int oldSize = Object2ShortOpenCustomHashMap.this.size;
			Object2ShortOpenCustomHashMap.this.removeShort(k);
			return Object2ShortOpenCustomHashMap.this.size != oldSize;
		}

		public void clear() {
			Object2ShortOpenCustomHashMap.this.clear();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.objects.Object2ShortMap.Entry<K>, java.util.Map.Entry<K, Short> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		public K getKey() {
			return Object2ShortOpenCustomHashMap.this.key[this.index];
		}

		@Override
		public short getShortValue() {
			return Object2ShortOpenCustomHashMap.this.value[this.index];
		}

		@Override
		public short setValue(short v) {
			short oldValue = Object2ShortOpenCustomHashMap.this.value[this.index];
			Object2ShortOpenCustomHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Short getValue() {
			return Object2ShortOpenCustomHashMap.this.value[this.index];
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
				java.util.Map.Entry<K, Short> e = (java.util.Map.Entry<K, Short>)o;
				return Object2ShortOpenCustomHashMap.this.strategy.equals(Object2ShortOpenCustomHashMap.this.key[this.index], (K)e.getKey())
					&& Object2ShortOpenCustomHashMap.this.value[this.index] == (Short)e.getValue();
			}
		}

		public int hashCode() {
			return Object2ShortOpenCustomHashMap.this.strategy.hashCode(Object2ShortOpenCustomHashMap.this.key[this.index])
				^ Object2ShortOpenCustomHashMap.this.value[this.index];
		}

		public String toString() {
			return Object2ShortOpenCustomHashMap.this.key[this.index] + "=>" + Object2ShortOpenCustomHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.objects.Object2ShortMap.Entry<K>> implements FastEntrySet<K> {
		private MapEntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.objects.Object2ShortMap.Entry<K>> iterator() {
			return Object2ShortOpenCustomHashMap.this.new EntryIterator();
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.objects.Object2ShortMap.Entry<K>> fastIterator() {
			return Object2ShortOpenCustomHashMap.this.new FastEntryIterator();
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getValue() != null && e.getValue() instanceof Short) {
					K k = (K)e.getKey();
					short v = (Short)e.getValue();
					if (Object2ShortOpenCustomHashMap.this.strategy.equals(k, null)) {
						return Object2ShortOpenCustomHashMap.this.containsNullKey && Object2ShortOpenCustomHashMap.this.value[Object2ShortOpenCustomHashMap.this.n] == v;
					} else {
						K[] key = Object2ShortOpenCustomHashMap.this.key;
						K curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(Object2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Object2ShortOpenCustomHashMap.this.mask]) == null) {
							return false;
						} else if (Object2ShortOpenCustomHashMap.this.strategy.equals(k, curr)) {
							return Object2ShortOpenCustomHashMap.this.value[pos] == v;
						} else {
							while ((curr = key[pos = pos + 1 & Object2ShortOpenCustomHashMap.this.mask]) != null) {
								if (Object2ShortOpenCustomHashMap.this.strategy.equals(k, curr)) {
									return Object2ShortOpenCustomHashMap.this.value[pos] == v;
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
				if (e.getValue() != null && e.getValue() instanceof Short) {
					K k = (K)e.getKey();
					short v = (Short)e.getValue();
					if (Object2ShortOpenCustomHashMap.this.strategy.equals(k, null)) {
						if (Object2ShortOpenCustomHashMap.this.containsNullKey && Object2ShortOpenCustomHashMap.this.value[Object2ShortOpenCustomHashMap.this.n] == v) {
							Object2ShortOpenCustomHashMap.this.removeNullEntry();
							return true;
						} else {
							return false;
						}
					} else {
						K[] key = Object2ShortOpenCustomHashMap.this.key;
						K curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(Object2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Object2ShortOpenCustomHashMap.this.mask]) == null) {
							return false;
						} else if (Object2ShortOpenCustomHashMap.this.strategy.equals(curr, k)) {
							if (Object2ShortOpenCustomHashMap.this.value[pos] == v) {
								Object2ShortOpenCustomHashMap.this.removeEntry(pos);
								return true;
							} else {
								return false;
							}
						} else {
							while ((curr = key[pos = pos + 1 & Object2ShortOpenCustomHashMap.this.mask]) != null) {
								if (Object2ShortOpenCustomHashMap.this.strategy.equals(curr, k) && Object2ShortOpenCustomHashMap.this.value[pos] == v) {
									Object2ShortOpenCustomHashMap.this.removeEntry(pos);
									return true;
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

		public int size() {
			return Object2ShortOpenCustomHashMap.this.size;
		}

		public void clear() {
			Object2ShortOpenCustomHashMap.this.clear();
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.objects.Object2ShortMap.Entry<K>> consumer) {
			if (Object2ShortOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(
					new BasicEntry(
						Object2ShortOpenCustomHashMap.this.key[Object2ShortOpenCustomHashMap.this.n],
						Object2ShortOpenCustomHashMap.this.value[Object2ShortOpenCustomHashMap.this.n]
					)
				);
			}

			int pos = Object2ShortOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Object2ShortOpenCustomHashMap.this.key[pos] != null) {
					consumer.accept(new BasicEntry(Object2ShortOpenCustomHashMap.this.key[pos], Object2ShortOpenCustomHashMap.this.value[pos]));
				}
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.objects.Object2ShortMap.Entry<K>> consumer) {
			BasicEntry<K> entry = new BasicEntry<>();
			if (Object2ShortOpenCustomHashMap.this.containsNullKey) {
				entry.key = Object2ShortOpenCustomHashMap.this.key[Object2ShortOpenCustomHashMap.this.n];
				entry.value = Object2ShortOpenCustomHashMap.this.value[Object2ShortOpenCustomHashMap.this.n];
				consumer.accept(entry);
			}

			int pos = Object2ShortOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Object2ShortOpenCustomHashMap.this.key[pos] != null) {
					entry.key = Object2ShortOpenCustomHashMap.this.key[pos];
					entry.value = Object2ShortOpenCustomHashMap.this.value[pos];
					consumer.accept(entry);
				}
			}
		}
	}

	private class MapIterator {
		int pos = Object2ShortOpenCustomHashMap.this.n;
		int last = -1;
		int c = Object2ShortOpenCustomHashMap.this.size;
		boolean mustReturnNullKey = Object2ShortOpenCustomHashMap.this.containsNullKey;
		ObjectArrayList<K> wrapped;

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
					return this.last = Object2ShortOpenCustomHashMap.this.n;
				} else {
					K[] key = Object2ShortOpenCustomHashMap.this.key;

					while (--this.pos >= 0) {
						if (key[this.pos] != null) {
							return this.last = this.pos;
						}
					}

					this.last = Integer.MIN_VALUE;
					K k = this.wrapped.get(-this.pos - 1);
					int p = HashCommon.mix(Object2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Object2ShortOpenCustomHashMap.this.mask;

					while (!Object2ShortOpenCustomHashMap.this.strategy.equals(k, key[p])) {
						p = p + 1 & Object2ShortOpenCustomHashMap.this.mask;
					}

					return p;
				}
			}
		}

		private void shiftKeys(int pos) {
			K[] key = Object2ShortOpenCustomHashMap.this.key;

			label38:
			while (true) {
				int last = pos;

				K curr;
				for (pos = pos + 1 & Object2ShortOpenCustomHashMap.this.mask; (curr = key[pos]) != null; pos = pos + 1 & Object2ShortOpenCustomHashMap.this.mask) {
					int slot = HashCommon.mix(Object2ShortOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2ShortOpenCustomHashMap.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new ObjectArrayList<>(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						Object2ShortOpenCustomHashMap.this.value[last] = Object2ShortOpenCustomHashMap.this.value[pos];
						continue label38;
					}
				}

				key[last] = null;
				return;
			}
		}

		public void remove() {
			if (this.last == -1) {
				throw new IllegalStateException();
			} else {
				if (this.last == Object2ShortOpenCustomHashMap.this.n) {
					Object2ShortOpenCustomHashMap.this.containsNullKey = false;
					Object2ShortOpenCustomHashMap.this.key[Object2ShortOpenCustomHashMap.this.n] = null;
				} else {
					if (this.pos < 0) {
						Object2ShortOpenCustomHashMap.this.removeShort(this.wrapped.set(-this.pos - 1, null));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				Object2ShortOpenCustomHashMap.this.size--;
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

	private final class ValueIterator extends Object2ShortOpenCustomHashMap<K>.MapIterator implements ShortIterator {
		public ValueIterator() {
		}

		@Override
		public short nextShort() {
			return Object2ShortOpenCustomHashMap.this.value[this.nextEntry()];
		}
	}
}
