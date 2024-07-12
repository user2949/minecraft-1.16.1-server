package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.Hash.Strategy;
import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharListIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2CharMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2CharSortedMap.FastSortedEntrySet;
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
import java.util.function.IntConsumer;
import java.util.function.ToIntFunction;

public class Object2CharLinkedOpenCustomHashMap<K> extends AbstractObject2CharSortedMap<K> implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient K[] key;
	protected transient char[] value;
	protected transient int mask;
	protected transient boolean containsNullKey;
	protected Strategy<K> strategy;
	protected transient int first = -1;
	protected transient int last = -1;
	protected transient long[] link;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;
	protected transient FastSortedEntrySet<K> entries;
	protected transient ObjectSortedSet<K> keys;
	protected transient CharCollection values;

	public Object2CharLinkedOpenCustomHashMap(int expected, float f, Strategy<K> strategy) {
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
			this.value = new char[this.n + 1];
			this.link = new long[this.n + 1];
		}
	}

	public Object2CharLinkedOpenCustomHashMap(int expected, Strategy<K> strategy) {
		this(expected, 0.75F, strategy);
	}

	public Object2CharLinkedOpenCustomHashMap(Strategy<K> strategy) {
		this(16, 0.75F, strategy);
	}

	public Object2CharLinkedOpenCustomHashMap(Map<? extends K, ? extends Character> m, float f, Strategy<K> strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Object2CharLinkedOpenCustomHashMap(Map<? extends K, ? extends Character> m, Strategy<K> strategy) {
		this(m, 0.75F, strategy);
	}

	public Object2CharLinkedOpenCustomHashMap(Object2CharMap<K> m, float f, Strategy<K> strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Object2CharLinkedOpenCustomHashMap(Object2CharMap<K> m, Strategy<K> strategy) {
		this(m, 0.75F, strategy);
	}

	public Object2CharLinkedOpenCustomHashMap(K[] k, char[] v, float f, Strategy<K> strategy) {
		this(k.length, f, strategy);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Object2CharLinkedOpenCustomHashMap(K[] k, char[] v, Strategy<K> strategy) {
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

	private char removeEntry(int pos) {
		char oldValue = this.value[pos];
		this.size--;
		this.fixPointers(pos);
		this.shiftKeys(pos);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	private char removeNullEntry() {
		this.containsNullKey = false;
		this.key[this.n] = null;
		char oldValue = this.value[this.n];
		this.size--;
		this.fixPointers(this.n);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	@Override
	public void putAll(Map<? extends K, ? extends Character> m) {
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

	private void insert(int pos, K k, char v) {
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
	public char put(K k, char v) {
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		} else {
			char oldValue = this.value[pos];
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
					this.fixPointers(pos, last);
					continue label30;
				}
			}

			key[last] = null;
			return;
		}
	}

	@Override
	public char removeChar(Object k) {
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

	private char setValue(int pos, char v) {
		char oldValue = this.value[pos];
		this.value[pos] = v;
		return oldValue;
	}

	public char removeFirstChar() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			int pos = this.first;
			this.first = (int)this.link[pos];
			if (0 <= this.first) {
				this.link[this.first] = this.link[this.first] | -4294967296L;
			}

			this.size--;
			char v = this.value[pos];
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

	public char removeLastChar() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			int pos = this.last;
			this.last = (int)(this.link[pos] >>> 32);
			if (0 <= this.last) {
				this.link[this.last] = this.link[this.last] | 4294967295L;
			}

			this.size--;
			char v = this.value[pos];
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

	public char getAndMoveToFirst(K k) {
		if (this.strategy.equals(k, null)) {
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
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) {
				return this.defRetValue;
			} else if (this.strategy.equals(k, curr)) {
				this.moveIndexToFirst(pos);
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (this.strategy.equals(k, curr)) {
						this.moveIndexToFirst(pos);
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	public char getAndMoveToLast(K k) {
		if (this.strategy.equals(k, null)) {
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
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) {
				return this.defRetValue;
			} else if (this.strategy.equals(k, curr)) {
				this.moveIndexToLast(pos);
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (this.strategy.equals(k, curr)) {
						this.moveIndexToLast(pos);
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	public char putAndMoveToFirst(K k, char v) {
		int pos;
		if (this.strategy.equals(k, null)) {
			if (this.containsNullKey) {
				this.moveIndexToFirst(this.n);
				return this.setValue(this.n, v);
			}

			this.containsNullKey = true;
			pos = this.n;
		} else {
			K[] key = this.key;
			K curr;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
				if (this.strategy.equals(curr, k)) {
					this.moveIndexToFirst(pos);
					return this.setValue(pos, v);
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (this.strategy.equals(curr, k)) {
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

	public char putAndMoveToLast(K k, char v) {
		int pos;
		if (this.strategy.equals(k, null)) {
			if (this.containsNullKey) {
				this.moveIndexToLast(this.n);
				return this.setValue(this.n, v);
			}

			this.containsNullKey = true;
			pos = this.n;
		} else {
			K[] key = this.key;
			K curr;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
				if (this.strategy.equals(curr, k)) {
					this.moveIndexToLast(pos);
					return this.setValue(pos, v);
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (this.strategy.equals(curr, k)) {
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
	public char getChar(Object k) {
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
	public boolean containsValue(char v) {
		char[] value = this.value;
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
	public char getOrDefault(Object k, char defaultValue) {
		if (this.strategy.equals((K)k, null)) {
			return this.containsNullKey ? this.value[this.n] : defaultValue;
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode((K)k)) & this.mask]) == null) {
				return defaultValue;
			} else if (this.strategy.equals((K)k, curr)) {
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (this.strategy.equals((K)k, curr)) {
						return this.value[pos];
					}
				}

				return defaultValue;
			}
		}
	}

	@Override
	public char putIfAbsent(K k, char v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(Object k, char v) {
		if (this.strategy.equals((K)k, null)) {
			if (this.containsNullKey && v == this.value[this.n]) {
				this.removeNullEntry();
				return true;
			} else {
				return false;
			}
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode((K)k)) & this.mask]) == null) {
				return false;
			} else if (this.strategy.equals((K)k, curr) && v == this.value[pos]) {
				this.removeEntry(pos);
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (this.strategy.equals((K)k, curr) && v == this.value[pos]) {
						this.removeEntry(pos);
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean replace(K k, char oldValue, char v) {
		int pos = this.find(k);
		if (pos >= 0 && oldValue == this.value[pos]) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public char replace(K k, char v) {
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			char oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	@Override
	public char computeCharIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
			this.insert(-pos - 1, k, newValue);
			return newValue;
		}
	}

	@Override
	public char computeCharIfPresent(K k, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			Character newValue = (Character)remappingFunction.apply(k, this.value[pos]);
			if (newValue == null) {
				if (this.strategy.equals(k, null)) {
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
	public char computeChar(K k, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		Character newValue = (Character)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
		if (newValue == null) {
			if (pos >= 0) {
				if (this.strategy.equals(k, null)) {
					this.removeNullEntry();
				} else {
					this.removeEntry(pos);
				}
			}

			return this.defRetValue;
		} else {
			char newVal = newValue;
			if (pos < 0) {
				this.insert(-pos - 1, k, newVal);
				return newVal;
			} else {
				return this.value[pos] = newVal;
			}
		}
	}

	@Override
	public char mergeChar(K k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return v;
		} else {
			Character newValue = (Character)remappingFunction.apply(this.value[pos], v);
			if (newValue == null) {
				if (this.strategy.equals(k, null)) {
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
	public Object2CharSortedMap<K> tailMap(K from) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object2CharSortedMap<K> headMap(K to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object2CharSortedMap<K> subMap(K from, K to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Comparator<? super K> comparator() {
		return null;
	}

	public FastSortedEntrySet<K> object2CharEntrySet() {
		if (this.entries == null) {
			this.entries = new Object2CharLinkedOpenCustomHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public ObjectSortedSet<K> keySet() {
		if (this.keys == null) {
			this.keys = new Object2CharLinkedOpenCustomHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public CharCollection values() {
		if (this.values == null) {
			this.values = new AbstractCharCollection() {
				@Override
				public CharIterator iterator() {
					return Object2CharLinkedOpenCustomHashMap.this.new ValueIterator();
				}

				public int size() {
					return Object2CharLinkedOpenCustomHashMap.this.size;
				}

				@Override
				public boolean contains(char v) {
					return Object2CharLinkedOpenCustomHashMap.this.containsValue(v);
				}

				public void clear() {
					Object2CharLinkedOpenCustomHashMap.this.clear();
				}

				@Override
				public void forEach(IntConsumer consumer) {
					if (Object2CharLinkedOpenCustomHashMap.this.containsNullKey) {
						consumer.accept(Object2CharLinkedOpenCustomHashMap.this.value[Object2CharLinkedOpenCustomHashMap.this.n]);
					}

					int pos = Object2CharLinkedOpenCustomHashMap.this.n;

					while (pos-- != 0) {
						if (Object2CharLinkedOpenCustomHashMap.this.key[pos] != null) {
							consumer.accept(Object2CharLinkedOpenCustomHashMap.this.value[pos]);
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
		char[] value = this.value;
		int mask = newN - 1;
		K[] newKey = (K[])(new Object[newN + 1]);
		char[] newValue = new char[newN + 1];
		int i = this.first;
		int prev = -1;
		int newPrev = -1;
		long[] link = this.link;
		long[] newLink = new long[newN + 1];
		this.first = -1;
		int j = this.size;

		while (j-- != 0) {
			int pos;
			if (this.strategy.equals(key[i], null)) {
				pos = newN;
			} else {
				pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;

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

	public Object2CharLinkedOpenCustomHashMap<K> clone() {
		Object2CharLinkedOpenCustomHashMap<K> c;
		try {
			c = (Object2CharLinkedOpenCustomHashMap<K>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (K[])((Object[])this.key.clone());
		c.value = (char[])this.value.clone();
		c.link = (long[])this.link.clone();
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

			t ^= this.value[i];
			h += t;
		}

		if (this.containsNullKey) {
			h += this.value[this.n];
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		K[] key = this.key;
		char[] value = this.value;
		Object2CharLinkedOpenCustomHashMap<K>.MapIterator i = new Object2CharLinkedOpenCustomHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeObject(key[e]);
			s.writeChar(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		K[] key = this.key = (K[])(new Object[this.n + 1]);
		char[] value = this.value = new char[this.n + 1];
		long[] link = this.link = new long[this.n + 1];
		int prev = -1;
		this.first = this.last = -1;
		int i = this.size;

		while (i-- != 0) {
			K k = (K)s.readObject();
			char v = s.readChar();
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
		extends Object2CharLinkedOpenCustomHashMap<K>.MapIterator
		implements ObjectListIterator<it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K>> {
		private Object2CharLinkedOpenCustomHashMap<K>.MapEntry entry;

		public EntryIterator() {
		}

		public EntryIterator(K from) {
			super(from);
		}

		public Object2CharLinkedOpenCustomHashMap<K>.MapEntry next() {
			return this.entry = Object2CharLinkedOpenCustomHashMap.this.new MapEntry(this.nextEntry());
		}

		public Object2CharLinkedOpenCustomHashMap<K>.MapEntry previous() {
			return this.entry = Object2CharLinkedOpenCustomHashMap.this.new MapEntry(this.previousEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator
		extends Object2CharLinkedOpenCustomHashMap<K>.MapIterator
		implements ObjectListIterator<it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K>> {
		final Object2CharLinkedOpenCustomHashMap<K>.MapEntry entry;

		public FastEntryIterator() {
			this.entry = Object2CharLinkedOpenCustomHashMap.this.new MapEntry();
		}

		public FastEntryIterator(K from) {
			super(from);
			this.entry = Object2CharLinkedOpenCustomHashMap.this.new MapEntry();
		}

		public Object2CharLinkedOpenCustomHashMap<K>.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}

		public Object2CharLinkedOpenCustomHashMap<K>.MapEntry previous() {
			this.entry.index = this.previousEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Object2CharLinkedOpenCustomHashMap<K>.MapIterator implements ObjectListIterator<K> {
		public KeyIterator(K k) {
			super(k);
		}

		@Override
		public K previous() {
			return Object2CharLinkedOpenCustomHashMap.this.key[this.previousEntry()];
		}

		public KeyIterator() {
		}

		public K next() {
			return Object2CharLinkedOpenCustomHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractObjectSortedSet<K> {
		private KeySet() {
		}

		public ObjectListIterator<K> iterator(K from) {
			return Object2CharLinkedOpenCustomHashMap.this.new KeyIterator(from);
		}

		public ObjectListIterator<K> iterator() {
			return Object2CharLinkedOpenCustomHashMap.this.new KeyIterator();
		}

		public void forEach(Consumer<? super K> consumer) {
			if (Object2CharLinkedOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(Object2CharLinkedOpenCustomHashMap.this.key[Object2CharLinkedOpenCustomHashMap.this.n]);
			}

			int pos = Object2CharLinkedOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				K k = Object2CharLinkedOpenCustomHashMap.this.key[pos];
				if (k != null) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Object2CharLinkedOpenCustomHashMap.this.size;
		}

		public boolean contains(Object k) {
			return Object2CharLinkedOpenCustomHashMap.this.containsKey(k);
		}

		public boolean remove(Object k) {
			int oldSize = Object2CharLinkedOpenCustomHashMap.this.size;
			Object2CharLinkedOpenCustomHashMap.this.removeChar(k);
			return Object2CharLinkedOpenCustomHashMap.this.size != oldSize;
		}

		public void clear() {
			Object2CharLinkedOpenCustomHashMap.this.clear();
		}

		public K first() {
			if (Object2CharLinkedOpenCustomHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Object2CharLinkedOpenCustomHashMap.this.key[Object2CharLinkedOpenCustomHashMap.this.first];
			}
		}

		public K last() {
			if (Object2CharLinkedOpenCustomHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Object2CharLinkedOpenCustomHashMap.this.key[Object2CharLinkedOpenCustomHashMap.this.last];
			}
		}

		public Comparator<? super K> comparator() {
			return null;
		}

		@Override
		public ObjectSortedSet<K> tailSet(K from) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSortedSet<K> headSet(K to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSortedSet<K> subSet(K from, K to) {
			throw new UnsupportedOperationException();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K>, java.util.Map.Entry<K, Character> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		public K getKey() {
			return Object2CharLinkedOpenCustomHashMap.this.key[this.index];
		}

		@Override
		public char getCharValue() {
			return Object2CharLinkedOpenCustomHashMap.this.value[this.index];
		}

		@Override
		public char setValue(char v) {
			char oldValue = Object2CharLinkedOpenCustomHashMap.this.value[this.index];
			Object2CharLinkedOpenCustomHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Character getValue() {
			return Object2CharLinkedOpenCustomHashMap.this.value[this.index];
		}

		@Deprecated
		@Override
		public Character setValue(Character v) {
			return this.setValue(v.charValue());
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<K, Character> e = (java.util.Map.Entry<K, Character>)o;
				return Object2CharLinkedOpenCustomHashMap.this.strategy.equals(Object2CharLinkedOpenCustomHashMap.this.key[this.index], (K)e.getKey())
					&& Object2CharLinkedOpenCustomHashMap.this.value[this.index] == (Character)e.getValue();
			}
		}

		public int hashCode() {
			return Object2CharLinkedOpenCustomHashMap.this.strategy.hashCode(Object2CharLinkedOpenCustomHashMap.this.key[this.index])
				^ Object2CharLinkedOpenCustomHashMap.this.value[this.index];
		}

		public String toString() {
			return Object2CharLinkedOpenCustomHashMap.this.key[this.index] + "=>" + Object2CharLinkedOpenCustomHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K>> implements FastSortedEntrySet<K> {
		private MapEntrySet() {
		}

		@Override
		public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K>> iterator() {
			return Object2CharLinkedOpenCustomHashMap.this.new EntryIterator();
		}

		public Comparator<? super it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K>> comparator() {
			return null;
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K>> subSet(
			it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K> fromElement, it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K> toElement
		) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K>> headSet(it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K> toElement) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K>> tailSet(it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K> fromElement) {
			throw new UnsupportedOperationException();
		}

		public it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K> first() {
			if (Object2CharLinkedOpenCustomHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Object2CharLinkedOpenCustomHashMap.this.new MapEntry(Object2CharLinkedOpenCustomHashMap.this.first);
			}
		}

		public it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K> last() {
			if (Object2CharLinkedOpenCustomHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Object2CharLinkedOpenCustomHashMap.this.new MapEntry(Object2CharLinkedOpenCustomHashMap.this.last);
			}
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getValue() != null && e.getValue() instanceof Character) {
					K k = (K)e.getKey();
					char v = (Character)e.getValue();
					if (Object2CharLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
						return Object2CharLinkedOpenCustomHashMap.this.containsNullKey
							&& Object2CharLinkedOpenCustomHashMap.this.value[Object2CharLinkedOpenCustomHashMap.this.n] == v;
					} else {
						K[] key = Object2CharLinkedOpenCustomHashMap.this.key;
						K curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(Object2CharLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2CharLinkedOpenCustomHashMap.this.mask])
							== null) {
							return false;
						} else if (Object2CharLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) {
							return Object2CharLinkedOpenCustomHashMap.this.value[pos] == v;
						} else {
							while ((curr = key[pos = pos + 1 & Object2CharLinkedOpenCustomHashMap.this.mask]) != null) {
								if (Object2CharLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) {
									return Object2CharLinkedOpenCustomHashMap.this.value[pos] == v;
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
				if (e.getValue() != null && e.getValue() instanceof Character) {
					K k = (K)e.getKey();
					char v = (Character)e.getValue();
					if (Object2CharLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
						if (Object2CharLinkedOpenCustomHashMap.this.containsNullKey
							&& Object2CharLinkedOpenCustomHashMap.this.value[Object2CharLinkedOpenCustomHashMap.this.n] == v) {
							Object2CharLinkedOpenCustomHashMap.this.removeNullEntry();
							return true;
						} else {
							return false;
						}
					} else {
						K[] key = Object2CharLinkedOpenCustomHashMap.this.key;
						K curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(Object2CharLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2CharLinkedOpenCustomHashMap.this.mask])
							== null) {
							return false;
						} else if (Object2CharLinkedOpenCustomHashMap.this.strategy.equals(curr, k)) {
							if (Object2CharLinkedOpenCustomHashMap.this.value[pos] == v) {
								Object2CharLinkedOpenCustomHashMap.this.removeEntry(pos);
								return true;
							} else {
								return false;
							}
						} else {
							while ((curr = key[pos = pos + 1 & Object2CharLinkedOpenCustomHashMap.this.mask]) != null) {
								if (Object2CharLinkedOpenCustomHashMap.this.strategy.equals(curr, k) && Object2CharLinkedOpenCustomHashMap.this.value[pos] == v) {
									Object2CharLinkedOpenCustomHashMap.this.removeEntry(pos);
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
			return Object2CharLinkedOpenCustomHashMap.this.size;
		}

		public void clear() {
			Object2CharLinkedOpenCustomHashMap.this.clear();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K>> iterator(it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K> from) {
			return Object2CharLinkedOpenCustomHashMap.this.new EntryIterator(from.getKey());
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K>> fastIterator() {
			return Object2CharLinkedOpenCustomHashMap.this.new FastEntryIterator();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K>> fastIterator(it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K> from) {
			return Object2CharLinkedOpenCustomHashMap.this.new FastEntryIterator(from.getKey());
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K>> consumer) {
			int i = Object2CharLinkedOpenCustomHashMap.this.size;
			int next = Object2CharLinkedOpenCustomHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Object2CharLinkedOpenCustomHashMap.this.link[next];
				consumer.accept(new BasicEntry(Object2CharLinkedOpenCustomHashMap.this.key[curr], Object2CharLinkedOpenCustomHashMap.this.value[curr]));
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K>> consumer) {
			BasicEntry<K> entry = new BasicEntry<>();
			int i = Object2CharLinkedOpenCustomHashMap.this.size;
			int next = Object2CharLinkedOpenCustomHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Object2CharLinkedOpenCustomHashMap.this.link[next];
				entry.key = Object2CharLinkedOpenCustomHashMap.this.key[curr];
				entry.value = Object2CharLinkedOpenCustomHashMap.this.value[curr];
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
			this.next = Object2CharLinkedOpenCustomHashMap.this.first;
			this.index = 0;
		}

		private MapIterator(K from) {
			if (Object2CharLinkedOpenCustomHashMap.this.strategy.equals(from, null)) {
				if (Object2CharLinkedOpenCustomHashMap.this.containsNullKey) {
					this.next = (int)Object2CharLinkedOpenCustomHashMap.this.link[Object2CharLinkedOpenCustomHashMap.this.n];
					this.prev = Object2CharLinkedOpenCustomHashMap.this.n;
				} else {
					throw new NoSuchElementException("The key " + from + " does not belong to this map.");
				}
			} else if (Object2CharLinkedOpenCustomHashMap.this.strategy
				.equals(Object2CharLinkedOpenCustomHashMap.this.key[Object2CharLinkedOpenCustomHashMap.this.last], from)) {
				this.prev = Object2CharLinkedOpenCustomHashMap.this.last;
				this.index = Object2CharLinkedOpenCustomHashMap.this.size;
			} else {
				for (int pos = HashCommon.mix(Object2CharLinkedOpenCustomHashMap.this.strategy.hashCode(from)) & Object2CharLinkedOpenCustomHashMap.this.mask;
					Object2CharLinkedOpenCustomHashMap.this.key[pos] != null;
					pos = pos + 1 & Object2CharLinkedOpenCustomHashMap.this.mask
				) {
					if (Object2CharLinkedOpenCustomHashMap.this.strategy.equals(Object2CharLinkedOpenCustomHashMap.this.key[pos], from)) {
						this.next = (int)Object2CharLinkedOpenCustomHashMap.this.link[pos];
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
					this.index = Object2CharLinkedOpenCustomHashMap.this.size;
				} else {
					int pos = Object2CharLinkedOpenCustomHashMap.this.first;

					for (this.index = 1; pos != this.prev; this.index++) {
						pos = (int)Object2CharLinkedOpenCustomHashMap.this.link[pos];
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
				this.next = (int)Object2CharLinkedOpenCustomHashMap.this.link[this.curr];
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
				this.prev = (int)(Object2CharLinkedOpenCustomHashMap.this.link[this.curr] >>> 32);
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
					this.prev = (int)(Object2CharLinkedOpenCustomHashMap.this.link[this.curr] >>> 32);
				} else {
					this.next = (int)Object2CharLinkedOpenCustomHashMap.this.link[this.curr];
				}

				Object2CharLinkedOpenCustomHashMap.this.size--;
				if (this.prev == -1) {
					Object2CharLinkedOpenCustomHashMap.this.first = this.next;
				} else {
					Object2CharLinkedOpenCustomHashMap.this.link[this.prev] = Object2CharLinkedOpenCustomHashMap.this.link[this.prev]
						^ (Object2CharLinkedOpenCustomHashMap.this.link[this.prev] ^ (long)this.next & 4294967295L) & 4294967295L;
				}

				if (this.next == -1) {
					Object2CharLinkedOpenCustomHashMap.this.last = this.prev;
				} else {
					Object2CharLinkedOpenCustomHashMap.this.link[this.next] = Object2CharLinkedOpenCustomHashMap.this.link[this.next]
						^ (Object2CharLinkedOpenCustomHashMap.this.link[this.next] ^ ((long)this.prev & 4294967295L) << 32) & -4294967296L;
				}

				int pos = this.curr;
				this.curr = -1;
				if (pos == Object2CharLinkedOpenCustomHashMap.this.n) {
					Object2CharLinkedOpenCustomHashMap.this.containsNullKey = false;
					Object2CharLinkedOpenCustomHashMap.this.key[Object2CharLinkedOpenCustomHashMap.this.n] = null;
				} else {
					K[] key = Object2CharLinkedOpenCustomHashMap.this.key;

					label61:
					while (true) {
						int last = pos;

						K curr;
						for (pos = pos + 1 & Object2CharLinkedOpenCustomHashMap.this.mask;
							(curr = key[pos]) != null;
							pos = pos + 1 & Object2CharLinkedOpenCustomHashMap.this.mask
						) {
							int slot = HashCommon.mix(Object2CharLinkedOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2CharLinkedOpenCustomHashMap.this.mask;
							if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
								key[last] = curr;
								Object2CharLinkedOpenCustomHashMap.this.value[last] = Object2CharLinkedOpenCustomHashMap.this.value[pos];
								if (this.next == pos) {
									this.next = last;
								}

								if (this.prev == pos) {
									this.prev = last;
								}

								Object2CharLinkedOpenCustomHashMap.this.fixPointers(pos, last);
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

		public void set(it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K> ok) {
			throw new UnsupportedOperationException();
		}

		public void add(it.unimi.dsi.fastutil.objects.Object2CharMap.Entry<K> ok) {
			throw new UnsupportedOperationException();
		}
	}

	private final class ValueIterator extends Object2CharLinkedOpenCustomHashMap<K>.MapIterator implements CharListIterator {
		@Override
		public char previousChar() {
			return Object2CharLinkedOpenCustomHashMap.this.value[this.previousEntry()];
		}

		public ValueIterator() {
		}

		@Override
		public char nextChar() {
			return Object2CharLinkedOpenCustomHashMap.this.value[this.nextEntry()];
		}
	}
}
