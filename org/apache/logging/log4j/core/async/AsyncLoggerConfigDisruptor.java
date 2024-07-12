package org.apache.logging.log4j.core.async;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventTranslatorTwoArg;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceReportingEventHandler;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.impl.LogEventFactory;
import org.apache.logging.log4j.core.impl.MutableLogEvent;
import org.apache.logging.log4j.core.impl.ReusableLogEventFactory;
import org.apache.logging.log4j.core.jmx.RingBufferAdmin;
import org.apache.logging.log4j.core.util.ExecutorServices;
import org.apache.logging.log4j.core.util.Log4jThreadFactory;
import org.apache.logging.log4j.message.ReusableMessage;

public class AsyncLoggerConfigDisruptor extends AbstractLifeCycle implements AsyncLoggerConfigDelegate {
	private static final int MAX_DRAIN_ATTEMPTS_BEFORE_SHUTDOWN = 200;
	private static final int SLEEP_MILLIS_BETWEEN_DRAIN_ATTEMPTS = 50;
	private static final EventFactory<AsyncLoggerConfigDisruptor.Log4jEventWrapper> FACTORY = new EventFactory<AsyncLoggerConfigDisruptor.Log4jEventWrapper>() {
		public AsyncLoggerConfigDisruptor.Log4jEventWrapper newInstance() {
			return new AsyncLoggerConfigDisruptor.Log4jEventWrapper();
		}
	};
	private static final EventFactory<AsyncLoggerConfigDisruptor.Log4jEventWrapper> MUTABLE_FACTORY = new EventFactory<AsyncLoggerConfigDisruptor.Log4jEventWrapper>(
		
	) {
		public AsyncLoggerConfigDisruptor.Log4jEventWrapper newInstance() {
			return new AsyncLoggerConfigDisruptor.Log4jEventWrapper(new MutableLogEvent());
		}
	};
	private static final EventTranslatorTwoArg<AsyncLoggerConfigDisruptor.Log4jEventWrapper, LogEvent, AsyncLoggerConfig> TRANSLATOR = new EventTranslatorTwoArg<AsyncLoggerConfigDisruptor.Log4jEventWrapper, LogEvent, AsyncLoggerConfig>(
		
	) {
		public void translateTo(AsyncLoggerConfigDisruptor.Log4jEventWrapper ringBufferElement, long sequence, LogEvent logEvent, AsyncLoggerConfig loggerConfig) {
			ringBufferElement.event = logEvent;
			ringBufferElement.loggerConfig = loggerConfig;
		}
	};
	private static final EventTranslatorTwoArg<AsyncLoggerConfigDisruptor.Log4jEventWrapper, LogEvent, AsyncLoggerConfig> MUTABLE_TRANSLATOR = new EventTranslatorTwoArg<AsyncLoggerConfigDisruptor.Log4jEventWrapper, LogEvent, AsyncLoggerConfig>(
		
	) {
		public void translateTo(AsyncLoggerConfigDisruptor.Log4jEventWrapper ringBufferElement, long sequence, LogEvent logEvent, AsyncLoggerConfig loggerConfig) {
			((MutableLogEvent)ringBufferElement.event).initFrom(logEvent);
			ringBufferElement.loggerConfig = loggerConfig;
		}
	};
	private static final ThreadFactory THREAD_FACTORY = Log4jThreadFactory.createDaemonThreadFactory("AsyncLoggerConfig");
	private int ringBufferSize;
	private AsyncQueueFullPolicy asyncQueueFullPolicy;
	private Boolean mutable = Boolean.FALSE;
	private volatile Disruptor<AsyncLoggerConfigDisruptor.Log4jEventWrapper> disruptor;
	private ExecutorService executor;
	private long backgroundThreadId;
	private EventFactory<AsyncLoggerConfigDisruptor.Log4jEventWrapper> factory;
	private EventTranslatorTwoArg<AsyncLoggerConfigDisruptor.Log4jEventWrapper, LogEvent, AsyncLoggerConfig> translator;

	@Override
	public void setLogEventFactory(LogEventFactory logEventFactory) {
		this.mutable = this.mutable || logEventFactory instanceof ReusableLogEventFactory;
	}

	@Override
	public synchronized void start() {
		if (this.disruptor != null) {
			LOGGER.trace("AsyncLoggerConfigDisruptor not starting new disruptor for this configuration, using existing object.");
		} else {
			LOGGER.trace("AsyncLoggerConfigDisruptor creating new disruptor for this configuration.");
			this.ringBufferSize = DisruptorUtil.calculateRingBufferSize("AsyncLoggerConfig.RingBufferSize");
			WaitStrategy waitStrategy = DisruptorUtil.createWaitStrategy("AsyncLoggerConfig.WaitStrategy");
			this.executor = Executors.newSingleThreadExecutor(THREAD_FACTORY);
			this.backgroundThreadId = DisruptorUtil.getExecutorThreadId(this.executor);
			this.asyncQueueFullPolicy = AsyncQueueFullPolicyFactory.create();
			this.translator = this.mutable ? MUTABLE_TRANSLATOR : TRANSLATOR;
			this.factory = this.mutable ? MUTABLE_FACTORY : FACTORY;
			this.disruptor = new Disruptor(this.factory, this.ringBufferSize, this.executor, ProducerType.MULTI, waitStrategy);
			ExceptionHandler<AsyncLoggerConfigDisruptor.Log4jEventWrapper> errorHandler = DisruptorUtil.getAsyncLoggerConfigExceptionHandler();
			this.disruptor.handleExceptionsWith(errorHandler);
			AsyncLoggerConfigDisruptor.Log4jEventWrapperHandler[] handlers = new AsyncLoggerConfigDisruptor.Log4jEventWrapperHandler[]{
				new AsyncLoggerConfigDisruptor.Log4jEventWrapperHandler()
			};
			this.disruptor.handleEventsWith(handlers);
			LOGGER.debug(
				"Starting AsyncLoggerConfig disruptor for this configuration with ringbufferSize={}, waitStrategy={}, exceptionHandler={}...",
				this.disruptor.getRingBuffer().getBufferSize(),
				waitStrategy.getClass().getSimpleName(),
				errorHandler
			);
			this.disruptor.start();
			super.start();
		}
	}

	@Override
	public boolean stop(long timeout, TimeUnit timeUnit) {
		Disruptor<AsyncLoggerConfigDisruptor.Log4jEventWrapper> temp = this.disruptor;
		if (temp == null) {
			LOGGER.trace("AsyncLoggerConfigDisruptor: disruptor for this configuration already shut down.");
			return true;
		} else {
			this.setStopping();
			LOGGER.trace("AsyncLoggerConfigDisruptor: shutting down disruptor for this configuration.");
			this.disruptor = null;

			for (int i = 0; hasBacklog(temp) && i < 200; i++) {
				try {
					Thread.sleep(50L);
				} catch (InterruptedException var7) {
				}
			}

			temp.shutdown();
			LOGGER.trace("AsyncLoggerConfigDisruptor: shutting down disruptor executor for this configuration.");
			ExecutorServices.shutdown(this.executor, timeout, timeUnit, this.toString());
			this.executor = null;
			if (DiscardingAsyncQueueFullPolicy.getDiscardCount(this.asyncQueueFullPolicy) > 0L) {
				LOGGER.trace(
					"AsyncLoggerConfigDisruptor: {} discarded {} events.",
					this.asyncQueueFullPolicy,
					DiscardingAsyncQueueFullPolicy.getDiscardCount(this.asyncQueueFullPolicy)
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

	@Override
	public EventRoute getEventRoute(Level logLevel) {
		int remainingCapacity = this.remainingDisruptorCapacity();
		return remainingCapacity < 0 ? EventRoute.DISCARD : this.asyncQueueFullPolicy.getRoute(this.backgroundThreadId, logLevel);
	}

	private int remainingDisruptorCapacity() {
		Disruptor<AsyncLoggerConfigDisruptor.Log4jEventWrapper> temp = this.disruptor;
		return this.hasLog4jBeenShutDown(temp) ? -1 : (int)temp.getRingBuffer().remainingCapacity();
	}

	private boolean hasLog4jBeenShutDown(Disruptor<AsyncLoggerConfigDisruptor.Log4jEventWrapper> aDisruptor) {
		if (aDisruptor == null) {
			LOGGER.warn("Ignoring log event after log4j was shut down");
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void enqueueEvent(LogEvent event, AsyncLoggerConfig asyncLoggerConfig) {
		try {
			LogEvent logEvent = this.prepareEvent(event);
			this.enqueue(logEvent, asyncLoggerConfig);
		} catch (NullPointerException var4) {
			LOGGER.warn("Ignoring log event after log4j was shut down.");
		}
	}

	private LogEvent prepareEvent(LogEvent event) {
		LogEvent logEvent = this.ensureImmutable(event);
		if (logEvent instanceof Log4jLogEvent && logEvent.getMessage() instanceof ReusableMessage) {
			((Log4jLogEvent)logEvent).makeMessageImmutable();
		}

		return logEvent;
	}

	private void enqueue(LogEvent logEvent, AsyncLoggerConfig asyncLoggerConfig) {
		this.disruptor.getRingBuffer().publishEvent(this.translator, logEvent, asyncLoggerConfig);
	}

	@Override
	public boolean tryEnqueue(LogEvent event, AsyncLoggerConfig asyncLoggerConfig) {
		LogEvent logEvent = this.prepareEvent(event);
		return this.disruptor.getRingBuffer().tryPublishEvent(this.translator, logEvent, asyncLoggerConfig);
	}

	private LogEvent ensureImmutable(LogEvent event) {
		LogEvent result = event;
		if (event instanceof RingBufferLogEvent) {
			result = ((RingBufferLogEvent)event).createMemento();
		}

		return result;
	}

	@Override
	public RingBufferAdmin createRingBufferAdmin(String contextName, String loggerConfigName) {
		return RingBufferAdmin.forAsyncLoggerConfig(this.disruptor.getRingBuffer(), contextName, loggerConfigName);
	}

	public static class Log4jEventWrapper {
		private AsyncLoggerConfig loggerConfig;
		private LogEvent event;

		public Log4jEventWrapper() {
		}

		public Log4jEventWrapper(MutableLogEvent mutableLogEvent) {
			this.event = mutableLogEvent;
		}

		public void clear() {
			this.loggerConfig = null;
			if (this.event instanceof MutableLogEvent) {
				((MutableLogEvent)this.event).clear();
			} else {
				this.event = null;
			}
		}

		public String toString() {
			return String.valueOf(this.event);
		}
	}

	private static class Log4jEventWrapperHandler implements SequenceReportingEventHandler<AsyncLoggerConfigDisruptor.Log4jEventWrapper> {
		private static final int NOTIFY_PROGRESS_THRESHOLD = 50;
		private Sequence sequenceCallback;
		private int counter;

		private Log4jEventWrapperHandler() {
		}

		public void setSequenceCallback(Sequence sequenceCallback) {
			this.sequenceCallback = sequenceCallback;
		}

		public void onEvent(AsyncLoggerConfigDisruptor.Log4jEventWrapper event, long sequence, boolean endOfBatch) throws Exception {
			event.event.setEndOfBatch(endOfBatch);
			event.loggerConfig.asyncCallAppenders(event.event);
			event.clear();
			this.notifyIntermediateProgress(sequence);
		}

		private void notifyIntermediateProgress(long sequence) {
			if (++this.counter > 50) {
				this.sequenceCallback.set(sequence);
				this.counter = 0;
			}
		}
	}
}
