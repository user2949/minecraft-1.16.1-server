package org.apache.logging.log4j.core.appender.rolling;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.appender.rolling.action.Action;
import org.apache.logging.log4j.core.appender.rolling.action.FileRenameAction;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.apache.logging.log4j.core.util.Integers;

@Plugin(
	name = "DefaultRolloverStrategy",
	category = "Core",
	printObject = true
)
public class DefaultRolloverStrategy extends AbstractRolloverStrategy {
	private static final int MIN_WINDOW_SIZE = 1;
	private static final int DEFAULT_WINDOW_SIZE = 7;
	private final int maxIndex;
	private final int minIndex;
	private final boolean useMax;
	private final int compressionLevel;
	private final List<Action> customActions;
	private final boolean stopCustomActionsOnError;

	@PluginFactory
	public static DefaultRolloverStrategy createStrategy(
		@PluginAttribute("max") String max,
		@PluginAttribute("min") String min,
		@PluginAttribute("fileIndex") String fileIndex,
		@PluginAttribute("compressionLevel") String compressionLevelStr,
		@PluginElement("Actions") Action[] customActions,
		@PluginAttribute(value = "stopCustomActionsOnError",defaultBoolean = true) boolean stopCustomActionsOnError,
		@PluginConfiguration Configuration config
	) {
		int minIndex;
		int maxIndex;
		boolean useMax;
		if (fileIndex != null && fileIndex.equalsIgnoreCase("nomax")) {
			minIndex = Integer.MIN_VALUE;
			maxIndex = Integer.MAX_VALUE;
			useMax = false;
		} else {
			useMax = fileIndex == null ? true : fileIndex.equalsIgnoreCase("max");
			minIndex = 1;
			if (min != null) {
				minIndex = Integer.parseInt(min);
				if (minIndex < 1) {
					LOGGER.error("Minimum window size too small. Limited to 1");
					minIndex = 1;
				}
			}

			maxIndex = 7;
			if (max != null) {
				maxIndex = Integer.parseInt(max);
				if (maxIndex < minIndex) {
					maxIndex = minIndex < 7 ? 7 : minIndex;
					LOGGER.error("Maximum window size must be greater than the minimum windows size. Set to " + maxIndex);
				}
			}
		}

		int compressionLevel = Integers.parseInt(compressionLevelStr, -1);
		return new DefaultRolloverStrategy(minIndex, maxIndex, useMax, compressionLevel, config.getStrSubstitutor(), customActions, stopCustomActionsOnError);
	}

	protected DefaultRolloverStrategy(
		int minIndex, int maxIndex, boolean useMax, int compressionLevel, StrSubstitutor strSubstitutor, Action[] customActions, boolean stopCustomActionsOnError
	) {
		super(strSubstitutor);
		this.minIndex = minIndex;
		this.maxIndex = maxIndex;
		this.useMax = useMax;
		this.compressionLevel = compressionLevel;
		this.stopCustomActionsOnError = stopCustomActionsOnError;
		this.customActions = customActions == null ? Collections.emptyList() : Arrays.asList(customActions);
	}

	public int getCompressionLevel() {
		return this.compressionLevel;
	}

	public List<Action> getCustomActions() {
		return this.customActions;
	}

	public int getMaxIndex() {
		return this.maxIndex;
	}

	public int getMinIndex() {
		return this.minIndex;
	}

	public boolean isStopCustomActionsOnError() {
		return this.stopCustomActionsOnError;
	}

	public boolean isUseMax() {
		return this.useMax;
	}

	private int purge(int lowIndex, int highIndex, RollingFileManager manager) {
		return this.useMax ? this.purgeAscending(lowIndex, highIndex, manager) : this.purgeDescending(lowIndex, highIndex, manager);
	}

	private int purgeAscending(int lowIndex, int highIndex, RollingFileManager manager) {
		SortedMap<Integer, Path> eligibleFiles = this.getEligibleFiles(manager);
		int maxFiles = highIndex - lowIndex + 1;
		boolean renameFiles = false;

		while (eligibleFiles.size() >= maxFiles) {
			try {
				LOGGER.debug("Eligible files: {}", eligibleFiles);
				Integer key = (Integer)eligibleFiles.firstKey();
				LOGGER.debug("Deleting {}", ((Path)eligibleFiles.get(key)).toFile().getAbsolutePath());
				Files.delete((Path)eligibleFiles.get(key));
				eligibleFiles.remove(key);
				renameFiles = true;
			} catch (IOException var16) {
				LOGGER.error("Unable to delete {}, {}", eligibleFiles.firstKey(), var16.getMessage(), var16);
				break;
			}
		}

		StringBuilder buf = new StringBuilder();
		if (renameFiles) {
			for (Entry<Integer, Path> entry : eligibleFiles.entrySet()) {
				buf.setLength(0);
				manager.getPatternProcessor().formatFileName(this.strSubstitutor, buf, (Integer)entry.getKey() - 1);
				String currentName = ((Path)entry.getValue()).toFile().getName();
				String renameTo = buf.toString();
				int suffixLength = this.suffixLength(renameTo);
				if (suffixLength > 0 && this.suffixLength(currentName) == 0) {
					renameTo = renameTo.substring(0, renameTo.length() - suffixLength);
				}

				Action action = new FileRenameAction(((Path)entry.getValue()).toFile(), new File(renameTo), true);

				try {
					LOGGER.debug("DefaultRolloverStrategy.purgeAscending executing {}", action);
					if (!action.execute()) {
						return -1;
					}
				} catch (Exception var15) {
					LOGGER.warn("Exception during purge in RollingFileAppender", (Throwable)var15);
					return -1;
				}
			}
		}

		return eligibleFiles.size() > 0 ? (eligibleFiles.lastKey() < highIndex ? (Integer)eligibleFiles.lastKey() + 1 : highIndex) : lowIndex;
	}

	private int purgeDescending(int lowIndex, int highIndex, RollingFileManager manager) {
		SortedMap<Integer, Path> eligibleFiles = this.getEligibleFiles(manager, false);
		int maxFiles = highIndex - lowIndex + 1;

		while (eligibleFiles.size() >= maxFiles) {
			try {
				Integer key = (Integer)eligibleFiles.firstKey();
				Files.delete((Path)eligibleFiles.get(key));
				eligibleFiles.remove(key);
			} catch (IOException var15) {
				LOGGER.error("Unable to delete {}, {}", eligibleFiles.firstKey(), var15.getMessage(), var15);
				break;
			}
		}

		StringBuilder buf = new StringBuilder();

		for (Entry<Integer, Path> entry : eligibleFiles.entrySet()) {
			buf.setLength(0);
			manager.getPatternProcessor().formatFileName(this.strSubstitutor, buf, (Integer)entry.getKey() + 1);
			String currentName = ((Path)entry.getValue()).toFile().getName();
			String renameTo = buf.toString();
			int suffixLength = this.suffixLength(renameTo);
			if (suffixLength > 0 && this.suffixLength(currentName) == 0) {
				renameTo = renameTo.substring(0, renameTo.length() - suffixLength);
			}

			Action action = new FileRenameAction(((Path)entry.getValue()).toFile(), new File(renameTo), true);

			try {
				LOGGER.debug("DefaultRolloverStrategy.purgeDescending executing {}", action);
				if (!action.execute()) {
					return -1;
				}
			} catch (Exception var14) {
				LOGGER.warn("Exception during purge in RollingFileAppender", (Throwable)var14);
				return -1;
			}
		}

		return lowIndex;
	}

	@Override
	public RolloverDescription rollover(RollingFileManager manager) throws SecurityException {
		int fileIndex;
		if (this.minIndex == Integer.MIN_VALUE) {
			SortedMap<Integer, Path> eligibleFiles = this.getEligibleFiles(manager);
			fileIndex = eligibleFiles.size() > 0 ? (Integer)eligibleFiles.lastKey() + 1 : 1;
		} else {
			if (this.maxIndex < 0) {
				return null;
			}

			long startNanos = System.nanoTime();
			fileIndex = this.purge(this.minIndex, this.maxIndex, manager);
			if (fileIndex < 0) {
				return null;
			}

			if (LOGGER.isTraceEnabled()) {
				double durationMillis = (double)TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos);
				LOGGER.trace("DefaultRolloverStrategy.purge() took {} milliseconds", durationMillis);
			}
		}

		StringBuilder buf = new StringBuilder(255);
		manager.getPatternProcessor().formatFileName(this.strSubstitutor, buf, fileIndex);
		String currentFileName = manager.getFileName();
		String renameTo = buf.toString();
		String compressedName = renameTo;
		Action compressAction = null;
		FileExtension fileExtension = manager.getFileExtension();
		if (fileExtension != null) {
			renameTo = renameTo.substring(0, renameTo.length() - fileExtension.length());
			compressAction = fileExtension.createCompressAction(renameTo, compressedName, true, this.compressionLevel);
		}

		if (currentFileName.equals(renameTo)) {
			LOGGER.warn("Attempt to rename file {} to itself will be ignored", currentFileName);
			return new RolloverDescriptionImpl(currentFileName, false, null, null);
		} else {
			FileRenameAction renameAction = new FileRenameAction(new File(currentFileName), new File(renameTo), manager.isRenameEmptyFiles());
			Action asyncAction = this.merge(compressAction, this.customActions, this.stopCustomActionsOnError);
			return new RolloverDescriptionImpl(currentFileName, false, renameAction, asyncAction);
		}
	}

	public String toString() {
		return "DefaultRolloverStrategy(min=" + this.minIndex + ", max=" + this.maxIndex + ", useMax=" + this.useMax + ")";
	}
}
