package org.apache.logging.log4j.core.appender;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.net.Advertiser;
import org.apache.logging.log4j.core.util.Booleans;
import org.apache.logging.log4j.core.util.Integers;

@Plugin(
	name = "MemoryMappedFile",
	category = "Core",
	elementType = "appender",
	printObject = true
)
public final class MemoryMappedFileAppender extends AbstractOutputStreamAppender<MemoryMappedFileManager> {
	private static final int BIT_POSITION_1GB = 30;
	private static final int MAX_REGION_LENGTH = 1073741824;
	private static final int MIN_REGION_LENGTH = 256;
	private final String fileName;
	private Object advertisement;
	private final Advertiser advertiser;

	private MemoryMappedFileAppender(
		String name,
		Layout<? extends Serializable> layout,
		Filter filter,
		MemoryMappedFileManager manager,
		String filename,
		boolean ignoreExceptions,
		boolean immediateFlush,
		Advertiser advertiser
	) {
		super(name, layout, filter, ignoreExceptions, immediateFlush, manager);
		if (advertiser != null) {
			Map<String, String> configuration = new HashMap(layout.getContentFormat());
			configuration.putAll(manager.getContentFormat());
			configuration.put("contentType", layout.getContentType());
			configuration.put("name", name);
			this.advertisement = advertiser.advertise(configuration);
		}

		this.fileName = filename;
		this.advertiser = advertiser;
	}

	@Override
	public boolean stop(long timeout, TimeUnit timeUnit) {
		this.setStopping();
		super.stop(timeout, timeUnit, false);
		if (this.advertiser != null) {
			this.advertiser.unadvertise(this.advertisement);
		}

		this.setStopped();
		return true;
	}

	@Override
	public void append(LogEvent event) {
		this.getManager().setEndOfBatch(event.isEndOfBatch());
		super.append(event);
	}

	public String getFileName() {
		return this.fileName;
	}

	public int getRegionLength() {
		return this.getManager().getRegionLength();
	}

	@Deprecated
	public static <B extends MemoryMappedFileAppender.Builder<B>> MemoryMappedFileAppender createAppender(
		String fileName,
		String append,
		String name,
		String immediateFlush,
		String regionLengthStr,
		String ignore,
		Layout<? extends Serializable> layout,
		Filter filter,
		String advertise,
		String advertiseURI,
		Configuration config
	) {
		boolean isAppend = Booleans.parseBoolean(append, true);
		boolean isImmediateFlush = Booleans.parseBoolean(immediateFlush, false);
		boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
		boolean isAdvertise = Boolean.parseBoolean(advertise);
		int regionLength = Integers.parseInt(regionLengthStr, 33554432);
		return ((MemoryMappedFileAppender.Builder)((MemoryMappedFileAppender.Builder)((MemoryMappedFileAppender.Builder)((MemoryMappedFileAppender.Builder)((MemoryMappedFileAppender.Builder)((MemoryMappedFileAppender.Builder)newBuilder(
										
									)
									.setAdvertise(isAdvertise)
									.setAdvertiseURI(advertiseURI)
									.setAppend(isAppend)
									.setConfiguration(config))
								.setFileName(fileName)
								.withFilter(filter))
							.withIgnoreExceptions(ignoreExceptions))
						.withImmediateFlush(isImmediateFlush))
					.withLayout(layout))
				.withName(name))
			.setRegionLength(regionLength)
			.build();
	}

	@PluginBuilderFactory
	public static <B extends MemoryMappedFileAppender.Builder<B>> B newBuilder() {
		return new MemoryMappedFileAppender.Builder<B>().asBuilder();
	}

	private static int determineValidRegionLength(String name, int regionLength) {
		if (regionLength > 1073741824) {
			LOGGER.info("MemoryMappedAppender[{}] Reduced region length from {} to max length: {}", name, regionLength, 1073741824);
			return 1073741824;
		} else if (regionLength < 256) {
			LOGGER.info("MemoryMappedAppender[{}] Expanded region length from {} to min length: {}", name, regionLength, 256);
			return 256;
		} else {
			int result = Integers.ceilingNextPowerOfTwo(regionLength);
			if (regionLength != result) {
				LOGGER.info("MemoryMappedAppender[{}] Rounded up region length from {} to next power of two: {}", name, regionLength, result);
			}

			return result;
		}
	}

	public static class Builder<B extends MemoryMappedFileAppender.Builder<B>>
		extends AbstractOutputStreamAppender.Builder<B>
		implements org.apache.logging.log4j.core.util.Builder<MemoryMappedFileAppender> {
		@PluginBuilderAttribute("fileName")
		private String fileName;
		@PluginBuilderAttribute("append")
		private boolean append = true;
		@PluginBuilderAttribute("regionLength")
		private int regionLength = 33554432;
		@PluginBuilderAttribute("advertise")
		private boolean advertise;
		@PluginBuilderAttribute("advertiseURI")
		private String advertiseURI;

		public MemoryMappedFileAppender build() {
			String name = this.getName();
			int actualRegionLength = MemoryMappedFileAppender.determineValidRegionLength(name, this.regionLength);
			if (name == null) {
				MemoryMappedFileAppender.LOGGER.error("No name provided for MemoryMappedFileAppender");
				return null;
			} else if (this.fileName == null) {
				MemoryMappedFileAppender.LOGGER.error("No filename provided for MemoryMappedFileAppender with name " + name);
				return null;
			} else {
				Layout<? extends Serializable> layout = this.getOrCreateLayout();
				MemoryMappedFileManager manager = MemoryMappedFileManager.getFileManager(
					this.fileName, this.append, this.isImmediateFlush(), actualRegionLength, this.advertiseURI, layout
				);
				return manager == null
					? null
					: new MemoryMappedFileAppender(
						name, layout, this.getFilter(), manager, this.fileName, this.isIgnoreExceptions(), false, this.advertise ? this.getConfiguration().getAdvertiser() : null
					);
			}
		}

		public B setFileName(String fileName) {
			this.fileName = fileName;
			return this.asBuilder();
		}

		public B setAppend(boolean append) {
			this.append = append;
			return this.asBuilder();
		}

		public B setRegionLength(int regionLength) {
			this.regionLength = regionLength;
			return this.asBuilder();
		}

		public B setAdvertise(boolean advertise) {
			this.advertise = advertise;
			return this.asBuilder();
		}

		public B setAdvertiseURI(String advertiseURI) {
			this.advertiseURI = advertiseURI;
			return this.asBuilder();
		}
	}
}
