package org.apache.logging.log4j.core.util;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.LifeCycle2;
import org.apache.logging.log4j.core.LifeCycle.State;
import org.apache.logging.log4j.status.StatusLogger;

public class DefaultShutdownCallbackRegistry implements ShutdownCallbackRegistry, LifeCycle2, Runnable {
	protected static final Logger LOGGER = StatusLogger.getLogger();
	private final AtomicReference<State> state = new AtomicReference(State.INITIALIZED);
	private final ThreadFactory threadFactory;
	private final Collection<Cancellable> hooks = new CopyOnWriteArrayList();
	private Reference<Thread> shutdownHookRef;

	public DefaultShutdownCallbackRegistry() {
		this(Executors.defaultThreadFactory());
	}

	protected DefaultShutdownCallbackRegistry(ThreadFactory threadFactory) {
		this.threadFactory = threadFactory;
	}

	public void run() {
		if (this.state.compareAndSet(State.STARTED, State.STOPPING)) {
			for (Runnable hook : this.hooks) {
				try {
					hook.run();
				} catch (Throwable var6) {
					Throwable t1 = var6;

					try {
						LOGGER.error(SHUTDOWN_HOOK_MARKER, "Caught exception executing shutdown hook {}", hook, t1);
					} catch (Throwable var5) {
						System.err.println("Caught exception " + var5.getClass() + " logging exception " + var6.getClass());
						var6.printStackTrace();
					}
				}
			}

			this.state.set(State.STOPPED);
		}
	}

	@Override
	public Cancellable addShutdownCallback(Runnable callback) {
		if (this.isStarted()) {
			Cancellable receipt = new DefaultShutdownCallbackRegistry.RegisteredCancellable(callback, this.hooks);
			this.hooks.add(receipt);
			return receipt;
		} else {
			throw new IllegalStateException("Cannot add new shutdown hook as this is not started. Current state: " + ((State)this.state.get()).name());
		}
	}

	@Override
	public void initialize() {
	}

	@Override
	public void start() {
		if (this.state.compareAndSet(State.INITIALIZED, State.STARTING)) {
			try {
				this.addShutdownHook(this.threadFactory.newThread(this));
				this.state.set(State.STARTED);
			} catch (IllegalStateException var2) {
				this.state.set(State.STOPPED);
				throw var2;
			} catch (Exception var3) {
				LOGGER.catching(var3);
				this.state.set(State.STOPPED);
			}
		}
	}

	private void addShutdownHook(Thread thread) {
		this.shutdownHookRef = new WeakReference(thread);
		Runtime.getRuntime().addShutdownHook(thread);
	}

	@Override
	public void stop() {
		this.stop(0L, AbstractLifeCycle.DEFAULT_STOP_TIMEUNIT);
	}

	@Override
	public boolean stop(long timeout, TimeUnit timeUnit) {
		if (this.state.compareAndSet(State.STARTED, State.STOPPING)) {
			try {
				this.removeShutdownHook();
			} finally {
				this.state.set(State.STOPPED);
			}
		}

		return true;
	}

	private void removeShutdownHook() {
		Thread shutdownThread = (Thread)this.shutdownHookRef.get();
		if (shutdownThread != null) {
			Runtime.getRuntime().removeShutdownHook(shutdownThread);
			this.shutdownHookRef.enqueue();
		}
	}

	@Override
	public State getState() {
		return (State)this.state.get();
	}

	@Override
	public boolean isStarted() {
		return this.state.get() == State.STARTED;
	}

	@Override
	public boolean isStopped() {
		return this.state.get() == State.STOPPED;
	}

	private static class RegisteredCancellable implements Cancellable {
		private final Reference<Runnable> hook;
		private Collection<Cancellable> registered;

		RegisteredCancellable(Runnable callback, Collection<Cancellable> registered) {
			this.registered = registered;
			this.hook = new SoftReference(callback);
		}

		@Override
		public void cancel() {
			this.hook.clear();
			this.registered.remove(this);
			this.registered = null;
		}

		public void run() {
			Runnable runnableHook = (Runnable)this.hook.get();
			if (runnableHook != null) {
				runnableHook.run();
				this.hook.clear();
			}
		}

		public String toString() {
			return String.valueOf(this.hook.get());
		}
	}
}
