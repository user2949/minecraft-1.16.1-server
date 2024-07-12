package org.apache.logging.log4j.core.appender.rolling.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class ZipCompressAction extends AbstractAction {
	private static final int BUF_SIZE = 8102;
	private final File source;
	private final File destination;
	private final boolean deleteSource;
	private final int level;

	public ZipCompressAction(File source, File destination, boolean deleteSource, int level) {
		Objects.requireNonNull(source, "source");
		Objects.requireNonNull(destination, "destination");
		this.source = source;
		this.destination = destination;
		this.deleteSource = deleteSource;
		this.level = level;
	}

	@Override
	public boolean execute() throws IOException {
		return execute(this.source, this.destination, this.deleteSource, this.level);
	}

	public static boolean execute(File source, File destination, boolean deleteSource, int level) throws IOException {
		if (source.exists()) {
			FileInputStream fis = new FileInputStream(source);
			Throwable var5 = null;

			try {
				ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destination));
				Throwable var7 = null;

				try {
					zos.setLevel(level);
					ZipEntry zipEntry = new ZipEntry(source.getName());
					zos.putNextEntry(zipEntry);
					byte[] inbuf = new byte[8102];

					int n;
					while ((n = fis.read(inbuf)) != -1) {
						zos.write(inbuf, 0, n);
					}
				} catch (Throwable var32) {
					var7 = var32;
					throw var32;
				} finally {
					if (zos != null) {
						if (var7 != null) {
							try {
								zos.close();
							} catch (Throwable var31) {
								var7.addSuppressed(var31);
							}
						} else {
							zos.close();
						}
					}
				}
			} catch (Throwable var34) {
				var5 = var34;
				throw var34;
			} finally {
				if (fis != null) {
					if (var5 != null) {
						try {
							fis.close();
						} catch (Throwable var30) {
							var5.addSuppressed(var30);
						}
					} else {
						fis.close();
					}
				}
			}

			if (deleteSource && !source.delete()) {
				LOGGER.warn("Unable to delete " + source.toString() + '.');
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void reportException(Exception ex) {
		LOGGER.warn("Exception during compression of '" + this.source.toString() + "'.", (Throwable)ex);
	}

	public String toString() {
		return ZipCompressAction.class.getSimpleName()
			+ '['
			+ this.source
			+ " to "
			+ this.destination
			+ ", level="
			+ this.level
			+ ", deleteSource="
			+ this.deleteSource
			+ ']';
	}

	public File getSource() {
		return this.source;
	}

	public File getDestination() {
		return this.destination;
	}

	public boolean isDeleteSource() {
		return this.deleteSource;
	}

	public int getLevel() {
		return this.level;
	}
}
