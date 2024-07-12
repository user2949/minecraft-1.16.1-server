package org.apache.logging.log4j.core.appender.db;

import java.io.Flushable;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.appender.ManagerFactory;

public abstract class AbstractDatabaseManager extends AbstractManager implements Flushable {
	private final ArrayList<LogEvent> buffer;
	private final int bufferSize;
	private boolean running = false;

	protected AbstractDatabaseManager(String name, int bufferSize) {
		super(null, name);
		this.bufferSize = bufferSize;
		this.buffer = new ArrayList(bufferSize + 1);
	}

	protected abstract void startupInternal() throws Exception;

	public final synchronized void startup() {
		if (!this.isRunning()) {
			try {
				this.startupInternal();
				this.running = true;
			} catch (Exception var2) {
				this.logError("Could not perform database startup operations", var2);
			}
		}
	}

	protected abstract boolean shutdownInternal() throws Exception;

	public final synchronized boolean shutdown() {
		boolean closed = true;
		this.flush();
		if (this.isRunning()) {
			try {
				closed &= this.shutdownInternal();
			} catch (Exception var6) {
				this.logWarn("Caught exception while performing database shutdown operations", var6);
				closed = false;
			} finally {
				this.running = false;
			}
		}

		return closed;
	}

	public final boolean isRunning() {
		return this.running;
	}

	protected abstract void connectAndStart();

	protected abstract void writeInternal(LogEvent logEvent);

	protected abstract boolean commitAndClose();

	public final synchronized void flush() {
		if (this.isRunning() && this.buffer.size() > 0) {
			this.connectAndStart();

			try {
				for (LogEvent event : this.buffer) {
					this.writeInternal(event);
				}
			} finally {
				this.commitAndClose();
				this.buffer.clear();
			}
		}
	}

	public final synchronized void write(LogEvent event) {
		if (this.bufferSize > 0) {
			this.buffer.add(event);
			if (this.buffer.size() >= this.bufferSize || event.isEndOfBatch()) {
				this.flush();
			}
		} else {
			this.connectAndStart();

			try {
				this.writeInternal(event);
			} finally {
				this.commitAndClose();
			}
		}
	}

	@Override
	public final boolean releaseSub(long timeout, TimeUnit timeUnit) {
		return this.shutdown();
	}

	public final String toString() {
		return this.getName();
	}

	protected static <M extends AbstractDatabaseManager, T extends AbstractDatabaseManager.AbstractFactoryData> M getManager(
		String name, T data, ManagerFactory<M, T> factory
	) {
		return AbstractManager.getManager(name, factory, data);
	}

	protected abstract static class AbstractFactoryData {
		private final int bufferSize;

		protected AbstractFactoryData(int bufferSize) {
			this.bufferSize = bufferSize;
		}

		public int getBufferSize() {
			return this.bufferSize;
		}
	}
}
