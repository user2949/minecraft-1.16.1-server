package org.apache.logging.log4j.core.appender.db.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.util.Strings;

@Converter(
	autoApply = false
)
public class MarkerAttributeConverter implements AttributeConverter<Marker, String> {
	public String convertToDatabaseColumn(Marker marker) {
		return marker == null ? null : marker.toString();
	}

	public Marker convertToEntityAttribute(String s) {
		if (Strings.isEmpty(s)) {
			return null;
		} else {
			int bracket = s.indexOf("[");
			return bracket < 1 ? MarkerManager.getMarker(s) : MarkerManager.getMarker(s.substring(0, bracket));
		}
	}
}
