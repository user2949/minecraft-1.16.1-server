package org.apache.logging.log4j.core.appender;

import java.io.Serializable;
import java.util.Objects;
import javax.script.Bindings;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.script.AbstractScript;
import org.apache.logging.log4j.core.script.ScriptManager;

@Plugin(
	name = "ScriptAppenderSelector",
	category = "Core",
	elementType = "appender",
	printObject = true
)
public class ScriptAppenderSelector extends AbstractAppender {
	@PluginBuilderFactory
	public static ScriptAppenderSelector.Builder newBuilder() {
		return new ScriptAppenderSelector.Builder();
	}

	private ScriptAppenderSelector(String name, Filter filter, Layout<? extends Serializable> layout) {
		super(name, filter, layout);
	}

	@Override
	public void append(LogEvent event) {
	}

	public static final class Builder implements org.apache.logging.log4j.core.util.Builder<Appender> {
		@PluginElement("AppenderSet")
		@Required
		private AppenderSet appenderSet;
		@PluginConfiguration
		@Required
		private Configuration configuration;
		@PluginBuilderAttribute
		@Required
		private String name;
		@PluginElement("Script")
		@Required
		private AbstractScript script;

		public Appender build() {
			if (this.name == null) {
				ScriptAppenderSelector.LOGGER.error("Name missing.");
				return null;
			} else if (this.script == null) {
				ScriptAppenderSelector.LOGGER.error("Script missing for ScriptAppenderSelector appender {}", this.name);
				return null;
			} else if (this.appenderSet == null) {
				ScriptAppenderSelector.LOGGER.error("AppenderSet missing for ScriptAppenderSelector appender {}", this.name);
				return null;
			} else if (this.configuration == null) {
				ScriptAppenderSelector.LOGGER.error("Configuration missing for ScriptAppenderSelector appender {}", this.name);
				return null;
			} else {
				ScriptManager scriptManager = this.configuration.getScriptManager();
				scriptManager.addScript(this.script);
				Bindings bindings = scriptManager.createBindings(this.script);
				Object object = scriptManager.execute(this.script.getName(), bindings);
				String appenderName = Objects.toString(object, null);
				return this.appenderSet.createAppender(appenderName, this.name);
			}
		}

		public AppenderSet getAppenderSet() {
			return this.appenderSet;
		}

		public Configuration getConfiguration() {
			return this.configuration;
		}

		public String getName() {
			return this.name;
		}

		public AbstractScript getScript() {
			return this.script;
		}

		public ScriptAppenderSelector.Builder withAppenderNodeSet(AppenderSet appenderSet) {
			this.appenderSet = appenderSet;
			return this;
		}

		public ScriptAppenderSelector.Builder withConfiguration(Configuration configuration) {
			this.configuration = configuration;
			return this;
		}

		public ScriptAppenderSelector.Builder withName(String name) {
			this.name = name;
			return this;
		}

		public ScriptAppenderSelector.Builder withScript(AbstractScript script) {
			this.script = script;
			return this;
		}
	}
}
