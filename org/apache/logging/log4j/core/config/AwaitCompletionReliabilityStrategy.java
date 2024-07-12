package org.apache.logging.log4j.core.config;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.Supplier;

public class AwaitCompletionReliabilityStrategy implements ReliabilityStrategy {
	private static final int MAX_RETRIES = 3;
	private final AtomicInteger counter = new AtomicInteger();
	private final AtomicBoolean shutdown = new AtomicBoolean(false);
	private final Lock shutdownLock = new ReentrantLock();
	private final Condition noLogEvents = this.shutdownLock.newCondition();
	private final LoggerConfig loggerConfig;

	public AwaitCompletionReliabilityStrategy(LoggerConfig loggerConfig) {
		this.loggerConfig = (LoggerConfig)Objects.requireNonNull(loggerConfig, "loggerConfig is null");
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
		return this.counter.incrementAndGet() > 0;
	}

	@Override
	public void afterLogEvent() {
		if (this.counter.decrementAndGet() == 0 && this.shutdown.get()) {
			this.signalCompletionIfShutdown();
		}
	}

	private void signalCompletionIfShutdown() {
		Lock lock = this.shutdownLock;
		lock.lock();

		try {
			this.noLogEvents.signalAll();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void beforeStopAppenders() {
		this.waitForCompletion();
	}

	private void waitForCompletion() {
		this.shutdownLock.lock();

		try {
			if (this.shutdown.compareAndSet(false, true)) {
				int retries = 0;

				while (!this.counter.compareAndSet(0, Integer.MIN_VALUE)) {
					if (this.counter.get() < 0) {
						return;
					}

					try {
						this.noLogEvents.await((long)(retries + 1), TimeUnit.SECONDS);
					} catch (InterruptedException var6) {
						if (++retries > 3) {
							return;
						}
					}
				}
			}
		} finally {
			this.shutdownLock.unlock();
		}
	}

	@Override
	public void beforeStopConfiguration(Configuration configuration) {
	}
}
