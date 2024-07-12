package org.apache.logging.log4j.core.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;
import org.apache.logging.log4j.LoggingException;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.impl.MutableLogEvent;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.layout.AbstractStringLayout.Serializer;
import org.apache.logging.log4j.core.util.CyclicBuffer;
import org.apache.logging.log4j.core.util.NameUtil;
import org.apache.logging.log4j.core.util.NetUtils;
import org.apache.logging.log4j.message.ReusableMessage;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.Strings;

public class SmtpManager extends AbstractManager {
	private static final SmtpManager.SMTPManagerFactory FACTORY = new SmtpManager.SMTPManagerFactory();
	private final Session session;
	private final CyclicBuffer<LogEvent> buffer;
	private volatile MimeMessage message;
	private final SmtpManager.FactoryData data;

	private static MimeMessage createMimeMessage(SmtpManager.FactoryData data, Session session, LogEvent appendEvent) throws MessagingException {
		return new MimeMessageBuilder(session)
			.setFrom(data.from)
			.setReplyTo(data.replyto)
			.setRecipients(RecipientType.TO, data.to)
			.setRecipients(RecipientType.CC, data.cc)
			.setRecipients(RecipientType.BCC, data.bcc)
			.setSubject(data.subject.toSerializable(appendEvent))
			.build();
	}

	protected SmtpManager(String name, Session session, MimeMessage message, SmtpManager.FactoryData data) {
		super(null, name);
		this.session = session;
		this.message = message;
		this.data = data;
		this.buffer = new CyclicBuffer<>(LogEvent.class, data.numElements);
	}

	public void add(LogEvent event) {
		if (event instanceof Log4jLogEvent && event.getMessage() instanceof ReusableMessage) {
			((Log4jLogEvent)event).makeMessageImmutable();
		} else if (event instanceof MutableLogEvent) {
			event = ((MutableLogEvent)event).createMemento();
		}

		this.buffer.add(event);
	}

	public static SmtpManager getSmtpManager(
		Configuration config,
		String to,
		String cc,
		String bcc,
		String from,
		String replyTo,
		String subject,
		String protocol,
		String host,
		int port,
		String username,
		String password,
		boolean isDebug,
		String filterName,
		int numElements
	) {
		if (Strings.isEmpty(protocol)) {
			protocol = "smtp";
		}

		StringBuilder sb = new StringBuilder();
		if (to != null) {
			sb.append(to);
		}

		sb.append(':');
		if (cc != null) {
			sb.append(cc);
		}

		sb.append(':');
		if (bcc != null) {
			sb.append(bcc);
		}

		sb.append(':');
		if (from != null) {
			sb.append(from);
		}

		sb.append(':');
		if (replyTo != null) {
			sb.append(replyTo);
		}

		sb.append(':');
		if (subject != null) {
			sb.append(subject);
		}

		sb.append(':');
		sb.append(protocol).append(':').append(host).append(':').append("port").append(':');
		if (username != null) {
			sb.append(username);
		}

		sb.append(':');
		if (password != null) {
			sb.append(password);
		}

		sb.append(isDebug ? ":debug:" : "::");
		sb.append(filterName);
		String name = "SMTP:" + NameUtil.md5(sb.toString());
		Serializer subjectSerializer = PatternLayout.newSerializerBuilder().setConfiguration(config).setPattern(subject).build();
		return getManager(
			name, FACTORY, new SmtpManager.FactoryData(to, cc, bcc, from, replyTo, subjectSerializer, protocol, host, port, username, password, isDebug, numElements)
		);
	}

	public void sendEvents(Layout<?> layout, LogEvent appendEvent) {
		if (this.message == null) {
			this.connect(appendEvent);
		}

		try {
			LogEvent[] priorEvents = this.buffer.removeAll();
			byte[] rawBytes = this.formatContentToBytes(priorEvents, appendEvent, layout);
			String contentType = layout.getContentType();
			String encoding = this.getEncoding(rawBytes, contentType);
			byte[] encodedBytes = this.encodeContentToBytes(rawBytes, encoding);
			InternetHeaders headers = this.getHeaders(contentType, encoding);
			MimeMultipart mp = this.getMimeMultipart(encodedBytes, headers);
			this.sendMultipartMessage(this.message, mp);
		} catch (IOException | RuntimeException | MessagingException var10) {
			this.logError("Caught exception while sending e-mail notification.", var10);
			throw new LoggingException("Error occurred while sending email", var10);
		}
	}

	protected byte[] formatContentToBytes(LogEvent[] priorEvents, LogEvent appendEvent, Layout<?> layout) throws IOException {
		ByteArrayOutputStream raw = new ByteArrayOutputStream();
		this.writeContent(priorEvents, appendEvent, layout, raw);
		return raw.toByteArray();
	}

	private void writeContent(LogEvent[] priorEvents, LogEvent appendEvent, Layout<?> layout, ByteArrayOutputStream out) throws IOException {
		this.writeHeader(layout, out);
		this.writeBuffer(priorEvents, appendEvent, layout, out);
		this.writeFooter(layout, out);
	}

	protected void writeHeader(Layout<?> layout, OutputStream out) throws IOException {
		byte[] header = layout.getHeader();
		if (header != null) {
			out.write(header);
		}
	}

	protected void writeBuffer(LogEvent[] priorEvents, LogEvent appendEvent, Layout<?> layout, OutputStream out) throws IOException {
		for (LogEvent priorEvent : priorEvents) {
			byte[] bytes = layout.toByteArray(priorEvent);
			out.write(bytes);
		}

		byte[] bytes = layout.toByteArray(appendEvent);
		out.write(bytes);
	}

	protected void writeFooter(Layout<?> layout, OutputStream out) throws IOException {
		byte[] footer = layout.getFooter();
		if (footer != null) {
			out.write(footer);
		}
	}

	protected String getEncoding(byte[] rawBytes, String contentType) {
		DataSource dataSource = new ByteArrayDataSource(rawBytes, contentType);
		return MimeUtility.getEncoding(dataSource);
	}

	protected byte[] encodeContentToBytes(byte[] rawBytes, String encoding) throws MessagingException, IOException {
		ByteArrayOutputStream encoded = new ByteArrayOutputStream();
		this.encodeContent(rawBytes, encoding, encoded);
		return encoded.toByteArray();
	}

	protected void encodeContent(byte[] bytes, String encoding, ByteArrayOutputStream out) throws MessagingException, IOException {
		OutputStream encoder = MimeUtility.encode(out, encoding);
		Throwable var5 = null;

		try {
			encoder.write(bytes);
		} catch (Throwable var14) {
			var5 = var14;
			throw var14;
		} finally {
			if (encoder != null) {
				if (var5 != null) {
					try {
						encoder.close();
					} catch (Throwable var13) {
						var5.addSuppressed(var13);
					}
				} else {
					encoder.close();
				}
			}
		}
	}

	protected InternetHeaders getHeaders(String contentType, String encoding) {
		InternetHeaders headers = new InternetHeaders();
		headers.setHeader("Content-Type", contentType + "; charset=UTF-8");
		headers.setHeader("Content-Transfer-Encoding", encoding);
		return headers;
	}

	protected MimeMultipart getMimeMultipart(byte[] encodedBytes, InternetHeaders headers) throws MessagingException {
		MimeMultipart mp = new MimeMultipart();
		MimeBodyPart part = new MimeBodyPart(headers, encodedBytes);
		mp.addBodyPart(part);
		return mp;
	}

	protected void sendMultipartMessage(MimeMessage msg, MimeMultipart mp) throws MessagingException {
		synchronized (msg) {
			msg.setContent(mp);
			msg.setSentDate(new Date());
			Transport.send(msg);
		}
	}

	private synchronized void connect(LogEvent appendEvent) {
		if (this.message == null) {
			try {
				this.message = createMimeMessage(this.data, this.session, appendEvent);
			} catch (MessagingException var3) {
				this.logError("Could not set SmtpAppender message options", var3);
				this.message = null;
			}
		}
	}

	private static class FactoryData {
		private final String to;
		private final String cc;
		private final String bcc;
		private final String from;
		private final String replyto;
		private final Serializer subject;
		private final String protocol;
		private final String host;
		private final int port;
		private final String username;
		private final String password;
		private final boolean isDebug;
		private final int numElements;

		public FactoryData(
			String to,
			String cc,
			String bcc,
			String from,
			String replyTo,
			Serializer subjectSerializer,
			String protocol,
			String host,
			int port,
			String username,
			String password,
			boolean isDebug,
			int numElements
		) {
			this.to = to;
			this.cc = cc;
			this.bcc = bcc;
			this.from = from;
			this.replyto = replyTo;
			this.subject = subjectSerializer;
			this.protocol = protocol;
			this.host = host;
			this.port = port;
			this.username = username;
			this.password = password;
			this.isDebug = isDebug;
			this.numElements = numElements;
		}
	}

	private static class SMTPManagerFactory implements ManagerFactory<SmtpManager, SmtpManager.FactoryData> {
		private SMTPManagerFactory() {
		}

		public SmtpManager createManager(String name, SmtpManager.FactoryData data) {
			String prefix = "mail." + data.protocol;
			Properties properties = PropertiesUtil.getSystemProperties();
			properties.put("mail.transport.protocol", data.protocol);
			if (properties.getProperty("mail.host") == null) {
				properties.put("mail.host", NetUtils.getLocalHostname());
			}

			if (null != data.host) {
				properties.put(prefix + ".host", data.host);
			}

			if (data.port > 0) {
				properties.put(prefix + ".port", String.valueOf(data.port));
			}

			Authenticator authenticator = this.buildAuthenticator(data.username, data.password);
			if (null != authenticator) {
				properties.put(prefix + ".auth", "true");
			}

			Session session = Session.getInstance(properties, authenticator);
			session.setProtocolForAddress("rfc822", data.protocol);
			session.setDebug(data.isDebug);
			return new SmtpManager(name, session, null, data);
		}

		private Authenticator buildAuthenticator(String username, String password) {
			return null != password && null != username ? new Authenticator() {
				private final PasswordAuthentication passwordAuthentication = new PasswordAuthentication(username, password);

				protected PasswordAuthentication getPasswordAuthentication() {
					return this.passwordAuthentication;
				}
			} : null;
		}
	}
}
