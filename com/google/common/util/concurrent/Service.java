package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Beta
@GwtIncompatible
public interface Service {
	@CanIgnoreReturnValue
	Service startAsync();

	boolean isRunning();

	Service.State state();

	@CanIgnoreReturnValue
	Service stopAsync();

	void awaitRunning();

	void awaitRunning(long long1, TimeUnit timeUnit) throws TimeoutException;

	void awaitTerminated();

	void awaitTerminated(long long1, TimeUnit timeUnit) throws TimeoutException;

	Throwable failureCause();

	void addListener(Service.Listener listener, Executor executor);

	@Beta
	public abstract static class Listener {
		public void starting() {
		}

		public void running() {
		}

		public void stopping(Service.State from) {
		}

		public void terminated(Service.State from) {
		}

		public void failed(Service.State from, Throwable failure) {
		}
	}

	@Beta
	public static enum State {
		NEW {
			@Override
			boolean isTerminal() {
				return false;
			}
		},
		STARTING {
			@Override
			boolean isTerminal() {
				return false;
			}
		},
		RUNNING {
			@Override
			boolean isTerminal() {
				return false;
			}
		},
		STOPPING {
			@Override
			boolean isTerminal() {
				return false;
			}
		},
		TERMINATED {
			@Override
			boolean isTerminal() {
				return true;
			}
		},
		FAILED {
			@Override
			boolean isTerminal() {
				return true;
			}
		};

		private State() {
		}

		abstract boolean isTerminal();
	}
}
