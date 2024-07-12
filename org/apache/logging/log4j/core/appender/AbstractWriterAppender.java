package org.apache.logging.log4j.core.appender;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.StringLayout;

public abstract class AbstractWriterAppender<M extends WriterManager> extends AbstractAppender {
	protected final boolean immediateFlush;
	private final M manager;
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final Lock readLock = this.readWriteLock.readLock();

	protected AbstractWriterAppender(String name, StringLayout layout, Filter filter, boolean ignoreExceptions, boolean immediateFlush, M manager) {
		super(name, filter, layout, ignoreExceptions);
		this.manager = manager;
		this.immediateFlush = immediateFlush;
	}

	@Override
	public void append(LogEvent event) {
		this.readLock.lock();

		try {
			String str = this.getStringLayout().toSerializable(event);
			if (str.length() > 0) {
				this.manager.write(str);
				if (this.immediateFlush || event.isEndOfBatch()) {
					this.manager.flush();
				}
			}
		} catch (AppenderLoggingException var6) {
			this.error("Unable to write " + this.manager.getName() + " for appender " + this.getName() + ": " + var6);
			throw var6;
		} finally {
			this.readLock.unlock();
		}
	}

	public M getManager() {
		return this.manager;
	}

	public StringLayout getStringLayout() {
		return (StringLayout)this.getLayout();
	}

	@Override
	public void start() {
		if (this.getLayout() == null) {
			LOGGER.error("No layout set for the appender named [{}].", this.getName());
		}

		if (this.manager == null) {
			LOGGER.error("No OutputStreamManager set for the appender named [{}].", this.getName());
		}

		super.start();
	}

	@Override
	public boolean stop(long timeout, TimeUnit timeUnit) {
		this.setStopping();
		boolean stopped = super.stop(timeout, timeUnit, false);
		stopped &= this.manager.stop(timeout, timeUnit);
		this.setStopped();
		return stopped;
	}
}
