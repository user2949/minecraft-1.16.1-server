package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.Hash.Strategy;
import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.objects.AbstractReference2CharMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2CharMap.FastEntrySet;
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
import java.util.function.ToIntFunction;

public class Reference2CharOpenCustomHashMap<K> extends AbstractReference2CharMap<K> implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient K[] key;
	protected transient char[] value;
	protected transient int mask;
	protected transient boolean containsNullKey;
	protected Strategy<K> strategy;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;
	protected transient FastEntrySet<K> entries;
	protected transient ReferenceSet<K> keys;
	protected transient CharCollection values;

	public Reference2CharOpenCustomHashMap(int expected, float f, Strategy<K> strategy) {
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
		}
	}

	public Reference2CharOpenCustomHashMap(int expected, Strategy<K> strategy) {
		this(expected, 0.75F, strategy);
	}

	public Reference2CharOpenCustomHashMap(Strategy<K> strategy) {
		this(16, 0.75F, strategy);
	}

	public Reference2CharOpenCustomHashMap(Map<? extends K, ? extends Character> m, float f, Strategy<K> strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Reference2CharOpenCustomHashMap(Map<? extends K, ? extends Character> m, Strategy<K> strategy) {
		this(m, 0.75F, strategy);
	}

	public Reference2CharOpenCustomHashMap(Reference2CharMap<K> m, float f, Strategy<K> strategy) {
		this(m.size(), f, strategy);
		this.putAll(m);
	}

	public Reference2CharOpenCustomHashMap(Reference2CharMap<K> m, Strategy<K> strategy) {
		this(m, 0.75F, strategy);
	}

	public Reference2CharOpenCustomHashMap(K[] k, char[] v, float f, Strategy<K> strategy) {
		this(k.length, f, strategy);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Reference2CharOpenCustomHashMap(K[] k, char[] v, Strategy<K> strategy) {
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

	public FastEntrySet<K> reference2CharEntrySet() {
		if (this.entries == null) {
			this.entries = new Reference2CharOpenCustomHashMap.MapEntrySet();
		}

		return this.entries;
	}

	@Override
	public ReferenceSet<K> keySet() {
		if (this.keys == null) {
			this.keys = new Reference2CharOpenCustomHashMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public CharCollection values() {
		if (this.values == null) {
			this.values = new AbstractCharCollection() {
				@Override
				public CharIterator iterator() {
					return Reference2CharOpenCustomHashMap.this.new ValueIterator();
				}

				public int size() {
					return Reference2CharOpenCustomHashMap.this.size;
				}

				@Override
				public boolean contains(char v) {
					return Reference2CharOpenCustomHashMap.this.containsValue(v);
				}

				public void clear() {
					Reference2CharOpenCustomHashMap.this.clear();
				}

				@Override
				public void forEach(IntConsumer consumer) {
					if (Reference2CharOpenCustomHashMap.this.containsNullKey) {
						consumer.accept(Reference2CharOpenCustomHashMap.this.value[Reference2CharOpenCustomHashMap.this.n]);
					}

					int pos = Reference2CharOpenCustomHashMap.this.n;

					while (pos-- != 0) {
						if (Reference2CharOpenCustomHashMap.this.key[pos] != null) {
							consumer.accept(Reference2CharOpenCustomHashMap.this.value[pos]);
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
		int i = this.n;
		int j = this.realSize();

		while (j-- != 0) {
			while (key[--i] == null) {
			}

			int pos;
			if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != null) {
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

	public Reference2CharOpenCustomHashMap<K> clone() {
		Reference2CharOpenCustomHashMap<K> c;
		try {
			c = (Reference2CharOpenCustomHashMap<K>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.containsNullKey = this.containsNullKey;
		c.key = (K[])((Object[])this.key.clone());
		c.value = (char[])this.value.clone();
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
		Reference2CharOpenCustomHashMap<K>.MapIterator i = new Reference2CharOpenCustomHashMap.MapIterator();
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
		}
	}

	private void checkTable() {
	}

	private class EntryIterator
		extends Reference2CharOpenCustomHashMap<K>.MapIterator
		implements ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2CharMap.Entry<K>> {
		private Reference2CharOpenCustomHashMap<K>.MapEntry entry;

		private EntryIterator() {
		}

		public Reference2CharOpenCustomHashMap<K>.MapEntry next() {
			return this.entry = Reference2CharOpenCustomHashMap.this.new MapEntry(this.nextEntry());
		}

		@Override
		public void remove() {
			super.remove();
			this.entry.index = -1;
		}
	}

	private class FastEntryIterator
		extends Reference2CharOpenCustomHashMap<K>.MapIterator
		implements ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2CharMap.Entry<K>> {
		private final Reference2CharOpenCustomHashMap<K>.MapEntry entry = Reference2CharOpenCustomHashMap.this.new MapEntry();

		private FastEntryIterator() {
		}

		public Reference2CharOpenCustomHashMap<K>.MapEntry next() {
			this.entry.index = this.nextEntry();
			return this.entry;
		}
	}

	private final class KeyIterator extends Reference2CharOpenCustomHashMap<K>.MapIterator implements ObjectIterator<K> {
		public KeyIterator() {
		}

		public K next() {
			return Reference2CharOpenCustomHashMap.this.key[this.nextEntry()];
		}
	}

	private final class KeySet extends AbstractReferenceSet<K> {
		private KeySet() {
		}

		@Override
		public ObjectIterator<K> iterator() {
			return Reference2CharOpenCustomHashMap.this.new KeyIterator();
		}

		public void forEach(Consumer<? super K> consumer) {
			if (Reference2CharOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(Reference2CharOpenCustomHashMap.this.key[Reference2CharOpenCustomHashMap.this.n]);
			}

			int pos = Reference2CharOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				K k = Reference2CharOpenCustomHashMap.this.key[pos];
				if (k != null) {
					consumer.accept(k);
				}
			}
		}

		public int size() {
			return Reference2CharOpenCustomHashMap.this.size;
		}

		public boolean contains(Object k) {
			return Reference2CharOpenCustomHashMap.this.containsKey(k);
		}

		public boolean remove(Object k) {
			int oldSize = Reference2CharOpenCustomHashMap.this.size;
			Reference2CharOpenCustomHashMap.this.removeChar(k);
			return Reference2CharOpenCustomHashMap.this.size != oldSize;
		}

		public void clear() {
			Reference2CharOpenCustomHashMap.this.clear();
		}
	}

	final class MapEntry implements it.unimi.dsi.fastutil.objects.Reference2CharMap.Entry<K>, java.util.Map.Entry<K, Character> {
		int index;

		MapEntry(int index) {
			this.index = index;
		}

		MapEntry() {
		}

		public K getKey() {
			return Reference2CharOpenCustomHashMap.this.key[this.index];
		}

		@Override
		public char getCharValue() {
			return Reference2CharOpenCustomHashMap.this.value[this.index];
		}

		@Override
		public char setValue(char v) {
			char oldValue = Reference2CharOpenCustomHashMap.this.value[this.index];
			Reference2CharOpenCustomHashMap.this.value[this.index] = v;
			return oldValue;
		}

		@Deprecated
		@Override
		public Character getValue() {
			return Reference2CharOpenCustomHashMap.this.value[this.index];
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
				return Reference2CharOpenCustomHashMap.this.strategy.equals(Reference2CharOpenCustomHashMap.this.key[this.index], (K)e.getKey())
					&& Reference2CharOpenCustomHashMap.this.value[this.index] == (Character)e.getValue();
			}
		}

		public int hashCode() {
			return Reference2CharOpenCustomHashMap.this.strategy.hashCode(Reference2CharOpenCustomHashMap.this.key[this.index])
				^ Reference2CharOpenCustomHashMap.this.value[this.index];
		}

		public String toString() {
			return Reference2CharOpenCustomHashMap.this.key[this.index] + "=>" + Reference2CharOpenCustomHashMap.this.value[this.index];
		}
	}

	private final class MapEntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.objects.Reference2CharMap.Entry<K>> implements FastEntrySet<K> {
		private MapEntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2CharMap.Entry<K>> iterator() {
			return Reference2CharOpenCustomHashMap.this.new EntryIterator();
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2CharMap.Entry<K>> fastIterator() {
			return Reference2CharOpenCustomHashMap.this.new FastEntryIterator();
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getValue() != null && e.getValue() instanceof Character) {
					K k = (K)e.getKey();
					char v = (Character)e.getValue();
					if (Reference2CharOpenCustomHashMap.this.strategy.equals(k, null)) {
						return Reference2CharOpenCustomHashMap.this.containsNullKey && Reference2CharOpenCustomHashMap.this.value[Reference2CharOpenCustomHashMap.this.n] == v;
					} else {
						K[] key = Reference2CharOpenCustomHashMap.this.key;
						K curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(Reference2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2CharOpenCustomHashMap.this.mask]) == null) {
							return false;
						} else if (Reference2CharOpenCustomHashMap.this.strategy.equals(k, curr)) {
							return Reference2CharOpenCustomHashMap.this.value[pos] == v;
						} else {
							while ((curr = key[pos = pos + 1 & Reference2CharOpenCustomHashMap.this.mask]) != null) {
								if (Reference2CharOpenCustomHashMap.this.strategy.equals(k, curr)) {
									return Reference2CharOpenCustomHashMap.this.value[pos] == v;
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
					if (Reference2CharOpenCustomHashMap.this.strategy.equals(k, null)) {
						if (Reference2CharOpenCustomHashMap.this.containsNullKey && Reference2CharOpenCustomHashMap.this.value[Reference2CharOpenCustomHashMap.this.n] == v) {
							Reference2CharOpenCustomHashMap.this.removeNullEntry();
							return true;
						} else {
							return false;
						}
					} else {
						K[] key = Reference2CharOpenCustomHashMap.this.key;
						K curr;
						int pos;
						if ((curr = key[pos = HashCommon.mix(Reference2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2CharOpenCustomHashMap.this.mask]) == null) {
							return false;
						} else if (Reference2CharOpenCustomHashMap.this.strategy.equals(curr, k)) {
							if (Reference2CharOpenCustomHashMap.this.value[pos] == v) {
								Reference2CharOpenCustomHashMap.this.removeEntry(pos);
								return true;
							} else {
								return false;
							}
						} else {
							while ((curr = key[pos = pos + 1 & Reference2CharOpenCustomHashMap.this.mask]) != null) {
								if (Reference2CharOpenCustomHashMap.this.strategy.equals(curr, k) && Reference2CharOpenCustomHashMap.this.value[pos] == v) {
									Reference2CharOpenCustomHashMap.this.removeEntry(pos);
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
			return Reference2CharOpenCustomHashMap.this.size;
		}

		public void clear() {
			Reference2CharOpenCustomHashMap.this.clear();
		}

		public void forEach(Consumer<? super it.unimi.dsi.fastutil.objects.Reference2CharMap.Entry<K>> consumer) {
			if (Reference2CharOpenCustomHashMap.this.containsNullKey) {
				consumer.accept(
					new BasicEntry(
						Reference2CharOpenCustomHashMap.this.key[Reference2CharOpenCustomHashMap.this.n],
						Reference2CharOpenCustomHashMap.this.value[Reference2CharOpenCustomHashMap.this.n]
					)
				);
			}

			int pos = Reference2CharOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Reference2CharOpenCustomHashMap.this.key[pos] != null) {
					consumer.accept(new BasicEntry(Reference2CharOpenCustomHashMap.this.key[pos], Reference2CharOpenCustomHashMap.this.value[pos]));
				}
			}
		}

		@Override
		public void fastForEach(Consumer<? super it.unimi.dsi.fastutil.objects.Reference2CharMap.Entry<K>> consumer) {
			BasicEntry<K> entry = new BasicEntry<>();
			if (Reference2CharOpenCustomHashMap.this.containsNullKey) {
				entry.key = Reference2CharOpenCustomHashMap.this.key[Reference2CharOpenCustomHashMap.this.n];
				entry.value = Reference2CharOpenCustomHashMap.this.value[Reference2CharOpenCustomHashMap.this.n];
				consumer.accept(entry);
			}

			int pos = Reference2CharOpenCustomHashMap.this.n;

			while (pos-- != 0) {
				if (Reference2CharOpenCustomHashMap.this.key[pos] != null) {
					entry.key = Reference2CharOpenCustomHashMap.this.key[pos];
					entry.value = Reference2CharOpenCustomHashMap.this.value[pos];
					consumer.accept(entry);
				}
			}
		}
	}

	private class MapIterator {
		int pos = Reference2CharOpenCustomHashMap.this.n;
		int last = -1;
		int c = Reference2CharOpenCustomHashMap.this.size;
		boolean mustReturnNullKey = Reference2CharOpenCustomHashMap.this.containsNullKey;
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
					return this.last = Reference2CharOpenCustomHashMap.this.n;
				} else {
					K[] key = Reference2CharOpenCustomHashMap.this.key;

					while (--this.pos >= 0) {
						if (key[this.pos] != null) {
							return this.last = this.pos;
						}
					}

					this.last = Integer.MIN_VALUE;
					K k = this.wrapped.get(-this.pos - 1);
					int p = HashCommon.mix(Reference2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2CharOpenCustomHashMap.this.mask;

					while (!Reference2CharOpenCustomHashMap.this.strategy.equals(k, key[p])) {
						p = p + 1 & Reference2CharOpenCustomHashMap.this.mask;
					}

					return p;
				}
			}
		}

		private void shiftKeys(int pos) {
			K[] key = Reference2CharOpenCustomHashMap.this.key;

			label38:
			while (true) {
				int last = pos;

				K curr;
				for (pos = pos + 1 & Reference2CharOpenCustomHashMap.this.mask; (curr = key[pos]) != null; pos = pos + 1 & Reference2CharOpenCustomHashMap.this.mask) {
					int slot = HashCommon.mix(Reference2CharOpenCustomHashMap.this.strategy.hashCode(curr)) & Reference2CharOpenCustomHashMap.this.mask;
					if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
						if (pos < last) {
							if (this.wrapped == null) {
								this.wrapped = new ReferenceArrayList<>(2);
							}

							this.wrapped.add(key[pos]);
						}

						key[last] = curr;
						Reference2CharOpenCustomHashMap.this.value[last] = Reference2CharOpenCustomHashMap.this.value[pos];
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
				if (this.last == Reference2CharOpenCustomHashMap.this.n) {
					Reference2CharOpenCustomHashMap.this.containsNullKey = false;
					Reference2CharOpenCustomHashMap.this.key[Reference2CharOpenCustomHashMap.this.n] = null;
				} else {
					if (this.pos < 0) {
						Reference2CharOpenCustomHashMap.this.removeChar(this.wrapped.set(-this.pos - 1, null));
						this.last = -1;
						return;
					}

					this.shiftKeys(this.last);
				}

				Reference2CharOpenCustomHashMap.this.size--;
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

	private final class ValueIterator extends Reference2CharOpenCustomHashMap<K>.MapIterator implements CharIterator {
		public ValueIterator() {
		}

		@Override
		public char nextChar() {
			return Reference2CharOpenCustomHashMap.this.value[this.nextEntry()];
		}
	}
}
