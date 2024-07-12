package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2BooleanMap.BasicEntry;
import it.unimi.dsi.fastutil.doubles.Double2BooleanSortedMap.FastSortedEntrySet;
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
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;

public class Double2BooleanLinkedOpenHashMap extends AbstractDouble2BooleanSortedMap implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient double[] key;
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
	protected transient DoubleSortedSet keys;
	protected transient BooleanCollection values;

	public Double2BooleanLinkedOpenHashMap(int expected, float f) {
		if (f <= 0.0F || f > 1.0F) {
			throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
		} else if (expected < 0) {
			throw new IllegalArgumentException("The expected number of elements must be nonnegative");
		} else {
			this.f = f;
			this.minN = this.n = HashCommon.arraySize(expected, f);
			this.mask = this.n - 1;
			this.maxFill = HashCommon.maxFill(this.n, f);
			this.key = new double[this.n + 1];
			this.value = new boolean[this.n + 1];
			this.link = new long[this.n + 1];
		}
	}

	public Double2BooleanLinkedOpenHashMap(int expected) {
		this(expected, 0.75F);
	}

	public Double2BooleanLinkedOpenHashMap() {
		this(16, 0.75F);
	}

	public Double2BooleanLinkedOpenHashMap(Map<? extends Double, ? extends Boolean> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Double2BooleanLinkedOpenHashMap(Map<? extends Double, ? extends Boolean> m) {
		this(m, 0.75F);
	}

	public Double2BooleanLinkedOpenHashMap(Double2BooleanMap m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Double2BooleanLinkedOpenHashMap(Double2BooleanMap m) {
		this(m, 0.75F);
	}

	public Double2BooleanLinkedOpenHashMap(double[] k, boolean[] v, float f) {
		this(k.length, f);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Double2BooleanLinkedOpenHashMap(double[] k, boolean[] v) {
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
	public void putAll(Map<? extends Double, ? extends Boolean> m) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(m.size());
		} else {
			this.tryCapacity((long)(this.size() + m.size()));
		}

		super.putAll(m);
	}

	private int find(double k) {
		if (Double.doubleToLongBits(k) == 0L) {
			return this.containsNullKey ? this.n : -(this.n + 1);
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) {
				return -(pos + 1);
			} else if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
				return pos;
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
						return pos;
					}
				}

				return -(pos + 1);
			}
		}
	}

	private void insert(int pos, double k, boolean v) {
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
	public boolean put(double k, boolean v) {
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
		double[] key = this.key;

		label30:
		while (true) {
			int last = pos;

			double curr;
			for (pos = pos + 1 & this.mask; Double.doubleToLongBits(curr = key[pos]) != 0L; pos = pos + 1 & this.mask) {
				int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					key[last] = curr;
					this.value[last] = this.value[pos];
					this.fixPointers(pos, last);
					continue label30;
				}
			}

			key[last] = 0.0;
			return;
		}
	}

	@Override
	public boolean remove(double k) {
		if (Double.doubleToLongBits(k) == 0L) {
			return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) {
				return this.defRetValue;
			} else if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
				return this.removeEntry(pos);
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
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

	public boolean getAndMoveToFirst(double k) {
		if (Double.doubleToLongBits(k) == 0L) {
			if (this.containsNullKey) {
				this.moveIndexToFirst(this.n);
				return this.value[this.n];
			} else {
				return this.defRetValue;
			}
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) {
				return this.defRetValue;
			} else if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
				this.moveIndexToFirst(pos);
				return this.value[pos];
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
						this.moveIndexToFirst(pos);
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	public boolean getAndMoveToLast(double k) {
		if (Double.doubleToLongBits(k) == 0L) {
			if (this.containsNullKey) {
				this.moveIndexToLast(this.n);
				return this.value[this.n];
			} else {
				return this.defRetValue;
			}
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) {
				return this.defRetValue;
			} else if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
				this.moveIndexToLast(pos);
				return this.value[pos];
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
						this.moveIndexToLast(pos);
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	public boolean putAndMoveToFirst(double k, boolean v) {
		int pos;
		if (Double.doubleToLongBits(k) == 0L) {
			if (this.containsNullKey) {
				this.moveIndexToFirst(this.n);
				return this.setValue(this.n, v);
			}

			this.containsNullKey = true;
			pos = this.n;
		} else {
			double[] key = this.key;
			double curr;
			if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
				if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
					this.moveIndexToFirst(pos);
					return this.setValue(pos, v);
				}

				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
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

	public boolean putAndMoveToLast(double k, boolean v) {
		int pos;
		if (Double.doubleToLongBits(k) == 0L) {
			if (this.containsNullKey) {
				this.moveIndexToLast(this.n);
				return this.setValue(this.n, v);
			}

			this.containsNullKey = true;
			pos = this.n;
		} else {
			double[] key = this.key;
			double curr;
			if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
				if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
					this.moveIndexToLast(pos);
					return this.setValue(pos, v);
				}

				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
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
	public boolean get(double k) {
		if (Double.doubleToLongBits(k) == 0L) {
			return this.containsNullKey ? this.value[this.n] : this.defRetValue;
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) {
				return this.defRetValue;
			} else if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
				return this.value[pos];
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public boolean containsKey(double k) {
		if (Double.doubleToLongBits(k) == 0L) {
			return this.containsNullKey;
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) {
				return false;
			} else if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
				return true;
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
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
		double[] key = this.key;
		if (this.containsNullKey && value[this.n] == v) {
			return true;
		} else {
			int i = this.n;

			while (i-- != 0) {
				if (Double.doubleToLongBits(key[i]) != 0L && value[i] == v) {
					return true;
				}
			}

			return false;
		}
	}

	@Override
	public boolean getOrDefault(double k, boolean defaultValue) {
		if (Double.doubleToLongBits(k) == 0L) {
			return this.containsNullKey ? this.value[this.n] : defaultValue;
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) {
				return defaultValue;
			} else if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
				return this.value[pos];
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
						return this.value[pos];
					}
				}

				return defaultValue;
			}
		}
	}

	@Override
	public boolean putIfAbsent(double k, boolean v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(double k, boolean v) {
		if (Double.doubleToLongBits(k) == 0L) {
			if (this.containsNullKey && v == this.value[this.n]) {
				this.removeNullEntry();
				return true;
			} else {
				return false;
			}
		} else {
			double[] key = this.key;
			double curr;
			int pos;
			if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) {
				return false;
			} else if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
				this.removeEntry(pos);
				return true;
			} else {
				while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
						this.removeEntry(pos);
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean replace(double k, boolean oldValue, boolean v) {
		int pos = this.find(k);
		if (pos >= 0 && oldValue == this.value[pos]) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean replace(double k, boolean v) {
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
	public boolean computeIfAbsent(double k, DoublePredicate mappingFunction) {
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
	public boolean computeIfAbsentNullable(double k, DoubleFunction<? extends Boolean> mappingFunction) {
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
	public boolean computeIfPresent(double k, BiFunction<? super Double, ? super Boolean, ? extends Boolean> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			Boolean newValue = (Boolean)remappingFunction.apply(k, this.value[pos]);
			if (newValue == null) {
				if (Double.doubleToLongBits(k) == 0L) {
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
	public boolean compute(double k, BiFunction<? super Double, ? super Boolean, ? extends Boolean> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		Boolean newValue = (Boolean)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
		if (newValue == null) {
			if (pos >= 0) {
				if (Double.doubleToLongBits(k) == 0L) {
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
	public boolean merge(double k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return v;
		} else {
			Boolean newValue = (Boolean)remappingFunction.apply(this.value[pos], v);
			if (newValue == null) {
				if (Double.doubleToLongBits(k) == 0L) {
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
			Arrays.fill(this.key, 0.0);
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
	public double firstDoubleKey() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			return this.key[this.first];
		}
	}

	@Override
	public double lastDoubleKey() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			return this.key[this.last];
		}
	}

	@Override
	public Double2BooleanSortedMap tailMap(double from) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Double2BooleanSortedMap headMap(double to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Double2BooleanSortedMap subMap(double from, double to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DoubleComparator comparator() {
		return null;
	}

	public FastSortedEntrySet double2BooleanEntrySet() {
		if (this.entries == null) {
			this.entries = new Double2BooleanLinkedOpenHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public DoubleSortedSet keySet() {
		if (this.keys == null) {
			this.keys = new Double2BooleanLinkedOpenHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public BooleanCollection values() {
		if (this.values == null) {
			this.values = new AbstractBooleanCollection() {
				@Override
				public BooleanIterator iterator() {
					return Double2BooleanLinkedOpenHashMap.this.new ValueIterator();
				}

				public int size() {
					return Double2BooleanLinkedOpenHashMap.this.size;
				}

				@Override
				public boolean contains(boolean v) {
					return Double2BooleanLinkedOpenHashMap.this.containsValue(v);
				}

				public void clear() {
					Double2BooleanLinkedOpenHashMap.this.clear();
				}

				@Override
				public void forEach(BooleanConsumer consumer) {
					if (Double2BooleanLinkedOpenHashMap.this.containsNullKey) {
						consumer.accept(Double2BooleanLinkedOpenHashMap.this.value[Double2BooleanLinkedOpenHashMap.this.n]);
					}

					int pos = Double2BooleanLinkedOpenHashMap.this.n;

					while (pos-- != 0) {
						if (Double.doubleToLongBits(Double2BooleanLinkedOpenHashMap.this.key[pos]) != 0L) {
							consumer.accept(Double2BooleanLinkedOpenHashMap.this.value[pos]);
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
		double[] key = this.key;
		boolean[] value = this.value;
		int mask = newN - 1;
		double[] newKey = new double[newN + 1];
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
			if (Double.doubleToLongBits(key[i]) == 0L) {
				pos = newN;
			} else {
				pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask;

				while (Double.doubleToLongBits(newKey[pos]) != 0L) {
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

	public Double2BooleanLinkedOpenHashMap clone() {
		Double2BooleanLinkedOpenHashMap c;
		try {
			c = (Double2BooleanLinkedOpenHashMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (double[])this.key.clone();
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
			while (Double.doubleToLongBits(this.key[i]) == 0L) {
				i++;
			}

			t = HashCommon.double2int(this.key[i]);
			t ^= this.value[i] ? 1231 : 1237;
			h += t;
		}

		if (this.containsNullKey) {
			h += this.value[this.n] ? 1231 : 1237;
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		double[] key = this.key;
		boolean[] value = this.value;
		Double2BooleanLinkedOpenHashMap.MapIterator i = new Double2BooleanLinkedOpenHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeDouble(key[e]);
			s.writeBoolean(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		double[] key = this.key = new double[this.n + 1];
		boolean[] value = this.value = new boolean[this.n + 1];
		long[] link = this.link = new long[this.n + 1];
		int prev = -1;
		this.first = this.last = -1;
		int i = this.size;

		while (i-- != 0) {
			double k = s.readDouble();
			boolean v = s.readBoolean();
			int pos;
			if (Double.doubleToLongBits(k) == 0L) {
				pos = this.n;
				this.containsNullKey = true;
			} else {
				pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;

				while (Double.doubleToLongBits(key[pos]) != 0L) {
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
		extends Double2BooleanLinkedOpenHashMap.MapIterator
		implements ObjectListIterator<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> {
		private Double2BooleanLinkedOpenHashMap.MapEntry entry;

		public EntryIterator() {
		}

		public EntryIterator(double from) {
			super(from);
		}

		public Double2BooleanLinkedOpenHashMap.MapEntry next() {
			return this.entry = Double2BooleanLinkedOpenHashMap.this.new MapEntry(this.nextEntry());
		}

		public Double2BooleanLinkedOpenHashMap.MapEntry previous() {
			return this.entry = Double2BooleanLinkedOpenHashMap.this.new MapEntry(this.previousEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator
		extends Double2BooleanLinkedOpenHashMap.MapIterator
		implements ObjectListIterator<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> {
		final Double2BooleanLinkedOpenHashMap.MapEntry entry;

		public FastEntryIterator() {
			this.entry = Double2BooleanLinkedOpenHashMap.this.new MapEntry();
		}

		public FastEntryIterator(double from) {
			super(from);
			this.entry = Double2BooleanLinkedOpenHashMap.this.new MapEntry();
		}

		public Double2BooleanLinkedOpenHashMap.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}

		public Double2BooleanLinkedOpenHashMap.MapEntry previous() {
			this.entry.index = this.previousEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Double2BooleanLinkedOpenHashMap.MapIterator implements DoubleListIterator {
		public KeyIterator(double k) {
			super(k);
		}

		@Override
		public double previousDouble() {
			return Double2BooleanLinkedOpenHashMap.this.key[this.previousEntry()];
		}

		public KeyIterator() {
		}

		@Override
		public double nextDouble() {
			return Double2BooleanLinkedOpenHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractDoubleSortedSet {
		private KeySet() {
		}

		public DoubleListIterator iterator(double from) {
			return Double2BooleanLinkedOpenHashMap.this.new KeyIterator(from);
		}

		public DoubleListIterator iterator() {
			return Double2BooleanLinkedOpenHashMap.this.new KeyIterator();
		}

		@Override
		public void forEach(java.util.function.DoubleConsumer consumer) {
			if (Double2BooleanLinkedOpenHashMap.this.containsNullKey) {
				consumer.accept(Double2BooleanLinkedOpenHashMap.this.key[Double2BooleanLinkedOpenHashMap.this.n]);
			}

			int pos = Double2BooleanLinkedOpenHashMap.this.n;

			while (pos-- != 0) {
				double k = Double2BooleanLinkedOpenHashMap.this.key[pos];
				if (Double.doubleToLongBits(k) != 0L) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Double2BooleanLinkedOpenHashMap.this.size;
		}

		@Override
		public boolean contains(double k) {
			return Double2BooleanLinkedOpenHashMap.this.containsKey(k);
		}

		@Override
		public boolean remove(double k) {
			int oldSize = Double2BooleanLinkedOpenHashMap.this.size;
			Double2BooleanLinkedOpenHashMap.this.remove(k);
			return Double2BooleanLinkedOpenHashMap.this.size != oldSize;
		}

		public void clear() {
			Double2BooleanLinkedOpenHashMap.this.clear();
		}

		@Override
		public double firstDouble() {
			if (Double2BooleanLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Double2BooleanLinkedOpenHashMap.this.key[Double2BooleanLinkedOpenHashMap.this.first];
			}
		}

		@Override
		public double lastDouble() {
			if (Double2BooleanLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Double2BooleanLinkedOpenHashMap.this.key[Double2BooleanLinkedOpenHashMap.this.last];
			}
		}

		@Override
		public DoubleComparator comparator() {
			return null;
		}

		@Override
		public DoubleSortedSet tailSet(double from) {
			throw new UnsupportedOperationException();
		}

		@Override
		public DoubleSortedSet headSet(double to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public DoubleSortedSet subSet(double from, double to) {
			throw new UnsupportedOperationException();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry, java.util.Map.Entry<Double, Boolean> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		@Override
		public double getDoubleKey() {
			return Double2BooleanLinkedOpenHashMap.this.key[this.index];
		}

		@Override
		public boolean getBooleanValue() {
			return Double2BooleanLinkedOpenHashMap.this.value[this.index];
		}

		@Override
		public boolean setValue(boolean v) {
			boolean oldValue = Double2BooleanLinkedOpenHashMap.this.value[this.index];
			Double2BooleanLinkedOpenHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Double getKey() {
			return Double2BooleanLinkedOpenHashMap.this.key[this.index];
		}

		@Deprecated
		@Override
		public Boolean getValue() {
			return Double2BooleanLinkedOpenHashMap.this.value[this.index];
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
				java.util.Map.Entry<Double, Boolean> e = (java.util.Map.Entry<Double, Boolean>)o;
				return Double.doubleToLongBits(Double2BooleanLinkedOpenHashMap.this.key[this.index]) == Double.doubleToLongBits((Double)e.getKey())
					&& Double2BooleanLinkedOpenHashMap.this.value[this.index] == (Boolean)e.getValue();
			}
		}

		public int hashCode() {
			return HashCommon.double2int(Double2BooleanLinkedOpenHashMap.this.key[this.index]) ^ (Double2BooleanLinkedOpenHashMap.this.value[this.index] ? 1231 : 1237);
		}

		public String toString() {
			return Double2BooleanLinkedOpenHashMap.this.key[this.index] + "=>" + Double2BooleanLinkedOpenHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSortedSet<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> implements FastSortedEntrySet {
		private MapEntrySet() {
		}

		@Override
		public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> iterator() {
			return Double2BooleanLinkedOpenHashMap.this.new EntryIterator();
		}

		public Comparator<? super it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> comparator() {
			return null;
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> subSet(
			it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry fromElement, it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry toElement
		) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> headSet(it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry toElement) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> tailSet(it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry fromElement) {
			throw new UnsupportedOperationException();
		}

		public it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry first() {
			if (Double2BooleanLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Double2BooleanLinkedOpenHashMap.this.new MapEntry(Double2BooleanLinkedOpenHashMap.this.first);
			}
		}

		public it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry last() {
			if (Double2BooleanLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Double2BooleanLinkedOpenHashMap.this.new MapEntry(Double2BooleanLinkedOpenHashMap.this.last);
			}
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Double)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Boolean) {
					double k = (Double)e.getKey();
					boolean v = (Boolean)e.getValue();
					if (Double.doubleToLongBits(k) == 0L) {
						return Double2BooleanLinkedOpenHashMap.this.containsNullKey && Double2BooleanLinkedOpenHashMap.this.value[Double2BooleanLinkedOpenHashMap.this.n] == v;
					} else {
						double[] key = Double2BooleanLinkedOpenHashMap.this.key;
						double curr;
						int pos;
						if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2BooleanLinkedOpenHashMap.this.mask]) == 0L) {
							return false;
						} else if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
							return Double2BooleanLinkedOpenHashMap.this.value[pos] == v;
						} else {
							while (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2BooleanLinkedOpenHashMap.this.mask]) != 0L) {
								if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
									return Double2BooleanLinkedOpenHashMap.this.value[pos] == v;
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
				if (e.getKey() != null && e.getKey() instanceof Double) {
					if (e.getValue() != null && e.getValue() instanceof Boolean) {
						double k = (Double)e.getKey();
						boolean v = (Boolean)e.getValue();
						if (Double.doubleToLongBits(k) == 0L) {
							if (Double2BooleanLinkedOpenHashMap.this.containsNullKey && Double2BooleanLinkedOpenHashMap.this.value[Double2BooleanLinkedOpenHashMap.this.n] == v) {
								Double2BooleanLinkedOpenHashMap.this.removeNullEntry();
								return true;
							} else {
								return false;
							}
						} else {
							double[] key = Double2BooleanLinkedOpenHashMap.this.key;
							double curr;
							int pos;
							if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2BooleanLinkedOpenHashMap.this.mask]) == 0L) {
								return false;
							} else if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
								if (Double2BooleanLinkedOpenHashMap.this.value[pos] == v) {
									Double2BooleanLinkedOpenHashMap.this.removeEntry(pos);
									return true;
								} else {
									return false;
								}
							} else {
								while (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2BooleanLinkedOpenHashMap.this.mask]) != 0L) {
									if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && Double2BooleanLinkedOpenHashMap.this.value[pos] == v) {
										Double2BooleanLinkedOpenHashMap.this.removeEntry(pos);
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
			return Double2BooleanLinkedOpenHashMap.this.size;
		}

		public void clear() {
			Double2BooleanLinkedOpenHashMap.this.clear();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> iterator(it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry from) {
			return Double2BooleanLinkedOpenHashMap.this.new EntryIterator(from.getDoubleKey());
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> fastIterator() {
			return Double2BooleanLinkedOpenHashMap.this.new FastEntryIterator();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> fastIterator(it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry from) {
			return Double2BooleanLinkedOpenHashMap.this.new FastEntryIterator(from.getDoubleKey());
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> consumer) {
			int i = Double2BooleanLinkedOpenHashMap.this.size;
			int next = Double2BooleanLinkedOpenHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Double2BooleanLinkedOpenHashMap.this.link[next];
				consumer.accept(new BasicEntry(Double2BooleanLinkedOpenHashMap.this.key[curr], Double2BooleanLinkedOpenHashMap.this.value[curr]));
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> consumer) {
			BasicEntry entry = new BasicEntry();
			int i = Double2BooleanLinkedOpenHashMap.this.size;
			int next = Double2BooleanLinkedOpenHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Double2BooleanLinkedOpenHashMap.this.link[next];
				entry.key = Double2BooleanLinkedOpenHashMap.this.key[curr];
				entry.value = Double2BooleanLinkedOpenHashMap.this.value[curr];
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
			this.next = Double2BooleanLinkedOpenHashMap.this.first;
			this.index = 0;
		}

		private MapIterator(double from) {
			if (Double.doubleToLongBits(from) == 0L) {
				if (Double2BooleanLinkedOpenHashMap.this.containsNullKey) {
					this.next = (int)Double2BooleanLinkedOpenHashMap.this.link[Double2BooleanLinkedOpenHashMap.this.n];
					this.prev = Double2BooleanLinkedOpenHashMap.this.n;
				} else {
					throw new NoSuchElementException("The key " + from + " does not belong to this map.");
				}
			} else if (Double.doubleToLongBits(Double2BooleanLinkedOpenHashMap.this.key[Double2BooleanLinkedOpenHashMap.this.last]) == Double.doubleToLongBits(from)) {
				this.prev = Double2BooleanLinkedOpenHashMap.this.last;
				this.index = Double2BooleanLinkedOpenHashMap.this.size;
			} else {
				for (int pos = (int)HashCommon.mix(Double.doubleToRawLongBits(from)) & Double2BooleanLinkedOpenHashMap.this.mask;
					Double.doubleToLongBits(Double2BooleanLinkedOpenHashMap.this.key[pos]) != 0L;
					pos = pos + 1 & Double2BooleanLinkedOpenHashMap.this.mask
				) {
					if (Double.doubleToLongBits(Double2BooleanLinkedOpenHashMap.this.key[pos]) == Double.doubleToLongBits(from)) {
						this.next = (int)Double2BooleanLinkedOpenHashMap.this.link[pos];
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
					this.index = Double2BooleanLinkedOpenHashMap.this.size;
				} else {
					int pos = Double2BooleanLinkedOpenHashMap.this.first;

					for (this.index = 1; pos != this.prev; this.index++) {
						pos = (int)Double2BooleanLinkedOpenHashMap.this.link[pos];
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
				this.next = (int)Double2BooleanLinkedOpenHashMap.this.link[this.curr];
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
				this.prev = (int)(Double2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32);
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
					this.prev = (int)(Double2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32);
				} else {
					this.next = (int)Double2BooleanLinkedOpenHashMap.this.link[this.curr];
				}

				Double2BooleanLinkedOpenHashMap.this.size--;
				if (this.prev == -1) {
					Double2BooleanLinkedOpenHashMap.this.first = this.next;
				} else {
					Double2BooleanLinkedOpenHashMap.this.link[this.prev] = Double2BooleanLinkedOpenHashMap.this.link[this.prev]
						^ (Double2BooleanLinkedOpenHashMap.this.link[this.prev] ^ (long)this.next & 4294967295L) & 4294967295L;
				}

				if (this.next == -1) {
					Double2BooleanLinkedOpenHashMap.this.last = this.prev;
				} else {
					Double2BooleanLinkedOpenHashMap.this.link[this.next] = Double2BooleanLinkedOpenHashMap.this.link[this.next]
						^ (Double2BooleanLinkedOpenHashMap.this.link[this.next] ^ ((long)this.prev & 4294967295L) << 32) & -4294967296L;
				}

				int pos = this.curr;
				this.curr = -1;
				if (pos == Double2BooleanLinkedOpenHashMap.this.n) {
					Double2BooleanLinkedOpenHashMap.this.containsNullKey = false;
				} else {
					double[] key = Double2BooleanLinkedOpenHashMap.this.key;

					label61:
					while (true) {
						int last = pos;

						double curr;
						for (pos = pos + 1 & Double2BooleanLinkedOpenHashMap.this.mask;
							Double.doubleToLongBits(curr = key[pos]) != 0L;
							pos = pos + 1 & Double2BooleanLinkedOpenHashMap.this.mask
						) {
							int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2BooleanLinkedOpenHashMap.this.mask;
							if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
								key[last] = curr;
								Double2BooleanLinkedOpenHashMap.this.value[last] = Double2BooleanLinkedOpenHashMap.this.value[pos];
								if (this.next == pos) {
									this.next = last;
								}

								if (this.prev == pos) {
									this.prev = last;
								}

								Double2BooleanLinkedOpenHashMap.this.fixPointers(pos, last);
								continue label61;
							}
						}

						key[last] = 0.0;
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

		public void set(it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry ok) {
			throw new UnsupportedOperationException();
		}

		public void add(it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry ok) {
			throw new UnsupportedOperationException();
		}
	}

	private final class ValueIterator extends Double2BooleanLinkedOpenHashMap.MapIterator implements BooleanListIterator {
		@Override
		public boolean previousBoolean() {
			return Double2BooleanLinkedOpenHashMap.this.value[this.previousEntry()];
		}

		public ValueIterator() {
		}

		@Override
		public boolean nextBoolean() {
			return Double2BooleanLinkedOpenHashMap.this.value[this.nextEntry()];
		}
	}
}
