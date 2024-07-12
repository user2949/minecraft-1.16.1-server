package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.SmoothRateLimiter.SmoothBursty;
import com.google.common.util.concurrent.SmoothRateLimiter.SmoothWarmingUp;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
@Beta
@GwtIncompatible
public abstract class RateLimiter {
	private final RateLimiter.SleepingStopwatch stopwatch;
	private volatile Object mutexDoNotUseDirectly;

	public static RateLimiter create(double permitsPerSecond) {
		return create(RateLimiter.SleepingStopwatch.createFromSystemTimer(), permitsPerSecond);
	}

	@VisibleForTesting
	static RateLimiter create(RateLimiter.SleepingStopwatch stopwatch, double permitsPerSecond) {
		RateLimiter rateLimiter = new SmoothBursty(stopwatch, 1.0);
		rateLimiter.setRate(permitsPerSecond);
		return rateLimiter;
	}

	public static RateLimiter create(double permitsPerSecond, long warmupPeriod, TimeUnit unit) {
		Preconditions.checkArgument(warmupPeriod >= 0L, "warmupPeriod must not be negative: %s", warmupPeriod);
		return create(RateLimiter.SleepingStopwatch.createFromSystemTimer(), permitsPerSecond, warmupPeriod, unit, 3.0);
	}

	@VisibleForTesting
	static RateLimiter create(RateLimiter.SleepingStopwatch stopwatch, double permitsPerSecond, long warmupPeriod, TimeUnit unit, double coldFactor) {
		RateLimiter rateLimiter = new SmoothWarmingUp(stopwatch, warmupPeriod, unit, coldFactor);
		rateLimiter.setRate(permitsPerSecond);
		return rateLimiter;
	}

	private Object mutex() {
		Object mutex = this.mutexDoNotUseDirectly;
		if (mutex == null) {
			synchronized (this) {
				mutex = this.mutexDoNotUseDirectly;
				if (mutex == null) {
					this.mutexDoNotUseDirectly = mutex = new Object();
				}
			}
		}

		return mutex;
	}

	RateLimiter(RateLimiter.SleepingStopwatch stopwatch) {
		this.stopwatch = Preconditions.checkNotNull(stopwatch);
	}

	public final void setRate(double permitsPerSecond) {
		Preconditions.checkArgument(permitsPerSecond > 0.0 && !Double.isNaN(permitsPerSecond), "rate must be positive");
		synchronized (this.mutex()) {
			this.doSetRate(permitsPerSecond, this.stopwatch.readMicros());
		}
	}

	abstract void doSetRate(double double1, long long2);

	public final double getRate() {
		synchronized (this.mutex()) {
			return this.doGetRate();
		}
	}

	abstract double doGetRate();

	@CanIgnoreReturnValue
	public double acquire() {
		return this.acquire(1);
	}

	@CanIgnoreReturnValue
	public double acquire(int permits) {
		long microsToWait = this.reserve(permits);
		this.stopwatch.sleepMicrosUninterruptibly(microsToWait);
		return 1.0 * (double)microsToWait / (double)TimeUnit.SECONDS.toMicros(1L);
	}

	final long reserve(int permits) {
		checkPermits(permits);
		synchronized (this.mutex()) {
			return this.reserveAndGetWaitLength(permits, this.stopwatch.readMicros());
		}
	}

	public boolean tryAcquire(long timeout, TimeUnit unit) {
		return this.tryAcquire(1, timeout, unit);
	}

	public boolean tryAcquire(int permits) {
		return this.tryAcquire(permits, 0L, TimeUnit.MICROSECONDS);
	}

	public boolean tryAcquire() {
		return this.tryAcquire(1, 0L, TimeUnit.MICROSECONDS);
	}

	public boolean tryAcquire(int permits, long timeout, TimeUnit unit) {
		long timeoutMicros = Math.max(unit.toMicros(timeout), 0L);
		checkPermits(permits);
		long microsToWait;
		synchronized (this.mutex()) {
			long nowMicros = this.stopwatch.readMicros();
			if (!this.canAcquire(nowMicros, timeoutMicros)) {
				return false;
			}

			microsToWait = this.reserveAndGetWaitLength(permits, nowMicros);
		}

		this.stopwatch.sleepMicrosUninterruptibly(microsToWait);
		return true;
	}

	private boolean canAcquire(long nowMicros, long timeoutMicros) {
		return this.queryEarliestAvailable(nowMicros) - timeoutMicros <= nowMicros;
	}

	final long reserveAndGetWaitLength(int permits, long nowMicros) {
		long momentAvailable = this.reserveEarliestAvailable(permits, nowMicros);
		return Math.max(momentAvailable - nowMicros, 0L);
	}

	abstract long queryEarliestAvailable(long long1);

	abstract long reserveEarliestAvailable(int integer, long long2);

	public String toString() {
		return String.format(Locale.ROOT, "RateLimiter[stableRate=%3.1fqps]", this.getRate());
	}

	private static void checkPermits(int permits) {
		Preconditions.checkArgument(permits > 0, "Requested permits (%s) must be positive", permits);
	}

	abstract static class SleepingStopwatch {
		protected SleepingStopwatch() {
		}

		protected abstract long readMicros();

		protected abstract void sleepMicrosUninterruptibly(long long1);

		public static final RateLimiter.SleepingStopwatch createFromSystemTimer() {
			return new RateLimiter.SleepingStopwatch() {
				final Stopwatch stopwatch = Stopwatch.createStarted();

				@Override
				protected long readMicros() {
					return this.stopwatch.elapsed(TimeUnit.MICROSECONDS);
				}

				@Override
				protected void sleepMicrosUninterruptibly(long micros) {
					if (micros > 0L) {
						Uninterruptibles.sleepUninterruptibly(micros, TimeUnit.MICROSECONDS);
					}
				}
			};
		}
	}
}
