package org.apache.logging.log4j.core.appender.db;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.logging.log4j.LoggingException;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;

public abstract class AbstractDatabaseAppender<T extends AbstractDatabaseManager> extends AbstractAppender {
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock readLock = this.lock.readLock();
	private final Lock writeLock = this.lock.writeLock();
	private T manager;

	protected AbstractDatabaseAppender(String name, Filter filter, boolean ignoreExceptions, T manager) {
		super(name, filter, null, ignoreExceptions);
		this.manager = manager;
	}

	@Override
	public final Layout<LogEvent> getLayout() {
		return null;
	}

	public final T getManager() {
		return this.manager;
	}

	@Override
	public final void start() {
		if (this.getManager() == null) {
			LOGGER.error("No AbstractDatabaseManager set for the appender named [{}].", this.getName());
		}

		super.start();
		if (this.getManager() != null) {
			this.getManager().startup();
		}
	}

	@Override
	public boolean stop(long timeout, TimeUnit timeUnit) {
		this.setStopping();
		boolean stopped = super.stop(timeout, timeUnit, false);
		if (this.getManager() != null) {
			stopped &= this.getManager().stop(timeout, timeUnit);
		}

		this.setStopped();
		return stopped;
	}

	@Override
	public final void append(LogEvent event) {
		this.readLock.lock();

		try {
			this.getManager().write(event);
		} catch (LoggingException var7) {
			LOGGER.error("Unable to write to database [{}] for appender [{}].", this.getManager().getName(), this.getName(), var7);
			throw var7;
		} catch (Exception var8) {
			LOGGER.error("Unable to write to database [{}] for appender [{}].", this.getManager().getName(), this.getName(), var8);
			throw new AppenderLoggingException("Unable to write to database in appender: " + var8.getMessage(), var8);
		} finally {
			this.readLock.unlock();
		}
	}

	protected final void replaceManager(T manager) {
		this.writeLock.lock();

		try {
			T old = this.getManager();
			if (!manager.isRunning()) {
				manager.startup();
			}

			this.manager = manager;
			old.close();
		} finally {
			this.writeLock.unlock();
		}
	}
}
