package org.apache.logging.log4j.core.appender.rolling.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.utils.IOUtils;

public final class CommonsCompressAction extends AbstractAction {
	private static final int BUF_SIZE = 8102;
	private final String name;
	private final File source;
	private final File destination;
	private final boolean deleteSource;

	public CommonsCompressAction(String name, File source, File destination, boolean deleteSource) {
		Objects.requireNonNull(source, "source");
		Objects.requireNonNull(destination, "destination");
		this.name = name;
		this.source = source;
		this.destination = destination;
		this.deleteSource = deleteSource;
	}

	@Override
	public boolean execute() throws IOException {
		return execute(this.name, this.source, this.destination, this.deleteSource);
	}

	public static boolean execute(String name, File source, File destination, boolean deleteSource) throws IOException {
		if (!source.exists()) {
			return false;
		} else {
			LOGGER.debug("Starting {} compression of {}", name, source.getPath());

			try {
				FileInputStream input = new FileInputStream(source);
				Throwable var5 = null;

				try {
					BufferedOutputStream output = new BufferedOutputStream(new CompressorStreamFactory().createCompressorOutputStream(name, new FileOutputStream(destination)));
					Throwable var7 = null;

					try {
						IOUtils.copy(input, output, 8102);
						LOGGER.debug("Finished {} compression of {}", name, source.getPath());
					} catch (Throwable var35) {
						var7 = var35;
						throw var35;
					} finally {
						if (output != null) {
							if (var7 != null) {
								try {
									output.close();
								} catch (Throwable var33) {
									var7.addSuppressed(var33);
								}
							} else {
								output.close();
							}
						}
					}
				} catch (Throwable var37) {
					var5 = var37;
					throw var37;
				} finally {
					if (input != null) {
						if (var5 != null) {
							try {
								input.close();
							} catch (Throwable var32) {
								var5.addSuppressed(var32);
							}
						} else {
							input.close();
						}
					}
				}
			} catch (CompressorException var39) {
				throw new IOException(var39);
			}

			if (deleteSource) {
				try {
					if (Files.deleteIfExists(source.toPath())) {
						LOGGER.debug("Deleted {}", source.toString());
					} else {
						LOGGER.warn("Unable to delete {} after {} compression. File did not exist", source.toString(), name);
					}
				} catch (Exception var34) {
					LOGGER.warn("Unable to delete {} after {} compression, {}", source.toString(), name, var34.getMessage());
				}
			}

			return true;
		}
	}

	@Override
	protected void reportException(Exception ex) {
		LOGGER.warn("Exception during " + this.name + " compression of '" + this.source.toString() + "'.", (Throwable)ex);
	}

	public String toString() {
		return CommonsCompressAction.class.getSimpleName() + '[' + this.source + " to " + this.destination + ", deleteSource=" + this.deleteSource + ']';
	}

	public String getName() {
		return this.name;
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
}
