package org.apache.logging.log4j.core.layout;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout.Serializer;
import org.apache.logging.log4j.core.layout.AbstractStringLayout.Serializer2;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternFormatter;
import org.apache.logging.log4j.core.pattern.PatternParser;
import org.apache.logging.log4j.core.pattern.RegexReplacement;
import org.apache.logging.log4j.util.Strings;

@Plugin(
	name = "PatternLayout",
	category = "Core",
	elementType = "layout",
	printObject = true
)
public final class PatternLayout extends AbstractStringLayout {
	public static final String DEFAULT_CONVERSION_PATTERN = "%m%n";
	public static final String TTCC_CONVERSION_PATTERN = "%r [%t] %p %c %notEmpty{%x }- %m%n";
	public static final String SIMPLE_CONVERSION_PATTERN = "%d [%t] %p %c - %m%n";
	public static final String KEY = "Converter";
	private final String conversionPattern;
	private final PatternSelector patternSelector;
	private final Serializer eventSerializer;

	private PatternLayout(
		Configuration config,
		RegexReplacement replace,
		String eventPattern,
		PatternSelector patternSelector,
		Charset charset,
		boolean alwaysWriteExceptions,
		boolean disableAnsi,
		boolean noConsoleNoAnsi,
		String headerPattern,
		String footerPattern
	) {
		super(
			config,
			charset,
			newSerializerBuilder()
				.setConfiguration(config)
				.setReplace(replace)
				.setPatternSelector(patternSelector)
				.setAlwaysWriteExceptions(alwaysWriteExceptions)
				.setDisableAnsi(disableAnsi)
				.setNoConsoleNoAnsi(noConsoleNoAnsi)
				.setPattern(headerPattern)
				.build(),
			newSerializerBuilder()
				.setConfiguration(config)
				.setReplace(replace)
				.setPatternSelector(patternSelector)
				.setAlwaysWriteExceptions(alwaysWriteExceptions)
				.setDisableAnsi(disableAnsi)
				.setNoConsoleNoAnsi(noConsoleNoAnsi)
				.setPattern(footerPattern)
				.build()
		);
		this.conversionPattern = eventPattern;
		this.patternSelector = patternSelector;
		this.eventSerializer = newSerializerBuilder()
			.setConfiguration(config)
			.setReplace(replace)
			.setPatternSelector(patternSelector)
			.setAlwaysWriteExceptions(alwaysWriteExceptions)
			.setDisableAnsi(disableAnsi)
			.setNoConsoleNoAnsi(noConsoleNoAnsi)
			.setPattern(eventPattern)
			.setDefaultPattern("%m%n")
			.build();
	}

	public static PatternLayout.SerializerBuilder newSerializerBuilder() {
		return new PatternLayout.SerializerBuilder();
	}

	@Deprecated
	public static Serializer createSerializer(
		Configuration configuration,
		RegexReplacement replace,
		String pattern,
		String defaultPattern,
		PatternSelector patternSelector,
		boolean alwaysWriteExceptions,
		boolean noConsoleNoAnsi
	) {
		PatternLayout.SerializerBuilder builder = newSerializerBuilder();
		builder.setAlwaysWriteExceptions(alwaysWriteExceptions);
		builder.setConfiguration(configuration);
		builder.setDefaultPattern(defaultPattern);
		builder.setNoConsoleNoAnsi(noConsoleNoAnsi);
		builder.setPattern(pattern);
		builder.setPatternSelector(patternSelector);
		builder.setReplace(replace);
		return builder.build();
	}

	public String getConversionPattern() {
		return this.conversionPattern;
	}

	@Override
	public Map<String, String> getContentFormat() {
		Map<String, String> result = new HashMap();
		result.put("structured", "false");
		result.put("formatType", "conversion");
		result.put("format", this.conversionPattern);
		return result;
	}

	public String toSerializable(LogEvent event) {
		return this.eventSerializer.toSerializable(event);
	}

	@Override
	public void encode(LogEvent event, ByteBufferDestination destination) {
		if (!(this.eventSerializer instanceof Serializer2)) {
			super.encode(event, destination);
		} else {
			StringBuilder text = this.toText((Serializer2)this.eventSerializer, event, getStringBuilder());
			Encoder<StringBuilder> encoder = this.getStringBuilderEncoder();
			encoder.encode(text, destination);
			trimToMaxSize(text);
		}
	}

	private StringBuilder toText(Serializer2 serializer, LogEvent event, StringBuilder destination) {
		return serializer.toSerializable(event, destination);
	}

	public static PatternParser createPatternParser(Configuration config) {
		if (config == null) {
			return new PatternParser(config, "Converter", LogEventPatternConverter.class);
		} else {
			PatternParser parser = config.getComponent("Converter");
			if (parser == null) {
				parser = new PatternParser(config, "Converter", LogEventPatternConverter.class);
				config.addComponent("Converter", parser);
				parser = config.getComponent("Converter");
			}

			return parser;
		}
	}

	public String toString() {
		return this.patternSelector == null ? this.conversionPattern : this.patternSelector.toString();
	}

	@PluginFactory
	@Deprecated
	public static PatternLayout createLayout(
		@PluginAttribute(value = "pattern",defaultString = "%m%n") String pattern,
		@PluginElement("PatternSelector") PatternSelector patternSelector,
		@PluginConfiguration Configuration config,
		@PluginElement("Replace") RegexReplacement replace,
		@PluginAttribute("charset") Charset charset,
		@PluginAttribute(value = "alwaysWriteExceptions",defaultBoolean = true) boolean alwaysWriteExceptions,
		@PluginAttribute("noConsoleNoAnsi") boolean noConsoleNoAnsi,
		@PluginAttribute("header") String headerPattern,
		@PluginAttribute("footer") String footerPattern
	) {
		return newBuilder()
			.withPattern(pattern)
			.withPatternSelector(patternSelector)
			.withConfiguration(config)
			.withRegexReplacement(replace)
			.withCharset(charset)
			.withAlwaysWriteExceptions(alwaysWriteExceptions)
			.withNoConsoleNoAnsi(noConsoleNoAnsi)
			.withHeader(headerPattern)
			.withFooter(footerPattern)
			.build();
	}

	public static PatternLayout createDefaultLayout() {
		return newBuilder().build();
	}

	public static PatternLayout createDefaultLayout(Configuration configuration) {
		return newBuilder().withConfiguration(configuration).build();
	}

	@PluginBuilderFactory
	public static PatternLayout.Builder newBuilder() {
		return new PatternLayout.Builder();
	}

	public static class Builder implements org.apache.logging.log4j.core.util.Builder<PatternLayout> {
		@PluginBuilderAttribute
		private String pattern = "%m%n";
		@PluginElement("PatternSelector")
		private PatternSelector patternSelector;
		@PluginConfiguration
		private Configuration configuration;
		@PluginElement("Replace")
		private RegexReplacement regexReplacement;
		@PluginBuilderAttribute
		private Charset charset = Charset.defaultCharset();
		@PluginBuilderAttribute
		private boolean alwaysWriteExceptions = true;
		@PluginBuilderAttribute
		private boolean disableAnsi;
		@PluginBuilderAttribute
		private boolean noConsoleNoAnsi;
		@PluginBuilderAttribute
		private String header;
		@PluginBuilderAttribute
		private String footer;

		private Builder() {
		}

		public PatternLayout.Builder withPattern(String pattern) {
			this.pattern = pattern;
			return this;
		}

		public PatternLayout.Builder withPatternSelector(PatternSelector patternSelector) {
			this.patternSelector = patternSelector;
			return this;
		}

		public PatternLayout.Builder withConfiguration(Configuration configuration) {
			this.configuration = configuration;
			return this;
		}

		public PatternLayout.Builder withRegexReplacement(RegexReplacement regexReplacement) {
			this.regexReplacement = regexReplacement;
			return this;
		}

		public PatternLayout.Builder withCharset(Charset charset) {
			if (charset != null) {
				this.charset = charset;
			}

			return this;
		}

		public PatternLayout.Builder withAlwaysWriteExceptions(boolean alwaysWriteExceptions) {
			this.alwaysWriteExceptions = alwaysWriteExceptions;
			return this;
		}

		public PatternLayout.Builder withDisableAnsi(boolean disableAnsi) {
			this.disableAnsi = disableAnsi;
			return this;
		}

		public PatternLayout.Builder withNoConsoleNoAnsi(boolean noConsoleNoAnsi) {
			this.noConsoleNoAnsi = noConsoleNoAnsi;
			return this;
		}

		public PatternLayout.Builder withHeader(String header) {
			this.header = header;
			return this;
		}

		public PatternLayout.Builder withFooter(String footer) {
			this.footer = footer;
			return this;
		}

		public PatternLayout build() {
			if (this.configuration == null) {
				this.configuration = new DefaultConfiguration();
			}

			return new PatternLayout(
				this.configuration,
				this.regexReplacement,
				this.pattern,
				this.patternSelector,
				this.charset,
				this.alwaysWriteExceptions,
				this.disableAnsi,
				this.noConsoleNoAnsi,
				this.header,
				this.footer
			);
		}
	}

	private static class PatternSelectorSerializer implements Serializer, Serializer2 {
		private final PatternSelector patternSelector;
		private final RegexReplacement replace;

		private PatternSelectorSerializer(PatternSelector patternSelector, RegexReplacement replace) {
			this.patternSelector = patternSelector;
			this.replace = replace;
		}

		@Override
		public String toSerializable(LogEvent event) {
			StringBuilder sb = AbstractStringLayout.getStringBuilder();

			String var3;
			try {
				var3 = this.toSerializable(event, sb).toString();
			} finally {
				AbstractStringLayout.trimToMaxSize(sb);
			}

			return var3;
		}

		@Override
		public StringBuilder toSerializable(LogEvent event, StringBuilder buffer) {
			PatternFormatter[] formatters = this.patternSelector.getFormatters(event);
			int len = formatters.length;

			for (int i = 0; i < len; i++) {
				formatters[i].format(event, buffer);
			}

			if (this.replace != null) {
				String str = buffer.toString();
				str = this.replace.format(str);
				buffer.setLength(0);
				buffer.append(str);
			}

			return buffer;
		}

		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(super.toString());
			builder.append("[patternSelector=");
			builder.append(this.patternSelector);
			builder.append(", replace=");
			builder.append(this.replace);
			builder.append("]");
			return builder.toString();
		}
	}

	private static class PatternSerializer implements Serializer, Serializer2 {
		private final PatternFormatter[] formatters;
		private final RegexReplacement replace;

		private PatternSerializer(PatternFormatter[] formatters, RegexReplacement replace) {
			this.formatters = formatters;
			this.replace = replace;
		}

		@Override
		public String toSerializable(LogEvent event) {
			StringBuilder sb = AbstractStringLayout.getStringBuilder();

			String var3;
			try {
				var3 = this.toSerializable(event, sb).toString();
			} finally {
				AbstractStringLayout.trimToMaxSize(sb);
			}

			return var3;
		}

		@Override
		public StringBuilder toSerializable(LogEvent event, StringBuilder buffer) {
			int len = this.formatters.length;

			for (int i = 0; i < len; i++) {
				this.formatters[i].format(event, buffer);
			}

			if (this.replace != null) {
				String str = buffer.toString();
				str = this.replace.format(str);
				buffer.setLength(0);
				buffer.append(str);
			}

			return buffer;
		}

		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(super.toString());
			builder.append("[formatters=");
			builder.append(Arrays.toString(this.formatters));
			builder.append(", replace=");
			builder.append(this.replace);
			builder.append("]");
			return builder.toString();
		}
	}

	public static class SerializerBuilder implements org.apache.logging.log4j.core.util.Builder<Serializer> {
		private Configuration configuration;
		private RegexReplacement replace;
		private String pattern;
		private String defaultPattern;
		private PatternSelector patternSelector;
		private boolean alwaysWriteExceptions;
		private boolean disableAnsi;
		private boolean noConsoleNoAnsi;

		public Serializer build() {
			if (Strings.isEmpty(this.pattern) && Strings.isEmpty(this.defaultPattern)) {
				return null;
			} else if (this.patternSelector == null) {
				try {
					PatternParser parser = PatternLayout.createPatternParser(this.configuration);
					List<PatternFormatter> list = parser.parse(
						this.pattern == null ? this.defaultPattern : this.pattern, this.alwaysWriteExceptions, this.disableAnsi, this.noConsoleNoAnsi
					);
					PatternFormatter[] formatters = (PatternFormatter[])list.toArray(new PatternFormatter[0]);
					return new PatternLayout.PatternSerializer(formatters, this.replace);
				} catch (RuntimeException var4) {
					throw new IllegalArgumentException("Cannot parse pattern '" + this.pattern + "'", var4);
				}
			} else {
				return new PatternLayout.PatternSelectorSerializer(this.patternSelector, this.replace);
			}
		}

		public PatternLayout.SerializerBuilder setConfiguration(Configuration configuration) {
			this.configuration = configuration;
			return this;
		}

		public PatternLayout.SerializerBuilder setReplace(RegexReplacement replace) {
			this.replace = replace;
			return this;
		}

		public PatternLayout.SerializerBuilder setPattern(String pattern) {
			this.pattern = pattern;
			return this;
		}

		public PatternLayout.SerializerBuilder setDefaultPattern(String defaultPattern) {
			this.defaultPattern = defaultPattern;
			return this;
		}

		public PatternLayout.SerializerBuilder setPatternSelector(PatternSelector patternSelector) {
			this.patternSelector = patternSelector;
			return this;
		}

		public PatternLayout.SerializerBuilder setAlwaysWriteExceptions(boolean alwaysWriteExceptions) {
			this.alwaysWriteExceptions = alwaysWriteExceptions;
			return this;
		}

		public PatternLayout.SerializerBuilder setDisableAnsi(boolean disableAnsi) {
			this.disableAnsi = disableAnsi;
			return this;
		}

		public PatternLayout.SerializerBuilder setNoConsoleNoAnsi(boolean noConsoleNoAnsi) {
			this.noConsoleNoAnsi = noConsoleNoAnsi;
			return this;
		}
	}
}
