package io.netty.handler.timeout;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class WriteTimeoutHandler extends ChannelOutboundHandlerAdapter {
	private static final long MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
	private final long timeoutNanos;
	private WriteTimeoutHandler.WriteTimeoutTask lastTask;
	private boolean closed;

	public WriteTimeoutHandler(int timeoutSeconds) {
		this((long)timeoutSeconds, TimeUnit.SECONDS);
	}

	public WriteTimeoutHandler(long timeout, TimeUnit unit) {
		if (unit == null) {
			throw new NullPointerException("unit");
		} else {
			if (timeout <= 0L) {
				this.timeoutNanos = 0L;
			} else {
				this.timeoutNanos = Math.max(unit.toNanos(timeout), MIN_TIMEOUT_NANOS);
			}
		}
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		if (this.timeoutNanos > 0L) {
			promise = promise.unvoid();
			this.scheduleTimeout(ctx, promise);
		}

		ctx.write(msg, promise);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		WriteTimeoutHandler.WriteTimeoutTask task = this.lastTask;
		this.lastTask = null;

		while (task != null) {
			task.scheduledFuture.cancel(false);
			WriteTimeoutHandler.WriteTimeoutTask prev = task.prev;
			task.prev = null;
			task.next = null;
			task = prev;
		}
	}

	private void scheduleTimeout(ChannelHandlerContext ctx, ChannelPromise promise) {
		WriteTimeoutHandler.WriteTimeoutTask task = new WriteTimeoutHandler.WriteTimeoutTask(ctx, promise);
		task.scheduledFuture = ctx.executor().schedule(task, this.timeoutNanos, TimeUnit.NANOSECONDS);
		if (!task.scheduledFuture.isDone()) {
			this.addWriteTimeoutTask(task);
			promise.addListener(task);
		}
	}

	private void addWriteTimeoutTask(WriteTimeoutHandler.WriteTimeoutTask task) {
		if (this.lastTask != null) {
			this.lastTask.next = task;
			task.prev = this.lastTask;
		}

		this.lastTask = task;
	}

	private void removeWriteTimeoutTask(WriteTimeoutHandler.WriteTimeoutTask task) {
		if (task == this.lastTask) {
			assert task.next == null;

			this.lastTask = this.lastTask.prev;
			if (this.lastTask != null) {
				this.lastTask.next = null;
			}
		} else {
			if (task.prev == null && task.next == null) {
				return;
			}

			if (task.prev == null) {
				task.next.prev = null;
			} else {
				task.prev.next = task.next;
				task.next.prev = task.prev;
			}
		}

		task.prev = null;
		task.next = null;
	}

	protected void writeTimedOut(ChannelHandlerContext ctx) throws Exception {
		if (!this.closed) {
			ctx.fireExceptionCaught(WriteTimeoutException.INSTANCE);
			ctx.close();
			this.closed = true;
		}
	}

	private final class WriteTimeoutTask implements Runnable, ChannelFutureListener {
		private final ChannelHandlerContext ctx;
		private final ChannelPromise promise;
		WriteTimeoutHandler.WriteTimeoutTask prev;
		WriteTimeoutHandler.WriteTimeoutTask next;
		ScheduledFuture<?> scheduledFuture;

		WriteTimeoutTask(ChannelHandlerContext ctx, ChannelPromise promise) {
			this.ctx = ctx;
			this.promise = promise;
		}

		public void run() {
			if (!this.promise.isDone()) {
				try {
					WriteTimeoutHandler.this.writeTimedOut(this.ctx);
				} catch (Throwable var2) {
					this.ctx.fireExceptionCaught(var2);
				}
			}

			WriteTimeoutHandler.this.removeWriteTimeoutTask(this);
		}

		public void operationComplete(ChannelFuture future) throws Exception {
			this.scheduledFuture.cancel(false);
			WriteTimeoutHandler.this.removeWriteTimeoutTask(this);
		}
	}
}
