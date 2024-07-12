package org.apache.logging.log4j.core.layout;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.impl.MutableLogEvent;
import org.apache.logging.log4j.core.layout.AbstractStringLayout.Serializer;
import org.apache.logging.log4j.core.util.StringBuilderWriter;

abstract class AbstractJacksonLayout extends AbstractStringLayout {
	protected static final String DEFAULT_EOL = "\r\n";
	protected static final String COMPACT_EOL = "";
	protected final String eol;
	protected final ObjectWriter objectWriter;
	protected final boolean compact;
	protected final boolean complete;

	protected AbstractJacksonLayout(
		Configuration config,
		ObjectWriter objectWriter,
		Charset charset,
		boolean compact,
		boolean complete,
		boolean eventEol,
		Serializer headerSerializer,
		Serializer footerSerializer
	) {
		super(config, charset, headerSerializer, footerSerializer);
		this.objectWriter = objectWriter;
		this.compact = compact;
		this.complete = complete;
		this.eol = compact && !eventEol ? "" : "\r\n";
	}

	public String toSerializable(LogEvent event) {
		StringBuilderWriter writer = new StringBuilderWriter();

		try {
			this.toSerializable(event, writer);
			return writer.toString();
		} catch (IOException var4) {
			LOGGER.error(var4);
			return "";
		}
	}

	private static LogEvent convertMutableToLog4jEvent(LogEvent event) {
		return (LogEvent)(event instanceof MutableLogEvent ? ((MutableLogEvent)event).createMemento() : event);
	}

	public void toSerializable(LogEvent event, Writer writer) throws JsonGenerationException, JsonMappingException, IOException {
		this.objectWriter.writeValue(writer, convertMutableToLog4jEvent(event));
		writer.write(this.eol);
		this.markEvent();
	}

	public abstract static class Builder<B extends AbstractJacksonLayout.Builder<B>> extends AbstractStringLayout.Builder<B> {
		@PluginBuilderAttribute
		private boolean eventEol;
		@PluginBuilderAttribute
		private boolean compact;
		@PluginBuilderAttribute
		private boolean complete;

		public boolean getEventEol() {
			return this.eventEol;
		}

		public boolean isCompact() {
			return this.compact;
		}

		public boolean isComplete() {
			return this.complete;
		}

		public B setEventEol(boolean eventEol) {
			this.eventEol = eventEol;
			return this.asBuilder();
		}

		public B setCompact(boolean compact) {
			this.compact = compact;
			return this.asBuilder();
		}

		public B setComplete(boolean complete) {
			this.complete = complete;
			return this.asBuilder();
		}
	}
}
