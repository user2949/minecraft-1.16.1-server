package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.AbstractChar2FloatMap.BasicEntry;
import it.unimi.dsi.fastutil.chars.Char2FloatMap.FastEntrySet;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
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
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;

public class Char2FloatOpenCustomHashMap extends AbstractChar2FloatMap implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient char[] key;
	protected transient float[] value;
	protected transient int mask;
	protected transient boolean containsNullKey;
	protected CharHash.Strategy strategy;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;
	protected transient FastEntrySet entries;
	protected transient CharSet keys;
	protected transient FloatCollection values;

	public Char2FloatOpenCustomHashMap(int expected, float f, CharHash.Strategy strategy) {
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
			this.key = new char[this.n + 1];
			this.value = new float[this.n + 1];
		}
	}

	public Char2FloatOpenCustomHashMap(int expected, CharHash.Strategy strategy) {
		this(expected, 0.75F, strategy);
	}

	public Char2FloatOpenCustomHashMap(CharHash.Strategy strategy) {
		this(16, 0.75F, strategy);
	}

	public Char2FloatOpenCustomHashMap(Map<? extends Character, ? extends Float> m, float f, CharHash.Strategy strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Char2FloatOpenCustomHashMap(Map<? extends Character, ? extends Float> m, CharHash.Strategy strategy) {
		this(m, 0.75F, strategy);
	}

	public Char2FloatOpenCustomHashMap(Char2FloatMap m, float f, CharHash.Strategy strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Char2FloatOpenCustomHashMap(Char2FloatMap m, CharHash.Strategy strategy) {
		this(m, 0.75F, strategy);
	}

	public Char2FloatOpenCustomHashMap(char[] k, float[] v, float f, CharHash.Strategy strategy) {
		this(k.length, f, strategy);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Char2FloatOpenCustomHashMap(char[] k, float[] v, CharHash.Strategy strategy) {
		this(k, v, 0.75F, strategy);
	}

	public CharHash.Strategy strategy() {
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
		float oldValue = this.value[this.n];
		this.size--;
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	@Override
	public void putAll(Map<? extends Character, ? extends Float> m) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(m.size());
		} else {
			this.tryCapacity((long)(this.size() + m.size()));
		}

		super.putAll(m);
	}

	private int find(char k) {
		if (this.strategy.equals(k, '\u0000')) {
			return this.containsNullKey ? this.n : -(this.n + 1);
		} else {
			char[] key = this.key;
			char curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return -(pos + 1);
			} else if (this.strategy.equals(k, curr)) {
				return pos;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr)) {
						return pos;
					}
				}

				return -(pos + 1);
			}
		}
	}

	private void insert(int pos, char k, float v) {
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
	public float put(char k, float v) {
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

	public float addTo(char k, float incr) {
		int pos;
		if (this.strategy.equals(k, '\u0000')) {
			if (this.containsNullKey) {
				return this.addToValue(this.n, incr);
			}

			pos = this.n;
			this.containsNullKey = true;
		} else {
			char[] key = this.key;
			char curr;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
				if (this.strategy.equals(curr, k)) {
					return this.addToValue(pos, incr);
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
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
		char[] key = this.key;

		label30:
		while (true) {
			int last = pos;

			char curr;
			for (pos = pos + 1 & this.mask; (curr = key[pos]) != 0; pos = pos + 1 & this.mask) {
				int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
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
	public float remove(char k) {
		if (this.strategy.equals(k, '\u0000')) {
			return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
		} else {
			char[] key = this.key;
			char curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return this.defRetValue;
			} else if (this.strategy.equals(k, curr)) {
				return this.removeEntry(pos);
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr)) {
						return this.removeEntry(pos);
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public float get(char k) {
		if (this.strategy.equals(k, '\u0000')) {
			return this.containsNullKey ? this.value[this.n] : this.defRetValue;
		} else {
			char[] key = this.key;
			char curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return this.defRetValue;
			} else if (this.strategy.equals(k, curr)) {
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr)) {
						return this.value[pos];
					}
				}

				return this.defRetValue;
			}
		}
	}

	@Override
	public boolean containsKey(char k) {
		if (this.strategy.equals(k, '\u0000')) {
			return this.containsNullKey;
		} else {
			char[] key = this.key;
			char curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return false;
			} else if (this.strategy.equals(k, curr)) {
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr)) {
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
		char[] key = this.key;
		if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v)) {
			return true;
		} else {
			int i = this.n;

			while (i-- != 0) {
				if (key[i] != 0 && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v)) {
					return true;
				}
			}

			return false;
		}
	}

	@Override
	public float getOrDefault(char k, float defaultValue) {
		if (this.strategy.equals(k, '\u0000')) {
			return this.containsNullKey ? this.value[this.n] : defaultValue;
		} else {
			char[] key = this.key;
			char curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return defaultValue;
			} else if (this.strategy.equals(k, curr)) {
				return this.value[pos];
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr)) {
						return this.value[pos];
					}
				}

				return defaultValue;
			}
		}
	}

	@Override
	public float putIfAbsent(char k, float v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(char k, float v) {
		if (this.strategy.equals(k, '\u0000')) {
			if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
				this.removeNullEntry();
				return true;
			} else {
				return false;
			}
		} else {
			char[] key = this.key;
			char curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) {
				return false;
			} else if (this.strategy.equals(k, curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
				this.removeEntry(pos);
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (this.strategy.equals(k, curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
						this.removeEntry(pos);
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean replace(char k, float oldValue, float v) {
		int pos = this.find(k);
		if (pos >= 0 && Float.floatToIntBits(oldValue) == Float.floatToIntBits(this.value[pos])) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public float replace(char k, float v) {
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
	public float computeIfAbsent(char k, IntToDoubleFunction mappingFunction) {
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
	public float computeIfAbsentNullable(char k, IntFunction<? extends Float> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			Float newValue = (Float)mappingFunction.apply(k);
			if (newValue == null) {
				return this.defRetValue;
			} else {
				float v = newValue;
				this.insert(-pos - 1, k, v);
				return v;
			}
		}
	}

	@Override
	public float computeIfPresent(char k, BiFunction<? super Character, ? super Float, ? extends Float> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			Float newValue = (Float)remappingFunction.apply(k, this.value[pos]);
			if (newValue == null) {
				if (this.strategy.equals(k, '\u0000')) {
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
	public float compute(char k, BiFunction<? super Character, ? super Float, ? extends Float> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		Float newValue = (Float)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
		if (newValue == null) {
			if (pos >= 0) {
				if (this.strategy.equals(k, '\u0000')) {
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
	public float merge(char k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return v;
		} else {
			Float newValue = (Float)remappingFunction.apply(this.value[pos], v);
			if (newValue == null) {
				if (this.strategy.equals(k, '\u0000')) {
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
			Arrays.fill(this.key, '\u0000');
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

	public FastEntrySet char2FloatEntrySet() {
		if (this.entries == null) {
			this.entries = new Char2FloatOpenCustomHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public CharSet keySet() {
		if (this.keys == null) {
			this.keys = new Char2FloatOpenCustomHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public FloatCollection values() {
		if (this.values == null) {
			this.values = new AbstractFloatCollection() {
				@Override
				public FloatIterator iterator() {
					return Char2FloatOpenCustomHashMap.this.new ValueIterator();
				}

				public int size() {
					return Char2FloatOpenCustomHashMap.this.size;
				}

				@Override
				public boolean contains(float v) {
					return Char2FloatOpenCustomHashMap.this.containsValue(v);
				}

				public void clear() {
					Char2FloatOpenCustomHashMap.this.clear();
				}

				@Override
				public void forEach(DoubleConsumer consumer) {
					if (Char2FloatOpenCustomHashMap.this.containsNullKey) {
						consumer.accept((double)Char2FloatOpenCustomHashMap.this.value[Char2FloatOpenCustomHashMap.this.n]);
					}

					int pos = Char2FloatOpenCustomHashMap.this.n;

					while (pos-- != 0) {
						if (Char2FloatOpenCustomHashMap.this.key[pos] != 0) {
							consumer.accept((double)Char2FloatOpenCustomHashMap.this.value[pos]);
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
		char[] key = this.key;
		float[] value = this.value;
		int mask = newN - 1;
		char[] newKey = new char[newN + 1];
		float[] newValue = new float[newN + 1];
		int i = this.n;
		int j = this.realSize();

		while (j-- != 0) {
			while (key[--i] == 0) {
			}

			int pos;
			if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0) {
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

	public Char2FloatOpenCustomHashMap clone() {
		Char2FloatOpenCustomHashMap c;
		try {
			c = (Char2FloatOpenCustomHashMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (char[])this.key.clone();
		c.value = (float[])this.value.clone();
		c.strategy = this.strategy;
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

			t = this.strategy.hashCode(this.key[i]);
			t ^= HashCommon.float2int(this.value[i]);
			h += t;
		}

		if (this.containsNullKey) {
			h += HashCommon.float2int(this.value[this.n]);
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		char[] key = this.key;
		float[] value = this.value;
		Char2FloatOpenCustomHashMap.MapIterator i = new Char2FloatOpenCustomHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeChar(key[e]);
			s.writeFloat(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		char[] key = this.key = new char[this.n + 1];
		float[] value = this.value = new float[this.n + 1];
		int i = this.size;

		while (i-- != 0) {
			char k = s.readChar();
			float v = s.readFloat();
			int pos;
			if (this.strategy.equals(k, '\u0000')) {
				pos = this.n;
				this.containsNullKey = true;
			} else {
				pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;

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

	private class EntryIterator extends Char2FloatOpenCustomHashMap.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.chars.Char2FloatMap.Entry> {
		private Char2FloatOpenCustomHashMap.MapEntry entry;

		private EntryIterator() {
		}

		public Char2FloatOpenCustomHashMap.MapEntry next() {
			return this.entry = Char2FloatOpenCustomHashMap.this.new MapEntry(this.nextEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator extends Char2FloatOpenCustomHashMap.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.chars.Char2FloatMap.Entry> {
		private final Char2FloatOpenCustomHashMap.MapEntry entry = Char2FloatOpenCustomHashMap.this.new MapEntry();

		private FastEntryIterator() {
		}

		public Char2FloatOpenCustomHashMap.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Char2FloatOpenCustomHashMap.MapIterator implements CharIterator {
		public KeyIterator() {
		}

		@Override
		public char nextChar() {
			return Char2FloatOpenCustomHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractCharSet {
		private KeySet() {
		}

		@Override
		public CharIterator iterator() {
			return Char2FloatOpenCustomHashMap.this.new KeyIterator();
		}

		@Override
		public void forEach(IntConsumer consumer) {
			if (Char2FloatOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(Char2FloatOpenCustomHashMap.this.key[Char2FloatOpenCustomHashMap.this.n]);
			}

			int pos = Char2FloatOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				char k = Char2FloatOpenCustomHashMap.this.key[pos];
				if (k != 0) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Char2FloatOpenCustomHashMap.this.size;
		}

		@Override
		public boolean contains(char k) {
			return Char2FloatOpenCustomHashMap.this.containsKey(k);
		}

		@Override
		public boolean remove(char k) {
			int oldSize = Char2FloatOpenCustomHashMap.this.size;
			Char2FloatOpenCustomHashMap.this.remove(k);
			return Char2FloatOpenCustomHashMap.this.size != oldSize;
		}

		public void clear() {
			Char2FloatOpenCustomHashMap.this.clear();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.chars.Char2FloatMap.Entry, java.util.Map.Entry<Character, Float> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		@Override
		public char getCharKey() {
			return Char2FloatOpenCustomHashMap.this.key[this.index];
		}

		@Override
		public float getFloatValue() {
			return Char2FloatOpenCustomHashMap.this.value[this.index];
		}

		@Override
		public float setValue(float v) {
			float oldValue = Char2FloatOpenCustomHashMap.this.value[this.index];
			Char2FloatOpenCustomHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Character getKey() {
			return Char2FloatOpenCustomHashMap.this.key[this.index];
		}

		@Deprecated
		@Override
		public Float getValue() {
			return Char2FloatOpenCustomHashMap.this.value[this.index];
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
				java.util.Map.Entry<Character, Float> e = (java.util.Map.Entry<Character, Float>)o;
				return Char2FloatOpenCustomHashMap.this.strategy.equals(Char2FloatOpenCustomHashMap.this.key[this.index], (Character)e.getKey())
					&& Float.floatToIntBits(Char2FloatOpenCustomHashMap.this.value[this.index]) == Float.floatToIntBits((Float)e.getValue());
			}
		}

		public int hashCode() {
			return Char2FloatOpenCustomHashMap.this.strategy.hashCode(Char2FloatOpenCustomHashMap.this.key[this.index])
				^ HashCommon.float2int(Char2FloatOpenCustomHashMap.this.value[this.index]);
		}

		public String toString() {
			return Char2FloatOpenCustomHashMap.this.key[this.index] + "=>" + Char2FloatOpenCustomHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.chars.Char2FloatMap.Entry> implements FastEntrySet {
		private MapEntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.chars.Char2FloatMap.Entry> iterator() {
			return Char2FloatOpenCustomHashMap.this.new EntryIterator();
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.chars.Char2FloatMap.Entry> fastIterator() {
			return Char2FloatOpenCustomHashMap.this.new FastEntryIterator();
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Character)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Float) {
					char k = (Character)e.getKey();
					float v = (Float)e.getValue();
					if (Char2FloatOpenCustomHashMap.this.strategy.equals(k, '\u0000')) {
						return Char2FloatOpenCustomHashMap.this.containsNullKey
							&& Float.floatToIntBits(Char2FloatOpenCustomHashMap.this.value[Char2FloatOpenCustomHashMap.this.n]) == Float.floatToIntBits(v);
					} else {
						char[] key = Char2FloatOpenCustomHashMap.this.key;
						char curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(Char2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Char2FloatOpenCustomHashMap.this.mask]) == 0) {
							return false;
						} else if (Char2FloatOpenCustomHashMap.this.strategy.equals(k, curr)) {
							return Float.floatToIntBits(Char2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v);
						} else {
							while ((curr = key[pos = pos + 1 & Char2FloatOpenCustomHashMap.this.mask]) != 0) {
								if (Char2FloatOpenCustomHashMap.this.strategy.equals(k, curr)) {
									return Float.floatToIntBits(Char2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v);
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
				if (e.getKey() != null && e.getKey() instanceof Character) {
					if (e.getValue() != null && e.getValue() instanceof Float) {
						char k = (Character)e.getKey();
						float v = (Float)e.getValue();
						if (Char2FloatOpenCustomHashMap.this.strategy.equals(k, '\u0000')) {
							if (Char2FloatOpenCustomHashMap.this.containsNullKey
								&& Float.floatToIntBits(Char2FloatOpenCustomHashMap.this.value[Char2FloatOpenCustomHashMap.this.n]) == Float.floatToIntBits(v)) {
								Char2FloatOpenCustomHashMap.this.removeNullEntry();
								return true;
							} else {
								return false;
							}
						} else {
							char[] key = Char2FloatOpenCustomHashMap.this.key;
							char curr;
							int pos;
							if ((curr = key[pos = HashCommon.mix(Char2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Char2FloatOpenCustomHashMap.this.mask]) == 0) {
								return false;
							} else if (Char2FloatOpenCustomHashMap.this.strategy.equals(curr, k)) {
								if (Float.floatToIntBits(Char2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
									Char2FloatOpenCustomHashMap.this.removeEntry(pos);
									return true;
								} else {
									return false;
								}
							} else {
								while ((curr = key[pos = pos + 1 & Char2FloatOpenCustomHashMap.this.mask]) != 0) {
									if (Char2FloatOpenCustomHashMap.this.strategy.equals(curr, k)
										&& Float.floatToIntBits(Char2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
										Char2FloatOpenCustomHashMap.this.removeEntry(pos);
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
			return Char2FloatOpenCustomHashMap.this.size;
		}

		public void clear() {
			Char2FloatOpenCustomHashMap.this.clear();
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.chars.Char2FloatMap.Entry> consumer) {
			if (Char2FloatOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(
					new BasicEntry(
						Char2FloatOpenCustomHashMap.this.key[Char2FloatOpenCustomHashMap.this.n], Char2FloatOpenCustomHashMap.this.value[Char2FloatOpenCustomHashMap.this.n]
					)
				);
			}

			int pos = Char2FloatOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Char2FloatOpenCustomHashMap.this.key[pos] != 0) {
					consumer.accept(new BasicEntry(Char2FloatOpenCustomHashMap.this.key[pos], Char2FloatOpenCustomHashMap.this.value[pos]));
				}
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.chars.Char2FloatMap.Entry> consumer) {
			BasicEntry entry = new BasicEntry();
			if (Char2FloatOpenCustomHashMap.this.containsNullKey) {
				entry.key = Char2FloatOpenCustomHashMap.this.key[Char2FloatOpenCustomHashMap.this.n];
				entry.value = Char2FloatOpenCustomHashMap.this.value[Char2FloatOpenCustomHashMap.this.n];
				consumer.accept(entry);
			}

			int pos = Char2FloatOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Char2FloatOpenCustomHashMap.this.key[pos] != 0) {
					entry.key = Char2FloatOpenCustomHashMap.this.key[pos];
					entry.value = Char2FloatOpenCustomHashMap.this.value[pos];
					consumer.accept(entry);
				}
			}
		}
	}

	private class MapIterator {
		int pos = Char2FloatOpenCustomHashMap.this.n;
		int last = -1;
		int c = Char2FloatOpenCustomHashMap.this.size;
		boolean mustReturnNullKey = Char2FloatOpenCustomHashMap.this.containsNullKey;
		CharArrayList wrapped;

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
					return this.last = Char2FloatOpenCustomHashMap.this.n;
				} else {
					char[] key = Char2FloatOpenCustomHashMap.this.key;

					while (--this.pos >= 0) {
						if (key[this.pos] != 0) {
							return this.last = this.pos;
						}
					}

					this.last = Integer.MIN_VALUE;
					char k = this.wrapped.getChar(-this.pos - 1);
					int p = HashCommon.mix(Char2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Char2FloatOpenCustomHashMap.this.mask;

					while (!Char2FloatOpenCustomHashMap.this.strategy.equals(k, key[p])) {
						p = p + 1 & Char2FloatOpenCustomHashMap.this.mask;
					}

					return p;
				}
			}
		}

		private void shiftKeys(int pos) {
			char[] key = Char2FloatOpenCustomHashMap.this.key;

			label38:
			while (true) {
				int last = pos;

				char curr;
				for (pos = pos + 1 & Char2FloatOpenCustomHashMap.this.mask; (curr = key[pos]) != 0; pos = pos + 1 & Char2FloatOpenCustomHashMap.this.mask) {
					int slot = HashCommon.mix(Char2FloatOpenCustomHashMap.this.strategy.hashCode(curr)) & Char2FloatOpenCustomHashMap.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new CharArrayList(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						Char2FloatOpenCustomHashMap.this.value[last] = Char2FloatOpenCustomHashMap.this.value[pos];
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
				if (this.last == Char2FloatOpenCustomHashMap.this.n) {
					Char2FloatOpenCustomHashMap.this.containsNullKey = false;
				} else {
					if (this.pos < 0) {
						Char2FloatOpenCustomHashMap.this.remove(this.wrapped.getChar(-this.pos - 1));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				Char2FloatOpenCustomHashMap.this.size--;
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

	private final class ValueIterator extends Char2FloatOpenCustomHashMap.MapIterator implements FloatIterator {
		public ValueIterator() {
		}

		@Override
		public float nextFloat() {
			return Char2FloatOpenCustomHashMap.this.value[this.nextEntry()];
		}
	}
}
