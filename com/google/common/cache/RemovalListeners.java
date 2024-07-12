package com.google.common.cache;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;

@GwtIncompatible
public final class RemovalListeners {
	private RemovalListeners() {
	}

	public static <K, V> RemovalListener<K, V> asynchronous(RemovalListener<K, V> listener, Executor executor) {
		Preconditions.checkNotNull(listener);
		Preconditions.checkNotNull(executor);
		return new RemovalListener<K, V>() {
			@Override
			public void onRemoval(RemovalNotification<K, V> notification) {
				executor.execute(new Runnable() {
					public void run() {
						listener.onRemoval(notification);
					}
				});
			}
		};
	}
}
