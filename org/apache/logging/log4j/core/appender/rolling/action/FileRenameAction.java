package org.apache.logging.log4j.core.appender.rolling.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileRenameAction extends AbstractAction {
	private final File source;
	private final File destination;
	private final boolean renameEmptyFiles;

	public FileRenameAction(File src, File dst, boolean renameEmptyFiles) {
		this.source = src;
		this.destination = dst;
		this.renameEmptyFiles = renameEmptyFiles;
	}

	@Override
	public boolean execute() {
		return execute(this.source, this.destination, this.renameEmptyFiles);
	}

	public File getDestination() {
		return this.destination;
	}

	public File getSource() {
		return this.source;
	}

	public boolean isRenameEmptyFiles() {
		return this.renameEmptyFiles;
	}

	public static boolean execute(File source, File destination, boolean renameEmptyFiles) {
		if (!renameEmptyFiles && source.length() <= 0L) {
			try {
				source.delete();
			} catch (Exception var11) {
				LOGGER.error("Unable to delete empty file {}: {} {}", source.getAbsolutePath(), var11.getClass().getName(), var11.getMessage());
			}
		} else {
			File parent = destination.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
				if (!parent.exists()) {
					LOGGER.error("Unable to create directory {}", parent.getAbsolutePath());
					return false;
				}
			}

			try {
				try {
					Files.move(
						Paths.get(source.getAbsolutePath()), Paths.get(destination.getAbsolutePath()), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING
					);
					LOGGER.trace("Renamed file {} to {} with Files.move", source.getAbsolutePath(), destination.getAbsolutePath());
					return true;
				} catch (IOException var12) {
					LOGGER.error(
						"Unable to move file {} to {}: {} {}", source.getAbsolutePath(), destination.getAbsolutePath(), var12.getClass().getName(), var12.getMessage()
					);
					boolean result = source.renameTo(destination);
					if (!result) {
						try {
							Files.copy(Paths.get(source.getAbsolutePath()), Paths.get(destination.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);

							try {
								Files.delete(Paths.get(source.getAbsolutePath()));
								LOGGER.trace("Renamed file {} to {} using copy and delete", source.getAbsolutePath(), destination.getAbsolutePath());
							} catch (IOException var9) {
								LOGGER.error("Unable to delete file {}: {} {}", source.getAbsolutePath(), var9.getClass().getName(), var9.getMessage());

								try {
									new PrintWriter(source.getAbsolutePath()).close();
									LOGGER.trace("Renamed file {} to {} with copy and truncation", source.getAbsolutePath(), destination.getAbsolutePath());
								} catch (IOException var8) {
									LOGGER.error("Unable to overwrite file {}: {} {}", source.getAbsolutePath(), var8.getClass().getName(), var8.getMessage());
								}
							}
						} catch (IOException var10) {
							LOGGER.error(
								"Unable to copy file {} to {}: {} {}", source.getAbsolutePath(), destination.getAbsolutePath(), var10.getClass().getName(), var10.getMessage()
							);
						}
					} else {
						LOGGER.trace("Renamed file {} to {} with source.renameTo", source.getAbsolutePath(), destination.getAbsolutePath());
					}

					return result;
				}
			} catch (RuntimeException var13) {
				LOGGER.error(
					"Unable to rename file {} to {}: {} {}", source.getAbsolutePath(), destination.getAbsolutePath(), var13.getClass().getName(), var13.getMessage()
				);
			}
		}

		return false;
	}

	public String toString() {
		return FileRenameAction.class.getSimpleName() + '[' + this.source + " to " + this.destination + ", renameEmptyFiles=" + this.renameEmptyFiles + ']';
	}
}
