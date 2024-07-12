package io.netty.util.internal.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.ExtendedLoggerWrapper;

class Log4J2Logger extends ExtendedLoggerWrapper implements InternalLogger {
	private static final long serialVersionUID = 5485418394879791397L;
	private static final String EXCEPTION_MESSAGE = "Unexpected exception:";

	Log4J2Logger(Logger logger) {
		super((ExtendedLogger)logger, logger.getName(), logger.getMessageFactory());
	}

	@Override
	public String name() {
		return this.getName();
	}

	@Override
	public void trace(Throwable t) {
		this.log(Level.TRACE, "Unexpected exception:", t);
	}

	@Override
	public void debug(Throwable t) {
		this.log(Level.DEBUG, "Unexpected exception:", t);
	}

	@Override
	public void info(Throwable t) {
		this.log(Level.INFO, "Unexpected exception:", t);
	}

	@Override
	public void warn(Throwable t) {
		this.log(Level.WARN, "Unexpected exception:", t);
	}

	@Override
	public void error(Throwable t) {
		this.log(Level.ERROR, "Unexpected exception:", t);
	}

	@Override
	public boolean isEnabled(InternalLogLevel level) {
		return this.isEnabled(this.toLevel(level));
	}

	@Override
	public void log(InternalLogLevel level, String msg) {
		this.log(this.toLevel(level), msg);
	}

	@Override
	public void log(InternalLogLevel level, String format, Object arg) {
		this.log(this.toLevel(level), format, arg);
	}

	@Override
	public void log(InternalLogLevel level, String format, Object argA, Object argB) {
		this.log(this.toLevel(level), format, argA, argB);
	}

	@Override
	public void log(InternalLogLevel level, String format, Object... arguments) {
		this.log(this.toLevel(level), format, arguments);
	}

	@Override
	public void log(InternalLogLevel level, String msg, Throwable t) {
		this.log(this.toLevel(level), msg, t);
	}

	@Override
	public void log(InternalLogLevel level, Throwable t) {
		this.log(this.toLevel(level), "Unexpected exception:", t);
	}

	protected Level toLevel(InternalLogLevel level) {
		switch (level) {
			case INFO:
				return Level.INFO;
			case DEBUG:
				return Level.DEBUG;
			case WARN:
				return Level.WARN;
			case ERROR:
				return Level.ERROR;
			case TRACE:
				return Level.TRACE;
			default:
				throw new Error();
		}
	}
}
