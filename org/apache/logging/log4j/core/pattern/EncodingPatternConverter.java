package org.apache.logging.log4j.core.pattern;

import java.util.List;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(
	name = "encode",
	category = "Converter"
)
@ConverterKeys({"enc", "encode"})
@PerformanceSensitive({"allocation"})
public final class EncodingPatternConverter extends LogEventPatternConverter {
	private final List<PatternFormatter> formatters;

	private EncodingPatternConverter(List<PatternFormatter> formatters) {
		super("encode", "encode");
		this.formatters = formatters;
	}

	public static EncodingPatternConverter newInstance(Configuration config, String[] options) {
		if (options.length != 1) {
			LOGGER.error("Incorrect number of options on escape. Expected 1, received " + options.length);
			return null;
		} else if (options[0] == null) {
			LOGGER.error("No pattern supplied on escape");
			return null;
		} else {
			PatternParser parser = PatternLayout.createPatternParser(config);
			List<PatternFormatter> formatters = parser.parse(options[0]);
			return new EncodingPatternConverter(formatters);
		}
	}

	@Override
	public void format(LogEvent event, StringBuilder toAppendTo) {
		int start = toAppendTo.length();

		for (int i = 0; i < this.formatters.size(); i++) {
			((PatternFormatter)this.formatters.get(i)).format(event, toAppendTo);
		}

		for (int i = toAppendTo.length() - 1; i >= start; i--) {
			char c = toAppendTo.charAt(i);
			switch (c) {
				case '\n':
					toAppendTo.setCharAt(i, '\\');
					toAppendTo.insert(i + 1, 'n');
					break;
				case '\r':
					toAppendTo.setCharAt(i, '\\');
					toAppendTo.insert(i + 1, 'r');
					break;
				case '"':
					toAppendTo.setCharAt(i, '&');
					toAppendTo.insert(i + 1, "quot;");
					break;
				case '&':
					toAppendTo.setCharAt(i, '&');
					toAppendTo.insert(i + 1, "amp;");
					break;
				case '\'':
					toAppendTo.setCharAt(i, '&');
					toAppendTo.insert(i + 1, "apos;");
					break;
				case '/':
					toAppendTo.setCharAt(i, '&');
					toAppendTo.insert(i + 1, "#x2F;");
					break;
				case '<':
					toAppendTo.setCharAt(i, '&');
					toAppendTo.insert(i + 1, "lt;");
					break;
				case '>':
					toAppendTo.setCharAt(i, '&');
					toAppendTo.insert(i + 1, "gt;");
			}
		}
	}
}
