package org.apache.logging.log4j.core.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module.SetupContext;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.logging.log4j.core.jackson.Initializers.SetupContextInitializer;
import org.apache.logging.log4j.core.jackson.Initializers.SetupContextJsonInitializer;
import org.apache.logging.log4j.core.jackson.Initializers.SimpleModuleInitializer;

final class Log4jYamlModule extends SimpleModule {
	private static final long serialVersionUID = 1L;
	private final boolean encodeThreadContextAsList;
	private final boolean includeStacktrace;

	Log4jYamlModule(boolean encodeThreadContextAsList, boolean includeStacktrace) {
		super(Log4jYamlModule.class.getName(), new Version(2, 0, 0, null, null, null));
		this.encodeThreadContextAsList = encodeThreadContextAsList;
		this.includeStacktrace = includeStacktrace;
		new SimpleModuleInitializer().initialize(this);
	}

	public void setupModule(SetupContext context) {
		super.setupModule(context);
		if (this.encodeThreadContextAsList) {
			new SetupContextInitializer().setupModule(context, this.includeStacktrace);
		} else {
			new SetupContextJsonInitializer().setupModule(context, this.includeStacktrace);
		}
	}
}
