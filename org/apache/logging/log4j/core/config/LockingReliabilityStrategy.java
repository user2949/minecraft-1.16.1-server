package org.apache.logging.log4j.core.config;

import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.Supplier;

public class LockingReliabilityStrategy implements ReliabilityStrategy {
	private final LoggerConfig loggerConfig;
	private final ReadWriteLock reconfigureLock = new ReentrantReadWriteLock();
	private volatile boolean isStopping = false;

	public LockingReliabilityStrategy(LoggerConfig loggerConfig) {
		this.loggerConfig = (LoggerConfig)Objects.requireNonNull(loggerConfig, "loggerConfig was null");
	}

	@Override
	public void log(Supplier<LoggerConfig> reconfigured, String loggerName, String fqcn, Marker marker, Level level, Message data, Throwable t) {
		LoggerConfig config = this.getActiveLoggerConfig(reconfigured);

		try {
			config.log(loggerName, fqcn, marker, level, data, t);
		} finally {
			config.getReliabilityStrategy().afterLogEvent();
		}
	}

	@Override
	public void log(Supplier<LoggerConfig> reconfigured, LogEvent event) {
		LoggerConfig config = this.getActiveLoggerConfig(reconfigured);

		try {
			config.log(event);
		} finally {
			config.getReliabilityStrategy().afterLogEvent();
		}
	}

	@Override
	public LoggerConfig getActiveLoggerConfig(Supplier<LoggerConfig> next) {
		LoggerConfig result = this.loggerConfig;
		if (!this.beforeLogEvent()) {
			result = next.get();
			return result.getReliabilityStrategy().getActiveLoggerConfig(next);
		} else {
			return result;
		}
	}

	private boolean beforeLogEvent() {
		this.reconfigureLock.readLock().lock();
		if (this.isStopping) {
			this.reconfigureLock.readLock().unlock();
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void afterLogEvent() {
		this.reconfigureLock.readLock().unlock();
	}

	@Override
	public void beforeStopAppenders() {
		this.reconfigureLock.writeLock().lock();

		try {
			this.isStopping = true;
		} finally {
			this.reconfigureLock.writeLock().unlock();
		}
	}

	@Override
	public void beforeStopConfiguration(Configuration configuration) {
	}
}
