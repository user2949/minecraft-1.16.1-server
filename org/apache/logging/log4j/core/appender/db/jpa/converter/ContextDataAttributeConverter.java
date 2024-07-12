package org.apache.logging.log4j.core.appender.db.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.apache.logging.log4j.util.ReadOnlyStringMap;

@Converter(
	autoApply = false
)
public class ContextDataAttributeConverter implements AttributeConverter<ReadOnlyStringMap, String> {
	public String convertToDatabaseColumn(ReadOnlyStringMap contextData) {
		return contextData == null ? null : contextData.toString();
	}

	public ReadOnlyStringMap convertToEntityAttribute(String s) {
		throw new UnsupportedOperationException("Log events can only be persisted, not extracted.");
	}
}
