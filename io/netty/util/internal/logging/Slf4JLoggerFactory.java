package io.netty.util.internal.logging;

import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLoggerFactory;

public class Slf4JLoggerFactory extends InternalLoggerFactory {
	public static final InternalLoggerFactory INSTANCE = new Slf4JLoggerFactory();

	@Deprecated
	public Slf4JLoggerFactory() {
	}

	Slf4JLoggerFactory(boolean failIfNOP) {
		assert failIfNOP;

		if (LoggerFactory.getILoggerFactory() instanceof NOPLoggerFactory) {
			throw new NoClassDefFoundError("NOPLoggerFactory not supported");
		}
	}

	@Override
	public InternalLogger newInstance(String name) {
		return new Slf4JLogger(LoggerFactory.getLogger(name));
	}
}
