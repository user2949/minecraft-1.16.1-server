package org.apache.logging.log4j.core.appender;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.util.FileUtils;
import org.apache.logging.log4j.core.util.NullOutputStream;

public class RandomAccessFileManager extends OutputStreamManager {
	static final int DEFAULT_BUFFER_SIZE = 262144;
	private static final RandomAccessFileManager.RandomAccessFileManagerFactory FACTORY = new RandomAccessFileManager.RandomAccessFileManagerFactory();
	private final String advertiseURI;
	private final RandomAccessFile randomAccessFile;
	private final ThreadLocal<Boolean> isEndOfBatch = new ThreadLocal();

	protected RandomAccessFileManager(
		LoggerContext loggerContext,
		RandomAccessFile file,
		String fileName,
		OutputStream os,
		int bufferSize,
		String advertiseURI,
		Layout<? extends Serializable> layout,
		boolean writeHeader
	) {
		super(loggerContext, os, fileName, false, layout, writeHeader, ByteBuffer.wrap(new byte[bufferSize]));
		this.randomAccessFile = file;
		this.advertiseURI = advertiseURI;
		this.isEndOfBatch.set(Boolean.FALSE);
	}

	public static RandomAccessFileManager getFileManager(
		String fileName, boolean append, boolean isFlush, int bufferSize, String advertiseURI, Layout<? extends Serializable> layout, Configuration configuration
	) {
		return (RandomAccessFileManager)getManager(
			fileName, new RandomAccessFileManager.FactoryData(append, isFlush, bufferSize, advertiseURI, layout, configuration), FACTORY
		);
	}

	public Boolean isEndOfBatch() {
		return (Boolean)this.isEndOfBatch.get();
	}

	public void setEndOfBatch(boolean endOfBatch) {
		this.isEndOfBatch.set(endOfBatch);
	}

	@Override
	protected void writeToDestination(byte[] bytes, int offset, int length) {
		try {
			this.randomAccessFile.write(bytes, offset, length);
		} catch (IOException var6) {
			String msg = "Error writing to RandomAccessFile " + this.getName();
			throw new AppenderLoggingException(msg, var6);
		}
	}

	@Override
	public synchronized void flush() {
		this.flushBuffer(this.byteBuffer);
	}

	@Override
	public synchronized boolean closeOutputStream() {
		this.flush();

		try {
			this.randomAccessFile.close();
			return true;
		} catch (IOException var2) {
			this.logError("Unable to close RandomAccessFile", var2);
			return false;
		}
	}

	public String getFileName() {
		return this.getName();
	}

	public int getBufferSize() {
		return this.byteBuffer.capacity();
	}

	@Override
	public Map<String, String> getContentFormat() {
		Map<String, String> result = new HashMap(super.getContentFormat());
		result.put("fileURI", this.advertiseURI);
		return result;
	}

	private static class FactoryData extends ConfigurationFactoryData {
		private final boolean append;
		private final boolean immediateFlush;
		private final int bufferSize;
		private final String advertiseURI;
		private final Layout<? extends Serializable> layout;

		public FactoryData(
			boolean append, boolean immediateFlush, int bufferSize, String advertiseURI, Layout<? extends Serializable> layout, Configuration configuration
		) {
			super(configuration);
			this.append = append;
			this.immediateFlush = immediateFlush;
			this.bufferSize = bufferSize;
			this.advertiseURI = advertiseURI;
			this.layout = layout;
		}
	}

	private static class RandomAccessFileManagerFactory implements ManagerFactory<RandomAccessFileManager, RandomAccessFileManager.FactoryData> {
		private RandomAccessFileManagerFactory() {
		}

		public RandomAccessFileManager createManager(String name, RandomAccessFileManager.FactoryData data) {
			File file = new File(name);
			if (!data.append) {
				file.delete();
			}

			boolean writeHeader = !data.append || !file.exists();
			OutputStream os = NullOutputStream.getInstance();

			try {
				FileUtils.makeParentDirs(file);
				RandomAccessFile raf = new RandomAccessFile(name, "rw");
				if (data.append) {
					raf.seek(raf.length());
				} else {
					raf.setLength(0L);
				}

				return new RandomAccessFileManager(data.getLoggerContext(), raf, name, os, data.bufferSize, data.advertiseURI, data.layout, writeHeader);
			} catch (Exception var8) {
				AbstractManager.LOGGER.error("RandomAccessFileManager (" + name + ") " + var8, (Throwable)var8);
				return null;
			}
		}
	}
}
