package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.objects.AbstractObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

public class LongArrayFrontCodedList extends AbstractObjectList<long[]> implements Serializable, Cloneable, RandomAccess {
	private static final long serialVersionUID = 1L;
	protected final int n;
	protected final int ratio;
	protected final long[][] array;
	protected transient long[] p;

	public LongArrayFrontCodedList(Iterator<long[]> arrays, int ratio) {
		if (ratio < 1) {
			throw new IllegalArgumentException("Illegal ratio (" + ratio + ")");
		} else {
			long[][] array = LongBigArrays.EMPTY_BIG_ARRAY;
			long[] p = LongArrays.EMPTY_ARRAY;
			long[][] a = new long[2][];
			long curSize = 0L;
			int n = 0;

			for (int b = 0; arrays.hasNext(); n++) {
				a[b] = (long[])arrays.next();
				int length = a[b].length;
				if (n % ratio == 0) {
					p = LongArrays.grow(p, n / ratio + 1);
					p[n / ratio] = curSize;
					array = LongBigArrays.grow(array, curSize + (long)count(length) + (long)length, curSize);
					curSize += (long)writeInt(array, length, curSize);
					LongBigArrays.copyToBig(a[b], 0, array, curSize, (long)length);
					curSize += (long)length;
				} else {
					int minLength = a[1 - b].length;
					if (length < minLength) {
						minLength = length;
					}

					int common = 0;

					while (common < minLength && a[0][common] == a[1][common]) {
						common++;
					}

					length -= common;
					array = LongBigArrays.grow(array, curSize + (long)count(length) + (long)count(common) + (long)length, curSize);
					curSize += (long)writeInt(array, length, curSize);
					curSize += (long)writeInt(array, common, curSize);
					LongBigArrays.copyToBig(a[b], common, array, curSize, (long)length);
					curSize += (long)length;
				}

				b = 1 - b;
			}

			this.n = n;
			this.ratio = ratio;
			this.array = LongBigArrays.trim(array, curSize);
			this.p = LongArrays.trim(p, (n + ratio - 1) / ratio);
		}
	}

	public LongArrayFrontCodedList(Collection<long[]> c, int ratio) {
		this(c.iterator(), ratio);
	}

	private static int readInt(long[][] a, long pos) {
		return (int)LongBigArrays.get(a, pos);
	}

	private static int count(int length) {
		return 1;
	}

	private static int writeInt(long[][] a, int length, long pos) {
		LongBigArrays.set(a, pos, (long)length);
		return 1;
	}

	public int ratio() {
		return this.ratio;
	}

	private int length(int index) {
		long[][] array = this.array;
		int delta = index % this.ratio;
		long pos = this.p[index / this.ratio];
		int length = readInt(array, pos);
		if (delta == 0) {
			return length;
		} else {
			pos += (long)(count(length) + length);
			length = readInt(array, pos);
			int common = readInt(array, pos + (long)count(length));

			for (int i = 0; i < delta - 1; i++) {
				pos += (long)(count(length) + count(common) + length);
				length = readInt(array, pos);
				common = readInt(array, pos + (long)count(length));
			}

			return length + common;
		}
	}

	public int arrayLength(int index) {
		this.ensureRestrictedIndex(index);
		return this.length(index);
	}

	private int extract(int index, long[] a, int offset, int length) {
		int delta = index % this.ratio;
		long startPos = this.p[index / this.ratio];
		long pos = startPos;
		int arrayLength = readInt(this.array, startPos);
		int currLen = 0;
		if (delta == 0) {
			pos = this.p[index / this.ratio] + (long)count(arrayLength);
			LongBigArrays.copyFromBig(this.array, pos, a, offset, Math.min(length, arrayLength));
			return arrayLength;
		} else {
			int common = 0;

			for (int i = 0; i < delta; i++) {
				long prevArrayPos = pos + (long)count(arrayLength) + (long)(i != 0 ? count(common) : 0);
				pos = prevArrayPos + (long)arrayLength;
				arrayLength = readInt(this.array, pos);
				common = readInt(this.array, pos + (long)count(arrayLength));
				int actualCommon = Math.min(common, length);
				if (actualCommon <= currLen) {
					currLen = actualCommon;
				} else {
					LongBigArrays.copyFromBig(this.array, prevArrayPos, a, currLen + offset, actualCommon - currLen);
					currLen = actualCommon;
				}
			}

			if (currLen < length) {
				LongBigArrays.copyFromBig(this.array, pos + (long)count(arrayLength) + (long)count(common), a, currLen + offset, Math.min(arrayLength, length - currLen));
			}

			return arrayLength + common;
		}
	}

	public long[] get(int index) {
		return this.getArray(index);
	}

	public long[] getArray(int index) {
		this.ensureRestrictedIndex(index);
		int length = this.length(index);
		long[] a = new long[length];
		this.extract(index, a, 0, length);
		return a;
	}

	public int get(int index, long[] a, int offset, int length) {
		this.ensureRestrictedIndex(index);
		LongArrays.ensureOffsetLength(a, offset, length);
		int arrayLength = this.extract(index, a, offset, length);
		return length >= arrayLength ? arrayLength : length - arrayLength;
	}

	public int get(int index, long[] a) {
		return this.get(index, a, 0, a.length);
	}

	public int size() {
		return this.n;
	}

	@Override
	public ObjectListIterator<long[]> listIterator(int start) {
		this.ensureIndex(start);
		return new ObjectListIterator<long[]>() {
			long[] s = LongArrays.EMPTY_ARRAY;
			int i = 0;
			long pos = 0L;
			boolean inSync;

			{
				if (start != 0) {
					if (start == LongArrayFrontCodedList.this.n) {
						this.i = start;
					} else {
						this.pos = LongArrayFrontCodedList.this.p[start / LongArrayFrontCodedList.this.ratio];
						int j = start % LongArrayFrontCodedList.this.ratio;
						this.i = start - j;

						while (j-- != 0) {
							this.next();
						}
					}
				}
			}

			public boolean hasNext() {
				return this.i < LongArrayFrontCodedList.this.n;
			}

			@Override
			public boolean hasPrevious() {
				return this.i > 0;
			}

			public int previousIndex() {
				return this.i - 1;
			}

			public int nextIndex() {
				return this.i;
			}

			public long[] next() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					int length;
					if (this.i % LongArrayFrontCodedList.this.ratio == 0) {
						this.pos = LongArrayFrontCodedList.this.p[this.i / LongArrayFrontCodedList.this.ratio];
						length = LongArrayFrontCodedList.readInt(LongArrayFrontCodedList.this.array, this.pos);
						this.s = LongArrays.ensureCapacity(this.s, length, 0);
						LongBigArrays.copyFromBig(LongArrayFrontCodedList.this.array, this.pos + (long)LongArrayFrontCodedList.count(length), this.s, 0, length);
						this.pos = this.pos + (long)(length + LongArrayFrontCodedList.count(length));
						this.inSync = true;
					} else if (this.inSync) {
						length = LongArrayFrontCodedList.readInt(LongArrayFrontCodedList.this.array, this.pos);
						int common = LongArrayFrontCodedList.readInt(LongArrayFrontCodedList.this.array, this.pos + (long)LongArrayFrontCodedList.count(length));
						this.s = LongArrays.ensureCapacity(this.s, length + common, common);
						LongBigArrays.copyFromBig(
							LongArrayFrontCodedList.this.array,
							this.pos + (long)LongArrayFrontCodedList.count(length) + (long)LongArrayFrontCodedList.count(common),
							this.s,
							common,
							length
						);
						this.pos = this.pos + (long)(LongArrayFrontCodedList.count(length) + LongArrayFrontCodedList.count(common) + length);
						length += common;
					} else {
						this.s = LongArrays.ensureCapacity(this.s, length = LongArrayFrontCodedList.this.length(this.i), 0);
						LongArrayFrontCodedList.this.extract(this.i, this.s, 0, length);
					}

					this.i++;
					return LongArrays.copy(this.s, 0, length);
				}
			}

			public long[] previous() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					this.inSync = false;
					return LongArrayFrontCodedList.this.getArray(--this.i);
				}
			}
		};
	}

	public LongArrayFrontCodedList clone() {
		return this;
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("[");

		for (int i = 0; i < this.n; i++) {
			if (i != 0) {
				s.append(", ");
			}

			s.append(LongArrayList.wrap(this.getArray(i)).toString());
		}

		s.append("]");
		return s.toString();
	}

	protected long[] rebuildPointerArray() {
		long[] p = new long[(this.n + this.ratio - 1) / this.ratio];
		long[][] a = this.array;
		long pos = 0L;
		int i = 0;
		int j = 0;

		for (int skip = this.ratio - 1; i < this.n; i++) {
			int length = readInt(a, pos);
			int count = count(length);
			if (++skip == this.ratio) {
				skip = 0;
				p[j++] = pos;
				pos += (long)(count + length);
			} else {
				pos += (long)(count + count(readInt(a, pos + (long)count)) + length);
			}
		}

		return p;
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.p = this.rebuildPointerArray();
	}
}
