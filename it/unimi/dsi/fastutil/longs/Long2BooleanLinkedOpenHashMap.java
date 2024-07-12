package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2BooleanMap.BasicEntry;
import it.unimi.dsi.fastutil.longs.Long2BooleanSortedMap.FastSortedEntrySet;
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
import java.util.function.LongFunction;
import java.util.function.LongPredicate;

public class Long2BooleanLinkedOpenHashMap extends AbstractLong2BooleanSortedMap implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient long[] key;
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
	protected transient LongSortedSet keys;
	protected transient BooleanCollection values;

	public Long2BooleanLinkedOpenHashMap(int expected, float f) {
		if (f <= 0.0F || f > 1.0F) {
			throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
		} else if (expected < 0) {
			throw new IllegalArgumentException("The expected number of elements must be nonnegative");
		} else {
			this.f = f;
			this.minN = this.n = HashCommon.arraySize(expected, f);
			this.mask = this.n - 1;
			this.maxFill = HashCommon.maxFill(this.n, f);
			this.key = new long[this.n + 1];
			this.value = new boolean[this.n + 1];
			this.link = new long[this.n + 1];
		}
	}

	public Long2BooleanLinkedOpenHashMap(int expected) {
		this(expected, 0.75F);
	}

	public Long2BooleanLinkedOpenHashMap() {
		this(16, 0.75F);
	}

	public Long2BooleanLinkedOpenHashMap(Map<? extends Long, ? extends Boolean> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Long2BooleanLinkedOpenHashMap(Map<? extends Long, ? extends Boolean> m) {
		this(m, 0.75F);
	}

	public Long2BooleanLinkedOpenHashMap(Long2BooleanMap m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Long2BooleanLinkedOpenHashMap(Long2BooleanMap m) {
		this(m, 0.75F);
	}

	public Long2BooleanLinkedOpenHashMap(long[] k, boolean[] v, float f) {
		this(k.length, f);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Long2BooleanLinkedOpenHashMap(long[] k, boolean[] v) {
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
	public void putAll(Map<? extends Long, ? extends Boolean> m) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(m.size());
		} else {
			this.tryCapacity((long)(this.size() + m.size()));
		}

		super.putAll(m);
	}

	private int find(long k) {
		if (k == 0L) {
			return this.containsNullKey ? this.n : -(this.n + 1);
		} else {
			long[] key = this.key;
			long curr;
			int pos;
			if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) {
				return -(pos + 1);
			} else if (k == curr) {
				return pos;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (k == curr) {
						return pos;
					}
				}

				return -(pos + 1);
			}
		}
	}

	private void insert(int pos, long k, boolean v) {
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
	public boolean put(long k, boolean v) {
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
		long[] key = this.key;

		label30:
		while (true) {
			int last = pos;

			long curr;
			for (pos = pos + 1 & this.mask; (curr = key[pos]) != 0L; pos = pos + 1 & this.mask) {
				int slot = (int)HashCommon.mix(curr) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					key[last] = curr;
					this.value[last] = this.value[pos];
					this.fixPointers(pos, last);
					continue label30;
				}
			}

			key[last] = 0L;
			return;
		}
	}

	@Override
	public boolean remove(long k) {
		if (k == 0L) {
			return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
		} else {
			long[] key = this.key;
			long curr;
			int pos;
			if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) {
				return this.defRetValue;
			} else if (k == curr) {
				return this.removeEntry(pos);
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (k == curr) {
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

	public boolean getAndMoveToFirst(long k) {
		if (k == 0L) {
			if (this.containsNullKey) {
				this.moveIndexToFirst(this.n);
				return this.value[this.n];
			} else {
				return this.defRetValue;
			}
		} else {
			long[] key = this.key;
			long curr;
			int pos;
			if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) {
				return this.defRetValue;
			} else if (k == curr) {
				this.moveIndexToFirst(pos);
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (k == curr) {
						this.moveIndexToFirst(pos);
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	public boolean getAndMoveToLast(long k) {
		if (k == 0L) {
			if (this.containsNullKey) {
				this.moveIndexToLast(this.n);
				return this.value[this.n];
			} else {
				return this.defRetValue;
			}
		} else {
			long[] key = this.key;
			long curr;
			int pos;
			if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) {
				return this.defRetValue;
			} else if (k == curr) {
				this.moveIndexToLast(pos);
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (k == curr) {
						this.moveIndexToLast(pos);
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	public boolean putAndMoveToFirst(long k, boolean v) {
		int pos;
		if (k == 0L) {
			if (this.containsNullKey) {
				this.moveIndexToFirst(this.n);
				return this.setValue(this.n, v);
			}

			this.containsNullKey = true;
			pos = this.n;
		} else {
			long[] key = this.key;
			long curr;
			if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
				if (curr == k) {
					this.moveIndexToFirst(pos);
					return this.setValue(pos, v);
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
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

	public boolean putAndMoveToLast(long k, boolean v) {
		int pos;
		if (k == 0L) {
			if (this.containsNullKey) {
				this.moveIndexToLast(this.n);
				return this.setValue(this.n, v);
			}

			this.containsNullKey = true;
			pos = this.n;
		} else {
			long[] key = this.key;
			long curr;
			if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
				if (curr == k) {
					this.moveIndexToLast(pos);
					return this.setValue(pos, v);
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
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
	public boolean get(long k) {
		if (k == 0L) {
			return this.containsNullKey ? this.value[this.n] : this.defRetValue;
		} else {
			long[] key = this.key;
			long curr;
			int pos;
			if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) {
				return this.defRetValue;
			} else if (k == curr) {
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (k == curr) {
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public boolean containsKey(long k) {
		if (k == 0L) {
			return this.containsNullKey;
		} else {
			long[] key = this.key;
			long curr;
			int pos;
			if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) {
				return false;
			} else if (k == curr) {
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (k == curr) {
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
		long[] key = this.key;
		if (this.containsNullKey && value[this.n] == v) {
			return true;
		} else {
			int i = this.n;

			while (i-- != 0) {
				if (key[i] != 0L && value[i] == v) {
					return true;
				}
			}

			return false;
		}
	}

	@Override
	public boolean getOrDefault(long k, boolean defaultValue) {
		if (k == 0L) {
			return this.containsNullKey ? this.value[this.n] : defaultValue;
		} else {
			long[] key = this.key;
			long curr;
			int pos;
			if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) {
				return defaultValue;
			} else if (k == curr) {
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
					if (k == curr) {
						return this.value[pos];
					}
				}

				return defaultValue;
			}
		}
	}

	@Override
	public boolean putIfAbsent(long k, boolean v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(long k, boolean v) {
		if (k == 0L) {
			if (this.containsNullKey && v == this.value[this.n]) {
				this.removeNullEntry();
				return true;
			} else {
				return false;
			}
		} else {
			long[] key = this.key;
			long curr;
			int pos;
			if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) {
				return false;
			} else if (k == curr && v == this.value[pos]) {
				this.removeEntry(pos);
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
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
	public boolean replace(long k, boolean oldValue, boolean v) {
		int pos = this.find(k);
		if (pos >= 0 && oldValue == this.value[pos]) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean replace(long k, boolean v) {
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
	public boolean computeIfAbsent(long k, LongPredicate mappingFunction) {
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
	public boolean computeIfAbsentNullable(long k, LongFunction<? extends Boolean> mappingFunction) {
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
	public boolean computeIfPresent(long k, BiFunction<? super Long, ? super Boolean, ? extends Boolean> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			Boolean newValue = (Boolean)remappingFunction.apply(k, this.value[pos]);
			if (newValue == null) {
				if (k == 0L) {
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
	public boolean compute(long k, BiFunction<? super Long, ? super Boolean, ? extends Boolean> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		Boolean newValue = (Boolean)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
		if (newValue == null) {
			if (pos >= 0) {
				if (k == 0L) {
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
	public boolean merge(long k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return v;
		} else {
			Boolean newValue = (Boolean)remappingFunction.apply(this.value[pos], v);
			if (newValue == null) {
				if (k == 0L) {
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
			Arrays.fill(this.key, 0L);
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
	public long firstLongKey() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			return this.key[this.first];
		}
	}

	@Override
	public long lastLongKey() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			return this.key[this.last];
		}
	}

	@Override
	public Long2BooleanSortedMap tailMap(long from) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long2BooleanSortedMap headMap(long to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long2BooleanSortedMap subMap(long from, long to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public LongComparator comparator() {
		return null;
	}

	public FastSortedEntrySet long2BooleanEntrySet() {
		if (this.entries == null) {
			this.entries = new Long2BooleanLinkedOpenHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public LongSortedSet keySet() {
		if (this.keys == null) {
			this.keys = new Long2BooleanLinkedOpenHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public BooleanCollection values() {
		if (this.values == null) {
			this.values = new AbstractBooleanCollection() {
				@Override
				public BooleanIterator iterator() {
					return Long2BooleanLinkedOpenHashMap.this.new ValueIterator();
				}

				public int size() {
					return Long2BooleanLinkedOpenHashMap.this.size;
				}

				@Override
				public boolean contains(boolean v) {
					return Long2BooleanLinkedOpenHashMap.this.containsValue(v);
				}

				public void clear() {
					Long2BooleanLinkedOpenHashMap.this.clear();
				}

				@Override
				public void forEach(BooleanConsumer consumer) {
					if (Long2BooleanLinkedOpenHashMap.this.containsNullKey) {
						consumer.accept(Long2BooleanLinkedOpenHashMap.this.value[Long2BooleanLinkedOpenHashMap.this.n]);
					}

					int pos = Long2BooleanLinkedOpenHashMap.this.n;

					while (pos-- != 0) {
						if (Long2BooleanLinkedOpenHashMap.this.key[pos] != 0L) {
							consumer.accept(Long2BooleanLinkedOpenHashMap.this.value[pos]);
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
		long[] key = this.key;
		boolean[] value = this.value;
		int mask = newN - 1;
		long[] newKey = new long[newN + 1];
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
			if (key[i] == 0L) {
				pos = newN;
			} else {
				pos = (int)HashCommon.mix(key[i]) & mask;

				while (newKey[pos] != 0L) {
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

	public Long2BooleanLinkedOpenHashMap clone() {
		Long2BooleanLinkedOpenHashMap c;
		try {
			c = (Long2BooleanLinkedOpenHashMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (long[])this.key.clone();
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
			while (this.key[i] == 0L) {
				i++;
			}

			t = HashCommon.long2int(this.key[i]);
			t ^= this.value[i] ? 1231 : 1237;
			h += t;
		}

		if (this.containsNullKey) {
			h += this.value[this.n] ? 1231 : 1237;
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		long[] key = this.key;
		boolean[] value = this.value;
		Long2BooleanLinkedOpenHashMap.MapIterator i = new Long2BooleanLinkedOpenHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeLong(key[e]);
			s.writeBoolean(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		long[] key = this.key = new long[this.n + 1];
		boolean[] value = this.value = new boolean[this.n + 1];
		long[] link = this.link = new long[this.n + 1];
		int prev = -1;
		this.first = this.last = -1;
		int i = this.size;

		while (i-- != 0) {
			long k = s.readLong();
			boolean v = s.readBoolean();
			int pos;
			if (k == 0L) {
				pos = this.n;
				this.containsNullKey = true;
			} else {
				pos = (int)HashCommon.mix(k) & this.mask;

				while (key[pos] != 0L) {
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

	private class EntryIterator extends Long2BooleanLinkedOpenHashMap.MapIterator implements ObjectListIterator<it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> {
		private Long2BooleanLinkedOpenHashMap.MapEntry entry;

		public EntryIterator() {
		}

		public EntryIterator(long from) {
			super(from);
		}

		public Long2BooleanLinkedOpenHashMap.MapEntry next() {
			return this.entry = Long2BooleanLinkedOpenHashMap.this.new MapEntry(this.nextEntry());
		}

		public Long2BooleanLinkedOpenHashMap.MapEntry previous() {
			return this.entry = Long2BooleanLinkedOpenHashMap.this.new MapEntry(this.previousEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator
		extends Long2BooleanLinkedOpenHashMap.MapIterator
		implements ObjectListIterator<it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> {
		final Long2BooleanLinkedOpenHashMap.MapEntry entry;

		public FastEntryIterator() {
			this.entry = Long2BooleanLinkedOpenHashMap.this.new MapEntry();
		}

		public FastEntryIterator(long from) {
			super(from);
			this.entry = Long2BooleanLinkedOpenHashMap.this.new MapEntry();
		}

		public Long2BooleanLinkedOpenHashMap.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}

		public Long2BooleanLinkedOpenHashMap.MapEntry previous() {
			this.entry.index = this.previousEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Long2BooleanLinkedOpenHashMap.MapIterator implements LongListIterator {
		public KeyIterator(long k) {
			super(k);
		}

		@Override
		public long previousLong() {
			return Long2BooleanLinkedOpenHashMap.this.key[this.previousEntry()];
		}

		public KeyIterator() {
		}

		@Override
		public long nextLong() {
			return Long2BooleanLinkedOpenHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractLongSortedSet {
		private KeySet() {
		}

		public LongListIterator iterator(long from) {
			return Long2BooleanLinkedOpenHashMap.this.new KeyIterator(from);
		}

		public LongListIterator iterator() {
			return Long2BooleanLinkedOpenHashMap.this.new KeyIterator();
		}

		@Override
		public void forEach(java.util.function.LongConsumer consumer) {
			if (Long2BooleanLinkedOpenHashMap.this.containsNullKey) {
				consumer.accept(Long2BooleanLinkedOpenHashMap.this.key[Long2BooleanLinkedOpenHashMap.this.n]);
			}

			int pos = Long2BooleanLinkedOpenHashMap.this.n;

			while (pos-- != 0) {
				long k = Long2BooleanLinkedOpenHashMap.this.key[pos];
				if (k != 0L) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Long2BooleanLinkedOpenHashMap.this.size;
		}

		@Override
		public boolean contains(long k) {
			return Long2BooleanLinkedOpenHashMap.this.containsKey(k);
		}

		@Override
		public boolean remove(long k) {
			int oldSize = Long2BooleanLinkedOpenHashMap.this.size;
			Long2BooleanLinkedOpenHashMap.this.remove(k);
			return Long2BooleanLinkedOpenHashMap.this.size != oldSize;
		}

		public void clear() {
			Long2BooleanLinkedOpenHashMap.this.clear();
		}

		@Override
		public long firstLong() {
			if (Long2BooleanLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Long2BooleanLinkedOpenHashMap.this.key[Long2BooleanLinkedOpenHashMap.this.first];
			}
		}

		@Override
		public long lastLong() {
			if (Long2BooleanLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Long2BooleanLinkedOpenHashMap.this.key[Long2BooleanLinkedOpenHashMap.this.last];
			}
		}

		@Override
		public LongComparator comparator() {
			return null;
		}

		@Override
		public LongSortedSet tailSet(long from) {
			throw new UnsupportedOperationException();
		}

		@Override
		public LongSortedSet headSet(long to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public LongSortedSet subSet(long from, long to) {
			throw new UnsupportedOperationException();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry, java.util.Map.Entry<Long, Boolean> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		@Override
		public long getLongKey() {
			return Long2BooleanLinkedOpenHashMap.this.key[this.index];
		}

		@Override
		public boolean getBooleanValue() {
			return Long2BooleanLinkedOpenHashMap.this.value[this.index];
		}

		@Override
		public boolean setValue(boolean v) {
			boolean oldValue = Long2BooleanLinkedOpenHashMap.this.value[this.index];
			Long2BooleanLinkedOpenHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Long getKey() {
			return Long2BooleanLinkedOpenHashMap.this.key[this.index];
		}

		@Deprecated
		@Override
		public Boolean getValue() {
			return Long2BooleanLinkedOpenHashMap.this.value[this.index];
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
				java.util.Map.Entry<Long, Boolean> e = (java.util.Map.Entry<Long, Boolean>)o;
				return Long2BooleanLinkedOpenHashMap.this.key[this.index] == (Long)e.getKey()
					&& Long2BooleanLinkedOpenHashMap.this.value[this.index] == (Boolean)e.getValue();
			}
		}

		public int hashCode() {
			return HashCommon.long2int(Long2BooleanLinkedOpenHashMap.this.key[this.index]) ^ (Long2BooleanLinkedOpenHashMap.this.value[this.index] ? 1231 : 1237);
		}

		public String toString() {
			return Long2BooleanLinkedOpenHashMap.this.key[this.index] + "=>" + Long2BooleanLinkedOpenHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSortedSet<it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> implements FastSortedEntrySet {
		private MapEntrySet() {
		}

		@Override
		public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> iterator() {
			return Long2BooleanLinkedOpenHashMap.this.new EntryIterator();
		}

		public Comparator<? super it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> comparator() {
			return null;
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> subSet(
			it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry fromElement, it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry toElement
		) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> headSet(it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry toElement) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> tailSet(it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry fromElement) {
			throw new UnsupportedOperationException();
		}

		public it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry first() {
			if (Long2BooleanLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Long2BooleanLinkedOpenHashMap.this.new MapEntry(Long2BooleanLinkedOpenHashMap.this.first);
			}
		}

		public it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry last() {
			if (Long2BooleanLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Long2BooleanLinkedOpenHashMap.this.new MapEntry(Long2BooleanLinkedOpenHashMap.this.last);
			}
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Long)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Boolean) {
					long k = (Long)e.getKey();
					boolean v = (Boolean)e.getValue();
					if (k == 0L) {
						return Long2BooleanLinkedOpenHashMap.this.containsNullKey && Long2BooleanLinkedOpenHashMap.this.value[Long2BooleanLinkedOpenHashMap.this.n] == v;
					} else {
						long[] key = Long2BooleanLinkedOpenHashMap.this.key;
						long curr;
						int pos;
						if ((curr = key[pos = (int)HashCommon.mix(k) & Long2BooleanLinkedOpenHashMap.this.mask]) == 0L) {
							return false;
						} else if (k == curr) {
							return Long2BooleanLinkedOpenHashMap.this.value[pos] == v;
						} else {
							while ((curr = key[pos = pos + 1 & Long2BooleanLinkedOpenHashMap.this.mask]) != 0L) {
								if (k == curr) {
									return Long2BooleanLinkedOpenHashMap.this.value[pos] == v;
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
				if (e.getKey() != null && e.getKey() instanceof Long) {
					if (e.getValue() != null && e.getValue() instanceof Boolean) {
						long k = (Long)e.getKey();
						boolean v = (Boolean)e.getValue();
						if (k == 0L) {
							if (Long2BooleanLinkedOpenHashMap.this.containsNullKey && Long2BooleanLinkedOpenHashMap.this.value[Long2BooleanLinkedOpenHashMap.this.n] == v) {
								Long2BooleanLinkedOpenHashMap.this.removeNullEntry();
								return true;
							} else {
								return false;
							}
						} else {
							long[] key = Long2BooleanLinkedOpenHashMap.this.key;
							long curr;
							int pos;
							if ((curr = key[pos = (int)HashCommon.mix(k) & Long2BooleanLinkedOpenHashMap.this.mask]) == 0L) {
								return false;
							} else if (curr == k) {
								if (Long2BooleanLinkedOpenHashMap.this.value[pos] == v) {
									Long2BooleanLinkedOpenHashMap.this.removeEntry(pos);
									return true;
								} else {
									return false;
								}
							} else {
								while ((curr = key[pos = pos + 1 & Long2BooleanLinkedOpenHashMap.this.mask]) != 0L) {
									if (curr == k && Long2BooleanLinkedOpenHashMap.this.value[pos] == v) {
										Long2BooleanLinkedOpenHashMap.this.removeEntry(pos);
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
			return Long2BooleanLinkedOpenHashMap.this.size;
		}

		public void clear() {
			Long2BooleanLinkedOpenHashMap.this.clear();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> iterator(it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry from) {
			return Long2BooleanLinkedOpenHashMap.this.new EntryIterator(from.getLongKey());
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> fastIterator() {
			return Long2BooleanLinkedOpenHashMap.this.new FastEntryIterator();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> fastIterator(it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry from) {
			return Long2BooleanLinkedOpenHashMap.this.new FastEntryIterator(from.getLongKey());
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> consumer) {
			int i = Long2BooleanLinkedOpenHashMap.this.size;
			int next = Long2BooleanLinkedOpenHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Long2BooleanLinkedOpenHashMap.this.link[next];
				consumer.accept(new BasicEntry(Long2BooleanLinkedOpenHashMap.this.key[curr], Long2BooleanLinkedOpenHashMap.this.value[curr]));
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry> consumer) {
			BasicEntry entry = new BasicEntry();
			int i = Long2BooleanLinkedOpenHashMap.this.size;
			int next = Long2BooleanLinkedOpenHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Long2BooleanLinkedOpenHashMap.this.link[next];
				entry.key = Long2BooleanLinkedOpenHashMap.this.key[curr];
				entry.value = Long2BooleanLinkedOpenHashMap.this.value[curr];
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
			this.next = Long2BooleanLinkedOpenHashMap.this.first;
			this.index = 0;
		}

		private MapIterator(long from) {
			if (from == 0L) {
				if (Long2BooleanLinkedOpenHashMap.this.containsNullKey) {
					this.next = (int)Long2BooleanLinkedOpenHashMap.this.link[Long2BooleanLinkedOpenHashMap.this.n];
					this.prev = Long2BooleanLinkedOpenHashMap.this.n;
				} else {
					throw new NoSuchElementException("The key " + from + " does not belong to this map.");
				}
			} else if (Long2BooleanLinkedOpenHashMap.this.key[Long2BooleanLinkedOpenHashMap.this.last] == from) {
				this.prev = Long2BooleanLinkedOpenHashMap.this.last;
				this.index = Long2BooleanLinkedOpenHashMap.this.size;
			} else {
				for (int pos = (int)HashCommon.mix(from) & Long2BooleanLinkedOpenHashMap.this.mask;
					Long2BooleanLinkedOpenHashMap.this.key[pos] != 0L;
					pos = pos + 1 & Long2BooleanLinkedOpenHashMap.this.mask
				) {
					if (Long2BooleanLinkedOpenHashMap.this.key[pos] == from) {
						this.next = (int)Long2BooleanLinkedOpenHashMap.this.link[pos];
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
					this.index = Long2BooleanLinkedOpenHashMap.this.size;
				} else {
					int pos = Long2BooleanLinkedOpenHashMap.this.first;

					for (this.index = 1; pos != this.prev; this.index++) {
						pos = (int)Long2BooleanLinkedOpenHashMap.this.link[pos];
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
				this.next = (int)Long2BooleanLinkedOpenHashMap.this.link[this.curr];
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
				this.prev = (int)(Long2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32);
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
					this.prev = (int)(Long2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32);
				} else {
					this.next = (int)Long2BooleanLinkedOpenHashMap.this.link[this.curr];
				}

				Long2BooleanLinkedOpenHashMap.this.size--;
				if (this.prev == -1) {
					Long2BooleanLinkedOpenHashMap.this.first = this.next;
				} else {
					Long2BooleanLinkedOpenHashMap.this.link[this.prev] = Long2BooleanLinkedOpenHashMap.this.link[this.prev]
						^ (Long2BooleanLinkedOpenHashMap.this.link[this.prev] ^ (long)this.next & 4294967295L) & 4294967295L;
				}

				if (this.next == -1) {
					Long2BooleanLinkedOpenHashMap.this.last = this.prev;
				} else {
					Long2BooleanLinkedOpenHashMap.this.link[this.next] = Long2BooleanLinkedOpenHashMap.this.link[this.next]
						^ (Long2BooleanLinkedOpenHashMap.this.link[this.next] ^ ((long)this.prev & 4294967295L) << 32) & -4294967296L;
				}

				int pos = this.curr;
				this.curr = -1;
				if (pos == Long2BooleanLinkedOpenHashMap.this.n) {
					Long2BooleanLinkedOpenHashMap.this.containsNullKey = false;
				} else {
					long[] key = Long2BooleanLinkedOpenHashMap.this.key;

					label61:
					while (true) {
						int last = pos;

						long curr;
						for (pos = pos + 1 & Long2BooleanLinkedOpenHashMap.this.mask; (curr = key[pos]) != 0L; pos = pos + 1 & Long2BooleanLinkedOpenHashMap.this.mask) {
							int slot = (int)HashCommon.mix(curr) & Long2BooleanLinkedOpenHashMap.this.mask;
							if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
								key[last] = curr;
								Long2BooleanLinkedOpenHashMap.this.value[last] = Long2BooleanLinkedOpenHashMap.this.value[pos];
								if (this.next == pos) {
									this.next = last;
								}

								if (this.prev == pos) {
									this.prev = last;
								}

								Long2BooleanLinkedOpenHashMap.this.fixPointers(pos, last);
								continue label61;
							}
						}

						key[last] = 0L;
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

		public void set(it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry ok) {
			throw new UnsupportedOperationException();
		}

		public void add(it.unimi.dsi.fastutil.longs.Long2BooleanMap.Entry ok) {
			throw new UnsupportedOperationException();
		}
	}

	private final class ValueIterator extends Long2BooleanLinkedOpenHashMap.MapIterator implements BooleanListIterator {
		@Override
		public boolean previousBoolean() {
			return Long2BooleanLinkedOpenHashMap.this.value[this.previousEntry()];
		}

		public ValueIterator() {
		}

		@Override
		public boolean nextBoolean() {
			return Long2BooleanLinkedOpenHashMap.this.value[this.nextEntry()];
		}
	}
}
