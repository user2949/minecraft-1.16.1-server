package io.netty.util.internal.logging;

import io.netty.util.internal.StringUtil;
import java.io.ObjectStreamException;
import java.io.Serializable;

public abstract class AbstractInternalLogger implements InternalLogger, Serializable {
	private static final long serialVersionUID = -6382972526573193470L;
	private static final String EXCEPTION_MESSAGE = "Unexpected exception:";
	private final String name;

	protected AbstractInternalLogger(String name) {
		if (name == null) {
			throw new NullPointerException("name");
		} else {
			this.name = name;
		}
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public boolean isEnabled(InternalLogLevel level) {
		switch (level) {
			case TRACE:
				return this.isTraceEnabled();
			case DEBUG:
				return this.isDebugEnabled();
			case INFO:
				return this.isInfoEnabled();
			case WARN:
				return this.isWarnEnabled();
			case ERROR:
				return this.isErrorEnabled();
			default:
				throw new Error();
		}
	}

	@Override
	public void trace(Throwable t) {
		this.trace("Unexpected exception:", t);
	}

	@Override
	public void debug(Throwable t) {
		this.debug("Unexpected exception:", t);
	}

	@Override
	public void info(Throwable t) {
		this.info("Unexpected exception:", t);
	}

	@Override
	public void warn(Throwable t) {
		this.warn("Unexpected exception:", t);
	}

	@Override
	public void error(Throwable t) {
		this.error("Unexpected exception:", t);
	}

	@Override
	public void log(InternalLogLevel level, String msg, Throwable cause) {
		switch (level) {
			case TRACE:
				this.trace(msg, cause);
				break;
			case DEBUG:
				this.debug(msg, cause);
				break;
			case INFO:
				this.info(msg, cause);
				break;
			case WARN:
				this.warn(msg, cause);
				break;
			case ERROR:
				this.error(msg, cause);
				break;
			default:
				throw new Error();
		}
	}

	@Override
	public void log(InternalLogLevel level, Throwable cause) {
		switch (level) {
			case TRACE:
				this.trace(cause);
				break;
			case DEBUG:
				this.debug(cause);
				break;
			case INFO:
				this.info(cause);
				break;
			case WARN:
				this.warn(cause);
				break;
			case ERROR:
				this.error(cause);
				break;
			default:
				throw new Error();
		}
	}

	@Override
	public void log(InternalLogLevel level, String msg) {
		switch (level) {
			case TRACE:
				this.trace(msg);
				break;
			case DEBUG:
				this.debug(msg);
				break;
			case INFO:
				this.info(msg);
				break;
			case WARN:
				this.warn(msg);
				break;
			case ERROR:
				this.error(msg);
				break;
			default:
				throw new Error();
		}
	}

	@Override
	public void log(InternalLogLevel level, String format, Object arg) {
		switch (level) {
			case TRACE:
				this.trace(format, arg);
				break;
			case DEBUG:
				this.debug(format, arg);
				break;
			case INFO:
				this.info(format, arg);
				break;
			case WARN:
				this.warn(format, arg);
				break;
			case ERROR:
				this.error(format, arg);
				break;
			default:
				throw new Error();
		}
	}

	@Override
	public void log(InternalLogLevel level, String format, Object argA, Object argB) {
		switch (level) {
			case TRACE:
				this.trace(format, argA, argB);
				break;
			case DEBUG:
				this.debug(format, argA, argB);
				break;
			case INFO:
				this.info(format, argA, argB);
				break;
			case WARN:
				this.warn(format, argA, argB);
				break;
			case ERROR:
				this.error(format, argA, argB);
				break;
			default:
				throw new Error();
		}
	}

	@Override
	public void log(InternalLogLevel level, String format, Object... arguments) {
		switch (level) {
			case TRACE:
				this.trace(format, arguments);
				break;
			case DEBUG:
				this.debug(format, arguments);
				break;
			case INFO:
				this.info(format, arguments);
				break;
			case WARN:
				this.warn(format, arguments);
				break;
			case ERROR:
				this.error(format, arguments);
				break;
			default:
				throw new Error();
		}
	}

	protected Object readResolve() throws ObjectStreamException {
		return InternalLoggerFactory.getInstance(this.name());
	}

	public String toString() {
		return StringUtil.simpleClassName(this) + '(' + this.name() + ')';
	}
}
