package org.apache.logging.log4j.core;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.LifeCycle.State;
import org.apache.logging.log4j.status.StatusLogger;

public class AbstractLifeCycle implements LifeCycle2 {
	public static final int DEFAULT_STOP_TIMEOUT = 0;
	public static final TimeUnit DEFAULT_STOP_TIMEUNIT = TimeUnit.MILLISECONDS;
	protected static final org.apache.logging.log4j.Logger LOGGER = StatusLogger.getLogger();
	private volatile State state = State.INITIALIZED;

	protected static org.apache.logging.log4j.Logger getStatusLogger() {
		return LOGGER;
	}

	protected boolean equalsImpl(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (this.getClass() != obj.getClass()) {
			return false;
		} else {
			LifeCycle other = (LifeCycle)obj;
			return this.state == other.getState();
		}
	}

	@Override
	public State getState() {
		return this.state;
	}

	protected int hashCodeImpl() {
		int prime = 31;
		int result = 1;
		return 31 * result + (this.state == null ? 0 : this.state.hashCode());
	}

	public boolean isInitialized() {
		return this.state == State.INITIALIZED;
	}

	@Override
	public boolean isStarted() {
		return this.state == State.STARTED;
	}

	public boolean isStarting() {
		return this.state == State.STARTING;
	}

	@Override
	public boolean isStopped() {
		return this.state == State.STOPPED;
	}

	public boolean isStopping() {
		return this.state == State.STOPPING;
	}

	protected void setStarted() {
		this.setState(State.STARTED);
	}

	protected void setStarting() {
		this.setState(State.STARTING);
	}

	protected void setState(State newState) {
		this.state = newState;
	}

	protected void setStopped() {
		this.setState(State.STOPPED);
	}

	protected void setStopping() {
		this.setState(State.STOPPING);
	}

	@Override
	public void initialize() {
		this.state = State.INITIALIZED;
	}

	@Override
	public void start() {
		this.setStarted();
	}

	@Override
	public void stop() {
		this.stop(0L, DEFAULT_STOP_TIMEUNIT);
	}

	protected boolean stop(Future<?> future) {
		boolean stopped = true;
		if (future != null) {
			if (future.isCancelled() || future.isDone()) {
				return true;
			}

			stopped = future.cancel(true);
		}

		return stopped;
	}

	@Override
	public boolean stop(long timeout, TimeUnit timeUnit) {
		this.state = State.STOPPED;
		return true;
	}
}
