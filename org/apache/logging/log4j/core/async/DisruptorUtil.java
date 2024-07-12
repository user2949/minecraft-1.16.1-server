package org.apache.logging.log4j.core.async;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.TimeoutBlockingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.async.AsyncLoggerConfigDisruptor.Log4jEventWrapper;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.core.util.Integers;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;

final class DisruptorUtil {
	private static final Logger LOGGER = StatusLogger.getLogger();
	private static final int RINGBUFFER_MIN_SIZE = 128;
	private static final int RINGBUFFER_DEFAULT_SIZE = 262144;
	private static final int RINGBUFFER_NO_GC_DEFAULT_SIZE = 4096;

	private DisruptorUtil() {
	}

	static long getTimeout(String propertyName, long defaultTimeout) {
		return PropertiesUtil.getProperties().getLongProperty(propertyName, defaultTimeout);
	}

	static WaitStrategy createWaitStrategy(String propertyName) {
		String key = propertyName.startsWith("AsyncLogger.") ? "AsyncLogger.Timeout" : "AsyncLoggerConfig.Timeout";
		long timeoutMillis = getTimeout(key, 10L);
		return createWaitStrategy(propertyName, timeoutMillis);
	}

	static WaitStrategy createWaitStrategy(String propertyName, long timeoutMillis) {
		String strategy = PropertiesUtil.getProperties().getStringProperty(propertyName, "TIMEOUT");
		LOGGER.trace("property {}={}", propertyName, strategy);
		String strategyUp = strategy.toUpperCase(Locale.ROOT);
		switch (strategyUp) {
			case "SLEEP":
				return new SleepingWaitStrategy();
			case "YIELD":
				return new YieldingWaitStrategy();
			case "BLOCK":
				return new BlockingWaitStrategy();
			case "BUSYSPIN":
				return new BusySpinWaitStrategy();
			case "TIMEOUT":
				return new TimeoutBlockingWaitStrategy(timeoutMillis, TimeUnit.MILLISECONDS);
			default:
				return new TimeoutBlockingWaitStrategy(timeoutMillis, TimeUnit.MILLISECONDS);
		}
	}

	static int calculateRingBufferSize(String propertyName) {
		int ringBufferSize = Constants.ENABLE_THREADLOCALS ? 4096 : 262144;
		String userPreferredRBSize = PropertiesUtil.getProperties().getStringProperty(propertyName, String.valueOf(ringBufferSize));

		try {
			int size = Integer.parseInt(userPreferredRBSize);
			if (size < 128) {
				size = 128;
				LOGGER.warn("Invalid RingBufferSize {}, using minimum size {}.", userPreferredRBSize, 128);
			}

			ringBufferSize = size;
		} catch (Exception var4) {
			LOGGER.warn("Invalid RingBufferSize {}, using default size {}.", userPreferredRBSize, ringBufferSize);
		}

		return Integers.ceilingNextPowerOfTwo(ringBufferSize);
	}

	static ExceptionHandler<RingBufferLogEvent> getAsyncLoggerExceptionHandler() {
		String cls = PropertiesUtil.getProperties().getStringProperty("AsyncLogger.ExceptionHandler");
		if (cls == null) {
			return new AsyncLoggerDefaultExceptionHandler();
		} else {
			try {
				Class<? extends ExceptionHandler<RingBufferLogEvent>> klass = (Class<? extends ExceptionHandler<RingBufferLogEvent>>)LoaderUtil.loadClass(cls);
				return (ExceptionHandler<RingBufferLogEvent>)klass.newInstance();
			} catch (Exception var2) {
				LOGGER.debug("Invalid AsyncLogger.ExceptionHandler value: error creating {}: ", cls, var2);
				return new AsyncLoggerDefaultExceptionHandler();
			}
		}
	}

	static ExceptionHandler<Log4jEventWrapper> getAsyncLoggerConfigExceptionHandler() {
		String cls = PropertiesUtil.getProperties().getStringProperty("AsyncLoggerConfig.ExceptionHandler");
		if (cls == null) {
			return new AsyncLoggerConfigDefaultExceptionHandler();
		} else {
			try {
				Class<? extends ExceptionHandler<Log4jEventWrapper>> klass = (Class<? extends ExceptionHandler<Log4jEventWrapper>>)LoaderUtil.loadClass(cls);
				return (ExceptionHandler<Log4jEventWrapper>)klass.newInstance();
			} catch (Exception var2) {
				LOGGER.debug("Invalid AsyncLoggerConfig.ExceptionHandler value: error creating {}: ", cls, var2);
				return new AsyncLoggerConfigDefaultExceptionHandler();
			}
		}
	}

	public static long getExecutorThreadId(ExecutorService executor) {
		Future<Long> result = executor.submit(new Callable<Long>() {
			public Long call() {
				return Thread.currentThread().getId();
			}
		});

		try {
			return (Long)result.get();
		} catch (Exception var4) {
			String msg = "Could not obtain executor thread Id. Giving up to avoid the risk of application deadlock.";
			throw new IllegalStateException("Could not obtain executor thread Id. Giving up to avoid the risk of application deadlock.", var4);
		}
	}
}
