package org.apache.logging.log4j.core.async;

import com.lmax.disruptor.ExceptionHandler;

public class AsyncLoggerDefaultExceptionHandler implements ExceptionHandler<RingBufferLogEvent> {
	public void handleEventException(Throwable throwable, long sequence, RingBufferLogEvent event) {
		StringBuilder sb = new StringBuilder(512);
		sb.append("AsyncLogger error handling event seq=").append(sequence).append(", value='");

		try {
			sb.append(event);
		} catch (Exception var7) {
			sb.append("[ERROR calling ").append(event.getClass()).append(".toString(): ");
			sb.append(var7).append("]");
		}

		sb.append("':");
		System.err.println(sb);
		throwable.printStackTrace();
	}

	public void handleOnStartException(Throwable throwable) {
		System.err.println("AsyncLogger error starting:");
		throwable.printStackTrace();
	}

	public void handleOnShutdownException(Throwable throwable) {
		System.err.println("AsyncLogger error shutting down:");
		throwable.printStackTrace();
	}
}
