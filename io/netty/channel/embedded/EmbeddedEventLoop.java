package io.netty.channel.embedded;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.AbstractScheduledEventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

final class EmbeddedEventLoop extends AbstractScheduledEventExecutor implements EventLoop {
	private final Queue<Runnable> tasks = new ArrayDeque(2);

	@Override
	public EventLoopGroup parent() {
		return (EventLoopGroup)super.parent();
	}

	@Override
	public EventLoop next() {
		return (EventLoop)super.next();
	}

	public void execute(Runnable command) {
		if (command == null) {
			throw new NullPointerException("command");
		} else {
			this.tasks.add(command);
		}
	}

	void runTasks() {
		while (true) {
			Runnable task = (Runnable)this.tasks.poll();
			if (task == null) {
				return;
			}

			task.run();
		}
	}

	long runScheduledTasks() {
		long time = AbstractScheduledEventExecutor.nanoTime();

		while (true) {
			Runnable task = this.pollScheduledTask(time);
			if (task == null) {
				return this.nextScheduledTaskNano();
			}

			task.run();
		}
	}

	long nextScheduledTask() {
		return this.nextScheduledTaskNano();
	}

	@Override
	protected void cancelScheduledTasks() {
		super.cancelScheduledTasks();
	}

	@Override
	public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Future<?> terminationFuture() {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	@Override
	public void shutdown() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isShuttingDown() {
		return false;
	}

	public boolean isShutdown() {
		return false;
	}

	public boolean isTerminated() {
		return false;
	}

	public boolean awaitTermination(long timeout, TimeUnit unit) {
		return false;
	}

	@Override
	public ChannelFuture register(Channel channel) {
		return this.register(new DefaultChannelPromise(channel, this));
	}

	@Override
	public ChannelFuture register(ChannelPromise promise) {
		ObjectUtil.checkNotNull(promise, "promise");
		promise.channel().unsafe().register(this, promise);
		return promise;
	}

	@Deprecated
	@Override
	public ChannelFuture register(Channel channel, ChannelPromise promise) {
		channel.unsafe().register(this, promise);
		return promise;
	}

	@Override
	public boolean inEventLoop() {
		return true;
	}

	@Override
	public boolean inEventLoop(Thread thread) {
		return true;
	}
}
