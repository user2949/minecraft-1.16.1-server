package org.apache.logging.log4j.core.appender.db.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.util.Strings;

@Converter(
	autoApply = false
)
public class LevelAttributeConverter implements AttributeConverter<Level, String> {
	public String convertToDatabaseColumn(Level level) {
		return level == null ? null : level.name();
	}

	public Level convertToEntityAttribute(String s) {
		return Strings.isEmpty(s) ? null : Level.toLevel(s, null);
	}
}
