package it.unimi.dsi.fastutil.bytes;

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

public class ByteArrayFrontCodedList extends AbstractObjectList<byte[]> implements Serializable, Cloneable, RandomAccess {
	private static final long serialVersionUID = 1L;
	protected final int n;
	protected final int ratio;
	protected final byte[][] array;
	protected transient long[] p;

	public ByteArrayFrontCodedList(Iterator<byte[]> arrays, int ratio) {
		if (ratio < 1) {
			throw new IllegalArgumentException("Illegal ratio (" + ratio + ")");
		} else {
			byte[][] array = ByteBigArrays.EMPTY_BIG_ARRAY;
			long[] p = LongArrays.EMPTY_ARRAY;
			byte[][] a = new byte[2][];
			long curSize = 0L;
			int n = 0;

			for (int b = 0; arrays.hasNext(); n++) {
				a[b] = (byte[])arrays.next();
				int length = a[b].length;
				if (n % ratio == 0) {
					p = LongArrays.grow(p, n / ratio + 1);
					p[n / ratio] = curSize;
					array = ByteBigArrays.grow(array, curSize + (long)count(length) + (long)length, curSize);
					curSize += (long)writeInt(array, length, curSize);
					ByteBigArrays.copyToBig(a[b], 0, array, curSize, (long)length);
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
					array = ByteBigArrays.grow(array, curSize + (long)count(length) + (long)count(common) + (long)length, curSize);
					curSize += (long)writeInt(array, length, curSize);
					curSize += (long)writeInt(array, common, curSize);
					ByteBigArrays.copyToBig(a[b], common, array, curSize, (long)length);
					curSize += (long)length;
				}

				b = 1 - b;
			}

			this.n = n;
			this.ratio = ratio;
			this.array = ByteBigArrays.trim(array, curSize);
			this.p = LongArrays.trim(p, (n + ratio - 1) / ratio);
		}
	}

	public ByteArrayFrontCodedList(Collection<byte[]> c, int ratio) {
		this(c.iterator(), ratio);
	}

	private static int readInt(byte[][] a, long pos) {
		byte b0 = ByteBigArrays.get(a, pos);
		if (b0 >= 0) {
			return b0;
		} else {
			byte b1 = ByteBigArrays.get(a, pos + 1L);
			if (b1 >= 0) {
				return -b0 - 1 << 7 | b1;
			} else {
				byte b2 = ByteBigArrays.get(a, pos + 2L);
				if (b2 >= 0) {
					return -b0 - 1 << 14 | -b1 - 1 << 7 | b2;
				} else {
					byte b3 = ByteBigArrays.get(a, pos + 3L);
					return b3 >= 0
						? -b0 - 1 << 21 | -b1 - 1 << 14 | -b2 - 1 << 7 | b3
						: -b0 - 1 << 28 | -b1 - 1 << 21 | -b2 - 1 << 14 | -b3 - 1 << 7 | ByteBigArrays.get(a, pos + 4L);
				}
			}
		}
	}

	private static int count(int length) {
		if (length < 128) {
			return 1;
		} else if (length < 16384) {
			return 2;
		} else if (length < 2097152) {
			return 3;
		} else {
			return length < 268435456 ? 4 : 5;
		}
	}

	private static int writeInt(byte[][] a, int length, long pos) {
		int count = count(length);
		ByteBigArrays.set(a, pos + (long)count - 1L, (byte)(length & 127));
		if (count != 1) {
			int i = count - 1;

			while (i-- != 0) {
				length >>>= 7;
				ByteBigArrays.set(a, pos + (long)i, (byte)(-(length & 127) - 1));
			}
		}

		return count;
	}

	public int ratio() {
		return this.ratio;
	}

	private int length(int index) {
		byte[][] array = this.array;
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

	private int extract(int index, byte[] a, int offset, int length) {
		int delta = index % this.ratio;
		long startPos = this.p[index / this.ratio];
		long pos = startPos;
		int arrayLength = readInt(this.array, startPos);
		int currLen = 0;
		if (delta == 0) {
			pos = this.p[index / this.ratio] + (long)count(arrayLength);
			ByteBigArrays.copyFromBig(this.array, pos, a, offset, Math.min(length, arrayLength));
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
					ByteBigArrays.copyFromBig(this.array, prevArrayPos, a, currLen + offset, actualCommon - currLen);
					currLen = actualCommon;
				}
			}

			if (currLen < length) {
				ByteBigArrays.copyFromBig(this.array, pos + (long)count(arrayLength) + (long)count(common), a, currLen + offset, Math.min(arrayLength, length - currLen));
			}

			return arrayLength + common;
		}
	}

	public byte[] get(int index) {
		return this.getArray(index);
	}

	public byte[] getArray(int index) {
		this.ensureRestrictedIndex(index);
		int length = this.length(index);
		byte[] a = new byte[length];
		this.extract(index, a, 0, length);
		return a;
	}

	public int get(int index, byte[] a, int offset, int length) {
		this.ensureRestrictedIndex(index);
		ByteArrays.ensureOffsetLength(a, offset, length);
		int arrayLength = this.extract(index, a, offset, length);
		return length >= arrayLength ? arrayLength : length - arrayLength;
	}

	public int get(int index, byte[] a) {
		return this.get(index, a, 0, a.length);
	}

	public int size() {
		return this.n;
	}

	@Override
	public ObjectListIterator<byte[]> listIterator(int start) {
		this.ensureIndex(start);
		return new ObjectListIterator<byte[]>() {
			byte[] s = ByteArrays.EMPTY_ARRAY;
			int i = 0;
			long pos = 0L;
			boolean inSync;

			{
				if (start != 0) {
					if (start == ByteArrayFrontCodedList.this.n) {
						this.i = start;
					} else {
						this.pos = ByteArrayFrontCodedList.this.p[start / ByteArrayFrontCodedList.this.ratio];
						int j = start % ByteArrayFrontCodedList.this.ratio;
						this.i = start - j;

						while (j-- != 0) {
							this.next();
						}
					}
				}
			}

			public boolean hasNext() {
				return this.i < ByteArrayFrontCodedList.this.n;
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

			public byte[] next() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					int length;
					if (this.i % ByteArrayFrontCodedList.this.ratio == 0) {
						this.pos = ByteArrayFrontCodedList.this.p[this.i / ByteArrayFrontCodedList.this.ratio];
						length = ByteArrayFrontCodedList.readInt(ByteArrayFrontCodedList.this.array, this.pos);
						this.s = ByteArrays.ensureCapacity(this.s, length, 0);
						ByteBigArrays.copyFromBig(ByteArrayFrontCodedList.this.array, this.pos + (long)ByteArrayFrontCodedList.count(length), this.s, 0, length);
						this.pos = this.pos + (long)(length + ByteArrayFrontCodedList.count(length));
						this.inSync = true;
					} else if (this.inSync) {
						length = ByteArrayFrontCodedList.readInt(ByteArrayFrontCodedList.this.array, this.pos);
						int common = ByteArrayFrontCodedList.readInt(ByteArrayFrontCodedList.this.array, this.pos + (long)ByteArrayFrontCodedList.count(length));
						this.s = ByteArrays.ensureCapacity(this.s, length + common, common);
						ByteBigArrays.copyFromBig(
							ByteArrayFrontCodedList.this.array,
							this.pos + (long)ByteArrayFrontCodedList.count(length) + (long)ByteArrayFrontCodedList.count(common),
							this.s,
							common,
							length
						);
						this.pos = this.pos + (long)(ByteArrayFrontCodedList.count(length) + ByteArrayFrontCodedList.count(common) + length);
						length += common;
					} else {
						this.s = ByteArrays.ensureCapacity(this.s, length = ByteArrayFrontCodedList.this.length(this.i), 0);
						ByteArrayFrontCodedList.this.extract(this.i, this.s, 0, length);
					}

					this.i++;
					return ByteArrays.copy(this.s, 0, length);
				}
			}

			public byte[] previous() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					this.inSync = false;
					return ByteArrayFrontCodedList.this.getArray(--this.i);
				}
			}
		};
	}

	public ByteArrayFrontCodedList clone() {
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

			s.append(ByteArrayList.wrap(this.getArray(i)).toString());
		}

		s.append("]");
		return s.toString();
	}

	protected long[] rebuildPointerArray() {
		long[] p = new long[(this.n + this.ratio - 1) / this.ratio];
		byte[][] a = this.array;
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
