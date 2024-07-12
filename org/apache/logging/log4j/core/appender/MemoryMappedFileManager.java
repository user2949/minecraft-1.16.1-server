package org.apache.logging.log4j.core.appender;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.util.Closer;
import org.apache.logging.log4j.core.util.FileUtils;
import org.apache.logging.log4j.core.util.NullOutputStream;

public class MemoryMappedFileManager extends OutputStreamManager {
	static final int DEFAULT_REGION_LENGTH = 33554432;
	private static final int MAX_REMAP_COUNT = 10;
	private static final MemoryMappedFileManager.MemoryMappedFileManagerFactory FACTORY = new MemoryMappedFileManager.MemoryMappedFileManagerFactory();
	private static final double NANOS_PER_MILLISEC = 1000000.0;
	private final boolean immediateFlush;
	private final int regionLength;
	private final String advertiseURI;
	private final RandomAccessFile randomAccessFile;
	private final ThreadLocal<Boolean> isEndOfBatch = new ThreadLocal();
	private MappedByteBuffer mappedBuffer;
	private long mappingOffset;

	protected MemoryMappedFileManager(
		RandomAccessFile file,
		String fileName,
		OutputStream os,
		boolean immediateFlush,
		long position,
		int regionLength,
		String advertiseURI,
		Layout<? extends Serializable> layout,
		boolean writeHeader
	) throws IOException {
		super(os, fileName, layout, writeHeader, ByteBuffer.wrap(new byte[0]));
		this.immediateFlush = immediateFlush;
		this.randomAccessFile = (RandomAccessFile)Objects.requireNonNull(file, "RandomAccessFile");
		this.regionLength = regionLength;
		this.advertiseURI = advertiseURI;
		this.isEndOfBatch.set(Boolean.FALSE);
		this.mappedBuffer = mmap(this.randomAccessFile.getChannel(), this.getFileName(), position, regionLength);
		this.byteBuffer = this.mappedBuffer;
		this.mappingOffset = position;
	}

	public static MemoryMappedFileManager getFileManager(
		String fileName, boolean append, boolean immediateFlush, int regionLength, String advertiseURI, Layout<? extends Serializable> layout
	) {
		return (MemoryMappedFileManager)getManager(
			fileName, new MemoryMappedFileManager.FactoryData(append, immediateFlush, regionLength, advertiseURI, layout), FACTORY
		);
	}

	public Boolean isEndOfBatch() {
		return (Boolean)this.isEndOfBatch.get();
	}

	public void setEndOfBatch(boolean endOfBatch) {
		this.isEndOfBatch.set(endOfBatch);
	}

	@Override
	protected synchronized void write(byte[] bytes, int offset, int length, boolean immediateFlush) {
		while (length > this.mappedBuffer.remaining()) {
			int chunk = this.mappedBuffer.remaining();
			this.mappedBuffer.put(bytes, offset, chunk);
			offset += chunk;
			length -= chunk;
			this.remap();
		}

		this.mappedBuffer.put(bytes, offset, length);
	}

	private synchronized void remap() {
		long offset = this.mappingOffset + (long)this.mappedBuffer.position();
		int length = this.mappedBuffer.remaining() + this.regionLength;

		try {
			unsafeUnmap(this.mappedBuffer);
			long fileLength = this.randomAccessFile.length() + (long)this.regionLength;
			LOGGER.debug("{} {} extending {} by {} bytes to {}", this.getClass().getSimpleName(), this.getName(), this.getFileName(), this.regionLength, fileLength);
			long startNanos = System.nanoTime();
			this.randomAccessFile.setLength(fileLength);
			float millis = (float)((double)(System.nanoTime() - startNanos) / 1000000.0);
			LOGGER.debug("{} {} extended {} OK in {} millis", this.getClass().getSimpleName(), this.getName(), this.getFileName(), millis);
			this.mappedBuffer = mmap(this.randomAccessFile.getChannel(), this.getFileName(), offset, length);
			this.byteBuffer = this.mappedBuffer;
			this.mappingOffset = offset;
		} catch (Exception var9) {
			this.logError("Unable to remap", var9);
		}
	}

	@Override
	public synchronized void flush() {
		this.mappedBuffer.force();
	}

	@Override
	public synchronized boolean closeOutputStream() {
		long position = (long)this.mappedBuffer.position();
		long length = this.mappingOffset + position;

		try {
			unsafeUnmap(this.mappedBuffer);
		} catch (Exception var7) {
			this.logError("Unable to unmap MappedBuffer", var7);
		}

		try {
			LOGGER.debug("MMapAppender closing. Setting {} length to {} (offset {} + position {})", this.getFileName(), length, this.mappingOffset, position);
			this.randomAccessFile.setLength(length);
			this.randomAccessFile.close();
			return true;
		} catch (IOException var6) {
			this.logError("Unable to close MemoryMappedFile", var6);
			return false;
		}
	}

	public static MappedByteBuffer mmap(FileChannel fileChannel, String fileName, long start, int size) throws IOException {
		int i = 1;

		while (true) {
			try {
				LOGGER.debug("MMapAppender remapping {} start={}, size={}", fileName, start, size);
				long startNanos = System.nanoTime();
				MappedByteBuffer map = fileChannel.map(MapMode.READ_WRITE, start, (long)size);
				map.order(ByteOrder.nativeOrder());
				float millis = (float)((double)(System.nanoTime() - startNanos) / 1000000.0);
				LOGGER.debug("MMapAppender remapped {} OK in {} millis", fileName, millis);
				return map;
			} catch (IOException var11) {
				if (var11.getMessage() == null || !var11.getMessage().endsWith("user-mapped section open")) {
					throw var11;
				}

				LOGGER.debug("Remap attempt {}/{} failed. Retrying...", i, 10, var11);
				if (i < 10) {
					Thread.yield();
				} else {
					try {
						Thread.sleep(1L);
					} catch (InterruptedException var10) {
						Thread.currentThread().interrupt();
						throw var11;
					}
				}

				i++;
			}
		}
	}

	private static void unsafeUnmap(MappedByteBuffer mbb) throws PrivilegedActionException {
		LOGGER.debug("MMapAppender unmapping old buffer...");
		long startNanos = System.nanoTime();
		AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
			public Object run() throws Exception {
				Method getCleanerMethod = mbb.getClass().getMethod("cleaner");
				getCleanerMethod.setAccessible(true);
				Object cleaner = getCleanerMethod.invoke(mbb);
				Method cleanMethod = cleaner.getClass().getMethod("clean");
				cleanMethod.invoke(cleaner);
				return null;
			}
		});
		float millis = (float)((double)(System.nanoTime() - startNanos) / 1000000.0);
		LOGGER.debug("MMapAppender unmapped buffer OK in {} millis", millis);
	}

	public String getFileName() {
		return this.getName();
	}

	public int getRegionLength() {
		return this.regionLength;
	}

	public boolean isImmediateFlush() {
		return this.immediateFlush;
	}

	@Override
	public Map<String, String> getContentFormat() {
		Map<String, String> result = new HashMap(super.getContentFormat());
		result.put("fileURI", this.advertiseURI);
		return result;
	}

	@Override
	protected void flushBuffer(ByteBuffer buffer) {
	}

	@Override
	public ByteBuffer getByteBuffer() {
		return this.mappedBuffer;
	}

	@Override
	public ByteBuffer drain(ByteBuffer buf) {
		this.remap();
		return this.mappedBuffer;
	}

	private static class FactoryData {
		private final boolean append;
		private final boolean immediateFlush;
		private final int regionLength;
		private final String advertiseURI;
		private final Layout<? extends Serializable> layout;

		public FactoryData(boolean append, boolean immediateFlush, int regionLength, String advertiseURI, Layout<? extends Serializable> layout) {
			this.append = append;
			this.immediateFlush = immediateFlush;
			this.regionLength = regionLength;
			this.advertiseURI = advertiseURI;
			this.layout = layout;
		}
	}

	private static class MemoryMappedFileManagerFactory implements ManagerFactory<MemoryMappedFileManager, MemoryMappedFileManager.FactoryData> {
		private MemoryMappedFileManagerFactory() {
		}

		public MemoryMappedFileManager createManager(String name, MemoryMappedFileManager.FactoryData data) {
			File file = new File(name);
			if (!data.append) {
				file.delete();
			}

			boolean writeHeader = !data.append || !file.exists();
			OutputStream os = NullOutputStream.getInstance();
			RandomAccessFile raf = null;

			try {
				FileUtils.makeParentDirs(file);
				raf = new RandomAccessFile(name, "rw");
				long position = data.append ? raf.length() : 0L;
				raf.setLength(position + (long)data.regionLength);
				return new MemoryMappedFileManager(raf, name, os, data.immediateFlush, position, data.regionLength, data.advertiseURI, data.layout, writeHeader);
			} catch (Exception var9) {
				AbstractManager.LOGGER.error("MemoryMappedFileManager (" + name + ") " + var9, (Throwable)var9);
				Closer.closeSilently(raf);
				return null;
			}
		}
	}
}
