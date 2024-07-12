package org.apache.logging.log4j.core.pattern;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;
import org.apache.logging.log4j.core.util.SystemNanoClock;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.Strings;

public final class PatternParser {
	static final String DISABLE_ANSI = "disableAnsi";
	static final String NO_CONSOLE_NO_ANSI = "noConsoleNoAnsi";
	private static final char ESCAPE_CHAR = '%';
	private static final Logger LOGGER = StatusLogger.getLogger();
	private static final int BUF_SIZE = 32;
	private static final int DECIMAL = 10;
	private final Configuration config;
	private final Map<String, Class<PatternConverter>> converterRules;

	public PatternParser(String converterKey) {
		this(null, converterKey, null, null);
	}

	public PatternParser(Configuration config, String converterKey, Class<?> expected) {
		this(config, converterKey, expected, null);
	}

	public PatternParser(Configuration config, String converterKey, Class<?> expectedClass, Class<?> filterClass) {
		this.config = config;
		PluginManager manager = new PluginManager(converterKey);
		manager.collectPlugins(config == null ? null : config.getPluginPackages());
		Map<String, PluginType<?>> plugins = manager.getPlugins();
		Map<String, Class<PatternConverter>> converters = new LinkedHashMap();

		for (PluginType<?> type : plugins.values()) {
			try {
				Class<PatternConverter> clazz = (Class<PatternConverter>)type.getPluginClass();
				if (filterClass == null || filterClass.isAssignableFrom(clazz)) {
					ConverterKeys keys = (ConverterKeys)clazz.getAnnotation(ConverterKeys.class);
					if (keys != null) {
						for (String key : keys.value()) {
							if (converters.containsKey(key)) {
								LOGGER.warn(
									"Converter key '{}' is already mapped to '{}'. Sorry, Dave, I can't let you do that! Ignoring plugin [{}].", key, converters.get(key), clazz
								);
							} else {
								converters.put(key, clazz);
							}
						}
					}
				}
			} catch (Exception var16) {
				LOGGER.error("Error processing plugin " + type.getElementName(), (Throwable)var16);
			}
		}

		this.converterRules = converters;
	}

	public List<PatternFormatter> parse(String pattern) {
		return this.parse(pattern, false, false, false);
	}

	public List<PatternFormatter> parse(String pattern, boolean alwaysWriteExceptions, boolean noConsoleNoAnsi) {
		return this.parse(pattern, alwaysWriteExceptions, false, noConsoleNoAnsi);
	}

	public List<PatternFormatter> parse(String pattern, boolean alwaysWriteExceptions, boolean disableAnsi, boolean noConsoleNoAnsi) {
		List<PatternFormatter> list = new ArrayList();
		List<PatternConverter> converters = new ArrayList();
		List<FormattingInfo> fields = new ArrayList();
		this.parse(pattern, converters, fields, disableAnsi, noConsoleNoAnsi, true);
		Iterator<FormattingInfo> fieldIter = fields.iterator();
		boolean handlesThrowable = false;

		for (PatternConverter converter : converters) {
			if (converter instanceof NanoTimePatternConverter && this.config != null) {
				this.config.setNanoClock(new SystemNanoClock());
			}

			LogEventPatternConverter pc;
			if (converter instanceof LogEventPatternConverter) {
				pc = (LogEventPatternConverter)converter;
				handlesThrowable |= pc.handlesThrowable();
			} else {
				pc = new LiteralPatternConverter(this.config, "", true);
			}

			FormattingInfo field;
			if (fieldIter.hasNext()) {
				field = (FormattingInfo)fieldIter.next();
			} else {
				field = FormattingInfo.getDefault();
			}

			list.add(new PatternFormatter(pc, field));
		}

		if (alwaysWriteExceptions && !handlesThrowable) {
			LogEventPatternConverter pcx = ExtendedThrowablePatternConverter.newInstance(null);
			list.add(new PatternFormatter(pcx, FormattingInfo.getDefault()));
		}

		return list;
	}

	private static int extractConverter(char lastChar, String pattern, int start, StringBuilder convBuf, StringBuilder currentLiteral) {
		int i = start;
		convBuf.setLength(0);
		if (!Character.isUnicodeIdentifierStart(lastChar)) {
			return start;
		} else {
			convBuf.append(lastChar);

			while (i < pattern.length() && Character.isUnicodeIdentifierPart(pattern.charAt(i))) {
				convBuf.append(pattern.charAt(i));
				currentLiteral.append(pattern.charAt(i));
				i++;
			}

			return i;
		}
	}

	private static int extractOptions(String pattern, int start, List<String> options) {
		int i = start;

		while (i < pattern.length() && pattern.charAt(i) == '{') {
			int begin = i++;
			int depth = 0;

			int end;
			do {
				end = pattern.indexOf(125, i);
				if (end == -1) {
					break;
				}

				int next = pattern.indexOf("{", i);
				if (next != -1 && next < end) {
					i = end + 1;
					depth++;
				} else if (depth > 0) {
					depth--;
				}
			} while (depth > 0);

			if (end == -1) {
				break;
			}

			String r = pattern.substring(begin + 1, end);
			options.add(r);
			i = end + 1;
		}

		return i;
	}

	public void parse(
		String pattern, List<PatternConverter> patternConverters, List<FormattingInfo> formattingInfos, boolean noConsoleNoAnsi, boolean convertBackslashes
	) {
		this.parse(pattern, patternConverters, formattingInfos, false, noConsoleNoAnsi, convertBackslashes);
	}

	public void parse(
		String pattern,
		List<PatternConverter> patternConverters,
		List<FormattingInfo> formattingInfos,
		boolean disableAnsi,
		boolean noConsoleNoAnsi,
		boolean convertBackslashes
	) {
		Objects.requireNonNull(pattern, "pattern");
		StringBuilder currentLiteral = new StringBuilder(32);
		int patternLength = pattern.length();
		PatternParser.ParserState state = PatternParser.ParserState.LITERAL_STATE;
		int i = 0;
		FormattingInfo formattingInfo = FormattingInfo.getDefault();

		while (i < patternLength) {
			char c = pattern.charAt(i++);
			switch (state) {
				case LITERAL_STATE:
					if (i == patternLength) {
						currentLiteral.append(c);
					} else if (c == '%') {
						switch (pattern.charAt(i)) {
							case '%':
								currentLiteral.append(c);
								i++;
								break;
							default:
								if (currentLiteral.length() != 0) {
									patternConverters.add(new LiteralPatternConverter(this.config, currentLiteral.toString(), convertBackslashes));
									formattingInfos.add(FormattingInfo.getDefault());
								}

								currentLiteral.setLength(0);
								currentLiteral.append(c);
								state = PatternParser.ParserState.CONVERTER_STATE;
								formattingInfo = FormattingInfo.getDefault();
						}
					} else {
						currentLiteral.append(c);
					}
					break;
				case CONVERTER_STATE:
					currentLiteral.append(c);
					switch (c) {
						case '-':
							formattingInfo = new FormattingInfo(true, formattingInfo.getMinLength(), formattingInfo.getMaxLength(), formattingInfo.isLeftTruncate());
							continue;
						case '.':
							state = PatternParser.ParserState.DOT_STATE;
							continue;
						default:
							if (c >= '0' && c <= '9') {
								formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), c - '0', formattingInfo.getMaxLength(), formattingInfo.isLeftTruncate());
								state = PatternParser.ParserState.MIN_STATE;
								continue;
							}

							i = this.finalizeConverter(
								c,
								pattern,
								i,
								currentLiteral,
								formattingInfo,
								this.converterRules,
								patternConverters,
								formattingInfos,
								disableAnsi,
								noConsoleNoAnsi,
								convertBackslashes
							);
							state = PatternParser.ParserState.LITERAL_STATE;
							formattingInfo = FormattingInfo.getDefault();
							currentLiteral.setLength(0);
							continue;
					}
				case MIN_STATE:
					currentLiteral.append(c);
					if (c >= '0' && c <= '9') {
						formattingInfo = new FormattingInfo(
							formattingInfo.isLeftAligned(), formattingInfo.getMinLength() * 10 + c - 48, formattingInfo.getMaxLength(), formattingInfo.isLeftTruncate()
						);
					} else if (c == '.') {
						state = PatternParser.ParserState.DOT_STATE;
					} else {
						i = this.finalizeConverter(
							c, pattern, i, currentLiteral, formattingInfo, this.converterRules, patternConverters, formattingInfos, disableAnsi, noConsoleNoAnsi, convertBackslashes
						);
						state = PatternParser.ParserState.LITERAL_STATE;
						formattingInfo = FormattingInfo.getDefault();
						currentLiteral.setLength(0);
					}
					break;
				case DOT_STATE:
					currentLiteral.append(c);
					switch (c) {
						case '-':
							formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength(), formattingInfo.getMaxLength(), false);
							continue;
						default:
							if (c >= '0' && c <= '9') {
								formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength(), c - '0', formattingInfo.isLeftTruncate());
								state = PatternParser.ParserState.MAX_STATE;
								continue;
							}

							LOGGER.error("Error occurred in position " + i + ".\n Was expecting digit, instead got char \"" + c + "\".");
							state = PatternParser.ParserState.LITERAL_STATE;
							continue;
					}
				case MAX_STATE:
					currentLiteral.append(c);
					if (c >= '0' && c <= '9') {
						formattingInfo = new FormattingInfo(
							formattingInfo.isLeftAligned(), formattingInfo.getMinLength(), formattingInfo.getMaxLength() * 10 + c - 48, formattingInfo.isLeftTruncate()
						);
					} else {
						i = this.finalizeConverter(
							c, pattern, i, currentLiteral, formattingInfo, this.converterRules, patternConverters, formattingInfos, disableAnsi, noConsoleNoAnsi, convertBackslashes
						);
						state = PatternParser.ParserState.LITERAL_STATE;
						formattingInfo = FormattingInfo.getDefault();
						currentLiteral.setLength(0);
					}
			}
		}

		if (currentLiteral.length() != 0) {
			patternConverters.add(new LiteralPatternConverter(this.config, currentLiteral.toString(), convertBackslashes));
			formattingInfos.add(FormattingInfo.getDefault());
		}
	}

	private PatternConverter createConverter(
		String converterId,
		StringBuilder currentLiteral,
		Map<String, Class<PatternConverter>> rules,
		List<String> options,
		boolean disableAnsi,
		boolean noConsoleNoAnsi
	) {
		String converterName = converterId;
		Class<PatternConverter> converterClass = null;
		if (rules == null) {
			LOGGER.error("Null rules for [" + converterId + ']');
			return null;
		} else {
			for (int i = converterId.length(); i > 0 && converterClass == null; i--) {
				converterName = converterName.substring(0, i);
				converterClass = (Class<PatternConverter>)rules.get(converterName);
			}

			if (converterClass == null) {
				LOGGER.error("Unrecognized format specifier [" + converterId + ']');
				return null;
			} else {
				if (AnsiConverter.class.isAssignableFrom(converterClass)) {
					options.add("disableAnsi=" + disableAnsi);
					options.add("noConsoleNoAnsi=" + noConsoleNoAnsi);
				}

				Method[] methods = converterClass.getDeclaredMethods();
				Method newInstanceMethod = null;

				for (Method method : methods) {
					if (Modifier.isStatic(method.getModifiers()) && method.getDeclaringClass().equals(converterClass) && method.getName().equals("newInstance")) {
						if (newInstanceMethod == null) {
							newInstanceMethod = method;
						} else if (method.getReturnType().equals(newInstanceMethod.getReturnType())) {
							LOGGER.error("Class " + converterClass + " cannot contain multiple static newInstance methods");
							return null;
						}
					}
				}

				if (newInstanceMethod == null) {
					LOGGER.error("Class " + converterClass + " does not contain a static newInstance method");
					return null;
				} else {
					Class<?>[] parmTypes = newInstanceMethod.getParameterTypes();
					Object[] parms = parmTypes.length > 0 ? new Object[parmTypes.length] : null;
					if (parms != null) {
						int i = 0;
						boolean errors = false;

						for (Class<?> clazz : parmTypes) {
							if (clazz.isArray() && clazz.getName().equals("[Ljava.lang.String;")) {
								String[] optionsArray = (String[])options.toArray(new String[options.size()]);
								parms[i] = optionsArray;
							} else if (clazz.isAssignableFrom(Configuration.class)) {
								parms[i] = this.config;
							} else {
								LOGGER.error("Unknown parameter type " + clazz.getName() + " for static newInstance method of " + converterClass.getName());
								errors = true;
							}

							i++;
						}

						if (errors) {
							return null;
						}
					}

					try {
						Object newObj = newInstanceMethod.invoke(null, parms);
						if (newObj instanceof PatternConverter) {
							currentLiteral.delete(0, currentLiteral.length() - (converterId.length() - converterName.length()));
							return (PatternConverter)newObj;
						}

						LOGGER.warn("Class {} does not extend PatternConverter.", converterClass.getName());
					} catch (Exception var20) {
						LOGGER.error("Error creating converter for " + converterId, (Throwable)var20);
					}

					return null;
				}
			}
		}
	}

	private int finalizeConverter(
		char c,
		String pattern,
		int start,
		StringBuilder currentLiteral,
		FormattingInfo formattingInfo,
		Map<String, Class<PatternConverter>> rules,
		List<PatternConverter> patternConverters,
		List<FormattingInfo> formattingInfos,
		boolean disableAnsi,
		boolean noConsoleNoAnsi,
		boolean convertBackslashes
	) {
		StringBuilder convBuf = new StringBuilder();
		int i = extractConverter(c, pattern, start, convBuf, currentLiteral);
		String converterId = convBuf.toString();
		List<String> options = new ArrayList();
		i = extractOptions(pattern, i, options);
		PatternConverter pc = this.createConverter(converterId, currentLiteral, rules, options, disableAnsi, noConsoleNoAnsi);
		if (pc == null) {
			StringBuilder msg;
			if (Strings.isEmpty(converterId)) {
				msg = new StringBuilder("Empty conversion specifier starting at position ");
			} else {
				msg = new StringBuilder("Unrecognized conversion specifier [");
				msg.append(converterId);
				msg.append("] starting at position ");
			}

			msg.append(Integer.toString(i));
			msg.append(" in conversion pattern.");
			LOGGER.error(msg.toString());
			patternConverters.add(new LiteralPatternConverter(this.config, currentLiteral.toString(), convertBackslashes));
			formattingInfos.add(FormattingInfo.getDefault());
		} else {
			patternConverters.add(pc);
			formattingInfos.add(formattingInfo);
			if (currentLiteral.length() > 0) {
				patternConverters.add(new LiteralPatternConverter(this.config, currentLiteral.toString(), convertBackslashes));
				formattingInfos.add(FormattingInfo.getDefault());
			}
		}

		currentLiteral.setLength(0);
		return i;
	}

	private static enum ParserState {
		LITERAL_STATE,
		CONVERTER_STATE,
		DOT_STATE,
		MIN_STATE,
		MAX_STATE;
	}
}
