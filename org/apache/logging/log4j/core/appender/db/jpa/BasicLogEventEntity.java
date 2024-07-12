package org.apache.logging.log4j.core.appender.db.jpa;

import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext.ContextStack;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.db.jpa.converter.ContextMapAttributeConverter;
import org.apache.logging.log4j.core.appender.db.jpa.converter.ContextStackAttributeConverter;
import org.apache.logging.log4j.core.appender.db.jpa.converter.LevelAttributeConverter;
import org.apache.logging.log4j.core.appender.db.jpa.converter.MarkerAttributeConverter;
import org.apache.logging.log4j.core.appender.db.jpa.converter.MessageAttributeConverter;
import org.apache.logging.log4j.core.appender.db.jpa.converter.StackTraceElementAttributeConverter;
import org.apache.logging.log4j.core.appender.db.jpa.converter.ThrowableAttributeConverter;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.apache.logging.log4j.message.Message;

@MappedSuperclass
public abstract class BasicLogEventEntity extends AbstractLogEventWrapperEntity {
	private static final long serialVersionUID = 1L;

	public BasicLogEventEntity() {
	}

	public BasicLogEventEntity(LogEvent wrappedEvent) {
		super(wrappedEvent);
	}

	@Convert(
		converter = LevelAttributeConverter.class
	)
	@Override
	public Level getLevel() {
		return this.getWrappedEvent().getLevel();
	}

	@Basic
	@Override
	public String getLoggerName() {
		return this.getWrappedEvent().getLoggerName();
	}

	@Convert(
		converter = StackTraceElementAttributeConverter.class
	)
	@Override
	public StackTraceElement getSource() {
		return this.getWrappedEvent().getSource();
	}

	@Convert(
		converter = MessageAttributeConverter.class
	)
	@Override
	public Message getMessage() {
		return this.getWrappedEvent().getMessage();
	}

	@Convert(
		converter = MarkerAttributeConverter.class
	)
	@Override
	public Marker getMarker() {
		return this.getWrappedEvent().getMarker();
	}

	@Basic
	@Override
	public long getThreadId() {
		return this.getWrappedEvent().getThreadId();
	}

	@Basic
	@Override
	public int getThreadPriority() {
		return this.getWrappedEvent().getThreadPriority();
	}

	@Basic
	@Override
	public String getThreadName() {
		return this.getWrappedEvent().getThreadName();
	}

	@Basic
	@Override
	public long getTimeMillis() {
		return this.getWrappedEvent().getTimeMillis();
	}

	@Basic
	@Override
	public long getNanoTime() {
		return this.getWrappedEvent().getNanoTime();
	}

	@Convert(
		converter = ThrowableAttributeConverter.class
	)
	@Override
	public Throwable getThrown() {
		return this.getWrappedEvent().getThrown();
	}

	@Transient
	@Override
	public ThrowableProxy getThrownProxy() {
		return this.getWrappedEvent().getThrownProxy();
	}

	@Convert(
		converter = ContextMapAttributeConverter.class
	)
	@Override
	public Map<String, String> getContextMap() {
		return this.getWrappedEvent().getContextMap();
	}

	@Convert(
		converter = ContextStackAttributeConverter.class
	)
	@Override
	public ContextStack getContextStack() {
		return this.getWrappedEvent().getContextStack();
	}

	@Basic
	@Override
	public String getLoggerFqcn() {
		return this.getWrappedEvent().getLoggerFqcn();
	}
}
