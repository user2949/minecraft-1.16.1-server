package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ByteLinkedOpenHashSet extends AbstractByteSortedSet implements Serializable, Cloneable, Hash {
	private static final long serialVersionUID = 0L;
	private static final boolean ASSERTS = false;
	protected transient byte[] key;
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

	public ByteLinkedOpenHashSet(int expected, float f) {
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
			this.link = new long[this.n + 1];
		}
	}

	public ByteLinkedOpenHashSet(int expected) {
		this(expected, 0.75F);
	}

	public ByteLinkedOpenHashSet() {
		this(16, 0.75F);
	}

	public ByteLinkedOpenHashSet(Collection<? extends Byte> c, float f) {
		this(c.size(), f);
		this.addAll(c);
	}

	public ByteLinkedOpenHashSet(Collection<? extends Byte> c) {
		this(c, 0.75F);
	}

	public ByteLinkedOpenHashSet(ByteCollection c, float f) {
		this(c.size(), f);
		this.addAll(c);
	}

	public ByteLinkedOpenHashSet(ByteCollection c) {
		this(c, 0.75F);
	}

	public ByteLinkedOpenHashSet(ByteIterator i, float f) {
		this(16, f);

		while (i.hasNext()) {
			this.add(i.nextByte());
		}
	}

	public ByteLinkedOpenHashSet(ByteIterator i) {
		this(i, 0.75F);
	}

	public ByteLinkedOpenHashSet(Iterator<?> i, float f) {
		this(ByteIterators.asByteIterator(i), f);
	}

	public ByteLinkedOpenHashSet(Iterator<?> i) {
		this(ByteIterators.asByteIterator(i));
	}

	public ByteLinkedOpenHashSet(byte[] a, int offset, int length, float f) {
		this(length < 0 ? 0 : length, f);
		ByteArrays.ensureOffsetLength(a, offset, length);

		for (int i = 0; i < length; i++) {
			this.add(a[offset + i]);
		}
	}

	public ByteLinkedOpenHashSet(byte[] a, int offset, int length) {
		this(a, offset, length, 0.75F);
	}

	public ByteLinkedOpenHashSet(byte[] a, float f) {
		this(a, 0, a.length, f);
	}

	public ByteLinkedOpenHashSet(byte[] a) {
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

	@Override
	public boolean addAll(ByteCollection c) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(c.size());
		} else {
			this.tryCapacity((long)(this.size() + c.size()));
		}

		return super.addAll(c);
	}

	public boolean addAll(Collection<? extends Byte> c) {
		if ((double)this.f <= 0.5) {
			this.ensureCapacity(c.size());
		} else {
			this.tryCapacity((long)(this.size() + c.size()));
		}

		return super.addAll(c);
	}

	@Override
	public boolean add(byte k) {
		int pos;
		if (k == 0) {
			if (this.containsNull) {
				return false;
			}

			pos = this.n;
			this.containsNull = true;
		} else {
			byte[] key = this.key;
			byte curr;
			if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
				if (curr == k) {
					return false;
				}

				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
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
		byte[] key = this.key;

		label30:
		while (true) {
			int last = pos;

			byte curr;
			for (pos = pos + 1 & this.mask; (curr = key[pos]) != 0; pos = pos + 1 & this.mask) {
				int slot = HashCommon.mix(curr) & this.mask;
				if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
					key[last] = curr;
					this.fixPointers(pos, last);
					continue label30;
				}
			}

			key[last] = 0;
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
		this.key[this.n] = 0;
		this.size--;
		this.fixPointers(this.n);
		if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
			this.rehash(this.n / 2);
		}

		return true;
	}

	@Override
	public boolean remove(byte k) {
		if (k == 0) {
			return this.containsNull ? this.removeNullEntry() : false;
		} else {
			byte[] key = this.key;
			byte curr;
			int pos;
			if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
				return false;
			} else if (k == curr) {
				return this.removeEntry(pos);
			} else {
				while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
					if (k == curr) {
						return this.removeEntry(pos);
					}
				}

				return false;
			}
		}
	}

	@Override
	public boolean contains(byte k) {
		if (k == 0) {
			return this.containsNull;
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

	public byte removeFirstByte() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			int pos = this.first;
			this.first = (int)this.link[pos];
			if (0 <= this.first) {
				this.link[this.first] = this.link[this.first] | -4294967296L;
			}

			byte k = this.key[pos];
			this.size--;
			if (k == 0) {
				this.containsNull = false;
				this.key[this.n] = 0;
			} else {
				this.shiftKeys(pos);
			}

			if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
				this.rehash(this.n / 2);
			}

			return k;
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

			byte k = this.key[pos];
			this.size--;
			if (k == 0) {
				this.containsNull = false;
				this.key[this.n] = 0;
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

	public boolean addAndMoveToFirst(byte k) {
		int pos;
		if (k == 0) {
			if (this.containsNull) {
				this.moveIndexToFirst(this.n);
				return false;
			}

			this.containsNull = true;
			pos = this.n;
		} else {
			byte[] key = this.key;

			for (pos = HashCommon.mix(k) & this.mask; key[pos] != 0; pos = pos + 1 & this.mask) {
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

	public boolean addAndMoveToLast(byte k) {
		int pos;
		if (k == 0) {
			if (this.containsNull) {
				this.moveIndexToLast(this.n);
				return false;
			}

			this.containsNull = true;
			pos = this.n;
		} else {
			byte[] key = this.key;

			for (pos = HashCommon.mix(k) & this.mask; key[pos] != 0; pos = pos + 1 & this.mask) {
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
			Arrays.fill(this.key, (byte)0);
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

	@Override
	public byte firstByte() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			return this.key[this.first];
		}
	}

	@Override
	public byte lastByte() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			return this.key[this.last];
		}
	}

	@Override
	public ByteSortedSet tailSet(byte from) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ByteSortedSet headSet(byte to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ByteSortedSet subSet(byte from, byte to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ByteComparator comparator() {
		return null;
	}

	public ByteListIterator iterator(byte from) {
		return new ByteLinkedOpenHashSet.SetIterator(from);
	}

	public ByteListIterator iterator() {
		return new ByteLinkedOpenHashSet.SetIterator();
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
		int mask = newN - 1;
		byte[] newKey = new byte[newN + 1];
		int i = this.first;
		int prev = -1;
		int newPrev = -1;
		long[] link = this.link;
		long[] newLink = new long[newN + 1];
		this.first = -1;
		int j = this.size;

		while (j-- != 0) {
			int pos;
			if (key[i] == 0) {
				pos = newN;
			} else {
				pos = HashCommon.mix(key[i]) & mask;

				while (newKey[pos] != 0) {
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

	public ByteLinkedOpenHashSet clone() {
		ByteLinkedOpenHashSet c;
		try {
			c = (ByteLinkedOpenHashSet)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (byte[])this.key.clone();
		c.containsNull = this.containsNull;
		c.link = (long[])this.link.clone();
		return c;
	}

	@Override
	public int hashCode() {
		int h = 0;
		int j = this.realSize();

		for (int i = 0; j-- != 0; i++) {
			while (this.key[i] == 0) {
				i++;
			}

			h += this.key[i];
		}

		return h;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		ByteIterator i = this.iterator();
		s.defaultWriteObject();
		int j = this.size;

		while (j-- != 0) {
			s.writeByte(i.nextByte());
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.n = HashCommon.arraySize(this.size, this.f);
		this.maxFill = HashCommon.maxFill(this.n, this.f);
		this.mask = this.n - 1;
		byte[] key = this.key = new byte[this.n + 1];
		long[] link = this.link = new long[this.n + 1];
		int prev = -1;
		this.first = this.last = -1;
		int i = this.size;

		while (i-- != 0) {
			byte k = s.readByte();
			int pos;
			if (k == 0) {
				pos = this.n;
				this.containsNull = true;
			} else if (key[pos = HashCommon.mix(k) & this.mask] != 0) {
				while (key[pos = pos + 1 & this.mask] != 0) {
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

	private class SetIterator implements ByteListIterator {
		int prev = -1;
		int next = -1;
		int curr = -1;
		int index = -1;

		SetIterator() {
			this.next = ByteLinkedOpenHashSet.this.first;
			this.index = 0;
		}

		SetIterator(byte from) {
			if (from == 0) {
				if (ByteLinkedOpenHashSet.this.containsNull) {
					this.next = (int)ByteLinkedOpenHashSet.this.link[ByteLinkedOpenHashSet.this.n];
					this.prev = ByteLinkedOpenHashSet.this.n;
				} else {
					throw new NoSuchElementException("The key " + from + " does not belong to this set.");
				}
			} else if (ByteLinkedOpenHashSet.this.key[ByteLinkedOpenHashSet.this.last] == from) {
				this.prev = ByteLinkedOpenHashSet.this.last;
				this.index = ByteLinkedOpenHashSet.this.size;
			} else {
				byte[] key = ByteLinkedOpenHashSet.this.key;

				for (int pos = HashCommon.mix(from) & ByteLinkedOpenHashSet.this.mask; key[pos] != 0; pos = pos + 1 & ByteLinkedOpenHashSet.this.mask) {
					if (key[pos] == from) {
						this.next = (int)ByteLinkedOpenHashSet.this.link[pos];
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

		@Override
		public byte nextByte() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.curr = this.next;
				this.next = (int)ByteLinkedOpenHashSet.this.link[this.curr];
				this.prev = this.curr;
				if (this.index >= 0) {
					this.index++;
				}

				return ByteLinkedOpenHashSet.this.key[this.curr];
			}
		}

		@Override
		public byte previousByte() {
			if (!this.hasPrevious()) {
				throw new NoSuchElementException();
			} else {
				this.curr = this.prev;
				this.prev = (int)(ByteLinkedOpenHashSet.this.link[this.curr] >>> 32);
				this.next = this.curr;
				if (this.index >= 0) {
					this.index--;
				}

				return ByteLinkedOpenHashSet.this.key[this.curr];
			}
		}

		private final void ensureIndexKnown() {
			if (this.index < 0) {
				if (this.prev == -1) {
					this.index = 0;
				} else if (this.next == -1) {
					this.index = ByteLinkedOpenHashSet.this.size;
				} else {
					int pos = ByteLinkedOpenHashSet.this.first;

					for (this.index = 1; pos != this.prev; this.index++) {
						pos = (int)ByteLinkedOpenHashSet.this.link[pos];
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
					this.prev = (int)(ByteLinkedOpenHashSet.this.link[this.curr] >>> 32);
				} else {
					this.next = (int)ByteLinkedOpenHashSet.this.link[this.curr];
				}

				ByteLinkedOpenHashSet.this.size--;
				if (this.prev == -1) {
					ByteLinkedOpenHashSet.this.first = this.next;
				} else {
					ByteLinkedOpenHashSet.this.link[this.prev] = ByteLinkedOpenHashSet.this.link[this.prev]
						^ (ByteLinkedOpenHashSet.this.link[this.prev] ^ (long)this.next & 4294967295L) & 4294967295L;
				}

				if (this.next == -1) {
					ByteLinkedOpenHashSet.this.last = this.prev;
				} else {
					ByteLinkedOpenHashSet.this.link[this.next] = ByteLinkedOpenHashSet.this.link[this.next]
						^ (ByteLinkedOpenHashSet.this.link[this.next] ^ ((long)this.prev & 4294967295L) << 32) & -4294967296L;
				}

				int pos = this.curr;
				this.curr = -1;
				if (pos == ByteLinkedOpenHashSet.this.n) {
					ByteLinkedOpenHashSet.this.containsNull = false;
					ByteLinkedOpenHashSet.this.key[ByteLinkedOpenHashSet.this.n] = 0;
				} else {
					byte[] key = ByteLinkedOpenHashSet.this.key;

					label61:
					while (true) {
						int last = pos;

						byte curr;
						for (pos = pos + 1 & ByteLinkedOpenHashSet.this.mask; (curr = key[pos]) != 0; pos = pos + 1 & ByteLinkedOpenHashSet.this.mask) {
							int slot = HashCommon.mix(curr) & ByteLinkedOpenHashSet.this.mask;
							if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
								key[last] = curr;
								if (this.next == pos) {
									this.next = last;
								}

								if (this.prev == pos) {
									this.prev = last;
								}

								ByteLinkedOpenHashSet.this.fixPointers(pos, last);
								continue label61;
							}
						}

						key[last] = 0;
						return;
					}
				}
			}
		}
	}
}
