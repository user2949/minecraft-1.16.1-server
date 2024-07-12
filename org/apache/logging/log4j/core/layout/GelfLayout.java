package org.apache.logging.log4j.core.layout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.apache.logging.log4j.core.net.Severity;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.apache.logging.log4j.core.util.KeyValuePair;
import org.apache.logging.log4j.core.util.NetUtils;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.apache.logging.log4j.util.TriConsumer;

@Plugin(
	name = "GelfLayout",
	category = "Core",
	elementType = "layout",
	printObject = true
)
public final class GelfLayout extends AbstractStringLayout {
	private static final char C = ',';
	private static final int COMPRESSION_THRESHOLD = 1024;
	private static final char Q = '"';
	private static final String QC = "\",";
	private static final String QU = "\"_";
	private final KeyValuePair[] additionalFields;
	private final int compressionThreshold;
	private final GelfLayout.CompressionType compressionType;
	private final String host;
	private final boolean includeStacktrace;
	private final boolean includeThreadContext;
	private static final TriConsumer<String, Object, StringBuilder> WRITE_KEY_VALUES_INTO = new TriConsumer<String, Object, StringBuilder>() {
		public void accept(String key, Object value, StringBuilder stringBuilder) {
			stringBuilder.append("\"_");
			JsonUtils.quoteAsString(key, stringBuilder);
			stringBuilder.append("\":\"");
			JsonUtils.quoteAsString(GelfLayout.toNullSafeString(String.valueOf(value)), stringBuilder);
			stringBuilder.append("\",");
		}
	};
	private static final ThreadLocal<StringBuilder> messageStringBuilder = new ThreadLocal();
	private static final ThreadLocal<StringBuilder> timestampStringBuilder = new ThreadLocal();

	@Deprecated
	public GelfLayout(
		String host, KeyValuePair[] additionalFields, GelfLayout.CompressionType compressionType, int compressionThreshold, boolean includeStacktrace
	) {
		this(null, host, additionalFields, compressionType, compressionThreshold, includeStacktrace, true);
	}

	private GelfLayout(
		Configuration config,
		String host,
		KeyValuePair[] additionalFields,
		GelfLayout.CompressionType compressionType,
		int compressionThreshold,
		boolean includeStacktrace,
		boolean includeThreadContext
	) {
		super(config, StandardCharsets.UTF_8, null, null);
		this.host = host != null ? host : NetUtils.getLocalHostname();
		this.additionalFields = additionalFields != null ? additionalFields : new KeyValuePair[0];
		if (config == null) {
			for (KeyValuePair additionalField : this.additionalFields) {
				if (valueNeedsLookup(additionalField.getValue())) {
					throw new IllegalArgumentException("configuration needs to be set when there are additional fields with variables");
				}
			}
		}

		this.compressionType = compressionType;
		this.compressionThreshold = compressionThreshold;
		this.includeStacktrace = includeStacktrace;
		this.includeThreadContext = includeThreadContext;
	}

	@Deprecated
	public static GelfLayout createLayout(
		@PluginAttribute("host") String host,
		@PluginElement("AdditionalField") KeyValuePair[] additionalFields,
		@PluginAttribute(value = "compressionType",defaultString = "GZIP") GelfLayout.CompressionType compressionType,
		@PluginAttribute(value = "compressionThreshold",defaultInt = 1024) int compressionThreshold,
		@PluginAttribute(value = "includeStacktrace",defaultBoolean = true) boolean includeStacktrace
	) {
		return new GelfLayout(null, host, additionalFields, compressionType, compressionThreshold, includeStacktrace, true);
	}

	@PluginBuilderFactory
	public static <B extends GelfLayout.Builder<B>> B newBuilder() {
		return new GelfLayout.Builder<B>().asBuilder();
	}

	@Override
	public Map<String, String> getContentFormat() {
		return Collections.emptyMap();
	}

	@Override
	public String getContentType() {
		return "application/json; charset=" + this.getCharset();
	}

	@Override
	public byte[] toByteArray(LogEvent event) {
		StringBuilder text = this.toText(event, getStringBuilder(), false);
		byte[] bytes = this.getBytes(text.toString());
		return this.compressionType != GelfLayout.CompressionType.OFF && bytes.length > this.compressionThreshold ? this.compress(bytes) : bytes;
	}

	@Override
	public void encode(LogEvent event, ByteBufferDestination destination) {
		if (this.compressionType != GelfLayout.CompressionType.OFF) {
			super.encode(event, destination);
		} else {
			StringBuilder text = this.toText(event, getStringBuilder(), true);
			Encoder<StringBuilder> helper = this.getStringBuilderEncoder();
			helper.encode(text, destination);
		}
	}

	private byte[] compress(byte[] bytes) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(this.compressionThreshold / 8);
			DeflaterOutputStream stream = this.compressionType.createDeflaterOutputStream(baos);
			Throwable var4 = null;

			byte[] x2;
			try {
				if (stream != null) {
					stream.write(bytes);
					stream.finish();
					return baos.toByteArray();
				}

				x2 = bytes;
			} catch (Throwable var16) {
				var4 = var16;
				throw var16;
			} finally {
				if (stream != null) {
					if (var4 != null) {
						try {
							stream.close();
						} catch (Throwable var15) {
							var4.addSuppressed(var15);
						}
					} else {
						stream.close();
					}
				}
			}

			return x2;
		} catch (IOException var18) {
			StatusLogger.getLogger().error(var18);
			return bytes;
		}
	}

	public String toSerializable(LogEvent event) {
		StringBuilder text = this.toText(event, getStringBuilder(), false);
		return text.toString();
	}

	private StringBuilder toText(LogEvent event, StringBuilder builder, boolean gcFree) {
		builder.append('{');
		builder.append("\"version\":\"1.1\",");
		builder.append("\"host\":\"");
		JsonUtils.quoteAsString(toNullSafeString(this.host), builder);
		builder.append("\",");
		builder.append("\"timestamp\":").append(formatTimestamp(event.getTimeMillis())).append(',');
		builder.append("\"level\":").append(this.formatLevel(event.getLevel())).append(',');
		if (event.getThreadName() != null) {
			builder.append("\"_thread\":\"");
			JsonUtils.quoteAsString(event.getThreadName(), builder);
			builder.append("\",");
		}

		if (event.getLoggerName() != null) {
			builder.append("\"_logger\":\"");
			JsonUtils.quoteAsString(event.getLoggerName(), builder);
			builder.append("\",");
		}

		if (this.additionalFields.length > 0) {
			StrSubstitutor strSubstitutor = this.getConfiguration().getStrSubstitutor();

			for (KeyValuePair additionalField : this.additionalFields) {
				builder.append("\"_");
				JsonUtils.quoteAsString(additionalField.getKey(), builder);
				builder.append("\":\"");
				String value = valueNeedsLookup(additionalField.getValue()) ? strSubstitutor.replace(event, additionalField.getValue()) : additionalField.getValue();
				JsonUtils.quoteAsString(toNullSafeString(value), builder);
				builder.append("\",");
			}
		}

		if (this.includeThreadContext) {
			event.getContextData().forEach(WRITE_KEY_VALUES_INTO, builder);
		}

		if (event.getThrown() != null) {
			builder.append("\"full_message\":\"");
			if (this.includeStacktrace) {
				JsonUtils.quoteAsString(formatThrowable(event.getThrown()), builder);
			} else {
				JsonUtils.quoteAsString(event.getThrown().toString(), builder);
			}

			builder.append("\",");
		}

		builder.append("\"short_message\":\"");
		Message message = event.getMessage();
		if (message instanceof CharSequence) {
			JsonUtils.quoteAsString((CharSequence)message, builder);
		} else if (gcFree && message instanceof StringBuilderFormattable) {
			StringBuilder messageBuffer = getMessageStringBuilder();

			try {
				((StringBuilderFormattable)message).formatTo(messageBuffer);
				JsonUtils.quoteAsString(messageBuffer, builder);
			} finally {
				trimToMaxSize(messageBuffer);
			}
		} else {
			JsonUtils.quoteAsString(toNullSafeString(message.getFormattedMessage()), builder);
		}

		builder.append('"');
		builder.append('}');
		return builder;
	}

	private static boolean valueNeedsLookup(String value) {
		return value != null && value.contains("${");
	}

	private static StringBuilder getMessageStringBuilder() {
		StringBuilder result = (StringBuilder)messageStringBuilder.get();
		if (result == null) {
			result = new StringBuilder(1024);
			messageStringBuilder.set(result);
		}

		result.setLength(0);
		return result;
	}

	private static CharSequence toNullSafeString(CharSequence s) {
		return (CharSequence)(s == null ? "" : s);
	}

	static CharSequence formatTimestamp(long timeMillis) {
		if (timeMillis < 1000L) {
			return "0";
		} else {
			StringBuilder builder = getTimestampStringBuilder();
			builder.append(timeMillis);
			builder.insert(builder.length() - 3, '.');
			return builder;
		}
	}

	private static StringBuilder getTimestampStringBuilder() {
		StringBuilder result = (StringBuilder)timestampStringBuilder.get();
		if (result == null) {
			result = new StringBuilder(20);
			timestampStringBuilder.set(result);
		}

		result.setLength(0);
		return result;
	}

	private int formatLevel(Level level) {
		return Severity.getSeverity(level).getCode();
	}

	static CharSequence formatThrowable(Throwable throwable) {
		StringWriter sw = new StringWriter(2048);
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		pw.flush();
		return sw.getBuffer();
	}

	public static class Builder<B extends GelfLayout.Builder<B>>
		extends AbstractStringLayout.Builder<B>
		implements org.apache.logging.log4j.core.util.Builder<GelfLayout> {
		@PluginBuilderAttribute
		private String host;
		@PluginElement("AdditionalField")
		private KeyValuePair[] additionalFields;
		@PluginBuilderAttribute
		private GelfLayout.CompressionType compressionType = GelfLayout.CompressionType.GZIP;
		@PluginBuilderAttribute
		private int compressionThreshold = 1024;
		@PluginBuilderAttribute
		private boolean includeStacktrace = true;
		@PluginBuilderAttribute
		private boolean includeThreadContext = true;

		public Builder() {
			this.setCharset(StandardCharsets.UTF_8);
		}

		public GelfLayout build() {
			return new GelfLayout(
				this.getConfiguration(),
				this.host,
				this.additionalFields,
				this.compressionType,
				this.compressionThreshold,
				this.includeStacktrace,
				this.includeThreadContext
			);
		}

		public String getHost() {
			return this.host;
		}

		public GelfLayout.CompressionType getCompressionType() {
			return this.compressionType;
		}

		public int getCompressionThreshold() {
			return this.compressionThreshold;
		}

		public boolean isIncludeStacktrace() {
			return this.includeStacktrace;
		}

		public boolean isIncludeThreadContext() {
			return this.includeThreadContext;
		}

		public KeyValuePair[] getAdditionalFields() {
			return this.additionalFields;
		}

		public B setHost(String host) {
			this.host = host;
			return this.asBuilder();
		}

		public B setCompressionType(GelfLayout.CompressionType compressionType) {
			this.compressionType = compressionType;
			return this.asBuilder();
		}

		public B setCompressionThreshold(int compressionThreshold) {
			this.compressionThreshold = compressionThreshold;
			return this.asBuilder();
		}

		public B setIncludeStacktrace(boolean includeStacktrace) {
			this.includeStacktrace = includeStacktrace;
			return this.asBuilder();
		}

		public B setIncludeThreadContext(boolean includeThreadContext) {
			this.includeThreadContext = includeThreadContext;
			return this.asBuilder();
		}

		public B setAdditionalFields(KeyValuePair[] additionalFields) {
			this.additionalFields = additionalFields;
			return this.asBuilder();
		}
	}

	public static enum CompressionType {
		GZIP {
			@Override
			public DeflaterOutputStream createDeflaterOutputStream(OutputStream os) throws IOException {
				return new GZIPOutputStream(os);
			}
		},
		ZLIB {
			@Override
			public DeflaterOutputStream createDeflaterOutputStream(OutputStream os) throws IOException {
				return new DeflaterOutputStream(os);
			}
		},
		OFF {
			@Override
			public DeflaterOutputStream createDeflaterOutputStream(OutputStream os) throws IOException {
				return null;
			}
		};

		private CompressionType() {
		}

		public abstract DeflaterOutputStream createDeflaterOutputStream(OutputStream outputStream) throws IOException;
	}
}
