package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloat2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.floats.Float2ObjectSortedMap.FastSortedEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
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
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;

public class Float2ObjectLinkedOpenHashMap<V> extends AbstractFloat2ObjectSortedMap<V> implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient float[] key;
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
	protected transient FloatSortedSet keys;
	protected transient ObjectCollection<V> values;

	public Float2ObjectLinkedOpenHashMap(int expected, float f) {
		if (f <= 0.0F || f > 1.0F) {
			throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
		} else if (expected < 0) {
			throw new IllegalArgumentException("The expected number of elements must be nonnegative");
		} else {
			this.f = f;
			this.minN = this.n = HashCommon.arraySize(expected, f);
			this.mask = this.n - 1;
			this.maxFill = HashCommon.maxFill(this.n, f);
			this.key = new float[this.n + 1];
			this.value = (V[])(new Object[this.n + 1]);
			this.link = new long[this.n + 1];
		}
	}

	public Float2ObjectLinkedOpenHashMap(int expected) {
		this(expected, 0.75F);
	}

	public Float2ObjectLinkedOpenHashMap() {
		this(16, 0.75F);
	}

	public Float2ObjectLinkedOpenHashMap(Map<? extends Float, ? extends V> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Float2ObjectLinkedOpenHashMap(Map<? extends Float, ? extends V> m) {
		this(m, 0.75F);
	}

	public Float2ObjectLinkedOpenHashMap(Float2ObjectMap<V> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Float2ObjectLinkedOpenHashMap(Float2ObjectMap<V> m) {
		this(m, 0.75F);
	}

	public Float2ObjectLinkedOpenHashMap(float[] k, V[] v, float f) {
		this(k.length, f);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Float2ObjectLinkedOpenHashMap(float[] k, V[] v) {
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
	public void putAll(Map<? extends Float, ? extends V> m) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(m.size());
		} else {
			this.tryCapacity((long)(this.size() + m.size()));
		}

		super.putAll(m);
	}

	private int find(float k) {
		if (Float.floatToIntBits(k) == 0) {
			return this.containsNullKey ? this.n : -(this.n + 1);
		} else {
			float[] key = this.key;
			float curr;
			int pos;
			if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) {
				return -(pos + 1);
			} else if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
				return pos;
			} else {
				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
						return pos;
					}
				}

				return -(pos + 1);
			}
		}
	}

	private void insert(int pos, float k, V v) {
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
	public V put(float k, V v) {
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
		float[] key = this.key;

		label30:
		while (true) {
			int last = pos;

			float curr;
			for (pos = pos + 1 & this.mask; Float.floatToIntBits(curr = key[pos]) != 0; pos = pos + 1 & this.mask) {
				int slot = HashCommon.mix(HashCommon.float2int(curr)) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					key[last] = curr;
					this.value[last] = this.value[pos];
					this.fixPointers(pos, last);
					continue label30;
				}
			}

			key[last] = 0.0F;
			this.value[last] = null;
			return;
		}
	}

	@Override
	public V remove(float k) {
		if (Float.floatToIntBits(k) == 0) {
			return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
		} else {
			float[] key = this.key;
			float curr;
			int pos;
			if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) {
				return this.defRetValue;
			} else if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
				return this.removeEntry(pos);
			} else {
				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
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

	public V getAndMoveToFirst(float k) {
		if (Float.floatToIntBits(k) == 0) {
			if (this.containsNullKey) {
				this.moveIndexToFirst(this.n);
				return this.value[this.n];
			} else {
				return this.defRetValue;
			}
		} else {
			float[] key = this.key;
			float curr;
			int pos;
			if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) {
				return this.defRetValue;
			} else if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
				this.moveIndexToFirst(pos);
				return this.value[pos];
			} else {
				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
						this.moveIndexToFirst(pos);
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	public V getAndMoveToLast(float k) {
		if (Float.floatToIntBits(k) == 0) {
			if (this.containsNullKey) {
				this.moveIndexToLast(this.n);
				return this.value[this.n];
			} else {
				return this.defRetValue;
			}
		} else {
			float[] key = this.key;
			float curr;
			int pos;
			if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) {
				return this.defRetValue;
			} else if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
				this.moveIndexToLast(pos);
				return this.value[pos];
			} else {
				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
						this.moveIndexToLast(pos);
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	public V putAndMoveToFirst(float k, V v) {
		int pos;
		if (Float.floatToIntBits(k) == 0) {
			if (this.containsNullKey) {
				this.moveIndexToFirst(this.n);
				return this.setValue(this.n, v);
			}

			this.containsNullKey = true;
			pos = this.n;
		} else {
			float[] key = this.key;
			float curr;
			if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
				if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
					this.moveIndexToFirst(pos);
					return this.setValue(pos, v);
				}

				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
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

	public V putAndMoveToLast(float k, V v) {
		int pos;
		if (Float.floatToIntBits(k) == 0) {
			if (this.containsNullKey) {
				this.moveIndexToLast(this.n);
				return this.setValue(this.n, v);
			}

			this.containsNullKey = true;
			pos = this.n;
		} else {
			float[] key = this.key;
			float curr;
			if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
				if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
					this.moveIndexToLast(pos);
					return this.setValue(pos, v);
				}

				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
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
	public V get(float k) {
		if (Float.floatToIntBits(k) == 0) {
			return this.containsNullKey ? this.value[this.n] : this.defRetValue;
		} else {
			float[] key = this.key;
			float curr;
			int pos;
			if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) {
				return this.defRetValue;
			} else if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
				return this.value[pos];
			} else {
				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public boolean containsKey(float k) {
		if (Float.floatToIntBits(k) == 0) {
			return this.containsNullKey;
		} else {
			float[] key = this.key;
			float curr;
			int pos;
			if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) {
				return false;
			} else if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
				return true;
			} else {
				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
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
		float[] key = this.key;
		if (this.containsNullKey && Objects.equals(value[this.n], v)) {
			return true;
		} else {
			int i = this.n;

			while (i-- != 0) {
				if (Float.floatToIntBits(key[i]) != 0 && Objects.equals(value[i], v)) {
					return true;
				}
			}

			return false;
		}
	}

	@Override
	public V getOrDefault(float k, V defaultValue) {
		if (Float.floatToIntBits(k) == 0) {
			return this.containsNullKey ? this.value[this.n] : defaultValue;
		} else {
			float[] key = this.key;
			float curr;
			int pos;
			if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) {
				return defaultValue;
			} else if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
				return this.value[pos];
			} else {
				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
						return this.value[pos];
					}
				}

				return defaultValue;
			}
		}
	}

	@Override
	public V putIfAbsent(float k, V v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(float k, Object v) {
		if (Float.floatToIntBits(k) == 0) {
			if (this.containsNullKey && Objects.equals(v, this.value[this.n])) {
				this.removeNullEntry();
				return true;
			} else {
				return false;
			}
		} else {
			float[] key = this.key;
			float curr;
			int pos;
			if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) {
				return false;
			} else if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && Objects.equals(v, this.value[pos])) {
				this.removeEntry(pos);
				return true;
			} else {
				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && Objects.equals(v, this.value[pos])) {
						this.removeEntry(pos);
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean replace(float k, V oldValue, V v) {
		int pos = this.find(k);
		if (pos >= 0 && Objects.equals(oldValue, this.value[pos])) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public V replace(float k, V v) {
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
	public V computeIfAbsent(float k, DoubleFunction<? extends V> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			V newValue = (V)mappingFunction.apply((double)k);
			this.insert(-pos - 1, k, newValue);
			return newValue;
		}
	}

	@Override
	public V computeIfPresent(float k, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			V newValue = (V)remappingFunction.apply(k, this.value[pos]);
			if (newValue == null) {
				if (Float.floatToIntBits(k) == 0) {
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
	public V compute(float k, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		V newValue = (V)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
		if (newValue == null) {
			if (pos >= 0) {
				if (Float.floatToIntBits(k) == 0) {
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
	public V merge(float k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos >= 0 && this.value[pos] != null) {
			V newValue = (V)remappingFunction.apply(this.value[pos], v);
			if (newValue == null) {
				if (Float.floatToIntBits(k) == 0) {
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
			Arrays.fill(this.key, 0.0F);
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
	public float firstFloatKey() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			return this.key[this.first];
		}
	}

	@Override
	public float lastFloatKey() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			return this.key[this.last];
		}
	}

	@Override
	public Float2ObjectSortedMap<V> tailMap(float from) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Float2ObjectSortedMap<V> headMap(float to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Float2ObjectSortedMap<V> subMap(float from, float to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public FloatComparator comparator() {
		return null;
	}

	public FastSortedEntrySet<V> float2ObjectEntrySet() {
		if (this.entries == null) {
			this.entries = new Float2ObjectLinkedOpenHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public FloatSortedSet keySet() {
		if (this.keys == null) {
			this.keys = new Float2ObjectLinkedOpenHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public ObjectCollection<V> values() {
		if (this.values == null) {
			this.values = new AbstractObjectCollection<V>() {
				@Override
				public ObjectIterator<V> iterator() {
					return Float2ObjectLinkedOpenHashMap.this.new ValueIterator();
				}

				public int size() {
					return Float2ObjectLinkedOpenHashMap.this.size;
				}

				public boolean contains(Object v) {
					return Float2ObjectLinkedOpenHashMap.this.containsValue(v);
				}

				public void clear() {
					Float2ObjectLinkedOpenHashMap.this.clear();
				}

				public void forEach(Consumer<? super V> consumer) {
					if (Float2ObjectLinkedOpenHashMap.this.containsNullKey) {
						consumer.accept(Float2ObjectLinkedOpenHashMap.this.value[Float2ObjectLinkedOpenHashMap.this.n]);
					}

					int pos = Float2ObjectLinkedOpenHashMap.this.n;

					while (pos-- != 0) {
						if (Float.floatToIntBits(Float2ObjectLinkedOpenHashMap.this.key[pos]) != 0) {
							consumer.accept(Float2ObjectLinkedOpenHashMap.this.value[pos]);
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
		float[] key = this.key;
		V[] value = this.value;
		int mask = newN - 1;
		float[] newKey = new float[newN + 1];
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
			if (Float.floatToIntBits(key[i]) == 0) {
				pos = newN;
			} else {
				pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask;

				while (Float.floatToIntBits(newKey[pos]) != 0) {
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

	public Float2ObjectLinkedOpenHashMap<V> clone() {
		Float2ObjectLinkedOpenHashMap<V> c;
		try {
			c = (Float2ObjectLinkedOpenHashMap<V>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (float[])this.key.clone();
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
			while (Float.floatToIntBits(this.key[i]) == 0) {
				i++;
			}

			t = HashCommon.float2int(this.key[i]);
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
		float[] key = this.key;
		V[] value = this.value;
		Float2ObjectLinkedOpenHashMap<V>.MapIterator i = new Float2ObjectLinkedOpenHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeFloat(key[e]);
			s.writeObject(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		float[] key = this.key = new float[this.n + 1];
		V[] value = this.value = (V[])(new Object[this.n + 1]);
		long[] link = this.link = new long[this.n + 1];
		int prev = -1;
		this.first = this.last = -1;
		int i = this.size;

		while (i-- != 0) {
			float k = s.readFloat();
			V v = (V)s.readObject();
			int pos;
			if (Float.floatToIntBits(k) == 0) {
				pos = this.n;
				this.containsNullKey = true;
			} else {
				pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;

				while (Float.floatToIntBits(key[pos]) != 0) {
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
		extends Float2ObjectLinkedOpenHashMap<V>.MapIterator
		implements ObjectListIterator<it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> {
		private Float2ObjectLinkedOpenHashMap<V>.MapEntry entry;

		public EntryIterator() {
		}

		public EntryIterator(float from) {
			super(from);
		}

		public Float2ObjectLinkedOpenHashMap<V>.MapEntry next() {
			return this.entry = Float2ObjectLinkedOpenHashMap.this.new MapEntry(this.nextEntry());
		}

		public Float2ObjectLinkedOpenHashMap<V>.MapEntry previous() {
			return this.entry = Float2ObjectLinkedOpenHashMap.this.new MapEntry(this.previousEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator
		extends Float2ObjectLinkedOpenHashMap<V>.MapIterator
		implements ObjectListIterator<it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> {
		final Float2ObjectLinkedOpenHashMap<V>.MapEntry entry;

		public FastEntryIterator() {
			this.entry = Float2ObjectLinkedOpenHashMap.this.new MapEntry();
		}

		public FastEntryIterator(float from) {
			super(from);
			this.entry = Float2ObjectLinkedOpenHashMap.this.new MapEntry();
		}

		public Float2ObjectLinkedOpenHashMap<V>.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}

		public Float2ObjectLinkedOpenHashMap<V>.MapEntry previous() {
			this.entry.index = this.previousEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Float2ObjectLinkedOpenHashMap<V>.MapIterator implements FloatListIterator {
		public KeyIterator(float k) {
			super(k);
		}

		@Override
		public float previousFloat() {
			return Float2ObjectLinkedOpenHashMap.this.key[this.previousEntry()];
		}

		public KeyIterator() {
		}

		@Override
		public float nextFloat() {
			return Float2ObjectLinkedOpenHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractFloatSortedSet {
		private KeySet() {
		}

		public FloatListIterator iterator(float from) {
			return Float2ObjectLinkedOpenHashMap.this.new KeyIterator(from);
		}

		public FloatListIterator iterator() {
			return Float2ObjectLinkedOpenHashMap.this.new KeyIterator();
		}

		@Override
		public void forEach(DoubleConsumer consumer) {
			if (Float2ObjectLinkedOpenHashMap.this.containsNullKey) {
				consumer.accept((double)Float2ObjectLinkedOpenHashMap.this.key[Float2ObjectLinkedOpenHashMap.this.n]);
			}

			int pos = Float2ObjectLinkedOpenHashMap.this.n;

			while (pos-- != 0) {
				float k = Float2ObjectLinkedOpenHashMap.this.key[pos];
				if (Float.floatToIntBits(k) != 0) {
					consumer.accept((double)k);
				}
			}
		}

		public int size() {
			return Float2ObjectLinkedOpenHashMap.this.size;
		}

		@Override
		public boolean contains(float k) {
			return Float2ObjectLinkedOpenHashMap.this.containsKey(k);
		}

		@Override
		public boolean remove(float k) {
			int oldSize = Float2ObjectLinkedOpenHashMap.this.size;
			Float2ObjectLinkedOpenHashMap.this.remove(k);
			return Float2ObjectLinkedOpenHashMap.this.size != oldSize;
		}

		public void clear() {
			Float2ObjectLinkedOpenHashMap.this.clear();
		}

		@Override
		public float firstFloat() {
			if (Float2ObjectLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Float2ObjectLinkedOpenHashMap.this.key[Float2ObjectLinkedOpenHashMap.this.first];
			}
		}

		@Override
		public float lastFloat() {
			if (Float2ObjectLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Float2ObjectLinkedOpenHashMap.this.key[Float2ObjectLinkedOpenHashMap.this.last];
			}
		}

		@Override
		public FloatComparator comparator() {
			return null;
		}

		@Override
		public FloatSortedSet tailSet(float from) {
			throw new UnsupportedOperationException();
		}

		@Override
		public FloatSortedSet headSet(float to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public FloatSortedSet subSet(float from, float to) {
			throw new UnsupportedOperationException();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>, java.util.Map.Entry<Float, V> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		@Override
		public float getFloatKey() {
			return Float2ObjectLinkedOpenHashMap.this.key[this.index];
		}

		public V getValue() {
			return Float2ObjectLinkedOpenHashMap.this.value[this.index];
		}

		public V setValue(V v) {
			V oldValue = Float2ObjectLinkedOpenHashMap.this.value[this.index];
			Float2ObjectLinkedOpenHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Float getKey() {
			return Float2ObjectLinkedOpenHashMap.this.key[this.index];
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<Float, V> e = (java.util.Map.Entry<Float, V>)o;
				return Float.floatToIntBits(Float2ObjectLinkedOpenHashMap.this.key[this.index]) == Float.floatToIntBits((Float)e.getKey())
					&& Objects.equals(Float2ObjectLinkedOpenHashMap.this.value[this.index], e.getValue());
			}
		}

		public int hashCode() {
			return HashCommon.float2int(Float2ObjectLinkedOpenHashMap.this.key[this.index])
				^ (Float2ObjectLinkedOpenHashMap.this.value[this.index] == null ? 0 : Float2ObjectLinkedOpenHashMap.this.value[this.index].hashCode());
		}

		public String toString() {
			return Float2ObjectLinkedOpenHashMap.this.key[this.index] + "=>" + Float2ObjectLinkedOpenHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSortedSet<it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> implements FastSortedEntrySet<V> {
		private MapEntrySet() {
		}

		@Override
		public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> iterator() {
			return Float2ObjectLinkedOpenHashMap.this.new EntryIterator();
		}

		public Comparator<? super it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> comparator() {
			return null;
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> subSet(
			it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V> fromElement, it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V> toElement
		) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> headSet(it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V> toElement) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> tailSet(it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V> fromElement) {
			throw new UnsupportedOperationException();
		}

		public it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V> first() {
			if (Float2ObjectLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Float2ObjectLinkedOpenHashMap.this.new MapEntry(Float2ObjectLinkedOpenHashMap.this.first);
			}
		}

		public it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V> last() {
			if (Float2ObjectLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Float2ObjectLinkedOpenHashMap.this.new MapEntry(Float2ObjectLinkedOpenHashMap.this.last);
			}
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() != null && e.getKey() instanceof Float) {
					float k = (Float)e.getKey();
					V v = (V)e.getValue();
					if (Float.floatToIntBits(k) == 0) {
						return Float2ObjectLinkedOpenHashMap.this.containsNullKey
							&& Objects.equals(Float2ObjectLinkedOpenHashMap.this.value[Float2ObjectLinkedOpenHashMap.this.n], v);
					} else {
						float[] key = Float2ObjectLinkedOpenHashMap.this.key;
						float curr;
						int pos;
						if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2ObjectLinkedOpenHashMap.this.mask]) == 0) {
							return false;
						} else if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
							return Objects.equals(Float2ObjectLinkedOpenHashMap.this.value[pos], v);
						} else {
							while (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ObjectLinkedOpenHashMap.this.mask]) != 0) {
								if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
									return Objects.equals(Float2ObjectLinkedOpenHashMap.this.value[pos], v);
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
				if (e.getKey() != null && e.getKey() instanceof Float) {
					float k = (Float)e.getKey();
					V v = (V)e.getValue();
					if (Float.floatToIntBits(k) == 0) {
						if (Float2ObjectLinkedOpenHashMap.this.containsNullKey
							&& Objects.equals(Float2ObjectLinkedOpenHashMap.this.value[Float2ObjectLinkedOpenHashMap.this.n], v)) {
							Float2ObjectLinkedOpenHashMap.this.removeNullEntry();
							return true;
						} else {
							return false;
						}
					} else {
						float[] key = Float2ObjectLinkedOpenHashMap.this.key;
						float curr;
						int pos;
						if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2ObjectLinkedOpenHashMap.this.mask]) == 0) {
							return false;
						} else if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
							if (Objects.equals(Float2ObjectLinkedOpenHashMap.this.value[pos], v)) {
								Float2ObjectLinkedOpenHashMap.this.removeEntry(pos);
								return true;
							} else {
								return false;
							}
						} else {
							while (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ObjectLinkedOpenHashMap.this.mask]) != 0) {
								if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && Objects.equals(Float2ObjectLinkedOpenHashMap.this.value[pos], v)) {
									Float2ObjectLinkedOpenHashMap.this.removeEntry(pos);
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
			return Float2ObjectLinkedOpenHashMap.this.size;
		}

		public void clear() {
			Float2ObjectLinkedOpenHashMap.this.clear();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> iterator(it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V> from) {
			return Float2ObjectLinkedOpenHashMap.this.new EntryIterator(from.getFloatKey());
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> fastIterator() {
			return Float2ObjectLinkedOpenHashMap.this.new FastEntryIterator();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> fastIterator(it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V> from) {
			return Float2ObjectLinkedOpenHashMap.this.new FastEntryIterator(from.getFloatKey());
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> consumer) {
			int i = Float2ObjectLinkedOpenHashMap.this.size;
			int next = Float2ObjectLinkedOpenHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Float2ObjectLinkedOpenHashMap.this.link[next];
				consumer.accept(new BasicEntry(Float2ObjectLinkedOpenHashMap.this.key[curr], Float2ObjectLinkedOpenHashMap.this.value[curr]));
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> consumer) {
			BasicEntry<V> entry = new BasicEntry<>();
			int i = Float2ObjectLinkedOpenHashMap.this.size;
			int next = Float2ObjectLinkedOpenHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Float2ObjectLinkedOpenHashMap.this.link[next];
				entry.key = Float2ObjectLinkedOpenHashMap.this.key[curr];
				entry.value = Float2ObjectLinkedOpenHashMap.this.value[curr];
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
			this.next = Float2ObjectLinkedOpenHashMap.this.first;
			this.index = 0;
		}

		private MapIterator(float from) {
			if (Float.floatToIntBits(from) == 0) {
				if (Float2ObjectLinkedOpenHashMap.this.containsNullKey) {
					this.next = (int)Float2ObjectLinkedOpenHashMap.this.link[Float2ObjectLinkedOpenHashMap.this.n];
					this.prev = Float2ObjectLinkedOpenHashMap.this.n;
				} else {
					throw new NoSuchElementException("The key " + from + " does not belong to this map.");
				}
			} else if (Float.floatToIntBits(Float2ObjectLinkedOpenHashMap.this.key[Float2ObjectLinkedOpenHashMap.this.last]) == Float.floatToIntBits(from)) {
				this.prev = Float2ObjectLinkedOpenHashMap.this.last;
				this.index = Float2ObjectLinkedOpenHashMap.this.size;
			} else {
				for (int pos = HashCommon.mix(HashCommon.float2int(from)) & Float2ObjectLinkedOpenHashMap.this.mask;
					Float.floatToIntBits(Float2ObjectLinkedOpenHashMap.this.key[pos]) != 0;
					pos = pos + 1 & Float2ObjectLinkedOpenHashMap.this.mask
				) {
					if (Float.floatToIntBits(Float2ObjectLinkedOpenHashMap.this.key[pos]) == Float.floatToIntBits(from)) {
						this.next = (int)Float2ObjectLinkedOpenHashMap.this.link[pos];
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
					this.index = Float2ObjectLinkedOpenHashMap.this.size;
				} else {
					int pos = Float2ObjectLinkedOpenHashMap.this.first;

					for (this.index = 1; pos != this.prev; this.index++) {
						pos = (int)Float2ObjectLinkedOpenHashMap.this.link[pos];
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
				this.next = (int)Float2ObjectLinkedOpenHashMap.this.link[this.curr];
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
				this.prev = (int)(Float2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32);
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
					this.prev = (int)(Float2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32);
				} else {
					this.next = (int)Float2ObjectLinkedOpenHashMap.this.link[this.curr];
				}

				Float2ObjectLinkedOpenHashMap.this.size--;
				if (this.prev == -1) {
					Float2ObjectLinkedOpenHashMap.this.first = this.next;
				} else {
					Float2ObjectLinkedOpenHashMap.this.link[this.prev] = Float2ObjectLinkedOpenHashMap.this.link[this.prev]
						^ (Float2ObjectLinkedOpenHashMap.this.link[this.prev] ^ (long)this.next & 4294967295L) & 4294967295L;
				}

				if (this.next == -1) {
					Float2ObjectLinkedOpenHashMap.this.last = this.prev;
				} else {
					Float2ObjectLinkedOpenHashMap.this.link[this.next] = Float2ObjectLinkedOpenHashMap.this.link[this.next]
						^ (Float2ObjectLinkedOpenHashMap.this.link[this.next] ^ ((long)this.prev & 4294967295L) << 32) & -4294967296L;
				}

				int pos = this.curr;
				this.curr = -1;
				if (pos == Float2ObjectLinkedOpenHashMap.this.n) {
					Float2ObjectLinkedOpenHashMap.this.containsNullKey = false;
					Float2ObjectLinkedOpenHashMap.this.value[Float2ObjectLinkedOpenHashMap.this.n] = null;
				} else {
					float[] key = Float2ObjectLinkedOpenHashMap.this.key;

					label61:
					while (true) {
						int last = pos;

						float curr;
						for (pos = pos + 1 & Float2ObjectLinkedOpenHashMap.this.mask;
							Float.floatToIntBits(curr = key[pos]) != 0;
							pos = pos + 1 & Float2ObjectLinkedOpenHashMap.this.mask
						) {
							int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2ObjectLinkedOpenHashMap.this.mask;
							if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
								key[last] = curr;
								Float2ObjectLinkedOpenHashMap.this.value[last] = Float2ObjectLinkedOpenHashMap.this.value[pos];
								if (this.next == pos) {
									this.next = last;
								}

								if (this.prev == pos) {
									this.prev = last;
								}

								Float2ObjectLinkedOpenHashMap.this.fixPointers(pos, last);
								continue label61;
							}
						}

						key[last] = 0.0F;
						Float2ObjectLinkedOpenHashMap.this.value[last] = null;
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

		public void set(it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V> ok) {
			throw new UnsupportedOperationException();
		}

		public void add(it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V> ok) {
			throw new UnsupportedOperationException();
		}
	}

	private final class ValueIterator extends Float2ObjectLinkedOpenHashMap<V>.MapIterator implements ObjectListIterator<V> {
		@Override
		public V previous() {
			return Float2ObjectLinkedOpenHashMap.this.value[this.previousEntry()];
		}

		public ValueIterator() {
		}

		public V next() {
			return Float2ObjectLinkedOpenHashMap.this.value[this.nextEntry()];
		}
	}
}
