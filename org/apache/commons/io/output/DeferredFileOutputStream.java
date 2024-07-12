package org.apache.commons.io.output;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;

public class DeferredFileOutputStream extends ThresholdingOutputStream {
	private ByteArrayOutputStream memoryOutputStream;
	private OutputStream currentOutputStream;
	private File outputFile;
	private final String prefix;
	private final String suffix;
	private final File directory;
	private boolean closed = false;

	public DeferredFileOutputStream(int threshold, File outputFile) {
		this(threshold, outputFile, null, null, null);
	}

	public DeferredFileOutputStream(int threshold, String prefix, String suffix, File directory) {
		this(threshold, null, prefix, suffix, directory);
		if (prefix == null) {
			throw new IllegalArgumentException("Temporary file prefix is missing");
		}
	}

	private DeferredFileOutputStream(int threshold, File outputFile, String prefix, String suffix, File directory) {
		super(threshold);
		this.outputFile = outputFile;
		this.memoryOutputStream = new ByteArrayOutputStream();
		this.currentOutputStream = this.memoryOutputStream;
		this.prefix = prefix;
		this.suffix = suffix;
		this.directory = directory;
	}

	@Override
	protected OutputStream getStream() throws IOException {
		return this.currentOutputStream;
	}

	@Override
	protected void thresholdReached() throws IOException {
		if (this.prefix != null) {
			this.outputFile = File.createTempFile(this.prefix, this.suffix, this.directory);
		}

		FileOutputStream fos = new FileOutputStream(this.outputFile);

		try {
			this.memoryOutputStream.writeTo(fos);
		} catch (IOException var3) {
			fos.close();
			throw var3;
		}

		this.currentOutputStream = fos;
		this.memoryOutputStream = null;
	}

	public boolean isInMemory() {
		return !this.isThresholdExceeded();
	}

	public byte[] getData() {
		return this.memoryOutputStream != null ? this.memoryOutputStream.toByteArray() : null;
	}

	public File getFile() {
		return this.outputFile;
	}

	@Override
	public void close() throws IOException {
		super.close();
		this.closed = true;
	}

	public void writeTo(OutputStream out) throws IOException {
		if (!this.closed) {
			throw new IOException("Stream not closed");
		} else {
			if (this.isInMemory()) {
				this.memoryOutputStream.writeTo(out);
			} else {
				FileInputStream fis = new FileInputStream(this.outputFile);

				try {
					IOUtils.copy(fis, out);
				} finally {
					IOUtils.closeQuietly(fis);
				}
			}
		}
	}
}
