package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;

@Plugin(
	name = "MethodLocationPatternConverter",
	category = "Converter"
)
@ConverterKeys({"M", "method"})
public final class MethodLocationPatternConverter extends LogEventPatternConverter {
	private static final MethodLocationPatternConverter INSTANCE = new MethodLocationPatternConverter();

	private MethodLocationPatternConverter() {
		super("Method", "method");
	}

	public static MethodLocationPatternConverter newInstance(String[] options) {
		return INSTANCE;
	}

	@Override
	public void format(LogEvent event, StringBuilder toAppendTo) {
		StackTraceElement element = event.getSource();
		if (element != null) {
			toAppendTo.append(element.getMethodName());
		}
	}
}
