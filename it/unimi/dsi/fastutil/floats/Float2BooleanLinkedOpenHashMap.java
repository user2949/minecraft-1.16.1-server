package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloat2BooleanMap.BasicEntry;
import it.unimi.dsi.fastutil.floats.Float2BooleanSortedMap.FastSortedEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
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
import java.util.function.DoublePredicate;

public class Float2BooleanLinkedOpenHashMap extends AbstractFloat2BooleanSortedMap implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient float[] key;
	protected transient boolean[] value;
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
	protected transient FastSortedEntrySet entries;
	protected transient FloatSortedSet keys;
	protected transient BooleanCollection values;

	public Float2BooleanLinkedOpenHashMap(int expected, float f) {
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
			this.value = new boolean[this.n + 1];
			this.link = new long[this.n + 1];
		}
	}

	public Float2BooleanLinkedOpenHashMap(int expected) {
		this(expected, 0.75F);
	}

	public Float2BooleanLinkedOpenHashMap() {
		this(16, 0.75F);
	}

	public Float2BooleanLinkedOpenHashMap(Map<? extends Float, ? extends Boolean> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Float2BooleanLinkedOpenHashMap(Map<? extends Float, ? extends Boolean> m) {
		this(m, 0.75F);
	}

	public Float2BooleanLinkedOpenHashMap(Float2BooleanMap m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Float2BooleanLinkedOpenHashMap(Float2BooleanMap m) {
		this(m, 0.75F);
	}

	public Float2BooleanLinkedOpenHashMap(float[] k, boolean[] v, float f) {
		this(k.length, f);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Float2BooleanLinkedOpenHashMap(float[] k, boolean[] v) {
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
		this.fixPointers(pos);
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
		this.fixPointers(this.n);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	@Override
	public void putAll(Map<? extends Float, ? extends Boolean> m) {
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

	private void insert(int pos, float k, boolean v) {
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
	public boolean put(float k, boolean v) {
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
			return;
		}
	}

	@Override
	public boolean remove(float k) {
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

	private boolean setValue(int pos, boolean v) {
		boolean oldValue = this.value[pos];
		this.value[pos] = v;
		return oldValue;
	}

	public boolean removeFirstBoolean() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			int pos = this.first;
			this.first = (int)this.link[pos];
			if (0 <= this.first) {
				this.link[this.first] = this.link[this.first] | -4294967296L;
			}

			this.size--;
			boolean v = this.value[pos];
			if (pos == this.n) {
				this.containsNullKey = false;
			} else {
				this.shiftKeys(pos);
			}

			if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
				this.rehash(this.n / 2);
			}

			return v;
		}
	}

	public boolean removeLastBoolean() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			int pos = this.last;
			this.last = (int)(this.link[pos] >>> 32);
			if (0 <= this.last) {
				this.link[this.last] = this.link[this.last] | 4294967295L;
			}

			this.size--;
			boolean v = this.value[pos];
			if (pos == this.n) {
				this.containsNullKey = false;
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

	public boolean getAndMoveToFirst(float k) {
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

	public boolean getAndMoveToLast(float k) {
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

	public boolean putAndMoveToFirst(float k, boolean v) {
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

	public boolean putAndMoveToLast(float k, boolean v) {
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
	public boolean get(float k) {
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
	public boolean containsValue(boolean v) {
		boolean[] value = this.value;
		float[] key = this.key;
		if (this.containsNullKey && value[this.n] == v) {
			return true;
		} else {
			int i = this.n;

			while (i-- != 0) {
				if (Float.floatToIntBits(key[i]) != 0 && value[i] == v) {
					return true;
				}
			}

			return false;
		}
	}

	@Override
	public boolean getOrDefault(float k, boolean defaultValue) {
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
	public boolean putIfAbsent(float k, boolean v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(float k, boolean v) {
		if (Float.floatToIntBits(k) == 0) {
			if (this.containsNullKey && v == this.value[this.n]) {
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
			} else if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
				this.removeEntry(pos);
				return true;
			} else {
				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
						this.removeEntry(pos);
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean replace(float k, boolean oldValue, boolean v) {
		int pos = this.find(k);
		if (pos >= 0 && oldValue == this.value[pos]) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean replace(float k, boolean v) {
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
	public boolean computeIfAbsent(float k, DoublePredicate mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			boolean newValue = mappingFunction.test((double)k);
			this.insert(-pos - 1, k, newValue);
			return newValue;
		}
	}

	@Override
	public boolean computeIfAbsentNullable(float k, DoubleFunction<? extends Boolean> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			Boolean newValue = (Boolean)mappingFunction.apply((double)k);
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
	public boolean computeIfPresent(float k, BiFunction<? super Float, ? super Boolean, ? extends Boolean> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			Boolean newValue = (Boolean)remappingFunction.apply(k, this.value[pos]);
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
	public boolean compute(float k, BiFunction<? super Float, ? super Boolean, ? extends Boolean> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		Boolean newValue = (Boolean)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
		if (newValue == null) {
			if (pos >= 0) {
				if (Float.floatToIntBits(k) == 0) {
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
	public boolean merge(float k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return v;
		} else {
			Boolean newValue = (Boolean)remappingFunction.apply(this.value[pos], v);
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
	public void clear() {
		if (this.size != 0) {
			this.size = 0;
			this.containsNullKey = false;
			Arrays.fill(this.key, 0.0F);
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
	public Float2BooleanSortedMap tailMap(float from) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Float2BooleanSortedMap headMap(float to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Float2BooleanSortedMap subMap(float from, float to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public FloatComparator comparator() {
		return null;
	}

	public FastSortedEntrySet float2BooleanEntrySet() {
		if (this.entries == null) {
			this.entries = new Float2BooleanLinkedOpenHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public FloatSortedSet keySet() {
		if (this.keys == null) {
			this.keys = new Float2BooleanLinkedOpenHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public BooleanCollection values() {
		if (this.values == null) {
			this.values = new AbstractBooleanCollection() {
				@Override
				public BooleanIterator iterator() {
					return Float2BooleanLinkedOpenHashMap.this.new ValueIterator();
				}

				public int size() {
					return Float2BooleanLinkedOpenHashMap.this.size;
				}

				@Override
				public boolean contains(boolean v) {
					return Float2BooleanLinkedOpenHashMap.this.containsValue(v);
				}

				public void clear() {
					Float2BooleanLinkedOpenHashMap.this.clear();
				}

				@Override
				public void forEach(BooleanConsumer consumer) {
					if (Float2BooleanLinkedOpenHashMap.this.containsNullKey) {
						consumer.accept(Float2BooleanLinkedOpenHashMap.this.value[Float2BooleanLinkedOpenHashMap.this.n]);
					}

					int pos = Float2BooleanLinkedOpenHashMap.this.n;

					while (pos-- != 0) {
						if (Float.floatToIntBits(Float2BooleanLinkedOpenHashMap.this.key[pos]) != 0) {
							consumer.accept(Float2BooleanLinkedOpenHashMap.this.value[pos]);
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
		boolean[] value = this.value;
		int mask = newN - 1;
		float[] newKey = new float[newN + 1];
		boolean[] newValue = new boolean[newN + 1];
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

	public Float2BooleanLinkedOpenHashMap clone() {
		Float2BooleanLinkedOpenHashMap c;
		try {
			c = (Float2BooleanLinkedOpenHashMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (float[])this.key.clone();
		c.value = (boolean[])this.value.clone();
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
			t ^= this.value[i] ? 1231 : 1237;
			h += t;
		}

		if (this.containsNullKey) {
			h += this.value[this.n] ? 1231 : 1237;
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		float[] key = this.key;
		boolean[] value = this.value;
		Float2BooleanLinkedOpenHashMap.MapIterator i = new Float2BooleanLinkedOpenHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeFloat(key[e]);
			s.writeBoolean(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		float[] key = this.key = new float[this.n + 1];
		boolean[] value = this.value = new boolean[this.n + 1];
		long[] link = this.link = new long[this.n + 1];
		int prev = -1;
		this.first = this.last = -1;
		int i = this.size;

		while (i-- != 0) {
			float k = s.readFloat();
			boolean v = s.readBoolean();
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
		extends Float2BooleanLinkedOpenHashMap.MapIterator
		implements ObjectListIterator<it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry> {
		private Float2BooleanLinkedOpenHashMap.MapEntry entry;

		public EntryIterator() {
		}

		public EntryIterator(float from) {
			super(from);
		}

		public Float2BooleanLinkedOpenHashMap.MapEntry next() {
			return this.entry = Float2BooleanLinkedOpenHashMap.this.new MapEntry(this.nextEntry());
		}

		public Float2BooleanLinkedOpenHashMap.MapEntry previous() {
			return this.entry = Float2BooleanLinkedOpenHashMap.this.new MapEntry(this.previousEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator
		extends Float2BooleanLinkedOpenHashMap.MapIterator
		implements ObjectListIterator<it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry> {
		final Float2BooleanLinkedOpenHashMap.MapEntry entry;

		public FastEntryIterator() {
			this.entry = Float2BooleanLinkedOpenHashMap.this.new MapEntry();
		}

		public FastEntryIterator(float from) {
			super(from);
			this.entry = Float2BooleanLinkedOpenHashMap.this.new MapEntry();
		}

		public Float2BooleanLinkedOpenHashMap.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}

		public Float2BooleanLinkedOpenHashMap.MapEntry previous() {
			this.entry.index = this.previousEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Float2BooleanLinkedOpenHashMap.MapIterator implements FloatListIterator {
		public KeyIterator(float k) {
			super(k);
		}

		@Override
		public float previousFloat() {
			return Float2BooleanLinkedOpenHashMap.this.key[this.previousEntry()];
		}

		public KeyIterator() {
		}

		@Override
		public float nextFloat() {
			return Float2BooleanLinkedOpenHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractFloatSortedSet {
		private KeySet() {
		}

		public FloatListIterator iterator(float from) {
			return Float2BooleanLinkedOpenHashMap.this.new KeyIterator(from);
		}

		public FloatListIterator iterator() {
			return Float2BooleanLinkedOpenHashMap.this.new KeyIterator();
		}

		@Override
		public void forEach(DoubleConsumer consumer) {
			if (Float2BooleanLinkedOpenHashMap.this.containsNullKey) {
				consumer.accept((double)Float2BooleanLinkedOpenHashMap.this.key[Float2BooleanLinkedOpenHashMap.this.n]);
			}

			int pos = Float2BooleanLinkedOpenHashMap.this.n;

			while (pos-- != 0) {
				float k = Float2BooleanLinkedOpenHashMap.this.key[pos];
				if (Float.floatToIntBits(k) != 0) {
					consumer.accept((double)k);
				}
			}
		}

		public int size() {
			return Float2BooleanLinkedOpenHashMap.this.size;
		}

		@Override
		public boolean contains(float k) {
			return Float2BooleanLinkedOpenHashMap.this.containsKey(k);
		}

		@Override
		public boolean remove(float k) {
			int oldSize = Float2BooleanLinkedOpenHashMap.this.size;
			Float2BooleanLinkedOpenHashMap.this.remove(k);
			return Float2BooleanLinkedOpenHashMap.this.size != oldSize;
		}

		public void clear() {
			Float2BooleanLinkedOpenHashMap.this.clear();
		}

		@Override
		public float firstFloat() {
			if (Float2BooleanLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Float2BooleanLinkedOpenHashMap.this.key[Float2BooleanLinkedOpenHashMap.this.first];
			}
		}

		@Override
		public float lastFloat() {
			if (Float2BooleanLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Float2BooleanLinkedOpenHashMap.this.key[Float2BooleanLinkedOpenHashMap.this.last];
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

	final class MapEntry implements it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry, java.util.Map.Entry<Float, Boolean> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		@Override
		public float getFloatKey() {
			return Float2BooleanLinkedOpenHashMap.this.key[this.index];
		}

		@Override
		public boolean getBooleanValue() {
			return Float2BooleanLinkedOpenHashMap.this.value[this.index];
		}

		@Override
		public boolean setValue(boolean v) {
			boolean oldValue = Float2BooleanLinkedOpenHashMap.this.value[this.index];
			Float2BooleanLinkedOpenHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Float getKey() {
			return Float2BooleanLinkedOpenHashMap.this.key[this.index];
		}

		@Deprecated
		@Override
		public Boolean getValue() {
			return Float2BooleanLinkedOpenHashMap.this.value[this.index];
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
				java.util.Map.Entry<Float, Boolean> e = (java.util.Map.Entry<Float, Boolean>)o;
				return Float.floatToIntBits(Float2BooleanLinkedOpenHashMap.this.key[this.index]) == Float.floatToIntBits((Float)e.getKey())
					&& Float2BooleanLinkedOpenHashMap.this.value[this.index] == (Boolean)e.getValue();
			}
		}

		public int hashCode() {
			return HashCommon.float2int(Float2BooleanLinkedOpenHashMap.this.key[this.index]) ^ (Float2BooleanLinkedOpenHashMap.this.value[this.index] ? 1231 : 1237);
		}

		public String toString() {
			return Float2BooleanLinkedOpenHashMap.this.key[this.index] + "=>" + Float2BooleanLinkedOpenHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSortedSet<it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry> implements FastSortedEntrySet {
		private MapEntrySet() {
		}

		@Override
		public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry> iterator() {
			return Float2BooleanLinkedOpenHashMap.this.new EntryIterator();
		}

		public Comparator<? super it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry> comparator() {
			return null;
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry> subSet(
			it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry fromElement, it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry toElement
		) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry> headSet(it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry toElement) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry> tailSet(it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry fromElement) {
			throw new UnsupportedOperationException();
		}

		public it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry first() {
			if (Float2BooleanLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Float2BooleanLinkedOpenHashMap.this.new MapEntry(Float2BooleanLinkedOpenHashMap.this.first);
			}
		}

		public it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry last() {
			if (Float2BooleanLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Float2BooleanLinkedOpenHashMap.this.new MapEntry(Float2BooleanLinkedOpenHashMap.this.last);
			}
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Float)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Boolean) {
					float k = (Float)e.getKey();
					boolean v = (Boolean)e.getValue();
					if (Float.floatToIntBits(k) == 0) {
						return Float2BooleanLinkedOpenHashMap.this.containsNullKey && Float2BooleanLinkedOpenHashMap.this.value[Float2BooleanLinkedOpenHashMap.this.n] == v;
					} else {
						float[] key = Float2BooleanLinkedOpenHashMap.this.key;
						float curr;
						int pos;
						if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2BooleanLinkedOpenHashMap.this.mask]) == 0) {
							return false;
						} else if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
							return Float2BooleanLinkedOpenHashMap.this.value[pos] == v;
						} else {
							while (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2BooleanLinkedOpenHashMap.this.mask]) != 0) {
								if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
									return Float2BooleanLinkedOpenHashMap.this.value[pos] == v;
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
					if (e.getValue() != null && e.getValue() instanceof Boolean) {
						float k = (Float)e.getKey();
						boolean v = (Boolean)e.getValue();
						if (Float.floatToIntBits(k) == 0) {
							if (Float2BooleanLinkedOpenHashMap.this.containsNullKey && Float2BooleanLinkedOpenHashMap.this.value[Float2BooleanLinkedOpenHashMap.this.n] == v) {
								Float2BooleanLinkedOpenHashMap.this.removeNullEntry();
								return true;
							} else {
								return false;
							}
						} else {
							float[] key = Float2BooleanLinkedOpenHashMap.this.key;
							float curr;
							int pos;
							if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2BooleanLinkedOpenHashMap.this.mask]) == 0) {
								return false;
							} else if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
								if (Float2BooleanLinkedOpenHashMap.this.value[pos] == v) {
									Float2BooleanLinkedOpenHashMap.this.removeEntry(pos);
									return true;
								} else {
									return false;
								}
							} else {
								while (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2BooleanLinkedOpenHashMap.this.mask]) != 0) {
									if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && Float2BooleanLinkedOpenHashMap.this.value[pos] == v) {
										Float2BooleanLinkedOpenHashMap.this.removeEntry(pos);
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
			return Float2BooleanLinkedOpenHashMap.this.size;
		}

		public void clear() {
			Float2BooleanLinkedOpenHashMap.this.clear();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry> iterator(it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry from) {
			return Float2BooleanLinkedOpenHashMap.this.new EntryIterator(from.getFloatKey());
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry> fastIterator() {
			return Float2BooleanLinkedOpenHashMap.this.new FastEntryIterator();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry> fastIterator(it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry from) {
			return Float2BooleanLinkedOpenHashMap.this.new FastEntryIterator(from.getFloatKey());
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry> consumer) {
			int i = Float2BooleanLinkedOpenHashMap.this.size;
			int next = Float2BooleanLinkedOpenHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Float2BooleanLinkedOpenHashMap.this.link[next];
				consumer.accept(new BasicEntry(Float2BooleanLinkedOpenHashMap.this.key[curr], Float2BooleanLinkedOpenHashMap.this.value[curr]));
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry> consumer) {
			BasicEntry entry = new BasicEntry();
			int i = Float2BooleanLinkedOpenHashMap.this.size;
			int next = Float2BooleanLinkedOpenHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Float2BooleanLinkedOpenHashMap.this.link[next];
				entry.key = Float2BooleanLinkedOpenHashMap.this.key[curr];
				entry.value = Float2BooleanLinkedOpenHashMap.this.value[curr];
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
			this.next = Float2BooleanLinkedOpenHashMap.this.first;
			this.index = 0;
		}

		private MapIterator(float from) {
			if (Float.floatToIntBits(from) == 0) {
				if (Float2BooleanLinkedOpenHashMap.this.containsNullKey) {
					this.next = (int)Float2BooleanLinkedOpenHashMap.this.link[Float2BooleanLinkedOpenHashMap.this.n];
					this.prev = Float2BooleanLinkedOpenHashMap.this.n;
				} else {
					throw new NoSuchElementException("The key " + from + " does not belong to this map.");
				}
			} else if (Float.floatToIntBits(Float2BooleanLinkedOpenHashMap.this.key[Float2BooleanLinkedOpenHashMap.this.last]) == Float.floatToIntBits(from)) {
				this.prev = Float2BooleanLinkedOpenHashMap.this.last;
				this.index = Float2BooleanLinkedOpenHashMap.this.size;
			} else {
				for (int pos = HashCommon.mix(HashCommon.float2int(from)) & Float2BooleanLinkedOpenHashMap.this.mask;
					Float.floatToIntBits(Float2BooleanLinkedOpenHashMap.this.key[pos]) != 0;
					pos = pos + 1 & Float2BooleanLinkedOpenHashMap.this.mask
				) {
					if (Float.floatToIntBits(Float2BooleanLinkedOpenHashMap.this.key[pos]) == Float.floatToIntBits(from)) {
						this.next = (int)Float2BooleanLinkedOpenHashMap.this.link[pos];
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
					this.index = Float2BooleanLinkedOpenHashMap.this.size;
				} else {
					int pos = Float2BooleanLinkedOpenHashMap.this.first;

					for (this.index = 1; pos != this.prev; this.index++) {
						pos = (int)Float2BooleanLinkedOpenHashMap.this.link[pos];
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
				this.next = (int)Float2BooleanLinkedOpenHashMap.this.link[this.curr];
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
				this.prev = (int)(Float2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32);
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
					this.prev = (int)(Float2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32);
				} else {
					this.next = (int)Float2BooleanLinkedOpenHashMap.this.link[this.curr];
				}

				Float2BooleanLinkedOpenHashMap.this.size--;
				if (this.prev == -1) {
					Float2BooleanLinkedOpenHashMap.this.first = this.next;
				} else {
					Float2BooleanLinkedOpenHashMap.this.link[this.prev] = Float2BooleanLinkedOpenHashMap.this.link[this.prev]
						^ (Float2BooleanLinkedOpenHashMap.this.link[this.prev] ^ (long)this.next & 4294967295L) & 4294967295L;
				}

				if (this.next == -1) {
					Float2BooleanLinkedOpenHashMap.this.last = this.prev;
				} else {
					Float2BooleanLinkedOpenHashMap.this.link[this.next] = Float2BooleanLinkedOpenHashMap.this.link[this.next]
						^ (Float2BooleanLinkedOpenHashMap.this.link[this.next] ^ ((long)this.prev & 4294967295L) << 32) & -4294967296L;
				}

				int pos = this.curr;
				this.curr = -1;
				if (pos == Float2BooleanLinkedOpenHashMap.this.n) {
					Float2BooleanLinkedOpenHashMap.this.containsNullKey = false;
				} else {
					float[] key = Float2BooleanLinkedOpenHashMap.this.key;

					label61:
					while (true) {
						int last = pos;

						float curr;
						for (pos = pos + 1 & Float2BooleanLinkedOpenHashMap.this.mask;
							Float.floatToIntBits(curr = key[pos]) != 0;
							pos = pos + 1 & Float2BooleanLinkedOpenHashMap.this.mask
						) {
							int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2BooleanLinkedOpenHashMap.this.mask;
							if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
								key[last] = curr;
								Float2BooleanLinkedOpenHashMap.this.value[last] = Float2BooleanLinkedOpenHashMap.this.value[pos];
								if (this.next == pos) {
									this.next = last;
								}

								if (this.prev == pos) {
									this.prev = last;
								}

								Float2BooleanLinkedOpenHashMap.this.fixPointers(pos, last);
								continue label61;
							}
						}

						key[last] = 0.0F;
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

		public void set(it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry ok) {
			throw new UnsupportedOperationException();
		}

		public void add(it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry ok) {
			throw new UnsupportedOperationException();
		}
	}

	private final class ValueIterator extends Float2BooleanLinkedOpenHashMap.MapIterator implements BooleanListIterator {
		@Override
		public boolean previousBoolean() {
			return Float2BooleanLinkedOpenHashMap.this.value[this.previousEntry()];
		}

		public ValueIterator() {
		}

		@Override
		public boolean nextBoolean() {
			return Float2BooleanLinkedOpenHashMap.this.value[this.nextEntry()];
		}
	}
}
