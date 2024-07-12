package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.apache.logging.log4j.util.Strings;

@Plugin(
	name = "ExtendedThrowablePatternConverter",
	category = "Converter"
)
@ConverterKeys({"xEx", "xThrowable", "xException"})
public final class ExtendedThrowablePatternConverter extends ThrowablePatternConverter {
	private ExtendedThrowablePatternConverter(String[] options) {
		super("ExtendedThrowable", "throwable", options);
	}

	public static ExtendedThrowablePatternConverter newInstance(String[] options) {
		return new ExtendedThrowablePatternConverter(options);
	}

	@Override
	public void format(LogEvent event, StringBuilder toAppendTo) {
		ThrowableProxy proxy = event.getThrownProxy();
		Throwable throwable = event.getThrown();
		if ((throwable != null || proxy != null) && this.options.anyLines()) {
			if (proxy == null) {
				super.format(event, toAppendTo);
				return;
			}

			String extStackTrace = proxy.getExtendedStackTraceAsString(this.options.getIgnorePackages(), this.options.getTextRenderer());
			int len = toAppendTo.length();
			if (len > 0 && !Character.isWhitespace(toAppendTo.charAt(len - 1))) {
				toAppendTo.append(' ');
			}

			if (this.options.allLines() && Strings.LINE_SEPARATOR.equals(this.options.getSeparator())) {
				toAppendTo.append(extStackTrace);
			} else {
				StringBuilder sb = new StringBuilder();
				String[] array = extStackTrace.split(Strings.LINE_SEPARATOR);
				int limit = this.options.minLines(array.length) - 1;

				for (int i = 0; i <= limit; i++) {
					sb.append(array[i]);
					if (i < limit) {
						sb.append(this.options.getSeparator());
					}
				}

				toAppendTo.append(sb.toString());
			}
		}
	}
}
