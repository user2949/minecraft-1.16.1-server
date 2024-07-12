package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.ForwardingObject;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@CanIgnoreReturnValue
@GwtIncompatible
public abstract class ForwardingExecutorService extends ForwardingObject implements ExecutorService {
	protected ForwardingExecutorService() {
	}

	protected abstract ExecutorService delegate();

	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return this.delegate().awaitTermination(timeout, unit);
	}

	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
		return this.delegate().invokeAll(tasks);
	}

	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
		return this.delegate().invokeAll(tasks, timeout, unit);
	}

	public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
		return (T)this.delegate().invokeAny(tasks);
	}

	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return (T)this.delegate().invokeAny(tasks, timeout, unit);
	}

	public boolean isShutdown() {
		return this.delegate().isShutdown();
	}

	public boolean isTerminated() {
		return this.delegate().isTerminated();
	}

	public void shutdown() {
		this.delegate().shutdown();
	}

	public List<Runnable> shutdownNow() {
		return this.delegate().shutdownNow();
	}

	public void execute(Runnable command) {
		this.delegate().execute(command);
	}

	public <T> Future<T> submit(Callable<T> task) {
		return this.delegate().submit(task);
	}

	public Future<?> submit(Runnable task) {
		return this.delegate().submit(task);
	}

	public <T> Future<T> submit(Runnable task, T result) {
		return this.delegate().submit(task, result);
	}
}
