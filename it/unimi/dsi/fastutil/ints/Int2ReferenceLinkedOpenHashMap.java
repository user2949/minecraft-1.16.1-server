package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.ints.AbstractInt2ReferenceMap.BasicEntry;
import it.unimi.dsi.fastutil.ints.Int2ReferenceSortedMap.FastSortedEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntFunction;

public class Int2ReferenceLinkedOpenHashMap<V> extends AbstractInt2ReferenceSortedMap<V> implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient int[] key;
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
	protected transient FastSortedEntrySet<V> entries;
	protected transient IntSortedSet keys;
	protected transient ReferenceCollection<V> values;

	public Int2ReferenceLinkedOpenHashMap(int expected, float f) {
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
			this.value = (V[])(new Object[this.n + 1]);
			this.link = new long[this.n + 1];
		}
	}

	public Int2ReferenceLinkedOpenHashMap(int expected) {
		this(expected, 0.75F);
	}

	public Int2ReferenceLinkedOpenHashMap() {
		this(16, 0.75F);
	}

	public Int2ReferenceLinkedOpenHashMap(Map<? extends Integer, ? extends V> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Int2ReferenceLinkedOpenHashMap(Map<? extends Integer, ? extends V> m) {
		this(m, 0.75F);
	}

	public Int2ReferenceLinkedOpenHashMap(Int2ReferenceMap<V> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Int2ReferenceLinkedOpenHashMap(Int2ReferenceMap<V> m) {
		this(m, 0.75F);
	}

	public Int2ReferenceLinkedOpenHashMap(int[] k, V[] v, float f) {
		this(k.length, f);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Int2ReferenceLinkedOpenHashMap(int[] k, V[] v) {
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
	public void putAll(Map<? extends Integer, ? extends V> m) {
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

	private void insert(int pos, int k, V v) {
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
	public V put(int k, V v) {
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
					this.fixPointers(pos, last);
					continue label30;
				}
			}

			key[last] = 0;
			this.value[last] = null;
			return;
		}
	}

	@Override
	public V remove(int k) {
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

	public V getAndMoveToFirst(int k) {
		if (k == 0) {
			if (this.containsNullKey) {
				this.moveIndexToFirst(this.n);
				return this.value[this.n];
			} else {
				return this.defRetValue;
			}
		} else {
			int[] key = this.key;
			int curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
				return this.defRetValue;
			} else if (k == curr) {
				this.moveIndexToFirst(pos);
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (k == curr) {
						this.moveIndexToFirst(pos);
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	public V getAndMoveToLast(int k) {
		if (k == 0) {
			if (this.containsNullKey) {
				this.moveIndexToLast(this.n);
				return this.value[this.n];
			} else {
				return this.defRetValue;
			}
		} else {
			int[] key = this.key;
			int curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
				return this.defRetValue;
			} else if (k == curr) {
				this.moveIndexToLast(pos);
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (k == curr) {
						this.moveIndexToLast(pos);
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	public V putAndMoveToFirst(int k, V v) {
		int pos;
		if (k == 0) {
			if (this.containsNullKey) {
				this.moveIndexToFirst(this.n);
				return this.setValue(this.n, v);
			}

			this.containsNullKey = true;
			pos = this.n;
		} else {
			int[] key = this.key;
			int curr;
			if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
				if (curr == k) {
					this.moveIndexToFirst(pos);
					return this.setValue(pos, v);
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
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

	public V putAndMoveToLast(int k, V v) {
		int pos;
		if (k == 0) {
			if (this.containsNullKey) {
				this.moveIndexToLast(this.n);
				return this.setValue(this.n, v);
			}

			this.containsNullKey = true;
			pos = this.n;
		} else {
			int[] key = this.key;
			int curr;
			if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
				if (curr == k) {
					this.moveIndexToLast(pos);
					return this.setValue(pos, v);
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
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
	public V get(int k) {
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
	public boolean containsValue(Object v) {
		V[] value = this.value;
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
	public V getOrDefault(int k, V defaultValue) {
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
	public V putIfAbsent(int k, V v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(int k, Object v) {
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
	public boolean replace(int k, V oldValue, V v) {
		int pos = this.find(k);
		if (pos >= 0 && oldValue == this.value[pos]) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public V replace(int k, V v) {
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
	public V computeIfAbsent(int k, IntFunction<? extends V> mappingFunction) {
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
	public V computeIfPresent(int k, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
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
	public V compute(int k, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
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
	public V merge(int k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
			Arrays.fill(this.key, 0);
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

	@Override
	public int firstIntKey() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			return this.key[this.first];
		}
	}

	@Override
	public int lastIntKey() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			return this.key[this.last];
		}
	}

	@Override
	public Int2ReferenceSortedMap<V> tailMap(int from) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Int2ReferenceSortedMap<V> headMap(int to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Int2ReferenceSortedMap<V> subMap(int from, int to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IntComparator comparator() {
		return null;
	}

	public FastSortedEntrySet<V> int2ReferenceEntrySet() {
		if (this.entries == null) {
			this.entries = new Int2ReferenceLinkedOpenHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public IntSortedSet keySet() {
		if (this.keys == null) {
			this.keys = new Int2ReferenceLinkedOpenHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public ReferenceCollection<V> values() {
		if (this.values == null) {
			this.values = new AbstractReferenceCollection<V>() {
				@Override
				public ObjectIterator<V> iterator() {
					return Int2ReferenceLinkedOpenHashMap.this.new ValueIterator();
				}

				public int size() {
					return Int2ReferenceLinkedOpenHashMap.this.size;
				}

				public boolean contains(Object v) {
					return Int2ReferenceLinkedOpenHashMap.this.containsValue(v);
				}

				public void clear() {
					Int2ReferenceLinkedOpenHashMap.this.clear();
				}

				public void forEach(Consumer<? super V> consumer) {
					if (Int2ReferenceLinkedOpenHashMap.this.containsNullKey) {
						consumer.accept(Int2ReferenceLinkedOpenHashMap.this.value[Int2ReferenceLinkedOpenHashMap.this.n]);
					}

					int pos = Int2ReferenceLinkedOpenHashMap.this.n;

					while (pos-- != 0) {
						if (Int2ReferenceLinkedOpenHashMap.this.key[pos] != 0) {
							consumer.accept(Int2ReferenceLinkedOpenHashMap.this.value[pos]);
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
		V[] value = this.value;
		int mask = newN - 1;
		int[] newKey = new int[newN + 1];
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
			if (key[i] == 0) {
				pos = newN;
			} else {
				pos = HashCommon.mix(key[i]) & mask;

				while (newKey[pos] != 0) {
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

	public Int2ReferenceLinkedOpenHashMap<V> clone() {
		Int2ReferenceLinkedOpenHashMap<V> c;
		try {
			c = (Int2ReferenceLinkedOpenHashMap<V>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (int[])this.key.clone();
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
			while (this.key[i] == 0) {
				i++;
			}

			t = this.key[i];
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
		int[] key = this.key;
		V[] value = this.value;
		Int2ReferenceLinkedOpenHashMap<V>.MapIterator i = new Int2ReferenceLinkedOpenHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeInt(key[e]);
			s.writeObject(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		int[] key = this.key = new int[this.n + 1];
		V[] value = this.value = (V[])(new Object[this.n + 1]);
		long[] link = this.link = new long[this.n + 1];
		int prev = -1;
		this.first = this.last = -1;
		int i = this.size;

		while (i-- != 0) {
			int k = s.readInt();
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
		extends Int2ReferenceLinkedOpenHashMap<V>.MapIterator
		implements ObjectListIterator<it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V>> {
		private Int2ReferenceLinkedOpenHashMap<V>.MapEntry entry;

		public EntryIterator() {
		}

		public EntryIterator(int from) {
			super(from);
		}

		public Int2ReferenceLinkedOpenHashMap<V>.MapEntry next() {
			return this.entry = Int2ReferenceLinkedOpenHashMap.this.new MapEntry(this.nextEntry());
		}

		public Int2ReferenceLinkedOpenHashMap<V>.MapEntry previous() {
			return this.entry = Int2ReferenceLinkedOpenHashMap.this.new MapEntry(this.previousEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator
		extends Int2ReferenceLinkedOpenHashMap<V>.MapIterator
		implements ObjectListIterator<it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V>> {
		final Int2ReferenceLinkedOpenHashMap<V>.MapEntry entry;

		public FastEntryIterator() {
			this.entry = Int2ReferenceLinkedOpenHashMap.this.new MapEntry();
		}

		public FastEntryIterator(int from) {
			super(from);
			this.entry = Int2ReferenceLinkedOpenHashMap.this.new MapEntry();
		}

		public Int2ReferenceLinkedOpenHashMap<V>.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}

		public Int2ReferenceLinkedOpenHashMap<V>.MapEntry previous() {
			this.entry.index = this.previousEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Int2ReferenceLinkedOpenHashMap<V>.MapIterator implements IntListIterator {
		public KeyIterator(int k) {
			super(k);
		}

		@Override
		public int previousInt() {
			return Int2ReferenceLinkedOpenHashMap.this.key[this.previousEntry()];
		}

		public KeyIterator() {
		}

		@Override
		public int nextInt() {
			return Int2ReferenceLinkedOpenHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractIntSortedSet {
		private KeySet() {
		}

		public IntListIterator iterator(int from) {
			return Int2ReferenceLinkedOpenHashMap.this.new KeyIterator(from);
		}

		public IntListIterator iterator() {
			return Int2ReferenceLinkedOpenHashMap.this.new KeyIterator();
		}

		@Override
		public void forEach(java.util.function.IntConsumer consumer) {
			if (Int2ReferenceLinkedOpenHashMap.this.containsNullKey) {
				consumer.accept(Int2ReferenceLinkedOpenHashMap.this.key[Int2ReferenceLinkedOpenHashMap.this.n]);
			}

			int pos = Int2ReferenceLinkedOpenHashMap.this.n;

			while (pos-- != 0) {
				int k = Int2ReferenceLinkedOpenHashMap.this.key[pos];
				if (k != 0) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Int2ReferenceLinkedOpenHashMap.this.size;
		}

		@Override
		public boolean contains(int k) {
			return Int2ReferenceLinkedOpenHashMap.this.containsKey(k);
		}

		@Override
		public boolean remove(int k) {
			int oldSize = Int2ReferenceLinkedOpenHashMap.this.size;
			Int2ReferenceLinkedOpenHashMap.this.remove(k);
			return Int2ReferenceLinkedOpenHashMap.this.size != oldSize;
		}

		public void clear() {
			Int2ReferenceLinkedOpenHashMap.this.clear();
		}

		@Override
		public int firstInt() {
			if (Int2ReferenceLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Int2ReferenceLinkedOpenHashMap.this.key[Int2ReferenceLinkedOpenHashMap.this.first];
			}
		}

		@Override
		public int lastInt() {
			if (Int2ReferenceLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Int2ReferenceLinkedOpenHashMap.this.key[Int2ReferenceLinkedOpenHashMap.this.last];
			}
		}

		@Override
		public IntComparator comparator() {
			return null;
		}

		@Override
		public IntSortedSet tailSet(int from) {
			throw new UnsupportedOperationException();
		}

		@Override
		public IntSortedSet headSet(int to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public IntSortedSet subSet(int from, int to) {
			throw new UnsupportedOperationException();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V>, java.util.Map.Entry<Integer, V> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		@Override
		public int getIntKey() {
			return Int2ReferenceLinkedOpenHashMap.this.key[this.index];
		}

		public V getValue() {
			return Int2ReferenceLinkedOpenHashMap.this.value[this.index];
		}

		public V setValue(V v) {
			V oldValue = Int2ReferenceLinkedOpenHashMap.this.value[this.index];
			Int2ReferenceLinkedOpenHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Integer getKey() {
			return Int2ReferenceLinkedOpenHashMap.this.key[this.index];
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<Integer, V> e = (java.util.Map.Entry<Integer, V>)o;
				return Int2ReferenceLinkedOpenHashMap.this.key[this.index] == (Integer)e.getKey() && Int2ReferenceLinkedOpenHashMap.this.value[this.index] == e.getValue();
			}
		}

		public int hashCode() {
			return Int2ReferenceLinkedOpenHashMap.this.key[this.index]
				^ (Int2ReferenceLinkedOpenHashMap.this.value[this.index] == null ? 0 : System.identityHashCode(Int2ReferenceLinkedOpenHashMap.this.value[this.index]));
		}

		public String toString() {
			return Int2ReferenceLinkedOpenHashMap.this.key[this.index] + "=>" + Int2ReferenceLinkedOpenHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSortedSet<it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V>> implements FastSortedEntrySet<V> {
		private MapEntrySet() {
		}

		@Override
		public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V>> iterator() {
			return Int2ReferenceLinkedOpenHashMap.this.new EntryIterator();
		}

		public Comparator<? super it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V>> comparator() {
			return null;
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V>> subSet(
			it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V> fromElement, it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V> toElement
		) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V>> headSet(it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V> toElement) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V>> tailSet(it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V> fromElement) {
			throw new UnsupportedOperationException();
		}

		public it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V> first() {
			if (Int2ReferenceLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Int2ReferenceLinkedOpenHashMap.this.new MapEntry(Int2ReferenceLinkedOpenHashMap.this.first);
			}
		}

		public it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V> last() {
			if (Int2ReferenceLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Int2ReferenceLinkedOpenHashMap.this.new MapEntry(Int2ReferenceLinkedOpenHashMap.this.last);
			}
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() != null && e.getKey() instanceof Integer) {
					int k = (Integer)e.getKey();
					V v = (V)e.getValue();
					if (k == 0) {
						return Int2ReferenceLinkedOpenHashMap.this.containsNullKey && Int2ReferenceLinkedOpenHashMap.this.value[Int2ReferenceLinkedOpenHashMap.this.n] == v;
					} else {
						int[] key = Int2ReferenceLinkedOpenHashMap.this.key;
						int curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(k) & Int2ReferenceLinkedOpenHashMap.this.mask]) == 0) {
							return false;
						} else if (k == curr) {
							return Int2ReferenceLinkedOpenHashMap.this.value[pos] == v;
						} else {
							while ((curr = key[pos = pos + 1 & Int2ReferenceLinkedOpenHashMap.this.mask]) != 0) {
								if (k == curr) {
									return Int2ReferenceLinkedOpenHashMap.this.value[pos] == v;
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
					int k = (Integer)e.getKey();
					V v = (V)e.getValue();
					if (k == 0) {
						if (Int2ReferenceLinkedOpenHashMap.this.containsNullKey && Int2ReferenceLinkedOpenHashMap.this.value[Int2ReferenceLinkedOpenHashMap.this.n] == v) {
							Int2ReferenceLinkedOpenHashMap.this.removeNullEntry();
							return true;
						} else {
							return false;
						}
					} else {
						int[] key = Int2ReferenceLinkedOpenHashMap.this.key;
						int curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(k) & Int2ReferenceLinkedOpenHashMap.this.mask]) == 0) {
							return false;
						} else if (curr == k) {
							if (Int2ReferenceLinkedOpenHashMap.this.value[pos] == v) {
								Int2ReferenceLinkedOpenHashMap.this.removeEntry(pos);
								return true;
							} else {
								return false;
							}
						} else {
							while ((curr = key[pos = pos + 1 & Int2ReferenceLinkedOpenHashMap.this.mask]) != 0) {
								if (curr == k && Int2ReferenceLinkedOpenHashMap.this.value[pos] == v) {
									Int2ReferenceLinkedOpenHashMap.this.removeEntry(pos);
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
			return Int2ReferenceLinkedOpenHashMap.this.size;
		}

		public void clear() {
			Int2ReferenceLinkedOpenHashMap.this.clear();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V>> iterator(it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V> from) {
			return Int2ReferenceLinkedOpenHashMap.this.new EntryIterator(from.getIntKey());
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V>> fastIterator() {
			return Int2ReferenceLinkedOpenHashMap.this.new FastEntryIterator();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V>> fastIterator(it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V> from) {
			return Int2ReferenceLinkedOpenHashMap.this.new FastEntryIterator(from.getIntKey());
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V>> consumer) {
			int i = Int2ReferenceLinkedOpenHashMap.this.size;
			int next = Int2ReferenceLinkedOpenHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Int2ReferenceLinkedOpenHashMap.this.link[next];
				consumer.accept(new BasicEntry(Int2ReferenceLinkedOpenHashMap.this.key[curr], Int2ReferenceLinkedOpenHashMap.this.value[curr]));
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V>> consumer) {
			BasicEntry<V> entry = new BasicEntry<>();
			int i = Int2ReferenceLinkedOpenHashMap.this.size;
			int next = Int2ReferenceLinkedOpenHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Int2ReferenceLinkedOpenHashMap.this.link[next];
				entry.key = Int2ReferenceLinkedOpenHashMap.this.key[curr];
				entry.value = Int2ReferenceLinkedOpenHashMap.this.value[curr];
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
			this.next = Int2ReferenceLinkedOpenHashMap.this.first;
			this.index = 0;
		}

		private MapIterator(int from) {
			if (from == 0) {
				if (Int2ReferenceLinkedOpenHashMap.this.containsNullKey) {
					this.next = (int)Int2ReferenceLinkedOpenHashMap.this.link[Int2ReferenceLinkedOpenHashMap.this.n];
					this.prev = Int2ReferenceLinkedOpenHashMap.this.n;
				} else {
					throw new NoSuchElementException("The key " + from + " does not belong to this map.");
				}
			} else if (Int2ReferenceLinkedOpenHashMap.this.key[Int2ReferenceLinkedOpenHashMap.this.last] == from) {
				this.prev = Int2ReferenceLinkedOpenHashMap.this.last;
				this.index = Int2ReferenceLinkedOpenHashMap.this.size;
			} else {
				for (int pos = HashCommon.mix(from) & Int2ReferenceLinkedOpenHashMap.this.mask;
					Int2ReferenceLinkedOpenHashMap.this.key[pos] != 0;
					pos = pos + 1 & Int2ReferenceLinkedOpenHashMap.this.mask
				) {
					if (Int2ReferenceLinkedOpenHashMap.this.key[pos] == from) {
						this.next = (int)Int2ReferenceLinkedOpenHashMap.this.link[pos];
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
					this.index = Int2ReferenceLinkedOpenHashMap.this.size;
				} else {
					int pos = Int2ReferenceLinkedOpenHashMap.this.first;

					for (this.index = 1; pos != this.prev; this.index++) {
						pos = (int)Int2ReferenceLinkedOpenHashMap.this.link[pos];
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
				this.next = (int)Int2ReferenceLinkedOpenHashMap.this.link[this.curr];
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
				this.prev = (int)(Int2ReferenceLinkedOpenHashMap.this.link[this.curr] >>> 32);
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
					this.prev = (int)(Int2ReferenceLinkedOpenHashMap.this.link[this.curr] >>> 32);
				} else {
					this.next = (int)Int2ReferenceLinkedOpenHashMap.this.link[this.curr];
				}

				Int2ReferenceLinkedOpenHashMap.this.size--;
				if (this.prev == -1) {
					Int2ReferenceLinkedOpenHashMap.this.first = this.next;
				} else {
					Int2ReferenceLinkedOpenHashMap.this.link[this.prev] = Int2ReferenceLinkedOpenHashMap.this.link[this.prev]
						^ (Int2ReferenceLinkedOpenHashMap.this.link[this.prev] ^ (long)this.next & 4294967295L) & 4294967295L;
				}

				if (this.next == -1) {
					Int2ReferenceLinkedOpenHashMap.this.last = this.prev;
				} else {
					Int2ReferenceLinkedOpenHashMap.this.link[this.next] = Int2ReferenceLinkedOpenHashMap.this.link[this.next]
						^ (Int2ReferenceLinkedOpenHashMap.this.link[this.next] ^ ((long)this.prev & 4294967295L) << 32) & -4294967296L;
				}

				int pos = this.curr;
				this.curr = -1;
				if (pos == Int2ReferenceLinkedOpenHashMap.this.n) {
					Int2ReferenceLinkedOpenHashMap.this.containsNullKey = false;
					Int2ReferenceLinkedOpenHashMap.this.value[Int2ReferenceLinkedOpenHashMap.this.n] = null;
				} else {
					int[] key = Int2ReferenceLinkedOpenHashMap.this.key;

					label61:
					while (true) {
						int last = pos;

						int curr;
						for (pos = pos + 1 & Int2ReferenceLinkedOpenHashMap.this.mask; (curr = key[pos]) != 0; pos = pos + 1 & Int2ReferenceLinkedOpenHashMap.this.mask) {
							int slot = HashCommon.mix(curr) & Int2ReferenceLinkedOpenHashMap.this.mask;
							if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
								key[last] = curr;
								Int2ReferenceLinkedOpenHashMap.this.value[last] = Int2ReferenceLinkedOpenHashMap.this.value[pos];
								if (this.next == pos) {
									this.next = last;
								}

								if (this.prev == pos) {
									this.prev = last;
								}

								Int2ReferenceLinkedOpenHashMap.this.fixPointers(pos, last);
								continue label61;
							}
						}

						key[last] = 0;
						Int2ReferenceLinkedOpenHashMap.this.value[last] = null;
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

		public void set(it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V> ok) {
			throw new UnsupportedOperationException();
		}

		public void add(it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry<V> ok) {
			throw new UnsupportedOperationException();
		}
	}

	private final class ValueIterator extends Int2ReferenceLinkedOpenHashMap<V>.MapIterator implements ObjectListIterator<V> {
		@Override
		public V previous() {
			return Int2ReferenceLinkedOpenHashMap.this.value[this.previousEntry()];
		}

		public ValueIterator() {
		}

		public V next() {
			return Int2ReferenceLinkedOpenHashMap.this.value[this.nextEntry()];
		}
	}
}
