package org.apache.logging.log4j.core.appender;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.layout.ByteBufferDestination;
import org.apache.logging.log4j.core.util.Constants;

public class OutputStreamManager extends AbstractManager implements ByteBufferDestination {
	protected final Layout<?> layout;
	protected ByteBuffer byteBuffer;
	private volatile OutputStream os;
	private boolean skipFooter;

	protected OutputStreamManager(OutputStream os, String streamName, Layout<?> layout, boolean writeHeader) {
		this(os, streamName, layout, writeHeader, Constants.ENCODER_BYTE_BUFFER_SIZE);
	}

	protected OutputStreamManager(OutputStream os, String streamName, Layout<?> layout, boolean writeHeader, int bufferSize) {
		this(os, streamName, layout, writeHeader, ByteBuffer.wrap(new byte[bufferSize]));
	}

	@Deprecated
	protected OutputStreamManager(OutputStream os, String streamName, Layout<?> layout, boolean writeHeader, ByteBuffer byteBuffer) {
		super(null, streamName);
		this.os = os;
		this.layout = layout;
		if (writeHeader && layout != null) {
			byte[] header = layout.getHeader();
			if (header != null) {
				try {
					this.getOutputStream().write(header, 0, header.length);
				} catch (IOException var8) {
					this.logError("Unable to write header", var8);
				}
			}
		}

		this.byteBuffer = (ByteBuffer)Objects.requireNonNull(byteBuffer, "byteBuffer");
	}

	protected OutputStreamManager(
		LoggerContext loggerContext,
		OutputStream os,
		String streamName,
		boolean createOnDemand,
		Layout<? extends Serializable> layout,
		boolean writeHeader,
		ByteBuffer byteBuffer
	) {
		super(loggerContext, streamName);
		if (createOnDemand && os != null) {
			LOGGER.error("Invalid OutputStreamManager configuration for '{}': You cannot both set the OutputStream and request on-demand.", streamName);
		}

		this.layout = layout;
		this.byteBuffer = (ByteBuffer)Objects.requireNonNull(byteBuffer, "byteBuffer");
		this.os = os;
		if (writeHeader && layout != null) {
			byte[] header = layout.getHeader();
			if (header != null) {
				try {
					this.getOutputStream().write(header, 0, header.length);
				} catch (IOException var10) {
					this.logError("Unable to write header for " + streamName, var10);
				}
			}
		}
	}

	public static <T> OutputStreamManager getManager(String name, T data, ManagerFactory<? extends OutputStreamManager, T> factory) {
		return AbstractManager.getManager(name, factory, data);
	}

	protected OutputStream createOutputStream() throws IOException {
		throw new IllegalStateException(this.getClass().getCanonicalName() + " must implement createOutputStream()");
	}

	public void skipFooter(boolean skipFooter) {
		this.skipFooter = skipFooter;
	}

	@Override
	public boolean releaseSub(long timeout, TimeUnit timeUnit) {
		this.writeFooter();
		return this.closeOutputStream();
	}

	protected void writeFooter() {
		if (this.layout != null && !this.skipFooter) {
			byte[] footer = this.layout.getFooter();
			if (footer != null) {
				this.write(footer);
			}
		}
	}

	public boolean isOpen() {
		return this.getCount() > 0;
	}

	public boolean hasOutputStream() {
		return this.os != null;
	}

	protected OutputStream getOutputStream() throws IOException {
		if (this.os == null) {
			this.os = this.createOutputStream();
		}

		return this.os;
	}

	protected void setOutputStream(OutputStream os) {
		byte[] header = this.layout.getHeader();
		if (header != null) {
			try {
				os.write(header, 0, header.length);
				this.os = os;
			} catch (IOException var4) {
				this.logError("Unable to write header", var4);
			}
		} else {
			this.os = os;
		}
	}

	protected void write(byte[] bytes) {
		this.write(bytes, 0, bytes.length, false);
	}

	protected void write(byte[] bytes, boolean immediateFlush) {
		this.write(bytes, 0, bytes.length, immediateFlush);
	}

	protected void write(byte[] bytes, int offset, int length) {
		this.write(bytes, offset, length, false);
	}

	protected synchronized void write(byte[] bytes, int offset, int length, boolean immediateFlush) {
		if (immediateFlush && this.byteBuffer.position() == 0) {
			this.writeToDestination(bytes, offset, length);
			this.flushDestination();
		} else {
			if (length >= this.byteBuffer.capacity()) {
				this.flush();
				this.writeToDestination(bytes, offset, length);
			} else {
				if (length > this.byteBuffer.remaining()) {
					this.flush();
				}

				this.byteBuffer.put(bytes, offset, length);
			}

			if (immediateFlush) {
				this.flush();
			}
		}
	}

	protected synchronized void writeToDestination(byte[] bytes, int offset, int length) {
		try {
			this.getOutputStream().write(bytes, offset, length);
		} catch (IOException var5) {
			throw new AppenderLoggingException("Error writing to stream " + this.getName(), var5);
		}
	}

	protected synchronized void flushDestination() {
		OutputStream stream = this.os;
		if (stream != null) {
			try {
				stream.flush();
			} catch (IOException var3) {
				throw new AppenderLoggingException("Error flushing stream " + this.getName(), var3);
			}
		}
	}

	protected synchronized void flushBuffer(ByteBuffer buf) {
		buf.flip();
		if (buf.limit() > 0) {
			this.writeToDestination(buf.array(), 0, buf.limit());
		}

		buf.clear();
	}

	public synchronized void flush() {
		this.flushBuffer(this.byteBuffer);
		this.flushDestination();
	}

	protected synchronized boolean closeOutputStream() {
		this.flush();
		OutputStream stream = this.os;
		if (stream != null && stream != System.out && stream != System.err) {
			try {
				stream.close();
				return true;
			} catch (IOException var3) {
				this.logError("Unable to close stream", var3);
				return false;
			}
		} else {
			return true;
		}
	}

	@Override
	public ByteBuffer getByteBuffer() {
		return this.byteBuffer;
	}

	@Override
	public ByteBuffer drain(ByteBuffer buf) {
		this.flushBuffer(buf);
		return buf;
	}
}
