package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.AbstractChar2ShortMap.BasicEntry;
import it.unimi.dsi.fastutil.chars.Char2ShortMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
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
import java.util.function.IntUnaryOperator;

public class Char2ShortOpenCustomHashMap extends AbstractChar2ShortMap implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient char[] key;
	protected transient short[] value;
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
	protected transient ShortCollection values;

	public Char2ShortOpenCustomHashMap(int expected, float f, CharHash.Strategy strategy) {
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
			this.value = new short[this.n + 1];
		}
	}

	public Char2ShortOpenCustomHashMap(int expected, CharHash.Strategy strategy) {
		this(expected, 0.75F, strategy);
	}

	public Char2ShortOpenCustomHashMap(CharHash.Strategy strategy) {
		this(16, 0.75F, strategy);
	}

	public Char2ShortOpenCustomHashMap(Map<? extends Character, ? extends Short> m, float f, CharHash.Strategy strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Char2ShortOpenCustomHashMap(Map<? extends Character, ? extends Short> m, CharHash.Strategy strategy) {
		this(m, 0.75F, strategy);
	}

	public Char2ShortOpenCustomHashMap(Char2ShortMap m, float f, CharHash.Strategy strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Char2ShortOpenCustomHashMap(Char2ShortMap m, CharHash.Strategy strategy) {
		this(m, 0.75F, strategy);
	}

	public Char2ShortOpenCustomHashMap(char[] k, short[] v, float f, CharHash.Strategy strategy) {
		this(k.length, f, strategy);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Char2ShortOpenCustomHashMap(char[] k, short[] v, CharHash.Strategy strategy) {
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

	private short removeEntry(int pos) {
		short oldValue = this.value[pos];
		this.size--;
		this.shiftKeys(pos);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	private short removeNullEntry() {
		this.containsNullKey = false;
		short oldValue = this.value[this.n];
		this.size--;
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return oldValue;
	}

	@Override
	public void putAll(Map<? extends Character, ? extends Short> m) {
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

	private void insert(int pos, char k, short v) {
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
	public short put(char k, short v) {
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		} else {
			short oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	private short addToValue(int pos, short incr) {
		short oldValue = this.value[pos];
		this.value[pos] = (short)(oldValue + incr);
		return oldValue;
	}

	public short addTo(char k, short incr) {
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
		this.value[pos] = (short)(this.defRetValue + incr);
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
	public short remove(char k) {
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
	public short get(char k) {
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
	public boolean containsValue(short v) {
		short[] value = this.value;
		char[] key = this.key;
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
	public short getOrDefault(char k, short defaultValue) {
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
	public short putIfAbsent(char k, short v) {
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			this.insert(-pos - 1, k, v);
			return this.defRetValue;
		}
	}

	@Override
	public boolean remove(char k, short v) {
		if (this.strategy.equals(k, '\u0000')) {
			if (this.containsNullKey && v == this.value[this.n]) {
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
			} else if (this.strategy.equals(k, curr) && v == this.value[pos]) {
				this.removeEntry(pos);
				return true;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
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
	public boolean replace(char k, short oldValue, short v) {
		int pos = this.find(k);
		if (pos >= 0 && oldValue == this.value[pos]) {
			this.value[pos] = v;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public short replace(char k, short v) {
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			short oldValue = this.value[pos];
			this.value[pos] = v;
			return oldValue;
		}
	}

	@Override
	public short computeIfAbsent(char k, IntUnaryOperator mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(k));
			this.insert(-pos - 1, k, newValue);
			return newValue;
		}
	}

	@Override
	public short computeIfAbsentNullable(char k, IntFunction<? extends Short> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int pos = this.find(k);
		if (pos >= 0) {
			return this.value[pos];
		} else {
			Short newValue = (Short)mappingFunction.apply(k);
			if (newValue == null) {
				return this.defRetValue;
			} else {
				short v = newValue;
				this.insert(-pos - 1, k, v);
				return v;
			}
		}
	}

	@Override
	public short computeIfPresent(char k, BiFunction<? super Character, ? super Short, ? extends Short> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			return this.defRetValue;
		} else {
			Short newValue = (Short)remappingFunction.apply(k, this.value[pos]);
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
	public short compute(char k, BiFunction<? super Character, ? super Short, ? extends Short> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		Short newValue = (Short)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
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
			short newVal = newValue;
			if (pos < 0) {
				this.insert(-pos - 1, k, newVal);
				return newVal;
			} else {
				return this.value[pos] = newVal;
			}
		}
	}

	@Override
	public short merge(char k, short v, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int pos = this.find(k);
		if (pos < 0) {
			this.insert(-pos - 1, k, v);
			return v;
		} else {
			Short newValue = (Short)remappingFunction.apply(this.value[pos], v);
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

	public FastEntrySet char2ShortEntrySet() {
		if (this.entries == null) {
			this.entries = new Char2ShortOpenCustomHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public CharSet keySet() {
		if (this.keys == null) {
			this.keys = new Char2ShortOpenCustomHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public ShortCollection values() {
		if (this.values == null) {
			this.values = new AbstractShortCollection() {
				@Override
				public ShortIterator iterator() {
					return Char2ShortOpenCustomHashMap.this.new ValueIterator();
				}

				public int size() {
					return Char2ShortOpenCustomHashMap.this.size;
				}

				@Override
				public boolean contains(short v) {
					return Char2ShortOpenCustomHashMap.this.containsValue(v);
				}

				public void clear() {
					Char2ShortOpenCustomHashMap.this.clear();
				}

				@Override
				public void forEach(IntConsumer consumer) {
					if (Char2ShortOpenCustomHashMap.this.containsNullKey) {
						consumer.accept(Char2ShortOpenCustomHashMap.this.value[Char2ShortOpenCustomHashMap.this.n]);
					}

					int pos = Char2ShortOpenCustomHashMap.this.n;

					while (pos-- != 0) {
						if (Char2ShortOpenCustomHashMap.this.key[pos] != 0) {
							consumer.accept(Char2ShortOpenCustomHashMap.this.value[pos]);
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
		short[] value = this.value;
		int mask = newN - 1;
		char[] newKey = new char[newN + 1];
		short[] newValue = new short[newN + 1];
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

	public Char2ShortOpenCustomHashMap clone() {
		Char2ShortOpenCustomHashMap c;
		try {
			c = (Char2ShortOpenCustomHashMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (char[])this.key.clone();
		c.value = (short[])this.value.clone();
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
			t ^= this.value[i];
			h += t;
		}

		if (this.containsNullKey) {
			h += this.value[this.n];
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		char[] key = this.key;
		short[] value = this.value;
		Char2ShortOpenCustomHashMap.MapIterator i = new Char2ShortOpenCustomHashMap.MapIterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			int e = i.nextEntry();
			s.writeChar(key[e]);
			s.writeShort(value[e]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		char[] key = this.key = new char[this.n + 1];
		short[] value = this.value = new short[this.n + 1];
		int i = this.size;

		while (i-- != 0) {
			char k = s.readChar();
			short v = s.readShort();
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

	private class EntryIterator extends Char2ShortOpenCustomHashMap.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.chars.Char2ShortMap.Entry> {
		private Char2ShortOpenCustomHashMap.MapEntry entry;

		private EntryIterator() {
		}

		public Char2ShortOpenCustomHashMap.MapEntry next() {
			return this.entry = Char2ShortOpenCustomHashMap.this.new MapEntry(this.nextEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator extends Char2ShortOpenCustomHashMap.MapIterator implements ObjectIterator<it.unimi.dsi.fastutil.chars.Char2ShortMap.Entry> {
		private final Char2ShortOpenCustomHashMap.MapEntry entry = Char2ShortOpenCustomHashMap.this.new MapEntry();

		private FastEntryIterator() {
		}

		public Char2ShortOpenCustomHashMap.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Char2ShortOpenCustomHashMap.MapIterator implements CharIterator {
		public KeyIterator() {
		}

		@Override
		public char nextChar() {
			return Char2ShortOpenCustomHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractCharSet {
		private KeySet() {
		}

		@Override
		public CharIterator iterator() {
			return Char2ShortOpenCustomHashMap.this.new KeyIterator();
		}

		@Override
		public void forEach(IntConsumer consumer) {
			if (Char2ShortOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(Char2ShortOpenCustomHashMap.this.key[Char2ShortOpenCustomHashMap.this.n]);
			}

			int pos = Char2ShortOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				char k = Char2ShortOpenCustomHashMap.this.key[pos];
				if (k != 0) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Char2ShortOpenCustomHashMap.this.size;
		}

		@Override
		public boolean contains(char k) {
			return Char2ShortOpenCustomHashMap.this.containsKey(k);
		}

		@Override
		public boolean remove(char k) {
			int oldSize = Char2ShortOpenCustomHashMap.this.size;
			Char2ShortOpenCustomHashMap.this.remove(k);
			return Char2ShortOpenCustomHashMap.this.size != oldSize;
		}

		public void clear() {
			Char2ShortOpenCustomHashMap.this.clear();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.chars.Char2ShortMap.Entry, java.util.Map.Entry<Character, Short> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		@Override
		public char getCharKey() {
			return Char2ShortOpenCustomHashMap.this.key[this.index];
		}

		@Override
		public short getShortValue() {
			return Char2ShortOpenCustomHashMap.this.value[this.index];
		}

		@Override
		public short setValue(short v) {
			short oldValue = Char2ShortOpenCustomHashMap.this.value[this.index];
			Char2ShortOpenCustomHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Character getKey() {
			return Char2ShortOpenCustomHashMap.this.key[this.index];
		}

		@Deprecated
		@Override
		public Short getValue() {
			return Char2ShortOpenCustomHashMap.this.value[this.index];
		}

		@Deprecated
		@Override
		public Short setValue(Short v) {
			return this.setValue(v.shortValue());
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<Character, Short> e = (java.util.Map.Entry<Character, Short>)o;
				return Char2ShortOpenCustomHashMap.this.strategy.equals(Char2ShortOpenCustomHashMap.this.key[this.index], (Character)e.getKey())
					&& Char2ShortOpenCustomHashMap.this.value[this.index] == (Short)e.getValue();
			}
		}

		public int hashCode() {
			return Char2ShortOpenCustomHashMap.this.strategy.hashCode(Char2ShortOpenCustomHashMap.this.key[this.index])
				^ Char2ShortOpenCustomHashMap.this.value[this.index];
		}

		public String toString() {
			return Char2ShortOpenCustomHashMap.this.key[this.index] + "=>" + Char2ShortOpenCustomHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.chars.Char2ShortMap.Entry> implements FastEntrySet {
		private MapEntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.chars.Char2ShortMap.Entry> iterator() {
			return Char2ShortOpenCustomHashMap.this.new EntryIterator();
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.chars.Char2ShortMap.Entry> fastIterator() {
			return Char2ShortOpenCustomHashMap.this.new FastEntryIterator();
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Character)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Short) {
					char k = (Character)e.getKey();
					short v = (Short)e.getValue();
					if (Char2ShortOpenCustomHashMap.this.strategy.equals(k, '\u0000')) {
						return Char2ShortOpenCustomHashMap.this.containsNullKey && Char2ShortOpenCustomHashMap.this.value[Char2ShortOpenCustomHashMap.this.n] == v;
					} else {
						char[] key = Char2ShortOpenCustomHashMap.this.key;
						char curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(Char2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Char2ShortOpenCustomHashMap.this.mask]) == 0) {
							return false;
						} else if (Char2ShortOpenCustomHashMap.this.strategy.equals(k, curr)) {
							return Char2ShortOpenCustomHashMap.this.value[pos] == v;
						} else {
							while ((curr = key[pos = pos + 1 & Char2ShortOpenCustomHashMap.this.mask]) != 0) {
								if (Char2ShortOpenCustomHashMap.this.strategy.equals(k, curr)) {
									return Char2ShortOpenCustomHashMap.this.value[pos] == v;
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
					if (e.getValue() != null && e.getValue() instanceof Short) {
						char k = (Character)e.getKey();
						short v = (Short)e.getValue();
						if (Char2ShortOpenCustomHashMap.this.strategy.equals(k, '\u0000')) {
							if (Char2ShortOpenCustomHashMap.this.containsNullKey && Char2ShortOpenCustomHashMap.this.value[Char2ShortOpenCustomHashMap.this.n] == v) {
								Char2ShortOpenCustomHashMap.this.removeNullEntry();
								return true;
							} else {
								return false;
							}
						} else {
							char[] key = Char2ShortOpenCustomHashMap.this.key;
							char curr;
							int pos;
							if ((curr = key[pos = HashCommon.mix(Char2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Char2ShortOpenCustomHashMap.this.mask]) == 0) {
								return false;
							} else if (Char2ShortOpenCustomHashMap.this.strategy.equals(curr, k)) {
								if (Char2ShortOpenCustomHashMap.this.value[pos] == v) {
									Char2ShortOpenCustomHashMap.this.removeEntry(pos);
									return true;
								} else {
									return false;
								}
							} else {
								while ((curr = key[pos = pos + 1 & Char2ShortOpenCustomHashMap.this.mask]) != 0) {
									if (Char2ShortOpenCustomHashMap.this.strategy.equals(curr, k) && Char2ShortOpenCustomHashMap.this.value[pos] == v) {
										Char2ShortOpenCustomHashMap.this.removeEntry(pos);
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
			return Char2ShortOpenCustomHashMap.this.size;
		}

		public void clear() {
			Char2ShortOpenCustomHashMap.this.clear();
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.chars.Char2ShortMap.Entry> consumer) {
			if (Char2ShortOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(
					new BasicEntry(
						Char2ShortOpenCustomHashMap.this.key[Char2ShortOpenCustomHashMap.this.n], Char2ShortOpenCustomHashMap.this.value[Char2ShortOpenCustomHashMap.this.n]
					)
				);
			}

			int pos = Char2ShortOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Char2ShortOpenCustomHashMap.this.key[pos] != 0) {
					consumer.accept(new BasicEntry(Char2ShortOpenCustomHashMap.this.key[pos], Char2ShortOpenCustomHashMap.this.value[pos]));
				}
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.chars.Char2ShortMap.Entry> consumer) {
			BasicEntry entry = new BasicEntry();
			if (Char2ShortOpenCustomHashMap.this.containsNullKey) {
				entry.key = Char2ShortOpenCustomHashMap.this.key[Char2ShortOpenCustomHashMap.this.n];
				entry.value = Char2ShortOpenCustomHashMap.this.value[Char2ShortOpenCustomHashMap.this.n];
				consumer.accept(entry);
			}

			int pos = Char2ShortOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Char2ShortOpenCustomHashMap.this.key[pos] != 0) {
					entry.key = Char2ShortOpenCustomHashMap.this.key[pos];
					entry.value = Char2ShortOpenCustomHashMap.this.value[pos];
					consumer.accept(entry);
				}
			}
		}
	}

	private class MapIterator {
		int pos = Char2ShortOpenCustomHashMap.this.n;
		int last = -1;
		int c = Char2ShortOpenCustomHashMap.this.size;
		boolean mustReturnNullKey = Char2ShortOpenCustomHashMap.this.containsNullKey;
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
					return this.last = Char2ShortOpenCustomHashMap.this.n;
				} else {
					char[] key = Char2ShortOpenCustomHashMap.this.key;

					while (--this.pos >= 0) {
						if (key[this.pos] != 0) {
							return this.last = this.pos;
						}
					}

					this.last = Integer.MIN_VALUE;
					char k = this.wrapped.getChar(-this.pos - 1);
					int p = HashCommon.mix(Char2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Char2ShortOpenCustomHashMap.this.mask;

					while (!Char2ShortOpenCustomHashMap.this.strategy.equals(k, key[p])) {
						p = p + 1 & Char2ShortOpenCustomHashMap.this.mask;
					}

					return p;
				}
			}
		}

		private void shiftKeys(int pos) {
			char[] key = Char2ShortOpenCustomHashMap.this.key;

			label38:
			while (true) {
				int last = pos;

				char curr;
				for (pos = pos + 1 & Char2ShortOpenCustomHashMap.this.mask; (curr = key[pos]) != 0; pos = pos + 1 & Char2ShortOpenCustomHashMap.this.mask) {
					int slot = HashCommon.mix(Char2ShortOpenCustomHashMap.this.strategy.hashCode(curr)) & Char2ShortOpenCustomHashMap.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new CharArrayList(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						Char2ShortOpenCustomHashMap.this.value[last] = Char2ShortOpenCustomHashMap.this.value[pos];
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
				if (this.last == Char2ShortOpenCustomHashMap.this.n) {
					Char2ShortOpenCustomHashMap.this.containsNullKey = false;
				} else {
					if (this.pos < 0) {
						Char2ShortOpenCustomHashMap.this.remove(this.wrapped.getChar(-this.pos - 1));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				Char2ShortOpenCustomHashMap.this.size--;
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

	private final class ValueIterator extends Char2ShortOpenCustomHashMap.MapIterator implements ShortIterator {
		public ValueIterator() {
		}

		@Override
		public short nextShort() {
			return Char2ShortOpenCustomHashMap.this.value[this.nextEntry()];
		}
	}
}
