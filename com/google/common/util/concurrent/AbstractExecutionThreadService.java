package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Service.Listener;
import com.google.common.util.concurrent.Service.State;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Beta
@GwtIncompatible
public abstract class AbstractExecutionThreadService implements Service {
	private static final Logger logger = Logger.getLogger(AbstractExecutionThreadService.class.getName());
	private final Service delegate = new AbstractService() {
		@Override
		protected final void doStart() {
			Executor executor = MoreExecutors.renamingDecorator(AbstractExecutionThreadService.this.executor(), new Supplier<String>() {
				public String get() {
					return AbstractExecutionThreadService.this.serviceName();
				}
			});
			executor.execute(new Runnable() {
				public void run() {
					try {
						AbstractExecutionThreadService.this.startUp();
						notifyStarted();
						if (isRunning()) {
							try {
								AbstractExecutionThreadService.this.run();
							} catch (Throwable var4) {
								try {
									AbstractExecutionThreadService.this.shutDown();
								} catch (Exception var3) {
									AbstractExecutionThreadService.logger.log(Level.WARNING, "Error while attempting to shut down the service after failure.", var3);
								}

								notifyFailed(var4);
								return;
							}
						}

						AbstractExecutionThreadService.this.shutDown();
						notifyStopped();
					} catch (Throwable var5) {
						notifyFailed(var5);
					}
				}
			});
		}

		@Override
		protected void doStop() {
			AbstractExecutionThreadService.this.triggerShutdown();
		}

		@Override
		public String toString() {
			return AbstractExecutionThreadService.this.toString();
		}
	};

	protected AbstractExecutionThreadService() {
	}

	protected void startUp() throws Exception {
	}

	protected abstract void run() throws Exception;

	protected void shutDown() throws Exception {
	}

	protected void triggerShutdown() {
	}

	protected Executor executor() {
		return new Executor() {
			public void execute(Runnable command) {
				MoreExecutors.newThread(AbstractExecutionThreadService.this.serviceName(), command).start();
			}
		};
	}

	public String toString() {
		return this.serviceName() + " [" + this.state() + "]";
	}

	@Override
	public final boolean isRunning() {
		return this.delegate.isRunning();
	}

	@Override
	public final State state() {
		return this.delegate.state();
	}

	@Override
	public final void addListener(Listener listener, Executor executor) {
		this.delegate.addListener(listener, executor);
	}

	@Override
	public final Throwable failureCause() {
		return this.delegate.failureCause();
	}

	@CanIgnoreReturnValue
	@Override
	public final Service startAsync() {
		this.delegate.startAsync();
		return this;
	}

	@CanIgnoreReturnValue
	@Override
	public final Service stopAsync() {
		this.delegate.stopAsync();
		return this;
	}

	@Override
	public final void awaitRunning() {
		this.delegate.awaitRunning();
	}

	@Override
	public final void awaitRunning(long timeout, TimeUnit unit) throws TimeoutException {
		this.delegate.awaitRunning(timeout, unit);
	}

	@Override
	public final void awaitTerminated() {
		this.delegate.awaitTerminated();
	}

	@Override
	public final void awaitTerminated(long timeout, TimeUnit unit) throws TimeoutException {
		this.delegate.awaitTerminated(timeout, unit);
	}

	protected String serviceName() {
		return this.getClass().getSimpleName();
	}
}
