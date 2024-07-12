package org.apache.logging.log4j.core.layout;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.Objects;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.status.StatusLogger;

public class StringBuilderEncoder implements Encoder<StringBuilder> {
	private static final int DEFAULT_BYTE_BUFFER_SIZE = 8192;
	private final ThreadLocal<CharBuffer> charBufferThreadLocal = new ThreadLocal();
	private final ThreadLocal<ByteBuffer> byteBufferThreadLocal = new ThreadLocal();
	private final ThreadLocal<CharsetEncoder> charsetEncoderThreadLocal = new ThreadLocal();
	private final Charset charset;
	private final int charBufferSize;
	private final int byteBufferSize;

	public StringBuilderEncoder(Charset charset) {
		this(charset, Constants.ENCODER_CHAR_BUFFER_SIZE, 8192);
	}

	public StringBuilderEncoder(Charset charset, int charBufferSize, int byteBufferSize) {
		this.charBufferSize = charBufferSize;
		this.byteBufferSize = byteBufferSize;
		this.charset = (Charset)Objects.requireNonNull(charset, "charset");
	}

	public void encode(StringBuilder source, ByteBufferDestination destination) {
		ByteBuffer temp = this.getByteBuffer();
		temp.clear();
		temp.limit(Math.min(temp.capacity(), destination.getByteBuffer().capacity()));
		CharsetEncoder charsetEncoder = this.getCharsetEncoder();
		int estimatedBytes = estimateBytes(source.length(), charsetEncoder.maxBytesPerChar());
		if (temp.remaining() < estimatedBytes) {
			this.encodeSynchronized(this.getCharsetEncoder(), this.getCharBuffer(), source, destination);
		} else {
			this.encodeWithThreadLocals(charsetEncoder, this.getCharBuffer(), temp, source, destination);
		}
	}

	private void encodeWithThreadLocals(
		CharsetEncoder charsetEncoder, CharBuffer charBuffer, ByteBuffer temp, StringBuilder source, ByteBufferDestination destination
	) {
		try {
			TextEncoderHelper.encodeTextWithCopy(charsetEncoder, charBuffer, temp, source, destination);
		} catch (Exception var7) {
			this.logEncodeTextException(var7, source, destination);
			TextEncoderHelper.encodeTextFallBack(this.charset, source, destination);
		}
	}

	private static int estimateBytes(int charCount, float maxBytesPerChar) {
		return (int)((double)charCount * (double)maxBytesPerChar);
	}

	private void encodeSynchronized(CharsetEncoder charsetEncoder, CharBuffer charBuffer, StringBuilder source, ByteBufferDestination destination) {
		synchronized (destination) {
			try {
				TextEncoderHelper.encodeText(charsetEncoder, charBuffer, destination.getByteBuffer(), source, destination);
			} catch (Exception var8) {
				this.logEncodeTextException(var8, source, destination);
				TextEncoderHelper.encodeTextFallBack(this.charset, source, destination);
			}
		}
	}

	private CharsetEncoder getCharsetEncoder() {
		CharsetEncoder result = (CharsetEncoder)this.charsetEncoderThreadLocal.get();
		if (result == null) {
			result = this.charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
			this.charsetEncoderThreadLocal.set(result);
		}

		return result;
	}

	private CharBuffer getCharBuffer() {
		CharBuffer result = (CharBuffer)this.charBufferThreadLocal.get();
		if (result == null) {
			result = CharBuffer.wrap(new char[this.charBufferSize]);
			this.charBufferThreadLocal.set(result);
		}

		return result;
	}

	private ByteBuffer getByteBuffer() {
		ByteBuffer result = (ByteBuffer)this.byteBufferThreadLocal.get();
		if (result == null) {
			result = ByteBuffer.wrap(new byte[this.byteBufferSize]);
			this.byteBufferThreadLocal.set(result);
		}

		return result;
	}

	private void logEncodeTextException(Exception ex, StringBuilder text, ByteBufferDestination destination) {
		StatusLogger.getLogger().error("Recovering from StringBuilderEncoder.encode('{}') error: {}", text, ex, ex);
	}
}
