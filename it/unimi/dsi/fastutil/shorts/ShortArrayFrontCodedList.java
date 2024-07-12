package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.objects.AbstractObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

public class ShortArrayFrontCodedList extends AbstractObjectList<short[]> implements Serializable, Cloneable, RandomAccess {
	private static final long serialVersionUID = 1L;
	protected final int n;
	protected final int ratio;
	protected final short[][] array;
	protected transient long[] p;

	public ShortArrayFrontCodedList(Iterator<short[]> arrays, int ratio) {
		if (ratio < 1) {
			throw new IllegalArgumentException("Illegal ratio (" + ratio + ")");
		} else {
			short[][] array = ShortBigArrays.EMPTY_BIG_ARRAY;
			long[] p = LongArrays.EMPTY_ARRAY;
			short[][] a = new short[2][];
			long curSize = 0L;
			int n = 0;

			for (int b = 0; arrays.hasNext(); n++) {
				a[b] = (short[])arrays.next();
				int length = a[b].length;
				if (n % ratio == 0) {
					p = LongArrays.grow(p, n / ratio + 1);
					p[n / ratio] = curSize;
					array = ShortBigArrays.grow(array, curSize + (long)count(length) + (long)length, curSize);
					curSize += (long)writeInt(array, length, curSize);
					ShortBigArrays.copyToBig(a[b], 0, array, curSize, (long)length);
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
					array = ShortBigArrays.grow(array, curSize + (long)count(length) + (long)count(common) + (long)length, curSize);
					curSize += (long)writeInt(array, length, curSize);
					curSize += (long)writeInt(array, common, curSize);
					ShortBigArrays.copyToBig(a[b], common, array, curSize, (long)length);
					curSize += (long)length;
				}

				b = 1 - b;
			}

			this.n = n;
			this.ratio = ratio;
			this.array = ShortBigArrays.trim(array, curSize);
			this.p = LongArrays.trim(p, (n + ratio - 1) / ratio);
		}
	}

	public ShortArrayFrontCodedList(Collection<short[]> c, int ratio) {
		this(c.iterator(), ratio);
	}

	private static int readInt(short[][] a, long pos) {
		short s0 = ShortBigArrays.get(a, pos);
		return s0 >= 0 ? s0 : s0 << 16 | ShortBigArrays.get(a, pos + 1L) & 65535;
	}

	private static int count(int length) {
		return length < 32768 ? 1 : 2;
	}

	private static int writeInt(short[][] a, int length, long pos) {
		if (length < 32768) {
			ShortBigArrays.set(a, pos, (short)length);
			return 1;
		} else {
			ShortBigArrays.set(a, pos++, (short)(-(length >>> 16) - 1));
			ShortBigArrays.set(a, pos, (short)(length & 65535));
			return 2;
		}
	}

	public int ratio() {
		return this.ratio;
	}

	private int length(int index) {
		short[][] array = this.array;
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

	private int extract(int index, short[] a, int offset, int length) {
		int delta = index % this.ratio;
		long startPos = this.p[index / this.ratio];
		long pos = startPos;
		int arrayLength = readInt(this.array, startPos);
		int currLen = 0;
		if (delta == 0) {
			pos = this.p[index / this.ratio] + (long)count(arrayLength);
			ShortBigArrays.copyFromBig(this.array, pos, a, offset, Math.min(length, arrayLength));
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
					ShortBigArrays.copyFromBig(this.array, prevArrayPos, a, currLen + offset, actualCommon - currLen);
					currLen = actualCommon;
				}
			}

			if (currLen < length) {
				ShortBigArrays.copyFromBig(this.array, pos + (long)count(arrayLength) + (long)count(common), a, currLen + offset, Math.min(arrayLength, length - currLen));
			}

			return arrayLength + common;
		}
	}

	public short[] get(int index) {
		return this.getArray(index);
	}

	public short[] getArray(int index) {
		this.ensureRestrictedIndex(index);
		int length = this.length(index);
		short[] a = new short[length];
		this.extract(index, a, 0, length);
		return a;
	}

	public int get(int index, short[] a, int offset, int length) {
		this.ensureRestrictedIndex(index);
		ShortArrays.ensureOffsetLength(a, offset, length);
		int arrayLength = this.extract(index, a, offset, length);
		return length >= arrayLength ? arrayLength : length - arrayLength;
	}

	public int get(int index, short[] a) {
		return this.get(index, a, 0, a.length);
	}

	public int size() {
		return this.n;
	}

	@Override
	public ObjectListIterator<short[]> listIterator(int start) {
		this.ensureIndex(start);
		return new ObjectListIterator<short[]>() {
			short[] s = ShortArrays.EMPTY_ARRAY;
			int i = 0;
			long pos = 0L;
			boolean inSync;

			{
				if (start != 0) {
					if (start == ShortArrayFrontCodedList.this.n) {
						this.i = start;
					} else {
						this.pos = ShortArrayFrontCodedList.this.p[start / ShortArrayFrontCodedList.this.ratio];
						int j = start % ShortArrayFrontCodedList.this.ratio;
						this.i = start - j;

						while (j-- != 0) {
							this.next();
						}
					}
				}
			}

			public boolean hasNext() {
				return this.i < ShortArrayFrontCodedList.this.n;
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

			public short[] next() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					int length;
					if (this.i % ShortArrayFrontCodedList.this.ratio == 0) {
						this.pos = ShortArrayFrontCodedList.this.p[this.i / ShortArrayFrontCodedList.this.ratio];
						length = ShortArrayFrontCodedList.readInt(ShortArrayFrontCodedList.this.array, this.pos);
						this.s = ShortArrays.ensureCapacity(this.s, length, 0);
						ShortBigArrays.copyFromBig(ShortArrayFrontCodedList.this.array, this.pos + (long)ShortArrayFrontCodedList.count(length), this.s, 0, length);
						this.pos = this.pos + (long)(length + ShortArrayFrontCodedList.count(length));
						this.inSync = true;
					} else if (this.inSync) {
						length = ShortArrayFrontCodedList.readInt(ShortArrayFrontCodedList.this.array, this.pos);
						int common = ShortArrayFrontCodedList.readInt(ShortArrayFrontCodedList.this.array, this.pos + (long)ShortArrayFrontCodedList.count(length));
						this.s = ShortArrays.ensureCapacity(this.s, length + common, common);
						ShortBigArrays.copyFromBig(
							ShortArrayFrontCodedList.this.array,
							this.pos + (long)ShortArrayFrontCodedList.count(length) + (long)ShortArrayFrontCodedList.count(common),
							this.s,
							common,
							length
						);
						this.pos = this.pos + (long)(ShortArrayFrontCodedList.count(length) + ShortArrayFrontCodedList.count(common) + length);
						length += common;
					} else {
						this.s = ShortArrays.ensureCapacity(this.s, length = ShortArrayFrontCodedList.this.length(this.i), 0);
						ShortArrayFrontCodedList.this.extract(this.i, this.s, 0, length);
					}

					this.i++;
					return ShortArrays.copy(this.s, 0, length);
				}
			}

			public short[] previous() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					this.inSync = false;
					return ShortArrayFrontCodedList.this.getArray(--this.i);
				}
			}
		};
	}

	public ShortArrayFrontCodedList clone() {
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

			s.append(ShortArrayList.wrap(this.getArray(i)).toString());
		}

		s.append("]");
		return s.toString();
	}

	protected long[] rebuildPointerArray() {
		long[] p = new long[(this.n + this.ratio - 1) / this.ratio];
		short[][] a = this.array;
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
