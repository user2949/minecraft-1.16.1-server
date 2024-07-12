package org.apache.logging.log4j.core.config;

import java.util.Objects;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.filter.AbstractFilterable;
import org.apache.logging.log4j.core.filter.Filterable;
import org.apache.logging.log4j.util.PerformanceSensitive;

public class AppenderControl extends AbstractFilterable {
	private final ThreadLocal<AppenderControl> recursive = new ThreadLocal();
	private final Appender appender;
	private final Level level;
	private final int intLevel;
	private final String appenderName;

	public AppenderControl(Appender appender, Level level, Filter filter) {
		super(filter);
		this.appender = appender;
		this.appenderName = appender.getName();
		this.level = level;
		this.intLevel = level == null ? Level.ALL.intLevel() : level.intLevel();
		this.start();
	}

	public String getAppenderName() {
		return this.appenderName;
	}

	public Appender getAppender() {
		return this.appender;
	}

	public void callAppender(LogEvent event) {
		if (!this.shouldSkip(event)) {
			this.callAppenderPreventRecursion(event);
		}
	}

	private boolean shouldSkip(LogEvent event) {
		return this.isFilteredByAppenderControl(event) || this.isFilteredByLevel(event) || this.isRecursiveCall();
	}

	@PerformanceSensitive
	private boolean isFilteredByAppenderControl(LogEvent event) {
		Filter filter = this.getFilter();
		return filter != null && Result.DENY == filter.filter(event);
	}

	@PerformanceSensitive
	private boolean isFilteredByLevel(LogEvent event) {
		return this.level != null && this.intLevel < event.getLevel().intLevel();
	}

	@PerformanceSensitive
	private boolean isRecursiveCall() {
		if (this.recursive.get() != null) {
			this.appenderErrorHandlerMessage("Recursive call to appender ");
			return true;
		} else {
			return false;
		}
	}

	private String appenderErrorHandlerMessage(String prefix) {
		String result = this.createErrorMsg(prefix);
		this.appender.getHandler().error(result);
		return result;
	}

	private void callAppenderPreventRecursion(LogEvent event) {
		try {
			this.recursive.set(this);
			this.callAppender0(event);
		} finally {
			this.recursive.set(null);
		}
	}

	private void callAppender0(LogEvent event) {
		this.ensureAppenderStarted();
		if (!this.isFilteredByAppender(event)) {
			this.tryCallAppender(event);
		}
	}

	private void ensureAppenderStarted() {
		if (!this.appender.isStarted()) {
			this.handleError("Attempted to append to non-started appender ");
		}
	}

	private void handleError(String prefix) {
		String msg = this.appenderErrorHandlerMessage(prefix);
		if (!this.appender.ignoreExceptions()) {
			throw new AppenderLoggingException(msg);
		}
	}

	private String createErrorMsg(String prefix) {
		return prefix + this.appender.getName();
	}

	private boolean isFilteredByAppender(LogEvent event) {
		return this.appender instanceof Filterable && ((Filterable)this.appender).isFiltered(event);
	}

	private void tryCallAppender(LogEvent event) {
		try {
			this.appender.append(event);
		} catch (RuntimeException var3) {
			this.handleAppenderError(var3);
		} catch (Exception var4) {
			this.handleAppenderError(new AppenderLoggingException(var4));
		}
	}

	private void handleAppenderError(RuntimeException ex) {
		this.appender.getHandler().error(this.createErrorMsg("An exception occurred processing Appender "), ex);
		if (!this.appender.ignoreExceptions()) {
			throw ex;
		}
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (!(obj instanceof AppenderControl)) {
			return false;
		} else {
			AppenderControl other = (AppenderControl)obj;
			return Objects.equals(this.appenderName, other.appenderName);
		}
	}

	public int hashCode() {
		return this.appenderName.hashCode();
	}

	public String toString() {
		return super.toString()
			+ "[appender="
			+ this.appender
			+ ", appenderName="
			+ this.appenderName
			+ ", level="
			+ this.level
			+ ", intLevel="
			+ this.intLevel
			+ ", recursive="
			+ this.recursive
			+ ", filter="
			+ this.getFilter()
			+ "]";
	}
}
