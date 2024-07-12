package org.apache.logging.log4j.core.appender.db.jpa.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.persistence.PersistenceException;
import org.apache.logging.log4j.ThreadContext.ContextStack;
import org.apache.logging.log4j.spi.DefaultThreadContextStack;
import org.apache.logging.log4j.util.Strings;

@Converter(
	autoApply = false
)
public class ContextStackJsonAttributeConverter implements AttributeConverter<ContextStack, String> {
	public String convertToDatabaseColumn(ContextStack contextStack) {
		if (contextStack == null) {
			return null;
		} else {
			try {
				return ContextMapJsonAttributeConverter.OBJECT_MAPPER.writeValueAsString(contextStack.asList());
			} catch (IOException var3) {
				throw new PersistenceException("Failed to convert stack list to JSON string.", var3);
			}
		}
	}

	public ContextStack convertToEntityAttribute(String s) {
		if (Strings.isEmpty(s)) {
			return null;
		} else {
			List<String> list;
			try {
				list = (List<String>)ContextMapJsonAttributeConverter.OBJECT_MAPPER.readValue(s, new TypeReference<List<String>>() {
				});
			} catch (IOException var4) {
				throw new PersistenceException("Failed to convert JSON string to list for stack.", var4);
			}

			DefaultThreadContextStack result = new DefaultThreadContextStack(true);
			result.addAll(list);
			return result;
		}
	}
}
