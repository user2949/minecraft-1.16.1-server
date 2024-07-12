package org.apache.logging.log4j.core.appender.db.jpa.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.persistence.PersistenceException;
import org.apache.logging.log4j.core.impl.ContextDataFactory;
import org.apache.logging.log4j.util.BiConsumer;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.StringMap;
import org.apache.logging.log4j.util.Strings;

@Converter(
	autoApply = false
)
public class ContextDataJsonAttributeConverter implements AttributeConverter<ReadOnlyStringMap, String> {
	static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public String convertToDatabaseColumn(ReadOnlyStringMap contextData) {
		if (contextData == null) {
			return null;
		} else {
			try {
				JsonNodeFactory factory = OBJECT_MAPPER.getNodeFactory();
				final ObjectNode root = factory.objectNode();
				contextData.forEach(new BiConsumer<String, Object>() {
					public void accept(String key, Object value) {
						root.put(key, String.valueOf(value));
					}
				});
				return OBJECT_MAPPER.writeValueAsString(root);
			} catch (Exception var4) {
				throw new PersistenceException("Failed to convert contextData to JSON string.", var4);
			}
		}
	}

	public ReadOnlyStringMap convertToEntityAttribute(String s) {
		if (Strings.isEmpty(s)) {
			return null;
		} else {
			try {
				StringMap result = ContextDataFactory.createContextData();
				ObjectNode root = (ObjectNode)OBJECT_MAPPER.readTree(s);
				Iterator<Entry<String, JsonNode>> entries = root.fields();

				while (entries.hasNext()) {
					Entry<String, JsonNode> entry = (Entry<String, JsonNode>)entries.next();
					Object value = ((JsonNode)entry.getValue()).textValue();
					result.putValue((String)entry.getKey(), value);
				}

				return result;
			} catch (IOException var7) {
				throw new PersistenceException("Failed to convert JSON string to map.", var7);
			}
		}
	}
}
