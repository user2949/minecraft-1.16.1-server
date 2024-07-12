package org.apache.logging.log4j.core.script;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.PluginValue;

@Plugin(
	name = "Script",
	category = "Core",
	printObject = true
)
public class Script extends AbstractScript {
	public Script(String name, String language, String scriptText) {
		super(name, language, scriptText);
	}

	@PluginFactory
	public static Script createScript(
		@PluginAttribute("name") String name, @PluginAttribute("language") String language, @PluginValue("scriptText") String scriptText
	) {
		if (language == null) {
			LOGGER.info("No script language supplied, defaulting to {}", "JavaScript");
			language = "JavaScript";
		}

		if (scriptText == null) {
			LOGGER.error("No scriptText attribute provided for ScriptFile {}", name);
			return null;
		} else {
			return new Script(name, language, scriptText);
		}
	}

	public String toString() {
		return this.getName() != null ? this.getName() : super.toString();
	}
}
