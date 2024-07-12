package org.apache.logging.log4j.core.pattern;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(
	name = "highlight",
	category = "Converter"
)
@ConverterKeys({"highlight"})
@PerformanceSensitive({"allocation"})
public final class HighlightConverter extends LogEventPatternConverter implements AnsiConverter {
	private static final Map<Level, String> DEFAULT_STYLES = new HashMap();
	private static final Map<Level, String> LOGBACK_STYLES = new HashMap();
	private static final String STYLE_KEY = "STYLE";
	private static final String STYLE_KEY_DEFAULT = "DEFAULT";
	private static final String STYLE_KEY_LOGBACK = "LOGBACK";
	private static final Map<String, Map<Level, String>> STYLES = new HashMap();
	private final Map<Level, String> levelStyles;
	private final List<PatternFormatter> patternFormatters;
	private final boolean noAnsi;
	private final String defaultStyle;

	private static Map<Level, String> createLevelStyleMap(String[] options) {
		if (options.length < 2) {
			return DEFAULT_STYLES;
		} else {
			String string = options[1].replaceAll("disableAnsi=(true|false)", "").replaceAll("noConsoleNoAnsi=(true|false)", "");
			Map<String, String> styles = AnsiEscape.createMap(string, new String[]{"STYLE"});
			Map<Level, String> levelStyles = new HashMap(DEFAULT_STYLES);

			for (Entry<String, String> entry : styles.entrySet()) {
				String key = ((String)entry.getKey()).toUpperCase(Locale.ENGLISH);
				String value = (String)entry.getValue();
				if ("STYLE".equalsIgnoreCase(key)) {
					Map<Level, String> enumMap = (Map<Level, String>)STYLES.get(value.toUpperCase(Locale.ENGLISH));
					if (enumMap == null) {
						LOGGER.error("Unknown level style: " + value + ". Use one of " + Arrays.toString(STYLES.keySet().toArray()));
					} else {
						levelStyles.putAll(enumMap);
					}
				} else {
					Level level = Level.toLevel(key);
					if (level == null) {
						LOGGER.error("Unknown level name: " + key + ". Use one of " + Arrays.toString(DEFAULT_STYLES.keySet().toArray()));
					} else {
						levelStyles.put(level, value);
					}
				}
			}

			return levelStyles;
		}
	}

	public static HighlightConverter newInstance(Configuration config, String[] options) {
		if (options.length < 1) {
			LOGGER.error("Incorrect number of options on style. Expected at least 1, received " + options.length);
			return null;
		} else if (options[0] == null) {
			LOGGER.error("No pattern supplied on style");
			return null;
		} else {
			PatternParser parser = PatternLayout.createPatternParser(config);
			List<PatternFormatter> formatters = parser.parse(options[0]);
			boolean disableAnsi = Arrays.toString(options).contains("disableAnsi=true");
			boolean noConsoleNoAnsi = Arrays.toString(options).contains("noConsoleNoAnsi=true");
			boolean hideAnsi = disableAnsi || noConsoleNoAnsi && System.console() == null;
			return new HighlightConverter(formatters, createLevelStyleMap(options), hideAnsi);
		}
	}

	private HighlightConverter(List<PatternFormatter> patternFormatters, Map<Level, String> levelStyles, boolean noAnsi) {
		super("style", "style");
		this.patternFormatters = patternFormatters;
		this.levelStyles = levelStyles;
		this.defaultStyle = AnsiEscape.getDefaultStyle();
		this.noAnsi = noAnsi;
	}

	@Override
	public void format(LogEvent event, StringBuilder toAppendTo) {
		int start = 0;
		int end = 0;
		if (!this.noAnsi) {
			start = toAppendTo.length();
			toAppendTo.append((String)this.levelStyles.get(event.getLevel()));
			end = toAppendTo.length();
		}

		int i = 0;

		for (int size = this.patternFormatters.size(); i < size; i++) {
			((PatternFormatter)this.patternFormatters.get(i)).format(event, toAppendTo);
		}

		boolean empty = toAppendTo.length() == end;
		if (!this.noAnsi) {
			if (empty) {
				toAppendTo.setLength(start);
			} else {
				toAppendTo.append(this.defaultStyle);
			}
		}
	}

	@Override
	public boolean handlesThrowable() {
		for (PatternFormatter formatter : this.patternFormatters) {
			if (formatter.handlesThrowable()) {
				return true;
			}
		}

		return false;
	}

	static {
		DEFAULT_STYLES.put(Level.FATAL, AnsiEscape.createSequence("BRIGHT", "RED"));
		DEFAULT_STYLES.put(Level.ERROR, AnsiEscape.createSequence("BRIGHT", "RED"));
		DEFAULT_STYLES.put(Level.WARN, AnsiEscape.createSequence("YELLOW"));
		DEFAULT_STYLES.put(Level.INFO, AnsiEscape.createSequence("GREEN"));
		DEFAULT_STYLES.put(Level.DEBUG, AnsiEscape.createSequence("CYAN"));
		DEFAULT_STYLES.put(Level.TRACE, AnsiEscape.createSequence("BLACK"));
		LOGBACK_STYLES.put(Level.FATAL, AnsiEscape.createSequence("BLINK", "BRIGHT", "RED"));
		LOGBACK_STYLES.put(Level.ERROR, AnsiEscape.createSequence("BRIGHT", "RED"));
		LOGBACK_STYLES.put(Level.WARN, AnsiEscape.createSequence("RED"));
		LOGBACK_STYLES.put(Level.INFO, AnsiEscape.createSequence("BLUE"));
		LOGBACK_STYLES.put(Level.DEBUG, AnsiEscape.createSequence((String[])null));
		LOGBACK_STYLES.put(Level.TRACE, AnsiEscape.createSequence((String[])null));
		STYLES.put("DEFAULT", DEFAULT_STYLES);
		STYLES.put("LOGBACK", LOGBACK_STYLES);
	}
}
