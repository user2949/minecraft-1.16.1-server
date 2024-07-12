package io.netty.util.concurrent;

import io.netty.util.concurrent.EventExecutorChooserFactory.EventExecutorChooser;
import java.util.concurrent.atomic.AtomicInteger;

public final class DefaultEventExecutorChooserFactory implements EventExecutorChooserFactory {
	public static final DefaultEventExecutorChooserFactory INSTANCE = new DefaultEventExecutorChooserFactory();

	private DefaultEventExecutorChooserFactory() {
	}

	@Override
	public EventExecutorChooser newChooser(EventExecutor[] executors) {
		return (EventExecutorChooser)(isPowerOfTwo(executors.length)
			? new DefaultEventExecutorChooserFactory.PowerOfTwoEventExecutorChooser(executors)
			: new DefaultEventExecutorChooserFactory.GenericEventExecutorChooser(executors));
	}

	private static boolean isPowerOfTwo(int val) {
		return (val & -val) == val;
	}

	private static final class GenericEventExecutorChooser implements EventExecutorChooser {
		private final AtomicInteger idx = new AtomicInteger();
		private final EventExecutor[] executors;

		GenericEventExecutorChooser(EventExecutor[] executors) {
			this.executors = executors;
		}

		@Override
		public EventExecutor next() {
			return this.executors[Math.abs(this.idx.getAndIncrement() % this.executors.length)];
		}
	}

	private static final class PowerOfTwoEventExecutorChooser implements EventExecutorChooser {
		private final AtomicInteger idx = new AtomicInteger();
		private final EventExecutor[] executors;

		PowerOfTwoEventExecutorChooser(EventExecutor[] executors) {
			this.executors = executors;
		}

		@Override
		public EventExecutor next() {
			return this.executors[this.idx.getAndIncrement() & this.executors.length - 1];
		}
	}
}
