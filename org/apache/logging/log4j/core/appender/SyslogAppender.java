package org.apache.logging.log4j.core.appender;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.appender.SocketAppender.AbstractBuilder;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.layout.LoggerFields;
import org.apache.logging.log4j.core.layout.Rfc5424Layout;
import org.apache.logging.log4j.core.layout.SyslogLayout;
import org.apache.logging.log4j.core.net.AbstractSocketManager;
import org.apache.logging.log4j.core.net.Advertiser;
import org.apache.logging.log4j.core.net.Facility;
import org.apache.logging.log4j.core.net.Protocol;
import org.apache.logging.log4j.core.net.ssl.SslConfiguration;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.util.EnglishEnums;

@Plugin(
	name = "Syslog",
	category = "Core",
	elementType = "appender",
	printObject = true
)
public class SyslogAppender extends SocketAppender {
	protected static final String RFC5424 = "RFC5424";

	protected SyslogAppender(
		String name,
		Layout<? extends Serializable> layout,
		Filter filter,
		boolean ignoreExceptions,
		boolean immediateFlush,
		AbstractSocketManager manager,
		Advertiser advertiser
	) {
		super(name, layout, filter, manager, ignoreExceptions, immediateFlush, advertiser);
	}

	@Deprecated
	public static <B extends SyslogAppender.Builder<B>> SyslogAppender createAppender(
		String host,
		int port,
		String protocolStr,
		SslConfiguration sslConfiguration,
		int connectTimeoutMillis,
		int reconnectDelayMillis,
		boolean immediateFail,
		String name,
		boolean immediateFlush,
		boolean ignoreExceptions,
		Facility facility,
		String id,
		int enterpriseNumber,
		boolean includeMdc,
		String mdcId,
		String mdcPrefix,
		String eventPrefix,
		boolean newLine,
		String escapeNL,
		String appName,
		String msgId,
		String excludes,
		String includes,
		String required,
		String format,
		Filter filter,
		Configuration configuration,
		Charset charset,
		String exceptionPattern,
		LoggerFields[] loggerFields,
		boolean advertise
	) {
		return ((SyslogAppender.Builder)((SyslogAppender.Builder)((SyslogAppender.Builder)((SyslogAppender.Builder)((SyslogAppender.Builder)((SyslogAppender.Builder)((SyslogAppender.Builder)((SyslogAppender.Builder)((SyslogAppender.Builder)((SyslogAppender.Builder)((SyslogAppender.Builder)((SyslogAppender.Builder)((SyslogAppender.Builder)newSyslogAppenderBuilder(
																	
																)
																.withHost(host))
															.withPort(port))
														.withProtocol(EnglishEnums.valueOf(Protocol.class, protocolStr)))
													.withSslConfiguration(sslConfiguration))
												.withConnectTimeoutMillis(connectTimeoutMillis))
											.withReconnectDelayMillis(reconnectDelayMillis))
										.withImmediateFail(immediateFail))
									.withName(appName))
								.withImmediateFlush(immediateFlush))
							.withIgnoreExceptions(ignoreExceptions))
						.withFilter(filter))
					.setConfiguration(configuration))
				.withAdvertise(advertise))
			.setFacility(facility)
			.setId(id)
			.setEnterpriseNumber(enterpriseNumber)
			.setIncludeMdc(includeMdc)
			.setMdcId(mdcId)
			.setMdcPrefix(mdcPrefix)
			.setEventPrefix(eventPrefix)
			.setNewLine(newLine)
			.setAppName(appName)
			.setMsgId(msgId)
			.setExcludes(excludes)
			.setIncludeMdc(includeMdc)
			.setRequired(required)
			.setFormat(format)
			.setCharsetName(charset)
			.setExceptionPattern(exceptionPattern)
			.setLoggerFields(loggerFields)
			.build();
	}

	@PluginBuilderFactory
	public static <B extends SyslogAppender.Builder<B>> B newSyslogAppenderBuilder() {
		return new SyslogAppender.Builder<B>().asBuilder();
	}

	public static class Builder<B extends SyslogAppender.Builder<B>>
		extends AbstractBuilder<B>
		implements org.apache.logging.log4j.core.util.Builder<SocketAppender> {
		@PluginBuilderAttribute("facility")
		private Facility facility = Facility.LOCAL0;
		@PluginBuilderAttribute("id")
		private String id;
		@PluginBuilderAttribute("enterpriseNumber")
		private int enterpriseNumber = 18060;
		@PluginBuilderAttribute("includeMdc")
		private boolean includeMdc = true;
		@PluginBuilderAttribute("mdcId")
		private String mdcId;
		@PluginBuilderAttribute("mdcPrefix")
		private String mdcPrefix;
		@PluginBuilderAttribute("eventPrefix")
		private String eventPrefix;
		@PluginBuilderAttribute("newLine")
		private boolean newLine;
		@PluginBuilderAttribute("newLineEscape")
		private String escapeNL;
		@PluginBuilderAttribute("appName")
		private String appName;
		@PluginBuilderAttribute("messageId")
		private String msgId;
		@PluginBuilderAttribute("mdcExcludes")
		private String excludes;
		@PluginBuilderAttribute("mdcIncludes")
		private String includes;
		@PluginBuilderAttribute("mdcRequired")
		private String required;
		@PluginBuilderAttribute("format")
		private String format;
		@PluginBuilderAttribute("charset")
		private Charset charsetName = StandardCharsets.UTF_8;
		@PluginBuilderAttribute("exceptionPattern")
		private String exceptionPattern;
		@PluginElement("LoggerFields")
		private LoggerFields[] loggerFields;

		public SyslogAppender build() {
			Protocol protocol = this.getProtocol();
			SslConfiguration sslConfiguration = this.getSslConfiguration();
			boolean useTlsMessageFormat = sslConfiguration != null || protocol == Protocol.SSL;
			Configuration configuration = this.getConfiguration();
			Layout<? extends Serializable> layout = this.getLayout();
			if (layout == null) {
				layout = (Layout<? extends Serializable>)("RFC5424".equalsIgnoreCase(this.format)
					? Rfc5424Layout.createLayout(
						this.facility,
						this.id,
						this.enterpriseNumber,
						this.includeMdc,
						this.mdcId,
						this.mdcPrefix,
						this.eventPrefix,
						this.newLine,
						this.escapeNL,
						this.appName,
						this.msgId,
						this.excludes,
						this.includes,
						this.required,
						this.exceptionPattern,
						useTlsMessageFormat,
						this.loggerFields,
						configuration
					)
					: ((SyslogLayout.Builder)SyslogLayout.newBuilder()
							.setFacility(this.facility)
							.setIncludeNewLine(this.newLine)
							.setEscapeNL(this.escapeNL)
							.setCharset(this.charsetName))
						.build());
			}

			String name = this.getName();
			if (name == null) {
				SyslogAppender.LOGGER.error("No name provided for SyslogAppender");
				return null;
			} else {
				AbstractSocketManager manager = SocketAppender.createSocketManager(
					name,
					protocol,
					this.getHost(),
					this.getPort(),
					this.getConnectTimeoutMillis(),
					sslConfiguration,
					this.getReconnectDelayMillis(),
					this.getImmediateFail(),
					layout,
					Constants.ENCODER_BYTE_BUFFER_SIZE,
					null
				);
				return new SyslogAppender(
					name, layout, this.getFilter(), this.isIgnoreExceptions(), this.isImmediateFlush(), manager, this.getAdvertise() ? configuration.getAdvertiser() : null
				);
			}
		}

		public Facility getFacility() {
			return this.facility;
		}

		public String getId() {
			return this.id;
		}

		public int getEnterpriseNumber() {
			return this.enterpriseNumber;
		}

		public boolean isIncludeMdc() {
			return this.includeMdc;
		}

		public String getMdcId() {
			return this.mdcId;
		}

		public String getMdcPrefix() {
			return this.mdcPrefix;
		}

		public String getEventPrefix() {
			return this.eventPrefix;
		}

		public boolean isNewLine() {
			return this.newLine;
		}

		public String getEscapeNL() {
			return this.escapeNL;
		}

		public String getAppName() {
			return this.appName;
		}

		public String getMsgId() {
			return this.msgId;
		}

		public String getExcludes() {
			return this.excludes;
		}

		public String getIncludes() {
			return this.includes;
		}

		public String getRequired() {
			return this.required;
		}

		public String getFormat() {
			return this.format;
		}

		public Charset getCharsetName() {
			return this.charsetName;
		}

		public String getExceptionPattern() {
			return this.exceptionPattern;
		}

		public LoggerFields[] getLoggerFields() {
			return this.loggerFields;
		}

		public B setFacility(Facility facility) {
			this.facility = facility;
			return this.asBuilder();
		}

		public B setId(String id) {
			this.id = id;
			return this.asBuilder();
		}

		public B setEnterpriseNumber(int enterpriseNumber) {
			this.enterpriseNumber = enterpriseNumber;
			return this.asBuilder();
		}

		public B setIncludeMdc(boolean includeMdc) {
			this.includeMdc = includeMdc;
			return this.asBuilder();
		}

		public B setMdcId(String mdcId) {
			this.mdcId = mdcId;
			return this.asBuilder();
		}

		public B setMdcPrefix(String mdcPrefix) {
			this.mdcPrefix = mdcPrefix;
			return this.asBuilder();
		}

		public B setEventPrefix(String eventPrefix) {
			this.eventPrefix = eventPrefix;
			return this.asBuilder();
		}

		public B setNewLine(boolean newLine) {
			this.newLine = newLine;
			return this.asBuilder();
		}

		public B setEscapeNL(String escapeNL) {
			this.escapeNL = escapeNL;
			return this.asBuilder();
		}

		public B setAppName(String appName) {
			this.appName = appName;
			return this.asBuilder();
		}

		public B setMsgId(String msgId) {
			this.msgId = msgId;
			return this.asBuilder();
		}

		public B setExcludes(String excludes) {
			this.excludes = excludes;
			return this.asBuilder();
		}

		public B setIncludes(String includes) {
			this.includes = includes;
			return this.asBuilder();
		}

		public B setRequired(String required) {
			this.required = required;
			return this.asBuilder();
		}

		public B setFormat(String format) {
			this.format = format;
			return this.asBuilder();
		}

		public B setCharsetName(Charset charset) {
			this.charsetName = charset;
			return this.asBuilder();
		}

		public B setExceptionPattern(String exceptionPattern) {
			this.exceptionPattern = exceptionPattern;
			return this.asBuilder();
		}

		public B setLoggerFields(LoggerFields[] loggerFields) {
			this.loggerFields = loggerFields;
			return this.asBuilder();
		}
	}
}
