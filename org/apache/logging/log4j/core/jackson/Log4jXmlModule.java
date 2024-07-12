package org.apache.logging.log4j.core.jackson;

import com.fasterxml.jackson.databind.Module.SetupContext;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import org.apache.logging.log4j.core.jackson.Initializers.SetupContextInitializer;
import org.apache.logging.log4j.core.jackson.Initializers.SimpleModuleInitializer;

final class Log4jXmlModule extends JacksonXmlModule {
	private static final long serialVersionUID = 1L;
	private final boolean includeStacktrace;

	Log4jXmlModule(boolean includeStacktrace) {
		this.includeStacktrace = includeStacktrace;
		new SimpleModuleInitializer().initialize(this);
	}

	public void setupModule(SetupContext context) {
		super.setupModule(context);
		new SetupContextInitializer().setupModule(context, this.includeStacktrace);
	}
}
