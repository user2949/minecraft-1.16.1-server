package org.apache.commons.codec.binary;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.codec.binary.BaseNCodec.Context;

public class BaseNCodecInputStream extends FilterInputStream {
	private final BaseNCodec baseNCodec;
	private final boolean doEncode;
	private final byte[] singleByte = new byte[1];
	private final Context context = new Context();

	protected BaseNCodecInputStream(InputStream in, BaseNCodec baseNCodec, boolean doEncode) {
		super(in);
		this.doEncode = doEncode;
		this.baseNCodec = baseNCodec;
	}

	public int available() throws IOException {
		return this.context.eof ? 0 : 1;
	}

	public synchronized void mark(int readLimit) {
	}

	public boolean markSupported() {
		return false;
	}

	public int read() throws IOException {
		int r = this.read(this.singleByte, 0, 1);

		while (r == 0) {
			r = this.read(this.singleByte, 0, 1);
		}

		if (r > 0) {
			byte b = this.singleByte[0];
			return b < 0 ? 256 + b : b;
		} else {
			return -1;
		}
	}

	public int read(byte[] b, int offset, int len) throws IOException {
		if (b == null) {
			throw new NullPointerException();
		} else if (offset >= 0 && len >= 0) {
			if (offset <= b.length && offset + len <= b.length) {
				if (len == 0) {
					return 0;
				} else {
					int readLen = 0;

					while (readLen == 0) {
						if (!this.baseNCodec.hasData(this.context)) {
							byte[] buf = new byte[this.doEncode ? 4096 : 8192];
							int c = this.in.read(buf);
							if (this.doEncode) {
								this.baseNCodec.encode(buf, 0, c, this.context);
							} else {
								this.baseNCodec.decode(buf, 0, c, this.context);
							}
						}

						readLen = this.baseNCodec.readResults(b, offset, len, this.context);
					}

					return readLen;
				}
			} else {
				throw new IndexOutOfBoundsException();
			}
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	public synchronized void reset() throws IOException {
		throw new IOException("mark/reset not supported");
	}

	public long skip(long n) throws IOException {
		if (n < 0L) {
			throw new IllegalArgumentException("Negative skip length: " + n);
		} else {
			byte[] b = new byte[512];
			long todo = n;

			while (todo > 0L) {
				int len = (int)Math.min((long)b.length, todo);
				len = this.read(b, 0, len);
				if (len == -1) {
					break;
				}

				todo -= (long)len;
			}

			return n - todo;
		}
	}
}
