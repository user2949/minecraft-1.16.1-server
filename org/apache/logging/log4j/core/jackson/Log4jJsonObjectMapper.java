package org.apache.logging.log4j.core.jackson;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Log4jJsonObjectMapper extends ObjectMapper {
	private static final long serialVersionUID = 1L;

	public Log4jJsonObjectMapper() {
		this(false, true);
	}

	public Log4jJsonObjectMapper(boolean encodeThreadContextAsList, boolean includeStacktrace) {
		this.registerModule(new Log4jJsonModule(encodeThreadContextAsList, includeStacktrace));
		this.setSerializationInclusion(Include.NON_EMPTY);
	}
}
