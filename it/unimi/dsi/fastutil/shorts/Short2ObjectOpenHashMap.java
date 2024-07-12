package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap.FastEntrySet;
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
import java.util.function.IntFunction;

public class Short2ObjectOpenHashMap<V> extends AbstractShort2ObjectMap<V> implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient short[] key;
	protected transient V[] value;
	protected transient int mask;
	protected transient boolean containsNullKey;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;
	protected transient FastEntrySet<V> entries;
	protected transient ShortSet keys;
	protected transient ObjectCollection<V> values;

	public Short2ObjectOpenHashMap(int expected, float f) {
		if (f <= 0.0F || f > 1.0F) {
			throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
		} else if (expected < 0) {
			throw new IllegalArgumentException("The expected number of elements must be nonnegative");
		} else {
			this.f = f;
			this.minN = this.n = HashCommon.arraySize(expected, f);
			this.mask = this.n - 1;
			this.maxFill = HashCommon.maxFill(this.n, f);
			this.key = new short[this.n + 1];
			this.value = (V[])(new Object[this.n + 1]);
		}
	}

	public Short2ObjectOpenHashMap(int expected) {
		this(expected, 0.75F);
	}

	public Short2ObjectOpenHashMap() {
		this(16, 0.75F);
	}

	public Short2ObjectOpenHashMap(Map<? extends Short, ? extends V> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Short2ObjectOpenHashMap(Map<? extends Short, ? extends V> m) {
		this(m, 0.75F);
	}

	public Short2ObjectOpenHashMap(Short2ObjectMap<V> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Short2ObjectOpenHashMap(Short2ObjectMap<V> m) {
		this(m, 0.75F);
	}

	public Short2ObjectOpenHashMap(short[] k, V[] v, float f) {
		this(k.length, f);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Short2ObjectOpenHashMap(short[] k, V[] v) {
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

	private V removeEntry(int pos) {
		V oldValue = this.value[pos];
		this.value[pos] = null;
		this.size--;
		this.shiftKeys(pos);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	private V removeNullEntry() {
		this.containsNullKey = false;
		V oldValue = this.value[this.n];
		this.value[this.n] = null;
		this.size--;
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	@Override
	public void putAll(Map<? extends Short, ? extends V> m) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(m.size());
		} else {
			this.tryCapacity((long)(this.size() + m.size()));
		}

		super.putAll(m);
	}

	private int find(short k) {
		if (k == 0) {
			return this.containsNullKey ? this.n : -(this.n + 1);
		} else {
			short[] key = this.key;
			short curr;
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

	private void insert(int pos, short k, V v) {
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
	public V put(short k, V v) {
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		} else {
			V oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	protected final void shiftKeys(int pos) {
		short[] key = this.key;

		label30:
		while (true) {
			int last = pos;

			short curr;
			for (pos = pos + 1 & this.mask; (curr = key[pos]) != 0; pos = pos + 1 & this.mask) {
				int slot = HashCommon.mix(curr) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					key[last] = curr;
					this.value[last] = this.value[pos];
					continue label30;
				}
			}

			key[last] = 0;
			this.value[last] = null;
			return;
		}
	}

	@Override
	public V remove(short k) {
		if (k == 0) {
			return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
		} else {
			short[] key = this.key;
			short curr;
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
	public V get(short k) {
		if (k == 0) {
			return this.containsNullKey ? this.value[this.n] : this.defRetValue;
		} else {
			short[] key = this.key;
			short curr;
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
	public boolean containsKey(short k) {
		if (k == 0) {
			return this.containsNullKey;
		} else {
			short[] key = this.key;
			short curr;
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
	public boolean containsValue(Object v) {
		V[] value = this.value;
		short[] key = this.key;
		if (this.containsNullKey && Objects.equals(value[this.n], v)) {
			return true;
		} else {
			int i = this.n;

			while (i-- != 0) {
				if (key[i] != 0 && Objects.equals(value[i], v)) {
					return true;
				}
			}

			return false;
		}
	}

	@Override
	public V getOrDefault(short k, V defaultValue) {
		if (k == 0) {
			return this.containsNullKey ? this.value[this.n] : defaultValue;
		} else {
			short[] key = this.key;
			short curr;
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
	public V putIfAbsent(short k, V v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(short k, Object v) {
		if (k == 0) {
			if (this.containsNullKey && Objects.equals(v, this.value[this.n])) {
				this.removeNullEntry();
				return true;
			} else {
				return false;
			}
		} else {
			short[] key = this.key;
			short curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
				return false;
			} else if (k == curr && Objects.equals(v, this.value[pos])) {
				this.removeEntry(pos);
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (k == curr && Objects.equals(v, this.value[pos])) {
						this.removeEntry(pos);
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean replace(short k, V oldValue, V v) {
		int pos = this.find(k);
		if (pos >= 0 && Objects.equals(oldValue, this.value[pos])) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public V replace(short k, V v) {
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			V oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	@Override
	public V computeIfAbsent(short k, IntFunction<? extends V> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			V newValue = (V)mappingFunction.apply(k);
			this.insert(-pos - 1, k, newValue);
			return newValue;
		}
	}

	@Override
	public V computeIfPresent(short k, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			V newValue = (V)remappingFunction.apply(k, this.value[pos]);
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
	public V compute(short k, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		V newValue = (V)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
		if (newValue == null) {
			if (pos >= 0) {
				if (k == 0) {
					this.removeNullEntry();
				} else {
					this.removeEntry(pos);
				}
			}

			return this.defRetValue;
		} else if (pos < 0) {
			this.insert(-pos - 1, k, newValue);
			return newValue;
		} else {
			return this.value[pos] = newValue;
		}
	}

	@Override
	public V merge(short k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos >= 0 && this.value[pos] != null) {
			V newValue = (V)remappingFunction.apply(this.value[pos], v);
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
		} else if (v == null) {
			return this.defRetValue;
		} else {
			this.insert(-pos - 1, k, v);
			return v;
		}
	}

	@Override
	public void clear() {
		if (this.size != 0) {
			this.size = 0;
			this.containsNullKey = false;
			Arrays.fill(this.key, (short)0);
			Arrays.fill(this.value, null);
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

	public FastEntrySet<V> short2ObjectEntrySet() {
		if (this.entries == null) {
			this.entries = new Short2ObjectOpenHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public ShortSet keySet() {
		if (this.keys == null) {
			this.keys = new Short2ObjectOpenHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public ObjectCollection<V> values() {
		if (this.values == null) {
			this.values = new AbstractObjectCollection<V>() {
				@Override
				public ObjectIterator<V> iterator() {
					return Short2ObjectOpenHashMap.this.new ValueIterator();
				}

				public int size() {
					return Short2ObjectOpenHashMap.this.size;
				}

				public boolean contains(Object v) {
					return Short2ObjectOpenHashMap.this.containsValue(v);
				}

				public void clear() {
					Short2ObjectOpenHashMap.this.clear();
				}

				public void forEach(Consumer<? super V> consumer) {
					if (Short2ObjectOpenHashMap.this.containsNullKey) {
						consumer.accept(Short2ObjectOpenHashMap.this.value[Short2ObjectOpenHashMap.this.n]);
					}

					int pos = Short2ObjectOpenHashMap.this.n;

					while (pos-- != 0) {
						if (Short2ObjectOpenHashMap.this.key[pos] != 0) {
							consumer.accept(Short2ObjectOpenHashMap.this.value[pos]);
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
		short[] key = this.key;
		V[] value = this.value;
		int mask = newN - 1;
		short[] newKey = new short[newN + 1];
		V[] newValue = (V[])(new Object[newN + 1]);
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

	public Short2ObjectOpenHashMap<V> clone() {
		Short2ObjectOpenHashMap<V> c;
		try {
			c = (Short2ObjectOpenHashMap<V>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (short[])this.key.clone();
		c.value = (V[])((Object[])this.value.clone());
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
			if (this != this.value[i]) {
				t ^= this.value[i] == null ? 0 : this.value[i].hashCode();
			}

			h += t;
		}

		if (this.containsNullKey) {
			h += this.value[this.n] == null ? 0 : this.value[this.n].hashCode();
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		short[] key = this.key;
		V[] value = this.value;
		Short2ObjectOpenHashMap<V>.MapIterator i = new Short2ObjectOpenHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeShort(key[e]);
			s.writeObject(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		short[] key = this.key = new short[this.n + 1];
		V[] value = this.value = (V[])(new Object[this.n + 1]);
		int i = this.size;

		while (i-- != 0) {
			short k = s.readShort();
			V v = (V)s.readObject();
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

	private class EntryIterator extends Short2ObjectOpenHashMap<V>.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.shorts.Short2ObjectMap.Entry<V>> {
		private Short2ObjectOpenHashMap<V>.MapEntry entry;

		private EntryIterator() {
		}

		public Short2ObjectOpenHashMap<V>.MapEntry next() {
			return this.entry = Short2ObjectOpenHashMap.this.new MapEntry(this.nextEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator
		extends Short2ObjectOpenHashMap<V>.MapIterator
		implements ObjectIterator<it.unimi.dsi.fastutil.shorts.Short2ObjectMap.Entry<V>> {
		private final Short2ObjectOpenHashMap<V>.MapEntry entry = Short2ObjectOpenHashMap.this.new MapEntry();

		private FastEntryIterator() {
		}

		public Short2ObjectOpenHashMap<V>.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Short2ObjectOpenHashMap<V>.MapIterator implements ShortIterator {
		public KeyIterator() {
		}

		@Override
		public short nextShort() {
			return Short2ObjectOpenHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractShortSet {
		private KeySet() {
		}

		@Override
		public ShortIterator iterator() {
			return Short2ObjectOpenHashMap.this.new KeyIterator();
		}

		@Override
		public void forEach(IntConsumer consumer) {
			if (Short2ObjectOpenHashMap.this.containsNullKey) {
				consumer.accept(Short2ObjectOpenHashMap.this.key[Short2ObjectOpenHashMap.this.n]);
			}

			int pos = Short2ObjectOpenHashMap.this.n;

			while (pos-- != 0) {
				short k = Short2ObjectOpenHashMap.this.key[pos];
				if (k != 0) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Short2ObjectOpenHashMap.this.size;
		}

		@Override
		public boolean contains(short k) {
			return Short2ObjectOpenHashMap.this.containsKey(k);
		}

		@Override
		public boolean remove(short k) {
			int oldSize = Short2ObjectOpenHashMap.this.size;
			Short2ObjectOpenHashMap.this.remove(k);
			return Short2ObjectOpenHashMap.this.size != oldSize;
		}

		public void clear() {
			Short2ObjectOpenHashMap.this.clear();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.shorts.Short2ObjectMap.Entry<V>, java.util.Map.Entry<Short, V> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		@Override
		public short getShortKey() {
			return Short2ObjectOpenHashMap.this.key[this.index];
		}

		public V getValue() {
			return Short2ObjectOpenHashMap.this.value[this.index];
		}

		public V setValue(V v) {
			V oldValue = Short2ObjectOpenHashMap.this.value[this.index];
			Short2ObjectOpenHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Short getKey() {
			return Short2ObjectOpenHashMap.this.key[this.index];
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<Short, V> e = (java.util.Map.Entry<Short, V>)o;
				return Short2ObjectOpenHashMap.this.key[this.index] == (Short)e.getKey() && Objects.equals(Short2ObjectOpenHashMap.this.value[this.index], e.getValue());
			}
		}

		public int hashCode() {
			return Short2ObjectOpenHashMap.this.key[this.index]
				^ (Short2ObjectOpenHashMap.this.value[this.index] == null ? 0 : Short2ObjectOpenHashMap.this.value[this.index].hashCode());
		}

		public String toString() {
			return Short2ObjectOpenHashMap.this.key[this.index] + "=>" + Short2ObjectOpenHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.shorts.Short2ObjectMap.Entry<V>> implements FastEntrySet<V> {
		private MapEntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.shorts.Short2ObjectMap.Entry<V>> iterator() {
			return Short2ObjectOpenHashMap.this.new EntryIterator();
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.shorts.Short2ObjectMap.Entry<V>> fastIterator() {
			return Short2ObjectOpenHashMap.this.new FastEntryIterator();
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() != null && e.getKey() instanceof Short) {
					short k = (Short)e.getKey();
					V v = (V)e.getValue();
					if (k == 0) {
						return Short2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Short2ObjectOpenHashMap.this.value[Short2ObjectOpenHashMap.this.n], v);
					} else {
						short[] key = Short2ObjectOpenHashMap.this.key;
						short curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(k) & Short2ObjectOpenHashMap.this.mask]) == 0) {
							return false;
						} else if (k == curr) {
							return Objects.equals(Short2ObjectOpenHashMap.this.value[pos], v);
						} else {
							while ((curr = key[pos = pos + 1 & Short2ObjectOpenHashMap.this.mask]) != 0) {
								if (k == curr) {
									return Objects.equals(Short2ObjectOpenHashMap.this.value[pos], v);
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
				if (e.getKey() != null && e.getKey() instanceof Short) {
					short k = (Short)e.getKey();
					V v = (V)e.getValue();
					if (k == 0) {
						if (Short2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Short2ObjectOpenHashMap.this.value[Short2ObjectOpenHashMap.this.n], v)) {
							Short2ObjectOpenHashMap.this.removeNullEntry();
							return true;
						} else {
							return false;
						}
					} else {
						short[] key = Short2ObjectOpenHashMap.this.key;
						short curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(k) & Short2ObjectOpenHashMap.this.mask]) == 0) {
							return false;
						} else if (curr == k) {
							if (Objects.equals(Short2ObjectOpenHashMap.this.value[pos], v)) {
								Short2ObjectOpenHashMap.this.removeEntry(pos);
								return true;
							} else {
								return false;
							}
						} else {
							while ((curr = key[pos = pos + 1 & Short2ObjectOpenHashMap.this.mask]) != 0) {
								if (curr == k && Objects.equals(Short2ObjectOpenHashMap.this.value[pos], v)) {
									Short2ObjectOpenHashMap.this.removeEntry(pos);
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
			return Short2ObjectOpenHashMap.this.size;
		}

		public void clear() {
			Short2ObjectOpenHashMap.this.clear();
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.shorts.Short2ObjectMap.Entry<V>> consumer) {
			if (Short2ObjectOpenHashMap.this.containsNullKey) {
				consumer.accept(
					new BasicEntry(Short2ObjectOpenHashMap.this.key[Short2ObjectOpenHashMap.this.n], Short2ObjectOpenHashMap.this.value[Short2ObjectOpenHashMap.this.n])
				);
			}

			int pos = Short2ObjectOpenHashMap.this.n;

			while (pos-- != 0) {
				if (Short2ObjectOpenHashMap.this.key[pos] != 0) {
					consumer.accept(new BasicEntry(Short2ObjectOpenHashMap.this.key[pos], Short2ObjectOpenHashMap.this.value[pos]));
				}
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.shorts.Short2ObjectMap.Entry<V>> consumer) {
			BasicEntry<V> entry = new BasicEntry<>();
			if (Short2ObjectOpenHashMap.this.containsNullKey) {
				entry.key = Short2ObjectOpenHashMap.this.key[Short2ObjectOpenHashMap.this.n];
				entry.value = Short2ObjectOpenHashMap.this.value[Short2ObjectOpenHashMap.this.n];
				consumer.accept(entry);
			}

			int pos = Short2ObjectOpenHashMap.this.n;

			while (pos-- != 0) {
				if (Short2ObjectOpenHashMap.this.key[pos] != 0) {
					entry.key = Short2ObjectOpenHashMap.this.key[pos];
					entry.value = Short2ObjectOpenHashMap.this.value[pos];
					consumer.accept(entry);
				}
			}
		}
	}

	private class MapIterator {
		int pos = Short2ObjectOpenHashMap.this.n;
		int last = -1;
		int c = Short2ObjectOpenHashMap.this.size;
		boolean mustReturnNullKey = Short2ObjectOpenHashMap.this.containsNullKey;
		ShortArrayList wrapped;

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
					return this.last = Short2ObjectOpenHashMap.this.n;
				} else {
					short[] key = Short2ObjectOpenHashMap.this.key;

					while (--this.pos >= 0) {
						if (key[this.pos] != 0) {
							return this.last = this.pos;
						}
					}

					this.last = Integer.MIN_VALUE;
					short k = this.wrapped.getShort(-this.pos - 1);
					int p = HashCommon.mix(k) & Short2ObjectOpenHashMap.this.mask;

					while (k != key[p]) {
						p = p + 1 & Short2ObjectOpenHashMap.this.mask;
					}

					return p;
				}
			}
		}

		private void shiftKeys(int pos) {
			short[] key = Short2ObjectOpenHashMap.this.key;

			label38:
			while (true) {
				int last = pos;

				short curr;
				for (pos = pos + 1 & Short2ObjectOpenHashMap.this.mask; (curr = key[pos]) != 0; pos = pos + 1 & Short2ObjectOpenHashMap.this.mask) {
					int slot = HashCommon.mix(curr) & Short2ObjectOpenHashMap.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new ShortArrayList(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						Short2ObjectOpenHashMap.this.value[last] = Short2ObjectOpenHashMap.this.value[pos];
						continue label38;
					}
				}

				key[last] = 0;
				Short2ObjectOpenHashMap.this.value[last] = null;
				return;
			}
		}

		public void remove() {
			if (this.last == -1) {
				throw new IllegalStateException();
			} else {
				if (this.last == Short2ObjectOpenHashMap.this.n) {
					Short2ObjectOpenHashMap.this.containsNullKey = false;
					Short2ObjectOpenHashMap.this.value[Short2ObjectOpenHashMap.this.n] = null;
				} else {
					if (this.pos < 0) {
						Short2ObjectOpenHashMap.this.remove(this.wrapped.getShort(-this.pos - 1));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				Short2ObjectOpenHashMap.this.size--;
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

	private final class ValueIterator extends Short2ObjectOpenHashMap<V>.MapIterator implements ObjectIterator<V> {
		public ValueIterator() {
		}

		public V next() {
			return Short2ObjectOpenHashMap.this.value[this.nextEntry()];
		}
	}
}
