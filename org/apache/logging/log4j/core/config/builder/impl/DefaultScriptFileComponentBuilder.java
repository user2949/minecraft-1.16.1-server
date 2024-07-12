package org.apache.logging.log4j.core.config.builder.impl;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.builder.api.ScriptFileComponentBuilder;

class DefaultScriptFileComponentBuilder extends DefaultComponentAndConfigurationBuilder<ScriptFileComponentBuilder> implements ScriptFileComponentBuilder {
	public DefaultScriptFileComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> builder, String name, String path) {
		super(builder, name != null ? name : path, "ScriptFile");
		this.addAttribute("path", path);
	}

	public DefaultScriptFileComponentBuilder addLanguage(String language) {
		this.addAttribute("language", language);
		return this;
	}

	public DefaultScriptFileComponentBuilder addIsWatched(boolean isWatched) {
		this.addAttribute("isWatched", Boolean.toString(isWatched));
		return this;
	}

	public DefaultScriptFileComponentBuilder addIsWatched(String isWatched) {
		this.addAttribute("isWatched", isWatched);
		return this;
	}

	public DefaultScriptFileComponentBuilder addCharset(String charset) {
		this.addAttribute("charset", charset);
		return this;
	}
}
