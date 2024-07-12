package org.apache.logging.log4j.core.filter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.message.Message;

@Plugin(
	name = "BurstFilter",
	category = "Core",
	elementType = "filter",
	printObject = true
)
public final class BurstFilter extends AbstractFilter {
	private static final long NANOS_IN_SECONDS = 1000000000L;
	private static final int DEFAULT_RATE = 10;
	private static final int DEFAULT_RATE_MULTIPLE = 100;
	private static final int HASH_SHIFT = 32;
	private final Level level;
	private final long burstInterval;
	private final DelayQueue<BurstFilter.LogDelay> history = new DelayQueue();
	private final Queue<BurstFilter.LogDelay> available = new ConcurrentLinkedQueue();

	static BurstFilter.LogDelay createLogDelay(long expireTime) {
		return new BurstFilter.LogDelay(expireTime);
	}

	private BurstFilter(Level level, float rate, long maxBurst, Result onMatch, Result onMismatch) {
		super(onMatch, onMismatch);
		this.level = level;
		this.burstInterval = (long)(1.0E9F * ((float)maxBurst / rate));

		for (int i = 0; (long)i < maxBurst; i++) {
			this.available.add(createLogDelay(0L));
		}
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
		return this.filter(level);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
		return this.filter(level);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
		return this.filter(level);
	}

	@Override
	public Result filter(LogEvent event) {
		return this.filter(event.getLevel());
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0) {
		return this.filter(level);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1) {
		return this.filter(level);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2) {
		return this.filter(level);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3) {
		return this.filter(level);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4) {
		return this.filter(level);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
		return this.filter(level);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
		return this.filter(level);
	}

	@Override
	public Result filter(
		Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7
	) {
		return this.filter(level);
	}

	@Override
	public Result filter(
		Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8
	) {
		return this.filter(level);
	}

	@Override
	public Result filter(
		Logger logger,
		Level level,
		Marker marker,
		String msg,
		Object p0,
		Object p1,
		Object p2,
		Object p3,
		Object p4,
		Object p5,
		Object p6,
		Object p7,
		Object p8,
		Object p9
	) {
		return this.filter(level);
	}

	private Result filter(Level level) {
		if (!this.level.isMoreSpecificThan(level)) {
			return this.onMatch;
		} else {
			for (BurstFilter.LogDelay delay = (BurstFilter.LogDelay)this.history.poll(); delay != null; delay = (BurstFilter.LogDelay)this.history.poll()) {
				this.available.add(delay);
			}

			BurstFilter.LogDelay var3 = (BurstFilter.LogDelay)this.available.poll();
			if (var3 != null) {
				var3.setDelay(this.burstInterval);
				this.history.add(var3);
				return this.onMatch;
			} else {
				return this.onMismatch;
			}
		}
	}

	public int getAvailable() {
		return this.available.size();
	}

	public void clear() {
		for (BurstFilter.LogDelay delay : this.history) {
			this.history.remove(delay);
			this.available.add(delay);
		}
	}

	@Override
	public String toString() {
		return "level=" + this.level.toString() + ", interval=" + this.burstInterval + ", max=" + this.history.size();
	}

	@PluginBuilderFactory
	public static BurstFilter.Builder newBuilder() {
		return new BurstFilter.Builder();
	}

	public static class Builder implements org.apache.logging.log4j.core.util.Builder<BurstFilter> {
		@PluginBuilderAttribute
		private Level level = Level.WARN;
		@PluginBuilderAttribute
		private float rate = 10.0F;
		@PluginBuilderAttribute
		private long maxBurst;
		@PluginBuilderAttribute
		private Result onMatch = Result.NEUTRAL;
		@PluginBuilderAttribute
		private Result onMismatch = Result.DENY;

		public BurstFilter.Builder setLevel(Level level) {
			this.level = level;
			return this;
		}

		public BurstFilter.Builder setRate(float rate) {
			this.rate = rate;
			return this;
		}

		public BurstFilter.Builder setMaxBurst(long maxBurst) {
			this.maxBurst = maxBurst;
			return this;
		}

		public BurstFilter.Builder setOnMatch(Result onMatch) {
			this.onMatch = onMatch;
			return this;
		}

		public BurstFilter.Builder setOnMismatch(Result onMismatch) {
			this.onMismatch = onMismatch;
			return this;
		}

		public BurstFilter build() {
			if (this.rate <= 0.0F) {
				this.rate = 10.0F;
			}

			if (this.maxBurst <= 0L) {
				this.maxBurst = (long)(this.rate * 100.0F);
			}

			return new BurstFilter(this.level, this.rate, this.maxBurst, this.onMatch, this.onMismatch);
		}
	}

	private static class LogDelay implements Delayed {
		private long expireTime;

		LogDelay(long expireTime) {
			this.expireTime = expireTime;
		}

		public void setDelay(long delay) {
			this.expireTime = delay + System.nanoTime();
		}

		public long getDelay(TimeUnit timeUnit) {
			return timeUnit.convert(this.expireTime - System.nanoTime(), TimeUnit.NANOSECONDS);
		}

		public int compareTo(Delayed delayed) {
			long diff = this.expireTime - ((BurstFilter.LogDelay)delayed).expireTime;
			return Long.signum(diff);
		}

		public boolean equals(Object o) {
			if (this == o) {
				return true;
			} else if (o != null && this.getClass() == o.getClass()) {
				BurstFilter.LogDelay logDelay = (BurstFilter.LogDelay)o;
				return this.expireTime == logDelay.expireTime;
			} else {
				return false;
			}
		}

		public int hashCode() {
			return (int)(this.expireTime ^ this.expireTime >>> 32);
		}
	}
}
