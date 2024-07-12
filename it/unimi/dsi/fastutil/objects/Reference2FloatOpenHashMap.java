package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.objects.AbstractReference2FloatMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap.FastEntrySet;
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
import java.util.function.ToDoubleFunction;

public class Reference2FloatOpenHashMap<K> extends AbstractReference2FloatMap<K> implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient K[] key;
	protected transient float[] value;
	protected transient int mask;
	protected transient boolean containsNullKey;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;
	protected transient FastEntrySet<K> entries;
	protected transient ReferenceSet<K> keys;
	protected transient FloatCollection values;

	public Reference2FloatOpenHashMap(int expected, float f) {
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
		}
	}

	public Reference2FloatOpenHashMap(int expected) {
		this(expected, 0.75F);
	}

	public Reference2FloatOpenHashMap() {
		this(16, 0.75F);
	}

	public Reference2FloatOpenHashMap(Map<? extends K, ? extends Float> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Reference2FloatOpenHashMap(Map<? extends K, ? extends Float> m) {
		this(m, 0.75F);
	}

	public Reference2FloatOpenHashMap(Reference2FloatMap<K> m, float f) {
		this(m.size(), f);
		this.putAll(m);
	}

	public Reference2FloatOpenHashMap(Reference2FloatMap<K> m) {
		this(m, 0.75F);
	}

	public Reference2FloatOpenHashMap(K[] k, float[] v, float f) {
		this(k.length, f);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Reference2FloatOpenHashMap(K[] k, float[] v) {
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

	public FastEntrySet<K> reference2FloatEntrySet() {
		if (this.entries == null) {
			this.entries = new Reference2FloatOpenHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public ReferenceSet<K> keySet() {
		if (this.keys == null) {
			this.keys = new Reference2FloatOpenHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public FloatCollection values() {
		if (this.values == null) {
			this.values = new AbstractFloatCollection() {
				@Override
				public FloatIterator iterator() {
					return Reference2FloatOpenHashMap.this.new ValueIterator();
				}

				public int size() {
					return Reference2FloatOpenHashMap.this.size;
				}

				@Override
				public boolean contains(float v) {
					return Reference2FloatOpenHashMap.this.containsValue(v);
				}

				public void clear() {
					Reference2FloatOpenHashMap.this.clear();
				}

				@Override
				public void forEach(DoubleConsumer consumer) {
					if (Reference2FloatOpenHashMap.this.containsNullKey) {
						consumer.accept((double)Reference2FloatOpenHashMap.this.value[Reference2FloatOpenHashMap.this.n]);
					}

					int pos = Reference2FloatOpenHashMap.this.n;

					while (pos-- != 0) {
						if (Reference2FloatOpenHashMap.this.key[pos] != null) {
							consumer.accept((double)Reference2FloatOpenHashMap.this.value[pos]);
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
		int i = this.n;
		int j = this.realSize();

		while (j-- != 0) {
			while (key[--i] == null) {
			}

			int pos;
			if (newKey[pos = HashCommon.mix(System.identityHashCode(key[i])) & mask] != null) {
				while (newKey[pos = pos + 1 & mask] != null) {
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

	public Reference2FloatOpenHashMap<K> clone() {
		Reference2FloatOpenHashMap<K> c;
		try {
			c = (Reference2FloatOpenHashMap<K>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (K[])((Object[])this.key.clone());
		c.value = (float[])this.value.clone();
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
		Reference2FloatOpenHashMap<K>.MapIterator i = new Reference2FloatOpenHashMap.MapIterator();
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
		}
	}

	private void checkTable() {
	}

	private class EntryIterator
		extends Reference2FloatOpenHashMap<K>.MapIterator
		implements ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> {
		private Reference2FloatOpenHashMap<K>.MapEntry entry;

		private EntryIterator() {
		}

		public Reference2FloatOpenHashMap<K>.MapEntry next() {
			return this.entry = Reference2FloatOpenHashMap.this.new MapEntry(this.nextEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator
		extends Reference2FloatOpenHashMap<K>.MapIterator
		implements ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> {
		private final Reference2FloatOpenHashMap<K>.MapEntry entry = Reference2FloatOpenHashMap.this.new MapEntry();

		private FastEntryIterator() {
		}

		public Reference2FloatOpenHashMap<K>.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Reference2FloatOpenHashMap<K>.MapIterator implements ObjectIterator<K> {
		public KeyIterator() {
		}

		public K next() {
			return Reference2FloatOpenHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractReferenceSet<K> {
		private KeySet() {
		}

		@Override
		public ObjectIterator<K> iterator() {
			return Reference2FloatOpenHashMap.this.new KeyIterator();
		}

		public void forEach(Consumer<? super K> consumer) {
			if (Reference2FloatOpenHashMap.this.containsNullKey) {
				consumer.accept(Reference2FloatOpenHashMap.this.key[Reference2FloatOpenHashMap.this.n]);
			}

			int pos = Reference2FloatOpenHashMap.this.n;

			while (pos-- != 0) {
				K k = Reference2FloatOpenHashMap.this.key[pos];
				if (k != null) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Reference2FloatOpenHashMap.this.size;
		}

		public boolean contains(Object k) {
			return Reference2FloatOpenHashMap.this.containsKey(k);
		}

		public boolean remove(Object k) {
			int oldSize = Reference2FloatOpenHashMap.this.size;
			Reference2FloatOpenHashMap.this.removeFloat(k);
			return Reference2FloatOpenHashMap.this.size != oldSize;
		}

		public void clear() {
			Reference2FloatOpenHashMap.this.clear();
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
			return Reference2FloatOpenHashMap.this.key[this.index];
		}

		@Override
		public float getFloatValue() {
			return Reference2FloatOpenHashMap.this.value[this.index];
		}

		@Override
		public float setValue(float v) {
			float oldValue = Reference2FloatOpenHashMap.this.value[this.index];
			Reference2FloatOpenHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Float getValue() {
			return Reference2FloatOpenHashMap.this.value[this.index];
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
				return Reference2FloatOpenHashMap.this.key[this.index] == e.getKey()
					&& Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[this.index]) == Float.floatToIntBits((Float)e.getValue());
			}
		}

		public int hashCode() {
			return System.identityHashCode(Reference2FloatOpenHashMap.this.key[this.index]) ^ HashCommon.float2int(Reference2FloatOpenHashMap.this.value[this.index]);
		}

		public String toString() {
			return Reference2FloatOpenHashMap.this.key[this.index] + "=>" + Reference2FloatOpenHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> implements FastEntrySet<K> {
		private MapEntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> iterator() {
			return Reference2FloatOpenHashMap.this.new EntryIterator();
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> fastIterator() {
			return Reference2FloatOpenHashMap.this.new FastEntryIterator();
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
						return Reference2FloatOpenHashMap.this.containsNullKey
							&& Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[Reference2FloatOpenHashMap.this.n]) == Float.floatToIntBits(v);
					} else {
						K[] key = Reference2FloatOpenHashMap.this.key;
						K curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2FloatOpenHashMap.this.mask]) == null) {
							return false;
						} else if (k == curr) {
							return Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v);
						} else {
							while ((curr = key[pos = pos + 1 & Reference2FloatOpenHashMap.this.mask]) != null) {
								if (k == curr) {
									return Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v);
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
						if (Reference2FloatOpenHashMap.this.containsNullKey
							&& Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[Reference2FloatOpenHashMap.this.n]) == Float.floatToIntBits(v)) {
							Reference2FloatOpenHashMap.this.removeNullEntry();
							return true;
						} else {
							return false;
						}
					} else {
						K[] key = Reference2FloatOpenHashMap.this.key;
						K curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2FloatOpenHashMap.this.mask]) == null) {
							return false;
						} else if (curr == k) {
							if (Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
								Reference2FloatOpenHashMap.this.removeEntry(pos);
								return true;
							} else {
								return false;
							}
						} else {
							while ((curr = key[pos = pos + 1 & Reference2FloatOpenHashMap.this.mask]) != null) {
								if (curr == k && Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
									Reference2FloatOpenHashMap.this.removeEntry(pos);
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
			return Reference2FloatOpenHashMap.this.size;
		}

		public void clear() {
			Reference2FloatOpenHashMap.this.clear();
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> consumer) {
			if (Reference2FloatOpenHashMap.this.containsNullKey) {
				consumer.accept(
					new BasicEntry(
						Reference2FloatOpenHashMap.this.key[Reference2FloatOpenHashMap.this.n], Reference2FloatOpenHashMap.this.value[Reference2FloatOpenHashMap.this.n]
					)
				);
			}

			int pos = Reference2FloatOpenHashMap.this.n;

			while (pos-- != 0) {
				if (Reference2FloatOpenHashMap.this.key[pos] != null) {
					consumer.accept(new BasicEntry(Reference2FloatOpenHashMap.this.key[pos], Reference2FloatOpenHashMap.this.value[pos]));
				}
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry<K>> consumer) {
			BasicEntry<K> entry = new BasicEntry<>();
			if (Reference2FloatOpenHashMap.this.containsNullKey) {
				entry.key = Reference2FloatOpenHashMap.this.key[Reference2FloatOpenHashMap.this.n];
				entry.value = Reference2FloatOpenHashMap.this.value[Reference2FloatOpenHashMap.this.n];
				consumer.accept(entry);
			}

			int pos = Reference2FloatOpenHashMap.this.n;

			while (pos-- != 0) {
				if (Reference2FloatOpenHashMap.this.key[pos] != null) {
					entry.key = Reference2FloatOpenHashMap.this.key[pos];
					entry.value = Reference2FloatOpenHashMap.this.value[pos];
					consumer.accept(entry);
				}
			}
		}
	}

	private class MapIterator {
		int pos = Reference2FloatOpenHashMap.this.n;
		int last = -1;
		int c = Reference2FloatOpenHashMap.this.size;
		boolean mustReturnNullKey = Reference2FloatOpenHashMap.this.containsNullKey;
		ReferenceArrayList<K> wrapped;

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
					return this.last = Reference2FloatOpenHashMap.this.n;
				} else {
					K[] key = Reference2FloatOpenHashMap.this.key;

					while (--this.pos >= 0) {
						if (key[this.pos] != null) {
							return this.last = this.pos;
						}
					}

					this.last = Integer.MIN_VALUE;
					K k = this.wrapped.get(-this.pos - 1);
					int p = HashCommon.mix(System.identityHashCode(k)) & Reference2FloatOpenHashMap.this.mask;

					while (k != key[p]) {
						p = p + 1 & Reference2FloatOpenHashMap.this.mask;
					}

					return p;
				}
			}
		}

		private void shiftKeys(int pos) {
			K[] key = Reference2FloatOpenHashMap.this.key;

			label38:
			while (true) {
				int last = pos;

				K curr;
				for (pos = pos + 1 & Reference2FloatOpenHashMap.this.mask; (curr = key[pos]) != null; pos = pos + 1 & Reference2FloatOpenHashMap.this.mask) {
					int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2FloatOpenHashMap.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new ReferenceArrayList<>(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						Reference2FloatOpenHashMap.this.value[last] = Reference2FloatOpenHashMap.this.value[pos];
						continue label38;
					}
				}

				key[last] = null;
				return;
			}
		}

		public void remove() {
			if (this.last == -1) {
				throw new IllegalStateException();
			} else {
				if (this.last == Reference2FloatOpenHashMap.this.n) {
					Reference2FloatOpenHashMap.this.containsNullKey = false;
					Reference2FloatOpenHashMap.this.key[Reference2FloatOpenHashMap.this.n] = null;
				} else {
					if (this.pos < 0) {
						Reference2FloatOpenHashMap.this.removeFloat(this.wrapped.set(-this.pos - 1, null));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				Reference2FloatOpenHashMap.this.size--;
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

	private final class ValueIterator extends Reference2FloatOpenHashMap<K>.MapIterator implements FloatIterator {
		public ValueIterator() {
		}

		@Override
		public float nextFloat() {
			return Reference2FloatOpenHashMap.this.value[this.nextEntry()];
		}
	}
}
