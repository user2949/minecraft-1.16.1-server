package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(
	name = "Null",
	category = "Core",
	elementType = "appender",
	printObject = true
)
public class NullAppender extends AbstractAppender {
	public static final String PLUGIN_NAME = "Null";

	@PluginFactory
	public static NullAppender createAppender(@PluginAttribute("name") String name) {
		return new NullAppender(name);
	}

	private NullAppender(String name) {
		super(name, null, null);
	}

	@Override
	public void append(LogEvent event) {
	}
}
