package org.apache.logging.log4j.core.config.builder.api;

public interface ScriptFileComponentBuilder extends ComponentBuilder<ScriptFileComponentBuilder> {
	ScriptFileComponentBuilder addLanguage(String string);

	ScriptFileComponentBuilder addIsWatched(boolean boolean1);

	ScriptFileComponentBuilder addIsWatched(String string);

	ScriptFileComponentBuilder addCharset(String string);
}
