package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteListIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloat2ByteMap.BasicEntry;
import it.unimi.dsi.fastutil.floats.Float2ByteSortedMap.FastSortedEntrySet;
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
import java.util.function.DoubleToIntFunction;
import java.util.function.IntConsumer;

public class Float2ByteLinkedOpenHashMap extends AbstractFloat2ByteSortedMap implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient float[] key;
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
	protected transient FastSortedEntrySet entries;
	protected transient FloatSortedSet keys;
	protected transient ByteCollection values;

	public Float2ByteLinkedOpenHashMap(int expected, float f) {
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
			this.value = new byte[this.n + 1];
			this.link = new long[this.n + 1];
		}
	}

	public Float2ByteLinkedOpenHashMap(int expected) {
		this(expected, 0.75F);
	}

	public Float2ByteLinkedOpenHashMap() {
		this(16, 0.75F);
	}

	public Float2ByteLinkedOpenHashMap(Map<? extends Float, ? extends Byte> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Float2ByteLinkedOpenHashMap(Map<? extends Float, ? extends Byte> m) {
		this(m, 0.75F);
	}

	public Float2ByteLinkedOpenHashMap(Float2ByteMap m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Float2ByteLinkedOpenHashMap(Float2ByteMap m) {
		this(m, 0.75F);
	}

	public Float2ByteLinkedOpenHashMap(float[] k, byte[] v, float f) {
		this(k.length, f);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Float2ByteLinkedOpenHashMap(float[] k, byte[] v) {
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
		byte oldValue = this.value[this.n];
		this.size--;
		this.fixPointers(this.n);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	@Override
	public void putAll(Map<? extends Float, ? extends Byte> m) {
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

	private void insert(int pos, float k, byte v) {
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
	public byte put(float k, byte v) {
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

	public byte addTo(float k, byte incr) {
		int pos;
		if (Float.floatToIntBits(k) == 0) {
			if (this.containsNullKey) {
				return this.addToValue(this.n, incr);
			}

			pos = this.n;
			this.containsNullKey = true;
		} else {
			float[] key = this.key;
			float curr;
			if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
				if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
					return this.addToValue(pos, incr);
				}

				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
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
	public byte remove(float k) {
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

	public byte getAndMoveToFirst(float k) {
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

	public byte getAndMoveToLast(float k) {
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

	public byte putAndMoveToFirst(float k, byte v) {
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

	public byte putAndMoveToLast(float k, byte v) {
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
	public byte get(float k) {
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
	public boolean containsValue(byte v) {
		byte[] value = this.value;
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
	public byte getOrDefault(float k, byte defaultValue) {
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
	public byte putIfAbsent(float k, byte v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(float k, byte v) {
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
	public boolean replace(float k, byte oldValue, byte v) {
		int pos = this.find(k);
		if (pos >= 0 && oldValue == this.value[pos]) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public byte replace(float k, byte v) {
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
	public byte computeIfAbsent(float k, DoubleToIntFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			byte newValue = SafeMath.safeIntToByte(mappingFunction.applyAsInt((double)k));
			this.insert(-pos - 1, k, newValue);
			return newValue;
		}
	}

	@Override
	public byte computeIfAbsentNullable(float k, DoubleFunction<? extends Byte> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			Byte newValue = (Byte)mappingFunction.apply((double)k);
			if (newValue == null) {
				return this.defRetValue;
			} else {
				byte v = newValue;
				this.insert(-pos - 1, k, v);
				return v;
			}
		}
	}

	@Override
	public byte computeIfPresent(float k, BiFunction<? super Float, ? super Byte, ? extends Byte> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			Byte newValue = (Byte)remappingFunction.apply(k, this.value[pos]);
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
	public byte compute(float k, BiFunction<? super Float, ? super Byte, ? extends Byte> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		Byte newValue = (Byte)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
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
	public byte merge(float k, byte v, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return v;
		} else {
			Byte newValue = (Byte)remappingFunction.apply(this.value[pos], v);
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
	public Float2ByteSortedMap tailMap(float from) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Float2ByteSortedMap headMap(float to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Float2ByteSortedMap subMap(float from, float to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public FloatComparator comparator() {
		return null;
	}

	public FastSortedEntrySet float2ByteEntrySet() {
		if (this.entries == null) {
			this.entries = new Float2ByteLinkedOpenHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public FloatSortedSet keySet() {
		if (this.keys == null) {
			this.keys = new Float2ByteLinkedOpenHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public ByteCollection values() {
		if (this.values == null) {
			this.values = new AbstractByteCollection() {
				@Override
				public ByteIterator iterator() {
					return Float2ByteLinkedOpenHashMap.this.new ValueIterator();
				}

				public int size() {
					return Float2ByteLinkedOpenHashMap.this.size;
				}

				@Override
				public boolean contains(byte v) {
					return Float2ByteLinkedOpenHashMap.this.containsValue(v);
				}

				public void clear() {
					Float2ByteLinkedOpenHashMap.this.clear();
				}

				@Override
				public void forEach(IntConsumer consumer) {
					if (Float2ByteLinkedOpenHashMap.this.containsNullKey) {
						consumer.accept(Float2ByteLinkedOpenHashMap.this.value[Float2ByteLinkedOpenHashMap.this.n]);
					}

					int pos = Float2ByteLinkedOpenHashMap.this.n;

					while (pos-- != 0) {
						if (Float.floatToIntBits(Float2ByteLinkedOpenHashMap.this.key[pos]) != 0) {
							consumer.accept(Float2ByteLinkedOpenHashMap.this.value[pos]);
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
		byte[] value = this.value;
		int mask = newN - 1;
		float[] newKey = new float[newN + 1];
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

	public Float2ByteLinkedOpenHashMap clone() {
		Float2ByteLinkedOpenHashMap c;
		try {
			c = (Float2ByteLinkedOpenHashMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (float[])this.key.clone();
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
			while (Float.floatToIntBits(this.key[i]) == 0) {
				i++;
			}

			t = HashCommon.float2int(this.key[i]);
			t ^= this.value[i];
			h += t;
		}

		if (this.containsNullKey) {
			h += this.value[this.n];
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		float[] key = this.key;
		byte[] value = this.value;
		Float2ByteLinkedOpenHashMap.MapIterator i = new Float2ByteLinkedOpenHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeFloat(key[e]);
			s.writeByte(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		float[] key = this.key = new float[this.n + 1];
		byte[] value = this.value = new byte[this.n + 1];
		long[] link = this.link = new long[this.n + 1];
		int prev = -1;
		this.first = this.last = -1;
		int i = this.size;

		while (i-- != 0) {
			float k = s.readFloat();
			byte v = s.readByte();
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

	private class EntryIterator extends Float2ByteLinkedOpenHashMap.MapIterator implements ObjectListIterator<it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry> {
		private Float2ByteLinkedOpenHashMap.MapEntry entry;

		public EntryIterator() {
		}

		public EntryIterator(float from) {
			super(from);
		}

		public Float2ByteLinkedOpenHashMap.MapEntry next() {
			return this.entry = Float2ByteLinkedOpenHashMap.this.new MapEntry(this.nextEntry());
		}

		public Float2ByteLinkedOpenHashMap.MapEntry previous() {
			return this.entry = Float2ByteLinkedOpenHashMap.this.new MapEntry(this.previousEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator
		extends Float2ByteLinkedOpenHashMap.MapIterator
		implements ObjectListIterator<it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry> {
		final Float2ByteLinkedOpenHashMap.MapEntry entry;

		public FastEntryIterator() {
			this.entry = Float2ByteLinkedOpenHashMap.this.new MapEntry();
		}

		public FastEntryIterator(float from) {
			super(from);
			this.entry = Float2ByteLinkedOpenHashMap.this.new MapEntry();
		}

		public Float2ByteLinkedOpenHashMap.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}

		public Float2ByteLinkedOpenHashMap.MapEntry previous() {
			this.entry.index = this.previousEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Float2ByteLinkedOpenHashMap.MapIterator implements FloatListIterator {
		public KeyIterator(float k) {
			super(k);
		}

		@Override
		public float previousFloat() {
			return Float2ByteLinkedOpenHashMap.this.key[this.previousEntry()];
		}

		public KeyIterator() {
		}

		@Override
		public float nextFloat() {
			return Float2ByteLinkedOpenHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractFloatSortedSet {
		private KeySet() {
		}

		public FloatListIterator iterator(float from) {
			return Float2ByteLinkedOpenHashMap.this.new KeyIterator(from);
		}

		public FloatListIterator iterator() {
			return Float2ByteLinkedOpenHashMap.this.new KeyIterator();
		}

		@Override
		public void forEach(DoubleConsumer consumer) {
			if (Float2ByteLinkedOpenHashMap.this.containsNullKey) {
				consumer.accept((double)Float2ByteLinkedOpenHashMap.this.key[Float2ByteLinkedOpenHashMap.this.n]);
			}

			int pos = Float2ByteLinkedOpenHashMap.this.n;

			while (pos-- != 0) {
				float k = Float2ByteLinkedOpenHashMap.this.key[pos];
				if (Float.floatToIntBits(k) != 0) {
					consumer.accept((double)k);
				}
			}
		}

		public int size() {
			return Float2ByteLinkedOpenHashMap.this.size;
		}

		@Override
		public boolean contains(float k) {
			return Float2ByteLinkedOpenHashMap.this.containsKey(k);
		}

		@Override
		public boolean remove(float k) {
			int oldSize = Float2ByteLinkedOpenHashMap.this.size;
			Float2ByteLinkedOpenHashMap.this.remove(k);
			return Float2ByteLinkedOpenHashMap.this.size != oldSize;
		}

		public void clear() {
			Float2ByteLinkedOpenHashMap.this.clear();
		}

		@Override
		public float firstFloat() {
			if (Float2ByteLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Float2ByteLinkedOpenHashMap.this.key[Float2ByteLinkedOpenHashMap.this.first];
			}
		}

		@Override
		public float lastFloat() {
			if (Float2ByteLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Float2ByteLinkedOpenHashMap.this.key[Float2ByteLinkedOpenHashMap.this.last];
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

	final class MapEntry implements it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry, java.util.Map.Entry<Float, Byte> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		@Override
		public float getFloatKey() {
			return Float2ByteLinkedOpenHashMap.this.key[this.index];
		}

		@Override
		public byte getByteValue() {
			return Float2ByteLinkedOpenHashMap.this.value[this.index];
		}

		@Override
		public byte setValue(byte v) {
			byte oldValue = Float2ByteLinkedOpenHashMap.this.value[this.index];
			Float2ByteLinkedOpenHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Float getKey() {
			return Float2ByteLinkedOpenHashMap.this.key[this.index];
		}

		@Deprecated
		@Override
		public Byte getValue() {
			return Float2ByteLinkedOpenHashMap.this.value[this.index];
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
				java.util.Map.Entry<Float, Byte> e = (java.util.Map.Entry<Float, Byte>)o;
				return Float.floatToIntBits(Float2ByteLinkedOpenHashMap.this.key[this.index]) == Float.floatToIntBits((Float)e.getKey())
					&& Float2ByteLinkedOpenHashMap.this.value[this.index] == (Byte)e.getValue();
			}
		}

		public int hashCode() {
			return HashCommon.float2int(Float2ByteLinkedOpenHashMap.this.key[this.index]) ^ Float2ByteLinkedOpenHashMap.this.value[this.index];
		}

		public String toString() {
			return Float2ByteLinkedOpenHashMap.this.key[this.index] + "=>" + Float2ByteLinkedOpenHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSortedSet<it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry> implements FastSortedEntrySet {
		private MapEntrySet() {
		}

		@Override
		public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry> iterator() {
			return Float2ByteLinkedOpenHashMap.this.new EntryIterator();
		}

		public Comparator<? super it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry> comparator() {
			return null;
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry> subSet(
			it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry fromElement, it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry toElement
		) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry> headSet(it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry toElement) {
			throw new UnsupportedOperationException();
		}

		public ObjectSortedSet<it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry> tailSet(it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry fromElement) {
			throw new UnsupportedOperationException();
		}

		public it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry first() {
			if (Float2ByteLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Float2ByteLinkedOpenHashMap.this.new MapEntry(Float2ByteLinkedOpenHashMap.this.first);
			}
		}

		public it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry last() {
			if (Float2ByteLinkedOpenHashMap.this.size == 0) {
				throw new NoSuchElementException();
			} else {
				return Float2ByteLinkedOpenHashMap.this.new MapEntry(Float2ByteLinkedOpenHashMap.this.last);
			}
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Float)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Byte) {
					float k = (Float)e.getKey();
					byte v = (Byte)e.getValue();
					if (Float.floatToIntBits(k) == 0) {
						return Float2ByteLinkedOpenHashMap.this.containsNullKey && Float2ByteLinkedOpenHashMap.this.value[Float2ByteLinkedOpenHashMap.this.n] == v;
					} else {
						float[] key = Float2ByteLinkedOpenHashMap.this.key;
						float curr;
						int pos;
						if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2ByteLinkedOpenHashMap.this.mask]) == 0) {
							return false;
						} else if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
							return Float2ByteLinkedOpenHashMap.this.value[pos] == v;
						} else {
							while (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ByteLinkedOpenHashMap.this.mask]) != 0) {
								if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
									return Float2ByteLinkedOpenHashMap.this.value[pos] == v;
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
					if (e.getValue() != null && e.getValue() instanceof Byte) {
						float k = (Float)e.getKey();
						byte v = (Byte)e.getValue();
						if (Float.floatToIntBits(k) == 0) {
							if (Float2ByteLinkedOpenHashMap.this.containsNullKey && Float2ByteLinkedOpenHashMap.this.value[Float2ByteLinkedOpenHashMap.this.n] == v) {
								Float2ByteLinkedOpenHashMap.this.removeNullEntry();
								return true;
							} else {
								return false;
							}
						} else {
							float[] key = Float2ByteLinkedOpenHashMap.this.key;
							float curr;
							int pos;
							if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2ByteLinkedOpenHashMap.this.mask]) == 0) {
								return false;
							} else if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
								if (Float2ByteLinkedOpenHashMap.this.value[pos] == v) {
									Float2ByteLinkedOpenHashMap.this.removeEntry(pos);
									return true;
								} else {
									return false;
								}
							} else {
								while (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ByteLinkedOpenHashMap.this.mask]) != 0) {
									if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && Float2ByteLinkedOpenHashMap.this.value[pos] == v) {
										Float2ByteLinkedOpenHashMap.this.removeEntry(pos);
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
			return Float2ByteLinkedOpenHashMap.this.size;
		}

		public void clear() {
			Float2ByteLinkedOpenHashMap.this.clear();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry> iterator(it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry from) {
			return Float2ByteLinkedOpenHashMap.this.new EntryIterator(from.getFloatKey());
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry> fastIterator() {
			return Float2ByteLinkedOpenHashMap.this.new FastEntryIterator();
		}

		public ObjectListIterator<it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry> fastIterator(it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry from) {
			return Float2ByteLinkedOpenHashMap.this.new FastEntryIterator(from.getFloatKey());
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry> consumer) {
			int i = Float2ByteLinkedOpenHashMap.this.size;
			int next = Float2ByteLinkedOpenHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Float2ByteLinkedOpenHashMap.this.link[next];
				consumer.accept(new BasicEntry(Float2ByteLinkedOpenHashMap.this.key[curr], Float2ByteLinkedOpenHashMap.this.value[curr]));
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry> consumer) {
			BasicEntry entry = new BasicEntry();
			int i = Float2ByteLinkedOpenHashMap.this.size;
			int next = Float2ByteLinkedOpenHashMap.this.first;

			while (i-- != 0) {
				int curr = next;
				next = (int)Float2ByteLinkedOpenHashMap.this.link[next];
				entry.key = Float2ByteLinkedOpenHashMap.this.key[curr];
				entry.value = Float2ByteLinkedOpenHashMap.this.value[curr];
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
			this.next = Float2ByteLinkedOpenHashMap.this.first;
			this.index = 0;
		}

		private MapIterator(float from) {
			if (Float.floatToIntBits(from) == 0) {
				if (Float2ByteLinkedOpenHashMap.this.containsNullKey) {
					this.next = (int)Float2ByteLinkedOpenHashMap.this.link[Float2ByteLinkedOpenHashMap.this.n];
					this.prev = Float2ByteLinkedOpenHashMap.this.n;
				} else {
					throw new NoSuchElementException("The key " + from + " does not belong to this map.");
				}
			} else if (Float.floatToIntBits(Float2ByteLinkedOpenHashMap.this.key[Float2ByteLinkedOpenHashMap.this.last]) == Float.floatToIntBits(from)) {
				this.prev = Float2ByteLinkedOpenHashMap.this.last;
				this.index = Float2ByteLinkedOpenHashMap.this.size;
			} else {
				for (int pos = HashCommon.mix(HashCommon.float2int(from)) & Float2ByteLinkedOpenHashMap.this.mask;
					Float.floatToIntBits(Float2ByteLinkedOpenHashMap.this.key[pos]) != 0;
					pos = pos + 1 & Float2ByteLinkedOpenHashMap.this.mask
				) {
					if (Float.floatToIntBits(Float2ByteLinkedOpenHashMap.this.key[pos]) == Float.floatToIntBits(from)) {
						this.next = (int)Float2ByteLinkedOpenHashMap.this.link[pos];
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
					this.index = Float2ByteLinkedOpenHashMap.this.size;
				} else {
					int pos = Float2ByteLinkedOpenHashMap.this.first;

					for (this.index = 1; pos != this.prev; this.index++) {
						pos = (int)Float2ByteLinkedOpenHashMap.this.link[pos];
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
				this.next = (int)Float2ByteLinkedOpenHashMap.this.link[this.curr];
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
				this.prev = (int)(Float2ByteLinkedOpenHashMap.this.link[this.curr] >>> 32);
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
					this.prev = (int)(Float2ByteLinkedOpenHashMap.this.link[this.curr] >>> 32);
				} else {
					this.next = (int)Float2ByteLinkedOpenHashMap.this.link[this.curr];
				}

				Float2ByteLinkedOpenHashMap.this.size--;
				if (this.prev == -1) {
					Float2ByteLinkedOpenHashMap.this.first = this.next;
				} else {
					Float2ByteLinkedOpenHashMap.this.link[this.prev] = Float2ByteLinkedOpenHashMap.this.link[this.prev]
						^ (Float2ByteLinkedOpenHashMap.this.link[this.prev] ^ (long)this.next & 4294967295L) & 4294967295L;
				}

				if (this.next == -1) {
					Float2ByteLinkedOpenHashMap.this.last = this.prev;
				} else {
					Float2ByteLinkedOpenHashMap.this.link[this.next] = Float2ByteLinkedOpenHashMap.this.link[this.next]
						^ (Float2ByteLinkedOpenHashMap.this.link[this.next] ^ ((long)this.prev & 4294967295L) << 32) & -4294967296L;
				}

				int pos = this.curr;
				this.curr = -1;
				if (pos == Float2ByteLinkedOpenHashMap.this.n) {
					Float2ByteLinkedOpenHashMap.this.containsNullKey = false;
				} else {
					float[] key = Float2ByteLinkedOpenHashMap.this.key;

					label61:
					while (true) {
						int last = pos;

						float curr;
						for (pos = pos + 1 & Float2ByteLinkedOpenHashMap.this.mask;
							Float.floatToIntBits(curr = key[pos]) != 0;
							pos = pos + 1 & Float2ByteLinkedOpenHashMap.this.mask
						) {
							int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2ByteLinkedOpenHashMap.this.mask;
							if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
								key[last] = curr;
								Float2ByteLinkedOpenHashMap.this.value[last] = Float2ByteLinkedOpenHashMap.this.value[pos];
								if (this.next == pos) {
									this.next = last;
								}

								if (this.prev == pos) {
									this.prev = last;
								}

								Float2ByteLinkedOpenHashMap.this.fixPointers(pos, last);
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

		public void set(it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry ok) {
			throw new UnsupportedOperationException();
		}

		public void add(it.unimi.dsi.fastutil.floats.Float2ByteMap.Entry ok) {
			throw new UnsupportedOperationException();
		}
	}

	private final class ValueIterator extends Float2ByteLinkedOpenHashMap.MapIterator implements ByteListIterator {
		@Override
		public byte previousByte() {
			return Float2ByteLinkedOpenHashMap.this.value[this.previousEntry()];
		}

		public ValueIterator() {
		}

		@Override
		public byte nextByte() {
			return Float2ByteLinkedOpenHashMap.this.value[this.nextEntry()];
		}
	}
}
