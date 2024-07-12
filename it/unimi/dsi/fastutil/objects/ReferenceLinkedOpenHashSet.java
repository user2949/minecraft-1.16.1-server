package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReferenceLinkedOpenHashSet<K> extends AbstractReferenceSortedSet<K> implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient K[] key;
	protected transient int mask;
	protected transient boolean containsNull;
	protected transient int first = -1;
	protected transient int last = -1;
	protected transient long[] link;
	protected transient int n;
	protected transient int maxFill;
	protected final transient int minN;
	protected int size;
	protected final float f;

	public ReferenceLinkedOpenHashSet(int expected, float f) {
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

	public ReferenceLinkedOpenHashSet(int expected) {
		this(expected, 0.75F);
	}

	public ReferenceLinkedOpenHashSet() {
		this(16, 0.75F);
	}

	public ReferenceLinkedOpenHashSet(Collection<? extends K> c, float f) {
		this(c.size(), f);
		this.addAll(c);
	}

	public ReferenceLinkedOpenHashSet(Collection<? extends K> c) {
		this(c, 0.75F);
	}

	public ReferenceLinkedOpenHashSet(ReferenceCollection<? extends K> c, float f) {
		this(c.size(), f);
		this.addAll(c);
	}

	public ReferenceLinkedOpenHashSet(ReferenceCollection<? extends K> c) {
		this(c, 0.75F);
	}

	public ReferenceLinkedOpenHashSet(Iterator<? extends K> i, float f) {
		this(16, f);

		while (i.hasNext()) {
			this.add((K)i.next());
		}
	}

	public ReferenceLinkedOpenHashSet(Iterator<? extends K> i) {
		this(i, 0.75F);
	}

	public ReferenceLinkedOpenHashSet(K[] a, int offset, int length, float f) {
		this(length < 0 ? 0 : length, f);
		ObjectArrays.ensureOffsetLength(a, offset, length);

		for (int i = 0; i < length; i++) {
			this.add(a[offset + i]);
		}
	}

	public ReferenceLinkedOpenHashSet(K[] a, int offset, int length) {
		this(a, offset, length, 0.75F);
	}

	public ReferenceLinkedOpenHashSet(K[] a, float f) {
		this(a, 0, a.length, f);
	}

	public ReferenceLinkedOpenHashSet(K[] a) {
		this(a, 0.75F);
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
		if (k == null) {
			if (this.containsNull) {
				return false;
			}

			pos = this.n;
			this.containsNull = true;
		} else {
			K[] key = this.key;
			K curr;
			if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
				if (curr == k) {
					return false;
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (curr == k) {
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
		if (k == null) {
			return this.containsNull ? this.removeNullEntry() : false;
		} else {
			K[] key = this.key;
			K curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
				return false;
			} else if (k == curr) {
				return this.removeEntry(pos);
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != null) {
					if (k == curr) {
						return this.removeEntry(pos);
					}
				}

				return false;
			}
		}
	}

	public boolean contains(Object k) {
		if (k == null) {
			return this.containsNull;
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
			if (k == null) {
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
			if (k == null) {
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
		if (k == null) {
			if (this.containsNull) {
				this.moveIndexToFirst(this.n);
				return false;
			}

			this.containsNull = true;
			pos = this.n;
		} else {
			K[] key = this.key;

			for (pos = HashCommon.mix(System.identityHashCode(k)) & this.mask; key[pos] != null; pos = pos + 1 & this.mask) {
				if (k == key[pos]) {
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
		if (k == null) {
			if (this.containsNull) {
				this.moveIndexToLast(this.n);
				return false;
			}

			this.containsNull = true;
			pos = this.n;
		} else {
			K[] key = this.key;

			for (pos = HashCommon.mix(System.identityHashCode(k)) & this.mask; key[pos] != null; pos = pos + 1 & this.mask) {
				if (k == key[pos]) {
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
	public ReferenceSortedSet<K> tailSet(K from) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ReferenceSortedSet<K> headSet(K to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ReferenceSortedSet<K> subSet(K from, K to) {
		throw new UnsupportedOperationException();
	}

	public Comparator<? super K> comparator() {
		return null;
	}

	public ObjectListIterator<K> iterator(K from) {
		return new ReferenceLinkedOpenHashSet.SetIterator(from);
	}

	public ObjectListIterator<K> iterator() {
		return new ReferenceLinkedOpenHashSet.SetIterator();
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
			if (key[i] == null) {
				pos = newN;
			} else {
				pos = HashCommon.mix(System.identityHashCode(key[i])) & mask;

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

	public ReferenceLinkedOpenHashSet<K> clone() {
		ReferenceLinkedOpenHashSet<K> c;
		try {
			c = (ReferenceLinkedOpenHashSet<K>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (K[])((Object[])this.key.clone());
		c.containsNull = this.containsNull;
		c.link = (long[])this.link.clone();
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
				h += System.identityHashCode(this.key[i]);
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
			if (k == null) {
				pos = this.n;
				this.containsNull = true;
			} else if (key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask] != null) {
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
			this.next = ReferenceLinkedOpenHashSet.this.first;
			this.index = 0;
		}

		SetIterator(K from) {
			if (from == null) {
				if (ReferenceLinkedOpenHashSet.this.containsNull) {
					this.next = (int)ReferenceLinkedOpenHashSet.this.link[ReferenceLinkedOpenHashSet.this.n];
					this.prev = ReferenceLinkedOpenHashSet.this.n;
				} else {
					throw new NoSuchElementException("The key " + from + " does not belong to this set.");
				}
			} else if (ReferenceLinkedOpenHashSet.this.key[ReferenceLinkedOpenHashSet.this.last] == from) {
				this.prev = ReferenceLinkedOpenHashSet.this.last;
				this.index = ReferenceLinkedOpenHashSet.this.size;
			} else {
				K[] key = ReferenceLinkedOpenHashSet.this.key;

				for (int pos = HashCommon.mix(System.identityHashCode(from)) & ReferenceLinkedOpenHashSet.this.mask;
					key[pos] != null;
					pos = pos + 1 & ReferenceLinkedOpenHashSet.this.mask
				) {
					if (key[pos] == from) {
						this.next = (int)ReferenceLinkedOpenHashSet.this.link[pos];
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
				this.next = (int)ReferenceLinkedOpenHashSet.this.link[this.curr];
				this.prev = this.curr;
				if (this.index >= 0) {
					this.index++;
				}

				return ReferenceLinkedOpenHashSet.this.key[this.curr];
			}
		}

		@Override
		public K previous() {
			if (!this.hasPrevious()) {
				throw new NoSuchElementException();
			} else {
				this.curr = this.prev;
				this.prev = (int)(ReferenceLinkedOpenHashSet.this.link[this.curr] >>> 32);
				this.next = this.curr;
				if (this.index >= 0) {
					this.index--;
				}

				return ReferenceLinkedOpenHashSet.this.key[this.curr];
			}
		}

		private final void ensureIndexKnown() {
			if (this.index < 0) {
				if (this.prev == -1) {
					this.index = 0;
				} else if (this.next == -1) {
					this.index = ReferenceLinkedOpenHashSet.this.size;
				} else {
					int pos = ReferenceLinkedOpenHashSet.this.first;

					for (this.index = 1; pos != this.prev; this.index++) {
						pos = (int)ReferenceLinkedOpenHashSet.this.link[pos];
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
					this.prev = (int)(ReferenceLinkedOpenHashSet.this.link[this.curr] >>> 32);
				} else {
					this.next = (int)ReferenceLinkedOpenHashSet.this.link[this.curr];
				}

				ReferenceLinkedOpenHashSet.this.size--;
				if (this.prev == -1) {
					ReferenceLinkedOpenHashSet.this.first = this.next;
				} else {
					ReferenceLinkedOpenHashSet.this.link[this.prev] = ReferenceLinkedOpenHashSet.this.link[this.prev]
						^ (ReferenceLinkedOpenHashSet.this.link[this.prev] ^ (long)this.next & 4294967295L) & 4294967295L;
				}

				if (this.next == -1) {
					ReferenceLinkedOpenHashSet.this.last = this.prev;
				} else {
					ReferenceLinkedOpenHashSet.this.link[this.next] = ReferenceLinkedOpenHashSet.this.link[this.next]
						^ (ReferenceLinkedOpenHashSet.this.link[this.next] ^ ((long)this.prev & 4294967295L) << 32) & -4294967296L;
				}

				int pos = this.curr;
				this.curr = -1;
				if (pos == ReferenceLinkedOpenHashSet.this.n) {
					ReferenceLinkedOpenHashSet.this.containsNull = false;
					ReferenceLinkedOpenHashSet.this.key[ReferenceLinkedOpenHashSet.this.n] = null;
				} else {
					K[] key = ReferenceLinkedOpenHashSet.this.key;

					label61:
					while (true) {
						int last = pos;

						K curr;
						for (pos = pos + 1 & ReferenceLinkedOpenHashSet.this.mask; (curr = key[pos]) != null; pos = pos + 1 & ReferenceLinkedOpenHashSet.this.mask) {
							int slot = HashCommon.mix(System.identityHashCode(curr)) & ReferenceLinkedOpenHashSet.this.mask;
							if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
								key[last] = curr;
								if (this.next == pos) {
									this.next = last;
								}

								if (this.prev == pos) {
									this.prev = last;
								}

								ReferenceLinkedOpenHashSet.this.fixPointers(pos, last);
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
