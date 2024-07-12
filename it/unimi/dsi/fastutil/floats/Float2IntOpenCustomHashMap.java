package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloat2IntMap.BasicEntry;
import it.unimi.dsi.fastutil.floats.Float2IntMap.FastEntrySet;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
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
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleToIntFunction;
import java.util.function.IntConsumer;

public class Float2IntOpenCustomHashMap extends AbstractFloat2IntMap implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient float[] key;
	protected transient int[] value;
	protected transient int mask;
	protected transient boolean containsNullKey;
	protected FloatHash.Strategy strategy;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;
	protected transient FastEntrySet entries;
	protected transient FloatSet keys;
	protected transient IntCollection values;

	public Float2IntOpenCustomHashMap(int expected, float f, FloatHash.Strategy strategy) {
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
			this.key = new float[this.n + 1];
			this.value = new int[this.n + 1];
		}
	}

	public Float2IntOpenCustomHashMap(int expected, FloatHash.Strategy strategy) {
		this(expected, 0.75F, strategy);
	}

	public Float2IntOpenCustomHashMap(FloatHash.Strategy strategy) {
		this(16, 0.75F, strategy);
	}

	public Float2IntOpenCustomHashMap(Map<? extends Float, ? extends Integer> m, float f, FloatHash.Strategy strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Float2IntOpenCustomHashMap(Map<? extends Float, ? extends Integer> m, FloatHash.Strategy strategy) {
		this(m, 0.75F, strategy);
	}

	public Float2IntOpenCustomHashMap(Float2IntMap m, float f, FloatHash.Strategy strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Float2IntOpenCustomHashMap(Float2IntMap m, FloatHash.Strategy strategy) {
		this(m, 0.75F, strategy);
	}

	public Float2IntOpenCustomHashMap(float[] k, int[] v, float f, FloatHash.Strategy strategy) {
		this(k.length, f, strategy);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Float2IntOpenCustomHashMap(float[] k, int[] v, FloatHash.Strategy strategy) {
		this(k, v, 0.75F, strategy);
	}

	public FloatHash.Strategy strategy() {
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

	private int removeEntry(int pos) {
		int oldValue = this.value[pos];
		this.size--;
		this.shiftKeys(pos);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	private int removeNullEntry() {
		this.containsNullKey = false;
		int oldValue = this.value[this.n];
		this.size--;
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	@Override
	public void putAll(Map<? extends Float, ? extends Integer> m) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(m.size());
		} else {
			this.tryCapacity((long)(this.size() + m.size()));
		}

		super.putAll(m);
	}

	private int find(float k) {
		if (this.strategy.equals(k, 0.0F)) {
			return this.containsNullKey ? this.n : -(this.n + 1);
		} else {
			float[] key = this.key;
			float curr;
			int pos;
			if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return -(pos + 1);
			} else if (this.strategy.equals(k, curr)) {
				return pos;
			} else {
				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr)) {
						return pos;
					}
				}

				return -(pos + 1);
			}
		}
	}

	private void insert(int pos, float k, int v) {
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
	public int put(float k, int v) {
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		} else {
			int oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	private int addToValue(int pos, int incr) {
		int oldValue = this.value[pos];
		this.value[pos] = oldValue + incr;
		return oldValue;
	}

	public int addTo(float k, int incr) {
		int pos;
		if (this.strategy.equals(k, 0.0F)) {
			if (this.containsNullKey) {
				return this.addToValue(this.n, incr);
			}

			pos = this.n;
			this.containsNullKey = true;
		} else {
			float[] key = this.key;
			float curr;
			if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
				if (this.strategy.equals(curr, k)) {
					return this.addToValue(pos, incr);
				}

				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(curr, k)) {
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
		float[] key = this.key;

		label30:
		while (true) {
			int last = pos;

			float curr;
			for (pos = pos + 1 & this.mask; Float.floatToIntBits(curr = key[pos]) != 0; pos = pos + 1 & this.mask) {
				int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					key[last] = curr;
					this.value[last] = this.value[pos];
					continue label30;
				}
			}

			key[last] = 0.0F;
			return;
		}
	}

	@Override
	public int remove(float k) {
		if (this.strategy.equals(k, 0.0F)) {
			return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
		} else {
			float[] key = this.key;
			float curr;
			int pos;
			if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return this.defRetValue;
			} else if (this.strategy.equals(k, curr)) {
				return this.removeEntry(pos);
			} else {
				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr)) {
						return this.removeEntry(pos);
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public int get(float k) {
		if (this.strategy.equals(k, 0.0F)) {
			return this.containsNullKey ? this.value[this.n] : this.defRetValue;
		} else {
			float[] key = this.key;
			float curr;
			int pos;
			if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return this.defRetValue;
			} else if (this.strategy.equals(k, curr)) {
				return this.value[pos];
			} else {
				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr)) {
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public boolean containsKey(float k) {
		if (this.strategy.equals(k, 0.0F)) {
			return this.containsNullKey;
		} else {
			float[] key = this.key;
			float curr;
			int pos;
			if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return false;
			} else if (this.strategy.equals(k, curr)) {
				return true;
			} else {
				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr)) {
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean containsValue(int v) {
		int[] value = this.value;
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
	public int getOrDefault(float k, int defaultValue) {
		if (this.strategy.equals(k, 0.0F)) {
			return this.containsNullKey ? this.value[this.n] : defaultValue;
		} else {
			float[] key = this.key;
			float curr;
			int pos;
			if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return defaultValue;
			} else if (this.strategy.equals(k, curr)) {
				return this.value[pos];
			} else {
				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr)) {
						return this.value[pos];
					}
				}

				return defaultValue;
			}
		}
	}

	@Override
	public int putIfAbsent(float k, int v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(float k, int v) {
		if (this.strategy.equals(k, 0.0F)) {
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
			if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return false;
			} else if (this.strategy.equals(k, curr) && v == this.value[pos]) {
				this.removeEntry(pos);
				return true;
			} else {
				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr) && v == this.value[pos]) {
						this.removeEntry(pos);
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean replace(float k, int oldValue, int v) {
		int pos = this.find(k);
		if (pos >= 0 && oldValue == this.value[pos]) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int replace(float k, int v) {
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			int oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	@Override
	public int computeIfAbsent(float k, DoubleToIntFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			int newValue = mappingFunction.applyAsInt((double)k);
			this.insert(-pos - 1, k, newValue);
			return newValue;
		}
	}

	@Override
	public int computeIfAbsentNullable(float k, DoubleFunction<? extends Integer> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			Integer newValue = (Integer)mappingFunction.apply((double)k);
			if (newValue == null) {
				return this.defRetValue;
			} else {
				int v = newValue;
				this.insert(-pos - 1, k, v);
				return v;
			}
		}
	}

	@Override
	public int computeIfPresent(float k, BiFunction<? super Float, ? super Integer, ? extends Integer> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			Integer newValue = (Integer)remappingFunction.apply(k, this.value[pos]);
			if (newValue == null) {
				if (this.strategy.equals(k, 0.0F)) {
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
	public int compute(float k, BiFunction<? super Float, ? super Integer, ? extends Integer> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		Integer newValue = (Integer)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
		if (newValue == null) {
			if (pos >= 0) {
				if (this.strategy.equals(k, 0.0F)) {
					this.removeNullEntry();
				} else {
					this.removeEntry(pos);
				}
			}

			return this.defRetValue;
		} else {
			int newVal = newValue;
			if (pos < 0) {
				this.insert(-pos - 1, k, newVal);
				return newVal;
			} else {
				return this.value[pos] = newVal;
			}
		}
	}

	@Override
	public int merge(float k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return v;
		} else {
			Integer newValue = (Integer)remappingFunction.apply(this.value[pos], v);
			if (newValue == null) {
				if (this.strategy.equals(k, 0.0F)) {
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

	public FastEntrySet float2IntEntrySet() {
		if (this.entries == null) {
			this.entries = new Float2IntOpenCustomHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public FloatSet keySet() {
		if (this.keys == null) {
			this.keys = new Float2IntOpenCustomHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public IntCollection values() {
		if (this.values == null) {
			this.values = new AbstractIntCollection() {
				@Override
				public IntIterator iterator() {
					return Float2IntOpenCustomHashMap.this.new ValueIterator();
				}

				public int size() {
					return Float2IntOpenCustomHashMap.this.size;
				}

				@Override
				public boolean contains(int v) {
					return Float2IntOpenCustomHashMap.this.containsValue(v);
				}

				public void clear() {
					Float2IntOpenCustomHashMap.this.clear();
				}

				@Override
				public void forEach(IntConsumer consumer) {
					if (Float2IntOpenCustomHashMap.this.containsNullKey) {
						consumer.accept(Float2IntOpenCustomHashMap.this.value[Float2IntOpenCustomHashMap.this.n]);
					}

					int pos = Float2IntOpenCustomHashMap.this.n;

					while (pos-- != 0) {
						if (Float.floatToIntBits(Float2IntOpenCustomHashMap.this.key[pos]) != 0) {
							consumer.accept(Float2IntOpenCustomHashMap.this.value[pos]);
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
		int[] value = this.value;
		int mask = newN - 1;
		float[] newKey = new float[newN + 1];
		int[] newValue = new int[newN + 1];
		int i = this.n;
		int j = this.realSize();

		while (j-- != 0) {
			while (Float.floatToIntBits(key[--i]) == 0) {
			}

			int pos;
			if (Float.floatToIntBits(newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0) {
				while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0) {
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

	public Float2IntOpenCustomHashMap clone() {
		Float2IntOpenCustomHashMap c;
		try {
			c = (Float2IntOpenCustomHashMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (float[])this.key.clone();
		c.value = (int[])this.value.clone();
		c.strategy = this.strategy;
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

			t = this.strategy.hashCode(this.key[i]);
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
		int[] value = this.value;
		Float2IntOpenCustomHashMap.MapIterator i = new Float2IntOpenCustomHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeFloat(key[e]);
			s.writeInt(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		float[] key = this.key = new float[this.n + 1];
		int[] value = this.value = new int[this.n + 1];
		int i = this.size;

		while (i-- != 0) {
			float k = s.readFloat();
			int v = s.readInt();
			int pos;
			if (this.strategy.equals(k, 0.0F)) {
				pos = this.n;
				this.containsNullKey = true;
			} else {
				pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;

				while (Float.floatToIntBits(key[pos]) != 0) {
					pos = pos + 1 & this.mask;
				}
			}

			key[pos] = k;
			value[pos] = v;
		}
	}

	private void checkTable() {
	}

	private class EntryIterator extends Float2IntOpenCustomHashMap.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.floats.Float2IntMap.Entry> {
		private Float2IntOpenCustomHashMap.MapEntry entry;

		private EntryIterator() {
		}

		public Float2IntOpenCustomHashMap.MapEntry next() {
			return this.entry = Float2IntOpenCustomHashMap.this.new MapEntry(this.nextEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator extends Float2IntOpenCustomHashMap.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.floats.Float2IntMap.Entry> {
		private final Float2IntOpenCustomHashMap.MapEntry entry = Float2IntOpenCustomHashMap.this.new MapEntry();

		private FastEntryIterator() {
		}

		public Float2IntOpenCustomHashMap.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Float2IntOpenCustomHashMap.MapIterator implements FloatIterator {
		public KeyIterator() {
		}

		@Override
		public float nextFloat() {
			return Float2IntOpenCustomHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractFloatSet {
		private KeySet() {
		}

		@Override
		public FloatIterator iterator() {
			return Float2IntOpenCustomHashMap.this.new KeyIterator();
		}

		@Override
		public void forEach(DoubleConsumer consumer) {
			if (Float2IntOpenCustomHashMap.this.containsNullKey) {
				consumer.accept((double)Float2IntOpenCustomHashMap.this.key[Float2IntOpenCustomHashMap.this.n]);
			}

			int pos = Float2IntOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				float k = Float2IntOpenCustomHashMap.this.key[pos];
				if (Float.floatToIntBits(k) != 0) {
					consumer.accept((double)k);
				}
			}
		}

		public int size() {
			return Float2IntOpenCustomHashMap.this.size;
		}

		@Override
		public boolean contains(float k) {
			return Float2IntOpenCustomHashMap.this.containsKey(k);
		}

		@Override
		public boolean remove(float k) {
			int oldSize = Float2IntOpenCustomHashMap.this.size;
			Float2IntOpenCustomHashMap.this.remove(k);
			return Float2IntOpenCustomHashMap.this.size != oldSize;
		}

		public void clear() {
			Float2IntOpenCustomHashMap.this.clear();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.floats.Float2IntMap.Entry, java.util.Map.Entry<Float, Integer> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		@Override
		public float getFloatKey() {
			return Float2IntOpenCustomHashMap.this.key[this.index];
		}

		@Override
		public int getIntValue() {
			return Float2IntOpenCustomHashMap.this.value[this.index];
		}

		@Override
		public int setValue(int v) {
			int oldValue = Float2IntOpenCustomHashMap.this.value[this.index];
			Float2IntOpenCustomHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Float getKey() {
			return Float2IntOpenCustomHashMap.this.key[this.index];
		}

		@Deprecated
		@Override
		public Integer getValue() {
			return Float2IntOpenCustomHashMap.this.value[this.index];
		}

		@Deprecated
		@Override
		public Integer setValue(Integer v) {
			return this.setValue(v.intValue());
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<Float, Integer> e = (java.util.Map.Entry<Float, Integer>)o;
				return Float2IntOpenCustomHashMap.this.strategy.equals(Float2IntOpenCustomHashMap.this.key[this.index], (Float)e.getKey())
					&& Float2IntOpenCustomHashMap.this.value[this.index] == (Integer)e.getValue();
			}
		}

		public int hashCode() {
			return Float2IntOpenCustomHashMap.this.strategy.hashCode(Float2IntOpenCustomHashMap.this.key[this.index])
				^ Float2IntOpenCustomHashMap.this.value[this.index];
		}

		public String toString() {
			return Float2IntOpenCustomHashMap.this.key[this.index] + "=>" + Float2IntOpenCustomHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.floats.Float2IntMap.Entry> implements FastEntrySet {
		private MapEntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.floats.Float2IntMap.Entry> iterator() {
			return Float2IntOpenCustomHashMap.this.new EntryIterator();
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.floats.Float2IntMap.Entry> fastIterator() {
			return Float2IntOpenCustomHashMap.this.new FastEntryIterator();
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Float)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Integer) {
					float k = (Float)e.getKey();
					int v = (Integer)e.getValue();
					if (Float2IntOpenCustomHashMap.this.strategy.equals(k, 0.0F)) {
						return Float2IntOpenCustomHashMap.this.containsNullKey && Float2IntOpenCustomHashMap.this.value[Float2IntOpenCustomHashMap.this.n] == v;
					} else {
						float[] key = Float2IntOpenCustomHashMap.this.key;
						float curr;
						int pos;
						if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(Float2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Float2IntOpenCustomHashMap.this.mask])
							== 0) {
							return false;
						} else if (Float2IntOpenCustomHashMap.this.strategy.equals(k, curr)) {
							return Float2IntOpenCustomHashMap.this.value[pos] == v;
						} else {
							while (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2IntOpenCustomHashMap.this.mask]) != 0) {
								if (Float2IntOpenCustomHashMap.this.strategy.equals(k, curr)) {
									return Float2IntOpenCustomHashMap.this.value[pos] == v;
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
					if (e.getValue() != null && e.getValue() instanceof Integer) {
						float k = (Float)e.getKey();
						int v = (Integer)e.getValue();
						if (Float2IntOpenCustomHashMap.this.strategy.equals(k, 0.0F)) {
							if (Float2IntOpenCustomHashMap.this.containsNullKey && Float2IntOpenCustomHashMap.this.value[Float2IntOpenCustomHashMap.this.n] == v) {
								Float2IntOpenCustomHashMap.this.removeNullEntry();
								return true;
							} else {
								return false;
							}
						} else {
							float[] key = Float2IntOpenCustomHashMap.this.key;
							float curr;
							int pos;
							if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(Float2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Float2IntOpenCustomHashMap.this.mask])
								== 0) {
								return false;
							} else if (Float2IntOpenCustomHashMap.this.strategy.equals(curr, k)) {
								if (Float2IntOpenCustomHashMap.this.value[pos] == v) {
									Float2IntOpenCustomHashMap.this.removeEntry(pos);
									return true;
								} else {
									return false;
								}
							} else {
								while (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2IntOpenCustomHashMap.this.mask]) != 0) {
									if (Float2IntOpenCustomHashMap.this.strategy.equals(curr, k) && Float2IntOpenCustomHashMap.this.value[pos] == v) {
										Float2IntOpenCustomHashMap.this.removeEntry(pos);
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
			return Float2IntOpenCustomHashMap.this.size;
		}

		public void clear() {
			Float2IntOpenCustomHashMap.this.clear();
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.floats.Float2IntMap.Entry> consumer) {
			if (Float2IntOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(
					new BasicEntry(
						Float2IntOpenCustomHashMap.this.key[Float2IntOpenCustomHashMap.this.n], Float2IntOpenCustomHashMap.this.value[Float2IntOpenCustomHashMap.this.n]
					)
				);
			}

			int pos = Float2IntOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Float.floatToIntBits(Float2IntOpenCustomHashMap.this.key[pos]) != 0) {
					consumer.accept(new BasicEntry(Float2IntOpenCustomHashMap.this.key[pos], Float2IntOpenCustomHashMap.this.value[pos]));
				}
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.floats.Float2IntMap.Entry> consumer) {
			BasicEntry entry = new BasicEntry();
			if (Float2IntOpenCustomHashMap.this.containsNullKey) {
				entry.key = Float2IntOpenCustomHashMap.this.key[Float2IntOpenCustomHashMap.this.n];
				entry.value = Float2IntOpenCustomHashMap.this.value[Float2IntOpenCustomHashMap.this.n];
				consumer.accept(entry);
			}

			int pos = Float2IntOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Float.floatToIntBits(Float2IntOpenCustomHashMap.this.key[pos]) != 0) {
					entry.key = Float2IntOpenCustomHashMap.this.key[pos];
					entry.value = Float2IntOpenCustomHashMap.this.value[pos];
					consumer.accept(entry);
				}
			}
		}
	}

	private class MapIterator {
		int pos = Float2IntOpenCustomHashMap.this.n;
		int last = -1;
		int c = Float2IntOpenCustomHashMap.this.size;
		boolean mustReturnNullKey = Float2IntOpenCustomHashMap.this.containsNullKey;
		FloatArrayList wrapped;

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
					return this.last = Float2IntOpenCustomHashMap.this.n;
				} else {
					float[] key = Float2IntOpenCustomHashMap.this.key;

					while (--this.pos >= 0) {
						if (Float.floatToIntBits(key[this.pos]) != 0) {
							return this.last = this.pos;
						}
					}

					this.last = Integer.MIN_VALUE;
					float k = this.wrapped.getFloat(-this.pos - 1);
					int p = HashCommon.mix(Float2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Float2IntOpenCustomHashMap.this.mask;

					while (!Float2IntOpenCustomHashMap.this.strategy.equals(k, key[p])) {
						p = p + 1 & Float2IntOpenCustomHashMap.this.mask;
					}

					return p;
				}
			}
		}

		private void shiftKeys(int pos) {
			float[] key = Float2IntOpenCustomHashMap.this.key;

			label38:
			while (true) {
				int last = pos;

				float curr;
				for (pos = pos + 1 & Float2IntOpenCustomHashMap.this.mask; Float.floatToIntBits(curr = key[pos]) != 0; pos = pos + 1 & Float2IntOpenCustomHashMap.this.mask) {
					int slot = HashCommon.mix(Float2IntOpenCustomHashMap.this.strategy.hashCode(curr)) & Float2IntOpenCustomHashMap.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new FloatArrayList(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						Float2IntOpenCustomHashMap.this.value[last] = Float2IntOpenCustomHashMap.this.value[pos];
						continue label38;
					}
				}

				key[last] = 0.0F;
				return;
			}
		}

		public void remove() {
			if (this.last == -1) {
				throw new IllegalStateException();
			} else {
				if (this.last == Float2IntOpenCustomHashMap.this.n) {
					Float2IntOpenCustomHashMap.this.containsNullKey = false;
				} else {
					if (this.pos < 0) {
						Float2IntOpenCustomHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				Float2IntOpenCustomHashMap.this.size--;
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

	private final class ValueIterator extends Float2IntOpenCustomHashMap.MapIterator implements IntIterator {
		public ValueIterator() {
		}

		@Override
		public int nextInt() {
			return Float2IntOpenCustomHashMap.this.value[this.nextEntry()];
		}
	}
}
