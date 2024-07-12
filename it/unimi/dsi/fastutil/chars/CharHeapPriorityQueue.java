package it.unimi.dsi.fastutil.chars;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CharHeapPriorityQueue implements CharPriorityQueue, Serializable {
	private static final long serialVersionUID = 1L;
	protected transient char[] heap = CharArrays.EMPTY_ARRAY;
	protected int size;
	protected CharComparator c;

	public CharHeapPriorityQueue(int capacity, CharComparator c) {
		if (capacity > 0) {
			this.heap = new char[capacity];
		}

		this.c = c;
	}

	public CharHeapPriorityQueue(int capacity) {
		this(capacity, null);
	}

	public CharHeapPriorityQueue(CharComparator c) {
		this(0, c);
	}

	public CharHeapPriorityQueue() {
		this(0, null);
	}

	public CharHeapPriorityQueue(char[] a, int size, CharComparator c) {
		this(c);
		this.heap = a;
		this.size = size;
		CharHeaps.makeHeap(a, size, c);
	}

	public CharHeapPriorityQueue(char[] a, CharComparator c) {
		this(a, a.length, c);
	}

	public CharHeapPriorityQueue(char[] a, int size) {
		this(a, size, null);
	}

	public CharHeapPriorityQueue(char[] a) {
		this(a, a.length);
	}

	public CharHeapPriorityQueue(CharCollection collection, CharComparator c) {
		this(collection.toCharArray(), c);
	}

	public CharHeapPriorityQueue(CharCollection collection) {
		this(collection, null);
	}

	public CharHeapPriorityQueue(Collection<? extends Character> collection, CharComparator c) {
		this(collection.size(), c);
		Iterator<? extends Character> iterator = collection.iterator();
		int size = collection.size();

		for (int i = 0; i < size; i++) {
			this.heap[i] = (Character)iterator.next();
		}
	}

	public CharHeapPriorityQueue(Collection<? extends Character> collection) {
		this(collection, null);
	}

	@Override
	public void enqueue(char x) {
		if (this.size == this.heap.length) {
			this.heap = CharArrays.grow(this.heap, this.size + 1);
		}

		this.heap[this.size++] = x;
		CharHeaps.upHeap(this.heap, this.size, this.size - 1, this.c);
	}

	@Override
	public char dequeueChar() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			char result = this.heap[0];
			this.heap[0] = this.heap[--this.size];
			if (this.size != 0) {
				CharHeaps.downHeap(this.heap, this.size, 0, this.c);
			}

			return result;
		}
	}

	@Override
	public char firstChar() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			return this.heap[0];
		}
	}

	@Override
	public void changed() {
		CharHeaps.downHeap(this.heap, this.size, 0, this.c);
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public void clear() {
		this.size = 0;
	}

	public void trim() {
		this.heap = CharArrays.trim(this.heap, this.size);
	}

	@Override
	public CharComparator comparator() {
		return this.c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeInt(this.heap.length);

		for (int i = 0; i < this.size; i++) {
			s.writeChar(this.heap[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.heap = new char[s.readInt()];

		for (int i = 0; i < this.size; i++) {
			this.heap[i] = s.readChar();
		}
	}
}
