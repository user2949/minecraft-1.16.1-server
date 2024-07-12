package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteListIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2ByteMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2ByteSortedMap.FastSortedEntrySet;
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

public class Object2ByteLinkedOpenHashMap<K> extends AbstractObject2ByteSortedMap<K> implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient K[] key;
	protected transient byte[] value;
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
	protected transient ObjectSortedSet<K> keys;
	protected transient ByteCollection values;

	public Object2ByteLinkedOpenHashMap(int expected, float f) {
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
			this.value = new byte[this.n + 1];
			this.link = new long[this.n + 1];
		}
	}

	public Object2ByteLinkedOpenHashMap(int expected) {
		this(expected, 0.75F);
	}

	public Object2ByteLinkedOpenHashMap() {
		this(16, 0.75F);
	}

	public Object2ByteLinkedOpenHashMap(Map<? extends K, ? extends Byte> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Object2ByteLinkedOpenHashMap(Map<? extends K, ? extends Byte> m) {
		this(m, 0.75F);
	}

	public Object2ByteLinkedOpenHashMap(Object2ByteMap<K> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Object2ByteLinkedOpenHashMap(Object2ByteMap<K> m) {
		this(m, 0.75F);
	}

	public Object2ByteLinkedOpenHashMap(K[] k, byte[] v, float f) {
		this(k.length, f);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Object2ByteLinkedOpenHashMap(K[] k, byte[] v) {
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

	private byte removeEntry(int pos) {
		byte oldValue = this.value[pos];
		this.size--;
		this.fixPointers(pos);
		this.shiftKeys(pos);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	private byte removeNullEntry() {
		this.containsNullKey = false;
		this.key[this.n] = null;
		byte oldValue = this.value[this.n];
		this.size--;
		this.fixPointers(this.n);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	@Override
	public void putAll(Map<? extends K, ? extends Byte> m) {
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
			if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
				return -(pos + 1);
			} else if (k.equals(curr)) {
				return pos;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (k.equals(curr)) {
						return pos;
					}
				}

				return -(pos + 1);
			}
		}
	}

	private void insert(int pos, K k, byte v) {
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
	public byte put(K k, byte v) {
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		} else {
			byte oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	private byte addToValue(int pos, byte incr) {
		byte oldValue = this.value[pos];
		this.value[pos] = (byte)(oldValue + incr);
		return oldValue;
	}

	public byte addTo(K k, byte incr) {
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
			if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
				if (curr.equals(k)) {
					return this.addToValue(pos, incr);
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (curr.equals(k)) {
						return this.addToValue(pos, incr);
					}
				}
			}
		}

		this.key[pos] = k;
		this.value[pos] = (byte)(this.defRetValue + incr);
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
				int slot = HashCommon.mix(curr.hashCode()) & this.mask;
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
	public byte removeByte(Object k) {
		if (k == null) {
			return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
				return this.defRetValue;
			} else if (k.equals(curr)) {
				return this.removeEntry(pos);
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (k.equals(curr)) {
						return this.removeEntry(pos);
					}
				}

				return this.defRetValue;
			}
		}
	}

	private byte setValue(int pos, byte v) {
		byte oldValue = this.value[pos];
		this.value[pos] = v;
		return oldValue;
	}

	public byte removeFirstByte() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			int pos = this.first;
			this.first = (int)this.link[pos];
			if (0 <= this.first) {
				this.link[this.first] = this.link[this.first] | -4294967296L;
			}

			this.size--;
			byte v = this.value[pos];
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

	public byte removeLastByte() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			int pos = this.last;
			this.last = (int)(this.link[pos] >>> 32);
			if (0 <= this.last) {
				this.link[this.last] = this.link[this.last] | 4294967295L;
			}

			this.size--;
			byte v = this.value[pos];
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

	public byte getAndMoveToFirst(K k) {
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
			if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
				return this.defRetValue;
			} else if (k.equals(curr)) {
				this.moveIndexToFirst(pos);
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (k.equals(curr)) {
						this.moveIndexToFirst(pos);
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	public byte getAndMoveToLast(K k) {
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
			if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
				return this.defRetValue;
			} else if (k.equals(curr)) {
				this.moveIndexToLast(pos);
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (k.equals(curr)) {
						this.moveIndexToLast(pos);
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	public byte putAndMoveToFirst(K k, byte v) {
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
			if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
				if (curr.equals(k)) {
					this.moveIndexToFirst(pos);
					return this.setValue(pos, v);
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (curr.equals(k)) {
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

	public byte putAndMoveToLast(K k, byte v) {
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
			if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
				if (curr.equals(k)) {
					this.moveIndexToLast(pos);
					return this.setValue(pos, v);
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (curr.equals(k)) {
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
	public byte getByte(Object k) {
		if (k == null) {
			return this.containsNullKey ? this.value[this.n] : this.defRetValue;
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
				return this.defRetValue;
			} else if (k.equals(curr)) {
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (k.equals(curr)) {
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
			if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
				return false;
			} else if (k.equals(curr)) {
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (k.equals(curr)) {
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean containsValue(byte v) {
		byte[] value = this.value;
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
	public byte getOrDefault(Object k, byte defaultValue) {
		if (k == null) {
			return this.containsNullKey ? this.value[this.n] : defaultValue;
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
				return defaultValue;
			} else if (k.equals(curr)) {
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (k.equals(curr)) {
						return this.value[pos];
					}
				}

				return defaultValue;
			}
		}
	}

	@Override
	public byte putIfAbsent(K k, byte v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(Object k, byte v) {
		if (k == null) {
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
			if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
				return false;
			} else if (k.equals(curr) && v == this.value[pos]) {
				this.removeEntry(pos);
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (k.equals(curr) && v == this.value[pos]) {
						this.removeEntry(pos);
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean replace(K k, byte oldValue, byte v) {
		int pos = this.find(k);
		if (pos >= 0 && oldValue == this.value[pos]) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public byte replace(K k, byte v) {
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			byte oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	@Override
	public byte computeByteIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			byte newValue = SafeMath.safeIntToByte(mappingFunction.applyAsInt(k));
			this.insert(-pos - 1, k, newValue);
			return newValue;
		}
	}

	@Override
	public byte computeByteIfPresent(K k, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			Byte newValue = (Byte)remappingFunction.apply(k, this.value[pos]);
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
	public byte computeByte(K k, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		Byte newValue = (Byte)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
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
			byte newVal = newValue;
			if (pos < 0) {
				this.insert(-pos - 1, k, newVal);
				return newVal;
			} else {
				return this.value[pos] = newVal;
			}
		}
	}

	@Override
	public byte mergeByte(K k, byte v, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return v;
		} else {
			Byte newValue = (Byte)remappingFunction.apply(this.value[pos], v);
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
	public Object2ByteSortedMap<K> tailMap(K from) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object2ByteSortedMap<K> headMap(K to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object2ByteSortedMap<K> subMap(K from, K to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Comparator<? super K> comparator() {
		return null;
	}

	public FastSortedEntrySet<K> object2ByteEntrySet() {
		if (this.entries == null) {
			this.entries = new Object2ByteLinkedOpenHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public ObjectSortedSet<K> keySet() {
		if (this.keys == null) {
			this.keys = new Object2ByteLinkedOpenHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public ByteCollection values() {
		if (this.values == null) {
			this.values = new AbstractByteCollection() {
				@Override
				public ByteIterator iterator() {
					return Object2ByteLinkedOpenHashMap.this.new ValueIterator();
				}

				public int size() {
					return Object2ByteLinkedOpenHashMap.this.size;
				}

				@Override
				public boolean contains(byte v) {
					return Object2ByteLinkedOpenHashMap.this.containsValue(v);
				}

				public void clear() {
					Object2ByteLinkedOpenHashMap.this.clear();
				}

				@Override
				public void forEach(IntConsumer consumer) {
					if (Object2ByteLinkedOpenHashMap.this.containsNullKey) {
						consumer.accept(Object2ByteLinkedOpenHashMap.this.value[Object2ByteLinkedOpenHashMap.this.n]);
					}

					int pos = Object2ByteLinkedOpenHashMap.this.n;

					while (pos-- != 0) {
						if (Object2ByteLinkedOpenHashMap.this.key[pos] != null) {
							consumer.accept(Object2ByteLinkedOpenHashMap.this.value[pos]);
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
		byte[] value = this.value;
		int mask = newN - 1;
		K[] newKey = (K[])(new Object[newN + 1]);
		byte[] newValue = new byte[newN + 1];
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
				pos = HashCommon.mix(key[i].hashCode()) & mask;

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

	public Object2ByteLinkedOpenHashMap<K> clone() {
		Object2ByteLinkedOpenHashMap<K> c;
		try {
			c = (Object2ByteLinkedOpenHashMap<K>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (K[])((Object[])this.key.clone());
		c.value = (byte[])this.value.clone();
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
				t = this.key[i].hashCode();
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
		byte[] value = this.value;
		Object2ByteLinkedOpenHashMap<K>.MapIterator i = new Object2ByteLinkedOpenHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeObject(key[e]);
			s.writeByte(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		K[] key = this.key = (K[])(new Object[this.n + 1]);
		byte[] value = this.value = new byte[this.n + 1];
		long[] link = this.link = new long[this.n + 1];
		int prev = -1;
		this.first = this.last = -1;
		int i = this.size;

		while (i-- != 0) {
			K k = (K)s.readObject();
			byte v = s.readByte();
			int pos;
			if (k == null) {
				pos = this.n;
				this.containsNullKey = true;
			} else {
				pos = HashCommon.mix(k.hashCode()) & this.mask;

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
		extends Object2ByteLinkedOpenHashMap<K>.MapIterator
		implements ObjectListIterator<it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K>> {
		private Object2ByteLinkedOpenHashMap<K>.MapEntry entry;

		public EntryIterator() {
		}

		public EntryIterator(K from) {
			super(from);
		}

		public Object2ByteLinkedOpenHashMap<K>.MapEntry next() {
			return this.entry = Object2ByteLinkedOpenHashMap.this.new MapEntry(this.nextEntry());
		}

		public Object2ByteLinkedOpenHashMap<K>.MapEntry previous() {
			return this.entry = Object2ByteLinkedOpenHashMap.this.new MapEntry(this.previousEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator
		extends Object2ByteLinkedOpenHashMap<K>.MapIterator
		implements ObjectListIterator<it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K>> {
		final Object2ByteLinkedOpenHashMap<K>.MapEntry entry;

		public FastEntryIterator() {
			this.entry = Object2ByteLinkedOpenHashMap.this.new MapEntry();
		}

		public FastEntryIterator(K from) {
			super(from);
			this.entry = Object2ByteLinkedOpenHashMap.this.new MapEntry();
		}

		public Object2ByteLinkedOpenHashMap<K>.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}

		public Object2ByteLinkedOpenHashMap<K>.MapEntry previous() {
			this.entry.index = this.previousEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Object2ByteLinkedOpenHashMap<K>.MapIterator implements ObjectListIterator<K> {
		public KeyIterator(K k) {
			super(k);
		}

		@Override
		public K previous() {
			return Object2ByteLinkedOpenHashMap.this.key[this.previousEntry()];
		}

		public KeyIterator() {
		}

		public K next() {
			return Object2ByteLinkedOpenHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractObjectSortedSet<K> {
		private KeySet() {
		}

		public ObjectListIterator<K> iterator(K from) {
			return Object2ByteLinkedOpenHashMap.this.new KeyIterator(from);
		}

		public ObjectListIterator<K> iterator() {
			return Object2ByteLinkedOpenHashMap.this.new KeyIterator();
		}

		public void forEach(Consumer<? super K> consumer) {
			if (Object2ByteLinkedOpenHashMap.this.containsNullKey) {
				consumer.accept(Object2ByteLinkedOpenHashMap.this.key[Object2ByteLinkedOpenHashMap.this.n]);
			}

			int pos = Object2ByteLinkedOpenHashMap.this.n;

			while (pos-- != 0) {
				K k = Object2ByteLinkedOpenHashMap.this.key[pos];
				if (k != null) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Object2ByteLinkedOpenHashMap.this.size;
		}

		public boolean contains(Object k) {
			return Object2ByteLinkedOpenHashMap.this.containsKey(k);
		}

		public boolean remove(Object k) {
			int oldSize = Object2ByteLinkedOpenHashMap.this.size;
			Object2ByteLinkedOpenHashMap.this.removeByte(k);
			return Object2ByteLinkedOpenHashMap.this.size != oldSize;
		}

		public void clear() {
			Object2ByteLinkedOpenHashMap.this.clear();
		}

		public K first() {
			if (Object2ByteLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Object2ByteLinkedOpenHashMap.this.key[Object2ByteLinkedOpenHashMap.this.first];
			}
		}

		public K last() {
			if (Object2ByteLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Object2ByteLinkedOpenHashMap.this.key[Object2ByteLinkedOpenHashMap.this.last];
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

	final class MapEntry implements it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K>, java.util.Map.Entry<K, Byte> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		public K getKey() {
			return Object2ByteLinkedOpenHashMap.this.key[this.index];
		}

		@Override
		public byte getByteValue() {
			return Object2ByteLinkedOpenHashMap.this.value[this.index];
		}

		@Override
		public byte setValue(byte v) {
			byte oldValue = Object2ByteLinkedOpenHashMap.this.value[this.index];
			Object2ByteLinkedOpenHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Byte getValue() {
			return Object2ByteLinkedOpenHashMap.this.value[this.index];
		}

		@Deprecated
		@Override
		public Byte setValue(Byte v) {
			return this.setValue(v.byteValue());
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<K, Byte> e = (java.util.Map.Entry<K, Byte>)o;
				return Objects.equals(Object2ByteLinkedOpenHashMap.this.key[this.index], e.getKey())
					&& Object2ByteLinkedOpenHashMap.this.value[this.index] == (Byte)e.getValue();
			}
		}

		public int hashCode() {
			return (Object2ByteLinkedOpenHashMap.this.key[this.index] == null ? 0 : Object2ByteLinkedOpenHashMap.this.key[this.index].hashCode())
				^ Object2ByteLinkedOpenHashMap.this.value[this.index];
		}

		public String toString() {
			return Object2ByteLinkedOpenHashMap.this.key[this.index] + "=>" + Object2ByteLinkedOpenHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K>> implements FastSortedEntrySet<K> {
		private MapEntrySet() {
		}

		@Override
		public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K>> iterator() {
			return Object2ByteLinkedOpenHashMap.this.new EntryIterator();
		}

		public Comparator<? super it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K>> comparator() {
			return null;
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K>> subSet(
			it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K> fromElement, it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K> toElement
		) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K>> headSet(it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K> toElement) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K>> tailSet(it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K> fromElement) {
			throw new UnsupportedOperationException();
		}

		public it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K> first() {
			if (Object2ByteLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Object2ByteLinkedOpenHashMap.this.new MapEntry(Object2ByteLinkedOpenHashMap.this.first);
			}
		}

		public it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K> last() {
			if (Object2ByteLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Object2ByteLinkedOpenHashMap.this.new MapEntry(Object2ByteLinkedOpenHashMap.this.last);
			}
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getValue() != null && e.getValue() instanceof Byte) {
					K k = (K)e.getKey();
					byte v = (Byte)e.getValue();
					if (k == null) {
						return Object2ByteLinkedOpenHashMap.this.containsNullKey && Object2ByteLinkedOpenHashMap.this.value[Object2ByteLinkedOpenHashMap.this.n] == v;
					} else {
						K[] key = Object2ByteLinkedOpenHashMap.this.key;
						K curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ByteLinkedOpenHashMap.this.mask]) == null) {
							return false;
						} else if (k.equals(curr)) {
							return Object2ByteLinkedOpenHashMap.this.value[pos] == v;
						} else {
							while ((curr = key[pos = pos + 1 & Object2ByteLinkedOpenHashMap.this.mask]) != null) {
								if (k.equals(curr)) {
									return Object2ByteLinkedOpenHashMap.this.value[pos] == v;
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
				if (e.getValue() != null && e.getValue() instanceof Byte) {
					K k = (K)e.getKey();
					byte v = (Byte)e.getValue();
					if (k == null) {
						if (Object2ByteLinkedOpenHashMap.this.containsNullKey && Object2ByteLinkedOpenHashMap.this.value[Object2ByteLinkedOpenHashMap.this.n] == v) {
							Object2ByteLinkedOpenHashMap.this.removeNullEntry();
							return true;
						} else {
							return false;
						}
					} else {
						K[] key = Object2ByteLinkedOpenHashMap.this.key;
						K curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ByteLinkedOpenHashMap.this.mask]) == null) {
							return false;
						} else if (curr.equals(k)) {
							if (Object2ByteLinkedOpenHashMap.this.value[pos] == v) {
								Object2ByteLinkedOpenHashMap.this.removeEntry(pos);
								return true;
							} else {
								return false;
							}
						} else {
							while ((curr = key[pos = pos + 1 & Object2ByteLinkedOpenHashMap.this.mask]) != null) {
								if (curr.equals(k) && Object2ByteLinkedOpenHashMap.this.value[pos] == v) {
									Object2ByteLinkedOpenHashMap.this.removeEntry(pos);
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
			return Object2ByteLinkedOpenHashMap.this.size;
		}

		public void clear() {
			Object2ByteLinkedOpenHashMap.this.clear();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K>> iterator(it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K> from) {
			return Object2ByteLinkedOpenHashMap.this.new EntryIterator(from.getKey());
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K>> fastIterator() {
			return Object2ByteLinkedOpenHashMap.this.new FastEntryIterator();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K>> fastIterator(it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K> from) {
			return Object2ByteLinkedOpenHashMap.this.new FastEntryIterator(from.getKey());
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K>> consumer) {
			int i = Object2ByteLinkedOpenHashMap.this.size;
			int next = Object2ByteLinkedOpenHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Object2ByteLinkedOpenHashMap.this.link[next];
				consumer.accept(new BasicEntry(Object2ByteLinkedOpenHashMap.this.key[curr], Object2ByteLinkedOpenHashMap.this.value[curr]));
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K>> consumer) {
			BasicEntry<K> entry = new BasicEntry<>();
			int i = Object2ByteLinkedOpenHashMap.this.size;
			int next = Object2ByteLinkedOpenHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Object2ByteLinkedOpenHashMap.this.link[next];
				entry.key = Object2ByteLinkedOpenHashMap.this.key[curr];
				entry.value = Object2ByteLinkedOpenHashMap.this.value[curr];
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
			this.next = Object2ByteLinkedOpenHashMap.this.first;
			this.index = 0;
		}

		private MapIterator(K from) {
			if (from == null) {
				if (Object2ByteLinkedOpenHashMap.this.containsNullKey) {
					this.next = (int)Object2ByteLinkedOpenHashMap.this.link[Object2ByteLinkedOpenHashMap.this.n];
					this.prev = Object2ByteLinkedOpenHashMap.this.n;
				} else {
					throw new NoSuchElementException("The key " + from + " does not belong to this map.");
				}
			} else if (Objects.equals(Object2ByteLinkedOpenHashMap.this.key[Object2ByteLinkedOpenHashMap.this.last], from)) {
				this.prev = Object2ByteLinkedOpenHashMap.this.last;
				this.index = Object2ByteLinkedOpenHashMap.this.size;
			} else {
				for (int pos = HashCommon.mix(from.hashCode()) & Object2ByteLinkedOpenHashMap.this.mask;
					Object2ByteLinkedOpenHashMap.this.key[pos] != null;
					pos = pos + 1 & Object2ByteLinkedOpenHashMap.this.mask
				) {
					if (Object2ByteLinkedOpenHashMap.this.key[pos].equals(from)) {
						this.next = (int)Object2ByteLinkedOpenHashMap.this.link[pos];
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
					this.index = Object2ByteLinkedOpenHashMap.this.size;
				} else {
					int pos = Object2ByteLinkedOpenHashMap.this.first;

					for (this.index = 1; pos != this.prev; this.index++) {
						pos = (int)Object2ByteLinkedOpenHashMap.this.link[pos];
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
				this.next = (int)Object2ByteLinkedOpenHashMap.this.link[this.curr];
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
				this.prev = (int)(Object2ByteLinkedOpenHashMap.this.link[this.curr] >>> 32);
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
					this.prev = (int)(Object2ByteLinkedOpenHashMap.this.link[this.curr] >>> 32);
				} else {
					this.next = (int)Object2ByteLinkedOpenHashMap.this.link[this.curr];
				}

				Object2ByteLinkedOpenHashMap.this.size--;
				if (this.prev == -1) {
					Object2ByteLinkedOpenHashMap.this.first = this.next;
				} else {
					Object2ByteLinkedOpenHashMap.this.link[this.prev] = Object2ByteLinkedOpenHashMap.this.link[this.prev]
						^ (Object2ByteLinkedOpenHashMap.this.link[this.prev] ^ (long)this.next & 4294967295L) & 4294967295L;
				}

				if (this.next == -1) {
					Object2ByteLinkedOpenHashMap.this.last = this.prev;
				} else {
					Object2ByteLinkedOpenHashMap.this.link[this.next] = Object2ByteLinkedOpenHashMap.this.link[this.next]
						^ (Object2ByteLinkedOpenHashMap.this.link[this.next] ^ ((long)this.prev & 4294967295L) << 32) & -4294967296L;
				}

				int pos = this.curr;
				this.curr = -1;
				if (pos == Object2ByteLinkedOpenHashMap.this.n) {
					Object2ByteLinkedOpenHashMap.this.containsNullKey = false;
					Object2ByteLinkedOpenHashMap.this.key[Object2ByteLinkedOpenHashMap.this.n] = null;
				} else {
					K[] key = Object2ByteLinkedOpenHashMap.this.key;

					label61:
					while (true) {
						int last = pos;

						K curr;
						for (pos = pos + 1 & Object2ByteLinkedOpenHashMap.this.mask; (curr = key[pos]) != null; pos = pos + 1 & Object2ByteLinkedOpenHashMap.this.mask) {
							int slot = HashCommon.mix(curr.hashCode()) & Object2ByteLinkedOpenHashMap.this.mask;
							if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
								key[last] = curr;
								Object2ByteLinkedOpenHashMap.this.value[last] = Object2ByteLinkedOpenHashMap.this.value[pos];
								if (this.next == pos) {
									this.next = last;
								}

								if (this.prev == pos) {
									this.prev = last;
								}

								Object2ByteLinkedOpenHashMap.this.fixPointers(pos, last);
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

		public void set(it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K> ok) {
			throw new UnsupportedOperationException();
		}

		public void add(it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry<K> ok) {
			throw new UnsupportedOperationException();
		}
	}

	private final class ValueIterator extends Object2ByteLinkedOpenHashMap<K>.MapIterator implements ByteListIterator {
		@Override
		public byte previousByte() {
			return Object2ByteLinkedOpenHashMap.this.value[this.previousEntry()];
		}

		public ValueIterator() {
		}

		@Override
		public byte nextByte() {
			return Object2ByteLinkedOpenHashMap.this.value[this.nextEntry()];
		}
	}
}
