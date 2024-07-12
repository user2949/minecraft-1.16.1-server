package io.netty.util.concurrent;

public interface EventExecutorChooserFactory {
	EventExecutorChooserFactory.EventExecutorChooser newChooser(EventExecutor[] arr);

	public interface EventExecutorChooser {
		EventExecutor next();
	}
}
