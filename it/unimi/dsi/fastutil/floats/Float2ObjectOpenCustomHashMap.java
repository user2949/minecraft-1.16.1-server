package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloat2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.floats.Float2ObjectMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
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

public class Float2ObjectOpenCustomHashMap<V> extends AbstractFloat2ObjectMap<V> implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient float[] key;
	protected transient V[] value;
	protected transient int mask;
	protected transient boolean containsNullKey;
	protected FloatHash.Strategy strategy;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;
	protected transient FastEntrySet<V> entries;
	protected transient FloatSet keys;
	protected transient ObjectCollection<V> values;

	public Float2ObjectOpenCustomHashMap(int expected, float f, FloatHash.Strategy strategy) {
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
			this.value = (V[])(new Object[this.n + 1]);
		}
	}

	public Float2ObjectOpenCustomHashMap(int expected, FloatHash.Strategy strategy) {
		this(expected, 0.75F, strategy);
	}

	public Float2ObjectOpenCustomHashMap(FloatHash.Strategy strategy) {
		this(16, 0.75F, strategy);
	}

	public Float2ObjectOpenCustomHashMap(Map<? extends Float, ? extends V> m, float f, FloatHash.Strategy strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Float2ObjectOpenCustomHashMap(Map<? extends Float, ? extends V> m, FloatHash.Strategy strategy) {
		this(m, 0.75F, strategy);
	}

	public Float2ObjectOpenCustomHashMap(Float2ObjectMap<V> m, float f, FloatHash.Strategy strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Float2ObjectOpenCustomHashMap(Float2ObjectMap<V> m, FloatHash.Strategy strategy) {
		this(m, 0.75F, strategy);
	}

	public Float2ObjectOpenCustomHashMap(float[] k, V[] v, float f, FloatHash.Strategy strategy) {
		this(k.length, f, strategy);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Float2ObjectOpenCustomHashMap(float[] k, V[] v, FloatHash.Strategy strategy) {
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

	private V removeEntry(int pos) {
		V oldValue = this.value[pos];
		this.value[pos] = null;
		this.size--;
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

	private void insert(int pos, float k, V v) {
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
				int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					key[last] = curr;
					this.value[last] = this.value[pos];
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
	public V get(float k) {
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
		if (this.strategy.equals(k, 0.0F)) {
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
			if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return false;
			} else if (this.strategy.equals(k, curr) && Objects.equals(v, this.value[pos])) {
				this.removeEntry(pos);
				return true;
			} else {
				while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr) && Objects.equals(v, this.value[pos])) {
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
	public V compute(float k, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		V newValue = (V)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
		if (newValue == null) {
			if (pos >= 0) {
				if (this.strategy.equals(k, 0.0F)) {
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
				if (this.strategy.equals(k, 0.0F)) {
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

	public FastEntrySet<V> float2ObjectEntrySet() {
		if (this.entries == null) {
			this.entries = new Float2ObjectOpenCustomHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public FloatSet keySet() {
		if (this.keys == null) {
			this.keys = new Float2ObjectOpenCustomHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public ObjectCollection<V> values() {
		if (this.values == null) {
			this.values = new AbstractObjectCollection<V>() {
				@Override
				public ObjectIterator<V> iterator() {
					return Float2ObjectOpenCustomHashMap.this.new ValueIterator();
				}

				public int size() {
					return Float2ObjectOpenCustomHashMap.this.size;
				}

				public boolean contains(Object v) {
					return Float2ObjectOpenCustomHashMap.this.containsValue(v);
				}

				public void clear() {
					Float2ObjectOpenCustomHashMap.this.clear();
				}

				public void forEach(Consumer<? super V> consumer) {
					if (Float2ObjectOpenCustomHashMap.this.containsNullKey) {
						consumer.accept(Float2ObjectOpenCustomHashMap.this.value[Float2ObjectOpenCustomHashMap.this.n]);
					}

					int pos = Float2ObjectOpenCustomHashMap.this.n;

					while (pos-- != 0) {
						if (Float.floatToIntBits(Float2ObjectOpenCustomHashMap.this.key[pos]) != 0) {
							consumer.accept(Float2ObjectOpenCustomHashMap.this.value[pos]);
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

	public Float2ObjectOpenCustomHashMap<V> clone() {
		Float2ObjectOpenCustomHashMap<V> c;
		try {
			c = (Float2ObjectOpenCustomHashMap<V>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (float[])this.key.clone();
		c.value = (V[])((Object[])this.value.clone());
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
		Float2ObjectOpenCustomHashMap<V>.MapIterator i = new Float2ObjectOpenCustomHashMap.MapIterator();
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
		int i = this.size;

		while (i-- != 0) {
			float k = s.readFloat();
			V v = (V)s.readObject();
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

	private class EntryIterator
		extends Float2ObjectOpenCustomHashMap<V>.MapIterator
		implements ObjectIterator<it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> {
		private Float2ObjectOpenCustomHashMap<V>.MapEntry entry;

		private EntryIterator() {
		}

		public Float2ObjectOpenCustomHashMap<V>.MapEntry next() {
			return this.entry = Float2ObjectOpenCustomHashMap.this.new MapEntry(this.nextEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator
		extends Float2ObjectOpenCustomHashMap<V>.MapIterator
		implements ObjectIterator<it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> {
		private final Float2ObjectOpenCustomHashMap<V>.MapEntry entry = Float2ObjectOpenCustomHashMap.this.new MapEntry();

		private FastEntryIterator() {
		}

		public Float2ObjectOpenCustomHashMap<V>.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Float2ObjectOpenCustomHashMap<V>.MapIterator implements FloatIterator {
		public KeyIterator() {
		}

		@Override
		public float nextFloat() {
			return Float2ObjectOpenCustomHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractFloatSet {
		private KeySet() {
		}

		@Override
		public FloatIterator iterator() {
			return Float2ObjectOpenCustomHashMap.this.new KeyIterator();
		}

		@Override
		public void forEach(DoubleConsumer consumer) {
			if (Float2ObjectOpenCustomHashMap.this.containsNullKey) {
				consumer.accept((double)Float2ObjectOpenCustomHashMap.this.key[Float2ObjectOpenCustomHashMap.this.n]);
			}

			int pos = Float2ObjectOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				float k = Float2ObjectOpenCustomHashMap.this.key[pos];
				if (Float.floatToIntBits(k) != 0) {
					consumer.accept((double)k);
				}
			}
		}

		public int size() {
			return Float2ObjectOpenCustomHashMap.this.size;
		}

		@Override
		public boolean contains(float k) {
			return Float2ObjectOpenCustomHashMap.this.containsKey(k);
		}

		@Override
		public boolean remove(float k) {
			int oldSize = Float2ObjectOpenCustomHashMap.this.size;
			Float2ObjectOpenCustomHashMap.this.remove(k);
			return Float2ObjectOpenCustomHashMap.this.size != oldSize;
		}

		public void clear() {
			Float2ObjectOpenCustomHashMap.this.clear();
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
			return Float2ObjectOpenCustomHashMap.this.key[this.index];
		}

		public V getValue() {
			return Float2ObjectOpenCustomHashMap.this.value[this.index];
		}

		public V setValue(V v) {
			V oldValue = Float2ObjectOpenCustomHashMap.this.value[this.index];
			Float2ObjectOpenCustomHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Float getKey() {
			return Float2ObjectOpenCustomHashMap.this.key[this.index];
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<Float, V> e = (java.util.Map.Entry<Float, V>)o;
				return Float2ObjectOpenCustomHashMap.this.strategy.equals(Float2ObjectOpenCustomHashMap.this.key[this.index], (Float)e.getKey())
					&& Objects.equals(Float2ObjectOpenCustomHashMap.this.value[this.index], e.getValue());
			}
		}

		public int hashCode() {
			return Float2ObjectOpenCustomHashMap.this.strategy.hashCode(Float2ObjectOpenCustomHashMap.this.key[this.index])
				^ (Float2ObjectOpenCustomHashMap.this.value[this.index] == null ? 0 : Float2ObjectOpenCustomHashMap.this.value[this.index].hashCode());
		}

		public String toString() {
			return Float2ObjectOpenCustomHashMap.this.key[this.index] + "=>" + Float2ObjectOpenCustomHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> implements FastEntrySet<V> {
		private MapEntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> iterator() {
			return Float2ObjectOpenCustomHashMap.this.new EntryIterator();
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> fastIterator() {
			return Float2ObjectOpenCustomHashMap.this.new FastEntryIterator();
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() != null && e.getKey() instanceof Float) {
					float k = (Float)e.getKey();
					V v = (V)e.getValue();
					if (Float2ObjectOpenCustomHashMap.this.strategy.equals(k, 0.0F)) {
						return Float2ObjectOpenCustomHashMap.this.containsNullKey
							&& Objects.equals(Float2ObjectOpenCustomHashMap.this.value[Float2ObjectOpenCustomHashMap.this.n], v);
					} else {
						float[] key = Float2ObjectOpenCustomHashMap.this.key;
						float curr;
						int pos;
						if (Float.floatToIntBits(
								curr = key[pos = HashCommon.mix(Float2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ObjectOpenCustomHashMap.this.mask]
							)
							== 0) {
							return false;
						} else if (Float2ObjectOpenCustomHashMap.this.strategy.equals(k, curr)) {
							return Objects.equals(Float2ObjectOpenCustomHashMap.this.value[pos], v);
						} else {
							while (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ObjectOpenCustomHashMap.this.mask]) != 0) {
								if (Float2ObjectOpenCustomHashMap.this.strategy.equals(k, curr)) {
									return Objects.equals(Float2ObjectOpenCustomHashMap.this.value[pos], v);
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
					if (Float2ObjectOpenCustomHashMap.this.strategy.equals(k, 0.0F)) {
						if (Float2ObjectOpenCustomHashMap.this.containsNullKey
							&& Objects.equals(Float2ObjectOpenCustomHashMap.this.value[Float2ObjectOpenCustomHashMap.this.n], v)) {
							Float2ObjectOpenCustomHashMap.this.removeNullEntry();
							return true;
						} else {
							return false;
						}
					} else {
						float[] key = Float2ObjectOpenCustomHashMap.this.key;
						float curr;
						int pos;
						if (Float.floatToIntBits(
								curr = key[pos = HashCommon.mix(Float2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ObjectOpenCustomHashMap.this.mask]
							)
							== 0) {
							return false;
						} else if (Float2ObjectOpenCustomHashMap.this.strategy.equals(curr, k)) {
							if (Objects.equals(Float2ObjectOpenCustomHashMap.this.value[pos], v)) {
								Float2ObjectOpenCustomHashMap.this.removeEntry(pos);
								return true;
							} else {
								return false;
							}
						} else {
							while (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ObjectOpenCustomHashMap.this.mask]) != 0) {
								if (Float2ObjectOpenCustomHashMap.this.strategy.equals(curr, k) && Objects.equals(Float2ObjectOpenCustomHashMap.this.value[pos], v)) {
									Float2ObjectOpenCustomHashMap.this.removeEntry(pos);
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
			return Float2ObjectOpenCustomHashMap.this.size;
		}

		public void clear() {
			Float2ObjectOpenCustomHashMap.this.clear();
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> consumer) {
			if (Float2ObjectOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(
					new BasicEntry(
						Float2ObjectOpenCustomHashMap.this.key[Float2ObjectOpenCustomHashMap.this.n],
						Float2ObjectOpenCustomHashMap.this.value[Float2ObjectOpenCustomHashMap.this.n]
					)
				);
			}

			int pos = Float2ObjectOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Float.floatToIntBits(Float2ObjectOpenCustomHashMap.this.key[pos]) != 0) {
					consumer.accept(new BasicEntry(Float2ObjectOpenCustomHashMap.this.key[pos], Float2ObjectOpenCustomHashMap.this.value[pos]));
				}
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.floats.Float2ObjectMap.Entry<V>> consumer) {
			BasicEntry<V> entry = new BasicEntry<>();
			if (Float2ObjectOpenCustomHashMap.this.containsNullKey) {
				entry.key = Float2ObjectOpenCustomHashMap.this.key[Float2ObjectOpenCustomHashMap.this.n];
				entry.value = Float2ObjectOpenCustomHashMap.this.value[Float2ObjectOpenCustomHashMap.this.n];
				consumer.accept(entry);
			}

			int pos = Float2ObjectOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Float.floatToIntBits(Float2ObjectOpenCustomHashMap.this.key[pos]) != 0) {
					entry.key = Float2ObjectOpenCustomHashMap.this.key[pos];
					entry.value = Float2ObjectOpenCustomHashMap.this.value[pos];
					consumer.accept(entry);
				}
			}
		}
	}

	private class MapIterator {
		int pos = Float2ObjectOpenCustomHashMap.this.n;
		int last = -1;
		int c = Float2ObjectOpenCustomHashMap.this.size;
		boolean mustReturnNullKey = Float2ObjectOpenCustomHashMap.this.containsNullKey;
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
					return this.last = Float2ObjectOpenCustomHashMap.this.n;
				} else {
					float[] key = Float2ObjectOpenCustomHashMap.this.key;

					while (--this.pos >= 0) {
						if (Float.floatToIntBits(key[this.pos]) != 0) {
							return this.last = this.pos;
						}
					}

					this.last = Integer.MIN_VALUE;
					float k = this.wrapped.getFloat(-this.pos - 1);
					int p = HashCommon.mix(Float2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ObjectOpenCustomHashMap.this.mask;

					while (!Float2ObjectOpenCustomHashMap.this.strategy.equals(k, key[p])) {
						p = p + 1 & Float2ObjectOpenCustomHashMap.this.mask;
					}

					return p;
				}
			}
		}

		private void shiftKeys(int pos) {
			float[] key = Float2ObjectOpenCustomHashMap.this.key;

			label38:
			while (true) {
				int last = pos;

				float curr;
				for (pos = pos + 1 & Float2ObjectOpenCustomHashMap.this.mask;
					Float.floatToIntBits(curr = key[pos]) != 0;
					pos = pos + 1 & Float2ObjectOpenCustomHashMap.this.mask
				) {
					int slot = HashCommon.mix(Float2ObjectOpenCustomHashMap.this.strategy.hashCode(curr)) & Float2ObjectOpenCustomHashMap.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new FloatArrayList(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						Float2ObjectOpenCustomHashMap.this.value[last] = Float2ObjectOpenCustomHashMap.this.value[pos];
						continue label38;
					}
				}

				key[last] = 0.0F;
				Float2ObjectOpenCustomHashMap.this.value[last] = null;
				return;
			}
		}

		public void remove() {
			if (this.last == -1) {
				throw new IllegalStateException();
			} else {
				if (this.last == Float2ObjectOpenCustomHashMap.this.n) {
					Float2ObjectOpenCustomHashMap.this.containsNullKey = false;
					Float2ObjectOpenCustomHashMap.this.value[Float2ObjectOpenCustomHashMap.this.n] = null;
				} else {
					if (this.pos < 0) {
						Float2ObjectOpenCustomHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				Float2ObjectOpenCustomHashMap.this.size--;
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

	private final class ValueIterator extends Float2ObjectOpenCustomHashMap<V>.MapIterator implements ObjectIterator<V> {
		public ValueIterator() {
		}

		public V next() {
			return Float2ObjectOpenCustomHashMap.this.value[this.nextEntry()];
		}
	}
}
