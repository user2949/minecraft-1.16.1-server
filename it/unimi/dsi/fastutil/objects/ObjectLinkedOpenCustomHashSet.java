package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.Hash.Strategy;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ObjectLinkedOpenCustomHashSet<K> extends AbstractObjectSortedSet<K> implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient K[] key;
	protected transient int mask;
	protected transient boolean containsNull;
	protected Strategy<K> strategy;
	protected transient int first = -1;
	protected transient int last = -1;
	protected transient long[] link;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;

	public ObjectLinkedOpenCustomHashSet(int expected, float f, Strategy<K> strategy) {
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
			this.link = new long[this.n + 1];
		}
	}

	public ObjectLinkedOpenCustomHashSet(int expected, Strategy<K> strategy) {
		this(expected, 0.75F, strategy);
	}

	public ObjectLinkedOpenCustomHashSet(Strategy<K> strategy) {
		this(16, 0.75F, strategy);
	}

	public ObjectLinkedOpenCustomHashSet(Collection<? extends K> c, float f, Strategy<K> strategy) {
		this(c.size(), f, strategy);
		this.addAll(c);
	}

	public ObjectLinkedOpenCustomHashSet(Collection<? extends K> c, Strategy<K> strategy) {
		this(c, 0.75F, strategy);
	}

	public ObjectLinkedOpenCustomHashSet(ObjectCollection<? extends K> c, float f, Strategy<K> strategy) {
		this(c.size(), f, strategy);
		this.addAll(c);
	}

	public ObjectLinkedOpenCustomHashSet(ObjectCollection<? extends K> c, Strategy<K> strategy) {
		this(c, 0.75F, strategy);
	}

	public ObjectLinkedOpenCustomHashSet(Iterator<? extends K> i, float f, Strategy<K> strategy) {
		this(16, f, strategy);

		while (i.hasNext()) {
			this.add((K)i.next());
		}
	}

	public ObjectLinkedOpenCustomHashSet(Iterator<? extends K> i, Strategy<K> strategy) {
		this(i, 0.75F, strategy);
	}

	public ObjectLinkedOpenCustomHashSet(K[] a, int offset, int length, float f, Strategy<K> strategy) {
		this(length < 0 ? 0 : length, f, strategy);
		ObjectArrays.ensureOffsetLength(a, offset, length);

		for (int i = 0; i < length; i++) {
			this.add(a[offset + i]);
		}
	}

	public ObjectLinkedOpenCustomHashSet(K[] a, int offset, int length, Strategy<K> strategy) {
		this(a, offset, length, 0.75F, strategy);
	}

	public ObjectLinkedOpenCustomHashSet(K[] a, float f, Strategy<K> strategy) {
		this(a, 0, a.length, f, strategy);
	}

	public ObjectLinkedOpenCustomHashSet(K[] a, Strategy<K> strategy) {
		this(a, 0.75F, strategy);
	}

	public Strategy<K> strategy() {
		return this.strategy;
	}

	private int realSize() {
		return this.containsNull ? this.size - 1 : this.size;
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

	public boolean addAll(Collection<? extends K> c) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(c.size());
		} else {
			this.tryCapacity((long)(this.size() + c.size()));
		}

		return super.addAll(c);
	}

	public boolean add(K k) {
		int pos;
		if (this.strategy.equals(k, null)) {
			if (this.containsNull) {
				return false;
			}

			pos = this.n;
			this.containsNull = true;
			this.key[this.n] = k;
		} else {
			K[] key = this.key;
			K curr;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
				if (this.strategy.equals(curr, k)) {
					return false;
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (this.strategy.equals(curr, k)) {
						return false;
					}
				}
			}

			key[pos] = k;
		}

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

		return true;
	}

	public K addOrGet(K k) {
		int pos;
		if (this.strategy.equals(k, null)) {
			if (this.containsNull) {
				return this.key[this.n];
			}

			pos = this.n;
			this.containsNull = true;
			this.key[this.n] = k;
		} else {
			K[] key = this.key;
			K curr;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
				if (this.strategy.equals(curr, k)) {
					return curr;
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (this.strategy.equals(curr, k)) {
						return curr;
					}
				}
			}

			key[pos] = k;
		}

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

		return k;
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
					this.fixPointers(pos, last);
					continue label30;
				}
			}

			key[last] = null;
			return;
		}
	}

	private boolean removeEntry(int pos) {
		this.size--;
		this.fixPointers(pos);
		this.shiftKeys(pos);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return true;
	}

	private boolean removeNullEntry() {
		this.containsNull = false;
		this.key[this.n] = null;
		this.size--;
		this.fixPointers(this.n);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return true;
	}

	public boolean remove(Object k) {
		if (this.strategy.equals((K)k, null)) {
			return this.containsNull ? this.removeNullEntry() : false;
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode((K)k)) & this.mask]) == null) {
				return false;
			} else if (this.strategy.equals((K)k, curr)) {
				return this.removeEntry(pos);
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (this.strategy.equals((K)k, curr)) {
						return this.removeEntry(pos);
					}
				}

				return false;
			}
		}
	}

	public boolean contains(Object k) {
		if (this.strategy.equals((K)k, null)) {
			return this.containsNull;
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

	public K get(Object k) {
		if (this.strategy.equals((K)k, null)) {
			return this.key[this.n];
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode((K)k)) & this.mask]) == null) {
				return null;
			} else if (this.strategy.equals((K)k, curr)) {
				return curr;
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (this.strategy.equals((K)k, curr)) {
						return curr;
					}
				}

				return null;
			}
		}
	}

	public K removeFirst() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			int pos = this.first;
			this.first = (int)this.link[pos];
			if (0 <= this.first) {
				this.link[this.first] = this.link[this.first] | -4294967296L;
			}

			K k = this.key[pos];
			this.size--;
			if (this.strategy.equals(k, null)) {
				this.containsNull = false;
				this.key[this.n] = null;
			} else {
				this.shiftKeys(pos);
			}

			if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
				this.rehash(this.n / 2);
			}

			return k;
		}
	}

	public K removeLast() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			int pos = this.last;
			this.last = (int)(this.link[pos] >>> 32);
			if (0 <= this.last) {
				this.link[this.last] = this.link[this.last] | 4294967295L;
			}

			K k = this.key[pos];
			this.size--;
			if (this.strategy.equals(k, null)) {
				this.containsNull = false;
				this.key[this.n] = null;
			} else {
				this.shiftKeys(pos);
			}

			if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
				this.rehash(this.n / 2);
			}

			return k;
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

	public boolean addAndMoveToFirst(K k) {
		int pos;
		if (this.strategy.equals(k, null)) {
			if (this.containsNull) {
				this.moveIndexToFirst(this.n);
				return false;
			}

			this.containsNull = true;
			pos = this.n;
		} else {
			K[] key = this.key;

			for (pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask; key[pos] != null; pos = pos + 1 & this.mask) {
				if (this.strategy.equals(k, key[pos])) {
					this.moveIndexToFirst(pos);
					return false;
				}
			}
		}

		this.key[pos] = k;
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

		return true;
	}

	public boolean addAndMoveToLast(K k) {
		int pos;
		if (this.strategy.equals(k, null)) {
			if (this.containsNull) {
				this.moveIndexToLast(this.n);
				return false;
			}

			this.containsNull = true;
			pos = this.n;
		} else {
			K[] key = this.key;

			for (pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask; key[pos] != null; pos = pos + 1 & this.mask) {
				if (this.strategy.equals(k, key[pos])) {
					this.moveIndexToLast(pos);
					return false;
				}
			}
		}

		this.key[pos] = k;
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

		return true;
	}

	public void clear() {
		if (this.size != 0) {
			this.size = 0;
			this.containsNull = false;
			Arrays.fill(this.key, null);
			this.first = this.last = -1;
		}
	}

	public int size() {
		return this.size;
	}

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

	public K first() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			return this.key[this.first];
		}
	}

	public K last() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			return this.key[this.last];
		}
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

	public Comparator<? super K> comparator() {
		return null;
	}

	public ObjectListIterator<K> iterator(K from) {
		return new ObjectLinkedOpenCustomHashSet.SetIterator(from);
	}

	public ObjectListIterator<K> iterator() {
		return new ObjectLinkedOpenCustomHashSet.SetIterator();
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
		int mask = newN - 1;
		K[] newKey = (K[])(new Object[newN + 1]);
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
	}

	public ObjectLinkedOpenCustomHashSet<K> clone() {
		ObjectLinkedOpenCustomHashSet<K> c;
		try {
			c = (ObjectLinkedOpenCustomHashSet<K>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (K[])((Object[])this.key.clone());
		c.containsNull = this.containsNull;
		c.link = (long[])this.link.clone();
		c.strategy = this.strategy;
		return c;
	}

	@Override
	public int hashCode() {
		int h = 0;
		int j = this.realSize();

		for (int i = 0; j-- != 0; i++) {
			while (this.key[i] == null) {
				i++;
			}

			if (this != this.key[i]) {
				h += this.strategy.hashCode(this.key[i]);
			}
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		ObjectIterator<K> i = this.iterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			s.writeObject(i.next());
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		K[] key = this.key = (K[])(new Object[this.n + 1]);
		long[] link = this.link = new long[this.n + 1];
		int prev = -1;
		this.first = this.last = -1;
		int i = this.size;

		while (i-- != 0) {
			K k = (K)s.readObject();
			int pos;
			if (this.strategy.equals(k, null)) {
				pos = this.n;
				this.containsNull = true;
			} else if (key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask] != null) {
				while (key[pos = pos + 1 & this.mask] != null) {
				}
			}

			key[pos] = k;
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

	private class SetIterator implements ObjectListIterator<K> {
		int prev = -1;
		int next = -1;
		int curr = -1;
		int index = -1;

		SetIterator() {
			this.next = ObjectLinkedOpenCustomHashSet.this.first;
			this.index = 0;
		}

		SetIterator(K from) {
			if (ObjectLinkedOpenCustomHashSet.this.strategy.equals(from, null)) {
				if (ObjectLinkedOpenCustomHashSet.this.containsNull) {
					this.next = (int)ObjectLinkedOpenCustomHashSet.this.link[ObjectLinkedOpenCustomHashSet.this.n];
					this.prev = ObjectLinkedOpenCustomHashSet.this.n;
				} else {
					throw new NoSuchElementException("The key " + from + " does not belong to this set.");
				}
			} else if (ObjectLinkedOpenCustomHashSet.this.strategy.equals(ObjectLinkedOpenCustomHashSet.this.key[ObjectLinkedOpenCustomHashSet.this.last], from)) {
				this.prev = ObjectLinkedOpenCustomHashSet.this.last;
				this.index = ObjectLinkedOpenCustomHashSet.this.size;
			} else {
				K[] key = ObjectLinkedOpenCustomHashSet.this.key;

				for (int pos = HashCommon.mix(ObjectLinkedOpenCustomHashSet.this.strategy.hashCode(from)) & ObjectLinkedOpenCustomHashSet.this.mask;
					key[pos] != null;
					pos = pos + 1 & ObjectLinkedOpenCustomHashSet.this.mask
				) {
					if (ObjectLinkedOpenCustomHashSet.this.strategy.equals(key[pos], from)) {
						this.next = (int)ObjectLinkedOpenCustomHashSet.this.link[pos];
						this.prev = pos;
						return;
					}
				}

				throw new NoSuchElementException("The key " + from + " does not belong to this set.");
			}
		}

		public boolean hasNext() {
			return this.next != -1;
		}

		@Override
		public boolean hasPrevious() {
			return this.prev != -1;
		}

		public K next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.curr = this.next;
				this.next = (int)ObjectLinkedOpenCustomHashSet.this.link[this.curr];
				this.prev = this.curr;
				if (this.index >= 0) {
					this.index++;
				}

				return ObjectLinkedOpenCustomHashSet.this.key[this.curr];
			}
		}

		@Override
		public K previous() {
			if (!this.hasPrevious()) {
				throw new NoSuchElementException();
			} else {
				this.curr = this.prev;
				this.prev = (int)(ObjectLinkedOpenCustomHashSet.this.link[this.curr] >>> 32);
				this.next = this.curr;
				if (this.index >= 0) {
					this.index--;
				}

				return ObjectLinkedOpenCustomHashSet.this.key[this.curr];
			}
		}

		private final void ensureIndexKnown() {
			if (this.index < 0) {
				if (this.prev == -1) {
					this.index = 0;
				} else if (this.next == -1) {
					this.index = ObjectLinkedOpenCustomHashSet.this.size;
				} else {
					int pos = ObjectLinkedOpenCustomHashSet.this.first;

					for (this.index = 1; pos != this.prev; this.index++) {
						pos = (int)ObjectLinkedOpenCustomHashSet.this.link[pos];
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

		@Override
		public void remove() {
			this.ensureIndexKnown();
			if (this.curr == -1) {
				throw new IllegalStateException();
			} else {
				if (this.curr == this.prev) {
					this.index--;
					this.prev = (int)(ObjectLinkedOpenCustomHashSet.this.link[this.curr] >>> 32);
				} else {
					this.next = (int)ObjectLinkedOpenCustomHashSet.this.link[this.curr];
				}

				ObjectLinkedOpenCustomHashSet.this.size--;
				if (this.prev == -1) {
					ObjectLinkedOpenCustomHashSet.this.first = this.next;
				} else {
					ObjectLinkedOpenCustomHashSet.this.link[this.prev] = ObjectLinkedOpenCustomHashSet.this.link[this.prev]
						^ (ObjectLinkedOpenCustomHashSet.this.link[this.prev] ^ (long)this.next & 4294967295L) & 4294967295L;
				}

				if (this.next == -1) {
					ObjectLinkedOpenCustomHashSet.this.last = this.prev;
				} else {
					ObjectLinkedOpenCustomHashSet.this.link[this.next] = ObjectLinkedOpenCustomHashSet.this.link[this.next]
						^ (ObjectLinkedOpenCustomHashSet.this.link[this.next] ^ ((long)this.prev & 4294967295L) << 32) & -4294967296L;
				}

				int pos = this.curr;
				this.curr = -1;
				if (pos == ObjectLinkedOpenCustomHashSet.this.n) {
					ObjectLinkedOpenCustomHashSet.this.containsNull = false;
					ObjectLinkedOpenCustomHashSet.this.key[ObjectLinkedOpenCustomHashSet.this.n] = null;
				} else {
					K[] key = ObjectLinkedOpenCustomHashSet.this.key;

					label61:
					while (true) {
						int last = pos;

						K curr;
						for (pos = pos + 1 & ObjectLinkedOpenCustomHashSet.this.mask; (curr = key[pos]) != null; pos = pos + 1 & ObjectLinkedOpenCustomHashSet.this.mask) {
							int slot = HashCommon.mix(ObjectLinkedOpenCustomHashSet.this.strategy.hashCode(curr)) & ObjectLinkedOpenCustomHashSet.this.mask;
							if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
								key[last] = curr;
								if (this.next == pos) {
									this.next = last;
								}

								if (this.prev == pos) {
									this.prev = last;
								}

								ObjectLinkedOpenCustomHashSet.this.fixPointers(pos, last);
								continue label61;
							}
						}

						key[last] = null;
						return;
					}
				}
			}
		}
	}
}
