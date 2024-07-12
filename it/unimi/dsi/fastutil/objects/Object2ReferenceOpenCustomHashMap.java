package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.Hash.Strategy;
import it.unimi.dsi.fastutil.objects.AbstractObject2ReferenceMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2ReferenceMap.FastEntrySet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;
import java.util.function.Consumer;

public class Object2ReferenceOpenCustomHashMap<K, V> extends AbstractObject2ReferenceMap<K, V> implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient K[] key;
	protected transient V[] value;
	protected transient int mask;
	protected transient boolean containsNullKey;
	protected Strategy<K> strategy;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;
	protected transient FastEntrySet<K, V> entries;
	protected transient ObjectSet<K> keys;
	protected transient ReferenceCollection<V> values;

	public Object2ReferenceOpenCustomHashMap(int expected, float f, Strategy<K> strategy) {
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
			this.value = (V[])(new Object[this.n + 1]);
		}
	}

	public Object2ReferenceOpenCustomHashMap(int expected, Strategy<K> strategy) {
		this(expected, 0.75F, strategy);
	}

	public Object2ReferenceOpenCustomHashMap(Strategy<K> strategy) {
		this(16, 0.75F, strategy);
	}

	public Object2ReferenceOpenCustomHashMap(Map<? extends K, ? extends V> m, float f, Strategy<K> strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Object2ReferenceOpenCustomHashMap(Map<? extends K, ? extends V> m, Strategy<K> strategy) {
		this(m, 0.75F, strategy);
	}

	public Object2ReferenceOpenCustomHashMap(Object2ReferenceMap<K, V> m, float f, Strategy<K> strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Object2ReferenceOpenCustomHashMap(Object2ReferenceMap<K, V> m, Strategy<K> strategy) {
		this(m, 0.75F, strategy);
	}

	public Object2ReferenceOpenCustomHashMap(K[] k, V[] v, float f, Strategy<K> strategy) {
		this(k.length, f, strategy);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Object2ReferenceOpenCustomHashMap(K[] k, V[] v, Strategy<K> strategy) {
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
		this.key[this.n] = null;
		V oldValue = this.value[this.n];
		this.value[this.n] = null;
		this.size--;
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
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

	private void insert(int pos, K k, V v) {
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
	public V put(K k, V v) {
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
			this.value[last] = null;
			return;
		}
	}

	@Override
	public V remove(Object k) {
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
	public V get(Object k) {
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
	public boolean containsValue(Object v) {
		V[] value = this.value;
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
	public void clear() {
		if (this.size != 0) {
			this.size = 0;
			this.containsNullKey = false;
			Arrays.fill(this.key, null);
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

	public FastEntrySet<K, V> object2ReferenceEntrySet() {
		if (this.entries == null) {
			this.entries = new Object2ReferenceOpenCustomHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public ObjectSet<K> keySet() {
		if (this.keys == null) {
			this.keys = new Object2ReferenceOpenCustomHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public ReferenceCollection<V> values() {
		if (this.values == null) {
			this.values = new AbstractReferenceCollection<V>() {
				@Override
				public ObjectIterator<V> iterator() {
					return Object2ReferenceOpenCustomHashMap.this.new ValueIterator();
				}

				public int size() {
					return Object2ReferenceOpenCustomHashMap.this.size;
				}

				public boolean contains(Object v) {
					return Object2ReferenceOpenCustomHashMap.this.containsValue(v);
				}

				public void clear() {
					Object2ReferenceOpenCustomHashMap.this.clear();
				}

				public void forEach(Consumer<? super V> consumer) {
					if (Object2ReferenceOpenCustomHashMap.this.containsNullKey) {
						consumer.accept(Object2ReferenceOpenCustomHashMap.this.value[Object2ReferenceOpenCustomHashMap.this.n]);
					}

					int pos = Object2ReferenceOpenCustomHashMap.this.n;

					while (pos-- != 0) {
						if (Object2ReferenceOpenCustomHashMap.this.key[pos] != null) {
							consumer.accept(Object2ReferenceOpenCustomHashMap.this.value[pos]);
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
		V[] value = this.value;
		int mask = newN - 1;
		K[] newKey = (K[])(new Object[newN + 1]);
		V[] newValue = (V[])(new Object[newN + 1]);
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

	public Object2ReferenceOpenCustomHashMap<K, V> clone() {
		Object2ReferenceOpenCustomHashMap<K, V> c;
		try {
			c = (Object2ReferenceOpenCustomHashMap<K, V>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (K[])((Object[])this.key.clone());
		c.value = (V[])((Object[])this.value.clone());
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

			if (this != this.value[i]) {
				t ^= this.value[i] == null ? 0 : System.identityHashCode(this.value[i]);
			}

			h += t;
		}

		if (this.containsNullKey) {
			h += this.value[this.n] == null ? 0 : System.identityHashCode(this.value[this.n]);
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		K[] key = this.key;
		V[] value = this.value;
		Object2ReferenceOpenCustomHashMap<K, V>.MapIterator i = new Object2ReferenceOpenCustomHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeObject(key[e]);
			s.writeObject(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		K[] key = this.key = (K[])(new Object[this.n + 1]);
		V[] value = this.value = (V[])(new Object[this.n + 1]);
		int i = this.size;

		while (i-- != 0) {
			K k = (K)s.readObject();
			V v = (V)s.readObject();
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
		extends Object2ReferenceOpenCustomHashMap<K, V>.MapIterator
		implements ObjectIterator<it.unimi.dsi.fastutil.objects.Object2ReferenceMap.Entry<K, V>> {
		private Object2ReferenceOpenCustomHashMap<K, V>.MapEntry entry;

		private EntryIterator() {
		}

		public Object2ReferenceOpenCustomHashMap<K, V>.MapEntry next() {
			return this.entry = Object2ReferenceOpenCustomHashMap.this.new MapEntry(this.nextEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator
		extends Object2ReferenceOpenCustomHashMap<K, V>.MapIterator
		implements ObjectIterator<it.unimi.dsi.fastutil.objects.Object2ReferenceMap.Entry<K, V>> {
		private final Object2ReferenceOpenCustomHashMap<K, V>.MapEntry entry = Object2ReferenceOpenCustomHashMap.this.new MapEntry();

		private FastEntryIterator() {
		}

		public Object2ReferenceOpenCustomHashMap<K, V>.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Object2ReferenceOpenCustomHashMap<K, V>.MapIterator implements ObjectIterator<K> {
		public KeyIterator() {
		}

		public K next() {
			return Object2ReferenceOpenCustomHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractObjectSet<K> {
		private KeySet() {
		}

		@Override
		public ObjectIterator<K> iterator() {
			return Object2ReferenceOpenCustomHashMap.this.new KeyIterator();
		}

		public void forEach(Consumer<? super K> consumer) {
			if (Object2ReferenceOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(Object2ReferenceOpenCustomHashMap.this.key[Object2ReferenceOpenCustomHashMap.this.n]);
			}

			int pos = Object2ReferenceOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				K k = Object2ReferenceOpenCustomHashMap.this.key[pos];
				if (k != null) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Object2ReferenceOpenCustomHashMap.this.size;
		}

		public boolean contains(Object k) {
			return Object2ReferenceOpenCustomHashMap.this.containsKey(k);
		}

		public boolean remove(Object k) {
			int oldSize = Object2ReferenceOpenCustomHashMap.this.size;
			Object2ReferenceOpenCustomHashMap.this.remove(k);
			return Object2ReferenceOpenCustomHashMap.this.size != oldSize;
		}

		public void clear() {
			Object2ReferenceOpenCustomHashMap.this.clear();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.objects.Object2ReferenceMap.Entry<K, V>, java.util.Map.Entry<K, V> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		public K getKey() {
			return Object2ReferenceOpenCustomHashMap.this.key[this.index];
		}

		public V getValue() {
			return Object2ReferenceOpenCustomHashMap.this.value[this.index];
		}

		public V setValue(V v) {
			V oldValue = Object2ReferenceOpenCustomHashMap.this.value[this.index];
			Object2ReferenceOpenCustomHashMap.this.value[this.index] = v;
			return oldValue;
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<K, V> e = (java.util.Map.Entry<K, V>)o;
				return Object2ReferenceOpenCustomHashMap.this.strategy.equals(Object2ReferenceOpenCustomHashMap.this.key[this.index], (K)e.getKey())
					&& Object2ReferenceOpenCustomHashMap.this.value[this.index] == e.getValue();
			}
		}

		public int hashCode() {
			return Object2ReferenceOpenCustomHashMap.this.strategy.hashCode(Object2ReferenceOpenCustomHashMap.this.key[this.index])
				^ (Object2ReferenceOpenCustomHashMap.this.value[this.index] == null ? 0 : System.identityHashCode(Object2ReferenceOpenCustomHashMap.this.value[this.index]));
		}

		public String toString() {
			return Object2ReferenceOpenCustomHashMap.this.key[this.index] + "=>" + Object2ReferenceOpenCustomHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.objects.Object2ReferenceMap.Entry<K, V>> implements FastEntrySet<K, V> {
		private MapEntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.objects.Object2ReferenceMap.Entry<K, V>> iterator() {
			return Object2ReferenceOpenCustomHashMap.this.new EntryIterator();
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.objects.Object2ReferenceMap.Entry<K, V>> fastIterator() {
			return Object2ReferenceOpenCustomHashMap.this.new FastEntryIterator();
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				K k = (K)e.getKey();
				V v = (V)e.getValue();
				if (Object2ReferenceOpenCustomHashMap.this.strategy.equals(k, null)) {
					return Object2ReferenceOpenCustomHashMap.this.containsNullKey
						&& Object2ReferenceOpenCustomHashMap.this.value[Object2ReferenceOpenCustomHashMap.this.n] == v;
				} else {
					K[] key = Object2ReferenceOpenCustomHashMap.this.key;
					K curr;
					int pos;
					if ((curr = key[pos = HashCommon.mix(Object2ReferenceOpenCustomHashMap.this.strategy.hashCode(k)) & Object2ReferenceOpenCustomHashMap.this.mask]) == null) {
						return false;
					} else if (Object2ReferenceOpenCustomHashMap.this.strategy.equals(k, curr)) {
						return Object2ReferenceOpenCustomHashMap.this.value[pos] == v;
					} else {
						while ((curr = key[pos = pos + 1 & Object2ReferenceOpenCustomHashMap.this.mask]) != null) {
							if (Object2ReferenceOpenCustomHashMap.this.strategy.equals(k, curr)) {
								return Object2ReferenceOpenCustomHashMap.this.value[pos] == v;
							}
						}

						return false;
					}
				}
			}
		}

		public boolean remove(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				K k = (K)e.getKey();
				V v = (V)e.getValue();
				if (Object2ReferenceOpenCustomHashMap.this.strategy.equals(k, null)) {
					if (Object2ReferenceOpenCustomHashMap.this.containsNullKey && Object2ReferenceOpenCustomHashMap.this.value[Object2ReferenceOpenCustomHashMap.this.n] == v) {
						Object2ReferenceOpenCustomHashMap.this.removeNullEntry();
						return true;
					} else {
						return false;
					}
				} else {
					K[] key = Object2ReferenceOpenCustomHashMap.this.key;
					K curr;
					int pos;
					if ((curr = key[pos = HashCommon.mix(Object2ReferenceOpenCustomHashMap.this.strategy.hashCode(k)) & Object2ReferenceOpenCustomHashMap.this.mask]) == null) {
						return false;
					} else if (Object2ReferenceOpenCustomHashMap.this.strategy.equals(curr, k)) {
						if (Object2ReferenceOpenCustomHashMap.this.value[pos] == v) {
							Object2ReferenceOpenCustomHashMap.this.removeEntry(pos);
							return true;
						} else {
							return false;
						}
					} else {
						while ((curr = key[pos = pos + 1 & Object2ReferenceOpenCustomHashMap.this.mask]) != null) {
							if (Object2ReferenceOpenCustomHashMap.this.strategy.equals(curr, k) && Object2ReferenceOpenCustomHashMap.this.value[pos] == v) {
								Object2ReferenceOpenCustomHashMap.this.removeEntry(pos);
								return true;
							}
						}

						return false;
					}
				}
			}
		}

		public int size() {
			return Object2ReferenceOpenCustomHashMap.this.size;
		}

		public void clear() {
			Object2ReferenceOpenCustomHashMap.this.clear();
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.objects.Object2ReferenceMap.Entry<K, V>> consumer) {
			if (Object2ReferenceOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(
					new BasicEntry(
						Object2ReferenceOpenCustomHashMap.this.key[Object2ReferenceOpenCustomHashMap.this.n],
						Object2ReferenceOpenCustomHashMap.this.value[Object2ReferenceOpenCustomHashMap.this.n]
					)
				);
			}

			int pos = Object2ReferenceOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Object2ReferenceOpenCustomHashMap.this.key[pos] != null) {
					consumer.accept(new BasicEntry(Object2ReferenceOpenCustomHashMap.this.key[pos], Object2ReferenceOpenCustomHashMap.this.value[pos]));
				}
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.objects.Object2ReferenceMap.Entry<K, V>> consumer) {
			BasicEntry<K, V> entry = new BasicEntry<>();
			if (Object2ReferenceOpenCustomHashMap.this.containsNullKey) {
				entry.key = Object2ReferenceOpenCustomHashMap.this.key[Object2ReferenceOpenCustomHashMap.this.n];
				entry.value = Object2ReferenceOpenCustomHashMap.this.value[Object2ReferenceOpenCustomHashMap.this.n];
				consumer.accept(entry);
			}

			int pos = Object2ReferenceOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Object2ReferenceOpenCustomHashMap.this.key[pos] != null) {
					entry.key = Object2ReferenceOpenCustomHashMap.this.key[pos];
					entry.value = Object2ReferenceOpenCustomHashMap.this.value[pos];
					consumer.accept(entry);
				}
			}
		}
	}

	private class MapIterator {
		int pos = Object2ReferenceOpenCustomHashMap.this.n;
		int last = -1;
		int c = Object2ReferenceOpenCustomHashMap.this.size;
		boolean mustReturnNullKey = Object2ReferenceOpenCustomHashMap.this.containsNullKey;
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
					return this.last = Object2ReferenceOpenCustomHashMap.this.n;
				} else {
					K[] key = Object2ReferenceOpenCustomHashMap.this.key;

					while (--this.pos >= 0) {
						if (key[this.pos] != null) {
							return this.last = this.pos;
						}
					}

					this.last = Integer.MIN_VALUE;
					K k = this.wrapped.get(-this.pos - 1);
					int p = HashCommon.mix(Object2ReferenceOpenCustomHashMap.this.strategy.hashCode(k)) & Object2ReferenceOpenCustomHashMap.this.mask;

					while (!Object2ReferenceOpenCustomHashMap.this.strategy.equals(k, key[p])) {
						p = p + 1 & Object2ReferenceOpenCustomHashMap.this.mask;
					}

					return p;
				}
			}
		}

		private void shiftKeys(int pos) {
			K[] key = Object2ReferenceOpenCustomHashMap.this.key;

			label38:
			while (true) {
				int last = pos;

				K curr;
				for (pos = pos + 1 & Object2ReferenceOpenCustomHashMap.this.mask; (curr = key[pos]) != null; pos = pos + 1 & Object2ReferenceOpenCustomHashMap.this.mask) {
					int slot = HashCommon.mix(Object2ReferenceOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2ReferenceOpenCustomHashMap.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new ObjectArrayList<>(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						Object2ReferenceOpenCustomHashMap.this.value[last] = Object2ReferenceOpenCustomHashMap.this.value[pos];
						continue label38;
					}
				}

				key[last] = null;
				Object2ReferenceOpenCustomHashMap.this.value[last] = null;
				return;
			}
		}

		public void remove() {
			if (this.last == -1) {
				throw new IllegalStateException();
			} else {
				if (this.last == Object2ReferenceOpenCustomHashMap.this.n) {
					Object2ReferenceOpenCustomHashMap.this.containsNullKey = false;
					Object2ReferenceOpenCustomHashMap.this.key[Object2ReferenceOpenCustomHashMap.this.n] = null;
					Object2ReferenceOpenCustomHashMap.this.value[Object2ReferenceOpenCustomHashMap.this.n] = null;
				} else {
					if (this.pos < 0) {
						Object2ReferenceOpenCustomHashMap.this.remove(this.wrapped.set(-this.pos - 1, null));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				Object2ReferenceOpenCustomHashMap.this.size--;
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

	private final class ValueIterator extends Object2ReferenceOpenCustomHashMap<K, V>.MapIterator implements ObjectIterator<V> {
		public ValueIterator() {
		}

		public V next() {
			return Object2ReferenceOpenCustomHashMap.this.value[this.nextEntry()];
		}
	}
}
