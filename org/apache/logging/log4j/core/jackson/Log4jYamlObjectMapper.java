package org.apache.logging.log4j.core.jackson;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public class Log4jYamlObjectMapper extends YAMLMapper {
	private static final long serialVersionUID = 1L;

	public Log4jYamlObjectMapper() {
		this(false, true);
	}

	public Log4jYamlObjectMapper(boolean encodeThreadContextAsList, boolean includeStacktrace) {
		this.registerModule(new Log4jYamlModule(encodeThreadContextAsList, includeStacktrace));
		this.setSerializationInclusion(Include.NON_EMPTY);
	}
}
