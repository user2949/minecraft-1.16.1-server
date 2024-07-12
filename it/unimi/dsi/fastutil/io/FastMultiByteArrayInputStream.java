package it.unimi.dsi.fastutil.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class FastMultiByteArrayInputStream extends MeasurableInputStream implements RepositionableStream {
	public static final int SLICE_BITS = 10;
	public static final int SLICE_SIZE = 1024;
	public static final int SLICE_MASK = 1023;
	public byte[][] array;
	public byte[] current;
	public long length;
	private long position;

	public FastMultiByteArrayInputStream(MeasurableInputStream is) throws IOException {
		this(is, is.length());
	}

	public FastMultiByteArrayInputStream(InputStream is, long size) throws IOException {
		this.length = size;
		this.array = new byte[(int)((size + 1024L - 1L) / 1024L) + 1][];

		for (int i = 0; i < this.array.length - 1; i++) {
			this.array[i] = new byte[size >= 1024L ? 1024 : (int)size];
			if (BinIO.loadBytes(is, this.array[i]) != this.array[i].length) {
				throw new EOFException();
			}

			size -= (long)this.array[i].length;
		}

		this.current = this.array[0];
	}

	public FastMultiByteArrayInputStream(FastMultiByteArrayInputStream is) {
		this.array = is.array;
		this.length = is.length;
		this.current = this.array[0];
	}

	public FastMultiByteArrayInputStream(byte[] array) {
		if (array.length == 0) {
			this.array = new byte[1][];
		} else {
			this.array = new byte[2][];
			this.array[0] = array;
			this.length = (long)array.length;
			this.current = array;
		}
	}

	public int available() {
		return (int)Math.min(2147483647L, this.length - this.position);
	}

	public long skip(long n) {
		if (n > this.length - this.position) {
			n = this.length - this.position;
		}

		this.position += n;
		this.updateCurrent();
		return n;
	}

	public int read() {
		if (this.length == this.position) {
			return -1;
		} else {
			int disp = (int)(this.position++ & 1023L);
			if (disp == 0) {
				this.updateCurrent();
			}

			return this.current[disp] & 0xFF;
		}
	}

	public int read(byte[] b, int offset, int length) {
		long remaining = this.length - this.position;
		if (remaining == 0L) {
			return length == 0 ? 0 : -1;
		} else {
			int n = (int)Math.min((long)length, remaining);
			int m = n;

			while (true) {
				int disp = (int)(this.position & 1023L);
				if (disp == 0) {
					this.updateCurrent();
				}

				int res = Math.min(n, this.current.length - disp);
				System.arraycopy(this.current, disp, b, offset, res);
				n -= res;
				this.position += (long)res;
				if (n == 0) {
					return m;
				}

				offset += res;
			}
		}
	}

	private void updateCurrent() {
		this.current = this.array[(int)(this.position >>> 10)];
	}

	@Override
	public long position() {
		return this.position;
	}

	@Override
	public void position(long newPosition) {
		this.position = Math.min(newPosition, this.length);
		this.updateCurrent();
	}

	@Override
	public long length() throws IOException {
		return this.length;
	}

	public void close() {
	}

	public boolean markSupported() {
		return false;
	}

	public void mark(int dummy) {
		throw new UnsupportedOperationException();
	}

	public void reset() {
		throw new UnsupportedOperationException();
	}
}
