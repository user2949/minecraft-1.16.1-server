package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Beta
@GwtIncompatible
public final class SimpleTimeLimiter implements TimeLimiter {
	private final ExecutorService executor;

	public SimpleTimeLimiter(ExecutorService executor) {
		this.executor = Preconditions.checkNotNull(executor);
	}

	public SimpleTimeLimiter() {
		this(Executors.newCachedThreadPool());
	}

	@Override
	public <T> T newProxy(T target, Class<T> interfaceType, long timeoutDuration, TimeUnit timeoutUnit) {
		Preconditions.checkNotNull(target);
		Preconditions.checkNotNull(interfaceType);
		Preconditions.checkNotNull(timeoutUnit);
		Preconditions.checkArgument(timeoutDuration > 0L, "bad timeout: %s", timeoutDuration);
		Preconditions.checkArgument(interfaceType.isInterface(), "interfaceType must be an interface type");
		final Set<Method> interruptibleMethods = findInterruptibleMethods(interfaceType);
		InvocationHandler handler = new InvocationHandler() {
			public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
				Callable<Object> callable = new Callable<Object>() {
					public Object call() throws Exception {
						try {
							return method.invoke(target, args);
						} catch (InvocationTargetException var2) {
							throw SimpleTimeLimiter.throwCause(var2, false);
						}
					}
				};
				return SimpleTimeLimiter.this.callWithTimeout((Callable<T>)callable, timeoutDuration, timeoutUnit, interruptibleMethods.contains(method));
			}
		};
		return newProxy(interfaceType, handler);
	}

	@CanIgnoreReturnValue
	@Override
	public <T> T callWithTimeout(Callable<T> callable, long timeoutDuration, TimeUnit timeoutUnit, boolean amInterruptible) throws Exception {
		Preconditions.checkNotNull(callable);
		Preconditions.checkNotNull(timeoutUnit);
		Preconditions.checkArgument(timeoutDuration > 0L, "timeout must be positive: %s", timeoutDuration);
		Future<T> future = this.executor.submit(callable);

		try {
			if (amInterruptible) {
				try {
					return (T)future.get(timeoutDuration, timeoutUnit);
				} catch (InterruptedException var8) {
					future.cancel(true);
					throw var8;
				}
			} else {
				return Uninterruptibles.getUninterruptibly(future, timeoutDuration, timeoutUnit);
			}
		} catch (ExecutionException var9) {
			throw throwCause(var9, true);
		} catch (TimeoutException var10) {
			future.cancel(true);
			throw new UncheckedTimeoutException(var10);
		}
	}

	private static Exception throwCause(Exception e, boolean combineStackTraces) throws Exception {
		Throwable cause = e.getCause();
		if (cause == null) {
			throw e;
		} else {
			if (combineStackTraces) {
				StackTraceElement[] combined = ObjectArrays.concat(cause.getStackTrace(), e.getStackTrace(), StackTraceElement.class);
				cause.setStackTrace(combined);
			}

			if (cause instanceof Exception) {
				throw (Exception)cause;
			} else if (cause instanceof Error) {
				throw (Error)cause;
			} else {
				throw e;
			}
		}
	}

	private static Set<Method> findInterruptibleMethods(Class<?> interfaceType) {
		Set<Method> set = Sets.<Method>newHashSet();

		for (Method m : interfaceType.getMethods()) {
			if (declaresInterruptedEx(m)) {
				set.add(m);
			}
		}

		return set;
	}

	private static boolean declaresInterruptedEx(Method method) {
		for (Class<?> exType : method.getExceptionTypes()) {
			if (exType == InterruptedException.class) {
				return true;
			}
		}

		return false;
	}

	private static <T> T newProxy(Class<T> interfaceType, InvocationHandler handler) {
		Object object = Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, handler);
		return (T)interfaceType.cast(object);
	}
}
