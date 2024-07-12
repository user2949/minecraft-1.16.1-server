package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Beta
@GwtIncompatible
public interface TimeLimiter {
	<T> T newProxy(T object, Class<T> class2, long long3, TimeUnit timeUnit);

	@CanIgnoreReturnValue
	<T> T callWithTimeout(Callable<T> callable, long long2, TimeUnit timeUnit, boolean boolean4) throws Exception;
}
