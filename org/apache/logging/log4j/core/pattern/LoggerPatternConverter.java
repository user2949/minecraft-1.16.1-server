package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(
	name = "LoggerPatternConverter",
	category = "Converter"
)
@ConverterKeys({"c", "logger"})
@PerformanceSensitive({"allocation"})
public final class LoggerPatternConverter extends NamePatternConverter {
	private static final LoggerPatternConverter INSTANCE = new LoggerPatternConverter(null);

	private LoggerPatternConverter(String[] options) {
		super("Logger", "logger", options);
	}

	public static LoggerPatternConverter newInstance(String[] options) {
		return options != null && options.length != 0 ? new LoggerPatternConverter(options) : INSTANCE;
	}

	@Override
	public void format(LogEvent event, StringBuilder toAppendTo) {
		this.abbreviate(event.getLoggerName(), toAppendTo);
	}
}
