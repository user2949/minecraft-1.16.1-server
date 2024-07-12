package org.apache.logging.log4j.core.pattern;

public interface PatternConverter {
	String CATEGORY = "Converter";

	void format(Object object, StringBuilder stringBuilder);

	String getName();

	String getStyleClass(Object object);
}
