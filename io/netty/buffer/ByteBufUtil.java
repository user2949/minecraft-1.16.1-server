package io.netty.buffer;

import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import io.netty.util.Recycler;
import io.netty.util.ByteProcessor.IndexOfProcessor;
import io.netty.util.Recycler.Handle;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Locale;

public final class ByteBufUtil {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(ByteBufUtil.class);
	private static final FastThreadLocal<CharBuffer> CHAR_BUFFERS = new FastThreadLocal<CharBuffer>() {
		protected CharBuffer initialValue() throws Exception {
			return CharBuffer.allocate(1024);
		}
	};
	private static final byte WRITE_UTF_UNKNOWN = 63;
	private static final int MAX_CHAR_BUFFER_SIZE;
	private static final int THREAD_LOCAL_BUFFER_SIZE;
	private static final int MAX_BYTES_PER_CHAR_UTF8 = (int)CharsetUtil.encoder(CharsetUtil.UTF_8).maxBytesPerChar();
	static final int WRITE_CHUNK_SIZE = 8192;
	static final ByteBufAllocator DEFAULT_ALLOCATOR;
	private static final ByteProcessor FIND_NON_ASCII;

	public static String hexDump(ByteBuf buffer) {
		return hexDump(buffer, buffer.readerIndex(), buffer.readableBytes());
	}

	public static String hexDump(ByteBuf buffer, int fromIndex, int length) {
		return ByteBufUtil.HexUtil.hexDump(buffer, fromIndex, length);
	}

	public static String hexDump(byte[] array) {
		return hexDump(array, 0, array.length);
	}

	public static String hexDump(byte[] array, int fromIndex, int length) {
		return ByteBufUtil.HexUtil.hexDump(array, fromIndex, length);
	}

	public static byte decodeHexByte(CharSequence s, int pos) {
		return StringUtil.decodeHexByte(s, pos);
	}

	public static byte[] decodeHexDump(CharSequence hexDump) {
		return StringUtil.decodeHexDump(hexDump, 0, hexDump.length());
	}

	public static byte[] decodeHexDump(CharSequence hexDump, int fromIndex, int length) {
		return StringUtil.decodeHexDump(hexDump, fromIndex, length);
	}

	public static boolean ensureWritableSuccess(int ensureWritableResult) {
		return ensureWritableResult == 0 || ensureWritableResult == 2;
	}

	public static int hashCode(ByteBuf buffer) {
		int aLen = buffer.readableBytes();
		int intCount = aLen >>> 2;
		int byteCount = aLen & 3;
		int hashCode = 1;
		int arrayIndex = buffer.readerIndex();
		if (buffer.order() == ByteOrder.BIG_ENDIAN) {
			for (int i = intCount; i > 0; i--) {
				hashCode = 31 * hashCode + buffer.getInt(arrayIndex);
				arrayIndex += 4;
			}
		} else {
			for (int i = intCount; i > 0; i--) {
				hashCode = 31 * hashCode + swapInt(buffer.getInt(arrayIndex));
				arrayIndex += 4;
			}
		}

		for (int i = byteCount; i > 0; i--) {
			hashCode = 31 * hashCode + buffer.getByte(arrayIndex++);
		}

		if (hashCode == 0) {
			hashCode = 1;
		}

		return hashCode;
	}

	public static int indexOf(ByteBuf needle, ByteBuf haystack) {
		int attempts = haystack.readableBytes() - needle.readableBytes() + 1;

		for (int i = 0; i < attempts; i++) {
			if (equals(needle, needle.readerIndex(), haystack, haystack.readerIndex() + i, needle.readableBytes())) {
				return haystack.readerIndex() + i;
			}
		}

		return -1;
	}

	public static boolean equals(ByteBuf a, int aStartIndex, ByteBuf b, int bStartIndex, int length) {
		if (aStartIndex >= 0 && bStartIndex >= 0 && length >= 0) {
			if (a.writerIndex() - length >= aStartIndex && b.writerIndex() - length >= bStartIndex) {
				int longCount = length >>> 3;
				int byteCount = length & 7;
				if (a.order() == b.order()) {
					for (int i = longCount; i > 0; i--) {
						if (a.getLong(aStartIndex) != b.getLong(bStartIndex)) {
							return false;
						}

						aStartIndex += 8;
						bStartIndex += 8;
					}
				} else {
					for (int i = longCount; i > 0; i--) {
						if (a.getLong(aStartIndex) != swapLong(b.getLong(bStartIndex))) {
							return false;
						}

						aStartIndex += 8;
						bStartIndex += 8;
					}
				}

				for (int i = byteCount; i > 0; i--) {
					if (a.getByte(aStartIndex) != b.getByte(bStartIndex)) {
						return false;
					}

					aStartIndex++;
					bStartIndex++;
				}

				return true;
			} else {
				return false;
			}
		} else {
			throw new IllegalArgumentException("All indexes and lengths must be non-negative");
		}
	}

	public static boolean equals(ByteBuf bufferA, ByteBuf bufferB) {
		int aLen = bufferA.readableBytes();
		return aLen != bufferB.readableBytes() ? false : equals(bufferA, bufferA.readerIndex(), bufferB, bufferB.readerIndex(), aLen);
	}

	public static int compare(ByteBuf bufferA, ByteBuf bufferB) {
		int aLen = bufferA.readableBytes();
		int bLen = bufferB.readableBytes();
		int minLength = Math.min(aLen, bLen);
		int uintCount = minLength >>> 2;
		int byteCount = minLength & 3;
		int aIndex = bufferA.readerIndex();
		int bIndex = bufferB.readerIndex();
		if (uintCount > 0) {
			boolean bufferAIsBigEndian = bufferA.order() == ByteOrder.BIG_ENDIAN;
			int uintCountIncrement = uintCount << 2;
			long res;
			if (bufferA.order() == bufferB.order()) {
				res = bufferAIsBigEndian
					? compareUintBigEndian(bufferA, bufferB, aIndex, bIndex, uintCountIncrement)
					: compareUintLittleEndian(bufferA, bufferB, aIndex, bIndex, uintCountIncrement);
			} else {
				res = bufferAIsBigEndian
					? compareUintBigEndianA(bufferA, bufferB, aIndex, bIndex, uintCountIncrement)
					: compareUintBigEndianB(bufferA, bufferB, aIndex, bIndex, uintCountIncrement);
			}

			if (res != 0L) {
				return (int)Math.min(2147483647L, Math.max(-2147483648L, res));
			}

			aIndex += uintCountIncrement;
			bIndex += uintCountIncrement;
		}

		for (int aEnd = aIndex + byteCount; aIndex < aEnd; bIndex++) {
			int comp = bufferA.getUnsignedByte(aIndex) - bufferB.getUnsignedByte(bIndex);
			if (comp != 0) {
				return comp;
			}

			aIndex++;
		}

		return aLen - bLen;
	}

	private static long compareUintBigEndian(ByteBuf bufferA, ByteBuf bufferB, int aIndex, int bIndex, int uintCountIncrement) {
		int aEnd = aIndex + uintCountIncrement;

		while (aIndex < aEnd) {
			long comp = bufferA.getUnsignedInt(aIndex) - bufferB.getUnsignedInt(bIndex);
			if (comp != 0L) {
				return comp;
			}

			aIndex += 4;
			bIndex += 4;
		}

		return 0L;
	}

	private static long compareUintLittleEndian(ByteBuf bufferA, ByteBuf bufferB, int aIndex, int bIndex, int uintCountIncrement) {
		int aEnd = aIndex + uintCountIncrement;

		while (aIndex < aEnd) {
			long comp = bufferA.getUnsignedIntLE(aIndex) - bufferB.getUnsignedIntLE(bIndex);
			if (comp != 0L) {
				return comp;
			}

			aIndex += 4;
			bIndex += 4;
		}

		return 0L;
	}

	private static long compareUintBigEndianA(ByteBuf bufferA, ByteBuf bufferB, int aIndex, int bIndex, int uintCountIncrement) {
		int aEnd = aIndex + uintCountIncrement;

		while (aIndex < aEnd) {
			long comp = bufferA.getUnsignedInt(aIndex) - bufferB.getUnsignedIntLE(bIndex);
			if (comp != 0L) {
				return comp;
			}

			aIndex += 4;
			bIndex += 4;
		}

		return 0L;
	}

	private static long compareUintBigEndianB(ByteBuf bufferA, ByteBuf bufferB, int aIndex, int bIndex, int uintCountIncrement) {
		int aEnd = aIndex + uintCountIncrement;

		while (aIndex < aEnd) {
			long comp = bufferA.getUnsignedIntLE(aIndex) - bufferB.getUnsignedInt(bIndex);
			if (comp != 0L) {
				return comp;
			}

			aIndex += 4;
			bIndex += 4;
		}

		return 0L;
	}

	public static int indexOf(ByteBuf buffer, int fromIndex, int toIndex, byte value) {
		return fromIndex <= toIndex ? firstIndexOf(buffer, fromIndex, toIndex, value) : lastIndexOf(buffer, fromIndex, toIndex, value);
	}

	public static short swapShort(short value) {
		return Short.reverseBytes(value);
	}

	public static int swapMedium(int value) {
		int swapped = value << 16 & 0xFF0000 | value & 0xFF00 | value >>> 16 & 0xFF;
		if ((swapped & 8388608) != 0) {
			swapped |= -16777216;
		}

		return swapped;
	}

	public static int swapInt(int value) {
		return Integer.reverseBytes(value);
	}

	public static long swapLong(long value) {
		return Long.reverseBytes(value);
	}

	public static ByteBuf writeShortBE(ByteBuf buf, int shortValue) {
		return buf.order() == ByteOrder.BIG_ENDIAN ? buf.writeShort(shortValue) : buf.writeShortLE(shortValue);
	}

	public static ByteBuf setShortBE(ByteBuf buf, int index, int shortValue) {
		return buf.order() == ByteOrder.BIG_ENDIAN ? buf.setShort(index, shortValue) : buf.setShortLE(index, shortValue);
	}

	public static ByteBuf writeMediumBE(ByteBuf buf, int mediumValue) {
		return buf.order() == ByteOrder.BIG_ENDIAN ? buf.writeMedium(mediumValue) : buf.writeMediumLE(mediumValue);
	}

	public static ByteBuf readBytes(ByteBufAllocator alloc, ByteBuf buffer, int length) {
		boolean release = true;
		ByteBuf dst = alloc.buffer(length);

		ByteBuf var5;
		try {
			buffer.readBytes(dst);
			release = false;
			var5 = dst;
		} finally {
			if (release) {
				dst.release();
			}
		}

		return var5;
	}

	private static int firstIndexOf(ByteBuf buffer, int fromIndex, int toIndex, byte value) {
		fromIndex = Math.max(fromIndex, 0);
		return fromIndex < toIndex && buffer.capacity() != 0 ? buffer.forEachByte(fromIndex, toIndex - fromIndex, new IndexOfProcessor(value)) : -1;
	}

	private static int lastIndexOf(ByteBuf buffer, int fromIndex, int toIndex, byte value) {
		fromIndex = Math.min(fromIndex, buffer.capacity());
		return fromIndex >= 0 && buffer.capacity() != 0 ? buffer.forEachByteDesc(toIndex, fromIndex - toIndex, new IndexOfProcessor(value)) : -1;
	}

	public static ByteBuf writeUtf8(ByteBufAllocator alloc, CharSequence seq) {
		ByteBuf buf = alloc.buffer(utf8MaxBytes(seq));
		writeUtf8(buf, seq);
		return buf;
	}

	public static int writeUtf8(ByteBuf buf, CharSequence seq) {
		return reserveAndWriteUtf8(buf, seq, utf8MaxBytes(seq));
	}

	public static int reserveAndWriteUtf8(ByteBuf buf, CharSequence seq, int reserveBytes) {
		while (!(buf instanceof AbstractByteBuf)) {
			if (!(buf instanceof WrappedByteBuf)) {
				byte[] bytes = seq.toString().getBytes(CharsetUtil.UTF_8);
				buf.writeBytes(bytes);
				return bytes.length;
			}

			buf = buf.unwrap();
		}

		AbstractByteBuf byteBuf = (AbstractByteBuf)buf;
		byteBuf.ensureWritable0(reserveBytes);
		int written = writeUtf8(byteBuf, byteBuf.writerIndex, seq, seq.length());
		byteBuf.writerIndex += written;
		return written;
	}

	static int writeUtf8(AbstractByteBuf buffer, int writerIndex, CharSequence seq, int len) {
		int oldWriterIndex = writerIndex;

		for (int i = 0; i < len; i++) {
			char c = seq.charAt(i);
			if (c < 128) {
				buffer._setByte(writerIndex++, (byte)c);
			} else if (c < 2048) {
				buffer._setByte(writerIndex++, (byte)(192 | c >> 6));
				buffer._setByte(writerIndex++, (byte)(128 | c & '?'));
			} else if (StringUtil.isSurrogate(c)) {
				if (!Character.isHighSurrogate(c)) {
					buffer._setByte(writerIndex++, 63);
				} else {
					char c2;
					try {
						c2 = seq.charAt(++i);
					} catch (IndexOutOfBoundsException var9) {
						buffer._setByte(writerIndex++, 63);
						break;
					}

					if (!Character.isLowSurrogate(c2)) {
						buffer._setByte(writerIndex++, 63);
						buffer._setByte(writerIndex++, Character.isHighSurrogate(c2) ? 63 : c2);
					} else {
						int codePoint = Character.toCodePoint(c, c2);
						buffer._setByte(writerIndex++, (byte)(240 | codePoint >> 18));
						buffer._setByte(writerIndex++, (byte)(128 | codePoint >> 12 & 63));
						buffer._setByte(writerIndex++, (byte)(128 | codePoint >> 6 & 63));
						buffer._setByte(writerIndex++, (byte)(128 | codePoint & 63));
					}
				}
			} else {
				buffer._setByte(writerIndex++, (byte)(224 | c >> '\f'));
				buffer._setByte(writerIndex++, (byte)(128 | c >> 6 & 63));
				buffer._setByte(writerIndex++, (byte)(128 | c & '?'));
			}
		}

		return writerIndex - oldWriterIndex;
	}

	public static int utf8MaxBytes(int seqLength) {
		return seqLength * MAX_BYTES_PER_CHAR_UTF8;
	}

	public static int utf8MaxBytes(CharSequence seq) {
		return utf8MaxBytes(seq.length());
	}

	public static int utf8Bytes(CharSequence seq) {
		if (seq instanceof AsciiString) {
			return seq.length();
		} else {
			int seqLength = seq.length();
			int i = 0;

			while (i < seqLength && seq.charAt(i) < 128) {
				i++;
			}

			return i < seqLength ? i + utf8Bytes(seq, i, seqLength) : i;
		}
	}

	private static int utf8Bytes(CharSequence seq, int start, int length) {
		int encodedLength = 0;

		for (int i = start; i < length; i++) {
			char c = seq.charAt(i);
			if (c < 2048) {
				encodedLength += (127 - c >>> 31) + 1;
			} else if (StringUtil.isSurrogate(c)) {
				if (!Character.isHighSurrogate(c)) {
					encodedLength++;
				} else {
					char c2;
					try {
						c2 = seq.charAt(++i);
					} catch (IndexOutOfBoundsException var8) {
						encodedLength++;
						break;
					}

					if (!Character.isLowSurrogate(c2)) {
						encodedLength += 2;
					} else {
						encodedLength += 4;
					}
				}
			} else {
				encodedLength += 3;
			}
		}

		return encodedLength;
	}

	public static ByteBuf writeAscii(ByteBufAllocator alloc, CharSequence seq) {
		ByteBuf buf = alloc.buffer(seq.length());
		writeAscii(buf, seq);
		return buf;
	}

	public static int writeAscii(ByteBuf buf, CharSequence seq) {
		int len = seq.length();
		if (seq instanceof AsciiString) {
			AsciiString asciiString = (AsciiString)seq;
			buf.writeBytes(asciiString.array(), asciiString.arrayOffset(), len);
			return len;
		} else {
			while (!(buf instanceof AbstractByteBuf)) {
				if (!(buf instanceof WrappedByteBuf)) {
					byte[] bytes = seq.toString().getBytes(CharsetUtil.US_ASCII);
					buf.writeBytes(bytes);
					return bytes.length;
				}

				buf = buf.unwrap();
			}

			AbstractByteBuf byteBuf = (AbstractByteBuf)buf;
			byteBuf.ensureWritable0(len);
			int written = writeAscii(byteBuf, byteBuf.writerIndex, seq, len);
			byteBuf.writerIndex += written;
			return written;
		}
	}

	static int writeAscii(AbstractByteBuf buffer, int writerIndex, CharSequence seq, int len) {
		for (int i = 0; i < len; i++) {
			buffer._setByte(writerIndex++, AsciiString.c2b(seq.charAt(i)));
		}

		return len;
	}

	public static ByteBuf encodeString(ByteBufAllocator alloc, CharBuffer src, Charset charset) {
		return encodeString0(alloc, false, src, charset, 0);
	}

	public static ByteBuf encodeString(ByteBufAllocator alloc, CharBuffer src, Charset charset, int extraCapacity) {
		return encodeString0(alloc, false, src, charset, extraCapacity);
	}

	static ByteBuf encodeString0(ByteBufAllocator alloc, boolean enforceHeap, CharBuffer src, Charset charset, int extraCapacity) {
		CharsetEncoder encoder = CharsetUtil.encoder(charset);
		int length = (int)((double)src.remaining() * (double)encoder.maxBytesPerChar()) + extraCapacity;
		boolean release = true;
		ByteBuf dst;
		if (enforceHeap) {
			dst = alloc.heapBuffer(length);
		} else {
			dst = alloc.buffer(length);
		}

		ByteBuf var12;
		try {
			ByteBuffer dstBuf = dst.internalNioBuffer(dst.readerIndex(), length);
			int pos = dstBuf.position();
			CoderResult cr = encoder.encode(src, dstBuf, true);
			if (!cr.isUnderflow()) {
				cr.throwException();
			}

			cr = encoder.flush(dstBuf);
			if (!cr.isUnderflow()) {
				cr.throwException();
			}

			dst.writerIndex(dst.writerIndex() + dstBuf.position() - pos);
			release = false;
			var12 = dst;
		} catch (CharacterCodingException var16) {
			throw new IllegalStateException(var16);
		} finally {
			if (release) {
				dst.release();
			}
		}

		return var12;
	}

	static String decodeString(ByteBuf src, int readerIndex, int len, Charset charset) {
		if (len == 0) {
			return "";
		} else {
			CharsetDecoder decoder = CharsetUtil.decoder(charset);
			int maxLength = (int)((double)len * (double)decoder.maxCharsPerByte());
			CharBuffer dst = CHAR_BUFFERS.get();
			if (dst.length() < maxLength) {
				dst = CharBuffer.allocate(maxLength);
				if (maxLength <= MAX_CHAR_BUFFER_SIZE) {
					CHAR_BUFFERS.set(dst);
				}
			} else {
				dst.clear();
			}

			if (src.nioBufferCount() == 1) {
				decodeString(decoder, src.nioBuffer(readerIndex, len), dst);
			} else {
				ByteBuf buffer = src.alloc().heapBuffer(len);

				try {
					buffer.writeBytes(src, readerIndex, len);
					decodeString(decoder, buffer.internalNioBuffer(buffer.readerIndex(), len), dst);
				} finally {
					buffer.release();
				}
			}

			return dst.flip().toString();
		}
	}

	private static void decodeString(CharsetDecoder decoder, ByteBuffer src, CharBuffer dst) {
		try {
			CoderResult cr = decoder.decode(src, dst, true);
			if (!cr.isUnderflow()) {
				cr.throwException();
			}

			cr = decoder.flush(dst);
			if (!cr.isUnderflow()) {
				cr.throwException();
			}
		} catch (CharacterCodingException var4) {
			throw new IllegalStateException(var4);
		}
	}

	public static ByteBuf threadLocalDirectBuffer() {
		if (THREAD_LOCAL_BUFFER_SIZE <= 0) {
			return null;
		} else {
			return (ByteBuf)(PlatformDependent.hasUnsafe()
				? ByteBufUtil.ThreadLocalUnsafeDirectByteBuf.newInstance()
				: ByteBufUtil.ThreadLocalDirectByteBuf.newInstance());
		}
	}

	public static byte[] getBytes(ByteBuf buf) {
		return getBytes(buf, buf.readerIndex(), buf.readableBytes());
	}

	public static byte[] getBytes(ByteBuf buf, int start, int length) {
		return getBytes(buf, start, length, true);
	}

	public static byte[] getBytes(ByteBuf buf, int start, int length, boolean copy) {
		if (MathUtil.isOutOfBounds(start, length, buf.capacity())) {
			throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= start + length(" + length + ") <= buf.capacity(" + buf.capacity() + ')');
		} else if (buf.hasArray()) {
			if (!copy && start == 0 && length == buf.capacity()) {
				return buf.array();
			} else {
				int baseOffset = buf.arrayOffset() + start;
				return Arrays.copyOfRange(buf.array(), baseOffset, baseOffset + length);
			}
		} else {
			byte[] v = new byte[length];
			buf.getBytes(start, v);
			return v;
		}
	}

	public static void copy(AsciiString src, ByteBuf dst) {
		copy(src, 0, dst, src.length());
	}

	public static void copy(AsciiString src, int srcIdx, ByteBuf dst, int dstIdx, int length) {
		if (MathUtil.isOutOfBounds(srcIdx, length, src.length())) {
			throw new IndexOutOfBoundsException("expected: 0 <= srcIdx(" + srcIdx + ") <= srcIdx + length(" + length + ") <= srcLen(" + src.length() + ')');
		} else {
			ObjectUtil.checkNotNull(dst, "dst").setBytes(dstIdx, src.array(), srcIdx + src.arrayOffset(), length);
		}
	}

	public static void copy(AsciiString src, int srcIdx, ByteBuf dst, int length) {
		if (MathUtil.isOutOfBounds(srcIdx, length, src.length())) {
			throw new IndexOutOfBoundsException("expected: 0 <= srcIdx(" + srcIdx + ") <= srcIdx + length(" + length + ") <= srcLen(" + src.length() + ')');
		} else {
			ObjectUtil.checkNotNull(dst, "dst").writeBytes(src.array(), srcIdx + src.arrayOffset(), length);
		}
	}

	public static String prettyHexDump(ByteBuf buffer) {
		return prettyHexDump(buffer, buffer.readerIndex(), buffer.readableBytes());
	}

	public static String prettyHexDump(ByteBuf buffer, int offset, int length) {
		return ByteBufUtil.HexUtil.prettyHexDump(buffer, offset, length);
	}

	public static void appendPrettyHexDump(StringBuilder dump, ByteBuf buf) {
		appendPrettyHexDump(dump, buf, buf.readerIndex(), buf.readableBytes());
	}

	public static void appendPrettyHexDump(StringBuilder dump, ByteBuf buf, int offset, int length) {
		ByteBufUtil.HexUtil.appendPrettyHexDump(dump, buf, offset, length);
	}

	public static boolean isText(ByteBuf buf, Charset charset) {
		return isText(buf, buf.readerIndex(), buf.readableBytes(), charset);
	}

	public static boolean isText(ByteBuf buf, int index, int length, Charset charset) {
		ObjectUtil.checkNotNull(buf, "buf");
		ObjectUtil.checkNotNull(charset, "charset");
		int maxIndex = buf.readerIndex() + buf.readableBytes();
		if (index < 0 || length < 0 || index > maxIndex - length) {
			throw new IndexOutOfBoundsException("index: " + index + " length: " + length);
		} else if (charset.equals(CharsetUtil.UTF_8)) {
			return isUtf8(buf, index, length);
		} else if (charset.equals(CharsetUtil.US_ASCII)) {
			return isAscii(buf, index, length);
		} else {
			CharsetDecoder decoder = CharsetUtil.decoder(charset, CodingErrorAction.REPORT, CodingErrorAction.REPORT);

			try {
				if (buf.nioBufferCount() == 1) {
					decoder.decode(buf.nioBuffer(index, length));
				} else {
					ByteBuf heapBuffer = buf.alloc().heapBuffer(length);

					try {
						heapBuffer.writeBytes(buf, index, length);
						decoder.decode(heapBuffer.internalNioBuffer(heapBuffer.readerIndex(), length));
					} finally {
						heapBuffer.release();
					}
				}

				return true;
			} catch (CharacterCodingException var11) {
				return false;
			}
		}
	}

	private static boolean isAscii(ByteBuf buf, int index, int length) {
		return buf.forEachByte(index, length, FIND_NON_ASCII) == -1;
	}

	private static boolean isUtf8(ByteBuf buf, int index, int length) {
		int endIndex = index + length;

		while (index < endIndex) {
			byte b1 = buf.getByte(index++);
			if ((b1 & 128) != 0) {
				if ((b1 & 224) != 192) {
					if ((b1 & 240) == 224) {
						if (index > endIndex - 2) {
							return false;
						}

						byte b2 = buf.getByte(index++);
						byte b3 = buf.getByte(index++);
						if ((b2 & 192) != 128 || (b3 & 192) != 128) {
							return false;
						}

						if ((b1 & 15) == 0 && (b2 & 255) < 160) {
							return false;
						}

						if ((b1 & 15) == 13 && (b2 & 255) > 159) {
							return false;
						}
					} else {
						if ((b1 & 248) != 240) {
							return false;
						}

						if (index > endIndex - 3) {
							return false;
						}

						byte b2x = buf.getByte(index++);
						byte b3x = buf.getByte(index++);
						byte b4 = buf.getByte(index++);
						if ((b2x & 192) == 128 && (b3x & 192) == 128 && (b4 & 192) == 128) {
							if ((b1 & 255) <= 244 && ((b1 & 255) != 240 || (b2x & 255) >= 144) && ((b1 & 255) != 244 || (b2x & 255) <= 143)) {
								continue;
							}

							return false;
						}

						return false;
					}
				} else {
					if (index >= endIndex) {
						return false;
					}

					byte b2x = buf.getByte(index++);
					if ((b2x & 192) != 128) {
						return false;
					}

					if ((b1 & 255) < 194) {
						return false;
					}
				}
			}
		}

		return true;
	}

	static void readBytes(ByteBufAllocator allocator, ByteBuffer buffer, int position, int length, OutputStream out) throws IOException {
		if (buffer.hasArray()) {
			out.write(buffer.array(), position + buffer.arrayOffset(), length);
		} else {
			int chunkLen = Math.min(length, 8192);
			buffer.clear().position(position);
			if (allocator.isDirectBufferPooled()) {
				ByteBuf tmpBuf = allocator.heapBuffer(chunkLen);

				try {
					byte[] tmp = tmpBuf.array();
					int offset = tmpBuf.arrayOffset();
					getBytes(buffer, tmp, offset, chunkLen, out, length);
				} finally {
					tmpBuf.release();
				}
			} else {
				getBytes(buffer, new byte[chunkLen], 0, chunkLen, out, length);
			}
		}
	}

	private static void getBytes(ByteBuffer inBuffer, byte[] in, int inOffset, int inLen, OutputStream out, int outLen) throws IOException {
		do {
			int len = Math.min(inLen, outLen);
			inBuffer.get(in, inOffset, len);
			out.write(in, inOffset, len);
			outLen -= len;
		} while (outLen > 0);
	}

	private ByteBufUtil() {
	}

	static {
		String allocType = SystemPropertyUtil.get("io.netty.allocator.type", PlatformDependent.isAndroid() ? "unpooled" : "pooled");
		allocType = allocType.toLowerCase(Locale.US).trim();
		ByteBufAllocator alloc;
		if ("unpooled".equals(allocType)) {
			alloc = UnpooledByteBufAllocator.DEFAULT;
			logger.debug("-Dio.netty.allocator.type: {}", allocType);
		} else if ("pooled".equals(allocType)) {
			alloc = PooledByteBufAllocator.DEFAULT;
			logger.debug("-Dio.netty.allocator.type: {}", allocType);
		} else {
			alloc = PooledByteBufAllocator.DEFAULT;
			logger.debug("-Dio.netty.allocator.type: pooled (unknown: {})", allocType);
		}

		DEFAULT_ALLOCATOR = alloc;
		THREAD_LOCAL_BUFFER_SIZE = SystemPropertyUtil.getInt("io.netty.threadLocalDirectBufferSize", 0);
		logger.debug("-Dio.netty.threadLocalDirectBufferSize: {}", THREAD_LOCAL_BUFFER_SIZE);
		MAX_CHAR_BUFFER_SIZE = SystemPropertyUtil.getInt("io.netty.maxThreadLocalCharBufferSize", 16384);
		logger.debug("-Dio.netty.maxThreadLocalCharBufferSize: {}", MAX_CHAR_BUFFER_SIZE);
		FIND_NON_ASCII = new ByteProcessor() {
			@Override
			public boolean process(byte value) {
				return value >= 0;
			}
		};
	}

	private static final class HexUtil {
		private static final char[] BYTE2CHAR = new char[256];
		private static final char[] HEXDUMP_TABLE = new char[1024];
		private static final String[] HEXPADDING = new String[16];
		private static final String[] HEXDUMP_ROWPREFIXES = new String[4096];
		private static final String[] BYTE2HEX = new String[256];
		private static final String[] BYTEPADDING = new String[16];

		private static String hexDump(ByteBuf buffer, int fromIndex, int length) {
			if (length < 0) {
				throw new IllegalArgumentException("length: " + length);
			} else if (length == 0) {
				return "";
			} else {
				int endIndex = fromIndex + length;
				char[] buf = new char[length << 1];
				int srcIdx = fromIndex;

				for (int dstIdx = 0; srcIdx < endIndex; dstIdx += 2) {
					System.arraycopy(HEXDUMP_TABLE, buffer.getUnsignedByte(srcIdx) << 1, buf, dstIdx, 2);
					srcIdx++;
				}

				return new String(buf);
			}
		}

		private static String hexDump(byte[] array, int fromIndex, int length) {
			if (length < 0) {
				throw new IllegalArgumentException("length: " + length);
			} else if (length == 0) {
				return "";
			} else {
				int endIndex = fromIndex + length;
				char[] buf = new char[length << 1];
				int srcIdx = fromIndex;

				for (int dstIdx = 0; srcIdx < endIndex; dstIdx += 2) {
					System.arraycopy(HEXDUMP_TABLE, (array[srcIdx] & 255) << 1, buf, dstIdx, 2);
					srcIdx++;
				}

				return new String(buf);
			}
		}

		private static String prettyHexDump(ByteBuf buffer, int offset, int length) {
			if (length == 0) {
				return "";
			} else {
				int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
				StringBuilder buf = new StringBuilder(rows * 80);
				appendPrettyHexDump(buf, buffer, offset, length);
				return buf.toString();
			}
		}

		private static void appendPrettyHexDump(StringBuilder dump, ByteBuf buf, int offset, int length) {
			if (MathUtil.isOutOfBounds(offset, length, buf.capacity())) {
				throw new IndexOutOfBoundsException("expected: 0 <= offset(" + offset + ") <= offset + length(" + length + ") <= buf.capacity(" + buf.capacity() + ')');
			} else if (length != 0) {
				dump.append(
					"         +-------------------------------------------------+"
						+ StringUtil.NEWLINE
						+ "         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |"
						+ StringUtil.NEWLINE
						+ "+--------+-------------------------------------------------+----------------+"
				);
				int startIndex = offset;
				int fullRows = length >>> 4;
				int remainder = length & 15;

				for (int row = 0; row < fullRows; row++) {
					int rowStartIndex = (row << 4) + startIndex;
					appendHexDumpRowPrefix(dump, row, rowStartIndex);
					int rowEndIndex = rowStartIndex + 16;

					for (int j = rowStartIndex; j < rowEndIndex; j++) {
						dump.append(BYTE2HEX[buf.getUnsignedByte(j)]);
					}

					dump.append(" |");

					for (int j = rowStartIndex; j < rowEndIndex; j++) {
						dump.append(BYTE2CHAR[buf.getUnsignedByte(j)]);
					}

					dump.append('|');
				}

				if (remainder != 0) {
					int rowStartIndex = (fullRows << 4) + startIndex;
					appendHexDumpRowPrefix(dump, fullRows, rowStartIndex);
					int rowEndIndex = rowStartIndex + remainder;

					for (int j = rowStartIndex; j < rowEndIndex; j++) {
						dump.append(BYTE2HEX[buf.getUnsignedByte(j)]);
					}

					dump.append(HEXPADDING[remainder]);
					dump.append(" |");

					for (int j = rowStartIndex; j < rowEndIndex; j++) {
						dump.append(BYTE2CHAR[buf.getUnsignedByte(j)]);
					}

					dump.append(BYTEPADDING[remainder]);
					dump.append('|');
				}

				dump.append(StringUtil.NEWLINE + "+--------+-------------------------------------------------+----------------+");
			}
		}

		private static void appendHexDumpRowPrefix(StringBuilder dump, int row, int rowStartIndex) {
			if (row < HEXDUMP_ROWPREFIXES.length) {
				dump.append(HEXDUMP_ROWPREFIXES[row]);
			} else {
				dump.append(StringUtil.NEWLINE);
				dump.append(Long.toHexString((long)rowStartIndex & 4294967295L | 4294967296L));
				dump.setCharAt(dump.length() - 9, '|');
				dump.append('|');
			}
		}

		static {
			char[] DIGITS = "0123456789abcdef".toCharArray();

			for (int i = 0; i < 256; i++) {
				HEXDUMP_TABLE[i << 1] = DIGITS[i >>> 4 & 15];
				HEXDUMP_TABLE[(i << 1) + 1] = DIGITS[i & 15];
			}

			for (int i = 0; i < HEXPADDING.length; i++) {
				int padding = HEXPADDING.length - i;
				StringBuilder buf = new StringBuilder(padding * 3);

				for (int j = 0; j < padding; j++) {
					buf.append("   ");
				}

				HEXPADDING[i] = buf.toString();
			}

			for (int var6 = 0; var6 < HEXDUMP_ROWPREFIXES.length; var6++) {
				StringBuilder buf = new StringBuilder(12);
				buf.append(StringUtil.NEWLINE);
				buf.append(Long.toHexString((long)(var6 << 4) & 4294967295L | 4294967296L));
				buf.setCharAt(buf.length() - 9, '|');
				buf.append('|');
				HEXDUMP_ROWPREFIXES[var6] = buf.toString();
			}

			for (int var7 = 0; var7 < BYTE2HEX.length; var7++) {
				BYTE2HEX[var7] = ' ' + StringUtil.byteToHexStringPadded(var7);
			}

			for (int var8 = 0; var8 < BYTEPADDING.length; var8++) {
				int padding = BYTEPADDING.length - var8;
				StringBuilder buf = new StringBuilder(padding);

				for (int j = 0; j < padding; j++) {
					buf.append(' ');
				}

				BYTEPADDING[var8] = buf.toString();
			}

			for (int var9 = 0; var9 < BYTE2CHAR.length; var9++) {
				if (var9 > 31 && var9 < 127) {
					BYTE2CHAR[var9] = (char)var9;
				} else {
					BYTE2CHAR[var9] = '.';
				}
			}
		}
	}

	static final class ThreadLocalDirectByteBuf extends UnpooledDirectByteBuf {
		private static final Recycler<ByteBufUtil.ThreadLocalDirectByteBuf> RECYCLER = new Recycler<ByteBufUtil.ThreadLocalDirectByteBuf>() {
			protected ByteBufUtil.ThreadLocalDirectByteBuf newObject(Handle<ByteBufUtil.ThreadLocalDirectByteBuf> handle) {
				return new ByteBufUtil.ThreadLocalDirectByteBuf(handle);
			}
		};
		private final Handle<ByteBufUtil.ThreadLocalDirectByteBuf> handle;

		static ByteBufUtil.ThreadLocalDirectByteBuf newInstance() {
			ByteBufUtil.ThreadLocalDirectByteBuf buf = RECYCLER.get();
			buf.setRefCnt(1);
			return buf;
		}

		private ThreadLocalDirectByteBuf(Handle<ByteBufUtil.ThreadLocalDirectByteBuf> handle) {
			super(UnpooledByteBufAllocator.DEFAULT, 256, Integer.MAX_VALUE);
			this.handle = handle;
		}

		@Override
		protected void deallocate() {
			if (this.capacity() > ByteBufUtil.THREAD_LOCAL_BUFFER_SIZE) {
				super.deallocate();
			} else {
				this.clear();
				this.handle.recycle(this);
			}
		}
	}

	static final class ThreadLocalUnsafeDirectByteBuf extends UnpooledUnsafeDirectByteBuf {
		private static final Recycler<ByteBufUtil.ThreadLocalUnsafeDirectByteBuf> RECYCLER = new Recycler<ByteBufUtil.ThreadLocalUnsafeDirectByteBuf>() {
			protected ByteBufUtil.ThreadLocalUnsafeDirectByteBuf newObject(Handle<ByteBufUtil.ThreadLocalUnsafeDirectByteBuf> handle) {
				return new ByteBufUtil.ThreadLocalUnsafeDirectByteBuf(handle);
			}
		};
		private final Handle<ByteBufUtil.ThreadLocalUnsafeDirectByteBuf> handle;

		static ByteBufUtil.ThreadLocalUnsafeDirectByteBuf newInstance() {
			ByteBufUtil.ThreadLocalUnsafeDirectByteBuf buf = RECYCLER.get();
			buf.setRefCnt(1);
			return buf;
		}

		private ThreadLocalUnsafeDirectByteBuf(Handle<ByteBufUtil.ThreadLocalUnsafeDirectByteBuf> handle) {
			super(UnpooledByteBufAllocator.DEFAULT, 256, Integer.MAX_VALUE);
			this.handle = handle;
		}

		@Override
		protected void deallocate() {
			if (this.capacity() > ByteBufUtil.THREAD_LOCAL_BUFFER_SIZE) {
				super.deallocate();
			} else {
				this.clear();
				this.handle.recycle(this);
			}
		}
	}
}
