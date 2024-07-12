package org.apache.logging.log4j.core.layout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.script.SimpleBindings;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.pattern.PatternFormatter;
import org.apache.logging.log4j.core.pattern.PatternParser;
import org.apache.logging.log4j.core.script.AbstractScript;
import org.apache.logging.log4j.core.script.ScriptRef;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(
	name = "ScriptPatternSelector",
	category = "Core",
	elementType = "patternSelector",
	printObject = true
)
public class ScriptPatternSelector implements PatternSelector {
	private final Map<String, PatternFormatter[]> formatterMap = new HashMap();
	private final Map<String, String> patternMap = new HashMap();
	private final PatternFormatter[] defaultFormatters;
	private final String defaultPattern;
	private static Logger LOGGER = StatusLogger.getLogger();
	private final AbstractScript script;
	private final Configuration configuration;

	@Deprecated
	public ScriptPatternSelector(
		AbstractScript script,
		PatternMatch[] properties,
		String defaultPattern,
		boolean alwaysWriteExceptions,
		boolean disableAnsi,
		boolean noConsoleNoAnsi,
		Configuration config
	) {
		this.script = script;
		this.configuration = config;
		if (!(script instanceof ScriptRef)) {
			config.getScriptManager().addScript(script);
		}

		PatternParser parser = PatternLayout.createPatternParser(config);

		for (PatternMatch property : properties) {
			try {
				List<PatternFormatter> list = parser.parse(property.getPattern(), alwaysWriteExceptions, disableAnsi, noConsoleNoAnsi);
				this.formatterMap.put(property.getKey(), list.toArray(new PatternFormatter[list.size()]));
				this.patternMap.put(property.getKey(), property.getPattern());
			} catch (RuntimeException var15) {
				throw new IllegalArgumentException("Cannot parse pattern '" + property.getPattern() + "'", var15);
			}
		}

		try {
			List<PatternFormatter> list = parser.parse(defaultPattern, alwaysWriteExceptions, disableAnsi, noConsoleNoAnsi);
			this.defaultFormatters = (PatternFormatter[])list.toArray(new PatternFormatter[list.size()]);
			this.defaultPattern = defaultPattern;
		} catch (RuntimeException var14) {
			throw new IllegalArgumentException("Cannot parse pattern '" + defaultPattern + "'", var14);
		}
	}

	@Override
	public PatternFormatter[] getFormatters(LogEvent event) {
		SimpleBindings bindings = new SimpleBindings();
		bindings.putAll(this.configuration.getProperties());
		bindings.put("substitutor", this.configuration.getStrSubstitutor());
		bindings.put("logEvent", event);
		Object object = this.configuration.getScriptManager().execute(this.script.getName(), bindings);
		if (object == null) {
			return this.defaultFormatters;
		} else {
			PatternFormatter[] patternFormatter = (PatternFormatter[])this.formatterMap.get(object.toString());
			return patternFormatter == null ? this.defaultFormatters : patternFormatter;
		}
	}

	@PluginBuilderFactory
	public static ScriptPatternSelector.Builder newBuilder() {
		return new ScriptPatternSelector.Builder();
	}

	@Deprecated
	public static ScriptPatternSelector createSelector(
		AbstractScript script, PatternMatch[] properties, String defaultPattern, boolean alwaysWriteExceptions, boolean noConsoleNoAnsi, Configuration configuration
	) {
		ScriptPatternSelector.Builder builder = newBuilder();
		builder.setScript(script);
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

	public static class Builder implements org.apache.logging.log4j.core.util.Builder<ScriptPatternSelector> {
		@PluginElement("Script")
		private AbstractScript script;
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

		private Builder() {
		}

		public ScriptPatternSelector build() {
			if (this.script == null) {
				ScriptPatternSelector.LOGGER.error("A Script, ScriptFile or ScriptRef element must be provided for this ScriptFilter");
				return null;
			} else if (this.script instanceof ScriptRef && this.configuration.getScriptManager().getScript(this.script.getName()) == null) {
				ScriptPatternSelector.LOGGER.error("No script with name {} has been declared.", this.script.getName());
				return null;
			} else {
				if (this.defaultPattern == null) {
					this.defaultPattern = "%m%n";
				}

				if (this.properties != null && this.properties.length != 0) {
					return new ScriptPatternSelector(
						this.script, this.properties, this.defaultPattern, this.alwaysWriteExceptions, this.disableAnsi, this.noConsoleNoAnsi, this.configuration
					);
				} else {
					ScriptPatternSelector.LOGGER.warn("No marker patterns were provided");
					return null;
				}
			}
		}

		public ScriptPatternSelector.Builder setScript(AbstractScript script) {
			this.script = script;
			return this;
		}

		public ScriptPatternSelector.Builder setProperties(PatternMatch[] properties) {
			this.properties = properties;
			return this;
		}

		public ScriptPatternSelector.Builder setDefaultPattern(String defaultPattern) {
			this.defaultPattern = defaultPattern;
			return this;
		}

		public ScriptPatternSelector.Builder setAlwaysWriteExceptions(boolean alwaysWriteExceptions) {
			this.alwaysWriteExceptions = alwaysWriteExceptions;
			return this;
		}

		public ScriptPatternSelector.Builder setDisableAnsi(boolean disableAnsi) {
			this.disableAnsi = disableAnsi;
			return this;
		}

		public ScriptPatternSelector.Builder setNoConsoleNoAnsi(boolean noConsoleNoAnsi) {
			this.noConsoleNoAnsi = noConsoleNoAnsi;
			return this;
		}

		public ScriptPatternSelector.Builder setConfiguration(Configuration config) {
			this.configuration = config;
			return this;
		}
	}
}
