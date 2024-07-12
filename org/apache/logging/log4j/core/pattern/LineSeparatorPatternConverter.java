package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.Strings;

@Plugin(
	name = "LineSeparatorPatternConverter",
	category = "Converter"
)
@ConverterKeys({"n"})
@PerformanceSensitive({"allocation"})
public final class LineSeparatorPatternConverter extends LogEventPatternConverter {
	private static final LineSeparatorPatternConverter INSTANCE = new LineSeparatorPatternConverter();
	private final String lineSep = Strings.LINE_SEPARATOR;

	private LineSeparatorPatternConverter() {
		super("Line Sep", "lineSep");
	}

	public static LineSeparatorPatternConverter newInstance(String[] options) {
		return INSTANCE;
	}

	@Override
	public void format(LogEvent event, StringBuilder toAppendTo) {
		toAppendTo.append(this.lineSep);
	}
}
