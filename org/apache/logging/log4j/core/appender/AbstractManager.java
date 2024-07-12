package org.apache.logging.log4j.core.appender;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.status.StatusLogger;

public abstract class AbstractManager implements AutoCloseable {
	protected static final Logger LOGGER = StatusLogger.getLogger();
	private static final Map<String, AbstractManager> MAP = new HashMap();
	private static final Lock LOCK = new ReentrantLock();
	protected int count;
	private final String name;
	private final LoggerContext loggerContext;

	protected AbstractManager(LoggerContext loggerContext, String name) {
		this.loggerContext = loggerContext;
		this.name = name;
		LOGGER.debug("Starting {} {}", this.getClass().getSimpleName(), name);
	}

	public void close() {
		this.stop(0L, AbstractLifeCycle.DEFAULT_STOP_TIMEUNIT);
	}

	public boolean stop(long timeout, TimeUnit timeUnit) {
		boolean stopped = true;
		LOCK.lock();

		try {
			this.count--;
			if (this.count <= 0) {
				MAP.remove(this.name);
				LOGGER.debug("Shutting down {} {}", this.getClass().getSimpleName(), this.getName());
				stopped = this.releaseSub(timeout, timeUnit);
				LOGGER.debug("Shut down {} {}, all resources released: {}", this.getClass().getSimpleName(), this.getName(), stopped);
			}
		} finally {
			LOCK.unlock();
		}

		return stopped;
	}

	public static <M extends AbstractManager, T> M getManager(String name, ManagerFactory<M, T> factory, T data) {
		LOCK.lock();

		AbstractManager var4;
		try {
			M manager = (M)MAP.get(name);
			if (manager == null) {
				manager = (M)factory.createManager(name, data);
				if (manager == null) {
					throw new IllegalStateException("ManagerFactory [" + factory + "] unable to create manager for [" + name + "] with data [" + data + "]");
				}

				MAP.put(name, manager);
			} else {
				manager.updateData(data);
			}

			manager.count++;
			var4 = manager;
		} finally {
			LOCK.unlock();
		}

		return (M)var4;
	}

	public void updateData(Object data) {
	}

	public static boolean hasManager(String name) {
		LOCK.lock();

		boolean var1;
		try {
			var1 = MAP.containsKey(name);
		} finally {
			LOCK.unlock();
		}

		return var1;
	}

	protected boolean releaseSub(long timeout, TimeUnit timeUnit) {
		return true;
	}

	protected int getCount() {
		return this.count;
	}

	public LoggerContext getLoggerContext() {
		return this.loggerContext;
	}

	@Deprecated
	public void release() {
		this.close();
	}

	public String getName() {
		return this.name;
	}

	public Map<String, String> getContentFormat() {
		return new HashMap();
	}

	protected void log(Level level, String message, Throwable throwable) {
		Message m = LOGGER.<MessageFactory>getMessageFactory().newMessage("{} {} {}: {}", this.getClass().getSimpleName(), this.getName(), message, throwable);
		LOGGER.log(level, m, throwable);
	}

	protected void logDebug(String message, Throwable throwable) {
		this.log(Level.DEBUG, message, throwable);
	}

	protected void logError(String message, Throwable throwable) {
		this.log(Level.ERROR, message, throwable);
	}

	protected void logWarn(String message, Throwable throwable) {
		this.log(Level.WARN, message, throwable);
	}
}
