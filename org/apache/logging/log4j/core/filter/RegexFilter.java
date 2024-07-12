package org.apache.logging.log4j.core.filter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.message.Message;

@Plugin(
	name = "RegexFilter",
	category = "Core",
	elementType = "filter",
	printObject = true
)
public final class RegexFilter extends AbstractFilter {
	private static final int DEFAULT_PATTERN_FLAGS = 0;
	private final Pattern pattern;
	private final boolean useRawMessage;

	private RegexFilter(boolean raw, Pattern pattern, Result onMatch, Result onMismatch) {
		super(onMatch, onMismatch);
		this.pattern = pattern;
		this.useRawMessage = raw;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
		return this.filter(msg);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
		return msg == null ? this.onMismatch : this.filter(msg.toString());
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
		if (msg == null) {
			return this.onMismatch;
		} else {
			String text = this.useRawMessage ? msg.getFormat() : msg.getFormattedMessage();
			return this.filter(text);
		}
	}

	@Override
	public Result filter(LogEvent event) {
		String text = this.useRawMessage ? event.getMessage().getFormat() : event.getMessage().getFormattedMessage();
		return this.filter(text);
	}

	private Result filter(String msg) {
		if (msg == null) {
			return this.onMismatch;
		} else {
			Matcher m = this.pattern.matcher(msg);
			return m.matches() ? this.onMatch : this.onMismatch;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("useRaw=").append(this.useRawMessage);
		sb.append(", pattern=").append(this.pattern.toString());
		return sb.toString();
	}

	@PluginFactory
	public static RegexFilter createFilter(
		@PluginAttribute("regex") String regex,
		@PluginElement("PatternFlags") String[] patternFlags,
		@PluginAttribute("useRawMsg") Boolean useRawMsg,
		@PluginAttribute("onMatch") Result match,
		@PluginAttribute("onMismatch") Result mismatch
	) throws IllegalArgumentException, IllegalAccessException {
		if (regex == null) {
			LOGGER.error("A regular expression must be provided for RegexFilter");
			return null;
		} else {
			return new RegexFilter(useRawMsg, Pattern.compile(regex, toPatternFlags(patternFlags)), match, mismatch);
		}
	}

	private static int toPatternFlags(String[] patternFlags) throws IllegalArgumentException, IllegalAccessException {
		if (patternFlags != null && patternFlags.length != 0) {
			Field[] fields = Pattern.class.getDeclaredFields();
			Comparator<Field> comparator = new Comparator<Field>() {
				public int compare(Field f1, Field f2) {
					return f1.getName().compareTo(f2.getName());
				}
			};
			Arrays.sort(fields, comparator);
			String[] fieldNames = new String[fields.length];

			for (int i = 0; i < fields.length; i++) {
				fieldNames[i] = fields[i].getName();
			}

			int flags = 0;

			for (String test : patternFlags) {
				int index = Arrays.binarySearch(fieldNames, test);
				if (index >= 0) {
					Field field = fields[index];
					flags |= field.getInt(Pattern.class);
				}
			}

			return flags;
		} else {
			return 0;
		}
	}
}
