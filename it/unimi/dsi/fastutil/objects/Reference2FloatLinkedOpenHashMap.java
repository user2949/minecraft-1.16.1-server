package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
import it.unimi.dsi.fastutil.objects.AbstractReference2FloatMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2FloatSortedMap.FastSortedEntrySet;
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
import java.util.function.ToDoubleFunction;

public class Reference2FloatLinkedOpenHashMap<K> extends AbstractReference2FloatSortedMap<K> implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient K[] key;
	protected transient float[] value;
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
	protected transient FastSortedEntrySet<K> entries;
	protected transient ReferenceSortedSet<K> keys;
	protected transient FloatCollection values;

	public Reference2FloatLinkedOpenHashMap(int expected, float f) {
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
			this.value = new float[this.n + 1];
			this.link = new long[this.n + 1];
		}
	}

	public Reference2FloatLinkedOpenHashMap(int expected) {
		this(expected, 0.75F);
	}

	public Reference2FloatLinkedOpenHashMap() {
		this(16, 0.75F);
	}

	public Reference2FloatLinkedOpenHashMap(Map<? extends K, ? extends Float> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Reference2FloatLinkedOpenHashMap(Map<? extends K, ? extends Float> m) {
		this(m, 0.75F);
	}

	public Reference2FloatLinkedOpenHashMap(Reference2FloatMap<K> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Reference2FloatLinkedOpenHashMap(Reference2FloatMap<K> m) {
		this(m, 0.75F);
	}

	public Reference2FloatLinkedOpenHashMap(K[] k, float[] v, float f) {
		this(k.length, f);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Reference2FloatLinkedOpenHashMap(K[] k, float[] v) {
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

	private float removeEntry(int pos) {
		float oldValue = this.value[pos];
		this.size--;
		this.fixPointers(pos);
		this.shiftKeys(pos);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	private float removeNullEntry() {
		this.containsNullKey = false;
		this.key[this.n] = null;
		float oldValue = this.value[this.n];
		this.size--;
		this.fixPointers(this.n);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	@Override
	public void putAll(Map<? extends K, ? extends Float> m) {
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

	private void insert(int pos, K k, float v) {
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
	public float put(K k, float v) {
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		} else {
			float oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	private float addToValue(int pos, float incr) {
		float oldValue = this.value[pos];
		this.value[pos] = oldValue + incr;
		return oldValue;
	}

	public float addTo(K k, float incr) {
		int pos;
		if (k == null) {
			if (this.containsNullKey) {
				return this.addToValue(this.n, incr);
			}

			pos = this.n;
			this.containsNullKey = true;
		} else {
			K[] key = this.key;
			K curr;
			if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
				if (curr == k) {
					return this.addToValue(pos, incr);
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (curr == k) {
						return this.addToValue(pos, incr);
					}
				}
			}
		}

		this.key[pos] = k;
		this.value[pos] = this.defRetValue + incr;
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

		return this.defRetValue;
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
			return;
		}
	}

	@Override
	public float removeFloat(Object k) {
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

	private float setValue(int pos, float v) {
		float oldValue = this.value[pos];
		this.value[pos] = v;
		return oldValue;
	}

	public float removeFirstFloat() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			int pos = this.first;
			this.first = (int)this.link[pos];
			if (0 <= this.first) {
				this.link[this.first] = this.link[this.first] | -4294967296L;
			}

			this.size--;
			float v = this.value[pos];
			if (pos == this.n) {
				this.containsNullKey = false;
				this.key[this.n] = null;
			} else {
				this.shiftKeys(pos);
			}

			if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
				this.rehash(this.n / 2);
			}

			return v;
		}
	}

	public float removeLastFloat() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			int pos = this.last;
			this.last = (int)(this.link[pos] >>> 32);
			if (0 <= this.last) {
				this.link[this.last] = this.link[this.last] | 4294967295L;
			}

			this.size--;
			float v = this.value[pos];
			if (pos == this.n) {
				this.containsNullKey = false;
				this.key[this.n] = null;
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

	public float getAndMoveToFirst(K k) {
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

	public float getAndMoveToLast(K k) {
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

	public float putAndMoveToFirst(K k, float v) {
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

	public float putAndMoveToLast(K k, float v) {
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
	public float getFloat(Object k) {
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
	public boolean containsValue(float v) {
		float[] value = this.value;
		K[] key = this.key;
		if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v)) {
			return true;
		} else {
			int i = this.n;

			while (i-- != 0) {
				if (key[i] != null && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v)) {
					return true;
				}
			}

			return false;
		}
	}

	@Override
	public float getOrDefault(Object k, float defaultValue) {
		if (k == null) {
			return this.containsNullKey ? this.value[this.n] : defaultValue;
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
				return defaultValue;
			} else if (k == curr) {
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (k == curr) {
						return this.value[pos];
					}
				}

				return defaultValue;
			}
		}
	}

	@Override
	public float putIfAbsent(K k, float v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(Object k, float v) {
		if (k == null) {
			if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
				this.removeNullEntry();
				return true;
			} else {
				return false;
			}
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
				return false;
			} else if (k == curr && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
				this.removeEntry(pos);
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (k == curr && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
						this.removeEntry(pos);
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean replace(K k, float oldValue, float v) {
		int pos = this.find(k);
		if (pos >= 0 && Float.floatToIntBits(oldValue) == Float.floatToIntBits(this.value[pos])) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public float replace(K k, float v) {
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			float oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	@Override
	public float computeFloatIfAbsent(K k, ToDoubleFunction<? super K> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(k));
			this.insert(-pos - 1, k, newValue);
			return newValue;
		}
	}

	@Override
	public float computeFloatIfPresent(K k, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			Float newValue = (Float)remappingFunction.apply(k, this.value[pos]);
			if (newValue == null) {
				if (k == null) {
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
	public float computeFloat(K k, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		Float newValue = (Float)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
		if (newValue == null) {
			if (pos >= 0) {
				if (k == null) {
					this.removeNullEntry();
				} else {
					this.removeEntry(pos);
				}
			}

			return this.defRetValue;
		} else {
			float newVal = newValue;
			if (pos < 0) {
				this.insert(-pos - 1, k, newVal);
				return newVal;
			} else {
				return this.value[pos] = newVal;
			}
		}
	}

	@Override
	public float mergeFloat(K k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return v;
		} else {
			Float newValue = (Float)remappingFunction.apply(this.value[pos], v);
			if (newValue == null) {
				if (k == null) {
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
	public Reference2FloatSortedMap<K> tailMap(K from) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reference2FloatSortedMap<K> headMap(K to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reference2FloatSortedMap<K> subMap(K from, K to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Comparator<? super K> comparator() {
		return null;
	}

	public FastSortedEntrySet<K> reference2FloatEntrySet() {
		if (this.entries == null) {
			this.entries = new Reference2FloatLinkedOpenHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public ReferenceSortedSet<K> keySet() {
		if (this.keys == null) {
			this.keys = new Reference2FloatLinkedOpenHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public FloatCollection values() {
		if (this.values == null) {
			this.values = new AbstractFloatCollection() {
				@Override
				public FloatIterator iterator() {
					return Reference2FloatLinkedOpenHashMap.this.new ValueIterator();
				}

				public int size() {
					return Reference2FloatLinkedOpenHashMap.this.size;
				}

				@Override
				public boolean contains(float v) {
					return Reference2FloatLinkedOpenHashMap.this.containsValue(v);
				}

				public void clear() {
					Reference2FloatLinkedOpenHashMap.this.clear();
				}

				@Override
				public void forEach(DoubleConsumer consumer) {
					if (Reference2FloatLinkedOpenHashMap.this.containsNullKey) {
						consumer.accept((double)Reference2FloatLinkedOpenHashMap.this.value[Reference2FloatLinkedOpenHashMap.this.n]);
					}

					int pos = Reference2FloatLinkedOpenHashMap.this.n;

					while (pos-- != 0) {
						if (Reference2FloatLinkedOpenHashMap.this.key[pos] != null) {
							consumer.accept((double)Reference2FloatLinkedOpenHashMap.this.value[pos]);
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
		float[] value = this.value;
		int mask = newN - 1;
		K[] newKey = (K[])(new Object[newN + 1]);
		float[] newValue = new float[newN + 1];
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

	public Reference2FloatLinkedOpenHashMap<K> clone() {
		Reference2FloatLinkedOpenHashMap<K> c;
		try {
			c = (Reference2FloatLinkedOpenHashMap<K>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (K[])((Object[])this.key.clone());
		c.value = (float[])this.value.clone();
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

			t ^= HashCommon.float2int(this.value[i]);
			h += t;
		}

		if (this.containsNullKey) {
			h += HashCommon.float2int(this.value[this.n]);
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		K[] key = this.key;
		float[] value = this.value;
		Reference2FloatLinkedOpenHashMap<K>.MapIterator i = new Reference2FloatLinkedOpenHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeObject(key[e]);
			s.writeFloat(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		K[] key = this.key = (K[])(new Object[this.n + 1]);
		float[] value = this.value = new float[this.n + 1];
		long[] link = this.link = new long[this.n + 1];
		int prev = -1;
		this.first = this.last = -1;
		int i = this.size;

		while (i-- != 0) {
			K k = (K)s.readObject();
			float v = s.readFloat();
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
		extends Reference2FloatLinkedOpenHashMap<K>.MapIterator
		implements ObjectListIterator<it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> {
		private Reference2FloatLinkedOpenHashMap<K>.MapEntry entry;

		public EntryIterator() {
		}

		public EntryIterator(K from) {
			super(from);
		}

		public Reference2FloatLinkedOpenHashMap<K>.MapEntry next() {
			return this.entry = Reference2FloatLinkedOpenHashMap.this.new MapEntry(this.nextEntry());
		}

		public Reference2FloatLinkedOpenHashMap<K>.MapEntry previous() {
			return this.entry = Reference2FloatLinkedOpenHashMap.this.new MapEntry(this.previousEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator
		extends Reference2FloatLinkedOpenHashMap<K>.MapIterator
		implements ObjectListIterator<it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> {
		final Reference2FloatLinkedOpenHashMap<K>.MapEntry entry;

		public FastEntryIterator() {
			this.entry = Reference2FloatLinkedOpenHashMap.this.new MapEntry();
		}

		public FastEntryIterator(K from) {
			super(from);
			this.entry = Reference2FloatLinkedOpenHashMap.this.new MapEntry();
		}

		public Reference2FloatLinkedOpenHashMap<K>.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}

		public Reference2FloatLinkedOpenHashMap<K>.MapEntry previous() {
			this.entry.index = this.previousEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Reference2FloatLinkedOpenHashMap<K>.MapIterator implements ObjectListIterator<K> {
		public KeyIterator(K k) {
			super(k);
		}

		@Override
		public K previous() {
			return Reference2FloatLinkedOpenHashMap.this.key[this.previousEntry()];
		}

		public KeyIterator() {
		}

		public K next() {
			return Reference2FloatLinkedOpenHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractReferenceSortedSet<K> {
		private KeySet() {
		}

		public ObjectListIterator<K> iterator(K from) {
			return Reference2FloatLinkedOpenHashMap.this.new KeyIterator(from);
		}

		public ObjectListIterator<K> iterator() {
			return Reference2FloatLinkedOpenHashMap.this.new KeyIterator();
		}

		public void forEach(Consumer<? super K> consumer) {
			if (Reference2FloatLinkedOpenHashMap.this.containsNullKey) {
				consumer.accept(Reference2FloatLinkedOpenHashMap.this.key[Reference2FloatLinkedOpenHashMap.this.n]);
			}

			int pos = Reference2FloatLinkedOpenHashMap.this.n;

			while (pos-- != 0) {
				K k = Reference2FloatLinkedOpenHashMap.this.key[pos];
				if (k != null) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Reference2FloatLinkedOpenHashMap.this.size;
		}

		public boolean contains(Object k) {
			return Reference2FloatLinkedOpenHashMap.this.containsKey(k);
		}

		public boolean remove(Object k) {
			int oldSize = Reference2FloatLinkedOpenHashMap.this.size;
			Reference2FloatLinkedOpenHashMap.this.removeFloat(k);
			return Reference2FloatLinkedOpenHashMap.this.size != oldSize;
		}

		public void clear() {
			Reference2FloatLinkedOpenHashMap.this.clear();
		}

		public K first() {
			if (Reference2FloatLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Reference2FloatLinkedOpenHashMap.this.key[Reference2FloatLinkedOpenHashMap.this.first];
			}
		}

		public K last() {
			if (Reference2FloatLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Reference2FloatLinkedOpenHashMap.this.key[Reference2FloatLinkedOpenHashMap.this.last];
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

	final class MapEntry implements it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>, java.util.Map.Entry<K, Float> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		public K getKey() {
			return Reference2FloatLinkedOpenHashMap.this.key[this.index];
		}

		@Override
		public float getFloatValue() {
			return Reference2FloatLinkedOpenHashMap.this.value[this.index];
		}

		@Override
		public float setValue(float v) {
			float oldValue = Reference2FloatLinkedOpenHashMap.this.value[this.index];
			Reference2FloatLinkedOpenHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Float getValue() {
			return Reference2FloatLinkedOpenHashMap.this.value[this.index];
		}

		@Deprecated
		@Override
		public Float setValue(Float v) {
			return this.setValue(v.floatValue());
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<K, Float> e = (java.util.Map.Entry<K, Float>)o;
				return Reference2FloatLinkedOpenHashMap.this.key[this.index] == e.getKey()
					&& Float.floatToIntBits(Reference2FloatLinkedOpenHashMap.this.value[this.index]) == Float.floatToIntBits((Float)e.getValue());
			}
		}

		public int hashCode() {
			return System.identityHashCode(Reference2FloatLinkedOpenHashMap.this.key[this.index])
				^ HashCommon.float2int(Reference2FloatLinkedOpenHashMap.this.value[this.index]);
		}

		public String toString() {
			return Reference2FloatLinkedOpenHashMap.this.key[this.index] + "=>" + Reference2FloatLinkedOpenHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSortedSet<it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> implements FastSortedEntrySet<K> {
		private MapEntrySet() {
		}

		@Override
		public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> iterator() {
			return Reference2FloatLinkedOpenHashMap.this.new EntryIterator();
		}

		public Comparator<? super it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> comparator() {
			return null;
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> subSet(
			it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K> fromElement, it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K> toElement
		) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> headSet(it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K> toElement) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> tailSet(
			it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K> fromElement
		) {
			throw new UnsupportedOperationException();
		}

		public it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K> first() {
			if (Reference2FloatLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Reference2FloatLinkedOpenHashMap.this.new MapEntry(Reference2FloatLinkedOpenHashMap.this.first);
			}
		}

		public it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K> last() {
			if (Reference2FloatLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Reference2FloatLinkedOpenHashMap.this.new MapEntry(Reference2FloatLinkedOpenHashMap.this.last);
			}
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getValue() != null && e.getValue() instanceof Float) {
					K k = (K)e.getKey();
					float v = (Float)e.getValue();
					if (k == null) {
						return Reference2FloatLinkedOpenHashMap.this.containsNullKey
							&& Float.floatToIntBits(Reference2FloatLinkedOpenHashMap.this.value[Reference2FloatLinkedOpenHashMap.this.n]) == Float.floatToIntBits(v);
					} else {
						K[] key = Reference2FloatLinkedOpenHashMap.this.key;
						K curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2FloatLinkedOpenHashMap.this.mask]) == null) {
							return false;
						} else if (k == curr) {
							return Float.floatToIntBits(Reference2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v);
						} else {
							while ((curr = key[pos = pos + 1 & Reference2FloatLinkedOpenHashMap.this.mask]) != null) {
								if (k == curr) {
									return Float.floatToIntBits(Reference2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v);
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
				if (e.getValue() != null && e.getValue() instanceof Float) {
					K k = (K)e.getKey();
					float v = (Float)e.getValue();
					if (k == null) {
						if (Reference2FloatLinkedOpenHashMap.this.containsNullKey
							&& Float.floatToIntBits(Reference2FloatLinkedOpenHashMap.this.value[Reference2FloatLinkedOpenHashMap.this.n]) == Float.floatToIntBits(v)) {
							Reference2FloatLinkedOpenHashMap.this.removeNullEntry();
							return true;
						} else {
							return false;
						}
					} else {
						K[] key = Reference2FloatLinkedOpenHashMap.this.key;
						K curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2FloatLinkedOpenHashMap.this.mask]) == null) {
							return false;
						} else if (curr == k) {
							if (Float.floatToIntBits(Reference2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
								Reference2FloatLinkedOpenHashMap.this.removeEntry(pos);
								return true;
							} else {
								return false;
							}
						} else {
							while ((curr = key[pos = pos + 1 & Reference2FloatLinkedOpenHashMap.this.mask]) != null) {
								if (curr == k && Float.floatToIntBits(Reference2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
									Reference2FloatLinkedOpenHashMap.this.removeEntry(pos);
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
			return Reference2FloatLinkedOpenHashMap.this.size;
		}

		public void clear() {
			Reference2FloatLinkedOpenHashMap.this.clear();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> iterator(it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K> from) {
			return Reference2FloatLinkedOpenHashMap.this.new EntryIterator(from.getKey());
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> fastIterator() {
			return Reference2FloatLinkedOpenHashMap.this.new FastEntryIterator();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> fastIterator(
			it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K> from
		) {
			return Reference2FloatLinkedOpenHashMap.this.new FastEntryIterator(from.getKey());
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> consumer) {
			int i = Reference2FloatLinkedOpenHashMap.this.size;
			int next = Reference2FloatLinkedOpenHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Reference2FloatLinkedOpenHashMap.this.link[next];
				consumer.accept(new BasicEntry(Reference2FloatLinkedOpenHashMap.this.key[curr], Reference2FloatLinkedOpenHashMap.this.value[curr]));
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> consumer) {
			BasicEntry<K> entry = new BasicEntry<>();
			int i = Reference2FloatLinkedOpenHashMap.this.size;
			int next = Reference2FloatLinkedOpenHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Reference2FloatLinkedOpenHashMap.this.link[next];
				entry.key = Reference2FloatLinkedOpenHashMap.this.key[curr];
				entry.value = Reference2FloatLinkedOpenHashMap.this.value[curr];
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
			this.next = Reference2FloatLinkedOpenHashMap.this.first;
			this.index = 0;
		}

		private MapIterator(K from) {
			if (from == null) {
				if (Reference2FloatLinkedOpenHashMap.this.containsNullKey) {
					this.next = (int)Reference2FloatLinkedOpenHashMap.this.link[Reference2FloatLinkedOpenHashMap.this.n];
					this.prev = Reference2FloatLinkedOpenHashMap.this.n;
				} else {
					throw new NoSuchElementException("The key " + from + " does not belong to this map.");
				}
			} else if (Reference2FloatLinkedOpenHashMap.this.key[Reference2FloatLinkedOpenHashMap.this.last] == from) {
				this.prev = Reference2FloatLinkedOpenHashMap.this.last;
				this.index = Reference2FloatLinkedOpenHashMap.this.size;
			} else {
				for (int pos = HashCommon.mix(System.identityHashCode(from)) & Reference2FloatLinkedOpenHashMap.this.mask;
					Reference2FloatLinkedOpenHashMap.this.key[pos] != null;
					pos = pos + 1 & Reference2FloatLinkedOpenHashMap.this.mask
				) {
					if (Reference2FloatLinkedOpenHashMap.this.key[pos] == from) {
						this.next = (int)Reference2FloatLinkedOpenHashMap.this.link[pos];
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
					this.index = Reference2FloatLinkedOpenHashMap.this.size;
				} else {
					int pos = Reference2FloatLinkedOpenHashMap.this.first;

					for (this.index = 1; pos != this.prev; this.index++) {
						pos = (int)Reference2FloatLinkedOpenHashMap.this.link[pos];
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
				this.next = (int)Reference2FloatLinkedOpenHashMap.this.link[this.curr];
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
				this.prev = (int)(Reference2FloatLinkedOpenHashMap.this.link[this.curr] >>> 32);
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
					this.prev = (int)(Reference2FloatLinkedOpenHashMap.this.link[this.curr] >>> 32);
				} else {
					this.next = (int)Reference2FloatLinkedOpenHashMap.this.link[this.curr];
				}

				Reference2FloatLinkedOpenHashMap.this.size--;
				if (this.prev == -1) {
					Reference2FloatLinkedOpenHashMap.this.first = this.next;
				} else {
					Reference2FloatLinkedOpenHashMap.this.link[this.prev] = Reference2FloatLinkedOpenHashMap.this.link[this.prev]
						^ (Reference2FloatLinkedOpenHashMap.this.link[this.prev] ^ (long)this.next & 4294967295L) & 4294967295L;
				}

				if (this.next == -1) {
					Reference2FloatLinkedOpenHashMap.this.last = this.prev;
				} else {
					Reference2FloatLinkedOpenHashMap.this.link[this.next] = Reference2FloatLinkedOpenHashMap.this.link[this.next]
						^ (Reference2FloatLinkedOpenHashMap.this.link[this.next] ^ ((long)this.prev & 4294967295L) << 32) & -4294967296L;
				}

				int pos = this.curr;
				this.curr = -1;
				if (pos == Reference2FloatLinkedOpenHashMap.this.n) {
					Reference2FloatLinkedOpenHashMap.this.containsNullKey = false;
					Reference2FloatLinkedOpenHashMap.this.key[Reference2FloatLinkedOpenHashMap.this.n] = null;
				} else {
					K[] key = Reference2FloatLinkedOpenHashMap.this.key;

					label61:
					while (true) {
						int last = pos;

						K curr;
						for (pos = pos + 1 & Reference2FloatLinkedOpenHashMap.this.mask; (curr = key[pos]) != null; pos = pos + 1 & Reference2FloatLinkedOpenHashMap.this.mask) {
							int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2FloatLinkedOpenHashMap.this.mask;
							if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
								key[last] = curr;
								Reference2FloatLinkedOpenHashMap.this.value[last] = Reference2FloatLinkedOpenHashMap.this.value[pos];
								if (this.next == pos) {
									this.next = last;
								}

								if (this.prev == pos) {
									this.prev = last;
								}

								Reference2FloatLinkedOpenHashMap.this.fixPointers(pos, last);
								continue label61;
							}
						}

						key[last] = null;
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

		public void set(it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K> ok) {
			throw new UnsupportedOperationException();
		}

		public void add(it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K> ok) {
			throw new UnsupportedOperationException();
		}
	}

	private final class ValueIterator extends Reference2FloatLinkedOpenHashMap<K>.MapIterator implements FloatListIterator {
		@Override
		public float previousFloat() {
			return Reference2FloatLinkedOpenHashMap.this.value[this.previousEntry()];
		}

		public ValueIterator() {
		}

		@Override
		public float nextFloat() {
			return Reference2FloatLinkedOpenHashMap.this.value[this.nextEntry()];
		}
	}
}
