package org.apache.logging.log4j.core.appender.db.jdbc;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.util.Booleans;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.Strings;

@Plugin(
	name = "Column",
	category = "Core",
	printObject = true
)
public final class ColumnConfig {
	private static final Logger LOGGER = StatusLogger.getLogger();
	private final String columnName;
	private final PatternLayout layout;
	private final String literalValue;
	private final boolean eventTimestamp;
	private final boolean unicode;
	private final boolean clob;

	private ColumnConfig(String columnName, PatternLayout layout, String literalValue, boolean eventDate, boolean unicode, boolean clob) {
		this.columnName = columnName;
		this.layout = layout;
		this.literalValue = literalValue;
		this.eventTimestamp = eventDate;
		this.unicode = unicode;
		this.clob = clob;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public PatternLayout getLayout() {
		return this.layout;
	}

	public String getLiteralValue() {
		return this.literalValue;
	}

	public boolean isEventTimestamp() {
		return this.eventTimestamp;
	}

	public boolean isUnicode() {
		return this.unicode;
	}

	public boolean isClob() {
		return this.clob;
	}

	public String toString() {
		return "{ name=" + this.columnName + ", layout=" + this.layout + ", literal=" + this.literalValue + ", timestamp=" + this.eventTimestamp + " }";
	}

	@Deprecated
	public static ColumnConfig createColumnConfig(
		Configuration config, String name, String pattern, String literalValue, String eventTimestamp, String unicode, String clob
	) {
		if (Strings.isEmpty(name)) {
			LOGGER.error("The column config is not valid because it does not contain a column name.");
			return null;
		} else {
			boolean isEventTimestamp = Boolean.parseBoolean(eventTimestamp);
			boolean isUnicode = Booleans.parseBoolean(unicode, true);
			boolean isClob = Boolean.parseBoolean(clob);
			return newBuilder()
				.setConfiguration(config)
				.setName(name)
				.setPattern(pattern)
				.setLiteral(literalValue)
				.setEventTimestamp(isEventTimestamp)
				.setUnicode(isUnicode)
				.setClob(isClob)
				.build();
		}
	}

	@PluginBuilderFactory
	public static ColumnConfig.Builder newBuilder() {
		return new ColumnConfig.Builder();
	}

	public static class Builder implements org.apache.logging.log4j.core.util.Builder<ColumnConfig> {
		@PluginConfiguration
		private Configuration configuration;
		@PluginBuilderAttribute
		@Required(
			message = "No name provided"
		)
		private String name;
		@PluginBuilderAttribute
		private String pattern;
		@PluginBuilderAttribute
		private String literal;
		@PluginBuilderAttribute
		private boolean isEventTimestamp;
		@PluginBuilderAttribute
		private boolean isUnicode = true;
		@PluginBuilderAttribute
		private boolean isClob;

		public ColumnConfig.Builder setConfiguration(Configuration configuration) {
			this.configuration = configuration;
			return this;
		}

		public ColumnConfig.Builder setName(String name) {
			this.name = name;
			return this;
		}

		public ColumnConfig.Builder setPattern(String pattern) {
			this.pattern = pattern;
			return this;
		}

		public ColumnConfig.Builder setLiteral(String literal) {
			this.literal = literal;
			return this;
		}

		public ColumnConfig.Builder setEventTimestamp(boolean eventTimestamp) {
			this.isEventTimestamp = eventTimestamp;
			return this;
		}

		public ColumnConfig.Builder setUnicode(boolean unicode) {
			this.isUnicode = unicode;
			return this;
		}

		public ColumnConfig.Builder setClob(boolean clob) {
			this.isClob = clob;
			return this;
		}

		public ColumnConfig build() {
			if (Strings.isEmpty(this.name)) {
				ColumnConfig.LOGGER.error("The column config is not valid because it does not contain a column name.");
				return null;
			} else {
				boolean isPattern = Strings.isNotEmpty(this.pattern);
				boolean isLiteralValue = Strings.isNotEmpty(this.literal);
				if ((!isPattern || !isLiteralValue) && (!isPattern || !this.isEventTimestamp) && (!isLiteralValue || !this.isEventTimestamp)) {
					if (this.isEventTimestamp) {
						return new ColumnConfig(this.name, null, null, true, false, false);
					} else if (isLiteralValue) {
						return new ColumnConfig(this.name, null, this.literal, false, false, false);
					} else if (isPattern) {
						PatternLayout layout = PatternLayout.newBuilder()
							.withPattern(this.pattern)
							.withConfiguration(this.configuration)
							.withAlwaysWriteExceptions(false)
							.build();
						return new ColumnConfig(this.name, layout, null, false, this.isUnicode, this.isClob);
					} else {
						ColumnConfig.LOGGER.error("To configure a column you must specify a pattern or literal or set isEventDate to true.");
						return null;
					}
				} else {
					ColumnConfig.LOGGER.error("The pattern, literal, and isEventTimestamp attributes are mutually exclusive.");
					return null;
				}
			}
		}
	}
}
