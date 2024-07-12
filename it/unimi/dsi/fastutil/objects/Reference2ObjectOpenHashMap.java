package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.objects.AbstractReference2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap.FastEntrySet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Consumer;

public class Reference2ObjectOpenHashMap<K, V> extends AbstractReference2ObjectMap<K, V> implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient K[] key;
	protected transient V[] value;
	protected transient int mask;
	protected transient boolean containsNullKey;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;
	protected transient FastEntrySet<K, V> entries;
	protected transient ReferenceSet<K> keys;
	protected transient ObjectCollection<V> values;

	public Reference2ObjectOpenHashMap(int expected, float f) {
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

	public Reference2ObjectOpenHashMap(int expected) {
		this(expected, 0.75F);
	}

	public Reference2ObjectOpenHashMap() {
		this(16, 0.75F);
	}

	public Reference2ObjectOpenHashMap(Map<? extends K, ? extends V> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Reference2ObjectOpenHashMap(Map<? extends K, ? extends V> m) {
		this(m, 0.75F);
	}

	public Reference2ObjectOpenHashMap(Reference2ObjectMap<K, V> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Reference2ObjectOpenHashMap(Reference2ObjectMap<K, V> m) {
		this(m, 0.75F);
	}

	public Reference2ObjectOpenHashMap(K[] k, V[] v, float f) {
		this(k.length, f);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Reference2ObjectOpenHashMap(K[] k, V[] v) {
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
		if (k == null) {
			return this.containsNullKey ? this.n : -(this.n + 1);
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
				return -(pos + 1);
			} else if (k == curr) {
				return pos;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (k == curr) {
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
				int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
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
		if (k == null) {
			return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
				return this.defRetValue;
			} else if (k == curr) {
				return this.removeEntry(pos);
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (k == curr) {
						return this.removeEntry(pos);
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public V get(Object k) {
		if (k == null) {
			return this.containsNullKey ? this.value[this.n] : this.defRetValue;
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
				return this.defRetValue;
			} else if (k == curr) {
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (k == curr) {
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public boolean containsKey(Object k) {
		if (k == null) {
			return this.containsNullKey;
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
				return false;
			} else if (k == curr) {
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
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
		K[] key = this.key;
		if (this.containsNullKey && Objects.equals(value[this.n], v)) {
			return true;
		} else {
			int i = this.n;

			while (i-- != 0) {
				if (key[i] != null && Objects.equals(value[i], v)) {
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

	public FastEntrySet<K, V> reference2ObjectEntrySet() {
		if (this.entries == null) {
			this.entries = new Reference2ObjectOpenHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public ReferenceSet<K> keySet() {
		if (this.keys == null) {
			this.keys = new Reference2ObjectOpenHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public ObjectCollection<V> values() {
		if (this.values == null) {
			this.values = new AbstractObjectCollection<V>() {
				@Override
				public ObjectIterator<V> iterator() {
					return Reference2ObjectOpenHashMap.this.new ValueIterator();
				}

				public int size() {
					return Reference2ObjectOpenHashMap.this.size;
				}

				public boolean contains(Object v) {
					return Reference2ObjectOpenHashMap.this.containsValue(v);
				}

				public void clear() {
					Reference2ObjectOpenHashMap.this.clear();
				}

				public void forEach(Consumer<? super V> consumer) {
					if (Reference2ObjectOpenHashMap.this.containsNullKey) {
						consumer.accept(Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n]);
					}

					int pos = Reference2ObjectOpenHashMap.this.n;

					while (pos-- != 0) {
						if (Reference2ObjectOpenHashMap.this.key[pos] != null) {
							consumer.accept(Reference2ObjectOpenHashMap.this.value[pos]);
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
			if (newKey[pos = HashCommon.mix(System.identityHashCode(key[i])) & mask] != null) {
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

	public Reference2ObjectOpenHashMap<K, V> clone() {
		Reference2ObjectOpenHashMap<K, V> c;
		try {
			c = (Reference2ObjectOpenHashMap<K, V>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (K[])((Object[])this.key.clone());
		c.value = (V[])((Object[])this.value.clone());
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
				t = System.identityHashCode(this.key[i]);
			}

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
		K[] key = this.key;
		V[] value = this.value;
		Reference2ObjectOpenHashMap<K, V>.MapIterator i = new Reference2ObjectOpenHashMap.MapIterator();
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
			if (k == null) {
				pos = this.n;
				this.containsNullKey = true;
			} else {
				pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;

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
		extends Reference2ObjectOpenHashMap<K, V>.MapIterator
		implements ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> {
		private Reference2ObjectOpenHashMap<K, V>.MapEntry entry;

		private EntryIterator() {
		}

		public Reference2ObjectOpenHashMap<K, V>.MapEntry next() {
			return this.entry = Reference2ObjectOpenHashMap.this.new MapEntry(this.nextEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator
		extends Reference2ObjectOpenHashMap<K, V>.MapIterator
		implements ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> {
		private final Reference2ObjectOpenHashMap<K, V>.MapEntry entry = Reference2ObjectOpenHashMap.this.new MapEntry();

		private FastEntryIterator() {
		}

		public Reference2ObjectOpenHashMap<K, V>.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Reference2ObjectOpenHashMap<K, V>.MapIterator implements ObjectIterator<K> {
		public KeyIterator() {
		}

		public K next() {
			return Reference2ObjectOpenHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractReferenceSet<K> {
		private KeySet() {
		}

		@Override
		public ObjectIterator<K> iterator() {
			return Reference2ObjectOpenHashMap.this.new KeyIterator();
		}

		public void forEach(Consumer<? super K> consumer) {
			if (Reference2ObjectOpenHashMap.this.containsNullKey) {
				consumer.accept(Reference2ObjectOpenHashMap.this.key[Reference2ObjectOpenHashMap.this.n]);
			}

			int pos = Reference2ObjectOpenHashMap.this.n;

			while (pos-- != 0) {
				K k = Reference2ObjectOpenHashMap.this.key[pos];
				if (k != null) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Reference2ObjectOpenHashMap.this.size;
		}

		public boolean contains(Object k) {
			return Reference2ObjectOpenHashMap.this.containsKey(k);
		}

		public boolean remove(Object k) {
			int oldSize = Reference2ObjectOpenHashMap.this.size;
			Reference2ObjectOpenHashMap.this.remove(k);
			return Reference2ObjectOpenHashMap.this.size != oldSize;
		}

		public void clear() {
			Reference2ObjectOpenHashMap.this.clear();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>, java.util.Map.Entry<K, V> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		public K getKey() {
			return Reference2ObjectOpenHashMap.this.key[this.index];
		}

		public V getValue() {
			return Reference2ObjectOpenHashMap.this.value[this.index];
		}

		public V setValue(V v) {
			V oldValue = Reference2ObjectOpenHashMap.this.value[this.index];
			Reference2ObjectOpenHashMap.this.value[this.index] = v;
			return oldValue;
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<K, V> e = (java.util.Map.Entry<K, V>)o;
				return Reference2ObjectOpenHashMap.this.key[this.index] == e.getKey() && Objects.equals(Reference2ObjectOpenHashMap.this.value[this.index], e.getValue());
			}
		}

		public int hashCode() {
			return System.identityHashCode(Reference2ObjectOpenHashMap.this.key[this.index])
				^ (Reference2ObjectOpenHashMap.this.value[this.index] == null ? 0 : Reference2ObjectOpenHashMap.this.value[this.index].hashCode());
		}

		public String toString() {
			return Reference2ObjectOpenHashMap.this.key[this.index] + "=>" + Reference2ObjectOpenHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> implements FastEntrySet<K, V> {
		private MapEntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> iterator() {
			return Reference2ObjectOpenHashMap.this.new EntryIterator();
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> fastIterator() {
			return Reference2ObjectOpenHashMap.this.new FastEntryIterator();
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				K k = (K)e.getKey();
				V v = (V)e.getValue();
				if (k == null) {
					return Reference2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n], v);
				} else {
					K[] key = Reference2ObjectOpenHashMap.this.key;
					K curr;
					int pos;
					if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ObjectOpenHashMap.this.mask]) == null) {
						return false;
					} else if (k == curr) {
						return Objects.equals(Reference2ObjectOpenHashMap.this.value[pos], v);
					} else {
						while ((curr = key[pos = pos + 1 & Reference2ObjectOpenHashMap.this.mask]) != null) {
							if (k == curr) {
								return Objects.equals(Reference2ObjectOpenHashMap.this.value[pos], v);
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
				if (k == null) {
					if (Reference2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n], v)) {
						Reference2ObjectOpenHashMap.this.removeNullEntry();
						return true;
					} else {
						return false;
					}
				} else {
					K[] key = Reference2ObjectOpenHashMap.this.key;
					K curr;
					int pos;
					if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ObjectOpenHashMap.this.mask]) == null) {
						return false;
					} else if (curr == k) {
						if (Objects.equals(Reference2ObjectOpenHashMap.this.value[pos], v)) {
							Reference2ObjectOpenHashMap.this.removeEntry(pos);
							return true;
						} else {
							return false;
						}
					} else {
						while ((curr = key[pos = pos + 1 & Reference2ObjectOpenHashMap.this.mask]) != null) {
							if (curr == k && Objects.equals(Reference2ObjectOpenHashMap.this.value[pos], v)) {
								Reference2ObjectOpenHashMap.this.removeEntry(pos);
								return true;
							}
						}

						return false;
					}
				}
			}
		}

		public int size() {
			return Reference2ObjectOpenHashMap.this.size;
		}

		public void clear() {
			Reference2ObjectOpenHashMap.this.clear();
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> consumer) {
			if (Reference2ObjectOpenHashMap.this.containsNullKey) {
				consumer.accept(
					new BasicEntry(
						Reference2ObjectOpenHashMap.this.key[Reference2ObjectOpenHashMap.this.n], Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n]
					)
				);
			}

			int pos = Reference2ObjectOpenHashMap.this.n;

			while (pos-- != 0) {
				if (Reference2ObjectOpenHashMap.this.key[pos] != null) {
					consumer.accept(new BasicEntry(Reference2ObjectOpenHashMap.this.key[pos], Reference2ObjectOpenHashMap.this.value[pos]));
				}
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> consumer) {
			BasicEntry<K, V> entry = new BasicEntry<>();
			if (Reference2ObjectOpenHashMap.this.containsNullKey) {
				entry.key = Reference2ObjectOpenHashMap.this.key[Reference2ObjectOpenHashMap.this.n];
				entry.value = Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n];
				consumer.accept(entry);
			}

			int pos = Reference2ObjectOpenHashMap.this.n;

			while (pos-- != 0) {
				if (Reference2ObjectOpenHashMap.this.key[pos] != null) {
					entry.key = Reference2ObjectOpenHashMap.this.key[pos];
					entry.value = Reference2ObjectOpenHashMap.this.value[pos];
					consumer.accept(entry);
				}
			}
		}
	}

	private class MapIterator {
		int pos = Reference2ObjectOpenHashMap.this.n;
		int last = -1;
		int c = Reference2ObjectOpenHashMap.this.size;
		boolean mustReturnNullKey = Reference2ObjectOpenHashMap.this.containsNullKey;
		ReferenceArrayList<K> wrapped;

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
					return this.last = Reference2ObjectOpenHashMap.this.n;
				} else {
					K[] key = Reference2ObjectOpenHashMap.this.key;

					while (--this.pos >= 0) {
						if (key[this.pos] != null) {
							return this.last = this.pos;
						}
					}

					this.last = Integer.MIN_VALUE;
					K k = this.wrapped.get(-this.pos - 1);
					int p = HashCommon.mix(System.identityHashCode(k)) & Reference2ObjectOpenHashMap.this.mask;

					while (k != key[p]) {
						p = p + 1 & Reference2ObjectOpenHashMap.this.mask;
					}

					return p;
				}
			}
		}

		private void shiftKeys(int pos) {
			K[] key = Reference2ObjectOpenHashMap.this.key;

			label38:
			while (true) {
				int last = pos;

				K curr;
				for (pos = pos + 1 & Reference2ObjectOpenHashMap.this.mask; (curr = key[pos]) != null; pos = pos + 1 & Reference2ObjectOpenHashMap.this.mask) {
					int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2ObjectOpenHashMap.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new ReferenceArrayList<>(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						Reference2ObjectOpenHashMap.this.value[last] = Reference2ObjectOpenHashMap.this.value[pos];
						continue label38;
					}
				}

				key[last] = null;
				Reference2ObjectOpenHashMap.this.value[last] = null;
				return;
			}
		}

		public void remove() {
			if (this.last == -1) {
				throw new IllegalStateException();
			} else {
				if (this.last == Reference2ObjectOpenHashMap.this.n) {
					Reference2ObjectOpenHashMap.this.containsNullKey = false;
					Reference2ObjectOpenHashMap.this.key[Reference2ObjectOpenHashMap.this.n] = null;
					Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n] = null;
				} else {
					if (this.pos < 0) {
						Reference2ObjectOpenHashMap.this.remove(this.wrapped.set(-this.pos - 1, null));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				Reference2ObjectOpenHashMap.this.size--;
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

	private final class ValueIterator extends Reference2ObjectOpenHashMap<K, V>.MapIterator implements ObjectIterator<V> {
		public ValueIterator() {
		}

		public V next() {
			return Reference2ObjectOpenHashMap.this.value[this.nextEntry()];
		}
	}
}
