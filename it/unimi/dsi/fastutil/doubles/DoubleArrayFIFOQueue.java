package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.NoSuchElementException;

public class DoubleArrayFIFOQueue implements DoublePriorityQueue, Serializable {
	private static final long serialVersionUID = 0L;
	public static final int INITIAL_CAPACITY = 4;
	protected transient double[] array;
	protected transient int length;
	protected transient int start;
	protected transient int end;

	public DoubleArrayFIFOQueue(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
		} else {
			this.array = new double[Math.max(1, capacity)];
			this.length = this.array.length;
		}
	}

	public DoubleArrayFIFOQueue() {
		this(4);
	}

	@Override
	public DoubleComparator comparator() {
		return null;
	}

	@Override
	public double dequeueDouble() {
		if (this.start == this.end) {
			throw new NoSuchElementException();
		} else {
			double t = this.array[this.start];
			if (++this.start == this.length) {
				this.start = 0;
			}

			this.reduce();
			return t;
		}
	}

	public double dequeueLastDouble() {
		if (this.start == this.end) {
			throw new NoSuchElementException();
		} else {
			if (this.end == 0) {
				this.end = this.length;
			}

			double t = this.array[--this.end];
			this.reduce();
			return t;
		}
	}

	private final void resize(int size, int newLength) {
		double[] newArray = new double[newLength];
		if (this.start >= this.end) {
			if (size != 0) {
				System.arraycopy(this.array, this.start, newArray, 0, this.length - this.start);
				System.arraycopy(this.array, 0, newArray, this.length - this.start, this.end);
			}
		} else {
			System.arraycopy(this.array, this.start, newArray, 0, this.end - this.start);
		}

		this.start = 0;
		this.end = size;
		this.array = newArray;
		this.length = newLength;
	}

	private final void expand() {
		this.resize(this.length, (int)Math.min(2147483639L, 2L * (long)this.length));
	}

	private final void reduce() {
		int size = this.size();
		if (this.length > 4 && size <= this.length / 4) {
			this.resize(size, this.length / 2);
		}
	}

	@Override
	public void enqueue(double x) {
		this.array[this.end++] = x;
		if (this.end == this.length) {
			this.end = 0;
		}

		if (this.end == this.start) {
			this.expand();
		}
	}

	public void enqueueFirst(double x) {
		if (this.start == 0) {
			this.start = this.length;
		}

		this.array[--this.start] = x;
		if (this.end == this.start) {
			this.expand();
		}
	}

	@Override
	public double firstDouble() {
		if (this.start == this.end) {
			throw new NoSuchElementException();
		} else {
			return this.array[this.start];
		}
	}

	@Override
	public double lastDouble() {
		if (this.start == this.end) {
			throw new NoSuchElementException();
		} else {
			return this.array[(this.end == 0 ? this.length : this.end) - 1];
		}
	}

	@Override
	public void clear() {
		this.start = this.end = 0;
	}

	public void trim() {
		int size = this.size();
		double[] newArray = new double[size + 1];
		if (this.start <= this.end) {
			System.arraycopy(this.array, this.start, newArray, 0, this.end - this.start);
		} else {
			System.arraycopy(this.array, this.start, newArray, 0, this.length - this.start);
			System.arraycopy(this.array, 0, newArray, this.length - this.start, this.end);
		}

		this.start = 0;
		this.length = (this.end = size) + 1;
		this.array = newArray;
	}

	@Override
	public int size() {
		int apparentLength = this.end - this.start;
		return apparentLength >= 0 ? apparentLength : this.length + apparentLength;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		int size = this.size();
		s.writeInt(size);
		int i = this.start;

		while (size-- != 0) {
			s.writeDouble(this.array[i++]);
			if (i == this.length) {
				i = 0;
			}
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.end = s.readInt();
		this.array = new double[this.length = HashCommon.nextPowerOfTwo(this.end + 1)];

		for (int i = 0; i < this.end; i++) {
			this.array[i] = s.readDouble();
		}
	}
}
