package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Service.Listener;
import com.google.common.util.concurrent.Service.State;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

@Beta
@GwtIncompatible
public abstract class AbstractScheduledService implements Service {
	private static final Logger logger = Logger.getLogger(AbstractScheduledService.class.getName());
	private final AbstractService delegate = new AbstractScheduledService.ServiceDelegate();

	protected AbstractScheduledService() {
	}

	protected abstract void runOneIteration() throws Exception;

	protected void startUp() throws Exception {
	}

	protected void shutDown() throws Exception {
	}

	protected abstract AbstractScheduledService.Scheduler scheduler();

	protected ScheduledExecutorService executor() {
		class 1ThreadFactoryImpl implements ThreadFactory {
			public Thread newThread(Runnable runnable) {
				return MoreExecutors.newThread(AbstractScheduledService.this.serviceName(), runnable);
			}
		}

		final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new 1ThreadFactoryImpl());
		this.addListener(new Listener() {
			@Override
			public void terminated(State from) {
				executor.shutdown();
			}

			@Override
			public void failed(State from, Throwable failure) {
				executor.shutdown();
			}
		}, MoreExecutors.directExecutor());
		return executor;
	}

	protected String serviceName() {
		return this.getClass().getSimpleName();
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

	@Beta
	public abstract static class CustomScheduler extends AbstractScheduledService.Scheduler {
		@Override
		final Future<?> schedule(AbstractService service, ScheduledExecutorService executor, Runnable runnable) {
			AbstractScheduledService.CustomScheduler.ReschedulableCallable task = new AbstractScheduledService.CustomScheduler.ReschedulableCallable(
				service, executor, runnable
			);
			task.reschedule();
			return task;
		}

		protected abstract AbstractScheduledService.CustomScheduler.Schedule getNextSchedule() throws Exception;

		private class ReschedulableCallable extends ForwardingFuture<Void> implements Callable<Void> {
			private final Runnable wrappedRunnable;
			private final ScheduledExecutorService executor;
			private final AbstractService service;
			private final ReentrantLock lock = new ReentrantLock();
			@GuardedBy("lock")
			private Future<Void> currentFuture;

			ReschedulableCallable(AbstractService service, ScheduledExecutorService executor, Runnable runnable) {
				this.wrappedRunnable = runnable;
				this.executor = executor;
				this.service = service;
			}

			public Void call() throws Exception {
				this.wrappedRunnable.run();
				this.reschedule();
				return null;
			}

			public void reschedule() {
				AbstractScheduledService.CustomScheduler.Schedule schedule;
				try {
					schedule = CustomScheduler.this.getNextSchedule();
				} catch (Throwable var8) {
					this.service.notifyFailed(var8);
					return;
				}

				Throwable scheduleFailure = null;
				this.lock.lock();

				try {
					if (this.currentFuture == null || !this.currentFuture.isCancelled()) {
						this.currentFuture = this.executor.schedule(this, schedule.delay, schedule.unit);
					}
				} catch (Throwable var9) {
					scheduleFailure = var9;
				} finally {
					this.lock.unlock();
				}

				if (scheduleFailure != null) {
					this.service.notifyFailed(scheduleFailure);
				}
			}

			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				this.lock.lock();

				boolean var2;
				try {
					var2 = this.currentFuture.cancel(mayInterruptIfRunning);
				} finally {
					this.lock.unlock();
				}

				return var2;
			}

			@Override
			public boolean isCancelled() {
				this.lock.lock();

				boolean var1;
				try {
					var1 = this.currentFuture.isCancelled();
				} finally {
					this.lock.unlock();
				}

				return var1;
			}

			@Override
			protected Future<Void> delegate() {
				throw new UnsupportedOperationException("Only cancel and isCancelled is supported by this future");
			}
		}

		@Beta
		protected static final class Schedule {
			private final long delay;
			private final TimeUnit unit;

			public Schedule(long delay, TimeUnit unit) {
				this.delay = delay;
				this.unit = Preconditions.checkNotNull(unit);
			}
		}
	}

	public abstract static class Scheduler {
		public static AbstractScheduledService.Scheduler newFixedDelaySchedule(long initialDelay, long delay, TimeUnit unit) {
			Preconditions.checkNotNull(unit);
			Preconditions.checkArgument(delay > 0L, "delay must be > 0, found %s", delay);
			return new AbstractScheduledService.Scheduler() {
				@Override
				public Future<?> schedule(AbstractService service, ScheduledExecutorService executor, Runnable task) {
					return executor.scheduleWithFixedDelay(task, initialDelay, delay, unit);
				}
			};
		}

		public static AbstractScheduledService.Scheduler newFixedRateSchedule(long initialDelay, long period, TimeUnit unit) {
			Preconditions.checkNotNull(unit);
			Preconditions.checkArgument(period > 0L, "period must be > 0, found %s", period);
			return new AbstractScheduledService.Scheduler() {
				@Override
				public Future<?> schedule(AbstractService service, ScheduledExecutorService executor, Runnable task) {
					return executor.scheduleAtFixedRate(task, initialDelay, period, unit);
				}
			};
		}

		abstract Future<?> schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable);

		private Scheduler() {
		}
	}

	private final class ServiceDelegate extends AbstractService {
		private volatile Future<?> runningTask;
		private volatile ScheduledExecutorService executorService;
		private final ReentrantLock lock = new ReentrantLock();
		private final Runnable task = new AbstractScheduledService.ServiceDelegate.Task();

		private ServiceDelegate() {
		}

		@Override
		protected final void doStart() {
			this.executorService = MoreExecutors.renamingDecorator(AbstractScheduledService.this.executor(), new Supplier<String>() {
				public String get() {
					return AbstractScheduledService.this.serviceName() + " " + ServiceDelegate.this.state();
				}
			});
			this.executorService
				.execute(
					new Runnable() {
						public void run() {
							ServiceDelegate.this.lock.lock();
		
							try {
								AbstractScheduledService.this.startUp();
								ServiceDelegate.this.runningTask = AbstractScheduledService.this.scheduler()
									.schedule(AbstractScheduledService.this.delegate, ServiceDelegate.this.executorService, ServiceDelegate.this.task);
								ServiceDelegate.this.notifyStarted();
							} catch (Throwable var5) {
								ServiceDelegate.this.notifyFailed(var5);
								if (ServiceDelegate.this.runningTask != null) {
									ServiceDelegate.this.runningTask.cancel(false);
								}
							} finally {
								ServiceDelegate.this.lock.unlock();
							}
						}
					}
				);
		}

		@Override
		protected final void doStop() {
			this.runningTask.cancel(false);
			this.executorService.execute(new Runnable() {
				public void run() {
					try {
						ServiceDelegate.this.lock.lock();

						label42: {
							try {
								if (ServiceDelegate.this.state() == State.STOPPING) {
									AbstractScheduledService.this.shutDown();
									break label42;
								}
							} finally {
								ServiceDelegate.this.lock.unlock();
							}

							return;
						}

						ServiceDelegate.this.notifyStopped();
					} catch (Throwable var5) {
						ServiceDelegate.this.notifyFailed(var5);
					}
				}
			});
		}

		@Override
		public String toString() {
			return AbstractScheduledService.this.toString();
		}

		class Task implements Runnable {
			public void run() {
				ServiceDelegate.this.lock.lock();

				try {
					if (!ServiceDelegate.this.runningTask.isCancelled()) {
						AbstractScheduledService.this.runOneIteration();
						return;
					}
				} catch (Throwable var8) {
					try {
						AbstractScheduledService.this.shutDown();
					} catch (Exception var7) {
						AbstractScheduledService.logger.log(Level.WARNING, "Error while attempting to shut down the service after failure.", var7);
					}

					ServiceDelegate.this.notifyFailed(var8);
					ServiceDelegate.this.runningTask.cancel(false);
					return;
				} finally {
					ServiceDelegate.this.lock.unlock();
				}
			}
		}
	}
}
