package com.google.common.eventbus;

import com.google.common.annotations.Beta;
import com.google.common.eventbus.EventBus.LoggingHandler;
import java.util.concurrent.Executor;

@Beta
public class AsyncEventBus extends EventBus {
	public AsyncEventBus(String identifier, Executor executor) {
		super(identifier, executor, Dispatcher.legacyAsync(), LoggingHandler.INSTANCE);
	}

	public AsyncEventBus(Executor executor, SubscriberExceptionHandler subscriberExceptionHandler) {
		super("default", executor, Dispatcher.legacyAsync(), subscriberExceptionHandler);
	}

	public AsyncEventBus(Executor executor) {
		super("default", executor, Dispatcher.legacyAsync(), LoggingHandler.INSTANCE);
	}
}
