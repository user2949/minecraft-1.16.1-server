package org.apache.logging.log4j.core.appender.db.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.apache.logging.log4j.ThreadContext.ContextStack;

@Converter(
	autoApply = false
)
public class ContextStackAttributeConverter implements AttributeConverter<ContextStack, String> {
	public String convertToDatabaseColumn(ContextStack contextStack) {
		if (contextStack == null) {
			return null;
		} else {
			StringBuilder builder = new StringBuilder();

			for (String value : contextStack.asList()) {
				if (builder.length() > 0) {
					builder.append('\n');
				}

				builder.append(value);
			}

			return builder.toString();
		}
	}

	public ContextStack convertToEntityAttribute(String s) {
		throw new UnsupportedOperationException("Log events can only be persisted, not extracted.");
	}
}
