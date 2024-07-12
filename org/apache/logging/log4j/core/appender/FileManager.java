package org.apache.logging.log4j.core.appender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.core.util.FileUtils;

public class FileManager extends OutputStreamManager {
	private static final FileManager.FileManagerFactory FACTORY = new FileManager.FileManagerFactory();
	private final boolean isAppend;
	private final boolean createOnDemand;
	private final boolean isLocking;
	private final String advertiseURI;
	private final int bufferSize;

	@Deprecated
	protected FileManager(
		String fileName,
		OutputStream os,
		boolean append,
		boolean locking,
		String advertiseURI,
		Layout<? extends Serializable> layout,
		int bufferSize,
		boolean writeHeader
	) {
		this(fileName, os, append, locking, advertiseURI, layout, writeHeader, ByteBuffer.wrap(new byte[bufferSize]));
	}

	@Deprecated
	protected FileManager(
		String fileName,
		OutputStream os,
		boolean append,
		boolean locking,
		String advertiseURI,
		Layout<? extends Serializable> layout,
		boolean writeHeader,
		ByteBuffer buffer
	) {
		super(os, fileName, layout, writeHeader, buffer);
		this.isAppend = append;
		this.createOnDemand = false;
		this.isLocking = locking;
		this.advertiseURI = advertiseURI;
		this.bufferSize = buffer.capacity();
	}

	protected FileManager(
		LoggerContext loggerContext,
		String fileName,
		OutputStream os,
		boolean append,
		boolean locking,
		boolean createOnDemand,
		String advertiseURI,
		Layout<? extends Serializable> layout,
		boolean writeHeader,
		ByteBuffer buffer
	) {
		super(loggerContext, os, fileName, createOnDemand, layout, writeHeader, buffer);
		this.isAppend = append;
		this.createOnDemand = createOnDemand;
		this.isLocking = locking;
		this.advertiseURI = advertiseURI;
		this.bufferSize = buffer.capacity();
	}

	public static FileManager getFileManager(
		String fileName,
		boolean append,
		boolean locking,
		boolean bufferedIo,
		boolean createOnDemand,
		String advertiseUri,
		Layout<? extends Serializable> layout,
		int bufferSize,
		Configuration configuration
	) {
		if (locking && bufferedIo) {
			locking = false;
		}

		return (FileManager)getManager(
			fileName, new FileManager.FactoryData(append, locking, bufferedIo, bufferSize, createOnDemand, advertiseUri, layout, configuration), FACTORY
		);
	}

	@Override
	protected OutputStream createOutputStream() throws FileNotFoundException {
		String filename = this.getFileName();
		LOGGER.debug("Now writing to {} at {}", filename, new Date());
		return new FileOutputStream(filename, this.isAppend);
	}

	@Override
	protected synchronized void write(byte[] bytes, int offset, int length, boolean immediateFlush) {
		if (this.isLocking) {
			try {
				FileChannel channel = ((FileOutputStream)this.getOutputStream()).getChannel();
				FileLock lock = channel.lock(0L, Long.MAX_VALUE, false);
				Throwable var7 = null;

				try {
					super.write(bytes, offset, length, immediateFlush);
				} catch (Throwable var17) {
					var7 = var17;
					throw var17;
				} finally {
					if (lock != null) {
						if (var7 != null) {
							try {
								lock.close();
							} catch (Throwable var16) {
								var7.addSuppressed(var16);
							}
						} else {
							lock.close();
						}
					}
				}
			} catch (IOException var19) {
				throw new AppenderLoggingException("Unable to obtain lock on " + this.getName(), var19);
			}
		} else {
			super.write(bytes, offset, length, immediateFlush);
		}
	}

	@Override
	protected synchronized void writeToDestination(byte[] bytes, int offset, int length) {
		if (this.isLocking) {
			try {
				FileChannel channel = ((FileOutputStream)this.getOutputStream()).getChannel();
				FileLock lock = channel.lock(0L, Long.MAX_VALUE, false);
				Throwable var6 = null;

				try {
					super.writeToDestination(bytes, offset, length);
				} catch (Throwable var16) {
					var6 = var16;
					throw var16;
				} finally {
					if (lock != null) {
						if (var6 != null) {
							try {
								lock.close();
							} catch (Throwable var15) {
								var6.addSuppressed(var15);
							}
						} else {
							lock.close();
						}
					}
				}
			} catch (IOException var18) {
				throw new AppenderLoggingException("Unable to obtain lock on " + this.getName(), var18);
			}
		} else {
			super.writeToDestination(bytes, offset, length);
		}
	}

	public String getFileName() {
		return this.getName();
	}

	public boolean isAppend() {
		return this.isAppend;
	}

	public boolean isCreateOnDemand() {
		return this.createOnDemand;
	}

	public boolean isLocking() {
		return this.isLocking;
	}

	public int getBufferSize() {
		return this.bufferSize;
	}

	@Override
	public Map<String, String> getContentFormat() {
		Map<String, String> result = new HashMap(super.getContentFormat());
		result.put("fileURI", this.advertiseURI);
		return result;
	}

	private static class FactoryData extends ConfigurationFactoryData {
		private final boolean append;
		private final boolean locking;
		private final boolean bufferedIo;
		private final int bufferSize;
		private final boolean createOnDemand;
		private final String advertiseURI;
		private final Layout<? extends Serializable> layout;

		public FactoryData(
			boolean append,
			boolean locking,
			boolean bufferedIo,
			int bufferSize,
			boolean createOnDemand,
			String advertiseURI,
			Layout<? extends Serializable> layout,
			Configuration configuration
		) {
			super(configuration);
			this.append = append;
			this.locking = locking;
			this.bufferedIo = bufferedIo;
			this.bufferSize = bufferSize;
			this.createOnDemand = createOnDemand;
			this.advertiseURI = advertiseURI;
			this.layout = layout;
		}
	}

	private static class FileManagerFactory implements ManagerFactory<FileManager, FileManager.FactoryData> {
		private FileManagerFactory() {
		}

		public FileManager createManager(String name, FileManager.FactoryData data) {
			File file = new File(name);

			try {
				FileUtils.makeParentDirs(file);
				boolean writeHeader = !data.append || !file.exists();
				int actualSize = data.bufferedIo ? data.bufferSize : Constants.ENCODER_BYTE_BUFFER_SIZE;
				ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[actualSize]);
				FileOutputStream fos = data.createOnDemand ? null : new FileOutputStream(file, data.append);
				return new FileManager(
					data.getLoggerContext(), name, fos, data.append, data.locking, data.createOnDemand, data.advertiseURI, data.layout, writeHeader, byteBuffer
				);
			} catch (IOException var8) {
				AbstractManager.LOGGER.error("FileManager (" + name + ") " + var8, (Throwable)var8);
				return null;
			}
		}
	}
}
