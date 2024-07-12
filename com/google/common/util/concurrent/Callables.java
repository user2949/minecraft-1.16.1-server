package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.util.concurrent.Callable;
import javax.annotation.Nullable;

@GwtCompatible(
	emulated = true
)
public final class Callables {
	private Callables() {
	}

	public static <T> Callable<T> returning(@Nullable T value) {
		return new Callable<T>() {
			public T call() {
				return value;
			}
		};
	}

	@Beta
	@GwtIncompatible
	public static <T> AsyncCallable<T> asAsyncCallable(Callable<T> callable, ListeningExecutorService listeningExecutorService) {
		Preconditions.checkNotNull(callable);
		Preconditions.checkNotNull(listeningExecutorService);
		return new AsyncCallable<T>() {
			@Override
			public ListenableFuture<T> call() throws Exception {
				return listeningExecutorService.submit(callable);
			}
		};
	}

	@GwtIncompatible
	static <T> Callable<T> threadRenaming(Callable<T> callable, Supplier<String> nameSupplier) {
		Preconditions.checkNotNull(nameSupplier);
		Preconditions.checkNotNull(callable);
		return new Callable<T>() {
			public T call() throws Exception {
				Thread currentThread = Thread.currentThread();
				String oldName = currentThread.getName();
				boolean restoreName = Callables.trySetName(nameSupplier.get(), currentThread);

				Object var4;
				try {
					var4 = callable.call();
				} finally {
					if (restoreName) {
						boolean var7 = Callables.trySetName(oldName, currentThread);
					}
				}

				return (T)var4;
			}
		};
	}

	@GwtIncompatible
	static Runnable threadRenaming(Runnable task, Supplier<String> nameSupplier) {
		Preconditions.checkNotNull(nameSupplier);
		Preconditions.checkNotNull(task);
		return new Runnable() {
			public void run() {
				Thread currentThread = Thread.currentThread();
				String oldName = currentThread.getName();
				boolean restoreName = Callables.trySetName(nameSupplier.get(), currentThread);

				try {
					task.run();
				} finally {
					if (restoreName) {
						boolean var6 = Callables.trySetName(oldName, currentThread);
					}
				}
			}
		};
	}

	@GwtIncompatible
	private static boolean trySetName(String threadName, Thread currentThread) {
		try {
			currentThread.setName(threadName);
			return true;
		} catch (SecurityException var3) {
			return false;
		}
	}
}
