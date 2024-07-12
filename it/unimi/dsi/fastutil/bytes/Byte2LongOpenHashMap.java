package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.bytes.AbstractByte2LongMap.BasicEntry;
import it.unimi.dsi.fastutil.bytes.Byte2LongMap.FastEntrySet;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntToLongFunction;
import java.util.function.LongConsumer;

public class Byte2LongOpenHashMap extends AbstractByte2LongMap implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient byte[] key;
	protected transient long[] value;
	protected transient int mask;
	protected transient boolean containsNullKey;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;
	protected transient FastEntrySet entries;
	protected transient ByteSet keys;
	protected transient LongCollection values;

	public Byte2LongOpenHashMap(int expected, float f) {
		if (f <= 0.0F || f > 1.0F) {
			throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
		} else if (expected < 0) {
			throw new IllegalArgumentException("The expected number of elements must be nonnegative");
		} else {
			this.f = f;
			this.minN = this.n = HashCommon.arraySize(expected, f);
			this.mask = this.n - 1;
			this.maxFill = HashCommon.maxFill(this.n, f);
			this.key = new byte[this.n + 1];
			this.value = new long[this.n + 1];
		}
	}

	public Byte2LongOpenHashMap(int expected) {
		this(expected, 0.75F);
	}

	public Byte2LongOpenHashMap() {
		this(16, 0.75F);
	}

	public Byte2LongOpenHashMap(Map<? extends Byte, ? extends Long> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Byte2LongOpenHashMap(Map<? extends Byte, ? extends Long> m) {
		this(m, 0.75F);
	}

	public Byte2LongOpenHashMap(Byte2LongMap m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Byte2LongOpenHashMap(Byte2LongMap m) {
		this(m, 0.75F);
	}

	public Byte2LongOpenHashMap(byte[] k, long[] v, float f) {
		this(k.length, f);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Byte2LongOpenHashMap(byte[] k, long[] v) {
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

	private long removeEntry(int pos) {
		long oldValue = this.value[pos];
		this.size--;
		this.shiftKeys(pos);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	private long removeNullEntry() {
		this.containsNullKey = false;
		long oldValue = this.value[this.n];
		this.size--;
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	@Override
	public void putAll(Map<? extends Byte, ? extends Long> m) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(m.size());
		} else {
			this.tryCapacity((long)(this.size() + m.size()));
		}

		super.putAll(m);
	}

	private int find(byte k) {
		if (k == 0) {
			return this.containsNullKey ? this.n : -(this.n + 1);
		} else {
			byte[] key = this.key;
			byte curr;
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

	private void insert(int pos, byte k, long v) {
		if (pos == this.n) {
			this.containsNullKey = true;
		}

		this.key[pos] = k;
		this.value[pos] = v;
		if (this.size++ >= this.maxFill) {
			this.rehash(HashCommon.arraySize(this.size + 1, this.f));
		}
	}

	@Override
	public long put(byte k, long v) {
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		} else {
			long oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	private long addToValue(int pos, long incr) {
		long oldValue = this.value[pos];
		this.value[pos] = oldValue + incr;
		return oldValue;
	}

	public long addTo(byte k, long incr) {
		int pos;
		if (k == 0) {
			if (this.containsNullKey) {
				return this.addToValue(this.n, incr);
			}

			pos = this.n;
			this.containsNullKey = true;
		} else {
			byte[] key = this.key;
			byte curr;
			if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
				if (curr == k) {
					return this.addToValue(pos, incr);
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (curr == k) {
						return this.addToValue(pos, incr);
					}
				}
			}
		}

		this.key[pos] = k;
		this.value[pos] = this.defRetValue + incr;
		if (this.size++ >= this.maxFill) {
			this.rehash(HashCommon.arraySize(this.size + 1, this.f));
		}

		return this.defRetValue;
	}

	protected final void shiftKeys(int pos) {
		byte[] key = this.key;

		label30:
		while (true) {
			int last = pos;

			byte curr;
			for (pos = pos + 1 & this.mask; (curr = key[pos]) != 0; pos = pos + 1 & this.mask) {
				int slot = HashCommon.mix(curr) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					key[last] = curr;
					this.value[last] = this.value[pos];
					continue label30;
				}
			}

			key[last] = 0;
			return;
		}
	}

	@Override
	public long remove(byte k) {
		if (k == 0) {
			return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
		} else {
			byte[] key = this.key;
			byte curr;
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

	@Override
	public long get(byte k) {
		if (k == 0) {
			return this.containsNullKey ? this.value[this.n] : this.defRetValue;
		} else {
			byte[] key = this.key;
			byte curr;
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
	public boolean containsKey(byte k) {
		if (k == 0) {
			return this.containsNullKey;
		} else {
			byte[] key = this.key;
			byte curr;
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
	public boolean containsValue(long v) {
		long[] value = this.value;
		byte[] key = this.key;
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
	public long getOrDefault(byte k, long defaultValue) {
		if (k == 0) {
			return this.containsNullKey ? this.value[this.n] : defaultValue;
		} else {
			byte[] key = this.key;
			byte curr;
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
	public long putIfAbsent(byte k, long v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(byte k, long v) {
		if (k == 0) {
			if (this.containsNullKey && v == this.value[this.n]) {
				this.removeNullEntry();
				return true;
			} else {
				return false;
			}
		} else {
			byte[] key = this.key;
			byte curr;
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
	public boolean replace(byte k, long oldValue, long v) {
		int pos = this.find(k);
		if (pos >= 0 && oldValue == this.value[pos]) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public long replace(byte k, long v) {
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			long oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	@Override
	public long computeIfAbsent(byte k, IntToLongFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			long newValue = mappingFunction.applyAsLong(k);
			this.insert(-pos - 1, k, newValue);
			return newValue;
		}
	}

	@Override
	public long computeIfAbsentNullable(byte k, IntFunction<? extends Long> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			Long newValue = (Long)mappingFunction.apply(k);
			if (newValue == null) {
				return this.defRetValue;
			} else {
				long v = newValue;
				this.insert(-pos - 1, k, v);
				return v;
			}
		}
	}

	@Override
	public long computeIfPresent(byte k, BiFunction<? super Byte, ? super Long, ? extends Long> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			Long newValue = (Long)remappingFunction.apply(k, this.value[pos]);
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
	public long compute(byte k, BiFunction<? super Byte, ? super Long, ? extends Long> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		Long newValue = (Long)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
		if (newValue == null) {
			if (pos >= 0) {
				if (k == 0) {
					this.removeNullEntry();
				} else {
					this.removeEntry(pos);
				}
			}

			return this.defRetValue;
		} else {
			long newVal = newValue;
			if (pos < 0) {
				this.insert(-pos - 1, k, newVal);
				return newVal;
			} else {
				return this.value[pos] = newVal;
			}
		}
	}

	@Override
	public long merge(byte k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return v;
		} else {
			Long newValue = (Long)remappingFunction.apply(this.value[pos], v);
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
	public void clear() {
		if (this.size != 0) {
			this.size = 0;
			this.containsNullKey = false;
			Arrays.fill(this.key, (byte)0);
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

	public FastEntrySet byte2LongEntrySet() {
		if (this.entries == null) {
			this.entries = new Byte2LongOpenHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public ByteSet keySet() {
		if (this.keys == null) {
			this.keys = new Byte2LongOpenHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public LongCollection values() {
		if (this.values == null) {
			this.values = new AbstractLongCollection() {
				@Override
				public LongIterator iterator() {
					return Byte2LongOpenHashMap.this.new ValueIterator();
				}

				public int size() {
					return Byte2LongOpenHashMap.this.size;
				}

				@Override
				public boolean contains(long v) {
					return Byte2LongOpenHashMap.this.containsValue(v);
				}

				public void clear() {
					Byte2LongOpenHashMap.this.clear();
				}

				@Override
				public void forEach(LongConsumer consumer) {
					if (Byte2LongOpenHashMap.this.containsNullKey) {
						consumer.accept(Byte2LongOpenHashMap.this.value[Byte2LongOpenHashMap.this.n]);
					}

					int pos = Byte2LongOpenHashMap.this.n;

					while (pos-- != 0) {
						if (Byte2LongOpenHashMap.this.key[pos] != 0) {
							consumer.accept(Byte2LongOpenHashMap.this.value[pos]);
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
		byte[] key = this.key;
		long[] value = this.value;
		int mask = newN - 1;
		byte[] newKey = new byte[newN + 1];
		long[] newValue = new long[newN + 1];
		int i = this.n;
		int j = this.realSize();

		while (j-- != 0) {
			while (key[--i] == 0) {
			}

			int pos;
			if (newKey[pos = HashCommon.mix(key[i]) & mask] != 0) {
				while (newKey[pos = pos + 1 & mask] != 0) {
				}
			}

			newKey[pos] = key[i];
			newValue[pos] = value[i];
		}

		newValue[newN] = value[this.n];
		this.n = newN;
		this.mask = mask;
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.key = newKey;
		this.value = newValue;
	}

	public Byte2LongOpenHashMap clone() {
		Byte2LongOpenHashMap c;
		try {
			c = (Byte2LongOpenHashMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (byte[])this.key.clone();
		c.value = (long[])this.value.clone();
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

			int var5 = this.key[i];
			var5 ^= HashCommon.long2int(this.value[i]);
			h += var5;
		}

		if (this.containsNullKey) {
			h += HashCommon.long2int(this.value[this.n]);
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		byte[] key = this.key;
		long[] value = this.value;
		Byte2LongOpenHashMap.MapIterator i = new Byte2LongOpenHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeByte(key[e]);
			s.writeLong(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		byte[] key = this.key = new byte[this.n + 1];
		long[] value = this.value = new long[this.n + 1];
		int i = this.size;

		while (i-- != 0) {
			byte k = s.readByte();
			long v = s.readLong();
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
		}
	}

	private void checkTable() {
	}

	private class EntryIterator extends Byte2LongOpenHashMap.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.bytes.Byte2LongMap.Entry> {
		private Byte2LongOpenHashMap.MapEntry entry;

		private EntryIterator() {
		}

		public Byte2LongOpenHashMap.MapEntry next() {
			return this.entry = Byte2LongOpenHashMap.this.new MapEntry(this.nextEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator extends Byte2LongOpenHashMap.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.bytes.Byte2LongMap.Entry> {
		private final Byte2LongOpenHashMap.MapEntry entry = Byte2LongOpenHashMap.this.new MapEntry();

		private FastEntryIterator() {
		}

		public Byte2LongOpenHashMap.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Byte2LongOpenHashMap.MapIterator implements ByteIterator {
		public KeyIterator() {
		}

		@Override
		public byte nextByte() {
			return Byte2LongOpenHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractByteSet {
		private KeySet() {
		}

		@Override
		public ByteIterator iterator() {
			return Byte2LongOpenHashMap.this.new KeyIterator();
		}

		@Override
		public void forEach(IntConsumer consumer) {
			if (Byte2LongOpenHashMap.this.containsNullKey) {
				consumer.accept(Byte2LongOpenHashMap.this.key[Byte2LongOpenHashMap.this.n]);
			}

			int pos = Byte2LongOpenHashMap.this.n;

			while (pos-- != 0) {
				byte k = Byte2LongOpenHashMap.this.key[pos];
				if (k != 0) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Byte2LongOpenHashMap.this.size;
		}

		@Override
		public boolean contains(byte k) {
			return Byte2LongOpenHashMap.this.containsKey(k);
		}

		@Override
		public boolean remove(byte k) {
			int oldSize = Byte2LongOpenHashMap.this.size;
			Byte2LongOpenHashMap.this.remove(k);
			return Byte2LongOpenHashMap.this.size != oldSize;
		}

		public void clear() {
			Byte2LongOpenHashMap.this.clear();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.bytes.Byte2LongMap.Entry, java.util.Map.Entry<Byte, Long> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		@Override
		public byte getByteKey() {
			return Byte2LongOpenHashMap.this.key[this.index];
		}

		@Override
		public long getLongValue() {
			return Byte2LongOpenHashMap.this.value[this.index];
		}

		@Override
		public long setValue(long v) {
			long oldValue = Byte2LongOpenHashMap.this.value[this.index];
			Byte2LongOpenHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Byte getKey() {
			return Byte2LongOpenHashMap.this.key[this.index];
		}

		@Deprecated
		@Override
		public Long getValue() {
			return Byte2LongOpenHashMap.this.value[this.index];
		}

		@Deprecated
		@Override
		public Long setValue(Long v) {
			return this.setValue(v.longValue());
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<Byte, Long> e = (java.util.Map.Entry<Byte, Long>)o;
				return Byte2LongOpenHashMap.this.key[this.index] == (Byte)e.getKey() && Byte2LongOpenHashMap.this.value[this.index] == (Long)e.getValue();
			}
		}

		public int hashCode() {
			return Byte2LongOpenHashMap.this.key[this.index] ^ HashCommon.long2int(Byte2LongOpenHashMap.this.value[this.index]);
		}

		public String toString() {
			return Byte2LongOpenHashMap.this.key[this.index] + "=>" + Byte2LongOpenHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.bytes.Byte2LongMap.Entry> implements FastEntrySet {
		private MapEntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.bytes.Byte2LongMap.Entry> iterator() {
			return Byte2LongOpenHashMap.this.new EntryIterator();
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.bytes.Byte2LongMap.Entry> fastIterator() {
			return Byte2LongOpenHashMap.this.new FastEntryIterator();
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Byte)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Long) {
					byte k = (Byte)e.getKey();
					long v = (Long)e.getValue();
					if (k == 0) {
						return Byte2LongOpenHashMap.this.containsNullKey && Byte2LongOpenHashMap.this.value[Byte2LongOpenHashMap.this.n] == v;
					} else {
						byte[] key = Byte2LongOpenHashMap.this.key;
						byte curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(k) & Byte2LongOpenHashMap.this.mask]) == 0) {
							return false;
						} else if (k == curr) {
							return Byte2LongOpenHashMap.this.value[pos] == v;
						} else {
							while ((curr = key[pos = pos + 1 & Byte2LongOpenHashMap.this.mask]) != 0) {
								if (k == curr) {
									return Byte2LongOpenHashMap.this.value[pos] == v;
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
				if (e.getKey() != null && e.getKey() instanceof Byte) {
					if (e.getValue() != null && e.getValue() instanceof Long) {
						byte k = (Byte)e.getKey();
						long v = (Long)e.getValue();
						if (k == 0) {
							if (Byte2LongOpenHashMap.this.containsNullKey && Byte2LongOpenHashMap.this.value[Byte2LongOpenHashMap.this.n] == v) {
								Byte2LongOpenHashMap.this.removeNullEntry();
								return true;
							} else {
								return false;
							}
						} else {
							byte[] key = Byte2LongOpenHashMap.this.key;
							byte curr;
							int pos;
							if ((curr = key[pos = HashCommon.mix(k) & Byte2LongOpenHashMap.this.mask]) == 0) {
								return false;
							} else if (curr == k) {
								if (Byte2LongOpenHashMap.this.value[pos] == v) {
									Byte2LongOpenHashMap.this.removeEntry(pos);
									return true;
								} else {
									return false;
								}
							} else {
								while ((curr = key[pos = pos + 1 & Byte2LongOpenHashMap.this.mask]) != 0) {
									if (curr == k && Byte2LongOpenHashMap.this.value[pos] == v) {
										Byte2LongOpenHashMap.this.removeEntry(pos);
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
			return Byte2LongOpenHashMap.this.size;
		}

		public void clear() {
			Byte2LongOpenHashMap.this.clear();
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.bytes.Byte2LongMap.Entry> consumer) {
			if (Byte2LongOpenHashMap.this.containsNullKey) {
				consumer.accept(new BasicEntry(Byte2LongOpenHashMap.this.key[Byte2LongOpenHashMap.this.n], Byte2LongOpenHashMap.this.value[Byte2LongOpenHashMap.this.n]));
			}

			int pos = Byte2LongOpenHashMap.this.n;

			while (pos-- != 0) {
				if (Byte2LongOpenHashMap.this.key[pos] != 0) {
					consumer.accept(new BasicEntry(Byte2LongOpenHashMap.this.key[pos], Byte2LongOpenHashMap.this.value[pos]));
				}
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.bytes.Byte2LongMap.Entry> consumer) {
			BasicEntry entry = new BasicEntry();
			if (Byte2LongOpenHashMap.this.containsNullKey) {
				entry.key = Byte2LongOpenHashMap.this.key[Byte2LongOpenHashMap.this.n];
				entry.value = Byte2LongOpenHashMap.this.value[Byte2LongOpenHashMap.this.n];
				consumer.accept(entry);
			}

			int pos = Byte2LongOpenHashMap.this.n;

			while (pos-- != 0) {
				if (Byte2LongOpenHashMap.this.key[pos] != 0) {
					entry.key = Byte2LongOpenHashMap.this.key[pos];
					entry.value = Byte2LongOpenHashMap.this.value[pos];
					consumer.accept(entry);
				}
			}
		}
	}

	private class MapIterator {
		int pos = Byte2LongOpenHashMap.this.n;
		int last = -1;
		int c = Byte2LongOpenHashMap.this.size;
		boolean mustReturnNullKey = Byte2LongOpenHashMap.this.containsNullKey;
		ByteArrayList wrapped;

		private MapIterator() {
		}

		public boolean hasNext() {
			return this.c != 0;
		}

		public int nextEntry() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.c--;
				if (this.mustReturnNullKey) {
					this.mustReturnNullKey = false;
					return this.last = Byte2LongOpenHashMap.this.n;
				} else {
					byte[] key = Byte2LongOpenHashMap.this.key;

					while (--this.pos >= 0) {
						if (key[this.pos] != 0) {
							return this.last = this.pos;
						}
					}

					this.last = Integer.MIN_VALUE;
					byte k = this.wrapped.getByte(-this.pos - 1);
					int p = HashCommon.mix(k) & Byte2LongOpenHashMap.this.mask;

					while (k != key[p]) {
						p = p + 1 & Byte2LongOpenHashMap.this.mask;
					}

					return p;
				}
			}
		}

		private void shiftKeys(int pos) {
			byte[] key = Byte2LongOpenHashMap.this.key;

			label38:
			while (true) {
				int last = pos;

				byte curr;
				for (pos = pos + 1 & Byte2LongOpenHashMap.this.mask; (curr = key[pos]) != 0; pos = pos + 1 & Byte2LongOpenHashMap.this.mask) {
					int slot = HashCommon.mix(curr) & Byte2LongOpenHashMap.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new ByteArrayList(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						Byte2LongOpenHashMap.this.value[last] = Byte2LongOpenHashMap.this.value[pos];
						continue label38;
					}
				}

				key[last] = 0;
				return;
			}
		}

		public void remove() {
			if (this.last == -1) {
				throw new IllegalStateException();
			} else {
				if (this.last == Byte2LongOpenHashMap.this.n) {
					Byte2LongOpenHashMap.this.containsNullKey = false;
				} else {
					if (this.pos < 0) {
						Byte2LongOpenHashMap.this.remove(this.wrapped.getByte(-this.pos - 1));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				Byte2LongOpenHashMap.this.size--;
				this.last = -1;
			}
		}

		public int skip(int n) {
			int i = n;

			while (i-- != 0 && this.hasNext()) {
				this.nextEntry();
			}

			return n - i - 1;
		}
	}

	private final class ValueIterator extends Byte2LongOpenHashMap.MapIterator implements LongIterator {
		public ValueIterator() {
		}

		@Override
		public long nextLong() {
			return Byte2LongOpenHashMap.this.value[this.nextEntry()];
		}
	}
}
