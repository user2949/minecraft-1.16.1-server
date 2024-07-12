package org.apache.logging.log4j.core.layout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.pattern.PatternFormatter;
import org.apache.logging.log4j.core.pattern.PatternParser;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(
	name = "MarkerPatternSelector",
	category = "Core",
	elementType = "patternSelector",
	printObject = true
)
public class MarkerPatternSelector implements PatternSelector {
	private final Map<String, PatternFormatter[]> formatterMap = new HashMap();
	private final Map<String, String> patternMap = new HashMap();
	private final PatternFormatter[] defaultFormatters;
	private final String defaultPattern;
	private static Logger LOGGER = StatusLogger.getLogger();

	@Deprecated
	public MarkerPatternSelector(PatternMatch[] properties, String defaultPattern, boolean alwaysWriteExceptions, boolean noConsoleNoAnsi, Configuration config) {
		this(properties, defaultPattern, alwaysWriteExceptions, false, noConsoleNoAnsi, config);
	}

	private MarkerPatternSelector(
		PatternMatch[] properties, String defaultPattern, boolean alwaysWriteExceptions, boolean disableAnsi, boolean noConsoleNoAnsi, Configuration config
	) {
		PatternParser parser = PatternLayout.createPatternParser(config);

		for (PatternMatch property : properties) {
			try {
				List<PatternFormatter> list = parser.parse(property.getPattern(), alwaysWriteExceptions, disableAnsi, noConsoleNoAnsi);
				this.formatterMap.put(property.getKey(), list.toArray(new PatternFormatter[list.size()]));
				this.patternMap.put(property.getKey(), property.getPattern());
			} catch (RuntimeException var14) {
				throw new IllegalArgumentException("Cannot parse pattern '" + property.getPattern() + "'", var14);
			}
		}

		try {
			List<PatternFormatter> list = parser.parse(defaultPattern, alwaysWriteExceptions, disableAnsi, noConsoleNoAnsi);
			this.defaultFormatters = (PatternFormatter[])list.toArray(new PatternFormatter[list.size()]);
			this.defaultPattern = defaultPattern;
		} catch (RuntimeException var13) {
			throw new IllegalArgumentException("Cannot parse pattern '" + defaultPattern + "'", var13);
		}
	}

	@Override
	public PatternFormatter[] getFormatters(LogEvent event) {
		Marker marker = event.getMarker();
		if (marker == null) {
			return this.defaultFormatters;
		} else {
			for (String key : this.formatterMap.keySet()) {
				if (marker.isInstanceOf(key)) {
					return (PatternFormatter[])this.formatterMap.get(key);
				}
			}

			return this.defaultFormatters;
		}
	}

	@PluginBuilderFactory
	public static MarkerPatternSelector.Builder newBuilder() {
		return new MarkerPatternSelector.Builder();
	}

	@Deprecated
	public static MarkerPatternSelector createSelector(
		PatternMatch[] properties, String defaultPattern, boolean alwaysWriteExceptions, boolean noConsoleNoAnsi, Configuration configuration
	) {
		MarkerPatternSelector.Builder builder = newBuilder();
		builder.setProperties(properties);
		builder.setDefaultPattern(defaultPattern);
		builder.setAlwaysWriteExceptions(alwaysWriteExceptions);
		builder.setNoConsoleNoAnsi(noConsoleNoAnsi);
		builder.setConfiguration(configuration);
		return builder.build();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;

		for (Entry<String, String> entry : this.patternMap.entrySet()) {
			if (!first) {
				sb.append(", ");
			}

			sb.append("key=\"").append((String)entry.getKey()).append("\", pattern=\"").append((String)entry.getValue()).append("\"");
			first = false;
		}

		if (!first) {
			sb.append(", ");
		}

		sb.append("default=\"").append(this.defaultPattern).append("\"");
		return sb.toString();
	}

	public static class Builder implements org.apache.logging.log4j.core.util.Builder<MarkerPatternSelector> {
		@PluginElement("PatternMatch")
		private PatternMatch[] properties;
		@PluginBuilderAttribute("defaultPattern")
		private String defaultPattern;
		@PluginBuilderAttribute("alwaysWriteExceptions")
		private boolean alwaysWriteExceptions = true;
		@PluginBuilderAttribute("disableAnsi")
		private boolean disableAnsi;
		@PluginBuilderAttribute("noConsoleNoAnsi")
		private boolean noConsoleNoAnsi;
		@PluginConfiguration
		private Configuration configuration;

		public MarkerPatternSelector build() {
			if (this.defaultPattern == null) {
				this.defaultPattern = "%m%n";
			}

			if (this.properties != null && this.properties.length != 0) {
				return new MarkerPatternSelector(
					this.properties, this.defaultPattern, this.alwaysWriteExceptions, this.disableAnsi, this.noConsoleNoAnsi, this.configuration
				);
			} else {
				MarkerPatternSelector.LOGGER.warn("No marker patterns were provided with PatternMatch");
				return null;
			}
		}

		public MarkerPatternSelector.Builder setProperties(PatternMatch[] properties) {
			this.properties = properties;
			return this;
		}

		public MarkerPatternSelector.Builder setDefaultPattern(String defaultPattern) {
			this.defaultPattern = defaultPattern;
			return this;
		}

		public MarkerPatternSelector.Builder setAlwaysWriteExceptions(boolean alwaysWriteExceptions) {
			this.alwaysWriteExceptions = alwaysWriteExceptions;
			return this;
		}

		public MarkerPatternSelector.Builder setDisableAnsi(boolean disableAnsi) {
			this.disableAnsi = disableAnsi;
			return this;
		}

		public MarkerPatternSelector.Builder setNoConsoleNoAnsi(boolean noConsoleNoAnsi) {
			this.noConsoleNoAnsi = noConsoleNoAnsi;
			return this;
		}

		public MarkerPatternSelector.Builder setConfiguration(Configuration configuration) {
			this.configuration = configuration;
			return this;
		}
	}
}
