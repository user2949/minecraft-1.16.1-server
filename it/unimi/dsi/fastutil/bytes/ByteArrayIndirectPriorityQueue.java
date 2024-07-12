package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.NoSuchElementException;

public class ByteArrayIndirectPriorityQueue implements ByteIndirectPriorityQueue {
	protected byte[] refArray;
	protected int[] array = IntArrays.EMPTY_ARRAY;
	protected int size;
	protected ByteComparator c;
	protected int firstIndex;
	protected boolean firstIndexValid;

	public ByteArrayIndirectPriorityQueue(byte[] refArray, int capacity, ByteComparator c) {
		if (capacity > 0) {
			this.array = new int[capacity];
		}

		this.refArray = refArray;
		this.c = c;
	}

	public ByteArrayIndirectPriorityQueue(byte[] refArray, int capacity) {
		this(refArray, capacity, null);
	}

	public ByteArrayIndirectPriorityQueue(byte[] refArray, ByteComparator c) {
		this(refArray, refArray.length, c);
	}

	public ByteArrayIndirectPriorityQueue(byte[] refArray) {
		this(refArray, refArray.length, null);
	}

	public ByteArrayIndirectPriorityQueue(byte[] refArray, int[] a, int size, ByteComparator c) {
		this(refArray, 0, c);
		this.array = a;
		this.size = size;
	}

	public ByteArrayIndirectPriorityQueue(byte[] refArray, int[] a, ByteComparator c) {
		this(refArray, a, a.length, c);
	}

	public ByteArrayIndirectPriorityQueue(byte[] refArray, int[] a, int size) {
		this(refArray, a, size, null);
	}

	public ByteArrayIndirectPriorityQueue(byte[] refArray, int[] a) {
		this(refArray, a, a.length);
	}

	private int findFirst() {
		if (this.firstIndexValid) {
			return this.firstIndex;
		} else {
			this.firstIndexValid = true;
			int i = this.size;
			int firstIndex = --i;
			byte first = this.refArray[this.array[i]];
			if (this.c == null) {
				while (i-- != 0) {
					if (this.refArray[this.array[i]] < first) {
						firstIndex = i;
						first = this.refArray[this.array[i]];
					}
				}
			} else {
				while (i-- != 0) {
					if (this.c.compare(this.refArray[this.array[i]], first) < 0) {
						firstIndex = i;
						first = this.refArray[this.array[i]];
					}
				}
			}

			return this.firstIndex = firstIndex;
		}
	}

	private int findLast() {
		int i = this.size;
		int lastIndex = --i;
		byte last = this.refArray[this.array[i]];
		if (this.c == null) {
			while (i-- != 0) {
				if (last < this.refArray[this.array[i]]) {
					lastIndex = i;
					last = this.refArray[this.array[i]];
				}
			}
		} else {
			while (i-- != 0) {
				if (this.c.compare(last, this.refArray[this.array[i]]) < 0) {
					lastIndex = i;
					last = this.refArray[this.array[i]];
				}
			}
		}

		return lastIndex;
	}

	protected final void ensureNonEmpty() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
	}

	protected void ensureElement(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
		} else if (index >= this.refArray.length) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is larger than or equal to reference array size (" + this.refArray.length + ")");
		}
	}

	@Override
	public void enqueue(int x) {
		this.ensureElement(x);
		if (this.size == this.array.length) {
			this.array = IntArrays.grow(this.array, this.size + 1);
		}

		if (this.firstIndexValid) {
			if (this.c == null) {
				if (this.refArray[x] < this.refArray[this.array[this.firstIndex]]) {
					this.firstIndex = this.size;
				}
			} else if (this.c.compare(this.refArray[x], this.refArray[this.array[this.firstIndex]]) < 0) {
				this.firstIndex = this.size;
			}
		} else {
			this.firstIndexValid = false;
		}

		this.array[this.size++] = x;
	}

	@Override
	public int dequeue() {
		this.ensureNonEmpty();
		int firstIndex = this.findFirst();
		int result = this.array[firstIndex];
		if (--this.size != 0) {
			System.arraycopy(this.array, firstIndex + 1, this.array, firstIndex, this.size - firstIndex);
		}

		this.firstIndexValid = false;
		return result;
	}

	@Override
	public int first() {
		this.ensureNonEmpty();
		return this.array[this.findFirst()];
	}

	@Override
	public int last() {
		this.ensureNonEmpty();
		return this.array[this.findLast()];
	}

	@Override
	public void changed() {
		this.ensureNonEmpty();
		this.firstIndexValid = false;
	}

	@Override
	public void changed(int index) {
		this.ensureElement(index);
		if (index == this.firstIndex) {
			this.firstIndexValid = false;
		}
	}

	@Override
	public void allChanged() {
		this.firstIndexValid = false;
	}

	@Override
	public boolean remove(int index) {
		this.ensureElement(index);
		int[] a = this.array;
		int i = this.size;

		while (i-- != 0 && a[i] != index) {
		}

		if (i < 0) {
			return false;
		} else {
			this.firstIndexValid = false;
			if (--this.size != 0) {
				System.arraycopy(a, i + 1, a, i, this.size - i);
			}

			return true;
		}
	}

	@Override
	public int front(int[] a) {
		byte top = this.refArray[this.array[this.findFirst()]];
		int i = this.size;
		int c = 0;

		while (i-- != 0) {
			if (top == this.refArray[this.array[i]]) {
				a[c++] = this.array[i];
			}
		}

		return c;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public void clear() {
		this.size = 0;
		this.firstIndexValid = false;
	}

	public void trim() {
		this.array = IntArrays.trim(this.array, this.size);
	}

	@Override
	public ByteComparator comparator() {
		return this.c;
	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("[");

		for (int i = 0; i < this.size; i++) {
			if (i != 0) {
				s.append(", ");
			}

			s.append(this.refArray[this.array[i]]);
		}

		s.append("]");
		return s.toString();
	}
}
