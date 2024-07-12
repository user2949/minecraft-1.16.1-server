package org.apache.logging.log4j.core.layout;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LoggingException;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.TlsSyslogFrame;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.net.Facility;
import org.apache.logging.log4j.core.net.Priority;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.apache.logging.log4j.core.pattern.PatternFormatter;
import org.apache.logging.log4j.core.pattern.PatternParser;
import org.apache.logging.log4j.core.pattern.ThrowablePatternConverter;
import org.apache.logging.log4j.core.util.NetUtils;
import org.apache.logging.log4j.core.util.Patterns;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.StructuredDataId;
import org.apache.logging.log4j.message.StructuredDataMessage;
import org.apache.logging.log4j.util.StringBuilders;
import org.apache.logging.log4j.util.Strings;

@Plugin(
	name = "Rfc5424Layout",
	category = "Core",
	elementType = "layout",
	printObject = true
)
public final class Rfc5424Layout extends AbstractStringLayout {
	public static final int DEFAULT_ENTERPRISE_NUMBER = 18060;
	public static final String DEFAULT_ID = "Audit";
	public static final Pattern NEWLINE_PATTERN = Pattern.compile("\\r?\\n");
	public static final Pattern PARAM_VALUE_ESCAPE_PATTERN = Pattern.compile("[\\\"\\]\\\\]");
	public static final String DEFAULT_MDCID = "mdc";
	private static final String LF = "\n";
	private static final int TWO_DIGITS = 10;
	private static final int THREE_DIGITS = 100;
	private static final int MILLIS_PER_MINUTE = 60000;
	private static final int MINUTES_PER_HOUR = 60;
	private static final String COMPONENT_KEY = "RFC5424-Converter";
	private final Facility facility;
	private final String defaultId;
	private final int enterpriseNumber;
	private final boolean includeMdc;
	private final String mdcId;
	private final StructuredDataId mdcSdId;
	private final String localHostName;
	private final String appName;
	private final String messageId;
	private final String configName;
	private final String mdcPrefix;
	private final String eventPrefix;
	private final List<String> mdcExcludes;
	private final List<String> mdcIncludes;
	private final List<String> mdcRequired;
	private final Rfc5424Layout.ListChecker listChecker;
	private final Rfc5424Layout.ListChecker noopChecker = new Rfc5424Layout.NoopChecker();
	private final boolean includeNewLine;
	private final String escapeNewLine;
	private final boolean useTlsMessageFormat;
	private long lastTimestamp = -1L;
	private String timestamppStr;
	private final List<PatternFormatter> exceptionFormatters;
	private final Map<String, Rfc5424Layout.FieldFormatter> fieldFormatters;
	private final String procId;

	private Rfc5424Layout(
		Configuration config,
		Facility facility,
		String id,
		int ein,
		boolean includeMDC,
		boolean includeNL,
		String escapeNL,
		String mdcId,
		String mdcPrefix,
		String eventPrefix,
		String appName,
		String messageId,
		String excludes,
		String includes,
		String required,
		Charset charset,
		String exceptionPattern,
		boolean useTLSMessageFormat,
		LoggerFields[] loggerFields
	) {
		super(charset);
		PatternParser exceptionParser = createPatternParser(config, ThrowablePatternConverter.class);
		this.exceptionFormatters = exceptionPattern == null ? null : exceptionParser.parse(exceptionPattern);
		this.facility = facility;
		this.defaultId = id == null ? "Audit" : id;
		this.enterpriseNumber = ein;
		this.includeMdc = includeMDC;
		this.includeNewLine = includeNL;
		this.escapeNewLine = escapeNL == null ? null : Matcher.quoteReplacement(escapeNL);
		this.mdcId = mdcId;
		this.mdcSdId = new StructuredDataId(mdcId, this.enterpriseNumber, null, null);
		this.mdcPrefix = mdcPrefix;
		this.eventPrefix = eventPrefix;
		this.appName = appName;
		this.messageId = messageId;
		this.useTlsMessageFormat = useTLSMessageFormat;
		this.localHostName = NetUtils.getLocalHostname();
		Rfc5424Layout.ListChecker c = null;
		if (excludes != null) {
			String[] array = excludes.split(Patterns.COMMA_SEPARATOR);
			if (array.length > 0) {
				c = new Rfc5424Layout.ExcludeChecker();
				this.mdcExcludes = new ArrayList(array.length);

				for (String str : array) {
					this.mdcExcludes.add(str.trim());
				}
			} else {
				this.mdcExcludes = null;
			}
		} else {
			this.mdcExcludes = null;
		}

		if (includes != null) {
			String[] array = includes.split(Patterns.COMMA_SEPARATOR);
			if (array.length > 0) {
				c = new Rfc5424Layout.IncludeChecker();
				this.mdcIncludes = new ArrayList(array.length);

				for (String str : array) {
					this.mdcIncludes.add(str.trim());
				}
			} else {
				this.mdcIncludes = null;
			}
		} else {
			this.mdcIncludes = null;
		}

		if (required != null) {
			String[] array = required.split(Patterns.COMMA_SEPARATOR);
			if (array.length > 0) {
				this.mdcRequired = new ArrayList(array.length);

				for (String str : array) {
					this.mdcRequired.add(str.trim());
				}
			} else {
				this.mdcRequired = null;
			}
		} else {
			this.mdcRequired = null;
		}

		this.listChecker = c != null ? c : this.noopChecker;
		String name = config == null ? null : config.getName();
		this.configName = Strings.isNotEmpty(name) ? name : null;
		this.fieldFormatters = this.createFieldFormatters(loggerFields, config);
		this.procId = "-";
	}

	private Map<String, Rfc5424Layout.FieldFormatter> createFieldFormatters(LoggerFields[] loggerFields, Configuration config) {
		Map<String, Rfc5424Layout.FieldFormatter> sdIdMap = new HashMap(loggerFields == null ? 0 : loggerFields.length);
		if (loggerFields != null) {
			for (LoggerFields loggerField : loggerFields) {
				StructuredDataId key = loggerField.getSdId() == null ? this.mdcSdId : loggerField.getSdId();
				Map<String, List<PatternFormatter>> sdParams = new HashMap();
				Map<String, String> fields = loggerField.getMap();
				if (!fields.isEmpty()) {
					PatternParser fieldParser = createPatternParser(config, null);

					for (Entry<String, String> entry : fields.entrySet()) {
						List<PatternFormatter> formatters = fieldParser.parse((String)entry.getValue());
						sdParams.put(entry.getKey(), formatters);
					}

					Rfc5424Layout.FieldFormatter fieldFormatter = new Rfc5424Layout.FieldFormatter(sdParams, loggerField.getDiscardIfAllFieldsAreEmpty());
					sdIdMap.put(key.toString(), fieldFormatter);
				}
			}
		}

		return sdIdMap.size() > 0 ? sdIdMap : null;
	}

	private static PatternParser createPatternParser(Configuration config, Class<? extends PatternConverter> filterClass) {
		if (config == null) {
			return new PatternParser(config, "Converter", LogEventPatternConverter.class, filterClass);
		} else {
			PatternParser parser = config.getComponent("RFC5424-Converter");
			if (parser == null) {
				parser = new PatternParser(config, "Converter", ThrowablePatternConverter.class);
				config.addComponent("RFC5424-Converter", parser);
				parser = config.getComponent("RFC5424-Converter");
			}

			return parser;
		}
	}

	@Override
	public Map<String, String> getContentFormat() {
		Map<String, String> result = new HashMap();
		result.put("structured", "true");
		result.put("formatType", "RFC5424");
		return result;
	}

	public String toSerializable(LogEvent event) {
		StringBuilder buf = getStringBuilder();
		this.appendPriority(buf, event.getLevel());
		this.appendTimestamp(buf, event.getTimeMillis());
		this.appendSpace(buf);
		this.appendHostName(buf);
		this.appendSpace(buf);
		this.appendAppName(buf);
		this.appendSpace(buf);
		this.appendProcessId(buf);
		this.appendSpace(buf);
		this.appendMessageId(buf, event.getMessage());
		this.appendSpace(buf);
		this.appendStructuredElements(buf, event);
		this.appendMessage(buf, event);
		return this.useTlsMessageFormat ? new TlsSyslogFrame(buf.toString()).toString() : buf.toString();
	}

	private void appendPriority(StringBuilder buffer, Level logLevel) {
		buffer.append('<');
		buffer.append(Priority.getPriority(this.facility, logLevel));
		buffer.append(">1 ");
	}

	private void appendTimestamp(StringBuilder buffer, long milliseconds) {
		buffer.append(this.computeTimeStampString(milliseconds));
	}

	private void appendSpace(StringBuilder buffer) {
		buffer.append(' ');
	}

	private void appendHostName(StringBuilder buffer) {
		buffer.append(this.localHostName);
	}

	private void appendAppName(StringBuilder buffer) {
		if (this.appName != null) {
			buffer.append(this.appName);
		} else if (this.configName != null) {
			buffer.append(this.configName);
		} else {
			buffer.append('-');
		}
	}

	private void appendProcessId(StringBuilder buffer) {
		buffer.append(this.getProcId());
	}

	private void appendMessageId(StringBuilder buffer, Message message) {
		boolean isStructured = message instanceof StructuredDataMessage;
		String type = isStructured ? ((StructuredDataMessage)message).getType() : null;
		if (type != null) {
			buffer.append(type);
		} else if (this.messageId != null) {
			buffer.append(this.messageId);
		} else {
			buffer.append('-');
		}
	}

	private void appendMessage(StringBuilder buffer, LogEvent event) {
		Message message = event.getMessage();
		String text = message instanceof StructuredDataMessage ? message.getFormat() : message.getFormattedMessage();
		if (text != null && text.length() > 0) {
			buffer.append(' ').append(this.escapeNewlines(text, this.escapeNewLine));
		}

		if (this.exceptionFormatters != null && event.getThrown() != null) {
			StringBuilder exception = new StringBuilder("\n");

			for (PatternFormatter formatter : this.exceptionFormatters) {
				formatter.format(event, exception);
			}

			buffer.append(this.escapeNewlines(exception.toString(), this.escapeNewLine));
		}

		if (this.includeNewLine) {
			buffer.append("\n");
		}
	}

	private void appendStructuredElements(StringBuilder buffer, LogEvent event) {
		Message message = event.getMessage();
		boolean isStructured = message instanceof StructuredDataMessage;
		if (!isStructured && this.fieldFormatters != null && this.fieldFormatters.isEmpty() && !this.includeMdc) {
			buffer.append('-');
		} else {
			Map<String, Rfc5424Layout.StructuredDataElement> sdElements = new HashMap();
			Map<String, String> contextMap = event.getContextData().toMap();
			if (this.mdcRequired != null) {
				this.checkRequired(contextMap);
			}

			if (this.fieldFormatters != null) {
				for (Entry<String, Rfc5424Layout.FieldFormatter> sdElement : this.fieldFormatters.entrySet()) {
					String sdId = (String)sdElement.getKey();
					Rfc5424Layout.StructuredDataElement elem = ((Rfc5424Layout.FieldFormatter)sdElement.getValue()).format(event);
					sdElements.put(sdId, elem);
				}
			}

			if (this.includeMdc && contextMap.size() > 0) {
				String mdcSdIdStr = this.mdcSdId.toString();
				Rfc5424Layout.StructuredDataElement union = (Rfc5424Layout.StructuredDataElement)sdElements.get(mdcSdIdStr);
				if (union != null) {
					union.union(contextMap);
					sdElements.put(mdcSdIdStr, union);
				} else {
					Rfc5424Layout.StructuredDataElement formattedContextMap = new Rfc5424Layout.StructuredDataElement(contextMap, false);
					sdElements.put(mdcSdIdStr, formattedContextMap);
				}
			}

			if (isStructured) {
				StructuredDataMessage data = (StructuredDataMessage)message;
				Map<String, String> map = data.getData();
				StructuredDataId id = data.getId();
				String sdId = this.getId(id);
				if (sdElements.containsKey(sdId)) {
					Rfc5424Layout.StructuredDataElement union = (Rfc5424Layout.StructuredDataElement)sdElements.get(id.toString());
					union.union(map);
					sdElements.put(sdId, union);
				} else {
					Rfc5424Layout.StructuredDataElement formattedData = new Rfc5424Layout.StructuredDataElement(map, false);
					sdElements.put(sdId, formattedData);
				}
			}

			if (sdElements.isEmpty()) {
				buffer.append('-');
			} else {
				for (Entry<String, Rfc5424Layout.StructuredDataElement> entry : sdElements.entrySet()) {
					this.formatStructuredElement((String)entry.getKey(), this.mdcPrefix, (Rfc5424Layout.StructuredDataElement)entry.getValue(), buffer, this.listChecker);
				}
			}
		}
	}

	private String escapeNewlines(String text, String replacement) {
		return null == replacement ? text : NEWLINE_PATTERN.matcher(text).replaceAll(replacement);
	}

	protected String getProcId() {
		return this.procId;
	}

	protected List<String> getMdcExcludes() {
		return this.mdcExcludes;
	}

	protected List<String> getMdcIncludes() {
		return this.mdcIncludes;
	}

	private String computeTimeStampString(long now) {
		long last;
		synchronized (this) {
			last = this.lastTimestamp;
			if (now == this.lastTimestamp) {
				return this.timestamppStr;
			}
		}

		StringBuilder buffer = new StringBuilder();
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(now);
		buffer.append(Integer.toString(cal.get(1)));
		buffer.append('-');
		this.pad(cal.get(2) + 1, 10, buffer);
		buffer.append('-');
		this.pad(cal.get(5), 10, buffer);
		buffer.append('T');
		this.pad(cal.get(11), 10, buffer);
		buffer.append(':');
		this.pad(cal.get(12), 10, buffer);
		buffer.append(':');
		this.pad(cal.get(13), 10, buffer);
		buffer.append('.');
		this.pad(cal.get(14), 100, buffer);
		int tzmin = (cal.get(15) + cal.get(16)) / 60000;
		if (tzmin == 0) {
			buffer.append('Z');
		} else {
			if (tzmin < 0) {
				tzmin = -tzmin;
				buffer.append('-');
			} else {
				buffer.append('+');
			}

			int tzhour = tzmin / 60;
			tzmin -= tzhour * 60;
			this.pad(tzhour, 10, buffer);
			buffer.append(':');
			this.pad(tzmin, 10, buffer);
		}

		synchronized (this) {
			if (last == this.lastTimestamp) {
				this.lastTimestamp = now;
				this.timestamppStr = buffer.toString();
			}
		}

		return buffer.toString();
	}

	private void pad(int val, int max, StringBuilder buf) {
		while (max > 1) {
			if (val < max) {
				buf.append('0');
			}

			max /= 10;
		}

		buf.append(Integer.toString(val));
	}

	private void formatStructuredElement(String id, String prefix, Rfc5424Layout.StructuredDataElement data, StringBuilder sb, Rfc5424Layout.ListChecker checker) {
		if ((id != null || this.defaultId != null) && !data.discard()) {
			sb.append('[');
			sb.append(id);
			if (!this.mdcSdId.toString().equals(id)) {
				this.appendMap(prefix, data.getFields(), sb, this.noopChecker);
			} else {
				this.appendMap(prefix, data.getFields(), sb, checker);
			}

			sb.append(']');
		}
	}

	private String getId(StructuredDataId id) {
		StringBuilder sb = new StringBuilder();
		if (id != null && id.getName() != null) {
			sb.append(id.getName());
		} else {
			sb.append(this.defaultId);
		}

		int ein = id != null ? id.getEnterpriseNumber() : this.enterpriseNumber;
		if (ein < 0) {
			ein = this.enterpriseNumber;
		}

		if (ein >= 0) {
			sb.append('@').append(ein);
		}

		return sb.toString();
	}

	private void checkRequired(Map<String, String> map) {
		for (String key : this.mdcRequired) {
			String value = (String)map.get(key);
			if (value == null) {
				throw new LoggingException("Required key " + key + " is missing from the " + this.mdcId);
			}
		}
	}

	private void appendMap(String prefix, Map<String, String> map, StringBuilder sb, Rfc5424Layout.ListChecker checker) {
		SortedMap<String, String> sorted = new TreeMap(map);

		for (Entry<String, String> entry : sorted.entrySet()) {
			if (checker.check((String)entry.getKey()) && entry.getValue() != null) {
				sb.append(' ');
				if (prefix != null) {
					sb.append(prefix);
				}

				String safeKey = this.escapeNewlines(this.escapeSDParams((String)entry.getKey()), this.escapeNewLine);
				String safeValue = this.escapeNewlines(this.escapeSDParams((String)entry.getValue()), this.escapeNewLine);
				StringBuilders.appendKeyDqValue(sb, safeKey, safeValue);
			}
		}
	}

	private String escapeSDParams(String value) {
		return PARAM_VALUE_ESCAPE_PATTERN.matcher(value).replaceAll("\\\\$0");
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("facility=").append(this.facility.name());
		sb.append(" appName=").append(this.appName);
		sb.append(" defaultId=").append(this.defaultId);
		sb.append(" enterpriseNumber=").append(this.enterpriseNumber);
		sb.append(" newLine=").append(this.includeNewLine);
		sb.append(" includeMDC=").append(this.includeMdc);
		sb.append(" messageId=").append(this.messageId);
		return sb.toString();
	}

	@PluginFactory
	public static Rfc5424Layout createLayout(
		@PluginAttribute(value = "facility",defaultString = "LOCAL0") Facility facility,
		@PluginAttribute("id") String id,
		@PluginAttribute(value = "enterpriseNumber",defaultInt = 18060) int enterpriseNumber,
		@PluginAttribute(value = "includeMDC",defaultBoolean = true) boolean includeMDC,
		@PluginAttribute(value = "mdcId",defaultString = "mdc") String mdcId,
		@PluginAttribute("mdcPrefix") String mdcPrefix,
		@PluginAttribute("eventPrefix") String eventPrefix,
		@PluginAttribute("newLine") boolean newLine,
		@PluginAttribute("newLineEscape") String escapeNL,
		@PluginAttribute("appName") String appName,
		@PluginAttribute("messageId") String msgId,
		@PluginAttribute("mdcExcludes") String excludes,
		@PluginAttribute("mdcIncludes") String includes,
		@PluginAttribute("mdcRequired") String required,
		@PluginAttribute("exceptionPattern") String exceptionPattern,
		@PluginAttribute("useTlsMessageFormat") boolean useTlsMessageFormat,
		@PluginElement("LoggerFields") LoggerFields[] loggerFields,
		@PluginConfiguration Configuration config
	) {
		if (includes != null && excludes != null) {
			LOGGER.error("mdcIncludes and mdcExcludes are mutually exclusive. Includes wil be ignored");
			includes = null;
		}

		return new Rfc5424Layout(
			config,
			facility,
			id,
			enterpriseNumber,
			includeMDC,
			newLine,
			escapeNL,
			mdcId,
			mdcPrefix,
			eventPrefix,
			appName,
			msgId,
			excludes,
			includes,
			required,
			StandardCharsets.UTF_8,
			exceptionPattern,
			useTlsMessageFormat,
			loggerFields
		);
	}

	public Facility getFacility() {
		return this.facility;
	}

	private class ExcludeChecker implements Rfc5424Layout.ListChecker {
		private ExcludeChecker() {
		}

		@Override
		public boolean check(String key) {
			return !Rfc5424Layout.this.mdcExcludes.contains(key);
		}
	}

	private class FieldFormatter {
		private final Map<String, List<PatternFormatter>> delegateMap;
		private final boolean discardIfEmpty;

		public FieldFormatter(Map<String, List<PatternFormatter>> fieldMap, boolean discardIfEmpty) {
			this.discardIfEmpty = discardIfEmpty;
			this.delegateMap = fieldMap;
		}

		public Rfc5424Layout.StructuredDataElement format(LogEvent event) {
			Map<String, String> map = new HashMap(this.delegateMap.size());

			for (Entry<String, List<PatternFormatter>> entry : this.delegateMap.entrySet()) {
				StringBuilder buffer = new StringBuilder();

				for (PatternFormatter formatter : (List)entry.getValue()) {
					formatter.format(event, buffer);
				}

				map.put(entry.getKey(), buffer.toString());
			}

			return Rfc5424Layout.this.new StructuredDataElement(map, this.discardIfEmpty);
		}
	}

	private class IncludeChecker implements Rfc5424Layout.ListChecker {
		private IncludeChecker() {
		}

		@Override
		public boolean check(String key) {
			return Rfc5424Layout.this.mdcIncludes.contains(key);
		}
	}

	private interface ListChecker {
		boolean check(String string);
	}

	private class NoopChecker implements Rfc5424Layout.ListChecker {
		private NoopChecker() {
		}

		@Override
		public boolean check(String key) {
			return true;
		}
	}

	private class StructuredDataElement {
		private final Map<String, String> fields;
		private final boolean discardIfEmpty;

		public StructuredDataElement(Map<String, String> fields, boolean discardIfEmpty) {
			this.discardIfEmpty = discardIfEmpty;
			this.fields = fields;
		}

		boolean discard() {
			if (!this.discardIfEmpty) {
				return false;
			} else {
				boolean foundNotEmptyValue = false;

				for (Entry<String, String> entry : this.fields.entrySet()) {
					if (Strings.isNotEmpty((CharSequence)entry.getValue())) {
						foundNotEmptyValue = true;
						break;
					}
				}

				return !foundNotEmptyValue;
			}
		}

		void union(Map<String, String> addFields) {
			this.fields.putAll(addFields);
		}

		Map<String, String> getFields() {
			return this.fields;
		}
	}
}
