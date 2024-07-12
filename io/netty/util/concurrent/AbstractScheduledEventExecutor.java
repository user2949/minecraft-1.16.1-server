package io.netty.util.concurrent;

import io.netty.util.internal.DefaultPriorityQueue;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PriorityQueue;
import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class AbstractScheduledEventExecutor extends AbstractEventExecutor {
	private static final Comparator<ScheduledFutureTask<?>> SCHEDULED_FUTURE_TASK_COMPARATOR = new Comparator<ScheduledFutureTask<?>>() {
		public int compare(ScheduledFutureTask<?> o1, ScheduledFutureTask<?> o2) {
			return o1.compareTo((Delayed)o2);
		}
	};
	PriorityQueue<ScheduledFutureTask<?>> scheduledTaskQueue;

	protected AbstractScheduledEventExecutor() {
	}

	protected AbstractScheduledEventExecutor(EventExecutorGroup parent) {
		super(parent);
	}

	protected static long nanoTime() {
		return ScheduledFutureTask.nanoTime();
	}

	PriorityQueue<ScheduledFutureTask<?>> scheduledTaskQueue() {
		if (this.scheduledTaskQueue == null) {
			this.scheduledTaskQueue = new DefaultPriorityQueue<>(SCHEDULED_FUTURE_TASK_COMPARATOR, 11);
		}

		return this.scheduledTaskQueue;
	}

	private static boolean isNullOrEmpty(Queue<ScheduledFutureTask<?>> queue) {
		return queue == null || queue.isEmpty();
	}

	protected void cancelScheduledTasks() {
		assert this.inEventLoop();

		PriorityQueue<ScheduledFutureTask<?>> scheduledTaskQueue = this.scheduledTaskQueue;
		if (!isNullOrEmpty(scheduledTaskQueue)) {
			ScheduledFutureTask<?>[] scheduledTasks = (ScheduledFutureTask<?>[])scheduledTaskQueue.toArray(new ScheduledFutureTask[scheduledTaskQueue.size()]);

			for (ScheduledFutureTask<?> task : scheduledTasks) {
				task.cancelWithoutRemove(false);
			}

			scheduledTaskQueue.clearIgnoringIndexes();
		}
	}

	protected final Runnable pollScheduledTask() {
		return this.pollScheduledTask(nanoTime());
	}

	protected final Runnable pollScheduledTask(long nanoTime) {
		assert this.inEventLoop();

		Queue<ScheduledFutureTask<?>> scheduledTaskQueue = this.scheduledTaskQueue;
		ScheduledFutureTask<?> scheduledTask = scheduledTaskQueue == null ? null : (ScheduledFutureTask)scheduledTaskQueue.peek();
		if (scheduledTask == null) {
			return null;
		} else if (scheduledTask.deadlineNanos() <= nanoTime) {
			scheduledTaskQueue.remove();
			return scheduledTask;
		} else {
			return null;
		}
	}

	protected final long nextScheduledTaskNano() {
		Queue<ScheduledFutureTask<?>> scheduledTaskQueue = this.scheduledTaskQueue;
		ScheduledFutureTask<?> scheduledTask = scheduledTaskQueue == null ? null : (ScheduledFutureTask)scheduledTaskQueue.peek();
		return scheduledTask == null ? -1L : Math.max(0L, scheduledTask.deadlineNanos() - nanoTime());
	}

	final ScheduledFutureTask<?> peekScheduledTask() {
		Queue<ScheduledFutureTask<?>> scheduledTaskQueue = this.scheduledTaskQueue;
		return scheduledTaskQueue == null ? null : (ScheduledFutureTask)scheduledTaskQueue.peek();
	}

	protected final boolean hasScheduledTasks() {
		Queue<ScheduledFutureTask<?>> scheduledTaskQueue = this.scheduledTaskQueue;
		ScheduledFutureTask<?> scheduledTask = scheduledTaskQueue == null ? null : (ScheduledFutureTask)scheduledTaskQueue.peek();
		return scheduledTask != null && scheduledTask.deadlineNanos() <= nanoTime();
	}

	@Override
	public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
		ObjectUtil.checkNotNull(command, "command");
		ObjectUtil.checkNotNull(unit, "unit");
		if (delay < 0L) {
			delay = 0L;
		}

		this.validateScheduled(delay, unit);
		return this.schedule(new ScheduledFutureTask(this, command, null, ScheduledFutureTask.deadlineNanos(unit.toNanos(delay))));
	}

	@Override
	public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
		ObjectUtil.checkNotNull(callable, "callable");
		ObjectUtil.checkNotNull(unit, "unit");
		if (delay < 0L) {
			delay = 0L;
		}

		this.validateScheduled(delay, unit);
		return this.schedule(new ScheduledFutureTask<>(this, callable, ScheduledFutureTask.deadlineNanos(unit.toNanos(delay))));
	}

	@Override
	public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
		ObjectUtil.checkNotNull(command, "command");
		ObjectUtil.checkNotNull(unit, "unit");
		if (initialDelay < 0L) {
			throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", initialDelay));
		} else if (period <= 0L) {
			throw new IllegalArgumentException(String.format("period: %d (expected: > 0)", period));
		} else {
			this.validateScheduled(initialDelay, unit);
			this.validateScheduled(period, unit);
			return this.schedule(
				new ScheduledFutureTask(this, Executors.callable(command, null), ScheduledFutureTask.deadlineNanos(unit.toNanos(initialDelay)), unit.toNanos(period))
			);
		}
	}

	@Override
	public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
		ObjectUtil.checkNotNull(command, "command");
		ObjectUtil.checkNotNull(unit, "unit");
		if (initialDelay < 0L) {
			throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", initialDelay));
		} else if (delay <= 0L) {
			throw new IllegalArgumentException(String.format("delay: %d (expected: > 0)", delay));
		} else {
			this.validateScheduled(initialDelay, unit);
			this.validateScheduled(delay, unit);
			return this.schedule(
				new ScheduledFutureTask(this, Executors.callable(command, null), ScheduledFutureTask.deadlineNanos(unit.toNanos(initialDelay)), -unit.toNanos(delay))
			);
		}
	}

	protected void validateScheduled(long amount, TimeUnit unit) {
	}

	<V> ScheduledFuture<V> schedule(ScheduledFutureTask<V> task) {
		if (this.inEventLoop()) {
			this.scheduledTaskQueue().add(task);
		} else {
			this.execute(new Runnable() {
				public void run() {
					AbstractScheduledEventExecutor.this.scheduledTaskQueue().add(task);
				}
			});
		}

		return task;
	}

	final void removeScheduled(ScheduledFutureTask<?> task) {
		if (this.inEventLoop()) {
			this.scheduledTaskQueue().removeTyped(task);
		} else {
			this.execute(new Runnable() {
				public void run() {
					AbstractScheduledEventExecutor.this.removeScheduled(task);
				}
			});
		}
	}
}
