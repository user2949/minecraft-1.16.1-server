package org.apache.logging.log4j.core.async;

import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.TimeoutException;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.jmx.RingBufferAdmin;
import org.apache.logging.log4j.core.util.ExecutorServices;
import org.apache.logging.log4j.core.util.Log4jThreadFactory;

class AsyncLoggerDisruptor extends AbstractLifeCycle {
	private static final int SLEEP_MILLIS_BETWEEN_DRAIN_ATTEMPTS = 50;
	private static final int MAX_DRAIN_ATTEMPTS_BEFORE_SHUTDOWN = 200;
	private volatile Disruptor<RingBufferLogEvent> disruptor;
	private ExecutorService executor;
	private String contextName;
	private boolean useThreadLocalTranslator = true;
	private long backgroundThreadId;
	private AsyncQueueFullPolicy asyncQueueFullPolicy;
	private int ringBufferSize;

	AsyncLoggerDisruptor(String contextName) {
		this.contextName = contextName;
	}

	public String getContextName() {
		return this.contextName;
	}

	public void setContextName(String name) {
		this.contextName = name;
	}

	Disruptor<RingBufferLogEvent> getDisruptor() {
		return this.disruptor;
	}

	@Override
	public synchronized void start() {
		if (this.disruptor != null) {
			LOGGER.trace("[{}] AsyncLoggerDisruptor not starting new disruptor for this context, using existing object.", this.contextName);
		} else {
			LOGGER.trace("[{}] AsyncLoggerDisruptor creating new disruptor for this context.", this.contextName);
			this.ringBufferSize = DisruptorUtil.calculateRingBufferSize("AsyncLogger.RingBufferSize");
			WaitStrategy waitStrategy = DisruptorUtil.createWaitStrategy("AsyncLogger.WaitStrategy");
			this.executor = Executors.newSingleThreadExecutor(Log4jThreadFactory.createDaemonThreadFactory("AsyncLogger[" + this.contextName + "]"));
			this.backgroundThreadId = DisruptorUtil.getExecutorThreadId(this.executor);
			this.asyncQueueFullPolicy = AsyncQueueFullPolicyFactory.create();
			this.disruptor = new Disruptor(RingBufferLogEvent.FACTORY, this.ringBufferSize, this.executor, ProducerType.MULTI, waitStrategy);
			ExceptionHandler<RingBufferLogEvent> errorHandler = DisruptorUtil.getAsyncLoggerExceptionHandler();
			this.disruptor.handleExceptionsWith(errorHandler);
			RingBufferLogEventHandler[] handlers = new RingBufferLogEventHandler[]{new RingBufferLogEventHandler()};
			this.disruptor.handleEventsWith(handlers);
			LOGGER.debug(
				"[{}] Starting AsyncLogger disruptor for this context with ringbufferSize={}, waitStrategy={}, exceptionHandler={}...",
				this.contextName,
				this.disruptor.getRingBuffer().getBufferSize(),
				waitStrategy.getClass().getSimpleName(),
				errorHandler
			);
			this.disruptor.start();
			LOGGER.trace("[{}] AsyncLoggers use a {} translator", this.contextName, this.useThreadLocalTranslator ? "threadlocal" : "vararg");
			super.start();
		}
	}

	@Override
	public boolean stop(long timeout, TimeUnit timeUnit) {
		Disruptor<RingBufferLogEvent> temp = this.getDisruptor();
		if (temp == null) {
			LOGGER.trace("[{}] AsyncLoggerDisruptor: disruptor for this context already shut down.", this.contextName);
			return true;
		} else {
			this.setStopping();
			LOGGER.debug("[{}] AsyncLoggerDisruptor: shutting down disruptor for this context.", this.contextName);
			this.disruptor = null;

			for (int i = 0; hasBacklog(temp) && i < 200; i++) {
				try {
					Thread.sleep(50L);
				} catch (InterruptedException var8) {
				}
			}

			try {
				temp.shutdown(timeout, timeUnit);
			} catch (TimeoutException var7) {
				temp.shutdown();
			}

			LOGGER.trace("[{}] AsyncLoggerDisruptor: shutting down disruptor executor.", this.contextName);
			ExecutorServices.shutdown(this.executor, timeout, timeUnit, this.toString());
			this.executor = null;
			if (DiscardingAsyncQueueFullPolicy.getDiscardCount(this.asyncQueueFullPolicy) > 0L) {
				LOGGER.trace(
					"AsyncLoggerDisruptor: {} discarded {} events.", this.asyncQueueFullPolicy, DiscardingAsyncQueueFullPolicy.getDiscardCount(this.asyncQueueFullPolicy)
				);
			}

			this.setStopped();
			return true;
		}
	}

	private static boolean hasBacklog(Disruptor<?> theDisruptor) {
		RingBuffer<?> ringBuffer = theDisruptor.getRingBuffer();
		return !ringBuffer.hasAvailableCapacity(ringBuffer.getBufferSize());
	}

	public RingBufferAdmin createRingBufferAdmin(String jmxContextName) {
		RingBuffer<RingBufferLogEvent> ring = this.disruptor == null ? null : this.disruptor.getRingBuffer();
		return RingBufferAdmin.forAsyncLogger(ring, jmxContextName);
	}

	EventRoute getEventRoute(Level logLevel) {
		int remainingCapacity = this.remainingDisruptorCapacity();
		return remainingCapacity < 0 ? EventRoute.DISCARD : this.asyncQueueFullPolicy.getRoute(this.backgroundThreadId, logLevel);
	}

	private int remainingDisruptorCapacity() {
		Disruptor<RingBufferLogEvent> temp = this.disruptor;
		return this.hasLog4jBeenShutDown(temp) ? -1 : (int)temp.getRingBuffer().remainingCapacity();
	}

	private boolean hasLog4jBeenShutDown(Disruptor<RingBufferLogEvent> aDisruptor) {
		if (aDisruptor == null) {
			LOGGER.warn("Ignoring log event after log4j was shut down");
			return true;
		} else {
			return false;
		}
	}

	public boolean tryPublish(RingBufferLogEventTranslator translator) {
		try {
			return this.disruptor.getRingBuffer().tryPublishEvent(translator);
		} catch (NullPointerException var3) {
			LOGGER.warn("[{}] Ignoring log event after log4j was shut down.", this.contextName);
			return false;
		}
	}

	void enqueueLogMessageInfo(RingBufferLogEventTranslator translator) {
		try {
			this.disruptor.publishEvent(translator);
		} catch (NullPointerException var3) {
			LOGGER.warn("[{}] Ignoring log event after log4j was shut down.", this.contextName);
		}
	}

	public boolean isUseThreadLocals() {
		return this.useThreadLocalTranslator;
	}

	public void setUseThreadLocals(boolean allow) {
		this.useThreadLocalTranslator = allow;
		LOGGER.trace("[{}] AsyncLoggers have been modified to use a {} translator", this.contextName, this.useThreadLocalTranslator ? "threadlocal" : "vararg");
	}
}
