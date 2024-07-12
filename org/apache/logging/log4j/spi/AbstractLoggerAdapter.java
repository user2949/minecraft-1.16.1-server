package org.apache.logging.log4j.spi;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.util.LoaderUtil;

public abstract class AbstractLoggerAdapter<L> implements LoggerAdapter<L> {
	protected final Map<LoggerContext, ConcurrentMap<String, L>> registry = new WeakHashMap();
	private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

	@Override
	public L getLogger(String name) {
		LoggerContext context = this.getContext();
		ConcurrentMap<String, L> loggers = this.getLoggersInContext(context);
		L logger = (L)loggers.get(name);
		if (logger != null) {
			return logger;
		} else {
			loggers.putIfAbsent(name, this.newLogger(name, context));
			return (L)loggers.get(name);
		}
	}

	public ConcurrentMap<String, L> getLoggersInContext(LoggerContext context) {
		this.lock.readLock().lock();

		ConcurrentMap<String, L> loggers;
		try {
			loggers = (ConcurrentMap<String, L>)this.registry.get(context);
		} finally {
			this.lock.readLock().unlock();
		}

		if (loggers != null) {
			return loggers;
		} else {
			this.lock.writeLock().lock();

			Object var3;
			try {
				ConcurrentMap<String, L> var11 = (ConcurrentMap)this.registry.get(context);
				if (var11 == null) {
					var11 = new ConcurrentHashMap();
					this.registry.put(context, var11);
				}

				var3 = var11;
			} finally {
				this.lock.writeLock().unlock();
			}

			return (ConcurrentMap<String, L>)var3;
		}
	}

	protected abstract L newLogger(String string, LoggerContext loggerContext);

	protected abstract LoggerContext getContext();

	protected LoggerContext getContext(Class<?> callerClass) {
		ClassLoader cl = null;
		if (callerClass != null) {
			cl = callerClass.getClassLoader();
		}

		if (cl == null) {
			cl = LoaderUtil.getThreadContextClassLoader();
		}

		return LogManager.getContext(cl, false);
	}

	public void close() {
		this.lock.writeLock().lock();

		try {
			this.registry.clear();
		} finally {
			this.lock.writeLock().unlock();
		}
	}
}
