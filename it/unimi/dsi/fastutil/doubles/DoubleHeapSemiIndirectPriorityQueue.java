package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.NoSuchElementException;

public class DoubleHeapSemiIndirectPriorityQueue implements DoubleIndirectPriorityQueue {
	protected final double[] refArray;
	protected int[] heap = IntArrays.EMPTY_ARRAY;
	protected int size;
	protected DoubleComparator c;

	public DoubleHeapSemiIndirectPriorityQueue(double[] refArray, int capacity, DoubleComparator c) {
		if (capacity > 0) {
			this.heap = new int[capacity];
		}

		this.refArray = refArray;
		this.c = c;
	}

	public DoubleHeapSemiIndirectPriorityQueue(double[] refArray, int capacity) {
		this(refArray, capacity, null);
	}

	public DoubleHeapSemiIndirectPriorityQueue(double[] refArray, DoubleComparator c) {
		this(refArray, refArray.length, c);
	}

	public DoubleHeapSemiIndirectPriorityQueue(double[] refArray) {
		this(refArray, refArray.length, null);
	}

	public DoubleHeapSemiIndirectPriorityQueue(double[] refArray, int[] a, int size, DoubleComparator c) {
		this(refArray, 0, c);
		this.heap = a;
		this.size = size;
		DoubleSemiIndirectHeaps.makeHeap(refArray, a, size, c);
	}

	public DoubleHeapSemiIndirectPriorityQueue(double[] refArray, int[] a, DoubleComparator c) {
		this(refArray, a, a.length, c);
	}

	public DoubleHeapSemiIndirectPriorityQueue(double[] refArray, int[] a, int size) {
		this(refArray, a, size, null);
	}

	public DoubleHeapSemiIndirectPriorityQueue(double[] refArray, int[] a) {
		this(refArray, a, a.length);
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
		if (this.size == this.heap.length) {
			this.heap = IntArrays.grow(this.heap, this.size + 1);
		}

		this.heap[this.size++] = x;
		DoubleSemiIndirectHeaps.upHeap(this.refArray, this.heap, this.size, this.size - 1, this.c);
	}

	@Override
	public int dequeue() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			int result = this.heap[0];
			this.heap[0] = this.heap[--this.size];
			if (this.size != 0) {
				DoubleSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
			}

			return result;
		}
	}

	@Override
	public int first() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			return this.heap[0];
		}
	}

	@Override
	public void changed() {
		DoubleSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
	}

	@Override
	public void allChanged() {
		DoubleSemiIndirectHeaps.makeHeap(this.refArray, this.heap, this.size, this.c);
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
		this.heap = IntArrays.trim(this.heap, this.size);
	}

	@Override
	public DoubleComparator comparator() {
		return this.c;
	}

	@Override
	public int front(int[] a) {
		return this.c == null
			? DoubleSemiIndirectHeaps.front(this.refArray, this.heap, this.size, a)
			: DoubleSemiIndirectHeaps.front(this.refArray, this.heap, this.size, a, this.c);
	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("[");

		for (int i = 0; i < this.size; i++) {
			if (i != 0) {
				s.append(", ");
			}

			s.append(this.refArray[this.heap[i]]);
		}

		s.append("]");
		return s.toString();
	}
}
