package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListenerCallQueue.Callback;
import com.google.common.util.concurrent.Monitor.Guard;
import com.google.common.util.concurrent.Service.Listener;
import com.google.common.util.concurrent.Service.State;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.Immutable;

@Beta
@GwtIncompatible
public abstract class AbstractService implements Service {
	private static final Callback<Listener> STARTING_CALLBACK = new Callback<Listener>("starting()") {
		void call(Listener listener) {
			listener.starting();
		}
	};
	private static final Callback<Listener> RUNNING_CALLBACK = new Callback<Listener>("running()") {
		void call(Listener listener) {
			listener.running();
		}
	};
	private static final Callback<Listener> STOPPING_FROM_STARTING_CALLBACK = stoppingCallback(State.STARTING);
	private static final Callback<Listener> STOPPING_FROM_RUNNING_CALLBACK = stoppingCallback(State.RUNNING);
	private static final Callback<Listener> TERMINATED_FROM_NEW_CALLBACK = terminatedCallback(State.NEW);
	private static final Callback<Listener> TERMINATED_FROM_RUNNING_CALLBACK = terminatedCallback(State.RUNNING);
	private static final Callback<Listener> TERMINATED_FROM_STOPPING_CALLBACK = terminatedCallback(State.STOPPING);
	private final Monitor monitor = new Monitor();
	private final Guard isStartable = new AbstractService.IsStartableGuard();
	private final Guard isStoppable = new AbstractService.IsStoppableGuard();
	private final Guard hasReachedRunning = new AbstractService.HasReachedRunningGuard();
	private final Guard isStopped = new AbstractService.IsStoppedGuard();
	@GuardedBy("monitor")
	private final List<ListenerCallQueue<Listener>> listeners = Collections.synchronizedList(new ArrayList());
	@GuardedBy("monitor")
	private volatile AbstractService.StateSnapshot snapshot = new AbstractService.StateSnapshot(State.NEW);

	private static Callback<Listener> terminatedCallback(State from) {
		return new Callback<Listener>("terminated({from = " + from + "})") {
			void call(Listener listener) {
				listener.terminated(from);
			}
		};
	}

	private static Callback<Listener> stoppingCallback(State from) {
		return new Callback<Listener>("stopping({from = " + from + "})") {
			void call(Listener listener) {
				listener.stopping(from);
			}
		};
	}

	protected AbstractService() {
	}

	protected abstract void doStart();

	protected abstract void doStop();

	@CanIgnoreReturnValue
	@Override
	public final Service startAsync() {
		if (this.monitor.enterIf(this.isStartable)) {
			try {
				this.snapshot = new AbstractService.StateSnapshot(State.STARTING);
				this.starting();
				this.doStart();
			} catch (Throwable var5) {
				this.notifyFailed(var5);
			} finally {
				this.monitor.leave();
				this.executeListeners();
			}

			return this;
		} else {
			throw new IllegalStateException("Service " + this + " has already been started");
		}
	}

	@CanIgnoreReturnValue
	@Override
	public final Service stopAsync() {
		if (this.monitor.enterIf(this.isStoppable)) {
			try {
				State previous = this.state();
				switch (previous) {
					case NEW:
						this.snapshot = new AbstractService.StateSnapshot(State.TERMINATED);
						this.terminated(State.NEW);
						break;
					case STARTING:
						this.snapshot = new AbstractService.StateSnapshot(State.STARTING, true, null);
						this.stopping(State.STARTING);
						break;
					case RUNNING:
						this.snapshot = new AbstractService.StateSnapshot(State.STOPPING);
						this.stopping(State.RUNNING);
						this.doStop();
						break;
					case STOPPING:
					case TERMINATED:
					case FAILED:
						throw new AssertionError("isStoppable is incorrectly implemented, saw: " + previous);
					default:
						throw new AssertionError("Unexpected state: " + previous);
				}
			} catch (Throwable var5) {
				this.notifyFailed(var5);
			} finally {
				this.monitor.leave();
				this.executeListeners();
			}
		}

		return this;
	}

	@Override
	public final void awaitRunning() {
		this.monitor.enterWhenUninterruptibly(this.hasReachedRunning);

		try {
			this.checkCurrentState(State.RUNNING);
		} finally {
			this.monitor.leave();
		}
	}

	@Override
	public final void awaitRunning(long timeout, TimeUnit unit) throws TimeoutException {
		if (this.monitor.enterWhenUninterruptibly(this.hasReachedRunning, timeout, unit)) {
			try {
				this.checkCurrentState(State.RUNNING);
			} finally {
				this.monitor.leave();
			}
		} else {
			throw new TimeoutException("Timed out waiting for " + this + " to reach the RUNNING state.");
		}
	}

	@Override
	public final void awaitTerminated() {
		this.monitor.enterWhenUninterruptibly(this.isStopped);

		try {
			this.checkCurrentState(State.TERMINATED);
		} finally {
			this.monitor.leave();
		}
	}

	@Override
	public final void awaitTerminated(long timeout, TimeUnit unit) throws TimeoutException {
		if (this.monitor.enterWhenUninterruptibly(this.isStopped, timeout, unit)) {
			try {
				this.checkCurrentState(State.TERMINATED);
			} finally {
				this.monitor.leave();
			}
		} else {
			throw new TimeoutException("Timed out waiting for " + this + " to reach a terminal state. Current state: " + this.state());
		}
	}

	@GuardedBy("monitor")
	private void checkCurrentState(State expected) {
		State actual = this.state();
		if (actual != expected) {
			if (actual == State.FAILED) {
				throw new IllegalStateException("Expected the service " + this + " to be " + expected + ", but the service has FAILED", this.failureCause());
			} else {
				throw new IllegalStateException("Expected the service " + this + " to be " + expected + ", but was " + actual);
			}
		}
	}

	protected final void notifyStarted() {
		this.monitor.enter();

		try {
			if (this.snapshot.state != State.STARTING) {
				IllegalStateException failure = new IllegalStateException("Cannot notifyStarted() when the service is " + this.snapshot.state);
				this.notifyFailed(failure);
				throw failure;
			}

			if (this.snapshot.shutdownWhenStartupFinishes) {
				this.snapshot = new AbstractService.StateSnapshot(State.STOPPING);
				this.doStop();
			} else {
				this.snapshot = new AbstractService.StateSnapshot(State.RUNNING);
				this.running();
			}
		} finally {
			this.monitor.leave();
			this.executeListeners();
		}
	}

	protected final void notifyStopped() {
		this.monitor.enter();

		try {
			State previous = this.snapshot.state;
			if (previous != State.STOPPING && previous != State.RUNNING) {
				IllegalStateException failure = new IllegalStateException("Cannot notifyStopped() when the service is " + previous);
				this.notifyFailed(failure);
				throw failure;
			}

			this.snapshot = new AbstractService.StateSnapshot(State.TERMINATED);
			this.terminated(previous);
		} finally {
			this.monitor.leave();
			this.executeListeners();
		}
	}

	protected final void notifyFailed(Throwable cause) {
		Preconditions.checkNotNull(cause);
		this.monitor.enter();

		try {
			State previous = this.state();
			switch (previous) {
				case NEW:
				case TERMINATED:
					throw new IllegalStateException("Failed while in state:" + previous, cause);
				case STARTING:
				case RUNNING:
				case STOPPING:
					this.snapshot = new AbstractService.StateSnapshot(State.FAILED, false, cause);
					this.failed(previous, cause);
				case FAILED:
					break;
				default:
					throw new AssertionError("Unexpected state: " + previous);
			}
		} finally {
			this.monitor.leave();
			this.executeListeners();
		}
	}

	@Override
	public final boolean isRunning() {
		return this.state() == State.RUNNING;
	}

	@Override
	public final State state() {
		return this.snapshot.externalState();
	}

	@Override
	public final Throwable failureCause() {
		return this.snapshot.failureCause();
	}

	@Override
	public final void addListener(Listener listener, Executor executor) {
		Preconditions.checkNotNull(listener, "listener");
		Preconditions.checkNotNull(executor, "executor");
		this.monitor.enter();

		try {
			if (!this.state().isTerminal()) {
				this.listeners.add(new ListenerCallQueue<>(listener, executor));
			}
		} finally {
			this.monitor.leave();
		}
	}

	public String toString() {
		return this.getClass().getSimpleName() + " [" + this.state() + "]";
	}

	private void executeListeners() {
		if (!this.monitor.isOccupiedByCurrentThread()) {
			for (int i = 0; i < this.listeners.size(); i++) {
				((ListenerCallQueue)this.listeners.get(i)).execute();
			}
		}
	}

	@GuardedBy("monitor")
	private void starting() {
		STARTING_CALLBACK.enqueueOn(this.listeners);
	}

	@GuardedBy("monitor")
	private void running() {
		RUNNING_CALLBACK.enqueueOn(this.listeners);
	}

	@GuardedBy("monitor")
	private void stopping(State from) {
		if (from == State.STARTING) {
			STOPPING_FROM_STARTING_CALLBACK.enqueueOn(this.listeners);
		} else {
			if (from != State.RUNNING) {
				throw new AssertionError();
			}

			STOPPING_FROM_RUNNING_CALLBACK.enqueueOn(this.listeners);
		}
	}

	@GuardedBy("monitor")
	private void terminated(State from) {
		switch (from) {
			case NEW:
				TERMINATED_FROM_NEW_CALLBACK.enqueueOn(this.listeners);
				break;
			case STARTING:
			case TERMINATED:
			case FAILED:
			default:
				throw new AssertionError();
			case RUNNING:
				TERMINATED_FROM_RUNNING_CALLBACK.enqueueOn(this.listeners);
				break;
			case STOPPING:
				TERMINATED_FROM_STOPPING_CALLBACK.enqueueOn(this.listeners);
		}
	}

	@GuardedBy("monitor")
	private void failed(State from, Throwable cause) {
		(new Callback<Listener>("failed({from = " + from + ", cause = " + cause + "})") {
			void call(Listener listener) {
				listener.failed(from, cause);
			}
		}).enqueueOn(this.listeners);
	}

	private final class HasReachedRunningGuard extends Guard {
		HasReachedRunningGuard() {
			super(AbstractService.this.monitor);
		}

		@Override
		public boolean isSatisfied() {
			return AbstractService.this.state().compareTo(State.RUNNING) >= 0;
		}
	}

	private final class IsStartableGuard extends Guard {
		IsStartableGuard() {
			super(AbstractService.this.monitor);
		}

		@Override
		public boolean isSatisfied() {
			return AbstractService.this.state() == State.NEW;
		}
	}

	private final class IsStoppableGuard extends Guard {
		IsStoppableGuard() {
			super(AbstractService.this.monitor);
		}

		@Override
		public boolean isSatisfied() {
			return AbstractService.this.state().compareTo(State.RUNNING) <= 0;
		}
	}

	private final class IsStoppedGuard extends Guard {
		IsStoppedGuard() {
			super(AbstractService.this.monitor);
		}

		@Override
		public boolean isSatisfied() {
			return AbstractService.this.state().isTerminal();
		}
	}

	@Immutable
	private static final class StateSnapshot {
		final State state;
		final boolean shutdownWhenStartupFinishes;
		@Nullable
		final Throwable failure;

		StateSnapshot(State internalState) {
			this(internalState, false, null);
		}

		StateSnapshot(State internalState, boolean shutdownWhenStartupFinishes, @Nullable Throwable failure) {
			Preconditions.checkArgument(
				!shutdownWhenStartupFinishes || internalState == State.STARTING,
				"shudownWhenStartupFinishes can only be set if state is STARTING. Got %s instead.",
				internalState
			);
			Preconditions.checkArgument(
				!(failure != null ^ internalState == State.FAILED),
				"A failure cause should be set if and only if the state is failed.  Got %s and %s instead.",
				internalState,
				failure
			);
			this.state = internalState;
			this.shutdownWhenStartupFinishes = shutdownWhenStartupFinishes;
			this.failure = failure;
		}

		State externalState() {
			return this.shutdownWhenStartupFinishes && this.state == State.STARTING ? State.STOPPING : this.state;
		}

		Throwable failureCause() {
			Preconditions.checkState(this.state == State.FAILED, "failureCause() is only valid if the service has failed, service is %s", this.state);
			return this.failure;
		}
	}
}
