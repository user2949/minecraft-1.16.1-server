package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.objects.AbstractReference2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2ObjectSortedMap.FastSortedEntrySet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Consumer;

public class Reference2ObjectLinkedOpenHashMap<K, V> extends AbstractReference2ObjectSortedMap<K, V> implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient K[] key;
	protected transient V[] value;
	protected transient int mask;
	protected transient boolean containsNullKey;
	protected transient int first = -1;
	protected transient int last = -1;
	protected transient long[] link;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;
	protected transient FastSortedEntrySet<K, V> entries;
	protected transient ReferenceSortedSet<K> keys;
	protected transient ObjectCollection<V> values;

	public Reference2ObjectLinkedOpenHashMap(int expected, float f) {
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
			this.link = new long[this.n + 1];
		}
	}

	public Reference2ObjectLinkedOpenHashMap(int expected) {
		this(expected, 0.75F);
	}

	public Reference2ObjectLinkedOpenHashMap() {
		this(16, 0.75F);
	}

	public Reference2ObjectLinkedOpenHashMap(Map<? extends K, ? extends V> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Reference2ObjectLinkedOpenHashMap(Map<? extends K, ? extends V> m) {
		this(m, 0.75F);
	}

	public Reference2ObjectLinkedOpenHashMap(Reference2ObjectMap<K, V> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Reference2ObjectLinkedOpenHashMap(Reference2ObjectMap<K, V> m) {
		this(m, 0.75F);
	}

	public Reference2ObjectLinkedOpenHashMap(K[] k, V[] v, float f) {
		this(k.length, f);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Reference2ObjectLinkedOpenHashMap(K[] k, V[] v) {
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
		this.fixPointers(pos);
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
		this.fixPointers(this.n);
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
		if (this.size == 0) {
			this.first = this.last = pos;
			this.link[pos] = -1L;
		} else {
			this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ (long)pos & 4294967295L) & 4294967295L;
			this.link[pos] = ((long)this.last & 4294967295L) << 32 | 4294967295L;
			this.last = pos;
		}

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
					this.fixPointers(pos, last);
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

	private V setValue(int pos, V v) {
		V oldValue = this.value[pos];
		this.value[pos] = v;
		return oldValue;
	}

	public V removeFirst() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			int pos = this.first;
			this.first = (int)this.link[pos];
			if (0 <= this.first) {
				this.link[this.first] = this.link[this.first] | -4294967296L;
			}

			this.size--;
			V v = this.value[pos];
			if (pos == this.n) {
				this.containsNullKey = false;
				this.key[this.n] = null;
				this.value[this.n] = null;
			} else {
				this.shiftKeys(pos);
			}

			if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
				this.rehash(this.n / 2);
			}

			return v;
		}
	}

	public V removeLast() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			int pos = this.last;
			this.last = (int)(this.link[pos] >>> 32);
			if (0 <= this.last) {
				this.link[this.last] = this.link[this.last] | 4294967295L;
			}

			this.size--;
			V v = this.value[pos];
			if (pos == this.n) {
				this.containsNullKey = false;
				this.key[this.n] = null;
				this.value[this.n] = null;
			} else {
				this.shiftKeys(pos);
			}

			if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
				this.rehash(this.n / 2);
			}

			return v;
		}
	}

	private void moveIndexToFirst(int i) {
		if (this.size != 1 && this.first != i) {
			if (this.last == i) {
				this.last = (int)(this.link[i] >>> 32);
				this.link[this.last] = this.link[this.last] | 4294967295L;
			} else {
				long linki = this.link[i];
				int prev = (int)(linki >>> 32);
				int next = (int)linki;
				this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 4294967295L) & 4294967295L;
				this.link[next] = this.link[next] ^ (this.link[next] ^ linki & -4294967296L) & -4294967296L;
			}

			this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ ((long)i & 4294967295L) << 32) & -4294967296L;
			this.link[i] = -4294967296L | (long)this.first & 4294967295L;
			this.first = i;
		}
	}

	private void moveIndexToLast(int i) {
		if (this.size != 1 && this.last != i) {
			if (this.first == i) {
				this.first = (int)this.link[i];
				this.link[this.first] = this.link[this.first] | -4294967296L;
			} else {
				long linki = this.link[i];
				int prev = (int)(linki >>> 32);
				int next = (int)linki;
				this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 4294967295L) & 4294967295L;
				this.link[next] = this.link[next] ^ (this.link[next] ^ linki & -4294967296L) & -4294967296L;
			}

			this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ (long)i & 4294967295L) & 4294967295L;
			this.link[i] = ((long)this.last & 4294967295L) << 32 | 4294967295L;
			this.last = i;
		}
	}

	public V getAndMoveToFirst(K k) {
		if (k == null) {
			if (this.containsNullKey) {
				this.moveIndexToFirst(this.n);
				return this.value[this.n];
			} else {
				return this.defRetValue;
			}
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
				return this.defRetValue;
			} else if (k == curr) {
				this.moveIndexToFirst(pos);
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (k == curr) {
						this.moveIndexToFirst(pos);
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	public V getAndMoveToLast(K k) {
		if (k == null) {
			if (this.containsNullKey) {
				this.moveIndexToLast(this.n);
				return this.value[this.n];
			} else {
				return this.defRetValue;
			}
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
				return this.defRetValue;
			} else if (k == curr) {
				this.moveIndexToLast(pos);
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (k == curr) {
						this.moveIndexToLast(pos);
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	public V putAndMoveToFirst(K k, V v) {
		int pos;
		if (k == null) {
			if (this.containsNullKey) {
				this.moveIndexToFirst(this.n);
				return this.setValue(this.n, v);
			}

			this.containsNullKey = true;
			pos = this.n;
		} else {
			K[] key = this.key;
			K curr;
			if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
				if (curr == k) {
					this.moveIndexToFirst(pos);
					return this.setValue(pos, v);
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (curr == k) {
						this.moveIndexToFirst(pos);
						return this.setValue(pos, v);
					}
				}
			}
		}

		this.key[pos] = k;
		this.value[pos] = v;
		if (this.size == 0) {
			this.first = this.last = pos;
			this.link[pos] = -1L;
		} else {
			this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ ((long)pos & 4294967295L) << 32) & -4294967296L;
			this.link[pos] = -4294967296L | (long)this.first & 4294967295L;
			this.first = pos;
		}

		if (this.size++ >= this.maxFill) {
			this.rehash(HashCommon.arraySize(this.size, this.f));
		}

		return this.defRetValue;
	}

	public V putAndMoveToLast(K k, V v) {
		int pos;
		if (k == null) {
			if (this.containsNullKey) {
				this.moveIndexToLast(this.n);
				return this.setValue(this.n, v);
			}

			this.containsNullKey = true;
			pos = this.n;
		} else {
			K[] key = this.key;
			K curr;
			if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
				if (curr == k) {
					this.moveIndexToLast(pos);
					return this.setValue(pos, v);
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (curr == k) {
						this.moveIndexToLast(pos);
						return this.setValue(pos, v);
					}
				}
			}
		}

		this.key[pos] = k;
		this.value[pos] = v;
		if (this.size == 0) {
			this.first = this.last = pos;
			this.link[pos] = -1L;
		} else {
			this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ (long)pos & 4294967295L) & 4294967295L;
			this.link[pos] = ((long)this.last & 4294967295L) << 32 | 4294967295L;
			this.last = pos;
		}

		if (this.size++ >= this.maxFill) {
			this.rehash(HashCommon.arraySize(this.size, this.f));
		}

		return this.defRetValue;
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
			this.first = this.last = -1;
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

	protected void fixPointers(int i) {
		if (this.size == 0) {
			this.first = this.last = -1;
		} else if (this.first == i) {
			this.first = (int)this.link[i];
			if (0 <= this.first) {
				this.link[this.first] = this.link[this.first] | -4294967296L;
			}
		} else if (this.last == i) {
			this.last = (int)(this.link[i] >>> 32);
			if (0 <= this.last) {
				this.link[this.last] = this.link[this.last] | 4294967295L;
			}
		} else {
			long linki = this.link[i];
			int prev = (int)(linki >>> 32);
			int next = (int)linki;
			this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 4294967295L) & 4294967295L;
			this.link[next] = this.link[next] ^ (this.link[next] ^ linki & -4294967296L) & -4294967296L;
		}
	}

	protected void fixPointers(int s, int d) {
		if (this.size == 1) {
			this.first = this.last = d;
			this.link[d] = -1L;
		} else if (this.first == s) {
			this.first = d;
			this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ ((long)d & 4294967295L) << 32) & -4294967296L;
			this.link[d] = this.link[s];
		} else if (this.last == s) {
			this.last = d;
			this.link[(int)(this.link[s] >>> 32)] = this.link[(int)(this.link[s] >>> 32)]
				^ (this.link[(int)(this.link[s] >>> 32)] ^ (long)d & 4294967295L) & 4294967295L;
			this.link[d] = this.link[s];
		} else {
			long links = this.link[s];
			int prev = (int)(links >>> 32);
			int next = (int)links;
			this.link[prev] = this.link[prev] ^ (this.link[prev] ^ (long)d & 4294967295L) & 4294967295L;
			this.link[next] = this.link[next] ^ (this.link[next] ^ ((long)d & 4294967295L) << 32) & -4294967296L;
			this.link[d] = links;
		}
	}

	public K firstKey() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			return this.key[this.first];
		}
	}

	public K lastKey() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			return this.key[this.last];
		}
	}

	@Override
	public Reference2ObjectSortedMap<K, V> tailMap(K from) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reference2ObjectSortedMap<K, V> headMap(K to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reference2ObjectSortedMap<K, V> subMap(K from, K to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Comparator<? super K> comparator() {
		return null;
	}

	public FastSortedEntrySet<K, V> reference2ObjectEntrySet() {
		if (this.entries == null) {
			this.entries = new Reference2ObjectLinkedOpenHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public ReferenceSortedSet<K> keySet() {
		if (this.keys == null) {
			this.keys = new Reference2ObjectLinkedOpenHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public ObjectCollection<V> values() {
		if (this.values == null) {
			this.values = new AbstractObjectCollection<V>() {
				@Override
				public ObjectIterator<V> iterator() {
					return Reference2ObjectLinkedOpenHashMap.this.new ValueIterator();
				}

				public int size() {
					return Reference2ObjectLinkedOpenHashMap.this.size;
				}

				public boolean contains(Object v) {
					return Reference2ObjectLinkedOpenHashMap.this.containsValue(v);
				}

				public void clear() {
					Reference2ObjectLinkedOpenHashMap.this.clear();
				}

				public void forEach(Consumer<? super V> consumer) {
					if (Reference2ObjectLinkedOpenHashMap.this.containsNullKey) {
						consumer.accept(Reference2ObjectLinkedOpenHashMap.this.value[Reference2ObjectLinkedOpenHashMap.this.n]);
					}

					int pos = Reference2ObjectLinkedOpenHashMap.this.n;

					while (pos-- != 0) {
						if (Reference2ObjectLinkedOpenHashMap.this.key[pos] != null) {
							consumer.accept(Reference2ObjectLinkedOpenHashMap.this.value[pos]);
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
		int i = this.first;
		int prev = -1;
		int newPrev = -1;
		long[] link = this.link;
		long[] newLink = new long[newN + 1];
		this.first = -1;
		int j = this.size;

		while (j-- != 0) {
			int pos;
			if (key[i] == null) {
				pos = newN;
			} else {
				pos = HashCommon.mix(System.identityHashCode(key[i])) & mask;

				while (newKey[pos] != null) {
					pos = pos + 1 & mask;
				}
			}

			newKey[pos] = key[i];
			newValue[pos] = value[i];
			if (prev != -1) {
				newLink[newPrev] ^= (newLink[newPrev] ^ (long)pos & 4294967295L) & 4294967295L;
				newLink[pos] ^= (newLink[pos] ^ ((long)newPrev & 4294967295L) << 32) & -4294967296L;
				newPrev = pos;
			} else {
				newPrev = this.first = pos;
				newLink[pos] = -1L;
			}

			int t = i;
			i = (int)link[i];
			prev = t;
		}

		this.link = newLink;
		this.last = newPrev;
		if (newPrev != -1) {
			newLink[newPrev] |= 4294967295L;
		}

		this.n = newN;
		this.mask = mask;
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.key = newKey;
		this.value = newValue;
	}

	public Reference2ObjectLinkedOpenHashMap<K, V> clone() {
		Reference2ObjectLinkedOpenHashMap<K, V> c;
		try {
			c = (Reference2ObjectLinkedOpenHashMap<K, V>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (K[])((Object[])this.key.clone());
		c.value = (V[])((Object[])this.value.clone());
		c.link = (long[])this.link.clone();
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
		Reference2ObjectLinkedOpenHashMap<K, V>.MapIterator i = new Reference2ObjectLinkedOpenHashMap.MapIterator();
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
		long[] link = this.link = new long[this.n + 1];
		int prev = -1;
		this.first = this.last = -1;
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
			if (this.first != -1) {
				link[prev] ^= (link[prev] ^ (long)pos & 4294967295L) & 4294967295L;
				link[pos] ^= (link[pos] ^ ((long)prev & 4294967295L) << 32) & -4294967296L;
				prev = pos;
			} else {
				prev = this.first = pos;
				link[pos] |= -4294967296L;
			}
		}

		this.last = prev;
		if (prev != -1) {
			link[prev] |= 4294967295L;
		}
	}

	private void checkTable() {
	}

	private class EntryIterator
		extends Reference2ObjectLinkedOpenHashMap<K, V>.MapIterator
		implements ObjectListIterator<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> {
		private Reference2ObjectLinkedOpenHashMap<K, V>.MapEntry entry;

		public EntryIterator() {
		}

		public EntryIterator(K from) {
			super(from);
		}

		public Reference2ObjectLinkedOpenHashMap<K, V>.MapEntry next() {
			return this.entry = Reference2ObjectLinkedOpenHashMap.this.new MapEntry(this.nextEntry());
		}

		public Reference2ObjectLinkedOpenHashMap<K, V>.MapEntry previous() {
			return this.entry = Reference2ObjectLinkedOpenHashMap.this.new MapEntry(this.previousEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator
		extends Reference2ObjectLinkedOpenHashMap<K, V>.MapIterator
		implements ObjectListIterator<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> {
		final Reference2ObjectLinkedOpenHashMap<K, V>.MapEntry entry;

		public FastEntryIterator() {
			this.entry = Reference2ObjectLinkedOpenHashMap.this.new MapEntry();
		}

		public FastEntryIterator(K from) {
			super(from);
			this.entry = Reference2ObjectLinkedOpenHashMap.this.new MapEntry();
		}

		public Reference2ObjectLinkedOpenHashMap<K, V>.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}

		public Reference2ObjectLinkedOpenHashMap<K, V>.MapEntry previous() {
			this.entry.index = this.previousEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Reference2ObjectLinkedOpenHashMap<K, V>.MapIterator implements ObjectListIterator<K> {
		public KeyIterator(K k) {
			super(k);
		}

		@Override
		public K previous() {
			return Reference2ObjectLinkedOpenHashMap.this.key[this.previousEntry()];
		}

		public KeyIterator() {
		}

		public K next() {
			return Reference2ObjectLinkedOpenHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractReferenceSortedSet<K> {
		private KeySet() {
		}

		public ObjectListIterator<K> iterator(K from) {
			return Reference2ObjectLinkedOpenHashMap.this.new KeyIterator(from);
		}

		public ObjectListIterator<K> iterator() {
			return Reference2ObjectLinkedOpenHashMap.this.new KeyIterator();
		}

		public void forEach(Consumer<? super K> consumer) {
			if (Reference2ObjectLinkedOpenHashMap.this.containsNullKey) {
				consumer.accept(Reference2ObjectLinkedOpenHashMap.this.key[Reference2ObjectLinkedOpenHashMap.this.n]);
			}

			int pos = Reference2ObjectLinkedOpenHashMap.this.n;

			while (pos-- != 0) {
				K k = Reference2ObjectLinkedOpenHashMap.this.key[pos];
				if (k != null) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Reference2ObjectLinkedOpenHashMap.this.size;
		}

		public boolean contains(Object k) {
			return Reference2ObjectLinkedOpenHashMap.this.containsKey(k);
		}

		public boolean remove(Object k) {
			int oldSize = Reference2ObjectLinkedOpenHashMap.this.size;
			Reference2ObjectLinkedOpenHashMap.this.remove(k);
			return Reference2ObjectLinkedOpenHashMap.this.size != oldSize;
		}

		public void clear() {
			Reference2ObjectLinkedOpenHashMap.this.clear();
		}

		public K first() {
			if (Reference2ObjectLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Reference2ObjectLinkedOpenHashMap.this.key[Reference2ObjectLinkedOpenHashMap.this.first];
			}
		}

		public K last() {
			if (Reference2ObjectLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Reference2ObjectLinkedOpenHashMap.this.key[Reference2ObjectLinkedOpenHashMap.this.last];
			}
		}

		public Comparator<? super K> comparator() {
			return null;
		}

		@Override
		public ReferenceSortedSet<K> tailSet(K from) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ReferenceSortedSet<K> headSet(K to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ReferenceSortedSet<K> subSet(K from, K to) {
			throw new UnsupportedOperationException();
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
			return Reference2ObjectLinkedOpenHashMap.this.key[this.index];
		}

		public V getValue() {
			return Reference2ObjectLinkedOpenHashMap.this.value[this.index];
		}

		public V setValue(V v) {
			V oldValue = Reference2ObjectLinkedOpenHashMap.this.value[this.index];
			Reference2ObjectLinkedOpenHashMap.this.value[this.index] = v;
			return oldValue;
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<K, V> e = (java.util.Map.Entry<K, V>)o;
				return Reference2ObjectLinkedOpenHashMap.this.key[this.index] == e.getKey()
					&& Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[this.index], e.getValue());
			}
		}

		public int hashCode() {
			return System.identityHashCode(Reference2ObjectLinkedOpenHashMap.this.key[this.index])
				^ (Reference2ObjectLinkedOpenHashMap.this.value[this.index] == null ? 0 : Reference2ObjectLinkedOpenHashMap.this.value[this.index].hashCode());
		}

		public String toString() {
			return Reference2ObjectLinkedOpenHashMap.this.key[this.index] + "=>" + Reference2ObjectLinkedOpenHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet
		extends AbstractObjectSortedSet<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>>
		implements FastSortedEntrySet<K, V> {
		private MapEntrySet() {
		}

		@Override
		public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> iterator() {
			return Reference2ObjectLinkedOpenHashMap.this.new EntryIterator();
		}

		public Comparator<? super it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> comparator() {
			return null;
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> subSet(
			it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V> fromElement, it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V> toElement
		) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> headSet(
			it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V> toElement
		) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> tailSet(
			it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V> fromElement
		) {
			throw new UnsupportedOperationException();
		}

		public it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V> first() {
			if (Reference2ObjectLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Reference2ObjectLinkedOpenHashMap.this.new MapEntry(Reference2ObjectLinkedOpenHashMap.this.first);
			}
		}

		public it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V> last() {
			if (Reference2ObjectLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Reference2ObjectLinkedOpenHashMap.this.new MapEntry(Reference2ObjectLinkedOpenHashMap.this.last);
			}
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				K k = (K)e.getKey();
				V v = (V)e.getValue();
				if (k == null) {
					return Reference2ObjectLinkedOpenHashMap.this.containsNullKey
						&& Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[Reference2ObjectLinkedOpenHashMap.this.n], v);
				} else {
					K[] key = Reference2ObjectLinkedOpenHashMap.this.key;
					K curr;
					int pos;
					if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ObjectLinkedOpenHashMap.this.mask]) == null) {
						return false;
					} else if (k == curr) {
						return Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[pos], v);
					} else {
						while ((curr = key[pos = pos + 1 & Reference2ObjectLinkedOpenHashMap.this.mask]) != null) {
							if (k == curr) {
								return Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[pos], v);
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
					if (Reference2ObjectLinkedOpenHashMap.this.containsNullKey
						&& Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[Reference2ObjectLinkedOpenHashMap.this.n], v)) {
						Reference2ObjectLinkedOpenHashMap.this.removeNullEntry();
						return true;
					} else {
						return false;
					}
				} else {
					K[] key = Reference2ObjectLinkedOpenHashMap.this.key;
					K curr;
					int pos;
					if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ObjectLinkedOpenHashMap.this.mask]) == null) {
						return false;
					} else if (curr == k) {
						if (Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[pos], v)) {
							Reference2ObjectLinkedOpenHashMap.this.removeEntry(pos);
							return true;
						} else {
							return false;
						}
					} else {
						while ((curr = key[pos = pos + 1 & Reference2ObjectLinkedOpenHashMap.this.mask]) != null) {
							if (curr == k && Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[pos], v)) {
								Reference2ObjectLinkedOpenHashMap.this.removeEntry(pos);
								return true;
							}
						}

						return false;
					}
				}
			}
		}

		public int size() {
			return Reference2ObjectLinkedOpenHashMap.this.size;
		}

		public void clear() {
			Reference2ObjectLinkedOpenHashMap.this.clear();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> iterator(
			it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V> from
		) {
			return Reference2ObjectLinkedOpenHashMap.this.new EntryIterator(from.getKey());
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> fastIterator() {
			return Reference2ObjectLinkedOpenHashMap.this.new FastEntryIterator();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> fastIterator(
			it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V> from
		) {
			return Reference2ObjectLinkedOpenHashMap.this.new FastEntryIterator(from.getKey());
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> consumer) {
			int i = Reference2ObjectLinkedOpenHashMap.this.size;
			int next = Reference2ObjectLinkedOpenHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Reference2ObjectLinkedOpenHashMap.this.link[next];
				consumer.accept(new BasicEntry(Reference2ObjectLinkedOpenHashMap.this.key[curr], Reference2ObjectLinkedOpenHashMap.this.value[curr]));
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> consumer) {
			BasicEntry<K, V> entry = new BasicEntry<>();
			int i = Reference2ObjectLinkedOpenHashMap.this.size;
			int next = Reference2ObjectLinkedOpenHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Reference2ObjectLinkedOpenHashMap.this.link[next];
				entry.key = Reference2ObjectLinkedOpenHashMap.this.key[curr];
				entry.value = Reference2ObjectLinkedOpenHashMap.this.value[curr];
				consumer.accept(entry);
			}
		}
	}

	private class MapIterator {
		int prev = -1;
		int next = -1;
		int curr = -1;
		int index = -1;

		protected MapIterator() {
			this.next = Reference2ObjectLinkedOpenHashMap.this.first;
			this.index = 0;
		}

		private MapIterator(K from) {
			if (from == null) {
				if (Reference2ObjectLinkedOpenHashMap.this.containsNullKey) {
					this.next = (int)Reference2ObjectLinkedOpenHashMap.this.link[Reference2ObjectLinkedOpenHashMap.this.n];
					this.prev = Reference2ObjectLinkedOpenHashMap.this.n;
				} else {
					throw new NoSuchElementException("The key " + from + " does not belong to this map.");
				}
			} else if (Reference2ObjectLinkedOpenHashMap.this.key[Reference2ObjectLinkedOpenHashMap.this.last] == from) {
				this.prev = Reference2ObjectLinkedOpenHashMap.this.last;
				this.index = Reference2ObjectLinkedOpenHashMap.this.size;
			} else {
				for (int pos = HashCommon.mix(System.identityHashCode(from)) & Reference2ObjectLinkedOpenHashMap.this.mask;
					Reference2ObjectLinkedOpenHashMap.this.key[pos] != null;
					pos = pos + 1 & Reference2ObjectLinkedOpenHashMap.this.mask
				) {
					if (Reference2ObjectLinkedOpenHashMap.this.key[pos] == from) {
						this.next = (int)Reference2ObjectLinkedOpenHashMap.this.link[pos];
						this.prev = pos;
						return;
					}
				}

				throw new NoSuchElementException("The key " + from + " does not belong to this map.");
			}
		}

		public boolean hasNext() {
			return this.next != -1;
		}

		public boolean hasPrevious() {
			return this.prev != -1;
		}

		private final void ensureIndexKnown() {
			if (this.index < 0) {
				if (this.prev == -1) {
					this.index = 0;
				} else if (this.next == -1) {
					this.index = Reference2ObjectLinkedOpenHashMap.this.size;
				} else {
					int pos = Reference2ObjectLinkedOpenHashMap.this.first;

					for (this.index = 1; pos != this.prev; this.index++) {
						pos = (int)Reference2ObjectLinkedOpenHashMap.this.link[pos];
					}
				}
			}
		}

		public int nextIndex() {
			this.ensureIndexKnown();
			return this.index;
		}

		public int previousIndex() {
			this.ensureIndexKnown();
			return this.index - 1;
		}

		public int nextEntry() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.curr = this.next;
				this.next = (int)Reference2ObjectLinkedOpenHashMap.this.link[this.curr];
				this.prev = this.curr;
				if (this.index >= 0) {
					this.index++;
				}

				return this.curr;
			}
		}

		public int previousEntry() {
			if (!this.hasPrevious()) {
				throw new NoSuchElementException();
			} else {
				this.curr = this.prev;
				this.prev = (int)(Reference2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32);
				this.next = this.curr;
				if (this.index >= 0) {
					this.index--;
				}

				return this.curr;
			}
		}

		public void remove() {
			this.ensureIndexKnown();
			if (this.curr == -1) {
				throw new IllegalStateException();
			} else {
				if (this.curr == this.prev) {
					this.index--;
					this.prev = (int)(Reference2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32);
				} else {
					this.next = (int)Reference2ObjectLinkedOpenHashMap.this.link[this.curr];
				}

				Reference2ObjectLinkedOpenHashMap.this.size--;
				if (this.prev == -1) {
					Reference2ObjectLinkedOpenHashMap.this.first = this.next;
				} else {
					Reference2ObjectLinkedOpenHashMap.this.link[this.prev] = Reference2ObjectLinkedOpenHashMap.this.link[this.prev]
						^ (Reference2ObjectLinkedOpenHashMap.this.link[this.prev] ^ (long)this.next & 4294967295L) & 4294967295L;
				}

				if (this.next == -1) {
					Reference2ObjectLinkedOpenHashMap.this.last = this.prev;
				} else {
					Reference2ObjectLinkedOpenHashMap.this.link[this.next] = Reference2ObjectLinkedOpenHashMap.this.link[this.next]
						^ (Reference2ObjectLinkedOpenHashMap.this.link[this.next] ^ ((long)this.prev & 4294967295L) << 32) & -4294967296L;
				}

				int pos = this.curr;
				this.curr = -1;
				if (pos == Reference2ObjectLinkedOpenHashMap.this.n) {
					Reference2ObjectLinkedOpenHashMap.this.containsNullKey = false;
					Reference2ObjectLinkedOpenHashMap.this.key[Reference2ObjectLinkedOpenHashMap.this.n] = null;
					Reference2ObjectLinkedOpenHashMap.this.value[Reference2ObjectLinkedOpenHashMap.this.n] = null;
				} else {
					K[] key = Reference2ObjectLinkedOpenHashMap.this.key;

					label61:
					while (true) {
						int last = pos;

						K curr;
						for (pos = pos + 1 & Reference2ObjectLinkedOpenHashMap.this.mask; (curr = key[pos]) != null; pos = pos + 1 & Reference2ObjectLinkedOpenHashMap.this.mask) {
							int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2ObjectLinkedOpenHashMap.this.mask;
							if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
								key[last] = curr;
								Reference2ObjectLinkedOpenHashMap.this.value[last] = Reference2ObjectLinkedOpenHashMap.this.value[pos];
								if (this.next == pos) {
									this.next = last;
								}

								if (this.prev == pos) {
									this.prev = last;
								}

								Reference2ObjectLinkedOpenHashMap.this.fixPointers(pos, last);
								continue label61;
							}
						}

						key[last] = null;
						Reference2ObjectLinkedOpenHashMap.this.value[last] = null;
						return;
					}
				}
			}
		}

		public int skip(int n) {
			int i = n;

			while (i-- != 0 && this.hasNext()) {
				this.nextEntry();
			}

			return n - i - 1;
		}

		public int back(int n) {
			int i = n;

			while (i-- != 0 && this.hasPrevious()) {
				this.previousEntry();
			}

			return n - i - 1;
		}

		public void set(it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V> ok) {
			throw new UnsupportedOperationException();
		}

		public void add(it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V> ok) {
			throw new UnsupportedOperationException();
		}
	}

	private final class ValueIterator extends Reference2ObjectLinkedOpenHashMap<K, V>.MapIterator implements ObjectListIterator<V> {
		@Override
		public V previous() {
			return Reference2ObjectLinkedOpenHashMap.this.value[this.previousEntry()];
		}

		public ValueIterator() {
		}

		public V next() {
			return Reference2ObjectLinkedOpenHashMap.this.value[this.nextEntry()];
		}
	}
}
